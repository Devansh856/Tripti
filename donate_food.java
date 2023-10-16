package com.example.tripti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class donate_food extends AppCompatActivity {

    EditText name, mob, fooditem, qty, address;
    Button donbutton;
    ProgressBar bar;
    TextView pack;
    CardView c;
    String totaldonations, email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bar = findViewById(R.id.donpro);
        c = findViewById(R.id.car);
        name = findViewById(R.id.donname);

        mob = findViewById(R.id.donnumber);
        fooditem = findViewById(R.id.donitem);
        qty = findViewById(R.id.donqty);
        address = findViewById(R.id.donaddress);
        donbutton = findViewById(R.id.donbutton);
        pack = findViewById(R.id.packag);
        String id = "Package Id : TR" + String.valueOf((int) Math.floor(Math.random() * (999999 - 100000 - 1) + 100000));
        pack.setText(id);
        totaldonations = getIntent().getStringExtra("total");
        email=getIntent().getStringExtra("email");

        donbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                donbutton.setVisibility(View.INVISIBLE);


                process();
            }
        });


    }

    void process() {
        String dname, dmob, ditem, dqty, dadd, packid;
        dname = name.getText().toString();
        dmob = mob.getText().toString();
        ditem = fooditem.getText().toString();  // Package Id : TR100000
        dqty = qty.getText().toString();
        dadd = address.getText().toString();
        packid = pack.getText().toString();
        String p = packid.substring(13);

        if (dname.equals("") || dmob.equals("") || ditem.equals("") || dqty.equals("") || dadd.equals("")) {
            FancyToast.makeText(this, "Fields can't be empty!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            bar.setVisibility(View.INVISIBLE);
            donbutton.setVisibility(View.VISIBLE);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dat = new Date();

            String date=formatter.format(dat);
            donmodel model = new donmodel(dname, dmob, ditem, dqty, dadd,date);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("donatefood");
            databaseReference.child(p).setValue(model);
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("donatefood1");
            databaseReference1.child(p).setValue(model);
            String em=email.substring(0,email.length()-10);
            DatabaseReference db=FirebaseDatabase.getInstance().getReference("donatefood");
            db.child(em).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful() && task.getResult().exists()){
                        DataSnapshot sp=task.getResult();
                        String t=String.valueOf(sp.child("total").getValue());
                        String c=String.valueOf(sp.child("Count").getValue());
                        int count=Integer.parseInt(c);
                        int counttotal=Integer.parseInt(t);
                       count+=1;
                        counttotal+=Integer.parseInt(dqty);
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("Count",String.valueOf(count));

                        map.put("total",String.valueOf(counttotal));

                        db.child(em).updateChildren(map);


                    }
                    else {
                        HashMap<String,String> map=new HashMap<>();
                        map.put("Count","1");
                        map.put("total",dqty);
                        db.child(em).setValue(map);

                    }
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    c.setVisibility(View.INVISIBLE);
                    AlertDialog al = new AlertDialog.Builder(donate_food.this).setTitle("Food Donation").setIcon(R.mipmap.splash1).setMessage("Thank you for donating the food.\n You will be notified when someone picks the food!")
                            .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(donate_food.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finishAndRemoveTask();
                                    finish();
                                    startActivity(intent);
                                }
                            }).show();
                }
            },1500) ;



        }
    }
}