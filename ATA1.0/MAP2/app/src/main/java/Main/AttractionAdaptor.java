package Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.map2.R;

/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is craeted to show the string array in the list view
 */
public class AttractionAdaptor extends BaseAdapter {
    LayoutInflater mInflator;

    String [] attractions;

    public AttractionAdaptor(Context c, String[] attraction){
        attractions = attraction;

        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return attractions.length;
    }

    @Override
    public Object getItem(int position) {
        return attractions [position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.london_attraction_detail, null);
        TextView attractionName = (TextView) v.findViewById(R.id.attractions);
        String name = attractions[position];
        attractionName.setText(name);

        return v;
    }
}
