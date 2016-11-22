package app.clirnet.com.clirnetapp.Utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.models.Counts;
import app.clirnet.com.clirnetapp.reports.MyMarkerView;

import static java.security.AccessController.getContext;


public class LineChartItem extends ChartItem implements
        OnChartGestureListener, OnChartValueSelectedListener {

    private Typeface mTf;
    Context mContext;
    private ViewHolder holder;
    private ArrayList<String> date;
    private ArrayList<String> nocountsperday;
    private String fromDate, toDate;


    public LineChartItem(ChartData<?> cd, Context c,String fromDate,String toDate) {
        super(cd);
        this.mContext = c;
        this.fromDate=fromDate;
        this.toDate=toDate;

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_linechart, null);
            holder.chart = (LineChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.chart.setOnChartGestureListener(this);
        holder.chart.setOnChartValueSelectedListener(this);
        holder.chart.setDrawGridBackground(false);

        // no description text
        holder.chart.getDescription().setEnabled(false);

        // enable touch gestures
        holder.chart.setTouchEnabled(true);

        // enable scaling and dragging
        holder.chart.setDragEnabled(true);
        holder.chart.setScaleEnabled(true);
        // holder.chart .setScaleXEnabled(true);
        // holder.chart .setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        holder.chart.setPinchZoom(true);

        // set an alternative background color
        // holder.chart .setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(holder.chart); // For bounds control
        holder.chart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setTypeface(tf);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(1000f);
        leftAxis.setAxisMinimum(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        holder.chart.getAxisRight().setEnabled(false);

        //holder.chart .getViewPortHandler().setMaximumScaleY(2f);
        //holder.chart .getViewPortHandler().setMaximumScaleX(2f);

        // add data
      //////////////  setData(45, 100);

//        holder.chart .setVisibleXRange(20);
//        holder.chart .setVisibleYRange(20f, AxisDependency.LEFT);
//        holder.chart .centerViewTo(20, 50, AxisDependency.LEFT);

        holder.chart.animateX(2500);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = holder.chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // holder.chart.setData((LineData) mChartData);


        // apply styling
        // holder.chart.setValueTypeface(mTf);
        // holder.chart.getDescription().setEnabled(false);
        /*holder.chart.setDrawGridBackground(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
       // leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
       // rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chart.setData((LineData) holder.chart Data);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateX(750);*/

        holder.chart.setData((LineData) mChartData);

        return convertView;
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();
       /* for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val));
        }*/
        try {
            SQLController sqlController = new SQLController(mContext);
            sqlController.open();

            ArrayList<Counts> countsNo;
            date = new ArrayList<>();
            nocountsperday = new ArrayList<>();
            countsNo = sqlController.countPerDay(fromDate, toDate);
            int size = countsNo.size();
            Log.e("nOoFrECORDS", " " + countsNo.size());
            for (int im = 0; im < size; im++) {
                String a = countsNo.get(im).getCount();
                String b = countsNo.get(im).getDate().trim();
                nocountsperday.add(a);
                date.add(b);
            }
            int size1 = nocountsperday.size();
            for (int in = 0; in < size1; in++) {
                int no = Integer.parseInt(nocountsperday.get(in));
                values.add(new Entry(in, no));
            }

            Log.e("coolby", " " + date.size() + "date is  " + nocountsperday.size());

        } catch (Exception e) {
            e.printStackTrace();
            new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Home" + e);
        }

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(values, "Daily Patients Updates");

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
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        holder.chart.setData(data);
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private static class ViewHolder {
        LineChart chart;
    }
}