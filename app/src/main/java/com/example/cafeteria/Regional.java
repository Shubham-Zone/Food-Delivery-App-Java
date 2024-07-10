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

import com.example.cafeteria.Adapters.RegionalAdapter;
import com.example.cafeteria.Models.FoodItems;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.databinding.ActivityRegionalBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Regional extends AppCompatActivity {

    ActivityRegionalBinding binding;
    LinearLayout homebtn;
    LinearLayout Profilebtn;
    FloatingActionButton Cartbtn;
    RegionalAdapter regionalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegionalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Regional");

        homebtn=findViewById(R.id.homeBtn);
        Profilebtn=findViewById(R.id.ProfileBtn);
        Cartbtn=findViewById(R.id.cartBtn);
        DBHelper helper1=new DBHelper(this);

        Profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Regional.this,UserProfile.class));
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
                            startActivity(new Intent(Regional.this, Cartlist.class));
                        }else {
                            startActivity(new Intent(Regional.this,EmptyCart.class));
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
                startActivity(new Intent(Regional.this,CategoryActivity.class));
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
//        list.add(new MainModel(R.drawable.dhokla,"Dhokla","0","Combination of sweet and sour taste."));
//        list.add(new MainModel(R.drawable.burger,"Burger","30","Load up buns in veg-free style with best ever veg burger."));
//        list.add(new MainModel(R.drawable.allu_tikki,"Allu_Tikki","40","Made with Boiled Potatoes,aromatic spices and herbs"));
//        list.add(new MainModel(R.drawable.breadomletpng,"Bread_Omlet","40","Freshly made , perfect omlette."));
//        list.add(new MainModel(R.drawable.chole_bhature,"Chole_Bhature","40","It is a combination of chana masala, and bhatura/puri, a fried bread made from maida."));
//        list.add(new MainModel(R.drawable.cholechawalpng2,"Chole Chawal","40","The rice is made with some peas and tiny cubes of paneer."));
//        list.add(new MainModel(R.drawable.chole_kulche,"Chole kulche","40"," Lip smacking combination of kulche and chhole."));
//        list.add(new MainModel(R.drawable.egg_fried_rice,"Egg Fried Rice","60"," A delicious Chinese recipe with stir fried eggs squashed together with garlic, onion, rice and various other spicy sauces."));
//        list.add(new MainModel(R.drawable.grillcheesesandwichpng,"Grill cheese sandwich","50","Fresh from the grill for you to eat."));
//        list.add(new MainModel(R.drawable.paneerpattiespng,"Paneer Patties","20","Crispy patty , filled with exotic paneer."));
//        list.add(new MainModel(R.drawable.pao_bhaji,"Pao bhaji","40","Spicy mashed vegetable dish , served hot with dollop of butter."));
//        list.add(new MainModel(R.drawable.rajma_chawal,"Rajma Chawal","40","Rajma chawal is a simple yet delicious meal. Rajma is rich and iron and protein."));
//        list.add(new MainModel(R.drawable.samosa,"Samosa","10","Stuffed with delicious potato filling with crispy and flaky crust."));
//        list.add(new MainModel(R.drawable.sandwichpng,"Sandwich","30","Two or more slices of bread, usually buttered, with a filling of cheese."));
//        list.add(new MainModel(R.drawable.lunchthalipng,"Lunch thali","50",""));
//        list.add(new MainModel(R.drawable.deluxethali,"Deluxe thali","70",""));
//        list.add(new MainModel(R.drawable.naanthalipng,"Naan thali","50",""));
//        list.add(new MainModel(R.drawable.breadpakorapng,"Bread pakora","15",""));
//        list.add(new MainModel(R.drawable.allupuripng,"Allu puri","40",""));
//        list.add(new MainModel(R.drawable.paneerricepng,"Paneer fried rice","60",""));
//        list.add(new MainModel(R.drawable.veg_patties,"Veg patties","15",""));
//
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
        binding.recyclerview.setLayoutManager(new WrapContentLinearLayoutManager(Regional.this,2,  GridLayoutManager.VERTICAL, false));

        //Get data from firebase database
        FirebaseRecyclerOptions<FoodItems> options=
                new FirebaseRecyclerOptions.Builder<FoodItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child("Regional"),FoodItems.class)
                        .build();

        regionalAdapter=new RegionalAdapter(options,this);
        binding.recyclerview.setAdapter(regionalAdapter);

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
//                startActivity(new Intent(Regional.this, OrderActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Regional.this,CategoryActivity.class));
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        regionalAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        regionalAdapter.stopListening();
    }
}