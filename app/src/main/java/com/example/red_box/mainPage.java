package com.example.red_box;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainPage extends AppCompatActivity {
    Button store,retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        store = findViewById(R.id.storebtn);
        retrieve = findViewById(R.id.retrievepassword);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainPage.this,MainActivity.class);
                intent.putExtra("Code",getIntent().getStringExtra("Code"));
                startActivity(intent);
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainPage.this,fetch_password.class);
                startActivity(intent);
            }
        });
    }
}