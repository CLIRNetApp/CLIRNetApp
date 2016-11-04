package app.clirnet.com.clirnetapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.Utility.ItemClickListener;
import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.adapters.RVAdapter;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.RegistrationModel;


/**
 * Created by ${Ashish} on 22-04-2016.
 */
public class ReportFragment extends Fragment implements RecyclerView.OnItemTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private RecyclerView Searchrecycler_view;
    private RVAdapter rvadapter;
    private ArrayList<RegistrationModel> patientData = new ArrayList<>();
    private SQLController sqlController;

    private SearchView searchView;
    private List<RegistrationModel> filteredModelList;

//this is of no use
    public ReportFragment() {
        this.setHasOptionsMenu(true);

    }


    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootview = inflater.inflate(R.layout.fragment_payment, container, false);


        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        Searchrecycler_view = (RecyclerView) rootview.findViewById(R.id.Searchrecycler_view);

        LinearLayout errorimg = (LinearLayout) rootview.findViewById(R.id.nwerror);


        rvadapter = new RVAdapter(patientData);
        try {

            sqlController = new SQLController(getContext());
            sqlController.open();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            patientData = (sqlController.getPatientList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //To check if there is data or not in arraylist in case of no internet connectivity or no data downloads

        if (patientData.size() <= 0) {
            errorimg.setVisibility(View.VISIBLE);
        } else {

          //  recyclerView.setAdapter(adapter);
        }


        return rootview;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //class to implement OnClick Listner
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private final GestureDetector gestureDetector;
        private final ItemClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemClickListener clickListener) {
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
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        MenuItem item = menu.findItem(R.id.search);

        //  MenuItem item = menu.findItem(R.id.spinner);
        try {
            searchView = new SearchView(((NavigationActivity) getActivity()).getSupportActionBar().getThemedContext());
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        searchView.setIconifiedByDefault(false);
        searchView.setInputType(InputType.TYPE_CLASS_PHONE);
        searchView.setQueryHint(getResources().getString(R.string.hint_search));


        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.d("posi", "" + position);
                System.out.println("on clicked: " + position);
                // lv.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("query", "" + query);
                // isInternetPresent = cd.isConnectingToInternet();

                // if (isInternetPresent && map.isLoaded()) {
                //  new SuggestionQueryFeatureLayer().execute(query);
                System.out.println("on query submit: " + query);
                searchView.clearFocus();
                //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
                // } else {
                // we May  need to show dialog to display if not connected to Internet. 6-6-16 Ashish
                //  Toast.makeText(getContext(), " You are not connected to Internet ", Toast.LENGTH_LONG).show();
                // }
                //   new SuggestionQueryFeatureLayer().execute(query);
                System.out.println("on query submit: " + query);
                searchView.clearFocus();


                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // isInternetPresent1 = cd.isConnectingToInternet();
                // if (isInternetPresent1 && map.isLoaded()) {
                if (newText.length() > 1) {
                    //  new SuggestionQueryFeatureLayer().execute(newText);
                    Searchrecycler_view.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);


                    Log.e("onQueryTextChange", "" + newText);
                    filteredModelList = filter(patientData, newText);

                    Log.e("filteredModelList", "" + filteredModelList.size());



                    rvadapter.setFilter(filteredModelList);
                    Searchrecycler_view.setAdapter(rvadapter);
                }
                System.out.println("on query submit: " + newText);

                Searchrecycler_view.addOnItemTouchListener(new HomeFragment.RecyclerTouchListener(getContext(), Searchrecycler_view, new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Log.d("click", "click" + position);
                        RegistrationModel model = filteredModelList.get(position);

                        Toast.makeText(getContext(), "click click" + model.getFirstName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }


    //This method will filter data from our database generated list according to user query 6/8/16 Ashish
    private List<RegistrationModel> filter(List<RegistrationModel> models, String query) {
        query = query.toLowerCase();

        final List<RegistrationModel> filteredModelList = new ArrayList<>();
        for (RegistrationModel model : models) {
            final String title = model.getMobileNumber().toLowerCase();
            final String facility = model.getFirstName().toLowerCase();
            // final String dob = model.getDob().toLowerCase();

            if (title.contains(query) || facility.contains(query)) {

                filteredModelList.add(model);
                Log.e("result", "" + title);
            }
        }
        return filteredModelList;
    }

}


