package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditProductActivity extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 1;
    final int REQUEST_CODE_GALLERY = 999;

    SQLiteHelper sqLiteHelper = new SQLiteHelper(this,"product.sqlite",null,1);

    @BindView(R.id.edtNameEdit)
    EditText mEtNameEdit;
    @BindView(R.id.edtDescriptionEdit)
    EditText mEtDescriptionEdit;
    @BindView(R.id.edtPriceEdit)
    EditText mEtPriceEdit;
    @BindView(R.id.imgProductEdit)
    ImageView mImgProductEdit;
    @BindView(R.id.btnUpdate)
    Button mBtnUpdate;
    @BindView(R.id.btnCancel)
    Button mBtnCancel;
    @BindView(R.id.btnChooseEdit)
    Button btnChooseEdit;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_activity);
        ButterKnife.bind(this);



        //Product product = sqLiteHelper.getData();

        //Button btnChooseEdit = findViewById(R.id.btnChooseEdit);

        btnChooseEdit.setOnClickListener((v) -> {
            Intent intent = new Intent();
            // show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //Alway show chooser
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

//        getDataInEdt(mainMenuActivity.idEdt);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProductActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProductActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You dont have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        ImageView imgProductEdit = findViewById(R.id.imgProductEdit);
        EditText edtNameEdit = findViewById(R.id.edtNameEdit);

        if (requestCode == 0) {
            String stringFilePath = Environment.getExternalStorageDirectory().getPath()+edtNameEdit.getText().toString()+".png";

            Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);

            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
            imgProductEdit.setImageBitmap(resizeBitmap);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                imgProductEdit.setImageBitmap(resizeBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getDataInEdt(int i){
//        mBtnUpdate = findViewById(R.id.btnUpdate);
//        mEtNameEdit= findViewById(R.id.edtNameEdit);
//        mEtDescriptionEdit = findViewById(R.id.edtDescriptionEdit);
//        mEtPriceEdit = findViewById(R.id.edtPriceEdit);
//        mImgProductEdit = findViewById(R.id.imgProductEdit);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    UploadProductActivity.sqLiteHelper.EditData(mEtNameEdit.getText().toString().trim(),
                            mEtDescriptionEdit.getText().toString().trim(),
                            Double.parseDouble(mEtPriceEdit.getText().toString()),
                            UploadProductActivity.imageProductToByte(mImgProductEdit), i);
                    Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                    mEtNameEdit.setText("");
                    mEtDescriptionEdit.setText("");
                    mEtPriceEdit.setText("");
                    mImgProductEdit.setImageResource(R.drawable.rectangle);
                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
