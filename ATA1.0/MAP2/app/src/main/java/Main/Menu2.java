package Main;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import Login.DBOpenHelperUser;
import Login.loginActivity;
import Community.Common.Community;

import static Login.loginActivity.userName;
import static Login.loginActivity.userID;
/**
 * Created by Haopeng Song and Yixian Lu, Group 27, Comp215, Uiversity of Liverpool
 */
/** A class to show menu with "build a plan" and "enter community" functions */

public class Menu2 extends AppCompatActivity {
    public static int statusOfLogin=0;
    private DBOpenHelperUser mDBOpenHelperUser;
    public static DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_options);

        mDBOpenHelperUser = new DBOpenHelperUser(this);
        this.myDB = new DatabaseHelper(this);
        InsertCambridgeDataInSQlite();
        InsertLondonDataInSQlite();
        InsertManchesterDataInSQlite();
        InsertYorkDataInSQlite();

        TextView tv4 = findViewById(R.id.menu_userInfo);

        if(statusOfLogin!=0) {
//            btn1.setEnabled(false);
            tv4.setText("User name: "+userName + "\n"+"User ID: "+ String.valueOf(userID));
        }
        if(statusOfLogin==0) {
//            btn2.setEnabled(false);
            tv4.setText("Not logged in yet");
        }


        Button btn4 = (Button) findViewById(R.id.buildBtn);
        Button btn5 = (Button) findViewById(R.id.communityBtn);

        btn4.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Intent i = new Intent(Menu2.this , BudgetActivity.class);
                                        startActivity(i);
                                    }
                                }
        );
        btn5.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        if(statusOfLogin!=0) {
                                            Intent i = new Intent(Menu2.this , Community.class);
                                            startActivity(i);
                                        }
                                        if(statusOfLogin==0) {
                                            Intent i = new Intent(Menu2.this , loginActivity.class);
                                            i.putExtra("request1", "Community");
                                            startActivity(i);
                                        }
                                    }
                                }
        );
    }


    /** Adding data from web into database */
    protected void InsertCambridgeDataInSQlite(){
        int i =0;
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try {
            // open the file 'Cambridage.csv' storing in Asset folder
            is = assetManager.open("Cambridge.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = null;
            // read the file
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            reader.readLine();
            String line = "";
            // delete the data in the Table Cambridge before insert the data
            this.myDB.deleteCambrige();
            while ((line = reader.readLine()) != null & i<100) {
                i++;
                System.out.println("第" + i + "行：" + line);
                // spilt usint comma
                String buffer[] = line.split(",");
                System.out.println("第" + i + "行：" + buffer[0].toString());
                System.out.println("第" + i + "行：" + buffer[1].toString());
                System.out.println("第" + i + "行：" + buffer[2].toString());
                // insert the first， second，third colum into the table Cambridge
                boolean isInserted = this.myDB.insertCambridgeData( buffer[0],buffer[1],buffer[2]);
                System.out.println(isInserted);
            }
            System.out.println("成功");
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    protected void InsertLondonDataInSQlite(){
        System.out.println("00000");
        int i =0;
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try {
            // open the file 'London.csv' storing in Asset folder
            is = assetManager.open("London.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = null;
            // read the file
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            reader.readLine();
            String line = "";
            // delete the data in the Table London before insert the data
            this.myDB.deleteLondon();
            while ((line = reader.readLine()) != null & i<100) {
                i++;
                System.out.println("第" + i + "行：" + line);
                // spilt usint comma
                String buffer[] = line.split(",");
                System.out.println("第" + i + "行：" + buffer[0].toString());
                System.out.println("第" + i + "行：" + buffer[1].toString());
                System.out.println("第" + i + "行：" + buffer[2].toString());
                // insert the first， second，third colum into the table London
                boolean isInserted = this.myDB.insertLondonData( buffer[0],buffer[1],buffer[2]);
                System.out.println(isInserted);
                String tableName = new String("London");
                System.out.println(this.myDB.getvisitTime(buffer[0],tableName));
            }
            String tableName = new String("London");

            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void InsertManchesterDataInSQlite(){
        System.out.println("00000");
        int i =0;
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try {
            // open the file 'Manchester.csv' storing in Asset folder
            is = assetManager.open("Manchester.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = null;
            // read the file
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            reader.readLine();
            String line = "";
            // delete the data in the Table Manchester before insert the data
            this.myDB.deleteManchester();
            while ((line = reader.readLine()) != null & i<100) {
                i++;
                System.out.println("第" + i + "行：" + line);
                String buffer[] = line.split(",");
                // spilt usint comma
                System.out.println("第" + i + "行：" + buffer[0].toString());
                System.out.println("第" + i + "行：" + buffer[1].toString());
                System.out.println("第" + i + "行：" + buffer[2].toString());
                // insert the first， second，third colum into the table Manchester
                boolean isInserted = this.myDB.insertManchesterData( buffer[0],buffer[1],buffer[2]);
                System.out.println(isInserted);
            }
            System.out.println("成功");
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    protected void InsertYorkDataInSQlite(){
        System.out.println("00000");
        int i =0;
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try {
            // open the file 'York.csv' storing in Asset folder
            is = assetManager.open("York.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = null;
            // read the file
            reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            reader.readLine();
            String line = "";
            // delete the data in the Table York before insert the data
            this.myDB.deleteYork();
            while ((line = reader.readLine()) != null & i<100) {
                i++;
                System.out.println("第" + i + "行：" + line);
                String buffer[] = line.split(",");
                // spilt usint comma
                System.out.println("第" + i + "行：" + buffer[0].toString());
                System.out.println("第" + i + "行：" + buffer[1].toString());
                System.out.println("第" + i + "行：" + buffer[2].toString());
                // insert the first， second，third colum into the table York
                boolean isInserted = this.myDB.insertYorkData( buffer[0],buffer[1],buffer[2]);
                System.out.println(isInserted);
            }
            System.out.println("成功");
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}


