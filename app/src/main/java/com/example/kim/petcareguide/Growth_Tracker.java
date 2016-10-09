package com.example.kim.petcareguide;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Growth_Tracker extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb = new DBHelper(Growth_Tracker.this);
    int id;
    int ADD_TRACKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth__tracker);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.growtht);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras()!=null)
        id = getIntent().getExtras().getInt("id");


        obj = (ListView) findViewById(R.id.listView2);
        loadDescription();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        loadDescription();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tracker, menu);
        return true;
    }

    public void loadDescription(){
        final ArrayList array_list = mydb.getAllDescription();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
        Toast.makeText(this,"SIZE: " + array_list.size(), Toast.LENGTH_LONG ).show();
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int id_To_Search = getIntent().getExtras().getInt("id");
                Toast.makeText(getApplicationContext(),"ID IN GROWTH TRACKER:" + id_To_Search,Toast.LENGTH_LONG).show();
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(), Edit_Tracker.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        super.onOptionsItemSelected(i);

            switch (i.getItemId()) {
                case R.id.item2:
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id);
                    Intent intent = new Intent(Growth_Tracker.this, Add_Tracker.class);
                    intent.putExtras(dataBundle);
                    startActivityForResult(intent,ADD_TRACKER);
                    return true;
                default:
                    return super.onOptionsItemSelected(i);
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_TRACKER) {
             // Make sure the request was successful
             if (resultCode == RESULT_OK) {
                 // The user picked a contact
                 // The Intent's data Uri identifies which contact was selected.
                 id = data.getExtras().getInt("id");
                 Toast.makeText(getApplicationContext(),id+ " ON ACTIVITY FOR RESULT",Toast.LENGTH_LONG).show();
                 // Do something with the contact here (bigger example below)
                 }
        }

    }
}
