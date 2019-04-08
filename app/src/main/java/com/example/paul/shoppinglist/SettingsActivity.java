package com.example.paul.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    protected static String serverIPAddress = "192.168.43.76:80";
    private EditText editIPAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editIPAddress = findViewById(R.id.editTextIPAdr);
        editIPAddress.setText(serverIPAddress);

    }

    public void setIpAddress(View view) {
        serverIPAddress = editIPAddress.getText().toString();
        editIPAddress.setText(serverIPAddress);
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
