package com.huskybits.alex.mdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huskybits.alex.mdm.QRCode.GenerateQRCodeActivity;

public class SummaryActivity extends Activity{

    // Text progress
    private TextView firstDrinkValue;
    private TextView secondDrinkValue;
    private TextView thirdDrinkValue;
    private TextView tempValue;

    // Buttons
    Button btnToPayment;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Update the summary
        firstDrinkValue = (TextView)findViewById(R.id.firstDrinkValue);
        firstDrinkValue.setText(String.valueOf(CustomDrinkActivity.firstDrinkValue) + '%');

        secondDrinkValue = (TextView)findViewById(R.id.secondDrinkValue);
        secondDrinkValue.setText(String.valueOf(CustomDrinkActivity.secondDrinkValue) + '%');

        thirdDrinkValue = (TextView)findViewById(R.id.thirdDrinkValue);
        thirdDrinkValue.setText(String.valueOf(CustomDrinkActivity.thirdDrinkValue) + '%');

        tempValue = (TextView)findViewById(R.id.tempValue);

        if (CustomDrinkActivity.tempValue == 0) {
            tempValue.setText("15°C");
        }
        else if (CustomDrinkActivity.tempValue == 1) {
            tempValue.setText("24°C");
        }
        else {
            tempValue.setText("40°C");
        }

        // Proceeed to payment
        btnToPayment = (Button)findViewById(R.id.btnToPayment);
        btnToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching PaymentActivity
                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(i);
            }
        });

    }

}

