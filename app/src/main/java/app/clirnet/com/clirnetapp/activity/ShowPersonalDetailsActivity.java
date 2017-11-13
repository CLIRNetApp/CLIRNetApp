package app.clirnet.com.clirnetapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.AddPatientUpdateAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


public class ShowPersonalDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    private SQLController sqlController;
    private ArrayList<RegistrationModel> patientPersonalData;

    private String strgender;
    private String strPatientPhoto;

    private String strId;
    private String strFirstName;

    private String strLastName;
    private String strPhone;
    private String strAge;

    private ImageView backChangingImages;
    private AppController appController;


    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private String doctor_membership_number;
    private Button editlastUpdate;
    private String strPhoneType;

    private String strEmail;

    private String mAlcohol;
    private String mStressLevel;
    private String mSmokerType;
    private String mLifeStyle;
    private String mExcercise, mChewinogTobaco;
    private String mSleepStatus;

    private int year_dob;

    private String strName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_personal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        appController = new AppController();

        try {
            //no inspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Show patient Details " + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        strId = getIntent().getStringExtra("ID");//patient id


        TextView txtSysDate = (TextView) findViewById(R.id.sysdate);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ImageView editPersonalInfo = (ImageView) findViewById(R.id.editPersonalInfo);

        editlastUpdate = (Button) findViewById(R.id.editlastUpdate);
        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);

        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);

        TextView editpatientName = (TextView) findViewById(R.id.patientName);
        TextView editAge = (TextView) findViewById(R.id.age);
        TextView editmobileno = (TextView) findViewById(R.id.mobileno);
        TextView phoneType = (TextView) findViewById(R.id.phoneType);
        TextView email = (TextView) findViewById(R.id.email);
        TextView txteMail = (TextView) findViewById(R.id.txtEmail);


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
        //open Terms and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsCondition.class);
                startActivity(intent);
                finish();

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


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM , yyyy", Locale.ENGLISH); //it will show date as 10 sep,2016
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        txtSysDate.setText("Today's Date: " + dd);

        try {
            //getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();

            patientPersonalData = new ArrayList<>();
            patientPersonalData = sqlController.getPatientHistoryListAll1(strId); //get all patient data from db
            int size = patientPersonalData.size();

            if (size > 0) {
              // ShowPersonalDetailsAdapter showPersonalDetailsAdapter = new ShowPersonalDetailsAdapter(ShowPersonalDetailsActivity.this, patientPersonalData);
                AddPatientUpdateAdapter showPersonalDetailsAdapter = new AddPatientUpdateAdapter(ShowPersonalDetailsActivity.this, patientPersonalData);
                recyclerView.setAdapter(showPersonalDetailsAdapter);
            }
            if (bannerClass == null) {
                bannerClass = new BannerClass(ShowPersonalDetailsActivity.this);
            }
            bannerimgNames = bannerClass.getImageName();

            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            year_dob=sqlController.getDobYear(strId);

            ArrayList<RegistrationModel> healthLifeStyleList = sqlController.getHealthAndLifestyle(strId);

            if (healthLifeStyleList.size() > 0) {
                mAlcohol = healthLifeStyleList.get(0).getAlocholConsumption();
                mStressLevel = healthLifeStyleList.get(0).getStressLevel();
                mSmokerType = healthLifeStyleList.get(0).getSmokerType();
                mLifeStyle = healthLifeStyleList.get(0).getLifeSyle();
                mExcercise = healthLifeStyleList.get(0).getExcercise();
                mChewinogTobaco = healthLifeStyleList.get(0).getChewingTobaco();
                mSleepStatus = healthLifeStyleList.get(0).getSleep();

            }

            HashMap<String, String> compList = sqlController.getTestData(strId);

            strFirstName = compList.get("FIRSTTNAME");
            String strMiddleName = compList.get("MIDDLENAME");
            strLastName = compList.get("LASTNAME");
            strName = strFirstName +" "+strLastName;

            strAge = compList.get("AGE");
            strPhone = compList.get("PHONE");
            strgender = compList.get("GENDER");
            strPatientPhoto = compList.get("PATIENTPHOTO");
            strEmail = compList.get("EMAIL");
            strPhoneType = compList.get("PHONETYPE");
            String yearDob = compList.get("DOB_YEAR");
            if(yearDob!=null && !yearDob.equals(""))
            year_dob = Integer.parseInt(compList.get("DOB_YEAR"));

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Show patient Details" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        editpatientName.setText(strName);
        editmobileno.setText(strPhone);

        if (strPhoneType != null && !strPhoneType.equals("")) {
            phoneType.setText(strPhoneType + " :");
        } else {
            phoneType.setText("Mobile :");
        }

        if (strEmail != null && !TextUtils.isEmpty(strEmail)) {
            txteMail.setVisibility(View.VISIBLE);
            email.setText(strEmail);
        }

        if(year_dob!=0) {
            String ageFinal = appController.getAgeFromYearDob(year_dob);
            if (strgender != null && strgender.equals("Male")) {
                editAge.setText("( M - " + ageFinal + " )");
            } else if (strgender != null && strgender.equals("Female")) {
                editAge.setText("( F - " + ageFinal + " )");
            }
        }else {
            if (strgender != null && strgender.equals("Male")) {
                editAge.setText("( M - " + strAge + " )");
            } else if (strgender != null && strgender.equals("Female")) {
                editAge.setText("( F - " + strAge + " )");
            }
        }


         //redirect to edit pesroanl info page
        editPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectToEditPersonalInfo();
            }


        });

        editlastUpdate.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    editlastUpdate.setBackground(getResources().getDrawable(R.drawable.rounded_corner_withbackground));

                    //   Toast.makeText(getApplicationContext(), "There is no history to update!!!", Toast.LENGTH_LONG).show();
                     if (patientPersonalData.size() > 0) {
                        RegistrationModel registrationModel = patientPersonalData.get(0); //0 will get first record from the list

                         Intent i = new Intent(getApplicationContext(), NewEditPatientUpdate.class);

                         i.putExtra("ID", registrationModel.getPat_id());

                        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                        // finish();
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        editlastUpdate.setBackground(getResources().getDrawable(R.drawable.rounded_corner_withbackground_blue));
                    }
                }
                return false;

            }
        });

        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {
            if (strPatientPhoto.length() > 0) {
                setUpGlide(patientImage);

            }
        }

        if (patientPersonalData.size() <= 0) {
            Toast.makeText(getApplicationContext(), "There is no history to update!", Toast.LENGTH_LONG).show();
        }

        setupAnimation();
        setImagesToHealthLifeStyle();
    }

    private void setUpGlide(ImageView patientImage) {
        Glide.with(getApplicationContext())
                .load(strPatientPhoto)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.main_profile)
                .into(patientImage);
    }

    private void redirectToEditPersonalInfo() {

        Intent i = new Intent(getApplicationContext(), NewEditPersonalInfo.class);

        i.putExtra("ID", strId);

        startActivity(i);
        // finish();
    }


    //this will used to change banner image after some time interval
    private void setupAnimation() {

        try {
            appController.setUpAdd(ShowPersonalDetailsActivity.this, bannerimgNames, backChangingImages, doctor_membership_number, "Show Personal Details");

        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Show patient Details" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (backChangingImages != null) {
            backChangingImages.setImageDrawable(null);
        }
    }

    private void goToNavigation1() {
        this.onBackPressed();
        finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // session.setLogin(false);

        if (sqlController != null) {
            sqlController = null;
        }
        if (appController != null) {
            appController = null;
        }

        strPhoneType = null;
        patientPersonalData = null;
        strPatientPhoto = null;

        strgender = null;
        strAge = null;

        strId=null;
        strFirstName=null;

        strLastName = null;
        if (bannerClass != null) {
            bannerClass = null;
        }

        bannerimgNames = null;
        doctor_membership_number = null;
        backChangingImages = null;
        editlastUpdate = null;
        strEmail=null;
        mAlcohol=null;
        mStressLevel=null;
        mSmokerType=null;
        mLifeStyle=null;
        mExcercise=null;
        mChewinogTobaco=null;
        mSleepStatus=null;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setupAnimation();
    }

    private void setImagesToHealthLifeStyle() {

        ImageView imgSmoke = (ImageView) findViewById(R.id.imgSmoke);
        ImageView imgDrink = (ImageView) findViewById(R.id.imgDrink);
        ImageView imgTobaco = (ImageView) findViewById(R.id.imgTobaco);
        ImageView imgStress = (ImageView) findViewById(R.id.imgStress);
        ImageView imgLifeStyle = (ImageView) findViewById(R.id.imgLifeStyle);
        ImageView imgExcercise = (ImageView) findViewById(R.id.imgExcercise);

        imgSmoke.setOnClickListener(this);
        imgDrink.setOnClickListener(this);
        imgTobaco.setOnClickListener(this);
        imgStress.setOnClickListener(this);
        imgLifeStyle.setOnClickListener(this);
        imgExcercise.setOnClickListener(this);

        // Log.e("mSmokerType",""+mSmokerType + "" +mAlcohol);

        if (mSmokerType != null && !mSmokerType.equals("") && mSmokerType.equals("Active Smoker")) {
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.smoke));
        } else if (mSmokerType != null && !mSmokerType.equals("") && mSmokerType.equals("Non Smoker")) {
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.no_smoke));
        }
        if (mAlcohol != null && !mAlcohol.equals("") && mAlcohol.equals("Drinker")) {
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.drink));
        } else if (mAlcohol != null && !mAlcohol.equals("") && mAlcohol.equals("Non Drinker")) {
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.no_drink));
        }
        if (mChewinogTobaco != null && !mChewinogTobaco.equals("") && mChewinogTobaco.equals("Yes")) {
            imgTobaco.setVisibility(View.VISIBLE);
            imgTobaco.setImageDrawable(getResources().getDrawable(R.drawable.tobacco));
        } else if (mSleepStatus != null && !mSleepStatus.equals("") && mSleepStatus.equals("No")) {
            imgTobaco.setVisibility(View.VISIBLE);
            imgTobaco.setImageDrawable(getResources().getDrawable(R.drawable.no_tobacco));
        }
        if (mStressLevel != null && !mStressLevel.equals("") && mStressLevel.equals("Low")) {
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stress_low));
        } else if (mStressLevel != null && !mStressLevel.equals("") && mStressLevel.equals("Moderate")) {
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stress_moderate));
        } else if (mStressLevel != null && !mStressLevel.equals("") && mStressLevel.equals("High")) {
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stress_high));
        }

        if (mExcercise != null && !mExcercise.equals("") && mExcercise.equals("Yes")) {
            imgExcercise.setVisibility(View.VISIBLE);
            imgExcercise.setImageDrawable(getResources().getDrawable(R.drawable.exercise));
        } else if (mExcercise != null && !mExcercise.equals("") && mExcercise.equals("No")) {
            imgExcercise.setVisibility(View.VISIBLE);
            imgExcercise.setImageDrawable(getResources().getDrawable(R.drawable.no_exercise));
        }
        if (mLifeStyle != null && !mLifeStyle.equals("") && mLifeStyle.equals("Sedentary")) {
            imgLifeStyle.setVisibility(View.VISIBLE);
            imgLifeStyle.setImageDrawable(getResources().getDrawable(R.drawable.sitting));
        } else if (mLifeStyle != null && !mLifeStyle.equals("") && mLifeStyle.equals("Light Active")) {
            imgLifeStyle.setVisibility(View.VISIBLE);
            imgLifeStyle.setImageDrawable(getResources().getDrawable(R.drawable.walking));
        } else if (mLifeStyle != null && !mLifeStyle.equals("") && mLifeStyle.equals("Active")) {
            imgLifeStyle.setVisibility(View.VISIBLE);
            imgLifeStyle.setImageDrawable(getResources().getDrawable(R.drawable.running));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgSmoke:
                appController.showToastMsg(getApplicationContext(), "Smoking Status: "+mSmokerType);
                break;
            case R.id.imgDrink:
                appController.showToastMsg(getApplicationContext(), "Drinking Status: "+mAlcohol);
                break;
            case R.id.imgTobaco:
                appController.showToastMsg(getApplicationContext(), "Use of Other Tobacco Products: " +mChewinogTobaco);
                break;
            case R.id.imgSleep:
                appController.showToastMsg(getApplicationContext(), "Sleep Status: "+mSleepStatus);
                break;
            case R.id.imgStress:
                appController.showToastMsg(getApplicationContext(), "Patient Stress Level: "+mStressLevel);
                break;
            case R.id.imgLifeStyle:
                appController.showToastMsg(getApplicationContext(), "Patient Lifestyle: "+mLifeStyle);
                break;
            case R.id.imgExcercise:
                appController.showToastMsg(getApplicationContext(), "Patient Exercises? "+mExcercise);
                break;
        }
    }
}
