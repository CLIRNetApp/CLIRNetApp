package app.clirnet.com.clirnetapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.MasterSessionService;
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
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.utility.ConnectivityChangeReceiver;
import app.clirnet.com.clirnetapp.utility.SyncDataService;



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
            MyFirebaseMessagingService.notifications.clear();

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

            checkConnection();/*Chcking internet connection Manually*/

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Navigation Activity" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        /*Handle push notification messages*/
       // Log.e("type", "   " + notificationTreySize + "  " + actionPath);
        // if(notificationTreySize > 1){
        if (type != null && !type.equals("") && notificationTreySize > 0) {
            callAction("notification");
        } else if (actionPath!=null && notificationTreySize <= 0) {
            callAction(actionPath);
        } else if (headerMsg != null && !headerMsg.equals("") && actionPath != null && !actionPath.equals("") && headerMsg.equals("goto")) {
            callAction(actionPath);
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

                // Launching the login activity
                AppController.getInstance().trackEvent("Logout", "Navigation");

                goToLoginActivity();
                stopService(new Intent(NavigationActivity.this, SyncDataService.class));
                fragment = new HomeFragment();
                break;

            case R.id.nav_help:

                // Launching the login activity
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
            if(getSupportActionBar()!=null)
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
        if (isConnected) callService();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (isConnected) callService();

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
}



