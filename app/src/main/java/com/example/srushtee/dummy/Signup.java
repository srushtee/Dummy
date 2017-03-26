package com.example.srushtee.dummy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Signup extends BaseActivity {

   private EditText editTextUserName;
    private EditText editTestEmail;
     private EditText editTextPassword;
    private EditText editTextPhoneNumber;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private Button nGoogleButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN=1;
    private static final int RC_FB_SIGN_IN=2;
    private static final String TAG="MAIN_ACTIVITY";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        if(mAuth.getCurrentUser()!=null)
        {
            finish();

            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(Signup.this,NextActivity.class));
                }
            }
        };
        progressDialog=new ProgressDialog(this);



        buttonRegister=(Button) findViewById(R.id.signup);
        editTestEmail=(EditText) findViewById(R.id.email_edittext);
        editTextPassword=(EditText) findViewById(R.id.pass_edittext);
        editTextUserName=(EditText)findViewById(R.id.username_edittext);
        editTextPhoneNumber=(EditText)findViewById(R.id.mobile_number);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==buttonRegister)
                {
                    registerUser();
                }
            }
        });

    }


    private void registerUser()
    {
        String email=editTestEmail.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        final String name = editTextUserName.getText().toString().trim();
        final String phone_number=editTextPhoneNumber.getText().toString().trim();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(Signup.this,"Enter name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phone_number))
        {
            Toast.makeText(Signup.this,"Enter phone number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isValidEmailAddress(email)==false)
        {
            Toast.makeText(Signup.this,"Email not valid",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    String user_id=mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db=mDatabase.child(user_id);
                    current_user_db.child("Name").setValue(name);
                    current_user_db.child("Phone_number").setValue(phone_number);
                    progressDialog.dismiss();
                    startActivity(new Intent(Signup.this,ProfileActivity.class));



                }

                else
                {
                    if(password.length()<=5)
                    {
                        Toast.makeText(Signup.this,"Password should be of atleast 6 characters",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

            }
        });



    }





    private boolean isValidEmailAddress(String email) {
        boolean result=true;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop()
    {

        super.onStop();



        if(mAuthListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



}
