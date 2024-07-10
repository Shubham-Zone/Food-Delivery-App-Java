package com.example.cafeteria.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeteria.Models.Usercart;
import com.example.cafeteria.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cartadapter extends FirebaseRecyclerAdapter<Usercart,Cartadapter.MyviewHolder> {

    private Context mcontext;
    TextView subtotal,totalRs;
    DatabaseReference database;
    DatabaseReference fees;
    ArrayList<Usercart> l5 = new ArrayList<>();

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Cartadapter(@NonNull FirebaseRecyclerOptions<Usercart> options) {
        super(options);
    }


    public Cartadapter(@NonNull FirebaseRecyclerOptions<Usercart> options,Context mcontext, TextView subtotal, TextView totalRs) {
        super(options);
        this.mcontext=mcontext;
        this.subtotal=subtotal;
        this.totalRs=totalRs;
    }



    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, int position, @NonNull Usercart model) {

        ArrayList<Usercart> l1=new ArrayList<>();
        l1.add(model);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference myref=db.getReference("User").child(model.getToken()).child("cart");


//        int sum=5;
//        int Subtotal=0;
//        for(int i=0;i<l1.size();i++){
//            sum=sum+(Integer.parseInt(l1.get(i).getQuantity()) * Integer.parseInt(l1.get(i).getPrice()));
//            Subtotal=Subtotal+(Integer.parseInt(l1.get(i).getQuantity()) * Integer.parseInt(l1.get(i).getPrice()));
//        }
//        subtotal.setText("Rs"+Subtotal);
//        totalRs.setText("Rs"+sum);


        Glide.with(holder.OrderImage.getContext())
                .load(model.getOrderImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.google.firebase.firestore.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.OrderImage);
        holder.soldItemName.setText(model.getSoldItemName());
        holder.quantity.setText(model.getQuantity());
        holder.price.setText(model.getPrice());

//        holder.addlocbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!l1.isEmpty()) {
//                    mcontext.startActivity(new Intent(mcontext, LocationActivity.class));
//                }else {
//                    Toast.makeText(mcontext, "Cart is Empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        holder.cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database= FirebaseDatabase.getInstance().getReference("User");
                database.child(model.getToken()).child("cart").child(model.getRoll()).removeValue();
//                int pos=holder.getAbsoluteAdapterPosition();
//                l1.remove(pos);
//                updateprice();
            }




//            private void updateprice() {
////                ArrayList<Usercart> l2=new ArrayList<>();
////                l2.add(model);
//                int sum=5;
//                int Subtotal=0;
//                for(int i=0;i<l2.size();i++){
//                    sum=sum+(Integer.parseInt(l2.get(i).getQuantity()) * Integer.parseInt(l2.get(i).getPrice()));
//                    Subtotal=Subtotal+(Integer.parseInt(l2.get(i).getQuantity()) * Integer.parseInt(l2.get(i).getPrice()));
//                }
//                subtotal.setText("Rs"+Subtotal);
//                totalRs.setText("Rs"+sum);
//
//            }

        });

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Usercart> l2=new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Roll roll =dataSnapshot.getValue(Roll.class);
                    Usercart usercart = dataSnapshot.getValue(Usercart.class);
                    assert usercart != null;
                    l2.add(usercart);
                    getlist(l2);

                }

                if(l2.isEmpty()){
                    subtotal.setText("0");
                    totalRs.setText("0");
                }

                //get delivery fee
                fees=FirebaseDatabase.getInstance().getReference("Deliveryfee").child("fee");
                fees.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String fee;
                            fee = snapshot.getValue(String.class);
                            int sum = Integer.parseInt(fee);
                            int Subtotal = 0;

                            for (int i = 0; i < l5.size(); i++) {
                                sum = sum + (Integer.parseInt(l5.get(i).getQuantity()) * Integer.parseInt(l5.get(i).getPrice()));
                                Subtotal = Subtotal + (Integer.parseInt(l5.get(i).getQuantity()) * Integer.parseInt(l5.get(i).getPrice()));
                            }

                            subtotal.setText("Rs"+Subtotal);
                            totalRs.setText("Rs"+sum);

                        if(l5.isEmpty()){
                            subtotal.setText("0");
                            totalRs.setText("0");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mcontext, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void getlist(ArrayList<Usercart> l2) {
        l5=l2;
    }
    


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist,parent,false);
        return new MyviewHolder(view);
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView OrderImage;
        TextView soldItemName,quantity,price;
        ImageView cancelbtn;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            OrderImage=itemView.findViewById(R.id.orderImage);
            soldItemName=itemView.findViewById(R.id.orderItemName);
            quantity=itemView.findViewById(R.id.quantitytxt);
            price=itemView.findViewById(R.id.orderprice);
            cancelbtn=itemView.findViewById(R.id.trashbtn);
//            Subtotal1=itemView.findViewById(R.id.subtotal);
//            TotalRs1=itemView.findViewById(R.id.Totalrs);
//            ordernumber=itemView.findViewById(R.id.onotxt);
        }
    }
}
