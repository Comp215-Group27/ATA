package Community.Comment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.map2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import Community.Common.Community;
import Community.Common.DBOpenHelperComment;
import Community.Common.commentFormat;
import map.MapsActivity;

import static Login.loginActivity.userID;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool */

/** A class to show the particular user's last plan and allow him to edit */

public class Comment extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelperComment mDBOpenHelperComment;
    private Button mBtCommentactivitySave;
   ImageView mBtCommentactivityReturn;
    private Button mBtCommentactivitySeeMap;
    private EditText mEtCommentactivityComment;
    public String randomATA;
    public ArrayList<String> s;
    commentFormat mlastPlan = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initView();


        mDBOpenHelperComment = new DBOpenHelperComment(this);


        // get all personal plans
        ArrayList<commentFormat> allData=mDBOpenHelperComment.getAllData();
        if (allData.size()>0) {
            ArrayList<commentFormat> individual_plan = new ArrayList<>();
            for (int i = 0; i < allData.size(); i++) {
                commentFormat plan = allData.get(i);
                if (userID == plan.getuserID()) {
                    individual_plan.add(plan);
                }
            }

            if (individual_plan.size()>0) {
                try {
                    mlastPlan = lastPlan(individual_plan);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TextView tv_places = findViewById(R.id.et_comment_places);
                TextView tv = findViewById(R.id.randomID);
                tv_places.setText("places: "+"\n"+mlastPlan.getPlaces());
                tv_places.setTextSize(18);
                randomATA = mlastPlan.getRandomATA();
                tv.setText("Time: "+randomATA);
                tv.setTextSize(18);


            }  else {
                TextView tv = findViewById(R.id.randomID);
                TextView tv_places = findViewById(R.id.et_comment_places);
                tv.setText(" ");
                tv_places.setText("No plans saved");
                mBtCommentactivitySave.setEnabled(false);
            }


        }else{
            TextView tv_places = findViewById(R.id.et_comment_places);
            tv_places.setText("No plans saved");
            mBtCommentactivitySave.setEnabled(false);
        }
    }


    private void initView() {

        mEtCommentactivityComment = findViewById(R.id.et_comment_comment);
        mBtCommentactivitySave = findViewById(R.id.bt_comment_save);
        mBtCommentactivityReturn = findViewById(R.id.bt_comment_return);
        mBtCommentactivitySeeMap=findViewById(R.id.bt_comment_see_in_map);
        mBtCommentactivitySave.setOnClickListener(this);
        mBtCommentactivityReturn.setOnClickListener(this);
        mBtCommentactivitySeeMap.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_comment_save:
                String comment = mEtCommentactivityComment.getText().toString();
                //change database
                String t=mlastPlan.getRandomATA();
                ArrayList<commentFormat> c =mDBOpenHelperComment.getAllData();
                for (int i=0;i<c.size();i++){
                    commentFormat temp=c.get(i);
                    if (temp.getRandomATA().equals(t)){

                    }
                }

                mDBOpenHelperComment.changeComment(randomATA, comment);
                Intent intent2 = new Intent(this, Community.class);
                startActivity(intent2);
                finish();
                Toast.makeText(this, "Comment successfully", Toast.LENGTH_SHORT).show();
                break;


            // to Community activity
            case R.id.bt_comment_return:
                Intent i2 = new Intent(this, Community.class);
                startActivity(i2);
                break;


            // to MapsActivity activity
            case R.id.bt_comment_see_in_map:
                Intent i3 = new Intent(this, MapsActivity.class);
                i3.putExtra("from","comment");
                ArrayList<String> p=new ArrayList<>();

                String[] subp = mlastPlan.getPlaces().split("No. ");
                for (int i =1;i<subp.length;i++){
                    subp[i]=subp[i].substring(2);
                    p.add(subp[i]);
                }

                i3.putStringArrayListExtra("places",p);
                startActivity(i3);
                break;


        }

    }

    // get the last plan for the specific user
    public commentFormat lastPlan(ArrayList<commentFormat> c) throws ParseException {
        if(c.size()==1){
            return c.get(0);
        }else{
            int pos=0;
            commentFormat temp=c.get(0);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date a=sdf.parse(temp.getRandomATA());
            for (int m=1;m<c.size();m++) {
                temp = c.get(m);
                Date b = sdf.parse(temp.getRandomATA());
                if (a.before(b)) {
                    a = b;
                    pos=m;
                }
            }
            return c.get(pos);
            }
        }




    }












