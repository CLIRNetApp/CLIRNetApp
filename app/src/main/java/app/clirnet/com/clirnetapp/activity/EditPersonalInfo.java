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
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;

public class EditPersonalInfo extends AppCompatActivity {


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int DATE_DIALOG_ID = 0;
    private final int[] imageArray = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
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
    private Spinner language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
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

        patientImage = (ImageView) findViewById(R.id.patientImage);
        editfirstname = (EditText) findViewById(R.id.firstname);
        editmiddlename = (EditText) findViewById(R.id.middlename);
        editlasttname = (AutoCompleteTextView) findViewById(R.id.lastname);
        editdob = (EditText) findViewById(R.id.dob);
        editage = (EditText) findViewById(R.id.age);
        editmobile_no = (EditText) findViewById(R.id.mobile_no);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGender);
        language = (Spinner) findViewById(R.id.language);
        Button save = (Button) findViewById(R.id.save);
        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);
        TextView date = (TextView) findViewById(R.id.sysdate);

        editfirstname.setText(strFirstName);
        editmiddlename.setText(strMiddleName);
        editlasttname.setText(strLastName);
        // editdob.setText(strDob);
        editage.setText(strAge);

        editmobile_no.setInputType(InputType.TYPE_CLASS_NUMBER);//this will do not let user to enter any other text than digit 0-9 only
        editmobile_no.setText(strPhone);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM,yyyy");
        Date todayDate1 = new Date();

        String dd = sdf1.format(todayDate1);
        date.setText("Today's Date " + dd);


        //this date is ued to set update records date in patient history table
        modified_on_date = sdf.format(todayDate);


        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
        Date todayDate3 = new Date();


        //this date is ued to set update records date in patient history table
        modifiedTime = sdf3.format(todayDate3);


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
            Log.e("docId", "" + docId);
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
            radioSexGroup.check(R.id.male);
        } else {
            radioSexGroup.check(R.id.female);
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
        }

        //set Language value to  spinner
        setLanguageSpinnerAdapters();
        Cursor cursor = null;
        try {

            if (sqlController == null) {
                sqlController = new SQLController(getApplicationContext());
                sqlController.open();
            }

            lastNamedb.openDataBase();

            cursor = lastNamedb.getLastNameList();
            mLastNameList = new ArrayList<>();
            int columnIndex = cursor.getColumnIndex("last_name");
            while (cursor.moveToNext()) {
                mLastNameList.add(cursor.getString(columnIndex)); //add the item

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if(lastNamedb !=null){
                lastNamedb.close();
            }
            if(sqlController !=null){
                sqlController.close();
            }

        }


        setLastnameSpinner();


        Button cancel = (Button) findViewById(R.id.cancel);
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


        patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagesFolder = new File(Environment.getExternalStorageDirectory(), "PatientsImages");
                imagesFolder.mkdirs();
                first_name = editfirstname.getText().toString().trim();
                middle_name = editmiddlename.getText().toString().trim();
                last_name = editlasttname.getText().toString().trim();


                if (TextUtils.isEmpty(first_name)) {
                    editfirstname.setError("Please enter First Name !");
                    return;
                }

                if (TextUtils.isEmpty(last_name)) {
                    editmiddlename.setError("Please enter Last Name !");
                    return;
                }


                if (TextUtils.isEmpty(last_name)) {
                    editmiddlename.setError("Please enter Last Name !");
                    return;
                }

                imageName = "imgs_" + first_name + "_" + last_name + ".png";


                File image = new File(imagesFolder, imageName);
                //  boolean deleted = image.delete();
                // Log.e("bool",""+deleted);

                uriSavedImage = Uri.fromFile(image);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                imageIntent.putExtra("data", uriSavedImage);
                startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
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
                String editAge = editage.getText().toString();
                String editPno = editmobile_no.getText().toString();
                strdateob = editdob.getText().toString();

                int length = editAge.length();

                Log.e("counter", "   " + length);
                if (length >= 4) {
                    editage.setError("Please enter Valid Age !");
                    return;
                }
                int age = Integer.parseInt(editAge);
                if (age > 100) {
                    editage.setError("Please enter Valid Age !");
                    return;
                }

                if (TextUtils.isEmpty(editfname)) {
                    editfirstname.setError("Please enter First Name !");
                    return;
                }

                if (TextUtils.isEmpty(editlname)) {
                    editlasttname.setError("Please enter Last Name !");
                    return;
                }

                if (TextUtils.isEmpty(editAge)) {
                    editage.setError("Please enter Age !");
                    return;
                }

                if (TextUtils.isEmpty(editPno)) {
                    editmobile_no.setError("Please enter Mobile Number !");
                    return;
                }
                if (editPno.length() < 10) {
                    editmobile_no.setError("Phone No should be 10 digit!");
                    return;
                }

                if (selectedLanguage != null && selectedLanguage.length() > 0) {

                    Log.e("tang1", "" + strLanguage);
                } else {
                    strLanguage = selectedLanguage;

                }
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String sex = radioSexButton.getText().toString();
                String modified_by = docId;
                String action = "modified";
                String flag = "0";
                //dbController.updatePatientPersonalInfo(strId,"Ashish","G", "Umredkar",strgender,strDob,"28","9695863874",strLanguage);
                try {
                    if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {

                        dbController.updatePatientPersonalInfo(strId, editfname, editmname, editlname, sex, strdateob, editAge, editPno, selectedLanguage, patientImagePath, modified_on_date, modified_by, modifiedTime, action, flag, docId);
                        Log.e("kt", "1");
                    } else {
                        dbController.updatePatientPersonalInfo(strId, editfname, editmname, editlname, sex, strdateob, editAge, editPno, selectedLanguage, strPatientPhoto, modified_on_date, modified_by, modifiedTime, action, flag, docId);
                        Log.e("bt", "2");
                    }
                    Toast.makeText(getApplicationContext(), "Records Updated Successfully !!!", Toast.LENGTH_LONG).show();
                    goToNavigation();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (dbController != null) {
                        dbController.close();
                    }
                }
            }
        });

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                selectedLanguage = (String) parent.getItemAtPosition(position);
               /* Toast.makeText(EditPersonalInfo.this, "selected language is:" + (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //This will redirect user to main activity
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                goToNavigation();

            }
        });

        setupAnimation();
    }


    private void setLastnameSpinner() {

        ArrayAdapter<String> lastnamespin = new ArrayAdapter<>(EditPersonalInfo.this,
                android.R.layout.simple_dropdown_item_1line, mLastNameList);


        lastnamespin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editlasttname.setThreshold(1);
        editlasttname.setAdapter(lastnamespin);
    }

    private void setLanguageSpinnerAdapters() {

        ArrayAdapter<CharSequence> language_reasonAdapter = ArrayAdapter
                .createFromResource(EditPersonalInfo.this, R.array.language_group,
                        android.R.layout.simple_spinner_item);
        language_reasonAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner commented fro
        language.setAdapter(language_reasonAdapter);

        language.setSelection(position);
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                selectedLanguage = (String) parent.getItemAtPosition(position);
              /*  Toast.makeText(EditPersonalInfo.this, "selected language is:" + (String) parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void showCancelAlertDialog() {

        final Dialog dialog = new Dialog(EditPersonalInfo.this);
        dialog.setContentView(R.layout.custom_cancelalert);


        dialog.setTitle("Please Confirm ");

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);

        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  frameLayout.setVisibility(View.GONE);
             /*  Intent  i = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(i);
                finish();*/
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
                    // user cancelled Image capture
                  /*  new RegistrationActivity().customToast("User cancelled image capture");*/
                } else {
                    // failed to capture image
                    /*new RegistrationActivity().customToast("Sorry! Failed to capture image");*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //preview the captured image into image view
    private void previewCapturedImage() {
        try {

            patientImagePath = uriSavedImage.getPath();

            if (patientImagePath != null && !TextUtils.isEmpty(patientImagePath)) {
                //set image to glide 2-11-2016
                setUpGlide(patientImagePath, patientImage);
                /*Glide.with(EditPersonalInfo.this)
                        .load(patientImagePath)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.main_profile)
                        .into(patientImage);*/


            }


            //  patientImage.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
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
                                pateintAge = new RegistrationActivity().getAge(year, monthOfYear, dayOfMonth);
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

    private void goToNavigation() {

        Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
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
        language = null;
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
}

