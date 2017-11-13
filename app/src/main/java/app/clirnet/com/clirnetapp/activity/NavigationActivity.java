package app.clirnet.com.clirnetapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.MasterSessionService;
import app.clirnet.com.clirnetapp.cloudStorage.MyDownloadService;
import app.clirnet.com.clirnetapp.cloudStorage.MyUploadService;
import app.clirnet.com.clirnetapp.dialogFragment.MasterSessionDialog;
import app.clirnet.com.clirnetapp.fcm.MyFirebaseMessagingService;
import app.clirnet.com.clirnetapp.fragments.AssociatesFragment;
import app.clirnet.com.clirnetapp.fragments.BarChartFragment;
import app.clirnet.com.clirnetapp.fragments.ConsultationLogFragment;
import app.clirnet.com.clirnetapp.fragments.DashboardFragment;
import app.clirnet.com.clirnetapp.fragments.HealthVitalsDialogFragment;
import app.clirnet.com.clirnetapp.fragments.HelpFragment;
import app.clirnet.com.clirnetapp.fragments.HomeFragment;
import app.clirnet.com.clirnetapp.fragments.IncompleteListFragment;
import app.clirnet.com.clirnetapp.fragments.KnowledgeFragment;
import app.clirnet.com.clirnetapp.fragments.PatientAnnouncements;
import app.clirnet.com.clirnetapp.fragments.PatientReportFragment;
import app.clirnet.com.clirnetapp.fragments.PoHistoryFragment;
import app.clirnet.com.clirnetapp.fragments.ReferralsFragment;
import app.clirnet.com.clirnetapp.fragments.ReportFragment;
import app.clirnet.com.clirnetapp.fragments.ReportFragmentViewPagerSetup;
import app.clirnet.com.clirnetapp.fragments.ShowNotifications;
import app.clirnet.com.clirnetapp.fragments.TopTwentySymptomsAndDiagnosisFragment;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.Images;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.utility.ConnectivityChangeReceiver;
import app.clirnet.com.clirnetapp.utility.SyncDataService;
import app.clirnet.com.clirnetapp.utility.Validator;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, ConsultationLogFragment.OnFragmentInteractionListener, PoHistoryFragment.OnFragmentInteractionListener
        , ReportFragment.OnFragmentInteractionListener, PatientReportFragment.OnFragmentInteractionListener, ReportFragmentViewPagerSetup.OnFragmentInteractionListener, TopTwentySymptomsAndDiagnosisFragment.OnFragmentInteractionListener,
        BarChartFragment.OnFragmentInteractionListener, KnowledgeFragment.OnFragmentInteractionListener, IncompleteListFragment.OnFragmentInteractionListener, AssociatesFragment.OnFragmentInteractionListener, HealthVitalsDialogFragment.onSubmitListener, DashboardFragment.OnFragmentInteractionListener, MasterSessionDialog.OnFragmentInteractionListener, PatientAnnouncements.OnFragmentInteractionListener, ConnectivityChangeReceiver.ConnectivityReceiverListener, ReferralsFragment.OnFragmentInteractionListener, ShowNotifications.OnFragmentInteractionListener, HelpFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;

    private SQLiteHandler dbController;
    private SQLController sqlController;

    private ProgressDialog pDialog;
    private String docName;
    private String emailId;
    private AppController appController;
    private String type, actionPath;
    private String msg, headerMsg;
    private String kind;
    private String docId;
    private NavigationView navigationView;
    private Context context;
    private String savedUserName;
    private String savedUserPassword;
    private String doctor_membership_number;
    private String clubbingFlag;
    private int notificationTreySize;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private String uid;
    public static final String TAG = "TokenAuth";
    private int fodRange;
    private int downloadImageDateRange;
    private ArrayList<Images> imageData;
    private String fromDate;
    private String toDate;
    private Validator mValidator;
    private String toDownloadDate;
    private String toDeleteDownloadImage;
    private String fromDeleteDownloadImageDate;
    private String currentDate;
    private String toDeleteCurrentDate;
    private String formDeleteImageCurrentDate;



    /*To set custom font to activity 28-10-2017*//*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*message from fcm service*/
            msg = getIntent().getStringExtra("MSG");

            type = getIntent().getStringExtra("TYPE");
            actionPath = getIntent().getStringExtra("ACTION_PATH");
            headerMsg = getIntent().getStringExtra("HEADER");
            kind = getIntent().getStringExtra("KIND");
            clubbingFlag = getIntent().getStringExtra("CLUBBINGFLAG");
            notificationTreySize = getIntent().getIntExtra("NOTIFITREYSIZE", 0);
             /*Clearing notifications ArrayList from MyFirebaseMessagingService faster clicking on it*/
            if (clubbingFlag != null && clubbingFlag.equals("1"))
                MyFirebaseMessagingService.notifications_club.clear();

            if (actionPath != null) {

                // Log.e("received","notification");

                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName("Notification Clicked!")
                        .putContentType("Opened Page")
                        .putContentId("article-350")
                        .putCustomAttribute("Custom String", actionPath)
                        .putCustomAttribute("Custom Number", 35));
            }

            if (mAuth == null) {
                mAuth = FirebaseAuth.getInstance();
            }
            if (mValidator == null) {
                mValidator = new Validator(getApplicationContext());
            }

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            // FirebaseStorage storage = FirebaseStorage.getInstance("gs://clirnetapp.appspot.com");

            FirebaseStorage storage = FirebaseStorage.getInstance("gs://clirnetstorage");
            if (storageReference == null)
                storageReference = storage.getReference();

            fodRange = mValidator.getFodRange();
            downloadImageDateRange = mValidator.getDownloadImageRange();


            Log.e("fodRange", "" + fodRange);

        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + "" + "/" + "Navigation Activity " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appController = new AppController();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        ImageView imgvw = (ImageView) hView.findViewById(R.id.imageView);
        TextView u_name = (TextView) hView.findViewById(R.id.user_name);
        TextView email = (TextView) hView.findViewById(R.id.email);
        imgvw.setImageResource(R.drawable.emp_img);

       /*Checking if last login time is more than 8 hours*/
        checkLastLoginTime();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        try {
            if (dbController == null)
                dbController = new SQLiteHandler(getApplicationContext());

            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }

            docName = sqlController.getDocdoctorName();

            emailId = sqlController.getDocdoctorEmail();

            docId = sqlController.getDoctorId();

            //doctor_membership_number = sqlController.getDoctorMembershipIdNew();

            ArrayList<LoginModel> al;
            al = sqlController.getUserLoginRecrodsNew();

            if (al.size() != 0) {
                savedUserName = al.get(0).getUserName();
                savedUserPassword = al.get(0).getPassowrd();
            }
            // Log.e("savedUserName",""+savedUserName);
            doctor_membership_number = sqlController.getDoctorMembershipIdNew(savedUserName);

            u_name.setText("Dr. " + docName);
            email.setText(emailId);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            Date date = new Date();
             currentDate = dateFormat.format(date);
            Date fodRangeDate = AppController.addDay1(date, fodRange);

            Date downloadDate = AppController.addDay1(date, downloadImageDateRange);

            Date fromdeleteDownloadImageDate = AppController.addDay1(date, 1);

            Date deleteDownloadImageDate = AppController.addDay1(date, 15);

            Date deleteImageDateCurrentDate = AppController.addDay1(date, -fodRange);

            String strTommorowsDate = dateFormat.format(fodRangeDate);


            Log.e("currentDate", "  " + currentDate + "   " + strTommorowsDate);

            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            fromDate = dateFormat3.format(date);
            toDate = dateFormat3.format(fodRangeDate);
            toDownloadDate = dateFormat3.format(downloadDate);

            toDeleteCurrentDate=dateFormat3.format(date);

            fromDeleteDownloadImageDate = dateFormat3.format(fromdeleteDownloadImageDate);
            toDeleteDownloadImage = dateFormat3.format(deleteDownloadImageDate);

            formDeleteImageCurrentDate=dateFormat3.format(deleteImageDateCurrentDate);
            // Log.e("fromDate", "  " + fromDate + "" + toDate);

            deleteDownloadedImagesFromRange();

            /* ArrayList<String> po=sqlController.getVisitIdFromDateRange(fromDate,toDate);
            Log.e("PO",""+po.size());

            String[] array=null;

            if (po.size() > 0) {
                array = po.toArray(new String[0]);

            }
            if(po.size() >0) {
                ArrayList<Images> successfulFollowUps = sqlController.getLink(array);
                int size = successfulFollowUps.size();

           *//* if(size>0){

               startService(new Intent(LoginActivity.this, MyDownloadService.class)
                        .putExtra(MyDownloadService.EXTRA_IMAGE_LIST,successfulFollowUps)
                        .setAction(MyDownloadService.ACTION_DOWNLOAD));

            }*//*
            }*/


            try {
                imageData = sqlController.getImagesFromRecodImagesTable();
                Log.e("imageData", "  " + imageData.size());
            } catch (ClirNetAppException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Navigation Activity" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        checkConnection();

        /*Checking internet connection Manually*/

        /*Handle push notification messages*/

        // Log.e("type", "   " + notificationTreySize + "  " + actionPath);

        // if(notificationTreySize > 1){
        if (type != null && !type.equals("") && notificationTreySize > 0) {
            callAction("notification");
        } else if (actionPath != null && !actionPath.equals("") && notificationTreySize <= 0 && type != null && !type.equals("") && !type.equals("1")) {
            callAction(actionPath);
        } else if (headerMsg != null && !headerMsg.equals("") && actionPath != null && !actionPath.equals("") && headerMsg.equals("goto")) {
            callAction(actionPath);
        } else if (type != null && !type.equals("") && actionPath != null && !actionPath.equals("") && type.equals("1")) {
            //for app-page
            Fragment fragment;
            fragment = new KnowledgeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("URL", actionPath);
            fragment.setArguments(bundle2);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        } else {
            callAction("DashboardFragment");
        }
    }

    private void subscribeToPushService() {

        //FirebaseMessaging.getInstance().subscribeToTopic("news");
        //   Log.d("AndroidBash", "Subscribed");
        //Toast.makeText(NavigationActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        //String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        // Log.d("AndroidBash", token);
        // Toast.makeText(NavigationActivity.this, token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //This will used to navigate user from one menu to another

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        selectDrawerItem(item);
        return true;
    }

    public void selectDrawerItem(MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.nav_po:
                fragment = new HomeFragment();
                AppController.getInstance().trackEvent("Patient Central", "Navigation");
                break;

            case R.id.nav_consultLog:
                fragment = new ConsultationLogFragment();
                AppController.getInstance().trackEvent("Consultation Log", "Navigation");

                break;

            case R.id.nav_pohistory:
                fragment = new PoHistoryFragment();
                AppController.getInstance().trackEvent("Patient History", "Navigation");

                break;

            case R.id.nav_prescription:
                AppController.getInstance().trackEvent("Prescription", "Navigation");

                fragment = new IncompleteListFragment();
                break;

            case R.id.nav_refered:
                AppController.getInstance().trackEvent("Associates", "Navigation");

                fragment = new AssociatesFragment();
                break;


            case R.id.nav_report:

                fragment = new ReportFragment();
                AppController.getInstance().trackEvent("Reports", "Navigation");

                break;

            case R.id.nav_dashboard:

                AppController.getInstance().trackEvent("Dashboard", "Navigation");

                fragment = new DashboardFragment();
                break;

            case R.id.nav_announcement:

                AppController.getInstance().trackEvent("Patient Announcements", "Navigation");

                fragment = new PatientAnnouncements();
                break;

            case R.id.nav_notification:

                AppController.getInstance().trackEvent("Notification", "Navigation");

                fragment = new ShowNotifications();
                break;

            case R.id.nav_knowldge:
                AppController.getInstance().trackEvent("Knowledge", "Navigation");

                fragment = new KnowledgeFragment();
                break;

            case R.id.nav_logout:

                AppController.getInstance().trackEvent("Logout", "Navigation");

                goToLoginActivity();
                stopService(new Intent(NavigationActivity.this, SyncDataService.class));
                fragment = new HomeFragment();
                break;

            case R.id.nav_help:

                AppController.getInstance().trackEvent("Help", "Navigation");

                fragment = new HelpFragment();
                break;
        }

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        item.setChecked(true);

        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        drawer.closeDrawers();
    }

    private void goToLoginActivity() {
        /*SETTING LOGIN SESSION FALSE*/
        new SessionManager(this).setLogin(false);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        /*Setting listner to start broadcast receiver*/
        AppController.getInstance().setConnectivityListener(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        // session.setLogin(false);
        //Close the all database connection opened here 31/10/2016 By. Ashish
        if (sqlController != null) {
            sqlController = null;
        }
        if (dbController != null) {
            dbController = null;
        }
        if (fragmentManager != null) {
            fragmentManager = null;
        }
        if (appController != null) {
            appController = null;
        }

        cleanResources();
    }

    private void cleanResources() {
        pDialog = null;
        docName = null;
        emailId = null;
        msg = null;
        actionPath = null;
        type = null;
        headerMsg = null;
        kind = null;
        docId = null;
    }

    public void setActionBarTitle(String title) {
        try {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(title);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void callAction(String action) {

        Fragment mFragment;
        fragmentManager = getSupportFragmentManager();
        switch (action) {
            case "DashboardFragment":
                AppController.getInstance().trackEvent("Dashboard", "Navigation");

                mFragment = new DashboardFragment();
                break;

            case "HomeFragment":
                mFragment = new HomeFragment();
                AppController.getInstance().trackEvent("Patient Central", "Navigation");

                break;

            case "ConsultationLogFragment":
                mFragment = new ConsultationLogFragment();
                AppController.getInstance().trackEvent("Consultation Log", "Navigation");

                break;

            case "PoHistoryFragment":
                mFragment = new PoHistoryFragment();
                AppController.getInstance().trackEvent("Patient History", "Navigation");

                break;

            case "ReportFragment":

                mFragment = new ReportFragment();
                AppController.getInstance().trackEvent("Patient Report", "Navigation");

                break;

            case "KnowledgeFragment":

                AppController.getInstance().trackEvent("Knowledge", "Navigation");

                mFragment = new KnowledgeFragment();
                break;

            case "IncompleteListFragment":

                AppController.getInstance().trackEvent("Prsecription", "Navigation");

                mFragment = new IncompleteListFragment();
                break;

            case "AssociatesFragment":

                AppController.getInstance().trackEvent("Associates", "Navigation");

                mFragment = new AssociatesFragment();
                break;


            case "PatientAnnouncements":

                AppController.getInstance().trackEvent("PatientAnnouncements", "Navigation");

                mFragment = new PatientAnnouncements();
                break;

            case "notification":

                AppController.getInstance().trackEvent("notification", "Navigation");

                mFragment = new ShowNotifications();
                break;


            default:

                mFragment = new DashboardFragment();
        }

        fragmentManager.beginTransaction().replace(R.id.flContent, mFragment).commit();

    }

    //get data from heaalth dialog fragment and pass to po history fragment for search opertaion.
    @Override
    public void setOnSubmitListener(Bundle arg) {
        //  Log.e("Dialog ","  Dialog is Clicked "+arg);
        PoHistoryFragment newFragment = new PoHistoryFragment();
        newFragment.updateDisplay(arg);
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityChangeReceiver.isConnected();

        getFirebaseAuthToken();//get the fcm auth token service
        callService();

        if (isConnected && uid!=null) {
            final Handler handler = new Handler();
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 5000ms
                sendImagesToCloud();
            }
        }, 5000);*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 8000ms
                startDeletingImageTask();
                startDownloadingImageTask();
            }
        }, 8000);

        }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        getFirebaseAuthToken();//get the fcm auth token service
        callService();
        if (isConnected && uid!=null) {

        final Handler handler = new Handler();
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 5000ms
                sendImagesToCloud();

            }
        }, 5000);*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 8000ms
                startDeletingImageTask();
                startDownloadingImageTask();
            }
        }, 8000);
    }

    }


    private void callService() {

        this.context = this;

        Intent msgIntent = new Intent(this.context, MasterSessionService.class);
        msgIntent.putExtra(MasterSessionService.TYPE, "all");
        msgIntent.putExtra(MasterSessionService.COUNT, "2");
        msgIntent.putExtra(MasterSessionService.DOC_ID, docId);
        msgIntent.putExtra(MasterSessionService.USERNAME, savedUserName);
        msgIntent.putExtra(MasterSessionService.DOC_MEM_ID, doctor_membership_number);
        startService(msgIntent);

       /* boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 0, msgIntent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, msgIntent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1800000, pendingIntent);
        }*/

    }

    /* this function used to check item from navigation list when perticular fragment gets called.*/
    public void setCountText(int count) {
        if (navigationView != null)
            navigationView.getMenu().getItem(count).setChecked(true);
    }


    private void checkLastLoginTime() {

        SharedPreferences pref = getSharedPreferences("savedCredit", MODE_PRIVATE);

        String loginTime = pref.getString("loginTime", null);

        int hrs = AppController.hoursAgo(loginTime);
        if (hrs >= 8) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            if (msg != null) {

                i.putExtra("MSG", msg);
                i.putExtra("TYPE", type);
                i.putExtra("ACTION_PATH", actionPath);
                i.putExtra("HEADER", headerMsg);
            }
            startActivity(i);
            System.gc();
        }

    }

    private void getFirebaseAuthToken() {

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
                    String strToken = user.getString("token");
                    siginInWithCustomeToken(strToken);

                    Log.e("strToken", "  " + strToken);

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

    private void siginInWithCustomeToken(String token) {

        mAuth.signInWithCustomToken(token)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = mAuth.getUid();
                            Log.e("user", "" + user + "   " + mAuth.getUid());
                            if(uid!=null)
                                sendImagesToCloud();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(NavigationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendImagesToCloud() {

        if (uid != null)
            if (imageData != null && imageData.size() > 0)
                startService(new Intent(NavigationActivity.this, MyUploadService.class)
                        //.putExtra(MyUploadService.EXTRA_FILE_URI, Uri.parse("content://media/external/images/media/26013"))
                        .putExtra(MyUploadService.EXTRA_IMAGE_LIST, imageData)
                        .putExtra(MyUploadService.FIREBASE_UID, uid)
                        .setAction(MyUploadService.ACTION_UPLOAD));
    }

    /*Deleting local images when */

    private void startDeletingImageTask() {

        if (sqlController != null)
            try {

                ArrayList<String> po = sqlController.getVisitIdFromDateRange(fromDeleteDownloadImageDate, toDate,currentDate,formDeleteImageCurrentDate,toDeleteCurrentDate);
                Log.e("PO", "" + po.size());


                Log.e("po.size()", "" + po.size());

                String[] array ;

                if (po.size() > 0) {
                    array = po.toArray(new String[0]);
                    ArrayList<Images> successfulFollowUps = sqlController.getLink(array);
                    int size = successfulFollowUps.size();

                    Log.e("successfulFollowUps" +
                            "",""+size);

                    if (size > 0) {

                        for (int i = 0; i < size; i++) {
                            String imgPath = successfulFollowUps.get(i).getActImagePath();
                            int id = successfulFollowUps.get(i).getpId();
                            Log.e("imgPath", "" + imgPath + "  id :  " + id);
                            if (imgPath != null)
                               deleteImageFile(id, imgPath,2);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }



    private void startDownloadingImageTask() {

        if (sqlController != null)
            try {

                ArrayList<String> po = sqlController.getVisitIdFromDateRangeForDownload(fromDate, toDownloadDate);
                Log.e("PO", "" + po.size());

                Log.e("po.size()", "" + po.size());

                String[] array;

                if (po.size() > 0) {

                    array = po.toArray(new String[0]);
                    ArrayList<Images> successfulFollowUps = sqlController.getImagesToDownload(array,fromDate, toDownloadDate);
                    int size = successfulFollowUps.size();

                    if (size > 0) {

                        startService(new Intent(getApplicationContext(), MyDownloadService.class)
                                .putExtra(MyDownloadService.EXTRA_IMAGE_LIST, successfulFollowUps)
                                .putExtra(MyDownloadService.FIREBASE_UID, uid)
                                .setAction(MyDownloadService.ACTION_DOWNLOAD));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void deleteDownloadedImagesFromRange(){
        if (sqlController != null)
            try {

                ArrayList<Images> deleteDownlodedImgIdList = sqlController.getVisitIdFromDateRangeForDeletion(fromDeleteDownloadImageDate, toDeleteDownloadImage);

                Log.e("deleteDownlodedImg", "" + deleteDownlodedImgIdList.size());

                int size = deleteDownlodedImgIdList.size();

                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                            String imgPath = deleteDownlodedImgIdList.get(i).getImageUrl();
                            int id = deleteDownlodedImgIdList.get(i).getpId();
                            Log.e("deleteDownlodedImg", "" + imgPath + "  id :  " + id);
                            if (imgPath != null)
                                deleteImageFile(id, imgPath,2);
                        }
                    }

            } catch (ClirNetAppException  e) {
                e.printStackTrace();
            }
    }
    private void deleteImageFile(int id, String imgPath,int status) {

        File fdelete = new File(imgPath);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + fdelete);
                if (sqlController != null)
                    sqlController.updateImagesRecords(id, "", status);//update status =2
            } else {
                System.out.println("file not Deleted :" + fdelete);
            }
        }
    }
}



