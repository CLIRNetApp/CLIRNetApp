package app.clirnet.com.clirnetapp.Utility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
//service runs in background to send data to server in 30 min interval
public class SyncDataService extends Service {
	private static final String TAG = "SyncDataService";
	private static final String BROADCAST_ACTION = "com.websmithing.broadcasttest.displayevent";
	private static final String PREFS_NAME = "SyncFlag";
	private static final String PREF_VALUE = "status";
	private final Handler handler = new Handler();
	int counter = 0;

	private ConnectionDetector connectionDetector;
	private SQLController sqlController;
	private ArrayList<RegistrationModel> patientIds_List=new ArrayList<>();
	private ArrayList<RegistrationModel> getPatientVisitIdsList=new ArrayList<>();
	private SQLiteHandler dbController;
	private String patientInfoArayString;
	private String patientVisitHistorArayString;
	private AppController appController;




	@Override
	public void onCreate() {
		super.onCreate();

		Intent intent = new Intent(BROADCAST_ACTION);
		connectionDetector = new ConnectionDetector(getApplicationContext());
		appController= new AppController();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		//get imei no of user ph
		TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imeiid = mngr.getDeviceId();
		Log.e("id","servic started"+ imeiid);

		try {

			sqlController = new SQLController(getApplicationContext());
			sqlController.open();
			dbController = new SQLiteHandler(getApplicationContext());
			JSONArray patientInfoarray = dbController.getResultsForPatientInformation();
			Log.e("PatientInfoarray", " " + patientInfoarray);
			patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());

			JSONArray patientVisitHistoryarray = dbController.getResultsForPatientHistory();

			patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());
			Log.e("PatientVisitHistor", " " + patientVisitHistoryarray);

		} catch (Exception e) {
			e.printStackTrace();
			appController.appendLog(appController.getDateTime()+" " +"/ "+"Sync Service" + e);
		}



		handler.removeCallbacks(sendUpdatesToUI);

		handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
		//Log.e("imsending", "I am sending the data after 1 sec");

	}

	private final Runnable sendUpdatesToUI = new Runnable() {
		public void run() {
			//DisplayLoggingInfo();   //this used to test the service weather it is sending data to activity or not 29/8/2016 Ashish
			try {
				asyncTask();
			} catch (ClirNetAppException e) {
				e.printStackTrace();
				appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e);

			}
			handler.postDelayed(this, 1800000); // 10 seconds 10000 //1800000
		}
	};

	private void asyncTask() throws ClirNetAppException {

		patientIds_List=  sqlController.getPatientIdsFalg0();
		getPatientVisitIdsList=sqlController.getPatientVisitIdsFalg0();

		/*Log.e("nodata12","no data to send"+patientIds_List.size());
		Log.e("nodata12","no data to send"+getPatientVisitIdsList.size());*/


		boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
		if (isInternetPresent) {
			if(patientIds_List.size()!= 0 || getPatientVisitIdsList.size()!= 0)
			{

			//Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
			//send data to server after 30 min

				getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
						.edit()
						.putString(PREF_VALUE, "1")
						.apply();
				Log.e("sending12", "data is sending");
				sendDataToServer(patientInfoArayString, patientVisitHistorArayString);



		}else{
				Log.e("nodata12", "no data to send");
		}
		}
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		handler.removeCallbacks(sendUpdatesToUI);
		Log.e("stoped","service stoped");
		super.onDestroy();
	}

	//this will send data to server
	private void sendDataToServer(final String patient_details, final String patient_visits) {

		String tag_string_req = "req_login";


		StringRequest strReq = new StringRequest(Request.Method.POST,
				AppConfig.URL_SYCHRONISED_TOSERVER, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d(TAG, "Login Response: " + response);

				try {
					JSONObject jObj = new JSONObject(response);


						JSONObject user = jObj.getJSONObject("data");

						String msg = user.getString("msg");
						//String result = user.getString("result");

						if (msg.equals("OK")) {

							Log.e("TAG", "" + patientIds_List.size());
							int size = patientIds_List.size();
							for(int i=0;i<size;i++){
								String patientId=patientIds_List.get(i).getPat_id();
								String  flag="1";
								//Log.e("TAG"+i," "+patientId);
								//update flag after success
								dbController.FlagupdatePatientPersonal(patientId,flag);
							}


							Log.e("TAG", "" + getPatientVisitIdsList.size());
							int listsize = getPatientVisitIdsList.size();
							for(int i=0;i<listsize;i++){
								String patientId=getPatientVisitIdsList.get(i).getPat_id();
								String patientVisitId=getPatientVisitIdsList.get(i).getKey_visit_id();
								String  flag="1";
								Log.e("TAG" + i, " " + patientId + "patientVisitId  "+patientVisitId);
								//update flag after success
								dbController.FlagupdatePatientVisit(patientVisitId,flag);
							}
							Log.e("sending12",  "Data Sent");
							getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
									.edit()
									.putString(PREF_VALUE, "2")
									.apply();


						} else {
							Log.e("TAG", "" + msg);

						}

				} catch (JSONException e) {
					// JSON error
					e.printStackTrace();
					appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e);

					Log.e("Json error", "Json error: " + e.getMessage());

				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Login Error: " + error.getMessage());
				appController.appendLog(appController.getDateTime()+" " +"/ "+"Sync Service" + error);



			}
		}) {

			@Override
			protected Map<String, String> getParams() {
				// Posting parameters to login url
				Map<String, String> params = new HashMap<>();

				params.put("apikey", "PFFt0436yjfn0945DevOp0958732Cons3214556");
				params.put("patient_details", patient_details);
				params.put("patient_visits", patient_visits);


				return params;
			}

		};

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
	}


}

