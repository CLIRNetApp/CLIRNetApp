package app.clirnet.com.clirnetapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.ShowPersonalDetailsAdapter;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;

public class ShowPersonalDetailsActivity extends AppCompatActivity {

    private SQLController sqlController;
    private ArrayList<RegistrationModel> patientPersonalData = new ArrayList<>();

    private String strName;
    private String strgender;
    private String strPatientPhoto;


    private String strId;
    private String strFirstName;
    private String strMiddleName;
    private String strLastName;
    private String strPhone;
    private String strAge;
    private String strDob;
    private String strLanguage;
    private final int[] imageArray = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};
    private ImageView backChangingImages;
    private TextView editpatientName;
    private TextView editage;
    private TextView editmobileno;
    private TextView editgender;
    private TextView editlang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_personal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        backChangingImages = (ImageView) findViewById(R.id.backChangingImages);

        setSupportActionBar(toolbar);

        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        strPatientPhoto = getIntent().getStringExtra("PATIENTPHOTO");

        strName = getIntent().getStringExtra("NAME");
        strId = getIntent().getStringExtra("ID");
        strFirstName = getIntent().getStringExtra("FIRSTTNAME");
        strMiddleName = getIntent().getStringExtra("MIDDLENAME");
        strLastName = getIntent().getStringExtra("LASTNAME");
        strPhone = getIntent().getStringExtra("PHONE");
        strAge = getIntent().getStringExtra("AGE");
        strDob = getIntent().getStringExtra("DOB");
        strLanguage = getIntent().getStringExtra("LANGUAGE");
        strgender = getIntent().getStringExtra("GENDER");

        TextView txtSysDate = (TextView) findViewById(R.id.sysdate);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ImageView editPersonalInfo = (ImageView) findViewById(R.id.editPersonalInfo);
        ImageView patientImage = (ImageView) findViewById(R.id.patientImage);
        editpatientName = (TextView) findViewById(R.id.patientName);
        editage = (TextView) findViewById(R.id.age1);
        editmobileno = (TextView) findViewById(R.id.mobileno);
        editgender = (TextView) findViewById(R.id.gender);
        editlang = (TextView) findViewById(R.id.lang);


        TextView privacyPolicy = (TextView) findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) findViewById(R.id.termsandCondition);
//open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPersonalDetailsActivity.this, PrivacyPolicy.class);
                startActivity(intent);
                finish();

            }
        });
//open Terms and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowPersonalDetailsActivity.this, TermsCondition.class);
                startActivity(intent);
                finish();

            }
        });


        getSysDate();
        setDatatoText();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy");//it will show date as 10 sep,2016
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        txtSysDate.setText("Today's Date " + dd);
        try {

            sqlController = new SQLController(ShowPersonalDetailsActivity.this);
            sqlController.open();
            patientPersonalData = (sqlController.getPatientHistoryListAll(strId)); //get all patient data from db
            int size = patientPersonalData.size();
            if (size > 0) {

                ShowPersonalDetailsAdapter showPersonalDetailsAdapter = new ShowPersonalDetailsAdapter(patientPersonalData);
                recyclerView.setAdapter(showPersonalDetailsAdapter);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //To changes backgound images on time slot


//redirect to edit pesroanl info page
        editPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectToEditPersonalInfo();
            }


        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Intent i = new Intent(ShowPersonalDetailsActivity.this, NavigationActivity.class);
                startActivity(i);
                finish();

            }
        });
        if (strPatientPhoto != null && !TextUtils.isEmpty(strPatientPhoto)) {
            if (strPatientPhoto.length() > 0) {
                setUpGlide(patientImage);


            }
        }
       /* patientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                 //  llayout.setBackground(patientImage.getDrawable());
                    imagefrdDisplay.setVisibility(View.VISIBLE);
                    Glide.with(ShowPersonalDetailsActivity.this)
                            .load(strPatientPhoto)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .error(R.drawable.main_profile)
                            .into(imagefrdDisplay);



                }
            }
        });*/
        if (patientPersonalData.size() > 0) {

        } else {
            Toast.makeText(ShowPersonalDetailsActivity.this, "There is no history to update!!!", Toast.LENGTH_LONG).show();
        }

    }

    private void setUpGlide(ImageView patientImage) {
        Glide.with(ShowPersonalDetailsActivity.this)
                .load(strPatientPhoto)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.main_profile)
                .into(patientImage);
    }

    private void redirectToEditPersonalInfo() {

        Intent i = new Intent(ShowPersonalDetailsActivity.this, EditPersonalInfo.class);

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
        startActivity(i);
        finish();
    }

    private void setDatatoText() {
        editpatientName.setText(strName);
        editmobileno.setText(strPhone);
        editage.setText(strAge);
        editlang.setText(strLanguage);
        editgender.setText(strgender);
    }

    private void getSysDate() {

        setupAnimation();
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
        backChangingImages.postDelayed(runnable, 200); //for initial delay..
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
        Log.d("OnDestroy", "The onDestroy() event");
        // session.setLogin(false);
        //Close the all database connection opened here 31/10/2008 By. Ashish
        try{
            if(sqlController != null){
                sqlController=null;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        patientPersonalData=null;
        strPatientPhoto=null;
        strLanguage=null;
        strgender=null;
        strAge=null;
        strDob=null;
        strLastName=null;
        System.gc();
    }
}
