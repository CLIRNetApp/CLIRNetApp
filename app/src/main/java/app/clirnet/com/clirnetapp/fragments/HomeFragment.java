package app.clirnet.com.clirnetapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.activity.AddPatientUpdate;
import app.clirnet.com.clirnetapp.activity.EditPatientUpdate;
import app.clirnet.com.clirnetapp.activity.LoginActivity;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.RegistrationActivityNew;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.RVAdapter;
import app.clirnet.com.clirnetapp.adapters.SearchViewdapter;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.app.LastNameAsynTask;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


@SuppressWarnings("ConstantConditions")
public class HomeFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    // TODO: Rename parameter arguments, choose names that match


    private OnFragmentInteractionListener mListener;
    private TextView date;


    private static final String TAG = "Synchronised";
    private static final String PREFS_NAME = "SyncFlag";
    private static final String PREF_VALUE = "status";

    private SearchView searchView;

    private SQLController sqlController;

    private RecyclerView recyclerView;
    private LinearLayout norecordtv;
    private final int[] imageArray = {R.drawable.brand, R.drawable.brethnum, R.drawable.deptrim, R.drawable.fenjoy, R.drawable.hapiom, R.drawable.liporev, R.drawable.magnamet, R.drawable.motirest, R.drawable.revituz, R.drawable.suprizon};
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


    private TextView todays_patient_updatetxt;
    private View view;
    private String savedUserName;
    private String savedUserPassword;
    private String asyn_value;
    private AppController appController;
    private Button sync;

    public HomeFragment() {
        this.setHasOptionsMenu(true);
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
        date = (TextView) view.findViewById(R.id.date);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Searchrecycler_view = (RecyclerView) view.findViewById(R.id.Searchrecycler_view);


        sync = (Button) view.findViewById(R.id.sync);
        Button export = (Button) view.findViewById(R.id.exp);
        addaNewPatient = (Button) view.findViewById(R.id.addanewPatient);
        todays_patient_updatetxt = (TextView) view.findViewById(R.id.todays_patient_updatetxt);


        if (filteredModelList != null) {
            filteredModelList.clear();
        }

        getFlagStatus();
        checkLastLoginTime();

        appController = new AppController();

        sdf1 = new SimpleDateFormat("dd-MM-yyyy");
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
        String added_on = "0000-00-00";
        String convertedDate;


        SimpleDateFormat dobsdf = new SimpleDateFormat("yyyy-MM-dd");
        if (added_on.equals(0000 - 00 - 00)) {
            added_on = null;
        }


        try {
            Date formatDate = dobsdf.parse(added_on);

            SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
        }

        //open database for further interaction with database

        try {

            sqlController = new SQLController(getActivity().getApplicationContext());
            sqlController.open();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

            patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());

            patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment " + e);
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
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
                        System.out.println("Date1 is after sysdate");


                        afterDateEditPatientUpdate(position);

                    }

                    if (date1.before(currentdate)) {

                        beforeDateAddPatientUpdate(position);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
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


        //used to fetch patient data only once in lifetime of app from server
        if (asyn_value == null) {

            boolean isInternetPresent = connectionDetector.isConnectingToInternet();
            if (isInternetPresent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.equals(asynTaskValue, "1")) {

                        cao.setValue("2");

                        getPatientRecords(savedUserName, savedUserPassword);
                        new LastNameAsynTask(getContext(),savedUserName, savedUserPassword);
                        /*LongOperation longOperation=new LongOperation();
                        longOperation.execute(savedUserName,savedUserPassword);*/

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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
        }
    }

    //send data to server
    private void sendDataToServer() throws ClirNetAppException {


        boolean isInternetPresent = connectionDetector.isConnectingToInternet();

        if (isInternetPresent) {

            if (patientIds_List != null && !patientIds_List.isEmpty() || getPatientVisitIdsList != null || !getPatientVisitIdsList.isEmpty()) {
                // Log.e("senddata", "data is sending");
                sendDataToServer(patientInfoArayString, patientVisitHistorArayString);

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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
        }


        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {/* Log.e("querty",""+ searchView.getQuery().toString());*/

                System.out.println("on clicked: " + position);

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

                    if (newText.trim().length() > 10) {

                        Toast toast = Toast.makeText(getContext(), "Phone Number Should Be Only 10 Digits ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return true;
                    }

                    if (newText.trim().length() > 4) {
                        norecordtv.setVisibility(View.GONE);
                        Searchrecycler_view.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        addaNewPatient.setVisibility(View.VISIBLE);
                        todays_patient_updatetxt.setVisibility(View.GONE);


                        filteredModelList = sqlController.getPatientListForPhoneNumberFilter(newText);


                        if (filteredModelList.size() <= 0) {

                            showCreatePatientAlertDialog(newText);

                        } else {
                            sva = new SearchViewdapter(filteredModelList);

                            sva.setFilter(filteredModelList);
                            Searchrecycler_view.setAdapter(sva);

                        }

                        searchView.clearFocus();
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "Enter Min 5 Digits to Begin Search", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment Search View " + e);
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
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
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
        // TODO: Update argument type and name
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
    private void setupAnimation() {

        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                backChangingImages.setImageResource(imageArray[i]);
                i++;
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                backChangingImages.postDelayed(this, 10000);  //for interval...
            }
        };
        backChangingImages.postDelayed(runnable, 100); //for initial delay..
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


            case android.R.id.home:

                break;

        }
        return true;

    }


    //sync data to server
    private void sendDataToServer(final String patient_details, final String patient_visits) {

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

                    // Check for error node in json

                    // user successfully logged in
                    // Now store the user in SQLite
                    //  dbController.addLoginRecord(name, md5EncyptedDataPassword);
                    // inputLoginData.add(new LoginModel(name, md5EncyptedDataPassword));
                    // Create login session


                    //   String uid = jObj.getString("uid");

                    JSONObject user = jObj.getJSONObject("data");

                    String msg = user.getString("msg");


                    if (msg.equals("OK")) {

                        //  Log.e("ashish", "" + patientIds_List.size());
                        int size = patientIds_List.size();
                        for (int i = 0; i < size; i++) {
                            String patientId = patientIds_List.get(i).getPat_id();
                            String flag = "1";

                            dbController.FlagupdatePatientPersonal(patientId, flag);
                        }

                        int listsize = getPatientVisitIdsList.size();
                        for (int i = 0; i < listsize; i++) {
                            // String patientId = getPatientVisitIdsList.get(i).getPat_id();
                            String patientVisitId = getPatientVisitIdsList.get(i).getKey_visit_id();
                            String flag = "1";

                            dbController.FlagupdatePatientVisit(patientVisitId, flag);
                        }
                        Log.e("senddata", "data is sent");
                        String time = appController.getDateTimenew();
                        Log.e("senddata", "data is sent"+time);
                        lastSyncTime(time);
                        toast = Toast.makeText(getContext().getApplicationContext(), "Data Send Successfully to Server", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
                    Toast.makeText(getContext().getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

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
                return params;

                // return checkParams(params);
            }

            /*private Map<String, String> checkParams(Map<String, String> params) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                    if (pairs.getValue() == null) {
                        params.put(pairs.getKey(), "");
                    }
                }
                return params;
            }
*/

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


        pDialog.setMessage("Initializing Application. Please Wait...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PATIENT_RECORDS, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Sync Response: " + response);
                Log.e("responseresponse", "" + response);

                //   new DownloadMusicfromInternet().execute(response);
                try {
                    JSONObject jObj = new JSONObject(response);


                    // Check for error node in json

                    // user successfully logged in
                    // Now store the user in SQLite

                    // Create login session

                    JSONObject user = jObj.getJSONObject("data");

                    JSONArray jsonArray = user.getJSONArray("doctor_patient_relation");
                    //  Log.e("jsonArray", "" + jsonArray);
                    JSONArray patientHistoryList = user.getJSONArray("patient_visit_details");//for live api
                   // JSONArray patientHistoryList = user.getJSONArray("patinet_visit_details"); //for local addrres
                    // Log.e("jsonArray", "" + patientHistoryList);
                    setPatientPersonalList(jsonArray);
                    setPatientHistoryList(patientHistoryList);
                    hideDialog();


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: Failed To Initalize Data" + error.getMessage());
                Toast.makeText(getContext(),
                        "Failed To Initalize Data" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
                String body;
                //get status code here
                //  String statusCode = String.valueOf(error.networkResponse.statusCode);
                //  Log.e("statusCode", "" + statusCode);
                //get response body and parse with appropriate encoding
               /* if (error.networkResponse.data != null) {
                    try {
                        body = new String(error.networkResponse.data, "UTF-8");
                        Log.e("statusCodebody", " " + body);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }*/
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
        //todo need to check the retry code for multiple times
        //this will retry the request for 2 times
        int socketTimeout = 30000;//30 seconds - change to what you want
        int retryforTimes = 2;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, retryforTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        AppController.getInstance().setPriority(Request.Priority.IMMEDIATE);
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
                appController.appendLog("Home Fragment" + e);
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
            String weight=jsonPatientHistoryObject.getString("weight");
            String   pulse=  jsonPatientHistoryObject.getString( "pulse");
            String  bp_high= jsonPatientHistoryObject.getString   ("bp_high");
            String    bp_low= jsonPatientHistoryObject.getString ("bp_low");
            String    temp= jsonPatientHistoryObject.getString ("temperature");
            String     sugar= jsonPatientHistoryObject.getString("sugar");
            String   drugs= jsonPatientHistoryObject.getString  ("drugs");
            String    tests=  jsonPatientHistoryObject.getString("tests");
            String  dihnosis= jsonPatientHistoryObject.getString  ( "diagnosis");
            String    symptoms=  jsonPatientHistoryObject.getString("symptoms");
            String  prescription=  jsonPatientHistoryObject.getString ( "prescription");
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
                appController.appendLog(appController.getDateTime() + " " + "/ " + " Home Fragment " + e);
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
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
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
                appController.appendLog(appController.getDateTime() + " " + "/ " + "Home Fragment" + e);
            }


            String patient_info_type_form = "Electronics"; //need to add this if records are added from web service to identify the data...........

            dbController.addPatientHistoryRecords(visit_id, pat_id, ailment, convertedVisitDate, follow_up_date, follow_up_days,
                    follow_up_weeks, follow_up_months, convertedActualfodDate, notes, added_by, convertedAddedonDate, convertedAddedonTime, modified_by, modified_on, is_disabled, disabled_by, disabled_on,
                    is_deleted, deleted_by, deleted_on, flag, patient_info_type_form,prescription,weight,pulse,bp_high,bp_low,temp,sugar,symptoms,dihnosis,tests,drugs);


        }

        dbController.addAsync();
        // hideDialog();
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();*/
       /* fragmentManager = this.getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();*/
        Intent i = new Intent(getContext(), NavigationActivity.class);
        startActivity(i);

        makeToast("Application Initialization Successful");

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

        recyclerView.setOnClickListener(null);
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
        Log.e("onDetach", "onDetach Home Fragment");
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment");

        super.onPause();
    }

    private void getFlagStatus() {
        SharedPreferences pref = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String status = pref.getString(PREF_VALUE, null);

        Log.e("Flag Status", "" + status);

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

                //fragmentManager = getActivity().getSupportFragmentManager();
                //  fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
                dialog.dismiss();

            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click

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
        Log.e("loginTime", "" + loginTime + " hours " + hrs);
        if (hrs > 8) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            System.gc();
        }
        SharedPreferences pref1 = getContext().getSharedPreferences("SyncFlag", getContext().MODE_PRIVATE);
        String lastSyncTime = pref1.getString("loginTime", null);
        int hrslastSync = AppController.hoursAgo(lastSyncTime);
        Log.e("loginTime12", "" + lastSyncTime + " hours " + hrslastSync);

        if (hrslastSync > 72) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            System.gc();
        }
    }
    public void lastSyncTime(String lastSyncTime ) {

        getContext().getSharedPreferences("SyncFlag",getContext().MODE_PRIVATE)
                .edit()
                .putString("lastSyncTime", lastSyncTime)
                .apply();

    }
    private class LongOperation extends AsyncTask<String, Void, String> {
        ProgressDialog pd;
        @Override
        protected String doInBackground(String... params) {

            new LastNameAsynTask(getContext(),savedUserName, savedUserPassword);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("onPostExecute","onPostExecute");
            pd.dismiss();
        }

        @Override
        protected void onPreExecute() {
            Log.e("onPreExecute","onPreExecute");
             pd = new ProgressDialog(getContext());
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}








