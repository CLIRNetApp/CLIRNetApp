package app.clirnet.com.clirnetapp.Utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.reports.MyAxisValueFormatter;

public class BarChartItem extends ChartItem {

    private Typeface mTf,mTfRegular,mTfLight;

    public BarChartItem(ChartData<?> cd, Context c) {
        super(cd);

       mTfRegular = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
      //  mTfLight = Typeface.createFromAsset(c.getAssets(), "OpenSans-Light.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = (BarChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.getDescription().setEnabled(false);
        holder.chart.setDrawGridBackground(false);
        holder.chart.setDrawBarShadow(false);

     /*   XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        //leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);*/
       // rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //holder.chart.setOnChartValueSelectedListener(true);

        holder.chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        holder.chart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        holder.chart.setPinchZoom(false);

        holder.chart.setDrawGridBackground(false);
        holder.chart.setDrawBarShadow(false);

        holder.chart.setDrawValueAboveBar(false);
        holder.chart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis =  holder.chart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        holder.chart.getAxisRight().setEnabled(false);

        XAxis xLabels =  holder.chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);

        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        // setting data
       /* mSeekBarX.setProgress(12);
        mSeekBarY.setProgress(100);*/

        Legend l =  holder.chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);


       // mChartData.setValueTypeface(mTf);

        // set data
        holder.chart.setData((BarData) mChartData);
        holder.chart.setFitBars(true);

        // do not forget to refresh the chart
        //holder.chart.invalidate();
        holder.chart.animateY(700);

        return convertView;
    }

    private static class ViewHolder {
        BarChart chart;
    }

}