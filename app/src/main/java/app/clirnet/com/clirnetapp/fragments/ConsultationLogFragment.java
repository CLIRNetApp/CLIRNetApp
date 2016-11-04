package app.clirnet.com.clirnetapp.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Objects;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.ShowPersonalDetailsActivity;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.FollowUpDateSearchAdapter;
import app.clirnet.com.clirnetapp.adapters.RVAdapterforUpdateDate;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


/**
 * Created by ${Ashish} on 22-04-2016.
 */
public class ConsultationLogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ListViewActivity";


    private final int[] imageArray = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five};

    private OnFragmentInteractionListener mListener;
    private EditText date;
    private static final int DATE_DIALOG_ID = 0;
    private String searchdate;
    private SQLController sqlController;
    private ArrayList<RegistrationModel> patientData = new ArrayList<>();
    private RecyclerView recycler_view;
    private ImageView backChangingImages;
    private LinearLayout norecordtv;
    private StringBuilder sysdate;
    private TextView txtupdateDate;
    private TextView txtfod;
    private List<RegistrationModel> filteredModelList;
    private List<RegistrationModel> visitDateDataFilter= new ArrayList<>();
    private RVAdapterforUpdateDate rvAdapterforUpdateDate;
    private FollowUpDateSearchAdapter followUpDateSearchAdapter;
    private View view;

    public ConsultationLogFragment() {
        // this.setHasOptionsMenu(true);

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            setRetainInstance(true);//used to save instance on screen rotation

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null; // now cleaning up!


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_consultation_log, container, false);
        date = (EditText) view.findViewById(R.id.date);
        Button searchRecords = (Button) view.findViewById(R.id.search);
        TextView currdate = (TextView) view.findViewById(R.id.sysdate);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

        norecordtv = (LinearLayout) view.findViewById(R.id.norecordtv);
        txtupdateDate = (TextView) view.findViewById(R.id.txtupdateDate);
        txtfod = (TextView) view.findViewById(R.id.txtfod);

        SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM,yyyy");
        Date todayDate = new Date();

        String dd = sdf.format(todayDate);
        currdate.setText("Today's Date "+dd);


        followUpDateSearchAdapter=new FollowUpDateSearchAdapter(patientData);
        rvAdapterforUpdateDate = new RVAdapterforUpdateDate(patientData);

        TextView privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);
        TextView termsandCondition = (TextView) view.findViewById(R.id.termsandCondition);
//open privacy poilicy page
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivacyPolicy.class);
                startActivity(intent);

            }
        });
        //open Terms and Condition page
        termsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TermsCondition.class);
                startActivity(intent);

            }
        });

        //open database for further interaction with database
        try {

            sqlController = new SQLController(getContext());
            sqlController.open();
            patientData = (sqlController.getPatientList());

        } catch (Exception e) {
            e.printStackTrace();
        }


//To changes backgound images on time slot



        final Calendar c = Calendar.getInstance();
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
        //search the records from user query
        searchRecords.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
                filteredModelList = new ArrayList<>();
                searchdate = date.getText().toString();
                String reformattedStr = "";
                try {

                    reformattedStr = myFormat.format(fromUser.parse(searchdate));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (TextUtils.isEmpty(searchdate)) {
                    date.setError("Please enter Phone Number !");
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (!Objects.equals(reformattedStr, "")) {
                        filteredModelList = filter(patientData, reformattedStr);
                        visitDateDataFilter=visitDateFilter(patientData, reformattedStr);
                    }
                }

                int filterModelSize= filteredModelList.size();
                int filterSize= visitDateDataFilter.size();

                if (filterModelSize <= 0 && filterSize <= 0) {

                } else {
                    norecordtv.setVisibility(View.GONE);
                }
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                    Date date1 = sdf1.parse(searchdate);

                    Date currentdate = sdf1.parse(String.valueOf(sysdate));

                    if (date1.after(currentdate) || date1.equals(currentdate)) {

                        //  Toast.makeText(getContext(), "Date1 is after sysdate", Toast.LENGTH_LONG).show();
                        txtfod.setVisibility(View.GONE);
                        txtupdateDate.setVisibility(View.VISIBLE);
                        followUpDateSearchAdapter.setFilter(filteredModelList);
                        recycler_view.setAdapter(followUpDateSearchAdapter);

                        recycler_view.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getContext(), recycler_view, new ItemClickListener() {

                            @Override
                            public void onClick(View view, int position) {

                                followUpDateSearchAdapterToRecyclerView(position);



                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }

                        }));

                    }

                    if (date1.before(currentdate)) {

                        //  System.out.println("Date1 is before or equal to Date2");

                        txtfod.setVisibility(View.VISIBLE);
                        txtupdateDate.setVisibility(View.GONE);

                        rvAdapterforUpdateDate.setFilter(visitDateDataFilter);
                        recycler_view.setAdapter(rvAdapterforUpdateDate);
                        //   Toast.makeText(getContext(), "Date1 is before sysdate", Toast.LENGTH_LONG).show();
                        recycler_view.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getContext(), recycler_view, new ItemClickListener() {

                            @Override
                            public void onClick(View view, int position) {



                                setrvAdapterforUpdateDateToRecyclerView(position);




                                //Toast.makeText(getContext(), book.getFirstName() + " is selected!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongClick(View view, int position) {

                            }

                        }));


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        setupAnimation();
        return view;
    }

    private void setrvAdapterforUpdateDateToRecyclerView(int position) {

        RegistrationModel book = visitDateDataFilter.get(position);
        Intent i = new Intent(getContext(), ShowPersonalDetailsActivity.class);

        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());
        Log.e("book.getPat_id()", "" + book.getPat_id());
        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());

        i.putExtra("PHONE", book.getMobileNumber());

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
        Log.e("img", "" + book.getPres_img());
        startActivity(i);
    }

    private void followUpDateSearchAdapterToRecyclerView(int position) {

        RegistrationModel book = filteredModelList.get(position);

        Intent i = new Intent(getContext(), ShowPersonalDetailsActivity.class);

        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());
        Log.e("book.getPat_id()", "" + book.getPat_id());
        i.putExtra("NAME", book.getFirstName() + " " + book.getLastName());
        i.putExtra("FIRSTTNAME", book.getFirstName());
        i.putExtra("MIDDLENAME", book.getMiddleName());
        i.putExtra("LASTNAME", book.getLastName());
        i.putExtra("DOB", book.getDob());

        i.putExtra("PHONE", book.getMobileNumber());

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
        Log.e("img", "" + book.getPres_img());
        startActivity(i);
    }

    //open date dialog
    private void showDialog() {
        switch (ConsultationLogFragment.DATE_DIALOG_ID) {
            case DATE_DIALOG_ID:
                final Calendar c2 = Calendar.getInstance();
                int mYear2 = c2.get(Calendar.YEAR);
                int mMonth2 = c2.get(Calendar.MONTH);
                int mDay2 = c2.get(Calendar.DAY_OF_MONTH);
                Log.d("date", "" + mDay2);
                System.out.println("" + mDay2 + "-" + mMonth2 + "-" + mYear2);

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


    // TODO: Rename method, update argument and hook method into UI event


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
    public void onDetach() {
        super.onDetach();
        if(sqlController != null){
            sqlController= null;
        }

        view = null; // now cleaning up!
        mListener = null;
        Log.e("onDetach","onDetach");
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //This method will filter data from our database generated list according to user query 6/8/16 Ashish
    private List<RegistrationModel> filter(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {

            String fod;
            try {
                // fod = model.getFollowUpDate();
                fod = model.getActualFollowupDate();


                if (fod.contains(query)) {

                    filteredModelList.add(model);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return filteredModelList;
    }

    //This method will filter data from our database generated list according to user query 6/8/16 Ashish
    private List<RegistrationModel> visitDateFilter(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {
            final String visit_date = model.getVisit_date();

            if (visit_date.contains(query)) {

                filteredModelList.add(model);

            }
        }
        return filteredModelList;
    }
    //show captured patient image into image view
    private void setupAnimation() {

        backChangingImages = (ImageView) view.findViewById(R.id.backChangingImages);

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

}


