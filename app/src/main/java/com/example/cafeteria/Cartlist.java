package com.example.cafeteria;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteria.Adapters.Cartadapter;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.databinding.ActivityCartlistBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cartlist extends AppCompatActivity {

    ActivityCartlistBinding binding;
    CardView AddLocBtn;
    TextView Subtotal,TotalRs;
    Cartadapter cartadapter;
    DatabaseReference database;
    DatabaseReference fees;
    public List<Usercart> globalList=new ArrayList<>();
    int sum ;
    int subtotal;

    private void getl1(ArrayList<Usercart> l1) {
        globalList=l1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Subtotal=findViewById(R.id.subtotal);
        TotalRs=findViewById(R.id.Totalrs);

        AddLocBtn=findViewById(R.id.addlocbtn);


        getSupportActionBar().hide();

        //code to change the color of status bar
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }


                    class WrapContentLinearLayoutManager extends LinearLayoutManager {
                public WrapContentLinearLayoutManager(Context context, int horizontal, boolean b) {
                    super(context);
                }

                //... constructor
                @Override
                public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                    try {
                        super.onLayoutChildren(recycler, state);
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("TAG", "meet a IOOBE in RecyclerView");
                    }
                }
            }

        SharedPreferences getShared1 = getSharedPreferences("token", MODE_PRIVATE);
        String token = getShared1.getString("myToken", "");

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerViewcart.setLayoutManager(new WrapContentLinearLayoutManager(Cartlist.this, LinearLayoutManager.HORIZONTAL, false));


        //Get data from firebase database
        FirebaseRecyclerOptions<Usercart> options=
                new FirebaseRecyclerOptions.Builder<Usercart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("User").child(token).child("cart"),Usercart.class)
                        .build();



        cartadapter=new Cartadapter(options,this,Subtotal,TotalRs);
        binding.recyclerViewcart.setAdapter(cartadapter);


        final ArrayList<Usercart> l1 = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("User").child(token).child("cart");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Usercart usercart = dataSnapshot.getValue(Usercart.class);
                    assert usercart != null;
                    l1.add(usercart);
                    getl1(l1);
                }

                //get delivery fee
                fees=FirebaseDatabase.getInstance().getReference("Deliveryfee").child("fee");
                fees.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String fee;
                            fee = snapshot.getValue(String.class);
                            sum = Integer.parseInt(fee);
                            binding.deliveryfee.setText("Rs"+fee);
                            subtotal = 0;
                            for (int i = 0; i < globalList.size(); i++) {
                                sum = sum + (Integer.parseInt(globalList.get(i).getQuantity()) * Integer.parseInt(globalList.get(i).getPrice()));
                                subtotal = subtotal + (Integer.parseInt(globalList.get(i).getQuantity()) * Integer.parseInt(globalList.get(i).getPrice()));
                            }

                            Subtotal.setText("Rs"+subtotal);
                            TotalRs.setText("Rs"+sum);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                AddLocBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!globalList.isEmpty()) {
                            startActivity(new Intent(Cartlist.this, LocationActivity.class));
                        }else {
                            Toast.makeText(Cartlist.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Cartlist.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        if(globalList.isEmpty()){
            Subtotal.setText("0");
            TotalRs.setText("0");
        }

//        Log.d("HareKrishna", String.valueOf(l1));



//        ArrayList<FirebaseRecyclerOptions<Usercart>> l1=new ArrayList<>();
//        l1.add(options);
//
//        DBHelper helper1=new DBHelper(this);
//        ArrayList<Cartmodel> l1=helper1.getmyorders();
//
//        int sum=5;
//        int subtotal=0;
//        for(int i=0;i<l1.size();i++){
//            sum=sum+(Integer.parseInt(l1.get(i).getQuantity()) * Integer.parseInt(l1.get(i).getPrice()));
//            subtotal=subtotal+(Integer.parseInt(l1.get(i).getQuantity()) * Integer.parseInt(l1.get(i).getPrice()));
//        }
//
//
//
//

////
////
//        Subtotal.setText("Rs"+subtotal);
//        TotalRs.setText("Rs"+sum);
//
//
//        Cartadapter cartadapter=new Cartadapter(l1,this,Subtotal,TotalRs);
//        binding.recyclerViewcart.setAdapter(cartadapter);
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        binding.recyclerViewcart.setLayoutManager(layoutManager);



    }



    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Cartlist.this,CategoryActivity.class));
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        cartadapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        cartadapter.stopListening();
    }

}
