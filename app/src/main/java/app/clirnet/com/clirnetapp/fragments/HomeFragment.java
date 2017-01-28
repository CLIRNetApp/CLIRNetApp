package app.clirnet.com.clirnetapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.Utility.ImageDownloader;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.activity.AddPatientUpdate;
import app.clirnet.com.clirnetapp.activity.EditPatientUpdate;
import app.clirnet.com.clirnetapp.activity.LoginActivity;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.RegistrationActivityNew;
import app.clirnet.com.clirnetapp.activity.ServiceArea;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.RVAdapter;
import app.clirnet.com.clirnetapp.adapters.SearchViewdapter;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.LastNameAsynTask;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


@SuppressWarnings("ConstantConditions")
public class HomeFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    private OnFragmentInteractionListener mListener;



    private SearchView searchView;

    private SQLController sqlController;

    private RecyclerView recyclerView;
    private LinearLayout norecordtv;
    private ImageView backChangingImages;

    private RecyclerView Searchrecycler_view;
    private StringBuilder sysdate;

    private List<RegistrationModel> filteredModelList;


    private SearchViewdapter sva;
    private SQLiteHandler dbController;
    private SimpleDateFormat sdf1;
    private Date date1;
    private Toast toast;
    private ConnectionDetector connectionDetector;
    private ProgressDialog pDialog;
    private String patientInfoArayString;
    private String patientVisitHistorArayString;

    private ArrayList<RegistrationModel> patientIds_List;
    private ArrayList<RegistrationModel> getPatientVisitIdsList;


    private String doctor_membership_number;
    private final String mailId = "support@clirnet.com";
    private Button addaNewPatient;
    private String searchNumber = null;

    private int ival = 0;
    private int loadLimit = 15;

    private int PAGE_SIZE = 2;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private TextView todays_patient_updatetxt;
    private View view;
    private String savedUserName;
    private String savedUserPassword;
    private String asyn_value;
    private AppController appController;
    private Button sync;
    private LinearLayoutManager mLayoutManager;
    private int queryCount;
    private int queryCountforTotalPatient;
    private BannerClass bannerClass;
    private ArrayList<String> bannerimgNames;
    private String company_id;
    private ImageDownloader mDownloader;

    private FileOutputStream fos;  private  Bitmap bmp;
    private ProgressBar pb;

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private String username;
    private String docId;
    private String pateintrecordasync_start_time;
    private String banner_downloadstart_time;


    public HomeFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

            setRetainInstance(true);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((NavigationActivity) getActivity()).setActionBarTitle("Patient Central");
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);
        TextView date = (TextView) view.findViewById(R.id.date);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Searchrecycler_view = (RecyclerView) view.findViewById(R.id.Searchrecycler_view);

        mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        Searchrecycler_view.setLayoutManager(mLayoutManager);

        sync = (Button) view.findViewById(R.id.sync);
        Button export = (Button) view.findViewById(R.id.exp);
        addaNewPatient = (Button) view.findViewById(R.id.addanewPatient);
        todays_patient_updatetxt = (TextView) view.findViewById(R.id.todays_patient_updatetxt);


        if (filteredModelList != null) {
            filteredModelList.clear();
        }

       // getFlagStatus();
        checkLastLoginTime();

        appController = new AppController();
        bannerClass=new BannerClass(getContext());

        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        pDialog = new ProgressDialog(getContext());

        connectionDetector = new ConnectionDetector(getContext());
        dbController = new SQLiteHandler(getContext());
        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);

        patientIds_List = new ArrayList<>();
        getPatientVisitIdsList = new ArrayList<>();

        Glide.get(getContext()).clearMemory();

        TextView termsandCondition = (TextView) view.findViewById(R.id.termsandCondition);
        //open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);


            }
        });
        //open Terms and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TermsCondition.class);
                startActivity(intent);

            }
        });

        //open database for further interaction with database

        try {

            sqlController = new SQLController(getActivity().getApplicationContext());
            sqlController.open();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

            patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());

            patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());
            bannerimgNames= bannerClass.getImageName();
            company_id=sqlController.getCompany_id();
            //Log.e("current Time", "" + company_id);

        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment " + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getContext()).clearDiskCache();
            }
        }).start();

        sync.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    sync.setBackgroundResource(R.drawable.sync1);

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    sync.setBackgroundResource(R.drawable.sync2);
                }
                return false;
            }

        });
          //send the data to server when sync botton pressed
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqlController = new SQLController(getActivity());
                    sqlController.open();
                    patientIds_List = sqlController.getPatientIdsFalg0();
                    getPatientVisitIdsList = sqlController.getPatientVisitIdsFalg0();
                    System.out.println(""+patientIds_List.size());
                  //  Log.e("getPatientVisitIdsList", " getPatientVisitIdsList " + getPatientVisitIdsList.size() + " patientIds_List " + patientIds_List.size());
                    docId = sqlController.getDoctorId();


                    int patientIds_ListSize = patientIds_List.size();

                    int getPatientVisitIdsListSize = getPatientVisitIdsList.size();

                    if (patientIds_ListSize != 0 || getPatientVisitIdsListSize != 0) {

                        sendDataToServer();

                    } else {
                        //Log.e("nodata","no data to send");
                        //makeToast("No Data to Send");

                        showAnotherAlert("Data Sychronization", "                        No Data to Send ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (sqlController != null) {
                        sqlController.close();
                    }
                }

            }

        });
        //this code is used to export database to sd card .................
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDactabase();

            }

        });

        //redirects to add new patient page
        addaNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), RegistrationActivityNew.class);
                i.putExtra("number", searchNumber);
                startActivity(i);
            }
        });


        norecordtv = (LinearLayout) view.findViewById(R.id.norecordtv);
        Date todayDate = new Date();


        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM,yyyy");
        String dd = DATE_FORMAT.format(todayDate);
        date.setText("Today's Date: " + dd);


        // doctor_membership_number =sqlController.getDocMembershipIdNew().toString();


        //  Log.e("doctor_member", "" + doctor_membership_number);
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        sysdate = new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append("");


        SQLController sqlController1 = null;
        try {
            sqlController1 = new SQLController(getActivity());
            sqlController1.open();
            String formatedDate = appController.ConvertDateFormat(sysdate.toString());

            filteredModelList = sqlController1.getPatientList(formatedDate);

            if (filteredModelList.size() <= 0) {
                //do nothing
            } else {

                norecordtv.setVisibility(View.GONE);
                RVAdapter rvadapter = new RVAdapter(filteredModelList);
                recyclerView.setAdapter(rvadapter);
                rvadapter.notifyDataSetChanged();
            }


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        if (sqlController1 != null) {
            sqlController1.close();
        }

        //setting list to adapter class
        // sva = new SearchViewdapter(filteredModelList);
        //To check if there is data or not in arraylist in case of no internet connectivity or no data downloads
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {

                RegistrationModel book = filteredModelList.get(position);
                String visit_date = book.getVisit_date();

                try {
                    date1 = sdf1.parse(visit_date);

                    Date currentdate = sdf1.parse(String.valueOf(sysdate));

                    if (date1.after(currentdate) || date1.equals(currentdate)) {
                       // System.out.println("Date1 is after sysdate");


                        afterDateEditPatientUpdate(position);

                    }

                    if (date1.before(currentdate)) {

                        beforeDateAddPatientUpdate(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        getUsernamePasswordFromDatabase();

        callDataFromServer();
        setupAnimation();


        return view;
    }


    private void makeToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void getUsernamePasswordFromDatabase() {
        Cursor cursor = null;
        SQLController sqlController1 = null;
        try {

            sqlController1 = new SQLController(getActivity().getApplicationContext());
            sqlController1.open();

            ArrayList<LoginModel> al;
            al = sqlController1.getUserLoginRecrodsNew();
            if (al.size() != 0) {
                savedUserName = al.get(0).getUserName();
                savedUserPassword = al.get(0).getPassowrd();
            }

            asyn_value = sqlController.getAsyncvalue();
            if (asyn_value != null) {
                asyn_value.trim();
            }


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            if (sqlController1 != null) {
                sqlController1.close();
            }
        }
    }

    private void callDataFromServer() {

        CallAsynOnce cao = new CallAsynOnce();
        String asynTaskValue = cao.getValue();
        boolean isInternetPresent = connectionDetector.isConnectingToInternet();
        /*if (isInternetPresent) {
            String apiKey = getResources().getString(R.string.apikey);
            getBannersData(savedUserName, savedUserPassword, apiKey, doctor_membership_number, company_id);
        }*/


        //used to fetch patient data only once in lifetime of app from server
        if (asyn_value == null) {
            if (isInternetPresent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.equals(asynTaskValue, "1")) {

                        cao.setValue("2");

                        String apiKey = getResources().getString(R.string.apikey);
                        getBannersData(savedUserName, savedUserPassword, apiKey, doctor_membership_number, company_id);


                        new LastNameAsynTask(getContext(), savedUserName, savedUserPassword,appController.getDateTime());
                        getPatientRecords(savedUserName, savedUserPassword);

                    }
                }
            }
        }
        /*boolean chkStatus= getFirstTimeLoginStatus();
        if(chkStatus){
            showChangePassDialog();
        }*/
    }

    private void beforeDateAddPatientUpdate(int position) {

        RegistrationModel registrationModel = filteredModelList.get(position);


        //  System.out.println("Date1 is before or equal to Date2");
        Intent i = new Intent(getContext().getApplicationContext(), AddPatientUpdate.class);
        i.putExtra("PATIENTPHOTO", registrationModel.getPhoto());
        i.putExtra("PatientID", registrationModel.getPat_id());
        i.putExtra("NAME", registrationModel.getFirstName() + " " + registrationModel.getLastName());
        i.putExtra("FIRSTTNAME", registrationModel.getFirstName());
        i.putExtra("MIDDLENAME", registrationModel.getMiddleName());
        i.putExtra("LASTNAME", registrationModel.getLastName());
        i.putExtra("DOB", registrationModel.getDob());
        i.putExtra("PHONE", registrationModel.getMobileNumber());
        i.putExtra("AGE", registrationModel.getAge());
        i.putExtra("LANGUAGE", registrationModel.getLanguage());
        i.putExtra("GENDER", registrationModel.getGender());
        i.putExtra("FOD", registrationModel.getFollowUpDate());
        i.putExtra("AILMENT", registrationModel.getAilments());
        i.putExtra("FOLLOWDAYS", registrationModel.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", registrationModel.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", registrationModel.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", registrationModel.getClinicalNotes());
        i.putExtra("PRESCRIPTION", registrationModel.getPres_img());

        i.putExtra("ADDRESS", registrationModel.getAddress());
        i.putExtra("CITYORTOWN", registrationModel.getCityortown());
        i.putExtra("DISTRICT", registrationModel.getDistrict());
        i.putExtra("PIN", registrationModel.getPin_code());
        i.putExtra("STATE", registrationModel.getState());

        i.putExtra("ALTERNATENUMBER", registrationModel.getAlternatePhoneNumber());
        i.putExtra("ALTERNATENUMBERTYPE", registrationModel.getAlternatePhoneType());

        i.putExtra("ISDCODE", registrationModel.getIsd_code());
        i.putExtra("ALTERNATEISDCODE", registrationModel.getAlternate_isd_code());
        Log.e("odelM"," "+ registrationModel.getIsd_code()+"   "+registrationModel.getAlternate_isd_code());


        startActivity(i);
//                        Toast.makeText(getContext(), "Date1 is before sysdate", Toast.LENGTH_LONG).show();
    }

    private void afterDateEditPatientUpdate(int position) {
        RegistrationModel registrationModel = filteredModelList.get(position);
        Intent i = new Intent(getContext().getApplicationContext(), EditPatientUpdate.class);

        i.putExtra("PATIENTPHOTO", registrationModel.getPhoto());
        i.putExtra("ID", registrationModel.getPat_id());
        i.putExtra("NAME", registrationModel.getFirstName() + " " + registrationModel.getLastName());
        i.putExtra("FIRSTTNAME", registrationModel.getFirstName());
        i.putExtra("MIDDLENAME", registrationModel.getMiddleName());
        i.putExtra("LASTNAME", registrationModel.getLastName());
        i.putExtra("DOB", registrationModel.getDob());
        i.putExtra("PHONE", registrationModel.getMobileNumber());
        i.putExtra("PHONETYPE", registrationModel.getPhone_type());
        i.putExtra("AGE", registrationModel.getAge());
        i.putExtra("LANGUAGE", registrationModel.getLanguage());
        i.putExtra("GENDER", registrationModel.getGender());
        i.putExtra("ACTUALFOD", registrationModel.getActualFollowupDate());
        i.putExtra("FOD", registrationModel.getFollowUpDate());
        i.putExtra("AILMENT", registrationModel.getAilments());
        i.putExtra("FOLLOWDAYS", registrationModel.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", registrationModel.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", registrationModel.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", registrationModel.getClinicalNotes());
        i.putExtra("PRESCRIPTION", registrationModel.getPres_img());
        i.putExtra("VISITID", registrationModel.getKey_visit_id());

        i.putExtra("ADDRESS", registrationModel.getAddress());
        i.putExtra("CITYORTOWN", registrationModel.getCityortown());
        i.putExtra("DISTRICT", registrationModel.getDistrict());
        i.putExtra("PIN", registrationModel.getPin_code());
        i.putExtra("STATE", registrationModel.getState());
        i.putExtra("WEIGHT", registrationModel.getWeight());
        i.putExtra("PULSE", registrationModel.getPulse());
        i.putExtra("BP", registrationModel.getBp());
        i.putExtra("LOWBP", registrationModel.getlowBp());
        i.putExtra("TEMPRATURE", registrationModel.getTemprature());
        i.putExtra("SUGAR", registrationModel.getSugar());
        i.putExtra("SYMPTOMS", registrationModel.getSymptoms());
        i.putExtra("DIGNOSIS", registrationModel.getDignosis());
        i.putExtra("TESTS", registrationModel.getTests());
        i.putExtra("DRUGS", registrationModel.getDrugs());
        i.putExtra("BMI", registrationModel.getBmi());
        i.putExtra("ALTERNATENUMBER", registrationModel.getAlternatePhoneNumber());
        i.putExtra("ALTERNATENUMBERTYPE", registrationModel.getAlternatePhoneType());
        i.putExtra("HEIGHT", registrationModel.getHeight());
        i.putExtra("SUGARFASTING", registrationModel.getSugarFasting());
        i.putExtra("ISDCODE", registrationModel.getIsd_code());
        i.putExtra("ALTERNATEISDCODE", registrationModel.getAlternate_isd_code());

        startActivity(i);
    }

    //this is for our test purpose not actualy used in app
    private void exportDactabase() {

        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source;
        FileChannel destination;
        String currentDBPath = "/data/" + "app.clirnet.com.clirnetapp" + "/databases/" + SQLiteHandler.DATABASE_NAME;
        String backupDBPath = SQLiteHandler.DATABASE_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(getContext(), "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    //send data to server
    private void sendDataToServer() throws ClirNetAppException {


        boolean isInternetPresent = connectionDetector.isConnectingToInternet();

        if (isInternetPresent) {

            if (patientIds_List != null && !patientIds_List.isEmpty() || getPatientVisitIdsList != null && !getPatientVisitIdsList.isEmpty()) {
                // Log.e("senddata", "data is sending");
                sendDataToServer(patientInfoArayString, patientVisitHistorArayString,doctor_membership_number,docId,getPatientVisitIdsList.size(),patientIds_List.size());

            } else {
                makeToast("Server is already up to date");

            }

        } else {

            showAnotherAlert("No Internet", "                        No Internet Connectivity. ");
        }

    }


    /////////////Show Search Bar//////////////////

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        MenuItem item = menu.findItem(R.id.search);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Do whatever you want ON PRESSING BACK ARROW OF SEARCH VIEW

                if (filteredModelList.size() > 0) {
                    filteredModelList.clear();
                }
                Searchrecycler_view.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                addaNewPatient.setVisibility(View.GONE);
                return true;
            }
        });

        //  MenuItem item = menu.findItem(R.id.spinner);
        try {
            if (searchView == null)
                //no inspection Constant Conditions
            searchView = new SearchView(((NavigationActivity) getActivity()).getSupportActionBar().getThemedContext());
            searchView.setIconifiedByDefault(false);
            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only
            searchView.setQueryHint(getResources().getString(R.string.hint_search));

        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {/* Log.e("querty",""+ searchView.getQuery().toString());*/

                //System.out.println("on clicked: " + position);

                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String newText) {


                searchNumber = newText;

                try {

                    if (searchNumber.trim().length() > 10) {

                        Toast toast = Toast.makeText(getContext(), "Phone Number Should Be Only 10 Digits ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return true;
                    }

                    if (searchNumber.trim().length() > 4) {

                        norecordtv.setVisibility(View.GONE);
                        Searchrecycler_view.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        addaNewPatient.setVisibility(View.VISIBLE);
                        todays_patient_updatetxt.setVisibility(View.GONE);

                        ival = 0;
                        loadLimit = 15;

                        filteredModelList = sqlController.getPatientListForPhoneNumberFilter(searchNumber, ival, loadLimit);
                        //   filteredModelList = sqlController.getPatientListForPhoneNumberFilter(searchNumber);

                        queryCountforTotalPatient = sqlController.getCountResultforgetPatientListForPhoneNumberFilter();

                      //  Log.e("queryCount1", "  " + queryCountforTotalPatient + "  filteredModelList  " + filteredModelList.size()+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());

                        if (filteredModelList.size() <= 0) {

                            showCreatePatientAlertDialog(newText);

                        } else {

                            sva = new SearchViewdapter(filteredModelList, queryCountforTotalPatient);
                            sva.setFilter(filteredModelList);
                            Searchrecycler_view.setAdapter(sva);
                            Searchrecycler_view.setHasFixedSize(true);
                            Searchrecycler_view.addOnScrollListener(recyclerViewOnScrollListener);

                        }

                        searchView.clearFocus();

                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "Enter Min 5 Digits to Begin Search", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Search View " + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                /// System.out.println("on query submit: " + newText);


                return false;
            }
        });

        Searchrecycler_view.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), Searchrecycler_view, new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {

                // searchViewRvMethod();

                RegistrationModel book = filteredModelList.get(position);
                String visit_date = book.getVisit_date();

                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    Date date1 = sdf1.parse(visit_date);

                    Date currentdate = sdf1.parse(String.valueOf(sysdate));

                    if (date1.after(currentdate) || date1.equals(currentdate)) {

                        dateisCurrent(position);

                    }

                    if (date1.before(currentdate)) {

                        dateisBefore(position);
                        //  System.out.println("Date1 is before or equal to Date2");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }


            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        super.onCreateOptionsMenu(menu, inflater);
    }

    private void dateisCurrent(int position) {

        RegistrationModel registrationModel = filteredModelList.get(position);
        Intent i = new Intent(getActivity().getApplicationContext(), EditPatientUpdate.class);

        i.putExtra("PATIENTPHOTO", registrationModel.getPhoto());
        i.putExtra("ID", registrationModel.getPat_id());

        i.putExtra("NAME", registrationModel.getFirstName() + " " + registrationModel.getLastName());
        i.putExtra("FIRSTTNAME", registrationModel.getFirstName());
        i.putExtra("MIDDLENAME", registrationModel.getMiddleName());
        i.putExtra("LASTNAME", registrationModel.getLastName());
        i.putExtra("DOB", registrationModel.getDob());

        i.putExtra("PHONE", registrationModel.getMobileNumber());

        i.putExtra("AGE", registrationModel.getAge());
        i.putExtra("LANGUAGE", registrationModel.getLanguage());
        i.putExtra("GENDER", registrationModel.getGender());
        i.putExtra("FOD", registrationModel.getFollowUpDate());

        i.putExtra("ACTUALFOD", registrationModel.getActualFollowupDate());

        i.putExtra("AILMENT", registrationModel.getAilments());
        i.putExtra("FOLLOWDAYS", registrationModel.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", registrationModel.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", registrationModel.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", registrationModel.getClinicalNotes());
        i.putExtra("PRESCRIPTION", registrationModel.getPres_img());
        i.putExtra("VISITID", registrationModel.getKey_visit_id());

        i.putExtra("ADDRESS", registrationModel.getAddress());
        i.putExtra("CITYORTOWN", registrationModel.getCityortown());
        i.putExtra("DISTRICT", registrationModel.getDistrict());
        i.putExtra("PIN", registrationModel.getPin_code());
        i.putExtra("STATE", registrationModel.getState());
        i.putExtra("WEIGHT", registrationModel.getWeight());
        i.putExtra("PULSE", registrationModel.getPulse());
        i.putExtra("BP", registrationModel.getBp());
        i.putExtra("LOWBP", registrationModel.getlowBp());
        i.putExtra("TEMPRATURE", registrationModel.getTemprature());
        i.putExtra("SUGAR", registrationModel.getSugar());
        i.putExtra("SYMPTOMS", registrationModel.getSymptoms());
        i.putExtra("DIGNOSIS", registrationModel.getDignosis());
        i.putExtra("TESTS", registrationModel.getTests());
        i.putExtra("DRUGS", registrationModel.getDrugs());
        i.putExtra("BMI", registrationModel.getBmi());
        i.putExtra("ALTERNATENUMBER", registrationModel.getAlternatePhoneNumber());
        i.putExtra("ALTERNATENUMBERTYPE", registrationModel.getAlternatePhoneType());
        i.putExtra("HEIGHT", registrationModel.getHeight());
        i.putExtra("SUGARFASTING", registrationModel.getSugarFasting());
        i.putExtra("ISDCODE", registrationModel.getIsd_code());
        i.putExtra("ALTERNATEISDCODE", registrationModel.getAlternate_isd_code());
        startActivity(i);
    }

    private void dateisBefore(int position) {

        RegistrationModel registrationModel = filteredModelList.get(position);

        Intent i = new Intent(getContext(), AddPatientUpdate.class);
        i.putExtra("PATIENTPHOTO", registrationModel.getPhoto());
        i.putExtra("PatientID", registrationModel.getPat_id());

        i.putExtra("NAME", registrationModel.getFirstName() + " " + registrationModel.getLastName());
        i.putExtra("FIRSTTNAME", registrationModel.getFirstName());
        i.putExtra("MIDDLENAME", registrationModel.getMiddleName());
        i.putExtra("LASTNAME", registrationModel.getLastName());
        i.putExtra("DOB", registrationModel.getDob());

        i.putExtra("PHONE", registrationModel.getMobileNumber());

        i.putExtra("AGE", registrationModel.getAge());
        i.putExtra("LANGUAGE", registrationModel.getLanguage());
        i.putExtra("GENDER", registrationModel.getGender());
        i.putExtra("FOD", registrationModel.getFollowUpDate());
        i.putExtra("AILMENT", registrationModel.getAilments());
        i.putExtra("FOLLOWDAYS", registrationModel.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", registrationModel.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", registrationModel.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", registrationModel.getClinicalNotes());
        i.putExtra("PRESCRIPTION", registrationModel.getPres_img());

        i.putExtra("ADDRESS", registrationModel.getAddress());
        i.putExtra("CITYORTOWN", registrationModel.getCityortown());
        i.putExtra("DISTRICT", registrationModel.getDistrict());
        i.putExtra("PIN", registrationModel.getPin_code());
        i.putExtra("STATE", registrationModel.getState());

        i.putExtra("ALTERNATENUMBER", registrationModel.getAlternatePhoneNumber());
        i.putExtra("ALTERNATENUMBERTYPE", registrationModel.getAlternatePhoneType());

        i.putExtra("ISDCODE", registrationModel.getIsd_code());
        i.putExtra("ALTERNATEISDCODE", registrationModel.getAlternate_isd_code());
        Log.e("odelM", " " + registrationModel.getIsd_code() + "   " + registrationModel.getAlternate_isd_code());

        startActivity(i);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private void showCreatePatientAlertDialog(final String number) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_alert_profile);


        dialog.setTitle("Please Confirm");
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
                searchView.clearFocus();

                Intent i = new Intent(getActivity().getApplicationContext(), RegistrationActivityNew.class);
                i.putExtra("number", number);
                startActivity(i);
                getActivity().finish();

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    //this will used to change banner image after some time interval
    private void setupAnimation()  {

        if(bannerimgNames.size()>0) {
            Random r = new Random();
            int n = r.nextInt(bannerimgNames.size());

            // final String url = getString(imageArray[n]);
            //  backChangingImages.setImageResource(imageArray[n]);
            final String url = bannerimgNames.get(n);
            //  Log.e("nUrl", "" + n + "" + url);

            BitmapDrawable d = new BitmapDrawable(getResources(), "sdcard/BannerImages/" + url + ".png"); // path is ur resultant //image

            //Log.e("BitmapDrawable", "" + d);
            backChangingImages.setImageDrawable(d);

            backChangingImages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Image Clicked" + url, Toast.LENGTH_SHORT).show();

                    String action = "clicked";

                    appController.showAdDialog(getContext(), url);
                    appController.saveBannerDataIntoDb(url, getContext(), doctor_membership_number, action);
                }
            });
            String action = "display";
            appController.saveBannerDataIntoDb(url, getContext(), doctor_membership_number, action);
        }

    }

    //class to implement OnClick Listner
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private final GestureDetector gestureDetector;

        private final ItemClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            try {
                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                    clickListener.onClick(child, rv.getChildPosition(child));
                }
            } catch (Exception exe) {

                exe.printStackTrace();

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            case R.id.help:
                String docName = null;
                String phoneNumber = null;
                try {
                    docName = sqlController.getDocdoctorName();

                    phoneNumber = sqlController.getPhoneNumber();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (sqlController != null) {
                        sqlController.close();
                    }
                    dbController.close();
                }
                // showAlertDialog();
                //used to send mail
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final String finalDocName = docName;
                final String finalPhoneNumber = phoneNumber;
                builder.setMessage("Send Emai!")
                        .setCancelable(true)
                        .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{mailId});

                                email.putExtra(Intent.EXTRA_SUBJECT, "Help Me");
                                // email.putExtra(Intent.EXTRA_TEXT, "Doctor Id: "+doctor_membership_number+"\n"+"Name :"+"Dr.PushpalMukherjee"+" \n"+"Phone Number : "+"1234567890");
                                email.putExtra(Intent.EXTRA_TEXT, "Doctor Id: " + doctor_membership_number + "\n" + "Name :" + finalDocName + " \n" + "Phone Number : " + finalPhoneNumber);
                                //need this to prompts email client only
                                email.setType("message/rfc822");

                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();


                break;

            case R.id.refresh:
                makeToast("Under Construction");
                break;

            case R.id.about:

                showCreatePatientAlertDialog();

                break;

            case R.id.tool:

                createDialog();

                break;

            case android.R.id.home:

                break;

        }
        return true;

    }


    private void createDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.askfor_password_dialog);


        dialog.setTitle("Authenticate Yourself");
        //  dialog.setCancelable(false);
        TextView btnSubmitPass = (TextView) dialog.findViewById(R.id.submit);


        final EditText password = (EditText) dialog.findViewById(R.id.password);


        btnSubmitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String newPass = password.getText().toString().trim();


                if (TextUtils.isEmpty(newPass)) {
                    password.setError("Please enter Password !");
                    return;
                }

                if (newPass.equals("clirnet123")) {
                    Intent intent = new Intent(getContext(), ServiceArea.class);
                    intent.putExtra("username", savedUserName);
                    intent.putExtra("password", savedUserPassword);
                    startActivity(intent);
                }

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



    //sync data to server
    private void sendDataToServer(final String patient_details, final String patient_visits ,final String docMemId,final String docId, final int patient_visits_count, final int patient_details_count  ) {

        String tag_string_req = "req_login";


        pDialog.setMessage("Syncing Data");
        pDialog.setCancelable(false);



        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SYCHRONISED_TOSERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);


                    JSONObject user = jObj.getJSONObject("data");

                    String msg = user.getString("msg");


                    if (msg.equals("OK")) {

                        //  Log.e("ashish", "" + patientIds_List.size());
                       int patientIds_Listsize = patientIds_List.size();
                        for (int i = 0; i < patientIds_Listsize; i++) {
                            String patientId = patientIds_List.get(i).getPat_id();
                            String flag = "1";

                            dbController.FlagupdatePatientPersonal(patientId, flag);
                        }

                      int  patientVisitIdsList = getPatientVisitIdsList.size();
                        for (int i = 0; i < patientVisitIdsList; i++) {
                            // String patientId = getPatientVisitIdsList.get(i).getPat_id();
                            String patientVisitId = getPatientVisitIdsList.get(i).getKey_visit_id();
                            String flag = "1";

                            dbController.FlagupdatePatientVisit(patientVisitId, flag);
                        }

                        String time = appController.getDateTimenew();
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Activity data is sync to server : patient Visit Count : " + patientVisitIdsList + " patient Count : " + patientIds_List);
                    //    Log.e("senddata", "data is sent " + time);
                        lastSyncTime(time);
                        toast = Toast.makeText(getContext().getApplicationContext(), "Data Send Successfully to Server", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                    Toast.makeText(getContext().getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Error while sending data to server " + error+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());

                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                String keyid = getResources().getString(R.string.apikey);
                Map<String, String> params = new HashMap<>();
                params.put("apikey", keyid);
                params.put("patient_details", patient_details);
                params.put("patient_visits", patient_visits);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
                params.put("patient_visits_count", String.valueOf(patient_visits_count));
                params.put("patient_details_count", String.valueOf(patient_details_count));
                System.out.println("patient_details_count" + String.valueOf(patient_details_count));
                System.out.println("patient_visits_count" + String.valueOf(patient_visits_count));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    //this will get Patient Records from server

    private void getPatientRecords(final String savedUserName, final String savedUserPassword) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        pDialog.setMessage("Initializing Application.Getting patient records, Please Wait...");
        pDialog.setCancelable(false);
        showDialog();
        pateintrecordasync_start_time=appController.getDateTimenew();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PATIENT_RECORDS, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
              //  Log.d(TAG, "Sync Response: " + response);
              //  Log.e("responseresponse", "" + response);

                new getPatientRecordsFromServer().execute(response);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            //    Log.e(TAG, "Login Error: Failed To Initalize Data" + error.getMessage()+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                Toast.makeText(getContext(),
                        "Failed To Initalize Data" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("username", savedUserName);
                params.put("password", savedUserPassword);
                params.put("apikey", getResources().getString(R.string.apikey));
                // Log.e("apikey",""+savedUserName + "  "+savedUserPassword + " "+ getResources().getString(R.string.apikey));
                return params;
            }

        };

        //this will retry the request for 2 times
        int socketTimeout = 30000;//30 seconds - change to what you want
        int retryforTimes = 2;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        AppController.getInstance().setPriority(Request.Priority.HIGH);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
//This method will get patient personal details from server and stores into db

    private void setPatientPersonalList(JSONArray jsonArray) throws JSONException {


        List<RegistrationModel> inputPatientData = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonProsolveObject = jsonArray.getJSONObject(i);

            String pat_id = jsonProsolveObject.getString("pat_id");
            String flag = "1";

            String doctor_id = jsonProsolveObject.getString("doctor_id");
            String doc_membership_id = jsonProsolveObject.getString("doc_membership_id");

            String patient_info_type_form = jsonProsolveObject.getString("patient_info_type_form");
            String pat_first_name = jsonProsolveObject.getString("pat_first_name");
            String pat_middle_name = jsonProsolveObject.getString("pat_middle_name");
            String pat_last_name = jsonProsolveObject.getString("pat_last_name");
            String pat_gender = jsonProsolveObject.getString("pat_gender");
            String pat_date_of_birth = jsonProsolveObject.getString("pat_date_of_birth");
            String pat_age = jsonProsolveObject.getString("pat_age");
            String pat_mobile_no = jsonProsolveObject.getString("pat_mobile_no");
            String pat_address = jsonProsolveObject.getString("pat_address");
            String pat_city_town = jsonProsolveObject.getString("pat_city_town");
            String pat_pincode = jsonProsolveObject.getString("pat_pincode");
            String pat_district = jsonProsolveObject.getString("pat_district");
            String pref_lang = jsonProsolveObject.getString("pref_lang");
            String photo_name = jsonProsolveObject.getString("photo_name");
            String consent = jsonProsolveObject.getString("consent");
            String special_instruction = jsonProsolveObject.getString("special_instruction");
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

            String convertedDate = null;
            String convertedTime = null;
            String converteddobDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dobsdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date formatDate = sdf.parse(added_on);
                Date formatDateOB = dobsdf.parse(pat_date_of_birth);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
                convertedDate = sd1.format(formatDate);
                converteddobDate = sd1.format(formatDateOB);

                SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
                convertedTime = sd2.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(" Home Fragment" + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

            dbController.addPatientPersoanlRecords(pat_id, doctor_id, doc_membership_id, patient_info_type_form, pat_first_name, pat_middle_name, pat_last_name,
                    pat_gender, converteddobDate, pat_age, pat_mobile_no, pat_address, pat_city_town, pat_pincode, pat_district, pref_lang, photo_name, consent,
                    special_instruction, added_by, convertedDate, convertedTime, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on, flag);

            inputPatientData.add(new RegistrationModel(pat_id, doctor_id, doc_membership_id, patient_info_type_form, pat_first_name, pat_middle_name, pat_last_name,
                    pat_gender, converteddobDate, pat_age, pat_mobile_no, pat_address, pat_city_town, pat_pincode, pat_district, pref_lang, photo_name, consent,
                    special_instruction, added_by, added_on, convertedDate, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on, flag));

        }

        SessionManager session = new SessionManager(getContext().getApplicationContext());
        session.setLogin(true);

    }


    //This method will get patient History details from server and stores into db
    private void setPatientHistoryList(JSONArray patientHistoryList) throws JSONException {
        String flag = "1";

        for (int i = 0; i < patientHistoryList.length(); i++) {

            JSONObject jsonPatientHistoryObject = patientHistoryList.getJSONObject(i);

            String visit_id = jsonPatientHistoryObject.getString("visit_id");
            String pat_id = jsonPatientHistoryObject.getString("pat_id");

            String ailment = jsonPatientHistoryObject.getString("ailment");

            String visit_date = jsonPatientHistoryObject.getString("visit_date");


            String follow_up_date = jsonPatientHistoryObject.getString("follow_up_date");

            String follow_up_days = jsonPatientHistoryObject.getString("follow_up_days");

            String follow_up_weeks = jsonPatientHistoryObject.getString("follow_up_weeks");

            String follow_up_months = jsonPatientHistoryObject.getString("follow_up_months");
            String act_follow_up_date = jsonPatientHistoryObject.getString("act_follow_up_date");

            String notes = jsonPatientHistoryObject.getString("notes");
            String weight = jsonPatientHistoryObject.getString("weight");
            String pulse = jsonPatientHistoryObject.getString("pulse");
            String bp_high = jsonPatientHistoryObject.getString("bp_high");
            String bp_low = jsonPatientHistoryObject.getString("bp_low");
            String temp = jsonPatientHistoryObject.getString("temperature");
            String sugar = jsonPatientHistoryObject.getString("sugar");
            String drugs = jsonPatientHistoryObject.getString("drugs");
            String tests = jsonPatientHistoryObject.getString("tests");
            String dihnosis = jsonPatientHistoryObject.getString("diagnosis");
            String symptoms = jsonPatientHistoryObject.getString("symptoms");
            String prescription = jsonPatientHistoryObject.getString("prescription");

            String added_by = jsonPatientHistoryObject.getString("added_by");
            String added_on = jsonPatientHistoryObject.getString("added_on");

            String modified_by = jsonPatientHistoryObject.getString("modified_by");
            String modified_on = jsonPatientHistoryObject.getString("modified_on");

            String is_disabled = jsonPatientHistoryObject.getString("is_disabled");

            String disabled_by = jsonPatientHistoryObject.getString("disabled_by");

            String disabled_on = jsonPatientHistoryObject.getString("disabled_on");
            String is_deleted = jsonPatientHistoryObject.getString("is_deleted");

            String deleted_by = jsonPatientHistoryObject.getString("deleted_by");
            String deleted_on = jsonPatientHistoryObject.getString("deleted_on");

            String convertedActualfodDate = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date formatDate = sdf.parse(act_follow_up_date);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
                convertedActualfodDate = sd1.format(formatDate);


            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + " Home Fragment " + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            String convertedAddedonDate = null;
            String convertedAddedonTime = null;

            try {
                Date formatDate = sdf.parse(added_on);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
                convertedAddedonDate = sd1.format(formatDate);

                SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
                convertedAddedonTime = sd2.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

            //This will convert visit date format into dd-MM-yyyy format 17-9-2016

            String convertedVisitDate = null;
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date formatDate = sdf1.parse(visit_date);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
                convertedVisitDate = sd1.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
            }


            String patient_info_type_form = "Electronics"; //need to add this if records are added from web service to identify the data...........

            dbController.addPatientHistoryRecords(visit_id, pat_id, ailment, convertedVisitDate, follow_up_date, follow_up_days,
                    follow_up_weeks, follow_up_months, convertedActualfodDate, notes, added_by, convertedAddedonDate, convertedAddedonTime, modified_by, modified_on, is_disabled, disabled_by, disabled_on,
                    is_deleted, deleted_by, deleted_on, flag, patient_info_type_form, prescription, weight, pulse, bp_high, bp_low, temp, sugar, symptoms, dihnosis, tests, drugs);

        }

        dbController.addAsync();
        String end_time=appController.getDateTimenew();

        dbController.addAsynctascRun_status("getPatientRecords",pateintrecordasync_start_time,end_time,"");//last parameter is update on which is blank right now

        Intent i = new Intent(getContext(), NavigationActivity.class);
        startActivity(i);
        //makeToast("Application Initialization Successful");

    }

    //custom dialog  for the sync button result
    private void showAnotherAlert(String title, String message) {
        Effectstype effect;
        effect = Effectstype.Fadein;
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(getContext());
        dialogBuilder.setCancelable(false);

        dialogBuilder
                .withTitle(title)                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#26ae90")                              //def
                .withMessage("             " + message)                     //.withMessage(null)  no Msg  "      " is for the set the mesage to center of dialog
                .withMessageColor("#FFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#0a599d")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.info))
                .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(effect)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")


                        //.withButtonDrawable(Integer.parseInt("#FFFFFF"))
                .setCustomView(R.layout.custom_dialog_view, getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                        dialogBuilder.dismiss();
                    }
                })

                .show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        if (sqlController != null) {
            sqlController.close();
            sqlController = null;
        }
        if (connectionDetector != null) {
            connectionDetector = null;
        }
        if (dbController != null) {
            dbController = null;
        }
        if (searchView != null) {
            searchView.clearFocus();
            searchView = null;
        }
        if (appController != null) {
            appController = null;
        }
        if (pDialog != null) {
            pDialog = null;
        }
        if (addaNewPatient != null) {
            addaNewPatient.setOnClickListener(null);
        }
        if(bannerClass != null){
            bannerClass= null;
        }
        bannerimgNames=null;

        //recyclerView.setOnClickListener(null);
        //  searchView.setOnClickListener(null);
        Searchrecycler_view.addOnScrollListener(null);
        norecordtv = null;
        addaNewPatient = null;
        view = null; // now cleaning up view!
        date1 = null;
        toast = null;
        asyn_value = null;//
        searchNumber = null;
        patientVisitHistorArayString = null;
        Searchrecycler_view = null;
        recyclerView = null;
        savedUserName = null;
        savedUserPassword = null;
        patientInfoArayString = null;
        doctor_membership_number=null;
        pateintrecordasync_start_time=null;
        banner_downloadstart_time=null;
        backChangingImages=null;
      //  Log.e("onDetach", "onDetach Home Fragment");

    }

    @Override
    public void onPause() {
       // Log.e("DEBUG", "OnPause of HomeFragment");

        super.onPause();
    }


    private void showCreatePatientAlertDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.about_app_dialog);

        String aboutApp = getResources().getString(R.string.aboutApp);
        dialog.setTitle(aboutApp);
        //  dialog.setCancelable(false);


        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);

        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;

    }

    private void checkLastLoginTime() {

        SharedPreferences pref = getContext().getSharedPreferences("savedCredit", getContext().MODE_PRIVATE);

        String loginTime = pref.getString("loginTime", null);

        int hrs = AppController.hoursAgo(loginTime);
    //    Log.e("loginTime", "" + loginTime + " hours " + hrs);

        if (hrs >= 8) {
            Intent i = new Intent(getContext().getApplicationContext(), LoginActivity.class);
            startActivity(i);
            System.gc();
        }

    }

    public void lastSyncTime(String lastSyncTime) {

        getContext().getSharedPreferences("SyncFlag", getContext().MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }

    private class getPatientRecordsFromServer extends AsyncTask<String, Void, String> {

        ProgressDialog pd;

        @Override
        protected String doInBackground(String... params) {


            try {

                JSONObject jObj = new JSONObject(params[0]);


                JSONObject user = jObj.getJSONObject("data");

                JSONArray jsonArray = user.getJSONArray("doctor_patient_relation");

                JSONArray patientHistoryList = user.getJSONArray("patient_visit_details");//for live api

                setPatientPersonalList(jsonArray);
                setPatientHistoryList(patientHistoryList);


            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e +" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                //Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
           /* new LastNameAsynTask(getContext(),savedUserName, savedUserPassword);*/
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           // Log.e("onPostExecute", "onPostExecute");
            //  pDialog.dismiss();
           // hideDialog();

           // makeToast("Application Initialization Successful");
        }

        @Override
        protected void onPreExecute() {
           // Log.e("onPreExecute", "onPreExecute");
              pd = new ProgressDialog(getContext());
              pd.setMessage("Initializing Application. Please Wait...2");
              pDialog.show();
              showDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
           // Log.e("visibleItemCount", "" + visibleItemCount + " totalItemCount  " + totalItemCount + " firstVisibleItemPosition " + firstVisibleItemPosition);

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {

                    isLoading = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDataForAdapter();
                        }
                    }, 2000);
                }
            }
        }
    };

    private void loadDataForAdapter() {//loading data on run time

        isLoading = false;
        ival = ival + 15;

        List<RegistrationModel> memberList = new ArrayList<>();

        int index = sva.getItemCount() - 1;
        int end = index + PAGE_SIZE;
      //  Log.e("index", "" + index + " " + end + " size is " + queryCount);

        if (end <= queryCountforTotalPatient) {
            try {
                memberList = sqlController.getPatientListForPhoneNumberFilter(searchNumber, ival, loadLimit);
            } catch (ClirNetAppException e) {

                e.printStackTrace();
            }

            //  patientData.addAll(memberList);
            // adapter.notifyDataSetChanged();
            sva.addAll(memberList);
            if (end >= queryCount) {
                sva.setLoading(false);

            }

        }
    }
    private void getBannersData(final String savedUserName, final String savedUserPassword, final String apiKey, final String docMemId, final String companyid) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        pDialog.setMessage("Initializing Application.Getting Banner data Please Wait...");
        pDialog.setCancelable(false);
        showDialog();
         banner_downloadstart_time=appController.getDateTimenew();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BANNER_API, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
              //  Log.d(TAG, "Sync Response: " + response);
               // Log.e("getBannersData", "" + response);
                // new getPatientRecordsFromServer().execute(response);
                hideDialog();
                pDialog.dismiss();

                new getBannerImagesFromServer().execute(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
              //  Log.e(TAG, "Login Error: Failed To Initalize Data" + error.getMessage()+""+Thread.currentThread().getStackTrace()[2].getLineNumber());
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment getting banners" + error);
                 hideDialog();
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
        AppController.getInstance().setPriority(Request.Priority.LOW);
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
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e +""+Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
           /* new LastNameAsynTask(getContext(),savedUserName, savedUserPassword);*/
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
          //  Log.e("onPostExecute", "onPostExecute");
             pd.dismiss();
             hideDialog();
            //makeToast("Application Initialization Successful");

        }
        @Override
        protected void onPreExecute() {
          //  Log.e("onPreExecute", "onPreExecute");
            pd = new ProgressDialog(getContext());
            pd.setMessage("Initializing Application. Downloading Images,Please Wait...2");
            pd.show();
          //  showDialog();
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
            String folder_name=jsonProsolveObject.getString("folder_name");
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

            if(checkifImageExists(banner_id))
            {
                File file = getImage("/"+banner_image_name+".png");
                String path = file.getAbsolutePath();
                if (path != null){

                    // Log.e("imageExist","imageExist allready");
                }
            } else {
                // Log.e("imageExist","imageExist not");
                downloadImage(banner_image_url, banner_image_name);
            }
            if(checkifImageExists(product_imagenm))
            {
                File file = getImage("/"+product_imagenm+".png");
                String path = file.getAbsolutePath();
                if (path != null){

                    //  Log.e("imageExist","imageExist allready");
                }
            } else {
                // Log.e("imageExist","imageExist not");
                downloadImage(product_image_url, product_imagenm);
            }

            //saveImageToSD(banner_image1);
            String img_download_status="inprogress";
            String download_initialization_time=appController.getDateTimenew();


            bannerClass.addBannerData(banner_id, company_id, brand_name, type, banner_image_url, speciality_name,
                    product_image_url, generic_name, manufactured_by, marketed_by, group_name, link_to_page, call_me, meet_me,
                    priority, status, start_time, end_time,
                    clinical_trial_source, clinical_trial_identifier, clinical_trial_link, clinical_sponsor, drug_composition, drug_dosing_durability, added_by, added_on, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on, banner_image_name, banner_type, product_imagenm, folder_name, status_name, img_download_status, download_initialization_time);

        }
        String processend_time=appController.getDateTimenew();
        dbController.addAsynctascRun_status("Banner Downloads",banner_downloadstart_time,processend_time,"");
    }

    private void downloadImage(final String banner_image_url, final String banner_image1) {
          /*--- check whether there is some Text entered ---*/
        if (banner_image_url.trim().length() > 0) {
            /*--- instantiate our downloader passing it required components ---*/
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mDownloader = new ImageDownloader(banner_image_url
                            .trim(), banner_image1.trim(), pb, getContext(), bmp, new ImageDownloader.ImageLoaderListener() {
                        @Override
                        public void onImageDownloaded(Bitmap bmp) {
                          bmp = bmp;
                    /*--- here we assign the value of bmp field in our Loader class
                     * to the bmp field of the current class ---*/
                        }
                    });
                    mDownloader.execute();
                    String img_download_status="downloading";
                 //   String download_start_time=appController.getDateTimenew();

                   // bannerClass.updateBannerImgDownloadStatus(banner_image1, banner_image_url, img_download_status,download_start_time);

                    String download_start_time=appController.getDateTimenew();
                    bannerClass.updateBannerImgDownloadStatus(banner_image1, banner_image_url, img_download_status,download_start_time);
                }

            });
        }
    }
    public static boolean checkifImageExists(String imagename)
    {
        Bitmap b = null ;
        File file = getImage("/"+imagename+".png");
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

            mediaImage = new File("sdcard/BannerImages/"+imagename);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return mediaImage;
    }

}








