package com.polymath.subscriptionmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.polymath.subscriptionmanager.data.SubscriptionContract.*;

public class SubscriptionDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "subs.db";


    public SubscriptionDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Create a Database
        String SQL_CREATE_ENTRIES = "CREATE TABLE "+ SubscriptionEntry.TABLE_NAME +" ("
                + SubscriptionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SubscriptionEntry.COLUMN_NAME + " TEXT NOT NULL ,"
                + SubscriptionEntry.COLUMN_START_DATE+ " TEXT NOT NULL ,"
                +SubscriptionEntry.COLUMN_DAYS_OF_SUBSCRIPTION+ " INTEGER NOT NULL DEFAULT 2"
                + ");";
        Log.d("In onCreate", SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Do Nothing
    }
}
