package com.huskybits.alex.mdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends Activity{

    // Buttons
    Button btnStandardDrinks;
    Button btnCustomDrink;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Buttons
        btnStandardDrinks= (Button) findViewById(R.id.btnStandardDrinks);
        btnCustomDrink = (Button) findViewById(R.id.btnCustomDrink);

        // Listener for btnStandardDrinks
        btnStandardDrinks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching
                Intent i = new Intent(getApplicationContext(), StandardDrinksActivity.class);
                startActivity(i);

            }
        });

        // Listener for btnCustomDrink
        btnCustomDrink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching
                Intent i = new Intent(getApplicationContext(), CustomDrinkActivity.class);
                startActivity(i);

            }
        });
    }

}
