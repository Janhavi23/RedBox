 package com.example.red_box;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

 public class fetch_password extends AppCompatActivity {

    private EditText email;
    private Button retrieve;
    private TextView password;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_password);

        email = findViewById(R.id.retrieveemail);
        retrieve = findViewById(R.id.retrieve);
        password = findViewById(R.id.retrievepassword);
        db = FirebaseFirestore.getInstance();

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            password.setText(documentSnapshot.getString("decrypted Password"));
                            password.setVisibility(View.VISIBLE);
                        }
                        else{
                            Toast.makeText(fetch_password.this,"Please check the email address",Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });

    }
}