package com.example.cafeteria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeteria.databinding.ActivityUserStatusBinding;

public class UserStatus extends AppCompatActivity {

    ActivityUserStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        binding.studentabc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shrdPref=getSharedPreferences("UserStatus",MODE_PRIVATE);
                SharedPreferences.Editor editor1=shrdPref.edit();
                editor1.putString("Userstatus","Student Abc");
                editor1.apply();
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.identityimg.setVisibility(View.GONE);
                binding.studentabc.setVisibility(View.GONE);
                binding.teacherabc.setVisibility(View.GONE);
                binding.teacherrla.setVisibility(View.GONE);
                binding.studentrla.setVisibility(View.GONE);

                Thread td=new Thread(){
                    public void run(){
                        try {
                            sleep(2000);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }finally{
                            startActivity(new Intent(UserStatus.this,CategoryActivity.class));
                            finish();
                        }
                    }
                };td.start();

            }
        });

        binding.teacherabc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shrdPref=getSharedPreferences("UserStatus",MODE_PRIVATE);
                SharedPreferences.Editor editor1=shrdPref.edit();
                editor1.putString("Userstatus","Teacher Abc");
                editor1.apply();
                startActivity(new Intent(UserStatus.this,CategoryActivity.class));

                binding.progressBar.setVisibility(View.VISIBLE);
                binding.identityimg.setVisibility(View.GONE);
                binding.studentabc.setVisibility(View.GONE);
                binding.studentrla.setVisibility(View.GONE);
                binding.teacherabc.setVisibility(View.GONE);
                binding.teacherrla.setVisibility(View.GONE);


                Thread td=new Thread(){
                    public void run(){
                        try {
                            sleep(2000);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }finally{
                            startActivity(new Intent(UserStatus.this,CategoryActivity.class));
                            finish();
                        }
                    }
                };td.start();

            }
        });

        binding.studentrla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shrdPref=getSharedPreferences("UserStatus",MODE_PRIVATE);
                SharedPreferences.Editor editor1=shrdPref.edit();
                editor1.putString("Userstatus","Student Rla");
                editor1.apply();
                startActivity(new Intent(UserStatus.this,CategoryActivity.class));

                binding.progressBar.setVisibility(View.VISIBLE);
                binding.identityimg.setVisibility(View.GONE);
                binding.studentabc.setVisibility(View.GONE);
                binding.studentrla.setVisibility(View.GONE);
                binding.teacherabc.setVisibility(View.GONE);
                binding.teacherrla.setVisibility(View.GONE);


                Thread td=new Thread(){
                    public void run(){
                        try {
                            sleep(2000);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }finally{
                            startActivity(new Intent(UserStatus.this,CategoryActivity.class));
                            finish();
                        }
                    }
                };td.start();

            }
        });

        binding.teacherrla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shrdPref=getSharedPreferences("UserStatus",MODE_PRIVATE);
                SharedPreferences.Editor editor1=shrdPref.edit();
                editor1.putString("Userstatus","Teacher Rla");
                editor1.apply();
                startActivity(new Intent(UserStatus.this,CategoryActivity.class));

                binding.progressBar.setVisibility(View.VISIBLE);
                binding.identityimg.setVisibility(View.GONE);
                binding.studentabc.setVisibility(View.GONE);
                binding.studentrla.setVisibility(View.GONE);
                binding.teacherabc.setVisibility(View.GONE);
                binding.teacherrla.setVisibility(View.GONE);


                Thread td=new Thread(){
                    public void run(){
                        try {
                            sleep(2000);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }finally{
                            startActivity(new Intent(UserStatus.this,CategoryActivity.class));
                            finish();
                        }
                    }
                };td.start();

            }
        });
    }
}