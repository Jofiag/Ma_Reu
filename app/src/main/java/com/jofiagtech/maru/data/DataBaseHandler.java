package com.jofiagtech.maru.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jofiagtech.maru.model.Meeting;
import com.jofiagtech.maru.model.Participant;
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

        String CREATE_PARTICIPANT_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_B
                + "("
                + Constants.COLUMN_ID_B + "INTEGER PRIMARY KEY"
                + Constants.COLUMN_EMAIL + "TEXT"
                + ");";

        db.execSQL(CREATE_MEETING_TABLE);
        db.execSQL(CREATE_PARTICIPANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME_B);

        onCreate(db);
    }

    //////////////////// MEETING TABLE ////////////////////

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


    //////////////////// PARTICIPANT TABLE ////////////////////

        public void addParticipant(Participant participant){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_EMAIL, participant.getEmail());

            db.insert(Constants.TABLE_NAME_B, null, values);
        }

        public Participant getParticipant(int id){
            SQLiteDatabase db = this.getReadableDatabase();

            Participant participant = new Participant();

            Cursor cursor = db.query(Constants.TABLE_NAME_B,
                    new String[]{Constants.COLUMN_ID_B, Constants.COLUMN_EMAIL},
                    Constants.COLUMN_ID_B + "=?",
                    null, null, null, null);

            if (cursor != null){
                participant.setId(cursor.getInt(0));
                participant.setEmail(cursor.getString(1));

                cursor.close();
            }

            return participant;
        }

        public List<Participant> getAllParticipant(){
            SQLiteDatabase db = this.getReadableDatabase();

            List<Participant> participantList = new ArrayList<>();


            Cursor cursor = db.query(Constants.TABLE_NAME_B,
                    new String[]{Constants.COLUMN_ID_B, Constants.COLUMN_EMAIL},
                    Constants.COLUMN_ID_B + "=?",
                    null, null, null, null);

            if (cursor.moveToNext()){
                do{
                    Participant participant = new Participant();

                    participant.setId(cursor.getInt(0));
                    participant.setEmail(cursor.getString(1));

                    participantList.add(participant);
                }while (cursor.moveToNext());

                cursor.close();
            }

            return participantList;
        }

        public int updateParticipant(Participant participant){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_EMAIL, participant.getEmail());

            int update = db.update(Constants.TABLE_NAME_B, values,
                    Constants.COLUMN_ID_B + "=?",
                    new String[]{String.valueOf(participant.getId())});

            db.close();

            return update;
        }

        public void deleteParticipant(int id){
            SQLiteDatabase db = this.getWritableDatabase();

            db.delete(Constants.TABLE_NAME_B, Constants.COLUMN_ID_B + "=?",
                    new String[]{String.valueOf(id)});

            db.close();
        }

        public void deleteAllParticipant(){
            SQLiteDatabase db = this.getReadableDatabase();

            for (Participant participant : getAllParticipant()){
                db.delete(Constants.TABLE_NAME_B, Constants.COLUMN_ID_B + "=?",
                        new String[]{String.valueOf(participant.getId())});
            }
        }

        public int getParticipantCount(){
            SQLiteDatabase db = this.getReadableDatabase();

            String countQuery = "SELECT * FROM " + Constants.TABLE_NAME_B;

            Cursor cursor = db.rawQuery(countQuery, null);

            int count = cursor.getCount();

            cursor.close();

            return count;
        }

}
