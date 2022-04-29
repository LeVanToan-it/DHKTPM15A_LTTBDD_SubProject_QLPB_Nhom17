package com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dhktpm15a_lttbdd_subproject_qlpb_nhom17.R;
import com.google.firebase.auth.FirebaseAuth;

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
    }
}