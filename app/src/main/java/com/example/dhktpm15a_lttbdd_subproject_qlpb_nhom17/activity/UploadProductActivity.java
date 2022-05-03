package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UploadProductActivity extends AppCompatActivity {

    EditText edtName, edtDescription, edtPrice;
    Button btnSave, btnCancel, btnChoose;
    ImageView imgProductAdd;

    final int REQUEST_CODE_GALLERY = 999;
    final int PICK_IMAGE_REQUEST = 1;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_activity);

        init();

        sqLiteHelper = new SQLiteHelper(this, "product.sqlite",null ,1);
        sqLiteHelper.queryData("CREATE TABLE product (name VARCHAR, description VARCHAR, price VARCHAR, image BLOG)");

        btnChoose.setOnClickListener((v) -> {
            Intent intent = new Intent();
            // show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //Alway show chooser
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    sqLiteHelper.insertData(edtName.getText().toString().trim(),
                            edtDescription.getText().toString().trim(),
                            Double.parseDouble(edtPrice.getText().toString()),
                            imageProductToByte(imgProductAdd));
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtDescription.setText("");
                    edtPrice.setText("");
                    imgProductAdd.setImageResource(R.drawable.rectangle);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private byte[] imageProductToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
//        if(requestCode == REQUEST_CODE_GALLERY && requestCode == RESULT_OK && data!=null){
//            Uri uri = data.getData();
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                imgProductAdd.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e){
//                e.printStackTrace();
//            }
//
//        }
        if (requestCode == 0) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");//image bitmap file
            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
            imgProductAdd.setImageBitmap(resizeBitmap);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                imgProductAdd.setImageBitmap(resizeBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){
        edtName =findViewById(R.id.edtName);
        edtDescription =findViewById(R.id.edtDescription);
        edtPrice =findViewById(R.id.edtPrice);
        btnSave =findViewById(R.id.btnSave);
        btnCancel =findViewById(R.id.btnCancel);
        btnChoose =findViewById(R.id.btn_choose);
        imgProductAdd =findViewById(R.id.imgProductAdd);
    }
}
