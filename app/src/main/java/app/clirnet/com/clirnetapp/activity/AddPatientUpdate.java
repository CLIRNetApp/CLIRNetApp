package app.clirnet.com.clirnetapp.activity;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.AddPatientUpdateAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


@SuppressWarnings("AccessStaticViaInstance")
public class AddPatientUpdate extends AppCompatActivity {

    private final int[] imageArray = {R.drawable.brand, R.drawable.brethnum, R.drawable.deptrim, R.drawable.fenjoy, R.drawable.hapiom,R.drawable.liporev, R.drawable.magnamet, R.drawable.motirest,R.drawable.revituz,R.drawable.suprizon};
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

    private String strPatientId;
    private TextView txtfollow_up_date;
    private String fowSel = null;
    private String usersellectedDate = null;
    private String monthSel = null;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private ArrayList<String> mAilmemtArrayList;

    private ImageView imageViewprescription;

    private Intent imageIntent;
    private File imagesFolder;
    private Uri uriSavedImage;
    private SimpleDateFormat sdf1;
    private int maxVisitId;
    private String doctor_membership_number;
    private String docId;
    private String addedTime;
    private String addedOnDate;
    private ArrayList<RegistrationModel> patientHistoryData = new ArrayList<>();
    private DatabaseClass databaseClass;
    private String PrescriptionimageName;
    private int maxid;
    private AppController appController;
    private Button addUpdate;
    private Button cancel;

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         appController=new AppController();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime()+"" +"/"+"Add Patient"+e);
        }

        if (databaseClass == null) {
            databaseClass = new DatabaseClass(getApplicationContext());
        }


        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");
        Log.e("photo", "" + strPatientPhoto);
        String strName = getIntent().getStringExtra("NAME");
        strPatientId = getIntent().getStringExtra("PatientID");
        Log.e("patid", "" + strPatientId);
        strFirstName = getIntent().getStringExtra("FIRSTTNAME");
        strMiddleName = getIntent().getStringExtra("MIDDLENAME");
        strLastName = getIntent().getStringExtra("LASTNAME");
        strPhone = getIntent().getStringExtra("PHONE");
        strAge = getIntent().getStringExtra("AGE");
        strDob = getIntent().getStringExtra("DOB");
        strLanguage = getIntent().getStringExtra("LANGUAGE");
        strgender = getIntent().getStringExtra("GENDER");

        String strPrescriptionImage = getIntent().getStringExtra("PRESCRIPTION");
        //Initalize global view to this method 3-11-2016
        initalizeView();

        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
        TextView date = (TextView) findViewById(R.id.sysdate);
        TextView editpatientName = (TextView) findViewById(R.id.patientName);
        TextView editage = (TextView) findViewById(R.id.age1);
        TextView editmobileno = (TextView) findViewById(R.id.mobileno);
        TextView editgender = (TextView) findViewById(R.id.gender);
        TextView editlang = (TextView) findViewById(R.id.lang);
        Button addPatientprescriptionBtn = (Button) findViewById(R.id.addPatientprescriptionBtn);
        ImageView imgEdit = (ImageView) findViewById(R.id.edit);
         cancel = (Button) findViewById(R.id.cancel);
         addUpdate = (Button) findViewById(R.id.addUpdate);

        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Button editlastUpdate = (Button) findViewById(R.id.editlastUpdate);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();

        Glide.get(AddPatientUpdate.this).clearMemory();
        recyclerView.setLayoutParams(params);


//open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);
                finish();

            }
        });
//open TermsCondition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsCondition.class);
                startActivity(intent);
                finish();

            }
        });

        sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy");
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        date.setText("Today's Date " + dd);
        //this date is ued to set update records date in patient history table
        // String current_date = sdf.format(todayDate);


        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
        Date todayDate3 = new Date();


        //this date is ued to set update records date in patient history table
        addedTime = sdf3.format(todayDate3);


        //This will give date format in 2-9-2016 format
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        addedOnDate = String.valueOf(new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append(""));


        editpatientName.setText(strName);
        editmobileno.setText(strPhone);
        editage.setText(strAge);
        editlang.setText(strLanguage);
        editgender.setText(strgender);


        try {
            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }
            dbController = new SQLiteHandler(getApplicationContext());
            //This will get all the visit  history of patient
            patientHistoryData = (sqlController.getPatientHistoryListAll(strPatientId)); //get all patient data from db
            int size = patientHistoryData.size();

            if (size > 0) {
                //this will set adapter to list to show all the patient history
                AddPatientUpdateAdapter pha = new AddPatientUpdateAdapter(patientHistoryData);
                recyclerView.setAdapter(pha);

            }
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            docId = sqlController.getDoctorId();
            maxVisitId = sqlController.getPatientVisitIdCount();
        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        Cursor cursor = null;
        try {

            databaseClass.openDataBase();
            mAilmemtArrayList=databaseClass.getAilmentsListNew();
            int id = databaseClass.getMaxAimId();
            maxid = id + 1;
            if(mAilmemtArrayList.size()>0){

                //this code is for setting list to auto complete text view  8/6/16

                ArrayAdapter<String> adp = new ArrayAdapter<>(AddPatientUpdate.this,
                        android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                ailments1.setThreshold(1);

                ailments1.setAdapter(adp);
                ailments1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime()+"" +"/"+"Add Patient" + e);
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            if (databaseClass != null) {
                databaseClass.close();
            }
        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(AddPatientUpdate.this).clearDiskCache();
            }
        }).start();

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {

            if (strPatientPhoto.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);
                setUpGlide(strPatientPhoto, patientImage);


                //  patientImage.setImageBitmap(bitmap);
            }
        }

        if (strPrescriptionImage != null && !TextUtils.isEmpty(strPrescriptionImage)) {
            if (strPrescriptionImage.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);

                setUpGlide(strPrescriptionImage, imageViewprescription);
               /* Glide.with(AddPatientUpdate.this)
                        .load(strPrescriptionImage)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.main_profile)
                        .into(imageViewprescription);*/

                //  patientImage.setImageBitmap(bitmap);
            }
        }

        addPatientprescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                PrescriptionimageName = "prescription_" +docId+"_"+appController.getDateTime()+  ".jpg";

                File image = new File(imagesFolder, PrescriptionimageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        //this will redirect to EditPersonalInfo class to edit personal info
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), EditPersonalInfo.class);

                i.putExtra("PATIENTPHOTO", strPatientPhoto);
                i.putExtra("ID", strPatientId);
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
        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // frameLayout.setVisibility(View.VISIBLE);


                goToNavigation();


            }


        });*/
//save the data to db
        addUpdate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        addUpdate.setOnClickListener(new View.OnClickListener() {
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
                goToNavigation();
                //  Toast.makeText(EditPatientUpdate.this,"back is pressed",Toast.LENGTH_SHORT).show();
            }
        });
        ailments1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                ailments1.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ailments1.setError(null);
            }
        });

        follow_up_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    follow_up_days.getText().clear();
                    follow_up_weeks.getText().clear();
                    follow_up_Months.getText().clear();
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
                       // Log.e("cdate", "" + dateis);
                        txtfollow_up_date.setText(dateis);
                    } catch (Exception e) {
                        e.printStackTrace();
                        appController.appendLog(appController.getDateTime()+"" +"/"+"Add Patient" + e);


                    }
                }
            }
        });

        follow_up_weeks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    try {


                        int fow = Integer.parseInt(follow_up_weeks.getText().toString());
                        fowSel = String.valueOf(fow);
                       // Log.e("fowSel", "" + fowSel);
                        int daysFromWeeks = fow * 7;
                        String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));
                        txtfollow_up_date.setText(dateis);
                    } catch (Exception e) {
                        e.printStackTrace();
                        appController.appendLog(appController.getDateTime()+"" +"/"+"Add Patient" + e);
                    }

                }
            }
        });

        follow_up_Months.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    try {

                        int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
                        monthSel = String.valueOf(monthselected);


                        String dateis = sdf1.format(RegistrationActivity.addMonth(new Date(), monthselected));
                        txtfollow_up_date.setText(dateis);
                    } catch (Exception e) {
                        e.printStackTrace();
                        appController.appendLog(appController.getDateTime()+"" +"/"+"Add Patient" + e);
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
                follow_up_weeks.setError(null);
                follow_up_days.setError(null);
                follow_up_Months.setError(null);

                shpwDialog();

            }
        });


        follow_up_days.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String mFod = follow_up_days.getText().toString();

                try {
                    if (mFod != null) {
                        int days = 0;
                        days = Integer.parseInt(mFod);
                        String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), days));
                       // Log.e("cdate", "" + dateis);
                        txtfollow_up_date.setText(dateis);
                        follow_up_date.getText().clear();
                        follow_up_weeks.getText().clear();
                        follow_up_Months.getText().clear();
                        follow_up_weeks.setError(null);
                        follow_up_Months.setError(null);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime()+" " +"/ "+"Add Patient" + e);
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
                String strFow = follow_up_weeks.getText().toString();

                try {
                    if (strFow != null) {
                        int fow = 0;
                        fow = Integer.parseInt(strFow);
                        fowSel = String.valueOf(fow);
                      //  Log.e("fowSel", "" + fowSel);
                        int daysFromWeeks = fow * 7;
                        String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));
                        txtfollow_up_date.setText(dateis);
                        follow_up_date.getText().clear();
                        follow_up_days.getText().clear();
                        follow_up_Months.getText().clear();
                        follow_up_days.setError(null);
                        follow_up_Months.setError(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime()+" " +"/ "+"Add Patient" + e);
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

                String mFom = follow_up_Months.getText().toString();
                try {
                    if (mFom != null) {
                        int monthselected = 0;
                        monthselected = Integer.parseInt(mFom);
                        monthSel = String.valueOf(monthselected);
                        String dateis = sdf1.format(RegistrationActivity.addMonth(new Date(), monthselected));
                        txtfollow_up_date.setText(dateis);

                        follow_up_date.getText().clear();
                        follow_up_days.getText().clear();
                        follow_up_weeks.getText().clear();

                        follow_up_days.setError(null);
                        follow_up_weeks.setError(null);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime()+" " +"/ "+"Add Patient" + e);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        if (patientHistoryData.size() > 0) {
            editlastUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sendDataToEditPatientUpdate();

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "There is no history to update!!!", Toast.LENGTH_LONG).show();
        }
        setupAnimation();
    }

    private void initalizeView() {

        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        ailments1 = (MultiAutoCompleteTextView) findViewById(R.id.ailments1);
        follow_up_date = (EditText) findViewById(R.id.follow_up_date);
        follow_up_days = (EditText) findViewById(R.id.follow_up_days);
        follow_up_weeks = (EditText) findViewById(R.id.follow_up_weeks);
        follow_up_Months = (EditText) findViewById(R.id.follow_up_Months);
        clinicalNotes = (EditText) findViewById(R.id.clinicalNotes);

        imageViewprescription = (ImageView) findViewById(R.id.imageViewprescription);
        txtfollow_up_date = (TextView) findViewById(R.id.txtfollow_up_date);


    }

    //this will send data to EditPatientUpdate Activity
    private void sendDataToEditPatientUpdate() {

        Intent i = new Intent(AddPatientUpdate.this, EditPatientUpdate.class);

        i.putExtra("PATIENTPHOTO", patientHistoryData.get(0).getPhoto());
        i.putExtra("ID", patientHistoryData.get(0).getPat_id());
        Log.e("Patientkaid", "" + patientHistoryData.get(0).getPat_id());
        Log.e("Patientkaid", "" + patientHistoryData.get(0).getVisit_date());

        i.putExtra("NAME", patientHistoryData.get(0).getFirstName() + " " + patientHistoryData.get(0).getLastName());
        i.putExtra("FIRSTTNAME", patientHistoryData.get(0).getFirstName());
        i.putExtra("MIDDLENAME", patientHistoryData.get(0).getMiddleName());
        i.putExtra("LASTNAME", patientHistoryData.get(0).getLastName());
        i.putExtra("DOB", patientHistoryData.get(0).getDob());

        i.putExtra("PHONE", patientHistoryData.get(0).getMobileNumber());

        i.putExtra("AGE", patientHistoryData.get(0).getAge());
        i.putExtra("LANGUAGE", patientHistoryData.get(0).getLanguage());
        i.putExtra("GENDER", patientHistoryData.get(0).getGender());
        i.putExtra("FOD", patientHistoryData.get(0).getFollowUpDate());
        i.putExtra("AILMENT", patientHistoryData.get(0).getAilments());
        i.putExtra("FOLLOWDAYS", patientHistoryData.get(0).getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", patientHistoryData.get(0).getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", patientHistoryData.get(0).getFollowUpMonth());
        i.putExtra("CLINICALNOTES", patientHistoryData.get(0).getClinicalNotes());
        i.putExtra("PRESCRIPTION", patientHistoryData.get(0).getPres_img());
        Log.e("img", "" + patientHistoryData.get(0).getPres_img());

        startActivity(i);
    }

    private void setUpGlide(String imgPath, ImageView patientImage) {

        Glide.with(getApplicationContext())
                .load(imgPath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .dontAnimate()
                .error(R.drawable.main_profile)
                .into(patientImage);
    }


    //saved the user enetred data to db
    private void saveData() {

        String daysSel = null;
        monthSel = null;
        fowSel = null;
        String ailments = ailments1.getText().toString().trim();
        String clinical_note = clinicalNotes.getText().toString().trim();
        String follow_up_dates = follow_up_date.getText().toString().trim();

        String followupdateSellected;

        if (TextUtils.isEmpty(ailments)) {
            ailments1.setError("Please enter Ailment");
            return;
        }else {
            ailments1.setError(null);
        }
        if(ailments.length()>0 && ailments.length()<2  && ailments.contains(",")) {
            ailments1.setError("Please Enter Valid ailment");
            return;
        }

        //remove comma occurance from string
        ailments= appController.removeCommaOccurance(ailments);


        Boolean ailmentValue =false;

        if (ailments.length() > 0) {
            ailmentValue = appController.findNumbersAilment(ailments);
            Log.e("ailmentValue", "" + ailmentValue);
            if(ailmentValue){
                ailments1.setError("Please Enter Valid ailment");
                return;
            }
        }

        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_days.getText().toString().length() == 0 & follow_up_date.getText().toString().length() > 0) {
            followupdateSellected = follow_up_date.getText().toString();
            try {


                Date date1 = sdf1.parse(followupdateSellected);

                usersellectedDate = sdf1.format(date1);
            } catch (Exception e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime()+" " +"/ "+"Add Patient" + e);
            }


        }

        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_days.getText().toString().length() > 0) {
            // followupdateSellected = follow_up_days.getText().toString();

            int days = Integer.parseInt(follow_up_days.getText().toString());
            // int days = fow + day2;
            if (days > 366) {
                follow_up_days.setError("Enter up to 366 Days");
                return;
            }

            daysSel = String.valueOf(days);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), days));
          //  Log.e("cdate", "" + dateis);

            usersellectedDate = dateis;
           // Log.e("usersellectedDays", "" + usersellectedDate);

        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() > 0) {

            //followupdateSellected = follow_up_weeks.getText().toString();

            //Used to conert weeks into date
            int fow = Integer.parseInt(follow_up_weeks.getText().toString());


            if (fow > 54) {
                follow_up_weeks.setError("Enter up to 54 Weeks");
                return;
            }

            fowSel = String.valueOf(fow);
            Log.e("fowSel", "" + fowSel);
            int daysFromWeeks = fow * 7;


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");


            // String dateis= String.valueOf(addDay(new Date(), daysFromWeeks));


            usersellectedDate = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));


        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() > 0) {
            //followupdateSellected = follow_up_Months.getText().toString();

            int monthselected = Integer.parseInt(follow_up_Months.getText().toString());

            if (monthselected > 12) {
                follow_up_Months.setError("Enter up to 12 months");
                return;
            }
            monthSel = String.valueOf(monthselected);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String dateis = sdf.format(RegistrationActivity.addMonth(new Date(), monthselected));
            //Log.e("fmonth", "" + dateis);
            usersellectedDate = dateis;


        }
        String delimiter = ",";
        String[] temp = ailments.split(delimiter);
             /* print substrings */
        for (String aTemp : temp) {
            //System.out.println(temp[i]);
          //  Log.e("log", aTemp);
            if (!new RegistrationActivity().isDuplicate(mAilmemtArrayList, aTemp)) {

                // dbController.addAilment(temp[i]);
                databaseClass.addAilments(aTemp, maxid);
                maxid = maxid + 1;
              //  Log.e("ailment", "" + aTemp);

            }
        }
//  System.out.println("Date1 is before or equal to Date2");

        //  dbController.addPatient(first_name, middle_name, last_name, sex, strdate_of_birth, current_age, phone_number, selectedLanguage, patientImagePath, usersellectedDate, daysSel, fowSel, monthSel, ailments, prescriptionImgPath, clinical_note, sysdate.toString());
        String visit_id = String.valueOf(maxVisitId + 1);
        String visit_date = addedOnDate;
      //  Log.e("visit_date", "" + visit_date);


        //here we need to convert date it to 1-09-2016 date format to get records in sys date filter list
        sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        StringBuilder modified_on = new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append("");
        //Log.d("sysdate", "" + modified_on);


        String flag = "0";
        String added_by = docId;
        String patientInfoType = "App";
        String action = "added";

        //  dbController.updatePatientPersonalforNewVisit(strPatientId, "2", modified_on.toString());//thiis will update pateint data for new visit
        dbController.addPatientNextVisitRecord(visit_id, strPatientId, usersellectedDate, follow_up_dates, daysSel, fowSel, monthSel, clinical_note, PrescriptionimageName, ailments, visit_date, docId, doctor_membership_number, addedOnDate, addedTime, flag, added_by, action, patientInfoType);

        Toast.makeText(getApplicationContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
        //Redirect to navigation Activity
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

    //Image capture code
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
                   /* new RegistrationActivity().customToast("User cancelled image capture");*/
                } else {
                    // failed to capture image
                   /* new RegistrationActivity().customToast("Sorry! Failed to capture image");*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime()+" " +"/ "+"Add Patient" + e);
        }
    }

    //this will show captured image to image view
    private void previewCapturedImage() {
        try {

            String patientImagePath = uriSavedImage.getPath();
            Log.e("path", "" + patientImagePath);
            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {

                setUpGlide(patientImagePath, imageViewprescription);
               /* Glide.with(AddPatientUpdate.this)
                        .load(patientImagePath)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageViewprescription);*/


            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime()+" " +"/ "+"Add Patient"+e);
        }

    }
    //show date sellector dialog box

    private void shpwDialog() {

        switch (AddPatientUpdate.DATE_DIALOG_ID) {
            case DATE_DIALOG_ID:

                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);
                Log.e("mYear2", "age is" + mYear2);
                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);

                Log.d("mYear1", "" + mYear2);

                DatePickerDialog dpd1 = new DatePickerDialog(AddPatientUpdate.this,
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

    private void goToNavigation() {
        Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
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

        if (sqlController != null) {
            sqlController = null;
        }
        if (dbController != null) {
            dbController = null;
        }
        if (databaseClass != null) {
            databaseClass = null;
        }
        if (patientHistoryData != null) {
            patientHistoryData.clear();
            patientHistoryData = null;
        }

        if(appController !=null) {
            appController = null;
        }

        cleanResources();
        System.gc();

    }

    private void cleanResources() {

        patientHistoryData = null;
        sdf1 = null;
        strPhone = null;
        strAge = null;
        strLanguage = null;
        strgender = null;
        strPatientPhoto = null;
        follow_up_date = null;

        ailments1 = null;
        follow_up_days = null;
        follow_up_weeks = null;
        follow_up_Months = null;
        clinicalNotes = null;
        strFirstName = null;
        strMiddleName = null;
        strLastName = null;
        strDob = null;
        strPatientId = null;
        txtfollow_up_date = null;
        fowSel = null;
        usersellectedDate = null;
        monthSel = null;
        sqlController = null;
        mAilmemtArrayList = null;
        imageViewprescription = null;
        doctor_membership_number = null;
        docId = null;
        addedTime = null;
        addedOnDate = null;
        patientHistoryData = null;
        PrescriptionimageName = null;
    }
}
