package com.example.kim.petcareguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kim on 9/27/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "petdb.db";
    public static final String PET_TABLE_NAME = "pet";
    public static final String PET_COLUMN_ID = "pet_id";
    public static final String PET_COLUMN_PETNAME = "petname";
    public static final String PET_COLUMN_PETNO = "petno";
    public static final String PET_COLUMN_BIRTHDATE = "birthdate";
    public static final String PET_COLUMN_IMAGE = "image";
    public static final String DESCRIPTION_COLUMN_DESCRIPTION = "description";
    public static final String DESCRIPTION_COLUMN_PET_ID = "pet_id";
    public static final String DESCRIPTION_COLUMN_ID_ = "desc_id";
    public static final String DESCRIPTION_TABLE_NAME = "desc";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PET_TABLE_NAME  + " ( " + PET_COLUMN_ID + " integer primary key, " +
                PET_COLUMN_PETNAME + " varchar(20)," + PET_COLUMN_PETNO +" text," + PET_COLUMN_BIRTHDATE+  " date, " + PET_COLUMN_IMAGE +" BLOB )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DESCRIPTION_TABLE_NAME  + " ( " + DESCRIPTION_COLUMN_ID_ + " integer primary key, " +
                DESCRIPTION_COLUMN_DESCRIPTION + " varchar(255)," + DESCRIPTION_COLUMN_PET_ID +" text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PET_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DESCRIPTION_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPet(String petname, String petno, String birthdate, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PET_COLUMN_PETNAME, petname);
        contentValues.put(PET_COLUMN_PETNO, petno);
        contentValues.put(PET_COLUMN_BIRTHDATE, birthdate);
        contentValues.put(PET_COLUMN_IMAGE, image);
        db.insert(PET_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertDescription(String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCRIPTION_COLUMN_DESCRIPTION, description);
        db.insert(DESCRIPTION_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public Cursor getPet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + PET_TABLE_NAME + " WHERE "+PET_COLUMN_ID+"=" +id + "", null);
    }

    public Cursor getDataDescription(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DESCRIPTION_TABLE_NAME + " WHERE "+DESCRIPTION_COLUMN_PET_ID+"=" + id + "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, PET_TABLE_NAME);
    }

    public boolean updatePet(Integer id, String petname, String petno, String birthdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PET_COLUMN_PETNAME, petname);
        contentValues.put(PET_COLUMN_PETNO, petno);
        contentValues.put(PET_COLUMN_BIRTHDATE, birthdate);

        db.update(PET_TABLE_NAME, contentValues, " "+PET_COLUMN_ID+" = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateDescription(Integer id, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCRIPTION_COLUMN_DESCRIPTION, description);
        db.update(DESCRIPTION_TABLE_NAME, contentValues, " " + DESCRIPTION_COLUMN_PET_ID + " = ? ", new String[]{Integer.toString(id)});
        return true;
    }


    public void deletePet(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PET_TABLE_NAME, " "+PET_COLUMN_ID+" = ? ", new String[]{Integer.toString(id)});
        db.close();
    }
    public void deleteDescription(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DESCRIPTION_TABLE_NAME, " "+DESCRIPTION_COLUMN_ID_+" = ? ", new String[]{Integer.toString(id)});
        db.close();
    }

    public ArrayList<String> getAllPets() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + PET_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(PET_COLUMN_PETNAME)));

            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllDescription() {
        ArrayList<String> array_list = new ArrayList<String>();
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                    Cursor res = db.rawQuery("SELECT * FROM  "  + DESCRIPTION_TABLE_NAME, null);
                    res.moveToFirst();

                    while (res.isAfterLast() == false) {
                        array_list.add(res.getString(res.getColumnIndex(DESCRIPTION_COLUMN_DESCRIPTION)));
                        res.moveToNext();
                    }
            } catch (SQLiteException e) {

            }
            return array_list;
        }
    }




