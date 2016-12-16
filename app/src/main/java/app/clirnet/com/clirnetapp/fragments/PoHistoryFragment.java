package app.clirnet.com.clirnetapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.PrivacyPolicy;
import app.clirnet.com.clirnetapp.activity.ShowPersonalDetailsActivity;
import app.clirnet.com.clirnetapp.activity.TermsCondition;
import app.clirnet.com.clirnetapp.adapters.MultipleFilterPatientAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


public class PoHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private final int[] imageArray = {R.drawable.brand, R.drawable.brethnum, R.drawable.deptrim, R.drawable.fenjoy, R.drawable.hapiom, R.drawable.liporev, R.drawable.magnamet, R.drawable.motirest, R.drawable.revituz, R.drawable.suprizon};
    private OnFragmentInteractionListener mListener;
    private EditText firstName;
    private EditText lastName;
    private EditText phone_no;
    private TextView age;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private String sex;
    private String strfname;
    private String strlname;
    private String strpno;
    private String strage;

    private SQLController sqlController;

    private MultipleFilterPatientAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView backChangingImages;
    private ArrayList<RegistrationModel> patientData = new ArrayList<>();
    //private ArrayList<RegistrationModel> patientData1 = new ArrayList<>();
    private LinearLayout norecordtv;
    private View rootview;
    private AppController appController;
    private Button submit;


    public PoHistoryFragment() {
        this.setHasOptionsMenu(true);

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


        TextView currdate = (TextView) rootview.findViewById(R.id.sysdate);
        backChangingImages = (ImageView) rootview.findViewById(R.id.backChangingImages);
        norecordtv = (LinearLayout) rootview.findViewById(R.id.norecordtv);


        phone_no = (EditText) rootview.findViewById(R.id.mobile_no);
        age = (TextView) rootview.findViewById(R.id.age);
        radioSexGroup = (RadioGroup) rootview.findViewById(R.id.radioGender);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM,yyyy");
        Date todayDate = new Date();
        String dd = sdf.format(todayDate);


        currdate.setText("Today's Date " + dd);

        TextView privacyPolicy = (TextView) rootview.findViewById(R.id.privacyPolicy);
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
        appController = new AppController();

        //appController. setValuesSharedPrefrence("VALUE", "one");


        setupAnimation();
        try {

            sqlController = new SQLController(getContext().getApplicationContext());
            sqlController.open();

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Frgament" + e);
        }


        patientData.clear(); //This method will clear all previous data from  Array list  24-8-2016


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
                strage = age.getText().toString().trim();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) rootview.findViewById(selectedId);

                //Toast.makeText(getContext(), " Gender" + radioSexButton.getText().toString(), Toast.LENGTH_SHORT).show();
                sex = radioSexButton.getText().toString();
                //This method will clear all previous data from  Array list  24-8-2016

                try {
                    patientData = (sqlController.getFilterDatanew(strfname, strlname, sex, strpno, strage));


                    if (patientData.size() > 0) {
                        removeDuplicate(patientData);
                        /*for (int i = 0; i < patientData.size(); i++) {
                            String mn = patientData.get(i).getFirstName();
                            String ln = patientData.get(i).getLastName();
                            String mobNo = patientData.get(i).getMobileNumber();
                            String age = patientData.get(i).getAge();


                            patientData1 = filter(patientData, mn, ln, mobNo, age);

                        }*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Fragment" + e);
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
                        adapter = new MultipleFilterPatientAdapter(patientData);
                        recyclerView.setAdapter(adapter);

                    } else {

                        recyclerView.setVisibility(View.INVISIBLE);
                        norecordtv.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    appController.appendLog(appController.getDateTime() + " " + "/ " + "Po History Fragment" + e);
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
        return rootview;
    }

    private void recyclerViewOnClick(int position) {

        RegistrationModel book = patientData.get(position);

        Intent i = new Intent(getContext().getApplicationContext(), ShowPersonalDetailsActivity.class);
        Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
        bundle.putString("VENUE_NAME", "Umredkar");
//Add the bundle to the intent
        i.putExtras(bundle);
        i.putExtra("PATIENTPHOTO", book.getPhoto());
        i.putExtra("ID", book.getPat_id());

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
        i.putExtra("FROMWHERE", "3"); //thi will identify from which fragment we are navigating

        startActivity(i);


               /* Toast.makeText(getContext(), book.getFirstName() + " is selected!", Toast.LENGTH_SHORT).show();*/
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        backChangingImages.postDelayed(runnable, 100); //for initial delay..
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
        if (adapter != null) {
            adapter = null;
        }

        if (appController != null) {
            appController = null;
        }


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
        radioSexGroup = null;
        radioSexButton = null;
        submit = null;
        Log.e("onDetach", "onDetach Home Fragment");
    }

    /*//This method will filter data from our database generated list according to user query by Phone Number 6/8/i Ashish
    private ArrayList<RegistrationModel> filter(List<RegistrationModel> models, String fn, String ln, String mobileno, String agestr) {

        fn = fn.toLowerCase();
        ln = ln.toLowerCase();
        mobileno = mobileno.toLowerCase();
        agestr = agestr.toLowerCase();

        final ArrayList<RegistrationModel> filteredModelList = new ArrayList<>();
        //final ArrayList<RegistrationModel> filteredModelList1 = new ArrayList<>();
        for (RegistrationModel model : models) {
            final String fname = model.getFirstName().toLowerCase();
            final String mname = model.getLastName().toLowerCase();
            final String mobno = model.getMobileNumber().toLowerCase();
            final String age = model.getAge().toLowerCase();

            if (fname.contains(fn) && mname.contains(ln) && mobno.contains(mobileno) && age.contains(agestr)) {

                filteredModelList.remove(model);
                Log.e("result", "" + fname);
            } else {
                filteredModelList.add(model);
                Log.e("Allready", "Record is allready there");
            }
        }
        return filteredModelList;
    }*/

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

        System.out.println(al);

    }
}


