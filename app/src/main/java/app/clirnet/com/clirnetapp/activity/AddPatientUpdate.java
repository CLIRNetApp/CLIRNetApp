package app.clirnet.com.clirnetapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.AddPatientUpdateAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.Validator;

import static app.clirnet.com.clirnetapp.R.id.sysdate;


@SuppressWarnings("AccessStaticViaInstance")
public class AddPatientUpdate extends AppCompatActivity {


    private ImageView backChangingImages;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int DATE_DIALOG_ID2 = 2;
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
    private String fowSel = null;
    private String usersellectedDate;
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
    private MultiAutoCompleteTextView edtSymptoms;
    private MultiAutoCompleteTextView edtDignosis;
    private BootstrapEditText fodtextshow;
    private Button days;
    private Button week;
    private Button month;
    private BootstrapEditText inputnumber;
    private String value;
    private String buttonSelected;
    private String daysSel;
    private TextView txtRecord;
    private TextView txtsymtomsanddignost;

    private int countvitalsLayout = 1;
    private int countsymtomsanddignostLayout = 1;
    private int countAddressLayout = 1;
    private EditText visitDate;

    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private LastnameDatabaseClass lastNamedb;
    private ArrayList<String> mSymptomsList;
    private EditText edtInput_sugarfasting;
    private EditText edtInput_bmi;
    private EditText edtInput_height;
    private Button btnclear;
    private String strAlternatenumber;
    private String strAlternatephtype;
    private String strIsd_code;
    private String strAlternateIsd_code;
    private String patientImagePath;
    private String prescriptionImgPath;
    private HashMap<String, String> NameData;
    private ArrayList<String> specialityArray;
    private int addCounter = 0;
    private String strReferedTo;
    private String strRederedBy;
    private ArrayList<String> mDiagnosisList;
    private String struid;
    private String strEmail;
    private String url;


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
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        if (databaseClass == null) {
            databaseClass =new DatabaseClass(getApplicationContext());
        }
        if (lastNamedb == null) {
            lastNamedb = new LastnameDatabaseClass(getApplicationContext());
        }


        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");

        String strName = getIntent().getStringExtra("NAME");
        strPatientId = getIntent().getStringExtra("PatientID");

        strFirstName = getIntent().getStringExtra("FIRSTTNAME");
        strMiddleName = getIntent().getStringExtra("MIDDLENAME");
        strLastName = getIntent().getStringExtra("LASTNAME");
        strPhone = getIntent().getStringExtra("PHONE");
        strAge = getIntent().getStringExtra("AGE");
        strDob = getIntent().getStringExtra("DOB");
        strLanguage = getIntent().getStringExtra("LANGUAGE");
        strgender = getIntent().getStringExtra("GENDER");

        strAddress = getIntent().getStringExtra("ADDRESS");
        strCityorTown = getIntent().getStringExtra("CITYORTOWN");
        strDistrict = getIntent().getStringExtra("DISTRICT");
        strPinNo = getIntent().getStringExtra("PIN");
        strState = getIntent().getStringExtra("STATE");

        strIsd_code = getIntent().getStringExtra("ISDCODE");
        strAlternateIsd_code = getIntent().getStringExtra("ALTERNATEISDCODE");

        strAlternatenumber = getIntent().getStringExtra("ALTERNATENUMBER");

        strAlternatephtype = getIntent().getStringExtra("ALTERNATENUMBERTYPE");
        struid = getIntent().getStringExtra("UID");
        strEmail = getIntent().getStringExtra("EMAIL");

        //Initalize global view to this method 3-11-2016
        initalizeView();

        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
        TextView date = (TextView) findViewById(sysdate);
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
        //presciptiontext = (TextView) findViewById(R.id.presciptiontext);
        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fodtextshow = (BootstrapEditText) findViewById(R.id.fodtextshow);
        inputnumber = (BootstrapEditText) findViewById(R.id.inputnumber);
        days = (Button) findViewById(R.id.days);
        week = (Button) findViewById(R.id.week);
        month = (Button) findViewById(R.id.month);
        btnclear = (Button) findViewById(R.id.btnclear);

        visitDate = (EditText) findViewById(R.id.visitDate);

        edtInput_weight = (EditText) findViewById(R.id.input_weight);
        edtInput_pulse = (EditText) findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) findViewById(R.id.input_bp);
        edtLowBp = (EditText) findViewById(R.id.lowBp);
        edtInput_temp = (EditText) findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) findViewById(R.id.input_sugar);
        edtInput_sugarfasting = (EditText) findViewById(R.id.input_sugarfasting);
        edtInput_bmi = (EditText) findViewById(R.id.input_bmi);
        edtInput_height = (EditText) findViewById(R.id.input_height);

        edtSymptoms = (MultiAutoCompleteTextView) findViewById(R.id.symptoms);
        edtDignosis = (MultiAutoCompleteTextView) findViewById(R.id.dignosis);


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

        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        date.setText("Today's Date " + dd);
        //this date is ued to set update records date in patient history table
        // String current_date = sdf.format(todayDate);


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
            dbController = SQLiteHandler.getInstance(getApplicationContext());
            //This will get all the visit  history of patient
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            docId = sqlController.getDoctorId();
            maxVisitId = sqlController.getPatientVisitIdCount();
            patientHistoryData = (sqlController.getPatientHistoryListAll1(strPatientId)); //get all patient data from db
            int size = patientHistoryData.size();

            if (size > 0) {
                //this will set adapter to list to show all the patient history
                AddPatientUpdateAdapter pha = new AddPatientUpdateAdapter(AddPatientUpdate.this, patientHistoryData);
                recyclerView.setAdapter(pha);

            }

            if (bannerClass == null) {
                bannerClass = new BannerClass(getApplicationContext());
            }
            bannerimgNames = bannerClass.getImageName();
            //  Log.e("ListimgNames", "" + bannerimgNames.size() + "  " + bannerimgNames.get(1));
        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
        }


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
            appController.appendLog(appController.getDateTime() + "" + "/" + "AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {

            if (databaseClass != null) {
                databaseClass.close();
            }
        }

        try {
            mSymptomsList = lastNamedb.getSymptoms();
            if (mSymptomsList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(AddPatientUpdate.this,
                        android.R.layout.simple_dropdown_item_1line, mSymptomsList);

                edtSymptoms.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtSymptoms.setThreshold(1);
                edtSymptoms.setAdapter(lastnamespin);
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        try {
            mDiagnosisList = lastNamedb.getDiagnosis();
            if (mDiagnosisList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(AddPatientUpdate.this,
                        android.R.layout.simple_dropdown_item_1line, mDiagnosisList);

                edtDignosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtDignosis.setThreshold(1);
                edtDignosis.setAdapter(lastnamespin);
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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

            }
        }

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
                i.putExtra("ALTERNATENUMBER", strAlternatenumber);
                i.putExtra("ALTERNATENUMBERTYPE", strAlternatephtype);
                i.putExtra("ISDCODE", strIsd_code);
                i.putExtra("ALTERNATEISDCODE", strAlternateIsd_code);
                i.putExtra("UID", struid);
                i.putExtra("EMAIL", strEmail);
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

                    saveData();//saved data int db

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
                goToNavigation1();

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
        Button refered = (Button) findViewById(R.id.referedby);
        refered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReferedDialogBox();
            }
        });

        setupAnimation();//change the banner image after 10  sec interval.
    }

    private void initalizeView() {

        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        ailments1 = (MultiAutoCompleteTextView) findViewById(R.id.ailments1);
        clinicalNotes = (EditText) findViewById(R.id.clinicalNotes);

        imageViewprescription = (ImageView) findViewById(R.id.imageViewprescription);
        // txtfollow_up_date = (TextView) findViewById(R.id.txtfollow_up_date);


    }

    private void showReferedDialogBox() {

        final Dialog dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan));

        LayoutInflater factory = LayoutInflater.from(AddPatientUpdate.this);
        final View f = factory.inflate(R.layout.refered_by_dialog, null);

        dialog.setTitle("Refered By-To");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);
        Button dialogButtonCancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) f.findViewById(R.id.customDialogOk);
        final Button addMore = (Button) f.findViewById(R.id.addMore);
        final AutoCompleteTextView nameRefredBy = (AutoCompleteTextView) f.findViewById(R.id.nameRefredBy);
        final AutoCompleteTextView nameReferedTo1 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo1);
        final AutoCompleteTextView nameReferedTo2 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo2);
        final AutoCompleteTextView nameReferedTo3 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo3);
        final AutoCompleteTextView nameReferedTo4 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo4);
        final AutoCompleteTextView nameReferedTo5 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo5);

        specialityArray = new ArrayList<>();

        try {
            final ArrayList<HashMap<String, String>> list = sqlController.getAllDataAssociateMaster();

            NameData = new HashMap<>();

            for (int im = 0; im < list.size(); im++) {
                String strid = list.get(im).get("ID");
                String strName = list.get(im).get("NAME");
                String str = list.get(im).get("SPECIALITY");
                specialityArray.add(str);
                NameData.put(strName, strid);
            }

            setCities(NameData.keySet().toArray(
                    new String[0]), f);

        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient Update" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
                    String refredToName1 = nameReferedTo1.getText().toString();
                    if (!refredToName1.equals("") && refredToName1.length() > 0) {
                        code = NameData.get(refredToName1);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(index);
                            sbname.append(refredToName1);
                        }
                    }

                }
                if (addCounter >= 1) {
                    String refredToName2 = nameReferedTo2.getText().toString();
                    if (!refredToName2.equals("") && refredToName2.length() > 0) {
                        code = NameData.get(refredToName2);
                        if (code != null) {

                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(refredToName2);
                        }
                    }
                }
                if (addCounter >= 2) {
                    String refredToName3 = nameReferedTo3.getText().toString();
                    if (!refredToName3.equals("") && refredToName3.length() > 0) {
                        code = NameData.get(refredToName3);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(refredToName3);
                        }
                    }
                }
                if (addCounter >= 3) {
                    String refredToName4 = nameReferedTo4.getText().toString();
                    if (!refredToName4.equals("") && refredToName4.length() > 0) {
                        code = NameData.get(refredToName4);
                        if (code != null) {

                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(refredToName4);
                        }
                    }
                }
                if (addCounter >= 4) {
                    String refredToName5 = nameReferedTo5.getText().toString();
                    if (!refredToName5.equals("") && refredToName5.length() > 0) {
                        code = NameData.get(refredToName5);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(refredToName5);
                        }
                    }
                }

                strReferedTo = String.valueOf(sb);
                String strRederedBy1 = nameRefredBy.getText().toString();

                if (!strRederedBy1.equals("") && strRederedBy1.length() > 0) {
                    code = NameData.get(strRederedBy1);
                    if (code != null) {
                        int index = Integer.parseInt(code);
                        strRederedBy = String.valueOf(index);
                    }

                }

                strReferedTo = String.valueOf(sb);
                String insertedName = String.valueOf(sbname);
                TextView textRefredByShow = (TextView) findViewById(R.id.txtrefredby);
                TextView textRefredToShow = (TextView) findViewById(R.id.txtrefredto);
                textRefredByShow.setText(strRederedBy + "");
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

    private void setCities(String cData[], View f) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, cData);

        final AutoCompleteTextView nameRefredBy = (AutoCompleteTextView) f.findViewById(R.id.nameRefredBy);
        final AutoCompleteTextView nameReferedTo1 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo1);
        final AutoCompleteTextView nameReferedTo2 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo2);
        final AutoCompleteTextView nameReferedTo3 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo3);
        final AutoCompleteTextView nameReferedTo4 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo4);
        final AutoCompleteTextView nameReferedTo5 = (AutoCompleteTextView) f.findViewById(R.id.nameRefredTo5);

        nameRefredBy.setThreshold(1);
        nameRefredBy.setAdapter(adapter);
        nameReferedTo1.setThreshold(1);
        nameReferedTo1.setAdapter(adapter);
        nameReferedTo2.setThreshold(1);
        nameReferedTo2.setAdapter(adapter);
        nameReferedTo3.setThreshold(1);
        nameReferedTo3.setAdapter(adapter);
        nameReferedTo4.setThreshold(1);
        nameReferedTo4.setAdapter(adapter);
        nameReferedTo5.setThreshold(1);
        nameReferedTo5.setAdapter(adapter);

        final TextView refredtoSpeciality1 = (TextView) f.findViewById(R.id.refredtoSpeciality1);
        final TextView refredtoSpeciality2 = (TextView) f.findViewById(R.id.refredtoSpeciality2);
        final TextView refredtoSpeciality3 = (TextView) f.findViewById(R.id.refredtoSpeciality3);
        final TextView refredtoSpeciality4 = (TextView) f.findViewById(R.id.refredtoSpeciality4);
        final TextView refredtoSpeciality5 = (TextView) f.findViewById(R.id.refredtoSpeciality5);
        final TextView refredBySpeciality = (TextView) f.findViewById(R.id.refredBySpeciality);


        nameRefredBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            String val = null;

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {

                    val = nameRefredBy.getText() + "";
                    int index = 0;
                    String strSpecialty = null;
                    String code = null;
                    if (!val.equals("") && val.length() > 0) {
                        code = NameData.get(val);
                        if (code != null) {
                            index = Integer.parseInt(code);
                        }
                    }
                    if (index == 0) {
                        //  strSpecialty=specialityArray.get(index);
                    } else {
                        strSpecialty = specialityArray.get(index - 1);
                    }
                    if (val == null) {
                        nameRefredBy.setError("Invalid Entry");
                    } else {
                        refredBySpeciality.setVisibility(View.VISIBLE);
                        refredBySpeciality.setText(strSpecialty);
                        // strRederedBy = String.valueOf(index);
                    }
                    if (val == null || code == null) {
                        strRederedBy = null;
                    }
                }
            }
        });

        nameReferedTo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String val = nameReferedTo1.getText() + "";
                    int index = 0;
                    String strSpecialty = null;
                    String code = null;
                    if (!val.equals("") && val.length() > 0) {
                        code = NameData.get(val);
                        if (code != null) {
                            index = Integer.parseInt(code);
                        }
                    }
                    if (index == 0) {
                        //  strSpecialty=specialityArray.get(index);
                    } else {
                        strSpecialty = specialityArray.get(index - 1);
                    }
                    refredtoSpeciality1.setVisibility(View.VISIBLE);
                    refredtoSpeciality1.setText(strSpecialty);
                    // sb.append(index);

                    if (code == null) {
                        nameReferedTo1.setError("Invalid Entry");
                    }
                }
            }
        });
        nameReferedTo2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String val = nameReferedTo2.getText() + "";
                    int index = 0;
                    String strSpecialty = null;
                    String code = null;
                    if (!val.equals("") && val.length() > 0) {
                        code = NameData.get(val);
                        if (code != null) {
                            index = Integer.parseInt(code);
                        }
                    }
                    if (index == 0) {
                        //  strSpecialty=specialityArray.get(index);
                    } else {
                        strSpecialty = specialityArray.get(index - 1);
                    }
                    refredtoSpeciality2.setVisibility(View.VISIBLE);
                    refredtoSpeciality2.setText(strSpecialty);
                    //   sb.append(",").append(index);
                    if (code == null) {
                        nameReferedTo2.setError("Invalid Entry");
                    }
                }
            }
        });
        nameReferedTo3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String val = nameReferedTo3.getText() + "";
                    int index = 0;
                    String strSpecialty = null;
                    String code = null;
                    if (!val.equals("") && val.length() > 0) {
                        code = NameData.get(val);
                        if (code != null) {
                            index = Integer.parseInt(code);
                        }
                    }
                    if (index == 0) {
                        //  strSpecialty=specialityArray.get(index);
                    } else {
                        strSpecialty = specialityArray.get(index - 1);
                    }
                    refredtoSpeciality3.setVisibility(View.VISIBLE);
                    refredtoSpeciality3.setText(strSpecialty);
                    //sb.append(",").append(index);
                    if (code == null) {
                        nameReferedTo3.setError("Invalid Entry");
                    }
                }
            }
        });
        nameReferedTo4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String val = nameReferedTo4.getText() + "";
                    int index = 0;
                    String strSpecialty = null;
                    String code = null;
                    if (!val.equals("") && val.length() > 0) {
                        code = NameData.get(val);
                        if (code != null) {
                            index = Integer.parseInt(code);
                        }
                    }
                    if (index == 0) {
                        //  strSpecialty=specialityArray.get(index);
                    } else {
                        strSpecialty = specialityArray.get(index - 1);
                    }
                    refredtoSpeciality4.setVisibility(View.VISIBLE);
                    refredtoSpeciality4.setText(strSpecialty);
                    // sb.append(",").append(index);
                    if (code == null) {
                        nameReferedTo2.setError("Invalid Entry");
                    }
                }
            }
        });
        nameReferedTo5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String val = nameReferedTo5.getText() + "";
                    int index = 0;
                    String strSpecialty = null;
                    String code = null;
                    if (!val.equals("") && val.length() > 0) {
                        code = NameData.get(val);
                        if (code != null) {
                            index = Integer.parseInt(code);
                        }
                    }
                    if (index == 0) {
                        //  strSpecialty=specialityArray.get(index);
                    } else {
                        strSpecialty = specialityArray.get(index - 1);
                    }
                    refredtoSpeciality5.setVisibility(View.VISIBLE);
                    refredtoSpeciality5.setText(strSpecialty);
                    // sb.append(",").append(index);
                    if (code == null) {
                        nameReferedTo5.setError("Invalid Entry");
                    }
                }
            }
        });
    }

    //this will send data to EditPatientUpdate Activity
    private void sendDataToEditPatientUpdate() {

        if (patientHistoryData.size() > 0) {
            RegistrationModel registrationModel = patientHistoryData.get(0);

            Intent i = new Intent(getApplicationContext(), EditPatientUpdate.class);

            i.putExtra("PATIENTPHOTO", registrationModel.getPhoto());
            i.putExtra("ID", registrationModel.getPat_id());
            i.putExtra("NAME", registrationModel.getFirstName() + " " + registrationModel.getLastName());
            i.putExtra("FIRSTTNAME", registrationModel.getFirstName());
            i.putExtra("MIDDLENAME", registrationModel.getMiddleName());
            i.putExtra("LASTNAME", registrationModel.getLastName());
            i.putExtra("DOB", registrationModel.getDob());
            i.putExtra("PHONE", registrationModel.getMobileNumber());
            i.putExtra("AGE", registrationModel.getAge());
            i.putExtra("LANGUAGE", registrationModel.getLanguage());
            i.putExtra("GENDER", registrationModel.getGender());
            i.putExtra("ACTUALFOD", registrationModel.getActualFollowupDate());
            i.putExtra("FOD", registrationModel.getFollowUpDate());
            i.putExtra("AILMENT", registrationModel.getAilments());
            i.putExtra("FOLLOWDAYS", registrationModel.getFollowUpdays());
            i.putExtra("FOLLOWWEEKS", registrationModel.getFollowUpWeek());
            i.putExtra("FOLLOWMONTH", registrationModel.getFollowUpMonth());
            i.putExtra("CLINICALNOTES", registrationModel.getClinicalNotes());
            i.putExtra("PRESCRIPTION", registrationModel.getPres_img());
            i.putExtra("VISITID", registrationModel.getKey_visit_id());

            i.putExtra("ADDRESS", registrationModel.getAddress());
            i.putExtra("CITYORTOWN", registrationModel.getCityortown());
            i.putExtra("DISTRICT", registrationModel.getDistrict());
            i.putExtra("PIN", registrationModel.getPin_code());
            i.putExtra("STATE", registrationModel.getState());
            i.putExtra("WEIGHT", registrationModel.getWeight());
            i.putExtra("PULSE", registrationModel.getPulse());
            i.putExtra("BP", registrationModel.getBp());
            i.putExtra("LOWBP", registrationModel.getlowBp());
            i.putExtra("TEMPRATURE", registrationModel.getTemprature());
            i.putExtra("SUGAR", registrationModel.getSugar());
            i.putExtra("SYMPTOMS", registrationModel.getSymptoms());
            i.putExtra("DIGNOSIS", registrationModel.getDignosis());
            i.putExtra("TESTS", registrationModel.getTests());
            i.putExtra("DRUGS", registrationModel.getDrugs());
            i.putExtra("BMI", registrationModel.getBmi());
            i.putExtra("ALTERNATENUMBER", registrationModel.getAlternatePhoneNumber());
            i.putExtra("ALTERNATENUMBERTYPE", registrationModel.getAlternatePhoneType());
            i.putExtra("HEIGHT", registrationModel.getHeight());
            i.putExtra("SUGARFASTING", registrationModel.getSugarFasting());
            i.putExtra("UID", registrationModel.getUid());
            i.putExtra("EMAIL", registrationModel.getEmail());
            startActivity(i);
        }
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
        String strTests = null;
        String strDrugs = null;

        String strHeight = edtInput_height.getText().toString().trim();
        String strbmi = edtInput_bmi.getText().toString().trim();
        String strSugarFasting = edtInput_sugarfasting.getText().toString().trim();


        usersellectedDate = fodtextshow.getText().toString();

        if (TextUtils.isEmpty(ailments) && TextUtils.isEmpty(strSymptoms) && TextUtils.isEmpty(strDignosis)) {
            Toast.makeText(getApplicationContext(), "Please enter any of Symptoms or Diagnosis ", Toast.LENGTH_LONG).show();
            // ailment.setError("Please enter Ailment");
            return;
        }

        if (strTemp.length() > 0) {
            int intTemp = Integer.parseInt(strTemp);
            if (intTemp > 110) {
                edtInput_temp.setError(" Temp can not be more than 110 ");
                return;
            } else {
                edtInput_temp.setError(null);
            }
        }


        String visit_id = String.valueOf(maxVisitId + 1);
        String visit_date = addedOnDate;


        //here we need to convert date it to 1-09-2016 date format to get records in sys date filter list
        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        String added_on = visitDate.getText().toString();
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

        try {
            //convert visit date from 2016-11-1 to 2016-11-01
            visit_date = myFormat.format(fromUser.parse(added_on));
            added_on = myFormat.format(fromUser.parse(addedOnDate));

            if (usersellectedDate != null && !usersellectedDate.equals("")) {
                usersellectedDate = myFormat.format(fromUser.parse(usersellectedDate));
            }

        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        String flag = "0";
        String added_by = docId;
        String patientInfoType = "App";
        String action = "added";

        dbController.addPatientNextVisitRecord(visit_id, strPatientId, usersellectedDate, follow_up_dates, daysSel, fowSel, monthSel, clinical_note, prescriptionImgPath, ailments, visit_date, docId, doctor_membership_number, added_on, addedTime, flag, added_by, action, patientInfoType,
                strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs, strHeight, strbmi, strSugarFasting, strRederedBy, strReferedTo);

        Toast.makeText(getApplicationContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
        //Redirect to navigation Activity
        goToNavigation();

    }


    //this will used to change banner image after some time interval
    private void setupAnimation() {

        try {
            if (bannerimgNames.size() > 0) {
                Random r = new Random();
                int n = r.nextInt(bannerimgNames.size());

                url = bannerimgNames.get(n);

                if (AppController.checkifImageExists(url)) {

                    url = bannerimgNames.get(n);
                    BitmapDrawable d = new BitmapDrawable(getResources(), "sdcard/BannerImages/" + url + ".png"); // path is ur resultant //image

                    //Log.e("BitmapDrawable", "" + d);
                    backChangingImages.setImageDrawable(d);

                    backChangingImages.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String action = "clicked";

                            appController.showAdDialog(AddPatientUpdate.this, url);
                            appController.saveBannerDataIntoDb(url, AddPatientUpdate.this, doctor_membership_number, action, "Add Patient Update");
                        }
                    });
                    String action = "display";
                    appController.saveBannerDataIntoDb(url, AddPatientUpdate.this, doctor_membership_number, action, "Add Patient Update");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Image capture code
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view

                    previewCapturedImage();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    //this will show captured image to image view
    private void previewCapturedImage() {
        try {
            prescriptionImgPath = uriSavedImage.getPath();
            TextView edtprescriptionImgPath = (TextView) findViewById(R.id.prescriptionImgPath);
            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(uriSavedImage.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        try {

            patientImagePath = uriSavedImage.getPath();

            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {

                //setUpGlide(patientImagePath, imageViewprescription);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "AddPatientUpdate" + e);
        }

    }

    private void goToNavigation() {
        Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(i);
        finish();
    }


    private void goToNavigation1() {
        this.onBackPressed();
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


    private void shpwDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID1: //for visit date

                final Calendar c1 = Calendar.getInstance();
                int mYear1 = c1.get(Calendar.YEAR);
                int mMonth1 = c1.get(Calendar.MONTH);
                int mDay1 = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd2 = new DatePickerDialog(AddPatientUpdate.this,
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

                DatePickerDialog dpd3 = new DatePickerDialog(AddPatientUpdate.this,
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
        // Log.d("lifecycle","onRestart invoked");
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
        if (mAilmemtArrayList != null) {
            mAilmemtArrayList = null;
        }
        if (bannerClass != null) {
            bannerClass = null;
        }
        if (mSymptomsList != null) {
            mSymptomsList = null;
        }

        if (lastNamedb != null) {
            lastNamedb = null;
        }
        doctor_membership_number = null;
        bannerimgNames = null;

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
        visitDate = null;
        fowSel = null;
        usersellectedDate = null;
        monthSel = null;
        sqlController = null;
        imageViewprescription = null;
        docId = null;
        addedTime = null;
        addedOnDate = null;
        patientHistoryData = null;
        PrescriptionimageName = null;
        backChangingImages = null;
        imageIntent = null;

        imagesFolder = null;
        uriSavedImage = null;
        addUpdate = null;
        cancel = null;
        validator = null;
        strCityorTown = null;
        strDistrict = null;
        strPinNo = null;
        strState = null;
        strAddress = null;

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
        txtRecord = null;
        txtsymtomsanddignost = null;
        edtInput_sugarfasting = null;
        edtInput_bmi = null;
        edtInput_height = null;
        btnclear = null;
        strAlternatenumber = null;
        strAlternatephtype = null;
        strIsd_code = null;
        strAlternateIsd_code = null;
        mDiagnosisList = null;
        strReferedTo = null;
        strRederedBy = null;
        NameData = null;
        specialityArray = null;
        patientImagePath = null;
        prescriptionImgPath = null;
        url = null;
        struid = null;
        strEmail = null;


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
                String dateis = sdf1.format(appController.addDay1(new Date(), fVal));
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
                buttonSelected = "month";
                String dateis = sdf1.format(appController.addMonth(new Date(), Integer.parseInt(value)));
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

    }
}
