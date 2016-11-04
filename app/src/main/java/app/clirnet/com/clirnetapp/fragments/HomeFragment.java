package app.clirnet.com.clirnetapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ConnectionDetector;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.activity.AddPatientUpdate;
import app.clirnet.com.clirnetapp.activity.EditPatientUpdate;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.RegistrationActivity;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.RVAdapter;
import app.clirnet.com.clirnetapp.adapters.SearchViewdapter;
import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.CustomeException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.helper.SessionManager;
import app.clirnet.com.clirnetapp.models.CallAsynOnce;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

import static app.clirnet.com.clirnetapp.R.id.email;


public class HomeFragment extends Fragment implements  RecyclerView.OnItemTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private TextView date;


    private static final String TAG = "Synchronised";



    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;


    private SearchView searchView;

    private android.support.v4.app.FragmentManager fragmentManager;
    private SQLController sqlController;

    private RecyclerView recyclerView;
    private LinearLayout norecordtv;
    private android.support.v4.app.FragmentTransaction ft;
    private final int[] imageArray = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    private ImageView backChangingImages;

    private RecyclerView Searchrecycler_view;
    private StringBuilder sysdate;

    private List<RegistrationModel> filteredModelList;
    private List<RegistrationModel> filteredModelList1 = new ArrayList<>();

    private SearchViewdapter sva;
    private SQLiteHandler dbController;
    private SimpleDateFormat sdf1;
    private Date date1;
    private Toast toast;
    private ConnectionDetector connectionDetector;
    private ProgressDialog pDialog;
    private String patientInfoArayString;
    private String patientVisitHistorArayString;
    private ArrayList<RegistrationModel> patientDataforPhoneFilter;
    private ArrayList<RegistrationModel> patientIds_List ;
    private ArrayList<RegistrationModel> getPatientVisitIdsList;


    private String doctor_membership_number;
    private final String mailId="support@clirnet.com";
    private Button addaNewPatient;
    private String searchNumber=null;


    private TextView todays_patient_updatetxt;
    private View view;
    private String savedUserName;
    private String savedUserPassword;
    private ArrayList<LoginModel> mLoginList;
    private String asyn_value;

    public HomeFragment() {
        this.setHasOptionsMenu(true);
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

            //getSupportActionBar().setTitle(Html.fromHtml("<font color='#877AFF'>Edit Personal Informationt</font>"));
           // this will handle screen rotation
            setRetainInstance(true);

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // backChangingImages = null; // now cleaning up!
        view=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


         view = inflater.inflate(R.layout.fragment_home, container, false);




        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);
        date = (TextView) view.findViewById(R.id.date);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        Searchrecycler_view = (RecyclerView) view.findViewById(R.id.Searchrecycler_view);

        /*final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Searchrecycler_view.setLayoutManager(layoutManager);*/
        Button sync = (Button) view.findViewById(R.id.sync);
        Button export = (Button) view.findViewById(R.id.exp);
        addaNewPatient=(Button)view.findViewById(R.id.addanewPatient);
        todays_patient_updatetxt=(TextView)view.findViewById(R.id.todays_patient_updatetxt);
        mLoginList=new ArrayList<>();

        sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        pDialog = new ProgressDialog(getContext());
        connectionDetector = new ConnectionDetector(getContext());
        dbController = new SQLiteHandler(getContext());
        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);

        patientIds_List  = new ArrayList<>();
        getPatientVisitIdsList= new ArrayList<>();

        Glide.get(getContext()).clearMemory();

        TextView termsandCondition = (TextView) view.findViewById(R.id.termsandCondition);
//open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PrivacyPolicy.class);
                startActivity(intent);


            }
        });
        //open Terms and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TermsCondition.class);
                startActivity(intent);

            }
        });
        String added_on = "0000-00-00";
        String convertedDate;

      //  StringBuffer sb=new StringBuffer("added_on");
       /* String sb1= String.valueOf(sb);
        sb1="";
       // sb.replace(0,"Java");//now original string is changed
        System.out.println("ashish"+sb1);//prints HJavaello
*/
        SimpleDateFormat dobsdf = new SimpleDateFormat("yyyy-MM-dd");
        if(added_on.equals(0000-00-00)){
            added_on=null;
        }

        try {
            Date formatDate = dobsdf.parse(added_on);

            SimpleDateFormat sd1 = new SimpleDateFormat("dd-MM-yyyy");
            convertedDate = sd1.format(formatDate);
           Log.e("convertedDate",""+convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //open database for further interaction with database

        try {

            sqlController= new SQLController(getContext());
            sqlController.open();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            JSONArray patientInfoarray = dbController.getResultsForPatientInformation();
            Log.e("PatientInfoarray", " " + patientInfoarray);
            patientInfoArayString = String.valueOf(dbController.getResultsForPatientInformation());
            JSONArray patientVisitHistoryarray = dbController.getResultsForPatientHistory();

            patientVisitHistorArayString = String.valueOf(dbController.getResultsForPatientHistory());
            Log.e("PatientVisitHistor", " " + patientVisitHistoryarray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(sqlController != null){
                sqlController.close();
            }
        }


        // this will get the json array from sqlite database


        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getContext()).clearDiskCache();
            }
        }).start();


//send the data to server when sync botton pressed
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqlController = new SQLController(getActivity());
                    sqlController.open();
                    patientIds_List=  sqlController.getPatientIdsFalg0();
                    getPatientVisitIdsList=sqlController.getPatientVisitIdsFalg0();

                    Log.e("patientIds_List", "data is sending"+patientIds_List.size());
                    Log.e("patientIds_List12","no data to send"+patientIds_List.size());
                    if(patientIds_List.size()!= 0 || getPatientVisitIdsList.size()!= 0) {


                            sendDataToServer();



                    }else{
                        Log.e("nodata","no data to send");
                        makeToast("No Data to Send");


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if(sqlController !=null){
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
                Intent i = new Intent(getContext(), RegistrationActivity.class);
                i.putExtra("number", searchNumber);
                startActivity(i);


            }
        });


        norecordtv = (LinearLayout) view.findViewById(R.id.norecordtv);
        Date todayDate = new Date();


        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM,yyyy");
        String dd = DATE_FORMAT.format(todayDate);
        date.setText("Today's Date "+dd);



       // doctor_membership_number =sqlController.getDocMembershipIdNew().toString();


        Log.e("doctor_member", "" +doctor_membership_number);
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        sysdate = new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append("");


        ArrayList<RegistrationModel> patientData = null;
        SQLController sqlController1=null;
        try {
            sqlController1 = new SQLController(getActivity());
            sqlController1.open();
            patientData = sqlController1.getPatientList();
            filteredModelList1 = filterBySystemDate(patientData, sysdate.toString()); //filter data from patient list via system date

            patientDataforPhoneFilter = new ArrayList<>();

            patientDataforPhoneFilter = (sqlController1.getPatientListForPhoneNumberFilter()); //get all patient data from db
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sqlController1 !=null){
            sqlController1.close();
        }
        Log.e("sizew", "" + patientData.size());





        sva = new SearchViewdapter(filteredModelList);
        //To check if there is data or not in arraylist in case of no internet connectivity or no data downloads
        if (filteredModelList1.size() <= 0) {

            //  errorimg.setVisibility(View.VISIBLE);
        } else {
            // adapter = new RVAdapter(filteredModelList1);
            norecordtv.setVisibility(View.GONE);
            // rvadapter = new RVAdapter(patientData);
//sets array list data to recycler view
            RVAdapter rvadapter = new RVAdapter(filteredModelList1);
            recyclerView.setAdapter(rvadapter);
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                                 /*latitudefrom_server=book.getLat();
                                     longitudefrom_server=book.getLonl();*/
                RegistrationModel book = filteredModelList1.get(position);
                String visit_date = book.getVisit_date();
                try {
                    date1 = sdf1.parse(visit_date);

                    Date currentdate = sdf1.parse(String.valueOf(sysdate));

                    if (date1.after(currentdate) || date1.equals(currentdate)) {
                        System.out.println("Date1 is after sysdate");

                       // Toast.makeText(getContext(), "Date1 is after sysdate", Toast.LENGTH_LONG).show();
                                afterDateEditPatientUpdate(position);



                       /* Toast.makeText(getContext(), book.getFirstName() + " is selected!", Toast.LENGTH_SHORT).show();*/
                    }

                    if (date1.before(currentdate)) {

                         beforeAddPatientUpdate(position);
                        //  System.out.println("Date1 is before or equal to Date2");

//                        Toast.makeText(getContext(), "Date1 is before sysdate", Toast.LENGTH_LONG).show();



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));



        setupAnimation();
        getDataFromDatabase();
        callDataFromServer();

        return view;
    }

    private void makeToast(String msg) {
        if(toast == null) {
            toast = Toast.makeText(getContext(),msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void getDataFromDatabase() {
        Cursor cursor = null;
        SQLController sqlController1 = null;
        try {

            sqlController1 = new SQLController(getActivity());
            sqlController1.open();



            cursor = sqlController1.getUserLoginRecords();
            // mLoginList = new ArrayList<String>();

            while (cursor.moveToNext()) {

                LoginModel user = new LoginModel(cursor.getString(0), cursor.getString(1));

                savedUserName = cursor.getString(0);
                savedUserPassword = cursor.getString(1);
                Log.e("qwert", "" + savedUserName + "/ " + savedUserPassword);
                mLoginList.add(user); //add the item
            }
            asyn_value = sqlController.getAsyncvalue().trim();
            Log.e("asyn_value","asyn_value is "+asyn_value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{

            if (cursor != null) {
                cursor.close();
            }
            if(sqlController1 !=null){
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
                if (asynTaskValue == "1") {

                    cao.setValue("2");

                    getPatientRecords(savedUserName, savedUserPassword);

                } else {
                    Log.e("repeat", "allready data is downloaded");
                }
            } else {
                Log.e("nonet", "no inetenet");
            }
        } else {
            Log.e("repeat", "allready data is downloaded");
        }

    }

    private void beforeAddPatientUpdate(int position) {

        RegistrationModel book = filteredModelList1.get(position);


        //  System.out.println("Date1 is before or equal to Date2");
        Intent i = new Intent(getContext(), AddPatientUpdate.class);
        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("PatientID", book.getPat_id());
        Log.e("Patientkaid", "" + book.getPat_id());
        Log.e("Patientkaid", "" + book.getVisit_date());
        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());
        i.putExtra("PHONE", book.getMobileNumber());
        i.putExtra("AGE", book.getAge());
        i.putExtra("LANGUAGE", book.getLanguage());
        i.putExtra("GENDER", book.getGender());
        i.putExtra("FOD", book.getFollowUpDate());
        i.putExtra("AILMENT", book.getAilments());
        i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", book.getClinicalNotes());
        i.putExtra("PRESCRIPTION", book.getPres_img());
        Log.e("img", "" + book.getPres_img());

        startActivity(i);
//                        Toast.makeText(getContext(), "Date1 is before sysdate", Toast.LENGTH_LONG).show();
    }

    private void afterDateEditPatientUpdate(int position) {
        RegistrationModel book = filteredModelList1.get(position);
        Intent i = new Intent(getContext(), EditPatientUpdate.class);

        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());
        Log.e("Patientkaid", "" + book.getPat_id());
        Log.e("Patientkaid", "" + book.getVisit_date());
        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());
        i.putExtra("PHONE", book.getMobileNumber());
        i.putExtra("AGE", book.getAge());
        i.putExtra("LANGUAGE", book.getLanguage());
        i.putExtra("GENDER", book.getGender());
        i.putExtra("ACTUALFOD", book.getActualFollowupDate());
        i.putExtra("FOD", book.getFollowUpDate());
        i.putExtra("AILMENT", book.getAilments());
        i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", book.getClinicalNotes());
        i.putExtra("PRESCRIPTION", book.getPres_img());
        i.putExtra("VISITID",book.getKey_visit_id());
        Log.e("VISITID", "" + book.getKey_visit_id());

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
        }
    }
//send data to server
    private void sendDataToServer() throws CustomeException {
        patientIds_List=  sqlController.getPatientIdsFalg0();
        getPatientVisitIdsList=sqlController.getPatientVisitIdsFalg0();

        Log.e("patientIds_List", "data is sending"+patientIds_List.size());
        Log.e("patientIds_List12","no data to send"+patientIds_List.size());
        boolean isInternetPresent = connectionDetector.isConnectingToInternet();

        if (isInternetPresent) {
            if(patientIds_List.size()!= 0 || getPatientVisitIdsList.size()!= 0) {
                //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                sendDataToServer(patientInfoArayString, patientVisitHistorArayString);
                Log.e("sending5","data is sending");
            }else{
                makeToast("No Data to Send");
            }
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Read Update
       /*     alertDialog.setTitle("hi");*/
            alertDialog.setMessage("Switch on the data...!!!");

            alertDialog.setButton("OK..", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  //
                  //  startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });
            alertDialog.setCancelable(true);

            alertDialog.show();  //<-- See This!
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("cap_img", "" + CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // user cancelled Image capture
                   /* Toast.makeText(getContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();*/
                } else {
                    // failed to capture image
                 /*   Toast.makeText(getContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();*/

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /////////////Show Search Bar//////////////////

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        MenuItem item = menu.findItem(R.id.search);

        //  MenuItem item = menu.findItem(R.id.spinner);
        try {
            if(searchView == null )
            searchView = new SearchView(((NavigationActivity) getActivity()).getSupportActionBar().getThemedContext());
            searchView.setIconifiedByDefault(false);
            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only
            searchView.setQueryHint(getResources().getString(R.string.hint_search));



        }catch (NullPointerException e){
            e.printStackTrace();
        }


        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);




        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {/* Log.e("querty",""+ searchView.getQuery().toString());*/

                System.out.println("on clicked: " + position);
                // lv.setVisibility(View.GONE);
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


                searchNumber=newText;

                try {

                    if (newText.trim().length() > 4) {
                        norecordtv.setVisibility(View.GONE);
                        Searchrecycler_view.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        addaNewPatient.setVisibility(View.VISIBLE);
                        todays_patient_updatetxt.setVisibility(View.GONE);

                        Log.e("onQueryTextChange", "" + newText);
                        filteredModelList = filter(patientDataforPhoneFilter, newText);// patientDataforPhoneFilter


                        if (filteredModelList.size() <= 0) {
                            showCreatePatientAlertDialog(newText);
                        } else {



                            sva.setFilter(filteredModelList);
                            Searchrecycler_view.setAdapter(sva);
                        }

                        System.out.println("on query submit: " + newText);
                        searchView.clearFocus();
                    } else {
                        Toast.makeText(getContext(), "Please Enter Min 5 Digit's to Search Records", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                System.out.println("on query submit: " + newText);


                return false;
            }
        });

        Searchrecycler_view.addOnItemTouchListener(new RecyclerTouchListener(getContext(), Searchrecycler_view, new ItemClickListener() {

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
                }


            }


            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        super.onCreateOptionsMenu(menu, inflater);
    }

    private void dateisCurrent(int position) {
        RegistrationModel book = filteredModelList.get(position);
        Intent i = new Intent(getContext(), EditPatientUpdate.class);

        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());

        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());

        i.putExtra("PHONE", book.getMobileNumber());

        i.putExtra("AGE", book.getAge());
        i.putExtra("LANGUAGE", book.getLanguage());
        i.putExtra("GENDER", book.getGender());
        i.putExtra("FOD", book.getFollowUpDate());

        i.putExtra("ACTUALFOD", book.getActualFollowupDate());

        i.putExtra("AILMENT", book.getAilments());
        i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", book.getClinicalNotes());
        i.putExtra("PRESCRIPTION", book.getPres_img());
        i.putExtra("VISITID",book.getKey_visit_id());
        Log.e("VISITID", "" + book.getKey_visit_id());
        Log.e("img", "" + book.getPres_img());
        startActivity(i);
    }

    private void dateisBefore(int position) {
        RegistrationModel book = filteredModelList.get(position);
        Intent i = new Intent(getContext(), AddPatientUpdate.class);
        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("PatientID", book.getPat_id());
        Log.e("Patientkaid", "" + book.getPat_id());
        Log.e("Patientkaid", "" + book.getVisit_date());

        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());

        i.putExtra("PHONE", book.getMobileNumber());

        i.putExtra("AGE", book.getAge());
        i.putExtra("LANGUAGE", book.getLanguage());
        i.putExtra("GENDER", book.getGender());
        i.putExtra("FOD", book.getFollowUpDate());
        i.putExtra("AILMENT", book.getAilments());
        i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", book.getClinicalNotes());
        i.putExtra("PRESCRIPTION", book.getPres_img());
        Log.e("img", "" + book.getPres_img());

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


    private class mDateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(monthOfYear + 1).append("/").append(dayOfMonth).append("/")
                    .append(year).append(" "));
            System.out.println(date.getText().toString());
        }
    }

    //This method will filter data from our database generated list according to user query By Sys Date 6/8/i Ashish
    private List<RegistrationModel> filterBySystemDate(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {


            final String dateCreatedPatient = model.getAdded_on().toLowerCase();

            final String phVisitDate = model.getVisit_date().toLowerCase();




            if (dateCreatedPatient.contains(query) || phVisitDate.contains(query)) {

                filteredModelList.add(model);
                Log.e("result", "" + dateCreatedPatient);
            }
        }
        return filteredModelList;
    }


    private void showCreatePatientAlertDialog(final String number) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_alert_profile);



       dialog.setTitle("Please Confirm ");



        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();
                dialog.dismiss();

            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchView.clearFocus();

                Intent i = new Intent(getContext(), RegistrationActivity.class);
                i.putExtra("number", number);
                startActivity(i);

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
            }catch (Exception exe){
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

    //This method will filter data from our database generated list according to user query by Phone Number 6/8/i Ashish
    private List<RegistrationModel> filter(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {
            final String number = model.getMobileNumber().toLowerCase();

            if (number.contains(query)) {

                filteredModelList.add(model);
                Log.e("result", "" + number);
            }
        }
        return filteredModelList;
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
                    Log.e("DoctorName", "" + docName);

                    Log.e("DoctorName", "phoneNumber " + phoneNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if(sqlController !=null){
                        sqlController.close();
                    }
                    dbController.close();
                }

               // showAlertDialog();
//used to send mail
              AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final String finalDocName = docName;
                final String finalPhoneNumber = phoneNumber;
                builder.setMessage("want to Send Mail!")
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

            case android.R.id.home:


                break;




        }
        return true;


    }


//sync data to server
private void sendDataToServer(final String patient_details, final String patient_visits) {

        String tag_string_req = "req_login";


        pDialog.setMessage("Sending Data..........");
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

                            Log.e("ashish", "" + patientIds_List.size());
                            int size = patientIds_List.size();
                            for(int i=0;i<size;i++){
                                String patientId=patientIds_List.get(i).getPat_id();
                                String  flag="1";
                                Log.e("Idis"+i," "+patientId);
                                dbController.FlagupdatePatientPersonal(patientId,flag);
                            }

                            Log.e("ashish", "" + getPatientVisitIdsList.size());
                            int listsize = getPatientVisitIdsList.size();
                            for(int i=0;i<listsize;i++){
                                String patientId=getPatientVisitIdsList.get(i).getPat_id();
                                String patientVisitId=getPatientVisitIdsList.get(i).getKey_visit_id();
                                String  flag="1";
                                Log.e("IdisVisit" + i, " " + patientId + "patientVisitId  "+patientVisitId);
                                dbController.FlagupdatePatientVisit(patientVisitId,flag);
                            }

                            toast = Toast.makeText(getContext(), "Data Send Successfully to Server" + msg, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();

                        }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                Map<String, String> params = new HashMap<>();

                params.put("apikey", getResources().getString(R.string.apikey));
                params.put("patient_details", patient_details);
                params.put("patient_visits", patient_visits);


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


        pDialog.setMessage("Please Wait Getting Patient Records  ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PATIENT_RECORDS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Sync Response: " + response);


                try {
                    JSONObject jObj = new JSONObject(response);

                    // Check for error node in json

                        // user successfully logged in
                        // Now store the user in SQLite

                        // Create login session


                        JSONObject user = jObj.getJSONObject("data");


                        JSONArray jsonArray = user.getJSONArray("doctor_patient_relation");
                        //  Log.e("jsonArray", "" + jsonArray);
                        JSONArray patientHistoryList = user.getJSONArray("patinet_visit_details");
                        // Log.e("jsonArray", "" + patientHistoryList);

                        setPatientPersonalList(jsonArray);
                        setPatientHistoryList(patientHistoryList);
                        // hideDialog();



                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
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

                return params;
            }

        };

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
            }
            dbController.addPatientPersoanlRecords(pat_id, doctor_id, doc_membership_id, patient_info_type_form, pat_first_name, pat_middle_name, pat_last_name,
                    pat_gender, converteddobDate, pat_age, pat_mobile_no, pat_address, pat_city_town, pat_pincode, pat_district, pref_lang, photo_name, consent,
                    special_instruction, added_by, convertedDate, convertedTime, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on, flag);

            inputPatientData.add(new RegistrationModel(pat_id, doctor_id, doc_membership_id, patient_info_type_form, pat_first_name, pat_middle_name, pat_last_name,
                    pat_gender, converteddobDate, pat_age, pat_mobile_no, pat_address, pat_city_town, pat_pincode, pat_district, pref_lang, photo_name, consent,
                    special_instruction, added_by, added_on, convertedDate, modified_by, modified_on, is_disabled, disabled_by, disabled_on, is_deleted, deleted_by, deleted_on, flag));


        }
        SessionManager session = new SessionManager(getContext());
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
            }


            String patient_info_type_form = "Electronics"; //need to add this if records are added from web service to identify the data...........

            dbController.addPatientHistoryRecords(visit_id, pat_id, ailment, convertedVisitDate, follow_up_date, follow_up_days,
                    follow_up_weeks, follow_up_months, convertedActualfodDate, notes, added_by, convertedAddedonDate, convertedAddedonTime, modified_by, modified_on, is_disabled, disabled_by, disabled_on,
                    is_deleted, deleted_by, deleted_on, flag, patient_info_type_form);


        }
        dbController.addAsync();
        hideDialog();
        makeToast("Data Downloded Successfully from server !!");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        if(sqlController != null){
            sqlController.close();
            sqlController= null;


        }
        if(connectionDetector != null){
            connectionDetector= null;
        }
        if(dbController != null)
        {
            dbController=null;

        }
        if(sva !=null){
           // sva=null;
        }
        addaNewPatient.setOnClickListener(null);
        recyclerView.setOnClickListener(null);
        //  searchView.setOnClickListener(null);
        Searchrecycler_view.addOnScrollListener(null);
        norecordtv=null;
        addaNewPatient=null;
        view = null; // now cleaning up view!
        date1=null;
        toast=null;
       // asyn_value=null;
        searchNumber=null;
        patientVisitHistorArayString=null;
        Log.e("onDetach","onDetach Home Fragment");
    }
}







