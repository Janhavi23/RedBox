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
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.KeyStore;
import java.util.Locale;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextToSpeech t1 ;


        //final String secretKey = "ssshhhhhhhhhhh!!!!";
        EditText plaintext =(EditText) findViewById(R.id.plaintext);
        EditText secretkey =(EditText) findViewById(R.id.firstkey);
        TextView showencryptedtext = (TextView) findViewById(R.id.textView);
        Button encrypt = (Button) findViewById(R.id.encrypt);

        showencryptedtext.setText("Encrypted text will be displayed here");

        t1=new TextToSpeech(getApplicationContext(), new  TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    //t1.setLanguage(Locale.ENGLISH);

                }
            }
        });

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String originalString = plaintext.getText().toString();
                String encryptedString = Aes.encrypt(originalString, secretkey.getText().toString());
                String decryptedString = Aes.decrypt(encryptedString, secretkey.getText().toString());

                showencryptedtext.setText(encryptedString);
                Log.i(originalString,"origianal_string ");
                Log.i(encryptedString," encrypted_string ");
                Log.i(decryptedString,"decrypted_string");
                Log.i("hello lets print this", "this is a message for debugging");

                t1.setLanguage(Locale.ENGLISH);
                t1.speak(encryptedString, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

    }
}
