package app.clirnet.com.clirnetapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.reports.BarChartItem;
import app.clirnet.com.clirnetapp.reports.ChartItem;
import app.clirnet.com.clirnetapp.reports.LineChartItem;
import app.clirnet.com.clirnetapp.adapters.ChartDataAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.Counts;
import app.clirnet.com.clirnetapp.reports.GenderWiseDataModel;
import app.clirnet.com.clirnetapp.reports.MyValueFormatter;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PatientUpdateFragment extends Fragment {


    private ArrayList<String> date;
    private ArrayList<String> nocountsperday;

    private OnFragmentInteractionListener mListener;
    private View view;

    private AppController appController;
    private String fromDate, toDate;

  // private static final List<Float> ageBoundArray = Arrays.asList(00.05f, 05.15f, 15.25f, 25.35f, 35.45f, 45.55f, 55.65f, 75.0f);
    private static final List<Float> ageBoundArray = Arrays.asList(05.0f, 15f, 25f, 35f, 45f, 55f, 65f, 75.0f);

    public PatientUpdateFragment() {
        // Required empty public constructor
        this.setHasOptionsMenu(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            fromDate = bundle.getString("FROMDATE");
            toDate = bundle.getString("TODATE");
            Log.e("dateis", "" + fromDate + "" + toDate);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // backChangingImages = null; // now cleaning up!
        view = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_patient_update, container, false);

        //new Counts().setSelectedView("one");
        appController = new AppController();
        this.setValuesSharedPrefrence("one");

        ListView lv = (ListView) view.findViewById(R.id.listView1);

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();

        //setSackedBarView();

        //list.add(new BarChartItem(generateDataBar(2), getContext()));
       list.add(new LineChartItem(generateDataLine(1), getContext(), fromDate, toDate));
       list.add(new BarChartItem(generateDataBarNew(2), getContext()));

        ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
        lv.setAdapter(cda);
        return view;
    }

    List<String> supplierNames = Arrays.asList("00.05", "05.15", "15.25", "25.35", "35.45", "45.55", "55.65", "65");


    private ChartData<?> generateDataBarNew(int e) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();

            ArrayList<GenderWiseDataModel> gd = new ArrayList<>();
            gd = sqlController.genderWiseData(fromDate, toDate);
            int size1 = gd.size();

            int ab = 1;
            if (size1 > 0) {
                for (int im = 0; im < size1; im++) {
                    String mcount = gd.get(im).getmCount();
                    String fCount = gd.get(im).getfCount();
                    String ageBound = gd.get(im).getAgeBound();

                    Float val = ageBoundArray.get(im);
                    // String val=supplierNames.get(im);
                   // int a = Integer.parseInt(val);
                   // float f = Float.parseFloat(val);
                    //  Log.e("asize", ""+f);

                    float val1 = Float.parseFloat(mcount);
                    float val2 = Float.parseFloat(fCount);


                    Log.e("Records", " " + mcount + "-" + fCount + " - " + ageBound);
                   yVals1.add(new BarEntry(val, new float[]{val1, val2}));
                    //yVals1.add(new BarEntry(val1, val2));
                    ab++;
                }
            } else {
                Toast.makeText(getContext(), "No Values To Display.", Toast.LENGTH_LONG).show();
            }

            //    yVals1.add(new BarEntry(, new float[]{ml, m2}, ageBound));
/*
                }
            }*/

            ArrayList<Counts> countsNo;
            date = new ArrayList<>();
            nocountsperday = new ArrayList<>();
            countsNo = sqlController.countPerDay(fromDate, toDate);
            int size = countsNo.size();
            Log.e("nOoFrECORDS", " " + countsNo.size());
           /* for (int im = 0; im < size; im++) {
                String a = countsNo.get(im).getCount();
                float val1= Float.parseFloat(a);
                float val2= val1+10;
               // float val2= im;
                String b = countsNo.get(im).getDate().trim();
                float simpleVal= Float.parseFloat(b);
             //   yVals1.add(new BarEntry(im, new float[]{val1, val2}));
                //nocountsperday.add(a);
               // date.add(b);
            }
*/

        } catch (Exception b) {
            new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Home" + b);
        }

        /*for (int i = 0; i < 10 + 1; i++) {
            float mult = (i + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
           *//* float val3 = (float) (Math.random() * mult) + mult / 3;*//*

            yVals1.add(new BarEntry(i, new float[]{val1, val2}));
        }*/

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "Patients By Gender and Age");
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{"Male", "Female"});

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextColor(Color.BLACK);


        // mChart.setData(data);

        return data;
    }

    private ChartData<?> generateDataLine(int i) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();

            ArrayList<Counts> countsNo;
            date = new ArrayList<>();
            nocountsperday = new ArrayList<>();
            countsNo = sqlController.countPerDay(fromDate, toDate);
            int size = countsNo.size();
            Log.e("nOoFrECORDS", " " + countsNo.size());
            if (size > 0) {
                for (int im = 0; im < size; im++) {
                    String a = countsNo.get(im).getCount();
                    String b = countsNo.get(im).getDate().trim();
                    nocountsperday.add(a);
                    date.add(b);
                }
            }
            int size1 = nocountsperday.size();

            if (size1 > 0) {
                for (int in = 0; in < size1; in++) {
                    int no = Integer.parseInt(nocountsperday.get(in));


                    values.add(new Entry(in, no));
                    //  values.add(new Entry(no,in));
                }
            } else {
                Toast.makeText(getContext(), "No Data To Display.", Toast.LENGTH_LONG).show();
            }

            Log.e("coolby", " " + date.size() + "date is  " + nocountsperday.size());

        } catch (Exception e) {
            e.printStackTrace();
            new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Home" + e);
        }

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(values, "DataSet 1");

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        //   holder.chart.setData(data);

            return data;


    }

    private ChartData<?> generateDataBar(int i) {


        ArrayList<BarEntry> entries = new ArrayList<>();
        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();
            //dbController = new SQLiteHandler(getContext());
            ArrayList<Counts> countsNo;
            date = new ArrayList<>();
            nocountsperday = new ArrayList<>();
            countsNo = sqlController.countPerDay();
            int size = countsNo.size();
            Log.e("nOoFrECORDS", " " + countsNo.size());
            for (int im = 0; im < size; im++) {
                String a = countsNo.get(im).getCount();
                String b = countsNo.get(im).getDate();
                nocountsperday.add(a);
                date.add(b);
            }
            int size1 = nocountsperday.size();
            for (int in = 0; in < size1; in++) {
                int no = Integer.parseInt(nocountsperday.get(in));
                entries.add(new BarEntry(no, in));
            }

            Log.e("coolby", " " + date.size() + "date is  " + nocountsperday.size());

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home" + e);
        }
        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        return data;
    }

    public static final int[] MATERIAL_COLORSNEW = {
            rgb("#50BAA0"), rgb("#3F51B5"), rgb("#e74c3c"), rgb("#3498db")
    };

    private int[] getColors() {

        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = this.MATERIAL_COLORSNEW[i];
        }

        return colors;
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
        void onFragmentInteraction(Uri uri);
    }

    public void setValuesSharedPrefrence(String key) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(new AppController().PREFS_NAME, getContext().MODE_PRIVATE).edit();
        editor.putString("value", key);
        // editor.putInt("idName", 12);
        editor.commit();


        //  Log.e("password", "" + username + "" + password)

    }


}
