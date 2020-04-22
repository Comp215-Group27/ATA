package Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.map2.R;

import java.util.ArrayList;
/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is created to show the string arrayList in the list view
 */
public class ShowcaseAdaptor extends BaseAdapter {
    LayoutInflater mInflator;

    ArrayList<String> names;

    public ShowcaseAdaptor(Context c, ArrayList<String> name){
        names = name;


        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.showcase_detail, null);
        TextView attractionName = (TextView) v.findViewById(R.id.showcaseName);
        // TextView attractionRate = (TextView) v.findViewById(R.id.showcaseRate);

        String name = names.get(position);

        attractionName.setText(name);

        return v;
    }
}
