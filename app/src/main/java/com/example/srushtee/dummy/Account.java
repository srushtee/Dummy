package com.example.srushtee.dummy;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Account extends Signup{
private FirebaseAuth mAuth;

    private TextView getEmailShow;

    private TextView getUserNameshow;

    private TextView getPhoneShow;
    private DatabaseReference mRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getEmailShow=(TextView) findViewById(R.id.textViewEmailShow);
        getUserNameshow=(TextView) findViewById(R.id.textViewUserShow);
        getPhoneShow=(TextView) findViewById(R.id.textViewPhone);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        mRef=FirebaseDatabase.getInstance().getReferenceFromUrl("https://dummy-3ea6a.firebaseio.com/");
        String userId=user.getUid();
        getEmailShow.setText(" "+user.getEmail());
        String name=mRef.child("Users").child("Name").push().toString().trim();
        getUserNameshow.setText(name);


    }
}
