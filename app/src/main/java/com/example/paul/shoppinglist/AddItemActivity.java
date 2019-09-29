package com.example.paul.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;


public class AddItemActivity extends AppCompatActivity {

    TextView productName;
    TextView productQuantity;
    TextView productPrice;
    private final String url = SettingsActivity.serverIPAddress+"/ShoppingListWeb/insertDB.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        productName = findViewById(R.id.editTextProduct);
        productQuantity = findViewById(R.id.editTextQuantity);
        productPrice = findViewById(R.id.editTextPrice);
    }

    public void backActivity(View view) {
        Intent backToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backToMainActivity);
    }

    public void addItemToDB(View view) {
        final String tempProductName = productName.getText().toString();
        final String tempProductQuantity = productQuantity.getText().toString();
        final String tempProductPrice = productPrice.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response: "+response,Toast.LENGTH_SHORT).show();
                Log.i("ConnectInfo" ,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Errors !", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> parms =new HashMap<>();
                parms.put("product",tempProductName);
                parms.put("quantity",tempProductQuantity);
                parms.put("price",tempProductPrice);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}
