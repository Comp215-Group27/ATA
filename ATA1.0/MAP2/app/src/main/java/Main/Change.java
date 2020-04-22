package Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

import java.util.ArrayList;
/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is used to modify the plan shown to users
 * it supports removing tourist attractions from list
 * and change the start and end tourist attraction
 */
public class Change extends AppCompatActivity {
    ArrayList<String> attratcionList;
    ArrayList<Integer> rates;
    ListView changeList;
    public static String changed;
    public static int startPos;
    public static int destPos;
    private int remove;
    int totalM;
    int totalT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        // get the tourist attractions chosen by users
        attratcionList = new ArrayList<>();
        attratcionList = getIntent().getStringArrayListExtra("names");
        startPos = 0;
        destPos = attratcionList.size()-1;
        // show the list of tourist attractions on the list view
        ShowcaseAdaptor adaptor = new ShowcaseAdaptor(this, attratcionList);
        changeList = (ListView) findViewById(R.id.changeLV);
        changeList.setAdapter(adaptor);
        // if the user click on the tourist attractions
        // first tell what the user wants to do
        // remove or change start and end
        changeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changed = attratcionList.get(position);
                if (getIntent().getIntExtra("which",0) == 1){
                    GeneratePlanActivity.start.setText(changed);
                    startPos = position;
                    System.out.println("startPos"+startPos);
                    String temp = attratcionList.get(0);
                    String  newStart = attratcionList.get(startPos);
                    attratcionList.set(0,newStart);
                    attratcionList.set(startPos,temp);

                }
                if (getIntent().getIntExtra("which",0) == 2){
                    GeneratePlanActivity.destination.setText(changed);
                    destPos = position;
                    System.out.println("destPos"+destPos);
                    String temp = attratcionList.get(attratcionList.size()-1);
                    String  newEnd = attratcionList.get(destPos);
                    attratcionList.set(attratcionList.size()-1, newEnd);
                    attratcionList.set(destPos,temp);
                }
                if (getIntent().getIntExtra("which",0) == 3) {
                    if (!attratcionList.isEmpty()){

                        // the user cannot remove if only two tourist attractions exist
                        if (attratcionList.size()>2){
                            attratcionList.remove(position);
                            GeneratePlanActivity.totalMoneyV.setText(Integer.toString(getTotalMoney(attratcionList, GeneratePlanActivity.input))+"/person");
                            GeneratePlanActivity.totalTimeV.setText(Integer.toString(getTotalTime(attratcionList, GeneratePlanActivity.input)));
                            //System.out.println("TotalM"+totalM);
                            GeneratePlanActivity.start.setText(attratcionList.get(0));
                            GeneratePlanActivity.destination.setText(attratcionList.get(attratcionList.size()-1));
                            remove = position;

                        }else{
                            Toast.makeText(Change.this,"At least two attractions are required", Toast.LENGTH_SHORT).show();
                        }

                        GeneratePlanActivity.arrayAdapter.clear();
                        GeneratePlanActivity.arrayAdapter.addAll(getShowList(attratcionList, GeneratePlanActivity.input));
                        GeneratePlanActivity.arrayAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(Change.this,"Cannot remove anymore", Toast.LENGTH_SHORT).show();
                    }

                }

                // send the modified result back to the activity: Generate plan
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("attractionNames", attratcionList);
                returnIntent.putIntegerArrayListExtra("values", rates);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


    }
    // get a new total after removing some tourist attractions
    private int getTotalMoney(ArrayList<String> names, ArrayList<Item> items){

        for (int i=0; i < names.size();i++){
            for (int m=0; m< items.size(); m++){
                if (names.get(i).matches(items.get(m).getName()) ){
                    totalM += items.get(m).getMoneyWeight();
                }
            }
        }
        return totalM;
    }
    // get a new total after removing some tourist attractions
    private int getTotalTime(ArrayList<String> names, ArrayList<Item> items){

        for (int i=0; i < names.size();i++){
            for (int m=0; m< items.size(); m++){
                if (names.get(i).matches(items.get(m).getName())){
                    totalT += items.get(m).getTimeWeight();
                }
            }
        }
        return totalT;


    }

    // This method is used to modify what is shown on the list view after the user removing some tourist attractions
    private ArrayList<String> getShowList(ArrayList<String> names, ArrayList<Item> items){
        ArrayList <String> toShow = new ArrayList<>();
        for (int i=0; i < names.size();i++){
            for (int m=0; m< items.size(); m++){
                if (names.get(i).matches(items.get(m).getName())){
                    String record = new String(items.get(m).getName() +"\n"
                            +"  time: " + items.get(m).getTimeWeight()
                            +"hour(s)    money: "+items.get(m).getMoneyWeight() +"pounds");

                    toShow.add(record);
                }
            }
        }
        return toShow;

    }
}
