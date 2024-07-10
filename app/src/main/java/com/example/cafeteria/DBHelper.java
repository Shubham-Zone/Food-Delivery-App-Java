package com.example.cafeteria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cafeteria.Models.OrdersModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    final static String DBNAME="mydatabase.db";
    final static int DBVERSION=2;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table orders"+
                        "(id integer primary key autoincrement,"+
                        "name text,"+
                        "phone text,"+
                        "price int,"+
                        "image int,"+
                        "quantity int,"+
                        "description text,"+
                        "foodname text)"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("DROP table if exists orders");
          onCreate(db);
    }


    public boolean insertOrder(String name,String phone,int price,int image,String desc,String foodname,int quantity){
        SQLiteDatabase database=getReadableDatabase();
        ContentValues values=new ContentValues();
        /*
        id=0
        name=1
        phone=2
        price=3
        image=4
        desc=5
        foodname=6
        quantity=7
         */
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",desc);
        values.put("foodname",foodname);
        values.put("quantity",quantity);


        long id=database.insert("orders",null,values);
        if(id<=0){  //if no value is inserted in database
            return false;
        }else{
            return true;
        }


    }

    //get items in cart
    public ArrayList<OrdersModel> getOrders(){
        ArrayList<OrdersModel> orders =new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("Select id,foodname,image,price from orders",null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                OrdersModel model=new OrdersModel();
                model.setOrderNumber(cursor.getInt(0)+"");
                model.setSoldItemName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(2));
                model.setPrice(cursor.getInt(3)+"");
                orders.add(model);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }


    //get values for orderholder
    public ArrayList<Cartmodel> getmyorders(){
        ArrayList<Cartmodel> myorders =new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("Select id,foodname,image,price,quantity from orders",null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()) {
                Cartmodel cart=new Cartmodel();
                cart.setOrderNumber(cursor.getInt(0)+"");
                cart.setSoldItemName(cursor.getString(1));
                cart.setOrderImage(cursor.getInt(2));
                cart.setPrice(cursor.getInt(3)+"");
                cart.setQuantity(cursor.getInt(4)+"");
                myorders.add(cart);
            }
        }
        cursor.close();
        database.close();
        return myorders;
    }

    public Cursor getOrderById(int id){
        ArrayList<OrdersModel> orders =new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("Select * from orders where id="+id,null);

        if(cursor!=null)
            cursor.moveToFirst();
        return cursor;
    }

    public boolean updateOrder(String name,String phone,int price,int image,String desc,String foodname,int quantity,int id){
        SQLiteDatabase database=getReadableDatabase();
        ContentValues values=new ContentValues();
        /*
        id=0
        name=1
        phone=2
        price=3
        image=4
        desc=5
        foodname=6
        quantity=7
         */
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",desc);
        values.put("foodname",foodname);
        values.put("quantity",quantity);
        values.put("id",id);

        long row=database.update("orders",values,"id="+id,null);
        if(row<=0){  //if no value is inserted in database
            return false;
        }else{
            return true;
        }
    }

    public int deleteOrder(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete("orders","id="+id,null);
    }


    
}
