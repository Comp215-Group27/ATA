package Community.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.map2.R;
import java.util.ArrayList;

import Community.Common.Community;
import Community.Common.DBOpenHelperComment;
import Community.Common.commentFormat;
import map.MapsActivity;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool */

/** A class used to allow users to input or edit places or comments */

public class Edit_main extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelperComment mDBOpenHelperComment;
    private Button mBtCommentactivitySave;
    private Button mBtCommentactivitySeeMap;
    private EditText mEtCommentactivityComment;
    private EditText mEtCommentactivityPlaces;
    ImageView backButton;
    public String randomATA;
    public ArrayList<String> s;
    commentFormat mPlan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_main);
        initView();
        mDBOpenHelperComment = new DBOpenHelperComment(this);
        TextView tv = findViewById(R.id.randomID2);
        randomATA= getIntent().getStringExtra("randomATA");
        tv.setText(randomATA);

        ArrayList<commentFormat> allData=mDBOpenHelperComment.getAllData();
        EditText tv1 = findViewById(R.id.et_comment_places2);
        EditText tv2 = findViewById(R.id.et_comment_comment2);

        if (allData.size()>0) {
            for (int i = 0; i < allData.size(); i++) {
                commentFormat plan = allData.get(i);
                if (randomATA.equals(plan.getRandomATA())) {
                    mPlan=plan;
                }
            }
            tv1.setText(mPlan.getPlaces());
            tv2.setText(mPlan.getComment());

        }else{
            TextView tv_places = findViewById(R.id.et_comment_places2);
            tv_places.setText("No plans saved");
            mBtCommentactivitySave.setEnabled(false);
        }



        backButton = findViewById(R.id.backbtn_edit);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Edit_main.this,show.class);
                startActivity(i);
                finish();
            }
        });



    }



    private void initView() {

        mEtCommentactivityComment = findViewById(R.id.et_comment_comment2);
        mEtCommentactivityPlaces= findViewById(R.id.et_comment_places2);
        mBtCommentactivitySave = findViewById(R.id.bt_comment_save2);

        mBtCommentactivitySeeMap=findViewById(R.id.bt_map2);
        mBtCommentactivitySave.setOnClickListener(this);
        mBtCommentactivitySeeMap.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // to save a plan
            case R.id.bt_comment_save2:
                String comment = mEtCommentactivityComment.getText().toString();
                String places=mEtCommentactivityPlaces.getText().toString();
                // change the database
                ArrayList<commentFormat> c =mDBOpenHelperComment.getAllData();
                for (int i=0;i<c.size();i++){
                    commentFormat temp=c.get(i);
                    if (temp.getRandomATA().equals(randomATA)){
                     mDBOpenHelperComment.changeComment(randomATA,comment);
                     mDBOpenHelperComment.changePlaces(randomATA,places);
                    }
                }
                Intent intent2 = new Intent(this, Community.class);
                startActivity(intent2);
                finish();
                Toast.makeText(this, "Post successfully", Toast.LENGTH_SHORT).show();
                break;

            // to see the plan in map
            case R.id.bt_map2:
                Intent i3 = new Intent(this, MapsActivity.class);
                i3.putExtra("from","edit");
                i3.putExtra("ATA",randomATA);
                String place=mEtCommentactivityPlaces.getText().toString();

                ArrayList<String> p=new ArrayList<>();

                String[] subp = place.split("No. ");
                for (int i =1;i<subp.length;i++){
                    subp[i]=subp[i].substring(2);
                    p.add(subp[i]);
                }
                i3.putStringArrayListExtra("places",p);
                startActivity(i3);
                break;



        }

    }



}












