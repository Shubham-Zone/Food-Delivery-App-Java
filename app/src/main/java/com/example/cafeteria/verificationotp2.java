package com.example.cafeteria;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

public class verificationotp2 extends AppCompatActivity {

    EditText inputnumber1,inputnumber2,inputnumber3,inputnumber4,inputnumber5,inputnumber6;
    String getotpbackend;
    private FirebaseAuth mAuth;
    TextView countdown,resendotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationotp2);

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

        final Button verifybuttonClick=findViewById(R.id.buttongetotp);

        inputnumber1=findViewById(R.id.inputotp1);
        inputnumber2=findViewById(R.id.inputotp2);
        inputnumber3=findViewById(R.id.inputotp3);
        inputnumber4=findViewById(R.id.inputotp4);
        inputnumber5=findViewById(R.id.inputotp5);
        inputnumber6=findViewById(R.id.inputotp6);

        countdown=findViewById(R.id.countdown);
        resendotp=findViewById(R.id.textresendotp);


        //countdown



            for(int i=0;i<10;i++) {

                new CountDownTimer(60000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        countdown.setText(":" + millisUntilFinished / 1000 + "sec");
                    }

                    public void onFinish() {
                        countdown.setText("");
                        resendotp.setText("Resend otp now");

                    }
                }.start();

//                Thread td=new Thread(){
//                    public void run(){
//                        try {
//                            sleep(60000);
//                        }catch (Exception ex){
//                            ex.printStackTrace();
//                        }finally{
//
//                            finish();
//                        }
//                    }
//                };td.start();
            }

        TextView textView=findViewById(R.id.textmobileno);
        textView.setText(String.format(
                "+91-%s",getIntent().getStringExtra("mobile")
        ));

        getotpbackend=getIntent().getStringExtra("backendotp");
        final ProgressBar progressBarverifyingotp=findViewById(R.id.progressbar_verify_otp);

        verifybuttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputnumber1.getText().toString().trim().isEmpty() && !inputnumber2.getText().toString().trim().isEmpty() && !inputnumber3.getText().toString().trim().isEmpty() && !inputnumber4.getText().toString().trim().isEmpty() && !inputnumber5.getText().toString().trim().isEmpty() && !inputnumber6.getText().toString().trim().isEmpty()){

                    String entercodeotp=inputnumber1.getText().toString()+
                            inputnumber2.getText().toString()+
                            inputnumber3.getText().toString()+
                            inputnumber4.getText().toString()+
                            inputnumber5.getText().toString()+
                            inputnumber6.getText().toString();

                    if(getotpbackend!=null){
                        progressBarverifyingotp.setVisibility(View.VISIBLE);
                        verifybuttonClick.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                                getotpbackend,entercodeotp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarverifyingotp.setVisibility(View.GONE);
                                        verifybuttonClick.setVisibility(View.VISIBLE);

                                        if(task.isSuccessful()){
                                            
                                            // fcm settings for perticular user

                                            FirebaseMessaging.getInstance().getToken()
                                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<String> task) {
                                                            if (!task.isSuccessful()) {
                                                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                                                return;
                                                            }

                                                            // Get new FCM registration token
                                                            String token = task.getResult();

                                                            SharedPreferences shrdPref=getSharedPreferences("token",MODE_PRIVATE);
                                                            SharedPreferences.Editor editor1=shrdPref.edit();
                                                            editor1.putString("myToken",token);
                                                            editor1.apply();

                                                            // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                                                            Log.d(TAG, "mytoken"+token);
//                        Toast.makeText(verificationotp2.this, token, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

//                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                            if (user != null) {
                                                // Name, email address, and profile photo Url
//                                                String PhoneNo = user.getPhoneNumber();
                                            String pNo=getIntent().getStringExtra("mobile");
                                            SharedPreferences shrdPref=getSharedPreferences("pNo",MODE_PRIVATE);
                                            SharedPreferences.Editor editor1=shrdPref.edit();
                                            editor1.putString("phoneNo",pNo);
                                            editor1.apply();

                                                SharedPreferences sharedPref=getSharedPreferences("Login Info",MODE_PRIVATE);
                                                SharedPreferences.Editor editor=sharedPref.edit();
                                                editor.putBoolean("user",true);
                                                editor.apply();

                                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                                // authenticate with your backend server, if you have one. Use
                                                // FirebaseUser.getIdToken() instead.
//                                                String uid = user.getUid();
//                                            }

                                            SharedPreferences getShared3=getSharedPreferences("UserStatus",MODE_PRIVATE);
                                            String Userstatuss=getShared3.getString("Userstatus","xxxx");

                                            if(Userstatuss!="xxxx"){
                                                startActivity(new Intent(verificationotp2.this,CategoryActivity.class));
                                            }else {
                                                Intent intent = new Intent(verificationotp2.this, UserStatus.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        }else{
                                            Toast.makeText(verificationotp2.this, "Enter the Correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else{
                        Toast.makeText(verificationotp2.this, "Please Check Internet Connection" , Toast.LENGTH_SHORT).show();
                    }

//                    Toast.makeText(verificationotp2.this, "otp verify", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(verificationotp2.this, "Please Enter all Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberotpmove();

        findViewById(R.id.textresendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+91" + getIntent().getStringExtra("mobile"))       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(verificationotp2.this)                 // Activity (for callback binding)
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(verificationotp2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                       getotpbackend=newbackendotp;
                        Toast.makeText(verificationotp2.this, "OTP Entered Successfully", Toast.LENGTH_SHORT).show();
                    }
                };
            }
        });

      //FCM


    }



    private void numberotpmove() {

        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber2.addTextChangedListener(new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}