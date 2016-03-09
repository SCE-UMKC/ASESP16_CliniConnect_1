package com.example.ase_group_1.health_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ClinicalTest extends Activity {

    String jsonFromRest="{" +
            "        \"Date1\": " +
            "        { " +
            "            \"Date\":\"03-07-2016\", " +
            "                \"Patient_ID\": \"1234567890.00\", " +
            "                \"Patient_Name\": \"Amitabh Bachhan\", " +
            "                \"Physician_Name\": \"Dr Anupam Kher\", " +
            "                \"Physician_Clinic_name\": \"ShahRukh Khan Memorial Hostital\", " +
            "                \"Tests\": [ " +
            "            {\"Name\": \"HDL\", \"Special_Instructions\": \"Do this before fasting.\"}, " +
            "            {\"Name\": \"LDL\", \"Special_Instructions\": \"Do this after fasting.\"}, " +
            "            {\"Name\": \"CBC\", \"Special_Instructions\": \"Do this during evening hours between 5PM to 10PM.\"} " +
            "            ] " +
            "        } " +
            "    }";
    //String[] dateStringArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
    ListView listView;
    String[] dateStringArray = {"03-01-2016","03-02-2016","03-03-2016","03-04-2016","03-05-2016","03-06-2016","03-07-2016","03-08-2016"};
    String[] testStringArray = {"HDL","CBC","LDL","RBC","LIPID Profile","Blood Sugar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clincal_test);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, dateStringArray);

        listView = (ListView)findViewById(R.id.clinical_test_date_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text

                Intent myIntent = new Intent(ClinicalTest.this, QRCodeAndDetailsActivity.class);
                myIntent.putExtra("jsonFromRest", jsonFromRest); //Optional parameters
                ClinicalTest.this.startActivity(myIntent);
            }
        });
    }
}
