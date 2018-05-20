package com.simbora;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class Main extends AppCompatActivity {

    private FragmentManager fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment.beginTransaction().replace(R.id.content, new MapaFragment()).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        fragment.beginTransaction().replace(R.id.content, new ViagensFragment()).commit();
                        return true;
                    case R.id.navigation_notifications:
                        fragment.beginTransaction().replace(R.id.content, new PerfilFragment()).commit();
                        return true;
                }
                return false;
            }
        };

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.fragment = getSupportFragmentManager();
        this.fragment.beginTransaction().replace(R.id.content, new MapaFragment()).commit();
    }

}
