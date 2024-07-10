package com.example.cafeteria;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class entermobileno1 extends AppCompatActivity {

    EditText enternumber;
    Button getotpbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entermobileno1);

        //code to change the color of status bar
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }


        //define ActionBar object
        ActionBar actionBar;
        actionBar =getSupportActionBar();

        /* Define ColorDrawable object and parse color
         * using parseColor method
         * with color hash code as its parameter*/
        ColorDrawable colorDrawable
                =new ColorDrawable(Color.parseColor("#FF4500"));

        //set background
        actionBar.setBackgroundDrawable(colorDrawable);

        mAuth=FirebaseAuth.getInstance();

         enternumber=findViewById(R.id.input_mobile_number);
         getotpbtn=findViewById(R.id.buttongetotp);

         final ProgressBar progressBar=findViewById(R.id.progressbar_sending_otp);

        PhoneAuthProvider.OnVerificationStateChangedCallbacks
                mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressBar.setVisibility(View.GONE);
                getotpbtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.GONE);
                getotpbtn.setVisibility(View.VISIBLE);
                Toast.makeText(entermobileno1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                progressBar.setVisibility(View.GONE);
                getotpbtn.setVisibility(View.VISIBLE);
                Intent intent=new Intent(entermobileno1.this,verificationotp2.class);
                intent.putExtra("mobile",enternumber.getText().toString());
                intent.putExtra("backendotp",backendotp);
                startActivity(intent);
            }
        };

         getotpbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (!enternumber.getText().toString().trim().isEmpty()){
                     if (enternumber.getText().toString().trim().length()==10){

                         progressBar.setVisibility(View.VISIBLE);
                         getotpbtn.setVisibility(View.INVISIBLE);


                         PhoneAuthOptions options =
                                 PhoneAuthOptions.newBuilder(mAuth)
                                         .setPhoneNumber("+91" + enternumber.getText().toString())       // Phone number to verify
                                         .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                         .setActivity(entermobileno1.this)
                                         .setCallbacks(mCallbacks)// Activity (for callback binding)
                                         .build();
                         PhoneAuthProvider.verifyPhoneNumber(options);


                     }else{
                         Toast.makeText(entermobileno1.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                     }
                 }else{
                     Toast.makeText(entermobileno1.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }
}