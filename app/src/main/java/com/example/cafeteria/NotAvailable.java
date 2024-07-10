package com.example.cafeteria;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class NotAvailable extends AppCompatActivity {

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_available);
        getSupportActionBar().hide();


    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(NotAvailable.this,CategoryActivity.class));
        finish();
    }
}