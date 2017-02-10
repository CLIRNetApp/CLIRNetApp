package app.clirnet.com.clirnetapp.Utility;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.GetBannerImageTask;
import app.clirnet.com.clirnetapp.app.UploadBannerDataAsyncTask;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.logfilesending.LogFileAsyncTask;
import app.clirnet.com.clirnetapp.models.LoginModel;
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
    private ArrayList<RegistrationModel> patientIds_List = new ArrayList<>();
    private ArrayList<RegistrationModel> getPatientVisitIdsList = new ArrayList<>();
    private SQLiteHandler dbController;
    private String patientInfoArayString;
    private String patientVisitHistorArayString;
    private AppController appController;
    private String mUserName;
    private String mPassword;
    private String mApiKey;
    private String mPhoneNumber;
    private String mEmailId;
    private static final int MY_NOTIFICATION_ID = 1;
    private NotificationManager nMn;
    private String docId;
    private String doctor_membership_number;
    private String company_id;
    private BannerClass bannerClass;
    private ProgressBar pb;
    private Bitmap bmp;
    private ImageDownloader mDownloader;
    private String start_time1;

    @Override
    public void onCreate() {
        super.onCreate();

        connectionDetector = new ConnectionDetector(getApplicationContext());
        appController = new AppController();

    }



    @Override
    public void onStart(Intent intent, int startId) {

        nMn = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            dbController = new SQLiteHandler(getApplicationContext());
            if (bannerClass == null) {
                bannerClass = new BannerClass(getApplicationContext());
            }

            patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());
           // Log.e("patientInfoArayString"," "+patientInfoArayString);

            patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());

            mPhoneNumber = sqlController.getPhoneNumber();
            mEmailId = sqlController.getDocdoctorEmail();
            docId = sqlController.getDoctorId();

            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            company_id = sqlController.getCompany_id();
            getUsernamePasswordFromDatabase();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        handler.removeCallbacks(sendUpdatesToUI);

        handler.postDelayed(sendUpdatesToUI,50000); // 2 min  or 120 second 180000

    }

    private final Runnable sendUpdatesToUI = new Runnable() {
        public void run() {


            //DisplayLoggingInfo();   //this used to test the service weather it is sending data to activity or not 29/8/2016 Ashish
            try {

                sendDataToServerAsyncTask();
               // Log.e("Started", "Service Started1  " + appController.getDateTimenew());


            } catch (ClirNetAppException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            handler.postDelayed(this, 1800000); // 10 seconds 10000 //1800000
        }
    };

    private void sendDataToServerAsyncTask() throws ClirNetAppException {

        ArrayList<String> bannerDisplayIds_List;
        ArrayList<String> bannaerClickedIdsList;
        //patient and patient history data count

        patientIds_List = sqlController.getPatientIdsFalg0();
        getPatientVisitIdsList = sqlController.getPatientVisitIdsFalg0();

        doctor_membership_number = sqlController.getDoctorMembershipIdNew();
        company_id = sqlController.getCompany_id();

        //banner click and display data count
        bannerDisplayIds_List = sqlController.getBannerDisplaysFalg0();
        bannaerClickedIdsList = sqlController.getBannerClickedFalg0();


        String bannerDisplayArayString = String.valueOf(dbController.getResultsForBannerDisplay());



        String bannerClickedArayString = String.valueOf(dbController.getResultsForBannerClicked());


        boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
        if (isInternetPresent) {

            String start_time=appController.getDateTimenew();

            if (bannaerClickedIdsList.size() > 0 & bannerDisplayIds_List.size() > 0) {
              new UploadBannerDataAsyncTask(mUserName, mPassword, getApplicationContext(), bannerDisplayArayString, bannerClickedArayString, doctor_membership_number, docId, bannerDisplayIds_List, bannaerClickedIdsList,start_time);
            }

            if (mUserName != null && mPassword != null) {

                new GetBannerImageTask(getApplicationContext(), mUserName, mPassword, doctor_membership_number, company_id); //send log file to server


            }

            if (patientIds_List.size() != 0 || getPatientVisitIdsList.size() != 0) {


                //send data to server after 30 min

                getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString(PREF_VALUE, "1")
                        .apply();

                 start_time1=appController.getDateTimenew();
                sendDataToServer(patientInfoArayString, patientVisitHistorArayString, doctor_membership_number, docId, getPatientVisitIdsList.size(), patientIds_List.size());

                new LogFileAsyncTask(getApplicationContext(), mUserName, mPassword,start_time1); //send log file to server
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

        super.onDestroy();
    }

    //this will send data to server
    private void sendDataToServer(final String patient_details, final String patient_visits, final String docMemId, final String docId, final int patient_visits_count, final int patient_details_count) {

        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SYCHRONISED_TOSERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Login Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONObject user = jObj.getJSONObject("data");

                    String msg = user.getString("msg");


                    if (msg.equals("OK")) {

                        int size = patientIds_List.size();

                        for (int i = 0; i < size; i++) {
                            String patientId = patientIds_List.get(i).getPat_id();
                            String flag = "1";

                            dbController.FlagupdatePatientPersonal(patientId, flag);
                        }


                        int listsize = getPatientVisitIdsList.size();
                        for (int i = 0; i < listsize; i++) {

                            String patientVisitId = getPatientVisitIdsList.get(i).getKey_visit_id();
                            String flag = "1";

                            //saved last updated sync time in shared prefrence
                            //update flag after success
                            dbController.FlagupdatePatientVisit(patientVisitId, flag);
                        }

                        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                                .edit()
                                .putString(PREF_VALUE, "2")
                                .apply();

                        String time = appController.getDateTimenew();
                        lastSyncTime(time);

                        appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sync to server from sync service  : patient Visit Count :" + listsize + " patient Count  :" + size);
                        dbController.addAsynctascRun_status("Data Synced",start_time1,time,"");

                    } else if (msg.equals("Credentials Mismatch or Not Found")) {

                        appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sync to server from sync service  : patient Visit Count :" + msg);

                        // showNotification();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());


                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e(TAG, "Login Error: " + error.getMessage());
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + error + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                String keyid = getResources().getString(R.string.apikey);
                params.put("apikey", keyid);
                params.put("patient_details", patient_details);
                params.put("patient_visits", patient_visits);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
                params.put("patient_visits_count", String.valueOf(patient_visits_count));
                params.put("patient_details_count", String.valueOf(patient_details_count));
                return checkParams(params);

            }
            private Map<String, String> checkParams(Map<String, String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void getUsernamePasswordFromDatabase() {
        Cursor cursor = null;
        SQLController sqlController1 = null;
        try {

            sqlController1 = new SQLController(getApplicationContext());
            sqlController1.open();


            ArrayList<LoginModel> al;
            al = sqlController1.getUserLoginRecrodsNew();
            if (al.size() != 0) {
                mUserName = al.get(0).getUserName();
                mPassword = al.get(0).getPassowrd();
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "SyncDataService " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            if (sqlController1 != null) {
                sqlController1.close();
            }
        }
    }

    //store last sync time in prefrence
    public void lastSyncTime(String lastSyncTime) {

        getSharedPreferences("SyncFlag", MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }

}

