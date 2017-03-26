package com.example.srushtee.dummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDataActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonSignout;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference databaseReference;
    public ListView listViewUser;
    List<list_item> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        listViewUser = (ListView) findViewById(R.id.listViewUser);
        listViewUser = (ListView) findViewById(R.id.listViewUser);
        userList = new ArrayList<>();
        buttonSignout=(Button)findViewById(R.id.buttonSignout);
        buttonSignout.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    list_item userData = userSnapshot.getValue(list_item.class);
                    userList.add(userData);
                }
                UserList adapter = new UserList(UserDataActivity.this, userList);
                listViewUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        if (v==buttonSignout)
        {
            firebaseAuth.signOut();
            finish();
            Intent i=new Intent(v.getContext(),MainActivity.class);

        }

    }
}
