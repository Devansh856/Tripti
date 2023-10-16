package com.example.tripti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class about_us extends AppCompatActivity {
    Button insta, face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        insta = findViewById(R.id.instagram);
        face = findViewById(R.id.facebook);
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://www.instagram.com/patel.devansh03");
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoURL("https://www.facebook.com/devanshpatel.dp.3");
            }
        });


    }
    void gotoURL(String url){
        Uri uri=Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}