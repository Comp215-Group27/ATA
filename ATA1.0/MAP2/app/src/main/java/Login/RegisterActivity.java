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

import com.example.map2.R;

import java.util.ArrayList;
import java.util.Calendar;

import Community.Common.Community;
import Community.Common.DBOpenHelperComment;
import map.load;

import static Main.Menu2.statusOfLogin;
import static Login.loginActivity.userName;
import static Login.loginActivity.userID;
import static map.MapsActivity.nameOrdered;
import static map.load.thisrandomid;
/**
 * Created by Zhenyan li and Haopeng Song, Group 27, Comp215, University of Liverpool, referred to some codes in github
 */

/** An activity for register*/

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelperUser mDBOpenHelperUser;
    private DBOpenHelperComment mDBOpenHelperComment;
    private Button mBtRegisteractivityRegister;
    private ImageView mIvRegisteractivityBack;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    public ArrayList<String> s;
    String n = "N";
    String id;
    int r = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        s = nameOrdered;
        initView();
        // set user id
        r = setuserRandomID();


        String rs = Integer.toString(r);
        n = getIntent().getStringExtra("request2");
        if (n.equals("load")) {
            id = thisrandomid;
        }
        mDBOpenHelperUser = new DBOpenHelperUser(this);
        mDBOpenHelperComment = new DBOpenHelperComment(this);
        TextView tv = findViewById(R.id.IDNumber);
        tv.setText("ID: " + rs);
    }

    private void initView() {
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mIvRegisteractivityBack.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // back to login activity
            case R.id.iv_registeractivity_back:
                Intent intent1 = new Intent(this, loginActivity.class);
                intent1.putExtra("request1",n );
                startActivity(intent1);
                finish();
                break;

            // register
            case R.id.bt_registeractivity_register:
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password1 = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();

                //check if the password and user name is in correct format
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(password1) && password1.equals(password2)) {
                    userID = r;
                    userName = username;
                    statusOfLogin = 1;
                    mDBOpenHelperUser.add(userID, username, password2);

                    if (n.equals("Community")) {
                        Intent intent = new Intent(this, Community.class);
                        startActivity(intent);
                        finish();
                    } else if (n.equals("load")) {
                        String places = "";
                        for (int i2 = 0; i2 < s.size(); i2++) {
                            if (i2 == 0) {
                                places = " No. 1 " + s.get(0);
                            } else {
                                String c2 = s.get(i2);
                                places = places + "\n" + " No. " + (i2 + 1) + " " + c2;
                            }
                        }

//                        // save in database for users
                        boolean save = false;
                        try {
                            mDBOpenHelperComment.add(id, userID, userName, places, "NULL");
                            save = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (save) {
                            Toast.makeText(RegisterActivity.this, "save successfully", Toast.LENGTH_SHORT).show();
                        }


                        Intent intent = new Intent(this, load.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, "Authentication pass, register successfully", Toast.LENGTH_SHORT).show();
                    }

                    } else {
                        Toast.makeText(this, "Incomplete information, registration failed", Toast.LENGTH_SHORT).show();
                    }
                    break;

        }

    }

    // set random userID
    public int setuserRandomID() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String s = "" + month + day + hour + minute + second;
        int ID = Integer.parseInt(s.trim());
        return ID;
    }
}



