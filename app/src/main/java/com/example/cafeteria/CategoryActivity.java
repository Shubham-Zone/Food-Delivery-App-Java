package com.example.cafeteria;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteria.Adapters.MenuAdapter;
import com.example.cafeteria.Adapters.PopularAdapter;
import com.example.cafeteria.FoodCategory.SouthIndian;
import com.example.cafeteria.Models.FoodItems;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.databinding.ActivityCategoryBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ConstraintLayout southindian;
    ConstraintLayout Chinese;
    ConstraintLayout Regional;
    ConstraintLayout Beverages;
    ConstraintLayout Sweets;
    ConstraintLayout snacks;
    LinearLayout homebtn;
    LinearLayout Profilebtn;
    FloatingActionButton Cartbtn;
    ActivityCategoryBinding binding;
    PopularAdapter popularAdapter;
    MenuAdapter menuAdapter;
    ArrayList<Usercart> l3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        southindian=findViewById(R.id.SouthIndian);
        Chinese=findViewById(R.id.chinese);
        Regional=findViewById(R.id.Regional);
        Beverages=findViewById(R.id.Beverages);
        Sweets=findViewById(R.id.Sweets);
        homebtn=findViewById(R.id.homeBtn);
        Profilebtn=findViewById(R.id.ProfileBtn);
        Cartbtn=findViewById(R.id.cartBtn);
        snacks=findViewById(R.id.snacks);
        DBHelper helper1=new DBHelper(this);

        //permission for notification
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(CategoryActivity.this, "Runtime permission granted ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }) .check();

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
                            startActivity(new Intent(CategoryActivity.this, Cartlist.class));
                        }else {
                            startActivity(new Intent(CategoryActivity.this,EmptyCart.class));
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        Profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this,UserProfile.class));

            }
        });



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

        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        southindian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, SouthIndian.class));
            }
        });


        Chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this,MainActivity.class));
            }
        });

        Regional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, Regional.class));
            }
        });

        Beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, com.example.cafeteria.FoodCategory.Beverages.class));
            }
        });

        Sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, com.example.cafeteria.FoodCategory.Sweets.class));
            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this,Snacks.class));
            }
        });



        //popular adapter

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

//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);

//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.popularrecyclerview.setLayoutManager(new WrapContentLinearLayoutManager(CategoryActivity.this, 1, GridLayoutManager.HORIZONTAL, false));

        //Get data from firebase database
        FirebaseRecyclerOptions<FoodItems> options=
                new FirebaseRecyclerOptions.Builder<FoodItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child("Popular"),FoodItems.class)
                        .build();

        popularAdapter=new PopularAdapter(options,this);
        binding.popularrecyclerview.setAdapter(popularAdapter);


        //Menu Adapter

        class WrapContentLinearLayoutManager1 extends GridLayoutManager {

            public WrapContentLinearLayoutManager1(Context context, int spanCount, int orientation, boolean reverseLayout) {
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

//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);

//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.menurecyclerviews.setLayoutManager(new WrapContentLinearLayoutManager1(CategoryActivity.this,3,  GridLayoutManager.VERTICAL, false));

        //Get data from firebase database
        FirebaseRecyclerOptions<FoodItems> options1=
                new FirebaseRecyclerOptions.Builder<FoodItems>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Food").child("Menu"),FoodItems.class)
                        .build();

        menuAdapter=new MenuAdapter(options1,this);
        binding.menurecyclerviews.setAdapter(menuAdapter);


    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent a=new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onStart(){
        super.onStart();
        popularAdapter.startListening();
        menuAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        popularAdapter.stopListening();
        menuAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.orders:
            startActivity(new Intent(CategoryActivity.this,Order_Status.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}