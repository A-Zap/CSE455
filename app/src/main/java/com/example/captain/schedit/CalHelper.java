package com.example.captain.schedit;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Captain on 11/23/2017.
 */

public class CalHelper {
    Context con;

    public final static void addEvent(Activity act)
    {
        // Construct event details
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 11, 28, 1, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 11, 28, 8, 45);
        endMillis = endTime.getTimeInMillis();

        // Insert Event
        ContentResolver cr = act.getContentResolver();
        ContentValues values = new ContentValues();
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.TITLE, "Test title");
        values.put(CalendarContract.Events.DESCRIPTION, "Test description");
        values.put(CalendarContract.Events.CALENDAR_ID, 3);



        int permissionCheck = ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_CALENDAR);
        Uri uri;
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            String eventID = uri.getLastPathSegment();
        }
        else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Context context = act.getApplicationContext();
            CharSequence text = "Permission Denied";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        // Retrieve ID for new event
    }
}
