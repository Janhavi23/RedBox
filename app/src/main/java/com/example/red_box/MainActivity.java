package com.example.red_box;
//import com.example.red_box.Aes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Locale;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends Activity{
    private EditText email,password;
    FirebaseFirestore db;
    DESEncryption Des;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        EditText secretkey =(EditText) findViewById(R.id.firstkey);
        TextView showencryptedtext = (TextView) findViewById(R.id.textView);
        Button encrypt = (Button) findViewById(R.id.encrypt);
        db = FirebaseFirestore.getInstance();
        try {
            Des = new DESEncryption();
        } catch (Exception e) {
            e.printStackTrace();
        }

        showencryptedtext.setText("Encrypted text will be displayed here");


        encrypt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String desEncryptEmail = Des.encrypt(Email);
                String encryptedEmail = Aes.encrypt(desEncryptEmail, secretkey.getText().toString());
                String decryptedEmail = Aes.decrypt(encryptedEmail, secretkey.getText().toString());
                String desDecryptEmail = Des.decrypt(decryptedEmail);
                String desEncryptedPassword = Des.encrypt(Password);
                String encryptedPassword = Aes.encrypt(desEncryptedPassword, secretkey.getText().toString());
                String decryptedPassword = Aes.decrypt(encryptedPassword, secretkey.getText().toString());
                String desDecryptedPassword = Des.decrypt(decryptedPassword);

                Map<String, Object> data = new HashMap<>();
                data.put("encrypted_Email",encryptedEmail);
                data.put("decrypted_Email",desDecryptEmail);
                data.put("encrypted_Password",encryptedPassword);
                data.put("decrypted_Password",desDecryptedPassword);
                data.put("key",Des.encrypt(secretkey.getText().toString()));
                db.collection("users").document(getIntent().getStringExtra("Code")).collection("Emails").document(Email)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Added", "Successful");
                                Toast.makeText(MainActivity.this,"Successfully added data!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Error", "Error adding document", e);
                                Toast.makeText(MainActivity.this,"Error:"+e,Toast.LENGTH_LONG).show();
                            }
                        });



                showencryptedtext.setText(encryptedPassword);
                Log.i(Password,"origianal_string ");
                Log.i(encryptedPassword," encrypted_string ");
                Log.i(decryptedPassword,"decrypted_string");
                Log.i("hello lets print this", "this is a message for debugging");

            }
        });


    }
}
