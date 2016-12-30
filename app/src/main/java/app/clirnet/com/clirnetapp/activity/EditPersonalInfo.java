package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
import android.util.Log;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;

public class EditPersonalInfo extends AppCompatActivity {

    private final int[] imageArray = {R.drawable.brand, R.drawable.brethnum, R.drawable.deptrim, R.drawable.fenjoy, R.drawable.hapiom, R.drawable.liporev, R.drawable.magnamet, R.drawable.motirest, R.drawable.revituz, R.drawable.suprizon};

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
    private RadioButton radioSexButton;
    private String sex;
    private int position;

    private String modified_on_date;
    private ArrayList<String> mLastNameList;
    private String docId;

    private String modifiedTime;
    private ImageView backChangingImages;
    private LastnameDatabaseClass lastNamedb;
    private String middle_name;
    private Spinner phType;
    private AppController appController;
    private Button save;
    private String fromWhere;
    private Button addPatientImgBtn;
    private String selectedPhoneType;
    private String selectedState;
    private BootstrapEditText edtAddress;
    private BootstrapEditText edtCity;
    private BootstrapEditText edtDistrict;
    private BootstrapEditText edtPin;


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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
        }

        getSupportActionBar().setTitle(Html.fromHtml("<font color='white'>Edit Personal Information</font>"));

        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");

        strId = getIntent().getStringExtra("ID");

        String strFirstName = getIntent().getStringExtra("FIRSTNAME");
        String strMiddleName = getIntent().getStringExtra("MIDDLEAME");
        String strLastName = getIntent().getStringExtra("LASTNAME");
        String strPhone = getIntent().getStringExtra("PHONE");
        String strAge = getIntent().getStringExtra("AGE");
        String strDob = getIntent().getStringExtra("DOB");

        strLanguage = getIntent().getStringExtra("LANGUAGE");
        String strgender = getIntent().getStringExtra("GENDER");

        String strVisitId = getIntent().getStringExtra("VISITID");
        String strAddress = getIntent().getStringExtra("ADDRESS");

        String strCityorTown = getIntent().getStringExtra("CITYORTOWN");
        String strDistrict = getIntent().getStringExtra("DISTRICT");
        String strPinNo = getIntent().getStringExtra("PIN");
        String strState = getIntent().getStringExtra("STATE");
        fromWhere = getIntent().getStringExtra("FROMWHERE");


        patientImage = (ImageView) findViewById(R.id.patientimage);
        editfirstname = (EditText) findViewById(R.id.firstname);
        editmiddlename = (EditText) findViewById(R.id.middlename);
        editlasttname = (AutoCompleteTextView) findViewById(R.id.lastname);
        addPatientImgBtn = (Button) findViewById(R.id.addPatientImgBtn);
        editdob = (EditText) findViewById(R.id.dob);
        editage = (EditText) findViewById(R.id.age);
        editmobile_no = (EditText) findViewById(R.id.mobile_no);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGender);
        RadioGroup radioLanguage = (RadioGroup) findViewById(R.id.radioLanguage);
        phType = (Spinner) findViewById(R.id.phType);

        Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinner);

        edtAddress = (BootstrapEditText) findViewById(R.id.address);
        edtCity = (BootstrapEditText) findViewById(R.id.city);
        edtDistrict = (BootstrapEditText) findViewById(R.id.district);
        edtPin = (BootstrapEditText) findViewById(R.id.pin);

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

        editmobile_no.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only
        editmobile_no.setText(strPhone);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM,yyyy");
        Date todayDate1 = new Date();

        String dd = sdf1.format(todayDate1);
        date.setText("Today's Date: " + dd);


        //this date is ued to set update records date in patient history table
        modified_on_date = sdf.format(todayDate);


        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
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

            dbController = new SQLiteHandler(getApplicationContext());
            docId = sqlController.getDoctorId();
            //   Log.e("docId", "" + docId);
            String sbdob;
            if (strDob.equals("30-11-0002")) {


                sbdob = strDob.replace(strDob, "");
                editdob.setText(sbdob);

                strDob = sbdob;
            } else {

                editdob.setText(strDob);


            }


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
            if (lastNamedb != null) {
                lastNamedb.close();
            }

        }


        //This will used to set radio button click came from previous form
        if (strgender.equals("Male")) {
            radioSexGroup.check(R.id.radioMale);
        } else if (strgender.equals("Female")) {
            radioSexGroup.check(R.id.radioFemale);
        } else if (strgender.equals("OTHR")) {
            radioSexGroup.check(R.id.radioOther);
        } else {
            radioSexGroup.check(R.id.radioNa);
        }

        if (strLanguage.equals("Englis")) {
            radioLanguage.check(R.id.radioEng);
        } else if (strLanguage.equals("Hindi")) {
            radioLanguage.check(R.id.radioHin);
        } else if (strLanguage.equals("Bengali")) {
            radioLanguage.check(R.id.radioBen);
        }

        int size = strDob.trim().length();
        if (size > 5) {
            editage.setEnabled(false);
        } else {
            editage.setEnabled(true);
            editdob.setText("");
        }

        Glide.get(getApplicationContext()).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();
            }
        }).start();

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {

            /*Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);
            patientImage.setImageBitmap(bitmap);*/
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
        }

        //set Language value to  spinner
        // setLanguageSpinnerAdapters();

        Cursor cursor = null;
        try {

            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }

            lastNamedb.openDataBase();


            mLastNameList = lastNamedb.getAilmentsListNew();

            if (mLastNameList.size() > 0) {
                setLastnameSpinner();
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (lastNamedb != null) {
                lastNamedb.close();
            }
            if (sqlController != null) {
                sqlController.close();
            }

        }


        setUpSpinner();


        final Button cancel = (Button) findViewById(R.id.cancel);
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


                showCancelAlertDialog();

            }
        });

        editdob.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog();

            }
        });
        RadioGroup gndrbutton = (RadioGroup) findViewById(R.id.radioGender);

        sex = "Male";//set Daefault gender value to Male if not selected any other value to prevent null value. 14-12-2016
        // Checked change Listener for RadioGroup 1
        gndrbutton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMale:
                        sex = "Male";
                        // Toast.makeText(getApplicationContext(), "Male RadioButton checked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioFemale:
                        sex = "Female";
                        // Toast.makeText(getApplicationContext(), "FeMale RadioButton checked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioOther:
                        sex = "Other";
                        // Toast.makeText(getApplicationContext(), "Other RadioButton checked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.radioNa:
                        sex = "NA";
                        //Toast.makeText(getApplicationContext(), "Na RadioButton checked", Toast.LENGTH_SHORT).show();
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
                        // Toast.makeText(getApplicationContext(), "English RadioButton checked" + selectedLanguage, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioHin:
                        selectedLanguage = "Hindi";
                        //Toast.makeText(getApplicationContext(), "Hindi RadioButton checked" + selectedLanguage, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioBen:
                        selectedLanguage = "Bengali";
                        // Toast.makeText(getApplicationContext(), "Bengali RadioButton checked" + selectedLanguage, Toast.LENGTH_SHORT).show();
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
                middle_name = editmiddlename.getText().toString().trim();
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


                File image = new File(imagesFolder, imageName);
                //  boolean deleted = image.delete();
                // Log.e("bool",""+deleted);

                uriSavedImage = Uri.fromFile(image);
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
                // frameLayout.setVisibility(View.VISIBLE);

//dbController.updatePatientPersonalInfo(strId,strFirstName,strMiddleName, strLastName,strgender,strDob,strAge,strPhone,strLanguage);
                String editfname = editfirstname.getText().toString();
                String editmname = editmiddlename.getText().toString();
                String editlname = editlasttname.getText().toString();
                String editAge = editage.getText().toString().trim();
                String editPno = editmobile_no.getText().toString();
                strdateob = editdob.getText().toString();

                String strAddress = edtAddress.getText().toString().trim();
                String strCity = edtCity.getText().toString().trim();
                String strDistrict = edtDistrict.getText().toString().trim();
                String strPin = edtPin.getText().toString().trim();
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
                        appController.appendLog(appController.getDateTime() + " " + "/ " + "Registration Page : " + e);
                    }
                }
                //   editAge = AppController.removeLeadingZeroes(editAge);

                if (selectedState.equals("Select State")) {
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

                if (TextUtils.isEmpty(editPno)) {
                    editmobile_no.setError("Please enter Mobile Number");
                    return;
                }
                if (editPno.length() < 10) {
                    editmobile_no.setError("Mobile Number should be 10 digits");
                    return;
                }

                if (selectedLanguage != null && selectedLanguage.length() > 0) {

                    Log.e("tang1", "" + strLanguage);
                } else {
                    strLanguage = selectedLanguage;

                }
               /* int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String sex = radioSexButton.getText().toString();*/
                String modified_by = docId;
                String action = "modified";
                String flag = "0";
                //dbController.updatePatientPersonalInfo(strId,"Ashish","G", "Umredkar",strgender,strDob,"28","9695863874",strLanguage);
                try {
                    if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {

                        dbController.updatePatientPersonalInfo(strId, editfname, editmname, editlname, sex, strdateob, editAge, editPno, selectedLanguage, patientImagePath, modified_on_date, modified_by, modifiedTime, action, flag, docId, strAddress, strCity, strDistrict, strPin, selectedState,selectedPhoneType);
                        // Log.e("kt", "1");
                    } else {
                        dbController.updatePatientPersonalInfo(strId, editfname, editmname, editlname, sex, strdateob, editAge, editPno, selectedLanguage, strPatientPhoto, modified_on_date, modified_by, modifiedTime, action, flag, docId, strAddress, strCity, strDistrict, strPin, selectedState,selectedPhoneType);
                        //Log.e("bt", "2");
                    }
                    Toast.makeText(getApplicationContext(), "Record Updated", Toast.LENGTH_LONG).show();
                    goToNavigation();

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
                } finally {
                    if (dbController != null) {
                        dbController.close();
                    }
                }
            }
        });


        /*//This will redirect user to main activity
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // back button pressed
                goToNavigation();

            }


        });
*/

        setupAnimation();
    }


    private void setUpSpinner() {

        ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(EditPersonalInfo.this,
                android.R.layout.simple_dropdown_item_1line, mLastNameList);


        lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editlasttname.setThreshold(1);
        editlasttname.setAdapter(lastnamespin);
        ///////////////////////////////////////////////
        Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        //stateSpinner.setOnItemSelectedListener(EditPatientUpdate.this);
       // stateSpinner.setPrompt("Select State");

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

                Log.v("item", (String) parent.getItemAtPosition(position));

                selectedState = (String) parent.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(), "selected language is:" + (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void setLastnameSpinner() {


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
        Log.d("cap_img", "" + CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        try {

            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage();

                } else if (resultCode == Activity.RESULT_CANCELED) {

                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Personal Info" + e);
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
        backChangingImages.postDelayed(runnable, 200); //for initial delay..
    }

    @Override
    public void onStop() {
        super.onStop();


    }

   /* @Override
    public void onBackPressed() {
        *//*this.onBackPressed();
        finish();*//*
        goToNavigation();

    }*/


    private void goToNavigation() {

        Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
        i.putExtra("FROMWHERE", fromWhere);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myPid());

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
        cleanResources();
        System.gc();

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
        radioSexButton = null;
        sex = null;
        modified_on_date = null;
        docId = null;
        modifiedTime = null;
        middle_name = null;
        phType = null;

        selectedState = null;
        edtAddress = null;
        edtCity = null;
        edtDistrict = null;
        edtPin = null;
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

        ArrayAdapter<CharSequence> language_reasonAdapter = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.phType_group,
                        android.R.layout.simple_spinner_item);
        language_reasonAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner commented fro
        phType.setAdapter(language_reasonAdapter);
        phType.setSelection(position);
        phType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                selectedPhoneType = (String) parent.getItemAtPosition(position);
                Toast.makeText(EditPersonalInfo.this, "selected language is:" + (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
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
}

