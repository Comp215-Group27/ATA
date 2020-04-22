package map;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import Community.Common.DBOpenHelperComment;
import Community.Common.commentFormat;
import Main.Menu2;
import com.example.map2.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Login.DBOpenHelperUser;
import Login.loginActivity;

import static map.MapsActivity.*;
import static Login.loginActivity.userName;
import static Login.loginActivity.userID;
import static Main.Menu2.statusOfLogin;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool
 */

/** A class to show plan details in google map */

public class load  extends AppCompatActivity {

    private DBOpenHelperUser mDBOpenHelperUser;
    private DBOpenHelperComment mDBOpenHelperComment;
    public static ArrayList<String> s;
    public static String thisrandomid;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        mDBOpenHelperUser = new DBOpenHelperUser(this);
        mDBOpenHelperComment= new DBOpenHelperComment(this);
        s= nameOrdered;
        // set plan id
        thisrandomid=setRandomID();
        // show time
        LinearLayout ll4 = (LinearLayout) findViewById(R.id.ll4);
        TextView tv = new TextView(this);
        tv.setText("The time you generate this plan is: "+thisrandomid+"\n");
        tv.setTextSize(18);
        ll4.addView(tv);
        // show places
        LinearLayout ll3 = (LinearLayout) findViewById(R.id.ll3);
        for (int i=0;i<s.size();i++){
            String c = s.get(i);
            TextView tv2 = new TextView(this);
            tv2.setText("  No."+(i+1)+" "+c);
            tv2.setTextSize(18);
            ll3.addView(tv2);
        }
        //buttons
        ImageView backButton;
        TextView tv4 = findViewById(R.id.userInfo);
        Button login = findViewById(R.id.login);
        Button logout = findViewById(R.id.logout);
        Button btn3 = findViewById(R.id.clearUser);
        ImageView homeButton = findViewById(R.id.back_to_menu);
        final Button savebt = findViewById(R.id.save_this_plan);

        /*If the database crashes, comment this sentence out, then find the "clear table" button in the program, click and restart the system. */
        btn3.setVisibility(View.INVISIBLE);

        // tell the login status
        if(statusOfLogin!=0) {
            login.setVisibility(View.INVISIBLE);
            tv4.setText("User name: "+userName + "\n"+"User ID: "+ String.valueOf(userID));
        }
        if(statusOfLogin==0) {
            logout.setVisibility(View.INVISIBLE);
            tv4.setText("Not logged in yet");
        }
        login.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent i1 = new Intent(load.this, loginActivity.class);
                                        i1.putExtra("request1", "load");
                                        startActivity(i1);
                                    }
        });
        logout.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        statusOfLogin=0;
                                        userName="NotLoggedIn";
                                        userID=0;
                                        Intent i3 = new Intent(load.this, load.class);
                                        startActivity(i3);
                                        Toast.makeText(load.this, "log out successfully", Toast.LENGTH_SHORT).show();
                                    }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(load.this,Menu2.class);
                startActivity(homeIntent);
            }
        });

        backButton = findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        savebt.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v) {
                                        // Check whether you can get time from the method to determine the network condition and prevent the database from being stored empty
                                        if (thisrandomid.equals("null")) {
                                            Toast.makeText(load.this, "Bad network, please back to map and enter again!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        // try to save the comment
                                        else {
                                            if (statusOfLogin != 0) {
                                                String places = "";
                                                ArrayList<commentFormat> a = mDBOpenHelperComment.getAllData();
                                                for (int i2 = 0; i2 < s.size(); i2++) {
                                                    if (i2 == 0) {
                                                        places = " No. 1 " + s.get(0);
                                                    } else {
                                                        String c2 = s.get(i2);
                                                        places = places + "\n" + " No. " + (i2 + 1) + " " + c2;
                                                    }
                                                }
                                                // save the plan
                                                boolean save = false;
                                                try {
                                                    mDBOpenHelperComment.add(thisrandomid, userID, userName, places, "NULL");
                                                    save = true;
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                if (save) {
                                                    Toast.makeText(load.this, "save successfully", Toast.LENGTH_SHORT).show();
                                                }
                                                savebt.setEnabled(false);

                                            } else if (statusOfLogin == 0) {
                                                Intent i5 = new Intent(load.this, loginActivity.class);
                                                i5.putExtra("request1", "load");
                                                i5.putExtra("id", thisrandomid);
                                                startActivity(i5);
                                            }
                                        }
                                    }
        });

        }

   //get current time used for primary key
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String setRandomID() {
        String timeStr1="null";
        timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return timeStr1;
    }
}
