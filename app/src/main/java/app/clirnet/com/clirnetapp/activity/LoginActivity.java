
package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.Utility.ImageDownloader;
import app.clirnet.com.clirnetapp.Utility.MD5;
import app.clirnet.com.clirnetapp.Utility.SyncDataService;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.DoctorDeatilsAsynTask;
import app.clirnet.com.clirnetapp.app.LoginAsyncTask;
import app.clirnet.com.clirnetapp.app.UpdatePassworsAsynTask;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;

public class LoginActivity extends Activity {
    private static final String TAG = "Login";
    private static final String PREFS_NAME = "savedCredit";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String LOGIN_TIME = "loginTime";
    private static final String FISRT_TIME_LOGIN = "firstTimeLogin";
    private static final String LOGIN_COUNT = "firstTimeLogin";

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private String name;
    private MD5 md5;
    private ConnectionDetector connectionDetector;
    private SQLiteHandler dbController;
    private String strPassword;
    private String md5EncyptedDataPassword;

    private SQLController sqlController;
    private AppController appController;
    private Button btnLogin;
    private String phoneNumber;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private String username;
    private String doctor_membership_number;

    private BannerClass bannerClass;

    private ImageDownloader mDownloader;
    private static Bitmap bmp;
    private FileOutputStream fos;
    private ProgressBar pb;
    private SharedPreferences.Editor editor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        Button btnLinkToForgetScreen = (Button) findViewById(R.id.btnLinkToForgetScreen);
        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);

        DatabaseClass databaseClass = new DatabaseClass(getApplicationContext());
        bannerClass = new BannerClass(getApplicationContext());
        LastnameDatabaseClass lastnameDatabaseClass = new LastnameDatabaseClass(getApplicationContext());
        appController = new AppController();

        convertDate();


        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);

            }
        });

        //redirect to TermsCondition Page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsCondition.class);
                startActivity(intent);

            }
        });

        if (md5 == null) {
            md5 = new MD5();
        }

        //this will set value to run asynctask only once per login session

        new CallAsynOnce().setValue("1");//this set value which helps to call asyntask only once while app is running.

//        String opinion = selectRadio.getText().toString();
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        // showDialog12();

        connectionDetector = new ConnectionDetector(getApplicationContext());
        //open database controller class for further operations on database
        // Cursor cursor = null;

        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            dbController = new SQLiteHandler(getApplicationContext());
            Boolean value=getFirstTimeLoginStatus();
            if(value) {
                doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            }else{
                Log.e("FisrtTimeLogin","FisrtTimeLogin to application");
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home" + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        try {

            databaseClass.createDataBase();
            bannerClass.createDataBase();
            phoneNumber = sqlController.getPhoneNumber();

        } catch (Exception ioe) {
            appController.appendLog(appController.getDateTime() + "" + "/" + "Home" + ioe+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());

            throw new Error("Unable to create database");

        }

        try {

            databaseClass.openDataBase();
            bannerClass.openDataBase();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Home" + e);
        } finally {
            if (databaseClass != null) {
                databaseClass.close();
            }
        }


        try {

            lastnameDatabaseClass.createDataBase();

        } catch (IOException ioe) {

            appController.appendLog(appController.getDateTime() + "" + "/" + "Home" + ioe);

            throw new Error("Unable to create database");
        }

        try {

            lastnameDatabaseClass.openDataBase();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Home" + e);
        } finally {
            if (lastnameDatabaseClass != null) {
                lastnameDatabaseClass.close();
            }
        }
        btnLogin.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    hideKeyBoard();
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.white));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {


                                        public void onClick(View view) {

                                            name = inputEmail.getText().toString().trim();
                                            strPassword = inputPassword.getText().toString().trim();

                                            String time = appController.getDateTimenew();
                                            // String time="2-1-2017 05:22:21";
                                            // Log.e("current Time",""+time);
                                            //This code used for Remember Me(ie. save login id and password for future ref.)
                                            rememberMe(name, strPassword, time); //save username only

                                            //rememberMeCheckbox();//Removed remeber me check box for safety concern 04-11-16
                                            //to authenticate user credentials
                                            LoginAuthentication();

                                        }

                                    }
        );

        // Link to Register Screen
        btnLinkToForgetScreen.setOnClickListener(new View.OnClickListener()

        {

            public void onClick(View view) {

                showChangePassDialog();

            }
        });


    }

    //it will hide the keyboard on button pressed
    private void hideKeyBoard() {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        try {

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void convertDate() {

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        String searchdate = "2016-12-02";
        String reformattedStr = "";
        try {

            reformattedStr = myFormat.format(fromUser.parse(searchdate));
            Log.e("reformattedStrqq", "" + reformattedStr);

        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e);
        }

    }

    //Do login authentication Operations 2-11-2016
    private void LoginAuthentication() {

        md5EncyptedDataPassword = MD5.getMD5(strPassword);
        String company_id = null;

        // Check for empty data in the form
        if (!name.isEmpty() && !strPassword.isEmpty()) {

            //Check if internet in on or not and if on authenticate user via entered Credentials
            // login user
            boolean isInternetPresent = connectionDetector.isConnectingToInternet();
            if (isInternetPresent) {
                //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                //  checkLogin(name, md5EncyptedDataPassword);
                try {
                    company_id=sqlController.getCompany_id();
                    Log.e("current Time", "" + company_id);
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e);
                }

                new DoctorDeatilsAsynTask(LoginActivity.this, name, md5EncyptedDataPassword);

                String apiKey = getResources().getString(R.string.apikey);

                new LoginAsyncTask(LoginActivity.this, name, md5EncyptedDataPassword, phoneNumber);
                startService();
                savedLoginCounter("true");//to save shrd pref to update login counter

               // getBannersData(username, strPassword, apiKey, doctor_membership_number, company_id);


                //update last sync time if sync from server
                String time = appController.getDateTimenew();
                Log.e("current Time", "" + time);
                //update last login time
                lastSyncTime(time);
                // hideDialog();

            } else {

                boolean isLogin;
                try {
                    //check last sync time to check if last sync from server is more than 72 hours or not
                    int lasttimeSync = getLastSyncTime();

                    if (lasttimeSync > 72) {
                        showCreatePatientAlertDialog();
                        Toast.makeText(getApplicationContext(), "Please login via internet and sync data to server", Toast.LENGTH_LONG).show();
                    } else {

                        isLogin = sqlController.validateUser(name, md5EncyptedDataPassword, phoneNumber);

                        if (isLogin) {

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            goToNavigation();

                            startService();

                        } else {
                            Toast.makeText(getApplicationContext(), "Username/Password Mismatch", Toast.LENGTH_LONG).show();

                            // Toast.makeText(getApplicationContext(), " You are not connected to Internet!! ", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (ClirNetAppException e) {
                    //e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
                } finally {
                    if (sqlController != null) {
                        sqlController.close();
                    }
                }
            }


        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Username/Password Incomplete", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private int getLastSyncTime() {

        SharedPreferences pref1 = getSharedPreferences("SyncFlag", MODE_PRIVATE);
        String lastSyncTime = pref1.getString("lastSyncTime", null);
        int hrslastSync = AppController.hoursAgo(lastSyncTime);
        Log.e("loginTime12", "" + lastSyncTime + "  " + hrslastSync);

        return hrslastSync;
    }

    private void startService() {
        String apiKey = getResources().getString(R.string.apikey);
        Intent serviceIntent = new Intent(getApplicationContext(), SyncDataService.class);
        serviceIntent.putExtra("name", name);
        serviceIntent.putExtra("password", md5EncyptedDataPassword);
        serviceIntent.putExtra("apikey", apiKey);
        startService(serviceIntent);
    }


    private void showChangePassDialog() {

        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.setTitle("Change Password");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        // set the custom dialog components - text, image and button
        TextView btnSubmitPass = (TextView) dialog.findViewById(R.id.submit);

        oldPassword = (EditText) dialog.findViewById(R.id.oldPassword);
        newPassword = (EditText) dialog.findViewById(R.id.password);
        confirmPassword = (EditText) dialog.findViewById(R.id.confirmPassword);
        //  TextView gotosetting = (TextView) dialog.findViewById(R.id.gotosetting);
        //text.setText("Android custom dialog example!");


        //  image.setImageResource(R.drawable.ic_launcher);
        btnSubmitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPass = oldPassword.getText().toString().trim();
                String newPass = newPassword.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();


                if (TextUtils.isEmpty(oldPass)) {
                    oldPassword.setError("Please enter Password !");
                    return;
                }
                if (TextUtils.isEmpty(newPass)) {
                    newPassword.setError("Please enter Password !");
                    return;
                }
                if (TextUtils.isEmpty(confirmPass)) {
                    confirmPassword.setError("Please enter Password !");
                    return;
                }
                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(getApplicationContext(), "Old and new Password did not match ", Toast.LENGTH_LONG).show();
                    confirmPassword.setError("Old and new Password did not match  !");
                    return;
                }

                String md5oldPassword = MD5.getMD5(oldPass);
                String md5newPassword = MD5.getMD5(newPass);
                //  Log.e("oldand new",""+md5oldPassword +" new "+md5newPassword);
                new UpdatePassworsAsynTask(LoginActivity.this, username, doctor_membership_number, md5oldPassword, md5newPassword);
                dialog.dismiss();
            }
        });

        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    private void goToNavigation() {

        Intent intent = new Intent(getApplicationContext(),
                NavigationActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //save username and password in SharedPreferences
    private void rememberMe(String user, String password, String dateTime) {

        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, user)
                .putString(PREF_PASSWORD, password)
                .putString(LOGIN_TIME, dateTime)
                .apply();

    }


    public void onStart() {
        super.onStart();
        //read username and password from SharedPreferences
        getUser();

    }

    //this method will set username and password to edit text if remember me chkbox is checked previously
    private void getUser() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);
        // Log.e("password", "" + username + "" + password);
        SharedPreferences pref1 = getSharedPreferences("SyncFlag", MODE_PRIVATE);
        String lastSync = pref1.getString("lastSyncTime", null);
        Log.e("lastSync", "" + lastSync);

        if (username != null || password != null) {
            //directly show logout form
            //  showLogout(username);
            inputEmail.setText(username);
            // inputPassword.setText(password);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "The onDestroy() event");
        // session.setLogin(false);
        //Close the all database connection opened here 31/10/2008 By. Ashish
        if (sqlController != null) {
            sqlController = null;
        }
        if (dbController != null) {
            dbController.close();
            dbController = null;
        }
        if (connectionDetector != null) {
            connectionDetector = null;
        }
        if (appController != null) {
            appController = null;
        }
        //  pDialog=null;
        md5 = null;
        md5EncyptedDataPassword = null;
        inputEmail = null;
        inputPassword = null;
        System.gc();
    }

    /*private static String getScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "{" + width + "," + height + "}";
    }*/
    //store last sync time in prefrence
    public void lastSyncTime(String lastSyncTime) {

        getSharedPreferences("SyncFlag", MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }

    private void showCreatePatientAlertDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.no_inetrnet_login_dialog);


        dialog.setTitle("Please Login Via Internet");
        //  dialog.setCancelable(false);


        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                startActivity(intent);*/

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    private void getBannersData(final String savedUserName, final String savedUserPassword, final String apiKey, final String docMemId, final String companyid) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        pDialog.setMessage("Initializing Application. Please Wait...");
        pDialog.setCancelable(false);
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BANNER_API, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Sync Response: " + response);
                Log.e("getBannersData", "" + response);

                // new getPatientRecordsFromServer().execute(response);


                new getBannerImagesFromServer().execute(response);

                //  Log.e("jsonArray", "" + jsonArray);
                // JSONArray patientHistoryList = user.getJSONArray("patient_visit_details");//for live api
                // JSONArray patientHistoryList = user.getJSONArray("patinet_visit_details"); //for local addrres
                // Log.e("jsonArray", "" + patientHistoryList);
                // setPatientPersonalList(jsonArray);
                //setPatientHistoryList(patientHistoryList);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: Failed To Initalize Data" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Failed To Initalize Data" + error.getMessage(), Toast.LENGTH_LONG).show();
                // hideDialog();

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

                // Check for error node in json
                // user successfully logged in
                // Now store the user in SQLite

                JSONObject user = jObj.getJSONObject("data");
                Log.e("123456", "" + user);


                String msg = user.getString("msg");
                String responce = user.getString("response");

                if (msg.equals("OK") && responce.equals("200")) {

                    JSONArray jsonArray = user.getJSONArray("result");


                    setBannerImgListList(jsonArray);

                }

            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
                //Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
           /* new LastNameAsynTask(getContext(),savedUserName, savedUserPassword);*/
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("onPostExecute", "onPostExecute");
              pd.dismiss();
            // hideDialog();
            //makeToast("Application Initialization Successful");

        }

        @Override
        protected void onPreExecute() {
            Log.e("onPreExecute", "onPreExecute");
             pd = new ProgressDialog(LoginActivity.this);
              pd.setMessage("Initializing Application. Please Wait...2");
              pd.show();
            // showDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void setBannerImgListList(JSONArray jsonArray) throws JSONException {


        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonProsolveObject = jsonArray.getJSONObject(i);

            String banner_id = jsonProsolveObject.getString("banner_id");
            Log.e("banner_id",""+banner_id);


            String company_id = jsonProsolveObject.getString("company_id");
            String brand_name = jsonProsolveObject.getString("brand_name");
            String banner_image_name = jsonProsolveObject.getString("banner_image1");
            banner_image_name = banner_image_name.replace(".jpg", "");
            String type = jsonProsolveObject.getString("type");
            String banner_image_url = jsonProsolveObject.getString("banner_image_url");
            Log.e("banner_image_urlbefore", "" + banner_image_url);
            banner_image_url = banner_image_url.replace("://", "http://");
            banner_image_url = banner_image_url.replace(" ", "%20");
            Log.e("banner_image_urlafter", "" + banner_image_url);

            String speciality_name = jsonProsolveObject.getString("speciality_name");
            String product_image_url = jsonProsolveObject.getString("product_image_url");
            product_image_url = product_image_url.replace("://", "http://");
            product_image_url = product_image_url.replace(" ", "%20");
            String product_image_name = jsonProsolveObject.getString("product_image2");
            String product_imagenm = product_image_name.replace(".jpg", "");
            String banner_type=jsonProsolveObject.getString("banner_type_id");


            String generic_name = jsonProsolveObject.getString("generic_name");
            String manufactured_by = jsonProsolveObject.getString("manufactured_by");
            String marketed_by = jsonProsolveObject.getString("marketed_by");
            String group_name = jsonProsolveObject.getString("group_name");
            String link_to_page = jsonProsolveObject.getString("link_to_page");
            String call_me = jsonProsolveObject.getString("call_me");
            String meet_me = jsonProsolveObject.getString("meet_me");

            String priority = jsonProsolveObject.getString("priority");

            String status = jsonProsolveObject.getString("status");

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

            if(checkifImageExists(banner_id))
            {
                File file = getImage("/"+banner_id+".png");
                String path = file.getAbsolutePath();
                if (path != null){
                    /*b = BitmapFactory.decodeFile(path);
                    imageView.setImageBitmap(b);*/
                    Log.e("imageExist","imageExist allready");
                }
            } else {
                Log.e("imageExist","imageExist not");
                downloadImage(banner_image_url, banner_id);
            }
            if(checkifImageExists(product_imagenm))
            {
                File file = getImage("/"+product_imagenm+".png");
                String path = file.getAbsolutePath();
                if (path != null){
                    /*b = BitmapFactory.decodeFile(path);
                    imageView.setImageBitmap(b);*/
                    Log.e("imageExist","imageExist allready");
                }
            } else {
                Log.e("imageExist","imageExist not");
                downloadImage(product_image_url, product_imagenm);
            }

            //saveImageToSD(banner_image1);

            bannerClass.addBannerData(banner_id, company_id, brand_name, type, banner_image_url, speciality_name,
                    product_image_url, generic_name, manufactured_by, marketed_by, group_name, link_to_page, call_me, meet_me,
                    priority, status, start_time, end_time,
                    clinical_trial_source, clinical_trial_identifier, clinical_trial_link, clinical_sponsor, drug_composition, drug_dosing_durability, added_by, added_on, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on,banner_image_name,banner_type,product_imagenm);

        }
    }

    private void downloadImage(final String banner_image_url, final String banner_image1) {
          /*--- check whether there is some Text entered ---*/
        if (banner_image_url.trim().length() > 0) {
            /*--- instantiate our downloader passing it required components ---*/
            LoginActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    mDownloader = new ImageDownloader(banner_image_url
                            .trim(), banner_image1.trim(), pb, LoginActivity.this, bmp, new ImageDownloader.ImageLoaderListener() {
                        @Override
                        public void onImageDownloaded(Bitmap bmp) {
                            LoginActivity.bmp = bmp;
         /*--- here we assign the value of bmp field in our Loader class
                   * to the bmp field of the current class ---*/
                        }
                    });
                    mDownloader.execute();
                }


            });


            /*--- we need to call execute() since nothing will happen otherwise ---*/

        }
    }
    public static boolean checkifImageExists(String imagename)
    {
        Bitmap b = null ;
        File file = getImage("/"+imagename+".png");
        String path = file.getAbsolutePath();

        if (path != null)
            b = BitmapFactory.decodeFile(path);

        if(b == null ||  b.equals(""))
        {
            return false ;
        }
        return true ;
    }
    public static File getImage(String imagename) {

        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File("sdcard/BannerImages/"+imagename);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaImage;
    }
    private void getTermsAndCondition() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String firstTimeLogin = pref.getString(FISRT_TIME_LOGIN
                , null);

        Log.e("firstTimeLogin", ""+ firstTimeLogin);
        if(firstTimeLogin == null){
            showChangePassDialog();
        }else if(firstTimeLogin.equals("false")){
            showChangePassDialog();
        }else{
            //do nothing
        }
    }
    public boolean getFirstTimeLoginStatus(){
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String firstTimeLogin = pref.getString(LOGIN_COUNT
                , null);
        Log.e("firstTimeLogin", ""+ firstTimeLogin);
        if(firstTimeLogin == null){
           return false;
        }else if(firstTimeLogin.equals("false")){

            return false;
        }
        return  true;
    }

    private void savedLoginCounter(String answer) {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(LOGIN_COUNT, answer)
                .apply();

    }
}
