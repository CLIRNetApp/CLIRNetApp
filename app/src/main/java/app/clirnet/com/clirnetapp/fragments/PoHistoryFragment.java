package app.clirnet.com.clirnetapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.Utility.MultiSpinner;
import app.clirnet.com.clirnetapp.Utility.MultiSpinner2;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.ShowPersonalDetailsActivity;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.PoHistoryAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.DatabaseClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


public class PoHistoryFragment extends Fragment implements MultiSpinner.MultiSpinnerListener, MultiSpinner2.MultiSpinnerListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private EditText firstName;
    private EditText lastName;
    private EditText phone_no;
    private TextView age;
    private String sex;
    private String strfname;
    private String strlname;
    private String strpno;
    private String strage;

    private SQLController sqlController;

    private PoHistoryAdapter poHistoryAdapter;
    private RecyclerView recyclerView;
    private ImageView backChangingImages;
    private ArrayList<RegistrationModel> patientData = new ArrayList<>();
    //private ArrayList<RegistrationModel> patientData1 = new ArrayList<>();
    private LinearLayout norecordtv;
    private View rootview;
    private AppController appController;
    private Button submit;
    private MultiSpinner genderSpinner;

    private int ival = 0;
    private int loadLimit = 25;

    private int[] selectedItems = {0, 0, 0, 0};
    private int[] selectedItems2 = {0, 0, 0, 0, 0, 0, 0, 0};
    private ArrayList genderList;

    private ArrayList selectedListGender;
    private ArrayList selectedAgeList;
    private MultiSpinner2 ageGapSpinner;
    private ArrayList ageList;
    private MultiAutoCompleteTextView ailments;
    private DatabaseClass databaseClass;
    private ArrayList<String> mAilmemtArrayList;
    private ArrayList selectedAilmentList;
    private LinearLayoutManager mLayoutManager;

    private int PAGE_SIZE = 2;

    private boolean isLoading = false;

    private int queryCount;
    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private String doctor_membership_number;

    public PoHistoryFragment() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        rootview = null;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        rootview = inflater.inflate(R.layout.fragment_po_history, container, false);

        ((NavigationActivity) getActivity()).setActionBarTitle("Patient History");

        firstName = (EditText) rootview.findViewById(R.id.firstname);

        lastName = (EditText) rootview.findViewById(R.id.lastname);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        ailments = (MultiAutoCompleteTextView) rootview.findViewById(R.id.ailments);
        TextView currdate = (TextView) rootview.findViewById(R.id.sysdate);
        backChangingImages = (ImageView) rootview.findViewById(R.id.backChangingImages);
        norecordtv = (LinearLayout) rootview.findViewById(R.id.norecordtv);

        genderSpinner = (MultiSpinner) rootview.findViewById(R.id.gender);
        ageGapSpinner = (MultiSpinner2) rootview.findViewById(R.id.ageGap);


        phone_no = (EditText) rootview.findViewById(R.id.mobile_no);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();
        String dd = sdf.format(todayDate);


        currdate.setText("Today's Date " + dd);

        final TextView privacyPolicy = (TextView) rootview.findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) rootview.findViewById(R.id.termsandCondition);

        //open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);

            }
        });
        //open Terms and Condition page

        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), TermsCondition.class);
                startActivity(intent);

            }
        });


        try {

            sqlController = new SQLController(getContext().getApplicationContext());
            sqlController.open();
            if (databaseClass == null) {
                databaseClass = new DatabaseClass(getContext());
            }
            if(bannerClass == null){
                bannerClass=new BannerClass(getContext());
            }
            if(appController == null){
                appController = new AppController();
            }
            bannerimgNames= bannerClass.getImageName();
          //  Log.e("ListimgNames", "" + bannerimgNames.size() + "  " + bannerimgNames.get(1));
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Frgament" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        patientData.clear(); //This method will clear all previous data from  Array list  24-8-2016
        setAilmentData();


        submit = (Button) rootview.findViewById(R.id.submit);

        submit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    submit.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patientData.clear(); //This method will clear all previous data from  Array list  24-8-2016


                strfname = firstName.getText().toString().trim();
                strlname = lastName.getText().toString().trim();
                strpno = phone_no.getText().toString().trim();
                String strAilment = ailments.getText().toString().trim();


                //remove comma occurance from string
                strAilment = appController.removeCommaOccurance(strAilment);
                //Remove spaces between text if more than 2 white spaces found 12-12-2016
                strAilment = strAilment.replaceAll("\\s+", " ");


                //This method will clear all previous data from  Array list  24-8-2016

                String delimiter = ",";
                String[] temp = strAilment.split(delimiter);
                selectedAilmentList = new ArrayList();
             /* print substrings */
                Collections.addAll(selectedAilmentList, temp);
                int sizegender = selectedListGender.size();
                int sizeage = selectedAgeList.size();
                int sizeailment = selectedAilmentList.size();
                Log.e("sizegender", "" + sizegender + " " + sizeage + " ==" + sizeailment);
                try {

                    ival = 0;
                    loadLimit = 25;
                    patientData = (sqlController.getFilterDatanew(strfname, strlname, sex, strpno, strage, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit));
                    //    patientData = sqlController.getFilterDatanew(strfname, strlname, selectedListGender.get(i).toString(), strpno, strage);
                    queryCount = sqlController.getCountResult();
                  //  Log.e("queryCount", "" + queryCount );

                    int beforeFilterCount = patientData.size();

                    if (patientData.size() > 0) {
                        removeDuplicate(patientData);
                    }
                   // Log.e("queryCountafterFilter", "" + patientData.size() );

                    int afterFilterCount = patientData.size();

                    int totalFilterDataCount = beforeFilterCount - afterFilterCount;

                    queryCount = queryCount - totalFilterDataCount;

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                } finally {
                    if (sqlController != null) {
                        sqlController.close();
                    }
                }


                int count = patientData.size();
                try {
                    if (count > 0) {

                        /*final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);*/

                        //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setVisibility(View.VISIBLE);
                        norecordtv.setVisibility(View.GONE);

                        poHistoryAdapter = new PoHistoryAdapter(patientData, queryCount);

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(poHistoryAdapter);
                        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);


                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        norecordtv.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Fragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
                }

            }
        });


        recyclerView.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getContext(), recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                //redirect to ShowPersonalDetailsActivity 02-11-2016
                recyclerViewOnClick(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

        setUpSpinner();
        setupAnimation();
        return rootview;
    }


    private void setAilmentData() {
        try {
            databaseClass.openDataBase();
            mAilmemtArrayList = databaseClass.getAilmentsListNew();

            if (mAilmemtArrayList.size() > 0) {

                //this code is for setting list to auto complete text view  8/6/16

                ArrayAdapter<String> adp = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, mAilmemtArrayList);

                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                ailments.setThreshold(1);

                ailments.setAdapter(adp);
                ailments.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + "" + "/" + "Add Patient" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {
            if (databaseClass != null) {
                databaseClass.close();
                databaseClass = null;
            }
        }
    }

    private void setUpSpinner() {

        selectedListGender = new ArrayList();
        selectedAgeList = new ArrayList();

        genderList = new ArrayList();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        genderList.add("NA");
        genderSpinner.setItems(genderList, "Select Gender", this);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                //  selectColoursButton.setText(al.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });
        ageList = new ArrayList();
        ageList.add("0-5");
        ageList.add("5-15");
        ageList.add("15-25");
        ageList.add("25-35");
        ageList.add("35-45");
        ageList.add("45-55");
        ageList.add("55-65");
        ageList.add("65-Above");
        ageGapSpinner.setItems(ageList, "Select Age gap", this);

        ageGapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                Log.e("", "" + (ageList.get(position).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });
    }

    private void recyclerViewOnClick(int position) {

        RegistrationModel book = patientData.get(position);

        Intent i = new Intent(getContext().getApplicationContext(), ShowPersonalDetailsActivity.class);

        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());
        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());
        i.putExtra("PHONE", book.getMobileNumber());
        i.putExtra("PHONETYPE", book.getPhone_type());
        i.putExtra("AGE", book.getAge());
        i.putExtra("LANGUAGE", book.getLanguage());
        i.putExtra("GENDER", book.getGender());
        i.putExtra("ACTUALFOD", book.getActualFollowupDate());
        i.putExtra("FOD", book.getFollowUpDate());
        i.putExtra("AILMENT", book.getAilments());
        i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
        i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
        i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
        i.putExtra("CLINICALNOTES", book.getClinicalNotes());
        i.putExtra("PRESCRIPTION", book.getPres_img());
        i.putExtra("VISITID", book.getKey_visit_id());

        i.putExtra("ADDRESS", book.getAddress());
        i.putExtra("CITYORTOWN", book.getCityortown());
        i.putExtra("DISTRICT", book.getDistrict());
        i.putExtra("PIN", book.getPin_code());
        i.putExtra("STATE", book.getState());
        i.putExtra("WEIGHT", book.getWeight());
        i.putExtra("PULSE", book.getPulse());
        i.putExtra("BP", book.getBp());
        i.putExtra("LOWBP", book.getlowBp());
        i.putExtra("TEMPRATURE", book.getTemprature());
        i.putExtra("SUGAR", book.getSugar());
        i.putExtra("SYMPTOMS", book.getSymptoms());
        i.putExtra("DIGNOSIS", book.getDignosis());
        i.putExtra("TESTS", book.getTests());
        i.putExtra("DRUGS", book.getDrugs());
        i.putExtra("BMI", book.getBmi());
        i.putExtra("ALTERNATENUMBER", book.getAlternatePhoneNumber());
        i.putExtra("ALTERNATENUMBERTYPE", book.getAlternatePhoneType());
        i.putExtra("HEIGHT", book.getHeight());
        i.putExtra("SUGARFASTING", book.getSugarFasting());

        startActivity(i);

    }


    /////////////Show Search Bar//////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.other_navigation, menu);

        //  MenuItem item = menu.findItem(R.id.spinner);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        // Handle action bar actions click
        return true;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        selectedListGender.clear();

        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedItems[i] = 1;
                // System.out.println("______________________" + genderList.get(i));
                String selGender = genderList.get(i).toString();

                selectedListGender.add(selGender);

            } else selectedItems[i] = 0;
        }
       /* for (int selectedItem : selectedItems) {
            // if(selectedItems[i]==1)
            // System.out.println(al.get(i));
            //  selectedListGender.add(genderList.get(i).toString());
        }*/
    }

    @Override
    public void onItemsSelected1(boolean[] selected) {
        selectedAgeList.clear();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedItems2[i] = 1;
               // System.out.println("______________________2" + ageList.get(i));

                String ageString = ageList.get(i).toString();
                selectedAgeList.add(ageString);

            } else selectedItems2[i] = 0;
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setupAnimation() {

       try {
           Random r = new Random();
           int n = r.nextInt(bannerimgNames.size());

           // final String url = getString(imageArray[n]);
           //  backChangingImages.setImageResource(imageArray[n]);
           final String url = bannerimgNames.get(n);
           Log.e("nUrl", "" + n + "" + url);

           BitmapDrawable d = new BitmapDrawable(getResources(), "sdcard/BannerImages/" + url + ".png"); // path is ur resultant //image
           backChangingImages.setImageDrawable(d);
           backChangingImages.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                   String action = "clicked";

                   appController.showAdDialog(getContext(), url);
                   appController.saveBannerDataIntoDb(url, getContext(), doctor_membership_number, action);
               }
           });
           String action = "display";
           appController.saveBannerDataIntoDb(url, getContext(), doctor_membership_number, action);
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    private static void removeDuplicate(final List<RegistrationModel> al) {

        for (int i = 0; i < al.size() - 1; i++) {

            String element = al.get(i).getPat_id();
            // Log.e("element", "" + element);
            for (int j = i + 1; j < al.size(); j++) {
                if (element.equals(al.get(j).getPat_id())) {
                    // Log.e("element1", "" + al.get(j).getPat_id());
                    al.remove(j);
                    j--;

                }
            }
        }
        //  System.out.println(al);
    }


    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
            //Log.e("visibleItemCount", "" + visibleItemCount + " totalItemCount  " + totalItemCount + " firstVisibleItemPosition " + firstVisibleItemPosition);

            boolean isLastPage = false;
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {

                    isLoading = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadDataForAdapter();
                        }
                    }, 2000);
                }
            }
        }
    };


    private void loadDataForAdapter() {

        isLoading = false;
        ival = ival + 25;

        List<RegistrationModel> memberList = new ArrayList<>();


        int index = poHistoryAdapter.getItemCount();
        int end = index + PAGE_SIZE;
       // Log.e("index", "" + index + " " + end + " size is " + queryCount);

        if (end <= queryCount) {
            try {
                memberList = sqlController.getFilterDatanew(strfname, strlname, sex, strpno, strage, selectedListGender, selectedAgeList, selectedAilmentList, ival, loadLimit);
            } catch (ClirNetAppException e) {

                e.printStackTrace();
            }


            //  patientData.addAll(memberList);
            // adapter.notifyDataSetChanged();
            poHistoryAdapter.addAll(memberList);

            if (end >= queryCount) {
                poHistoryAdapter.setLoading(false);

            }
        }

    }



    @Override
    public void onPause() {

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(firstName.getWindowToken(), 0);
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        rootview = null; // view cleaning up!

        if (sqlController != null) {
            sqlController.close();
            sqlController = null;
        }
        if (poHistoryAdapter != null) {
            poHistoryAdapter = null;
        }

        if (appController != null) {
            appController = null;
        }
        if (databaseClass != null) {
            databaseClass = null;
        }
        if(bannerClass != null){
            bannerClass= null;
        }
        bannerimgNames=null;


        patientData.clear();
        patientData = null;

        recyclerView.setOnClickListener(null);
        //  searchView.setOnClickListener(null);

        norecordtv = null;

        recyclerView = null;
        sex = null;
        strfname = null;
        strlname = null;
        strpno = null;
        strage = null;

        submit = null;
        selectedListGender = null;
        selectedAgeList = null;
        ageGapSpinner = null;
        ageList = null;
        ailments = null;
        mAilmemtArrayList = null;

        selectedItems = null;
        selectedItems2 = null;
        genderList = null;
        selectedListGender = null;
        selectedAgeList = null;
        ageGapSpinner = null;
        selectedAilmentList = null;
        mLayoutManager = null;
        doctor_membership_number=null;
        backChangingImages=null;
        firstName=null;
        lastName=null;
        phone_no=null;
        genderSpinner=null;

        Log.e("onDetach", "onDetach Po hISTORY fRAGMENT Fragment");
    }
}


