package com.example.cafeteria;

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

import com.example.cafeteria.Adapters.SnacksAdapter;
import com.example.cafeteria.Models.FoodItems;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.databinding.ActivitySnacksBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Snacks extends AppCompatActivity {

    ActivitySnacksBinding binding;
    LinearLayout homebtn;
    LinearLayout Profilebtn;
    FloatingActionButton Cartbtn;
    SnacksAdapter snacksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySnacksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Snacks");


        homebtn=findViewById(R.id.homeBtn);
        Profilebtn=findViewById(R.id.ProfileBtn);
        Cartbtn=findViewById(R.id.cartBtn);
        DBHelper helper1=new DBHelper(this);

        Profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Snacks.this,UserProfile.class));
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
                            startActivity(new Intent(Snacks.this, Cartlist.class));
                        }else {
                            startActivity(new Intent(Snacks.this,EmptyCart.class));
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
                startActivity(new Intent(Snacks.this,CategoryActivity.class));
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
//        list.add(new MainModel(R.drawable.bingo,"Bingo","20","Feels like having a tasty treat ? Bingo is for you . Enjoy it with your favourite beverage."));
//        list.add(new MainModel(R.drawable.bingomadangles,"Bingo mad angles","20","It is a classic instant snack with a desi twist."));
//        list.add(new MainModel(R.drawable.cadbury_dairy_milk,"Cadbury dairy milk","20","delightfully rich ,smooth and delicious chocolate that melt-in-the mouth…"));
//        list.add(new MainModel(R.drawable.choco_pie,"Choco pie","10","Fluffy marshmallows, soft biscuit and rich choco come together to bring…"));
//        list.add(new MainModel(R.drawable.dark_fantacy,"Dark fantacy","40","is one-of-a-kind cookie you can indulge in for a sinful chocolate experience."));
//        list.add(new MainModel(R.drawable.hide_and_seek,"Hide and seek","30","Are topped with luscious choco to offer you that never ending feeling of unbridled…."));
//        list.add(new MainModel(R.drawable.jimjam,"Jimjam","0",""));
////        list.add(new MainModel(R.drawable.kinderjoy,"Kinder joy","0",""));
//        list.add(new MainModel(R.drawable.kitkat,"Kitkat","20","chocolate- coated wafer biscuit bar."));
//        list.add(new MainModel(R.drawable.kurkure_green_chutney,"Kurkure(green chutney)","20","crunchy snack flavoured with a spicy green chutney flavour in a North Indian Rajasthani style……"));
//        list.add(new MainModel(R.drawable.kurkure_green_chutney,"Kurkure(green chutney)","10","crunchy snack flavoured with a spicy green chutney flavour in a North Indian Rajasthani style……"));
//        list.add(new MainModel(R.drawable.kurkure_masala_munch,"Kurkure(masala munch)","20","classic Kurkure flavour with a great combination of spice and crunch…."));
//        list.add(new MainModel(R.drawable.kurkure_masala_munch,"Kurkure(masala munch)","10","classic Kurkure flavour with a great combination of spice and crunch…."));
//        list.add(new MainModel(R.drawable.kurkure_puffcorn,"Kurkure puffcorn","20","light and fluffy snack that has a irresistibly yummy cheese flavour."));
//        list.add(new MainModel(R.drawable.kurkure_puffcorn,"Kurkure puffcorn","10","light and fluffy snack that has a irresistibly yummy cheese flavour."));
//        list.add(new MainModel(R.drawable.lays_cream_onion,"lays(cream onion)","20","all starts with farm-grown potatoes,  cooked and seasoned to perfection."));
//        list.add(new MainModel(R.drawable.lays_cream_onion,"lays(cream onion)","10","all starts with farm-grown potatoes,  cooked and seasoned to perfection."));
//        list.add(new MainModel(R.drawable.lays_indian_magic_masala,"lays(indian magic masala)","20","Taste the unbeatable blend of delectable Indian spices with best quality…"));
//        list.add(new MainModel(R.drawable.lays_indian_magic_masala,"lays(indian magic masala)","10","Taste the unbeatable blend of delectable Indian spices with best quality…"));
//        list.add(new MainModel(R.drawable.lays_salt,"lays(salt)","20","delicious crispy salted chips from Lay’s …"));
//        list.add(new MainModel(R.drawable.lays_salt,"lays(salt)","10","delicious crispy salted chips from Lay’s …"));
//        list.add(new MainModel(R.drawable.maxx,"Maxx","20","Made with epic combination of Maxx flavours.."));
//        list.add(new MainModel(R.drawable.maxx,"Maxx","10","Made with epic combination of Maxx flavours.."));
//        list.add(new MainModel(R.drawable.oreo,"Oreo","30","consisting of two chocolate wafers with a sweet crème filling in between…."));
//        list.add(new MainModel(R.drawable.oreo,"Oreo","10","consisting of two chocolate wafers with a sweet crème filling in between…."));
//        list.add(new MainModel(R.drawable.snickers,"Snickers","0","Consists of creamy sweet noughat , topped with delicious caramel and peanuts…"));
//        list.add(new MainModel(R.drawable.uncle_chips,"Uncle chips","20","Bolo mere lips !! I love Uncle Chpis ……"));
//        list.add(new MainModel(R.drawable.uncle_chips,"Uncle chips","10","Bolo mere lips !! I love Uncle Chpis ……"));
//
//        MainAdapter adapter=new MainAdapter(list,this);
//        binding.recyclerview.setAdapter(adapter);
//
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
//
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        binding.recyclerview.setLayoutManager(gridLayoutManager);

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
        binding.recyclerview.setLayoutManager(new WrapContentLinearLayoutManager(Snacks.this,2,  GridLayoutManager.VERTICAL, false));

        //Get data from firebase database
        FirebaseRecyclerOptions<FoodItems> options=
                new FirebaseRecyclerOptions.Builder<FoodItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child("Snacks"),FoodItems.class)
                        .build();

        snacksAdapter=new SnacksAdapter(options,this);
        binding.recyclerview.setAdapter(snacksAdapter);

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
//                startActivity(new Intent(Snacks.this, OrderActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Snacks.this,CategoryActivity.class));
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        snacksAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        snacksAdapter.stopListening();
    }

}