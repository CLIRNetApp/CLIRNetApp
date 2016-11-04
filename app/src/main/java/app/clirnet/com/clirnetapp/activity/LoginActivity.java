
package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.Utility.MD5;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;
import app.clirnet.com.clirnetapp.models.LoginModel;

public class LoginActivity extends Activity {
    private static final String TAG = "Login";
    private static final String PREFS_NAME = "savedCredit";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;


    private int login_user; //to check which user is login to app
    private EditText confirmPassord, password;
    private EditText email_forget;
    private String name;
    private MD5 md5;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent;
    private SQLiteHandler dbController;
    private String strPassword;
    private String md5EncyptedDataPassword;

    private SQLController sqlController;
    private final ArrayList<LoginModel> mLoginList = new ArrayList<>();
    private String savedUserName;
    private String savedUserPassword;
    private int count;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        TextView btnLogin = (Button) findViewById(R.id.btnLogin);


        Button btnLinkToForgetScreen = (Button) findViewById(R.id.btnLinkToForgetScreen);
        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);

        DatabaseClass databaseClass = new DatabaseClass(LoginActivity.this);
        LastnameDatabaseClass lastnameDatabaseClass = new LastnameDatabaseClass(LoginActivity.this);

        //redirect to PrivacyPolicy Page

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PrivacyPolicy.class);
                startActivity(intent);

            }
        });

        //redirect to TermsCondition Page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TermsCondition.class);
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

        connectionDetector = new ConnectionDetector(getApplicationContext());


        //open database controller class for further operations on database
        Cursor cursor = null;
        try {

            sqlController = new SQLController(LoginActivity.this);
            sqlController.open();
            dbController = new SQLiteHandler(LoginActivity.this);

            cursor = sqlController.getUserLoginRecords();
            // mLoginList = new ArrayList<String>();

            while (cursor.moveToNext()) {

                LoginModel user = new LoginModel(cursor.getString(0), cursor.getString(1));

                savedUserName = cursor.getString(0);
                savedUserPassword = cursor.getString(1);
                Log.e("savedpwd", "" + savedUserName + "" + savedUserPassword);

                mLoginList.add(user); //add the item
            }
            count = mLoginList.size();
            Log.e("size", "" + count);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            //Close db here
            if(sqlController != null){
                sqlController.close();
            }
        }
        try {

            databaseClass.createDataBase();

        } catch (Exception ioe) {

            throw new Error("Unable to create database");

        }

        try {

            databaseClass.openDataBase();


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            lastnameDatabaseClass.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            lastnameDatabaseClass.openDataBase();


        } catch (Exception e) {
            e.printStackTrace();
        }


        //this will check and get user credentials for offline authentication if no internet is present


        //to check which user is login to app
          // Session manager
      //  SessionManager session = new SessionManager(getApplicationContext());


        /*if (session.isLoggedIn() & login_user == 0) {
            intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);

        } else {

            intent = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(intent);

        }*/


        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {


                                        public void onClick(View view) {
                                            name = inputEmail.getText().toString().trim();
                                            strPassword = inputPassword.getText().toString().trim();
                                            //This code used for Remember Me(ie. save login id and password for future ref.)

                                            rememberMeCheckbox();
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
                                                 }

        );

    }

    //Do login authentication Operations 2-11-2016
    private void LoginAuthentication() {

        md5EncyptedDataPassword = MD5.getMD5(strPassword);

        // Check for empty data in the form
        if (!name.isEmpty() && !strPassword.isEmpty()) {

            //Check if internet in on or not and if on authenticate user via entered Credentials
            // login user
            isInternetPresent = connectionDetector.isConnectingToInternet();
            if (isInternetPresent) {
                //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                checkLogin(name, md5EncyptedDataPassword);
                getDoctorDetails(name, md5EncyptedDataPassword);

            } else if (count > 0) {
                if (name.equals(savedUserName) && md5EncyptedDataPassword.equals(savedUserPassword)) {
                    //Redirect to Navigation activity
                    goToNavigation();
                } else {
                    Toast.makeText(LoginActivity.this, " No Database found for the user " + name, Toast.LENGTH_LONG).show();
                }
                Toast.makeText(LoginActivity.this, " You are not connected to Internet ", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(LoginActivity.this, " You are not connected to Internet ", Toast.LENGTH_LONG).show();
            }


        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void rememberMeCheckbox() {
        CheckBox ch = (CheckBox) findViewById(R.id.ch_rememberme);
        try {
            if (ch.isChecked()) {
                rememberMe(name, strPassword); //save username and password
            } else {
                rememberMe(name, ""); //save username and password
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void showChangePassDialog() {

        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.setTitle("Change Password...");

        // set the custom dialog components - text, image and button


        TextView btnSubmitPass = (TextView) dialog.findViewById(R.id.submit);


        email_forget = (EditText) dialog.findViewById(R.id.email);
        password = (EditText) dialog.findViewById(R.id.password);
        //  TextView gotosetting = (TextView) dialog.findViewById(R.id.gotosetting);
        //text.setText("Android custom dialog example!");

        //  image.setImageResource(R.drawable.ic_launcher);
        btnSubmitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = password.getText().toString();
                String email_forgot = email_forget.getText().toString();

                if (TextUtils.isEmpty(pass)) {
                    password.setError("Please enter Username !");
                    return;
                }

                if (TextUtils.isEmpty(email_forgot)) {
                    confirmPassord.setError("Please enter Conform Password !");
                }
                //call asynchronous task for forget password

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


    private void showAlert() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.custom_popup);
        dialog.setTitle("Change Password...");

        // set the custom dialog components - text, image and button

        TextView btnSubmit = (TextView) dialog.findViewById(R.id.submit);


        password = (EditText) dialog.findViewById(R.id.password);
        confirmPassord = (EditText) dialog.findViewById(R.id.confirmpassword);

        //  image.setImageResource(R.drawable.ic_launcher);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = password.getText().toString();
                String conPass = confirmPassord.getText().toString();

                if (TextUtils.isEmpty(pass)) {
                    password.setError("Please enter Username !");
                    return;
                }

                if (TextUtils.isEmpty(conPass)) {
                    confirmPassord.setError("Please enter Conform Password !");
                    return;
                }
                if (!pass.equals(conPass)) {
                    confirmPassord.setError("Password doesn't match ! Try again");
                }

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

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);

                    // Check for error node in json

                        // user successfully logged in
                        // Now store the user in SQLite
                        dbController.addLoginRecord(name, md5EncyptedDataPassword);
                        //  inputLoginData.add(new LoginModel(name, md5EncyptedDataPassword));
                        // Create login session
                         JSONObject user = jObj.getJSONObject("data");

                        String result = user.getString("result");

                        if (result.equals("true")) {
                            //Redirect to Navigation activity
                            goToNavigation();

                        } else {
                            Toast.makeText(LoginActivity.this, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show();

                        }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                /*    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();*/
                }
                finally {
                    if(dbController != null){
                        dbController.close();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();


                params.put("username", email);
                params.put("password", password);
                params.put("apikey", getResources().getString(R.string.apikey));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void goToNavigation() {

        Intent  intent = new Intent(LoginActivity.this,
                NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //This will get doctors detailed Information
    private void getDoctorDetails(final String name, final String password) {

        String tag_string_req = "req_login";


        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_DOCTORINFO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);

                    // Check for error node in json

                      JSONObject user = jObj.getJSONObject("data");

                        String msg = user.getString("msg");
                       String responce=user.getString("response");

                        JSONObject docPerInfo = user.getJSONObject("result");

                        if (msg.equals("OK") && responce.equals("200")) {

                            String id = docPerInfo.getString("id");
                            String doctor_login_id = docPerInfo.getString("doctor_login_id");
                            String membership_id = docPerInfo.getString("membership_id");
                            String first_name = docPerInfo.getString("first_name");
                            String middle_name = docPerInfo.getString("middle_name");
                            String last_name = docPerInfo.getString("last_name");
                            String email_id = docPerInfo.getString("email_id");
                            String mobNo = docPerInfo.getString("mobile_no_prim");


                            dbController.addDoctorPerInfo(id, doctor_login_id, membership_id, first_name, middle_name, last_name, email_id, mobNo);
                            // docPerInfoData.add(new LoginModel(id, doctor_login_id,membership_id,first_name,middle_name,last_name,email_id));

                        } else {
                            if(responce.equals("404")){
                                Toast.makeText(getApplicationContext(),"Username Password Invalid OR Not Found" ,Toast.LENGTH_LONG).show();
                            }
                            else{

                              // Toast.makeText(LoginActivity.this, "" + msg, Toast.LENGTH_LONG).show();
                            }

                        }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    //  Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
               /* Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();*/
                hideDialog();
            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
             /*   params.put("email", email);
                params.put("password", password);*/

                params.put("username", name);
                params.put("password", password);
                params.put("apikey", getResources().getString(R.string.apikey));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //save username and password in SharedPreferences
    private void rememberMe(String user, String password) {

        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, user)
                .putString(PREF_PASSWORD, password)
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
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);
        Log.e("password", "" + username + "" + password);

        if (username != null || password != null) {
            //directly show logout form
            //  showLogout(username);
            inputEmail.setText(username);
            inputPassword.setText(password);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "The onDestroy() event");
        // session.setLogin(false);
        //Close the all database connection opened here 31/10/2008 By. Ashish
        if(sqlController != null){
            sqlController = null;
        }
        if(dbController != null){
            dbController.close();
            dbController=null;
        }
        if(connectionDetector != null){
            connectionDetector=null;
        }
        pDialog=null;
        savedUserName=null;
        savedUserPassword=null;
        md5=null;
        md5EncyptedDataPassword=null;
        inputEmail=null;
        inputPassword=null;
        System.gc();
    }
}
