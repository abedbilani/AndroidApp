package net.eduware.myapplication1.Database;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.eduware.myapplication1.Activities.inside_activity;

import java.util.HashMap;


public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_LAST_NAME + " TEXT, " +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD + " TEXT )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertUser(Context context, String username, String lastName, String pass) {
        // Submit your form here. your form is valid
        //Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();

        // Gets the data repository in write mode

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME, username);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LAST_NAME, lastName);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, pass);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = getWritableDatabase().insert(FeedReaderContract.FeedEntry.TABLE_NAME,
                null, values);
    }


    public void signIn(Context context, String user, String Pass) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " = ? ";
        String[] selectionArgs = {user};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_NAME + " ASC";
        Cursor cursor = getReadableDatabase().query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder
        );
        HashMap<String, String> credentials = new HashMap<String, String>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String userName = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NAME));
                String userPass = cursor.getString(cursor.getColumnIndexOrThrow
                        (FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD));
                credentials.put(userName, userPass);
            }
            cursor.close();
        }
        if (credentials.containsKey(user) && credentials.get(user).toString().equals(Pass)) {
            Intent intent = new Intent(context, inside_activity.class);
            context.startActivity(intent);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("User Name Or Password are wrong");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}