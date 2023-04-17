package com.example.captain.schedit;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nestor Saavedra/Andres Zapata on 11/2/2017.
 * using sqlite to store our task information
 */
public class DbHelper extends SQLiteOpenHelper {

//table and column definitions
    //changed database
    private static final String DB_NAME= "SCHEDITDB";
    private static final  int DB_VER = 1;
    public static final String DB_TABLE = "TasksAndDate";
    public static final String DB_COLUMN = "TaskName";
    public static final String DB_COLUMN1 = "FavTask";
    public static final String DB_COLUMN2 = "Date";

    public DbHelper(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }
//Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL  );", DB_TABLE,DB_COLUMN,DB_COLUMN1, DB_COLUMN2);
        db.execSQL(query);
    }
//if the table exists delete it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s ", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

//add an item to the database
    public boolean insertNewTask(String task, String fav, String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, task);
        values.put(DB_COLUMN1, fav);
        values.put(DB_COLUMN2, date);

        long result  = db.insert(DB_TABLE, null, values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
//delete from data base
    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " =?", new String[]{task});
        db.close();

    }
//get list from the database
    public ArrayList<String> getTaskList(){

        ArrayList< String > taskList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN, DB_COLUMN1, DB_COLUMN2},null,null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);


            taskList.add(cursor.getString(index));


        }
        cursor.close();
        db.close();
        return taskList;
    }

    //like getTaskList, but returns CalEvent instead of strings
    public ArrayList<CalEvent> getEventList()
    {

        ArrayList< CalEvent > eventList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN, DB_COLUMN1, DB_COLUMN2},null,null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);
            String taskName = cursor.getString(index);
            String taskFav = cursor.getString(index + 1);
            String taskDate = cursor.getString(index + 2);
            eventList.add(new CalEvent(taskName, taskDate, taskFav));
        }
        cursor.close();
        db.close();
        return eventList;
    }

    //like getTaskList, but returns CalendarDay instead of String
    public ArrayList<CalendarDay> getCalendarDayList()
    {

        ArrayList< CalendarDay > eventList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN, DB_COLUMN1, DB_COLUMN2},null,null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);
            String taskDate = cursor.getString(index + 2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
            CalendarDay cday;
            Date date;
            try {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(taskDate);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date(0,0,0);
            }
            cday = CalendarDay.from(date);
            eventList.add(cday);
        }
        cursor.close();
        db.close();
        return eventList;
    }

    //like getTaskList, but returns CalEvent instead of strings
    public ArrayList<String> getEventFromDay(String date)
    {
        ArrayList< String > eventList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN, DB_COLUMN1, DB_COLUMN2},null,null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);
            String taskName = cursor.getString(index);
            String taskDate = cursor.getString(index + 2);
            if (date.equals(taskDate))//do not use == operator with strings
            {
                eventList.add(taskName);
            }
        }
        cursor.close();
        db.close();
        return eventList;
    }


}

