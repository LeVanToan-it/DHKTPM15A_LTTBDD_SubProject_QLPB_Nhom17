package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, String description, Double price, byte[] image){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("image", image);

        //database.insert("product",null, values);

        String sql = "INSERT INTO Product VALUES (NULL,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, description);
        statement.bindDouble(3, price);
        statement.bindBlob(4, image);

        statement.executeInsert();
    }

    public void EditData(String name, String description, double price, byte[] image, int id){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("image", image);

//        database.update("product", values, "name=?", new String[]{});
        String sql = "update product set name =?, description=?, price= ?, image=? where id =?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2,description);
        statement.bindDouble(3, price);
        statement.bindBlob(4,image);
        statement.bindDouble(5, (double)id);

        statement.execute();
        database.close();
    }

    public void DeleteData(int id){
        SQLiteDatabase database = getWritableDatabase();

        String sql = "delete from product where id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindDouble(0, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("create table product(name VARCHAR primary key, description VARCHAR, price VARCHAR, image BLOG)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("drop table if exists product");
    }
}
