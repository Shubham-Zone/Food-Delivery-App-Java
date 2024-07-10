package com.example.cafeteria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    TextView mobNo,userstatus;
    LinearLayout orderhistory;
    LinearLayout sharemyapp;
    LinearLayout aboutus;
    LinearLayout contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
//        orderhistory=findViewById(R.id.OrderHistory);
        sharemyapp=findViewById(R.id.sharemyapp);
        aboutus=findViewById(R.id.AboutUs);
        userstatus=findViewById(R.id.userstatus);
        contactus=findViewById(R.id.contactus);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cafetaria profile");


//        orderhistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(UserProfile.this,OrderActivity.class));
//            }
//        });

        sharemyapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,AboutUs.class));
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, com.example.cafeteria.contactus.class));
            }
        });

        //code to change the color of status bar
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }


//        //define ActionBar object
//        ActionBar actionBar;
//        actionBar =getSupportActionBar();
//
//        /* Define ColorDrawable object and parse color
//         * using parseColor method
//         * with color hash code as its parameter*/
//        ColorDrawable colorDrawable
//                =new ColorDrawable(Color.parseColor("#FF4500"));
//
//        //set background
//        actionBar.setBackgroundDrawable(colorDrawable);

        getSupportActionBar().hide();

        mobNo=findViewById(R.id.PhoneNo);

        SharedPreferences getShared=getSharedPreferences("pNo",MODE_PRIVATE);
        String pString=getShared.getString("phoneNo","xxxxxxxxxx");

        mobNo.setText(pString);


        //get user status
        SharedPreferences getShared3=getSharedPreferences("UserStatus",MODE_PRIVATE);
        String Userstatuss=getShared3.getString("Userstatus","xxxx");

        userstatus.setText(Userstatuss);
    }
}