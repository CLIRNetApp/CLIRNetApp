package app.clirnet.com.clirnetapp.quickAdd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

import static app.clirnet.com.clirnetapp.R.id.sysdate;


public class QuickAddVisitFragment extends Fragment {

    private View view;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int DATE_DIALOG_ID2 = 2;
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
    private TextView presciptiontext;
    private Button addUpdate;
    private Button cancel;
    private EditText edtInput_sugarfasting;
    private EditText edtInput_bmi;
    private EditText edtInput_height;
    private Button btnclear;
    private ImageView backChangingImages;
    private EditText clinicalNotes;
    private String strAlternatenumber;
    private String strAlternatephtype;
    private String strIsd_code;
    private String strAlternateIsd_code;
    private String patientImagePath;
    private String prescriptionImgPath;
    private EditText visitDate;
    private EditText edtInput_weight;
    private EditText edtInput_pulse;
    private EditText edtInput_bp;
    private EditText edtLowBp;
    private EditText edtInput_temp;
    private EditText edtInput_sugar;
    private MultiAutoCompleteTextView edtSymptoms;
    private MultiAutoCompleteTextView edtDignosis;
    private String strPatientId;

    private OnFragmentInteractionListener mListener;
    private String strPatientPhoto;
    private DatabaseClass databaseClass;
    private LastnameDatabaseClass lastNamedb;
    private String strName;
    private String mPhNo;
    private String mAge;
    private String mLang;
    private String mGender;
    private String addedOnDate;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private String doctor_membership_number;
    private String docId;
    private int maxVisitId;
    private BannerClass bannerClass;
    private ArrayList<String> bannerimgNames;
    private ArrayList<String> mAilmemtArrayList;
    private ArrayList<String> mSymptomsList;
    private AppController appController;
    private EditText ailments;
    private ImageView imageViewprescription;
    private String fowSel = null;
    private String usersellectedDate;
    private String monthSel = null;
    private String addedTime;
    private SimpleDateFormat sdf1;

    private int countvitalsLayout = 1;
    private int countsymtomsanddignostLayout = 1;
    private int countPrescriptiontLayout = 1;
    private String prescriptionimgPath;
    private String prescriptionimgId;
    private String added_on;
    private ArrayList<RegistrationModel> mAssociateList;
    private HashMap<String, String> NameData;
    private ArrayList<String> specialityArray;
    private StringBuilder sb;
    private int addCounter = 0;
    private String strReferedTo;
    private String strReferedBy;
    private Intent imageIntent;
    private File imagesFolder;
    private Uri uriSavedImage;
    private String PrescriptionimageName;
    private TextView edtprescriptionImgPath;
    private String url;

    private TextView textRefredByShow;
    private TextView textRefredToShow;
    private String strReferredById;
    private String strReferredTo1Id;
    private String strReferredTo2Id;
    private String strReferredTo3Id;
    private String strReferredTo4Id;
    private String strReferredTo5Id;
    private String strReferredByName;
    private String strReferredTo1Name;
    private String strReferredTo2Name;
    private String strReferredTo3Name;
    private String strReferredTo4Name;
    private String strReferredTo5Name;
    private ArrayList<String> nameReferalsList;

    public QuickAddVisitFragment() {
        // Required empty public constructor
    }


    public static QuickAddVisitFragment newInstance(String param1, String param2) {
        QuickAddVisitFragment fragment = new QuickAddVisitFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        if (getArguments() != null) {

            this.setRetainInstance(true);

            strName = getArguments().getString("NAME");
            strPatientId = getArguments().getString("PATIENTID");
            strPatientPhoto = getArguments().getString("PATIENTPHOTO");
            prescriptionimgPath = getArguments().getString("PRESCRIPTIONIMG");

            prescriptionimgId = getArguments().getString("PRESCRIPTIONID");
            added_on=getArguments().getString("ADDED_ON");
            strReferedBy=getArguments().getString("REFEREDBY");
            strReferedTo=getArguments().getString("REFEREDTO");
            Log.e("QuckAddVisit"," "+strReferedBy +" "+strReferedTo );


            mPhNo = getArguments().getString("PHONE");
            mAge = getArguments().getString("AGE");
            mLang = getArguments().getString("LANGUAGE");
            mGender = getArguments().getString("GENDER");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quick_add_visit_fraagment, container, false);

        ImageView patientImage = (ImageView) view.findViewById(R.id.patientImage);
        TextView date = (TextView) view.findViewById(sysdate);
        TextView editpatientName = (TextView) view.findViewById(R.id.patientName);
        TextView editage = (TextView) view.findViewById(R.id.age1);
        TextView editmobileno = (TextView) view.findViewById(R.id.mobileno);
        TextView editgender = (TextView) view.findViewById(R.id.gender);
        TextView editlang = (TextView) view.findViewById(R.id.lang);
        Button prescriptionBtn = (Button) view.findViewById(R.id.prescriptionBtn);
        ImageView imgEdit = (ImageView) view.findViewById(R.id.editPersonalInfo);
        cancel = (Button) view.findViewById(R.id.cancel);
        addUpdate = (Button) view.findViewById(R.id.addUpdate);
        txtRecord = (TextView) view.findViewById(R.id.txtRecord);
        txtsymtomsanddignost = (TextView) view.findViewById(R.id.txtsymptomsanddignost);
      // presciptiontext = (TextView) view.findViewById(R.id.presciptiontext);
        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) view.findViewById(R.id.termsandCondition);

        fodtextshow = (BootstrapEditText) view.findViewById(R.id.fodtextshow);
        inputnumber = (BootstrapEditText) view.findViewById(R.id.inputnumber);
        days = (Button) view.findViewById(R.id.days);
        week = (Button) view.findViewById(R.id.week);
        month = (Button) view.findViewById(R.id.month);
        btnclear = (Button) view.findViewById(R.id.btnclear);

        visitDate = (EditText) view.findViewById(R.id.visitDate);

        edtInput_weight = (EditText) view.findViewById(R.id.input_weight);
        edtInput_pulse = (EditText) view.findViewById(R.id.input_pulse);
        edtInput_bp = (EditText) view.findViewById(R.id.input_bp);
        edtLowBp = (EditText) view.findViewById(R.id.lowBp);
        edtInput_temp = (EditText) view.findViewById(R.id.input_temp);
        edtInput_sugar = (EditText) view.findViewById(R.id.input_sugar);
        edtInput_sugarfasting = (EditText) view.findViewById(R.id.input_sugarfasting);
        edtInput_bmi = (EditText) view.findViewById(R.id.input_bmi);
        edtInput_height = (EditText) view.findViewById(R.id.input_height);


        clinicalNotes = (EditText) view.findViewById(R.id.clinicalNotes);

        imageViewprescription = (ImageView) view.findViewById(R.id.imageViewprescription);

        edtSymptoms = (MultiAutoCompleteTextView) view.findViewById(R.id.symptoms);
        edtDignosis = (MultiAutoCompleteTextView) view.findViewById(R.id.dignosis);

        textRefredByShow=(TextView)view.findViewById(R.id.txtrefredby) ;
        textRefredToShow=(TextView)view.findViewById(R.id.txtrefredto) ;

        if (databaseClass == null) {
            databaseClass = new DatabaseClass(getActivity().getApplicationContext());
        }
        if (lastNamedb == null) {
            lastNamedb = new LastnameDatabaseClass(getActivity().getApplicationContext());
        }

        addFollowupdateButtonListner();
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivacyPolicy.class);
                startActivity(intent);
            }
        });
//open TermsCondition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsCondition.class);
                startActivity(intent);
            }
        });
        sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        date.setText("Today's Date " + dd);

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
       // visitDate.setText(addedOnDate);

        editpatientName.setText(strName);
        editmobileno.setText(mPhNo);
        editage.setText(mAge);
        editlang.setText(mLang);
        editgender.setText(mGender);
        visitDate.setText(added_on);

        edtprescriptionImgPath = (TextView) view.findViewById(R.id.prescriptionImgPath);
        edtprescriptionImgPath.setVisibility(View.VISIBLE);
        edtprescriptionImgPath.setText(prescriptionimgPath);

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {

            if (strPatientPhoto.length() > 0) {
                // Bitmap bitmap = BitmapFactory.decodeFile(strPatientPhoto);
                setUpGlide(strPatientPhoto, patientImage);

            }
        }
        try {
            if (sqlController == null) {
                sqlController = new SQLController(getContext());
                sqlController.open();
            }
            dbController = SQLiteHandler.getInstance(getContext());
            //This will get all the visit  history of patient
            appController = new AppController();

            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            docId = sqlController.getDoctorId();
            maxVisitId = sqlController.getPatientVisitIdCount();

            if (bannerClass == null) {
                bannerClass = new BannerClass(getContext());
            }
            bannerimgNames = bannerClass.getImageName();
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
                    if (sbname != null) {
                        textRefredToShow.setText(sbname);
                    } else {
                        textRefredToShow.setText("");
                    }
                }
            }
        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } finally {
            if (sqlController != null) {
                sqlController.close();
            }
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        try {
            ArrayList<String> mDiagnosisList = lastNamedb.getDiagnosis();
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
            appController.appendLog(appController.getDateTime() + " " + "/ " + "QuickAdd Visit Fragment " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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
        prescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();

                prescriptionimgPath = "prescription_" + docId + "_" + appController.getDateTime() + ".jpg";

                File image = new File(imagesFolder, prescriptionimgPath);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
        Button refered = (Button) view.findViewById(R.id.referedby);
        refered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReferedDialogBox();
            }
        });

        setUpAnimation();
        return view;

    }
    private void showReferedDialogBox() {

        final Dialog dialog = new Dialog(new ContextThemeWrapper(getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan));

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View f = factory.inflate(R.layout.refered_by_dialog, null);

        dialog.setTitle("Referred By-To");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(f);
        Button dialogButtonCancel = (Button) f.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) f.findViewById(R.id.customDialogOk);
        final Button addMore = (Button) f.findViewById(R.id.addMore);


        final Spinner nameRefredBySpinner = (Spinner) f.findViewById(R.id.nameRefredBySpinner);
        final Spinner nameRefredTo1Spinner = (Spinner) f.findViewById(R.id.nameRefredTo1Spinner);
        specialityArray = new ArrayList<>();

        try {

            mAssociateList = sqlController.getAssociateDataIdName();
            final ArrayList<HashMap<String, String>> list = sqlController.getAllDataAssociateMaster();

            NameData = new HashMap<>();
            ArrayList<String> abc = new ArrayList<>();

            for (int im = 0; im < list.size(); im++) {
                String strid = list.get(im).get("ID");
                String strName = list.get(im).get("NAME");
                String str = list.get(im).get("SPECIALITY");
                specialityArray.add(str);
                NameData.put(strName, strid);
              //  Log.e("str", " " + strName + "  " + strid + " " + str);

            }

            setSpinner(f);

            nameReferalsList.add(0,"Select Referrals");

            if (strReferedBy != null && !strReferedBy.equals("")) {

                String referedBy = sqlController.getNameByIdAssociateMaster(strReferedBy);
                String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                appController.setSpinnerPosition(nameRefredBySpinner,some_array,referedBy);

                textRefredByShow.setText(referedBy);
            }
            if (strReferedTo != null && !strReferedTo.equals("")) {

                if (strReferedTo.length() > 1) {
                    String delimiter = ",";

                    String[] temp = strReferedTo.split(delimiter);

                    for (String aTemp : temp) {
                        String referedBy = sqlController.getNameByIdAssociateMaster(aTemp);

                        abc.add(referedBy);
                    }
                }
                int size = abc.size();

                if (size > 0) {

                    String idNo= abc.get(0);
                    String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                    appController.setSpinnerPosition(nameRefredTo1Spinner,some_array,idNo);
                } else {
                    String referedTo = sqlController.getNameByIdAssociateMaster(strReferedTo);
                    String[] some_array = nameReferalsList.toArray(new String[nameReferalsList.size()]);
                    appController.setSpinnerPosition(nameRefredTo1Spinner,some_array,referedTo);
                }

            }


            } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Registration" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
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

                    if (!strReferredTo1Name.equals("") && strReferredTo1Name.length() > 0) {
                        code = NameData.get(strReferredTo1Name);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(index);
                            sbname.append(strReferredTo1Name);
                        }
                    }
                }
                if (addCounter >= 1) {

                    if (!strReferredTo2Name.equals("") && strReferredTo2Name.length() > 0) {
                        code = NameData.get(strReferredTo2Name);
                        if (code != null) {

                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo2Name);
                        }
                    }
                }
                if (addCounter >= 2) {

                    if (!strReferredTo3Name.equals("") && strReferredTo3Name.length() > 0) {
                        code = NameData.get(strReferredTo3Name);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo3Name);
                        }
                    }
                }
                if (addCounter >= 3) {

                    if (!strReferredTo4Name.equals("") && strReferredTo4Name.length() > 0) {
                        code = NameData.get(strReferredTo4Name);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo4Name);
                        }
                    }
                }
                if (addCounter >= 4) {

                    if (!strReferredTo5Name.equals("") && strReferredTo5Name.length() > 0) {
                        code = NameData.get(strReferredTo5Name);
                        if (code != null) {
                            int index = Integer.parseInt(code);
                            sb.append(",").append(index);
                            sbname.append(",").append(strReferredTo5Name);
                        }
                    }
                }

                strReferedTo = String.valueOf(sb);

                if (!strReferredByName.equals("") && strReferredByName.length() > 0) {
                    code = NameData.get(strReferredByName);
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
                insertedName=appController.removeCommaOccurance(insertedName);

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
                    Toast.makeText(getContext(), "Limit Exceed! You can not add more", Toast.LENGTH_LONG).show();
                }

            }
        });


        dialog.show();
    }

    private void setSpinner( View f) {

        final Spinner nameRefredBySpinner = (Spinner) f.findViewById(R.id.nameRefredBySpinner);
        final Spinner nameRefredTo1Spinner = (Spinner) f.findViewById(R.id.nameRefredTo1Spinner);
        final Spinner nameRefredTo2Spinner = (Spinner) f.findViewById(R.id.nameRefredTo2Spinner);
        final Spinner nameRefredTo3Spinner = (Spinner) f.findViewById(R.id.nameRefredTo3Spinner);
        final Spinner nameRefredTo4Spinner = (Spinner) f.findViewById(R.id.nameRefredTo4Spinner);
        final Spinner nameRefredTo5Spinner = (Spinner) f.findViewById(R.id.nameRefredTo5Spinner);



        final TextView refredtoSpeciality1 = (TextView) f.findViewById(R.id.refredtoSpeciality1);
        final TextView refredtoSpeciality2 = (TextView) f.findViewById(R.id.refredtoSpeciality2);
        final TextView refredtoSpeciality3 = (TextView) f.findViewById(R.id.refredtoSpeciality3);
        final TextView refredtoSpeciality4 = (TextView) f.findViewById(R.id.refredtoSpeciality4);
        final TextView refredtoSpeciality5 = (TextView) f.findViewById(R.id.refredtoSpeciality5);
        final TextView refredBySpeciality = (TextView) f.findViewById(R.id.refredBySpeciality);
        try {
            nameReferalsList = dbController.getReferals();

            if (nameReferalsList.size() > 0) {
                ArrayAdapter<String> referralName = new ArrayAdapter<>(getContext(),
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
        } catch (ClirNetAppException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + " AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        nameRefredTo1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo1Name= (String) parent.getItemAtPosition(position);
                try {
                    if(nameRefredTo1Spinner.getSelectedItem() == "Select Referrals")
                    {

                    }
                    else {
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo1Name);

                        strReferredTo1Id = list.get(0).get("ID");
                        String strSpeclty = list.get(0).get("SPECIALITY");
                        refredtoSpeciality1.setText(strSpeclty);
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        nameRefredTo2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo2Name= (String) parent.getItemAtPosition(position);
                try {
                    if(nameRefredTo2Spinner.getSelectedItem() == "Select Referrals")
                    {

                    }
                    else {
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo2Name);

                        strReferredTo2Id = list.get(0).get("ID");
                        String strSpeclty = list.get(0).get("SPECIALITY");
                        refredtoSpeciality2.setText(strSpeclty);
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        nameRefredTo3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo3Name= (String) parent.getItemAtPosition(position);
                try {
                    if(nameRefredTo3Spinner.getSelectedItem() == "Select Referrals")
                    {

                    } else {
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo3Name);

                        strReferredTo3Id = list.get(0).get("ID");
                        String strSpeclty = list.get(0).get("SPECIALITY");
                        refredtoSpeciality3.setText(strSpeclty);
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        nameRefredTo4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo4Name= (String) parent.getItemAtPosition(position);
                try {
                    if(nameRefredTo4Spinner.getSelectedItem() == "Select Referrals")
                    {

                    } else {
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo4Name);

                        strReferredTo4Id = list.get(0).get("ID");
                        String strSpeclty = list.get(0).get("SPECIALITY");
                        refredtoSpeciality4.setText(strSpeclty);
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        nameRefredTo5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredTo5Name= (String) parent.getItemAtPosition(position);
                try {
                    if(nameRefredTo5Spinner.getSelectedItem() == "Select Referrals")
                    {

                    }
                    else {
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredTo5Name);

                        strReferredTo5Id = list.get(0).get("ID");
                        String strSpeclty = list.get(0).get("SPECIALITY");
                        refredtoSpeciality5.setText(strSpeclty);
                    }
                } catch (ClirNetAppException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        nameRefredBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                strReferredByName = (String) parent.getItemAtPosition(position);
                try {
                    if(nameRefredBySpinner.getSelectedItem() == "Select Referrals")
                    {

                    }
                    else {
                        ArrayList<HashMap<String, String>> list = sqlController.getIdNameDataAssociateMaster(strReferredByName);

                        strReferredById = list.get(0).get("ID");
                        String strSpeclty = list.get(0).get("SPECIALITY");
                        refredBySpeciality.setText(strSpeclty);
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
    //saved the user enetred data to db
    private void saveData() {

        // String daysSel = null;
        monthSel = null;
        fowSel = null;

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
        // String strTests = edtTest.getText().toString().trim();
        //  String strDrugs = edtDrugs.getText().toString().trim();

        String strHeight = edtInput_height.getText().toString().trim();
        String strbmi = edtInput_bmi.getText().toString().trim();
        String strSugarFasting = edtInput_sugarfasting.getText().toString().trim();


        usersellectedDate = fodtextshow.getText().toString();

        if (TextUtils.isEmpty(strSymptoms) && TextUtils.isEmpty(strDignosis)) {
            Toast.makeText(getContext(), "Please enter any of Symptoms or Diagnosis ", Toast.LENGTH_LONG).show();
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


        String visit_id = String.valueOf(maxVisitId + 1);
        String visit_date = addedOnDate;
        //  Log.e("visit_date", "" + visit_date);

        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        // String visit_date1 = addedOnDate.toString();
        String added_on = visitDate.getText().toString();

        try {
            //convert visit date from 2016-11-1 to 2016-11-01
            visit_date = myFormat.format(fromUser.parse(added_on));
            added_on = myFormat.format(fromUser.parse(addedOnDate));
            //usersellectedDate=myFormat.format(fromUser.parse(usersellectedDate));
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
        String strTests = null, strDrugs = null, ailment = null;
        String record_source="QuickAdd New Visit";

        //  dbController.updatePatientPersonalforNewVisit(strPatientId, "2", modified_on.toString());//thiis will update pateint data for new visit
        dbController.addPatientNextVisitRecord(visit_id, strPatientId, usersellectedDate, follow_up_dates, daysSel, fowSel, monthSel, clinical_note, prescriptionimgPath, ailment, visit_date, docId, doctor_membership_number, added_on, addedTime, flag, added_by, action, patientInfoType,
                strWeight, strPulse, strBp, strLowBp, strTemp, strSugar, strSymptoms, strDignosis, strTests, strDrugs, strHeight, strbmi, strSugarFasting, strReferedBy,strReferedTo,record_source);

        dbController.deletePrescriptionImageQueue(prescriptionimgId, prescriptionimgPath);

        Toast.makeText(getContext(), "Patient Record Updated", Toast.LENGTH_LONG).show();
        //Redirect to navigation Activity
        goToNavigation();

    }

    private void goToNavigation() {
        Intent i = new Intent(getActivity(), NavigationActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void setUpGlide(String imgPath, ImageView patientImage) {

        Glide.with(getContext())
                .load(imgPath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .dontAnimate()
                .error(R.drawable.main_profile)
                .into(patientImage);
    }

    private void addFollowupdateButtonListner() {

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

    private void shpwDialog(int id) {
        switch (id) {

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
                        }, mYear3, mMonth3, mDay3);
                c3.add(Calendar.DATE, 1);

                Date newDate3 = c3.getTime();
                dpd3.getDatePicker().setMinDate(newDate3.getTime());

                dpd3.show();

                break;
        }
    }
    private void setUpAnimation() {
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        try {
            bannerimgNames = bannerClass.getImageName();
            appController.setUpAdd(getContext(),bannerimgNames,backChangingImages,doctor_membership_number,"Quick Add New Records");

        } catch (ClirNetAppException e) {
            e.printStackTrace();
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
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AppController.getInstance().trackScreenView("Quick Add Visit Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
         view=null;
        if (databaseClass != null) {
            databaseClass.close();
            databaseClass = null;
        }
        if (lastNamedb != null) {
            lastNamedb.close();
            lastNamedb = null;
        }
        if (dbController != null) {
            dbController.close();
            dbController = null;
        }
        if (bannerClass != null) {
            bannerClass.close();
            bannerClass = null;
        }
        if(appController!=null){
            appController=null;
        }
        backChangingImages=null;
        mSymptomsList=null;
        doctor_membership_number = null;
        docId = null;
        sdf1 = null;
        strName = null;
        mPhNo = null;
        mAge = null;
        mLang = null;
        mGender = null;
        addedOnDate = null;
        strPatientPhoto = null;

        clinicalNotes = null;

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

        prescriptionimgPath = null;
        prescriptionimgId = null;
        backChangingImages = null;

        addUpdate = null;
        cancel = null;

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
        presciptiontext = null;
        edtInput_sugarfasting = null;
        edtInput_bmi = null;
        edtInput_height = null;
        btnclear = null;
        strAlternatenumber = null;
        strAlternatephtype = null;
        strIsd_code = null;
        strAlternateIsd_code = null;
        edtprescriptionImgPath=null;
        url=null;

        textRefredByShow=null;
        textRefredToShow=null;
        strReferredById= null;
        strReferredTo1Id= null;
        strReferredTo2Id= null;
        strReferredTo3Id= null;
        strReferredTo4Id= null;
        strReferredTo5Id= null;
        strReferredByName= null;
        strReferredTo1Name= null;
        strReferredTo2Name= null;
        strReferredTo3Name= null;
        strReferredTo4Name= null;
        strReferredTo5Name= null;
        nameReferalsList=null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
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
            edtprescriptionImgPath.setVisibility(View.VISIBLE);
            edtprescriptionImgPath.setText(prescriptionImgPath);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "AddPatientUpdate" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

}
