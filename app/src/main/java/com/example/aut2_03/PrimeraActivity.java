package com.example.aut2_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PrimeraActivity extends AppCompatActivity {

    private SideNavBar sideNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera);

        sideNavBar = new SideNavBar();
        sideNavBar.createSideNavBar(this);
    }
}