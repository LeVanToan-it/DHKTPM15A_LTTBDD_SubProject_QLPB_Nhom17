package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.tvUploadAvatar)
    TextView mTvUploadAvatar;
    @BindView(R.id.ivAvatar)
    ImageView mIvAvatar;
    @BindView(R.id.edtName)
    EditText mEdtName;
    @BindView(R.id.edtGmail)
    EditText mEdtGmail;
    @BindView(R.id.edtPassword)
    EditText mEdtPassword;
    @BindView(R.id.btnSignup)
    Button mBtnSignup;
    @BindView(R.id.tvSignin)
    TextView mTvSignin;

    private File imageFile;
    private int PICK_IMAGE_REQUEST = 1;

    // Authentication
    private FirebaseAuth mAuth;
    // Storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference
    final StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mTvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        mTvUploadAvatar.setOnClickListener((v) -> {
            Intent intent = new Intent();
            // show only images, no videos or anything else
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //Alway show chooser
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        mBtnSignup.setOnClickListener((v) -> {
            if (mEdtName.getText().length() == 0) {
                mEdtName.setError("Please enter your name!");
            } else if (mEdtGmail.getText().length() == 0) {
                mEdtGmail.setError("Please enter your email!");
            } else if (mEdtPassword.getText().length() == 0) {
                mEdtPassword.setError("Please enter your password!");
            } else {
                signUp();
            }
        });
    }

    private void signUp() {
        final String email = mEdtGmail.getText().toString();
        String password = mEdtPassword.getText().toString();
        final String name = mEdtName.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (task) -> {
                    if (task.isSuccessful()) {
                        // Upload image and get link
                        Calendar calendar = Calendar.getInstance();

                        // create a reference to avt.jpg
                        StorageReference avtRef = storageReference.child(mEdtGmail.getText().toString() + ".png");

                        // Create a reference to images/avt.jpg
                        StorageReference avtImagesRef = storageReference.child("images");

                        // While the file names are the same, the references point to different files
                        avtRef.getName().equals(avtImagesRef.getName());
                        avtRef.getPath().equals(avtImagesRef.getPath());

                        // Get data from an ImageView as bytes
                        mIvAvatar.setDrawingCacheEnabled(true);
                        mIvAvatar.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) mIvAvatar.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = avtRef.putBytes(data);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@android.support.annotation.NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Toast.makeText(SignupActivity.this, "Error! Hey you", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String photoLink = uri.toString();
                                        //Toast.makeText(SignUpActivity.this, photoLink, Toast.LENGTH_SHORT).show();
                                        //we will store the additional fields in firebase database
                                        User user = new User(
                                                name, photoLink, email
                                        );
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignupActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                                    mEdtName.setText("");
                                                    mEdtName.requestFocus();
                                                    mEdtGmail.setText("");
                                                    mEdtPassword.setText("");
                                                    startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                                                } else {
                                                    Toast.makeText(SignupActivity.this, "Failure! please check internet connection!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });

                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                //Toast.makeText(SignUpActivity.this, "Successfully Upload Im!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(SignupActivity.this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");//image bitmap file
            Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 680, 500, false);
            mIvAvatar.setImageBitmap(resizeBitmap);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, 680, 500, false);
                mIvAvatar.setImageBitmap(resizeBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

