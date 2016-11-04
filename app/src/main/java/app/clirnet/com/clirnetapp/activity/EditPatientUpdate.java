package app.clirnet.com.clirnetapp.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.EditPatientAdapter;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

public class EditPatientUpdate extends AppCompatActivity {

    private final int[] imageArray = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    private ImageView backChangingImages;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID = 0;

    private String strPhone;
    private String strAge;
    private String strLanguage;

    private String strgender;
    private String strPatientPhoto;
    private EditText follow_up_date;
    private MultiAutoCompleteTextView ailments1;
    private EditText follow_up_days;
    private EditText follow_up_weeks;
    private EditText follow_up_Months;
    private EditText clinicalNotes;
    private String strFirstName;
    private String strMiddleName;
    private String strLastName;
    private String strDob;
    private String strId;
    private TextView txtfollow_up_date;
    private String fowSel;
    private String usersellectedDate = null;
    private String monthSel;

    private SQLController sqlController;
    private SQLiteHandler dbController;
    private DatabaseClass databaseClass;

    private ArrayList<String> mAilmemtArrayList;

    private ImageView imageViewprescription;
    private String imageName;
    private Intent imageIntent;
    private File imagesFolder;
    private Uri uriSavedImage;
    private String patientImagePath;
    private SimpleDateFormat sdf1;

    private String updatedTime;
    private String sysdate;
    private TextView noRecordsText;
    private String docId;

    private String strVisitId;
    private int maxid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        databaseClass = new DatabaseClass(EditPatientUpdate.this);
        dbController = new SQLiteHandler(EditPatientUpdate.this);
        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");
        Log.e("photo", "" + strPatientPhoto);
        String strName = getIntent().getStringExtra("NAME");
        strId = getIntent().getStringExtra("ID");
        Log.e("patid", "" + strId);
        strFirstName = getIntent().getStringExtra("FIRSTTNAME");
        strMiddleName = getIntent().getStringExtra("MIDDLENAME");
        strLastName = getIntent().getStringExtra("LASTNAME");
        strPhone = getIntent().getStringExtra("PHONE");
        strAge = getIntent().getStringExtra("AGE");
        strDob = getIntent().getStringExtra("DOB");

        strLanguage = getIntent().getStringExtra("LANGUAGE");
        strgender = getIntent().getStringExtra("GENDER");
        String strFollowUpDate = getIntent().getStringExtra("FOD");
        String strActualFollowUpDate = getIntent().getStringExtra("ACTUALFOD");
        String strAilment = getIntent().getStringExtra("AILMENT");
        String strFollowupDays = getIntent().getStringExtra("FOLLOWDAYS");
        String strFollowupWeeks = getIntent().getStringExtra("FOLLOWWEEKS");

        String strFollowupMonth = getIntent().getStringExtra("FOLLOWMONTH");

        String strClinicalNotes = getIntent().getStringExtra("CLINICALNOTES");
        String strPrescriptionImage = getIntent().getStringExtra("PRESCRIPTION");
        strVisitId = getIntent().getStringExtra("VISITID");

        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
        TextView date = (TextView) findViewById(R.id.sysdate);
        TextView editpatientName = (TextView) findViewById(R.id.patientName);
        TextView editage = (TextView) findViewById(R.id.age1);
        TextView editmobileno = (TextView) findViewById(R.id.mobileno);
        TextView editgender = (TextView) findViewById(R.id.gender);
        TextView editlang = (TextView) findViewById(R.id.lang);
        Button addPatientprescriptionBtn = (Button) findViewById(R.id.addPatientprescriptionBtn);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ailments1 = (MultiAutoCompleteTextView) findViewById(R.id.ailments1);
        follow_up_date = (EditText) findViewById(R.id.follow_up_date);
        follow_up_days = (EditText) findViewById(R.id.follow_up_days);
        follow_up_weeks = (EditText) findViewById(R.id.follow_up_weeks);
        follow_up_Months = (EditText) findViewById(R.id.follow_up_Months);
        clinicalNotes = (EditText) findViewById(R.id.clinicalNotes);
        ImageView imgEdit = (ImageView) findViewById(R.id.edit);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button editUpdate = (Button) findViewById(R.id.editUpdate);
        imageViewprescription = (ImageView) findViewById(R.id.imageViewprescription);
        txtfollow_up_date = (TextView) findViewById(R.id.txtfollow_up_date);

        /*noRecordsText=(TextView)findViewById(R.id.noRecordsText);

        lastUpdateButton=(Button)findViewById(R.id.lastUpdate);
*/

        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);
//open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPatientUpdate.this, PrivacyPolicy.class);
                startActivity(intent);
                finish();

            }
        });
        Glide.get(EditPatientUpdate.this).clearMemory();
        //open Terms  and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPatientUpdate.this, TermsCondition.class);
                startActivity(intent);
                finish();

            }
        });
        sdf1 = new SimpleDateFormat("dd-MM-yyyy");


        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        sysdate = String.valueOf(new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append(""));


        SimpleDateFormat sdf0 = new SimpleDateFormat("dd-M-yyyy");


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy");
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        String justDate = sdf0.format(todayDate);

        date.setText("Today's Date " + dd);
        //this date is ued to set update records date in patient history table


        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
        Date todayDate3 = new Date();

        //this date is ued to set update records date in patient history table
        updatedTime = sdf3.format(todayDate3);


        if (strFollowupDays == "0") {

            strFollowupDays = "";
        }

        if (strFollowupWeeks == "0") {

            strFollowupWeeks = "";
        }

        if (strFollowupMonth == "0") {

            strFollowupMonth = "";
        }


        editpatientName.setText(strName);
        editmobileno.setText(strPhone);
        editage.setText(strAge);
        editlang.setText(strLanguage);
        editgender.setText(strgender);

        ailments1.setText(strAilment);
        follow_up_days.setText(strFollowupDays);
        follow_up_weeks.setText(strFollowupWeeks);
        follow_up_Months.setText(strFollowupMonth);
        clinicalNotes.setText(strClinicalNotes);
        txtfollow_up_date.setText(strActualFollowUpDate);//add selected date to date text view

        if (TextUtils.isEmpty(strFollowupDays) && TextUtils.isEmpty(strFollowupWeeks) && TextUtils.isEmpty(strFollowupMonth)) {
            follow_up_date.setText(strFollowUpDate);
        }

        try {

            sqlController = new SQLController(EditPatientUpdate.this);
            sqlController.open();
            docId = sqlController.getDoctorId();

            databaseClass.openDataBase();

            ArrayList<RegistrationModel> patientData = (sqlController.getPatientHistoryListAll(strId));
            int size = patientData.size();


            List<RegistrationModel> filteredpatientData = filterBySystemDate(patientData, justDate);


            if (size > 0) {

                EditPatientAdapter editPatientAdapter = new EditPatientAdapter(filteredpatientData);
                recyclerView.setAdapter(editPatientAdapter);


            } else {
                recyclerView.setVisibility(View.GONE);
                noRecordsText.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                int id = databaseClass.getMaxAimId();
                maxid = id + 1;
            }catch(Exception e) {
                e.printStackTrace();

            }
            }

        Cursor cursor =null;
        try {
            cursor = databaseClass.getAilmentsList();
            mAilmemtArrayList = new ArrayList<>();
            int columnIndex = cursor.getColumnIndex("ailment_name");
            while (cursor.moveToNext()) {
                mAilmemtArrayList.add(cursor.getString(columnIndex)); //add the item
                //  Log.e("ali", "ali is:" + cursor.getString(columnIndex));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(cursor !=null){
                cursor.close();
            }
        }
        //this code is for setting list to auto complete text view  8/6/16
        ArrayAdapter<String> adp = new ArrayAdapter<>(EditPatientUpdate.this,
                android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ailments1.setThreshold(1);

        ailments1.setAdapter(adp);
        ailments1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

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

                //  patientImage.setImageBitmap(bitmap);
            }
        }

        if (strPrescriptionImage != null && !TextUtils.isEmpty(strPrescriptionImage)) {
            if (strPrescriptionImage.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);
                Glide.with(EditPatientUpdate.this)
                        .load(strPrescriptionImage)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        .skipMemoryCache(true)
                        .error(R.drawable.main_profile)
                        .into(imageViewprescription);

                //  patientImage.setImageBitmap(bitmap);
            }
        }
        //this will redirect to EditPersonalInfo class to edit personal info
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EditPatientUpdate.this, EditPersonalInfo.class);

                i.putExtra("PATIENTPHOTO", strPatientPhoto);
                i.putExtra("ID", strId);
                i.putExtra("FIRSTNAME", strFirstName);
                i.putExtra("MIDDLEAME", strMiddleName);
                i.putExtra("LASTNAME", strLastName);
                i.putExtra("PHONE", strPhone);
                i.putExtra("DOB", strDob);
                i.putExtra("AGE", strAge);
                i.putExtra("LANGUAGE", strLanguage);
                i.putExtra("GENDER", strgender);
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // frameLayout.setVisibility(View.VISIBLE);
              /*  AppController appController=new AppController();
                appController.goToNavigationActivity(getApplicationContext(),NavigationActivity.class);*/
                //redirect to navigation activity

                goToNavigation();

                // Toast.makeText(getContext(),"chala  jana bhai",Toast.LENGTH_SHORT).show();
            }
        });
//update the data to db
        editUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // frameLayout.setVisibility(View.VISIBLE);
                saveData();

            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                //redirect to navigation activity
                goToNavigation();
                //  Toast.makeText(EditPatientUpdate.this,"back is pressed",Toast.LENGTH_SHORT).show();
            }
        });


//capture image

        addPatientprescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                imageName = "prescription_" + strFirstName + "_" + strLastName + ".png";

                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        setupAnimation();

        follow_up_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    follow_up_days.getText().clear();
                    follow_up_weeks.getText().clear();
                    follow_up_Months.getText().clear();
                }
                if (!hasFocus) {

                    txtfollow_up_date.setText(follow_up_date.getText().toString());
                }
            }

        });


        follow_up_days.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    //Toast.makeText(RegistrationActivity.this,"fodays "+follow_up_days.getText().toString(),Toast.LENGTH_SHORT).show();
                    try {

                        int days = Integer.parseInt(follow_up_days.getText().toString());
                        String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), days));
                        txtfollow_up_date.setText(dateis);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        });

        follow_up_weeks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {


                    try {


                        //  Toast.makeText(EditPatientUpdate.this, "fodweeks " + follow_up_weeks.getText().toString(), Toast.LENGTH_SHORT).show();


                        int fow = Integer.parseInt(follow_up_weeks.getText().toString());
                        fowSel = String.valueOf(fow);

                        int daysFromWeeks = fow * 7;
                        String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));
                        txtfollow_up_date.setText(dateis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        follow_up_Months.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    try {
                        //  Toast.makeText(EditPatientUpdate.this, "fodmonth " + follow_up_Months.getText().toString(), Toast.LENGTH_SHORT).show();

                        int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
                        monthSel = String.valueOf(monthselected);


                        String dateis = sdf1.format(RegistrationActivity.addMonth(new Date(), monthselected));
                        txtfollow_up_date.setText(dateis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        });
        follow_up_date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                follow_up_days.getText().clear();
                follow_up_weeks.getText().clear();
                follow_up_Months.getText().clear();

                shpwDialog();

            }
        });

        follow_up_date.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
              /*  follow_up_days.getText().clear();
                follow_up_weeks.getText().clear();
                follow_up_Months.getText().clear();*/
            }
        });

        follow_up_days.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                try {


                    int days = Integer.parseInt(follow_up_days.getText().toString());
                    String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), days));

                    txtfollow_up_date.setText(dateis);
                    follow_up_date.getText().clear();
                    follow_up_weeks.getText().clear();
                    follow_up_Months.getText().clear();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        follow_up_weeks.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                try {

                    int fow = Integer.parseInt(follow_up_weeks.getText().toString());
                    fowSel = String.valueOf(fow);

                    int daysFromWeeks = fow * 7;
                    String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));
                    txtfollow_up_date.setText(dateis);
                    follow_up_date.getText().clear();
                    follow_up_days.getText().clear();
                    follow_up_Months.getText().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        follow_up_Months.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                try {
                    int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
                    monthSel = String.valueOf(monthselected);


                    String dateis = sdf1.format(RegistrationActivity.addMonth(new Date(), monthselected));
                    txtfollow_up_date.setText(dateis);

                    follow_up_date.getText().clear();
                    follow_up_days.getText().clear();
                    follow_up_weeks.getText().clear();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }

    //saved the user enetred data to db
    private void saveData() {
        String daysSel = null;
        monthSel = null;
        fowSel = null;
        String strfollow_up_date;
        String ailments = ailments1.getText().toString().trim();
        String clinical_note = clinicalNotes.getText().toString().trim();
        strfollow_up_date = follow_up_date.getText().toString().trim();
        if (TextUtils.isEmpty(ailments)) {
            ailments1.setError("Please enter Ailment !");
            return;
        }

        String followupdateSellected;
        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_days.getText().toString().length() == 0 & follow_up_date.getText().toString().length() > 0) {
            followupdateSellected = follow_up_date.getText().toString();
            try {


                Date date1 = sdf1.parse(followupdateSellected);

                usersellectedDate = sdf1.format(date1);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* Toast.makeText(EditPatientUpdate.this, "Date is:!" + followupdateSellected, Toast.LENGTH_LONG).show();*/

        }

        String dateis;
        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_days.getText().toString().length() > 0) {
            // followupdateSellected = follow_up_days.getText().toString();

            int days = Integer.parseInt(follow_up_days.getText().toString());
            // int days = fow + day2;

            daysSel = String.valueOf(days);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            dateis = sdf1.format(RegistrationActivity.addDay(new Date(), days));


            usersellectedDate = dateis;

        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() > 0) {

//          /  followupdateSellected = follow_up_weeks.getText().toString();

            //Used to conert weeks into date
            int fow = Integer.parseInt(follow_up_weeks.getText().toString());
            fowSel = String.valueOf(fow);

            int daysFromWeeks = fow * 7;


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            dateis = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));
            // String dateis= String.valueOf(addDay(new Date(), daysFromWeeks));


            usersellectedDate = dateis;


        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() > 0) {
            //followupdateSellected = follow_up_Months.getText().toString();

            int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
            monthSel = String.valueOf(monthselected);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            dateis = sdf.format(RegistrationActivity.addMonth(new Date(), monthselected));

            usersellectedDate = dateis;


        }
        String delimiter = ",";
        String[] temp = ailments.split(delimiter);
             /* print substrings */
        for (String aTemp : temp) {
            //System.out.println(temp[i]);

            if (!new RegistrationActivity().isDuplicate(mAilmemtArrayList, aTemp)) {

                // dbController.addAilment(temp[i]);
                databaseClass.addAilments(aTemp, maxid);
                maxid = maxid + 1;


            }
        }
//  System.out.println("Date1 is before or equal to Date2");

        //  dbController.addPatient(first_name, middle_name, last_name, sex, strdate_of_birth, current_age, phone_number, selectedLanguage, patientImagePath, usersellectedDate, daysSel, fowSel, monthSel, ailments, prescriptionImgPath, clinical_note, sysdate.toString());

        String modified_by = docId;//INSERTING DOC ID IN ADDED BY COLUMN AS PER PUSHPAL SAID
        String flag = "0";

        String patientInfoType = "App";
        String action = "updated";
        dbController.updatePatientOtherInfo(strId, strVisitId, usersellectedDate, strfollow_up_date, daysSel, fowSel, monthSel, clinical_note, patientImagePath, ailments, sysdate, updatedTime, modified_by, action, patientInfoType, flag);

        Toast.makeText(EditPatientUpdate.this, "Patient Record Updated Successfully!!!", Toast.LENGTH_LONG).show();
//redirect to navigation activity

        goToNavigation();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("cap_img", "" + CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // user cancelled Image capture
                    /*new RegistrationActivity().customToast("User cancelled image capture");*/
                } else {
                    // failed to capture image
                   /* new RegistrationActivity().customToast("Sorry! Failed to capture image");*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this will show captured image to image view
    private void previewCapturedImage() {
        try {

            patientImagePath = uriSavedImage.getPath();
            Log.e("path", "" + patientImagePath);
            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {
                     setUpGlide(patientImagePath,imageViewprescription);
                      /*Glide.with(EditPatientUpdate.this)
                        .load(patientImagePath)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageViewprescription);*/


            }
       /*     Toast.makeText(EditPatientUpdate.this, "Patient Image Path=" + patientImagePath, Toast.LENGTH_LONG).show();*/

            //  patientImage.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setUpGlide(String patientImagePath, ImageView img) {
        Glide.with(EditPatientUpdate.this)
                .load(patientImagePath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(img);
    }

    //show date sellector dialog box
    private void shpwDialog() {

        switch (EditPatientUpdate.DATE_DIALOG_ID) {
            case DATE_DIALOG_ID:

                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);
                Log.e("mYear2", "age is" + mYear2);
                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);

                Log.d("mYear1", "" + mYear2);

                DatePickerDialog dpd1 = new DatePickerDialog(EditPatientUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                follow_up_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                txtfollow_up_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                // we calculate age from dob and set to text view.
                            }

                        }, mYear2, mMonth2, mDay2);
                c2.add(Calendar.DATE, 1);
                Date newDate = c2.getTime();
                dpd1.getDatePicker().setMinDate(newDate.getTime());
                dpd1.show();
                //show age of pateint

                break;
        }
    }

    //This method will filter data from our database generated list according to user query By Sys Date 6/8/i Ashish
    private List<RegistrationModel> filterBySystemDate(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {


            final String phVisitDate = model.getVisit_date().toLowerCase();


            if (phVisitDate.contains(query)) {

              /*  filteredModelList.add(model);
                Log.e("result", "" + dateCreatedPatient);*/
            } else {
                filteredModelList.add(model);
                Log.e("result", "" + phVisitDate);
            }
        }
        return filteredModelList;
    }

    private void goToNavigation() {
        Intent i = new Intent(EditPatientUpdate.this, NavigationActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (backChangingImages != null) {
            // backChangingImages=null;
            backChangingImages.setImageDrawable(null);
        }
        System.gc();
    }

    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myPid());

        super.onDestroy();

        if(sqlController != null){
            sqlController = null;
        }
        if(dbController != null){
            dbController=null;
        }
        if(databaseClass != null){
            databaseClass=null;
        }
        sdf1=null;
       // System.gc();

    }
}
