package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SigninActivity extends AppCompatActivity {

    private static final String TAG = "SigninActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.edtGmail)
    EditText mEdtGmail;
    @BindView(R.id.edtPassword)
    EditText mEdtPassword;
    @BindView(R.id.btnSignin)
    Button mBtnSignin;
    @BindView(R.id.tvSignup)
    TextView mTvSignup;

    // Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEdtGmail.getText().length()==0)
                {
                    mEdtGmail.setError("Please enter your email!");
                }
                else if(mEdtPassword.getText().length()==0)
                {
                    mEdtPassword.setError("Please enter your password!");
                }
                else {
                    signIn();
                }
            }
        });
    }

    public void signIn(){
        String email = mEdtGmail.getText().toString();
        String password = mEdtPassword.getText().toString();
        final Intent intentUser = new Intent(SigninActivity.this, MainMenuActivity.class);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, (task) -> {
                   if (task.isSuccessful()){
                       final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                               .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                       ref.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               startActivity(intentUser);
                           }
                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                       Toast.makeText(SigninActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(SigninActivity.this, MainMenuActivity.class));
                   }else{
                       Toast.makeText(SigninActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                   }
                });
    }
}