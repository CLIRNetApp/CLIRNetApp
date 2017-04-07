package app.clirnet.com.clirnetapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.fragments.AssociatesFragment;
import app.clirnet.com.clirnetapp.fragments.BarChartFragment;
import app.clirnet.com.clirnetapp.fragments.ConsultationLogFragment;
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
import app.clirnet.com.clirnetapp.utility.SyncDataService;

/*import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;*/


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, ConsultationLogFragment.OnFragmentInteractionListener, PoHistoryFragment.OnFragmentInteractionListener
        , ReportFragment.OnFragmentInteractionListener, PatientReportFragment.OnFragmentInteractionListener, ReportFragmentViewPagerSetup.OnFragmentInteractionListener, TopTenAilmentFragment.OnFragmentInteractionListener,
        BarChartFragment.OnFragmentInteractionListener, KnowledgeFragment.OnFragmentInteractionListener, IncompleteListFragment.OnFragmentInteractionListener, AssociatesFragment.OnFragmentInteractionListener {


    private FragmentManager fragmentManager;


    //private  String msg = "Android : ";
    private SQLiteHandler dbController;
    private SQLController sqlController;

    private ProgressDialog pDialog;
    private String docName;
    private String emailId;
    private AppController appController;
    private String msgType, type, actionPath;
    private String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*message from fcm service*/
            msg = getIntent().getStringExtra("MSG");
            //
            msgType = getIntent().getStringExtra("TAG");
            type = getIntent().getStringExtra("TYPE");
            actionPath = getIntent().getStringExtra("ACTION_PATH");
            Log.e("QUOTE", "QUOTE: " + type + "  " + actionPath);

        } catch (Exception e) {
            e.printStackTrace();
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
            appController.appendLog(appController.getDateTime() + "" + "/" + "Navigation Activity" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        Fragment fragment;

        if (type != null && !type.equals("") && actionPath != null && !actionPath.equals("") && type.equals("2")) {
            //for web-page
            callAction(actionPath);
        } else if (type != null && !type.equals("") && actionPath != null && !actionPath.equals("") && type.equals("1")) {
            //for app-page
            fragment = new KnowledgeFragment();
            fragmentManager = getSupportFragmentManager();
            Bundle bundle2 = new Bundle();
            bundle2.putString("URL", actionPath);
            fragment.setArguments(bundle2);
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        } else if (type != null && !type.equals("") && type.equals("3"))
          {//for announcement
            fragment = new HomeFragment();
            fragmentManager = getSupportFragmentManager();
            Bundle bundle2 = new Bundle();
            bundle2.putString("SERVICE", "3");
            bundle2.putString("MESSAGE",msg);
            fragment.setArguments(bundle2);
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        } else if (type != null && !type.equals("") && type.equals("4")) {//for service
            fragment = new HomeFragment();
            fragmentManager = getSupportFragmentManager();
            Bundle bundle2 = new Bundle();
            bundle2.putString("SERVICE", "true");
            fragment.setArguments(bundle2);
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        } else {
            fragment = new HomeFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        }
        // subscribeToPushService();
    }

    private void subscribeToPushService() {

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //   Log.d("AndroidBash", "Subscribed");
        Toast.makeText(NavigationActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

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
    public boolean onNavigationItemSelected(MenuItem item) {

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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    /** Called just before the activity is destroyed. */
    /** Called when the activity is no longer visible. */
    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onResume() {
        super.onResume();
        //  Log.d(msg, "The onResume() event");

    }

    @Override
    protected void onPause() {
        super.onPause();
        //  Log.d(msg, "The onPause() event");

    }

    @Override
    protected void onStop() {
        super.onStop();
        //  Log.d(msg, "The onStop() event");

        System.gc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  Log.d(msg, "The onDestroy() event");
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
        System.gc();
    }

    private void cleanResources() {
        pDialog = null;
        docName = null;
        emailId = null;
        msg = null;
        msgType = null;
        actionPath = null;
        type = null;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    public void callAction(String action) {
        Fragment fragment = null;
        switch (action) {
            case "HomeFragment":
                fragment = new HomeFragment();
                AppController.getInstance().trackEvent("Patient Central", "Navigation", "Track event");

                break;

            case "ConsultationLogFragment":
                fragment = new ConsultationLogFragment();
                AppController.getInstance().trackEvent("Consultation Log", "Navigation", "Track event");

                break;

            case "PoHistoryFragment":
                fragment = new PoHistoryFragment();
                AppController.getInstance().trackEvent("Patient History", "Navigation", "Track event");

                break;

            case "ReportFragment":

                fragment = new ReportFragment();
                AppController.getInstance().trackEvent("Patient Report", "Navigation", "Track event");

                break;

            case "KnowledgeFragment":
                AppController.getInstance().trackEvent("Knowledge", "Navigation", "Track event");

                fragment = new KnowledgeFragment();
                break;
            case "IncompleteListFragment":
                AppController.getInstance().trackEvent("Prsecription", "Navigation", "Track event");

                fragment = new IncompleteListFragment();
                break;

            case "AssociatesFragment":
                AppController.getInstance().trackEvent("Associates", "Navigation", "Track event");

                fragment = new AssociatesFragment();
                break;


            default:
                fragment = new HomeFragment();

        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

}



