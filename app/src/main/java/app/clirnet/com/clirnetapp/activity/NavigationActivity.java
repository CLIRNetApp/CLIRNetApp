package app.clirnet.com.clirnetapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.fragments.AssociatesFragment;
import app.clirnet.com.clirnetapp.fragments.BarChartFragment;
import app.clirnet.com.clirnetapp.fragments.ConsultationLogFragment;
import app.clirnet.com.clirnetapp.fragments.DashboardFragment;
import app.clirnet.com.clirnetapp.fragments.HealthVitalsDialogFragment;
import app.clirnet.com.clirnetapp.fragments.HomeFragment;
import app.clirnet.com.clirnetapp.fragments.IncompleteListFragment;
import app.clirnet.com.clirnetapp.fragments.KnowledgeFragment;
import app.clirnet.com.clirnetapp.fragments.PatientReportFragment;
import app.clirnet.com.clirnetapp.fragments.PoHistoryFragment;
import app.clirnet.com.clirnetapp.fragments.ReportFragment;
import app.clirnet.com.clirnetapp.fragments.ReportFragmentViewPagerSetup;
import app.clirnet.com.clirnetapp.fragments.TopTenAilmentFragment;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.utility.SyncDataService;

/*import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;*/


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, ConsultationLogFragment.OnFragmentInteractionListener, PoHistoryFragment.OnFragmentInteractionListener
        , ReportFragment.OnFragmentInteractionListener, PatientReportFragment.OnFragmentInteractionListener, ReportFragmentViewPagerSetup.OnFragmentInteractionListener, TopTenAilmentFragment.OnFragmentInteractionListener,
        BarChartFragment.OnFragmentInteractionListener, KnowledgeFragment.OnFragmentInteractionListener, IncompleteListFragment.OnFragmentInteractionListener, AssociatesFragment.OnFragmentInteractionListener,HealthVitalsDialogFragment.onSubmitListener,DashboardFragment.OnFragmentInteractionListener {


    private FragmentManager fragmentManager;

    private SQLiteHandler dbController;
    private SQLController sqlController;

    private ProgressDialog pDialog;
    private String docName;
    private String emailId;
    private AppController appController;
    private String type, actionPath;
    private String msg, headerMsg;
    private String calledFrom;
    private String kind;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*message from fcm service*/
            msg = getIntent().getStringExtra("MSG");

            type = getIntent().getStringExtra("TYPE");
            actionPath = getIntent().getStringExtra("ACTION_PATH");
            headerMsg = getIntent().getStringExtra("HEADER");
            calledFrom=getIntent().getStringExtra("CALLEDFROM");
            kind=getIntent().getStringExtra("KIND");

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView = navigationView.getHeaderView(0);
        ImageView imgvw = (ImageView) hView.findViewById(R.id.imageView);
        TextView u_name = (TextView) hView.findViewById(R.id.user_name);
        TextView email = (TextView) hView.findViewById(R.id.email);
        imgvw.setImageResource(R.drawable.emp_img);


        dbController = new SQLiteHandler(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();

            docName = sqlController.getDocdoctorName();

            emailId = sqlController.getDocdoctorEmail();

            u_name.setText("Welcome,  Dr. " + docName);
            email.setText(emailId);

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Navigation Activity" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        Fragment fragment = null;

        /*Handle push notification messages*/

        if (type != null && !type.equals("") && actionPath != null && !actionPath.equals("") && type.equals("2")) {
            //for web-page
            callAction(actionPath);

        } else if (type != null && !type.equals("") && actionPath != null && !actionPath.equals("") && type.equals("1")) {
            //for app-page
            fragment = new KnowledgeFragment();

            Bundle bundle2 = new Bundle();
            bundle2.putString("URL", actionPath);
            fragment.setArguments(bundle2);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        } else if (type != null && !type.equals("") && type.equals("3")) {//for announcement
            fragment = new HomeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("SERVICE", "3");
            bundle2.putString("MESSAGE", msg);
            bundle2.putString("HEADER", headerMsg);
            fragment.setArguments(bundle2);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        } else if (type != null && !type.equals("") && type.equals("4")) {//for service
            fragment = new HomeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("SERVICE", "START");
            fragment.setArguments(bundle2);
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        }else if(kind!=null && kind.equals("1")){
            fragment = new PoHistoryFragment();
            onNavigationItemSelected(navigationView.getMenu().getItem(2));
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        } else if(kind!=null && kind.equals("2")){
            fragment = new ConsultationLogFragment();
            onNavigationItemSelected(navigationView.getMenu().getItem(1));
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        } else {
            fragment = new HomeFragment();
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        }
     //   fragmentManager = getSupportFragmentManager();
      //  fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        // subscribeToPushService();


    }

    private void subscribeToPushService() {

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //   Log.d("AndroidBash", "Subscribed");
        Toast.makeText(NavigationActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

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

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_po:
                fragment = new HomeFragment();
                AppController.getInstance().trackEvent("Patient Central", "Navigation", "Track event");
                break;

            case R.id.nav_consultLog:
                fragment = new ConsultationLogFragment();
                AppController.getInstance().trackEvent("Consultation Log", "Navigation", "Track event");

                break;

            case R.id.nav_pohistory:
                fragment = new PoHistoryFragment();
                AppController.getInstance().trackEvent("Patient History", "Navigation", "Track event");

                break;

            case R.id.nav_report:

                fragment = new ReportFragment();
                AppController.getInstance().trackEvent("Patient Report", "Navigation", "Track event");

                break;

            case R.id.nav_knowldge:
                AppController.getInstance().trackEvent("Knowledge", "Navigation", "Track event");

                fragment = new KnowledgeFragment();
                break;

            case R.id.nav_dashboard:

                AppController.getInstance().trackEvent("Dashboard", "Navigation", "Track event");

                fragment = new DashboardFragment();
                break;

            case R.id.nav_prescription:
                AppController.getInstance().trackEvent("Prsecription", "Navigation", "Track event");

                fragment = new IncompleteListFragment();
                break;

            case R.id.nav_refered:
                AppController.getInstance().trackEvent("Associates", "Navigation", "Track event");

                fragment = new AssociatesFragment();
                break;

            case R.id.nav_logout:

                // Launching the login activity
                AppController.getInstance().trackEvent("Logout", "Navigation", "Track event");

                goToLoginActivity();
                stopService(new Intent(NavigationActivity.this, SyncDataService.class));
                fragment = new HomeFragment();
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
        //Close the all database connection opened here 31/10/2008 By. Ashish
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
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    public void callAction(String action) {
        Fragment mFragment;
        fragmentManager = getSupportFragmentManager();
        switch (action) {
            case "HomeFragment":
                mFragment = new HomeFragment();
                AppController.getInstance().trackEvent("Patient Central", "Navigation", "Track event");

                break;

            case "ConsultationLogFragment":
                mFragment = new ConsultationLogFragment();
                AppController.getInstance().trackEvent("Consultation Log", "Navigation", "Track event");

                break;

            case "PoHistoryFragment":
                mFragment = new PoHistoryFragment();
                AppController.getInstance().trackEvent("Patient History", "Navigation", "Track event");

                break;

            case "ReportFragment":

                mFragment = new ReportFragment();
                AppController.getInstance().trackEvent("Patient Report", "Navigation", "Track event");

                break;

            case "KnowledgeFragment":
                AppController.getInstance().trackEvent("Knowledge", "Navigation", "Track event");

                mFragment = new KnowledgeFragment();
                break;
            case "IncompleteListFragment":
                AppController.getInstance().trackEvent("Prsecription", "Navigation", "Track event");

                mFragment = new IncompleteListFragment();
                break;

            case "AssociatesFragment":
                AppController.getInstance().trackEvent("Associates", "Navigation", "Track event");

                mFragment = new AssociatesFragment();
                break;


            default:
                mFragment = new HomeFragment();

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
}



