package com.example.srushtee.dummy;

import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements derp_adapter.ItemClickCallback {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private boolean backPressedToExitOnce;
    private boolean doubleBackToExitPressedOnce;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private RecyclerView rec_view;
    private derp_adapter adapter;
    private ArrayList list_data;
    private static final String[] place_name={"Manali","Shimla","Shrinagar","Darjeeling","Leh Ladakh","Haridwar"};
    private static final String[] place_desc={"Beautiful place","Beautiful place","Beautiful place","Beautiful place"
            ,"Beautiful place","Beautiful place"};
    private static final int[] place_image={R.drawable.manali,
            R.drawable.shimla,
            R.drawable.srinagar,
            R.drawable.darjeeling,
            R.drawable.leh_ladakh,
            R.drawable.haridwar};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth==null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch(item.getItemId())
                {
                    case R.id.nav_account:
                        Toast.makeText(ProfileActivity.this,"called account",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this,Account.class));
                        return true;
                    case R.id.nav_LogOut:
                        signout();
                        return true;
                    case R.id.nav_cities:
                        startActivity(new Intent(ProfileActivity.this,CitiesActivity.class));
                        return true;


                    default:
                        Toast.makeText(ProfileActivity.this,"default",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
       // String getEmail=user.getEmail().toString().trim();

        TextView textEmail=(TextView)navigationView.getHeaderView(0).findViewById(R.id.textViewUserEmail);
        //textEmail.setText(getEmail);
        TextView txtUserName=(TextView)navigationView.getHeaderView(0).findViewById(R.id.textViewUserName);
        //textEmail.setText(" "+user.getDisplayName());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close); List<list_item> data = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            list_item item = new list_item();
            item.setImageResId(place_image[j]);
            item.setTitle(place_name[j]);
            item.setId(place_name[j]);
            item.setStatus(place_desc[j]);
            data.add(item);
        }

        list_data = (ArrayList) data;

        //data stored and retrieved until now

        rec_view = (RecyclerView) findViewById(R.id.places_recyler_view);
        rec_view.setLayoutManager(new LinearLayoutManager(this));

        adapter = new derp_adapter(data, this);
        rec_view.setAdapter(adapter);

        adapter.setItemClickCallback(this);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();






    }

    private void signout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,MainActivity.class));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            Process.killProcess(Process.myPid());
            System.exit(1);

        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);

    }

    @Override
    public void onItemClick(int p) {
        list_item item=(list_item)list_data.get(p);
        Toast.makeText(getApplicationContext(),"Clicked at:"+item.getId(),Toast.LENGTH_SHORT).show();
        Intent i=new Intent("com.example.anonymous.rajat_project.home_page");
        startActivity(i);
    }

    @Override
    public void onSecondaryIconClick(int p) {

    }
}
