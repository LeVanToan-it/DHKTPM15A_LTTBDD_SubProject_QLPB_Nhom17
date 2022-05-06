package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.model.Product;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    ListView lvShoe;
    ProductAdapter adt;
    ArrayList<Product> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        lvShoe = (ListView) findViewById(R.id.lvShoe);
        arrayList = new ArrayList<>();
        adt = new ProductAdapter(this, R.layout.list_view_shoes, arrayList);

        lvShoe.setAdapter(adt);

        // Get Data form SQlite
        Cursor cursor = UploadProductActivity.sqLiteHelper.getData("Select * from Product");
        arrayList.clear();
        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String description = cursor.getString(1);
            double price = cursor.getDouble(2);
            byte[] image = cursor.getBlob(3);

            arrayList.add(new Product(name,description,price,image));
        }
        adt.notifyDataSetChanged();
    }
}
