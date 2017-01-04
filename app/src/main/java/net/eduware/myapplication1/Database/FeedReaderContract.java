package net.eduware.myapplication1.Database;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {
    }

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_LAST_NAME = "Last_Name";
        public static final String COLUMN_NAME_PASSWORD = "Password";

    }


}