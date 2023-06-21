package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Product.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String productSql = "CREATE TABLE product(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT, gia TEXT, loai TEXT, sl TEXT,mau TEXT)";
        db.execSQL(productSql);

        String historySql = "CREATE TABLE history(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_user TEXT, id_product TEXT, actions TEXT, time TEXT)";
        db.execSQL(historySql);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("product", null, null, null, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String ten  = rs.getString(1);
            String gia = rs.getString(2);
            String loai = rs.getString(3);
            String soluong = rs.getString(4);
            String mau = rs.getString(5);
            list.add(new Product(id, ten, gia, loai, soluong, mau));
        }
        return list;
    }

    //add product
    public long addProduct(Product i) {
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("gia", i.getGia());
        values.put("loai", i.getLoai());
        values.put("sl", i.getSl());
        values.put("mau", i.getMau());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("product", null, values);
    }
    //
    public Product getProductById(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor cursor = sqLiteDatabase.query("product",
                null, whereClause, whereArgs,
                null, null, null);
        if (cursor!= null && cursor.moveToFirst()) {
            String productName = cursor.getString(1);
            String productPrice = cursor.getString(2);
            String productType = cursor.getString(3);
            String productQuantity = cursor.getString(4);
            String productColor = cursor.getString(5);
            cursor.close();
            return new Product(productName, productPrice, productType, productQuantity, productColor);
        }
        return null;
    }


    //update product
    public int updateProduct(Product i) {
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("gia", i.getGia());
        values.put("loai", i.getLoai());
        values.put("sl", i.getSl());
        values.put("mau", i.getMau());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("product",
                values, whereClause, whereArgs);
    }
    // delete product
    public int deleteProduct(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("product",
                whereClause, whereArgs);
    }
    // searchByTen
    public List<Product> searchByTen(String key) {
        List<Product> list= new ArrayList<>();
        String whereClause = "ten like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("product",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String productName = rs.getString(1);
            String productPrice = rs.getString(2);
            String productType = rs.getString(3);
            String productQuantity = rs.getString(4);
            String productColor = rs.getString(5);
            list.add(new Product(id,productName, productPrice, productType, productQuantity, productColor));
        }
        return list;
    }
    //searchbyLoai
    public List<Product> searchByLoai(String key) {
        List<Product> list = new ArrayList<>();
        String whereClause = "loai like ?";
        String[] whereArgs = {key};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("product",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String productName = rs.getString(1);
            String productPrice = rs.getString(2);
            String productType = rs.getString(3);
            String productQuantity = rs.getString(4);
            String productColor = rs.getString(5);
            list.add(new Product(id, productName, productPrice, productType, productQuantity, productColor));
        }
        return list;
    }
    // add History
    public long addHistory(History i) {
        ContentValues values = new ContentValues();
        values.put("id_user", i.getId_user());
        values.put("id_product", i.getId_product());
        values.put("actions", i.getActions());
        values.put("time", i.getTime());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("history", null, values);
    }
    public List<History> getAllHistory() {
        List<History> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("history", null, null, null, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String id_user  = rs.getString(1);
            String id_product = rs.getString(2);
            String actions = rs.getString(3);
            String time = rs.getString(4);

            list.add(new History(id, Integer.parseInt(id_product), id_user,actions,time));
        }
        return list;
    }
}
