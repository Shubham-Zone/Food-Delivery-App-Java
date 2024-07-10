package com.example.cafeteria;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.databinding.ActivityDetailBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    ImageView plus,minus;
    TextView quantity;
    int numberOrder=1;
    TextView orderNo;
    DatabaseReference database;
    EditText nameBox,phoneBox;
    ArrayList list=new ArrayList<>();


    //code for unique order no
//    int Ono;
//    int countingitems(){
//        database= FirebaseDatabase.getInstance().getReference("orders");
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String x="";
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    Roll roll=dataSnapshot.getValue(Roll.class);
//                    list.add(roll.getRoll());
//                }
//                x=(String) list.get(list.size()-1);
//                if(Integer.parseInt(x)+1==1) {
//                Ono=2;
//                }
//                else
//                Ono=Integer.parseInt(x)+1;
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//      return Ono;
//    }


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
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        plus=findViewById(R.id.plusBtn);
        minus=findViewById(R.id.MinusBtn);
        quantity=findViewById(R.id.quantity);
        orderNo=findViewById(R.id.ordernumber);
        getSupportActionBar().hide();


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder=numberOrder+1;
                quantity.setText(String.valueOf(numberOrder));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder>1)
                    numberOrder=numberOrder-1;
                quantity.setText(String.valueOf(numberOrder));
            }
        });



        //code to change the color of status bar
        if(Build.VERSION.SDK_INT>=21){
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
//        //define ActionBar object
//        ActionBar actionBar;
//        actionBar =getSupportActionBar();
//        /* Define ColorDrawable object and parse color
//         * using parseColor method
//         * with color hash code as its parameter*/
//        ColorDrawable colorDrawable
//                =new ColorDrawable(Color.parseColor("#FF4500"));
//
//        //set background
//        actionBar.setBackgroundDrawable(colorDrawable);



        //Main code
        final DBHelper helper = new DBHelper(this);
        if(getIntent().getIntExtra("type",0)==1) {

            final String image = getIntent().getStringExtra("image");
            final int price = Integer.parseInt(getIntent().getStringExtra("price"));
            final String name = getIntent().getStringExtra("name");
            final String description = getIntent().getStringExtra("desc");

//            binding.detailImage.setImageResource(image);

            Glide.with(binding.detailImage.getContext())
                    .load(image)
                    .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                    .error(com.google.firebase.firestore.R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(binding.detailImage);

            binding.priceLbl.setText(String.format("%d", price));
            binding.foodname.setText(name);
            binding.detailDescription.setText(description);



        }else{

            int id=getIntent().getIntExtra("id",0);
            Cursor cursor=helper.getOrderById(id);

            int image=cursor.getInt(4);

//            binding.detailImage.setImageResource(image);
            Glide.with(binding.detailImage.getContext())
                    .load(image)
                    .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                    .error(com.google.firebase.firestore.R.drawable.common_google_signin_btn_icon_dark_normal)
                    .into(binding.detailImage);

            binding.priceLbl.setText(String.format("%d", cursor.getInt(3)));
            binding.detailDescription.setText(cursor.getString(5));
            binding.foodname.setText(cursor.getString(6));
            binding.quantity.setText(String.format("%d", cursor.getInt(7)));
            binding.insertBtn.setVisibility(View.GONE);
        }
    }


    public void process(View view) {

        final String image = getIntent().getStringExtra("image");
        final int price = Integer.parseInt(getIntent().getStringExtra("price"));
        final String name = getIntent().getStringExtra("name");
        final String description = getIntent().getStringExtra("desc");


        final DBHelper helper = new DBHelper(this);
//        int roll = countingitems();
        int roll=random(1000,2000);
//        String roll1 = Integer.toString(roll);
        String Foodname = binding.foodname.getText().toString();
        String Price=binding.priceLbl.getText().toString();
        String Quant = String.valueOf(numberOrder);

        final String foodquantity=binding.quantity.getText().toString();


        SharedPreferences getShared = getSharedPreferences("token", MODE_PRIVATE);
        String pString = getShared.getString("myToken", "");


        FirebaseDatabase DB = FirebaseDatabase.getInstance();
        DatabaseReference Userdata = DB.getReference("User").child(pString).child("cart").child(String.valueOf(roll));

         Usercart usercart=new Usercart(image,String.valueOf(price),foodquantity,name,String.valueOf(roll),pString);
         Userdata.setValue(usercart);


            boolean isInserted = helper.insertOrder(
                    "xyz",
                    "zyx",
                    price,
                    2345,
                    description,
                    name,
                    Integer.parseInt(foodquantity)
            );

            if (isInserted) {
                Toast.makeText(DetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
    }

}

