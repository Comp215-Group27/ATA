package Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

/**
 * /**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is created to show the cities and let users choose the one they want to visit
 */
public class ChooseCityActivity extends AppCompatActivity {

    Button londonButton;
    Button manchesterButton;
    Button cambridgeButton;
    Button yorkButton;
    ImageView ib;
    ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        // backButton = findViewById(R.id.backbtn);
        ib=  findViewById(R.id.iv_choosecity_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseCityActivity.this,BudgetActivity.class);
                startActivity(i);
                finish();
            }
        });


        homeButton = findViewById(R.id.homebtn);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ChooseCityActivity.this,Menu2.class);
                startActivity(homeIntent);
            }
        });

        // once users click on the button
        // pass the corresponding city index to the next activity: AttractionActivity
        // and start the next activity

        londonButton = findViewById(R.id.londonBtn);

        londonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLondon = new Intent(ChooseCityActivity.this,AttractionActivity.class);
                intentLondon.putExtra("City_Index", 0);
                startActivity(intentLondon);
            }
        });

        manchesterButton = findViewById(R.id.manchesterBtn);

        manchesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMan = new Intent(ChooseCityActivity.this,AttractionActivity.class);
                intentMan.putExtra("City_Index", 2);
                startActivity(intentMan);
            }
        });

        cambridgeButton = findViewById(R.id.cambridgeBtn);

        cambridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdinburgh = new Intent(ChooseCityActivity.this,AttractionActivity.class);
                intentEdinburgh.putExtra("City_Index", 3);
                startActivity(intentEdinburgh);
            }
        });

        yorkButton = findViewById(R.id.yorkBtn);

        yorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLiv = new Intent(ChooseCityActivity.this,AttractionActivity.class);
                intentLiv.putExtra("City_Index", 1);
                startActivity(intentLiv);
            }
        });






    }


}
