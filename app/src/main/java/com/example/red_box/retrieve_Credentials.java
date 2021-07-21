package com.example.red_box;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

public class retrieve_Credentials extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Email> candidateArrayList;
    adapterEmail adapterEmail;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_credentials);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        candidateArrayList = new ArrayList<Email>();

        db.collection("users").document(getIntent().getStringExtra("Code")).collection("Emails").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty()){
                        Toast.makeText(retrieve_Credentials.this,"No Credentials stored!",Toast.LENGTH_LONG);
                    }
                    else{
                        progressDialog = new ProgressDialog(retrieve_Credentials.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Fetching Email's list");
                        progressDialog.show();
                        recyclerView = findViewById(R.id.recycleview);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(retrieve_Credentials.this));
                        candidateArrayList = new ArrayList<Email>();
                        adapterEmail = new adapterEmail(retrieve_Credentials.this, candidateArrayList, new adapterEmail.ItemClickListener() {
                            @Override
                            public void onItemClick(Email email) {
                                    click(email);
                                    }
                                });
                        Log.i("Array size in main class:",String.valueOf(candidateArrayList.size()));
                                recyclerView.setAdapter(adapterEmail);
                                EventChangeListener();
                            }

                    }
        });
    }

    private void click(Email email){
        AlertDialog.Builder builder = new AlertDialog.Builder(retrieve_Credentials.this);
        builder.setTitle("PassKey:");
        final View customLayout= getLayoutInflater().inflate(R.layout.custom_alert_dialog,null);
        builder.setView(customLayout);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText
                        = customLayout
                        .findViewById(
                                R.id.key);
                try {
                    Fetch(email.decrypted_Email,editText.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }

    private void Fetch(String Email,String Key) throws Exception {
        DESEncryption Des = new DESEncryption();
        String digitalsign = Des.encrypt(Key);

        db.collection("users").document(getIntent().getStringExtra("Code")).collection("Emails").document(Email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("key").equals(digitalsign)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(retrieve_Credentials.this);
                    builder.setTitle("Password");
                    builder.setMessage(documentSnapshot.getString("decrypted_Password"));
                    AlertDialog dialog
                            = builder.create();
                    dialog.show();

                }
                else {
                    Toast.makeText(retrieve_Credentials.this,"Check your key value!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void EventChangeListener() {
        db.collection("users/"+getIntent().getStringExtra("Code")+"/Emails").orderBy("decrypted_Email", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                candidateArrayList.add(dc.getDocument().toObject(Email.class));
                            }
                            adapterEmail.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

}