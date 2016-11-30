package app.clirnet.com.clirnetapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.numetriclabz.numandroidcharts.LineChart;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.adapters.ChartDataAdapter;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.Counts;
import app.clirnet.com.clirnetapp.reports.ChartItem;
import app.clirnet.com.clirnetapp.reports.LineChartItem;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragmenlt.
 */
//we are calling two seprate fragments for each report ie for daily patient count and age wise report from this fragment class
public class PatientReportFragment extends Fragment {


    private ArrayList<String> dateList;
    private ArrayList<String> nocountsperday;

    private OnFragmentInteractionListener mListener;
    private View view;

    private AppController appController;
    private String fromDate, toDate;
    LineChart Chart;
    private LinkedList<String> newdate;


    public PatientReportFragment() {
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

        view = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_patient_update, container, false);


        appController = new AppController();
        this.setValuesSharedPrefrence("one");

        ListView lv = (ListView) view.findViewById(R.id.listView1);

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();


        list.add(new LineChartItem(generateDataLine(1), getContext(), fromDate, toDate));
       //list.add(new BarChartItem(generateDataBarNew(), getContext()));



        ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
        lv.setAdapter(cda);
//This will display multibar chart with achartengine lib,commented on 29/11-2016 By Ashish
        /*BarChartFragment fragment1 = new BarChartFragment( fromDate, toDate);

        FragmentManager fragmentManager1 = getChildFragmentManager();
        fragmentManager1.beginTransaction().replace(R.id.flContent, fragment1).commit();*/

     NewBarChartFragment fragment2 = new NewBarChartFragment( fromDate, toDate);

        FragmentManager fragmentManager2 = getChildFragmentManager();
        fragmentManager2.beginTransaction().replace(R.id.flContent1, fragment2).commit();




        return view;
    }







    private ChartData<?> generateDataLine(int i) {

        ArrayList<Entry> values = new ArrayList<>();
        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();

            ArrayList<Counts> countsNo;
            dateList = new ArrayList<>();
            nocountsperday = new ArrayList<>();
            countsNo = sqlController.countPerDay(fromDate, toDate);


            int size = countsNo.size();
            Log.e("nOoFrECORDS", " " + countsNo.size());
            if (size > 0) {
                for (int im = 0; im < size; im++) {
                    String a = countsNo.get(im).getCount();
                    String b = countsNo.get(im).getDate().trim();
                    Log.e("DateandCount", " " + a + "dateList is  " + b);
                    nocountsperday.add(a);
                    dateList.add(b);

                }
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            newdate = new LinkedList<>();
            List<String> newcount = new LinkedList<>();
            try {
                Date stdate = format.parse(fromDate);
                Date eddate = format.parse(toDate);

                String newsd=format1.format(stdate);
                String newed=format1.format(eddate);

                Date d1=format1.parse(newsd);
                Date d2=format1.parse(newed);

                Log.e("stdate",""+newsd + "  "+newed);

                Calendar start = Calendar.getInstance();
                start.setTime(d1);
                Calendar end = Calendar.getInstance();
                end.setTime(d2);
                end.add(Calendar.DATE, 1);

//here we arechecking if date is present in dateList or not which came from db if not we r adding customly date and count which is 0. 29/11-2016 By.Ashish
                for (Date date1 = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date1 = start.getTime()) {

                    Format formatter = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat   sdf = new SimpleDateFormat("dd-MM");
                    Date date;
                    String s = formatter.format(date1);

                    if (dateList.contains(s)) {
                        int getindex = dateList.indexOf(s);
                        String countval =nocountsperday.get(getindex);

                        date =sdf.parse(s);
                        System.out.println(date);
                        System.out.println(sdf.format(date));
                        Log.e("sdf.format(date)", "" + sdf.format(date));
                        String convertedDate=sdf.format(date);
                        newdate.add(convertedDate);
                        newcount.add(countval);

                    } else {
                        date =sdf.parse(s);
                        System.out.println(date);
                        System.out.println(sdf.format(date));
                        Log.e("sdf.format(date)", "" + sdf.format(date));
                        String convertedDate=sdf.format(date);
                        newdate.add(convertedDate);
                        newcount.add("0");
                    }
                }
            } catch (ParseException e) {

                e.printStackTrace();
            }

            for(int i1 = 0 ; i1 < newdate.size() ; i1++) {

                System.out.println(newdate.get(i1) + " " + newcount.get(i1));
            }

            int size1 = newcount.size();
            if (size1 > 0) {
                for (int in = 0; in < size1; in++) {
                    Float no = Float.valueOf(newcount.get(in));
                    values.add(new Entry(no, in));
                    //  values.add(new Entry(no,in));
                }
            } else {
                Toast.makeText(getContext(), "No Data To Display.", Toast.LENGTH_LONG).show();
            }

            Log.e("coolby", " " + dateList.size() + "dateList is  " + nocountsperday.size());

        } catch (Exception e) {
            e.printStackTrace();
            new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Home" + e);
        }

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(values, "Day Wise Count");

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

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

      //Sort the dateList Array list dateList wise
        //sortDate(dateList);

        // create a data object with the datasets
        LineData data = new LineData(newdate,dataSets);

        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(16f);
        return data;


    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
    private void sortDate(ArrayList<String> date) {
        Collections.sort(date, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    private ChartData<?> generateDataBar(int i) {


        ArrayList<BarEntry> entries = new ArrayList<>();
        try {
            SQLController sqlController = new SQLController(getContext());
            sqlController.open();
            //dbController = new SQLiteHandler(getContext());
            ArrayList<Counts> countsNo;
            dateList = new ArrayList<>();
            nocountsperday = new ArrayList<>();
            countsNo = sqlController.countPerDay();
            int size = countsNo.size();
            Log.e("nOoFrECORDS", " " + countsNo.size());
            for (int im = 0; im < size; im++) {
                String a = countsNo.get(im).getCount();
                String b = countsNo.get(im).getDate();
                nocountsperday.add(a);
                dateList.add(b);
            }
            int size1 = nocountsperday.size();
            for (int in = 0; in < size1; in++) {
                int no = Integer.parseInt(nocountsperday.get(in));
                entries.add(new BarEntry(no, in));
            }

            Log.e("coolby", " " + dateList.size() + "dateList is  " + nocountsperday.size());

        } catch (Exception e) {
            e.printStackTrace();
            appController.appendLog(appController.getDateTime() + " " + "/ " + "Home" + e);
        }
        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(dateList,bardataset);
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
