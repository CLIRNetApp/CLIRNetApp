
package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.Utility.MD5;
import app.clirnet.com.clirnetapp.Utility.SyncDataService;
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

public class LoginActivity extends Activity  {
    private static final String TAG = "Login";
    private static final String PREFS_NAME = "savedCredit";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String LOGIN_TIME = "loginTime";

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private String name;
    private MD5 md5;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent;
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
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



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
        BannerClass bannerClass = new BannerClass(getApplicationContext());
        LastnameDatabaseClass lastnameDatabaseClass = new LastnameDatabaseClass(getApplicationContext());
        appController = new AppController();

        convertDate();


        /*Intent serviceIntent = new Intent(getApplicationContext(), SyncDataService.class);
        serviceIntent. putExtra("UserID", "123456");
        startService(serviceIntent);*/

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
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home" + e);
        }

        try {

            databaseClass.createDataBase();
            bannerClass.createDataBase();
            phoneNumber = sqlController.getPhoneNumber();

        } catch (Exception ioe) {
            appController.appendLog(appController.getDateTime() + "" + "/" + "Home" + ioe);

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

                                            String time=appController.getDateTimenew();
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

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
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


// --Commented out by Inspection START (07-11-2016 16:43):
//// This method to add ailments from asset folder to our db ie. ailments
//    private void saveAilmentToDb() {
//        Thread thread = new Thread(){
//            public void run(){
//                Cursor cursor = null;
//                //this will populate ailments  from asset folder ailment table
//                DatabaseClass databaseClass = new DatabaseClass(LoginActivity.this);
//                try {
//                    cursor = databaseClass.getAilmentsList();
//                    ArrayList<Object> mAilmemtArrayList = new ArrayList<>();
//                    int columnIndex = cursor.getColumnIndex("ailment_name");
//                    while (cursor.moveToNext()) {
//                        mAilmemtArrayList.add(cursor.getString(columnIndex)); //add the item
//                        dbController.addAilments(cursor.getString(columnIndex));
//                        Log.e("ali", "ali is:" + cursor.getString(columnIndex));
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                    if(databaseClass !=null){
//                        databaseClass.close();
//                    }
//                }
//            }
//        };
//
//        thread.start();
//    }
// --Commented out by Inspection STOP (07-11-2016 16:43)

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
                //  checkLogin(name, md5EncyptedDataPassword);

                new DoctorDeatilsAsynTask(LoginActivity.this, name, md5EncyptedDataPassword);
                new LoginAsyncTask(LoginActivity.this, name, md5EncyptedDataPassword, phoneNumber);
                startService();
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
                    int lasttimeSync= getLastSyncTime();

                    if(lasttimeSync > 72){
                        showCreatePatientAlertDialog();
                        Toast.makeText(getApplicationContext(),"Please login via internet and sync data to server",Toast.LENGTH_LONG).show();
                    }else {

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
        Log.e("loginTime12", "" + lastSyncTime+"  "+hrslastSync);

        return  hrslastSync;
    }

    private void startService() {
        Intent serviceIntent = new Intent(getApplicationContext(), SyncDataService.class);
        serviceIntent.putExtra("name", name);
        serviceIntent.putExtra("password", md5EncyptedDataPassword);
        serviceIntent.putExtra("apikey", "PFFt0436yjfn0945DevOp0958732Cons3214556");
        startService(serviceIntent);
    }

// --Commented out by Inspection START (07-11-2016 16:43):
//    private void rememberMeCheckbox() {
//        CheckBox ch = (CheckBox) findViewById(R.id.ch_rememberme);
//        try {
//            if (ch.isChecked()) {
//                rememberMe(name, strPassword); //save username and password
//            } else {
//                rememberMe(name, ""); //save username and password
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
// --Commented out by Inspection STOP (07-11-2016 16:43)


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
                String newPass =newPassword.getText().toString().trim();
                String confirmPass=confirmPassword.getText().toString().trim();


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
                if(!newPass.equals(confirmPass)){
                    Toast.makeText(getApplicationContext(),"Old and new Password did not match ",Toast.LENGTH_LONG).show();
                    confirmPassword.setError("Old and new Password did not match  !");
                    return;
                }

                String md5oldPassword = MD5.getMD5(oldPass);
                String md5newPassword = MD5.getMD5(newPass);
              //  Log.e("oldand new",""+md5oldPassword +" new "+md5newPassword);
                new UpdatePassworsAsynTask(LoginActivity.this,username,doctor_membership_number,md5oldPassword,md5newPassword);
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


// --Commented out by Inspection START (07-11-2016 16:43):
//    private void showAlert() {
//        final Dialog dialog = new Dialog(LoginActivity.this);
//        dialog.setContentView(R.layout.custom_popup);
//        dialog.setTitle("Change Password...");
//
//        // set the custom dialog components - text, image and button
//
//        TextView btnSubmit = (TextView) dialog.findViewById(R.id.submit);
//
//
//        password = (EditText) dialog.findViewById(R.id.password);
//        confirmPassord = (EditText) dialog.findViewById(R.id.confirmpassword);
//
//        //  image.setImageResource(R.drawable.ic_launcher);
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String pass = password.getText().toString();
//                String conPass = confirmPassord.getText().toString();
//
//                if (TextUtils.isEmpty(pass)) {
//                    password.setError("Please enter Username !");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(conPass)) {
//                    confirmPassord.setError("Please enter Conform Password !");
//                    return;
//                }
//                if (!pass.equals(conPass)) {
//                    confirmPassord.setError("Password doesn't match ! Try again");
//                }
//
//            }
//        });
//
//        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
//        // if button is clicked, close the custom dialog
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
// --Commented out by Inspection STOP (07-11-2016 16:43)


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
    private void rememberMe(String user,String password,String dateTime ) {

        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME, user)
                .putString(PREF_PASSWORD, password)
                .putString(LOGIN_TIME,dateTime)
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
        String lastSync=pref1.getString("lastSyncTime",null);
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
    public void lastSyncTime(String lastSyncTime ) {

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
}
