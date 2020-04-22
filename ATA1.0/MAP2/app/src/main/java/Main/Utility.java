package Main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool, referred to several codes in Baidu */

/** A class to calculate the height of the attraction list used in class "GeneratePlanActivity" */

public class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        //get the adaptor of the ListView
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   // to get the number of Data items
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  // to calculate the width and height of the child View
            totalHeight += listItem.getMeasuredHeight();  // to count the total height of all children

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)))*2; // based on each of our data items has two lines
        listView.setLayoutParams(params);
    }

}

