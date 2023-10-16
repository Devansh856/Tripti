package com.example.tripti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.concurrent.TimeUnit;

public class forgot1 extends AppCompatActivity {
    EditText otp;
    Button sendotp;

    FirebaseAuth mauth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ProgressBar p = findViewById(R.id.pr);

        otp = findViewById(R.id.edit);
        sendotp = findViewById(R.id.sendotp);
        mauth = FirebaseAuth.getInstance();
        sendotp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                p.setVisibility(View.VISIBLE);
                sendotp.setVisibility(View.INVISIBLE);
                String data = otp.getText().toString();
                if (data.equals("")) {
                    p.setVisibility(View.INVISIBLE);
                    sendotp.setVisibility(View.VISIBLE);
                    otp.setError("Field can't be empty!");
                } else {
                    if (data.charAt(0) == '0' || data.length() != 10) {
                        otp.setError("Enter valid mobile number");
                        p.setVisibility(View.INVISIBLE);
                        sendotp.setVisibility(View.VISIBLE);
                    } else {

                        DatabaseReference db=FirebaseDatabase.getInstance().getReference("login");
                        db.child(data).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.isSuccessful() && task.getResult().exists()){
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                            "+91" + data, 60, TimeUnit.SECONDS, forgot1.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    p.setVisibility(View.INVISIBLE);
                                                    sendotp.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    p.setVisibility(View.INVISIBLE);
                                                    sendotp.setVisibility(View.VISIBLE);
                                                    FancyToast.makeText(forgot1.this, "Check your Internet Connection...", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    Intent intent = new Intent(forgot1.this, forgot2.class);
                                                    intent.putExtra("code", s);
                                                    intent.putExtra("data", data);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }
                                    );
                                }

                                else {
                                    p.setVisibility(View.INVISIBLE);
                                    sendotp.setVisibility(View.VISIBLE);
                                    FancyToast.makeText(forgot1.this,"No record found for this mobile no. ",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                    otp.setText("");
                                }
                            }
                        });



                    }
                }
            }
        });

    }

}