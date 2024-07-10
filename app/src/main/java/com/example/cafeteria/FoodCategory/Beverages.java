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

import com.example.cafeteria.Adapters.BeveragesAdapter;
import com.example.cafeteria.Cartlist;
import com.example.cafeteria.CategoryActivity;
import com.example.cafeteria.DBHelper;
import com.example.cafeteria.EmptyCart;
import com.example.cafeteria.Models.FoodItems;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.R;
import com.example.cafeteria.UserProfile;
import com.example.cafeteria.databinding.ActivityBeveragesBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Beverages extends AppCompatActivity {

    ActivityBeveragesBinding binding;
    LinearLayout homebtn;
    LinearLayout Profilebtn;
    FloatingActionButton Cartbtn;
    BeveragesAdapter beveragesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityBeveragesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Beverages");

        homebtn=findViewById(R.id.homeBtn);
        Profilebtn=findViewById(R.id.ProfileBtn);
        Cartbtn=findViewById(R.id.cartBtn);
        DBHelper helper1=new DBHelper(this);

        Profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Beverages.this, UserProfile.class));

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
                            startActivity(new Intent(Beverages.this, Cartlist.class));
                        }else {
                            startActivity(new Intent(Beverages.this,EmptyCart.class));
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
                startActivity(new Intent(Beverages.this, CategoryActivity.class));

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
//        list.add(new MainModel(R.drawable.coffee,"Coffee","15","simple as it gets with ground coffee beans steeped in hot water.."));
//        list.add(new MainModel(R.drawable.coldcoffeepng1,"ColdCoffee","40","Sippin’ and chillin’…"));
//        list.add(new MainModel(R.drawable.coca_coca_750ml,"Coca Cola 750ml","40","Open happiness to “taste the feeling”"));
//        list.add(new MainModel(R.drawable.fizz750ml,"Apple Fiz 750ml","40","bubbly, sweet and tangy with a light refreshing taste."));
//        list.add(new MainModel(R.drawable.limca750ml,"Limca 750ml","40","Lime and Lemony Limca!!!!"));
//        list.add(new MainModel(R.drawable.dew750ml,"Dew 750ml","40","Quenching your thurst has never been this tasty."));
//        list.add(new MainModel(R.drawable.sprite_750ml,"Sprite 750ml","40","Beat the heat with Spriteee…"));
//        list.add(new MainModel(R.drawable.mirinda750ml,"Marinda 750ml","40","Beat the heat with Marinda…"));
//        list.add(new MainModel(R.drawable.fanta_750ml,"Fanta 750ml","40","Beat the heat with Fantaa…"));
//        list.add(new MainModel(R.drawable.lassi,"Lassi","0","Satisfaction down to the last drop."));
//        list.add(new MainModel(R.drawable.chaipng,"Tea","10","Fuel the day with “Tea”"));
//        list.add(new MainModel(R.drawable.sting,"Sting","20","Drink up !! Irresistibly refreshing…."));
//        list.add(new MainModel(R.drawable.chilled_latte,"Chilled latte","40","“ Today’s good mood id sponsored by coffee. “"));
//        list.add(new MainModel(R.drawable.chocolate_milkshake,"Chocolate milkshake","20","shake rattle and enjoy!!!!"));
//        list.add(new MainModel(R.drawable.guavava,"Guava","20","Juice that Pack A Punch!!!"));
//        list.add(new MainModel(R.drawable.hazelnut,"Hazelnut","40","Juice that Pack A Punch!!!"));
//        list.add(new MainModel(R.drawable.intense_cafe,"Intense cafe","40",""));
//        list.add(new MainModel(R.drawable.litchi,"Litchi","20","Sip the juice and chillllll."));
//        list.add(new MainModel(R.drawable.litchi_juice,"Litchi juice","20","Sip the juice and chillllll."));
//        list.add(new MainModel(R.drawable.mango,"Mango","20","Happiness inside every Sip….."));
//        list.add(new MainModel(R.drawable.mixed_fruit,"Mixed fruit","20","Sip your Sunshine!!!"));
//        list.add(new MainModel(R.drawable.orange,"Orange","20","Juice that Pack A Punch!!!"));
//        list.add(new MainModel(R.drawable.strawberry_milkshake,"Strawberry milkshake","20","Sweetness and coolness overloaded…"));
//        list.add(new MainModel(R.drawable.red_bull,"Red bull","110","Red Bull Energy Drink is appreciated worldwide by top athletes, students, and in highly demanding professions as well as during long drives."));
//        list.add(new MainModel(R.drawable.raspberry,"Raspberry","40","Taste joy after every Sip…"));
//        list.add(new MainModel(R.drawable.peach_non,"Peach non","40","Don’t blink!!! Your drink will be gone."));
//        list.add(new MainModel(R.drawable.fruit_beer,"Fruit beer","40","A drink that says ‘LETS PARTY’."));
//        list.add(new MainModel(R.drawable.coolberg,"Coolberg","40","Stressed grab a (insert beer name)!!!!"));
//        list.add(new MainModel(R.drawable.heineken,"Heineken","40","Designed to be different from every other drink."));
//        list.add(new MainModel(R.drawable.fizz1,"fizz 100ml","12",""));
//        list.add(new MainModel(R.drawable.sprite_can,"Sprite Can","40","Beat the heat with Spriteee…"));
//        list.add(new MainModel(R.drawable.coca_cola_can,"Coca cola can","40","Open happiness to “taste the feeling”"));
//        list.add(new MainModel(R.drawable.fanta_can,"Fanta can","40","Beat the heat with Fantaa…"));
//        list.add(new MainModel(R.drawable.fizz_can,"Fizz can","40","bubbly, sweet and tangy with a light refreshing taste."));
//        list.add(new MainModel(R.drawable.drinking_water,"Drinking water","20",""));
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
        binding.recyclerview.setLayoutManager(new WrapContentLinearLayoutManager(Beverages.this,2,  GridLayoutManager.VERTICAL, false));

        //Get data from firebase database
        FirebaseRecyclerOptions<FoodItems> options=
                new FirebaseRecyclerOptions.Builder<FoodItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child("Beverages"),FoodItems.class)
                        .build();

        beveragesAdapter=new BeveragesAdapter(options,this);
        binding.recyclerview.setAdapter(beveragesAdapter);

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
//                startActivity(new Intent(Beverages.this, OrderActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Beverages.this,CategoryActivity.class));
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        beveragesAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        beveragesAdapter.stopListening();
    }
}