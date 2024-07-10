package com.example.cafeteria.FoodCategory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteria.Adapters.SouthIndianAdapter;
import com.example.cafeteria.Cartlist;
import com.example.cafeteria.CategoryActivity;
import com.example.cafeteria.DBHelper;
import com.example.cafeteria.EmptyCart;
import com.example.cafeteria.Models.FoodItems;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.R;
import com.example.cafeteria.UserProfile;
import com.example.cafeteria.databinding.ActivitySouthIndianBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SouthIndian extends AppCompatActivity {

    ActivitySouthIndianBinding binding;
    LinearLayout homebtn;
    LinearLayout Profilebtn;
    FloatingActionButton Cartbtn;
    SouthIndianAdapter southIndianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySouthIndianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("South indian");

        homebtn=findViewById(R.id.homeBtn);
        Profilebtn=findViewById(R.id.ProfileBtn);
        Cartbtn=findViewById(R.id.cartBtn);
        DBHelper helper1=new DBHelper(this);

        Profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SouthIndian.this, UserProfile.class));
            }
        });

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
//                    getorderlist(l2);
                    Log.d("RamRam", String.valueOf(l2));
                }

                Cartbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //enter empty activity if cart is empty
                        Usercart usercart=new Usercart();
                        ArrayList<Usercart> l1=new ArrayList<>();
                        l1.add(usercart);
                        if(!l2.isEmpty()) {
                            startActivity(new Intent(SouthIndian.this, Cartlist.class));
                        }else {
                            startActivity(new Intent(SouthIndian.this,EmptyCart.class));
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SouthIndian.this, CategoryActivity.class));
            }
        });

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

//        ArrayList<MainModel> list =new ArrayList<>();
//
//        list.add(new MainModel(R.drawable.idli_sambar,"Idli Sambar","40","An ideal & flavoured south Indian style receipe."));
//        list.add(new MainModel(R.drawable.masala_dosa,"Masala dosa","40","This one dish , that always been and will continued ."));
//        list.add(new MainModel(R.drawable.onion_utthapam,"Onion utthapam","50","Topped with finely chopped onionsand herbs."));
//        list.add(new MainModel(R.drawable.plain_dosa,"Plain dosa","30","Dosas are the staple food of south india."));
//        list.add(new MainModel(R.drawable.sambar_vada,"Sambar vada","40","Prepared by combining the spiced and flavoured Sambhar with soft & crunchy vada."));
//
//        MainAdapter adapter=new MainAdapter(list,this);
//        binding.recyclerview.setAdapter(adapter);
//
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        binding.recyclerview.setLayoutManager(gridLayoutManager);\

        class WrapContentLinearLayoutManager extends GridLayoutManager {


            public WrapContentLinearLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
                super(context, spanCount, orientation, reverseLayout);
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

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);

//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(new WrapContentLinearLayoutManager(SouthIndian.this,2,  GridLayoutManager.VERTICAL, false));

        //Get data from firebase database
        FirebaseRecyclerOptions<FoodItems> options=
                new FirebaseRecyclerOptions.Builder<FoodItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child("SouthIndian"),FoodItems.class)
                        .build();

        southIndianAdapter=new SouthIndianAdapter(options,this);
        binding.recyclerview.setAdapter(southIndianAdapter);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.orders:
//                startActivity(new Intent(SouthIndian.this, OrderActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(SouthIndian.this,CategoryActivity.class));
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        southIndianAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        southIndianAdapter.stopListening();
    }
}