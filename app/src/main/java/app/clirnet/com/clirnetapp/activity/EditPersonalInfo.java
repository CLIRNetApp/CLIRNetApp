package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.utility.ImageCompression;

public class EditPersonalInfo extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID = 0;

    private String strPatientPhoto;
    private String strLanguage;
    private EditText editfirstname;
    private EditText editmiddlename;
    private AutoCompleteTextView editlasttname;
    private EditText editdob;
    private EditText editage;
    private EditText editmobile_no;
    private String selectedLanguage;
    private String strId;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private ImageView patientImage;
    private Intent imageIntent;
    private File imagesFolder;
    private String imageName;
    private String first_name;
    private String last_name;
    private Uri uriSavedImage;
    private String patientImagePath;
    private Bitmap bitmap;
    private String strdateob;
    private int pateintAge;
    private RadioGroup radioSexGroup;
    private String sex;
    private int position;

    private String modified_on_date;
    private ArrayList<String> mLastNameList;
    private String docId;

    private String modifiedTime;
    private ImageView backChangingImages;
    private LastnameDatabaseClass lastNamedb;
    private Spinner phType;
    private AppController appController;
    private Button save;

    private String selectedPhoneType;
    private String selectedState;
    private EditText edtAddress;
    private EditText edtCity;
    private EditText edtDistrict;
    private EditText edtPin;
    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private String doctor_membership_number;
    private String selectedPhoneTypealternate_no;
    private EditText alternatemobile_no;
    private Spinner stateSpinner;
    private Spinner phoneTypeSpinner2;
    private String selectedUidType;
    private EditText uid;
    private String selectedIsd_codeType;
    private String selectedAlternateNoIsd_codeType;
    private Spinner isd_code;
    private int position1;
    private EditText edtEmail_id;
    private File imagePathFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appController = new AppController();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        getSupportActionBar().setTitle(Html.fromHtml("<font color='white'>Edit Personal Information</font>"));

        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");

        strId = getIntent().getStringExtra("ID");

        String strFirstName = getIntent().getStringExtra("FIRSTNAME");
        String strMiddleName = getIntent().getStringExtra("MIDDLEAME");
        String strLastName = getIntent().getStringExtra("LASTNAME");
        String strPhone = getIntent().getStringExtra("PHONE");
        String strPhoneType = getIntent().getStringExtra("PHONETYPE");
        String strAge = getIntent().getStringExtra("AGE");
        String strDob = getIntent().getStringExtra("DOB");

        strLanguage = getIntent().getStringExtra("LANGUAGE");
        String strgender = getIntent().getStringExtra("GENDER");

        //String strVisitId = getIntent().getStringExtra("VISITID");
        String strAddress = getIntent().getStringExtra("ADDRESS");

        String strCityorTown = getIntent().getStringExtra("CITYORTOWN");
        String strDistrict = getIntent().getStringExtra("DISTRICT");
        String strPinNo = getIntent().getStringExtra("PIN");
        String strState = getIntent().getStringExtra("STATE");

        String strUid = getIntent().getStringExtra("UID");
        String strEmail = getIntent().getStringExtra("EMAIL");


        String strAlternatenumber = getIntent().getStringExtra("ALTERNATENUMBER");

        String strAlternatephtype = getIntent().getStringExtra("ALTERNATENUMBERTYPE");

        patientImage = (ImageView) findViewById(R.id.patientimage);
        editfirstname = (EditText) findViewById(R.id.firstname);
        editmiddlename = (EditText) findViewById(R.id.middlename);
        editlasttname = (AutoCompleteTextView) findViewById(R.id.lastname);
        Button addPatientImgBtn = (Button) findViewById(R.id.addPatientImgBtn);
        editdob = (EditText) findViewById(R.id.dob);
        editage = (EditText) findViewById(R.id.age);
        editmobile_no = (EditText) findViewById(R.id.mobile_no);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGender);
        RadioGroup radioLanguage = (RadioGroup) findViewById(R.id.radioLanguage);
        phType = (Spinner) findViewById(R.id.phoneTypeSpinner);
        alternatemobile_no = (EditText) findViewById(R.id.alternatemobile_no);
        uid = (EditText) findViewById(R.id.uid);


        edtAddress = (EditText) findViewById(R.id.address);
        edtCity = (EditText) findViewById(R.id.city);
        edtDistrict = (EditText) findViewById(R.id.district);
        edtPin = (EditText) findViewById(R.id.pin);
        edtEmail_id = (EditText) findViewById(R.id.email_id);

        save = (Button) findViewById(R.id.save);
        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        TextView date = (TextView) findViewById(R.id.sysdate);

        editfirstname.setText(strFirstName);
        editmiddlename.setText(strMiddleName);
        editlasttname.setText(strLastName);
        // editdob.setText(strDob);
        editage.setText(strAge);
        edtAddress.setText(strAddress);
        edtCity.setText(strCityorTown);
        edtDistrict.setText(strDistrict);
        edtPin.setText(strPinNo);
        alternatemobile_no.setText(strAlternatenumber);
        uid.setText(strUid);
        edtEmail_id.setText(strEmail);

        editmobile_no.setInputType(InputType.TYPE_CLASS_NUMBER);
        alternatemobile_no.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only
        editmobile_no.setText(strPhone);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date todayDate = new Date();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate1 = new Date();

        String dd = sdf1.format(todayDate1);
        date.setText("Today's Date: " + dd);


        //this date is ued to set update records date in patient history table
        modified_on_date = sdf.format(todayDate);


        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date todayDate3 = new Date();


        //this date is ued to set update records date in patient history table
        modifiedTime = sdf3.format(todayDate3);

        setPhoneTypeSpinnerAdapters();
        //open database controller class for further operations on database
        try {
            if (lastNamedb == null) {
                lastNamedb = new LastnameDatabaseClass(getApplicationContext());
                lastNamedb.openDataBase();
            }
            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }

            dbController = SQLiteHandler.getInstance(getApplicationContext());
            docId = sqlController.getDoctorId();

            String sbdob;
            //if we get date in wrong format
            if (strDob.equals("30-11-0002")) {

                sbdob = strDob.replace(strDob, "");
                editdob.setText(sbdob);

                strDob = sbdob;
            } else {

                editdob.setText(strDob);
            }
            if (bannerClass == null) {
                bannerClass = new BannerClass(getApplicationContext());
            }
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            bannerimgNames = bannerClass.getImageName();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
            if (lastNamedb != null) {
                lastNamedb.close();
            }
        }

        //This will used to set radio button click came from previous form
        switch (strgender) {
            case "Male":
                radioSexGroup.check(R.id.radioMale);
                break;
            case "Female":
                radioSexGroup.check(R.id.radioFemale);
                break;
            case "Trans":
                radioSexGroup.check(R.id.radioTrans);
                break;
            default:
                radioSexGroup.check(R.id.radioMale);
                break;
        }

        switch (strLanguage) {
            case "Englis":
                radioLanguage.check(R.id.radioEng);
                break;
            case "Hindi":
                radioLanguage.check(R.id.radioHin);
                break;
            case "Bengali":
                radioLanguage.check(R.id.radioBen);
                break;
        }
        if (strDob != null && !strDob.equals("")) {
            int size = strDob.trim().length();
            if (size > 5) {
                editage.setEnabled(false);
            } else {
                editage.setEnabled(true);
                editdob.setText("");
            }
        }

        Glide.get(getApplicationContext()).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();
            }
        }).start();

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto) && appController.getImageFromPath(strPatientPhoto)) {

            setUpGlide(strPatientPhoto, patientImage);

        } else {
            patientImage.setImageResource(R.drawable.main_profile);
        }
        switch (strLanguage) {
            case "English":
                position = 0;
                break;
            case "Hindi":
                position = 1;
                break;
            case "Bengali":
                position = 2;
                break;
        }
        //this will disable age edit text if dob is present 31-8-2016 Ashish
        try {
            if (strDob.length() > 0) {
                editage.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        //set Language value to  spinner
        // setLanguageSpinnerAdapters();

        try {

            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }

            lastNamedb.openDataBase();


            mLastNameList = lastNamedb.getLastNameNew();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " Edit Personal Info " + e + "  Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {

            if (lastNamedb != null) {
                lastNamedb.close();
            }
            if (sqlController != null) {
                sqlController.close();
            }

        }


        setUpSpinner();

        String[] some_array = getResources().getStringArray(R.array.states);
        ArrayList<String> _stateList = new ArrayList<>();
        Collections.addAll(_stateList, some_array);
        if (strState != null) {
            int postion = getCategoryPos(_stateList, strState);
            stateSpinner.setSelection(postion);
        }

        String[] phTypeArray = getResources().getStringArray(R.array.phType_group);
        ArrayList<String> _phTypeList = new ArrayList<>();
        Collections.addAll(_phTypeList, phTypeArray);//adding all elemnts into list from array
        if (strAlternatephtype != null) {
            int postion = getCategoryPos(_phTypeList, strAlternatephtype);
            phoneTypeSpinner2.setSelection(postion);
        }

        if (strPhoneType != null) {
            int postion = getCategoryPos(_phTypeList, strPhoneType);
            phType.setSelection(postion);
        }


        final Button cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    cancel.setBackgroundColor(getResources().getColor(R.color.cancelbtn));
                    showCancelAlertDialog();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    cancel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        editdob.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog();

            }
        });

        Button resetdobage = (Button) findViewById(R.id.resetdobage);

        resetdobage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editage.setEnabled(true);
                editage.setText("");
                editdob.setText("");
            }
        });
       // RadioGroup gndrbutton = (RadioGroup) findViewById(R.id.radioGender);

        sex = "Male";//set Daefault gender value to Male if not selected any other value to prevent null value. 14-12-2016
        // Checked change Listener for RadioGroup 1
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMale:
                        sex = "Male";
                        break;
                    case R.id.radioFemale:
                        sex = "Female";
                        break;
                    case R.id.radioTrans:
                        sex = "Trans";
                        break;

                    default:
                        break;
                }
            }
        });

        selectedLanguage = "English";
        radioLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioEng:
                        selectedLanguage = "English";
                        break;
                    case R.id.radioHin:
                        selectedLanguage = "Hindi";
                        break;
                    case R.id.radioBen:
                        selectedLanguage = "Bengali";
                        break;


                    default:
                        break;
                }
            }
        });


        addPatientImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();
                first_name = editfirstname.getText().toString().trim();
                last_name = editlasttname.getText().toString().trim();


                if (TextUtils.isEmpty(first_name)) {
                    editfirstname.setError("Please enter First Name");
                    return;
                }


                if (TextUtils.isEmpty(last_name)) {
                    editlasttname.setError("Please enter Last Name");
                    return;
                }

                imageName = "imgs_" + first_name + "_" + last_name + "_" + docId + "_" + appController.getDateTime() + ".png";


                imagePathFile = new File(imagesFolder, imageName);

                uriSavedImage = Uri.fromFile(imagePathFile);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        //Remove red error after text changes By.Ashish 15-11-2016

        editfirstname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                editfirstname.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                editfirstname.setError(null);
            }
        });

        editlasttname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                editlasttname.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                editlasttname.setError(null);
            }
        });

        save.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    save.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    save.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//dbController.updatePatientPersonalInfo(strId,strFirstName,strMiddleName, strLastName,strgender,strDob,strAge,strPhone,strLanguage);
                String editfname = editfirstname.getText().toString();
                String editmname = editmiddlename.getText().toString();
                String editlname = editlasttname.getText().toString();
                String editAge = editage.getText().toString().trim();
                String editPno = editmobile_no.getText().toString();
                strdateob = editdob.getText().toString();
                String editAltrntNumber = alternatemobile_no.getText().toString();

                String strAddress = edtAddress.getText().toString().trim();
                String strCity = edtCity.getText().toString().trim();
                String strDistrict = edtDistrict.getText().toString().trim();
                String strPin = edtPin.getText().toString().trim();
                String strEmailId = edtEmail_id.getText().toString();
                //Removes  leading zeros from age filed  11-11-2016 By.Ashish
                int length = 0;
                int age = 0;
                if (editAge.length() > 0) {
                    try {
                        editAge = AppController.removeLeadingZeroes(editAge);
                        length = editAge.length();
                        age = Integer.parseInt(editAge);
                    } catch (Exception e) {
                        e.printStackTrace();
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "Registration Page : " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }

                if (selectedState != null && selectedState.equals("Select State")) {
                    selectedState = null;
                }

                if (length >= 4) {
                    editage.setError("Invalid Age Entered");
                    return;
                }

                if (age > 150) {
                    editage.setError("Age should be less than 150 Years");
                    return;
                }

                if (TextUtils.isEmpty(editfname)) {
                    editfirstname.setError("Please enter First Name");
                    return;
                }

                if (TextUtils.isEmpty(editlname)) {
                    editlasttname.setError("Please enter Last Name");
                    return;
                }

                if (TextUtils.isEmpty(editAge)) {
                    editage.setError("Please enter Age");
                    return;
                }
                Boolean valuenew = appController.PhoneNumberValidation(editPno);

                if (!valuenew) {
                    editmobile_no.setError("Enter Valid Mobile Number");
                    return;
                }
                if (TextUtils.isEmpty(editPno)) {
                    editmobile_no.setError("Please enter Mobile Number");
                    return;
                }
                if (editPno.length() < 10) {
                    editmobile_no.setError("Mobile Number should be 10 digits");
                    return;
                }

                if (selectedLanguage != null && selectedLanguage.length() > 0) {


                } else {
                    strLanguage = selectedLanguage;

                }
                Boolean valuenew2 = appController.PhoneNumberValidation(editAltrntNumber);
                //  Log.e("value", "  "+value);
                if (!valuenew2) {
                    alternatemobile_no.setError("Enter Valid Mobile Number");
                    // Toast.makeText(getApplicationContext(), "Mobile Number should be 10 digits", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                //convert visit date from 2016-11-1 to 2016-11-01
                try {
                    modified_on_date = myFormat.format(fromUser.parse(modified_on_date));

                } catch (ParseException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
                String strUid = uid.getText().toString();

                String modified_by = docId;
                String action = "modified";
                String flag = "0";
                String status = null;
                try {

                    if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {

                      //  dbController.updatePatientPersonalInfo(strId, editfname, editmname, editlname, sex, strdateob, editAge, editPno, selectedLanguage, patientImagePath, modified_on_date, modified_by, modifiedTime, action, flag, docId, strAddress, strCity, strDistrict, strPin, selectedState, selectedPhoneType, selectedPhoneTypealternate_no, editAltrntNumber, strUid, selectedUidType, selectedIsd_codeType, selectedAlternateNoIsd_codeType, status, strEmailId,"","");
                        // Log.e("kt", "1");
                    } else {
                      //  dbController.updatePatientPersonalInfo(strId, editfname, editmname, editlname, sex, strdateob, editAge, editPno, selectedLanguage, strPatientPhoto, modified_on_date, modified_by, modifiedTime, action, flag, docId, strAddress, strCity, strDistrict, strPin, selectedState, selectedPhoneType, selectedPhoneTypealternate_no, editAltrntNumber, strUid, selectedUidType, selectedIsd_codeType, selectedAlternateNoIsd_codeType, status, strEmailId,"","");
                        //Log.e("bt", "2");
                    }

                    Toast.makeText(getApplicationContext(), "Record Updated", Toast.LENGTH_LONG).show();
                    goToNavigation();

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                } finally {
                    if (dbController != null) {
                        dbController.close();
                    }
                }
            }
        });

        setupAnimation();
    }


    private void setUpSpinner() {

        ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(EditPersonalInfo.this,
                android.R.layout.simple_dropdown_item_1line, mLastNameList);


        lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editlasttname.setThreshold(1);
        editlasttname.setAdapter(lastnamespin);
        ///////////////////////////////////////////////
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.states,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setSelection(position);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedState = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /////////////////////////////////////////////////////
        isd_code = (Spinner) findViewById(R.id.isd_code);
        isd_code.setPrompt("Select Type");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> isd_codeTypeAdapter = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.isd_code,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        isd_codeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        isd_code.setAdapter(isd_codeTypeAdapter);
        isd_code.setSelection(position1);

        isd_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                selectedIsd_codeType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/////////////////////////////////////////////////////
        Spinner isd_code2 = (Spinner) findViewById(R.id.isd_code2);
        isd_code2.setPrompt("Select Type");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> isd_codeTypeAdapter2 = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.isd_code,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        isd_codeTypeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        isd_code2.setAdapter(isd_codeTypeAdapter);
        isd_code2.setSelection(position1);

        isd_code2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                selectedAlternateNoIsd_codeType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void showCancelAlertDialog() {

        final Dialog dialog = new Dialog(EditPersonalInfo.this);
        dialog.setContentView(R.layout.custom_cancelalert);


        dialog.setTitle("Please Confirm");

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


                goToNavigation();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    new ImageCompression(this, imagePathFile.getPath()).execute(uriSavedImage.getPath().trim());
                    previewCapturedImage();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    //preview the captured image into image view
    private void previewCapturedImage() {
        try {

            patientImagePath = uriSavedImage.getPath();

            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {
                //set image to glide 2-11-2016
                setUpGlide(patientImagePath, patientImage);


            }
            //  patientImage.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }


    //show date picker dialog box
    private void shpwDialog() {

        switch (EditPersonalInfo.DATE_DIALOG_ID) {
            case DATE_DIALOG_ID:
                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);

                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd1 = new DatePickerDialog(EditPersonalInfo.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editdob.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                                // we calculate age from dob and set to text view.
                                pateintAge = appController.getAge(year, monthOfYear, dayOfMonth);
                                String ageid = String.valueOf(pateintAge);
                                editage.setText(ageid);
                                editage.setEnabled(false);

                            }

                        }, mYear2, mMonth2, mDay2);
                dpd1.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpd1.show();
                //show age of pateint

                break;

        }
    }

    private void setupAnimation() {

        try {

            appController.setUpAdd(EditPersonalInfo.this, bannerimgNames, backChangingImages, doctor_membership_number, "Edit Personal Info");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();


    }


    private void goToNavigation() {

        Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("lifecycle","onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log.d("lifecycle","onPause invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setupAnimation();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (lastNamedb != null) {
            lastNamedb = null;
        }
        if (sqlController != null) {
            sqlController = null;
        }
        if (dbController != null) {
            dbController = null;
        }

        if (appController != null) {
            appController = null;
        }
        if (bannerClass != null) {
            bannerClass = null;
        }
        doctor_membership_number = null;
        bannerimgNames = null;
        cleanResources();
    }

    private void cleanResources() {

        strPatientPhoto = null;
        strLanguage = null;
        editfirstname = null;
        editmiddlename = null;
        editlasttname = null;
        editdob = null;
        editage = null;
        editmobile_no = null;
        selectedLanguage = null;
        strId = null;
        patientImage = null;
        imageIntent = null;
        imagesFolder = null;
        imageName = null;
        first_name = null;
        last_name = null;
        uriSavedImage = null;
        patientImagePath = null;
        bitmap = null;
        strdateob = null;
        radioSexGroup = null;
        sex = null;
        modified_on_date = null;
        docId = null;
        modifiedTime = null;
        phType = null;

        selectedState = null;
        edtAddress = null;
        edtCity = null;
        edtDistrict = null;
        edtPin = null;
        selectedAlternateNoIsd_codeType = null;
        selectedIsd_codeType = null;
        uid = null;
        stateSpinner = null;
        phoneTypeSpinner2 = null;
        selectedUidType = null;
        selectedPhoneTypealternate_no = null;
        alternatemobile_no = null;
        isd_code = null;
        edtEmail_id = null;
        backChangingImages = null;
        mLastNameList = null;
        phType = null;
        selectedPhoneType = null;
        imagePathFile=null;
    }


    private void setUpGlide(String strPatientPhoto, ImageView patientImage) {
        Glide.with(getApplicationContext())
                .load(strPatientPhoto)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(patientImage);
    }

    private void setPhoneTypeSpinnerAdapters() {

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.phType_group,
                        android.R.layout.simple_spinner_item);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner commented fro
        phType.setAdapter(dataAdapter);
        phType.setSelection(position);
        phType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedPhoneType = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        phoneTypeSpinner2 = (Spinner) findViewById(R.id.phoneTypeSpinner2);
        phoneTypeSpinner2.setAdapter(dataAdapter);
        phoneTypeSpinner2.setSelection(position);

        phoneTypeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedPhoneTypealternate_no = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

////////////////////////////////////////////////////////////////////////////////////
        Spinner uidType = (Spinner) findViewById(R.id.uidtype);
        uidType.setPrompt("Select Type");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> uidTypeAdapter = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.uid_group,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        uidTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        uidType.setAdapter(uidTypeAdapter);
        uidType.setSelection(position1);

        uidType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedUidType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //work like default android back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return false;
    }

    private int getCategoryPos(ArrayList<String> _categories, String category) {
        return _categories.indexOf(category);
    }
}

