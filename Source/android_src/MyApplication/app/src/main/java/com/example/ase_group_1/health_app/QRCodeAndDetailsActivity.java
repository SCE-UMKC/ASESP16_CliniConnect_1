package com.example.ase_group_1.health_app;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ase_group_1.health_app.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class QRCodeAndDetailsActivity extends AppCompatActivity {

    JSONObject jsonObj;
    JSONObject jsonObj1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_and_details);

        Intent intent = getIntent();
        String jsonFromRest = intent.getStringExtra("jsonFromRest"); //if it's a string you stored.
        String informationOnQRCodeString="Empty";
        try {
            jsonObj = new JSONObject(jsonFromRest);
            jsonObj1 = jsonObj.getJSONObject("Date1");

            informationOnQRCodeString="\n\nDate : "+jsonObj1.getString("Date")+"\n";
            informationOnQRCodeString = informationOnQRCodeString+"Patient ID : "+jsonObj1.getString("Patient_ID")+"\n";
            informationOnQRCodeString = informationOnQRCodeString+"Patient Name : "+jsonObj1.getString("Patient_Name")+"\n";
            informationOnQRCodeString = informationOnQRCodeString+"Physician Name : "+jsonObj1.getString("Physician_Name")+"\n";
            informationOnQRCodeString = informationOnQRCodeString+"Clinic Name : "+jsonObj1.getString("Physician_Clinic_name")+"\n\n";

            JSONArray jsonarr = jsonObj1.getJSONArray("Tests");
            for(int i = 0; i < jsonarr.length(); i++){
                JSONObject jsonobj = jsonarr.getJSONObject(i);
                String name =jsonobj.getString("Name");
                String specialInstruction=jsonobj.getString("Special_Instructions");
                informationOnQRCodeString = informationOnQRCodeString+"Test Name : "+name+"\n";
                informationOnQRCodeString = informationOnQRCodeString+"Test Special Instructions : "+specialInstruction+"\n\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView tv1;
        tv1= (ImageView) findViewById(R.id.qrCodeImageView);
        Bitmap bitmap1 = encodeToQrCode(informationOnQRCodeString,275, 275);
        tv1.setImageBitmap(bitmap1);
        TextView informationOnQRCodeTextView = (TextView)findViewById(R.id.qrInformation);
        informationOnQRCodeTextView.setText(informationOnQRCodeString);
    }
    public static Bitmap encodeToQrCode(String text, int width, int height){
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(text, BarcodeFormat.QR_CODE, 275, 275);
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                bmp.setPixel(x, y, matrix.get(x,y) ? Color.GREEN : Color.BLUE);
            }
        }
        return bmp;
    }
}
