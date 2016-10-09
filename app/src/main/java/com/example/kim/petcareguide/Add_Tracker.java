package com.example.kim.petcareguide;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Add_Tracker extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView description;
    int id_To_Update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__tracker);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.addtracker_);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




        description = (TextView) findViewById(R.id.edDiscription);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int id = extras.getInt("id");

            if(id>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getDataDescription(id);
                id_To_Update = id;
                try {
                    rs.moveToFirst();

                    String descriptio = rs.getString(rs.getColumnIndex(DBHelper.DESCRIPTION_COLUMN_DESCRIPTION));

                    if (!rs.isClosed()) {
                        rs.close();

                    }
                    ImageView img2 = (ImageView) findViewById(R.id.imgaddtracker);
                    img2.setVisibility(View.INVISIBLE);

                    description.setText(descriptio);
                    description.setFocusable(false);
                    description.setClickable(false);


                }catch (CursorIndexOutOfBoundsException e){}
            }
        }

    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateDescription(id_To_Update,description.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("id",Value);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertDescription(description.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),Growth_Tracker.class);
                startActivity(intent);
                finish();

            }
        }
    }

    public void back(View view){
        Intent intent = new Intent(Add_Tracker.this,Growth_Tracker.class);
        startActivity(intent);
    }


}
