package app.clirnet.com.clirnetapp.quickAdd;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

import static app.clirnet.com.clirnetapp.R.id.ailments;
import static app.clirnet.com.clirnetapp.R.id.prescriptionImgPath;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickAddNewRecordsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickAddNewRecordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickAddNewRecordsFragment extends Fragment {
    private static final int DATE_DIALOG_ID = 0;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int DATE_DIALOG_ID2 = 2;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 = 1889;
    private int countvitalsLayout = 1;
    private int countsymtomsanddignostLayout = 1;
    private int countPrescriptiontLayout = 1;
    private int countAddressLayout = 1;
    private OnFragmentInteractionListener mListener;
    private String strFirstName;
    private String strMiddleName;
    private String strLastName;
    private EditText firstName;
    private EditText middleName;
    private BootstrapEditText address, city, pin, district;
    private AutoCompleteTextView lastName;
    private EditText date_of_birth;
    private EditText phone_no;
    private EditText age;
    private ImageView patientimage;
    private ImageView imageViewprescription;
    private EditText clinical_Notes;

    private MultiAutoCompleteTextView multiAutoComplete;
    private Spinner language;
    private Button add;
    private Button cancel;
    private TextView showfodtext;
    private ImageView backChangingImages;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private String doctor_membership_number;
    private String docId;
    private AppController appController;
    private SimpleDateFormat sdf1;
    private StringBuilder sysdate;
    private EditText visitDate;
    private String addedTime;
    private View view;
    private int pateintage;
    private BootstrapEditText fodtextshow;
    private Intent imageIntent;
    private String usersellectedDate;
    private String daysSel, fowSel, monthSel;
    private ArrayList<String> mLastNameList;
    private ArrayList<String> mSymptomsList;
    private ArrayList<String> mDiagnosisList;
    private String patientImagePath;
    private LastnameDatabaseClass lastNamedb;
    private DatabaseClass databaseClass;
    private String sex;
    private String selectedLanguage;
    private File imagesFolder;
    private String imageName;
    private Button days;
    private Button week;
    private Button month;
    private TextView txtaddress;
    private Button btnclear;
    private String value;
    private EditText edtInput_height;
    private EditText edtInput_sugarfasting;
    private EditText edtInput_bmi;
    private String selectedUidType;
    private BootstrapEditText uid;
    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtlowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private int position;
    private String selectedPhoneTypealternate_no;
    private BootstrapEditText alternatePhone_no;
    private String selectedIsd_codeType;
    private String selectedPhoneType;
    private String selectedState;
    private String selectedAlternateNoIsd_codeType;
    private BootstrapEditText inputnumber;
    private String middle_name;
    private String buttonSelected;
    private String strPatientId;
    private MultiAutoCompleteTextView edtSymptoms;
    private MultiAutoCompleteTextView edtDignosis;
    private MultiAutoCompleteTextView ailments1;
    private BootstrapEditText edtTest;
    private BootstrapEditText edtDrugs;
    private String prescriptionimgPath;
    private String strVisitId;
    private Uri uriSavedImage;
    private ImageView img;
    private TextView txtsymtomsanddignost;
    private TextView presciptiontext;
    private TextView txtRecord;
    private MultiAutoCompleteTextView ailment;
    private SearchView searchView;
    private ArrayList<RegistrationModel> filteredModelList;
    private String clickedList;
    private int maxPatientIdCount;
    private int maxVisitId;
    private String prescriptionimgId;
    private String phNumber;
    private String added_on;
    private BannerClass bannerClass;
    private ArrayList<String> bannerimgNames;
    private String emial_addr;

    public QuickAddNewRecordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    public static QuickAddNewRecordsFragment newInstance(String param1, String param2) {
        QuickAddNewRecordsFragment fragment = new QuickAddNewRecordsFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            prescriptionimgPath = getArguments().getString("PRESCRIPTIONIMG");
            prescriptionimgId = getArguments().getString("PRESCRIPTIONID");
            phNumber = getArguments().getString("PHNUMBER");
            added_on=getArguments().getString("ADDED_ON");
             emial_addr=getArguments().getString("EMIAL_ADDRESS");
            //Log.e("prescriptionimgId", "" + emial_addr);
        }
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quick_edit_records, container, false);
        firstName = (EditText) view.findViewById(R.id.firstname);
        middleName = (EditText) view.findViewById(R.id.middlename);
        lastName = (AutoCompleteTextView) view.findViewById(R.id.lastname);
        img = (ImageView) view.findViewById(R.id.patientimage);
        date_of_birth = (EditText) view.findViewById(R.id.dob);
        phone_no = (EditText) view.findViewById(R.id.mobile_no);
        age = (EditText) view.findViewById(R.id.age);
        inputnumber = (BootstrapEditText) view.findViewById(R.id.inputnumber);
        alternatePhone_no = (BootstrapEditText) view.findViewById(R.id.alternatemobile_no);

        fodtextshow = (BootstrapEditText) view.findViewById(R.id.fodtextshow);
        txtsymtomsanddignost = (TextView) view.findViewById(R.id.txtsymptomsanddignost);
        txtRecord = (TextView) view.findViewById(R.id.txtRecord);
        presciptiontext = (TextView) view.findViewById(R.id.presciptiontext);
        txtaddress = (TextView) view.findViewById(R.id.txtaddress);
        ailment = (MultiAutoCompleteTextView) view.findViewById(ailments);
        patientimage = (ImageView) view.findViewById(R.id.patientimage);
        Button addPatientImgBtn = (Button) view.findViewById(R.id.addPatientImgBtn);
        Button prescriptionBtn = (Button) view.findViewById(R.id.addPatientprescriptionBtn);
        imageViewprescription = (ImageView) view.findViewById(R.id.imageViewprescription);
        clinical_Notes = (EditText) view.findViewById(R.id.clinicalNotes);
        add = (Button) view.findViewById(R.id.add);
        cancel = (Button) view.findViewById(R.id.cancel);
        multiAutoComplete = (MultiAutoCompleteTextView) view.findViewById(ailments);
        visitDate = (EditText) view.findViewById(R.id.visitDate);
        showfodtext = (TextView) view.findViewById(R.id.fodtextshow);
        address = (BootstrapEditText) view.findViewById(R.id.address);
        city = (BootstrapEditText) view.findViewById(R.id.city);
        district = (BootstrapEditText) view.findViewById(R.id.district);
        pin = (BootstrapEditText) view.findViewById(R.id.pin);
        uid = (BootstrapEditText) view.findViewById(R.id.uid);
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        txtaddress = (TextView) view.findViewById(R.id.txtaddress);
        days = (Button) view.findViewById(R.id.days);
        week = (Button) view.findViewById(R.id.week);
        month = (Button) view.findViewById(R.id.month);
        btnclear = (Button) view.findViewById(R.id.btnclear);

        edtInput_weight = (EditText) view.findViewById(R.id.input_weight);
        edtInput_height = (EditText) view.findViewById(R.id.input_height);
        edtInput_pulse = (EditText) view.findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) view.findViewById(R.id.input_bp);
        edtlowBp = (EditText) view.findViewById(R.id.lowBp);
        edtInput_temp = (EditText) view.findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) view.findViewById(R.id.input_sugar);
        edtInput_sugarfasting = (EditText) view.findViewById(R.id.input_sugarfasting);
        edtInput_bmi = (EditText) view.findViewById(R.id.input_bmi);
        edtSymptoms = (MultiAutoCompleteTextView) view.findViewById(R.id.symptoms);
        edtDignosis = (MultiAutoCompleteTextView) view.findViewById(R.id.dignosis);
        //  ailments1 = (MultiAutoCompleteTextView) view.findViewById(R.id.ailments1);
        phone_no.setText(phNumber);
        phone_no.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only

        TextView edtprescriptionImgPath=(TextView) view.findViewById(prescriptionImgPath);
        edtprescriptionImgPath.setVisibility(View.VISIBLE);
        edtprescriptionImgPath.setText(prescriptionimgPath);

        try {

            sqlController = new SQLController(getContext());
            sqlController.open();
            dbController = SQLiteHandler.getInstance(getContext());
            appController = new AppController();
            databaseClass = new DatabaseClass(getContext());

            lastNamedb = new LastnameDatabaseClass(getContext());
            //get doctor membership id
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            //get doctor  id
            docId = sqlController.getDoctorId();

            //this will give us a max of patient_id from patient records which will help to store records locally
            maxPatientIdCount = sqlController.getPatientIdCount();
            //int getPatientIdCountnew = sqlController.getPatientIdCountnew();

            maxVisitId = sqlController.getPatientVisitIdCount();

            if (bannerClass == null) {
                bannerClass = new BannerClass(getContext());
            }
            bannerimgNames = bannerClass.getImageName();


        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Registration" + e);
        }

        addListners();

        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);
        sysdate = new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append("");


        //Log.e("currentDate", "" + sysdate);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date todayDate = new Date();

        //this date is ued to set update records date in patient history table
        addedTime = sdf.format(todayDate);
        if(added_on!=null && !added_on.equals("")){
            visitDate.setText(added_on);
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
            }
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        addFollowupdateButtonListner();
        setUpAnimation();



        RadioGroup gndrbutton = (RadioGroup) view.findViewById(R.id.radioGender);
        RadioGroup radioLanguage = (RadioGroup) view.findViewById(R.id.radioLanguage);
        sex = "Male";//set Daefault gender value to Male if not selected any other value to prevent null value. 14-12-2016
        // Checked change Listener for RadioGroup 1
        gndrbutton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMale:
                        sex = "Male";
                        break;
                    case R.id.radioFemale:
                        sex = "Female";
                        break;
                    case R.id.radioOther:
                        sex = "Other";
                        break;
                    case R.id.radioNa:
                        sex = "NA";
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
                strFirstName = firstName.getText().toString().trim();
                strLastName = lastName.getText().toString().trim();

                if (TextUtils.isEmpty(strFirstName)) {
                    firstName.setError("Please enter First Name");
                    return;
                } else {
                    firstName.setError(null); //null the error which we are display when error found
                }

                if (TextUtils.isEmpty(strLastName)) {
                    lastName.setError("Please enter Last Name");
                    return;
                } else {
                    lastName.setError(null);
                }

                imageName = "img_" + strFirstName + "_" + strLastName + "_" + "docId" + "_" + appController.getDateTime() + ".png";

                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        prescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();
                strFirstName = firstName.getText().toString().trim();
                strLastName = lastName.getText().toString().trim();

                if (TextUtils.isEmpty(strFirstName)) {
                    firstName.setError("Please enter First Name");
                    return;
                } else {
                    firstName.setError(null);
                }

                if (TextUtils.isEmpty(strFirstName)) {
                    lastName.setError("Please enter Last Name");
                    return;
                } else {
                    lastName.setError(null);
                }

                imageName = "prescription_" + strFirstName + "_" + docId + "_" + appController.getDateTime() + ".png";


                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);

                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1);

            }
        });

        date_of_birth.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog(DATE_DIALOG_ID);
                setOnActionDownBackground();

            }
        });

        // setSpinnerData();
        setOnClickListner();
        setSpinnerValue();


        return view;
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


    private void addFollowupdateButtonListner() {
        fodtextshow.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                shpwDialog(DATE_DIALOG_ID2);

            }
        });
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
                    buttonSelected = "days";
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
                    String dateis = sdf1.format(addDay(new Date(), val));
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
                buttonSelected = "week";
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
                buttonSelected = "week";
                int fVal = (int) (val * 7);
                String dateis = sdf1.format(addDay(new Date(), fVal));
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
                buttonSelected = "month";
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
                String dateis = sdf1.format(addMonth(new Date(), Integer.parseInt(value)));
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
                buttonSelected = null;
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
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void shpwDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID: //for dob field

                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);

                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd1 = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date_of_birth.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                                // we calculate age from dob and set to text view.
                                pateintage = appController.getAge(year, monthOfYear, dayOfMonth);
                                String ageid = String.valueOf(pateintage);
                                age.setText(ageid);
                                age.setEnabled(false);//this will set edit text editable false if dob is present


                            }

                        }, mYear2, mMonth2, mDay2);
                dpd1.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpd1.show();
                //show age of pateint

                break;

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
                               /* showfodtext.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);*/


                            }
                        }, mYear1, mMonth1, mDay1);
                c1.add(Calendar.DATE, 0);

                Date newDate = c1.getTime();
                dpd2.getDatePicker().setMaxDate(newDate.getTime());

                dpd2.show();

                break;

            case DATE_DIALOG_ID2://for follow up date

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


                            }
                        }, mYear3, mMonth3, mDay3);
                c3.add(Calendar.DATE, 1);

                Date newDate3 = c3.getTime();
                dpd3.getDatePicker().setMinDate(newDate3.getTime());

                dpd3.show();

                break;
        }
    }

    private void addListners() {

        visitDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog(DATE_DIALOG_ID1);

            }
        });

        add.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    add.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));
                    //Insert data into database
                    insertIntoDB();
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    add.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
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


        firstName.setOnTouchListener(new View.OnTouchListener() {


            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    firstName.setBackground(getResources().getDrawable(R.drawable.rectangle_cornor_blue_color));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    setOnActionDownBackground();

                }
                return false;
            }

        });
        middleName.setOnTouchListener(new View.OnTouchListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    middleName.setBackground(getResources().getDrawable(R.drawable.rectangle_cornor_blue_color));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    setOnActionDownBackground();

                }
                return false;
            }


        });
        lastName.setOnTouchListener(new View.OnTouchListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    lastName.setBackground(getResources().getDrawable(R.drawable.rectangle_cornor_blue_color));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    setOnActionDownBackground();

                }

                return false;
            }

        });

        Button resetdobage = (Button) view.findViewById(R.id.resetdobage);

        resetdobage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                age.setEnabled(true);
                age.setText("");
                date_of_birth.setText("");
            }
        });

    }

    private void setOnActionDownBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            firstName.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
            middleName.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
            lastName.setBackground(getResources().getDrawable(R.drawable.rectangle_corner));
        }
    }

    private void goToNavigation() {
        Intent i = new Intent(getContext(), NavigationActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void setOnClickListner() {

        txtaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout addresslayoutlayout = (LinearLayout) view.findViewById(R.id.addresslayout);
                if (countAddressLayout == 1) {
                    addresslayoutlayout.setVisibility(View.VISIBLE);
                    txtaddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrow, 0); //set drawable right to text view
                    countAddressLayout = 2;
                } else {
                    addresslayoutlayout.setVisibility(View.GONE);
                    txtaddress.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.down_arrow, 0); //set drawable right to text view
                    countAddressLayout = 1;
                }
                //  txtRecord.setBackground(R.drawable.);
            }
        });

        txtRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout vitalsLayout = (LinearLayout) view.findViewById(R.id.vitalsLayout);
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
                LinearLayout symptomsdigosislayout = (LinearLayout) view.findViewById(R.id.symptomsdigosislayout);
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
                LinearLayout symptomsdigosislayout = (LinearLayout) view.findViewById(R.id.presciptionlayout);
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


        inputnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = inputnumber.getText().toString().trim();

                fodtextshow.setText("");
                if (buttonSelected != null && !value.equals("") && value.length() > 0) {

                    long val = Long.parseLong(value);


                    switch (buttonSelected) {
                        case "days":

                            if (val > 366) {
                                inputnumber.setError("Enter up to 366 Days");
                                return;
                            } else {
                                inputnumber.setError(null);
                            }


                            break;
                        case "week":
                            if (val > 54) {
                                inputnumber.setError("Enter up to 54 Weeks");
                                return;
                            } else {
                                inputnumber.setError(null);
                            }

                            break;
                        case "month":

                            if (val > 12) {
                                inputnumber.setError("Enter up to 12 Months");
                                return;
                            } else {
                                inputnumber.setError(null);
                            }
                            break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = inputnumber.getText().toString().trim();

                fodtextshow.setText("");
                if (buttonSelected != null && value != null && !value.equals("") && value.length() > 0) {


                    long val = Long.parseLong(value);
                    // Log.e("val23", "" + val);
                    if (buttonSelected.equals("days")) {

                        if (val > 366) {
                            inputnumber.setError("Enter up to 366 Days");
                            return;
                        } else {
                            int fVal = (int) (val);
                            buttonSelected = "days";
                            String dateis = sdf1.format(addDay(new Date(), fVal));
                            fodtextshow.setText(dateis);
                            daysSel = value;
                            usersellectedDate = dateis;
                        }
                    }
                    if (buttonSelected.equals("week")) {
                        if (val > 54) {
                            inputnumber.setError("Enter up to 54 Weeks");
                            return;
                        } else {
                            buttonSelected = "week";
                            int fVal = (int) (val * 7);
                            String dateis = sdf1.format(addDay(new Date(), fVal));
                            usersellectedDate = dateis;
                            fowSel = value;
                            fodtextshow.setText(dateis);
                            inputnumber.setError(null);
                        }
                    }
                    if (buttonSelected.equals("month")) {

                        if (val > 12) {
                            inputnumber.setError("Enter up to 12 Months");
                        } else {
                            buttonSelected = "month";
                            String dateis = sdf1.format(addMonth(new Date(), Integer.parseInt(value)));
                            usersellectedDate = dateis;
                            monthSel = value;
                            fodtextshow.setText(dateis);
                            inputnumber.setError(null);
                        }
                    }
                }
            }


        });


        firstName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                firstName.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                firstName.setError(null);
            }
        });
        //Remove red error after text changes By.Ashish 15-11-2016
        lastName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                lastName.setError(null);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                lastName.setError(null);
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

    private void setSpinnerValue() {
        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.phoneTypeSpinner);
        Spinner stateSpinner = (Spinner) view.findViewById(R.id.stateSpinner);
        Spinner phoneTypeSpinner2 = (Spinner) view.findViewById(R.id.phoneTypeSpinner2);
        Spinner uidType = (Spinner) view.findViewById(R.id.uidtype);

        // Spinner click listener


        // Creating adapter for spinner
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.phType_group,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(position);

        phoneTypeSpinner2.setAdapter(dataAdapter);
        phoneTypeSpinner2.setSelection(position);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //  Log.v("item", (String) parent.getItemAtPosition(position));

                selectedPhoneType = (String) parent.getItemAtPosition(position);
              /*  Toast.makeText(EditPersonalInfo.this, "selected language is:" + (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        phoneTypeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // Log.v("item", (String) parent.getItemAtPosition(position));

                selectedPhoneTypealternate_no = (String) parent.getItemAtPosition(position);
              /*  Toast.makeText(EditPersonalInfo.this, "selected language is:" + (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
////////////////////////////////////////////////////////////////////////////////////

        stateSpinner.setPrompt("Select State");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.states,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setSelection(position);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //   Log.v("item", (String) parent.getItemAtPosition(position));

                selectedState = (String) parent.getItemAtPosition(position);
                // Toast.makeText(RegistrationActivityNew.this, "selected State is:" + selectedState, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////

        uidType.setPrompt("Select Type");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> uidTypeAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.uid_group,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        uidTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        uidType.setAdapter(uidTypeAdapter);
        uidType.setSelection(position);

        uidType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //   Log.v("item", (String) parent.getItemAtPosition(position));

                selectedUidType = (String) parent.getItemAtPosition(position);
                // Toast.makeText(RegistrationActivityNew.this, "selected State is:" + selectedState, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////////////////////////////////////
        Spinner isd_code = (Spinner) view.findViewById(R.id.isd_code);
        isd_code.setPrompt("Select Type");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> isd_codeTypeAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.isd_code,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        isd_codeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        isd_code.setAdapter(isd_codeTypeAdapter);
        isd_code.setSelection(position);

        isd_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //   Log.v("item", (String) parent.getItemAtPosition(position));

                selectedIsd_codeType = (String) parent.getItemAtPosition(position);
                // Toast.makeText(RegistrationActivityNew.this, "selected State is:" + selectedState, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////////////////////////////////////////////////////
        Spinner isd_code2 = (Spinner) view.findViewById(R.id.isd_code2);
        isd_code2.setPrompt("Select Type");

        // Creating adapter for spinner
        ArrayAdapter<CharSequence> isd_codeTypeAdapter2 = ArrayAdapter
                .createFromResource(getContext(), R.array.isd_code,
                        android.R.layout.simple_spinner_item);

        // Drop down layout style - list view with radio button
        isd_codeTypeAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        isd_code2.setAdapter(isd_codeTypeAdapter);
        isd_code2.setSelection(position);

        isd_code2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //   Log.v("item", (String) parent.getItemAtPosition(position));

                selectedAlternateNoIsd_codeType = (String) parent.getItemAtPosition(position);
                // Toast.makeText(RegistrationActivityNew.this, "selected State is:" + selectedState, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AppController.getInstance().trackScreenView("Quick Add New Records Fragment");
    }
    @Override
    public void onDetach() {
        super.onDetach();
               mListener = null;
       view=null;
// session.setLogin(false);
        if (dbController != null) {
            dbController.close();//Close the all database connection opened here 31/10/2008 By. Ashish
            dbController = null;
        }
        if (databaseClass != null) {
            databaseClass.close();//Close the all database connection opened here 31/10/2008 By. Ashish
            databaseClass = null;
        }
        if (lastNamedb != null) {
            lastNamedb.close();//Close the all database connection opened here 31/10/2008 By. Ashish
            lastNamedb = null;
        }
        if (appController != null) {
            appController = null;
        }

        cleanResources();

    }

    @Override
    public void onPause() {
        // Log.e("DEBUG", "OnPause of HomeFragment");

        super.onPause();
    }

    private void cleanResources() {

        if (mSymptomsList != null) {
            mSymptomsList = null;
        }
        firstName = null;
        middleName = null;
        lastName = null;
        date_of_birth = null;
        phone_no = null;
        age = null;
        clinical_Notes = null;
        sysdate = null;
        selectedLanguage = null;
        imageIntent = null;
        imagesFolder = null;
        strFirstName = null;
        strLastName = null;
        imageName = null;
        mLastNameList = null;
        sdf1 = null;
        doctor_membership_number = null;
        docId = null;
        addedTime = null;

        selectedPhoneType = null;
        txtaddress = null;

        selectedState = null;
        edtInput_weight = null;
        edtInput_pulse = null;
        edtInput_bp = null;
        edtlowBp = null;
        edtInput_temp = null;
        edtInput_sugar = null;
        selectedPhoneTypealternate_no = null;
        edtInput_height = null;
        edtInput_sugarfasting = null;
        edtInput_bmi = null;
        selectedUidType = null;
        uid = null;
        backChangingImages = null;
        mDiagnosisList = null;
        btnclear = null;
        selectedIsd_codeType = null;
        selectedAlternateNoIsd_codeType = null;
        visitDate = null;
        add = null;
        sex = null;
        middle_name = null;
        fodtextshow = null;
        buttonSelected = null;
        strPatientId = null;
        edtSymptoms = null;
        edtDignosis = null;
        edtTest = null;
        edtDrugs = null;
        strVisitId = null;
        uriSavedImage = null;
        prescriptionimgPath = null;
        img = null;
    }

    private void insertIntoDB() {
        String strailments = null;
        strFirstName = firstName.getText().toString().trim();
        middle_name = middleName.getText().toString().trim();
        strLastName = lastName.getText().toString().trim();

        String strdate_of_birth = date_of_birth.getText().toString().trim();
        String phone_number = phone_no.getText().toString().trim();
        String current_age = age.getText().toString().trim();

        String strAddress = address.getText().toString().trim();
        String strCity = city.getText().toString().trim();
        String strDistrict = district.getText().toString().trim();
        String strPin = pin.getText().toString().trim();

        String strWeight = edtInput_weight.getText().toString().trim();
        String strPulse = edtInput_pulse.getText().toString().trim();
        String strBp = edtInput_bp.getText().toString().trim();
        String strLowBp = edtlowBp.getText().toString().trim();
        String strTemp = edtInput_temp.getText().toString().trim();
        String strSugar = edtInput_sugar.getText().toString().trim();
        String strSymptoms = edtSymptoms.getText().toString().trim();
        String strDignosis = edtDignosis.getText().toString().trim();

        String strTests =null;
        String strDrugs = null;
        String stralternatePhone_no = alternatePhone_no.getText().toString().trim();
        String strHeight = edtInput_height.getText().toString().trim();
        String strbmi = edtInput_bmi.getText().toString().trim();
        String strSugarFasting = edtInput_sugarfasting.getText().toString().trim();


        usersellectedDate = fodtextshow.getText().toString();
        String strUid = uid.getText().toString();

        try {
            if (selectedState != null && selectedState.equals("Select State")) {
                selectedState = null;
            }
            if (strWeight.length() > 0) {
                int weightVal = Integer.parseInt(strWeight);

                if (buttonSelected.equals("days")) {

                    if (weightVal > 300) {
                        edtInput_weight.setError("Weight can not be more than 300Kgs ");
                        return;
                    } else {
                        edtInput_weight.setError(null);
                    }
                }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(strFirstName)) {
            firstName.setError("Please enter First Name");
            return;
        } else {
            firstName.setError(null); //null the error which we are display when error found
        }

        if (TextUtils.isEmpty(strLastName)) {
            lastName.setError("Please enter Last Name");
            return;
        } else {
            lastName.setError(null);
        }


        String clinical_note = clinical_Notes.getText().toString().trim();
        int length = current_age.length();

        if (length >= 4) {
            age.setError("Invalid Age Entered");
            return;
        }
        int nwage;
        try {
            if (current_age.length() > 0) {
                nwage = Integer.parseInt(current_age);
                if (nwage > 150) {
                    age.setError("Age should be less than 150 Years");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Registration" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        if (TextUtils.isEmpty(strFirstName) || TextUtils.isEmpty(strLastName) || TextUtils.isEmpty(phone_number) || phone_number.length() < 10 || TextUtils.isEmpty(current_age)) {

            if (TextUtils.isEmpty(strFirstName)) {
                firstName.setError("Please enter First Name");
                return;
            } else if (TextUtils.isEmpty(strLastName)) {
                lastName.setError("Please enter Last Name");
                return;
            } else if (TextUtils.isEmpty(current_age)) {
                age.setError("Please enter Age");
                return;
            }
            if (current_age.length() > 0) {
                age.setError(null);
            } else if (TextUtils.isEmpty(phone_number)) {
                phone_no.setError("Please enter Mobile Number");

                return;
            }


            String value = inputnumber.getText().toString().trim();

            if (buttonSelected != null && value != null && value.length() > 0) {

                int val = Integer.parseInt(value);

                switch (buttonSelected) {
                    case "days":

                        if (val > 366) {
                            inputnumber.setError("Enter up to 366 Days");
                            return;
                        } else {
                            inputnumber.setError(null);
                        }


                        break;
                    case "week":
                        if (val > 54) {
                            inputnumber.setError("Enter up to 54 Weeks");
                            return;
                        } else {
                            inputnumber.setError(null);
                        }

                        break;
                    case "month":

                        if (val > 12) {
                            inputnumber.setError("Enter up to 12 Months");
                            return;
                        } else {
                            inputnumber.setError(null);
                        }
                        break;
                }
            }

            //mobile no validations
            if (appController.findLength(phone_number) < 10) {
                phone_no.setError("Mobile Number should be 10 digits");
                return;
            }
            Boolean valuenew = appController.PhoneNumberValidation(phone_number);
            //  Log.e("value", "  "+value);
            if (!valuenew) {
                phone_no.setError("Enter Valid Mobile Number");

                return;
            }
        }
        //Code to save only unique records

        if (TextUtils.isEmpty(strSymptoms) && TextUtils.isEmpty(strDignosis)) {
            Toast.makeText(getContext(), "Please enter any of Ailemnt,Symptoms or Diagnosis ", Toast.LENGTH_LONG).show();

            return;
        }

        if (current_age != null || current_age.length() > 0) {

            SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            String flag = "0";


            String visit_date = visitDate.getText().toString();
            String added_on = sysdate.toString();

            //convert visit date from 2016-11-1 to 2016-11-01
            try {

                visit_date = myFormat.format(fromUser.parse(visit_date));
                added_on = myFormat.format(fromUser.parse(added_on));
                //usersellectedDate=myFormat.format(fromUser.parse(usersellectedDate));
                if(usersellectedDate!= null && !usersellectedDate.equals("")) {
                    usersellectedDate = myFormat.format(fromUser.parse(usersellectedDate));
                }
                // Log.e("reformattedStrqq", "" + visit_date);

            } catch (ParseException e) {
                e.printStackTrace();
                appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

            String action = "added";
            String patientInfoType = "App";
            String strfollow_up_date = null;
            strailments = ailment.getText().toString().trim();


            String added_by = docId;//INSERTING DOC ID IN ADDED BY COLUMN AS PER PUSHPALDA SAID

            int patient_id = maxPatientIdCount + 1;
            int visit_id = maxVisitId + 1;

            //Add a new patient
                dbController.addPatientPersonalfromLocal(patient_id, docId, strFirstName, middle_name, strLastName, sex, strdate_of_birth, current_age, phone_number, selectedLanguage, patientImagePath, visit_date, doctor_membership_number, flag, patientInfoType, addedTime, added_by, action,
                        strAddress, strCity, strDistrict, strPin, selectedState, selectedPhoneType, stralternatePhone_no, selectedPhoneTypealternate_no, strUid, selectedUidType, selectedIsd_codeType, selectedAlternateNoIsd_codeType,emial_addr);

                dbController.addHistoryPatientRecords(visit_id, patient_id, usersellectedDate, strfollow_up_date, daysSel, fowSel, monthSel, strailments, prescriptionimgPath, clinical_note, added_on, visit_date, docId, doctor_membership_number, flag, addedTime, patientInfoType, added_by, action,
                        strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs, strHeight, strbmi, strSugarFasting,"","");//"" are refredby and to

                dbController.deletePrescriptionImageQueue(prescriptionimgId,prescriptionimgPath);

            Toast.makeText(getContext(), "Patient Record Saved", Toast.LENGTH_LONG).show();

            goToNavigation();
        }
    }

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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view

                    previewPrescriptionCapturedImage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Registration" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


    }
    private void setUpAnimation() {
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        Random r = new Random();
        try {
            if (bannerimgNames.size() == 0) {
                backChangingImages.setVisibility(View.GONE);

            } else {
                if (bannerimgNames.size() > 0) {
                    int n = r.nextInt(bannerimgNames.size());

                    final String url = bannerimgNames.get(n);

                    BitmapDrawable d = new BitmapDrawable(getResources(), "sdcard/BannerImages/" + url + ".png"); // path is ur resultant //image
                    backChangingImages.setImageDrawable(d);

                    backChangingImages.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String action = "clicked";

                            appController.showAdDialog(getContext(), url);
                            appController.saveBannerDataIntoDb(url, getContext(), doctor_membership_number, action,"Quick Add New Records");

                        }
                    });
                    String action = "display";
                    appController.saveBannerDataIntoDb(url, getContext(), doctor_membership_number, action,"Quick Add New Records");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }
    }
    private void previewCapturedImage() {
        try {
            patientImagePath = uriSavedImage.getPath().trim();
            if (!TextUtils.isEmpty(patientImagePath)) {
                if (patientImagePath.length() > 0) {
                    Glide.with(getContext())
                            .load(patientImagePath)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img);

                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    private void previewPrescriptionCapturedImage() {
        try {
            prescriptionimgPath = uriSavedImage.getPath();
            TextView edtprescriptionImgPath=(TextView)view. findViewById(prescriptionImgPath);
            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(uriSavedImage.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd New Record Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    private static Date addDay(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

    //method to add days to current month
    private static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.other_navigation, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
}