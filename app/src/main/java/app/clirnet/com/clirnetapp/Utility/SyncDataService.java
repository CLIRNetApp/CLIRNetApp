package app.clirnet.com.clirnetapp.Utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
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

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.LoginActivity;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

//service runs in background to send data to server in 30 min interval
public class SyncDataService extends Service {
    private static final String TAG = "SyncDataService";
    private static final String LAST_SYNC = "lastSyncTime";
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

    @Override
    public void onCreate() {
        super.onCreate();

        connectionDetector = new ConnectionDetector(getApplicationContext());
        appController = new AppController();

    }

    @Override
    public void onStart(Intent intent, int startId) {

        nMn = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //get imei no of user ph
        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiid = mngr.getDeviceId();
        Log.e("id", "servic started" + imeiid);
       // showNotification();


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
            Log.e("PatientVisitHistor", " " + patientInfoArayString);
            mPhoneNumber = sqlController.getPhoneNumber();
            mEmailId = sqlController.getDocdoctorEmail();
            docId = sqlController.getDoctorId();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

            getUsernamePasswordFromDatabase();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        handler.removeCallbacks(sendUpdatesToUI);

        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
        //Log.e("imsending", "I am sending the data after 1 sec");

    }



    private final Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            //DisplayLoggingInfo();   //this used to test the service weather it is sending data to activity or not 29/8/2016 Ashish
            try {
                sendDataToServerAsyncTask();

            } catch (ClirNetAppException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
            handler.postDelayed(this, 1800000); // 10 seconds 10000 //1800000
        }
    };

    private void sendDataToServerAsyncTask() throws ClirNetAppException {


        patientIds_List = sqlController.getPatientIdsFalg0();
        getPatientVisitIdsList = sqlController.getPatientVisitIdsFalg0();

		/*Log.e("nodata12","no data to send"+patientIds_List.size());
        Log.e("nodata12","no data to send"+getPatientVisitIdsList.size());*/


        boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
        if (isInternetPresent) {
            if (patientIds_List.size() != 0 || getPatientVisitIdsList.size() != 0) {

                //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                //send data to server after 30 min

                getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString(PREF_VALUE, "1")
                        .apply();
               // Log.e("sending12", "data is sending");

              //  sendDataToServer(patientInfoArayString, patientVisitHistorArayString,doctor_membership_number,docId,patientIds_List.size(),getPatientVisitIdsList.size());
                sendDataToServer(patientInfoArayString, patientVisitHistorArayString,doctor_membership_number,docId,getPatientVisitIdsList.size(),patientIds_List.size());

               // new LogFileAsyncTask(mUserName, mPassword); //send log file to server


            } else {
              //  Log.e("nodata12", "no data to send");
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
        Log.e("stoped", "service stoped");
        super.onDestroy();
    }

    //this will send data to server
    private void sendDataToServer(final String patient_details, final String patient_visits, final String docMemId, final String docId, final int  patient_visits_count, final int patient_details_count  ) {

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


                    if (msg.equals("OK")) {

                       // Log.e("TAG", "" + patientIds_List.size());
                        int size = patientIds_List.size();
                        for (int i = 0; i < size; i++) {
                            String patientId = patientIds_List.get(i).getPat_id();
                            String flag = "1";

                            dbController.FlagupdatePatientPersonal(patientId, flag);
                        }


                       // Log.e("TAG", "" + getPatientVisitIdsList.size());
                        int listsize = getPatientVisitIdsList.size();
                        for (int i = 0; i < listsize; i++) {
                            // String patientId = getPatientVisitIdsList.get(i).getPat_id();
                            String patientVisitId = getPatientVisitIdsList.get(i).getKey_visit_id();
                            String flag = "1";
                           // Log.e("TAG" + i, "patientVisitId  " + patientVisitId);
                            //saved last updated sync time in shared prefrence

                            //update flag after success
                            dbController.FlagupdatePatientVisit(patientVisitId, flag);
                        }

                        //Log.e("sending12", "Data Sent");

                        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                                .edit()
                                .putString(PREF_VALUE, "2")
                                .apply();

                        String time=appController.getDateTimenew();
                        lastSyncTime(time);

                        appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sync to server from sync service  : patient Visit Count :" + listsize + " patient Count  :" + size);

                     //   appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sent to server");

                    } else if (msg.equals("Credentials Mismatch or Not Found")) {
                        Log.e("TAG", "" + msg);
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sync to server from sync service  : patient Visit Count :" + msg);

                        // showNotification();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());

                    Log.e("Json error", "Json error: " + e.getMessage());

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + error+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                String keyid = getResources().getString(R.string.apikey);
                params.put("apikey",keyid);
                params.put("patient_details", patient_details);
                params.put("patient_visits", patient_visits);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
                params.put("patient_visits_count", String.valueOf(patient_visits_count));
                params.put("patient_details_count", String.valueOf(patient_details_count));


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void showNotification() {
        CharSequence text = "Login with new Password";
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, LoginActivity.class), 0);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                .setContentTitle("Password Changed")
                .setContentText(text)
                .setSmallIcon(R.drawable.cliricon)
                .setContentIntent(contentIntent)
                //.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(uri)
                .setLights(Color.RED, 3000, 3000)
                .setOngoing(false);

        Notification notification = builder.getNotification();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        nMn.notify(MY_NOTIFICATION_ID, notification);

    }
    private void getUsernamePasswordFromDatabase() {
        Cursor cursor = null;
        SQLController sqlController1 = null;
        try {

            sqlController1 = new SQLController(getApplicationContext());
            sqlController1.open();


            ArrayList<LoginModel> al = new ArrayList<>();
            al = sqlController1.getUserLoginRecrodsNew();
            if (al.size() != 0) {
                mUserName = al.get(0).getUserName();
                mPassword = al.get(0).getPassowrd();
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "SyncDataService " + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
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
    public void lastSyncTime(String lastSyncTime ) {

        getSharedPreferences("SyncFlag", MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }
}

