package com.example.kim.petcareguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Add_Pet extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView petname;
    TextView birthdate;
    TextView petno;
    int id_To_Update = 0;

    ImageView viewImage1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__pet);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.add_pet);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




        b1=(Button)findViewById(R.id.button3);
        viewImage1=(ImageView)findViewById(R.id.imagePet);
        b1.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        petname = (TextView) findViewById(R.id.edPetName);
        petno = (TextView) findViewById(R.id.edPetNo);
        birthdate = (TextView) findViewById(R.id.edBirthDate);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getPet(Value);
                id_To_Update = Value;
            try {
                rs.moveToFirst();

                String petnam = rs.getString(rs.getColumnIndex(DBHelper.PET_COLUMN_PETNAME));
                String petn = rs.getString(rs.getColumnIndex(DBHelper.PET_COLUMN_PETNO));
                String birthdat = rs.getString(rs.getColumnIndex(DBHelper.PET_COLUMN_BIRTHDATE));


                if (!rs.isClosed()) {
                    rs.close();

                }
                ImageView img = (ImageView) findViewById(R.id.imgaddpet);
                img.setVisibility(View.INVISIBLE);

                petname.setText((CharSequence) petnam);
                petname.setFocusable(false);
                petname.setClickable(false);

                birthdate.setText((CharSequence) birthdat);
                birthdate.setFocusable(false);
                birthdate.setClickable(false);

                petno.setText((CharSequence) petn);
                petno.setFocusable(false);
                petno.setClickable(false);
            }catch (CursorIndexOutOfBoundsException e){}
            }
        }

    }
    //to display the edit and delete//
    //to edit and delete//


    //to add pet//
    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updatePet(id_To_Update,petname.getText().toString(), petno.getText().toString(), birthdate.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                String name = petname.getText().toString();
                String petNum = petno.getText().toString();
                String birth = birthdate.getText().toString();
                Bitmap b =  ((BitmapDrawable)viewImage1.getDrawable()).getBitmap();
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                b.compress(Bitmap.CompressFormat.PNG, 100, out);

                byte[] buffer=out.toByteArray();
                if(mydb.insertPet(name,petNum,birth,buffer)){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
                finish();
            }
        }
    }
    public boolean hasImageCaptureBug() {

        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
                + android.os.Build.DEVICE);

    }
    private int IMAGE_CAPTURE = 1;
    //images to select
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Add_Pet.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                 /*   Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (hasImageCaptureBug()) {
                        i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp")));
                    } else {
                        i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    startActivityForResult(i, IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)); */
                 //   finish();
dispatchTakePictureIntent();

                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                //    finish();

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
             // Create the File where the photo should go
          //   File photoFile = null;
          //   try {
          //       photoFile = createImageFile();
          //     } catch (IOException ex) {
                // Error occurred while creating the File
         //      }
            // Continue only if the File was successfully created
         //   if (photoFile != null) {
           //     Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
            //    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE);
        //    }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);

        // Save a file: path for use with ACTION_VIEW intents
       // mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_CAPTURE) {

                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage1.setImageBitmap(bitmap);

                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("path of image ", picturePath + "");
                viewImage1.setImageBitmap(thumbnail);
            }
        }*/
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        viewImage1.setScaleType(ImageView.ScaleType.FIT_XY);

        viewImage1.setImageBitmap(imageBitmap);

    }
    public void home(View view){
        Intent intent = new Intent(Add_Pet.this,Home.class);
        startActivity(intent);
    }
    public void back(View view){
        Intent intent = new Intent(Add_Pet.this,Home.class);
        startActivity(intent);
    }
}
