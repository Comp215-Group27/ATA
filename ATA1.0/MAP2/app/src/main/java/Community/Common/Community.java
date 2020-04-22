package Community.Common;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Community.Comment.Comment;
import Community.Comment.show;
import Main.Menu2;
import com.example.map2.R;

import java.util.ArrayList;

import Login.DBOpenHelperUser;
import Login.loginActivity;

import static map.MapsActivity.*;
import static Login.loginActivity.userName;
import static Login.loginActivity.userID;
import static Main.Menu2.statusOfLogin;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool,referred to some codes on GitHub
 */

/** A class to show Community's main page and other functions */

public class Community extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelperUser mDBOpenHelperUser;
    private DBOpenHelperComment mDBOpenHelperComment;
    public static ArrayList<String> s = nameOrdered;
    private Button btn1_login;
    private Button btn2_logout;
    private Button btn3_clearUser;
    ImageView btn4_back;
    private Button btn5_new;
    private Button btn6_old;
    ListView PlanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_load);
        initView();

        mDBOpenHelperUser = new DBOpenHelperUser(this);
        mDBOpenHelperComment = new DBOpenHelperComment(this);

        //If the database crashes, log off the following line of code, find the "clear comment" button on the page in the app, click and restart the app.
        btn3_clearUser.setVisibility(View.INVISIBLE);

        LinearLayout ll5 = (LinearLayout) findViewById(R.id.ll5);
        TextView tv4 = findViewById(R.id.Community_userInfo);

        //show all plans that users have shared, i.e. all plans that comments are not default "NULL"
        ArrayList<commentFormat> allData;
        allData=mDBOpenHelperComment.getAllData();
        ArrayList<commentFormat> list=new ArrayList<>();
        for (int i=0;i<allData.size();i++){
            commentFormat temp=allData.get(i);
            if (!temp.getComment().equals("NULL")){
                list.add(temp);
            }
        }
        CommentAdaptor adaptor = new CommentAdaptor(this, list);
        PlanList = (ListView) findViewById(R.id.Allplan);
        PlanList.setAdapter(adaptor);
        PlanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        // set login status and relative functions
        if (statusOfLogin != 0) {
            btn1_login.setEnabled(false);
            tv4.setText("User name: " + userName + "\n" + "User ID: " + String.valueOf(userID));
        }
         else {
            btn2_logout.setEnabled(false);

            tv4.setText("Not logged in yet");
            TextView tv3 = new TextView(this);;
            tv3.setText("   You are currently in customer mode!"+"\n"+
                        "   You can: Read other customer plans and reviews;"+"\n"+
                        "   You can't: Share or edit your own plans and comments "+"\n"+
                        "   For more features please log in!");
            tv3.setTextSize(10);
            ll5.addView(tv3);
        }

    }


    // set buttons or texts
    private void initView() {
        btn1_login= findViewById(R.id.Community_login);
        btn2_logout= findViewById(R.id.Community_logout);
        btn3_clearUser =findViewById(R.id.Community_clearUser);
        btn4_back=findViewById(R.id.Community_back_to_menu);
        btn5_new=findViewById(R.id.bt_shareNewPlan);
        btn6_old=findViewById(R.id.bt_personalPlan);

        btn1_login.setOnClickListener(this);
        btn2_logout.setOnClickListener(this);
        btn3_clearUser.setOnClickListener(this);
        btn4_back.setOnClickListener(this);
        btn5_new.setOnClickListener(this);
        btn6_old.setOnClickListener(this);


    }



    public void onClick(View view) {
        switch (view.getId()) {
            // to login activity
            case R.id.Community_login:
                Intent i1 = new Intent(Community.this, loginActivity.class);
                i1.putExtra("request1", "Community");
                startActivity(i1);
                break;

            // to logout
            case R.id.Community_logout:
                statusOfLogin = 0;
                userName = "NotLoggedIn";
                userID = 0;
                Intent i3 = new Intent(Community.this, Community.class);
                startActivity(i3);
                Toast.makeText(Community.this, "log out successfully", Toast.LENGTH_SHORT).show();
                break;

            // to clear table if database is crashed
            case R.id.Community_clearUser:
                mDBOpenHelperComment.deleteAll();
                mDBOpenHelperComment.deleteTable();
                break;

            // to go back to menu
            case R.id.Community_back_to_menu:
                Intent i4 = new Intent(Community.this, Menu2.class);
                startActivity(i4);
                break;

            // to Comment activity
            case R.id.bt_shareNewPlan:
                if (statusOfLogin!=0){
                Intent intent = new Intent(this, Comment.class);
                startActivity(intent);
                finish();
                }else{
                    statusOfLogin = 0;
                    userName = "NotLoggedIn";
                    userID = 0;
                    Toast.makeText(Community.this, "Please log in", Toast.LENGTH_SHORT).show();
                }
                break;

            // to Show activity
            case R.id.bt_personalPlan:
                if (statusOfLogin!=0){
                Intent i2 = new Intent(this, show.class);
                startActivity(i2);
                finish();
                }else{
                    statusOfLogin = 0;
                    userName = "NotLoggedIn";
                    userID = 0;
                    Toast.makeText(Community.this, "Please log in", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
}
