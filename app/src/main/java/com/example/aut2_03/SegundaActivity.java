package com.example.aut2_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SegundaActivity extends AppCompatActivity {

    private SideNavBar sideNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        sideNavBar = new SideNavBar();
        sideNavBar.createSideNavBar(this);
    }
}