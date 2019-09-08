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

public class RegisterActivity extends AppCompatActivity {

    private String url = SettingsActivity.serverIPAddress+"/ShoppingListWeb/sign_up.php";
    EditText userName;
    EditText userPassword;
    EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.editTextNameR);
        userPassword = findViewById(R.id.editTextPassword);
        userEmail = findViewById(R.id.editTextEmail);
    }

    public void signIn(View view) {



        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ConnectInfo" ,response);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                if (response.equals("Create account successfully!")) {
                    Intent moveToLoginActivty = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(moveToLoginActivty);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Errors ! Incorrect IP address, login or password!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params =new HashMap<>();
                params.put("nameUsr",userName.getText().toString());
                params.put("passwordUsr",userPassword.getText().toString());
                params.put("emailUsr",userEmail.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent moveToLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(moveToLoginActivity);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
