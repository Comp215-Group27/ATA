package Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

import java.util.ArrayList;

import map.MapsActivity;
/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is created to show the final result returned by knapsack
 *
 */

public class GeneratePlanActivity extends AppCompatActivity {
    ImageView homeButton;
    ImageView backButton;
    Button startOverButton;
    Button generateRouteButton;
    private static knapsack knapTime;
    private static knapsackMoney knapMoney;
    public static ArrayList<Item> input = new ArrayList<Item>();
    private ArrayList<String> resultNameList = new ArrayList<String>();
    private int totalMoney = 0;
    private int totalTime = 0;
    int which = 0;
    private int toRemove;
    // these public static variables will be modified in the Change activity
    public static ArrayAdapter arrayAdapter;
    public static TextView start;
    public static TextView destination;
    public static TextView totalMoneyV;
    public static TextView totalTimeV;
    ArrayList<String> toShowList;
    ArrayList<String> names;
    ArrayList<Integer> values;
    ListView TMList;
    static String city_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_plan);
        city_Name = getIntent().getStringExtra("cityName");
        backButton = findViewById(R.id.backbtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        homeButton = findViewById(R.id.homebtn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(GeneratePlanActivity.this,Menu2.class);
                startActivity(homeIntent);
            }
        });


        // get the names from the last activity: attraction activity
        names = getIntent().getStringArrayListExtra("attractionNames");
        values = getIntent().getIntegerArrayListExtra("attractionRates");
        // create tourist attractions object
        input=createItems(names,values);

        // use a set of two knapsacks
        // the first one takes time as parameters
        // the second one takes money as parameters
        knapTime = new knapsack(input, BudgetActivity.time);
        ArrayList<Item> firstResult = knapTime.getKnapsack();

        knapMoney = new knapsackMoney(firstResult, BudgetActivity.money/BudgetActivity.numberPeople);
        final ArrayList<Item> finalResult = knapMoney.getKnapsack();


        for (int i = 0; i < finalResult.size(); i++){
            String name = finalResult.get(i).getName();
            resultNameList.add(name);
            totalMoney+=finalResult.get(i).getMoneyWeight();
            totalTime+= finalResult.get(i).getTimeWeight();
        }

        // use this list View to show the final result
        ListView resultList = findViewById(R.id.plan_list);
        if (finalResult.size()>0){

            toShowList = getResultList(finalResult);
            arrayAdapter = new ArrayAdapter<String>( this, R.layout.showcase_detail, toShowList);
            resultList.setAdapter(arrayAdapter);
            Utility.setListViewHeightBasedOnChildren(resultList);
            System.out.println("check"+ input.get(0).getName());


            totalMoneyV = (TextView)findViewById(R.id.moneycostTxt);
            totalTimeV = (TextView) findViewById(R.id.timecostTxt);
            start = (TextView) findViewById(R.id.startPointTxt);
            destination = (TextView) findViewById(R.id.endPointTxt);
            String totalM = Integer.toString(totalMoney);
            String totalT = Integer.toString(totalTime);
            totalMoneyV.setText(totalM + "/person");
            totalTimeV.setText(totalT);
            start.setText(finalResult.get(0).getName());
            destination.setText(finalResult.get(finalResult.size()-1).getName());

        }else if (firstResult.isEmpty()){
            Toast.makeText(GeneratePlanActivity.this,"The time budget is too tight for the selected attractions",
                    Toast.LENGTH_SHORT).show();
        }else if (finalResult.isEmpty()){
            Toast.makeText(GeneratePlanActivity.this,"The money budget is too tight for the selected attractions",
                    Toast.LENGTH_SHORT).show();
        }


        Button changeStartBtn =  (Button) findViewById(R.id.changeBtn);
        Button changeFinishBtn =  (Button) findViewById(R.id.changeBtn2);
        Button removeBtn = (Button) findViewById(R.id.removeBtn);
        Button clearBtn = (Button) findViewById(R.id.startagianBtn);

        Change.startPos=0;
        Change.destPos=resultNameList.size()-1;
        // change the start tourist attraction
        changeStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(), Change.class);
                which =1;
                change.putStringArrayListExtra("names", resultNameList);
                change.putExtra("which", which);
                startActivityForResult(change, 112);

            }
        });
        // change the end tourist attraction
        changeFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(), Change.class);
                which =2;
                change.putStringArrayListExtra("names", resultNameList);
                change.putExtra("which", which);
                startActivityForResult(change, 112);


            }
        });
        // remove the tourist attraction
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(), Change.class);
                which =3;
                change.putStringArrayListExtra("names", resultNameList);
                //change.putIntegerArrayListExtra("values", values);
                change.putExtra("which", which);
                startActivityForResult(change, 112);

            }
        });


        // this button is used for start over
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayAdapter.clear();
                resultNameList.clear();
                Intent start = new Intent(getApplicationContext(), BudgetActivity.class);
                startActivity(start);
                finish();
            }
        });


        // this button will take users to the maps acitivity
        Button mapBtn =  (Button) findViewById(R.id.generateBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                map.putExtra("from","generate");

                if (!resultNameList.isEmpty()){
                    if (!destination.getText().toString().equals(start.getText().toString())){
                        map.putStringArrayListExtra("places", resultNameList);
                        startActivity(map);
                    }else{
                        Toast.makeText(GeneratePlanActivity.this,"start and end should not be the same attractions", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(GeneratePlanActivity.this,"No attractions have been chosen", Toast.LENGTH_SHORT).show();
                }

            }

        });



    }

    // this method is used to get result from change activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 112:
                if (resultCode == Activity.RESULT_OK){
                    resultNameList=data.getStringArrayListExtra("attractionNames");
                    values = data.getIntegerArrayListExtra("values");
                    toRemove = data.getIntExtra("removeIndex",0);

                }
        }

    }
    // this method is used to create objects: tourist attractions
    public static ArrayList<Item> createItems(ArrayList<String> names, ArrayList<Integer> values){
        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < names.size(); i++){
            String name = names.get(i);
            int value = values.get(i);
            Item item = new Item(name,value,Menu2.myDB.getvisitTime(name,city_Name),Menu2.myDB.getticketPrice(name,city_Name));
            items.add(item);
        }

        return items;
    }

    private int getTotalMoney(ArrayList<Item> items){
        for (int i = 0; i < items.size(); i++){

            totalMoney+=items.get(i).getMoneyWeight();

        }
        return totalMoney;
    }
    private int getTotalTime(ArrayList<Item> items){
        for (int i = 0; i < items.size(); i++){

            totalTime+= items.get(i).getTimeWeight();
        }
        return totalTime;
    }

    private ArrayList<Integer> getMoneyList(ArrayList<Item> items){
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (int i = 0; i<items.size();i++){
            returnArray.add(items.get(i).getMoneyWeight());
        }
        return returnArray;

    }

    private ArrayList<Integer> getTimeList(ArrayList<Item> items){
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (int i = 0; i<items.size();i++){
            returnArray.add(items.get(i).getTimeWeight());
        }
        return returnArray;

    }

    private ArrayList<String> getResultList(ArrayList<Item> items){
        ArrayList <String> toShow = new ArrayList<>();
        for (int i=0; i< items.size(); i++){
            String record = new String(items.get(i).getName()
                    +"    time: " + items.get(i).getTimeWeight()
                    +"hour(s)    money: "+items.get(i).getMoneyWeight() +"pounds");

            toShow.add(record);
        }
        return toShow;
    }


}
