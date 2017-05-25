package app.clirnet.com.clirnetapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
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
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.ItemClickListener;
import app.clirnet.com.clirnetapp.utility.MultiSpinner;
import app.clirnet.com.clirnetapp.utility.MultiSpinner2;
import app.clirnet.com.clirnetapp.utility.RangeSeekBar;


public class PoHistoryFragment extends Fragment implements MultiSpinner.MultiSpinnerListener, MultiSpinner2.MultiSpinnerListener {


    private boolean viewGroupIsVisible = true;
    private View mViewGroup;
    private OnFragmentInteractionListener mListener;
    private EditText firstName;
    private EditText lastName;
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
    private MultiAutoCompleteTextView ailments;
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
    private Button selectVitals, resetVitals;
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

    private TextView moreFilters;
    private RangeSeekBar seekbarWeight;
    private RangeSeekBar seekbarHeight;
    private RangeSeekBar seekbarBmi;
    private RangeSeekBar seekbarPulse;
    private RangeSeekBar seekbarTemp;
    private RangeSeekBar seekbarSystole;
    private RangeSeekBar seekbarDiastole;
    private RangeSeekBar seekbarSugarFpg;
    private RangeSeekBar seekbarSugarPpg;
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


    private StringBuilder sb = new StringBuilder();

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

       /* Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });*/

        rootview = inflater.inflate(R.layout.fragment_po_history, container, false);

        ((NavigationActivity) getActivity()).setActionBarTitle("Patient History");

        firstName = (EditText) rootview.findViewById(R.id.firstname);

        lastName = (EditText) rootview.findViewById(R.id.lastname);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        ailments = (MultiAutoCompleteTextView) rootview.findViewById(R.id.ailments);
        TextView currdate = (TextView) rootview.findViewById(R.id.sysdate);
        backChangingImages = (ImageView) rootview.findViewById(R.id.backChangingImages);
        norecordtv = (LinearLayout) rootview.findViewById(R.id.norecordtv);

        genderSpinner = (MultiSpinner) rootview.findViewById(R.id.gender);
        ageGapSpinner = (MultiSpinner2) rootview.findViewById(R.id.ageGap);


        phone_no = (EditText) rootview.findViewById(R.id.mobile_no);

        selectVitals = (Button) rootview.findViewById(R.id.selectVitals);

        showData = (TextView) rootview.findViewById(R.id.showData);

        initalizeView(rootview);

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
            bannerimgNames = bannerClass.getImageName();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            ArrayList<HashMap<String, Integer>> demoList = sqlController.getMinMaxVitals();

            if (demoList.size() > 0) {
                strMinWeight = demoList.get(0).get("MINWEIGHT");
                strMaxWeight = demoList.get(0).get("MAXWEIGHT");
                //  Log.e("strMinWeight", "" + strMinWeight + "  " + strMaxWeight);
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


                viewGroupIsVisible = !viewGroupIsVisible;
                return false;
            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewGroupIsVisible) {
                    mViewGroup.setVisibility(View.GONE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
                }

                patientData.clear(); //This method will clear all previous data from  Array list  24-8-2016

                String valMinWeight = input_min_weight.getText().toString();
                String valMaxWeight = input_max_weight.getText().toString();

                if (!valMinWeight.equals("") && !valMaxWeight.equals("")) {

                    weightMinValue = Integer.valueOf(valMinWeight);
                    weightMaxValue = Integer.valueOf(valMaxWeight);

                    sb.append("  Weight: Min ").append(weightMinValue).append(" - Max ").append(weightMaxValue).append(" ;");

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
                    } else if (heightMaxValue > strMaxHeight) {
                        input_max_height.setError("Entered value must be small from  " + strMaxHeight);
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


                strfname = firstName.getText().toString().trim();
                strlname = lastName.getText().toString().trim();
                strpno = phone_no.getText().toString().trim();
                String strAilment = ailments.getText().toString().trim();


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
                    patientData = (sqlController.getFilterDatanew(strfname, strlname, strpno, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit, weightMinValue, weightMaxValue, heightMinValue, heightMaxValue, bmiMinValue, bmiMaxValue,
                            pulseMinValue, pulseMaxValue, tempMinValue, tempMaxValue, systoleMinValue, systoleMaxValue, distoleMinValue, distoleMaxValue, sugarFpgMinValue, sugarFpgMaxValue, sugarPpgMinValue, sugarPpgMaxValue));
                    //    patientData = sqlController.getFilterDatanew(strfname, strlname, selectedListGender.get(i).toString(), strpno, strage);
                    queryCount = sqlController.getCountResult();
                    // queryCount = 70;
                    // Log.e("queryCount", "" + queryCount );

                    int beforeFilterCount = patientData.size();

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
        setValueToSeekBarFromDb();

        selectVitals.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {


                    showVitalsDialogBox();

                    selectVitals.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    selectVitals.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        resetVitals = (Button) rootview.findViewById(R.id.resetVitals);

        resetVitals.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    resetVitalsData();

                    resetVitals.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    resetVitals.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        //  addresslayoutlayout = (LinearLayout)rootview.findViewById(R.id.vitalsLayout);

        mViewGroup = rootview.findViewById(R.id.vitalsLayout);

        moreFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewGroupIsVisible) {
                    mViewGroup.setVisibility(View.GONE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
                } else {
                    mViewGroup.setVisibility(View.VISIBLE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);
                }

                viewGroupIsVisible = !viewGroupIsVisible;

                /*if(addresslayoutlayout.getVisibility() == View.VISIBLE){
                    addresslayoutlayout.setVisibility(View.GONE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);
                } else {
                    addresslayoutlayout.setVisibility(View.VISIBLE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);
                }*/

               /* if (countVitalsLayout == 1) {
                    addresslayoutlayout.setVisibility(View.VISIBLE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countVitalsLayout = 2;
                } else {
                    addresslayoutlayout.setVisibility(View.GONE);
                    moreFilters.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countVitalsLayout = 1;
                }*/
                //  txtRecord.setBackground(R.drawable.);
            }
        });
        return rootview;
    }

    private void initalizeView(View f) {

         moreFilters = (TextView) rootview.findViewById(R.id.moreFilters);

        seekbarWeight = (RangeSeekBar) f.findViewById(R.id.seekbarWeight);
        seekbarHeight = (RangeSeekBar) f.findViewById(R.id.seekbarHeight);
        seekbarBmi = (RangeSeekBar) f.findViewById(R.id.seekbarBmi);
        seekbarPulse = (RangeSeekBar) f.findViewById(R.id.seekbarPulse);
        seekbarTemp = (RangeSeekBar) f.findViewById(R.id.seekbarTemp);
        seekbarSystole = (RangeSeekBar) f.findViewById(R.id.seekbarSystole);
        seekbarDiastole = (RangeSeekBar) f.findViewById(R.id.seekbarDiastole);
        seekbarSugarFpg = (RangeSeekBar) f.findViewById(R.id.seekbarSugarFpg);
        seekbarSugarPpg = (RangeSeekBar) f.findViewById(R.id.seekbarSugarPpg);

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
    }

    private void setAilmentData() {
        try {
            databaseClass.openDataBase();
            mAilmemtArrayList = databaseClass.getAilmentsListNew();

            if (mAilmemtArrayList.size() > 0) {

                //this code is for setting list to auto complete text view  8/6/16

                ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                ailments.setThreshold(1);

                ailments.setAdapter(adp);
                ailments.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
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
        int beforeFilterCount = 0;

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
                    memberList = sqlController.getFilterDatanew(strfname, strlname, strpno, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit, weightMinValue, weightMaxValue, heightMinValue, heightMaxValue, bmiMinValue, bmiMaxValue,
                            pulseMinValue, pulseMaxValue, tempMinValue, tempMaxValue, systoleMinValue, systoleMaxValue, distoleMinValue, distoleMaxValue, sugarFpgMinValue, sugarFpgMaxValue, sugarPpgMinValue, sugarPpgMaxValue);

                    queryCount = sqlController.getCountResult();
                    beforeFilterCount = memberList.size();

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
        ailments = null;
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
    }

    private void showVitalsDialogBox() {
        final Dialog dialog = new Dialog(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View f = factory.inflate(R.layout.filter_vitals_dialog_pohistory, null);

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
        Button dialogButtonReset = (Button) f.findViewById(R.id.customDialogReset);


        Log.e("weightMinValue", "  " + weightMinValue);
        if (weightMinValue != null && weightMaxValue != null) {
            input_min_weight.setText(weightMinValue.toString());
            input_max_weight.setText(weightMaxValue.toString());
            seekbarWeight.setSelectedMinValue(weightMinValue);
            seekbarWeight.setSelectedMaxValue(weightMaxValue);
        }
        if (heightMinValue != null && heightMaxValue != null) {
            input_min_height.setText(heightMinValue.toString());
            input_max_height.setText(heightMaxValue.toString());
            seekbarHeight.setSelectedMinValue(heightMinValue);
            seekbarHeight.setSelectedMaxValue(heightMaxValue);
        }
        if (bmiMinValue != null && bmiMaxValue != null) {
            input_min_bmi.setText(bmiMinValue.toString());
            input_max_bmi.setText(bmiMaxValue.toString());
            seekbarBmi.setSelectedMinValue(bmiMinValue);
            seekbarBmi.setSelectedMaxValue(bmiMaxValue);
        }

        if (pulseMinValue != null && pulseMaxValue != null) {
            input_min_pulse.setText(pulseMinValue.toString());
            input_max_pulse.setText(pulseMaxValue.toString());
            seekbarPulse.setSelectedMinValue(pulseMinValue);
            seekbarPulse.setSelectedMaxValue(pulseMaxValue);
        }
        if (tempMinValue != null && tempMaxValue != null) {
            input_min_temp.setText(tempMinValue.toString());
            input_max_temp.setText(tempMaxValue.toString());
            seekbarTemp.setSelectedMinValue(tempMinValue);
            seekbarTemp.setSelectedMaxValue(tempMaxValue);
        }
        if (systoleMinValue != null && systoleMaxValue != null) {
            input_min_systole.setText(systoleMinValue.toString());
            input_max_systole.setText(systoleMaxValue.toString());
            seekbarSystole.setSelectedMinValue(systoleMinValue);
            seekbarSystole.setSelectedMaxValue(systoleMaxValue);
        }
        if (distoleMinValue != null && distoleMaxValue != null) {
            input_min_distole.setText(distoleMinValue.toString());
            input_max_distole.setText(distoleMaxValue.toString());
            seekbarDiastole.setSelectedMinValue(distoleMinValue);
            seekbarDiastole.setSelectedMaxValue(distoleMaxValue);
        }
        if (sugarFpgMinValue != null && sugarFpgMaxValue != null) {
            input_min_sugarfpg.setText(sugarFpgMinValue.toString());
            input_max_sugarfpg.setText(sugarFpgMaxValue.toString());
            seekbarSugarFpg.setSelectedMinValue(sugarFpgMinValue);
            seekbarSugarFpg.setSelectedMaxValue(sugarFpgMaxValue);
        }
        if (sugarPpgMinValue != null && sugarPpgMaxValue != null) {
            input_min_sugarppg.setText(sugarPpgMinValue.toString());
            input_max_sugarppg.setText(sugarPpgMaxValue.toString());
            seekbarSugarPpg.setSelectedMinValue(sugarPpgMinValue);
            seekbarSugarPpg.setSelectedMaxValue(sugarPpgMaxValue);
        }

        input_min_weight.addTextChangedListener(new TextWatcher() {
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

                    sb.append("  Weight: Min ").append(weightMinValue).append(" - Max ").append(weightMaxValue).append(" ;");

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
                    } else if (heightMaxValue > strMaxHeight) {
                        input_max_height.setError("Entered value must be small from  " + strMaxHeight);
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
                dialog.dismiss();
            }
        });

        dialogButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clearVitalsValue2();

                resetVitalsData();
            }
        });
        dialog.show();
    }
private void setValueToSeekBarFromDb(){

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

}

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

    private void clearVitalsValue2() {
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
    }

    private void resetVitalsData() {

       // seekbarWeight.setRangeValues(strMinWeight, strMaxWeight);// if we want to set progrmmatically set range of seekbar
        seekbarWeight.setSelectedMinValue(strMinWeight);
        seekbarWeight.setSelectedMaxValue(strMaxWeight);
        seekbarHeight.setSelectedMinValue(strMinHeight);
        seekbarHeight.setSelectedMaxValue(strMaxHeight);
        seekbarBmi.setSelectedMinValue(strMinBmi);
        seekbarBmi.setSelectedMaxValue(strMaxBmi);
        seekbarPulse.setSelectedMinValue(strMinPulse);
        seekbarPulse.setSelectedMaxValue(strMaxPulse);
        seekbarTemp.setSelectedMinValue(strMinTemp);
        seekbarTemp.setSelectedMaxValue(strMaxTemp);
        seekbarSystole.setSelectedMinValue(strMinSystole);
        seekbarSystole.setSelectedMaxValue(strMaxSystole);
        seekbarDiastole.setSelectedMinValue(strMinDistole);
        seekbarDiastole.setSelectedMaxValue(strMaxDistole);
        seekbarSugarFpg.setSelectedMinValue(strMinSugarFPG);
        seekbarSugarFpg.setSelectedMaxValue(strMaxSugarFPG);
        seekbarSugarPpg.setSelectedMinValue(strMinSugarPPG);
        seekbarSugarPpg.setSelectedMaxValue(strMaxSugarPPG);

        input_min_weight.setText("");
        input_max_weight.setText("");
        input_min_height.setText("");
        input_max_height.setText("");
        input_min_bmi.setText("");
        input_max_bmi.setText("");
        input_min_pulse.setText("");
        input_max_pulse.setText("");
        input_min_temp.setText("");
        input_max_temp.setText("");
        input_min_systole.setText("");
        input_max_systole.setText("");
        input_min_distole.setText("");
        input_max_distole.setText("");
        input_min_sugarfpg.setText("");
        input_max_sugarfpg.setText("");
        input_min_sugarppg.setText("");
        input_max_sugarppg.setText("");
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

       // sb.setLength(0);
       // showData.setText("");

    }
}


