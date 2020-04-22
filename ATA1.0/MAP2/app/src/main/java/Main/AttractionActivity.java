package Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

import java.util.ArrayList;
/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is intended for the following activities
 * To show the list of tourist attractions
 * To collect attraction names chosen by the user
 * To collect star rates given by the user
 */
public class AttractionActivity extends AppCompatActivity {

    ImageView backButton;
    Button goAtaButton;
    ListView attractionList;
    public static String [] london_Attractions;
    public static String [] cambridge_Attractions;
    public static String [] manchester_Attractions;
    public static String [] york_Attractions;
    private String [] names;
    private ArrayList<String> attractionNames = new ArrayList<>();
    private ArrayList<Integer> attractionRates = new ArrayList<>();
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        // The back button should finish the activity once clicked on
        backButton = findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //find the list view and string arrays
        attractionList =(ListView) findViewById(R.id.attractionList);
        london_Attractions = toArray(Menu2.myDB.getAttractionNames("London"));
        york_Attractions = toArray(Menu2.myDB.getAttractionNames("York"));
        cambridge_Attractions = toArray(Menu2.myDB.getAttractionNames("Cambridge"));
        manchester_Attractions = toArray(Menu2.myDB.getAttractionNames("Manchester"));

        // this city index is passed from the last activity Choose The City
        // it is used to identify which city the user has chosen
        int city_index = getIntent().getIntExtra("City_Index",0);

        // based on the city index, put corresponding string array onto the listView
        // the adapter here is used to show the content of the string array
        if (city_index == 0){
            names = london_Attractions;
            AttractionAdaptor attractionAdaptor1 = new AttractionAdaptor(this,london_Attractions);
            attractionList.setAdapter(attractionAdaptor1);
            cityName = "London";
        }
        if (city_index == 1){
            names = york_Attractions;
            AttractionAdaptor attractionAdaptor1 = new AttractionAdaptor(this,york_Attractions);
            attractionList.setAdapter(attractionAdaptor1);
            cityName = "York";
        }
        if (city_index == 2){
            names = manchester_Attractions;
            AttractionAdaptor attractionAdaptor1 = new AttractionAdaptor(this,manchester_Attractions);
            attractionList.setAdapter(attractionAdaptor1);
            cityName = "Manchester";
        }
        if (city_index == 3){
            names = cambridge_Attractions;
            AttractionAdaptor attractionAdaptor1 = new AttractionAdaptor(this,cambridge_Attractions);
            attractionList.setAdapter(attractionAdaptor1);
            cityName = "Cambridge";
        }


        // once a user clicks on the attraction in the list view,
        // start the rating activity and pass the name of the attraction to it
        attractionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent rateAttraction = new Intent(getApplicationContext(), RatingActivity.class);
                rateAttraction.putExtra("name", names[position]);
                startActivityForResult(rateAttraction, 111);


            }
        });

        // once a user clicks on the GoATA button
        // pass the attractionNames and rates into the next activity: Generate plan
        Button calBtn =  (Button) findViewById(R.id.goAtaBtn);
        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the user hasn't chosen any tourist attractions then the next activity should not start
                if (attractionNames.isEmpty()){
                    Toast.makeText(AttractionActivity.this,"You haven't choose any tourist attractions",
                            Toast.LENGTH_SHORT).show();

                }else if (attractionNames.size() ==1 ) {
                    Toast.makeText(AttractionActivity.this,"At least two attractions should be chosen",
                            Toast.LENGTH_SHORT).show();

                }else{
                    Intent calculation = new Intent(getApplicationContext(), GeneratePlanActivity.class);
                    calculation.putStringArrayListExtra("attractionNames", attractionNames);
                    calculation.putIntegerArrayListExtra("attractionRates", attractionRates);
                    calculation.putExtra("cityName", cityName);
                    startActivity(calculation);
                    finish();
                }
            }

        });

    }

    // this method is used to get the returned values from its next activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 111:
                if (resultCode == Activity.RESULT_OK){
                    // get the returned name and rate from the rating activity
                    String name = data.getStringExtra("name");
                    int rate = data.getIntExtra("rate",0);
                    // if the name is already added before then update only the rate
                    // otherwise add both name and rate
                    if (!attractionNames.contains(name)){
                        attractionNames.add(name);
                        attractionRates.add(data.getIntExtra("rate",0));
                    }else{
                        Toast.makeText(AttractionActivity.this,"This attraction is already in the list, rate updated",
                                Toast.LENGTH_SHORT).show();

                    }

                }
        }

    }

    // this method is used to transform an ArrayList into an array
    private String [] toArray(ArrayList<String> names){
        String [] nameArray = new String [30];
        for (int i =0; i< names.size();i++){
            nameArray [i] = names.get(i);
        }
        return nameArray;
    }


}
