package com.example.cafeteria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeteria.Models.Usercart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class LocationActivity extends AppCompatActivity {

    EditText UserLoc;
    Button placeorder;
    DatabaseReference database;
    int unique_No=0;
    ArrayList<Usercart> cartitems=new ArrayList<>();
    ArrayList<Usercart> l3;


    //code for unique order no
    public int countingitems() {

        final int[] Ono = new int[1];
        final ArrayList<Object> list = new ArrayList<>();

//        for(int i=1;i<1800;i++) {

        database = FirebaseDatabase.getInstance().getReference("orders");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Roll roll =dataSnapshot.getValue(Roll.class);
                    Roll roll = dataSnapshot.getValue(Roll.class);
                    assert roll != null;
                    list.add(roll.getRoll());
//                        y[0] = String.valueOf(list.get(list.size()-1));
                    Ono[0] = Integer.parseInt(String.valueOf(list.get(list.size() - 1)));
                    calljustonce(Ono[0]);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LocationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

//            x = (String) list.get(list.size() - 1);
//            Log.d("listvalue", x);
        if (Ono[0] + 1 == 1) {
            Ono[0] = 2;
            Log.d("Works1", String.valueOf(Ono[0]));
        } else {
//                int z=Integer.parseInt(y[0])+1;
//                y[0]= String.valueOf(z);
            Ono[0] = Ono[0] + 1;
            Log.d("Works2", String.valueOf(Ono[0]));
        }


//    }
            return Ono[0];

        }

    private void calljustonce(int j) {

        if (j + 1 == 1) {
            j = 2;
            unique_No=j;
            Log.d("Works1", String.valueOf(j));
        } else {
//                int z=Integer.parseInt(y[0])+1;
//                y[0]= String.valueOf(z);
            j =j + 1;
            unique_No=j;
            Log.d("Works2", String.valueOf(j));
        }

        Log.d("YESITSWORKS", unique_No+"Value updated");

    }

    //begins here
    public ArrayList<Usercart> xyz() {

        //get Token
        SharedPreferences getShared1 = getSharedPreferences("token", MODE_PRIVATE);
        String token = getShared1.getString("myToken", "");

        FirebaseDatabase db4 = FirebaseDatabase.getInstance();
        DatabaseReference myref = db4.getReference("User").child(token).child("cart");

        final ArrayList<Usercart> l2 = new ArrayList<>();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Roll roll =dataSnapshot.getValue(Roll.class);
                    Usercart usercart = dataSnapshot.getValue(Usercart.class);
                    assert usercart != null;
                    l2.add(usercart);
                    getorderlist(l2);
                    Log.d("RamRam", String.valueOf(l2));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return l2;
    }

    private void getorderlist(ArrayList<Usercart> l2) {
        l3=l2;
    }

    //ends here


    public void process(View view){

        Log.d("HeyKrishn", String.valueOf(l3));


        //get Token
        SharedPreferences getShared1 = getSharedPreferences("token", MODE_PRIVATE);
        String token = getShared1.getString("myToken", "");

        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference Userdata = DB.getReference("User");

        //get user status
        SharedPreferences getShared3=getSharedPreferences("UserStatus",MODE_PRIVATE);
        String Userstatuss=getShared3.getString("Userstatus","xxxx");


        //get admin token
        database = FirebaseDatabase.getInstance().getReference("Admintoken").child("admin_token");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                // send notification to admin
                String notification="New order arrived please check!!!";
                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(value,"Order arrived"
                        , notification, getApplicationContext(),  LocationActivity.this);
                notificationsSender.SendNotifications();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LocationActivity.this, "Error occur", Toast.LENGTH_SHORT).show();
            }
        });



        //get phone No
        SharedPreferences getShared=getSharedPreferences("pNo",MODE_PRIVATE);
        String pString=getShared.getString("phoneNo","xxxxxxxxxx");

        //get user location
        String userLoc=UserLoc.getText().toString();


        //get order no
//        int roll=random(1000,2000);
//        int roll=countingitems();
        int roll=unique_No;
        Log.d("Yesitworks2", "unique no Value updated"+unique_No);
        Log.d("Yesitworks3", "Roll Value updated"+String.valueOf(roll));
//        int roll=c;
        String roll1 =String.valueOf( roll);
        Log.d("MeraRoll", roll1);

        SharedPreferences shrdPref=getSharedPreferences("MyRoll",MODE_PRIVATE);
        SharedPreferences.Editor editor1=shrdPref.edit();
        editor1.putString("Roll",roll1);
        editor1.apply();

        //Main code
//        DBHelper helper1=new DBHelper(this);
//        ArrayList<Cartmodel> l1=helper1.getmyorders();

        //get cart list
        FirebaseDatabase db4=FirebaseDatabase.getInstance();
        DatabaseReference myref=db4.getReference("User").child(token).child("cart");


//        final ArrayList<Usercart> l1 = new ArrayList<>();
//        myref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
////                        Roll roll =dataSnapshot.getValue(Roll.class);
//                    Usercart usercart = dataSnapshot.getValue(Usercart.class);
//                    assert usercart != null;
//                    l1.add(usercart);
//                    Log.d("Prabhu", String.valueOf(l1));
//
//                }
                String foodname="";
                String quantity="";
                String orderitems="";

                if(l3!=null) {
                    for (int i = 0; i < l3.size(); i++) {
                        foodname = foodname + " " + ((l3.get(i).getQuantity()) + " " + (l3.get(i).getSoldItemName())) + ",";
                    }
                }else{
                    foodname=null;
                }


                //get totol price
                int sum = 5;
                if(l3!=null) {
                    for (int i = 0; i < l3.size(); i++) {
                        sum = sum + (Integer.parseInt(l3.get(i).getQuantity()) * Integer.parseInt(l3.get(i).getPrice()));
                    }
                }else{
                    sum=0;
                }

                String totalprice= String.valueOf(sum);


                //items to store in database
                String foodlist=foodname;
                String PhoneNo=pString;
                String status="5";


                PlaceOrderModel placeOrderModel=new PlaceOrderModel(foodlist,userLoc,PhoneNo,token,roll1,Userstatuss,totalprice);
                OrderStatus orderStatus=new OrderStatus(status);



                //code to store in database
                if(!userLoc.isEmpty() && foodname!=null) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference node = db.getReference("orders");
                    DatabaseReference node2 = db.getReference("status");
                    node2.child(roll1).setValue(orderStatus);
                    node.child(roll1).setValue(placeOrderModel);
                    Toast.makeText(LocationActivity.this, "Order placed", Toast.LENGTH_SHORT).show();

                    //clear cart
                    myref.removeValue();
//                    for(int i=0;i<l3.size();i++){
//                        l3.remove(i);
//                    }


                    //clear loc edittext
                    UserLoc.setText("");

                    startActivity(new Intent(LocationActivity.this,OrderConfirmationActivity.class));

                }else{
                    Toast.makeText(LocationActivity.this, "Location or Cart is Empty", Toast.LENGTH_SHORT).show();
                }

//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(LocationActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
//            }
//        });


    }


    int random(int x1, int x2)
    {
        int max = x1;
        int min = x2;
        int range = max - min + 1;

        int rand = (int)(Math.random() * range) + min;
        return rand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Objects.requireNonNull(getSupportActionBar()).hide();

        xyz();
        UserLoc=findViewById(R.id.UserLoc);
        placeorder=findViewById(R.id.PlaceOrderbtn);

        final int[] Ono = new int[1];
        final ArrayList<Object> list=new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("orders");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Roll roll =dataSnapshot.getValue(Roll.class);
                    Roll roll =dataSnapshot.getValue(Roll.class);
                    assert roll != null;
                    list.add(roll.getRoll());
//                        y[0] = String.valueOf(list.get(list.size()-1));
                    Ono[0] = Integer.parseInt(String.valueOf(list.get(list.size()-1)));
                    Log.d("Yess", String.valueOf(Ono[0]));
//                    Toast.makeText(LocationActivity.this, Ono[0], Toast.LENGTH_SHORT).show();
                }
                Log.d("Yess1", String.valueOf(Ono[0]));
//                Toast.makeText(LocationActivity.this, Ono[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LocationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("Yess2", String.valueOf(Ono[0]));

        Log.d("CCCCC", String.valueOf(countingitems()));


    }

}