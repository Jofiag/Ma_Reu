package com.jofiagtech.maru.util;

import android.provider.BaseColumns;

public class ConstantsB implements BaseColumns
{
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "participantList";
    public static final String TABLE_NAME_B = "participant_table";

    //participant table column
    public static final String COLUMN_ID_B = "participant_id";
    public static final String COLUMN_EMAIL = "email";
}
