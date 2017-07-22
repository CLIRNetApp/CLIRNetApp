package app.clirnet.com.clirnetapp.quickAdd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.tokenautocomplete.TokenCompleteTextView;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.NewEditPersonalInfo;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.ContactsCompletionView;
import app.clirnet.com.clirnetapp.utility.ImageCompression;


public class QuickAddVisitFragment extends Fragment {

    private View view;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int DATE_DIALOG_ID2 = 2;
    private BootstrapEditText fodtextshow;
    private Button days;
    private Button week;
    private Button month;
    private BootstrapEditText inputnumber;
    private String value;

    private String daysSel;


    private Button addUpdate;
    private Button cancel;

    private Button btnclear;
    private ImageView backChangingImages;
    private EditText clinicalNotes;
    private EditText familyHistory;
    private EditText hospitalizationSurgery;
    private EditText visitDate;

    private ContactsCompletionView edtSymptoms;
    private ContactsCompletionView edtDignosis;
    private String strPatientId;

    private OnFragmentInteractionListener mListener;
    private String strPatientPhoto;
    private DatabaseClass databaseClass;
    private LastnameDatabaseClass lastNamedb;
    private String strName;
    private String mPhNo;
    private String mAge;
    private String mLang;
    private String mGender;
    private String addedOnDate;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private String doctor_membership_number;
    private String docId;
    private BannerClass bannerClass;
    private ArrayList<String> bannerimgNames;
    private ArrayList<String> mSymptomsList;
    private AppController appController;
    private String fowSel = null;
    private String usersellectedDate;
    private String monthSel = null;
    private String addedTime;
    private SimpleDateFormat sdf1;

    private String prescriptionimgPath;
    private String prescriptionimgId;
    private String added_on;
    private HashMap<String, String> NameData;
    private int addCounter = 0;
    private String strReferedTo;
    private String strReferedBy;
    private Intent imageIntent;
    private File imagesFolder;
    private Uri uriSavedImage;
    private TextView edtprescriptionImgPath;

    private TextView textRefredByShow;
    private TextView textRefredToShow;

    private String strReferredByName;
    private String strReferredTo1Name;
    private String strReferredTo2Name;
    private String strReferredTo3Name;
    private String strReferredTo4Name;
    private String strReferredTo5Name;
    private ArrayList<String> nameReferalsList;

    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtLowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private EditText edtInput_sugarfasting;
    private EditText edtInput_bmi;
    private EditText edtInput_height;

    private TextView txtReferredTo, txtReferredBy;

    private String strWeight;
    private String strPulse;
    private String strBp;
    private String strLowBp;
    private String strTemp;
    private String strHeight;
    private String strbmi;
    private EditText edtInput_spo2;
    private EditText edtInput_respiration_rate;

    private LinearLayout showReferrals;
    private String strEmail;
    private String strPhoneType;
    private String strFirstName, strMiddleName, strLastName;
    private String strDob;
    private String strAddress;
    private String strCityorTown;
    private String strDistrict;
    private String strPinNo, strState;
    private String strAlternatenumber, strAlternatephtype;
    private String strIsd_code, strUid;
    private String strAlternateIsd_code;
    private File imagePathFile;

    private TextView showInvestigationData;
    private TextView showVitalsData;
    private Button buttonInvestigation;
    private Button buttonObservations;
    private String strLipidTC;
    private String strLipidTG;
    private String strLipidLDL;
    private String strLipidVHDL;
    private String strLipidHDL;
    private String strSgar;
    private String strSugarFasting;
    private String strHbA1c;
    private String strSerumUrea;
    private String strAcer;
    private String strEcg;
    private String strPft;

    private StringBuilder sbVitals = new StringBuilder();
    private StringBuilder sbObservations = new StringBuilder();
    private StringBuilder sbInvestigations = new StringBuilder();
    private String strVisitId;

    private String mAlcohol;
    private String  mStressLevel;
    private String mSmokerType;
    private String mSleepStatus;
    private String strSpo2, strRespirationRate;
    private String strPatientFollowUpStatus;
    private String strPallorDescription;
    private String strCyanosisDescription;
    private String strTremorsDescription;
    private String strIcterusDescription;
    private String strClubbingDescription;
    private String strOedemaDescription;
    private String strCalfTendernessDescription;
    private String strLymphadenopathyDescription;
    private String strPallore;
    private String strCyanosis, strTremors, strIcterus, strClubbing, strOedema, strCalfTenderness, strLymphadenopathy;
    private TextView showObservationsData;
    private EditText ediInput_obesity;
    private String strObestity;
    private String mLifeStyle;
    private String mExcercise;
    private String mChewinogTobaco;

    public QuickAddVisitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        if (getArguments() != null) {

            this.setRetainInstance(true);

            strName = getArguments().getString("NAME");
            strFirstName = getArguments().getString("FIRSTTNAME");
            strMiddleName = getArguments().getString("MIDDLENAME");
            strLastName = getArguments().getString("LASTNAME");
            strDob = getArguments().getString("DOB");
            strAddress = getArguments().getString("ADDRESS");
            strCityorTown = getArguments().getString("CITYORTOWN");
            strDistrict = getArguments().getString("DISTRICT");
            strPinNo = getArguments().getString("CITYORTOWN");
            strState = getArguments().getString("STATE");
            strAlternatenumber = getArguments().getString("ALTERNATENUMBER");
            strAlternatephtype = getArguments().getString("ALTERNATENUMBERTYPE");
            strIsd_code = getArguments().getString("ISDCODE");
            strAlternateIsd_code = getArguments().getString("ALTERNATEISDCODE");
            strUid = getArguments().getString("UID");
            strPatientId = getArguments().getString("PATIENTID");
            strPatientPhoto = getArguments().getString("PATIENTPHOTO");
            prescriptionimgPath = getArguments().getString("PRESCRIPTIONIMG");
            prescriptionimgId = getArguments().getString("PRESCRIPTIONID");
            added_on = getArguments().getString("ADDED_ON");
            strReferedBy = getArguments().getString("REFEREDBY");
            strReferedTo = getArguments().getString("REFEREDTO");
            mPhNo = getArguments().getString("PHONE");
            mAge = getArguments().getString("AGE");
            mLang = getArguments().getString("LANGUAGE");
            mGender = getArguments().getString("GENDER");
            strEmail = getArguments().getString("EMAIL");
            strPhoneType = getArguments().getString("PHONETYPE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quick_add_visit_fraagment, container, false);


        //TextView date = (TextView) view.findViewById(R.id.sysdate);
        ImageView patientImage = (ImageView) view.findViewById(R.id.patientImage);
        // TextView date = (TextView) findViewById(R.id.sysdate);
        TextView editpatientName = (TextView) view.findViewById(R.id.patientName);
        TextView editAge = (TextView) view.findViewById(R.id.age);
        TextView editmobileno = (TextView) view.findViewById(R.id.mobileno);
        TextView phoneType = (TextView) view.findViewById(R.id.phoneType);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView txteMail = (TextView) view.findViewById(R.id.txtEmail);


        Button addPatientprescriptionBtn = (Button) view.findViewById(R.id.addPatientprescriptionBtn);

        cancel = (Button) view.findViewById(R.id.cancel);
        addUpdate = (Button) view.findViewById(R.id.addUpdate);

        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) view.findViewById(R.id.termsandCondition);

        fodtextshow = (BootstrapEditText) view.findViewById(R.id.fodtextshow);
        inputnumber = (BootstrapEditText) view.findViewById(R.id.inputnumber);
        days = (Button) view.findViewById(R.id.days);
        week = (Button) view.findViewById(R.id.week);
        month = (Button) view.findViewById(R.id.month);
        btnclear = (Button) view.findViewById(R.id.btnclear);

        visitDate = (EditText) view.findViewById(R.id.visitDate);

        edtInput_weight = (EditText) view.findViewById(R.id.input_weight);
        edtInput_pulse = (EditText) view.findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) view.findViewById(R.id.input_bp);
        edtLowBp = (EditText) view.findViewById(R.id.lowBp);
        edtInput_temp = (EditText) view.findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) view.findViewById(R.id.input_sugar);
        edtInput_sugarfasting = (EditText) view.findViewById(R.id.input_sugarfasting);
        edtInput_bmi = (EditText) view.findViewById(R.id.input_bmi);
        edtInput_height = (EditText) view.findViewById(R.id.input_height);

        txtReferredBy = (TextView) view.findViewById(R.id.txtReferredBy);
        txtReferredTo = (TextView) view.findViewById(R.id.txtReferredTo);

        showReferrals = (LinearLayout) view.findViewById(R.id.showReferrals);

        clinicalNotes = (EditText) view.findViewById(R.id.clinicalNotes);

        showObservationsData = (TextView) view.findViewById(R.id.showObservationsData);
        showVitalsData = (TextView) view.findViewById(R.id.showVitalsData);

        showInvestigationData = (TextView) view.findViewById(R.id.showInvestigationData);
        buttonInvestigation = (Button) view.findViewById(R.id.buttonInvestigation);
        buttonObservations = (Button) view.findViewById(R.id.buttonObservations);

        Button refered = (Button) view.findViewById(R.id.buttonReferrals);
        Button buttonVital = (Button) view.findViewById(R.id.buttonVital);
        Button buttonHistory = (Button) view.findViewById(R.id.buttonHistory);
        buttonHistory.setVisibility(View.GONE);


        ImageView editPersonalInfo = (ImageView) view.findViewById(R.id.editPersonalInfo);
        //linearlayoutEdit.setVisibility(View.GONE); //we are hiding edit button which redirect to edit personal info page  04-05-2017

        edtSymptoms = (ContactsCompletionView) view.findViewById(R.id.symptoms);
        edtDignosis = (ContactsCompletionView) view.findViewById(R.id.dignosis);

        textRefredByShow = (TextView) view.findViewById(R.id.txtrefredby);
        textRefredToShow = (TextView) view.findViewById(R.id.txtrefredto);

        if (databaseClass == null) {
            databaseClass = new DatabaseClass(getActivity().getApplicationContext());
        }
        if (lastNamedb == null) {
            lastNamedb = new LastnameDatabaseClass(getActivity().getApplicationContext());
        }

        addFollowupdateButtonListner();
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivacyPolicy.class);
                startActivity(intent);
            }
        });
          //open TermsCondition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsCondition.class);
                startActivity(intent);
            }
        });
        CheckBox checkBoxFollowUp = (CheckBox) view.findViewById(R.id.checkBoxFollowUp);

        checkBoxFollowUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chk checked?
                if (((CheckBox) v).isChecked()) {

                    strPatientFollowUpStatus = "FollowUp";
                }
            }
        });

        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        //SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        //  Date todayDate = new Date();

        // String dd = sdf.format(todayDate);
        // date.setText("Today's Date " + dd);

        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date todayDate3 = new Date();

        //this date is ued to set update records date in patient history table
        addedTime = sdf3.format(todayDate3);

        //This will give date format in 2-9-2016 format
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        addedOnDate = String.valueOf(new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append(""));
        // visitDate.setText(addedOnDate);

        editpatientName.setText(strName);
        editmobileno.setText(mPhNo);
        //txtReferredBy.setText(strReferedBy);
        txtReferredTo.setText(strReferedTo);
                       /*setting prescription added date to visit date.*/
        if (added_on != null && !added_on.equals("")) {
            visitDate.setText(added_on);
        } else
            visitDate.setText(addedOnDate); //incase added on date comes null or ""

        if (strPhoneType != null) {
            phoneType.setText(strPhoneType + " :");
        } else {
            phoneType.setText("Mobile :");
        }

        if (strEmail != null && !TextUtils.isEmpty(strEmail)) {
            txteMail.setVisibility(View.VISIBLE);
            email.setText(strEmail);
        }
        if (mGender != null && mGender.equals("Male")) {
            editAge.setText("( M - " + mAge + " )");
        } else if (mGender != null && mGender.equals("Female")) {
            editAge.setText("( F - " + mAge + " )");
        } else if (mGender != null && mGender.equals("Trans")) {
            editAge.setText("( T - " + mAge + " )");
        }

        edtprescriptionImgPath = (TextView) view.findViewById(R.id.prescriptionImgPath);
        edtprescriptionImgPath.setVisibility(View.VISIBLE);
        edtprescriptionImgPath.setText(prescriptionimgPath);

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {

            if (strPatientPhoto.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);
                setUpGlide(strPatientPhoto, patientImage);
            }
        }
        try {
            if (sqlController == null) {
                sqlController = new SQLController(getContext());
                sqlController.open();
            }
            dbController = SQLiteHandler.getInstance(getContext());
            //This will get all the visit  history of patient
            appController = new AppController();

            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            docId = sqlController.getDoctorId();
            int maxVisitId = sqlController.getPatientVisitIdCount();
            strVisitId = String.valueOf(maxVisitId + 1);

            ArrayList<RegistrationModel> healthLifeStyleList = sqlController.getHealthAndLifestyle(strPatientId);

            if (healthLifeStyleList.size() > 0) {
                mAlcohol = healthLifeStyleList.get(0).getAlocholConsumption();
                mStressLevel = healthLifeStyleList.get(0).getStressLevel();
                mSmokerType = healthLifeStyleList.get(0).getSmokerType();
                mLifeStyle = healthLifeStyleList.get(0).getLifeSyle();
                String mLactoseTolerance = healthLifeStyleList.get(0).getLactoseTolerance();
                String mFoodPreference = healthLifeStyleList.get(0).getFoodPreference();
                String mFoodHabit = healthLifeStyleList.get(0).getFoodHabit();
                mExcercise = healthLifeStyleList.get(0).getExcercise();
                mChewinogTobaco = healthLifeStyleList.get(0).getChewingTobaco();
                String mBingeEating = healthLifeStyleList.get(0).getBingeEating();
                String mAllergies = healthLifeStyleList.get(0).getAllergies();
                String mSexuallyActive = healthLifeStyleList.get(0).getSexuallyActive();

                String mDrug = healthLifeStyleList.get(0).getDrugConsumption();
                String otherDrugTaking = healthLifeStyleList.get(0).getOtherDrugConsumption();
                String otherTobacoTaking = healthLifeStyleList.get(0).getOtherTobacoConsumption();
                mSleepStatus = healthLifeStyleList.get(0).getSleep();
            }

            if (bannerClass == null) {
                bannerClass = new BannerClass(getContext());
            }
            bannerimgNames = bannerClass.getImageName();
            if (strReferedBy != null && !strReferedBy.equals("")) {
                String referedBy = sqlController.getNameByIdAssociateMaster(strReferedBy);
                showReferrals.setVisibility(View.VISIBLE);
                textRefredByShow.setVisibility(View.VISIBLE);
                txtReferredBy.setVisibility(View.VISIBLE);
                textRefredByShow.setText(referedBy);


            }
            if (strReferedTo != null && !strReferedTo.equals("")) {

                if (strReferedTo.length() > 0) {
                    String delimiter = ",";

                    String[] temp = strReferedTo.split(delimiter);
                    StringBuilder sbname = new StringBuilder();
                    ArrayList<String> abc = new ArrayList<>();

                    for (String aTemp : temp) {
                        String referedto = sqlController.getNameByIdAssociateMaster(aTemp);

                        abc.add(referedto);
                    }

                    for (int i = 0; i < abc.size(); i++) {
                        if (i == 0) {
                            sbname.append(abc.get(0));
                        } else {
                            sbname.append(",").append(abc.get(i));
                        }
                    }
                    if (sbname != null) {
                        showReferrals.setVisibility(View.VISIBLE);
                        textRefredToShow.setVisibility(View.VISIBLE);
                        txtReferredTo.setVisibility(View.VISIBLE);
                        textRefredToShow.setText(sbname);
                    } else {
                        textRefredToShow.setVisibility(View.GONE);
                        txtReferredTo.setVisibility(View.GONE);
                    }
                }
            }
        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        try {
            mSymptomsList = lastNamedb.getSymptoms();
            if (mSymptomsList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mSymptomsList);

                edtSymptoms.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtSymptoms.setThreshold(1);
                edtSymptoms.setAdapter(lastnamespin);
                edtSymptoms.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);

            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        try {
            ArrayList<String> mDiagnosisList = lastNamedb.getDiagnosis();
            if (mDiagnosisList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mDiagnosisList);

                edtDignosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtDignosis.setThreshold(1);
                edtDignosis.setAdapter(lastnamespin);
                edtDignosis.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);

            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        cancel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    cancel.setBackgroundColor(getResources().getColor(R.color.cancelbtn));

                    goToNavigation();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    cancel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }


        });
        addUpdate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                    saveData();//saved data int db

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        addPatientprescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                prescriptionimgPath = "prescription_" + docId + "_" + appController.getDateTime() + ".jpg";

                imagePathFile = new File(imagesFolder, prescriptionimgPath);
                uriSavedImage = Uri.fromFile(imagePathFile);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        buttonInvestigation.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    buttonInvestigation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_withbackground));

                    showInvestigationDialog();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    buttonInvestigation.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        buttonObservations.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    buttonObservations.setBackground(getResources().getDrawable(R.drawable.rounded_corner_withbackground));
                    showObservationsDialog();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    buttonObservations.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        refered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReferedDialogBox();
            }
        });
        buttonVital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addVitalsDialog();
            }
        });
        editPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), NewEditPersonalInfo.class);

                i.putExtra("PATIENTPHOTO", strPatientPhoto);
                i.putExtra("ID", strPatientId);
                i.putExtra("FIRSTNAME", strFirstName);
                i.putExtra("MIDDLEAME", strMiddleName);
                i.putExtra("LASTNAME", strLastName);
                i.putExtra("PHONE", mPhNo);
                i.putExtra("DOB", strDob);
                i.putExtra("AGE", mAge);
                i.putExtra("LANGUAGE", mLang);
                i.putExtra("GENDER", mGender);
                i.putExtra("ADDRESS", strAddress);
                i.putExtra("CITYORTOWN", strCityorTown);
                i.putExtra("DISTRICT", strDistrict);
                i.putExtra("PIN", strPinNo);
                i.putExtra("STATE", strState);
                i.putExtra("ALTERNATENUMBER", strAlternatenumber);
                i.putExtra("ALTERNATENUMBERTYPE", strAlternatephtype);
                i.putExtra("ISDCODE", strIsd_code);
                i.putExtra("ALTERNATEISDCODE", strAlternateIsd_code);
                i.putExtra("UID", strUid);
                i.putExtra("EMAIL", strEmail);
                i.putExtra("PHONETYPE", strPhoneType);

                startActivity(i);
                // finish();
            }
        });

        setUpAnimation();
         setImagesToHealthLifeStyle();
        return view;

    }

    private void showReferedDialogBox() {

        final Dialog dialog = new Dialog(getContext());

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View f = factory.inflate(R.layout.refered_by_dialog, null);

        dialog.setTitle("Referred By-To");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);
        Button dialogButtonCancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) f.findViewById(R.id.customDialogOk);
        final Button addMore = (Button) f.findViewById(R.id.addMore);


        final Spinner nameRefredBySpinner = (Spinner) f.findViewById(R.id.nameRefredBySpinner);
        final Spinner nameRefredTo1Spinner = (Spinner) f.findViewById(R.id.nameRefredTo1Spinner);


        try {
            final ArrayList<HashMap<String, String>> list = sqlController.getAllDataAssociateMaster();

            NameData = new HashMap<>();
            ArrayList<String> abc = new ArrayList<>();

            for (int im = 0; im < list.size(); im++) {
                String strid = list.get(im).get("ID");
                String strName = list.get(im).get("NAME");
                //  String str = list.get(im).get("SPECIALITY");

                NameData.put(strName, strid);
            }

            setSpinner(f);

            nameReferalsList.add(0, "Select Referrals");

            if (strReferedBy != null && !strReferedBy.equals("")) {

                String referedBy = sqlController.getNameByIdAssociateMaster(strReferedBy);
                String title = sqlController.getTitleByNameAssociateMaster(referedBy);
                String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                appController.setSpinnerPosition(nameRefredBySpinner, some_array, title + " " + referedBy);

                textRefredByShow.setText(referedBy);
            }
            if (strReferedTo != null && !strReferedTo.equals("")) {

                if (strReferedTo.length() > 0) {
                    String delimiter = ",";

                    String[] temp = strReferedTo.split(delimiter);

                    for (String aTemp : temp) {
                        String referedTo = sqlController.getNameByIdAssociateMaster(aTemp);
                        abc.add(referedTo);
                    }
                }
                int size = abc.size();

                if (size > 0) {

                    String strName = abc.get(0);

                    String title = sqlController.getTitleByNameAssociateMaster(strName);
                    String[] some_array;
                    some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                    appController.setSpinnerPosition(nameRefredTo1Spinner, some_array, title + " " + strName);
                }
            }


        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCounter = 0;
                dialog.dismiss();

            }
        });

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {


            String code;

            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sbname = new StringBuilder();
                if (addCounter >= 0) {

                    if (strReferredTo1Name != null && !strReferredTo1Name.equals("") && strReferredTo1Name.length() > 0) {
                        code = NameData.get(strReferredTo1Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(index);
                            sbname.append(strReferredTo1Name);
                        }
                    }
                }
                if (addCounter >= 1) {

                    if (strReferredTo2Name != null && !strReferredTo2Name.equals("") && strReferredTo2Name.length() > 0) {
                        code = NameData.get(strReferredTo2Name.trim());
                        if (code != null) {

                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo2Name);
                        }
                    }
                }
                if (addCounter >= 2) {

                    if (strReferredTo3Name != null && !strReferredTo3Name.equals("") && strReferredTo3Name.length() > 0) {
                        code = NameData.get(strReferredTo3Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo3Name);
                        }
                    }
                }
                if (addCounter >= 3) {

                    if (strReferredTo4Name != null && !strReferredTo4Name.equals("") && strReferredTo4Name.length() > 0) {
                        code = NameData.get(strReferredTo4Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo4Name);
                        }
                    }
                }
                if (addCounter >= 4) {

                    if (strReferredTo5Name != null && !strReferredTo5Name.equals("") && strReferredTo5Name.length() > 0) {
                        code = NameData.get(strReferredTo5Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo5Name);
                        }
                    }
                }

                strReferedTo = String.valueOf(sb);

                if (strReferredByName != null && !strReferredByName.equals("") && strReferredByName.length() > 0) {
                    code = NameData.get(strReferredByName.trim());
                    if (code != null) {
                        int index = Integer.parseInt(code);
                        strReferedBy = String.valueOf(index);
                        textRefredByShow.setText("");
                        textRefredByShow.setText(strReferredByName);
                    } else {
                        textRefredByShow.setText("");
                        textRefredByShow.setVisibility(View.GONE);
                        txtReferredBy.setVisibility(View.GONE);
                    }
                }

                showReferrals.setVisibility(View.VISIBLE);

                strReferedTo = String.valueOf(sb);
                String insertedName = String.valueOf(sbname);
                insertedName = appController.removeCommaOccurance(insertedName);

                if (insertedName != null && !insertedName.equals("") && insertedName.length() > 0) {
                    textRefredToShow.setVisibility(View.VISIBLE);
                    txtReferredTo.setVisibility(View.VISIBLE);
                    textRefredToShow.setText(insertedName + "");
                } else {
                    textRefredToShow.setVisibility(View.INVISIBLE);
                    txtReferredTo.setVisibility(View.INVISIBLE);
                }

                textRefredToShow.setText(insertedName + "");
                addCounter = 0;
                dialog.dismiss();
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addCounter = addCounter + 1;
                if (addCounter == 1) {
                    LinearLayout refred1 = (LinearLayout) dialog.findViewById(R.id.refred1);
                    refred1.setVisibility(View.VISIBLE);
                }
                if (addCounter == 2) {
                    LinearLayout refred2 = (LinearLayout) dialog.findViewById(R.id.refred2);
                    refred2.setVisibility(View.VISIBLE);
                }
                if (addCounter == 3) {
                    LinearLayout refred3 = (LinearLayout) dialog.findViewById(R.id.refred3);
                    refred3.setVisibility(View.VISIBLE);
                }
                if (addCounter == 4) {
                    LinearLayout refred4 = (LinearLayout) dialog.findViewById(R.id.refred4);
                    refred4.setVisibility(View.VISIBLE);
                }
                if (addCounter >= 4) {
                    addMore.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Limit Exceed! You can not add more", Toast.LENGTH_LONG).show();
                }

            }
        });


        dialog.show();
    }

    private void setSpinner(View f) {

        final Spinner nameRefredBySpinner = (Spinner) f.findViewById(R.id.nameRefredBySpinner);
        final Spinner nameRefredTo1Spinner = (Spinner) f.findViewById(R.id.nameRefredTo1Spinner);
        final Spinner nameRefredTo2Spinner = (Spinner) f.findViewById(R.id.nameRefredTo2Spinner);
        final Spinner nameRefredTo3Spinner = (Spinner) f.findViewById(R.id.nameRefredTo3Spinner);
        final Spinner nameRefredTo4Spinner = (Spinner) f.findViewById(R.id.nameRefredTo4Spinner);
        final Spinner nameRefredTo5Spinner = (Spinner) f.findViewById(R.id.nameRefredTo5Spinner);


        final TextView refredtoSpeciality1 = (TextView) f.findViewById(R.id.refredtoSpeciality1);
        final TextView refredtoSpeciality2 = (TextView) f.findViewById(R.id.refredtoSpeciality2);
        final TextView refredtoSpeciality3 = (TextView) f.findViewById(R.id.refredtoSpeciality3);
        final TextView refredtoSpeciality4 = (TextView) f.findViewById(R.id.refredtoSpeciality4);
        final TextView refredtoSpeciality5 = (TextView) f.findViewById(R.id.refredtoSpeciality5);
        final TextView refredBySpeciality = (TextView) f.findViewById(R.id.refredBySpeciality);

        try {
            nameReferalsList = dbController.getReferalsnew();

            if (nameReferalsList.size() > 0) {
                ArrayAdapter<String> referralName = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line);

                referralName.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                referralName.add("Select Referrals");
                referralName.addAll(nameReferalsList);
                nameRefredBySpinner.setAdapter(referralName);
                nameRefredTo1Spinner.setAdapter(referralName);
                nameRefredTo2Spinner.setAdapter(referralName);
                nameRefredTo3Spinner.setAdapter(referralName);
                nameRefredTo4Spinner.setAdapter(referralName);
                nameRefredTo5Spinner.setAdapter(referralName);

            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        nameRefredTo1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo1Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo1Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo1Name, ".")) {
                            String[] parts = strReferredTo1Name.split(". ", 2);
                            //    String string1 = parts[0];//namealias
                            strReferredTo1Name = parts[1].trim();//actual name

                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo1Name.trim());
                        if (list.size() > 0) {
                            //strReferredTo1Id = list.get(0).get("ID");
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality1.setText(strSpeclty);
                        }
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nameRefredTo2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo2Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo2Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo2Name, ".")) {
                            String[] parts = strReferredTo2Name.split(". ", 2);
                            //    String string1 = parts[0];//namealias
                            strReferredTo2Name = parts[1].trim();//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo2Name.trim());
                        if (list.size() > 0) {
                            //strReferredTo2Id = list.get(0).get("ID");
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality2.setText(strSpeclty);
                        }
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nameRefredTo3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo3Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo3Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo3Name, ".")) {
                            String[] parts = strReferredTo3Name.split(". ", 2);
                            //    String string1 = parts[0];//namealias
                            strReferredTo3Name = parts[1].trim();//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo3Name.trim());
                        if (list.size() > 0) {
                            //strReferredTo3Id = list.get(0).get("ID");
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality3.setText(strSpeclty);
                        }
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nameRefredTo4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo4Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo4Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo4Name, ".")) {
                            String[] parts = strReferredTo4Name.split(". ", 2);
                            //    String string1 = parts[0];//namealias
                            strReferredTo4Name = parts[1].trim();//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo4Name.trim());
                        if (list.size() > 0) {
                            // strReferredTo4Id = list.get(0).get("ID");
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality4.setText(strSpeclty);
                        }
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        nameRefredTo5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo5Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo5Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo2Name, ".")) {
                            String[] parts = strReferredTo2Name.split(". ", 2);
                            //    String string1 = parts[0];//namealias
                            strReferredTo2Name = parts[1].trim();//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo5Name.trim());
                        if (list.size() > 0) {
                            //strReferredTo5Id = list.get(0).get("ID");
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality5.setText(strSpeclty);
                        }
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nameRefredBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredByName = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredBySpinner.getSelectedItem() != "Select Referrals") {
                        if (appController.contains(strReferredByName, ".")) {
                            String[] parts = strReferredByName.split(". ", 2);
                            //    String string1 = parts[0];//namealias
                            strReferredByName = parts[1].trim();//actual name

                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredByName.trim());
                        if (list.size() > 0) {
                            //strReferredById = list.get(0).get("ID");
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredBySpeciality.setText(strSpeclty);
                        }
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //saved the user enetred data to db
    private void saveData() {

        monthSel = null;
        fowSel = null;
        String clinical_note = clinicalNotes.getText().toString().trim();


        String strSymptoms = edtSymptoms.getText().toString().trim();
        List symptoms = edtSymptoms.getObjects();
        String[] arraySymptoms = (String[]) symptoms.toArray(new String[symptoms.size()]);
        String sympt = Arrays.toString(arraySymptoms);
        strSymptoms = strSymptoms.replace(",,", "");

        String correctSymptoms = sympt.substring(1, sympt.length() - 1);
        strSymptoms = correctSymptoms + strSymptoms;

        String strDignosis = edtDignosis.getText().toString().trim();

        List dignosis = edtDignosis.getObjects();
        String[] diagonosArray = (String[]) dignosis.toArray(new String[dignosis.size()]);
        String diagonos = Arrays.toString(diagonosArray);
        strDignosis = strDignosis.replace(",,", "");

        String correctDignosis = diagonos.substring(1, diagonos.length() - 1);
        strDignosis = correctDignosis + strDignosis;

        usersellectedDate = fodtextshow.getText().toString();

        if (TextUtils.isEmpty(strSymptoms) && TextUtils.isEmpty(strDignosis)) {
            Toast.makeText(getContext(), "Please enter any of Symptoms or Diagnosis ", Toast.LENGTH_LONG).show();
            // ailment.setError("Please enter Ailment");
            return;
        }


        String visit_date = addedOnDate;
        //  Log.e("visit_date", "" + visit_date);

        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        // String visit_date1 = addedOnDate.toString();
        String added_on = visitDate.getText().toString();

        try {
            //convert visit date from 2016-11-1 to 2016-11-01
            visit_date = myFormat.format(fromUser.parse(added_on));
            added_on = myFormat.format(fromUser.parse(addedOnDate));
            //usersellectedDate=myFormat.format(fromUser.parse(usersellectedDate));
            if (usersellectedDate != null && !usersellectedDate.equals("")) {
                usersellectedDate = myFormat.format(fromUser.parse(usersellectedDate));
            }

        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        String flag = "0";
        String added_by = docId;
        String patientInfoType = "App";
        String action = "added";
        String record_source = "QuickAdd New Visit";
        //  String strFollowUpStatus = null;

        dbController.addPatientNextVisitRecord(strVisitId, strPatientId, usersellectedDate, daysSel, fowSel, monthSel, clinical_note, prescriptionimgPath, visit_date, docId, doctor_membership_number, added_on, addedTime, flag, added_by, action, patientInfoType,
                strWeight, strPulse, strBp, strLowBp, strTemp, strSymptoms, strDignosis, strHeight, strbmi, strReferedBy, strReferedTo, strPatientFollowUpStatus, record_source, strSpo2, strRespirationRate,strObestity);

        dbController.deletePrescriptionImageQueue(prescriptionimgId, prescriptionimgPath);

        //saving investigation data

        dbController.addInvestigation(strPatientId, strVisitId, strSgar, strSugarFasting, strHbA1c, strAcer, strSerumUrea, strLipidHDL, strLipidTC
                , strLipidTG, strLipidLDL, strLipidVHDL, strEcg, strPft, flag);

        Toast.makeText(getContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
        //Redirect to navigation Activity
        goToNavigation();

    }

    private void goToNavigation() {
        Intent i = new Intent(getActivity(), NavigationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);  // it will directly jump to navigation activity eith called fragment
        startActivity(i);
        getActivity().finish();
    }

    private void setUpGlide(String imgPath, ImageView patientImage) {

        Glide.with(getContext())
                .load(imgPath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .dontAnimate()
                .error(R.drawable.main_profile)
                .into(patientImage);
    }

    private void addFollowupdateButtonListner() {

        days.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    days.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    week.setTextColor(getResources().getColor(R.color.black));
                    month.setTextColor(getResources().getColor(R.color.black));
                    days.setTextColor(getResources().getColor(R.color.white));
                    btnclear.setTextColor(getResources().getColor(R.color.black));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        week.setBackground(getResources().getDrawable(R.drawable.circle));
                        month.setBackground(getResources().getDrawable(R.drawable.circle));
                        btnclear.setBackground(getResources().getDrawable(R.drawable.circle));
                    }

                    value = inputnumber.getText().toString().trim();

                    if (TextUtils.isEmpty(value)) {
                        inputnumber.setError("Please Enter Value");
                        return true;
                    }


                    int val = Integer.parseInt(value);

                    int days = Integer.parseInt(value);
                    //
                    if (days > 366) {
                        inputnumber.setError("Enter up to 366 Days");
                        return true;
                    } else {
                        inputnumber.setError(null);
                    }
                    String strVisitDate = visitDate.getText().toString();
                    Date calDate = appController.stringToDate(strVisitDate);
                    String dateis = sdf1.format(AppController.addDay1(calDate, val));
                    fodtextshow.setText(dateis);
                    daysSel = value;
                    usersellectedDate = dateis;

                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    days.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        week.setBackground(getResources().getDrawable(R.drawable.circle));
                        month.setBackground(getResources().getDrawable(R.drawable.circle));
                    }


                }
                return false;
            }
        });

        week.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                week.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                week.setTextColor(getResources().getColor(R.color.white));
                month.setTextColor(getResources().getColor(R.color.black));
                days.setTextColor(getResources().getColor(R.color.black));
                btnclear.setTextColor(getResources().getColor(R.color.black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    days.setBackground(getResources().getDrawable(R.drawable.circle));
                    month.setBackground(getResources().getDrawable(R.drawable.circle));
                    btnclear.setBackground(getResources().getDrawable(R.drawable.circle));
                }

                value = inputnumber.getText().toString().trim();

                if (TextUtils.isEmpty(value)) {
                    inputnumber.setError("Please Enter Value");
                    return;
                }
                long val = Long.parseLong(value);

                if (value != null) {

                    if (val > 54) {
                        inputnumber.setError("Enter up to 54 Weeks");
                        return;
                    } else {
                        inputnumber.setError(null);
                    }
                }
                int fVal = (int) (val * 7);
                String strVisitDate = visitDate.getText().toString();
                Date calDate = appController.stringToDate(strVisitDate);
                String dateis = sdf1.format(AppController.addDay1(calDate, fVal));
                usersellectedDate = dateis;
                fowSel = value;
                fodtextshow.setText(dateis);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                week.setTextColor(getResources().getColor(R.color.black));
                month.setTextColor(getResources().getColor(R.color.white));
                days.setTextColor(getResources().getColor(R.color.black));
                btnclear.setTextColor(getResources().getColor(R.color.black));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    days.setBackground(getResources().getDrawable(R.drawable.circle));
                    week.setBackground(getResources().getDrawable(R.drawable.circle));
                    btnclear.setBackground(getResources().getDrawable(R.drawable.circle));
                }

                value = inputnumber.getText().toString().trim();

                if (TextUtils.isEmpty(value)) {
                    inputnumber.setError("Please Enter Value");
                    return;
                }
                long val = Long.parseLong(value);
                if (val > 12) {
                    inputnumber.setError("Enter up to 12 Months");
                    return;
                } else {
                    inputnumber.setError(null);
                }
                String strVisitDate = visitDate.getText().toString();
                Date calDate = appController.stringToDate(strVisitDate);
                String dateis = sdf1.format(AppController.addMonth(calDate, Integer.parseInt(value)));
                usersellectedDate = dateis;
                monthSel = value;
                fodtextshow.setText(dateis);
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnclear.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                btnclear.setTextColor(getResources().getColor(R.color.white));
                inputnumber.setText("");
                fodtextshow.setText("");
                month.setTextColor(getResources().getColor(R.color.black));
                days.setTextColor(getResources().getColor(R.color.black));
                week.setTextColor(getResources().getColor(R.color.black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    days.setBackground(getResources().getDrawable(R.drawable.circle));
                    month.setBackground(getResources().getDrawable(R.drawable.circle));
                    week.setBackground(getResources().getDrawable(R.drawable.circle));
                }
                daysSel = null;
                fowSel = null;
                monthSel = null;
                CleanFollowup();
            }
        });

        visitDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog(DATE_DIALOG_ID1);

            }
        });
        fodtextshow.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog(DATE_DIALOG_ID2);

            }
        });

    }

    private void shpwDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID1: //for visit date

                final Calendar c1 = Calendar.getInstance();
                int mYear1 = c1.get(Calendar.YEAR);
                int mMonth1 = c1.get(Calendar.MONTH);
                int mDay1 = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd2 = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                visitDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear1, mMonth1, mDay1);
                c1.add(Calendar.DATE, 0);

                Date newDate = c1.getTime();
                dpd2.getDatePicker().setMaxDate(newDate.getTime());

                dpd2.show();

                break;

            case DATE_DIALOG_ID2: //for fod

                final Calendar c3 = Calendar.getInstance();
                int mYear3 = c3.get(Calendar.YEAR);
                int mMonth3 = c3.get(Calendar.MONTH);
                int mDay3 = c3.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd3 = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                fodtextshow.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                               /* showfodtext.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);*/
                                inputnumber.setText("");
                                CleanFollowup();

                            }
                        }, mYear3, mMonth3, mDay3);
                c3.add(Calendar.DATE, 1);

                Date newDate3 = c3.getTime();
                dpd3.getDatePicker().setMinDate(newDate3.getTime());

                dpd3.show();

                break;
        }
    }

    private void setUpAnimation() {
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        try {
            bannerimgNames = bannerClass.getImageName();
            appController.setUpAdd(getContext(), bannerimgNames, backChangingImages, doctor_membership_number, "Quick Add New Records");

        } catch (ClirNetAppException e) {
            e.printStackTrace();
        }
    }

    private void CleanFollowup() {

        month.setTextColor(getResources().getColor(R.color.black));
        days.setTextColor(getResources().getColor(R.color.black));
        week.setTextColor(getResources().getColor(R.color.black));
        inputnumber.setText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            days.setBackground(getResources().getDrawable(R.drawable.circle));
            month.setBackground(getResources().getDrawable(R.drawable.circle));
            week.setBackground(getResources().getDrawable(R.drawable.circle));
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AppController.getInstance().trackScreenView("Quick Add Visit Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        view = null;
        if (databaseClass != null) {
            databaseClass.close();
            databaseClass = null;
        }
        if (lastNamedb != null) {
            lastNamedb.close();
            lastNamedb = null;
        }
        if (dbController != null) {
            dbController.close();
            dbController = null;
        }
        if (bannerClass != null) {
            bannerClass.close();
            bannerClass = null;
        }
        if (appController != null) {
            appController = null;
        }
        backChangingImages = null;
        mSymptomsList = null;
        doctor_membership_number = null;
        docId = null;
        sdf1 = null;
        strName = null;
        mPhNo = null;
        mAge = null;
        mLang = null;
        mGender = null;
        addedOnDate = null;
        strPatientPhoto = null;
        clinicalNotes = null;
        strPatientId = null;
        visitDate = null;
        fowSel = null;
        usersellectedDate = null;
        monthSel = null;
        sqlController = null;
        docId = null;
        addedTime = null;
        addedOnDate = null;
        prescriptionimgPath = null;
        prescriptionimgId = null;
        backChangingImages = null;
        addUpdate = null;
        cancel = null;
        edtInput_weight = null;
        edtInput_pulse = null;
        edtInput_bp = null;
        edtLowBp = null;
        edtInput_temp = null;
        edtInput_sugar = null;
        edtSymptoms = null;
        edtDignosis = null;
        fodtextshow = null;
        days = null;
        week = null;
        month = null;
        inputnumber = null;
        value = null;
        daysSel = null;

        edtInput_sugarfasting = null;
        edtInput_bmi = null;
        edtInput_height = null;
        btnclear = null;
        edtprescriptionImgPath = null;
        textRefredByShow = null;
        textRefredToShow = null;
        strReferredByName = null;
        strReferredTo1Name = null;
        strReferredTo2Name = null;
        strReferredTo3Name = null;
        strReferredTo4Name = null;
        strReferredTo5Name = null;
        nameReferalsList = null;
        edtInput_weight = null;
        edtInput_pulse = null;
        edtInput_bp = null;
        edtLowBp = null;
        edtInput_temp = null;
        edtInput_sugar = null;
        edtInput_sugarfasting = null;
        edtInput_bmi = null;
        edtInput_height = null;
        txtReferredTo = null;
        txtReferredBy = null;
        strWeight = null;
        strPulse = null;
        strBp = null;
        strLowBp = null;
        strTemp = null;

        strSugarFasting = null;
        strHeight = null;
        showReferrals = null;
        buttonObservations= null;
        strPatientFollowUpStatus=null;
        imagePathFile = null;
        strEmail = null;
        strPhoneType = null;
        strFirstName = null;
        strMiddleName = null;
        strLastName = null;
        strDob = null;
        strAddress = null;
        strCityorTown = null;
        strDistrict = null;
        strPinNo = null;
        strState = null;
        strAlternatenumber = null;
        strAlternatephtype = null;
        strIsd_code = null;
        strUid = null;
        strAlternateIsd_code = null;
        sbVitals = null;
        sbInvestigations = null;
        strSpo2=null;strRespirationRate=null;
        mSleepStatus=null;
        strPallorDescription= null;
        strCyanosisDescription= null;
        strTremorsDescription= null;
        strIcterusDescription= null;
        strClubbingDescription= null;
        strOedemaDescription= null;
        strCalfTendernessDescription= null;
        strLymphadenopathyDescription= null;
        edtInput_spo2=null;
        edtInput_respiration_rate=null;
        strPallore=null;
        strCyanosis=null; strTremors=null; strIcterus=null;strClubbing=null; strOedema=null; strCalfTenderness=null;
        strLymphadenopathy=null;showObservationsData=null;
        sbVitals= null;sbObservations =null;sbInvestigations=null;

        showInvestigationData=null;showVitalsData=null;showObservationsData=null;
        strEcg=null;
        strPft=null;
        strLipidTC=null;
        strLipidTG=null;
        strLipidLDL=null;
        strLipidVHDL=null;
        strLipidHDL=null;
        strSgar=null;
        strHbA1c=null;
        strSerumUrea=null;
        strAcer=null;
        buttonInvestigation=null;
        strVisitId=null;

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    //Image capture code
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image display it in image view
                    new ImageCompression(getContext(), imagePathFile.getPath()).execute(uriSavedImage.getPath().trim());

                    previewCapturedImage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    //this will show captured image to image view
    private void previewCapturedImage() {
        try {
            String prescriptionImgPath = uriSavedImage.getPath();
            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(prescriptionImgPath);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void addVitalsDialog() {

        final Dialog dialog;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            dialog = new Dialog(new ContextThemeWrapper(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan));
            LayoutInflater factory = LayoutInflater.from(getContext());

            final View f = factory.inflate(R.layout.vitals_in_dialog, null);

            Button cancel = (Button) f.findViewById(R.id.customDialogCancel);
            Button save = (Button) f.findViewById(R.id.customDialogOk);
            edtInput_weight = (EditText) f.findViewById(R.id.input_weight);
            edtInput_pulse = (EditText) f.findViewById(R.id.input_pulse);
            edtInput_bp = (EditText) f.findViewById(R.id.input_bp);
            edtLowBp = (EditText) f.findViewById(R.id.lowBp);
            edtInput_temp = (EditText) f.findViewById(R.id.input_temp);
            edtInput_bmi = (EditText) f.findViewById(R.id.input_bmi);
            edtInput_height = (EditText) f.findViewById(R.id.input_height);
            edtInput_spo2 = (EditText) f.findViewById(R.id.input_spo2);
            edtInput_respiration_rate = (EditText) f.findViewById(R.id.input_respiration_rate);
            ediInput_obesity=(EditText)f.findViewById(R.id.input_obesity);

            dialog.setTitle(" Add Vitals ");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(f);
            sbVitals.setLength(0);

            if (strWeight != null && !strWeight.equals("") && strWeight.length() > 0) {
                edtInput_weight.setText(strWeight);
            }

            if (strHeight != null && !strHeight.equals("") && strHeight.length() > 0) {

                edtInput_height.setText(strHeight);
            }

            if (strbmi != null && !strbmi.equals("") && strbmi.length() > 0) {

                edtInput_bmi.setText(strbmi);
            }

            if (strPulse != null && !strPulse.equals("") && strPulse.length() > 0) {

                edtInput_pulse.setText(strPulse);
            }
            if (strBp != null && !strBp.equals("") && strBp.length() > 0) {

                edtInput_bp.setText(strBp);
            }
            if (strLowBp != null && !strLowBp.equals("") && strLowBp.length() > 0) {

                edtLowBp.setText(strLowBp);
            }
            if (strTemp != null && !strTemp.equals("") && strTemp.length() > 0) {

                edtInput_temp.setText(strTemp);
            }
            if (strWeight != null && !strWeight.equals("") && strWeight.length() > 0) {
                edtInput_weight.setText(strWeight);
            }

            if (strHeight != null && !strHeight.equals("") && strHeight.length() > 0) {

                edtInput_height.setText(strHeight);
            }
            if (strObestity != null && !strObestity.equals("") && strObestity.length() > 0) {
                ediInput_obesity.setText(strObestity);
            }
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strWeight = edtInput_weight.getText().toString().trim();
                    strPulse = edtInput_pulse.getText().toString().trim();
                    strBp = edtInput_bp.getText().toString().trim();
                    strLowBp = edtLowBp.getText().toString().trim();
                    strTemp = edtInput_temp.getText().toString().trim();

                    strHeight = edtInput_height.getText().toString().trim();
                    strbmi = edtInput_bmi.getText().toString().trim();

                    strSpo2 = edtInput_spo2.getText().toString();
                    strRespirationRate = edtInput_respiration_rate.getText().toString();
                    strObestity=ediInput_obesity.getText().toString();

                    if (strTemp.length() > 0) {
                        int intTemp = Integer.parseInt(strTemp);
                        if (intTemp > 110) {
                            edtInput_temp.setError(" Temp can not be more than 110 ");
                            return;
                        } else {
                            edtInput_temp.setError(null);
                        }
                    }
                    if (strWeight != null && !strWeight.equals("") && strWeight.length() > 0) {
                        sbVitals.append("Weight: ").append(strWeight).append(" ;");
                    }

                    if (strHeight != null && !strHeight.equals("") && strHeight.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Height: ").append(strHeight).append(" ;");
                    }

                    if (strbmi != null && !strbmi.equals("") && strbmi.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Bmi: ").append(strbmi).append(" ;");
                    }
                    if (strObestity != null && !strObestity.equals("") && strObestity.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Obesity: ").append(strObestity).append(" ;");
                    }
                    if (strPulse != null && !strPulse.equals("") && strPulse.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Pulse: ").append(strPulse).append(" ;");
                    }
                    if (strBp != null && !strBp.equals("") && strBp.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Systole: ").append(strBp).append(" ;");
                    }
                    if (strLowBp != null && !strLowBp.equals("") && strLowBp.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Diastole: ").append(strLowBp).append(" ;");
                    }
                    if (strTemp != null && !strTemp.equals("") && strTemp.length() > 0) {

                        sbVitals.append("  ");
                        sbVitals.append(" Temp: ").append(strTemp).append(" ;");
                    }
                    showVitalsData.setText(sbVitals);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        edtInput_height.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                edtInput_height.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String bmi = appController.CalculateBMI(edtInput_weight.getText().toString(), edtInput_height.getText().toString());
                edtInput_bmi.setText(bmi);
                strObestity=appController.getObesity(bmi);
                // Log.e("obestity","  "+strObestity);
                ediInput_obesity.setText(strObestity);
            }
        });
        edtInput_weight.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                edtInput_weight.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String bmi = appController.CalculateBMI(edtInput_weight.getText().toString(), edtInput_height.getText().toString());
                edtInput_bmi.setText(bmi);
                strObestity=appController.getObesity(bmi);
                // Log.e("obestity","  "+strObestity);
                ediInput_obesity.setText(strObestity);
            }
        });
    }

    private void showInvestigationDialog() {

        final Dialog dialog;

        dialog = new Dialog(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View f = factory.inflate(R.layout.investigation_dialog, null);
        dialog.setTitle(" Edit Investigation ");
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
        edtInput_sugar = (EditText) f.findViewById(R.id.input_sugar);
        edtInput_sugarfasting = (EditText) f.findViewById(R.id.input_sugarfasting);

        RadioGroup radioEcg = (RadioGroup) f.findViewById(R.id.radioEcg);
        RadioGroup radioPft = (RadioGroup) f.findViewById(R.id.radioPft);


        sbInvestigations.setLength(0);

        if (strLipidTC != null) {
            input_LipidTC.setText(strLipidTC);
        }
        if (strLipidTG != null && !strLipidTG.equals("")) input_LipidTG.setText(strLipidTG);
        if (strLipidLDL != null && !strLipidLDL.equals("")) input_LipidLDL.setText(strLipidLDL);
        if (strLipidVHDL != null && !strLipidVHDL.equals("")) input_LipidVHDL.setText(strLipidVHDL);
        if (strLipidHDL != null && !strLipidHDL.equals("")) input_LipidHDL.setText(strLipidHDL);
        if (strSgar != null && !strSgar.equals("")) edtInput_sugar.setText(strSgar);
        if (strSugarFasting != null && !strSugarFasting.equals(""))
            edtInput_sugarfasting.setText(strSugarFasting);
        if (strHbA1c != null && !strHbA1c.equals("")) input_hba1c.setText(strHbA1c);
        if (strSerumUrea != null && !strSerumUrea.equals("")) input_seremUrea.setText(strSerumUrea);
        if (strAcer != null && !strAcer.equals("")) input_acer.setText(strAcer);


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
                strSugarFasting = edtInput_sugarfasting.getText().toString();
                strSgar = edtInput_sugar.getText().toString();
                strHbA1c = input_hba1c.getText().toString();
                strSerumUrea = input_seremUrea.getText().toString();
                strAcer = input_acer.getText().toString();
                String flag = "0";

                if (strSgar != null && !strSgar.equals("") && strSgar.length() > 0) {
                    sbInvestigations.append("Sugar(PPG):").append(strSgar).append(" ;");

                }
                if (strSugarFasting != null && !strSugarFasting.equals("") && strSugarFasting.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" Sugar(FPG):").append(strSugarFasting).append(" ;");
                }
                if (strEcg != null && !strEcg.equals("") && strEcg.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" ECG:").append(strEcg).append(" ;");
                }
                if (strPft != null && !strPft.equals("") && strPft.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" PFT:").append(strPft).append(" ;");
                }
                if (strHbA1c != null && !strHbA1c.equals("") && strHbA1c.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" HbA1c:").append(strHbA1c).append(" ;");
                }
                if (strAcer != null && !strAcer.equals("") && strAcer.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" Acer:").append(strAcer).append(" ;");
                }
                if (strSerumUrea != null && !strSerumUrea.equals("") && strSerumUrea.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" SerumUrea:").append(strSerumUrea).append(" ;");
                }
                if (strLipidHDL != null && !strLipidHDL.equals("") && strLipidHDL.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" HDL:").append(strLipidHDL).append(" ;");
                }
                if (strLipidTC != null && !strLipidTC.equals("") && strLipidTC.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" TC:").append(strLipidTC).append(" ;");
                }
                if (strLipidTG != null && !strLipidTG.equals("") && strLipidTG.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" TG:").append(strLipidTG).append(" ;");
                }
                if (strLipidLDL != null && !strLipidLDL.equals("") && strLipidLDL.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" LDL:").append(strLipidLDL).append(" ;");
                }
                if (strLipidVHDL != null && !strLipidVHDL.equals("") && strLipidVHDL.length() > 0) {
                    sbInvestigations.append("  ");
                    sbInvestigations.append(" VLDL:").append(strLipidVHDL).append(" ");
                }

               /* dbController.addInvestigation(strPatientId, strVisitId, strSgar, strSugarFasting, strHbA1c, strAcer, strSerumUrea, strLipidHDL, strLipidTC
                        , strLipidTG, strLipidLDL, strLipidVHDL, strEcg, strPft, flag);*/

                showInvestigationData.setText(sbInvestigations);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void setImagesToHealthLifeStyle() {

        ImageView imgSmoke = (ImageView) view. findViewById(R.id.imgSmoke);
        ImageView imgDrink = (ImageView)view. findViewById(R.id.imgDrink);
        ImageView imgTobaco = (ImageView) view.findViewById(R.id.imgTobaco);
        ImageView imgStress = (ImageView)view. findViewById(R.id.imgStress);
        ImageView imgLifeStyle=(ImageView)view. findViewById(R.id.imgLifeStyle);
        ImageView imgExcercise=(ImageView)view. findViewById(R.id.imgExcercise);

        Log.e("mSmokerType",""+mSmokerType + "" +mAlcohol);

        if(mSmokerType!=null && !mSmokerType.equals("")&& mSmokerType.equals("Active Smoker")){
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.smoke));
        }else if(mSmokerType!=null && !mSmokerType.equals("")&& mSmokerType.equals("Non Smoker")){
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.no_smoke));
        }
        if(mAlcohol!=null && !mAlcohol.equals("")&& mAlcohol.equals("Drinker")){
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.drink));
        }else if(mAlcohol!=null && !mAlcohol.equals("")&& mAlcohol.equals("Non Drinker")){
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.no_drink));
        }
        if(mChewinogTobaco!=null && !mChewinogTobaco.equals("")&& mChewinogTobaco.equals("Yes")){
            imgTobaco.setVisibility(View.VISIBLE);
            imgTobaco.setImageDrawable(getResources().getDrawable(R.drawable.tobacco));
        }else if(mSleepStatus!=null && !mSleepStatus.equals("")&& mSleepStatus.equals("No")){
            imgTobaco.setVisibility(View.VISIBLE);
            imgTobaco.setImageDrawable(getResources().getDrawable(R.drawable.no_tobacco));
        }
        if(mStressLevel!=null && !mStressLevel.equals("")&& mStressLevel.equals("Low")){
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stress_low));
        }else if(mStressLevel!=null && !mStressLevel.equals("")&& mStressLevel.equals("Moderate")){
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stress_moderate));
        } else if(mStressLevel!=null && !mStressLevel.equals("")&& mStressLevel.equals("High")){
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stress_high));
        }

        if(mExcercise!=null && !mExcercise.equals("")&& mExcercise.equals("Yes")){
            imgExcercise.setVisibility(View.VISIBLE);
            imgExcercise.setImageDrawable(getResources().getDrawable(R.drawable.exercise));
        }else if(mExcercise!=null && !mExcercise.equals("")&& mExcercise.equals("No")){
            imgExcercise.setVisibility(View.VISIBLE);
            imgExcercise.setImageDrawable(getResources().getDrawable(R.drawable.no_exercise));
        }
        if(mLifeStyle!=null && !mLifeStyle.equals("")&& mLifeStyle.equals("Sedentary")){
            imgLifeStyle.setVisibility(View.VISIBLE);
            imgLifeStyle.setImageDrawable(getResources().getDrawable(R.drawable.sitting));
        }else if(mLifeStyle!=null && !mLifeStyle.equals("")&& mLifeStyle.equals("Light Active")){
            imgLifeStyle.setVisibility(View.VISIBLE);
            imgLifeStyle.setImageDrawable(getResources().getDrawable(R.drawable.walking));
        }
        else if(mLifeStyle!=null && !mLifeStyle.equals("")&& mLifeStyle.equals("Active")){
            imgLifeStyle.setVisibility(View.VISIBLE);
            imgLifeStyle.setImageDrawable(getResources().getDrawable(R.drawable.running));
        }
    }

    private void showObservationsDialog() {

        final Dialog dialog;

        dialog = new Dialog(getContext());
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View f = factory.inflate(R.layout.observation_dialog, null);
        dialog.setTitle(" Edit Observation ");
        dialog.setCanceledOnTouchOutside(false);

        dialog.setContentView(f);


        Button cancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button ok = (Button) f.findViewById(R.id.customDialogOk);

        RadioGroup radioPallor = (RadioGroup) f.findViewById(R.id.radioPallor);
        RadioGroup radioCyanosis = (RadioGroup) f.findViewById(R.id.radioCyanosis);
        RadioGroup radioTremors = (RadioGroup) f.findViewById(R.id.radioTremors);
        RadioGroup radioIcterus = (RadioGroup) f.findViewById(R.id.radioIcterus);
        RadioGroup radioClubbing = (RadioGroup) f.findViewById(R.id.radioClubbing);
        RadioGroup radioOedema = (RadioGroup) f.findViewById(R.id.radioOedema);
        RadioGroup radioCalfTenderness = (RadioGroup) f.findViewById(R.id.radioCalfTenderness);
        RadioGroup radioLymphadenopathy = (RadioGroup) f.findViewById(R.id.radioLymphadenopathy);

        final EditText pallorDescription = (EditText) f.findViewById(R.id.pallorDescription);
        final EditText cyanosisDescription = (EditText) f.findViewById(R.id.cyanosisDescription);
        final EditText tremorsDescription = (EditText) f.findViewById(R.id.tremorsDescription);
        final EditText icterusDescription = (EditText) f.findViewById(R.id.icterusDescription);
        final EditText clubbingDescription = (EditText) f.findViewById(R.id.clubbingDescription);
        final EditText oedemaDescription = (EditText) f.findViewById(R.id.oedemaDescription);
        final EditText calfTendernessDescription = (EditText) f.findViewById(R.id.calfTendernessDescription);
        final EditText lymphadenopathyDescription = (EditText) f.findViewById(R.id.lymphadenopathyDescription);

        if (strPallorDescription != null) pallorDescription.setText(strPallorDescription);
        if (strCyanosisDescription != null) cyanosisDescription.setText(strCyanosisDescription);
        if (strTremorsDescription != null) tremorsDescription.setText(strTremorsDescription);
        if (strIcterusDescription != null) icterusDescription.setText(strIcterusDescription);
        if (strClubbingDescription != null) clubbingDescription.setText(strClubbingDescription);
        if (strOedemaDescription != null) oedemaDescription.setText(strOedemaDescription);
        if (strCalfTendernessDescription != null)
            calfTendernessDescription.setText(strCalfTendernessDescription);
        if (strLymphadenopathyDescription != null)
            lymphadenopathyDescription.setText(strLymphadenopathyDescription);

        if (strPallore != null && !strPallore.equals(""))
            switch (strPallore) {
                case "Yes":
                    radioPallor.check(R.id.radioPallorYes);
                    break;
                case "No":
                    radioPallor.check(R.id.radioPallorNo);
                    break;

            }
        if (strCyanosis != null && !strCyanosis.equals(""))
            switch (strCyanosis) {
                case "Yes":
                    radioCyanosis.check(R.id.radioCyanosisYes);
                    break;
                case "No":
                    radioCyanosis.check(R.id.radioCyanosisNo);
                    break;
            }
        if (strTremors != null && !strTremors.equals(""))
            switch (strTremors) {
                case "Yes":
                    radioTremors.check(R.id.radioTremorsYes);
                    break;
                case "No":
                    radioTremors.check(R.id.radioTremorsNo);
                    break;
            }
        if (strIcterus != null && !strIcterus.equals(""))
            switch (strIcterus) {
                case "Yes":
                    radioIcterus.check(R.id.radioIcterusYes);
                    break;
                case "No":
                    radioIcterus.check(R.id.radioIcterusNo);
                    break;
            }

        if (strClubbing != null && !strClubbing.equals(""))
            switch (strClubbing) {
                case "Yes":
                    radioClubbing.check(R.id.radioClubbingYes);
                    break;
                case "No":
                    radioClubbing.check(R.id.radioClubbingNo);
                    break;
            }
        if (strOedema != null && !strOedema.equals(""))
            switch (strOedema) {
                case "Yes":
                    radioOedema.check(R.id.radioOedemaYes);
                    break;
                case "No":
                    radioOedema.check(R.id.radioOedemaNo);
                    break;
            }
        if (strCalfTenderness != null && !strCalfTenderness.equals(""))
            switch (strCalfTenderness) {
                case "Yes":
                    radioCalfTenderness.check(R.id.radioCalfTendernessYes);
                    break;
                case "No":
                    radioCalfTenderness.check(R.id.radioCalfTendernessNo);
                    break;
            }
        if (strLymphadenopathy != null && !strLymphadenopathy.equals(""))
            switch (strLymphadenopathy) {
                case "Yes":
                    radioLymphadenopathy.check(R.id.radioLymphadenopathyYes);
                    break;
                case "No":
                    radioLymphadenopathy.check(R.id.radioLymphadenopathyNo);
                    break;
            }

        radioPallor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioPallorYes:
                        strPallore = "Yes";
                        break;

                    case R.id.radioPallorNo:
                        strPallore = "No";
                        break;

                    default:
                        break;
                }
            }
        });

        radioCyanosis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioCyanosisYes:
                        strCyanosis = "Yes";
                        break;

                    case R.id.radioCyanosisNo:
                        strCyanosis = "No";
                        break;

                    default:
                        break;
                }
            }
        });
        radioTremors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioTremorsYes:
                        strTremors = "Yes";
                        break;

                    case R.id.radioTremorsNo:
                        strTremors = "No";
                        break;

                    default:
                        break;
                }
            }
        });
        radioIcterus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioIcterusYes:
                        strIcterus = "Yes";
                        break;

                    case R.id.radioIcterusNo:
                        strIcterus = "No";
                        break;

                    default:
                        break;
                }
            }
        });
        radioClubbing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioClubbingYes:
                        strClubbing = "Yes";
                        break;

                    case R.id.radioClubbingNo:
                        strClubbing = "No";
                        break;

                    default:
                        break;
                }
            }
        });
        radioOedema.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioOedemaYes:
                        strOedema = "Yes";
                        break;

                    case R.id.radioOedemaNo:
                        strOedema = "No";
                        break;

                    default:
                        break;
                }
            }
        });
        radioCalfTenderness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioCalfTendernessYes:
                        strCalfTenderness = "Yes";
                        break;

                    case R.id.radioCalfTendernessNo:
                        strCalfTenderness = "No";
                        break;

                    default:
                        break;
                }
            }
        });
        radioLymphadenopathy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.radioLymphadenopathyYes:
                        strLymphadenopathy = "Yes";
                        break;

                    case R.id.radioLymphadenopathyNo:
                        strLymphadenopathy = "No";
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

                String flag = "0";
                sbObservations.setLength(0);

                strPallorDescription = pallorDescription.getText().toString();
                strCyanosisDescription = cyanosisDescription.getText().toString();
                strTremorsDescription = tremorsDescription.getText().toString();
                strIcterusDescription = icterusDescription.getText().toString();
                strClubbingDescription = clubbingDescription.getText().toString();
                strOedemaDescription = oedemaDescription.getText().toString();
                strCalfTendernessDescription = calfTendernessDescription.getText().toString();
                strLymphadenopathyDescription = lymphadenopathyDescription.getText().toString();

                showObservationsData();


                dbController.addObservations(strPatientId, strVisitId, strPallore, strPallorDescription, strCyanosis, strCyanosisDescription, strTremors, strTremorsDescription, strIcterus, strIcterusDescription
                        , strClubbing, strClubbingDescription, strOedema, strOedemaDescription, strCalfTenderness, strCalfTendernessDescription, strLymphadenopathy, strLymphadenopathyDescription, new AppController().getDateTimeddmmyyyy(), flag);


                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void showObservationsData() {

        if (strPallore != null && !strPallore.equals("") && strPallore.length() > 0) {
            sbObservations.append("Pallor : ").append(strPallore).append(" ;");
        }
        if (strPallorDescription != null && !strPallorDescription.equals("") && strPallorDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Pallor Desc. : ").append(strPallorDescription).append(" ;");
        }
        if (strCyanosis != null && !strCyanosis.equals("") && strCyanosis.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Cyanosis : ").append(strCyanosis).append(" ;");
        }
        if (strCyanosisDescription != null && !strCyanosisDescription.equals("") && strCyanosisDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Cyanosis Desc. : ").append(strCyanosisDescription).append(" ;");
        }
        if (strTremors != null && !strTremors.equals("") && strTremors.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Tremors : ").append(strTremors).append(" ;");
        }
        if (strTremorsDescription != null && !strTremorsDescription.equals("") && strTremorsDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Tremors Desc. : ").append(strTremorsDescription).append(" ;");
        }
        if (strIcterus != null && !strIcterus.equals("") && strIcterus.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Icterus : ").append(strIcterus).append(" ;");
        }
        if (strIcterusDescription != null && !strIcterusDescription.equals("") && strIcterusDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Icterus Desc. : ").append(strIcterusDescription).append(" ;");
        }
        if (strClubbing != null && !strClubbing.equals("") && strClubbing.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Clubbing : ").append(strClubbing).append(" ;");
        }
        if (strClubbingDescription != null && !strClubbingDescription.equals("") && strClubbingDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Clubbing Desc. : ").append(strClubbingDescription).append(" ;");
        }
        if (strOedema != null && !strOedema.equals("") && strOedema.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Oedema : ").append(strOedema).append(" ;");
        }
        if (strOedemaDescription != null && !strOedemaDescription.equals("") && strOedemaDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Oedema Desc. : ").append(strOedemaDescription).append(" ;");
        }
        if (strCalfTenderness != null && !strCalfTenderness.equals("") && strCalfTenderness.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Tenderness : ").append(strCalfTenderness).append(" ;");
        }
        if (strCalfTendernessDescription != null && !strCalfTendernessDescription.equals("") && strCalfTendernessDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Tenderness Desc. : ").append(strCalfTendernessDescription).append(" ;");
        }
        if (strLymphadenopathy != null && !strLymphadenopathy.equals("") && strLymphadenopathy.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Lymphadenopathy : ").append(strLymphadenopathy).append(" ;");
        }
        if (strLymphadenopathyDescription != null && !strLymphadenopathyDescription.equals("") && strLymphadenopathyDescription.length() > 0) {
            sbObservations.append("  ");
            sbObservations.append(" Lymphadenopathy Desc. : ").append(strLymphadenopathyDescription).append(" ;");
        }

        showObservationsData.setText(sbObservations);

    }
}
