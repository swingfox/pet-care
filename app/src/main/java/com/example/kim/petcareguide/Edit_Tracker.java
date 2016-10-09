package com.example.kim.petcareguide;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_Tracker extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView description;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__tracker);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.edit_tracker);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);





        description= (TextView) findViewById(R.id.edDiscription);
        mydb = new DBHelper(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getDataDescription(Value);
                id_To_Update = Value;
                try {
                    rs.moveToFirst();

                    String descriptio = rs.getString(rs.getColumnIndex(DBHelper.DESCRIPTION_COLUMN_DESCRIPTION));

                    if (!rs.isClosed()) {
                        rs.close();

                    }
                    ImageView img = (ImageView) findViewById(R.id.imgudate);
                    img.setVisibility(View.INVISIBLE);

                    description.setText((CharSequence)descriptio);
                    description.setFocusable(false);
                    description.setClickable(false);
                } catch (CursorIndexOutOfBoundsException e) {
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.edit_tracker,menu);
            }

            else{
                getMenuInflater().inflate(R.menu.menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Contact:
                ImageView img3 = (ImageView) findViewById(R.id.imgudate);
                img3.setVisibility(View.VISIBLE);

                description.setEnabled(true);
                description.setFocusableInTouchMode(true);
                description.setClickable(true);

                return true;

            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteDescription(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Growth_Tracker.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                //if(mydb.updateDescription(id_To_Update,description.getText().toString())){
                    mydb.insertDescription(description.getText().toString()+"");
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("id",Value);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
             //   }
            //    else{
                    //Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
            //    }
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
    public void back(View view) {
        Intent intent = new Intent(Edit_Tracker.this, Growth_Tracker.class);
        startActivity(intent);
    }


}
