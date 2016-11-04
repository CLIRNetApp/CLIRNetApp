package app.clirnet.com.clirnetapp.Utility;

import android.view.View;

//interface to handle onclick on recycler view
public interface ItemClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}



