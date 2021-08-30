package com.polymath.subscriptionmanager;

import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CalendarContract;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.polymath.subscriptionmanager.data.SubscriptionContract;
import com.polymath.subscriptionmanager.data.SubscriptionContract.*;
import com.polymath.subscriptionmanager.data.SubscriptionDbHelper;



import java.util.Calendar;
import java.util.GregorianCalendar;


public class EditorActivity extends AppCompatActivity
{
    private EditText mNameEditText;
    private EditText mDayEditText;
    private EditText mMonthEditText;
    private EditText mYearEditText;
    private EditText mDaySEditText;

    private SubscriptionDbHelper mDbHelper= new SubscriptionDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditText = (EditText) findViewById(R.id.edit_text_name);
        mDayEditText = (EditText) findViewById(R.id.edit_text_startDay);
        mMonthEditText = (EditText) findViewById(R.id.edit_text_startMonth);
        mYearEditText = (EditText) findViewById(R.id.edit_text_startYear);
        mDaySEditText = (EditText) findViewById(R.id.edit_text_days);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void insertCalendarIntent()
    {
        String nameString  = mNameEditText.getText().toString().trim();
        String dateString = mDayEditText.getText().toString().trim();
        String monthString = mMonthEditText.getText().toString().trim();
        String yearString= mYearEditText.getText().toString().trim();
        String daysString = mDaySEditText.getText().toString().trim();

        int date = Integer.parseInt(dateString);
        int month = Integer.parseInt(monthString);
        int year = Integer.parseInt(yearString);
        int days = Integer.parseInt(daysString);

        String startDate = date+"-"+month+"-"+year;

        ContentValues values = new ContentValues();
        values.put(SubscriptionEntry.COLUMN_NAME, nameString);
        values.put(SubscriptionEntry.COLUMN_START_DATE, startDate);
        values.put(SubscriptionEntry.COLUMN_DAYS_OF_SUBSCRIPTION, days);



        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long row = db.insert(SubscriptionEntry.TABLE_NAME, null, values);

        if (row == -1)
        {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "There was an error adding the Subscription", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "The Subscription was successfully added." , Toast.LENGTH_SHORT).show();
        }

        //TODO:Throw Calendar Intent



        //Extracting the End Date from the StartDate
        GregorianCalendar endDate = new GregorianCalendar(year, month, date);
        endDate.add(Calendar.DAY_OF_MONTH, days-1);

        //Making a calendar Intent
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, "Subscription Reminder for "+nameString);
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "This is the reminder by the Subscription Reminder app to cancel " +
                "your subscription for the service "+nameString+". Your subscription will auto-renew tomorrow. Please " +
                "cancel your subscription now to avoid being charged for the next session period :)");


        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                endDate.getTimeInMillis()+32400000);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                endDate.getTimeInMillis()+32700000);

        //Throw Calendar Intent
        startActivity(calIntent);


        //TODO:Display
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.done_item:
            {
                insertCalendarIntent();
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
