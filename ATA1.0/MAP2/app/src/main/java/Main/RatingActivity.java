package Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is created to collect star rates from users
 */
public class RatingActivity extends AppCompatActivity {

    ImageView backButton;
    int value;
    RatingBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        backButton = findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // once the users click on the button "Add to the list"
        Button submitBtn =  (Button) findViewById(R.id.addBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar = (RatingBar) findViewById(R.id.ratingBar);
                // get the value from the star rate bars
                // round the value into an integer
                value = Math.round(bar.getRating());
                if (value<0 || value >5){
                    Toast.makeText(RatingActivity.this,"please input a number between 0 and 10", Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent = getIntent();
                    String name = intent.getStringExtra("name");
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("rate",value);
                    returnIntent.putExtra("name", name);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();


                }

            }
        });

    }


}

