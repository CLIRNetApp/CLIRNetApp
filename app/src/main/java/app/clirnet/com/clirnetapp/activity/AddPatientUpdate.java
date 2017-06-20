package app.clirnet.com.clirnetapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.sql.SQLException;
import java.util.ArrayList;
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
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.Validator;


@SuppressWarnings("AccessStaticViaInstance")
public class AddPatientUpdate extends AppCompatActivity implements  OldHistoryFragment.OnFragmentInteractionListener {


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
    private SQLiteHandler dbController;

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
    private ArrayList<String> mSymptomsList;

    private String strAlternatenumber;
    private String strAlternatephtype;
    private String strIsd_code;
    private String strAlternateIsd_code;

    private String struid;
    private String strEmail;
    private ArrayList<RegistrationModel> healthLifeStyleList;
    private String mAlcohol;
    private String mPacsWeek, mStressLevel;
    private String mSmokerType, mStickCount;
    private String mLifeStyle, mLactoseTolerance;
    private String mFoodPreference, mFoodHabit;
    private  String mExcercise, mChewinogTobaco ;
    private String mBingeEating , mAllergies ;
    private String mSexuallyActive;
    private String mDrug;
    private String otherDrugTaking;
    private String otherTobacoTaking;
    private String mSleepStatus;

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
        String  strPhoneType = getIntent().getStringExtra("PHONETYPE");

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
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        if (databaseClass == null) {
            databaseClass = new DatabaseClass(getApplicationContext());
        }
        if (lastNamedb == null) {
            lastNamedb = new LastnameDatabaseClass(getApplicationContext());
        }
        if(bannerClass==null){
            bannerClass=new BannerClass(getApplicationContext());
        }
        try {
            sqlController = new SQLController(getApplicationContext());
            sqlController.open();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();
            healthLifeStyleList = sqlController.getHealthAndLifestyle(strPatientId);

            if (healthLifeStyleList.size() > 0) {
                mAlcohol = healthLifeStyleList.get(0).getAlocholConsumption();
                mStressLevel = healthLifeStyleList.get(0).getStressLevel();
                mSmokerType = healthLifeStyleList.get(0).getSmokerType();
                mLifeStyle = healthLifeStyleList.get(0).getLifeSyle();
                mLactoseTolerance = healthLifeStyleList.get(0).getLactoseTolerance();
                mFoodPreference = healthLifeStyleList.get(0).getFoodPreference();
                mFoodHabit = healthLifeStyleList.get(0).getFoodHabit();
                mExcercise = healthLifeStyleList.get(0).getExcercise();
                mChewinogTobaco = healthLifeStyleList.get(0).getChewingTobaco();
                mBingeEating = healthLifeStyleList.get(0).getBingeEating();
                mAllergies = healthLifeStyleList.get(0).getAllergies();
                mSexuallyActive = healthLifeStyleList.get(0).getSexuallyActive();
                mDrug=healthLifeStyleList.get(0).getDrugConsumption();
                otherDrugTaking = healthLifeStyleList.get(0).getOtherDrugConsumption();
                otherTobacoTaking = healthLifeStyleList.get(0).getOtherTobacoConsumption();
                mSleepStatus=healthLifeStyleList.get(0).getSleep();
            }
        } catch (ClirNetAppException |SQLException e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Add Patient Update" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }



        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);

        //Initalize global view to this method 3-11-2016


        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
       // TextView date = (TextView) findViewById(R.id.sysdate);
        TextView editpatientName = (TextView) findViewById(R.id.patientName);
        TextView editAge = (TextView) findViewById(R.id.age);
        TextView editmobileno = (TextView) findViewById(R.id.mobileno);
        TextView phoneType = (TextView) findViewById(R.id.phoneType);
        TextView email = (TextView) findViewById(R.id.email);
        TextView txteMail=(TextView)findViewById(R.id.txtEmail);

        ImageView imgEdit = (ImageView) findViewById(R.id.editPersonalInfo);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);


        if (validator != null) {
            validator = new Validator(getApplicationContext());
        }

       /* SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        date.setText("Today's Date " + dd);*/

        editpatientName.setText(strName);
        editmobileno.setText(strPhone);


        if(strPhoneType!=null) {
            phoneType.setText(strPhoneType + " :");
        }else{
            phoneType.setText("Mobile :");
        }

        if(strEmail!=null &&!TextUtils.isEmpty(strEmail) ){
            txteMail.setVisibility(View.VISIBLE);
            email.setText(strEmail);
        }
        if(strgender!=null && strgender.equals("Male")){
            editAge.setText("( M - "+ strAge + " )");
        }else if(strgender!=null && strgender.equals("Female")) {
            editAge.setText("( F - "+ strAge + " )");
        }else if(strgender!=null && strgender.equals("Trans")) {
            editAge.setText("( T - "+ strAge + " )");
        }

        if(strPatientPhoto!=null && !TextUtils.isEmpty(strPatientPhoto)){
            setUpGlide(strPatientPhoto,patientImage);
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




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                goToNavigation1();
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
        backChangingImages=(ImageView)findViewById(R.id.backChangingImages);

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
    }
    private void cleanResources() {

        patientHistoryData = null;
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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),viewPager);

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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        ViewPager viewPager;

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        ViewPagerAdapter(FragmentManager manager,ViewPager viewPager) {

            super(manager);
            this.viewPager=viewPager;
        }
  //this method is also working fine bt we have do spcl so we used below getItem code to pass tab view position from button
        //ie we call view pager from external button call
      /*  @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }*/
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
                  return  oldHistoryFragment;

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

    private void setImagesToHealthLifeStyle(){
        ImageView imgSmoke = (ImageView) findViewById(R.id.imgSmoke);
        ImageView imgDrink = (ImageView) findViewById(R.id.imgDrink);
        ImageView imgTobaco = (ImageView) findViewById(R.id.imgTobaco);
        ImageView imgFood = (ImageView) findViewById(R.id.imgFood);
        ImageView imgSleep = (ImageView) findViewById(R.id.imgSleep);
        ImageView imgStress = (ImageView) findViewById(R.id.imgStress);
        Log.e("mSmokerType",""+mSmokerType + "" +mAlcohol);
        if(mSmokerType!=null && !mSmokerType.equals("")&& mSmokerType.equals("Active Smoker")){
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.smoke));
        }else if(mSmokerType!=null && !mSmokerType.equals("")&& mSmokerType.equals("Non Smoker")){
            imgSmoke.setVisibility(View.VISIBLE);
            imgSmoke.setImageDrawable(getResources().getDrawable(R.drawable.no_smoke));
        }
        if(mAlcohol!=null && !mAlcohol.equals("")&& mAlcohol.equals("Drinker")){
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.drink));
        }else if(mAlcohol!=null && !mAlcohol.equals("")&& mAlcohol.equals("Non Drinker")){
            imgDrink.setVisibility(View.VISIBLE);
            imgDrink.setImageDrawable(getResources().getDrawable(R.drawable.no_drink));
        }
        if(mSleepStatus!=null && !mSleepStatus.equals("")&& mSleepStatus.equals("Adequate")){
            imgSleep.setVisibility(View.VISIBLE);
            imgSleep.setImageDrawable(getResources().getDrawable(R.drawable.sleep));
        }else if(mSleepStatus!=null && !mSleepStatus.equals("")&& mSleepStatus.equals("InAdequate")){
            imgSleep.setVisibility(View.VISIBLE);
            imgSleep.setImageDrawable(getResources().getDrawable(R.drawable.no_sleep));
        }
        if(mStressLevel!=null && !mStressLevel.equals("")&& mStressLevel.equals("Low")){
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.stressed));
        }else if(mStressLevel!=null && !mStressLevel.equals("")&& mStressLevel.equals("Moderate")){
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.no_stressed));
        } else if(mStressLevel!=null && !mStressLevel.equals("")&& mStressLevel.equals("High")){
            imgStress.setVisibility(View.VISIBLE);
            imgStress.setImageDrawable(getResources().getDrawable(R.drawable.no_stressed));
        }
    }
}
