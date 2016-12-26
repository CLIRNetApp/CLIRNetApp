package app.clirnet.com.clirnetapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.Validator;
import app.clirnet.com.clirnetapp.adapters.AddPatientUpdateAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


@SuppressWarnings("AccessStaticViaInstance")
public class AddPatientUpdate extends AppCompatActivity {

    private final int[] imageArray = {R.drawable.brand, R.drawable.brethnum, R.drawable.deptrim, R.drawable.fenjoy, R.drawable.hapiom, R.drawable.liporev, R.drawable.magnamet, R.drawable.motirest, R.drawable.revituz, R.drawable.suprizon};
    private ImageView backChangingImages;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID = 0;
    private String strPhone;
    private String strAge;
    private String strLanguage;
    private String strgender;
    private String strPatientPhoto;
    private MultiAutoCompleteTextView ailments1;

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
    private Validator validator;
    private String strCityorTown;
    private String strDistrict;
    private String strPinNo;
    private String strState;
    private String strAddress;

    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtLowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private BootstrapEditText edtSymptoms;
    private BootstrapEditText edtDignosis;
    private BootstrapEditText edtTest;
    private BootstrapEditText edtDrugs;
    private BootstrapEditText fodtextshow;
    private Button days;
    private Button week;
    private Button month;
    private BootstrapEditText inputnumber;
    private String value;
    private String buttonSelected;
    private String daysSel;
    private TextView txtaddress;
    private TextView txtRecord;
    private TextView txtsymtomsanddignost;
    private TextView presciptiontext;

    private int countvitalsLayout = 1;
    private int countsymtomsanddignostLayout = 1;
    private int countPrescriptiontLayout = 1;
    private int countAddressLayout = 1;
    private EditText visitDate;


    @SuppressLint({"SimpleDateFormat", "SetTValidatorextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appController = new AppController();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient" + e);
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

        strAddress = getIntent().getStringExtra("ADDRESS");
        strCityorTown = getIntent().getStringExtra("CITYORTOWN");
        strDistrict = getIntent().getStringExtra("DISTRICT");
        strPinNo = getIntent().getStringExtra("PIN");
        strState = getIntent().getStringExtra("STATE");
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
        ImageView imgEdit = (ImageView) findViewById(R.id.editPersonalInfo);
        cancel = (Button) findViewById(R.id.cancel);
        addUpdate = (Button) findViewById(R.id.addUpdate);
        txtRecord = (TextView) findViewById(R.id.txtRecord);
        txtsymtomsanddignost = (TextView) findViewById(R.id.txtsymptomsanddignost);
        presciptiontext = (TextView) findViewById(R.id.presciptiontext);
        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fodtextshow = (BootstrapEditText) findViewById(R.id.fodtextshow);
        inputnumber = (BootstrapEditText) findViewById(R.id.inputnumber);
        days = (Button) findViewById(R.id.days);
        week = (Button) findViewById(R.id.week);
        month = (Button) findViewById(R.id.month);

        visitDate=(EditText)findViewById(R.id.visitDate);

        edtInput_weight = (EditText) findViewById(R.id.input_weight);
        edtInput_pulse = (EditText) findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) findViewById(R.id.input_bp);
        edtLowBp = (EditText) findViewById(R.id.lowBp);
        edtInput_temp = (EditText) findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) findViewById(R.id.input_sugar);

        edtSymptoms = (BootstrapEditText) findViewById(R.id.symptoms);
        edtDignosis = (BootstrapEditText) findViewById(R.id.dignosis);
        edtTest = (BootstrapEditText) findViewById(R.id.test);
        edtDrugs = (BootstrapEditText) findViewById(R.id.drugs);

        Button editlastUpdate = (Button) findViewById(R.id.editlastUpdate);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();

        Glide.get(AddPatientUpdate.this).clearMemory();
        recyclerView.setLayoutParams(params);

        addFollowupdateButtonListner();


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

        if (validator != null) {
            validator = new Validator(getApplicationContext());
        }

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
         visitDate.setText(addedOnDate);

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
                AddPatientUpdateAdapter pha = new AddPatientUpdateAdapter(AddPatientUpdate.this,patientHistoryData);
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
            mAilmemtArrayList = databaseClass.getAilmentsListNew();

            if (mAilmemtArrayList.size() > 0) {

                //this code is for setting list to auto complete text view  8/6/16

                ArrayAdapter<String> adp = new ArrayAdapter<>(AddPatientUpdate.this,
                        android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                ailments1.setThreshold(1);

                ailments1.setAdapter(adp);
                ailments1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
            int id = databaseClass.getMaxAimId();
            maxid = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient" + e);
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

       /* if (strPrescriptionImage != null && !TextUtils.isEmpty(strPrescriptionImage)) {
            if (strPrescriptionImage.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);

                setUpGlide(strPrescriptionImage, imageViewprescription);
               *//* Glide.with(AddPatientUpdate.this)
                        .load(strPrescriptionImage)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.main_profile)
                        .into(imageViewprescription);*//*

                //  patientImage.setImageBitmap(bitmap);
            }
        }*/

        addPatientprescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                PrescriptionimageName = "prescription_" + docId + "_" + appController.getDateTime() + ".jpg";

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
                i.putExtra("ADDRESS", strAddress);
                i.putExtra("CITYORTOWN", strCityorTown);
                i.putExtra("DISTRICT", strDistrict);
                i.putExtra("PIN", strPinNo);
                i.putExtra("STATE", strState);
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

//save the data to db
        addUpdate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));
                    saveData();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
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

        setupAnimation();//change the banner image after 10  sec interval.
    }

    private void addFollowupdateButtonListner() {

        txtRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout vitalsLayout = (LinearLayout) findViewById(R.id.vitalsLayout);
                if (countvitalsLayout == 1) {
                    vitalsLayout.setVisibility(View.VISIBLE);
                    txtRecord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0);//set drawable right to text view
                    countvitalsLayout = 2;
                } else {
                    vitalsLayout.setVisibility(View.GONE);
                    txtRecord.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countvitalsLayout = 1;
                }
                //  txtRecord.setBackground(R.drawable.);
            }
        });
        txtsymtomsanddignost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout symptomsdigosislayout = (LinearLayout) findViewById(R.id.symptomsdigosislayout);
                if (countsymtomsanddignostLayout == 1) {
                    symptomsdigosislayout.setVisibility(View.VISIBLE);
                    txtsymtomsanddignost.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countsymtomsanddignostLayout = 2;
                } else {
                    symptomsdigosislayout.setVisibility(View.GONE);
                    txtsymtomsanddignost.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countsymtomsanddignostLayout = 1;
                }
                //  txtRecord.setBackground(R.drawable.);
            }
        });

        presciptiontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout symptomsdigosislayout = (LinearLayout) findViewById(R.id.presciptionlayout);
                if (countPrescriptiontLayout == 1) {
                    symptomsdigosislayout.setVisibility(View.VISIBLE);
                    presciptiontext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countPrescriptiontLayout = 2;
                } else {
                    symptomsdigosislayout.setVisibility(View.GONE);
                    presciptiontext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countPrescriptiontLayout = 1;
                }
                //  txtRecord.setBackground(R.drawable.);
            }
        });


        days.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    days.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        week.setBackground(getResources().getDrawable(R.drawable.circle));
                        month.setBackground(getResources().getDrawable(R.drawable.circle));
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
                    String dateis = sdf1.format(appController.addDay1(new Date(), val));
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    days.setBackground(getResources().getDrawable(R.drawable.circle));
                    month.setBackground(getResources().getDrawable(R.drawable.circle));
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
                String dateis = sdf1.format(appController.addDay1(new Date(), fVal));
                usersellectedDate = dateis;
                fowSel = value;
                fodtextshow.setText(dateis);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    days.setBackground(getResources().getDrawable(R.drawable.circle));
                    week.setBackground(getResources().getDrawable(R.drawable.circle));
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
                String dateis = sdf1.format(appController.addMonth(new Date(), Integer.parseInt(value)));
                usersellectedDate = dateis;
                monthSel = value;
                fodtextshow.setText(dateis);
            }
        });
    }

    private void initalizeView() {

        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        ailments1 = (MultiAutoCompleteTextView) findViewById(R.id.ailments1);
        clinicalNotes = (EditText) findViewById(R.id.clinicalNotes);

        imageViewprescription = (ImageView) findViewById(R.id.imageViewprescription);
       // txtfollow_up_date = (TextView) findViewById(R.id.txtfollow_up_date);


    }

    //this will send data to EditPatientUpdate Activity
    private void sendDataToEditPatientUpdate() {

        Intent i = new Intent(AddPatientUpdate.this, EditPatientUpdate.class);

        i.putExtra("PATIENTPHOTO", patientHistoryData.get(0).getPhoto());
        i.putExtra("ID", patientHistoryData.get(0).getPat_id());


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

       // String daysSel = null;
        monthSel = null;
        fowSel = null;
        String ailments = ailments1.getText().toString().trim();
        String clinical_note = clinicalNotes.getText().toString().trim();
        //String follow_up_dates = follow_up_date.getText().toString().trim();
        String follow_up_dates = null;

        String strWeight = edtInput_weight.getText().toString().trim();
        String strPulse = edtInput_pulse.getText().toString().trim();
        String strBp = edtInput_bp.getText().toString().trim();
        String strLowBp = edtLowBp.getText().toString().trim();
        String strTemp = edtInput_temp.getText().toString().trim();
        String strSugar = edtInput_sugar.getText().toString().trim();
        String strSymptoms = edtSymptoms.getText().toString().trim();
        String strDignosis = edtDignosis.getText().toString().trim();
        String strTests = edtTest.getText().toString().trim();
        String strDrugs = edtDrugs.getText().toString().trim();


        String followupdateSellected;

        if (TextUtils.isEmpty(ailments)) {
            ailments1.setError("Please enter Ailment");
            return;
        } else {
            ailments1.setError(null);
        }
        if (ailments.length() > 0 && ailments.length() < 2 && ailments.contains(",")) {
            ailments1.setError("Please Enter Valid ailment");
            return;
        }

        //remove comma occurance from string
        ailments = appController.removeCommaOccurance(ailments);
        //Remove spaces between text if more than 2 white spaces found 12-12-2016
        ailments = ailments.replaceAll("\\s+", " ");


        Boolean ailmentValue = false;

        if (ailments.length() > 0) {
            ailmentValue = appController.findNumbersAilment(ailments);
            Log.e("ailmentValue", "" + ailmentValue);
            if (ailmentValue) {
                ailments1.setError("Please Enter Valid ailment");
                return;
            }
        }


        String delimiter = ",";
        String[] temp = ailments.split(delimiter);
             /* print substrings */
        for (String aTemp : temp) {
            //System.out.println(temp[i]);
            //  Log.e("log", aTemp);
            if (!new AppController().isDuplicate(mAilmemtArrayList, aTemp)) {

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

        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
       // String visit_date1 = addedOnDate.toString();
        String added_on = visitDate.getText().toString();

        try {
            //convert visit date from 2016-11-1 to 2016-11-01
            visit_date = myFormat.format(fromUser.parse(added_on));
            added_on = myFormat.format(fromUser.parse(addedOnDate));
            Log.e("reformattedStrqq", "" + visit_date);

        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e);
        }


        String flag = "0";
        String added_by = docId;
        String patientInfoType = "App";
        String action = "added";

        //  dbController.updatePatientPersonalforNewVisit(strPatientId, "2", modified_on.toString());//thiis will update pateint data for new visit
        dbController.addPatientNextVisitRecord(visit_id, strPatientId, usersellectedDate, follow_up_dates, daysSel, fowSel, monthSel, clinical_note, PrescriptionimageName, ailments, visit_date, docId, doctor_membership_number, added_on, addedTime, flag, added_by, action, patientInfoType,
                strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs);

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

                    //to_do check this for null poinetr exception

                    previewCapturedImage(); //

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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e);
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e);
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

        if (appController != null) {
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
        ailments1 = null;
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
