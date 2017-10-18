package app.clirnet.com.clirnetapp.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.fragments.AddPatientUpdateFragment;
import app.clirnet.com.clirnetapp.fragments.OldHistoryFragment;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.LastnameDatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.Validator;


@SuppressWarnings("AccessStaticViaInstance")
public class AddPatientUpdate extends AppCompatActivity implements OldHistoryFragment.OnFragmentInteractionListener, View.OnClickListener {

    public static final String EXTRA_KEY_NOTIFY = "EXTRA_NOTIFY";
    public static final String ACTION_MyUpdate = "app.clirnet.com.clirnetapp.app.UPDATE";

    private ImageView backChangingImages;
    private String strPhone;
    private String strAge;
    private String strLanguage;
    private String strgender;
    private String strPatientPhoto;

    private String strFirstName;
    private String strMiddleName;
    private String strLastName;
    private String strDob;

    private String strPatientId;

    private SQLController sqlController;


    private String doctor_membership_number;

    private ArrayList<RegistrationModel> patientHistoryData = new ArrayList<>();
    private DatabaseClass databaseClass;

    private AppController appController;

    private Validator validator;
    private String strCityorTown;
    private String strDistrict;
    private String strPinNo;
    private String strState;
    private String strAddress;

    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private LastnameDatabaseClass lastNamedb;


    private String strAlternatenumber;
    private String strAlternatephtype;
    private String strIsd_code;
    private String strAlternateIsd_code;

    private String struid;
    private String strEmail;
    private String mAlcohol;
    private String mStressLevel;
    private String mSmokerType;
    private String mLifeStyle;
    private String mExcercise, mChewinogTobaco;
    private String mSleepStatus;
    private String strFamilyHistory;
    private String strHospitalizaionSurgery;

    private MyBroadcastReceiver_Update myBroadcastReceiver_Update;
    private TextView editpatientName;
    private TextView editAge;
    private TextView editmobileno;
    private TextView phoneType;
    private TextView email;
    private TextView txteMail;
    private String strPhoneType;
    private int year_dob;

    @SuppressLint({"SimpleDateFormat", "SetTValidatorextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_update);

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
        strPhoneType = getIntent().getStringExtra("PHONETYPE");

        strFamilyHistory = getIntent().getStringExtra("FAMILYHISTORY");
        strHospitalizaionSurgery = getIntent().getStringExtra("HOSPITALIZATION");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appController = new AppController();

        try {
            final ActionBar ab = getSupportActionBar();
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
            assert ab != null;
            ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
            ab.setDisplayShowTitleEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient Update  " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        myBroadcastReceiver_Update = new MyBroadcastReceiver_Update();

        IntentFilter intentFilter_update = new IntentFilter(ACTION_MyUpdate);
        intentFilter_update.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver_Update, intentFilter_update);

        if (databaseClass == null) {
            databaseClass = new DatabaseClass(getApplicationContext());
        }
        if (lastNamedb == null) {
            lastNamedb = new LastnameDatabaseClass(getApplicationContext());
        }
        if (bannerClass == null) {
            bannerClass = new BannerClass(getApplicationContext());
        }
        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            year_dob=sqlController.getDobYear(strPatientId);


            ArrayList<RegistrationModel> healthLifeStyleList = sqlController.getHealthAndLifestyle(strPatientId);

            if (healthLifeStyleList.size() > 0) {
                mAlcohol = healthLifeStyleList.get(0).getAlocholConsumption();
                mStressLevel = healthLifeStyleList.get(0).getStressLevel();
                mSmokerType = healthLifeStyleList.get(0).getSmokerType();
                mLifeStyle = healthLifeStyleList.get(0).getLifeSyle();

                mExcercise = healthLifeStyleList.get(0).getExcercise();
                mChewinogTobaco = healthLifeStyleList.get(0).getChewingTobaco();

                mSleepStatus = healthLifeStyleList.get(0).getSleep();
            }
        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient Update" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);

        //Initalize global view to this method 3-11-2016


        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
        // TextView date = (TextView) findViewById(R.id.sysdate);
        editpatientName = (TextView) findViewById(R.id.patientName);
        editAge = (TextView) findViewById(R.id.age);
        editmobileno = (TextView) findViewById(R.id.mobileno);
        phoneType = (TextView) findViewById(R.id.phoneType);
        email = (TextView) findViewById(R.id.email);
        txteMail = (TextView) findViewById(R.id.txtEmail);

        ImageView imgEdit = (ImageView) findViewById(R.id.editPersonalInfo);

        ImageView imgSmoke = (ImageView) findViewById(R.id.imgSmoke);
        ImageView imgDrink = (ImageView) findViewById(R.id.imgDrink);
        ImageView imgTobaco = (ImageView) findViewById(R.id.imgTobaco);
        ImageView imgFood = (ImageView) findViewById(R.id.imgFood);
        ImageView imgSleep = (ImageView) findViewById(R.id.imgSleep);
        ImageView imgStress = (ImageView) findViewById(R.id.imgStress);
        ImageView imgLifeStyle = (ImageView) findViewById(R.id.imgLifeStyle);
        ImageView imgExcercise = (ImageView) findViewById(R.id.imgExcercise);
        imgSmoke.setOnClickListener(this);
        imgDrink.setOnClickListener(this);
        imgTobaco.setOnClickListener(this);
        imgFood.setOnClickListener(this);
        imgSleep.setOnClickListener(this);
        imgStress.setOnClickListener(this);
        imgLifeStyle.setOnClickListener(this);
        imgExcercise.setOnClickListener(this);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);


        if (validator != null) {
            validator = new Validator(getApplicationContext());
        }


        editpatientName.setText(strName);
        editmobileno.setText(strPhone);

        if (strPhoneType != null && !strPhoneType.equals("") && !strPhone.equals("Select Type")) {
            phoneType.setText(strPhoneType + " :");
        } else {
            phoneType.setText("Mobile :");
        }

        if (strEmail != null && !TextUtils.isEmpty(strEmail)) {
            txteMail.setVisibility(View.VISIBLE);
            email.setText(strEmail);
        }

        if(year_dob!=0) {
            String ageFinal=appController.getAgeFromYearDob(year_dob);
            if (strgender != null && strgender.equals("Male")) {
                editAge.setText("( M - " + ageFinal + " )");
            } else if (strgender != null && strgender.equals("Female")) {
                editAge.setText("( F - " + ageFinal + " )");
            } else if (strgender != null && strgender.equals("Trans")) {
                editAge.setText("( T - " + ageFinal + " )");
            }
        }else{
            if (strgender != null && strgender.equals("Male")) {
                editAge.setText("( M - " + strAge + " )");
            } else if (strgender != null && strgender.equals("Female")) {
                editAge.setText("( F - " + strAge + " )");
            } else if (strgender != null && strgender.equals("Trans")) {
                editAge.setText("( T - " + strAge + " )");
            }
        }

        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {
            setUpGlide(strPatientPhoto, patientImage);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(AddPatientUpdate.this).clearDiskCache();
            }
        }).start();

        //this is to check of image url is null or not for handle null pointer exception 13-8-16 Ashish


        //this will redirect to EditPersonalInfo class to edit personal info
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), NewEditPersonalInfo.class);

                i.putExtra("PATIENTPHOTO", strPatientPhoto);
                i.putExtra("ID", strPatientId);
                i.putExtra("FIRSTNAME", strFirstName);
                i.putExtra("MIDDLEAME", strMiddleName);
                i.putExtra("LASTNAME", strLastName);
                i.putExtra("PHONE", strPhone);
                i.putExtra("PHONETYPE", strPhoneType);
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
                i.putExtra("FAMILYHISTORY", strFamilyHistory);
                i.putExtra("HOSPITALIZATION", strHospitalizaionSurgery);
                i.putExtra("FROMWHERE", "editpatient");
                startActivity(i);
                // finish();
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                exitByBackKey();
            }
        });

        setupAnimation();
        setImagesToHealthLifeStyle();
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

    private void setupAnimation() {
        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);

        try {
            bannerimgNames = bannerClass.getImageName();

            appController.setUpAdd(AddPatientUpdate.this, bannerimgNames, backChangingImages, doctor_membership_number, "Add Patient Update");

        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient Update " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    private void goToNavigation1() {
        this.onBackPressed();
        finish();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (backChangingImages != null) {
            backChangingImages.setImageDrawable(null);
        }
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

    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myPid());

        super.onDestroy();

        unregisterReceiver(myBroadcastReceiver_Update);

        if (sqlController != null) {
            sqlController = null;
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
        if (bannerClass != null) {
            bannerClass = null;
        }

        if (lastNamedb != null) {
            lastNamedb = null;
        }
        doctor_membership_number = null;
        bannerimgNames = null;

        cleanResources();
    }

    private void cleanResources() {

        strPhone = null;
        strAge = null;
        strLanguage = null;
        strgender = null;
        strPatientPhoto = null;
        strFirstName = null;
        strMiddleName = null;
        strLastName = null;
        strDob = null;
        strPatientId = null;
        sqlController = null;
        patientHistoryData = null;
        backChangingImages = null;
        validator = null;
        strCityorTown = null;
        strDistrict = null;
        strPinNo = null;
        strState = null;
        strAddress = null;
        strAlternatenumber = null;
        strAlternatephtype = null;
        strIsd_code = null;
        strAlternateIsd_code = null;
        struid = null;
        strEmail = null;
        editpatientName = null;
        editAge = null;
        editmobileno = null;
        phoneType = null;
        email = null;
        txteMail = null;
        strPhoneType = null;

        mAlcohol= null;
        mStressLevel= null;
        mSmokerType= null;
        mLifeStyle= null;
        mExcercise= null; mChewinogTobaco= null;
        mSleepStatus= null;
        strFamilyHistory= null;
        strHospitalizaionSurgery= null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), viewPager);

        AddPatientUpdateFragment addPatientUpdateFragment = new AddPatientUpdateFragment();

        adapter.addFragment(addPatientUpdateFragment, "Current");
        Bundle bundle2 = new Bundle();
        bundle2.putString("PatientID", strPatientId);

        addPatientUpdateFragment.setArguments(bundle2);

        OldHistoryFragment oldHistoryFragment = new OldHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PatientID", strPatientId);

        oldHistoryFragment.setArguments(bundle);
        adapter.addFragment(oldHistoryFragment, "History");

        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        ViewPager viewPager;

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        ViewPagerAdapter(FragmentManager manager, ViewPager viewPager) {

            super(manager);
            this.viewPager = viewPager;
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    AddPatientUpdateFragment argumentFragment = new AddPatientUpdateFragment();//Get Fragment Instance
                    argumentFragment.getViewPager(viewPager);
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("PATIENTID", strPatientId);
                    argumentFragment.setArguments(data);
                    return argumentFragment;

                case 1:
                    // Games fragment activity
                    OldHistoryFragment oldHistoryFragment = new OldHistoryFragment();
                    // oldHistoryFragment.getViewPager(viewPager);
                    Bundle bundle = new Bundle();
                    bundle.putString("PATIENTID", strPatientId);

                    oldHistoryFragment.setArguments(bundle);
                    return oldHistoryFragment;

            }

            return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setImagesToHealthLifeStyle() {

        ImageView imgSmoke = (ImageView) findViewById(R.id.imgSmoke);
        ImageView imgDrink = (ImageView) findViewById(R.id.imgDrink);
        ImageView imgTobaco = (ImageView) findViewById(R.id.imgTobaco);
        ImageView imgStress = (ImageView) findViewById(R.id.imgStress);
        ImageView imgLifeStyle = (ImageView) findViewById(R.id.imgLifeStyle);
        ImageView imgExcercise = (ImageView) findViewById(R.id.imgExcercise);

        if (mSmokerType != null && !mSmokerType.equals("") && mSmokerType.equals("Active Smoker")) {
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.smoke));
        } else if (mSmokerType != null && !mSmokerType.equals("") && mSmokerType.equals("Non Smoker")||  mSmokerType != null && mSmokerType.equals("Ex Smoker") ||  mSmokerType != null && mSmokerType.equals("Passive Smoker")) {
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.no_smoke));
        }
        if (mAlcohol != null && !mAlcohol.equals("") && mAlcohol.equals("Drinker")) {
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.drink));
        } else if (mAlcohol != null && !mAlcohol.equals("") && mAlcohol.equals("Non Drinker")|| mAlcohol != null && mAlcohol.equals("Ex Drinker")) {
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.no_drink));
        }
        if (mChewinogTobaco != null && !mChewinogTobaco.equals("") && mChewinogTobaco.equals("Yes")) {
            imgTobaco.setVisibility(View.VISIBLE);
            imgTobaco.setImageDrawable(getResources().getDrawable(R.drawable.tobacco));
        } else if (mChewinogTobaco != null && !mChewinogTobaco.equals("") && mChewinogTobaco.equals("No")) {
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
                appController.showToastMsg(getApplicationContext(), "Patient is a " + mSmokerType);
                break;
            case R.id.imgDrink:
                appController.showToastMsg(getApplicationContext(), "Patient is a " + mAlcohol);
                break;
            case R.id.imgTobaco:
                appController.showToastMsg(getApplicationContext(), " Consumption of other Tobacco Products: "+mChewinogTobaco);
                break;

            case R.id.imgSleep:
                appController.showToastMsg(getApplicationContext(), "Patient gets " + mSleepStatus + " sleep");
                break;
            case R.id.imgStress:
                appController.showToastMsg(getApplicationContext(), "Patient stress level is " + mStressLevel);
                break;
            case R.id.imgLifeStyle:
                appController.showToastMsg(getApplicationContext(), "Patient Lifestyle is " + mLifeStyle);
                break;
            case R.id.imgExcercise:
                appController.showToastMsg(getApplicationContext(), "Patient Exercises? " + mExcercise);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exitByBackKey();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm Action");
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                goToNavigation1();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();

    }

    public class MyBroadcastReceiver_Update extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String patId = intent.getStringExtra(EXTRA_KEY_NOTIFY);

            if (patId != null) {

                try {
                    if (sqlController == null) {
                        sqlController = new SQLController(getApplicationContext());
                        sqlController.open();
                    }
                    HashMap<String, String> ageAddedOnDate = sqlController.getPatientAndHealthDataUpdate(patId);

                    strFirstName = ageAddedOnDate.get("first_name");
                    strLastName = ageAddedOnDate.get("last_name");
                    strAge = ageAddedOnDate.get("age");
                    strgender = ageAddedOnDate.get("gender");
                    strEmail = ageAddedOnDate.get("email");
                    strPhone = ageAddedOnDate.get("phonenumber");
                    mAlcohol = ageAddedOnDate.get("alcohol_consumption");
                    mStressLevel = ageAddedOnDate.get("stress_level");
                    mSmokerType = ageAddedOnDate.get("smoker_type");
                    mChewinogTobaco = ageAddedOnDate.get("chewing_tobaco");
                    mLifeStyle = ageAddedOnDate.get("life_style");
                    mExcercise = ageAddedOnDate.get("excercise");
                    mSleepStatus = ageAddedOnDate.get("sleep_status");
                    strPhoneType = ageAddedOnDate.get("phone_type");

                    setImagesToHealthLifeStyle();

                    editpatientName.setText(strFirstName + " " + strLastName);

                    editmobileno.setText(strPhone);

                    if (strPhoneType != null && !strPhoneType.equals("") && !strPhoneType.equals("Mobile") && !strPhoneType.equals("Landline")) {
                        phoneType.setText(strPhoneType + " :");
                    } else {
                        phoneType.setText("Mobile :");
                    }
                    if (strEmail != null && !TextUtils.isEmpty(strEmail)) {
                        txteMail.setVisibility(View.VISIBLE);
                        email.setText(strEmail);
                    }
                    if (strgender != null && strgender.equals("Male")) {
                        editAge.setText("( M - " + strAge + " )");
                    } else if (strgender != null && strgender.equals("Female")) {
                        editAge.setText("( F - " + strAge + " )");
                    } else if (strgender != null && strgender.equals("Trans")) {
                        editAge.setText("( T - " + strAge + " )");
                    }

                } catch (Exception e) {
                    appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient Update " + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                    e.printStackTrace();
                }
            }
        }
    }
}
