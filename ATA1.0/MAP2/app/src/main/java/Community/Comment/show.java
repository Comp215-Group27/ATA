package Community.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

import java.util.ArrayList;

import Community.Common.Community;
import Community.Common.DBOpenHelperComment;
import Community.Common.commentFormat;

import static Main.Menu2.statusOfLogin;
import static Login.loginActivity.userID;


/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool */

/** A class to show all personal plans */

public class show extends AppCompatActivity implements View.OnClickListener{

    private DBOpenHelperComment mDBOpenHelperComment;
    private Button btn1_delete;
    private Button btn2_edit;
    ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        initView();

        mDBOpenHelperComment = new DBOpenHelperComment(this);
        ArrayList<commentFormat> t = mDBOpenHelperComment.getAllData();
        ArrayList<commentFormat> list = new ArrayList<>();
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        // get personal plans
        if (t.size() > 0) {
            for (int i=0;i<t.size();i++){
                commentFormat temp=t.get(i);
                if (temp.getuserID()==userID){
                    list.add(temp);
                }
            }

            for (commentFormat c : list) {
                TextView tv = new TextView(this);
                tv.setText(c.toString());
                tv.setTextSize(18);
                ll.addView(tv);

            }
        }else{
            TextView tv2 = new TextView(this);
            tv2.setText("No plans yet");
            tv2.setTextSize(18);
            ll.addView(tv2);
        }


        if(statusOfLogin==0){
            Button deleteBT =findViewById(R.id.delete);
            deleteBT.setEnabled(false);
        }


        backButton = findViewById(R.id.backbtn_show);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(show.this,Community.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void initView() {
        btn1_delete= findViewById(R.id.delete);
        btn2_edit= findViewById(R.id.edit);

        btn1_delete.setOnClickListener(this);
        btn2_edit.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            // to Delete activity
            case R.id.delete:
                Intent intent = new Intent(this, Delete.class);
                startActivity(intent);
                finish();
                break;

            // to Edit activity
            case R.id.edit:
                Intent intent2 = new Intent(this, Edit.class);
                startActivity(intent2);
                finish();
                break;






        }
        }


    }



