package com.example.tripti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.CoreComponentFactory;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String f1 = "dev";
    Button log;

    View view;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolba;


    String email, name;
    TextView headername, headeremail, totaldonations, kilos;
    CardView donat, receiv, history, donatemoney,news;


    SharedPreferences sh,sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        toolba = findViewById(R.id.toolbar);
        donat = findViewById(R.id.donatefood);
        receiv = findViewById(R.id.receivefood);
        donatemoney = findViewById(R.id.donateus);
        news=findViewById(R.id.news);
        totaldonations = findViewById(R.id.totaltext);
        kilos = findViewById(R.id.kilotext);
        history = findViewById(R.id.history);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_bar);
        view = navigationView.getHeaderView(0);
        headeremail = view.findViewById(R.id.header_email);
        headername = view.findViewById(R.id.header_name);
        toolba.post(new Runnable() {
            @Override
            public void run() {
                Drawable r = ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_segment_24, null);
                toolba.setNavigationIcon(r);
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences=getSharedPreferences(Login.flag,MODE_PRIVATE);

      sh=getSharedPreferences(Login.PREF_NAME,MODE_PRIVATE);
      String name=sh.getString("name",null);
      String email=sh.getString("email",null);
      if(name!=null || email!=null){
          headeremail.setText(email);
          headername.setText(name);
      }


        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolba, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        getDetails();


        donat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, donate_food.class);

                //intent.putExtra("total",total);
                intent.putExtra("email", headeremail.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        receiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, receive_food.class);
                intent.putExtra("email", headeremail.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, history.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        donatemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, donate_money.class);
                intent.putExtra("email", email);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, newsAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemid = item.getItemId();
        if (itemid == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Download this necessary application\n");
            intent.putExtra(Intent.EXTRA_TEXT, "Application Link Here : https://www.github.com/Devansh856");
            startActivity(Intent.createChooser(intent, "Share Via"));
        } else if (itemid == R.id.nav_logout) {

            SharedPreferences.Editor editor=sh.edit();
            editor.clear();
            editor.commit();
            SharedPreferences.Editor ed=sharedPreferences.edit();
            ed.clear();
            ed.putBoolean("haslo", false);
            ed.commit();
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (itemid == R.id.nav_donate) {
            Intent intent = new Intent(MainActivity.this, donate_food.class);
            intent.putExtra("email", headeremail.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (itemid == R.id.nav_money) {
            Intent intent = new Intent(MainActivity.this, donate_money.class);
            intent.putExtra("email", email);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (itemid == R.id.nav_receive) {
            Intent intent = new Intent(MainActivity.this, receive_food.class);
            intent.putExtra("email", headeremail.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (itemid == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, history.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (itemid == R.id.donateus) {
            Intent intent = new Intent(MainActivity.this, donate_money.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (itemid == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, about_us.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(itemid==R.id.nav_news){
            Intent intent = new Intent(MainActivity.this, newsAct.class);

            startActivity(intent);
        }
        drawerLayout.close();
        return false;
    }

    void getDetails() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("donatefood");
        String e = headeremail.getText().toString();
        String em=e.substring(0,e.length()-10);
       db.child(em).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DataSnapshot> task) {
               if(task.isSuccessful() && task.getResult().exists()){
                   String t = String.valueOf(task.getResult().child("Count").getValue());
                   String k = String.valueOf(task.getResult().child("total").getValue());

                   totaldonations.setText(t);
                   kilos.setText(k + " KG");
               }
               else {
                   totaldonations.setText("0");
                   kilos.setText("0 KG");
               }
           }
       });
    }
}