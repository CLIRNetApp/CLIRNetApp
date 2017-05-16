package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.EditPatientAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.Validator;

public class EditPatientUpdate extends AppCompatActivity {

    private ImageView backChangingImages;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID = 0;
    private static final int DATE_DIALOG_ID1 = 1;

    private String strPhone;
    private String strAge;
    private String strLanguage;

    private String strgender;
    private String strPatientPhoto;
    private EditText clinicalNotes;
    private String strFirstName;
    private String strMiddleName;
    private String strLastName;
    private String strDob;
    private String strId;
    private String fowSel;
    private String usersellectedDate = null;


    private SQLController sqlController;
    private SQLiteHandler dbController;
    private DatabaseClass databaseClass;

    private ArrayList<String> mAilmemtArrayList;

    private String imageName;
    private Intent imageIntent;
    private File imagesFolder;
    private Uri uriSavedImage;
    private String prescriptionImagePath;
    private SimpleDateFormat sdf1;

    private String updatedTime;
    private String sysdate;
    private String docId;

    private String strVisitId;
    private int maxid;
    private AppController appController;
    private Button editUpdate;
    private Button cancel;
    private Validator validator;
    private BootstrapEditText fodtextshow;
    private Button days;
    private Button week;
    private Button month;
    private BootstrapEditText inputnumber;
    private String value;
    private String buttonSelected;
    private String daysSel;
    private String monthSel;

    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtlowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private MultiAutoCompleteTextView edtSymptoms;
    private MultiAutoCompleteTextView edtDignosis;
    private String strAddress;
    private String strCityorTown;
    private String strDistrict;
    private String strPinNo;
    private String strState;
    private TextView txtRecord;
    private int countvitalsLayout = 1;
    private TextView txtsymtomsanddignost;
    private int countsymtomsanddignostLayout = 1;

    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;

    private String doctor_membership_number;
    private LastnameDatabaseClass lastNamedb;
    private ArrayList<String> mSymptomsList;
    private Button btnclear;
    private EditText edtInput_sugarfasting;
    private EditText edtInput_bmi;
    private EditText edtInput_height;
    private String strAlternatenumber;
    private String strAlternatephtype;
    private String strPhoneTpe;
    private String strIsd_code;
    private String strAlternateIsd_code;
    private EditText visitDate;
    private TextView edtprescriptionImgPath;
    private String strVisitDate;
    private HashMap<String, String> NameData;
    private int addCounter = 0;
    private String strReferedTo;
    private String strReferedBy;
    private ArrayList<String> mDiagnosisList;
    private String struid;
    private String strEmail;
    private ArrayList<HashMap<String, String>> list;
    private TextView textRefredByShow;
    private TextView textRefredToShow;
    private String strReferredByName;
    private String strReferredTo1Name;
    private String strReferredTo2Name;
    private String strReferredTo3Name;
    private String strReferredTo4Name;
    private String strReferredTo5Name;
    private ArrayList<String> nameReferalsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appController = new AppController();
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        if (databaseClass == null && dbController == null) {
            databaseClass = new DatabaseClass(getApplicationContext());
            dbController = SQLiteHandler.getInstance(getApplicationContext());
        }
        if (validator == null) {
            validator = new Validator(getApplicationContext());
        }
        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");
        String strName = getIntent().getStringExtra("NAME");
        strId = getIntent().getStringExtra("ID");

        strFirstName = getIntent().getStringExtra("FIRSTTNAME");
        strMiddleName = getIntent().getStringExtra("MIDDLENAME");
        strLastName = getIntent().getStringExtra("LASTNAME");
        strPhone = getIntent().getStringExtra("PHONE");

        strAge = getIntent().getStringExtra("AGE");
        strDob = getIntent().getStringExtra("DOB");

        strLanguage = getIntent().getStringExtra("LANGUAGE");
        strgender = getIntent().getStringExtra("GENDER");
        struid = getIntent().getStringExtra("UID");
        strEmail = getIntent().getStringExtra("EMAIL");
        String strActualFollowUpDate = getIntent().getStringExtra("ACTUALFOD");

        String strFollowupDays = getIntent().getStringExtra("FOLLOWDAYS");
        String strFollowupWeeks = getIntent().getStringExtra("FOLLOWWEEKS");

        String strFollowupMonth = getIntent().getStringExtra("FOLLOWMONTH");

        String strClinicalNotes = getIntent().getStringExtra("CLINICALNOTES");
        prescriptionImagePath = getIntent().getStringExtra("PRESCRIPTION");
        strVisitId = getIntent().getStringExtra("VISITID");

        strVisitDate = getIntent().getStringExtra("VISITDATE");

        strAddress = getIntent().getStringExtra("ADDRESS");
        strCityorTown = getIntent().getStringExtra("CITYORTOWN");
        strDistrict = getIntent().getStringExtra("DISTRICT");
        strPinNo = getIntent().getStringExtra("PIN");
        strState = getIntent().getStringExtra("STATE");
        strAlternatenumber = getIntent().getStringExtra("ALTERNATENUMBER");

        strAlternatephtype = getIntent().getStringExtra("ALTERNATENUMBERTYPE");
        strPhoneTpe = getIntent().getStringExtra("PHONETYPE");

        strIsd_code = getIntent().getStringExtra("ISDCODE");
        strAlternateIsd_code = getIntent().getStringExtra("ALTERNATEISDCODE");

        String strWeight = getIntent().getStringExtra("WEIGHT");
        String strPulse = getIntent().getStringExtra("PULSE");
        String strBp = getIntent().getStringExtra("BP");
        String strMmhg = getIntent().getStringExtra("LOWBP");
        String strTemprature = getIntent().getStringExtra("TEMPRATURE");
        String strSugar = getIntent().getStringExtra("SUGAR");
        String strSymptoms = getIntent().getStringExtra("SYMPTOMS");
        String strDignosis = getIntent().getStringExtra("DIGNOSIS");

        String strbmi = getIntent().getStringExtra("BMI");

        String height = getIntent().getStringExtra("HEIGHT");
        String sugarfasting = getIntent().getStringExtra("SUGARFASTING");

        strReferedBy = getIntent().getStringExtra("REFEREDBY");
        strReferedTo = getIntent().getStringExtra("REFEREDTO");

        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
        TextView date = (TextView) findViewById(R.id.sysdate);
        TextView editpatientName = (TextView) findViewById(R.id.patientName);
        TextView editage = (TextView) findViewById(R.id.age1);
        TextView editmobileno = (TextView) findViewById(R.id.mobileno);
        TextView editgender = (TextView) findViewById(R.id.gender);
        TextView editlang = (TextView) findViewById(R.id.lang);
        Button addPatientprescriptionBtn = (Button) findViewById(R.id.addPatientprescriptionBtn);
        fodtextshow = (BootstrapEditText) findViewById(R.id.fodtextshow);
        inputnumber = (BootstrapEditText) findViewById(R.id.inputnumber);
        days = (Button) findViewById(R.id.days);
        week = (Button) findViewById(R.id.week);
        month = (Button) findViewById(R.id.month);
        btnclear = (Button) findViewById(R.id.btnclear);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        edtInput_weight = (EditText) findViewById(R.id.input_weight);
        edtInput_height = (EditText) findViewById(R.id.input_height);
        edtInput_pulse = (EditText) findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) findViewById(R.id.input_bp);
        edtlowBp = (EditText) findViewById(R.id.lowBp);
        edtInput_temp = (EditText) findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) findViewById(R.id.input_sugar);

        edtSymptoms = (MultiAutoCompleteTextView) findViewById(R.id.symptoms);
        edtDignosis = (MultiAutoCompleteTextView) findViewById(R.id.dignosis);

        edtInput_sugarfasting = (EditText) findViewById(R.id.input_sugarfasting);
        edtInput_bmi = (EditText) findViewById(R.id.input_bmi);

        // ailments1 = (MultiAutoCompleteTextView) findViewById(ailments1);

        clinicalNotes = (EditText) findViewById(R.id.clinicalNotes);
        ImageView imgEdit = (ImageView) findViewById(R.id.editPersonalInfo);
        cancel = (Button) findViewById(R.id.cancel);
        editUpdate = (Button) findViewById(R.id.editUpdate);

        edtprescriptionImgPath = (TextView) findViewById(R.id.prescriptionImgPath);
        visitDate = (EditText) findViewById(R.id.visitDate);
        textRefredByShow = (TextView) findViewById(R.id.txtrefredby);
        textRefredToShow = (TextView) findViewById(R.id.txtrefredto);

        addFollowupdateButtonListner();
        addArrowUpDownListner();


        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);
        //open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);
                finish();

            }
        });
        Glide.get(EditPatientUpdate.this).clearMemory();
        //open Terms  and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsCondition.class);
                startActivity(intent);
                finish();

            }
        });
        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        sysdate = String.valueOf(new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append(""));
        visitDate.setText(strVisitDate);

        SimpleDateFormat sdf0 = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        String justDate = sdf0.format(todayDate);

        date.setText("Today's Date: " + dd);

        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date todayDate3 = new Date();

        //this date is ued to set update records date in patient history table
        updatedTime = sdf3.format(todayDate3);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(strFollowupDays, "0")) {

                strFollowupDays = "";
            }
        }

        if (Objects.equals(strFollowupWeeks, "0")) {

            strFollowupWeeks = "";
        }

        if (Objects.equals(strFollowupMonth, "0")) {

            strFollowupMonth = "";
        }


        editpatientName.setText(strName);
        editmobileno.setText(strPhone);
        editage.setText(strAge);
        editlang.setText(strLanguage);
        editgender.setText(strgender);
        clinicalNotes.setText(strClinicalNotes);
        edtInput_weight.setText(strWeight);
        edtInput_pulse.setText(strPulse);
        edtInput_bp.setText(strBp);
        edtlowBp.setText(strMmhg);
        edtInput_temp.setText(strTemprature);
        edtInput_sugar.setText(strSugar);
        edtSymptoms.setText(strSymptoms);
        edtDignosis.setText(strDignosis);

        if (prescriptionImagePath != null && prescriptionImagePath.length() > 0) {
            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(prescriptionImagePath);
        }

        edtInput_bmi.setText(strbmi);
        edtInput_height.setText(height);
        edtInput_sugarfasting.setText(sugarfasting);

        if (strActualFollowUpDate == null || strActualFollowUpDate.equals("0000-00-00") || strActualFollowUpDate.equals("30-11-0002") || strActualFollowUpDate.equals("")) {
            fodtextshow.setText("");//add selected date to date text view
            CleanFollowup();
        } else {
            fodtextshow.setText(strActualFollowUpDate);//add selected date to date text view
        }

        if (strFollowupDays != null && !strFollowupDays.equals("") && !strFollowupDays.equals("0")) {
            inputnumber.setText(strFollowupDays);
            days.setSelected(true);
            days.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            days.setTextColor(getResources().getColor(R.color.white));

            daysSel = strFollowupDays;

        } else if (strFollowupWeeks != null && !strFollowupWeeks.equals("") && !strFollowupWeeks.equals("0")) {
            inputnumber.setText(strFollowupWeeks);
            week.setSelected(true);
            week.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            week.setTextColor(getResources().getColor(R.color.white));
            fowSel = strFollowupWeeks;
        } else if (strFollowupMonth != null && !strFollowupMonth.equals("") && !strFollowupMonth.equals("0")) {
            //  Log.e("strFollowupMonth ","   "+strFollowupMonth);
            inputnumber.setText(strFollowupMonth);
            month.setSelected(true);
            month.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            month.setTextColor(getResources().getColor(R.color.white));

            monthSel = strFollowupMonth;
        }

        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            docId = sqlController.getDoctorId();

            databaseClass.openDataBase();

            if (lastNamedb == null) {
                lastNamedb = new LastnameDatabaseClass(getApplicationContext());
            }

            ArrayList<RegistrationModel> patientData = sqlController.getPatientHistoryListAll(strId);

            List<RegistrationModel> filteredpatientData = filterBySystemDate(patientData, justDate);

            int size = filteredpatientData.size();

            if (size > 0) {
                //Removed 1st element from list bcs it is allreday showing in upper section
                filteredpatientData.remove(0);

                EditPatientAdapter editPatientAdapter = new EditPatientAdapter(EditPatientUpdate.this, filteredpatientData);
                recyclerView.setAdapter(editPatientAdapter);
                if (size > 1) {
                    TextView txtupdatehistory = (TextView) findViewById(R.id.txtupdatehistory);
                    txtupdatehistory.setVisibility(View.VISIBLE);
                }

            } else {
                recyclerView.setVisibility(View.GONE);
            }
            int id = databaseClass.getMaxAimId();
            maxid = id + 1;

            if (bannerClass == null) {
                bannerClass = new BannerClass(getApplicationContext());
            }
            bannerimgNames = bannerClass.getImageName();

            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

            list = sqlController.getAllDataAssociateMaster();

            if (strReferedBy != null && !strReferedBy.equals("")) {
                String referedBy = sqlController.getNameByIdAssociateMaster(strReferedBy);
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
                    String strConvertedReferredTo = String.valueOf(sbname);
                    strConvertedReferredTo = appController.removeCommaOccurance(strConvertedReferredTo);

                    textRefredToShow.setText(strConvertedReferredTo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }


        try {
            mSymptomsList = lastNamedb.getSymptoms();
            if (mSymptomsList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(EditPatientUpdate.this,
                        android.R.layout.simple_dropdown_item_1line, mSymptomsList);

                edtSymptoms.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtSymptoms.setThreshold(1);
                edtSymptoms.setAdapter(lastnamespin);
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        try {
            mDiagnosisList = lastNamedb.getDiagnosis();
            if (mDiagnosisList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(EditPatientUpdate.this,
                        android.R.layout.simple_dropdown_item_1line, mDiagnosisList);

                edtDignosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtDignosis.setThreshold(1);
                edtDignosis.setAdapter(lastnamespin);
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        //this code is for setting list to auto complete text view  8/6/16
        ArrayAdapter<String> adp = new ArrayAdapter<>(EditPatientUpdate.this,
                android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(EditPatientUpdate.this).clearDiskCache();
            }
        }).start();

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {
            if (strPatientPhoto.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);
                Glide.with(EditPatientUpdate.this)
                        .load(strPatientPhoto)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        .error(R.drawable.main_profile)
                        .into(patientImage);
            }
        }

        //this will redirect to EditPersonalInfo class to edit personal info
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), EditPersonalInfo.class);

                i.putExtra("PATIENTPHOTO", strPatientPhoto);
                i.putExtra("ID", strId);
                i.putExtra("FIRSTNAME", strFirstName);
                i.putExtra("MIDDLEAME", strMiddleName);
                i.putExtra("LASTNAME", strLastName);
                i.putExtra("PHONE", strPhone);
                i.putExtra("PHONETYPE", strPhoneTpe);
                i.putExtra("DOB", strDob);
                i.putExtra("AGE", strAge);
                i.putExtra("LANGUAGE", strLanguage);
                i.putExtra("GENDER", strgender);
                i.putExtra("ADDRESS", strAddress);
                i.putExtra("CITYORTOWN", strCityorTown);
                i.putExtra("DISTRICT", strDistrict);
                i.putExtra("PIN", strPinNo);
                i.putExtra("STATE", strState);
                i.putExtra("ALTERNATENUMBER", strAlternatenumber);
                i.putExtra("ALTERNATENUMBERTYPE", strAlternatephtype);
                i.putExtra("ISDCODE", strIsd_code);
                i.putExtra("ALTERNATEISDCODE", strAlternateIsd_code);
                i.putExtra("UID", struid);
                i.putExtra("EMAIL", strEmail);
                i.putExtra("FROMWHERE", "editpatient");
                startActivity(i);

            }
        });

        cancel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    cancel.setBackgroundColor(getResources().getColor(R.color.cancelbtn));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    cancel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToNavigation();
            }
        });
//update the data to db

        editUpdate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    editUpdate.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));
                    saveData();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    editUpdate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                //redirect to navigation activity
                goToNavigation1();

            }
        });


//capture image

        addPatientprescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                imageName = "prescription_" + strFirstName + "_" + strLastName + docId + "_" + appController.getDateTime() + ".png";

                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });


        Button refered = (Button) findViewById(R.id.referedby);
        refered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReferedDialogBox();
            }
        });

        setupAnimation();
    }

    private void showReferedDialogBox() {

        final Dialog dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan));

        LayoutInflater factory = LayoutInflater.from(EditPatientUpdate.this);
        final View f = factory.inflate(R.layout.refered_by_dialog, null);

        dialog.setTitle("Referred By-To");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);
        Button dialogButtonCancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) f.findViewById(R.id.customDialogOk);
        final Button addMore = (Button) f.findViewById(R.id.addMore);

        final Spinner nameRefredBySpinner = (Spinner) f.findViewById(R.id.nameRefredBySpinner);
        final Spinner nameRefredTo1Spinner = (Spinner) f.findViewById(R.id.nameRefredTo1Spinner);
        final Spinner nameRefredTo2Spinner = (Spinner) f.findViewById(R.id.nameRefredTo2Spinner);
        final Spinner nameRefredTo3Spinner = (Spinner) f.findViewById(R.id.nameRefredTo3Spinner);
        final Spinner nameRefredTo4Spinner = (Spinner) f.findViewById(R.id.nameRefredTo4Spinner);
        final Spinner nameRefredTo5Spinner = (Spinner) f.findViewById(R.id.nameRefredTo5Spinner);

        try {
            NameData = new HashMap<>();
            ArrayList<String> NameList = new ArrayList<>();

            for (int im = 0; im < list.size(); im++) {
                String strid = list.get(im).get("ID");
                String strName = list.get(im).get("NAME");
                //String str = list.get(im).get("SPECIALITY");
                NameData.put(strName, strid);
            }
            nameReferalsList = dbController.getReferalsnew();
            setSpinnerValue(f);
            nameReferalsList.add(0, "Select Referrals");

            if (strReferedBy != null && !strReferedBy.equals("")) {

                String referedBy = sqlController.getNameByIdAssociateMaster(strReferedBy);
                String title = sqlController.getTitleByNameAssociateMaster(referedBy);
                String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                appController.setSpinnerPosition(nameRefredBySpinner, some_array, title + " " +referedBy);

                textRefredByShow.setText(referedBy);
                // testArrayList("Method1:ArrayListOfHashMaps", Integer.parseInt(strReferedBy),list);
            }
            if (strReferedTo != null && !strReferedTo.equals("")) {

                if (strReferedTo.length() > 1) {
                    String delimiter = ",";

                    String[] temp = strReferedTo.split(delimiter);

                    for (String aTemp : temp) {
                        String referedBy = sqlController.getNameByIdAssociateMaster(aTemp);
                        NameList.add(referedBy);
                    }
                }
                int size = NameList.size();
                if (size > 0) {

                    String idNo = NameList.get(0);
                    String title = sqlController.getTitleByNameAssociateMaster(idNo);
                    String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                    appController.setSpinnerPosition(nameRefredTo1Spinner, some_array, title + " " + idNo);

                    if (size > 1) {
                        String idNo1 = NameList.get(1);
                        String title1 = sqlController.getTitleByNameAssociateMaster(idNo1);
                        String[] some_array1 = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                        appController.setSpinnerPosition(nameRefredTo2Spinner, some_array1, title1 + " " + idNo1);
                    }

                    if (size > 2) {
                        String idNo2 = NameList.get(2);
                        String title1 = sqlController.getTitleByNameAssociateMaster(idNo2);
                        String[] some_array2 = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                        appController.setSpinnerPosition(nameRefredTo3Spinner, some_array2, title1 + " " +idNo2);
                    }
                    if (size > 3) {
                        String idNo3 = NameList.get(3);
                        String title1 = sqlController.getTitleByNameAssociateMaster(idNo3);
                        String[] some_array2 = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                        appController.setSpinnerPosition(nameRefredTo4Spinner, some_array2, title1 + " " + idNo3);
                    }

                    if (size > 4) {
                        String idNo4 = NameList.get(4);
                        String title1 = sqlController.getTitleByNameAssociateMaster(idNo4);
                        String[] some_array2 = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                        appController.setSpinnerPosition(nameRefredTo5Spinner, some_array2,  title1 + " " +idNo4);
                    }

                } else {
                    String referedTo = sqlController.getNameByIdAssociateMaster(strReferedTo);
                    String title1 = sqlController.getTitleByNameAssociateMaster(referedTo);
                    String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                    appController.setSpinnerPosition(nameRefredTo1Spinner, some_array, title1 + " " +referedTo);
                }
            }

        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "EditPatientUpdate " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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

                    if (strReferredTo1Name!=null && !strReferredTo1Name.equals("") && strReferredTo1Name.length() > 0) {
                        code = NameData.get(strReferredTo1Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(index);
                            sbname.append(strReferredTo1Name);
                        }
                    }
                }
                if (addCounter >= 1) {

                    if (strReferredTo2Name!=null && !strReferredTo2Name.equals("") && strReferredTo2Name.length() > 0) {
                        code = NameData.get(strReferredTo2Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo2Name);
                        }
                    }
                }
                if (addCounter >= 2) {

                    if (strReferredTo3Name!=null && !strReferredTo3Name.equals("") && strReferredTo3Name.length() > 0) {
                        code = NameData.get(strReferredTo3Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo3Name);
                        }
                    }
                }
                if (addCounter >= 3) {

                    if (strReferredTo4Name!=null && !strReferredTo4Name.equals("") && strReferredTo4Name.length() > 0) {
                        code = NameData.get(strReferredTo4Name.trim());
                        if (code != null) {

                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo4Name);
                        }
                    }
                }
                if (addCounter >= 4) {

                    if (strReferredTo5Name!=null && !strReferredTo5Name.equals("") && strReferredTo5Name.length() > 0) {
                        code = NameData.get(strReferredTo5Name.trim());
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo5Name);
                        }
                    }
                }

                strReferedTo = String.valueOf(sb);

                if (strReferredByName!=null && !strReferredByName.equals("") && strReferredByName.length() > 0) {
                    code = NameData.get(strReferredByName.trim());
                    if (code != null) {
                        int index = Integer.parseInt(code);
                        strReferedBy = String.valueOf(index);
                        textRefredByShow.setText("");
                        textRefredByShow.setText(strReferredByName);
                    } else {
                        textRefredByShow.setText("");
                    }
                }
                strReferedTo = String.valueOf(sb);
                String insertedName = String.valueOf(sbname);
                insertedName = appController.removeCommaOccurance(insertedName);
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
                    Toast.makeText(getApplicationContext(), "Limit Exceed! You can not add more", Toast.LENGTH_LONG).show();
                }

            }
        });


        dialog.show();
    }

    private void setSpinnerValue(View f) {

        final TextView refredtoSpeciality1 = (TextView) f.findViewById(R.id.refredtoSpeciality1);
        final TextView refredtoSpeciality2 = (TextView) f.findViewById(R.id.refredtoSpeciality2);
        final TextView refredtoSpeciality3 = (TextView) f.findViewById(R.id.refredtoSpeciality3);
        final TextView refredtoSpeciality4 = (TextView) f.findViewById(R.id.refredtoSpeciality4);
        final TextView refredtoSpeciality5 = (TextView) f.findViewById(R.id.refredtoSpeciality5);
        final TextView refredBySpeciality = (TextView) f.findViewById(R.id.refredBySpeciality);

        final Spinner nameRefredBySpinner = (Spinner) f.findViewById(R.id.nameRefredBySpinner);
        final Spinner nameRefredTo1Spinner = (Spinner) f.findViewById(R.id.nameRefredTo1Spinner);
        final Spinner nameRefredTo2Spinner = (Spinner) f.findViewById(R.id.nameRefredTo2Spinner);
        final Spinner nameRefredTo3Spinner = (Spinner) f.findViewById(R.id.nameRefredTo3Spinner);
        final Spinner nameRefredTo4Spinner = (Spinner) f.findViewById(R.id.nameRefredTo4Spinner);
        final Spinner nameRefredTo5Spinner = (Spinner) f.findViewById(R.id.nameRefredTo5Spinner);

        try {

            if (nameReferalsList.size() > 0) {
                ArrayAdapter<String> referralName = new ArrayAdapter<>(EditPatientUpdate.this,
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
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " Edit PatientUpdate " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        nameRefredBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredByName = (String) parent.getItemAtPosition(position);

                try {
                    if (nameRefredBySpinner.getSelectedItem() != "Select Referrals") {
                        if (appController.contains(strReferredByName, ".")) {

                            String[] parts = strReferredByName.split(". ", 2);
                             //String string1 = parts[0];//namealias
                            strReferredByName = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredByName.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredBySpeciality.setText(strSpeclty);
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

        nameRefredTo1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo1Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo1Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo1Name, ".")) {

                            String[] parts = strReferredTo1Name.split(". ", 2);
                           // String string1 = parts[0];//namealias
                            strReferredTo1Name = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo1Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality1.setText(strSpeclty);
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

        nameRefredTo2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo2Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo2Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo2Name, ".")) {

                            String[] parts = strReferredTo2Name.split(". ", 2);
                           // String string1 = parts[0];//namealias
                            strReferredTo2Name = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo2Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality2.setText(strSpeclty);
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

        nameRefredTo3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo3Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo3Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo3Name, ".")) {

                            String[] parts = strReferredTo3Name.split(". ", 2);
                           // String string1 = parts[0];//namealias
                            strReferredTo3Name = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo3Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality3.setText(strSpeclty);
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

        nameRefredTo4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo4Name = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredTo4Spinner.getSelectedItem() != "Select Referrals") {

                        if (appController.contains(strReferredTo4Name, ".")) {

                            String[] parts = strReferredTo4Name.split(". ", 2);
                            String string1 = parts[0];//namealias
                            strReferredTo4Name = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo4Name.trim());

                       if(list.size()>0) {
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

                        if (appController.contains(strReferredTo5Name, ".")) {

                            String[] parts = strReferredTo5Name.split(". ", 2);
                            String string1 = parts[0];//namealias
                            strReferredTo5Name = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo5Name.trim());

                        if(list.size()>0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            refredtoSpeciality5.setText(strSpeclty);
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
    }

    private void addArrowUpDownListner() {

        txtRecord = (TextView) findViewById(R.id.txtRecord);
        txtsymtomsanddignost = (TextView) findViewById(R.id.txtsymptomsanddignost);
        txtRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout vitalsLayout = (LinearLayout) findViewById(R.id.vitalsLayout);
                if (countvitalsLayout == 1) {
                    vitalsLayout.setVisibility(View.GONE);
                    txtRecord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0);//set drawable right to text view
                    countvitalsLayout = 2;
                } else {
                    vitalsLayout.setVisibility(View.VISIBLE);
                    txtRecord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countvitalsLayout = 1;
                }
            }
        });
        txtsymtomsanddignost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout symptomsdigosislayout = (LinearLayout) findViewById(R.id.symptomsdigosislayout);
                if (countsymtomsanddignostLayout == 1) {
                    symptomsdigosislayout.setVisibility(View.GONE);
                    txtsymtomsanddignost.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countsymtomsanddignostLayout = 2;
                } else {
                    symptomsdigosislayout.setVisibility(View.VISIBLE);
                    txtsymtomsanddignost.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countsymtomsanddignostLayout = 1;
                }

            }
        });

    }


    private void addFollowupdateButtonListner() {
        days.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    days.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    week.setTextColor(getResources().getColor(R.color.black));
                    month.setTextColor(getResources().getColor(R.color.black));
                    btnclear.setTextColor(getResources().getColor(R.color.black));
                    days.setTextColor(getResources().getColor(R.color.white));
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
                    buttonSelected = "days";
                    String dateis = sdf1.format(AppController.addDay1(new Date(), val));
                    fodtextshow.setText(dateis);
                    daysSel = value;
                    monthSel = null;
                    fowSel = null;
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
                buttonSelected = "week";
                String dateis = sdf1.format(AppController.addDay1(new Date(), fVal));
                usersellectedDate = dateis;
                fowSel = value;
                daysSel = null;
                monthSel = null;
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
                buttonSelected = "month";
                String dateis = sdf1.format(AppController.addMonth(new Date(), Integer.parseInt(value)));
                usersellectedDate = dateis;
                monthSel = value;
                daysSel = null;
                fowSel = null;
                fodtextshow.setText(dateis);
            }
        });

        fodtextshow.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                shpwDialog(DATE_DIALOG_ID);

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
            }
        });

        visitDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog(DATE_DIALOG_ID1);

            }
        });

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

    //saved the user enetred data to db
    private void saveData() {

        String strfollow_up_date = null;
        String ailments = null;
        String clinical_note = clinicalNotes.getText().toString().trim();
        // strfollow_up_date = follow_up_date.getText().toString().trim();
        usersellectedDate = fodtextshow.getText().toString();


        String strWeight = edtInput_weight.getText().toString().trim();
        String strPulse = edtInput_pulse.getText().toString().trim();
        String strBp = edtInput_bp.getText().toString().trim();
        String strLowBp = edtlowBp.getText().toString().trim();
        String strTemp = edtInput_temp.getText().toString().trim();
        String strSugar = edtInput_sugar.getText().toString().trim();
        String strSymptoms = edtSymptoms.getText().toString().trim();
        String strDignosis = edtDignosis.getText().toString().trim();
        String strTests = null;
        String strDrugs = null;

        String strHeight = edtInput_height.getText().toString().trim();
        String strbmi = edtInput_bmi.getText().toString().trim();
        String strSugarFasting = edtInput_sugarfasting.getText().toString().trim();

        if (TextUtils.isEmpty(strSymptoms) && TextUtils.isEmpty(strDignosis)) {
            Toast.makeText(getApplicationContext(), "Please enter any of Ailemnts,Symptoms or Diagnosis ", Toast.LENGTH_LONG).show();
            // ailment.setError("Please enter Ailment");
            return;
        }


        if (strTemp.length() > 0) {
            int intTemp = Integer.parseInt(strTemp);
            if (intTemp > 110) {
                edtInput_temp.setError("Temp can not be more than 110 ");
                return;
            } else {
                edtInput_temp.setError(null);
            }
        }
        //remove comma occurance from string


        //Remove spaces between text if more than 2 white spaces found 12-12-2016

        String delimiter = ",";

        String[] diagno = strDignosis.split(delimiter);
             /* print substrings */
        for (String aTemp : diagno) {
            //System.out.println(temp[i]);

            if (!new AppController().isDuplicate(mDiagnosisList, aTemp)) {

                // dbController.addAilment(temp[i]);
                databaseClass.addDiagnosis(aTemp);

            }
        }

        String[] symp = strSymptoms.split(delimiter);
             /* print substrings */
        for (String aTemp : symp) {
            //System.out.println(temp[i]);

            if (!new AppController().isDuplicate(mSymptomsList, aTemp)) {

                // dbController.addAilment(temp[i]);
                databaseClass.addSymptoms(aTemp);

            }
        }
        String modified_on = sysdate;
        String visit_date = visitDate.getText().toString();
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            //convert visit date from 2016-11-1 to 2016-11-01
            visit_date = myFormat.format(fromUser.parse(visit_date));
            modified_on = myFormat.format(fromUser.parse(sysdate));
            //Log.e("visit_date", "" + visit_date + "  " + modified_on);
            usersellectedDate = myFormat.format(fromUser.parse(usersellectedDate));

        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        String modified_by = docId;//INSERTING DOC ID IN ADDED BY COLUMN AS PER PUSHPAL SAID
        String flag = "0";

        String patientInfoType = "App";
        String action = "updated";
        String status = null;
        try {
            dbController.updatePatientOtherInfo(strId, strVisitId, usersellectedDate, strfollow_up_date, daysSel, fowSel, monthSel, clinical_note, prescriptionImagePath, ailments, modified_on, updatedTime, modified_by, action, patientInfoType, flag,
                    strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs, strHeight, strbmi, strSugarFasting, status, visit_date, strReferedBy, strReferedTo);
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        Toast.makeText(getApplicationContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
        //redirect to navigation activity

        goToNavigation();

    }
//this will used to change banner image after some time interval

    private void setupAnimation() {

        try {

            appController.setUpAdd(EditPatientUpdate.this, bannerimgNames, backChangingImages, doctor_membership_number, "Edit Patient Update");

        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + "" + "/" + "Edit Patient Update " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image display it in image view
                    previewCapturedImage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    //this will show captured image to image view
    private void previewCapturedImage() {
        try {

            prescriptionImagePath = uriSavedImage.getPath();

            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(prescriptionImagePath);

        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    //show date sellector dialog box
    private void shpwDialog(int id) {

        final Calendar c1 = Calendar.getInstance();
        int mYear1 = c1.get(Calendar.YEAR);
        int mMonth1 = c1.get(Calendar.MONTH);
        int mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        switch (id) {

            case DATE_DIALOG_ID:

                DatePickerDialog dpd1 = new DatePickerDialog(EditPatientUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                fodtextshow.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                fodtextshow.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                CleanFollowup();

                                // we calculate age from dob and set to text view.
                            }

                        }, mYear1, mMonth1, mDay1);
                c1.add(Calendar.DATE, 1);
                Date newDate = c1.getTime();
                dpd1.getDatePicker().setMinDate(newDate.getTime());
                dpd1.show();
                //show age of pateint
                break;

            case DATE_DIALOG_ID1: //for visit date

                DatePickerDialog dpd2 = new DatePickerDialog(EditPatientUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                visitDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear1, mMonth1, mDay1);
                c1.add(Calendar.DATE, 0);

                Date newDate2 = c1.getTime();
                dpd2.getDatePicker().setMaxDate(newDate2.getTime());

                dpd2.show();

                break;
        }
    }

    //This method will filter data from our database generated list according to user query By Sys Date 6/8/i Ashish
    //Removes current date data from list and show other data, we dnt want to show curent date data to list.
    private List<RegistrationModel> filterBySystemDate(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {


            final String phVisitDate = model.getVisit_date().toLowerCase();

            if (!phVisitDate.contains(query)) {

                filteredModelList.add(model);

            }
        }
        return filteredModelList;
    }

    private void goToNavigation1() {
        this.onBackPressed();
        finish();

    }

    private void goToNavigation() {
        Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (backChangingImages != null) {
            backChangingImages.setImageDrawable(null);
        }
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
    protected void onRestart() {
        super.onRestart();
        setupAnimation();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (sqlController != null) {
            sqlController = null;
        }
        if (dbController != null) {
            dbController = null;
        }
        if (databaseClass != null) {
            databaseClass = null;
        }

        if (appController != null) {
            appController = null;
        }
        if (mAilmemtArrayList != null) {
            mAilmemtArrayList = null;
        }
        if (bannerClass != null) {
            bannerClass = null;
        }
        if (mSymptomsList != null) {
            mSymptomsList = null;
        }

        lastNamedb = null;

        doctor_membership_number = null;
        bannerimgNames = null;

        sdf1 = null;

        cleanResources();
        System.gc();

    }

    private void cleanResources() {
        strPhone = null;
        strAge = null;
        strLanguage = null;
        strgender = null;
        strPatientPhoto = null;
        clinicalNotes = null;
        strFirstName = null;
        strMiddleName = null;
        strLastName = null;
        strDob = null;
        strId = null;
        usersellectedDate = null;
        monthSel = null;
        imageName = null;
        imageIntent = null;
        imagesFolder = null;
        uriSavedImage = null;
        prescriptionImagePath = null;
        sdf1 = null;
        updatedTime = null;
        sysdate = null;
        docId = null;
        strVisitId = null;
        strAddress = null;
        strCityorTown = null;
        strDistrict = null;
        strPinNo = null;
        strState = null;
        edtInput_weight = null;
        edtInput_pulse = null;
        edtInput_bp = null;
        edtlowBp = null;
        edtInput_temp = null;
        edtInput_sugar = null;
        edtSymptoms = null;
        edtDignosis = null;
        txtRecord = null;
        txtsymtomsanddignost = null;
        edtInput_height = null;
        edtInput_sugarfasting = null;
        edtInput_bmi = null;
        btnclear = null;
        visitDate = null;
        strVisitDate = null;
        edtprescriptionImgPath = null;
        strAlternatenumber = null;
        strAlternatephtype = null;
        strPhoneTpe = null;
        strIsd_code = null;
        strAlternateIsd_code = null;
        mDiagnosisList = null;
        struid = null;
        textRefredByShow = null;
        textRefredToShow = null;
        list = null;
        strEmail = null;
        strReferredByName = null;
        strReferredTo1Name = null;
        strReferredTo2Name = null;
        strReferredTo3Name = null;
        strReferredTo4Name = null;
        strReferredTo5Name = null;
        nameReferalsList = null;
    }

}
