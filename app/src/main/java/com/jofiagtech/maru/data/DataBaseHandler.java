package com.jofiagtech.maru.data;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jofiagtech.maru.model.Meeting;
import com.jofiagtech.maru.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper
{
    private final Context mContext;

    public DataBaseHandler(Context context)
    {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_MEETING_TABLE = "CREATE TABLE " + Constants.TABLE_NAME
                + "("
                + Constants.COLUMN_ID + "INTEGER PRIMARY KEY"
                + Constants.COLUMN_SUBJECT + "TEXT, "
                + Constants.COLUMN_TIME + "TEXT, "
                + Constants.COLUMN_PLACE + "TEXT, "
                + Constants.COLUMN_NUMBER_OF_PARTICIPANT + "INTEGER"
                + ");";

        db.execSQL(CREATE_MEETING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME);
        onCreate(db);
    }

    private ContentValues getTableValue(Meeting meeting){
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_SUBJECT, meeting.getSubject());
        values.put(Constants.COLUMN_TIME, meeting.getTime());
        values.put(Constants.COLUMN_PLACE, meeting.getPlace());
        values.put(Constants.COLUMN_NUMBER_OF_PARTICIPANT, meeting.getNumberOfParticipant());

        return values;
    }
    public void addMeeting(Meeting meeting){
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(Constants.TABLE_NAME, null, getTableValue(meeting));
    }

    public Meeting getMeeting(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Meeting meeting = new Meeting();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.COLUMN_ID,
                        Constants.COLUMN_SUBJECT,
                        Constants.COLUMN_TIME,
                        Constants.COLUMN_PLACE,
                        Constants.COLUMN_NUMBER_OF_PARTICIPANT},
                Constants.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            meeting.setId(cursor.getInt(0));
            meeting.setSubject(cursor.getString(1));
            meeting.setTime(cursor.getString(2));
            meeting.setPlace(cursor.getString(3));
            meeting.setNumberOfParticipant(cursor.getInt(4));
            cursor.close();
        }

        return meeting;
    }

    public List<Meeting> getAllMeeting(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Meeting> meetingList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.COLUMN_ID,
                        Constants.COLUMN_SUBJECT,
                        Constants.COLUMN_TIME,
                        Constants.COLUMN_PLACE,
                        Constants.COLUMN_NUMBER_OF_PARTICIPANT},
                Constants.COLUMN_ID + "=?",
                null ,null, null,
                Constants.COLUMN_TIME + "ASC");

        if (cursor.moveToFirst()){
            do {
                Meeting meeting = new Meeting();
                meeting.setId(cursor.getInt(0));
                meeting.setSubject(cursor.getString(1));
                meeting.setTime(cursor.getString(2));
                meeting.setPlace(cursor.getString(3));
                meeting.setNumberOfParticipant(cursor.getInt(4));

                meetingList.add(meeting);
            }while (cursor.moveToNext());

            cursor.close();
        }

        return meetingList;
    }

    public int updateMeeting(Meeting meeting){
        SQLiteDatabase db = this.getWritableDatabase();

        int update = db.update(Constants.TABLE_NAME, getTableValue(meeting),
                Constants.COLUMN_ID + "=?",
                new String[]{String.valueOf(meeting.getId())});

        db.close();

        return update;
    }

    public void deleteMeeting(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constants.TABLE_NAME, Constants.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public void deleteAllItem(){
        SQLiteDatabase db = this.getReadableDatabase();

        for (Meeting meeting : getAllMeeting()){
            db.delete(Constants.TABLE_NAME, Constants.COLUMN_ID + "=?",
                    new String[]{String.valueOf(meeting.getId())});
        }
    }

    public int getMeetingCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

}
