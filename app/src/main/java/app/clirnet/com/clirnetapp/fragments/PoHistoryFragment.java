package app.clirnet.com.clirnetapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.ShowPersonalDetailsActivity;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.PoHistoryAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.ItemClickListener;
import app.clirnet.com.clirnetapp.utility.MultiSpinner;
import app.clirnet.com.clirnetapp.utility.MultiSpinner2;


public class PoHistoryFragment extends Fragment implements MultiSpinner.MultiSpinnerListener, MultiSpinner2.MultiSpinnerListener {


    private OnFragmentInteractionListener mListener;
    private static Bundle healthDataBundle;
    private EditText firstName;
    private AutoCompleteTextView lastName;
    private EditText phone_no;
    private String strfname;
    private String strlname;
    private String strpno;

    private SQLController sqlController;

    private PoHistoryAdapter poHistoryAdapter;
    private RecyclerView recyclerView;
    private ImageView backChangingImages;
    private ArrayList<RegistrationModel> patientData = new ArrayList<>();
    private LinearLayout norecordtv;
    private View rootview;
    private AppController appController;
    private Button submit;
    private MultiSpinner genderSpinner;

    private int ival = 0;
    private int loadLimit = 25;

    private int[] selectedItems = {0, 0, 0, 0};
    private int[] selectedItems2 = {0, 0, 0, 0, 0, 0, 0, 0};
    private ArrayList genderList;

    private ArrayList selectedListGender;
    private ArrayList selectedAgeList;
    private MultiSpinner2 ageGapSpinner;
    private ArrayList ageList;
    private MultiAutoCompleteTextView symptomsDiagnosis;
    private DatabaseClass databaseClass;
    private ArrayList<String> mAilmemtArrayList;
    private ArrayList selectedAilmentList;
    private LinearLayoutManager mLayoutManager;

    private final int PAGE_SIZE = 2;

    private boolean isLoading = false;

    private int queryCount;
    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private String doctor_membership_number;
    private Button selectVitals, resetFilters;
    private Integer weightMinValue, weightMaxValue;
    private Integer heightMinValue, heightMaxValue;
    private Integer bmiMinValue, bmiMaxValue;
    private Integer pulseMinValue, pulseMaxValue;
    private Integer tempMinValue, tempMaxValue;
    private Integer systoleMinValue, systoleMaxValue;
    private Integer distoleMinValue, distoleMaxValue;
    private Integer sugarFpgMinValue, sugarFpgMaxValue;
    private Integer sugarPpgMinValue, sugarPpgMaxValue;

    private Integer strMinWeight;
    private Integer strMaxWeight;
    private Integer strMinHeight;
    private Integer strMaxHeight;
    private Integer strMinBmi;
    private Integer strMaxBmi;
    private Integer strMinPulse;
    private Integer strMaxPulse;
    private Integer strMinTemp;
    private Integer strMaxTemp;
    private Integer strMinSystole;
    private Integer strMaxSystole;
    private Integer strMinDistole;
    private Integer strMaxDistole;
    private Integer strMinSugarFPG;
    private Integer strMaxSugarFPG;
    private Integer strMinSugarPPG;
    private Integer strMaxSugarPPG;
    private TextView showData;



    private EditText input_min_weight;
    private EditText input_max_weight;
    private EditText input_min_height;
    private EditText input_max_height;
    private EditText input_min_bmi;
    private EditText input_max_bmi;
    private EditText input_min_pulse;
    private EditText input_max_pulse;
    private EditText input_min_temp;
    private EditText input_max_temp;
    private EditText input_min_systole;
    private EditText input_max_systole;
    private EditText input_min_distole;
    private EditText input_max_distole;
    private EditText input_min_sugarfpg;
    private EditText input_max_sugarfpg;
    private EditText input_min_sugarppg;
    private EditText input_max_sugarppg;
    private long mLastClickTime = 0;

    private StringBuilder sb = new StringBuilder();
    private String strEcg;
    private String strPft;
    private String strLipidTC;
    private String strLipidTG;
    private String strLipidLDL;
    private String strLipidVHDL;
    private String strLipidHDL;
    private String strHbA1c;
    private String strSerumUrea;
    private String strAcer;
    private String strAllergie;
    private String strNoOfSticks;
    private String strPackPerWeek;

    private String strLifeStyle;
    private String strStressLevel;
    private String strExcercise;
    private String strBingeEating;
    private String strSexuallyActive;
    private String strFoodHabit;
    private String strFoodPreference;
    private String strSmoking;
    private String strTobaco;
    private String strAlcoholConsumption;
    private String noOfSticksPerYear;
    private String noOfPegsPerYear;
    private Button btnMoreFilter;
    private String strLeftSmokingSinceYear;
    private String strLeftAlcoholSinceYear;
    private String otherTobacoTaking;
    private String strDrug;
    private String otherDrugTaking;
    private String strLactoseTolerance;
    private String strSleepStatus;
    private String strLipidTCMax;
    private String strLipidTGMax;
    private String strLipidLDLMax;
    private String strLipidVHDLMax;
    private String strLipidHDLMax;
    private String strHbA1cMax;
    private String strSerumUreaMax;
    private String strAcerMax;
    private LastnameDatabaseClass lastnameDatabaseClass;

    public PoHistoryFragment() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rootview = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootview = inflater.inflate(R.layout.fragment_po_history, container, false);

        ((NavigationActivity) getActivity()).setActionBarTitle("Patient History");

        firstName = (EditText) rootview.findViewById(R.id.firstname);

        lastName = (AutoCompleteTextView) rootview.findViewById(R.id.lastname);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        symptomsDiagnosis = (MultiAutoCompleteTextView) rootview.findViewById(R.id.symptoms);
        TextView currdate = (TextView) rootview.findViewById(R.id.sysdate);
        backChangingImages = (ImageView) rootview.findViewById(R.id.backChangingImages);
        norecordtv = (LinearLayout) rootview.findViewById(R.id.norecordtv);

        genderSpinner = (MultiSpinner) rootview.findViewById(R.id.gender);
        ageGapSpinner = (MultiSpinner2) rootview.findViewById(R.id.ageGap);


        phone_no = (EditText) rootview.findViewById(R.id.mobile_no);

        //selectVitals = (Button) rootview.findViewById(R.id.selectVitals);

        showData = (TextView) rootview.findViewById(R.id.showData);


        Button filterInvestigation = (Button) rootview.findViewById(R.id.filterInvestigation);
        Button filterHealth = (Button) rootview.findViewById(R.id.filterHealth);
        btnMoreFilter = (Button) rootview.findViewById(R.id.btnMoreFilters);

        //initalizeView(rootview);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();
        String dd = sdf.format(todayDate);


        currdate.setText("Today's Date " + dd);

        TextView privacyPolicy = (TextView) rootview.findViewById(R.id.privacyPolicy);
        TextView termsAndCondition = (TextView) rootview.findViewById(R.id.termsandCondition);

        //open privacy policy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);

            }
        });

        //open Terms and Condition page
        termsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), TermsCondition.class);
                startActivity(intent);

            }
        });

        try {
            sqlController = new SQLController(getContext().getApplicationContext());
            sqlController.open();
            if (databaseClass == null) {
                databaseClass = new DatabaseClass(getContext());
            }
            if (bannerClass == null) {
                bannerClass = new BannerClass(getContext());
            }
            if (appController == null) {
                appController = new AppController();
            }
            if(lastnameDatabaseClass==null){
               lastnameDatabaseClass=new LastnameDatabaseClass(getContext());
            }
            bannerimgNames = bannerClass.getImageName();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

            ArrayList<HashMap<String, Integer>> demoList = sqlController.getMinMaxVitals();

            if (demoList.size() > 0) {
                strMinWeight = demoList.get(0).get("MINWEIGHT");
                strMaxWeight = demoList.get(0).get("MAXWEIGHT");
                strMinHeight = demoList.get(0).get("MINHEIGHT");
                strMaxHeight = demoList.get(0).get("MAXHEIGHT");
                strMinBmi = demoList.get(0).get("MINBMI");
                strMaxBmi = demoList.get(0).get("MAXBMI");
                strMinPulse = demoList.get(0).get("MINPULSE");
                strMaxPulse = demoList.get(0).get("MAXPULSE");
                strMinTemp = demoList.get(0).get("MINTEMP");
                strMaxTemp = demoList.get(0).get("MAXTEMP");
                strMinSystole = demoList.get(0).get("MINSYSTOLE");
                strMaxSystole = demoList.get(0).get("MAXSYSTOLE");
                strMinDistole = demoList.get(0).get("MINDISTOLE");
                strMaxDistole = demoList.get(0).get("MAXDISTOLE");
                strMinSugarFPG = demoList.get(0).get("MINSUGARFPG");
                strMaxSugarFPG = demoList.get(0).get("MAXSUGARFPG");
                strMinSugarPPG = demoList.get(0).get("MINSUGARPPG");
                strMaxSugarPPG = demoList.get(0).get("MAXSUGARPPG");
                //Log.e("strMin", " " + strMinSugarFPG + " " + strMaxSugarFPG + " " + strMinSugarPPG + "  " + strMaxSugarPPG + "  " + strMinBmi + "  " + strMaxBmi);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Frgament" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        patientData.clear(); //This method will clear all previous data from  Array list  24-8-2016

        setAilmentData();

        submit = (Button) rootview.findViewById(R.id.submit);

        submit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {


                    submit.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }

                return false;
            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patientData.clear(); //This method will clear all previous data from  Array list  24-8-2016
                if (poHistoryAdapter != null) {
                    poHistoryAdapter.notifyDataSetChanged();
                }
                        //data from health dialog fragment
                if (healthDataBundle != null) {

                    strSmoking = healthDataBundle.getString("SMOKING");
                    noOfSticksPerYear = healthDataBundle.getString("NUMODSTICKS");
                    strLeftSmokingSinceYear = healthDataBundle.getString("SMOKELEFTSINCE");

                    strAlcoholConsumption = healthDataBundle.getString("DRINKING");
                    noOfPegsPerYear = healthDataBundle.getString("NUMOFPEGS");
                    strLeftAlcoholSinceYear = healthDataBundle.getString("DRINKINGLEFTSINCE");
                    strTobaco = healthDataBundle.getString("TOBACO");
                    otherTobacoTaking = healthDataBundle.getString("OTHERTOBACO");
                    strDrug = healthDataBundle.getString("DRUG");
                    otherDrugTaking = healthDataBundle.getString("OTHERDRUG");
                    strFoodHabit = healthDataBundle.getString("FOODHABIT");
                    strFoodPreference = healthDataBundle.getString("FOODPREFERNCE");
                    strBingeEating = healthDataBundle.getString("BINFEEATING");
                    strLactoseTolerance = healthDataBundle.getString("LACTOSETOLERNCE");
                    strLifeStyle = healthDataBundle.getString("LIFESTYLE");

                    strSleepStatus = healthDataBundle.getString("SLEEP");
                    strStressLevel = healthDataBundle.getString("STRESSLEVEL");
                    strSexuallyActive = healthDataBundle.getString("SEXUALACTIVITY");
                    strExcercise = healthDataBundle.getString("EXCERCISE");
                    strAllergie = healthDataBundle.getString("ALLERGIES");
                  //  Log.e("strAllergie", "  " + strAllergie);
                }


                strfname = firstName.getText().toString().trim();
                strlname = lastName.getText().toString().trim();
                strpno = phone_no.getText().toString().trim();
                String strAilment = symptomsDiagnosis.getText().toString().trim();

                //remove comma occurance from string
                strAilment = appController.removeCommaOccurance(strAilment);
                //Remove spaces between text if more than 2 white spaces found 12-12-2016
                strAilment = strAilment.replaceAll("\\s+", " ");

                String delimiter = ",";
                String[] temp = strAilment.split(delimiter);
                selectedAilmentList = new ArrayList();
             /* print substrings */
                Collections.addAll(selectedAilmentList, temp);

                try {
                    ival = 0;
                    loadLimit = 25;
                    patientData = sqlController.getFilterDatanew(strfname, strlname, strpno, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit, weightMinValue, weightMaxValue, heightMinValue, heightMaxValue, bmiMinValue, bmiMaxValue,
                            pulseMinValue, pulseMaxValue, tempMinValue, tempMaxValue, systoleMinValue, systoleMaxValue, distoleMinValue, distoleMaxValue, sugarFpgMinValue, sugarFpgMaxValue, sugarPpgMinValue, sugarPpgMaxValue, strLipidTC,strLipidTCMax, strLipidTG,strLipidTGMax, strLipidLDL,strLipidLDLMax, strLipidVHDL,strLipidVHDLMax,strLipidHDL,strLipidHDLMax, strHbA1c, strHbA1cMax, strSerumUrea, strSerumUreaMax, strAcer, strAcerMax,strEcg, strPft
                            , strSmoking, noOfSticksPerYear, strLeftSmokingSinceYear, strAlcoholConsumption, noOfPegsPerYear, strLeftAlcoholSinceYear, strTobaco, otherTobacoTaking, strDrug, otherDrugTaking, strFoodHabit, strFoodPreference, strBingeEating, strLactoseTolerance, strLifeStyle, strSleepStatus, strStressLevel, strSexuallyActive, strExcercise, strAllergie);


                    //    patientData = sqlController.getFilterDatanew(strfname, strlname, selectedListGender.get(i).toString(), strpno, strage);
                    queryCount = sqlController.getCountResult();
                    // queryCount = 70;
                    // Log.e("queryCount", "" + patientData.size()+"  "+queryCount );

                    //int beforeFilterCount = patientData.size();

                    if (patientData.size() > 0) {
                        removeDuplicate(patientData);
                    }

                    // int afterFilterCount = patientData.size();

                    // int totalFilterDataCount = beforeFilterCount - afterFilterCount;

                    //queryCount = queryCount - totalFilterDataCount;

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Fragment" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                } finally {
                    if (sqlController != null) {
                        sqlController.close();
                    }
                }

                int count = patientData.size();
                try {
                    if (count > 0) {

                        recyclerView.setVisibility(View.VISIBLE);
                        norecordtv.setVisibility(View.GONE);


                        poHistoryAdapter = new PoHistoryAdapter(patientData, queryCount);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(poHistoryAdapter);
                        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);


                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        norecordtv.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Fragment" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }

            }

        });


        recyclerView.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getContext(), recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                //redirect to ShowPersonalDetailsActivity 02-11-2016
                recyclerViewOnClick(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        setUpSpinner();
        setupAnimation();
        //setValueToSeekBarFromDb();

        btnMoreFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {


                    showVitalsDialogBox();

                    btnMoreFilter.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnMoreFilter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        resetFilters = (Button) rootview.findViewById(R.id.resetFilters);

        resetFilters.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    reseFiltersData();

                    resetFilters.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    resetFilters.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        filterInvestigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInvestigationDialog();
            }
        });

        filterHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                HealthVitalsDialogFragment testDialog = new HealthVitalsDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putBundle("FILTERDATA", healthDataBundle);
                testDialog.setRetainInstance(true);
                testDialog.setArguments(bundle);
                //testDialog.setTargetFragment(getContext(), 0);
                Log.e("strPatientId", " " + healthDataBundle);
                testDialog.show(fm, "fragment_name");

            }
        });

        return rootview;
    }


    private void setAilmentData() {
        try {
            databaseClass.openDataBase();
            mAilmemtArrayList = databaseClass.getAilmentsListNew();
            ArrayList<String> mSymptomsList = lastnameDatabaseClass.getSymptoms();
            ArrayList<String> mDiagnosisList = lastnameDatabaseClass.getDiagnosis();

            mAilmemtArrayList.addAll(mSymptomsList);
            mAilmemtArrayList.addAll(mDiagnosisList);

            ArrayList<String> mLastNameList = lastnameDatabaseClass.getLastNameNew();

            if (mAilmemtArrayList.size() > 0) {

                //this code is for setting list to auto complete text view  8/6/16
                ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                symptomsDiagnosis.setThreshold(1);

                symptomsDiagnosis.setAdapter(adp);
                symptomsDiagnosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
            if(mLastNameList.size()>0){
                ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mLastNameList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                lastName.setThreshold(1);

                lastName.setAdapter(adp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Po History Fragment" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (databaseClass != null) {
                databaseClass.close();
                databaseClass = null;
            }
        }
    }

    private void setUpSpinner() {

        selectedListGender = new ArrayList();
        selectedAgeList = new ArrayList();

        genderList = new ArrayList();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Transgender");

        genderSpinner.setItems(genderList, "Select Gender", this);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                //  selectColoursButton.setText(al.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });
        ageList = new ArrayList();
        ageList.add("0-5");
        ageList.add("5-15");
        ageList.add("15-25");
        ageList.add("25-35");
        ageList.add("35-45");
        ageList.add("45-55");
        ageList.add("55-65");
        ageList.add("65-Above");
        ageGapSpinner.setItems(ageList, "Select Age Group", this);

        ageGapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                //Log.e("", "" + (ageList.get(position).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });
    }

    private void recyclerViewOnClick(int position) {

        RegistrationModel book = patientData.get(position);

        Intent i = new Intent(getContext().getApplicationContext(), ShowPersonalDetailsActivity.class);

        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());
        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());
        i.putExtra("PHONE", book.getMobileNumber());
        i.putExtra("PHONETYPE", book.getPhone_type());
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
        i.putExtra("VISITID", book.getKey_visit_id());
        i.putExtra("VISITDATE", book.getVisit_date());
        i.putExtra("ADDRESS", book.getAddress());
        i.putExtra("CITYORTOWN", book.getCityortown());
        i.putExtra("DISTRICT", book.getDistrict());
        i.putExtra("PIN", book.getPin_code());
        i.putExtra("STATE", book.getState());
        i.putExtra("WEIGHT", book.getWeight());
        i.putExtra("PULSE", book.getPulse());
        i.putExtra("BP", book.getBp());
        i.putExtra("LOWBP", book.getlowBp());
        i.putExtra("TEMPRATURE", book.getTemprature());
        i.putExtra("SUGAR", book.getSugar());
        i.putExtra("SYMPTOMS", book.getSymptoms());
        i.putExtra("DIGNOSIS", book.getDignosis());
        i.putExtra("TESTS", book.getTests());
        i.putExtra("DRUGS", book.getDrugs());
        i.putExtra("BMI", book.getBmi());
        i.putExtra("ALTERNATENUMBER", book.getAlternatePhoneNumber());
        i.putExtra("ALTERNATENUMBERTYPE", book.getAlternatePhoneType());
        i.putExtra("HEIGHT", book.getHeight());
        i.putExtra("SUGARFASTING", book.getSugarFasting());
        i.putExtra("UID", book.getUid());
        i.putExtra("EMAIL", book.getEmail());
        i.putExtra("CALLEDFROM", "1");
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);


    }


    /////////////Show Search Bar//////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.other_navigation, menu);

        //  MenuItem item = menu.findItem(R.id.spinner);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        // Handle action bar actions click
        return true;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        selectedListGender.clear();

        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedItems[i] = 1;
                // System.out.println("______________________" + genderList.get(i));
                String selGender = genderList.get(i).toString();

                selectedListGender.add(selGender);

            } else selectedItems[i] = 0;
        }
       /* for (int selectedItem : selectedItems) {
            // if(selectedItems[i]==1)
            // System.out.println(al.get(i));
            //  selectedListGender.add(genderList.get(i).toString());
        }*/
    }

    @Override
    public void onItemsSelected1(boolean[] selected) {
        selectedAgeList.clear();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedItems2[i] = 1;
                // System.out.println("______________________2" + ageList.get(i));

                String ageString = ageList.get(i).toString();
                selectedAgeList.add(ageString);

            } else selectedItems2[i] = 0;
        }

    }

    public void updateDisplay(Bundle bundle) {
        //Log.e("Dialog 5 ", "  Dialog is Clicked " + bundle);
        healthDataBundle = bundle;
    }

   /* @Override
    public void setOnSubmitListener(String arg) {
        Log.e("Dialog ","  Dialog is Clicked "+arg);
    }
*/

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setupAnimation() {

        try {
            appController.setUpAdd(getContext(), bannerimgNames, backChangingImages, doctor_membership_number, "POHistory Fragment");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removeDuplicate(final List<RegistrationModel> al) {

        for (int i = 0; i < al.size() - 1; i++) {

            String element = al.get(i).getPat_id();
            // Log.e("element", "" + element);
            for (int j = i + 1; j < al.size(); j++) {
                if (element.equals(al.get(j).getPat_id())) {
                    // Log.e("element1", "" + al.get(j).getPat_id());
                    al.remove(j);
                    j--;

                }
            }
        }
        //  System.out.println(al);
    }


    private final RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
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
            //Log.e("visibleItemCount", "" + visibleItemCount + " totalItemCount  " + totalItemCount + " firstVisibleItemPosition " + firstVisibleItemPosition);

            boolean isLastPage = false;
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {

                    isLoading = true;
                    try {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                loadDataForAdapter();
                            }
                        }, 2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    private void loadDataForAdapter() {

        isLoading = false;
        ival = ival + 25;
        //  int beforeFilterCount = 0;

        List<RegistrationModel> memberList = new ArrayList<>();
        int end = 0;
        try {
            if (poHistoryAdapter != null) {
                int index = poHistoryAdapter.getItemCount();
                end = index + PAGE_SIZE;
            }
            //  Log.e("index", "" + index + " " + end + " size is " + queryCount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (end <= queryCount) {
            try {
                if (sqlController != null) {
                    // memberList = sqlController.getFilterDatanew(strfname, strlname, strpno, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit, weightMinValue, weightMaxValue, heightMinValue, heightMaxValue, bmiMinValue, bmiMaxValue,
                    //   pulseMinValue, pulseMaxValue, tempMinValue, tempMaxValue, systoleMinValue, systoleMaxValue, distoleMinValue, distoleMaxValue, sugarFpgMinValue, sugarFpgMaxValue, sugarPpgMinValue, sugarPpgMaxValue);
                    //   memberList = sqlController.getFilterDatanew(strfname, strlname, strpno, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit, weightMinValue, weightMaxValue, heightMinValue, heightMaxValue, bmiMinValue, bmiMaxValue,
                    //       pulseMinValue, pulseMaxValue, tempMinValue, tempMaxValue, systoleMinValue, systoleMaxValue, distoleMinValue, distoleMaxValue, sugarFpgMinValue, sugarFpgMaxValue, sugarPpgMinValue, sugarPpgMaxValue,strLipidTC,strLipidTG,strLipidLDL,strLipidVHDL,strLipidHDL,strHbA1c,strSerumUrea,strAcer,strEcg,strPft);
                    memberList = sqlController.getFilterDatanew(strfname, strlname, strpno, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit, weightMinValue, weightMaxValue, heightMinValue, heightMaxValue, bmiMinValue, bmiMaxValue,
                            pulseMinValue, pulseMaxValue, tempMinValue, tempMaxValue, systoleMinValue, systoleMaxValue, distoleMinValue, distoleMaxValue, sugarFpgMinValue, sugarFpgMaxValue, sugarPpgMinValue, sugarPpgMaxValue, strLipidTC,strLipidTCMax, strLipidTG,strLipidTGMax, strLipidLDL,strLipidLDLMax, strLipidVHDL,strLipidVHDLMax,strLipidHDL,strLipidHDLMax, strHbA1c, strHbA1cMax, strSerumUrea, strSerumUreaMax, strAcer, strAcerMax,strEcg, strPft
                            , strSmoking, noOfSticksPerYear, strLeftSmokingSinceYear, strAlcoholConsumption, noOfPegsPerYear, strLeftAlcoholSinceYear, strTobaco, otherTobacoTaking, strDrug, otherDrugTaking, strFoodHabit, strFoodPreference, strBingeEating, strLactoseTolerance, strLifeStyle, strSleepStatus, strStressLevel, strSexuallyActive, strExcercise, strAllergie);

                    queryCount = sqlController.getCountResult();
                    // beforeFilterCount = memberList.size();

                    if (memberList.size() > 0) {
                        removeDuplicate(memberList);
                    }
                }
            } catch (Exception ex) {
                //catch all the ones I didn't think of.
                ex.printStackTrace();
            }
            try {
                //  int afterFilterCount = memberList.size();

                // int totalFilterDataCount = beforeFilterCount - afterFilterCount;

                //  queryCount = queryCount - totalFilterDataCount;
                /// Log.e("poHistoryAdapter","  "+poHistoryAdapter);
                if (poHistoryAdapter != null) {
                    poHistoryAdapter.addAll(memberList);

                    if (end >= queryCount) {
                        poHistoryAdapter.setLoading(false);

                    }
                }
            } catch (Exception e) {
                appController.appendLog(appController.getDateTime() + " " + "/ " + "PoHistory Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        //  Log.e("onPause"," "+"onPause");
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(firstName.getWindowToken(), 0);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Log.e("onResume"," "+"onResume");
        // Tracking the screen view
        AppController.getInstance().trackScreenView("Po History Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        rootview = null; // view cleaning up!

        if (sqlController != null) {
            sqlController.close();
            sqlController = null;
        }
        if (appController != null) {
            appController = null;
        }
        if (databaseClass != null) {
            databaseClass = null;
        }
        if (bannerClass != null) {
            bannerClass = null;
        }
        if (poHistoryAdapter != null) {
            poHistoryAdapter = null;
        }
        if(lastnameDatabaseClass!=null){
            lastnameDatabaseClass=null;
        }
        bannerimgNames = null;

        patientData.clear();
        patientData = null;

        recyclerView.setOnClickListener(null);
        //  searchView.setOnClickListener(null);
        norecordtv = null;

        recyclerView = null;
        strfname = null;
        strlname = null;
        strpno = null;
        submit = null;
        selectedListGender = null;
        selectedAgeList = null;
        ageGapSpinner = null;
        ageList = null;
        symptomsDiagnosis = null;
        mAilmemtArrayList = null;

        selectedItems = null;
        selectedItems2 = null;
        genderList = null;
        selectedAilmentList = null;
        mLayoutManager = null;
        doctor_membership_number = null;
        backChangingImages = null;
        firstName = null;
        lastName = null;
        phone_no = null;
        genderSpinner = null;
        clearVitalsValue();
        reseFiltersData();
        healthDataBundle = null;
    }

    private void showVitalsDialogBox() {

        final Dialog dialog = new Dialog(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View f = factory.inflate(R.layout.filter_vitals_dialog_pohistory, null);

        input_min_weight = (EditText) f.findViewById(R.id.input_min_weight);
        input_max_weight = (EditText) f.findViewById(R.id.input_max_weight);
        input_min_height = (EditText) f.findViewById(R.id.input_min_height);
        input_max_height = (EditText) f.findViewById(R.id.input_max_height);
        input_min_bmi = (EditText) f.findViewById(R.id.input_min_bmi);
        input_max_bmi = (EditText) f.findViewById(R.id.input_max_bmi);
        input_min_pulse = (EditText) f.findViewById(R.id.input_min_pulse);
        input_max_pulse = (EditText) f.findViewById(R.id.input_max_pulse);
        input_min_temp = (EditText) f.findViewById(R.id.input_min_temp);
        input_max_temp = (EditText) f.findViewById(R.id.input_max_temp);
        input_min_systole = (EditText) f.findViewById(R.id.input_min_systole);
        input_max_systole = (EditText) f.findViewById(R.id.input_max_systole);
        input_min_distole = (EditText) f.findViewById(R.id.input_min_distole);
        input_max_distole = (EditText) f.findViewById(R.id.input_max_distole);
        input_min_sugarfpg = (EditText) f.findViewById(R.id.input_min_sugarfpg);
        input_max_sugarfpg = (EditText) f.findViewById(R.id.input_max_sugarfpg);
        input_min_sugarppg = (EditText) f.findViewById(R.id.input_min_sugarppg);
        input_max_sugarppg = (EditText) f.findViewById(R.id.input_max_sugarppg);

        /* Clear The entered viatls for next search*/
        //clearVitalsValue2();
        sb.setLength(0);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);

        dialog.setTitle("Filter Vitals");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);
        Button dialogButtonCancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) f.findViewById(R.id.customDialogOk);

        //Log.e("weightMinValue", "  " + weightMinValue);

        if (weightMinValue != null && weightMaxValue != null) {
            input_min_weight.setText(weightMinValue.toString());
            input_max_weight.setText(weightMaxValue.toString());
            //seekbarWeight.setSelectedMinValue(weightMinValue);
           // seekbarWeight.setSelectedMaxValue(weightMaxValue);
        }
        if (heightMinValue != null && heightMaxValue != null) {
            input_min_height.setText(heightMinValue.toString());
            input_max_height.setText(heightMaxValue.toString());
           // seekbarHeight.setSelectedMinValue(heightMinValue);
           // seekbarHeight.setSelectedMaxValue(heightMaxValue);
        }
        if (bmiMinValue != null && bmiMaxValue != null) {
            input_min_bmi.setText(bmiMinValue.toString());
            input_max_bmi.setText(bmiMaxValue.toString());
           // seekbarBmi.setSelectedMinValue(bmiMinValue);
           // seekbarBmi.setSelectedMaxValue(bmiMaxValue);
        }

        if (pulseMinValue != null && pulseMaxValue != null) {
            input_min_pulse.setText(pulseMinValue.toString());
            input_max_pulse.setText(pulseMaxValue.toString());
           // seekbarPulse.setSelectedMinValue(pulseMinValue);
           // seekbarPulse.setSelectedMaxValue(pulseMaxValue);
        }
        if (tempMinValue != null && tempMaxValue != null) {
            input_min_temp.setText(tempMinValue.toString());
            input_max_temp.setText(tempMaxValue.toString());
          //  seekbarTemp.setSelectedMinValue(tempMinValue);
           // seekbarTemp.setSelectedMaxValue(tempMaxValue);
        }
        if (systoleMinValue != null && systoleMaxValue != null) {
            input_min_systole.setText(systoleMinValue.toString());
            input_max_systole.setText(systoleMaxValue.toString());
           // seekbarSystole.setSelectedMinValue(systoleMinValue);
          //  seekbarSystole.setSelectedMaxValue(systoleMaxValue);
        }
        if (distoleMinValue != null && distoleMaxValue != null) {
            input_min_distole.setText(distoleMinValue.toString());
            input_max_distole.setText(distoleMaxValue.toString());
            //seekbarDiastole.setSelectedMinValue(distoleMinValue);
           // seekbarDiastole.setSelectedMaxValue(distoleMaxValue);
        }
        if (sugarFpgMinValue != null && sugarFpgMaxValue != null) {
            input_min_sugarfpg.setText(sugarFpgMinValue.toString());
            input_max_sugarfpg.setText(sugarFpgMaxValue.toString());
           // seekbarSugarFpg.setSelectedMinValue(sugarFpgMinValue);
           // seekbarSugarFpg.setSelectedMaxValue(sugarFpgMaxValue);
        }
        if (sugarPpgMinValue != null && sugarPpgMaxValue != null) {
            input_min_sugarppg.setText(sugarPpgMinValue.toString());
            input_max_sugarppg.setText(sugarPpgMaxValue.toString());
           // seekbarSugarPpg.setSelectedMinValue(sugarPpgMinValue);
           // seekbarSugarPpg.setSelectedMaxValue(sugarPpgMaxValue);
        }

      /*  input_min_weight.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinWeight && i <= strMaxWeight) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarWeight.setSelectedMinValue(i);
                        input_max_weight.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_weight.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinWeight && i <= strMaxWeight) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarWeight.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        input_min_height.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinHeight && i <= strMaxHeight) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarHeight.setSelectedMinValue(i);
                        input_max_height.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_height.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinHeight && i <= strMaxHeight) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarHeight.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_min_bmi.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinBmi && i <= strMaxBmi) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarBmi.setSelectedMinValue(i);
                        input_max_bmi.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_bmi.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinBmi && i <= strMaxBmi) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarBmi.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        input_min_pulse.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinPulse && i <= strMaxPulse) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarPulse.setSelectedMinValue(i);
                        input_max_pulse.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_pulse.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinPulse && i <= strMaxPulse) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarPulse.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        input_min_temp.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinTemp && i <= strMaxTemp) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarTemp.setSelectedMinValue(i);
                        input_max_temp.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_temp.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMaxTemp && i <= strMaxTemp) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarTemp.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        input_min_systole.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinSystole && i <= strMaxSystole) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarSystole.setSelectedMinValue(i);
                        input_max_systole.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_systole.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinSystole && i <= strMaxSystole) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarSystole.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_min_distole.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinDistole && i <= strMaxDistole) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarDiastole.setSelectedMinValue(i);
                        input_max_distole.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_distole.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinDistole && i <= strMaxDistole) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarDiastole.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_min_sugarfpg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinSugarFPG && i <= strMaxSugarFPG) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarSugarFpg.setSelectedMinValue(i);
                        input_max_sugarfpg.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_sugarfpg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinSugarFPG && i <= strMaxSugarFPG) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarSugarFpg.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_min_sugarppg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinSugarPPG && i <= strMaxSugarPPG) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarSugarPpg.setSelectedMinValue(i);
                        input_max_sugarppg.setError(null);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        input_max_sugarppg.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    int i = Integer.parseInt(s.toString());
                    if (i >= strMinSugarPPG && i <= strMaxSugarPPG) {
                        //seekbarWeight.setRangeValues(i, strMaxWeight); // This ensures 0-120 value for seekbar
                        seekbarSugarPpg.setSelectedMaxValue(i);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
*/

        /////////////////////////////////////
        // Log.e("strMaxWeight"," "+strMinWeight +" "+strMaxWeight);

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

                String valMinWeight = input_min_weight.getText().toString();
                String valMaxWeight = input_max_weight.getText().toString();

                if (!valMinWeight.equals("") && !valMaxWeight.equals("")) {

                    weightMinValue = Integer.valueOf(valMinWeight);
                    weightMaxValue = Integer.valueOf(valMaxWeight);

                    sb.append(" Weight: Min ").append(weightMinValue).append(" - Max ").append(weightMaxValue).append(" ;");

                    if (weightMaxValue < weightMinValue) {
                        input_max_weight.setError("Value must be greater from min value");
                        return;
                    } else if (weightMinValue > weightMaxValue) {
                        input_min_weight.setError("Value must be small from max value");
                        return;
                    } else if (weightMinValue > strMaxWeight) {
                        input_min_weight.setError("Entered value must be small from  " + strMaxWeight);
                        return;
                    } else if (weightMaxValue > strMaxWeight) {
                        input_max_weight.setError("Entered value must be small from  " + strMaxWeight);
                        return;
                    }
                }

                String valMinHeight = input_min_height.getText().toString();
                String valMaxHeight = input_max_height.getText().toString();

                if (!valMinHeight.equals("") && !valMaxHeight.equals("")) {

                    heightMinValue = Integer.valueOf(valMinHeight);
                    heightMaxValue = Integer.valueOf(valMaxHeight);

                    sb.append("  ");
                    sb.append(" Height: Min ").append(heightMinValue).append(" - Max ").append(heightMaxValue).append(" ;");

                    if (heightMaxValue < heightMinValue) {
                        input_max_height.setError("Value must be greater from min value");
                        return;
                    } else if (heightMinValue > heightMaxValue) {
                        input_min_height.setError("Value must be small from max value");
                        return;
                    }
                }


                String valMinBmi = input_min_bmi.getText().toString();
                String valMaxBmi = input_max_bmi.getText().toString();
                if (!valMinBmi.equals("") && !valMaxBmi.equals("")) {

                    bmiMinValue = Integer.valueOf(valMinBmi);
                    bmiMaxValue = Integer.valueOf(valMaxBmi);
                    sb.append("  ");
                    sb.append(" Bmi: Min ").append(bmiMinValue).append(" - Max ").append(bmiMaxValue).append(" ;");

                    if (bmiMaxValue < bmiMinValue) {
                        input_max_bmi.setError("Value must be greater from min value");
                        return;
                    } else if (bmiMinValue > bmiMaxValue) {
                        input_min_bmi.setError("Value must be small from max value");
                        return;
                    } else if (bmiMaxValue > strMaxBmi) {
                        input_max_height.setError("Entered value must be small from  " + strMaxBmi);
                        return;
                    }
                }

                String valMinPulse = input_min_pulse.getText().toString();
                String valMaxPulse = input_max_pulse.getText().toString();
                if (!valMinPulse.equals("") && !valMaxPulse.equals("")) {

                    pulseMinValue = Integer.valueOf(valMinPulse);
                    pulseMaxValue = Integer.valueOf(valMaxPulse);
                    sb.append("  ");
                    sb.append(" Pulse: Min ").append(pulseMinValue).append(" -  Max ").append(pulseMaxValue).append(" ;");

                    if (pulseMaxValue < pulseMinValue) {
                        input_max_bmi.setError("Value must be greater from min value");
                        return;
                    } else if (pulseMinValue > pulseMaxValue) {
                        input_min_bmi.setError("Value must be small from max value");
                        return;
                    } else if (pulseMaxValue > strMaxPulse) {
                        input_max_height.setError("Entered value must be small from  " + strMaxPulse);
                        return;
                    }
                }
                String valMinTemp = input_min_temp.getText().toString();
                String valMaxTemp = input_max_temp.getText().toString();
                if (!valMinTemp.equals("") && !valMaxTemp.equals("")) {

                    tempMinValue = Integer.valueOf(valMinTemp);
                    tempMaxValue = Integer.valueOf(valMaxTemp);

                    sb.append("  ");
                    sb.append(" Temp: Min ").append(tempMinValue).append(" -  Max ").append(tempMaxValue).append(" ;");

                    if (tempMaxValue < tempMinValue) {
                        input_max_bmi.setError("Value must be greater from min value");
                        return;
                    } else if (tempMinValue > tempMaxValue) {
                        input_min_bmi.setError("Value must be small from max value");
                        return;
                    } else if (tempMaxValue > strMaxTemp) {
                        input_max_height.setError("Entered value must be small from  " + strMaxTemp);
                        return;
                    }
                }
                String valMinSystole = input_min_systole.getText().toString();
                String valMaxSystole = input_max_systole.getText().toString();
                if (!valMinSystole.equals("") && !valMaxSystole.equals("")) {

                    systoleMinValue = Integer.valueOf(valMinSystole);
                    systoleMaxValue = Integer.valueOf(valMaxSystole);

                    sb.append("  ");
                    sb.append(" Systole: Min ").append(systoleMinValue).append(" -  Max  ").append(systoleMaxValue).append(" ;");

                    if (systoleMaxValue < systoleMinValue) {
                        input_max_bmi.setError("Value must be greater from min value");
                        return;
                    } else if (systoleMinValue > systoleMaxValue) {
                        input_min_bmi.setError("Value must be small from max value");
                        return;
                    } else if (systoleMaxValue > strMaxSystole) {
                        input_max_height.setError("Entered value must be small from  " + strMaxSystole);
                        return;
                    }
                }
                String valMinDistole = input_min_distole.getText().toString();
                String valMaxDistole = input_max_distole.getText().toString();
                if (!valMinDistole.equals("") && !valMaxDistole.equals("")) {

                    distoleMinValue = Integer.valueOf(valMinDistole);
                    distoleMaxValue = Integer.valueOf(valMaxDistole);

                    sb.append("  ");
                    sb.append(" Distole: Min ").append(distoleMinValue).append(" -  Max ").append(distoleMaxValue).append(" ;");

                    if (distoleMaxValue < distoleMinValue) {
                        input_max_systole.setError("Value must be greater from min value");
                        return;
                    } else if (distoleMinValue > distoleMaxValue) {
                        input_min_systole.setError("Value must be small from max value");
                        return;
                    } else if (distoleMaxValue > strMaxDistole) {
                        input_max_systole.setError("Entered value must be small from  " + strMaxDistole);
                        return;
                    }
                }
                String valMinSugarFpg = input_min_sugarfpg.getText().toString();
                String valMaxSugarFpg = input_max_sugarfpg.getText().toString();
                if (!valMinSugarFpg.equals("") && !valMaxSugarFpg.equals("")) {

                    sugarFpgMinValue = Integer.valueOf(valMinSugarFpg);
                    sugarFpgMaxValue = Integer.valueOf(valMaxSugarFpg);

                    sb.append("  ");
                    sb.append(" SugarFpg: Min ").append(sugarFpgMinValue).append(" -  Max ").append(sugarFpgMaxValue).append(" ;");


                    if (sugarFpgMaxValue < sugarFpgMinValue) {
                        input_max_sugarfpg.setError("Value must be greater from min value");
                        return;
                    } else if (sugarFpgMinValue > sugarFpgMaxValue) {
                        input_min_sugarfpg.setError("Value must be small from max value");
                        return;
                    } else if (sugarFpgMaxValue > strMaxSugarFPG) {
                        input_max_sugarfpg.setError("Entered value must be small from  " + strMaxSugarFPG);
                        return;
                    }
                }
                String valMinSugarPpg = input_min_sugarppg.getText().toString();
                String valMaxSugarPpg = input_max_sugarppg.getText().toString();
                if (!valMinSugarPpg.equals("") && !valMaxSugarPpg.equals("")) {

                    sugarPpgMinValue = Integer.valueOf(valMinSugarPpg);
                    sugarPpgMaxValue = Integer.valueOf(valMaxSugarPpg);

                    sb.append("  ");
                    sb.append("SugarPpg: Min ").append(sugarPpgMinValue).append(" -  Max ").append(sugarPpgMaxValue).append(" ;");


                    if (sugarPpgMaxValue < sugarPpgMinValue) {
                        input_max_sugarppg.setError("Value must be greater from min value");
                        return;
                    } else if (sugarPpgMinValue > sugarPpgMaxValue) {
                        input_min_sugarppg.setError("Value must be small from max value");
                        return;
                    } else if (sugarPpgMaxValue > strMaxSugarPPG) {
                        input_max_sugarppg.setError("Entered value must be small from  " + strMaxSugarPPG);
                        return;
                    }
                }
                showData.setText(sb);
               // Log.e("valMinWeight ",""+weightMinValue + ""+weightMaxValue);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

   /* private void setValueToSeekBarFromDb() {

        if (strMinWeight == 0 && strMaxWeight == 0) {
            seekbarWeight.setVisibility(View.GONE);
        } else {

            seekbarWeight.setRangeValues(strMinWeight, strMaxWeight);// if we want to set progrmmatically set range of seekbar

            seekbarWeight.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {


                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    input_min_weight.setText(minValue.toString());
                    input_max_weight.setText(maxValue.toString());
                }

            });
        }
        if (strMinHeight == 0 && strMaxHeight == 0) {
            seekbarHeight.setVisibility(View.GONE);
        } else {
            seekbarHeight.setRangeValues(strMinHeight, strMaxHeight);

            seekbarHeight.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {


                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

                    input_min_height.setText(minValue.toString());
                    input_max_height.setText(maxValue.toString());
                }

            });
        }
        if (strMinBmi == 0 && strMaxBmi == 0) {
            seekbarBmi.setVisibility(View.GONE);
        } else {

            seekbarBmi.setRangeValues(strMinBmi, strMaxBmi);

            seekbarBmi.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Integer minValue, Integer maxValue) {

                    input_min_bmi.setText(minValue.toString());
                    input_max_bmi.setText(maxValue.toString());

                }
            });
        }
        if (strMinPulse == 0 && strMaxPulse == 0) {
            seekbarPulse.setVisibility(View.GONE);
        } else {

            seekbarPulse.setRangeValues(strMinPulse, strMaxPulse);
            seekbarPulse.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Integer minValue, Integer maxValue) {
                    input_min_pulse.setText(minValue.toString());
                    input_max_pulse.setText(maxValue.toString());

                }
            });
        }
        if (strMinTemp == 0 && strMaxTemp == 0) {
            seekbarTemp.setVisibility(View.GONE);
        } else {

            seekbarTemp.setRangeValues(strMinTemp, strMaxTemp);
            seekbarTemp.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    input_min_temp.setText(minValue.toString());
                    input_max_temp.setText(maxValue.toString());
                }
            });
        }
        if (strMinSystole == 0 && strMaxSystole == 0) {
            seekbarSystole.setVisibility(View.GONE);
        } else {
            seekbarSystole.setRangeValues(strMinSystole, strMaxSystole);
            seekbarSystole.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    input_min_systole.setText(minValue.toString());
                    input_max_systole.setText(maxValue.toString());
                }
            });
        }
        if (strMinDistole == 0 && strMaxDistole == 0) {
            seekbarDiastole.setVisibility(View.GONE);
        } else {
            seekbarDiastole.setRangeValues(strMinDistole, strMaxDistole);
            seekbarDiastole.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    input_min_distole.setText(minValue.toString());
                    input_max_distole.setText(maxValue.toString());
                }
            });
        }
        if (strMinSugarFPG == 0 && strMaxSugarFPG == 0) {
            seekbarSugarFpg.setVisibility(View.GONE);
        } else {
            seekbarSugarFpg.setRangeValues(strMinSugarFPG, strMaxSugarFPG);  //value from  db
            seekbarSugarFpg.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    input_min_sugarfpg.setText(minValue.toString());
                    input_max_sugarfpg.setText(maxValue.toString());

                }
            });
        }
        if (strMinSugarPPG == 0 && strMaxSugarPPG == 0) {
            seekbarSugarPpg.setVisibility(View.GONE);
        } else {
            seekbarSugarPpg.setRangeValues(strMinSugarPPG, strMaxSugarPPG);
            seekbarSugarPpg.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    input_min_sugarppg.setText(minValue.toString());
                    input_max_sugarppg.setText(maxValue.toString());

                }
            });
        }

    }*/

    private void clearVitalsValue() {
        weightMinValue = null;
        weightMaxValue = null;
        heightMinValue = null;
        heightMaxValue = null;
        bmiMinValue = null;
        bmiMaxValue = null;
        pulseMinValue = null;
        pulseMaxValue = null;
        tempMinValue = null;
        tempMaxValue = null;
        systoleMinValue = null;
        systoleMaxValue = null;
        distoleMinValue = null;
        distoleMaxValue = null;
        sugarFpgMinValue = null;
        sugarFpgMaxValue = null;
        sugarPpgMinValue = null;
        sugarPpgMaxValue = null;
        strMinWeight = null;
        strMaxWeight = null;
        strMinHeight = null;
        strMaxHeight = null;
        strMinBmi = null;
        strMaxBmi = null;
        strMinPulse = null;
        strMaxPulse = null;
        strMinTemp = null;
        strMaxTemp = null;
        strMinSystole = null;
        strMaxSystole = null;
        strMinDistole = null;
        strMaxDistole = null;
        strMinSugarFPG = null;
        strMaxSugarFPG = null;
        strMinSugarPPG = null;
        strMaxSugarPPG = null;
        showData = null;
    }

    private void reseFiltersData() {

        // seekbarWeight.setRangeValues(strMinWeight, strMaxWeight);// if we want to set progrmmatically set range of seekbar

        weightMinValue = null;
        weightMaxValue = null;
        heightMinValue = null;
        heightMaxValue = null;
        bmiMinValue = null;
        bmiMaxValue = null;
        pulseMaxValue = null;
        pulseMinValue = null;
        tempMinValue = null;
        tempMaxValue = null;
        systoleMinValue = null;
        systoleMaxValue = null;
        distoleMinValue = null;
        distoleMaxValue = null;
        sugarFpgMinValue = null;
        sugarFpgMaxValue = null;
        sugarPpgMinValue = null;
        sugarPpgMaxValue = null;
        strEcg = null;
        strPft = null;
        strAcer = null;
        strHbA1c = null;
        strLipidHDL = null;
        strLipidLDL = null;
        strLipidTC = null;
        strLipidTG = null;
        strLipidVHDL = null;
        strSerumUrea = null;
        strEcg = null;
        strPft = null;
        strAcerMax = null;
        strHbA1cMax = null;
        strLipidHDLMax = null;
        strLipidLDLMax = null;
        strLipidTCMax = null;
        strLipidTGMax = null;
        strLipidVHDLMax = null;
        strSerumUreaMax = null;
        btnMoreFilter = null;
        healthDataBundle = null;
        strTobaco=null;
        strSmoking = null;
        strAlcoholConsumption = null;
        noOfSticksPerYear = null;
        noOfPegsPerYear = null;
        strLeftSmokingSinceYear = null;
        strLeftAlcoholSinceYear = null;
        strSleepStatus = null;
        strStressLevel = null;
        strSexuallyActive = null;
        strExcercise = null;
        strAllergie = null;
        strBingeEating = null;
        strLactoseTolerance = null;
        strLifeStyle = null;
        otherTobacoTaking = null;
        strDrug = null;
        otherDrugTaking = null;
        strFoodHabit = null;
        strFoodPreference = null;
    }

   /* abstract class OnSingleClickListener implements View.OnClickListener {

        private static final long MIN_CLICK_INTERVAL = 600;

        private long mLastClickTime;


        public abstract void onSingleClick(View v);

        @Override
        public final void onClick(View v) {
            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;

            mLastClickTime = currentClickTime;

            if (elapsedTime <= MIN_CLICK_INTERVAL)
                return;

            onSingleClick(v);
        }

    }*/

    private void openInvestigationDialog() {

        final Dialog dialog = new Dialog(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());
       // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final View f = factory.inflate(R.layout.investigation_dialognew, null);
        dialog.setTitle(" Filter Investigation ");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);


        Button cancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button ok = (Button) f.findViewById(R.id.customDialogOk);
        final EditText input_hba1c = (EditText) f.findViewById(R.id.input_hba1c);
        final EditText input_acer = (EditText) f.findViewById(R.id.input_acer);
        final EditText input_seremUrea = (EditText) f.findViewById(R.id.input_seremUrea);
        final EditText input_LipidHDL = (EditText) f.findViewById(R.id.input_LipidHDL);
        final EditText input_LipidTC = (EditText) f.findViewById(R.id.input_LipidTC);
        final EditText input_LipidTG = (EditText) f.findViewById(R.id.input_LipidTG);
        final EditText input_LipidLDL = (EditText) f.findViewById(R.id.input_LipidLDL);
        final EditText input_LipidVHDL = (EditText) f.findViewById(R.id.input_LipidVHDL);
        final EditText edtInput_sugar = (EditText) f.findViewById(R.id.input_sugar);
        final EditText edtInput_sugarfasting = (EditText) f.findViewById(R.id.input_sugarfasting);

        final EditText input_hba1c_max = (EditText) f.findViewById(R.id.input_hba1c_max);
        final EditText input_acer_max = (EditText) f.findViewById(R.id.input_acer_max);
        final EditText input_seremUrea_max = (EditText) f.findViewById(R.id.input_seremUrea_max);
        final EditText input_LipidHDL_max = (EditText) f.findViewById(R.id.input_LipidHDL_max);
        final EditText input_LipidTC_max = (EditText) f.findViewById(R.id.input_LipidTC_max);
        final EditText input_LipidTG_max = (EditText) f.findViewById(R.id.input_LipidTG_max);
        final EditText input_LipidLDL_max = (EditText) f.findViewById(R.id.input_LipidLDL_max);
        final EditText input_LipidVHDL_max = (EditText) f.findViewById(R.id.input_LipidVHDL_max);


        edtInput_sugar.setVisibility(View.GONE);
        edtInput_sugarfasting.setVisibility(View.GONE);
        /*LinearLayout sugarLayout = (LinearLayout) f.findViewById(R.id.sugarLayout);
        sugarLayout.setVisibility(View.GONE);*/

        RadioGroup radioEcg = (RadioGroup) f.findViewById(R.id.radioEcg);
        RadioGroup radioPft = (RadioGroup) f.findViewById(R.id.radioPft);

        if (strLipidTC != null) {
            input_LipidTC.setText(strLipidTC);
        }
        if (strLipidTG != null && !strLipidTG.equals("")) input_LipidTG.setText(strLipidTG);
        if (strLipidLDL != null && !strLipidLDL.equals("")) input_LipidLDL.setText(strLipidLDL);
        if (strLipidVHDL != null && !strLipidVHDL.equals("")) input_LipidVHDL.setText(strLipidVHDL);
        if (strLipidHDL != null && !strLipidHDL.equals("")) input_LipidHDL.setText(strLipidHDL);
        if (strHbA1c != null && !strHbA1c.equals("")) input_hba1c.setText(strHbA1c);
        if (strSerumUrea != null && !strSerumUrea.equals("")) input_seremUrea.setText(strSerumUrea);
        if (strAcer != null && !strAcer.equals("")) input_acer.setText(strAcer);

        if (strLipidTCMax != null) {
            input_LipidTC_max.setText(strLipidTCMax);
        }
        if (strLipidTGMax != null && !strLipidTGMax.equals("")) input_LipidTG_max.setText(strLipidTGMax);
        if (strLipidLDLMax != null && !strLipidLDLMax.equals("")) input_LipidLDL_max.setText(strLipidLDLMax);
        if (strLipidVHDLMax != null && !strLipidVHDLMax.equals("")) input_LipidVHDL_max.setText(strLipidVHDLMax);
        if (strLipidHDLMax != null && !strLipidHDLMax.equals("")) input_LipidHDL_max.setText(strLipidHDLMax);
        if (strHbA1cMax != null && !strHbA1cMax.equals("")) input_hba1c_max.setText(strHbA1cMax);
        if (strSerumUreaMax != null && !strSerumUreaMax.equals("")) input_seremUrea_max.setText(strSerumUreaMax);
        if (strAcerMax != null && !strAcerMax.equals("")) input_acer_max.setText(strAcerMax);

        if (strEcg != null && !strEcg.equals(""))
            switch (strEcg) {
                case "Normal":
                    radioEcg.check(R.id.radioEcgNormal);
                    break;
                case "Abnormal":
                    radioEcg.check(R.id.radioEcgAbnormal);
                    break;
            }
        if (strPft != null && !strPft.equals(""))
            switch (strPft) {
                case "Normal":
                    radioPft.check(R.id.radioPftNormal);
                    break;
                case "Abnormal":
                    radioPft.check(R.id.radioPftAbnormal);
                    break;
            }

        radioEcg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioEcgNormal:
                        strEcg = "Normal";
                        break;

                    case R.id.radioEcgAbnormal:
                        strEcg = "Abnormal";
                        break;

                    default:
                        break;
                }
            }
        });
        radioPft.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioPftNormal:
                        strPft = "Normal";
                        break;

                    case R.id.radioPftAbnormal:
                        strPft = "Abnormal";
                        break;

                    default:
                        break;
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strLipidTC = input_LipidTC.getText().toString();
                strLipidTG = input_LipidTG.getText().toString();
                strLipidLDL = input_LipidLDL.getText().toString();
                strLipidVHDL = input_LipidVHDL.getText().toString();
                strLipidHDL = input_LipidHDL.getText().toString();
                //strSugarFasting = edtInput_sugarfasting.getText().toString();
                // strSgar = edtInput_sugar.getText().toString();
                strHbA1c = input_hba1c.getText().toString();
                strSerumUrea = input_seremUrea.getText().toString();
                strAcer = input_acer.getText().toString();

                strLipidTCMax = input_LipidTC_max.getText().toString();
                strLipidTGMax = input_LipidTG_max.getText().toString();
                strLipidLDLMax = input_LipidLDL_max.getText().toString();
                strLipidVHDLMax = input_LipidVHDL_max.getText().toString();
                strLipidHDLMax = input_LipidHDL_max.getText().toString();
                strHbA1cMax = input_hba1c_max.getText().toString();
                strSerumUreaMax = input_seremUrea_max.getText().toString();
                strAcerMax = input_acer_max.getText().toString();

               /* if (strSgar != null && !strSgar.equals("") && strSgar.length() > 0) {
                    sbInvestigations.append("Sugar(PPG):").append(strSgar).append(" ;");

                }
                if (strSugarFasting != null && !strSugarFasting.equals("") && strSugarFasting.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" Sugar(FPG):").append(strSgar).append(" ;");
                }
                if (strEcg != null && !strEcg.equals("") && strEcg.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" ECG:").append(strEcg).append(" ;");
                }
                if (strPft != null && !strPft.equals("") && strPft.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" PFT:").append(strPft).append(" ;");
                }
                sbInvestigations.append("  ");
                sbInvestigations.append(" HbA1c:").append(strHbA1c).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" Acer:").append(strAcer).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" SerumUrea:").append(strSerumUrea).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" LipidHDL:").append(strLipidHDL).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" LipidTC:").append(strLipidTC).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" LipidTG:").append(strLipidTG).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" LipidLDL:").append(strLipidLDL).append(" ;");
                sbInvestigations.append("  ");
                sbInvestigations.append(" LipidVHDL:").append(strLipidVHDL).append(" ;");



                  showInvestigationData.setText(sbInvestigations);*/
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openHealthLifestyleDialog() {
        final Dialog dialog = new Dialog(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View dialogView = factory.inflate(R.layout.health_and_lifestyle_dialog, null);
        dialog.setTitle("Health and LifeStyle ");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(dialogView);

        final RadioGroup radioSmoker = (RadioGroup) dialogView.findViewById(R.id.radioSmoker);
        final RadioGroup radioTobaco = (RadioGroup) dialogView.findViewById(R.id.radioTobaco);

        final RadioGroup radioAlcoholConsumption = (RadioGroup) dialogView.findViewById(R.id.radioAlcoholConsumption);
        final RadioGroup radioLifeStyle = (RadioGroup) dialogView.findViewById(R.id.radioLifeStyle);

        final RadioGroup radioStressLevel = (RadioGroup) dialogView.findViewById(R.id.radioStressLevel);
        final RadioGroup radioExcercise = (RadioGroup) dialogView.findViewById(R.id.radioExcercise);


        final RadioGroup radioBingeEating = (RadioGroup) dialogView.findViewById(R.id.radioBingeEating);
        final RadioGroup radiosexuallyActive = (RadioGroup) dialogView.findViewById(R.id.radiosexuallyActive);

        final RadioGroup radioFoodHabit = (RadioGroup) dialogView.findViewById(R.id.radioFoodHabit);
        final RadioGroup radioFoodPreference = (RadioGroup) dialogView.findViewById(R.id.radioFoodPreference);

        final RadioGroup radioLactoseTolerance = (RadioGroup) dialogView.findViewById(R.id.radioLactoseTolerance);

        final Button update = (Button) dialogView.findViewById(R.id.save);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);

        final EditText noOfSticks = (EditText) dialogView.findViewById(R.id.noOfSticks);
        final EditText packPerWeer = (EditText) dialogView.findViewById(R.id.packPerWeer);
        final EditText input_Alergies = (EditText) dialogView.findViewById(R.id.input_Alergies);

        //setRadioButtons();

        if (strAllergie != null && !strAllergie.equals("")) {
            input_Alergies.setText(strAllergie);
        }
        if (strNoOfSticks != null && !strNoOfSticks.equals("")) {
            noOfSticks.setVisibility(View.VISIBLE);
            noOfSticks.setText(strNoOfSticks);
        }
        if (strPackPerWeek != null && !strPackPerWeek.equals("")) {
            packPerWeer.setVisibility(View.VISIBLE);
            packPerWeer.setText(strPackPerWeek);
        }
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
  /*  private void setRadioButtons() {
        //  strSmoking = "Non Smoker";//set Daefault gender value to Male if not selected any other value to prevent null value. 14-12-2016
        // Checked change Listener for RadioGroup 1

        radioSmoker.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {


                    case R.id.radioActiveSmoker:
                        strSmoking = "Active Smoker";
                        noOfSticks.setVisibility(View.VISIBLE);
                        stickVisible = true;
                        radioSmokeDates.setVisibility(View.VISIBLE);
                        txtSinceSmoke.setVisibility(View.INVISIBLE);
                        sinceSmokeYear.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.radioPassiveSmoker:
                        //Toast.makeText(getContext(),"Male Selected",Toast.LENGTH_LONG).show();
                        strSmoking = "Passsive Smoker";
                        if (stickVisible) {
                            noOfSticks.setVisibility(View.INVISIBLE);
                            radioSmokeDates.setVisibility(View.INVISIBLE);
                            txtSinceSmoke.setVisibility(View.INVISIBLE);
                            sinceSmokeYear.setVisibility(View.INVISIBLE);
                        }
                        break;

                    case R.id.radioExSmoker:
                        strSmoking = "Ex Smoker";
                        txtSinceSmoke.setVisibility(View.VISIBLE);
                        sinceSmokeYear.setVisibility(View.VISIBLE);
                        noOfSticks.setVisibility(View.INVISIBLE);
                        radioSmokeDates.setVisibility(View.INVISIBLE);
                        txtSinceSmoke.setVisibility(View.VISIBLE);
                        sinceSmokeYear.setVisibility(View.VISIBLE);
                        break;

                    case R.id.radioNonSmoker:
                        strSmoking = "Non Smoker";
                        if (stickVisible) {
                            noOfSticks.setVisibility(View.INVISIBLE);
                            radioSmokeDates.setVisibility(View.INVISIBLE);
                            txtSinceSmoke.setVisibility(View.INVISIBLE);
                            sinceSmokeYear.setVisibility(View.INVISIBLE);
                        }
                        break;

                    default:
                        break;
                }
            }
        });

        //strsDaysSelectedSmoking = null;
        radioSmokeDates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioSmokeDay:
                        strsDaysSelectedSmoking = "Days";
                        String daysSelected = noOfSticks.getText().toString();
                        if (daysSelected != null && !daysSelected.equals("")) {

                            noOfSticksPerYear = String.valueOf(((Integer.parseInt(daysSelected)) * 365));
                        } else {
                            noOfSticks.setError("Please Enter Sticks");
                        }
                        Toast.makeText(getContext(), " pegs taking " + noOfSticksPerYear, Toast.LENGTH_LONG).show();
                        break;

                    case R.id.radioSmokeWeek:
                        strsDaysSelectedSmoking = "Week";
                        String weekSelected = noOfSticks.getText().toString();
                        if (weekSelected != null && !weekSelected.equals("")) {
                            noOfSticksPerYear = String.valueOf(((Integer.parseInt(weekSelected)) * 52));
                        } else {
                            noOfSticks.setError("Please Enter Sticks");
                        }
                        Toast.makeText(getContext(), " pegs taking " + noOfSticksPerYear, Toast.LENGTH_LONG).show();

                        break;

                    case R.id.radioSmokeMonth:
                        strsDaysSelectedSmoking = "Month";
                        String monthSelected = noOfSticks.getText().toString();
                        if (monthSelected != null && !monthSelected.equals("")) {
                            noOfSticksPerYear = String.valueOf(((Integer.parseInt(monthSelected)) * 12));
                        } else {
                            noOfSticks.setError("Please Enter Sticks");
                        }
                        Toast.makeText(getContext(), " pegs taking " + noOfSticksPerYear, Toast.LENGTH_LONG).show();

                        break;

                    default:
                        break;
                }
            }
        });
      //  strTobaco = null;
        radioTobaco.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioOtherTobacoYes:
                        strTobaco = "Yes";
                        otherTobacoEdit.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioOtherTobacoNo:
                        strTobaco = "No";
                        otherTobacoEdit.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        });
       // strDrug = null;
        radioOtherDrug.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioOtherDrugYes:
                        strDrug = "Yes";
                        otherDrugEdit.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioOtherDrugNo:
                        strDrug = "No";
                        otherDrugEdit.setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        });
       // strAlcoholConsumption = null;
        radioAlcoholConsumption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioActiveDrinker:
                        strAlcoholConsumption = "Drinker";
                        packPerWeer.setVisibility(View.VISIBLE);
                        packsVisible = true;
                        radioAlcoholDates.setVisibility(View.VISIBLE);
                        txtSinceAlcohol.setVisibility(View.INVISIBLE);
                        sinceAlcoholYear.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.radioNonDrinker:
                        strAlcoholConsumption = "Non Drinker";
                        if (packsVisible) {
                            packPerWeer.setVisibility(View.INVISIBLE);
                            radioAlcoholDates.setVisibility(View.INVISIBLE);
                            txtSinceAlcohol.setVisibility(View.INVISIBLE);
                            sinceAlcoholYear.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case R.id.radioExDrinker:
                        strAlcoholConsumption = "Ex Drinker";
                        packPerWeer.setVisibility(View.INVISIBLE);
                        radioAlcoholDates.setVisibility(View.INVISIBLE);
                        txtSinceAlcohol.setVisibility(View.VISIBLE);
                        sinceAlcoholYear.setVisibility(View.VISIBLE);

                        break;

                    default:
                        break;
                }
            }
        });
       // strsDaysSelectedDrinking = null;
        radioAlcoholDates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioAlcoholDay:
                        strsDaysSelectedDrinking = "Days";
                        String daysSelected = packPerWeer.getText().toString();
                        if (daysSelected != null && !daysSelected.equals("")) {
                            noOfPegsPerYear = String.valueOf(((Integer.parseInt(daysSelected)) * 365));
                        } else {
                            packPerWeer.setError("Please Enter Pegs");
                        }
                        Toast.makeText(getContext(), " pegs taking " + noOfPegsPerYear, Toast.LENGTH_LONG).show();
                        break;

                    case R.id.radioAlcoholWeek:
                        strsDaysSelectedDrinking = "Week";
                        String weekSelected = packPerWeer.getText().toString();
                        if (weekSelected != null && !weekSelected.equals("")) {
                            noOfPegsPerYear = String.valueOf(((Integer.parseInt(weekSelected)) * 52));
                        } else {
                            packPerWeer.setError("Please Enter Pegs");
                        }
                        break;

                    case R.id.radioAlcoholMonth:
                        strsDaysSelectedDrinking = "Month";
                        String monthSelected = noOfSticks.getText().toString();
                        if (monthSelected != null && !monthSelected.equals("")) {
                            noOfSticksPerYear = String.valueOf(((Integer.parseInt(monthSelected)) * 12));
                        } else {
                            packPerWeer.setError("Please Enter Pegs");
                        }
                        break;
                    default:
                        break;
                }
            }
        });

       // strLifeStyle = null;
        radioLifeStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioSedentary:
                        strLifeStyle = "Sedentary";
                        break;
                    case R.id.radioLightActive:
                        strLifeStyle = "Light Active";
                        break;
                    case R.id.radioActive:
                        strLifeStyle = "Active";
                        break;
                    default:
                        break;
                }
            }
        });
       // strStressLevel = null;
        radioStressLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioLowStress:
                        strStressLevel = "Low";
                        break;

                    case R.id.radioModerateStrees:
                        strStressLevel = "Moderate";
                        break;

                    case R.id.radioHighStrees:
                        strStressLevel = "High";
                        break;

                    default:
                        break;
                }
            }
        });

      //  strExcercise = null;
        radioExcercise.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioExcerciseNo:
                        strExcercise = "No";
                        break;

                    case R.id.radioExcerciseYes:
                        strExcercise = "Yes";
                        break;

                    default:
                        break;
                }
            }
        });

      //  strBingeEating = null;
        radioBingeEating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioBingeNo:
                        strBingeEating = "No";
                        break;

                    case R.id.radioBingeYes:
                        strBingeEating = "Yes";
                        break;

                    default:
                        break;
                }
            }
        });

        //strSexuallyActive = null;
        radiosexuallyActive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioSexStatusNo:
                        strSexuallyActive = "No";
                        break;

                    case R.id.radioSexStatusYes:
                        strSexuallyActive = "Yes";
                        break;

                    default:
                        break;
                }
            }
        });

        //strFoodHabit = null;
        radioFoodHabit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioFoodHabitVeg:
                        strFoodHabit = "Veg";
                        otherFoodSpinner.setVisibility(View.GONE);
                        break;

                    case R.id.radioFoodHabitNonVeg:
                        strFoodHabit = "NonVeg";
                        otherFoodSpinner.setVisibility(View.GONE);
                        break;

                    case R.id.radioFoodHabitOther:
                        strFoodHabit = "Other";
                        otherFoodSpinner.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }
            }
        });

        //strFoodPreference = null;
        radioFoodPreference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioFoodSweet:
                        strFoodPreference = "Sweet";
                        break;

                    case R.id.radioFoodSour:
                        strFoodPreference = "Sour";
                        break;


                    case R.id.radioFoodSpicy:
                        strFoodPreference = "Spicy";
                        break;

                    default:
                        break;
                }
            }
        });

       // strLactoseTolerance = null;
        radioLactoseTolerance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioLactoseToleranceNo:
                        strLactoseTolerance = "No";
                        break;

                    case R.id.radioLactoseToleranceYes:
                        strLactoseTolerance = "Yes";
                        break;

                    default:
                        break;
                }
            }
        });
       // strSleepStatus = null;
        radioSleep.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioSleepAdequate:
                        strSleepStatus = "Adequate";
                        break;

                    case R.id.radioSleepInAdequate:
                        strSleepStatus = "InAdequate";
                        break;

                    default:
                        break;
                }
            }
        });

        otherFoodSpinner.setPrompt("Select Type");


        // Creating adapter for spinner
        ArrayAdapter<CharSequence> foodTypeAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.otherFoodType,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        foodTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        otherFoodSpinner.setAdapter(foodTypeAdapter);
        otherFoodSpinner.setSelection(position);
        strFoodHabit = null;
        otherFoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strFoodHabit = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }*/
}


