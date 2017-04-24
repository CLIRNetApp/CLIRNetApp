package app.clirnet.com.clirnetapp.utility;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.DoctorDeatilsAsynTask;
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

    private static final String PREFS_NAME = "SyncFlag";
    private static final String PREF_VALUE = "status";
    private Handler handler;

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

    private String docId;
    private String doctor_membership_number;
    private String company_id;
    private BannerClass bannerClass;

    private String start_time1;
    private String pat_personal_count;
    private String pat_visit_count;


    @Override
    public void onCreate() {
        super.onCreate();

        connectionDetector = new ConnectionDetector(getApplicationContext());
        appController = new AppController();
        handler = new Handler();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            if (dbController == null) {
                dbController = SQLiteHandler.getInstance(getApplicationContext());
            }
            if (bannerClass == null) {
                bannerClass = new BannerClass(getApplicationContext());
            }

            docId = sqlController.getDoctorId();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            company_id = sqlController.getCompany_id();
            getUsernamePasswordFromDatabase();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        handler.removeCallbacks(sendUpdatesToUI);

        handler.postDelayed(sendUpdatesToUI, 50000); // 2 min  or 120 second 180000
    }

    private volatile boolean running = true;
    private final Runnable sendUpdatesToUI = new Runnable() {
        public void run() {

            //this used to test the service weather it is sending data to activity or not 29/8/2016 Ashish
            try {
                if (running) {
                    sendDataToServerAsyncTask();
                    checkIfImageExist();
                }

            } catch (ClirNetAppException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            handler.postDelayed(this, 1800000); // 10 seconds 10000 //1800000 30 min time interval
        }
    };

    private void checkIfImageExist() {
        try {
            ArrayList<String> bannerimgNames = bannerClass.getImageName();
            int imgListSize = bannerimgNames.size();
            if (imgListSize > 0) {
                for (int i = 0; i < imgListSize; i++) {
                    String url = bannerimgNames.get(i);
                    if (!appController.checkifImageExists(url)) {
                        bannerClass.updateBannerImgDownloadStatus1(url, "pending");
                    }
                }
            }
        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync DataService: getImageName " + e);
        }
    }

    private void sendDataToServerAsyncTask() throws ClirNetAppException {

        boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet

        if (isInternetPresent) {
            String start_time = appController.getDateTimenew();

            //  new CallDataFromOne(getApplicationContext(), mUserName, mPassword, start_time, docId, doctor_membership_number);
           // new LogFileAsyncTask(getApplicationContext(), mUserName, mPassword, doctor_membership_number, docId, start_time); //send log file to server

            ArrayList<String> bannerDisplayIds_List;
            ArrayList<String> bannaerClickedIdsList;
            //patient and patient history data count

            patientIds_List = sqlController.getPatientIdsFalg0();
            getPatientVisitIdsList = sqlController.getPatientVisitIdsFalg0();
            if (doctor_membership_number == null) {
                doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            }
            if (company_id == null) {
                company_id = sqlController.getCompany_id();
            }


            //banner click and display data count
            bannerDisplayIds_List = sqlController.getBannerDisplaysFalg0();
            bannaerClickedIdsList = sqlController.getBannerClickedFalg0();

            patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());

            patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());

            /*count no of patient_id occurance od patient and visit string from db*/
            pat_personal_count = appController.getCharFreq(patientInfoArayString);

            pat_visit_count = appController.getCharFreq(patientVisitHistorArayString);

            int incomplete_count = sqlController.getPrescriptionQueueCount();
            String incompletePrescriptionQueueCount = String.valueOf(incomplete_count);//incomplete prescription queue count

            // get banner clicked and display data in string
            String bannerDisplayArayString = String.valueOf(dbController.getResultsForBannerDisplay());


            String bannerClickedArayString = String.valueOf(dbController.getResultsForBannerClicked());

            if (bannaerClickedIdsList.size() > 0 || bannerDisplayIds_List.size() > 0) {

                new UploadBannerDataAsyncTask(mUserName, mPassword, getApplicationContext(), bannerDisplayArayString, bannerClickedArayString, doctor_membership_number, docId, bannerDisplayIds_List, bannaerClickedIdsList, start_time);
            }

            if (mUserName != null && mPassword != null) {

                new GetBannerImageTask(getApplicationContext(), mUserName, mPassword, doctor_membership_number, company_id, start_time); //send log file to server
            }

            new DoctorDeatilsAsynTask(getApplicationContext(), mUserName, mPassword, doctor_membership_number, docId, start_time);

            if (patientIds_List.size() != 0 || getPatientVisitIdsList.size() != 0) {

                //send data to server after 30 min

                getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString(PREF_VALUE, "1")
                        .apply();

                start_time1 = appController.getDateTimenew();
                appController.appendLog(appController.getDateTime() + " " + " / " + "Sending Data to server from Sync Service" + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                sendDataToServer(patientInfoArayString, patientVisitHistorArayString, doctor_membership_number, docId, getPatientVisitIdsList.size(), patientIds_List.size(), new AppController().getDateTimenew(),incompletePrescriptionQueueCount);
                new LogFileAsyncTask(getApplicationContext(), mUserName, mPassword, doctor_membership_number, docId, start_time); //send log file to server

            }
        }
    }

        @Override
        public IBinder onBind (Intent intent){
            return null;
        }

        @Override
        public void onDestroy () {
            super.onDestroy();

            handler.removeCallbacks(sendUpdatesToUI);
            if (appController != null) {
                appController = null;
            }
            if (connectionDetector != null) {
                connectionDetector = null;
            }
            if (sqlController != null) {
                sqlController = null;
            }
            if (dbController != null) {
                dbController = null;
            }
            if (bannerClass != null) {
                bannerClass = null;
            }
            running = false;
            patientIds_List = null;
            getPatientVisitIdsList = null;
            doctor_membership_number = null;
            docId = null;
            patientInfoArayString = null;
            patientVisitHistorArayString = null;
            pat_personal_count = null;
            pat_visit_count = null;
            mUserName = null;
            mPassword = null;

        }

        //this will send data to server

    private void sendDataToServer(final String patient_details, final String patient_visits, final String docMemId, final String docId, final int patient_visits_count, final int patient_details_count, final String start_time,final String incompletePrescriptionQueueCount) {

        String tag_string_req = "req_sync2";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SYCHRONISED_TOSERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                appController.appendLog(appController.getDateTimenew() + " " + " / " + "Response from Server Service" + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONObject user = jObj.getJSONObject("data");

                    String msg = user.getString("msg");

                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync DataService data is sync to server : Messaage: " + msg);

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

                        appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sync to server from sync service  : patient Visit Count :" + pat_visit_count + " patient Count  :" + pat_personal_count);
                        dbController.addAsynctascRun_status("Data Synced", start_time1, time, "");

                    } else if (msg.equals("Credentials Mismatch or Not Found")) {

                        appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Data Service:" + msg);


                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Data Service Exception" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());


                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e("ServiceError", "Login Error: " + error.getMessage());

                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Data Service Error  " + error + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                String keyid = getResources().getString(R.string.apikey);
                params.put("apikey", keyid);
                params.put("username", mUserName);
                params.put("patient_details", patient_details);
                params.put("patient_visits", patient_visits);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
                params.put("patient_visits_count", String.valueOf(patient_visits_count));
                params.put("patient_details_count", String.valueOf(patient_details_count));
                params.put("process_start_time", start_time);
                params.put("prescriptionQueueCount", incompletePrescriptionQueueCount);
                return checkParams(params);

            }

            private Map<String, String> checkParams(Map<String, String> map) {
                for (Map.Entry<String, String> pairs : map.entrySet())
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                return map;
            }
        };
        int socketTimeout = 180000;  //180 seconds - change to what you want
        int retryforTimes = 1;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void getUsernamePasswordFromDatabase() {

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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "SyncDataService getUsernamePasswordFromDatabase " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {

            if (sqlController1 != null) {
                sqlController1.close();
            }
        }
    }


    //store last sync time in prefrence
    private void lastSyncTime(String lastSyncTime) {

        getSharedPreferences("SyncFlag", MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }
}

