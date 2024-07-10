package com.example.cafeteria;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Order_Status extends AppCompatActivity {

    ArrayList<Status> list;
    DatabaseReference database;
    ImageView wait,done,out;

    //get status of order
    public String status() {

        final String[] y = {""};
        for(int i=1;i<1800;i++) {

            Log.d("HOHOHO", String.valueOf(i));
            //get roll
            SharedPreferences getShared = getSharedPreferences("MyRoll", MODE_PRIVATE);
            String pString = getShared.getString("Roll", "xxxxxxxxxx");

            if(pString.equals("xxxxxxxxxx"))
                break;

            database = FirebaseDatabase.getInstance().getReference("status").child(pString).child("status");

            //get status
            final String[] x = new String[1];
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    x[0] = (String) snapshot.getValue();
                    y[0] =x[0];
//                   Log.d("HeyRam", x[0]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Order_Status.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            });

            Log.d("YYYYY", "b"+y[0]+"b");
            if(y[0].equals("1") || y[0].equals("0") || y[0].equals("2")) {
                Log.d("SS", "status value changed by admin"+String.valueOf(i));
                break;
            }else  if(i==1799 && y[0].equals("5")){
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
        setContentView(R.layout.activity_order_status);
        getSupportActionBar().hide();

        wait=findViewById(R.id.wait);
        done=findViewById(R.id.done);
        out=findViewById(R.id.out);


        Thread td=new Thread(){
            public void run(){
                try {
                    sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally{
                    if(status().equals("")) {
                        wait.setImageResource(R.drawable.wait);
                        done.setImageResource(R.drawable.wait);
                        out.setImageResource(R.drawable.wait);
                    }else{
                        if (status().equals("1")) {
                            wait.setImageResource(R.drawable.completed);
                            done.setImageResource(R.drawable.completed);
                        }
                            if (status().equals("2")) {
                                out.setImageResource(R.drawable.completed);
                                wait.setImageResource(R.drawable.completed);
                                done.setImageResource(R.drawable.completed);
                            }
                            if (status().equals("0")) {
                                wait.setImageResource(R.drawable.wait);
                                done.setImageResource(R.drawable.wait);
                                out.setImageResource(R.drawable.wait);
                            }
                            if (status().equals("5")) {
                                wait.setImageResource(R.drawable.wait);
                                done.setImageResource(R.drawable.wait);
                                out.setImageResource(R.drawable.wait);
                           }
                    }
                }
            }
        };td.start();

    }
}