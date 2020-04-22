package Community.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

import java.util.ArrayList;

import Community.Common.CommentAdaptor;
import Community.Common.DBOpenHelperComment;
import Community.Common.commentFormat;

import static Login.loginActivity.userID;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool */

/** A class to show a list of plans, in order to allow users to choose to delete */

public class Delete extends AppCompatActivity {

    ListView deleteList;
    private DBOpenHelperComment mDBOpenHelperComment;
    ArrayList<commentFormat> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
            mDBOpenHelperComment = new DBOpenHelperComment(this);
        ArrayList<commentFormat> t = mDBOpenHelperComment.getAllData();
        if (t.size()>0){
            // to get personal plans
            for (int i=0;i<t.size();i++){
                commentFormat temp=t.get(i);
                if (temp.getuserID()==userID){
                    list.add(temp);
                }
            }
            // show personal plans as a list view and allow users to choose
            CommentAdaptor adaptor = new CommentAdaptor(this, list );
            deleteList = (ListView) findViewById(R.id.changeLV3);
            deleteList.setAdapter(adaptor);
            deleteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                commentFormat changed = list.get(position);
                if (userID==changed.getuserID()) {
                    mDBOpenHelperComment.deleteRow(changed.getRandomATA());
                    Intent i2 = new Intent(Delete.this, show.class);
                    startActivity(i2);
                    Toast.makeText(Delete.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Intent i1 = new Intent(Delete.this, Delete.class);
                    startActivity(i1);
                    Toast.makeText(Delete.this, "You can't delete other people's record!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        });
        }




    }

    public void onClick(View view) {
        switch (view.getId()) {
            // to show class
            case R.id.cancel3:
                Intent i1 = new Intent(Delete.this , show.class);
                startActivity(i1);
                finish();
                break;

        }
    }

}
