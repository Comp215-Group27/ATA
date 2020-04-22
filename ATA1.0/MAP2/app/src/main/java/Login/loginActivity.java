package Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Main.Menu2;
import com.example.map2.R;

import java.util.ArrayList;

import Community.Common.Community;
import Community.Common.DBOpenHelperComment;
import Community.Common.commentFormat;
import map.load;

import static map.MapsActivity.nameOrdered;
import static Main.Menu2.statusOfLogin;
import static map.load.thisrandomid;

/**
 * Created by Zhenyan li and Haopeng Song, Group 27, Comp215, University of Liverpool, referred to some codes in github
 */

/** An activity for logging in*/
public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelperUser mDBOpenHelperUser;
    private DBOpenHelperComment mDBOpenHelperComment;

    private TextView mTvLoginactivityRegister;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    TextView mBtLoginactivityCustomer;
    ImageView homeButton;
    private Button mBtLoginactivityLogin;
   ImageView mBtLoginactivityback;
    TextView v;
    public ArrayList<String> s;
    public String c="";
    String randomid;
    public static String userName="NotLoggedIn";
    public static int userID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        s= nameOrdered;
        initView();
        c = getIntent().getStringExtra("request1");
        randomid=thisrandomid;
        mDBOpenHelperUser = new DBOpenHelperUser(this);
        mDBOpenHelperComment= new DBOpenHelperComment(this);

        if (c.equals("load")) {
           mBtLoginactivityCustomer.setVisibility(View.INVISIBLE);
           v.setVisibility(View.INVISIBLE);
        }


        homeButton = findViewById(R.id.homebtn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(loginActivity.this, Menu2.class);
                startActivity(homeIntent);
            }
        });



        mBtLoginactivityback = findViewById(R.id.iv_login_back);
        mBtLoginactivityback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.equals("load")) {
                    Intent intent = new Intent(loginActivity.this, load.class);
                    startActivity(intent);
                }
                else if (c.equals("Community")) {
                    Intent intent = new Intent(loginActivity.this, Community.class);
                    startActivity(intent);
                }
            }
        });

    }


    private void initView() {
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mBtLoginactivityCustomer = findViewById(R.id.as_customer);
        v = findViewById(R.id.haveNoAccountTxt);
        mBtLoginactivityback=findViewById(R.id.iv_login_back);
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
        mBtLoginactivityCustomer.setOnClickListener(this);
        mBtLoginactivityback.setOnClickListener(this);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            // to register activity
            case R.id.tv_loginactivity_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("request2",c);
                startActivity(intent);
                finish();
                break;

                // check if the password is correct using user name
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelperUser.getAllData();
                    boolean match = false;
                    int pos=0;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            pos=i;
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        User user = data.get(pos);
                        userID=user.getIDNumber();
                        userName=name;
                        statusOfLogin=1;
                        Toast.makeText(this, "log in successful", Toast.LENGTH_SHORT).show();
                        if (c.equals("Community")) {
                            Intent intent1 = new Intent(this, Community.class);
                            startActivity(intent1);
                            finish();
                        }
                        else if (c.equals("load")) {
                            String places = "";
                            for (int i = 0; i < mDBOpenHelperComment.getAllData().size(); i++) {
                                commentFormat c = mDBOpenHelperComment.getAllData().get(i);
                            }

                            for (int i2 = 0; i2 < s.size(); i2++) {
                                if (i2 == 0) {
                                    places = " No. 1 " + s.get(0);
                                } else {
                                    String c2 = s.get(i2);
                                    places = places + "\n" + " No. " + (i2 + 1) + " " + c2;
                                }
                            }

                            boolean save = false;
                            try {
                                mDBOpenHelperComment.add(randomid,userID, userName, places, "NULL");
                                save = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (save) {
                                Toast.makeText(loginActivity.this, "save successfully", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent2 = new Intent(this,load.class);
                            intent2.putExtra("request2",c);
                            startActivity(intent2);
                            finish();
                        }

                    } else {
                        Toast.makeText(this, "username or password fails，please enter again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "please enter your username and password", Toast.LENGTH_SHORT).show();
                }
                break;

                // to log in as a customer
            case R.id.as_customer:
                if (c.equals("Community")) {
                    Intent intent1 = new Intent(this, Community.class);
                    Toast.makeText(this, "You've entered the community as a customer successfully", Toast.LENGTH_SHORT).show();
                    statusOfLogin=0;
                    startActivity(intent1);
                    finish();
                }
                break;

                // to last activity
            case R.id.iv_login_back:
                if (c.equals("Community")) {
                    Intent intent1 = new Intent(this, Community.class);
                    statusOfLogin=0;
                    startActivity(intent1);
                    finish();
                }

                else if (c.equals("load")){
                    Intent intent1 = new Intent(this,load.class);
                    statusOfLogin=0;
                    startActivity(intent1);
                    finish();//销毁此Activity
                }


        }
    }
}



