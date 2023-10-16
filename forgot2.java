package com.example.tripti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.concurrent.TimeUnit;

public class forgot2 extends AppCompatActivity {
    EditText e1, e2, e3, e4, e5, e6;
    TextView msg1, msg2, resend;
    Button verify;

    String d1, d2, d3, d4, d5, d6;
    String data;
    String code;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_forgot2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        resend = findViewById(R.id.resend);
        ProgressBar p = findViewById(R.id.prg);
        e1 = findViewById(R.id.edt1);
        e2 = findViewById(R.id.edt2);
        e3 = findViewById(R.id.edt3);
        e4 = findViewById(R.id.edt4);
        e5 = findViewById(R.id.edt5);
        e6 = findViewById(R.id.edt6);
        msg1 = findViewById(R.id.msg1);
        msg2 = findViewById(R.id.msg2);
        verify = findViewById(R.id.verifyotp);


        data = getIntent().getStringExtra("data");
        code = getIntent().getStringExtra("code");


        msg1.setText("OTP has been sent on the mobile no.");
        msg2.setText("( +91" + data + " )");


        verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                p.setVisibility(View.VISIBLE);
                verify.setVisibility(View.INVISIBLE);
                d1 = e1.getText().toString();
                d2 = e2.getText().toString();
                d3 = e3.getText().toString();
                d4 = e4.getText().toString();
                d5 = e5.getText().toString();
                d6 = e6.getText().toString();
                if (d1.equals("") || d2.equals("") || d3.equals("") || d4.equals("") || d5.equals("") || d6.equals("")) {
                    p.setVisibility(View.INVISIBLE);
                    verify.setVisibility(View.VISIBLE);
                    FancyToast.makeText(forgot2.this, "Fields can't be empty",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                } else {

                    String usercode = d1 + d2 + d3 + d4 + d5 + d6;

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getInstance().getCredential(code, usercode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() == true) {
                                p.setVisibility(View.INVISIBLE);
                                verify.setVisibility(View.VISIBLE);
                                startActivity(new Intent(getApplicationContext(), changepassword.class).putExtra("mobile",data));
                                finish();

                            } else {
                                FancyToast.makeText(forgot2.this, "Enter correct OTP",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                p.setVisibility(View.INVISIBLE);
                                verify.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + data, 60, TimeUnit.SECONDS, forgot2.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                FancyToast.makeText(forgot2.this, "Check your Internet Connection",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String ss, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                code = ss;
                                FancyToast.makeText(forgot2.this, "OTP sent successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                            }
                        }
                );
            }
        });


        moveData();
    }


    void moveData() {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    void moverevData() {
        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    e1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}