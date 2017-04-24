package app.clirnet.com.clirnetapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

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

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.AddPatientUpdate;
import app.clirnet.com.clirnetapp.activity.EditPatientUpdate;
import app.clirnet.com.clirnetapp.activity.LoginActivity;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.RegistrationActivityNew;
import app.clirnet.com.clirnetapp.activity.ServiceArea;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.IncompleteRecordsAdapter;
import app.clirnet.com.clirnetapp.adapters.RVAdapter;
import app.clirnet.com.clirnetapp.adapters.SearchViewdapter;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.CallDataFromOne;
import app.clirnet.com.clirnetapp.app.GetBannerImageTask;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.quickAdd.EditIncompleteRecordsActivity;
import app.clirnet.com.clirnetapp.quickAdd.QuickAddActivity;
import app.clirnet.com.clirnetapp.utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.utility.ImageDownloader;
import app.clirnet.com.clirnetapp.utility.ItemClickListener;
import app.clirnet.com.clirnetapp.utility.SyncDataService;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;


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
    private ArrayList<RegistrationModel> getAssociateMasterList;

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
    private int queryCountforTotalPatient;
    private BannerClass bannerClass;
    private ArrayList<String> bannerimgNames;
    private String company_id;
    private ImageDownloader mDownloader;

    private String docId;
    private String pateintrecordasync_start_time;
    private String send_data_start_time;
    private RecyclerView incomplete_recordsRecyclerview;
    private ArrayList<RegistrationModel> incompleteRecordList;
    private TextView incompletedisplay_tv;
    private String pat_personal_count;
    private String pat_visit_count;
    private LinearLayout nameheader;
    Button fab;



    public HomeFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            this.setRetainInstance(true);

            String service = getArguments().getString("SERVICE");
            String message = getArguments().getString("MESSAGE");
            String header = getArguments().getString("HEADER");
            if (service.equals("START")) {
                startService();
            } else if (service.equals("3")) {
                showNoDataToSendAlert(header, message);
            }
        }

       // setRetainInstance(true);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        ((NavigationActivity) getActivity()).setActionBarTitle("Patient Central");
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);
        TextView date = (TextView) view.findViewById(R.id.date);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Searchrecycler_view = (RecyclerView) view.findViewById(R.id.Searchrecycler_view);
        incomplete_recordsRecyclerview = (RecyclerView) view.findViewById(R.id.incomplete_recordsRecyclerview);

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
        bannerClass = new BannerClass(getContext());

        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        pDialog = new ProgressDialog(getContext());

        connectionDetector = new ConnectionDetector(getContext());
        dbController = SQLiteHandler.getInstance(getContext());
        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);

        patientIds_List = new ArrayList<>();
        getPatientVisitIdsList = new ArrayList<>();
        getAssociateMasterList = new ArrayList<>();


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

        fab = (Button) view.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    fab.setBackgroundResource(R.drawable.quickaddpressed);
                    //Calling quick add
                    Intent intent = new Intent(getActivity().getApplicationContext(), QuickAddActivity.class);
                    startActivity(intent);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    fab.setBackgroundResource(R.drawable.quickadd);
                }
                return false;
            }

        });

        try {
            sqlController = new SQLController(getActivity().getApplicationContext());
            sqlController.open();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            docId = sqlController.getDoctorId();
            //bannerimgNames = bannerClass.getImageName();
            company_id = sqlController.getCompany_id();


        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }
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
                    boolean isInternetPresent = connectionDetector.isConnectingToInternet();

                    if (isInternetPresent) {
                        String process_start_time = new AppController().getDateTimenew();
                        sqlController = new SQLController(getActivity());
                        sqlController.open();
                        patientIds_List = sqlController.getPatientIdsFalg0();
                        getPatientVisitIdsList = sqlController.getPatientVisitIdsFalg0();
                        getAssociateMasterList = sqlController.getAssociateMasterFalg0();

                        patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());
                        //   Log.e("patientInfoArayString"," "+patientInfoArayString);

                        patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());

                        // Log.e("patientVisitHisto"," "+patientVisitHistorArayString);

                        pat_personal_count = appController.getCharFreq(patientInfoArayString);

                        pat_visit_count = appController.getCharFreq(patientVisitHistorArayString);

                        /*int patientIds_ListSize = patientIds_List.size();

                        int getPatientVisitIdsListSize = getPatientVisitIdsList.size();

                        ArrayList<String> bannerDisplayIds_List = sqlController.getBannerDisplaysFalg0();
                        ArrayList<String> bannaerClickedIdsList = sqlController.getBannerClickedFalg0();
                        String bannerDisplayArayString = String.valueOf(dbController.getResultsForBannerDisplay());
                        String bannerClickedArayString = String.valueOf(dbController.getResultsForBannerClicked());

                        String associateMasterDataList = String.valueOf(dbController.getAssociateMasterDataDisplay());*/
                        //Log.e("getAssociateMaster", "" + associateMasterDataList);

                        new CallDataFromOne(getContext(), savedUserName, savedUserPassword, process_start_time, docId, doctor_membership_number);

                        /*if (bannaerClickedIdsList.size() > 0 || bannerDisplayIds_List.size() > 0) {
                            new UploadBannerDataAsyncTask(savedUserName, savedUserPassword, getContext(), bannerDisplayArayString, bannerClickedArayString, doctor_membership_number, docId, bannerDisplayIds_List, bannaerClickedIdsList, new AppController().getDateTimenew());
                        }//
                        if (patientIds_ListSize != 0 || getPatientVisitIdsListSize != 0) {
                            appController.appendLog(appController.getDateTime() + " " + " / " + "Sending Data to server from home " + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                            //  sendDataToServer(patientInfoArayString,patientVisitHistorArayString,doctor_membership_number,docId,patientIds_ListSize,getPatientVisitIdsListSize,savedUserName);
                            sendDataToServer(patientInfoArayString, patientVisitHistorArayString, associateMasterDataList, doctor_membership_number, docId, getPatientVisitIdsList.size(), patientIds_List.size(), savedUserName, process_start_time);

                        } else {

                            showNoDataToSendAlert("Data Sychronization", "Data Already Synced.");
                        }*/

                    } else {
                        showNoInternetAlert("No Internet", " No Internet Connectivity.");
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


        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        String dd = DATE_FORMAT.format(todayDate);
        date.setText("Today's Date: " + dd);

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
            incompleteRecordList = sqlController1.getIncompleteRecordList(formatedDate);


            if (filteredModelList.size() > 0) {
                //do nothing
                norecordtv.setVisibility(View.GONE);
                RVAdapter rvadapter = new RVAdapter(filteredModelList);
                recyclerView.setAdapter(rvadapter);
                rvadapter.notifyDataSetChanged();
            }
            if (incompleteRecordList.size() > 0) {
                incompletedisplay_tv = (TextView) view.findViewById(R.id.incompletedisplay_tv);
                incompletedisplay_tv.setVisibility(View.VISIBLE);
                nameheader = (LinearLayout) view.findViewById(R.id.nameheader);
                nameheader.setVisibility(View.VISIBLE);
                View view1 = view.findViewById(R.id.view);
                view1.setVisibility(View.VISIBLE);
                IncompleteRecordsAdapter incompleteRecordsAdapter = new IncompleteRecordsAdapter(incompleteRecordList);
                incomplete_recordsRecyclerview.setVisibility(View.VISIBLE);
                incomplete_recordsRecyclerview.setAdapter(incompleteRecordsAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + " / " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        if (sqlController1 != null) {
            sqlController1.close();
        }

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
                        //Redirect to Edit patient update page
                        afterDateEditPatientUpdate(position);
                    }

                    if (date1.before(currentdate)) {
                        //Redirect to add patient update page
                        beforeDateAddPatientUpdate(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        incomplete_recordsRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), incomplete_recordsRecyclerview, new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {

                RegistrationModel registrationModel = incompleteRecordList.get(position);
                Intent i = new Intent(getContext().getApplicationContext(), EditIncompleteRecordsActivity.class);
                i.putExtra("PRESCRIPTION", registrationModel.getPres_img());
                i.putExtra("ID", registrationModel.getId());
                i.putExtra("ADDED_ON", registrationModel.getAdded_on());
                i.putExtra("MOBILE_NO", registrationModel.getMobileNumber());
                i.putExtra("EMAIL_ADDRESS", registrationModel.getEmail());
                i.putExtra("REFEREDBY", registrationModel.getReferedBy());
                i.putExtra("REFEREDTO", registrationModel.getReferedTo());
                startActivity(i);
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

    private void makeToast(final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {

                toast = Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
        //  Log.e("asynTaskValue",""+asynTaskValue);
        boolean isInternetPresent = connectionDetector.isConnectingToInternet();

        //used to fetch patient data only once in lifetime of app from server
        if (asyn_value == null) {
            if (isInternetPresent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.equals(asynTaskValue, "1")) {

                        cao.setValue("2");

                        //   String apiKey = getResources().getString(R.string.apikey);
                        if (savedUserName != null && savedUserPassword != null) {

                            new GetBannerImageTask(getContext(), savedUserName, savedUserPassword, doctor_membership_number, company_id, new AppController().getDateTimenew()); //send log file to server

                        }

                        // new LastNameAsynTask(getContext(), savedUserName, savedUserPassword, appController.getDateTime());
                        getPatientRecords(savedUserName, savedUserPassword, doctor_membership_number, docId);

                    }
                }
            }
        }

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
        i.putExtra("UID", registrationModel.getUid());

        startActivity(i);
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
        i.putExtra("VISITDATE", registrationModel.getVisit_date());
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
        i.putExtra("REFEREDBY", registrationModel.getReferedBy());
        i.putExtra("REFEREDTO", registrationModel.getReferedTo());
        i.putExtra("UID", registrationModel.getUid());
        i.putExtra("EMAIL", registrationModel.getEmail());


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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number:  " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
                searchView.onActionViewCollapsed();//removes unwanted warings like  sendKeyEvent on inactive InputConnection
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment;

                fragment = new HomeFragment();
                fm.beginTransaction().replace(R.id.flContent, fragment)
                        .addToBackStack(null).commit();

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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

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

                        makeToast("Phone Number Should Be Only 10 Digits ");

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

                        queryCountforTotalPatient = sqlController.getCountResultforgetPatientListForPhoneNumberFilter();

                     //   Log.e("queryCountforTotalP"," "+filteredModelList.size() + "  / "+queryCountforTotalPatient);

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
                        makeToast("Enter Min 5 Digits to Begin Search");
                        //  Toast.makeText(getContext().getApplicationContext(), "Enter Min 5 Digits to Begin Search", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Search View " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

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
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    Date date1 = sdf1.parse(visit_date);

                    Date currentdate = sdf1.parse(String.valueOf(sysdate));

                    if (date1.after(currentdate) || date1.equals(currentdate)) {

                        dateisCurrent(position);

                    }

                    if (date1.before(currentdate)) {

                        dateisBefore(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
        i.putExtra("VISITDATE", registrationModel.getVisit_date());
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
        i.putExtra("REFEREDBY", registrationModel.getReferedBy());
        i.putExtra("REFEREDTO", registrationModel.getReferedTo());
        i.putExtra("UID", registrationModel.getUid());
        i.putExtra("EMAIL", registrationModel.getEmail());
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
        i.putExtra("UID", registrationModel.getUid());
        i.putExtra("EMAIL", registrationModel.getEmail());
        startActivity(i);
    }


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
        dialog.setCancelable(false);


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
    private void setupAnimation() {

        try {
            bannerimgNames = bannerClass.getImageName();

            appController.setUpAdd(getContext(), bannerimgNames, backChangingImages, doctor_membership_number, "Patient Central");
        } catch (ClirNetAppException e) {
            e.printStackTrace();
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
                builder.setMessage("Send Email !")
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

                showCreatePatientAlertDialog(getContext());

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
        dialog.setCanceledOnTouchOutside(false);
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

                if (newPass.equals(getString(R.string.servicePassword))) {
                    Intent intent = new Intent(getContext(), ServiceArea.class);
                    intent.putExtra("username", savedUserName);
                    intent.putExtra("password", savedUserPassword);
                    startActivity(intent);
                    dialog.dismiss();
                } else {
                    makeToast("Enter Correct Password");
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


    //sync data to server
    private void sendDataToServer(final String patient_details, final String patient_visits, final String associate_data, final String docMemId, final String docId, final int patient_visits_count, final int patient_details_count, final String savedUserName, final String process_start_time) {

        String tag_string_req = "req_sync1";

        send_data_start_time = appController.getDateTimenew();

        pDialog.setMessage("Syncing Data");
        pDialog.setCancelable(false);

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SYCHRONISED_TOSERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                appController.appendLog(appController.getDateTimenew() + " " + " / " + "Response from Server Home" + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                //  hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);

                    JSONObject user = jObj.getJSONObject("data");
                    String strresponse = user.getString("response");

                    String response_end_time = user.getString("process_end_time");

                    String msg = user.getString("msg");
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment data is sync to server : Message: " + msg + "  Response  " + strresponse + "  Response End Time:  " + response_end_time);

                    if (msg.equals("OK")) {

                        int patientIds_Listsize = patientIds_List.size();
                        for (int i = 0; i < patientIds_Listsize; i++) {
                            String patientId = patientIds_List.get(i).getPat_id();
                            String flag = "1";

                            dbController.FlagupdatePatientPersonal(patientId, flag);
                        }

                        int patientVisitIdsList = getPatientVisitIdsList.size();
                        for (int i = 0; i < patientVisitIdsList; i++) {
                            String patientVisitId = getPatientVisitIdsList.get(i).getKey_visit_id();
                            String flag = "1";

                            dbController.FlagupdatePatientVisit(patientVisitId, flag);
                        }

                        int associateMasterIdsList = getAssociateMasterList.size();

                        for (int i = 0; i < associateMasterIdsList; i++) {
                            String patientVisitId = getAssociateMasterList.get(i).getPat_id();

                            String flag = "1";

                            dbController.FlagupdateAssociateMater(patientVisitId, flag);
                        }

                        String time = appController.getDateTimenew();
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Activity data is sync to server : patient Visit Count : " + pat_visit_count + " patient Count : " + pat_personal_count);

                        lastSyncTime(time);

                        dbController.addAsynctascRun_status("Data Synced", send_data_start_time, time, "");
                        dbController.addSyncStatus(time, patient_details_count, patient_visits_count, msg, time);
                        hideDialog();
                        makeToast("Data Sent Successfully to Server");

                    } else if (strresponse.equals("501")) {
                        hideDialog();
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Activity data is sync to server : Error : " + strresponse + " patient Count : " + msg);
                    }

                } catch (JSONException e) {
                    // JSON error
                    hideDialog();
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Json Exception error" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    Toast.makeText(getContext().getApplicationContext(), " Json Exception error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // As of f605da3 the following should work
                hideDialog();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Error while sending data to server " + error + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                String keyid = getResources().getString(R.string.apikey);
                Map<String, String> params = new HashMap<>();
                params.put("apikey", keyid);
                params.put("username", savedUserName);
                params.put("patient_details", patient_details);
                params.put("patient_visits", patient_visits);
                params.put("associate_master", associate_data);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
                params.put("patient_visits_count", String.valueOf(patient_visits_count));
                params.put("patient_details_count", String.valueOf(patient_details_count));
                params.put("process_start_time", process_start_time);

                return params;
            }
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers= new HashMap<>();
                headers.put("Accept","application/json");
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }*/
        };
        int socketTimeout = 30000;  //30 seconds - change to what you want
        int retryforTimes = 2;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
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

    private void getPatientRecords(final String savedUserName, final String savedUserPassword, final String docMemId, final String docId) {
        // Tag used to cancel the request
        String tag_string_req = "req_patientdata";


        pDialog.setMessage("Initializing Application.Getting patient records, Please Wait...");
        pDialog.setCancelable(false);
        showDialog();
        pateintrecordasync_start_time = appController.getDateTimenew();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PATIENT_RECORDS, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


                new getPatientRecordsFromServer().execute(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //    Log.e(TAG, "Login Error: Failed To Initalize Data" + error.getMessage()+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                Toast.makeText(getContext(),
                        "Failed To Initalize Data" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Volley error" + error + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("username", savedUserName);
                params.put("password", savedUserPassword);
                params.put("apikey", getResources().getString(R.string.apikey));
                params.put("process_start_time", pateintrecordasync_start_time);
                params.put("membershipid", docMemId);
                params.put("docId", docId);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat dobsdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try {
                Date formatDate = sdf.parse(added_on);
                Date formatDateOB = dobsdf.parse(pat_date_of_birth);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                convertedDate = sd1.format(formatDate);
                converteddobDate = sd1.format(formatDateOB);

                SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                convertedTime = sd2.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(" Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                Date formatDate = sdf.parse(act_follow_up_date);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                convertedActualfodDate = sd1.format(formatDate);


            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + " Home Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            String convertedAddedonDate = null;
            String convertedAddedonTime = null;

            try {
                Date formatDate = sdf.parse(added_on);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                convertedAddedonDate = sd1.format(formatDate);

                SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
                convertedAddedonTime = sd2.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

            //This will convert visit date format into dd-MM-yyyy format 17-9-2016

            String convertedVisitDate = null;
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            try {
                Date formatDate = sdf1.parse(visit_date);

                SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                convertedVisitDate = sd1.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

            String patient_info_type_form = "Electronics"; //need to add this if records are added from web service to identify the data...........

            dbController.addPatientHistoryRecords(visit_id, pat_id, ailment, convertedVisitDate, follow_up_date, follow_up_days,
                    follow_up_weeks, follow_up_months, convertedActualfodDate, notes, added_by, convertedAddedonDate, convertedAddedonTime, modified_by, modified_on, is_disabled, disabled_by, disabled_on,
                    is_deleted, deleted_by, deleted_on, flag, patient_info_type_form, prescription, weight, pulse, bp_high, bp_low, temp, sugar, symptoms, dihnosis, tests, drugs, doctor_membership_number, docId);

        }

        dbController.addAsync();//set sync value true if data is synced from server successfully.
        String end_time = appController.getDateTimenew();


        dbController.addAsynctascRun_status("getPatientRecords", pateintrecordasync_start_time, end_time, "");//last parameter is update on which is blank right now

        Intent i = new Intent(getContext(), NavigationActivity.class);
        startActivity(i);
        makeToast("Application Initialization Successful");
    }

    //custom dialog  for the sync button result
    private void showNoInternetAlert(String title, String message) {
        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.no_inetrnet_login_dialog);

        dialog.setTitle(title);
        //  dialog.setCancelable(false);
        TextView msgTxt = (TextView) dialog.findViewById(R.id.msgTxt);
        msgTxt.setText(message);

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

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

    //custom dialog  for the sync button result
    private void showNoDataToSendAlert(String title, String message) {
        final Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.no_datatosend_dialog);

        dialog.setTitle(title);
        //  dialog.setCancelable(false);
        TextView msgTxt = (TextView) dialog.findViewById(R.id.msgTxt);
        TextView last_sync_time = (TextView) dialog.findViewById(R.id.last_sync_time);
        msgTxt.setText(message);
        String strlast_syncTime = getLastSyncTime();
        last_sync_time.setText(strlast_syncTime);

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialog.show();
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
        if (bannerClass != null) {
            bannerClass = null;
        }
        if (mDownloader != null) {
            mDownloader = null;
        }
        bannerimgNames = null;

        incomplete_recordsRecyclerview = null;
        incompleteRecordList = null;

        norecordtv = null;
        addaNewPatient = null;
        view = null;
        date1 = null;
        toast = null;
        asyn_value = null;
        searchNumber = null;
        patientVisitHistorArayString = null;
        Searchrecycler_view = null;
        recyclerView = null;
        savedUserName = null;
        savedUserPassword = null;
        patientInfoArayString = null;
        doctor_membership_number = null;
        pateintrecordasync_start_time = null;
        backChangingImages = null;
        send_data_start_time = null;
        incompletedisplay_tv = null;
        docId = null;
        company_id = null;
        nameheader = null;
        sync = null;
        fab = null;
        pat_visit_count = null;
        mLayoutManager = null;
        todays_patient_updatetxt = null;
        patientIds_List = null;
        getPatientVisitIdsList = null;
        getAssociateMasterList = null;
        sdf1 = null;
        sva = null;
        filteredModelList = null;
        sysdate = null;
    }

    @Override
    public void onPause() {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AppController.getInstance().trackScreenView("Home Fragment");
    }

    private void showCreatePatientAlertDialog(Context con) {

        final Dialog dialog = new Dialog(con);
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

                String msg = user.getString("msg");
                String responce = user.getString("response");
                String response_end_time = user.getString("process_end_time");
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Initial Patient Data :  Message :" + msg + "  Response : " + responce + "   Response End Time:  " + response_end_time + "  " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                JSONArray jsonArray = user.getJSONArray("doctor_patient_relation");

                JSONArray patientHistoryList = user.getJSONArray("patient_visit_details");//for live api

                setPatientPersonalList(jsonArray);
                setPatientHistoryList(patientHistoryList);


            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
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
        int end = 0;
        try {
            int index = sva.getItemCount() - 1;
            end = index + PAGE_SIZE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  Log.e("index", "" + index + " " + end + " size is " + queryCount);

        if (end <= queryCountforTotalPatient) {
            try {
                memberList = sqlController.getPatientListForPhoneNumberFilter(searchNumber, ival, loadLimit);
            } catch (ClirNetAppException e) {
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                e.printStackTrace();
            }
            try {
                sva.addAll(memberList);
            } catch (Exception e) {
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                e.printStackTrace();
            }



            if (end >= queryCountforTotalPatient) {
                sva.setLoading(false);

            }
        }
    }

    private String getLastSyncTime() {

        SharedPreferences pref1 = getContext().getSharedPreferences("SyncFlag", MODE_PRIVATE);

        return pref1.getString("lastSyncTime", null);
    }

    public void startService() {
        String apiKey = getResources().getString(R.string.apikey);
        Intent serviceIntent = new Intent(getContext(), SyncDataService.class);
        serviceIntent.putExtra("name", savedUserName);
        serviceIntent.putExtra("password", savedUserName);
        serviceIntent.putExtra("apikey", apiKey);
        getContext().startService(serviceIntent);
    }
}