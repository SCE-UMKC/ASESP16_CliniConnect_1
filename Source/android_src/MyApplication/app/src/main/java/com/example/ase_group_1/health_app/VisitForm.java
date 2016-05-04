package com.example.ase_group_1.health_app;

/**
 * Created by ljm7b on 3/30/2016.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.github.dkharrat.nexusdialog.FormWithAppCompatActivity;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates the bare minimum to display a form in an Activity.
 */
public class VisitForm extends FormWithAppCompatActivity {

    String[] vars = {"firstCom", "secondCom", "thirdCom", "allergy1", "allergy2", "allergy3", "foodAllergy",
            "firstMed", "secondMed", "thirdMed", "firstSur", "secondSur",
            "thirdSur", "smoke", "drink", "cancer", "tuberculosis", "diabetes"};
    String mEmail;
    JSONObject jo;
    JSONParser2 jsonParser2 = new JSONParser2();
    VisitFormTask vTask;
    List<String> lStr;

    @Override
    protected void initForm() {
        setTitle("Pre-Visit Form");
        mEmail = getIntent().getStringExtra("username");


        FormSectionController section1 = new FormSectionController(this, "Today's Complaint");
        section1.addElement(new EditTextController(this, "firstCom", "First"));
        section1.addElement(new EditTextController(this, "secondCom", "Second"));
        section1.addElement(new EditTextController(this, "thirdCom", "Third"));
        getFormController().addSection(section1);


        FormSectionController section2 = new FormSectionController(this, "Medication Allergies");

        section2.addElement(new SelectionController(this, "allergy1", "Medication 1", true, "Select",
                Arrays.asList("Codeine", "Penicillin", "Sulfa Drugs", "Mycins", "Asprin"), true));

        section2.addElement(new SelectionController(this, "allergy2", "Medication 2", true, "Select",
                Arrays.asList("Codeine", "Penicillin", "Sulfa Drugs", "Mycins", "Asprin"), true));

        section2.addElement(new SelectionController(this, "allergy3", "Medication 3", true, "Select",
                Arrays.asList("Codeine", "Penicillin", "Sulfa Drugs", "Mycins", "Asprin"), true));

        section2.addElement(new EditTextController(this, "foodAllergy", "Food Allergies"));

        getFormController().addSection(section2);

        FormSectionController section3 = new FormSectionController(this, "Medications I Take");
        section3.addElement(new EditTextController(this, "firstMed", "First"));
        section3.addElement(new EditTextController(this, "secondMed", "Second"));
        section3.addElement(new EditTextController(this, "thirdMed", "Third"));
        getFormController().addSection(section3);


        FormSectionController section4 = new FormSectionController(this, "Surgeries I Have Had");
        section4.addElement(new EditTextController(this, "firstSur", "First"));
        section4.addElement(new EditTextController(this, "secondSur", "Second"));
        section4.addElement(new EditTextController(this, "thirdSur", "Third"));
        getFormController().addSection(section4);

        FormSectionController section5 = new FormSectionController(this, "Drugs");
        section5.addElement(new SelectionController(this, "smoke", "Do You Smoke?", true, "Select",
                Arrays.asList("Yes", "No"), true));
        section5.addElement(new SelectionController(this, "drink", "Drink Alcohol?", true, "Select",
                Arrays.asList("Yes", "No"), true));
        getFormController().addSection(section5);

        FormSectionController section6 = new FormSectionController(this, "Has Any Relative had?");
        section6.addElement(new SelectionController(this, "cancer", "Cancer?", true, "Select",
                Arrays.asList("Yes", "No"), true));
        section6.addElement(new SelectionController(this, "tuberculosis", "Tuberculosis?", true, "Select",
                Arrays.asList("Yes", "No"), true));
        section6.addElement(new SelectionController(this, "diabetes", "Diabetes?", true, "Select",
                Arrays.asList("Yes", "No"), true));
        getFormController().addSection(section6);


        getModel().addPropertyChangeListener("diabetes", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                Log.i("tag", "Value was: " + event.getOldValue() + ", now: " + event.getNewValue());
                getVars();
                vTask = new VisitFormTask(jo, lStr);
                vTask.execute((Void) null);
            }
        });
    }

    public void getVars() {
        jo = new JSONObject();
        lStr = new ArrayList<>();
        for (int i = 0; i < 18; ++i) {

            try {

                if (getModel().getValue(vars[i]) != null) {
                    Log.d(vars[i], getModel().getValue(vars[i]).toString());
                    jo.put(vars[i], getModel().getValue(vars[i]).toString());
                    lStr.add(vars[i]);

                }

            } catch (Exception e) {
                Log.d("JSON", "error");
            }
        }
    }


    public class VisitFormTask extends AsyncTask<Void, Void, Boolean> {

        JSONObject jObj;
        List<String> str;

        VisitFormTask(JSONObject j, List<String> s) {
            jObj = j;
            str = s;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            List<NameValuePair> args = new ArrayList<NameValuePair>();

            args.add(new BasicNameValuePair("username", mEmail));


            for (int i = 0; i < 18; ++i) {
                try {
                    args.add(new BasicNameValuePair(lStr.get(i), jObj.getString(lStr.get(i))));

                } catch (Exception e) {
                    Log.d("in-background", "build name val");
                }

            }
            Log.d("thee array", lStr.toString());
            args.add(new BasicNameValuePair("objects", lStr.toString()));
            String url_login_user = "< TODO add_url_here >";
            //String url_login_user = "192.168.1.15:8080/CliniConnectAdmin/VisitForm";

            JSONObject json;
            // getting JSON Object
            // Note that create product url accepts POST method
            try {
                json = jsonParser2.makeHttpRequest(url_login_user,
                        "POST", args);

                // check log cat from response
                Log.d("Create Response", json.toString());
            } catch (Exception e) {
                // there is an error with server connection
                return false;
            }


            try {
                String success = json.get("status").toString();
                if (success.equals("success")) {

                    return true;
                }

            } catch (Exception e) {
                return false;
            }

            return false;
            // TODO: register the new account here.
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                finish();

                Context context = getApplicationContext();
                CharSequence text = "Form Submitted, Thanks!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

//                Intent redirect = new Intent(VisitForm.this, MainActivity.class);
//                redirect.putExtra("username", mEmail);
//                VisitForm.this.startActivity(redirect);
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Error Submitting Form, Try Again Later.";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}