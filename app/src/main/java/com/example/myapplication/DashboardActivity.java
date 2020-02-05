package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActionBar actionbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
         actionbar=getSupportActionBar();




        firebaseAuth=firebaseAuth.getInstance();

        BottomNavigationView navigationView=findViewById(R.id.nav_id);

        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        HomeFragment  homeFragment=new HomeFragment();
        actionbar.setTitle("Home");
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();




    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){

                        case R.id.home_nav_id:

                            actionbar.setTitle("Home");
                            HomeFragment fragemnt1=new HomeFragment();
                            FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.frame_layout_id,fragemnt1);
                            ft1.commit();
                            return true;


                        case R.id.user_nav_id:

                            actionbar.setTitle("User's");
                            UserFragment userFragment=new UserFragment();
                            FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.frame_layout_id,userFragment);
                            ft2.commit();
                            return true;

                      case  R.id.profile_nav_id:

                          actionbar.setTitle("Profile");
                          ProfileFragment profileFragment=new ProfileFragment();
                          FragmentTransaction f3=getSupportFragmentManager().beginTransaction();
                          f3.replace(R.id.frame_layout_id,profileFragment);
                          f3.commit();
                          return true;


                    }
                    return false;
                }
            };

    private void checkUserstatus(){

        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null){




        } else {

            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserstatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.logoutmenu_id){

            firebaseAuth.signOut();
            checkUserstatus();

        }

        return super.onOptionsItemSelected(item);
    }



}

