package app.clirnet.com.clirnetapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.Validator;
import app.clirnet.com.clirnetapp.adapters.EditPatientAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

public class EditPatientUpdate extends AppCompatActivity {

    private final int[] imageArray = {R.drawable.brand, R.drawable.brethnum, R.drawable.deptrim, R.drawable.fenjoy, R.drawable.hapiom, R.drawable.liporev, R.drawable.magnamet, R.drawable.motirest, R.drawable.revituz, R.drawable.suprizon};
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

    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtlowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private BootstrapEditText edtSymptoms;
    private BootstrapEditText edtDignosis;
    private BootstrapEditText edtTest;
    private BootstrapEditText edtDrugs;
    private String strAddress;
    private String strCityorTown;
    private String strDistrict;
    private String strPinNo;
    private String strState;
    private TextView txtRecord;
    private int countvitalsLayout = 1;
    private TextView txtsymtomsanddignost;
    private TextView presciptiontext;
    private int countsymtomsanddignostLayout = 1;
    private int countPrescriptiontLayout = 1;
    private ExpandableListView expListView;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appController = new AppController();
        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e);
        }
        if (databaseClass == null && dbController == null) {
            databaseClass = new DatabaseClass(getApplicationContext());
            dbController = new SQLiteHandler(getApplicationContext());
        }
        if (validator != null) {
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
        String strFollowUpDate = getIntent().getStringExtra("FOD");
        String strActualFollowUpDate = getIntent().getStringExtra("ACTUALFOD");
        String strAilment = getIntent().getStringExtra("AILMENT");
        String strFollowupDays = getIntent().getStringExtra("FOLLOWDAYS");
        String strFollowupWeeks = getIntent().getStringExtra("FOLLOWWEEKS");

        String strFollowupMonth = getIntent().getStringExtra("FOLLOWMONTH");

        String strClinicalNotes = getIntent().getStringExtra("CLINICALNOTES");
        String strPrescriptionImage = getIntent().getStringExtra("PRESCRIPTION");
        strVisitId = getIntent().getStringExtra("VISITID");
        strAddress = getIntent().getStringExtra("ADDRESS");

        strCityorTown = getIntent().getStringExtra("CITYORTOWN");
        strDistrict = getIntent().getStringExtra("DISTRICT");
        strPinNo = getIntent().getStringExtra("PIN");
        strState = getIntent().getStringExtra("STATE");
        String strWeight = getIntent().getStringExtra("WEIGHT");
        String strPulse = getIntent().getStringExtra("PULSE");
        String strBp = getIntent().getStringExtra("BP");
        String strMmhg = getIntent().getStringExtra("LOWBP");
        String strTemprature = getIntent().getStringExtra("TEMPRATURE");
        String strSugar = getIntent().getStringExtra("SUGAR");
        String strSymptoms = getIntent().getStringExtra("SYMPTOMS");
        String strDignosis = getIntent().getStringExtra("DIGNOSIS");
        String strTests = getIntent().getStringExtra("TESTS");
        String strDrugs = getIntent().getStringExtra("DRUGS");

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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        edtInput_weight = (EditText) findViewById(R.id.input_weight);
        edtInput_pulse = (EditText) findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) findViewById(R.id.input_bp);
        edtlowBp = (EditText) findViewById(R.id.lowBp);
        edtInput_temp = (EditText) findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) findViewById(R.id.input_sugar);

        edtSymptoms = (BootstrapEditText) findViewById(R.id.symptoms);
        edtDignosis = (BootstrapEditText) findViewById(R.id.dignosis);
        edtTest = (BootstrapEditText) findViewById(R.id.test);
        edtDrugs = (BootstrapEditText) findViewById(R.id.drugs);

        ailments1 = (MultiAutoCompleteTextView) findViewById(R.id.ailments1);

        clinicalNotes = (EditText) findViewById(R.id.clinicalNotes);
        ImageView imgEdit = (ImageView) findViewById(R.id.editPersonalInfo);
        cancel = (Button) findViewById(R.id.cancel);
        editUpdate = (Button) findViewById(R.id.editUpdate);
        imageViewprescription = (ImageView) findViewById(R.id.imageViewprescription);
        // txtfollow_up_date = (TextView) findViewById(R.id.txtfollow_up_date);

        /*noRecordsText=(TextView)findViewById(R.id.noRecordsText);

        lastUpdateButton=(Button)findViewById(R.id.lastUpdate);
*/

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

        date.setText("Today's Date: " + dd);
        //this date is ued to set update records date in patient history table


        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
        Date todayDate3 = new Date();

        //this date is ued to set update records date in patient history table
        updatedTime = sdf3.format(todayDate3);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(strFollowupDays, "0")) {

                strFollowupDays = "";
            }
        }

        if (strFollowupWeeks == "0") {

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
        ailments1.setText(strAilment);
        clinicalNotes.setText(strClinicalNotes);
        edtInput_weight.setText(strWeight);
        edtInput_pulse.setText(strPulse);
        edtInput_bp.setText(strBp);
        edtlowBp.setText(strMmhg);
        edtInput_temp.setText(strTemprature);
        edtInput_sugar.setText(strSugar);
        edtSymptoms.setText(strSymptoms);
        edtDignosis.setText(strDignosis);
        edtTest.setText(strTests);
        edtDrugs.setText(strDrugs);

        fodtextshow.setText(strActualFollowUpDate);//add selected date to date text view

        if (strFollowupDays != null) {
            inputnumber.setText(strFollowupDays);
            days.setSelected(true);
            days.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            daysSel = strFollowupDays;

        } else if (strFollowupWeeks != null) {
            inputnumber.setText(strFollowupWeeks);
            week.setSelected(true);
            week.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            fowSel = strFollowupWeeks;
        } else if (strFollowupMonth != null) {
            inputnumber.setText(strFollowupMonth);
            month.setSelected(true);
            month.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            monthSel = strFollowupMonth;
        }

       /* if (TextUtils.isEmpty(strFollowupDays) && TextUtils.isEmpty(strFollowupWeeks) && TextUtils.isEmpty(strFollowupMonth)) {
            follow_up_date.setText(strFollowUpDate);
        }*/

        try {
            expListView = (ExpandableListView) findViewById(R.id.lvExp);
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            docId = sqlController.getDoctorId();

            databaseClass.openDataBase();

            ArrayList<RegistrationModel> patientData = (sqlController.getPatientHistoryListAll(strId));



            List<RegistrationModel> filteredpatientData = filterBySystemDate(patientData, justDate);
            Log.e("asize0", "" + filteredpatientData.size());
            int size = filteredpatientData.size();

            if (size > 0) {

                EditPatientAdapter editPatientAdapter = new EditPatientAdapter(EditPatientUpdate.this, filteredpatientData);
                recyclerView.setAdapter(editPatientAdapter);

            } else {
                recyclerView.setVisibility(View.GONE);
                //  noRecordsText.setVisibility(View.VISIBLE);
            }
            int id = databaseClass.getMaxAimId();
            maxid = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog("Edit Patient" + e);
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }

        try {

            mAilmemtArrayList = databaseClass.getAilmentsListNew();
            if (mAilmemtArrayList.size() != 0) {
                ArrayAdapter<String> adp = new ArrayAdapter<>(getBaseContext(),
                        android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                ailments1.setThreshold(1);

                ailments1.setAdapter(adp);
                ailments1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

            }

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog("Edit Patient" + e);

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

                Intent i = new Intent(getApplicationContext(), EditPersonalInfo.class);

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
                i.putExtra("ADDRESS", strAddress);
                i.putExtra("CITYORTOWN", strCityorTown);
                i.putExtra("DISTRICT", strDistrict);
                i.putExtra("PIN", strPinNo);
                i.putExtra("STATE", strState);
                i.putExtra("FROMWHERE", "editpatient");
                startActivity(i);
               // finish();
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
                // frameLayout.setVisibility(View.VISIBLE);
              /*  AppController appController=new AppController();
                appController.goToNavigationActivity(getApplicationContext(),NavigationActivity.class);*/
                //redirect to navigation activity

                goToNavigation();

                // Toast.makeText(getContext(),"chala  jana bhai",Toast.LENGTH_SHORT).show();
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

                imageName = "prescription_" + strFirstName + "_" + strLastName + docId + "_" + appController.getDateTime() + ".png";

                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        setupAnimation();

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
    }

    private void addArrowUpDownListner() {
        txtRecord = (TextView) findViewById(R.id.txtRecord);
        txtsymtomsanddignost = (TextView) findViewById(R.id.txtsymptomsanddignost);
        presciptiontext = (TextView) findViewById(R.id.presciptiontext);
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
                //  txtRecord.setBackground(R.drawable.);
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
                //  txtRecord.setBackground(R.drawable.);
            }
        });

        presciptiontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout symptomsdigosislayout = (LinearLayout) findViewById(R.id.presciptionlayout);
                if (countPrescriptiontLayout == 1) {
                    symptomsdigosislayout.setVisibility(View.GONE);
                    presciptiontext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countPrescriptiontLayout = 2;
                } else {
                    symptomsdigosislayout.setVisibility(View.VISIBLE);
                    presciptiontext.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countPrescriptiontLayout = 1;
                }
                //  txtRecord.setBackground(R.drawable.);
            }
        });
    }


    private void addFollowupdateButtonListner() {
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

    //saved the user enetred data to db
    private void saveData() {
        //l String daysSel = null;
        //  monthSel = null;
        //fowSel = null;
        String strfollow_up_date = null;
        String ailments = ailments1.getText().toString().trim();
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
        String strTests = edtTest.getText().toString().trim();
        String strDrugs = edtDrugs.getText().toString().trim();

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

            if (!new AppController().isDuplicate(mAilmemtArrayList, aTemp)) {

                // dbController.addAilment(temp[i]);
                databaseClass.addAilments(aTemp, maxid);
                maxid = maxid + 1;


            }
        }

        String modified_by = docId;//INSERTING DOC ID IN ADDED BY COLUMN AS PER PUSHPAL SAID
        String flag = "0";

        String patientInfoType = "App";
        String action = "updated";
        try {
            dbController.updatePatientOtherInfo(strId, strVisitId, usersellectedDate, strfollow_up_date, daysSel, fowSel, monthSel, clinical_note, patientImagePath, ailments, sysdate, updatedTime, modified_by, action, patientInfoType, flag,
                    strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs);
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e);
        }

        Toast.makeText(getApplicationContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e);
        }
    }

    //this will show captured image to image view
    private void previewCapturedImage() {
        try {

            patientImagePath = uriSavedImage.getPath();

            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {
                setUpGlide(patientImagePath, imageViewprescription);
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Edit Patient" + e);
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

                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);


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
    //Removes current date data from list and show other data, we dnt want to show curent date data to list.
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
                Log.e(appController.getDateTime() + " " + "/ " + "result", "" + phVisitDate);
            }
        }
        return filteredModelList;
    }

    private void goToNavigation1() {
        this.onBackPressed();
        finish();
        /*Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(i);
        finish();*/
    }

    private void goToNavigation() {
        this.onBackPressed();
        //finish();
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

        if (appController != null) {
            appController = null;
        }

        sdf1 = null;

        cleanResources();
        System.gc();

    }

    private void cleanResources() {
        ailments1 = null;
        strPhone = null;
        strAge = null;
        strLanguage = null;
        strgender = null;
        strPatientPhoto = null;
        txtfollow_up_date = null;

        clinicalNotes = null;
        strFirstName = null;
        strMiddleName = null;
        strLastName = null;
        strDob = null;
        strId = null;
        txtfollow_up_date = null;
        fowSel = null;
        usersellectedDate = null;
        monthSel = null;
        imageName = null;
        imageIntent = null;
        imagesFolder = null;
        uriSavedImage = null;
        patientImagePath = null;
        sdf1 = null;
        updatedTime = null;
        sysdate = null;
        noRecordsText = null;
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
        edtTest = null;
        edtDrugs = null;
        txtRecord = null;
        txtsymtomsanddignost = null;
        presciptiontext = null;


        if (mAilmemtArrayList != null) {
            mAilmemtArrayList = null;
        }
    }

}
