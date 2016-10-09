package com.example.kim.petcareguide;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Petcare extends AppCompatActivity {

    private ImageView pet_info;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.pet_care);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        id = getIntent().getExtras().getInt("id");
    }


    public void petinfo(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Pet_Information.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void gtracker(View view)
    {
        Intent intent = new Intent(Petcare.this,Growth_Tracker.class);
        Toast.makeText(getApplicationContext(),"ID IN PET OPTIONS: " + id, Toast.LENGTH_LONG).show();

        intent.putExtra("id",id);
        startActivity(intent);

    }
    public void reminder(View view)
    {
        Intent intent = new Intent(Petcare.this,Reminder.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
    public void callvet(View view)
    {
        Intent intent = new Intent(Petcare.this,Call_Vet.class);
        startActivity(intent);
    }

}
