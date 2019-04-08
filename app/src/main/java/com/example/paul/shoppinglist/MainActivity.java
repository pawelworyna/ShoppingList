package com.example.paul.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String itemList = "";
    private final String urlToDelete = "http://"+SettingsActivity.serverIPAddress+"/ShoppingListWeb/deleteFromDB.php";
    ListView listView;
    GetJSON getJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                itemList = (String) listView.getItemAtPosition( position );
                Toast.makeText(getApplicationContext(),
                        "Chosen: " + itemList, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    public void deleteFromList(View view) {
        if(!itemList.isEmpty()){
            String [] tempArr = itemList.split(" ");
            final String tempProductName = tempArr[0];
            final String tempProductQuantity = tempArr[1];

            StringRequest stringRequest=new StringRequest(Request.Method.POST, urlToDelete, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Delete successfully",Toast.LENGTH_SHORT).show();
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
                    return parms;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }


    public void addActivity(View view) {
        Intent goToNextActivity = new Intent(getApplicationContext(), AddItemActivity.class);
        startActivity(goToNextActivity);
    }

    public void showRecords(View view){
        getJSON = new GetJSON("http://"+SettingsActivity.serverIPAddress+"/ShoppingListWeb/index.php", this.getApplicationContext());
        getJSON.execute();
    }

    private class GetJSON extends AsyncTask<Void, Void, String> {
        private String urlService;
        private Context context;

        public GetJSON(String urlService, Context context) {
            this.urlService = urlService;
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                //creating a URL
                URL url = new URL(urlService);

                //Opening the URL using HttpURLConnection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //StringBuilder object to read the string from the service
                StringBuilder sb = new StringBuilder();

                //We will use a buffered reader to read the string from service
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                //A simple string to read values from each line
                String json;

                //reading until we don't find null
                while ((json = bufferedReader.readLine()) != null) {

                    //appending it to string builder
                    sb.append(json + "\n");
                }

                //finally returning the read string
                return sb.toString().trim();
            } catch (Exception e) {
                return null;
            }

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            try {
                loadIntoListView(s);
            } catch (JSONException | NullPointerException  e) {
                e.printStackTrace();
            }

        }

        private void loadIntoListView(String json) throws JSONException {
            //creating a json array from the json string
            JSONArray jsonArray = new JSONArray(json);

            //creating a string array for listview
            String[] products = new String[jsonArray.length()];

            //looping through all the elements in json array
            for (int i = 0; i < jsonArray.length(); i++) {

                //getting json object from the json array
                JSONObject obj = jsonArray.getJSONObject(i);

                //getting the name from the json object and putting it inside string array
                products[i] = obj.getString("product") + " " + obj.getString("quantity");
            }

            //the array adapter to load data into list
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, products);

            //attaching adapter to listview
            listView.setAdapter(arrayAdapter);
        }
    }
}
