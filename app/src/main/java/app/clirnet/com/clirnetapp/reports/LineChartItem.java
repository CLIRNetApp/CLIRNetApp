package app.clirnet.com.clirnetapp.reports;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import app.clirnet.com.clirnetapp.R;


public class LineChartItem extends ChartItem {

    private Typeface mTf;
    Context mContext;
    private ArrayList<String> date;
    private ArrayList<String> nocountsperday;
    private ArrayList<String> mDate;


    public LineChartItem(ChartData<?> cd, Context c, String fromDate, String toDate) {
        super(cd);
        this.mContext = c;
        String fromDate1 = fromDate;
        String toDate1 = toDate;
        Utils.init(mContext.getResources());
        Utils.init(mContext);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

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
        Utils.init(mContext.getResources());
        Utils.init(mContext);
        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextSize(13f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setGridColor(R.color.text_background);
       // xAxis.setAxisMinValue(0f);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setTextSize(14f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setGridColor(mContext.getResources().getColor(R.color.light_grey));
     //   leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        rightAxis.setTextSize(14f);
       // rightAxis.setEnabled(false);
      //rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chart.setData((LineData) mChartData);
        holder. chart.setDescription(null);//this will not show the chart description
        mChartData.setValueFormatter(new LargeValueFormatter()); //this will change flost vslue 1.00 to 1

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateX(750);
       // holder.chart.invalidate();
        /*LineChartMarkerView mv = new LineChartMarkerView(c, R.layout.linechart_marker_view);
        mv.setEnabled(true);
        mv.setGravity(position);

        holder.chart.setMarkerView(mv); // Set the marker to the chart*/

        return convertView;
    }


    private static class ViewHolder {
        LineChart chart;
    }
}