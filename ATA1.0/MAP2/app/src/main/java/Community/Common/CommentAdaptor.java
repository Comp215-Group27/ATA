package Community.Common;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.map2.R;

import java.util.ArrayList;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool, referred to codes in class "Item" which is created by team member Yifan Zhu
 */

/** A CommentAdaptor class*/

public class CommentAdaptor extends BaseAdapter {
    LayoutInflater mInflator;

    ArrayList<commentFormat> rows;

    public CommentAdaptor(Context c, ArrayList<commentFormat> name){
        rows = name;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = mInflator.inflate(R.layout.delete_detail, null);
        TextView mTextView =(TextView) v.findViewById(R.id.deleteRows);
        commentFormat name = rows.get(position);
        mTextView.setText(name.toString());
        mTextView.setTextSize(15);
        mTextView.setTextColor(Color.BLUE);

        return mTextView;
    }
}
