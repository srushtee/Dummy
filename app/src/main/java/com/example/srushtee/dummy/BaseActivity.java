package com.example.srushtee.dummy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by srushtee on 18-03-2017.
 */

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {



    protected FirebaseUser mFirebaseUser;
    protected GoogleApiClient mGoogleApiClient;
    protected FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private GoogleSignInOptions mGso;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth!=null)
        {
            mFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient= new GoogleApiClient.Builder(this.getApplicationContext())
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }
    protected void showProgressDialog()
    {
        if(mProgressDialog==null)

        {
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage("Logging you in");
            mProgressDialog.setIndeterminate(true);

        }
        mProgressDialog.show();
    }
    protected void hideProgressDialog()
    {
        if(mProgressDialog!=null&&mProgressDialog.isShowing())
        {
            mProgressDialog.hide();
        }
    }
    protected void signOut()
    {
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        hideProgressDialog();
    }








    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
