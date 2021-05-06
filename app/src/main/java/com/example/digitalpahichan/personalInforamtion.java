package com.example.digitalpahichan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class personalInforamtion extends AppCompatActivity {
TextView pname,Pphonenumber,pgender,Taddress,
        Paddress,Fathername,mothername,
        Grandfathername,marriedstatus,edulevel,occupation,
        citizenship,bloodgroup;
    RequestQueue requestQueue;
    FirebaseAuth firebaseAuth;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_inforamtion);
        pname = findViewById(R.id.Pname);
        Pphonenumber = findViewById(R.id.PphoneNumber);
        pgender=findViewById(R.id.Pgender);
        Taddress = findViewById(R.id.Taddress);
        Paddress = findViewById(R.id.Paddress);
        Fathername = findViewById(R.id.Fathername);
        mothername = findViewById(R.id.mothername);
        Grandfathername = findViewById(R.id.Grandfathername);
        marriedstatus = findViewById(R.id.marriedstatus);
        edulevel = findViewById(R.id.edulevel);
        occupation = findViewById(R.id.occupation);
        citizenship = findViewById(R.id.citizenship);
        bloodgroup = findViewById(R.id.bloodgroup);
        requestQueue = Volley.newRequestQueue(this);
        phonenumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        parseData();
    }
    private void parseData(){
        String url ="https://raw.githubusercontent.com/aba10101/random/main/hello.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("users");
                    for(int i =0 ; i<jsonArray.length();i++){
                        JSONObject users = jsonArray.getJSONObject(i);
                        if (users.getString("phone").equals(phonenumber)){
                            pname.setText(users.getString("name"));
                            Pphonenumber.setText(users.getString("phone"));
                            pgender.setText(users.getString("Gender"));
                            Taddress.setText(users.getString("Temporary_Address"));
                            Paddress.setText(users.getString("Permanent_Address"));
                            Fathername.setText(users.getString("Father’s Name"));
                            mothername.setText(users.getString("Mother’s Name"));
                            Grandfathername.setText(users.getString("Grandfather’s Name"));
                            marriedstatus.setText(users.getString("Married Status"));
                            edulevel.setText(users.getString("Education Level"));
                            occupation.setText(users.getString("Occupation"));
                            citizenship.setText(users.getString("Citizenship no"));
                            bloodgroup.setText(users.getString("Blood Group"));




                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error Featching DATA",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}