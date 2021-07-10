package com.parth.petsdatabase.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PetDatabaseOpenHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shelter.db";

    //defining the standard schemas from the database constants
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + " (" +
                    PetContract.PetEntry._ID + " INTEGER PRIMARY KEY," +
                    PetContract.PetEntry.COLUMN_PET_NAME + " TEXT," +
                    PetContract.PetEntry.COLUMN_PET_BREED + " TEXT,"+
                    PetContract.PetEntry.COLUMN_PET_GENDER+" INTEGER,"+
                    PetContract.PetEntry.COLUMN_PET_WEIGHT+" INTEGER"+");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetContract.PetEntry.TABLE_NAME;

    //defining the constructor
    public PetDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //overriding methods from the open helper class
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //this database is only for the cache of the online data,so its upgrade policy is to simply discard the data and start over
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
