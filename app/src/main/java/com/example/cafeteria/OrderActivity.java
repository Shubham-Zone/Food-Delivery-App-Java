package com.example.cafeteria;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cafeteria.Adapters.OrdersAdapter;
import com.example.cafeteria.Models.OrdersModel;
import com.example.cafeteria.databinding.ActivityOrderBinding;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    SwipeRefreshLayout swipeRefresh;

    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());   
        setContentView(binding.getRoot());

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i++;
                swipeRefresh.setRefreshing(false);
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

        DBHelper helper=new DBHelper(this);
        ArrayList<OrdersModel> list=helper.getOrders();

        OrdersAdapter adapter=new OrdersAdapter(list,this);
        binding.orderRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.orderRecyclerView.setLayoutManager(layoutManager);

    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(OrderActivity.this,CategoryActivity.class));
        finish();
    }
}