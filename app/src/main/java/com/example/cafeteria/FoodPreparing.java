package com.example.cafeteria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class FoodPreparing extends AppCompatActivity {


    DatabaseReference database;


    private String status() {
        final String[] y = {""};
        for(int i=1;i<1800;i++) {

            Log.d("HOHOHO", String.valueOf(i));
            //get roll
            SharedPreferences getShared = getSharedPreferences("MyRoll", MODE_PRIVATE);
            String pString = getShared.getString("Roll", "xxxxxxxxxx");

            database = FirebaseDatabase.getInstance().getReference("status").child(pString).child("status");

            //get status
            final String[] x = new String[1];
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    x[0] = (String) snapshot.getValue();
                    y[0] =x[0];
                    Log.d("HeyRam", x[0]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(FoodPreparing.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            });

            Log.d("YYYYY", "b"+y[0]+"b");
            if(y[0].equals("2") ) {
                Log.d("SS", "status value changed by admin"+String.valueOf(i));
                break;
            }else  if(i==1799 && y[0].equals("1")){
                Log.d("RR", "time out status value not changed");
                y[0]="0";
            }else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
        return y[0];

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_preparing);

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
                   if(status().equals("2")){
                       startActivity(new Intent(FoodPreparing.this,DeliveryOut.class));
                   }
                    finish();
                }
            }
        };td.start();

    }
}