package com.example.cafeteria;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmed extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        getSupportActionBar().hide();

        //code to change the color of status bar
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }


        Thread td=new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally{
                  startActivity(new Intent(OrderConfirmed.this,FoodPreparing.class));
                    finish();
                }
            }
        };td.start();

    }
}