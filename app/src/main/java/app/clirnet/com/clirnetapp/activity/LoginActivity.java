
package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
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
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.utility.MD5;
import app.clirnet.com.clirnetapp.utility.SyncDataService;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class LoginActivity extends Activity {

    private static final String PREFS_NAME = "savedCredit";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String LOGIN_TIME = "loginTime";
    private static final String LOGIN_COUNT = "firstTimeLogin";

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private String name;
    private MD5 md5;
    private ConnectionDetector connectionDetector;

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

    private Dialog dialog;
    private static String BASE_URL = "http://192.168.1.7/server_side_code/register.php";
    private SQLiteHandler sInstance;
    private String docId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        TextView btnLinkToForgetScreen = (TextView) findViewById(R.id.btnLinkToForgetScreen);
        //  TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        // TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);

        // DatabaseClass databaseClass = DatabaseClass.getInstance(getApplicationContext());
        DatabaseClass databaseClass = new DatabaseClass(getApplicationContext());
        bannerClass = new BannerClass(getApplicationContext());
        LastnameDatabaseClass lastnameDatabaseClass = new LastnameDatabaseClass(getApplicationContext());
        appController = new AppController();

      /*  privacyPolicy.setOnClickListener(new View.OnClickListener() {
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
*/
        if (md5 == null) {
            md5 = new MD5();
        }

        //this will set value to run asynctask only once per login session

        new CallAsynOnce().setValue("1");//this set value which helps to call asyntask only once while app is running.


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        connectionDetector = new ConnectionDetector(getApplicationContext());

        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            sInstance = SQLiteHandler.getInstance(getApplicationContext());
            Boolean value = getFirstTimeLoginStatus();
            if (value) {
                doctor_membership_number = sqlController.getDoctorMembershipIdNew();
                docId = sqlController.getDoctorId();
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTimenew() + " " + "/ " + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        try {

            databaseClass.createDataBase();
            bannerClass.createDataBase();
            phoneNumber = sqlController.getPhoneNumber();

        } catch (Exception ioe) {
            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page" + ioe + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            throw new Error("Unable to create database");

        }

        try {

            databaseClass.openDataBase();
            bannerClass.openDataBase();

            /*This AsyncTask will Insert data from Asset folder file to data 23-03-2016*/
            new InsertDataLocally().execute();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } finally {
            if (databaseClass != null) {
                databaseClass.close();
            }
        }


        try {

            lastnameDatabaseClass.createDataBase();

        } catch (IOException e) {

            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());


            throw new Error("Unable to create database");
        }

        try {

            lastnameDatabaseClass.openDataBase();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

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
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.bg_login));
                    name = inputEmail.getText().toString().trim();
                    strPassword = inputPassword.getText().toString().trim();

                    String time = appController.getDateTimenew();
                    AppController.getInstance().trackEvent("Login", "Login Pressed", "Track event");

                    //This code used for Remember Me(ie. save login id and password for future ref.)
                    rememberMe(name, strPassword, time); //save username only

                    //rememberMeCheckbox();//Removed remeber me check box for safety concern 04-11-16
                    //to authenticate user credentials
                    LoginAuthentication();


                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        /*We are updating visit date format*/
        updateVisitDateFormat();

        /*  Updateing banner stats flag to 0 build no 1.3+  */
        String bannerUpdateFlag = getupdateBannerClickVisitFlag0();
        if (bannerUpdateFlag == null) {
            updateBannerTableDataFlag();
        }

        // Link to Register Screen
        btnLinkToForgetScreen.setOnClickListener(new View.OnClickListener()

        {

            public void onClick(View view) {

                showChangePassDialog();

            }
        });

       /* if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.e("value", "  " + getIntent().getExtras().getString(key));
                Intent intent = new Intent(this, NavigationActivity.class);
                intent.putExtra("value", value);
                startActivity(intent);
                finish();

            }
        }*/

    }

    private void updateBannerTableDataFlag() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                sInstance.FlagupdateBannerClicked("0");
                sInstance.FlagupdateBannerDisplay("0");
                setupdateBannerClickVisitFlag0("true");
            }
        });
    }

    private void updateVisitDateFormat() {

        String visitFlag = getUpdateVisitDateFlag();

        if (visitFlag == null) {
            try {
                ArrayList<LoginModel> visitDateCount = sqlController.getPatientVisitDate();
                if (visitDateCount.size() > 0) {
                    for (int i = 0; i < visitDateCount.size(); i++) {
                        String visit_date = visitDateCount.get(i).getVisit_date();
                        String visit_id = visitDateCount.get(i).getVisit_id();
                        String fod = visitDateCount.get(i).getAct_followupdate();
                        String added_on = visitDateCount.get(i).getAdded_on();

                        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                        try {
                            String visit_date_converted = myFormat.format(fromUser.parse(visit_date));
                            String fod_converted = null;
                            String added_on_converted = null;
                            if (fod != null && !fod.equals("")) {
                                fod_converted = myFormat.format(fromUser.parse(fod));
                            }
                            if (added_on != null && !added_on.equals("")) {
                                added_on_converted = myFormat.format(fromUser.parse(added_on));
                            }
                            sqlController.updateVisitDate(visit_date_converted, visit_id, fod_converted, added_on_converted);

                        } catch (ParseException e) {
                            e.printStackTrace();
                            appController.appendLog(appController.getDateTime() + " " + "/ " + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }
                    setupdateVisitDateFlag("true");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //it will hide the keyboard on button pressed
    private void hideKeyBoard() {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        try {

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Do login authentication Operations 2-11-2016
    private void LoginAuthentication() {

        md5EncyptedDataPassword = MD5.getMD5(strPassword);

        // Check for empty data in the form
        if (!name.isEmpty() && !strPassword.isEmpty()) {

            //Check if internet in on or not and if on authenticate user via entered Credentials
            // login user
            boolean isInternetPresent = connectionDetector.isConnectingToInternet();
            if (isInternetPresent) {

                String start_time = appController.getDateTimenew();
                new DoctorDeatilsAsynTask(LoginActivity.this, name, md5EncyptedDataPassword, doctor_membership_number, docId, start_time);
                new LoginAsyncTask(LoginActivity.this, name, md5EncyptedDataPassword, phoneNumber, doctor_membership_number, docId, start_time);
                //startService();
                savedLoginCounter("true");//to save shrd pref to update login counter
                //registerToServer(name, "ashish.umredkar@clirnet.com"); //for fcm notification

                //update last sync time if sync from server
                // update last login time
                //lastSyncTime(start_time);
                //lastSyncTime("05-02-2017 02:12:25");
                // hideDialog();

            } else {

                boolean isLogin;
                try {
                    //check last sync time to check if last sync from server is more than 72 hours or not
                    int lasttimeSync = getLastSyncTime();

                    if (lasttimeSync > 72) {
                        showCreatePatientAlertDialog();
                        Toast.makeText(getApplicationContext(), "User not logged in for 3 days. Security credentials expired.", Toast.LENGTH_LONG).show();
                    } else {

                        isLogin = sqlController.validateUser(name, md5EncyptedDataPassword, phoneNumber);

                        if (isLogin) {

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            goToNavigation();

                            startService();

                        } else {
                            Toast.makeText(getApplicationContext(), "Username/Password Mismatch", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (ClirNetAppException e) {
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Login Page" + e);
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

        return AppController.hoursAgo(lastSyncTime);
    }

    public void startService() {
        String apiKey = getResources().getString(R.string.apikey);
        Intent serviceIntent = new Intent(getApplicationContext(), SyncDataService.class);
        serviceIntent.putExtra("name", name);
        serviceIntent.putExtra("password", md5EncyptedDataPassword);
        serviceIntent.putExtra("apikey", apiKey);
        startService(serviceIntent);
    }

    private void showChangePassDialog() {

        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.setTitle("Change Password");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        // set the custom dialog components - text, image and button
        TextView btnSubmitPass = (TextView) dialog.findViewById(R.id.submit);

        oldPassword = (EditText) dialog.findViewById(R.id.oldPassword);
        newPassword = (EditText) dialog.findViewById(R.id.password);
        confirmPassword = (EditText) dialog.findViewById(R.id.confirmPassword);


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
                    Toast.makeText(getApplicationContext(), "Old and new Password do not match ", Toast.LENGTH_LONG).show();
                    confirmPassword.setError("Old and new Password do not match  !");
                    return;
                }

                String md5oldPassword = MD5.getMD5(oldPass);
                String md5newPassword = MD5.getMD5(newPass);
                String start_time = appController.getDateTimenew();
                new UpdatePassworsAsynTask(LoginActivity.this, username, doctor_membership_number, docId, md5oldPassword, md5newPassword, start_time);
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
        //overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
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

    @Override
    public void onResume() {
        super.onResume();
        // overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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

        if (username != null || password != null) {

            inputEmail.setText(username);
            // inputPassword.setText(password);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Close the all database connection opened here 31/10/2016 By. Ashish
        if (sqlController != null) {
            sqlController = null;
        }
        if (connectionDetector != null) {
            connectionDetector = null;
        }
        if (appController != null) {
            appController = null;
        }
        if (bannerClass != null) {
            bannerClass = null;
        }
        if (pDialog != null) {
            pDialog = null;
        }
        md5 = null;
        strPassword = null;
        md5EncyptedDataPassword = null;
        inputEmail = null;
        inputPassword = null;
        name = null;

        dialog = null;
        btnLogin = null;
        phoneNumber = null;
        oldPassword = null;
        newPassword = null;
        confirmPassword = null;
        username = null;
        doctor_membership_number = null;
        System.gc();
    }

    private void showCreatePatientAlertDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.no_inetrnet_login_dialog);

        dialog.setTitle("Your security credentials have expired. Please login with an active internet connection to refresh.");
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

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    private boolean getFirstTimeLoginStatus() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String firstTimeLogin = pref.getString(LOGIN_COUNT
                , null);
        if (firstTimeLogin == null) {
            return false;
        } else if (firstTimeLogin.equals("false")) {

            return false;
        }
        return true;
    }

    private void savedLoginCounter(String answer) {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(LOGIN_COUNT, "true")
                .apply();
    }

    private void setupdateVisitDateFlag(String answer) {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString("flag1", answer)
                .apply();
    }

    //this method will set username and password to edit text if remember me chkbox is checked previously
    private String getUpdateVisitDateFlag() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return pref.getString("flag1", null);
    }

    private void setupdateBannerClickVisitFlag0(String answer) {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString("bannerflag", answer)
                .apply();
    }

    //this method will set username and password to edit text if remember me chkbox is checked previously
    private String getupdateBannerClickVisitFlag0() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return pref.getString("bannerflag", null);
    }

    private void registerToServer(final String name, final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                BASE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Register Successful!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                        i.putExtra("name", name);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "this email is already in use", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Database Problem!", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("PUSHNOTIREGISTER", "Register Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                String fcm_id = FirebaseInstanceId.getInstance().getToken();

                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("fcm_id", fcm_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class InsertDataLocally extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                String Diagnosiscount = sInstance.getTableCount("Diagnosis");
                String Specialitycount = sInstance.getTableCount("Speciality");
                String symptomscount = sInstance.getTableCount("Symptoms");
                String last_name_masterCount = sInstance.getTableCount("last_name_master");
                // Log.e("symptomscount"," "+symptomscount + " "+Diagnosiscount + " "+Specialitycount +" "+last_name_masterCount);

                if (symptomscount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Symptoms.sql"));

                }
                if (Diagnosiscount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Diagnosis.sql"));

                }
                if (Specialitycount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Speciality.sql"));

                }
                if (last_name_masterCount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("last_name_master.sql"));

                }
                //Toast.makeText(this, "Rows loaded from file= " + insertCount, Toast.LENGTH_SHORT).show();
            } catch (IOException | ClirNetAppException e) {
                appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
            return null;
        }

        protected void onPostExecute(String e) {
            //  Toast.makeText(getApplicationContext(),"Rows loaded from file", Toast.LENGTH_SHORT).show();
        }

    }
}
