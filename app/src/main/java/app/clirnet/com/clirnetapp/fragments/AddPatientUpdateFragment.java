package app.clirnet.com.clirnetapp.fragments;

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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;

//Our class extending fragment
public class AddPatientUpdateFragment extends Fragment {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int DATE_DIALOG_ID2 = 2;
    private Button addUpdate;
    private Button cancel;
    private MultiAutoCompleteTextView edtSymptoms;
    private MultiAutoCompleteTextView edtDignosis;
    private BootstrapEditText fodtextshow;
    private Button days;
    private Button week;
    private Button month;
    private BootstrapEditText inputnumber;
    private Button btnclear;
    private EditText clinicalNotes;
    private String strPatientId;
    private String fowSel = null;
    private String daysSel;
    private String userSellectedDate;
    private String monthSel = null;
    private int maxVisitId;
    private String doctor_membership_number;
    private String docId;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private BannerClass bannerClass;
    Button addPatientprescriptionBtn;
    private Intent imageIntent;
    private File imagesFolder,imageFile;
    private String PrescriptionimageName;
    private AppController appController;
    private Uri uriSavedImage;
    private String prescriptionImgPath;
    private TextView edtprescriptionImgPath;
    private LastnameDatabaseClass lastNamedb;
    private ArrayList<String> mSymptomsList;
    private ArrayList<String> mDiagnosisList;
    private String value;
    private String buttonSelected;
    private SimpleDateFormat sdf1;
    private EditText visitDate;
    private String addedTime;
    private String addedOnDate;
    private ArrayList<String> specialityArray;
    private HashMap<String, String> NameData;
    private String strReferredByName;
    private String strReferredTo1Name;
    private String strReferredTo2Name;
    private String strReferredTo3Name;
    private String strReferredTo4Name;
    private String strReferredTo5Name;
    private String strReferedTo;
    private String strReferedBy;
    private int addCounter;
    private TextView textRefredByShow;
    private TextView textRefredToShow;
    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtLowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private EditText edtInput_sugarfasting;
    private EditText edtInput_bmi;
    private EditText edtInput_height;

    private String strWeight;
    private String strPulse ;
    private String strBp;
    private String strLowBp ;
    private String strTemp ;
    private String strSugar;
    private String strTests ;
    private String strDrugs ;
    private String strHeight ;
    private String strbmi ;
    private String strSugarFasting;
    private DatabaseClass databaseClass;
    private  LinearLayout showReferrals,showVitals;
    private TextView txtReferredTo,txtReferredBy;
    private TextView txtLabelWeight,txtWeight;
    private TextView txtLabelHeight,txtHeight;
    private TextView txtLabelBmi,txtBmi;
    private TextView txtLabelPulse,txtPulse;
    private TextView txtLabelSystole,txtSystole;
    private TextView txtLabelTemp,txtTemp;
    private TextView txtLabelDistole,txtDistole;
    private TextView txtLabelSugarpp,txtSugarPp;
    private TextView txtLabelSugarFast,txtSugarFast;
    private  ViewPager viewPager;


    public  AddPatientUpdateFragment (){

    }
    public static AddPatientUpdateFragment newInstance(ViewPager viewPager,String strPatientId) {
        AddPatientUpdateFragment fragment = new AddPatientUpdateFragment();
        Bundle args = new Bundle();
        args.putString("strPatientId", strPatientId);
        args.putString("viewpager", String.valueOf(viewPager));
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            strPatientId=getArguments().getString("PATIENTID");
           // this.viewPager= (ViewPager) getArguments().get("viewpager");
          //  Log.e("strPatientId"," "+strPatientId);
        }
    }
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_patient_update_fragment, container, false);
        //strPatientId=getArguments().getString("PATIENTID");
        //Log.e("strPatientId"," "+strPatientId);
        cancel = (Button) view.findViewById(R.id.cancel);
        addUpdate = (Button) view.findViewById(R.id.addUpdate);
        edtSymptoms = (MultiAutoCompleteTextView) view.findViewById(R.id.symptoms);
        edtDignosis = (MultiAutoCompleteTextView) view.findViewById(R.id.dignosis);
        fodtextshow = (BootstrapEditText) view.findViewById(R.id.fodtextshow);
        inputnumber = (BootstrapEditText) view.findViewById(R.id.inputnumber);
        days = (Button) view.findViewById(R.id.days);
        week = (Button) view.findViewById(R.id.week);
        month = (Button) view.findViewById(R.id.month);
        btnclear = (Button) view.findViewById(R.id.btnclear);
        clinicalNotes = (EditText) view.findViewById(R.id.clinicalNotes);
        addPatientprescriptionBtn = (Button) view.findViewById(R.id.addPatientprescriptionBtn);
        edtprescriptionImgPath = (TextView) view.findViewById(R.id.prescriptionImgPath);
        visitDate = (EditText) view.findViewById(R.id.visitDate);

        textRefredByShow = (TextView) view.findViewById(R.id.txtrefredby);
        textRefredToShow = (TextView) view.findViewById(R.id.txtrefredto);
        Button refered = (Button) view.findViewById(R.id.buttonReferrals);
        Button buttonVital = (Button) view.findViewById(R.id.buttonVital);
        Button buttonHistory=(Button)view.findViewById(R.id.buttonHistory);
        showReferrals=(LinearLayout) view.findViewById(R.id.showReferrals);
        showVitals=(LinearLayout)view.findViewById(R.id.showVitals);
        txtReferredBy=(TextView)view.findViewById(R.id.txtReferredBy);
        txtReferredTo=(TextView)view.findViewById(R.id.txtReferredTo);

        txtLabelWeight=(TextView) view.findViewById(R.id.txtLabelWeight);
        txtWeight=(TextView) view.findViewById(R.id.txtWeight);
        txtLabelHeight=(TextView)view.findViewById(R.id.txtLabelHeight);
        txtHeight=(TextView)view.findViewById(R.id.txtHeight);

        txtLabelBmi=(TextView) view.findViewById(R.id.txtLabelBmi);
        txtBmi=(TextView) view.findViewById(R.id.txtBmi);
        txtLabelPulse=(TextView)view.findViewById(R.id.txtLabelPulse);
        txtPulse=(TextView)view.findViewById(R.id.txtPulse);

        txtLabelSystole=(TextView) view.findViewById(R.id.txtLabelSystole);
        txtSystole=(TextView) view.findViewById(R.id.txtSystole);
        txtLabelTemp=(TextView)view.findViewById(R.id.txtLabelTemp);
        txtTemp=(TextView)view.findViewById(R.id.txtTemp);

        txtLabelDistole=(TextView) view.findViewById(R.id.txtLabelDistole);
        txtDistole=(TextView) view.findViewById(R.id.txtDistole);
        txtLabelSugarpp=(TextView)view.findViewById(R.id.txtLabelSugarpp);
        txtSugarPp=(TextView)view.findViewById(R.id.txtSugarPp);
        txtLabelSugarFast=(TextView)view.findViewById(R.id.txtLabelSugarFast);
        txtSugarFast=(TextView)view.findViewById(R.id.txtSugarFast);


        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

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
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        try {
            if (sqlController == null) {
                sqlController = new SQLController(getContext());
                sqlController.open();
            }
            appController = new AppController();
            dbController = SQLiteHandler.getInstance(getContext());
            //This will get all the visit  history of patient
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            docId = sqlController.getDoctorId();
            maxVisitId = sqlController.getPatientVisitIdCount();

            if (bannerClass == null) {
                bannerClass = new BannerClass(getContext());
            }
            if (lastNamedb == null) {
                lastNamedb = new LastnameDatabaseClass(getContext());
            }
            if (databaseClass == null) {
                databaseClass = new DatabaseClass(getContext());
            }

        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
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
        addPrescription();
        addSymptomsAndDisgnosis();
        addFollowupdateButtonListner();

        addUpdate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                    saveDataToDataBase();//saved data int db

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    addUpdate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        return view;
    }


    private void addSymptomsAndDisgnosis() {
        try {
            mSymptomsList = lastNamedb.getSymptoms();
            if (mSymptomsList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mSymptomsList);

                edtSymptoms.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtSymptoms.setThreshold(1);
                edtSymptoms.setAdapter(lastnamespin);
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        try {
            mDiagnosisList = lastNamedb.getDiagnosis();
            if (mDiagnosisList.size() > 0) {
                ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mDiagnosisList);

                edtDignosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                edtDignosis.setThreshold(1);
                edtDignosis.setAdapter(lastnamespin);
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void addPrescription() {
        addPatientprescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                PrescriptionimageName = "prescription_" + docId + "_" + appController.getDateTime() + ".jpg";
                imageFile = new File(imagesFolder, PrescriptionimageName);
                uriSavedImage = Uri.fromFile(imageFile);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    //Image capture code
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                  //  new ImageCompression(getContext(),imageFile.getPath().toString()).execute( uriSavedImage.getPath().trim());
                    previewCapturedImage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    //this will show captured image to image view
    private void previewCapturedImage() {
        try {
            prescriptionImgPath = uriSavedImage.getPath();


            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(prescriptionImgPath);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "AddPatientUpdate" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
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
                    buttonSelected = "days";
                    String dateis = sdf1.format(appController.addDay1(new Date(), val));
                    fodtextshow.setText(dateis);
                    daysSel = value;
                    userSellectedDate = dateis;

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
                userSellectedDate = dateis;
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
                userSellectedDate = dateis;
                monthSel = value;
                fodtextshow.setText(dateis);
            }
        });

        btnclear.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    btnclear.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    btnclear.setTextColor(getResources().getColor(R.color.colorPrimary));
                    inputnumber.setText("");
                    fodtextshow.setText("");
                    daysSel = null;
                    fowSel = null;
                    monthSel = null;
                    CleanFollowup();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    btnclear.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                    btnclear.setTextColor(getResources().getColor(R.color.white));
                }
                return false;
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
        final Calendar c1 = Calendar.getInstance();
        int mYear1 = c1.get(Calendar.YEAR);
        int mMonth1 = c1.get(Calendar.MONTH);
        int mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        switch (id) {

            case DATE_DIALOG_ID1: //for visit date

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
                        }, mYear1, mMonth1, mDay1);
                c1.add(Calendar.DATE, 1);

                Date newDate3 = c1.getTime();
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

    private void goToNavigation() {
        Intent i = new Intent(getContext(), NavigationActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void showReferedDialogBox() {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan));
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View f = factory.inflate(R.layout.refered_by_dialog, null);


        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);

        dialog.setTitle("Referred By-To");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);
        Button dialogButtonCancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) f.findViewById(R.id.customDialogOk);
        final Button addMore = (Button) f.findViewById(R.id.addMore);

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

            setSpinner(f);//passsing view to method

        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient Update" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
                        textRefredByShow.setVisibility(View.VISIBLE);
                        txtReferredBy.setVisibility(View.VISIBLE);
                        int index = Integer.parseInt(code);
                        strReferedBy = String.valueOf(index);
                        textRefredByShow.setText("");
                        textRefredByShow.setText(strReferredByName);
                    } else {
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
                }else{
                    textRefredToShow.setVisibility(View.INVISIBLE);
                    txtReferredTo.setVisibility(View.INVISIBLE);
                }
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


        final TextView referredtoSpeciality1 = (TextView) f.findViewById(R.id.refredtoSpeciality1);
        final TextView referredtoSpeciality2 = (TextView) f.findViewById(R.id.refredtoSpeciality2);
        final TextView referredtoSpeciality3 = (TextView) f.findViewById(R.id.refredtoSpeciality3);
        final TextView referredtoSpeciality4 = (TextView) f.findViewById(R.id.refredtoSpeciality4);
        final TextView referredtoSpeciality5 = (TextView) f.findViewById(R.id.refredtoSpeciality5);
        final TextView referredBySpeciality  = (TextView) f.findViewById(R.id.refredBySpeciality);


        try {
            ArrayList<String> nameReferralsList = dbController.getReferalsnew();

            if (nameReferralsList.size() > 0) {
                ArrayAdapter<String> referralName = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line);
                referralName.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                referralName.add("Select Referrals");
                referralName.addAll(nameReferralsList);

                nameRefredBySpinner.setAdapter(referralName);
                nameRefredTo1Spinner.setAdapter(referralName);
                nameRefredTo2Spinner.setAdapter(referralName);
                nameRefredTo3Spinner.setAdapter(referralName);
                nameRefredTo4Spinner.setAdapter(referralName);
                nameRefredTo5Spinner.setAdapter(referralName);

            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        nameRefredTo1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo1Name = (String) parent.getItemAtPosition(position);

                try {
                    if (nameRefredTo1Spinner.getSelectedItem() == "Select Referrals") {

                    } else {
                        if (appController.contains(strReferredTo1Name, ".")) {
                            String[] parts = strReferredTo1Name.split(". ", 2);
                           // String string1 = parts[0];//namealias
                            strReferredTo1Name = parts[1].trim();//actual name

                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo1Name.trim());


                        //String strReferredTo1Id = list.get(0).get("ID");
                        if (list.size() > 0) {
                            String strSpeciality = list.get(0).get("SPECIALITY");
                            referredtoSpeciality1.setText(strSpeciality);
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
                    if (nameRefredTo2Spinner.getSelectedItem() == "Select Referrals") {

                    } else {
                        if (appController.contains(strReferredTo2Name, ".")) {
                            String[] parts = strReferredTo2Name.split(". ", 2);
                            //String string1 = parts[0];//namealias
                            strReferredTo2Name = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo2Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            referredtoSpeciality2.setText(strSpeclty);
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
                    if (nameRefredTo3Spinner.getSelectedItem() == "Select Referrals") {

                    } else {
                        if (appController.contains(strReferredTo3Name, ".")) {
                            String[] parts = strReferredTo3Name.split(". ", 2);
                            //String string1 = parts[0];//namealias
                            strReferredTo3Name = parts[1];//actual name
                        }

                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo3Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            referredtoSpeciality3.setText(strSpeclty);
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
                    if (nameRefredTo4Spinner.getSelectedItem() == "Select Referrals") {

                    } else {
                        if (appController.contains(strReferredTo4Name, ".")) {
                            String[] parts = strReferredTo4Name.split(". ", 2);
                            //String string1 = parts[0];//namealias
                            strReferredTo4Name = parts[1];//actual name
                        }

                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo4Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            referredtoSpeciality4.setText(strSpeclty);
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
                    if (nameRefredTo5Spinner.getSelectedItem() == "Select Referrals") {

                    } else {
                        if (appController.contains(strReferredTo5Name, ".")) {
                            String[] parts = strReferredTo5Name.split(". ", 2);
                           // String string1 = parts[0];//namealias
                            strReferredTo5Name = parts[1];//actual name
                        }

                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo5Name.trim());

                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            referredtoSpeciality5.setText(strSpeclty);
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

        nameRefredBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredByName = (String) parent.getItemAtPosition(position);
                try {
                    if (nameRefredBySpinner.getSelectedItem() == "Select Referrals") {

                    } else {
                        if (appController.contains(strReferredByName, ".")) {

                            String[] parts = strReferredByName.split(". ", 2);
                           // String string1 = parts[0];//namealias
                            strReferredByName = parts[1];//actual name
                        }
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredByName.trim());

                        // String strReferredById = list.get(0).get("ID");
                        if (list.size() > 0) {
                            String strSpeclty = list.get(0).get("SPECIALITY");
                            referredBySpeciality.setText(strSpeclty);
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
    private void addVitalsDialog() {

        final Dialog dialog;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            dialog = new Dialog(new ContextThemeWrapper(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan));
            LayoutInflater factory = LayoutInflater.from(getContext());

            final View f = factory.inflate(R.layout.vitals_in_dialog, null);

            Button cancel = (Button) f.findViewById(R.id.customDialogCancel);
            Button save = (Button) f.findViewById(R.id.customDialogOk);
            edtInput_weight = (EditText)f. findViewById(R.id.input_weight);
            edtInput_pulse = (EditText)f. findViewById(R.id.input_pulse);
            edtInput_bp = (EditText) f.findViewById(R.id.input_bp);
            edtLowBp = (EditText) f.findViewById(R.id.lowBp);
            edtInput_temp = (EditText)f. findViewById(R.id.input_temp);
            edtInput_sugar = (EditText) f.findViewById(R.id.input_sugar);
            edtInput_sugarfasting = (EditText)f. findViewById(R.id.input_sugarfasting);
            edtInput_bmi = (EditText)f. findViewById(R.id.input_bmi);
            edtInput_height = (EditText) f.findViewById(R.id.input_height);

            dialog.setTitle(" Add Vitals ");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(f);
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
                    strSugar = edtInput_sugar.getText().toString().trim();
                    strSugarFasting = edtInput_sugarfasting.getText().toString().trim();
                    strTests = null;
                    strDrugs = null;

                    strHeight = edtInput_height.getText().toString().trim();
                    strbmi = edtInput_bmi.getText().toString().trim();

                    if (strTemp.length() > 0) {
                        double valueTemp = Double.parseDouble(strTemp);
                        if (valueTemp > 110) {
                            edtInput_temp.setError(" Temp can not be more than 110 ");
                            return;
                        } else {
                            edtInput_temp.setError(null);
                        }
                    }
                    showVitals.setVisibility(View.VISIBLE);
                    if (strWeight != null && !strWeight.equals("") && strWeight.length()>0) {
                        txtLabelWeight.setVisibility(View.VISIBLE);
                        txtWeight.setVisibility(View.VISIBLE);
                        txtWeight.setText(strWeight+ " Kgs");
                    } else {
                        txtLabelWeight.setVisibility(View.GONE);
                        txtWeight.setVisibility(View.GONE);
                    }

                    if (strHeight != null && !strHeight.equals("") && strHeight.length()>0) {
                        txtLabelHeight.setVisibility(View.VISIBLE);
                        txtHeight.setVisibility(View.VISIBLE);
                        txtHeight.setText(strHeight + " Cms");
                    } else {
                        txtLabelHeight.setVisibility(View.GONE);
                        txtHeight.setVisibility(View.GONE);
                    }

                    if (strbmi != null && !strbmi.equals("") && strbmi.length()>0) {
                        txtLabelBmi.setVisibility(View.VISIBLE);
                        txtBmi.setVisibility(View.VISIBLE);
                        txtBmi.setText(strbmi + " Bmi");
                    } else {
                        txtLabelBmi.setVisibility(View.GONE);
                        txtBmi.setVisibility(View.GONE);
                    }

                    if (strPulse != null && !strPulse.equals("") && strPulse.length()>0) {
                        txtLabelPulse.setVisibility(View.VISIBLE);
                        txtPulse.setVisibility(View.VISIBLE);
                        txtPulse.setText(strPulse + " bmp");
                    } else {
                        txtLabelPulse.setVisibility(View.GONE);
                        txtPulse.setVisibility(View.GONE);
                    }
                    if (strBp != null && !strBp.equals("") && strBp.length()>0) {
                        txtLabelSystole.setVisibility(View.VISIBLE);
                        txtSystole.setVisibility(View.VISIBLE);
                        txtSystole.setText(strBp + " mmhg");
                    } else {
                        txtLabelSystole.setVisibility(View.GONE);
                        txtSystole.setVisibility(View.GONE);
                    }
                    if (strLowBp != null && !strLowBp.equals("") && strLowBp.length()>0) {
                        txtLabelDistole.setVisibility(View.VISIBLE);
                        txtDistole.setVisibility(View.VISIBLE);
                        txtDistole.setText(strLowBp + " mmhg");
                    } else {
                        txtLabelDistole.setVisibility(View.GONE);
                        txtDistole.setVisibility(View.GONE);
                    }
                    if (strTemp != null && !strTemp.equals("") && strTemp.length()>0) {
                        txtLabelTemp.setVisibility(View.VISIBLE);
                        txtTemp.setVisibility(View.VISIBLE);
                        txtTemp.setText(strTemp + ""+ getResources().getString(R.string.degree));
                    } else {
                        txtLabelTemp.setVisibility(View.GONE);
                        txtTemp.setVisibility(View.GONE);
                    }


                    if (strSugar != null && !strSugar.equals("") && strSugar.length()>0) {
                        txtLabelSugarpp.setVisibility(View.VISIBLE);
                        txtSugarPp.setVisibility(View.VISIBLE);
                        txtSugarPp.setText(strSugar +" mg/dl");
                    } else {
                        txtLabelSugarpp.setVisibility(View.GONE);
                        txtSugarPp.setVisibility(View.GONE);
                    }
                    if (strSugarFasting != null && !strSugarFasting.equals("") && strSugarFasting.length()>0) {
                        txtLabelSugarFast.setVisibility(View.VISIBLE);
                        txtSugarFast.setVisibility(View.VISIBLE);
                        txtSugarFast.setText(strSugarFasting+" mg/dl");
                    } else {
                        txtLabelSugarFast.setVisibility(View.GONE);
                        txtSugarFast.setVisibility(View.GONE);
                    }

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
            }
        });
    }

    private void saveDataToDataBase() {
        monthSel = null;
        fowSel = null;
        String ailments = null;
        String clinical_note = clinicalNotes.getText().toString().trim();
        //String follow_up_dates = follow_up_date.getText().toString().trim();
        String follow_up_dates = null;
        String strSymptoms = edtSymptoms.getText().toString().trim();
        String strDignosis = edtDignosis.getText().toString().trim();

        userSellectedDate = fodtextshow.getText().toString();

        if (TextUtils.isEmpty(strSymptoms) && TextUtils.isEmpty(strDignosis)) {
            Toast.makeText(getContext(), "Symptoms or Diagnosis left blank.", Toast.LENGTH_LONG).show();
            // ailment.setError("Please enter Ailment");
            return;
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

            if (!new AppController().isDuplicate(mDiagnosisList, aTemp)) {

                databaseClass.addDiagnosis(aTemp);

            }
        }

        String[] symp = strSymptoms.split(delimiter);

        for (String aTemp : symp) {

            if (!new AppController().isDuplicate(mSymptomsList, aTemp)) {

                databaseClass.addSymptoms(aTemp);

            }
        }

        try {
            //convert visit date from 2016-11-1 to 2016-11-01
            visit_date = myFormat.format(fromUser.parse(added_on));
            added_on = myFormat.format(fromUser.parse(addedOnDate));

            if (userSellectedDate != null && !userSellectedDate.equals("")) {
                userSellectedDate = myFormat.format(fromUser.parse(userSellectedDate));
            }

        } catch (ParseException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        String flag = "0";
        String added_by = docId;
        String patientInfoType = "App";
        String action = "added";
        String record_source = "Add Patient Update";

        dbController.addPatientNextVisitRecord(visit_id, strPatientId, userSellectedDate, follow_up_dates, daysSel, fowSel, monthSel, clinical_note, prescriptionImgPath, ailments, visit_date, docId, doctor_membership_number, added_on, addedTime, flag, added_by, action, patientInfoType,
                strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs, strHeight, strbmi, strSugarFasting, strReferedBy, strReferedTo, record_source);

        Toast.makeText(getContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
        //Redirect to navigation Activity
        goToNavigation();
    }
    @Override
    public void onDetach() {
        super.onDetach();
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
        if (bannerClass != null) {
            bannerClass = null;
        }
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
        txtLabelWeight = null;
        txtWeight = null;
        txtLabelHeight = null;
        txtHeight = null;
        txtLabelBmi = null;
        txtBmi = null;
        txtLabelPulse = null;
        txtPulse = null;
        txtLabelSystole = null;
        txtSystole = null;
        txtLabelTemp = null;
        txtTemp = null;
        txtLabelDistole = null;
        txtDistole = null;
        txtLabelSugarpp = null;
        txtSugarPp = null;
        txtLabelSugarFast = null;
        txtSugarFast = null;
        visitDate = null;
        edtprescriptionImgPath = null;
        addPatientprescriptionBtn = null;
        inputnumber = null;
        btnclear = null;
        clinicalNotes = null;
        fodtextshow = null;
        days = null;
        week = null;
        month = null;
        addUpdate = null;
        cancel = null;
        edtSymptoms = null;
        edtDignosis = null;

        daysSel = null;
        fowSel = null;
        monthSel = null;
        userSellectedDate=null;
        strWeight = null;
        strPulse = null;
        strBp = null;
        strLowBp = null;
        strTemp = null;
        strSugar = null;
        strSugarFasting = null;
        strHeight = null;

        showReferrals = null;
        showVitals = null;

        textRefredByShow = null;
        textRefredToShow = null;
        strReferredByName = null;
        strReferredTo1Name = null;
        strReferredTo2Name = null;
        strReferredTo3Name = null;
        strReferredTo4Name = null;
        strReferredTo5Name = null;

        strReferedTo = null;
        strReferedBy = null;
        NameData=null;
        mDiagnosisList=null;
        mSymptomsList=null;
        value=null;
        sdf1=null;
        imageIntent=null;
        uriSavedImage=null;
        imagesFolder=null;
        doctor_membership_number=null;
        docId=null;
        PrescriptionimageName=null;
        prescriptionImgPath=null;

        addedTime=null;
        addedOnDate=null;
        specialityArray=null;
    }
public void getViewPager(ViewPager viewPager){
    this.viewPager=viewPager;
   // Log.e("viewPager",""+viewPager);

}
}
