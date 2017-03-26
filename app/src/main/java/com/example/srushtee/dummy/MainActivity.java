package com.example.srushtee.dummy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.makeText;


public class  MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextMail;
    private FirebaseAuth firebaseAuth;
    private TextView skip1;
    private TextView signup;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;
    private EditText editTextPassword;
    private Button buttonLogin;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifi!=null&datac!=null)&&(wifi.isConnected()|datac.isConnected()))
        {

        }
        else
        {

            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

        editTextMail=(EditText) findViewById(R.id.editTextMail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        buttonLogin=(Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        signup=(TextView)findViewById(R.id.signup);
        signup.setOnClickListener(this);

        skip1=(TextView)findViewById(R.id.skip);
        skip1.setOnClickListener(this);

        forgotPassword=(TextView)findViewById(R.id.textView4) ;
        forgotPassword.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);




        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();

            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }





    }




    private void userLogin()
    {
        String email=editTextMail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Signing you up...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    finish();

                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                }
                else
                {
                    checkIfEmailVerified();
                }

            }
        });

    }

    private void checkIfEmailVerified() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user.isEmailVerified())
        {
            finish();
            Toast.makeText(MainActivity.this,"EMail verified",Toast.LENGTH_LONG).show();
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
        }
    }


    @Override
    public void onClick(View v) {
        if(v==buttonLogin)
            userLogin();
        if(v==signup)
        {
            Intent i=new Intent(v.getContext(),Signup.class);
            startActivity(i);

        }
        if(v==skip1)
        {
            Intent i=new Intent(v.getContext(),ProfileActivity.class);
            startActivity(i);
        }
        if (v==forgotPassword)
        {
            Intent i=new Intent(v.getContext(),ResetActivity.class);
            startActivity(i);
        }
    }


}


