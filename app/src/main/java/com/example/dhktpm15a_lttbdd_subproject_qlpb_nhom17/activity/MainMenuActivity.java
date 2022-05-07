package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.model.Product;

import java.util.ArrayList;

import butterknife.BindView;

public class MainMenuActivity extends AppCompatActivity {

    Button btnAdd;
    Button  btnEdit;
    Button btnDelete;
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
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            double price = cursor.getDouble(3);
            byte[] image = cursor.getBlob(4);

            arrayList.add(new Product(name,description,price,image));
        }
        adt.notifyDataSetChanged();

        lvShoe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence[] item = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenuActivity.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            Cursor c = UploadProductActivity.sqLiteHelper.getData("select id from product");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            Intent intent = new Intent(MainMenuActivity.this, EditProductActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Delete...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, UploadProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
