package app.clirnet.com.clirnetapp.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.reports.GenderWiseDataModel;

public class NewBarChartFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mFromDate;
    private String mToDate;

    private OnFragmentInteractionListener mListener;
    private View view;

    private BarEntry v1e1;
    private BarEntry v2e1;
    private Typeface mTfRegular;
    private ArrayList<String> ageBoundlis;
    private BarChart chart;
    private ArrayList<String> date;
    private ArrayList<String> listSetAgeBound;
    private List<Integer> male;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewBarChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewBarChartFragment newInstance(String param1, String param2) {
        NewBarChartFragment fragment = new NewBarChartFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public NewBarChartFragment() {
        // Required empty public constructor
    }

    public NewBarChartFragment(String fromDate, String toDate) {
        this.mFromDate = fromDate;
        this.mToDate = toDate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTfRegular = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_bar_chart, container, false);
        chart = (BarChart) view.findViewById(R.id.chart);

        //set the data to chart
        getDataSet();
        //////////////////////

        //Marker is working bt hide it,we dont want this functionality right now Dt-30-11-2016 By.Ashish
       /* MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
        mv.setGravity(RelativeLayout.CENTER_IN_PARENT);
        // Set the marker to the chart
        chart.setMarkerView(mv);*/


        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        //allow to set value of bar to top
        chart.setDrawValueAboveBar(true);

        //  holder.chart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels



        /*XAxis xLabels = chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);// this will decide how to set the x axis value and where
        //todo uncomment this to test for x axis value
        xLabels.setTextSize(12f);
        xLabels.setTextColor(Color.WHITE);

        //  xLabels.setAxisMinimum(05f);
        //  xLabels.setAxisMinimum(75f);

        // xLabels.setGranularity(1f);
        //  xLabels.setCenterAxisLabels(true);//this will set the x values to center
        xLabels.setTypeface(mTfRegular);
        xLabels.setValueFormatter(new MyValueFormatter());*/

        chart.invalidate();

        return view;
    }

    private void getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;
        ArrayList<BarEntry> male = new ArrayList<>();
        ArrayList<BarEntry> female = new ArrayList<>();
        listSetAgeBound = new ArrayList<String>();

        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();


            ArrayList<GenderWiseDataModel> gd = new ArrayList<>();
            gd = sqlController.genderWiseData(mFromDate, mToDate);


            int size1 = gd.size();

            if (size1 > 0) {
                for (int im = 0; im < size1; im++) {
                    int mcount = Integer.parseInt(gd.get(im).getmCount());
                    int fCount = Integer.parseInt(gd.get(im).getfCount());
                    String ageBound = gd.get(im).getAgeBound();

                    // listSet.add(mcount);
                    //listSetAgeBound.add(ageBound);

                    v1e1 = new BarEntry(mcount, im);
                    v2e1 = new BarEntry(fCount, im);

                    male.add(v1e1);
                    female.add(v2e1);
                    listSetAgeBound.add(ageBound);


                    Log.e("Records789", " " + mcount + "-" + fCount + " - " + ageBound);

                }
                //setDataToLayout();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        Typeface mTf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");

        BarDataSet barDataSet1 = new BarDataSet(male, "Male");
        barDataSet1.setColor(getResources().getColor(R.color.colorPrimary));
        BarDataSet barDataSet2 = new BarDataSet(female, "Female");
        barDataSet2.setColor(getResources().getColor(R.color.bg_login));
        barDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);//if true this will draw line after setting data to x axis ie after 0
        xAxis.setTextSize(13f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisMaxValue(8);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setTextSize(14f);
        leftAxis.setTextColor(Color.BLACK);


        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextSize(14f);
        rightAxis.setTextColor(Color.BLACK);
        rightAxis.setEnabled(false);


        //  BarData data = new BarData(getXAxisValues(), dataSets);
        BarData data = new BarData(listSetAgeBound, dataSets);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextColor(Color.BLACK);
        chart.setDrawValueAboveBar(true);


        chart.setData(data);
        chart.setDescription(null);//this will not show the chart description

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e != null) {
                    String name = chart.getBarData().getXVals().get(e.getXIndex());
                    int val = (int) e.getVal();
                    //  Toast.makeText(getContext(),"Value is "+val,Toast.LENGTH_LONG).show();

                    /*Toast  toast = Toast.makeText(getContext().getApplicationContext(), ""+val +" Patient", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 100);
                    toast.show();*/
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (view != null) {
            view = null;
        }
        mListener = null;
        mFromDate = null;
        mToDate = null;
        if (v1e1 != null) {
            v1e1 = null;
        }
        if (v2e1 != null) {
            v2e1 = null;
        }
        if(chart !=null){
            chart=null;
        }
        if(ageBoundlis!=null){
            ageBoundlis=null;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
