package com.huskybits.alex.mdm.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.huskybits.alex.mdm.CustomDrinkActivity;
import com.huskybits.alex.mdm.R;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import org.jasypt.util.text.BasicTextEncryptor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class GenerateQRCodeActivity extends Activity{

    // Encrypted string
    public static String encrypted_drink_info;

    // Password for string encryption/decryption
    public String encryptionPassword = "L8_N0it_3ng1ne3r$_R_Za_B3$t";

    // Contains all order info
    public String drink_info;

    // Button to restart the app once the order is complete
    Button btnRestart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the action_bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_generate_qr);

        // Generate a random uuid
        UUID order_UUID = UUID.randomUUID();

        // Get the current time-date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
        String currentTime = sdf.format(cal.getTime());

        // Get the temperature
        String tempValue;
        if (CustomDrinkActivity.tempValue == 0) {
            tempValue = "15";
        }
        else if (CustomDrinkActivity.tempValue == 1) {
            tempValue = "24";
        }
        else {
            tempValue = "40";
        }

        // Build the variable that holds the order info
        drink_info = "{\"UUID\":\"" + String.valueOf(order_UUID) + "\","
                + "\"DATE\":\"" + currentTime + "\","
                + "\"D1\":\"" + String.valueOf(CustomDrinkActivity.firstDrinkValue/10) + "\","
                + "\"D2\":\"" + String.valueOf(CustomDrinkActivity.secondDrinkValue/10) + "\","
                + "\"D3\":\"" + String.valueOf(CustomDrinkActivity.thirdDrinkValue/10) + "\","
                + "\"TEMP\":\"" + String.valueOf(tempValue) + "\"}";

        // Start encryption -> encoding
        encrypt();

        // Restart the app
        btnRestart = (Button)findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Relaunching the app
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    public void generate_QR() {

        String LOG_TAG = "GenerateQRCode";
        String qrInputText = encrypted_drink_info;
        //String qrInputText = drink_info;
        Log.v(LOG_TAG, qrInputText);

        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width > height ? width : height;
        //smallerDimension = smallerDimension * 7/4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void encrypt () {
        BasicTextEncryptor infoEncryptor = new BasicTextEncryptor();
        infoEncryptor.setPassword(encryptionPassword);
        encrypted_drink_info = infoEncryptor.encrypt(drink_info);
        generate_QR();
    }
}

