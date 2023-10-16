package com.example.tripti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class changepassword extends AppCompatActivity {
    EditText old, confirm;
    Button change;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        old = findViewById(R.id.oldpass);
        confirm = findViewById(R.id.confpass);
        change = findViewById(R.id.change);
        final ProgressBar progressBar = findViewById(R.id.progr);
        // String mobile = getIntent().getStringExtra("mobile");
        String mobile = "8279745113";


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                change.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);


                String oldpassword = old.getText().toString();
                String confirmm = confirm.getText().toString();
                String email, name;
                if (Signup.pwdvalidation(oldpassword) == true && Signup.pwdvalidation(confirmm) == true) {

                    if (oldpassword.equals(confirmm)) {
                        db = FirebaseDatabase.getInstance().getReference("login");


                        db.child(mobile).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        DataSnapshot snapshot = task.getResult();
                                        String email = String.valueOf(snapshot.child("email").getValue());
                                        String name = String.valueOf(snapshot.child("name").getValue());
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("name", name);
                                        map.put("email", email);
                                        map.put("mobile", mobile);
                                        map.put("password", confirmm);

                                        if (!map.isEmpty()) {

                                            String em = email.substring(0, email.length() - 10);


                                            db.child(mobile).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        db.child(em).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful()) {
                                                                    change.setVisibility(View.VISIBLE);
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    FancyToast.makeText(changepassword.this,"Password Changed Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                                                    Intent intent=new Intent(changepassword.this, Login.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    finishAndRemoveTask();
                                                                    finish();
                                                                    startActivity(intent);
                                                                } else {
                                                                    change.setVisibility(View.VISIBLE);
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    FancyToast.makeText(changepassword.this, "Some error occurred",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                                                }
                                                            }
                                                        });

                                                    } else {
                                                        change.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        FancyToast.makeText(changepassword.this,
                                                                "Some error occurred",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                                    }
                                                }

                                            });
                                        }

                                    }
                                }
                            }
                        });
                    } else {
                        change.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        confirm.setError("Password doesn't match");
                    }

                } else {
                    change.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    old.setError("Password must be greater than 6 digits,it must be start from capital letter and contains atleast one special character and a number ");
                }


            }
        });


    }
}