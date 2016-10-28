package edu.orangecoastcollege.cs273.dpham147.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Kyubey on 2016-10-27.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PetProtector";
    static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IMAGE_URI = "uri";

    public DBHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + DATABASE_TABLE + "(" +
                KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIELD_NAME + " TEXT, " +
                FIELD_DETAILS + " TEXT, " +
                FIELD_PHONE + " TEXT, " +
                FIELD_IMAGE_URI + " TEXT" + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void addPet(Pet newPet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, newPet.getName());
        values.put(FIELD_DETAILS, newPet.getDetails());
        values.put(FIELD_PHONE, newPet.getPhone());
        values.put(FIELD_IMAGE_URI, newPet.getImageURI().toString());

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public ArrayList<Pet> getAllPets()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Pet> allPets = new ArrayList<>();

        Cursor cursor = db.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String details = cursor.getString(2);
                String phone = cursor.getString(3);
                Uri imageURI = Uri.parse(cursor.getString(4));

                Pet newPet = new Pet(id, name, details, phone, imageURI);
                allPets.add(newPet);
            }
            while (cursor.moveToNext());
        }

        db.close();
        return allPets;
    }
}
