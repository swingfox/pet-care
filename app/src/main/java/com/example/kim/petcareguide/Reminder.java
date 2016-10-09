package com.example.kim.petcareguide;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Reminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.remind);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }
    public void back(View view) {
        Intent intent = new Intent(Reminder.this, Petcare.class);
        startActivity(intent);
    }
}
