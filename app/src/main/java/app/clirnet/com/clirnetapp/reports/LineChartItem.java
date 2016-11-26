package app.clirnet.com.clirnetapp.reports;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

import app.clirnet.com.clirnetapp.R;


public class LineChartItem extends ChartItem {

    private Typeface mTf;
    Context mContext;
    private ViewHolder holder;
    private ArrayList<String> date;
    private ArrayList<String> nocountsperday;
    private ArrayList<String> mDate;
    private String fromDate, toDate;


    public LineChartItem(ChartData<?> cd, Context c, String fromDate, String toDate, ArrayList<String> Date) {
        super(cd);
        this.mContext = c;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.mDate = Date;

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
        //  holder.chart.getDescription().setEnabled(false);
        holder.chart.setDrawGridBackground(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        //  leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        //rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chart.setData((LineData) mChartData);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateX(750);

        /*MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view);
        mv.setEnabled(true);

        holder.chart.setMarkerView(mv); // Set the marker to the chart*/
        return convertView;
    }


    private static class ViewHolder {
        LineChart chart;
    }
}