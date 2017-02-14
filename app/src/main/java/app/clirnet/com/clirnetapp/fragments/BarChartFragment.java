package app.clirnet.com.clirnetapp.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.reports.GenderWiseDataModel;

public class BarChartFragment extends android.support.v4.app.Fragment {

    private String mFromDate;
    private String mToDate;

    private OnFragmentInteractionListener mListener;
    private View view;

    private BarEntry v1e1;
    private BarEntry v2e1;
    private ArrayList<String> ageBoundlis;
    private BarChart chart;
    private ArrayList<String> date;
    private List<Integer> male;
    private AppController appController;



    public static BarChartFragment newInstance(String param1, String param2) {
        BarChartFragment fragment = new BarChartFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public BarChartFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface mTfRegular = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_bar_chart, container, false);
        chart = (BarChart) view.findViewById(R.id.chart);

        mFromDate = getArguments().getString("FROMDATE");
        mToDate = getArguments().getString("TODATE");

        //set the data to chart
        getDataSet();

        //////////////////////
        if (appController == null) {
            appController = new AppController();
        }



        Utils.init(getResources());
        Utils.init(getContext());

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);

        //allow to set value of bar to top
        chart.setDrawValueAboveBar(true);

        chart.invalidate();

        return view;
    }

    private void getDataSet() {

        ArrayList<IBarDataSet> dataSets = null;
        ArrayList<BarEntry> male = new ArrayList<>();
        ArrayList<BarEntry> female = new ArrayList<>();
        ArrayList<String> listSetAgeBound = new ArrayList<>();

        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();


            ArrayList<GenderWiseDataModel> gd;
            gd = sqlController.genderWiseData(mFromDate, mToDate);


            int size1 = gd.size();

            if (size1 > 0) {
                for (int im = 0; im < size1; im++) {
                    int mcount = Integer.parseInt(gd.get(im).getmCount());
                    int fCount = Integer.parseInt(gd.get(im).getfCount());
                    String ageBound = gd.get(im).getAgeBound();


                    v1e1 = new BarEntry(mcount, im);
                    v2e1 = new BarEntry(fCount, im);

                    male.add(v1e1);
                    female.add(v2e1);
                    listSetAgeBound.add(ageBound);



                }

            }
            Utils.init(getResources());
            Utils.init(getContext());
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
            xAxis.setGridColor(getResources().getColor(R.color.light_grey));
            xAxis.setDrawAxisLine(true);//if true this will draw line after setting data to x axis ie after 0
            xAxis.setTextSize(13f);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setAxisMaxValue(8);

            YAxis leftAxis = chart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setLabelCount(5, false);
            leftAxis.setTextSize(14f);
            leftAxis.setGridColor(getResources().getColor(R.color.light_grey));
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
            chart.setPinchZoom(false);
            chart.setTouchEnabled(false);//disable touch gesture on chart view 10-112-2016

            chart.setData(data);
            chart.setDescription(null);//this will not show the chart description

            //  chart.invalidate();


        } catch (Exception e) {
          //  appController.appendLog(appController.getDateTime() + " " + "/ " + "BarChartFragment" + e+" "+Thread.currentThread().getStackTrace()[2].getLineNumber());
            e.printStackTrace();
        }

    }


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
        if(appController !=null){
            appController=null;
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
        if (chart != null) {
            chart = null;
        }
        if (ageBoundlis != null) {
            ageBoundlis = null;
        }

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


}
