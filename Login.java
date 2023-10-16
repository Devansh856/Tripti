package com.example.tripti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity {
    TextView newuser, forget;
    EditText email, pass;


    Button login;
    ProgressBar progressBar;
    public static final String flag = "devansh";
    public static final String PREF_NAME = "Myrefer";
    SharedPreferences sharedPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences s = getSharedPreferences(flag, 0);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        SharedPreferences.Editor ed = s.edit();
        ed.putBoolean("haslog", true);
        ed.commit();
        if (sharedPreferences.getString("name", null) != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        progressBar = findViewById(R.id.pro);
        login = findViewById(R.id.login);
        newuser = findViewById(R.id.newuser);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pas);
        forget = findViewById(R.id.forgot);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, forgot1.class));

            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, Signup.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finishAndRemoveTask();
                finish();
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                process();
            }
        });


    }

    void process() {

        progressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.INVISIBLE);
        String ema, pas;
        ema = email.getText().toString();
        pas = pass.getText().toString();
        if (ema.equals("") || pas.equals("")) {
            progressBar.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            FancyToast.makeText(this, "Field can't be empty", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false);
        } else {

            String em = ema.substring(0, ema.length() - 10);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("login");

            ref.child(em).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot sp = task.getResult();
                            String password = String.valueOf(sp.child("password").getValue());
                            String name = String.valueOf(sp.child("name").getValue());
                            String email = String.valueOf(sp.child("email").getValue());
                            if (password.equals(pas) && email.equalsIgnoreCase(ema)) {
                                SharedPreferences.Editor e = sharedPreferences.edit();
                                e.putString("name", name);
                                e.putString("email", email);
                                e.apply();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else
                                FancyToast.makeText(Login.this, "Invalid Credentials", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        } else
                            FancyToast.makeText(Login.this, "Invalid Credentials", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                    } else
                        FancyToast.makeText(Login.this, "Invalid Credentials", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    login.setVisibility(View.VISIBLE);
                }

            });
        }
    }


}