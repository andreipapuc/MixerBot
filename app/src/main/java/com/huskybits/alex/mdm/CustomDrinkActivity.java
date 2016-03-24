package com.huskybits.alex.mdm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class CustomDrinkActivity extends Activity{

    // SeekBars declaration
    private SeekBar seekBarFirstDrink;
    private SeekBar seekBarSecondDrink;
    private SeekBar seekBarThirdDrink;
    private SeekBar seekBarTemp;

    // Text labels declaration
    private TextView textProgressFirstDrink;
    private TextView textProgressSecondDrink;
    private TextView textProgressThirdDrink;
    private TextView textProgressTemp;

    // Button
    Button btnNext;

    // Values of drinks
    public static int firstDrinkValue = 0;
    public static int secondDrinkValue = 0;
    public static int thirdDrinkValue = 0;
    public static int tempValue = 0;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        // Load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drink);

        // Reinitialization
        firstDrinkValue = 0;
        secondDrinkValue = 0;
        thirdDrinkValue = 0;
        tempValue = 0;

        // Seekbars
        seekBarFirstDrink = (SeekBar)findViewById(R.id.seekBarFirstDrink);
        seekBarSecondDrink = (SeekBar)findViewById(R.id.seekBarSecondDrink);
        seekBarThirdDrink = (SeekBar)findViewById(R.id.seekBarThirdDrink);
        seekBarTemp = (SeekBar)findViewById(R.id.seekBarTemp);

        // SeekBar listener
        sliderListener sldListener = new sliderListener();
        seekBarFirstDrink.setOnSeekBarChangeListener(sldListener);
        seekBarSecondDrink.setOnSeekBarChangeListener(sldListener);
        seekBarThirdDrink.setOnSeekBarChangeListener(sldListener);
        seekBarTemp.setOnSeekBarChangeListener(sldListener);

        // Progress text fields
        textProgressFirstDrink = (TextView)findViewById(R.id.textProgressFirstDrink);
        textProgressSecondDrink = (TextView)findViewById(R.id.textProgressSecondDrink);
        textProgressThirdDrink = (TextView)findViewById(R.id.textProgressThirdDrink);
        textProgressTemp = (TextView)findViewById(R.id.textProgressTemp);

        // Summary button click event
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstDrinkValue + secondDrinkValue + thirdDrinkValue != 100) {
                    new AlertDialog.Builder(CustomDrinkActivity.this)
                            .setTitle("Error")
                            .setMessage("Make sure the ingredients' values add up to exactly 100!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    // Launching Summary Activity
                    Intent i = new Intent(getApplicationContext(), SummaryActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    private class sliderListener implements OnSeekBarChangeListener {

        // Variables for smoothness of seekbars
        private int smoothnessFactorTemp = 50;
        private int smoothnessFactorDrinks = 10;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // Auto-generated method

            // Change the progress value
            if (seekBar == seekBarFirstDrink) {
                progress = Math.round(progress / smoothnessFactorDrinks);
                textProgressFirstDrink.setText(String.valueOf(progress * 10) + '%');
                firstDrinkValue = progress * 10;
            } else if (seekBar == seekBarSecondDrink) {
                progress = Math.round(progress / smoothnessFactorDrinks);
                textProgressSecondDrink.setText(String.valueOf(progress * 10) + '%');
                secondDrinkValue = progress * 10;
            } else if (seekBar == seekBarThirdDrink) {
                progress = Math.round(progress / smoothnessFactorDrinks);
                textProgressThirdDrink.setText(String.valueOf(progress * 10) + '%');
                thirdDrinkValue = progress * 10;
            } else if (seekBar == seekBarTemp) {
                progress = Math.round(progress / smoothnessFactorTemp);
                tempValue = progress;
                if (tempValue == 0) {
                    textProgressTemp.setText("15°C");
                }
                else if (tempValue == 1) {
                    textProgressTemp.setText("24°C");
                }
                else if (tempValue == 2) {
                    textProgressTemp.setText("40°C");
                }
                //textProgressTemp.setText(String.valueOf(progress) + '%');
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Auto-generated method
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Auto-generated method

            // Implement the smoothness
            if (seekBar == seekBarTemp) {
                seekBar.setProgress(Math.round((seekBar.getProgress() + (smoothnessFactorTemp / 2)) / smoothnessFactorTemp) * smoothnessFactorTemp);
            }
            else {
                seekBar.setProgress(Math.round((seekBar.getProgress() + (smoothnessFactorDrinks / 2)) / smoothnessFactorDrinks) * smoothnessFactorDrinks);
            }
        }
        }
    }

