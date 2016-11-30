package app.clirnet.com.clirnetapp.reports;

/**
 *
 */

import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * baseclass of the chart-listview items
 * Created by CLIRNET1 on 11/17/2016.
 *public ChartItem() {
    }
 */
public abstract class ChartItem {

    protected static final int TYPE_BARCHART = 0;
    protected static final int TYPE_LINECHART = 1;
    protected static final int TYPE_PIECHART = 2;

    protected ChartData<?> mChartData;
    ArrayList<IBarDataSet> iBarDataSets;


    public ChartItem(ChartData<?> cd) {
        this.mChartData = cd;
    }

    public ChartItem(ArrayList<IBarDataSet> iBarDataSets, Context context) {
        this.iBarDataSets=iBarDataSets;
    }


    public abstract int getItemType();

    public abstract View getView(int position, View convertView, Context c);
}