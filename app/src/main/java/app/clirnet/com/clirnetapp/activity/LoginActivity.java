package app.clirnet.com.clirnetapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.DoctorDeatilsAsynTask;
import app.clirnet.com.clirnetapp.app.UpdatePassworsAsynTask;
import app.clirnet.com.clirnetapp.cloudStorage.MyDownloadService;
import app.clirnet.com.clirnetapp.cloudStorage.MyUploadService;
import app.clirnet.com.clirnetapp.fcm.MyFirebaseMessagingService;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;
import app.clirnet.com.clirnetapp.models.Images;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.utility.MD5;
import app.clirnet.com.clirnetapp.utility.SyncDataService;
import app.clirnet.com.clirnetapp.utility.Validator;
import butterknife.ButterKnife;
import butterknife.Bind;
import io.fabric.sdk.android.Fabric;

import static com.tokenautocomplete.TokenCompleteTextView.TAG;


public class LoginActivity extends Activity {

    private static final String PREFS_NAME = "savedCredit";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String LOGIN_TIME = "loginTime";
    private static final String LOGIN_COUNT = "firstTimeLogin";
    private static final String SUGAR_INTO_INVESTIGATION = "vitalsToInvestigation";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private static final String PREF_TERMSANDCONDITION = "terms";

    @Bind(R.id.email)
    EditText inputEmail;
    @Bind(R.id.password)
    EditText inputPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;

    private ProgressDialog pDialog;
    private String name;
    private MD5 md5;
    private ConnectionDetector connectionDetector;

    private String strPassword;
    private String md5EncyptedDataPassword;

    private SQLController sqlController;
    private AppController appController;

    private String phoneNumber;

    private String username;
    private String doctor_membership_number;

    private BannerClass bannerClass;
    private SQLiteHandler dbController;

    private Dialog dialog;
    private SQLiteHandler sInstance;
    private String docId;
    private String type, actionPath;
    private String msg, headerMsg;
    private String clubbingFlag;
    private int notificationTreySize;
    private String start_time;
    private boolean responceCheck;
    private String savedUserName;
    private String savedPhoneNumber;
    private String netSpeed;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private String uid;
    private BroadcastReceiver mBroadcastReceiver;
    private ProgressDialog mProgressDialog;
    private Uri mDownloadUrl = null;


    public void onStart() {
        super.onStart();
        //read username and password from SharedPreferences
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.registerReceiver(mBroadcastReceiver, MyDownloadService.getIntentFilter());
        manager.registerReceiver(mBroadcastReceiver, MyUploadService.getIntentFilter());
        getUser();

    }

    @Override
    public void onStop() {
        super.onStop();

        // Unregister download receiver
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);

        try {
            /*message from fcm service*/
            msg = getIntent().getStringExtra("MSG");
            type = getIntent().getStringExtra("TYPE");
            actionPath = getIntent().getStringExtra("ACTION_PATH");
            headerMsg = getIntent().getStringExtra("HEADER");
            clubbingFlag = getIntent().getStringExtra("CLUBBINGFLAG");
            notificationTreySize = getIntent().getIntExtra("NOTIFITREYSIZE", 0);


            /*Clearing notifications ArrayList from MyFirebaseMessagingService after clicking on it*/

            MyFirebaseMessagingService.notifications_club.clear();

            // FirebaseCrash.setCrashCollectionEnabled(false);


        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + "" + "/" + "Navigation Activity" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        TextView btnLinkToForgetScreen = (TextView) findViewById(R.id.btnLinkToForgetScreen);

        DatabaseClass databaseClass = new DatabaseClass(getApplicationContext());

        LastnameDatabaseClass lastnameDatabaseClass = new LastnameDatabaseClass(getApplicationContext());

        dbController = SQLiteHandler.getInstance(getApplicationContext());
        bannerClass = new BannerClass(getApplicationContext());
        appController = new AppController();

        if (md5 == null) {
            md5 = new MD5();
        }

        mAuth = FirebaseAuth.getInstance();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        // FirebaseStorage storage = FirebaseStorage.getInstance("gs://clirnetapp.appspot.com");
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://clirnetstorage");
        storageReference = storage.getReference();


        //getCurrentDob(21);
        //getCurrentDob(10);

        //this will set value to run  asynctask only once per login session


        new CallAsynOnce().setValue("1");//this set value which helps to call asyntask only once while app is running.

        // Progress dialog

        connectionDetector = new ConnectionDetector(getApplicationContext());
        //Log.e("netSpeed",""+connectionDetector.checkNetworkType());
        netSpeed = connectionDetector.checkNetworkType();

        try {
            sInstance = SQLiteHandler.getInstance(getApplicationContext());

            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }
            Boolean value = getFirstTimeLoginStatus();

            if (value) {
                phoneNumber = sqlController.getPhoneNumber();

                doctor_membership_number = sqlController.getDoctorMembershipIdNew();
                docId = sqlController.getDoctorId();

                //getFirebaseAuthToken();//get the fcm auth token service
            }

        } catch (Exception e) {
            e.printStackTrace();

            appController.appendLog(appController.getDateTimenew() + " " + "/ " + "Login Page  " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        try {

            databaseClass.createDataBase();
            bannerClass.createDataBase();

            int fodRange = new Validator(LoginActivity.this).getFodRange();
            Log.e("fodRange", "" + fodRange);

        } catch (Exception ioe) {
            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page  " + ioe + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            // throw new Error("Unable to create database");
        }

        try {

            databaseClass.openDataBase();
            bannerClass.openDataBase();

            /*This AsyncTask will Insert data from Asset folder file to data 23-03-2016*/
            new InsertDataLocally().execute();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } finally {
            databaseClass.close();
        }

        try {

            lastnameDatabaseClass.createDataBase();

        } catch (IOException e) {

            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            throw new Error("Unable to create database");
        }
        try {

            lastnameDatabaseClass.openDataBase();

            getUsernamePasswordFromDatabase();

           /*  ArrayList<String> po=sqlController.getVisitIdFromDateRange("","");
            Log.e("PO",""+po.size());

            String[] array=null;

            if (po.size() > 0) {
             array = po.toArray(new String[0]);

            }

            ArrayList<Images> successfulFollowUps = sqlController.getLink(array);
            int size=successfulFollowUps.size();

           if(size>0){

               startService(new Intent(LoginActivity.this, MyDownloadService.class)
                        .putExtra(MyDownloadService.EXTRA_IMAGE_LIST,successfulFollowUps)
                        .setAction(MyDownloadService.ACTION_DOWNLOAD));

            }*/

            // downloadFile();
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } finally {
            lastnameDatabaseClass.close();
        }

        btnLogin.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyBoard();
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.rounded_corner_withbackground));
                    name = inputEmail.getText().toString().trim();
                    strPassword = inputPassword.getText().toString().trim();

                    String time = appController.getDateTimenew();
                    AppController.getInstance().trackEvent("Login", "Login Pressed");

                    //This code used for Remember Me(ie. save login id and password for future ref.)
                    rememberMe(name, strPassword, time); //save username only

                    //rememberMeCheckbox();//Removed remeber me check box for safety concern 04-11-16

                    //to authenticate user credentials
                    //Log.e("name",""+name +" savedUserName "+savedUserName+ " phoneNumber "+phoneNumber);
                    //if(name.equals(savedUserName) || name.equals(phoneNumber))
                    LoginAuthentication();
                    logUser();

                    // uploadFile1(Uri.parse("content://media/external/images/media/25980"));
                   /* ArrayList<Images> imageData=null;
                    try {
                         imageData = sqlController.getImagesFromRecodImagesTable();
                        Log.e("imageData","  "+imageData.size());
                    } catch (ClirNetAppException e) {
                        e.printStackTrace();
                    }*/
                    /* if(imageData.size()>0)
                   startService(new Intent(LoginActivity.this, MyUploadService.class)
                            //.putExtra(MyUploadService.EXTRA_FILE_URI, Uri.parse("content://media/external/images/media/26013"))
                            .putExtra(MyUploadService.EXTRA_IMAGE_LIST,  imageData)
                            .putExtra(MyUploadService.FIREBASE_UID,  uid)
                            .setAction(MyUploadService.ACTION_UPLOAD));*/

                  /*  startService(new Intent(LoginActivity.this, MyDownloadService.class)
                            .putExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH, "https://firebasestorage.googleapis.com/v0/b/clirnetapp.appspot.com/o/demo%2F0099-999999-0040-1509689430%2Fprescription_107_22-06-2017_19%3A55%3A38.png?alt=media&token=1b6b3c97-e9bf-4e82-9402-25a382e055b3")
                            .setAction(MyDownloadService.ACTION_DOWNLOAD));*/

//                    Intent intent = new Intent(LoginActivity.this, MyDownloadService.class)
//                            .putExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH, path)
//                            .setAction(MyDownloadService.ACTION_DOWNLOAD);
//                    startService(intent);
                    //  forceCrash();
                    // else appController.showToastMsg(getApplicationContext(),"This username is not licensed to log into this device. Please check username");

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnLogin.setBackground(getResources().getDrawable(R.drawable.rounded_corner_withbackground_blue));
                }
                return false;
            }

        });

        /*We are updating visit date format*/
        // updateVisitDateFormat(); //Commented on  10-05-2017

        addVitalsIntoInvstigation();

        //Commented on  10-05-2017
        /*  Updateing banner stats flag to 0 build no 1.3+  */
        /*Updating associate master flag from 1 to 0*/
        String bannerUpdateFlag = getupdateBannerClickVisitFlag0();
        if (bannerUpdateFlag == null || bannerUpdateFlag.equals("true")) {
            updateBannerTableDataFlag();
        }

        // Link to Register Screen
        btnLinkToForgetScreen.setOnClickListener(new View.OnClickListener()

        {

            public void onClick(View view) {
                boolean isInternetPresent = connectionDetector.isConnectingToInternet();
                if (isInternetPresent && netSpeed.equals("Good")) {
                    showChangePassDialog();
                } else if (isInternetPresent && netSpeed.equals("Low")) {
                    appController.showToastMsg(getApplicationContext(), "Internet Connection is too low To make request/!");
                } else {
                    appController.showToastMsg(getApplicationContext(), "Please Connect To Internet And Try Again!");
                }

            }
        });

        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }

        // Local broadcast receiver
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive:" + intent);
                hideProgressDialog();

                switch (intent.getAction()) {
                    case MyDownloadService.DOWNLOAD_COMPLETED:
                        // Get number of bytes downloaded
                        long numBytes = intent.getLongExtra(MyDownloadService.EXTRA_BYTES_DOWNLOADED, 0);

                        // Alert success
                        showMessageDialog(getString(R.string.success), String.format(Locale.getDefault(),
                                "%d bytes downloaded from %s",
                                numBytes,
                                intent.getStringExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH)));
                        // setUpImage(MyDownloadService.EXTRA_DOWNLOAD_PATH);
                        break;
                    case MyDownloadService.DOWNLOAD_ERROR:
                        // Alert failure
                        showMessageDialog("Error", String.format(Locale.getDefault(),
                                "Failed to download from %s",
                                intent.getStringExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH)));
                        break;
                    case MyUploadService.UPLOAD_COMPLETED:
                    case MyUploadService.UPLOAD_ERROR:
                        onUploadResultIntent(intent);
                        break;
                }
            }
        };

    }

    public static String bytesToHuman(long size) {
        long Kb = 1 * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return floatForm(size) + " byte";
        if (size >= Kb && size < Mb) return floatForm((double) size / Kb) + " Kb";
        if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + " Mb";
        if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + " Gb";
        if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + " Tb";
        if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + " Pb";
        if (size >= Eb) return floatForm((double) size / Eb) + " Eb";

        return "???";
    }

    public static String floatForm(double d) {
        return new DecimalFormat("#.##").format(d);
    }

    private void onUploadResultIntent(Intent intent) {
        // Got a new intent from MyUploadService with a success or failure
        mDownloadUrl = intent.getParcelableExtra(MyUploadService.EXTRA_DOWNLOAD_URL);
        Uri mFileUri = intent.getParcelableExtra(MyUploadService.EXTRA_FILE_URI);
        Log.e("mDownloadUrl", "  " + mDownloadUrl);
        // updateUI(mAuth.getCurrentUser());
    }

    private void showMessageDialog(String title, String message) {
       /* AlertDialog ad = new AlertDialog.Builder(getApplicationContext())
                .setTitle(title)
                .setMessage(message)
                .create();
        ad.show();*/
        appController.showToastMsg(getApplicationContext(), message);
    }

    private void showProgressDialog(String caption) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getApplicationContext());
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.setMessage(caption);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_LOGS};

        int result;
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void updateBannerTableDataFlag() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                // sInstance.FlagupdateBannerClicked("0");//V 1.3.1
                // sInstance.FlagupdateBannerDisplay("0");//V 1.3.1
                sInstance.FlagupdateAssociateMaster("0");//V 1.3.2.8 //02-09-2017 ASHISH
                setupdateBannerClickVisitFlag0();
            }
        });
    }

    private void updateDiagnosisDataFlag() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                // sInstance.FlagupdateBannerClicked("0");//V 1.3.1
                // sInstance.FlagupdateBannerDisplay("0");//V 1.3.1
                sInstance.updateDiagnosisData();//V 1.3.2.8 //02-09-2017 ASHISH
                setDiagnosisUpdateFlag();
            }
        });
    }

    /* we are adding patient_history sugar and sugar_fasting data into Investigation table along with key_visit_id */
    private void addVitalsIntoInvstigation() {

        String addedVitalsSugarFlag = getInsertedInvestigationVitalsFlag();
        if (addedVitalsSugarFlag == null) {
            SQLiteDatabase db = null;
            try {
                db = sInstance.getWritableDatabase();
                db.execSQL("insert into table_investigation(patient_id,key_visit_id,sugar,sugar_fasting) select patient_id, key_visit_id , sugar, sugar_fasting from patient_history;");

                /*Updating flag to true so wont run 2nd time */ //20-05-2017

                setInsertedInvestigationVitalsFlag();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                    setupdateVisitDateFlag();
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

            if (isInternetPresent && netSpeed.equals("Good")) {
                start_time = appController.getDateTimenew();

                new DoctorDeatilsAsynTask(LoginActivity.this, name, md5EncyptedDataPassword, doctor_membership_number, docId, start_time);
                // Removing url response cache from volley.............
                AppController.getInstance().getRequestQueue().getCache().remove(AppConfig.URL_LOGIN);
                //  new LoginAsyncTask(LoginActivity.this, name, md5EncyptedDataPassword, phoneNumber, doctor_membership_number, docId, start_time,type,actionPath,msg,headerMsg,clubbingFlag,notificationTreySize);
                //startService();


                if (savedUserName == null && savedPhoneNumber == null || name.equals(savedUserName) || name.equals(savedPhoneNumber)) {
                    checkLogin(name, md5EncyptedDataPassword, doctor_membership_number, docId);
                } else
                    appController.showToastMsg(getApplicationContext(), "This username is not licensed to log into this device. Please check username");


                savedLoginCounter(); //to save shared pref to update login counter

                new SessionManager(this).setLogin(true);

                //registerToServer(name, "ashish.umredkar@clirnet.com"); //for fcm notification

                //update last getFirebaseAuthToken time if getFirebaseAuthToken from server
                // update last login time
                //lastSyncTime(start_time);
                //lastSyncTime("05-02-2017 02:12:25");
                // hideDialog();

            } else {

                boolean isLogin;

                try {
                    //check last getFirebaseAuthToken time to check if last getFirebaseAuthToken from server is more than 72 hours or not
                    int lasttimeSync = getLastSyncTime();

                    if (lasttimeSync > 72) {
                        showCreatePatientAlertDialog();
                        appController.showToastMsg(getApplicationContext(), "Security credentials expired. Please log in with Internet connection On.");
                        // Toast.makeText(getApplicationContext(), "User not logged in for 3 days. Security credentials expired.", Toast.LENGTH_LONG).show();
                    } else {


                        isLogin = sqlController.validateUser(name, md5EncyptedDataPassword, phoneNumber);

                        if (isLogin) {
                            appController.showToastMsg(getApplicationContext(), "Login Successful");

                            goToNavigation();

                            startService();

                            new SessionManager(this).setLogin(true);

                        } else {
                            appController.showToastMsg(getApplicationContext(), "Username/Password Mismatch");
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
            appController.showToastMsg(getApplicationContext(), "Username/Password Incomplete");

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
        Button btnSubmitPass = (Button) dialog.findViewById(R.id.submit);

        oldPassword = (EditText) dialog.findViewById(R.id.oldPassword);
        newPassword = (EditText) dialog.findViewById(R.id.password);
        confirmPassword = (EditText) dialog.findViewById(R.id.confirmPassword);

        name = inputEmail.getText().toString().trim();


        btnSubmitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPass = oldPassword.getText().toString().trim();
                String newPass = newPassword.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();
                try {
                    if (name != null || !name.equals(""))
                        doctor_membership_number = sqlController.getDoctorMembershipIdNew(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(oldPass)) {
                    oldPassword.setError("Please enter current password!");
                    return;
                }
                if (TextUtils.isEmpty(newPass)) {
                    newPassword.setError("Please enter new password!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPass)) {
                    confirmPassword.setError("Please confirm new password!");
                    return;
                }
                if (!newPass.equals(confirmPass)) {

                    confirmPassword.setError("New and confirmation password do not match. Please re-enter.");
                    return;
                }

                String md5oldPassword = MD5.getMD5(oldPass);
                String md5newPassword = MD5.getMD5(newPass);
                String start_time = appController.getDateTimenew();
                new UpdatePassworsAsynTask(LoginActivity.this, username, doctor_membership_number, docId, md5oldPassword, md5newPassword, start_time);
                dialog.dismiss();

            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void goToNavigation() {

        Intent intent = new Intent(this, SplashActivity.class);
        if (msg != null) {
            intent.putExtra("MSG", msg);
            intent.putExtra("TYPE", type);
            intent.putExtra("ACTION_PATH", actionPath);
            intent.putExtra("HEADER", headerMsg);
        }

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

    @Override
    public void onResume() {
        super.onResume();

    }


    //this method will set username and password to edit text if remember me chkbox is checked previously
    private void getUser() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            inputEmail.setText(username);
        }
    }

    private void getUsernamePasswordFromDatabase() {

        try {
            ArrayList<LoginModel> al;
            al = sqlController.getUserLoginRecrods();
            if (al.size() != 0) {
                savedUserName = al.get(0).getUserName();
                savedPhoneNumber = al.get(0).getPassowrd();
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        dismissProgressDialog();
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

    private void savedLoginCounter() {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(LOGIN_COUNT, "true")
                .apply();
    }

    private void setupdateVisitDateFlag() {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString("flag1", "true")
                .apply();
    }

    //this method will set username and password to edit text if remember me chkbox is checked previously
    private String getUpdateVisitDateFlag() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return pref.getString("flag1", null);
    }

    private String getInsertedInvestigationVitalsFlag() {

        SharedPreferences pref = getSharedPreferences(SUGAR_INTO_INVESTIGATION, MODE_PRIVATE);
        return pref.getString("addedVitals", null);
    }

    private void setInsertedInvestigationVitalsFlag() {

        getSharedPreferences(SUGAR_INTO_INVESTIGATION, Context.MODE_PRIVATE)
                .edit()
                .putString("addedVitals", "true")
                .apply();
    }

    private void setupdateBannerClickVisitFlag0() {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString("bannerflag", "true1")
                .apply();
    }

    //this method will set username and password to edit text if remember me chkbox is checked previously
    private String getupdateBannerClickVisitFlag0() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return pref.getString("bannerflag", null);
    }


    private void setDiagnosisUpdateFlag() {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString("diagnosisFlag", "true")
                .apply();
    }


    //this method will set username and password to edit text if remember me chkbox is checked previously
    private String getDiagnosisUpdateFlag() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return pref.getString("diagnosisFlag", null);
    }

    private void setTransferImagesFlag() {

        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString("transferImageFlag", "true")
                .apply();
    }

    //this method will set username and password to edit text if remember me chkbox is checked previously
    private String getTransferImagesFlag() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return pref.getString("transferImageFlag", null);
    }

    private class InsertDataLocally extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                /* String Diagnosiscount = sInstance.getTableCount("Diagnosis");
                String Specialitycount = sInstance.getTableCount("Speciality");
                String symptomscount = sInstance.getTableCount("Symptoms");
                String last_name_masterCount = sInstance.getTableCount("last_name_master");
                String occupationCount = sInstance.getTableCount("occupation");
                String allergyCount = sInstance.getTableCount("Allergy");

                if (symptomscount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Symptoms.sql"));
                }
                if (Diagnosiscount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Diagnosis.sql"));
                   // Log.e("updating","addding the diagnosis");
                }
                else{
                    String diagnosisUpdateFlag = getDiagnosisUpdateFlag();

                    if (diagnosisUpdateFlag == null || diagnosisUpdateFlag.equals("")) {
                        updateDiagnosisDataFlag();
                     //   Log.e("updating","updating the diagnosis");
                    }
                }
                if (Specialitycount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Speciality.sql"));
                }
                if (last_name_masterCount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("last_name_master.sql"));
                }
                if (occupationCount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("occupation.sql"));
                }
                if (allergyCount.equals("0")) {
                    bannerClass.insertFromFile(getAssets().open("Allergy.sql"));
                   // Log.e("Allergy","Task is ruuning ");
                }*/

                Boolean value = getFirstTimeLoginStatus();

                if (value) {
                    String transferImagesFlag = getTransferImagesFlag();

                    Log.e("transferImagesFlag", "Task is ruuning " + transferImagesFlag);

                    if (transferImagesFlag == null || transferImagesFlag.equals("")) {

                        ArrayList<Images> imageData = sqlController.getImagesFromPatientHistory();
                        int size = imageData.size();
                        Log.e("size", "" + size);

                        if (size > 0) {
                            for (int i = 0; i < size; i++) {
                                String patId = imageData.get(i).getPatId();
                                String visitId = imageData.get(i).getVisitId();
                                String imgUrl = imageData.get(i).getImageUrl();

                                if (imgUrl != null) {
                                    if (!imgUrl.startsWith("/storage/emulated/0/PatientsImages/")) {

                                        imgUrl = "/storage/emulated/0/PatientsImages/" + imgUrl;
                                    }
                                }

                                sInstance.addImages(patId, visitId, imgUrl);
                            }
                        }
                        setTransferImagesFlag();
                    }
                }
            } catch (Exception e) {
                if (appController != null)
                    appController.appendLog(appController.getDateTimenew() + "" + "/" + "Login Page  " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
            return null;
        }

        protected void onPostExecute(String e) {

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        // Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_LOGS, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_LOGS) == PackageManager.PERMISSION_GRANTED) {
                        // Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        // Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_LOGS)) {
                            showDialogOK("Camera and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                           /* Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();*/
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private String getCurrentDob(int age) {

        final Calendar c2 = Calendar.getInstance();
        int mYear = c2.get(Calendar.YEAR);

        int mMonth = c2.get(Calendar.MONTH);

        if (mMonth > 11) {
            mMonth = mMonth - 9;
        } else if (mMonth > 6) {
            mMonth = mMonth - 4;
        }
        int mDay = c2.get(Calendar.DAY_OF_MONTH);
        if (mDay > 27) {
            mDay = mDay - 22;
        } else if (mDay > 16) {
            mDay = mDay - 15;
        }
        int yr = mYear - age;

        String dob = mDay + "-"
                + (mMonth + 1) + "-" + yr;

        dob = appController.ConvertDateFormat(dob);
        return dob;
    }

    private void checkLogin(final String email, final String password, final String docMemId, final String docId) {

        String tag_string_req = "req_login";

        showProgressDialog();
        // showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                // Log.e("Loginresponse", " " + response);

                if (LoginActivity.this.isDestroyed()) { // or call isFinishing() if min sdk version < 17
                    return;
                }
                dismissProgressDialog();

                try {
                    JSONObject jObj = new JSONObject(response);

                    AppController appController = new AppController();
                    String end_time = appController.getDateTimenew();

                    // Create login session
                    JSONObject user = jObj.getJSONObject("data");
                    String result = user.getString("result");
                    String msg = user.getString("msg");
                    String response_end_time = user.getString("process_end_time");
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Login Async Task:  Message :" + msg + "  Result : " + result + " response_end_time:  " + response_end_time + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                    if (result.equals("true")) {

                        dbController.addLoginRecord(email, password, phoneNumber);

                        getTermsAndCondition();
                        //update last getFirebaseAuthToken time if getFirebaseAuthToken from server
                        lastSyncTime(end_time);

                    } else {

                        appController.showToastMsg(getApplicationContext(), "Username/Password Mismatch");

                    }

                } catch (JSONException | ClirNetAppException e) {
                    // JSON error
                    e.printStackTrace();
                    new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Login Async Task" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                } finally {
                    if (dbController != null) {
                        dbController.close();
                    }

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(mContext, " " +error.getMessage(), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                @SuppressLint("MissingPermission") String imeiNo = manager.getDeviceId();
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                String keyid = getResources().getString(R.string.apikey);
                String fcm_id = FirebaseInstanceId.getInstance().getToken();
                params.put("username", email);
                params.put("password", password);
                params.put("apikey", keyid);
                params.put("process_start_time", start_time);
                params.put("fcm_id", fcm_id);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
                params.put("imei_no",imeiNo);
                return checkParams(params);
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                for (Map.Entry<String, String> pairs : map.entrySet()) {
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        int retryforTimes = 2;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        AppController.getInstance().setPriority(Request.Priority.HIGH);
        // Adding request to request queue
        strReq.setShouldCache(false);//set cache false

        AppController.getInstance().getRequestQueue().getCache().clear(); //removing all previous cache
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
    private void getTermsAndCondition() {

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_TERMSANDCONDITION, null);

        if (username == null) {
            showDialog12();
        } else if (username.equals("Yes")) {

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            intent.putExtra("MSG", msg);
            intent.putExtra("TYPE", type);
            intent.putExtra("ACTION_PATH", actionPath);
            intent.putExtra("HEADER", headerMsg);
            intent.putExtra("UNAME",username);
            intent.putExtra("CLUBBINGFLAG", clubbingFlag);
            intent.putExtra("NOTIFITREYSIZE",notificationTreySize);
            startActivity(intent);
            new AppController().showToastMsg(getApplicationContext(), "Login Successful");
            startService();//here we are starting service to start background task

        } else if (username.equals("No")) {
            showDialog12();
        } else if (username.equals("Cancel")) {
            showDialog12();
        }

    }

    private void showDialog12() {

        final View checkBoxView = View.inflate(this, R.layout.alert_checkbox, null);
        final CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkBox);
        final WebView wv = (WebView) checkBoxView.findViewById(R.id.webview);

        //  checkBox.setText("Yes, I accept the terms and condition");
        final AlertDialog.Builder ad = new AlertDialog.Builder(this)
                // .setMessage(termsnconditiomessage)
                .setView(checkBoxView)
                .setIcon(R.drawable.info)
                .setTitle("Terms of Service");

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //Method will send radio button checked status to yes button for further process of redirection
                    checkedStatus(true);

                } else {

                    checkedStatus(false);
                    Toast.makeText(getApplicationContext(), "Please accept the terms and condition to proceed ", Toast.LENGTH_LONG).show();
                }


            }

        });

        wv.setVisibility(View.VISIBLE);
        wv.loadUrl("https://doctor.clirnet.com/doctor/patientcentral/termsandcondition");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });


        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(mContext.getApplicationContext(), "You have accepted the TOS. Welcom to the site", Toast.LENGTH_SHORT).show();
                if (responceCheck) {
                    rememberTermsCondition("Yes");
                    Intent intent = new Intent(getApplicationContext(),
                            SplashActivity.class);
                    startActivity(intent);
                    new AppController().showToastMsg(getApplicationContext(), "Login Successful");
                    startService();

                } else {
                    new AppController().showToastMsg(getApplicationContext(), "Please accept the terms and condition to proceed");
                    // Toast.makeText(mContext.getApplicationContext(), "Please accept the terms and condition to proceed", Toast.LENGTH_SHORT).show();

                }

            }
        });
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You have denied the TOS. You may not access the site", Toast.LENGTH_SHORT).show();
                rememberTermsCondition("No");
            }
        });


        ad.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Please select yes or no", Toast.LENGTH_SHORT).show();
                rememberTermsCondition("No");
            }
        });

        ad.setCancelable(false);
        ad.setView(checkBoxView);
        ad.show();

    }
    private void rememberTermsCondition(String answer) {
       getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .  edit()
                .putString(PREF_TERMSANDCONDITION, answer)
                .apply();

    }
    //store last getFirebaseAuthToken time in prefrence
    private void lastSyncTime(String lastSyncTime) {

        getSharedPreferences("SyncFlag", MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }
    private void checkedStatus(boolean b) {
        responceCheck = b;
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logging in ......");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
    private void logUser() {

        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(docId);
        Crashlytics.setUserEmail(doctor_membership_number);
        Crashlytics.setUserName(username);
    }

    public void uploadFile1(Uri uri) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        Log.e("uid","");

        StorageReference riversRef = storageReference.child("doctor").child("0099-999999-0040-1509512326").child(uri.getLastPathSegment()+".jpg");
        Log.e("riversRef",""+riversRef);
        Log.e("filePath"," "+uri);

        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //if the upload is successfull
                        //hiding the progress dialog
                        //Log.e("taskSnapshot"," "+taskSnapshot.getDownloadUrl());
                        progressDialog.dismiss();
                        //setUpImage(taskSnapshot.getDownloadUrl());
                        //and displaying a success toast
                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        //if the upload is not successfull
                        //hiding the progress dialog
                        progressDialog.dismiss();
                        //and displaying error message
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });

        //Log.e("riversRef", "  " + riversRef.child("images/pic1.jpg").getDownloadUrl());

    }

    private void getFirebaseAuthToken(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
       // pDialog.show();
        final String TAG = "sending";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://43.242.212.136/clirnetapplicationv2/public/doctor/webapi/fcmtoken", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pDialog.hide();
                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONObject user = jObj.getJSONObject("data");
                    String  strToken = user.getString("token");
                    siginInWithCustomeToken(strToken);

                    Log.e("strToken","  "+strToken);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "training");
                params.put("password", "c778fefef897d9a1654bd4babf75bbff");//"c778fefef897d9a1654bd4babf75bbff"
                params.put("apikey", "PFFt0436yjfn0945DevOp0958732Cons3214556");
                params.put("membershipid", "0099-999999-0040");
                return checkParams(params);
            }

            private Map<String, String> checkParams(Map<String, String> map) {
                for (Map.Entry<String, String> pairs : map.entrySet()) {
                    if (pairs.getValue() == null) {
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        int retryforTimes = 2;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);

        // Adding request to request queue
        strReq.setShouldCache(false);//set cache false
        AppController.getInstance().getRequestQueue().getCache().clear(); //removing all previous cache
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }
    private void siginInWithCustomeToken(String token){

        mAuth.signInWithCustomToken(token)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid=mAuth.getUid();
                            Log.e("user",""+user+"   "+mAuth.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void downloadFile(){

        try {
            URL url = new URL("https://firebasestorage.googleapis.com/v0/b/clirnetapp.appspot.com/o/demo%2F0099-999999-0040-1509689430%2Fprescription_07-09-2017%2018%3A15%3A39.png?alt=media&token=19f81bd6-7e0a-42bf-9702-4c0e60319d3a");
            InputStream in = new BufferedInputStream(url.openStream());

            File rootPath = new File(Environment.getExternalStorageDirectory(), "file_name");
            if(!rootPath.exists()) {
                rootPath.mkdirs();
            }
            final File localFile = new File(rootPath,"Image-Porkeri_001.png");

            OutputStream out = new BufferedOutputStream(new FileOutputStream(localFile));

            for ( int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
