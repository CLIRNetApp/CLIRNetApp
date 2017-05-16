package app.clirnet.com.clirnetapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.ShowPersonalDetailsActivity;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.FollowUpDateSearchAdapter;
import app.clirnet.com.clirnetapp.adapters.RVAdapterforUpdateDate;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.BannerClass;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.utility.ItemClickListener;

public class ConsultationLogFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText date;
    private static final int DATE_DIALOG_ID = 0;
    private String searchdate;
    private SQLController sqlController;

    private RecyclerView recycler_view;
    private ImageView backChangingImages;
    private LinearLayout norecordtv;
    private StringBuilder sysdate;
    private TextView txtupdateDate;
    private TextView txtfod;


    private RVAdapterforUpdateDate rvAdapterforUpdateDate;
    private FollowUpDateSearchAdapter followUpDateSearchAdapter;
    private View view;
    private ArrayList<RegistrationModel> filterfodList;
    private ArrayList<RegistrationModel> filterVistDateList;
    private AppController appController;
    private ArrayList<String> bannerimgNames;
    private BannerClass bannerClass;
    private String doctor_membership_number;

    private Button searchRecords;

    public ConsultationLogFragment() {
        // this.setHasOptionsMenu(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String greeting = (savedInstanceState != null) ? savedInstanceState.getString("greeting2") : "null";
        //Log.i(USER, " onViewStateRestoredfrag: " + greeting);
        setRetainInstance(true);//used to save instance on screen rotation
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /*Log.i(USER, " onSaveInstanceState.");
        savedInstanceState.putString("greeting2", "Hello");*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null; // now cleaning up!
        //No leak found by leak canary lib   tested on 28-01-2017 2:36pm
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_consultation_log, container, false);
        ((NavigationActivity) getActivity()).setActionBarTitle("Consultation Log");

        date = (EditText) view.findViewById(R.id.date);
        searchRecords = (Button) view.findViewById(R.id.search);
        TextView currdate = (TextView) view.findViewById(R.id.sysdate);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setHasFixedSize(true);

        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        norecordtv = (LinearLayout) view.findViewById(R.id.norecordtv);
        txtupdateDate = (TextView) view.findViewById(R.id.txtupdateDate);
        txtfod = (TextView) view.findViewById(R.id.txtfod);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        currdate.setText("Today's Date " + dd);

        bannerClass = new BannerClass(getContext());

        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) view.findViewById(R.id.termsandCondition);
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

        //open database for further interaction with database
        try {

            sqlController = new SQLController(getContext().getApplicationContext());
            sqlController.open();
            appController = new AppController();
            bannerimgNames = bannerClass.getImageName();
            doctor_membership_number = sqlController.getDoctorMembershipIdNew();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Consultation Log" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        Calendar c = Calendar.getInstance();

        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int day1 = c.get(Calendar.DAY_OF_MONTH);

        // Show current date
        date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day1).append("-").append(month1 + 1).append("-")
                .append(year1).append(" "));

        sysdate = new StringBuilder().append(day1).append("-").append(month1 + 1).append("-").append(year1).append("");

//open date picker dialog
        date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                showDialog();

            }
        });
        searchRecords.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    searchRecords.setBackgroundColor(getResources().getColor(R.color.btn_back_sbmt));

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    searchRecords.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                return false;
            }

        });
        //search the records from user query
        searchRecords.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                norecordtv.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.INVISIBLE);

                searchdate = date.getText().toString();
                String reformattedStr = "";
                try {

                    reformattedStr = myFormat.format(fromUser.parse(searchdate));
                } catch (ParseException e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Consultation Log" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }

                if (TextUtils.isEmpty(searchdate)) {
                    date.setError("Please enter Date");
                    return;
                }

                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    Date date1 = sdf1.parse(searchdate);

                    Date currentdate = sdf1.parse(String.valueOf(sysdate));

                             /*Search Patient By Follow up date */
                    if (date1.after(currentdate) || date1.equals(currentdate)) {

                        if (filterVistDateList != null && filterVistDateList.size() > 0) {

                            filterVistDateList.clear();

                        }

                        filterfodList = new ArrayList<>();
                        //get records from database vai entered follow up date
                        filterfodList = sqlController.getPatientListnew(reformattedStr);


                        int filterModelSize = filterfodList.size();

                        if (filterModelSize > 0) {
                            followUpDateSearchAdapter = new FollowUpDateSearchAdapter(filterfodList);
                            norecordtv.setVisibility(View.GONE);
                            txtfod.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.VISIBLE);
                            txtupdateDate.setVisibility(View.VISIBLE);
                            followUpDateSearchAdapter.setFilter(filterfodList);
                            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
                            recycler_view.setAdapter(followUpDateSearchAdapter);

                        }
                        if (filterfodList != null && filterModelSize > 0) {
                            recycler_view.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getContext().getApplicationContext(), recycler_view, new ItemClickListener() {

                                @Override
                                public void onClick(View view, int position) {

                                    followUpDateSearchAdapterToRecyclerView(position);

                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }

                            }));
                        }

                        //  Toast.makeText(getContext(), "Date1 is after sysdate", Toast.LENGTH_LONG).show();
                         /*Search Patient By Follow up date */
                    } else if (date1.before(currentdate)) {

                        if (filterfodList != null && filterfodList.size() > 0) {

                            filterfodList.clear();

                        }

                        filterVistDateList = new ArrayList<>();
                        //get records from database vai entered visit  date
                        filterVistDateList = sqlController.getPatientListVisitDateSearch(reformattedStr);

                        int filterModelSize = filterVistDateList.size();

                        if (filterModelSize > 0) {
                            norecordtv.setVisibility(View.GONE);
                            rvAdapterforUpdateDate = new RVAdapterforUpdateDate(filterfodList);
                            recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
                            txtfod.setVisibility(View.VISIBLE);
                            txtupdateDate.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.VISIBLE);
                            rvAdapterforUpdateDate.setFilter(filterVistDateList);
                            recycler_view.setAdapter(rvAdapterforUpdateDate);
                        }


                        //   Toast.makeText(getContext(), "Date1 is before sysdate", Toast.LENGTH_LONG).show();
                        if (filterModelSize > 0) {
                            recycler_view.addOnItemTouchListener(new RecyclerTouchListener(getContext().getApplicationContext(), recycler_view, new ItemClickListener() {

                                @Override
                                public void onClick(View view, int position) {


                                    setrvAdapterforUpdateDateToRecyclerView(position);

                                }

                                @Override
                                public void onLongClick(View view, int position) {

                                }

                            }));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Consultation Log" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                }

            }

        });


        setupAnimation();
        return view;
    }


    private void setrvAdapterforUpdateDateToRecyclerView(int position) {
        if (filterVistDateList.size() > 0) {
            RegistrationModel book = filterVistDateList.get(position);
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
            i.putExtra("FOD", book.getFollowUpDate());
            i.putExtra("AILMENT", book.getAilments());
            i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
            i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
            i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
            i.putExtra("CLINICALNOTES", book.getClinicalNotes());
            i.putExtra("PRESCRIPTION", book.getPres_img());
            i.putExtra("ISDCODE", book.getIsd_code());
            i.putExtra("ALTERNATEISDCODE", book.getAlternate_isd_code());
            i.putExtra("EMAIL", book.getEmail());
            i.putExtra("ADDRESS", book.getAddress());
            i.putExtra("CITYORTOWN", book.getCityortown());
            i.putExtra("DISTRICT", book.getDistrict());
            i.putExtra("PIN", book.getPin_code());
            i.putExtra("STATE", book.getState());
            i.putExtra("ALTERNATENUMBER", book.getAlternatePhoneNumber());
            i.putExtra("ALTERNATENUMBERTYPE", book.getAlternatePhoneType());
            i.putExtra("PHONETYPE", book.getPhone_type());
            i.putExtra("UID", book.getUid());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
        }
    }

    private void followUpDateSearchAdapterToRecyclerView(int position) {
        if (filterfodList.size() > 0) {
            RegistrationModel book = filterfodList.get(position);

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
            i.putExtra("FOD", book.getFollowUpDate());
            i.putExtra("AILMENT", book.getAilments());
            i.putExtra("FOLLOWDAYS", book.getFollowUpdays());
            i.putExtra("FOLLOWWEEKS", book.getFollowUpWeek());
            i.putExtra("FOLLOWMONTH", book.getFollowUpMonth());
            i.putExtra("CLINICALNOTES", book.getClinicalNotes());
            i.putExtra("PRESCRIPTION", book.getPres_img());
            i.putExtra("ISDCODE", book.getIsd_code());
            i.putExtra("ALTERNATEISDCODE", book.getAlternate_isd_code());
            i.putExtra("UID", book.getUid());
            i.putExtra("EMAIL", book.getEmail());
            i.putExtra("ADDRESS", book.getAddress());
            i.putExtra("CITYORTOWN", book.getCityortown());
            i.putExtra("DISTRICT", book.getDistrict());
            i.putExtra("PIN", book.getPin_code());
            i.putExtra("STATE", book.getState());
            i.putExtra("ALTERNATENUMBER", book.getAlternatePhoneNumber());
            i.putExtra("ALTERNATENUMBERTYPE", book.getAlternatePhoneType());
            i.putExtra("PHONETYPE", book.getPhone_type());
            i.putExtra("UID", book.getUid());
            i.putExtra("CALLEDFROM","2");
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }

    }

    //open date dialog
    private void showDialog() {
        switch (ConsultationLogFragment.DATE_DIALOG_ID) {
            case DATE_DIALOG_ID:
                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);
                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd1 = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear2, mMonth2, mDay2);
                dpd1.show();
                break;
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
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    //show captured patient image into image view
    private void setupAnimation() {

        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        try {
            appController.setUpAdd(getContext(), bannerimgNames, backChangingImages, doctor_membership_number, "Consultation Log");

        } catch (Exception e) {
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Consultation Log" + e + " Line Number: " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
//
    }

    @Override
    public void onPause() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(date.getWindowToken(), 0);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AppController.getInstance().trackScreenView("ConsultationLog Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        view = null; // now cleaning up!

        if (sqlController != null) {
            sqlController.close();
            sqlController = null;
        }
        if (followUpDateSearchAdapter != null) {
            followUpDateSearchAdapter = null;
        }
        if (rvAdapterforUpdateDate != null) {
            rvAdapterforUpdateDate = null;
        }
        if (appController != null) {
            appController = null;
        }
        if (bannerClass != null) {
            bannerClass = null;
        }
        bannerimgNames = null;

        doctor_membership_number = null;
        norecordtv = null;

        searchdate = null;
        sysdate = null;
        date = null;
        txtfod = null;
        recycler_view = null;
        backChangingImages.setOnClickListener(null);
        backChangingImages = null;
        txtupdateDate = null;
        searchRecords = null;
        filterfodList = null;
        filterVistDateList = null;

    }

    //class to implement OnClick Listner
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private final GestureDetector gestureDetector;

        private final ItemClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            try {
                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                    clickListener.onClick(child, rv.getChildPosition(child));
                }
            } catch (Exception exe) {

                exe.printStackTrace();

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }
}


