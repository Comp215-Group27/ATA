package Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.map2.R;

/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is intended for the following purpose
 * To collect user's time, money budgets for further knapsack calculation
 * To collect the number of travelers
 */
public class BudgetActivity extends AppCompatActivity {
    ImageView backButton;
    Button cityButton;
    public static Integer time;
    public static Integer money;
    public static Integer numberPeople;
    ImageView homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        // this button is used to finish the activity
        backButton = findViewById(R.id.backbtn_budget);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BudgetActivity.this,Menu2.class);
                startActivity(i);
                finish();
            }
        });
        // the home button is used to return to the home page
        homeButton = findViewById(R.id.homebtn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(BudgetActivity.this,Menu2.class);
                startActivity(homeIntent);
            }
        });

        // Once the user clicks on the next button
        // the time and money budgets and number of people should be passed on to the next activity
        // start the next activity: Choose the city
        Button nextBtn =  (Button) findViewById(R.id.nextcitybtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText timeBudget = (EditText) findViewById(R.id.timeBudget);
                EditText moneyBudget =  (EditText) findViewById(R.id.moneyBudget);
                EditText numberPeopleT = (EditText) findViewById(R.id.peopleBudget);
                // try to transform the content of editText into integers
                try{
                    time = Integer.parseInt(timeBudget.getText().toString());
                    money = Integer.parseInt(moneyBudget.getText().toString());
                    numberPeople = Integer.parseInt(numberPeopleT.getText().toString());
                    if (time<0 || time >24){
                        Toast.makeText(BudgetActivity.this,"please input a time budget between 0 and 24", Toast.LENGTH_SHORT).show();
                    }else if (money<0 || money >10000){
                        Toast.makeText(BudgetActivity.this,"please input a money budget between 0 and 10000", Toast.LENGTH_SHORT).show();
                    }else if (numberPeople <=0){
                        Toast.makeText(BudgetActivity.this,"number of people cannot be 0 or less", Toast.LENGTH_SHORT).show();
                    } else{
                        Intent list1 = new Intent(getApplicationContext(), ChooseCityActivity.class);
                        startActivity(list1);
                        finish();
                    }

                }catch (Exception e){
                    Toast.makeText(BudgetActivity.this,"please input only whole numbers", Toast.LENGTH_SHORT).show();
                }


            }
        });




    }

}
