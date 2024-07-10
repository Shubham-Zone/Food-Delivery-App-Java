package com.example.cafeteria.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeteria.DetailActivity;
import com.example.cafeteria.Models.FoodItems;
import com.example.cafeteria.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChineseAdapter extends FirebaseRecyclerAdapter<FoodItems,ChineseAdapter.MyViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChineseAdapter(@NonNull FirebaseRecyclerOptions<FoodItems> options) {
        super(options);
    }
    public ChineseAdapter(@NonNull FirebaseRecyclerOptions<FoodItems> options, Context context) {
        super(options);
        this.context=context;
    }


    @NonNull
    @Override
    public ChineseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_design_main,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull FoodItems model) {
        holder.mainName.setText(model.getFoodname());
        holder.price.setText(model.getPrice());

        Glide.with(holder.foodimage.getContext())
                .load(model.getImageurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.google.firebase.firestore.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.foodimage);

//        holder.description.setText(model.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("image",model.getImageurl());
                intent.putExtra("price",model.getPrice());
                intent.putExtra("desc",model.getDesc());
                intent.putExtra("name",model.getFoodname());
                intent.putExtra("type",1);
                context.startActivity(intent);
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView foodimage;
        TextView mainName,price,description;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodimage=itemView.findViewById(R.id.orderImage);
            mainName=itemView.findViewById(R.id.orderItemName);
            price=itemView.findViewById(R.id.price);

        }
    }
}
