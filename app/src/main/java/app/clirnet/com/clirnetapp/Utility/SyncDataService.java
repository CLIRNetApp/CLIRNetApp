package app.clirnet.com.clirnetapp.Utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.LoginActivity;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
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

    @Override
    public void onCreate() {
        super.onCreate();

        connectionDetector = new ConnectionDetector(getApplicationContext());
        appController = new AppController();

    }

    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    public void onStart(Intent intent, int startId) {

        nMn = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //get imei no of user ph
        //TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        Log.e("Started", "Service Started" + appController.getDateTimenew());

        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            dbController = new SQLiteHandler(getApplicationContext());
            if (bannerClass == null) {
                bannerClass = new BannerClass(getApplicationContext());
            }

            patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());

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

        handler.postDelayed(sendUpdatesToUI, 1000); // 2 min  or 120 second 180000

    }

    private final Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            //DisplayLoggingInfo();   //this used to test the service weather it is sending data to activity or not 29/8/2016 Ashish
            try {

                sendDataToServerAsyncTask();
                Log.e("Started", "Service Started1  " + appController.getDateTimenew());

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


        //banner click and display data count
        bannerDisplayIds_List = sqlController.getBannerDisplaysFalg0();
        bannaerClickedIdsList = sqlController.getBannerClickedFalg0();

        Log.e("patientIds_List", "  " + bannerDisplayIds_List.size() + "     " + bannaerClickedIdsList.size());

        String bannerDisplayArayString = String.valueOf(dbController.getResultsForBannerDisplay());

        Log.e("bannerDisplayArayString", "  " + bannerDisplayArayString);

        String bannerClickedArayString = String.valueOf(dbController.getResultsForBannerClicked());
        Log.e("bannerClickedArayString", "  " + bannerClickedArayString);

        boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
        if (isInternetPresent) {
            String start_time=appController.getDateTimenew();

            if (bannaerClickedIdsList.size() > 0 || bannerDisplayIds_List.size() > 0) {
                new UploadBannerDataAsyncTask(mUserName, mPassword, getApplicationContext(), bannerDisplayArayString, bannerClickedArayString, doctor_membership_number, docId, bannerDisplayIds_List, bannaerClickedIdsList,start_time);
            }
            String apiKey = getResources().getString(R.string.apikey);

            if (mUserName != null && mPassword != null) {

                getBannersData(mUserName, mPassword, apiKey, doctor_membership_number, company_id);
            }

            if (patientIds_List.size() != 0 || getPatientVisitIdsList.size() != 0) {


                //send data to server after 30 min

                getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                        .edit()
                        .putString(PREF_VALUE, "1")
                        .apply();

                String start_time1=appController.getDateTimenew();
                sendDataToServer(patientInfoArayString, patientVisitHistorArayString, doctor_membership_number, docId, getPatientVisitIdsList.size(), patientIds_List.size());

                new LogFileAsyncTask(getApplicationContext(), mUserName, mPassword,start_time1); //send log file to server
                //   new  UploadBannerDataAsyncTask(getApplicationContext(),bannerDisplayArayString,bannerClickedArayString,doctor_membership_number,docId,bannerDisplayIds_List,bannaerClickedIdsList);
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
                Log.d(TAG, "Login Response: " + response);

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


                        // Log.e("TAG", "" + getPatientVisitIdsList.size());
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


                    } else if (msg.equals("Credentials Mismatch or Not Found")) {
                        Log.e("TAG", "" + msg);
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "data is sync to server from sync service  : patient Visit Count :" + msg);

                        // showNotification();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Service" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                    // Log.e("Json error", "Json error: " + e.getMessage());

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

    private void getBannersData(final String savedUserName, final String savedUserPassword, final String apiKey, final String docMemId, final String companyid) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BANNER_API, new Response.Listener<String>() {


            @Override
            public void onResponse(final String response) {


                new getBannerImagesFromServer().execute(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e(TAG, "Login Error: Failed To Initalize Data" + error.getMessage()+""+Thread.currentThread().getStackTrace()[2].getLineNumber());
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment getting banners" + error);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("username", savedUserName);
                params.put("password", savedUserPassword);
                params.put("apikey", getResources().getString(R.string.apikey));
                params.put("membershipid", docMemId);
                params.put("companyid", companyid);
                // Log.e("apikey",""+savedUserName + "  "+savedUserPassword + " "+ getResources().getString(R.string.apikey));
                return params;
            }

        };
        //todo need to check the retry code for multiple times
        //this will retry the request for 2 times
        int socketTimeout = 30000;//30 seconds - change to what you want
        int retryforTimes = 2;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        AppController.getInstance().setPriority(Request.Priority.HIGH);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private class getBannerImagesFromServer extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected String doInBackground(String... params) {


            try {

                JSONObject jObj = new JSONObject(params[0]);

                JSONObject user = jObj.getJSONObject("data");


                String msg = user.getString("msg");
                String responce = user.getString("response");

                if (msg.equals("OK") && responce.equals("200")) {

                    JSONArray jsonArray = user.getJSONArray("result");


                    setBannerImgListList(jsonArray);

                }

            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Sync Data Service" + e + "" + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
           /* new LastNameAsynTask(getContext(),savedUserName, savedUserPassword);*/
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //  Log.e("onPostExecute", "onPostExecute");

            //makeToast("Application Initialization Successful");

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void setBannerImgListList(JSONArray jsonArray) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonProsolveObject = jsonArray.getJSONObject(i);

            String banner_id = jsonProsolveObject.getString("banner_id");
           // Log.e("banner_id", "" + banner_id);

            String company_id = jsonProsolveObject.getString("company_id");
            String brand_name = jsonProsolveObject.getString("brand_name");
            String folder_name = jsonProsolveObject.getString("folder_name");
            String banner_image_name = jsonProsolveObject.getString("banner_image1");
            banner_image_name = banner_image_name.replace(".jpg", "");
            String type = jsonProsolveObject.getString("type");
            String banner_image_url = jsonProsolveObject.getString("banner_image_url");
            //  Log.e("banner_image_urlbefore", "" + banner_image_url);
            banner_image_url = banner_image_url.replace("://", "http://");
            banner_image_url = banner_image_url.replace(" ", "%20");
            //  Log.e("banner_image_urlafter", "" + banner_image_url);

            String speciality_name = jsonProsolveObject.getString("speciality_name");
            String product_image_url = jsonProsolveObject.getString("product_image_url");
            product_image_url = product_image_url.replace("://", "http://");
            product_image_url = product_image_url.replace(" ", "%20");
            String product_image_name = jsonProsolveObject.getString("product_image2");
            String product_imagenm = product_image_name.replace(".jpg", "");
            String banner_type = jsonProsolveObject.getString("banner_type_id");


            String generic_name = jsonProsolveObject.getString("generic_name");
            String manufactured_by = jsonProsolveObject.getString("manufactured_by");
            String marketed_by = jsonProsolveObject.getString("marketed_by");
            String group_name = jsonProsolveObject.getString("group_name");
            String link_to_page = jsonProsolveObject.getString("link_to_page");
          /*  if(link_to_page.startsWith("www")){
                link_to_page.replace("http://www", "www");
            }*/
            String call_me = jsonProsolveObject.getString("call_me");
            String meet_me = jsonProsolveObject.getString("meet_me");

            String priority = jsonProsolveObject.getString("priority");

            String status = jsonProsolveObject.getString("status");
            String status_name = jsonProsolveObject.getString("status_name");

            String start_time = jsonProsolveObject.getString("start_time");
            String end_time = jsonProsolveObject.getString("end_time");
            String clinical_trial_source = jsonProsolveObject.getString("clinical_trial_source");
            String clinical_trial_identifier = jsonProsolveObject.getString("clinical_trial_identifier");

            String clinical_trial_link = jsonProsolveObject.getString("clinical_trial_link");
            String clinical_sponsor = jsonProsolveObject.getString("clinical_sponsor");
            String drug_composition = jsonProsolveObject.getString("drug_composition");
            String drug_dosing_durability = jsonProsolveObject.getString("drug_dosing_durability");


            String added_by = jsonProsolveObject.getString("added_by");
            String added_on = jsonProsolveObject.getString("added_on");
            String modified_by = jsonProsolveObject.getString("modified_by");
            String modified_on = jsonProsolveObject.getString("modified_on");
            String is_disabled = jsonProsolveObject.getString("is_disabled");
            String disabled_by = jsonProsolveObject.getString("disabled_by");
            String disabled_on = jsonProsolveObject.getString("disabled_on");
            String is_deleted = jsonProsolveObject.getString("is_deleted");
            String deleted_by = jsonProsolveObject.getString("deleted_by");
            String deleted_on = jsonProsolveObject.getString("deleted_on");
            String img_download_status=null;

            if (checkifImageExists(banner_image_name)) {
                File file = getImage("/" + banner_image_name + ".png");
                String path = file.getAbsolutePath();
                if (path != null) {

                    // Log.e("imageExist","imageExist allready");
                    img_download_status  = "downloaded";
                }
            } else {
                // Log.e("imageExist","imageExist not");
                img_download_status  = "inprogress";
                downloadImage(banner_image_url, banner_image_name);
            }
            if (checkifImageExists(product_imagenm)) {
                File file = getImage("/" + product_imagenm + ".png");
                String path = file.getAbsolutePath();
                if (path != null) {
                    /*b = BitmapFactory.decodeFile(path);
                    imageView.setImageBitmap(b);*/
                    //  Log.e("imageExist","imageExist allready");
                    img_download_status  = "downloaded";
                }
            } else {
                // Log.e("imageExist","imageExist not");
                img_download_status  = "inprogress";
                downloadImage(product_image_url, product_imagenm);
            }

            //saveImageToSD(banner_image1);

            String download_intislizatio_time = appController.getDateTimenew();


            bannerClass.addBannerData(banner_id, company_id, brand_name, type, banner_image_url, speciality_name,
                    product_image_url, generic_name, manufactured_by, marketed_by, group_name, link_to_page, call_me, meet_me,
                    priority, status, start_time, end_time,
                    clinical_trial_source, clinical_trial_identifier, clinical_trial_link, clinical_sponsor, drug_composition, drug_dosing_durability, added_by, added_on, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on, banner_image_name, banner_type, product_imagenm, folder_name, status_name, img_download_status, download_intislizatio_time);
        }
    }

    private void downloadImage(final String banner_image_url, final String banner_image1) {
          /*--- check whether there is some Text entered ---*/
        if (banner_image_url.trim().length() > 0) {
            /*--- instantiate our downloader passing it required components ---*/
            runOnUiThread(new Runnable() {
                public void run() {
                    mDownloader = new ImageDownloader(banner_image_url
                            .trim(), banner_image1.trim(), pb, getApplicationContext(), bmp, new ImageDownloader.ImageLoaderListener() {
                        @Override
                        public void onImageDownloaded(Bitmap bmp) {
                            //  bmp = bmp;
         /*--- here we assign the value of bmp field in our Loader class
                   * to the bmp field of the current class ---*/
                        }
                    });
                    mDownloader.execute();
                    String img_download_status = "downloading";
                    String download_start_time = appController.getDateTimenew();
                    bannerClass.updateBannerImgDownloadStatus(banner_image1,banner_image_url,img_download_status,download_start_time);
                }


            });

        }
    }


    public static boolean checkifImageExists(String imagename) {
        Bitmap b = null;
        File file = getImage("/" + imagename + ".png");
        String path = file.getAbsolutePath();

        if (path != null)
            b = BitmapFactory.decodeFile(path);

        return !(b == null || b.equals(""));
    }

    public static File getImage(String imagename) {

        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File("sdcard/BannerImages/" + imagename);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return mediaImage;
    }

}

