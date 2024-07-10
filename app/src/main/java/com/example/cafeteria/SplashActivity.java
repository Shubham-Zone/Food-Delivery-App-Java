package com.example.cafeteria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    String myToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("all");



//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
//                  Intent intent=new Intent(SplashActivity.this,entermobileno1.class);
//                  startActivity(intent);

                  SharedPreferences getShared=getSharedPreferences("Login Info",MODE_PRIVATE);
                  Boolean pString=getShared.getBoolean("user",false);

                  SharedPreferences getShared3=getSharedPreferences("UserStatus",MODE_PRIVATE);
                  String Userstatuss=getShared3.getString("Userstatus","xxxx");

                  if (pString) {
                      if(Userstatuss!="xxxx"){
                          startActivity(new Intent(SplashActivity.this,CategoryActivity.class));
                      }else{
                          startActivity(new Intent(SplashActivity.this,UserStatus.class));
                      }
                      finish();
                  }else{

                      startActivity(new Intent(SplashActivity.this,entermobileno1.class));
                      finish();

                  }




                  finish();
              }
          }
        };td.start();


    }
}