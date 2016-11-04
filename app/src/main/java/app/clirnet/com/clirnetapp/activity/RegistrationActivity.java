package app.clirnet.com.clirnetapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;

public class RegistrationActivity extends AppCompatActivity {

    private static final int DATE_DIALOG_ID = 0;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private EditText firstName;
    private EditText middleName;
    private AutoCompleteTextView lastName;
    private EditText date_of_birth;
    private EditText phone_no;
    private EditText age;
    private EditText follow_up_date;
    private EditText follow_up_days;
    private EditText follow_up_weeks;
    private EditText follow_up_Months;
    private ImageView patientimage;
    private ImageView imageViewprescription;
    private EditText clinical_Notes;
    private RadioGroup radioSexGroup;
    private MultiAutoCompleteTextView multiAutoComplete;
    private StringBuilder sysdate;
    private SQLController sqlController;
    private SQLiteHandler dbController;
    private ArrayList<String> mAilmemtArrayList;
    private List<String> lastNameList;
    private String selectedLanguage;

    private Intent imageIntent;
    private File imagesFolder;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String imageName;
    private Uri uriSavedImage;

    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1 = 1889;
    private int pateintage;
    private String follow_up_dates;
    private String prescriptionImgPath;
    private String patientImagePath;
    private String followupdateSellected;
    private String usersellectedDate;

    private String monthSel;
    private String fowSel;
    private ArrayList<String> mLastNameList;
    private TextView showfodtext;
    private String dt;
    private int maxPatientIdCount;
    private int maxVisitId;
    private SimpleDateFormat sdf1;
    private String doctor_membership_number;
    private String docId;
    private String addedTime;
    private DatabaseClass databaseClass;
    private final int[] imageArray = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    private ImageView backChangingImages;
    private int maxid;//this is for ailments table
    private LastnameDatabaseClass lastNamedb;
    private int maxLastId;
    Spinner language;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        databaseClass = new DatabaseClass(RegistrationActivity.this);

        lastNamedb = new LastnameDatabaseClass(RegistrationActivity.this);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#fffff'>Add New Patient</font>"));

        lastNameList = new ArrayList<>();
        String seacrhNumber = getIntent().getStringExtra("number");

        firstName = (EditText) findViewById(R.id.firstname);
        middleName = (EditText) findViewById(R.id.middlename);
        lastName = (AutoCompleteTextView) findViewById(R.id.lastname);
        date_of_birth = (EditText) findViewById(R.id.dob);
        phone_no = (EditText) findViewById(R.id.mobile_no);
        age = (EditText) findViewById(R.id.age);
        language = (Spinner) findViewById(R.id.language);
        follow_up_date = (EditText) findViewById(R.id.follow_up_date);
        follow_up_days = (EditText) findViewById(R.id.follow_up_days);
        follow_up_weeks = (EditText) findViewById(R.id.follow_up_weeks);
        follow_up_Months = (EditText) findViewById(R.id.follow_up_Months);
        // ailment= (MultiAutoCompleteTextView) view.findViewById(R.id.ailments);
        patientimage = (ImageView) findViewById(R.id.patientimage);
        Button addPatientImgBtn = (Button) findViewById(R.id.addPatientImgBtn);
        Button prescriptionBtn = (Button) findViewById(R.id.prescriptionBtn);
        imageViewprescription = (ImageView) findViewById(R.id.imageViewprescription);
        clinical_Notes = (EditText) findViewById(R.id.cliniclaNotes);
        Button add = (Button) findViewById(R.id.add);
        Button cancel = (Button) findViewById(R.id.cancel);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGender);
        multiAutoComplete = (MultiAutoCompleteTextView) findViewById(R.id.ailments);

        showfodtext = (TextView) findViewById(R.id.fodtextshow);

        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);

        phone_no.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only
        phone_no.setText(seacrhNumber);

        TextView privacyPolcicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);
//open privacy poilicy page
        privacyPolcicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, PrivacyPolicy.class);
                startActivity(intent);
                finish();

            }
        });
        //open Terms and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistrationActivity.this, TermsCondition.class);
                startActivity(intent);
                finish();

            }
        });


//start the change image thread
        setUpAnimation();

        sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);


        sysdate = new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append("");


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date todayDate = new Date();


        //this date is ued to set update records date in patient history table
        addedTime = sdf.format(todayDate);
        Log.e("date", "" + addedTime);


        try {

            sqlController = new SQLController(RegistrationActivity.this);
            sqlController.open();
            dbController = new SQLiteHandler(RegistrationActivity.this);
            //get doctor membership id
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
//get doctor  id
            docId = sqlController.getDoctorId();
            Log.e("docId",""+docId);
            //this will give us a max of patient_id from patient records which will help to store records locally
            maxPatientIdCount = sqlController.getPatientIdCount();
            maxVisitId = sqlController.getPatientVisitIdCount();

        } catch (Exception e) {
            e.printStackTrace();
        }


//set languages to spinner
        setLanguageSpiner();


        // dbController.open();


        try {
            databaseClass.openDataBase();
            int nameId = lastNamedb.getMaxLastNameId();
            maxLastId = nameId + 1;
            int id = databaseClass.getMaxAimId();
            maxid = id + 1;

            lastNamedb.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Cursor cursor = null;
        //this will populate ailments  from asset folder ailment table
        try {
            cursor = databaseClass.getAilmentsList();
            mAilmemtArrayList = new ArrayList<>();
            int columnIndex = cursor.getColumnIndex("ailment_name");
            while (cursor.moveToNext()) {
                mAilmemtArrayList.add(cursor.getString(columnIndex)); //add the item
                // Log.e("ali", "ali is:" + cursor.getString(columnIndex));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        //this code is for setting list to auto complete text view  8/6/16
        ArrayAdapter<String> adp = new ArrayAdapter<>(RegistrationActivity.this,
                android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        multiAutoComplete.setThreshold(1);

        multiAutoComplete.setAdapter(adp);

        Cursor cursor1 = null;
        try {
            cursor1 = lastNamedb.getLastNameList();
            mLastNameList = new ArrayList<>();
            int columnIndex = cursor1.getColumnIndex("last_name");
            while (cursor1.moveToNext()) {
                mLastNameList.add(cursor1.getString(columnIndex)); //add the item
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor1 != null) {
                cursor1.close();
            }
        }
        //this code is for setting list to auto complete text view
        ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(RegistrationActivity.this,
                android.R.layout.simple_dropdown_item_1line, mLastNameList);

        multiAutoComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        lastName.setThreshold(1);
        lastName.setAdapter(lastnamespin);

        // when the user clicks an item of the drop-down list

        multiAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position,

                                    long rowId) {


                  /*  Toast.makeText(RegistrationActivity.this, "MultiAutoComplete: " +

                                    "you add ailment " + parent.getItemAtPosition(position),

                            Toast.LENGTH_LONG).show();*/


            }

        });

//select the language
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedLanguage = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

//save the data into db
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    insertIntoDB();
                    //  finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // frameLayout.setVisibility(View.VISIBLE);


                goToNavigation();

                // Toast.makeText(getContext(),"chala  jana bhai",Toast.LENGTH_SHORT).show();
            }
        });


        date_of_birth.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                shpwDialog(DATE_DIALOG_ID);

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
                if (!hasFocus) {
                    /*follow_up_days.getText().clear();
                    follow_up_weeks.getText().clear();
                    follow_up_Months.getText().clear();
                    Toast.makeText(EditPatientUpdate.this, "fod" + follow_up_date.getText().toString(), Toast.LENGTH_SHORT).show();*/
                    showfodtext.setText(follow_up_date.getText().toString());
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

                        showfodtext.setText(dateis);
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


                        int fow = Integer.parseInt(follow_up_weeks.getText().toString());
                        fowSel = String.valueOf(fow);

                        int daysFromWeeks = fow * 7;
                        String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), daysFromWeeks));
                        showfodtext.setText(dateis);
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


                        int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
                        monthSel = String.valueOf(monthselected);


                        String dateis = sdf1.format(RegistrationActivity.addMonth(new Date(), monthselected));
                        showfodtext.setText(dateis);
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

                shpwDialog(DATE_DIALOG_ID1);

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

            }
        });

        follow_up_days.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                try {


                    int days = Integer.parseInt(follow_up_days.getText().toString());
                    String dateis = sdf1.format(RegistrationActivity.addDay(new Date(), days));

                    showfodtext.setText(dateis);
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
                    showfodtext.setText(dateis);
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
                    showfodtext.setText(dateis);

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


        //used to set fod to text view 20-8-16 ashish u
        multiAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getDataFollowUpDate();
                    showfodtext.setText(dt);

                }
            }
        });
//capture patient image
        addPatientImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();
                first_name = firstName.getText().toString().trim();
                middle_name = middleName.getText().toString().trim();
                last_name = lastName.getText().toString().trim();

                if (TextUtils.isEmpty(first_name)) {
                    firstName.setError("Please enter First Name !");
                    return;
                }

                if (TextUtils.isEmpty(last_name)) {
                    lastName.setError("Please enter Last Name !");
                    return;
                }

                imageName = "img_" + first_name + "_" + last_name + ".png";


                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        //capture prescription image
        prescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();
                first_name = firstName.getText().toString().trim();
                middle_name = middleName.getText().toString().trim();
                last_name = lastName.getText().toString().trim();

                if (TextUtils.isEmpty(first_name)) {
                    firstName.setError("Please enter First Name !");
                    return;
                }

                if (TextUtils.isEmpty(last_name)) {
                    lastName.setError("Please enter Last Name !");
                    return;
                }


                imageName = "prescription_" + first_name + "_" + last_name + ".png";


                File image = new File(imagesFolder, imageName);
                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1);

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent i = new Intent(RegistrationActivity.this, NavigationActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    private void setLanguageSpiner() {
        ArrayAdapter<CharSequence> language_reasonAdapter = ArrayAdapter
                .createFromResource(RegistrationActivity.this, R.array.language_group,
                        android.R.layout.simple_spinner_item);
        language_reasonAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Apply the adapter to the spinner commented fro
        language.setAdapter(language_reasonAdapter);
    }

    //start changing image into some time interval
    private void setUpAnimation() {


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


    private void getDataFollowUpDate() {

        follow_up_dates = follow_up_date.getText().toString().trim();

        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_days.getText().toString().length() == 0 & follow_up_date.getText().toString().length() > 0) {
            followupdateSellected = follow_up_date.getText().toString();

            dt = followupdateSellected;
            /*Toast.makeText(RegistrationActivity.this, "Date is:!" + followupdateSellected, Toast.LENGTH_LONG).show();*/

        }


        // Date date= cal.getTime();

        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_days.getText().toString().length() > 0) {
            followupdateSellected = follow_up_days.getText().toString();

            int days = Integer.parseInt(follow_up_days.getText().toString());
            // int days = fow + day2;


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");


            dt = sdf1.format(addDay(new Date(), days));


        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() > 0) {

            followupdateSellected = follow_up_weeks.getText().toString();

            //Used to conert weeks into date
            int fow = Integer.parseInt(follow_up_weeks.getText().toString());
            fowSel = String.valueOf(fow);

            int daysFromWeeks = fow * 7;


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");


            dt = sdf1.format(addDay(new Date(), daysFromWeeks));


        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() > 0) {
            followupdateSellected = follow_up_Months.getText().toString();

            int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
            monthSel = String.valueOf(monthselected);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            dt = sdf.format(addMonth(new Date(), monthselected));


        }

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
                 /*   customToast("User cancelled image capture");*/
                } else {
                    // failed to capture image
                 /*   customToast("Sorry! Failed to capture image");*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE1) {

                if (resultCode == Activity.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewPrescriptionCapturedImage();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // user cancelled Image capture
                  /*  customToast("User cancelled image capture");*/
                } else {
                    // failed to capture image
                    /*customToast("Sorry! Failed to capture image");*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //show captured Prescription image into image view

    private void previewPrescriptionCapturedImage() {
        try {
            //cap_img.setVisibility(View.GONE);
            // setpic.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 5;
            final Bitmap bitmap = BitmapFactory.decodeFile(uriSavedImage.getPath(),
                    options);

            //  Toast.makeText(RegistrationActivity.this,"Image Path="+uriSavedImage.getPath().toString(),Toast.LENGTH_LONG).show();

            prescriptionImgPath = uriSavedImage.getPath();

           /* Toast.makeText(RegistrationActivity.this, "Prescription Image Path=" + prescriptionImgPath, Toast.LENGTH_LONG).show();*/

            imageViewprescription.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //show captured patient image into image view
    private void previewCapturedImage() {
        try {

            patientImagePath = uriSavedImage.getPath().trim();
            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {
                if (patientImagePath.length() > 0) {
                    Glide.with(RegistrationActivity.this)
                            .load(patientImagePath)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(patientimage);
                }
            }

            /*Toast.makeText(RegistrationActivity.this, "Patient Image Path=" + patientImagePath, Toast.LENGTH_LONG).show();*/

            //patientimage.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //show date seletor dialog box
    private void shpwDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);

                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd1 = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date_of_birth.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                                // we calculate age from dob and set to text view.
                                pateintage = getAge(year, monthOfYear, dayOfMonth);
                                String ageid = String.valueOf(pateintage);
                                age.setText(ageid);
                                age.setEnabled(false);//this will set edit text editable false iof dob is present


                            }

                        }, mYear2, mMonth2, mDay2);
                dpd1.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpd1.show();
                //show age of pateint

                break;

            case DATE_DIALOG_ID1:
                final Calendar c1 = Calendar.getInstance();
                int mYear1 = c1.get(Calendar.YEAR);
                int mMonth1 = c1.get(Calendar.MONTH);
                int mDay1 = c1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd2 = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                                String amol = sdf1.format(c1.getTime());
                                Log.e("qwerty", "" + amol);
                                follow_up_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                showfodtext.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear1, mMonth1, mDay1);
                c1.add(Calendar.DATE, 1);
                Date newDate = c1.getTime();
                dpd2.getDatePicker().setMinDate(newDate.getTime());

                dpd2.show();

                break;
        }
    }

    //to get age from date
    public int getAge(int year, int monthOfYear, int dayOfMonth) {
        Date now = new Date();
        int nowMonths = now.getMonth() + 1;
        int nowDate = now.getDate();
        int nowYear = now.getYear() + 1900;
        int result = nowYear - year;

        if (monthOfYear > nowMonths) {
            result = 0;
        } else if (dayOfMonth == nowMonths) {
            if (dayOfMonth > nowDate) {
                result = 0;
            }
        }
        return result;
    }

    //method to save data into db
    private void insertIntoDB() throws ParseException {

        String daysSel = null;
        monthSel = null;
        fowSel = null;

        first_name = firstName.getText().toString().trim();
        middle_name = middleName.getText().toString().trim();
        last_name = lastName.getText().toString().trim();
        String strdate_of_birth = date_of_birth.getText().toString().trim();
        String phone_number = phone_no.getText().toString().trim();
        String current_age = age.getText().toString().trim();

        String ailments = multiAutoComplete.getText().toString().trim();


        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
        //  Toast.makeText(RegistrationActivity.this, " Gender" + radioSexButton.getText().toString(), Toast.LENGTH_SHORT).show();
        String sex = radioSexButton.getText().toString();

        // dofollowupValidations();


        //  String languages = language.getText().toString().trim();
        follow_up_dates = follow_up_date.getText().toString().trim();

        // prescriptions = precription.getText().toString().trim();
        String clinical_note = clinical_Notes.getText().toString().trim();
        int length = current_age.length();

        Log.e("counter", "   " + length);
        if (length >= 4) {
            age.setError("Please enter Valid Age !");
            return;
        }
        if (TextUtils.isEmpty(first_name)) {
            firstName.setError("Please enter First Name !");
            return;
        }

        if (TextUtils.isEmpty(last_name)) {
            lastName.setError("Please enter Last Name !");
            return;
        }
        lastNameList.add(last_name);
        if (!isDuplicate(mLastNameList, last_name)) {

            //dbController.addLastName(last_name);
            lastNamedb.addLastName(last_name, maxLastId);

        }
        if (TextUtils.isEmpty(phone_number)) {
            phone_no.setError("Please enter Phone Number !");

            return;
        }


//mobile no validations
        if (phone_number.length() < 10) {

            Toast.makeText(RegistrationActivity.this, "Phone Number Should be of 10 digit! ", Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(current_age)) {
            age.setError("Please enter Age !");
            return;
        }
        if (current_age == String.valueOf(0)) {


            age.setError("Please enter proper date");
            date_of_birth.setError("Please enter proper date");
        }


        //Code to save only unique records

        if (TextUtils.isEmpty(ailments)) {
            multiAutoComplete.setError("Please enter Ailment !");
            return;
        }
        String delimiter = ",";
        String[] temp = ailments.split(delimiter);
             /* print substrings */
        for (String aTemp : temp) {
            //System.out.println(temp[i]);

            if (!isDuplicate(mAilmemtArrayList, aTemp.trim())) {


                databaseClass.addAilments(aTemp, maxid);
                maxid = maxid + 1;


            } else {

                mAilmemtArrayList.remove(aTemp);// this will remove selected item from list so that it will appear second as per pushpal's demand 31-8-16 Ashish

            }
        }


        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_days.getText().toString().length() == 0 & follow_up_date.getText().toString().length() > 0) {
            followupdateSellected = follow_up_date.getText().toString();

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            Date date1 = sdf1.parse(followupdateSellected);

            usersellectedDate = sdf1.format(date1);


        }

        // Date date= cal.getTime();

        if (follow_up_weeks.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_days.getText().toString().length() > 0) {
            followupdateSellected = follow_up_days.getText().toString();

            int days = Integer.parseInt(follow_up_days.getText().toString());
            //

            daysSel = String.valueOf(days);

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");


            usersellectedDate = sdf1.format(addDay(new Date(), days));


        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() > 0) {

            followupdateSellected = follow_up_weeks.getText().toString();

            //Used to conert weeks into date
            int fow = Integer.parseInt(follow_up_weeks.getText().toString());
            fowSel = String.valueOf(fow);

            int daysFromWeeks = fow * 7;


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");


            usersellectedDate = sdf1.format(addDay(new Date(), daysFromWeeks));

        }

        if (follow_up_days.getText().toString().length() == 0 & follow_up_weeks.getText().toString().length() == 0 & follow_up_date.getText().toString().length() == 0 & follow_up_Months.getText().toString().length() > 0) {
            followupdateSellected = follow_up_Months.getText().toString();

            int monthselected = Integer.parseInt(follow_up_Months.getText().toString());
            monthSel = String.valueOf(monthselected);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            usersellectedDate = sdf.format(addMonth(new Date(), monthselected));


        }


        String patient_id = String.valueOf(maxPatientIdCount + 1);


        String visit_id = String.valueOf(maxVisitId + 1);


        if (current_age != null || current_age.length() > 0) {


            String flag = "0";
            String visit_date = sysdate.toString();
            String added_by = docId;//INSERTING DOC ID IN ADDED BY COLUMN AS PER PUSHPAL SAID
            String action = "added";
            String patientInfoType = "App";
            dbController.addPatientPersonalfromLocal(patient_id, docId, first_name, middle_name, last_name, sex, strdate_of_birth, current_age, phone_number, selectedLanguage, patientImagePath, sysdate.toString(), doctor_membership_number, flag, patientInfoType, addedTime, added_by, action);

            dbController.addHistoryPatientRecords(visit_id, patient_id, usersellectedDate, follow_up_dates, daysSel, fowSel, monthSel, ailments, prescriptionImgPath, clinical_note, sysdate.toString(), visit_date, docId, doctor_membership_number, flag, addedTime, patientInfoType, added_by, action);


            Toast.makeText(RegistrationActivity.this, "Patient Record Saved Successfully!!!", Toast.LENGTH_LONG).show();

            goToNavigation();
        }
    }

    //Method to validate if there is allready records in db to check
    public boolean isDuplicate(List<String> col, String value) {
        boolean isDuplicate = false;
        for (String s : col) {
            if (s.equals(value)) {
                isDuplicate = true;
                break;
            }
        }
        return isDuplicate;
    }

    //Custom Toast.......................................
    public void customToast(CharSequence charSequence) {

        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(RegistrationActivity.this, charSequence, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //method to add days to current day;s
    public static Date addDay(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

    //method to add days to current month
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (backChangingImages != null) {
            // backChangingImages=null;
            backChangingImages.setImageDrawable(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Android", "The onDestroy() event");
        // session.setLogin(false);
        if(dbController !=null){
            dbController.close();//Close the all database connection opened here 31/10/2008 By. Ashish
        }
        if(databaseClass !=null){
            databaseClass.close();//Close the all database connection opened here 31/10/2008 By. Ashish
        }
        if(lastNamedb !=null){
            lastNamedb.close();//Close the all database connection opened here 31/10/2008 By. Ashish
        }
        cleanResources();
        System.gc();
    }

    private void cleanResources() {
        firstName = null;
        middleName = null;
        lastName = null;
        date_of_birth = null;
        phone_no = null;
        age = null;
        follow_up_date = null;
        follow_up_days = null;
        follow_up_weeks = null;
        follow_up_Months = null;
        patientimage = null;
        imageViewprescription = null;
        clinical_Notes = null;
        radioSexGroup = null;
        multiAutoComplete = null;
        sysdate = null;

        selectedLanguage = null;
        imageIntent = null;
        imagesFolder = null;
        first_name = null;
        middle_name = null;
        last_name = null;
        imageName = null;
        uriSavedImage = null;
        follow_up_dates = null;
        prescriptionImgPath = null;
        patientImagePath = null;
        followupdateSellected = null;
        usersellectedDate = null;
        monthSel = null;
        fowSel = null;
        mLastNameList = null;
        showfodtext = null;
        dt = null;
        sdf1 = null;
        doctor_membership_number = null;
        docId = null;
        addedTime = null;
        language = null;
    }

    private void goToNavigation() {
        Intent i = new Intent(RegistrationActivity.this, NavigationActivity.class);
        startActivity(i);
        finish();
    }
}
