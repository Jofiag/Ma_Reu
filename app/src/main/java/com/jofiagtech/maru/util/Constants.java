package com.jofiagtech.maru.util;

import android.provider.BaseColumns;

public class Constants implements BaseColumns
{
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "meetingList";
    public static final String TABLE_NAME = "meeting_table";

    //meeting table columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_NUMBER_OF_PARTICIPANT = "number_of_participant";
    public static final String COLUMN_DATE = "date";



}
