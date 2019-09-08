package com.example.paul.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private String url = SettingsActivity.serverIPAddress+"/ShoppingListWeb/sign_in.php";
    protected static String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextNameR);
        editTextPassword = findViewById(R.id.editTextPassword);
        Log.i("ip",SettingsActivity.serverIPAddress);
        Log.i("logged", String.valueOf(Logger.isLogged()));
        if (Logger.isLogged()) {
            Intent moveToMainActivty = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(moveToMainActivty);
        }


    }

    public void signIn(View view) {
        final String userName = editTextName.getText().toString();
        final String password = editTextPassword.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ConnectInfo" ,response);
                //try read jsonarray
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int statusUsr = jsonObject.getInt("status");
                    int usrId = jsonObject.getInt("usr_id");
                    if (statusUsr == 1 && usrId != -1) {
                        new Logger();
                        Intent moveToMainActivty = new Intent(getApplicationContext(), MainActivity.class);
                        userData = userName + " " + usrId;
                        startActivity(moveToMainActivty);

                    }

                } catch (JSONException jsne) {
                    jsne.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("errorConnection", error.toString());
                Toast.makeText(getApplicationContext(), "Errors ! Incorrect IP address, login or password!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params =new HashMap<>();
                params.put("nameUsr",userName);
                params.put("passwordUsr",password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void signUp(View view) {
        Intent moveToSignUp = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(moveToSignUp);
    }

    public void moveToSettings(View view) {
        Intent toSettings = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(toSettings);
    }


}
