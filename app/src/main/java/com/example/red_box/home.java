package com.example.red_box;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Destroyable;

public class home extends AppCompatActivity {
    Button login,retrieve;
    EditText code;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        login = findViewById(R.id.loginbtn);
        code = findViewById(R.id.PinCode);
        db = FirebaseFirestore.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            String signature;
            @Override
            public void onClick(View view) {

                DESEncryption Des = null;
                try {
                    Des = new DESEncryption();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                signature = Des.encrypt(code.getText().toString());
                Map<String, Object> data = new HashMap<>();
                data.put("Signature",signature);
                db.collection("users").document(code.getText().toString()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(home.this,mainPage.class);
                        intent.putExtra("Code",code.getText().toString());
                        startActivity(intent);
                    }
                });

            }
        });
    }
}