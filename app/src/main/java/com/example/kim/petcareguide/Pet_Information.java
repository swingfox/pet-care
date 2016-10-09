package com.example.kim.petcareguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Pet_Information extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView petname;
    TextView birthdate;
    TextView petno;
    int id_To_Update = 0;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet__information);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.petinform);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        petname = (TextView) findViewById(R.id.edPetName);
        petno = (TextView) findViewById(R.id.edPetNo);
        birthdate = (TextView) findViewById(R.id.edBirthDate);
        imageView = (ImageView) findViewById(R.id.petImage);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getPet(Value);
                id_To_Update = Value;
                try {
                    rs.moveToFirst();

                    String petnam = rs.getString(rs.getColumnIndex(DBHelper.PET_COLUMN_PETNAME));
                    String petn = rs.getString(rs.getColumnIndex(DBHelper.PET_COLUMN_PETNO));
                    String birthdat = rs.getString(rs.getColumnIndex(DBHelper.PET_COLUMN_BIRTHDATE));
                    byte[] image = rs.getBlob(rs.getColumnIndex(DBHelper.PET_COLUMN_IMAGE));

                    if (!rs.isClosed()) {
                        rs.close();

                    }
                    ImageView img = (ImageView) findViewById(R.id.imgupdate);
                    img.setVisibility(View.INVISIBLE);

                    petname.setText(petnam);
                    petname.setFocusable(false);
                    petname.setClickable(false);

                    birthdate.setText(birthdat);
                    birthdate.setFocusable(false);
                    birthdate.setClickable(false);

                    petno.setText(petn);
                    petno.setFocusable(false);
                    petno.setClickable(false);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(getImage(image));
                } catch (CursorIndexOutOfBoundsException e) {
                }
            }
        }
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            Bundle extras = getIntent().getExtras();

            if(extras !=null)
            {
                int Value = extras.getInt("id");
                if(Value>0){
                    getMenuInflater().inflate(R.menu.display_details, menu);
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
                ImageView img = (ImageView) findViewById(R.id.imgupdate);
                img.setVisibility(View.VISIBLE);

                petname.setEnabled(true);
                petname.setFocusableInTouchMode(true);
                petname.setClickable(true);

                petno.setEnabled(true);
                petno.setFocusableInTouchMode(true);
                petno.setClickable(true);

                birthdate.setEnabled(true);
                birthdate.setFocusableInTouchMode(true);
                birthdate.setClickable(true);

                return true;

            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deletePet(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Home.class);
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
                if(mydb.updatePet(id_To_Update,petname.getText().toString(), petno.getText().toString(), birthdate.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                  //  Intent intent = new Intent(getApplicationContext(),Home.class);
                 //   startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
              /*  if(mydb.insertPet(petname.getText().toString(), petno.getText().toString(),birthdate.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }*/
               // Intent intent = new Intent(getApplicationContext(),Home.class);
               // startActivity(intent);
                finish();
            }
        }
    }
}