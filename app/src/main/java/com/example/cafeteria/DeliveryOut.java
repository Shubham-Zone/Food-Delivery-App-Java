package com.example.cafeteria;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DeliveryOut extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_out);

        getSupportActionBar().hide();


        Thread td=new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally{
                    startActivity(new Intent(DeliveryOut.this,CategoryActivity.class));
                    finish();
                }
            }
        };td.start();
    }
}