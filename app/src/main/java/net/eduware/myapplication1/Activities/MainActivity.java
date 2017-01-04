package net.eduware.myapplication1.Activities;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import net.eduware.myapplication1.Database.FeedReaderDbHelper;
import net.eduware.myapplication1.Functions.validation;
import net.eduware.myapplication1.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sign_in:
                if (checkValidation()) {
                    FeedReaderDbHelper c = new FeedReaderDbHelper(this);
                    c.signIn(this, ((EditText) findViewById(R.id.user_login)).getText().toString(), (
                            (EditText) findViewById(R.id.user_password)).getText().toString());
                }
                break;
            case R.id.sign_up_main:
                signUp();
                break;
            case R.id.Map:
                showMap();
                break;
            case R.id.contact:
                pickContact();
                break;
            default:
                break;
        }
    }

    private void signUp() {
        Intent intent = new Intent(this, sign_up_activity.class);
        startActivity(intent);
    }

    private boolean checkValidation() {

        boolean valid = true;

        if (!validation.hasText((EditText) findViewById(R.id.user_login))) valid = false;
        if (!validation.hasText((EditText) findViewById(R.id.user_password))) valid = false;

        return valid;
    }

    private void showMap() {
//        Uri location = Uri.parse("geo::37.422219,-122.08364?z=14");
        //   Intent mapIntent = new Intent(Intent.ACTION_VIEW);
//        startActivity(mapIntent);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 10, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
        startActivity(calendarIntent);

//        String title = "new app intent";
//// Create intent to show chooser
//        Intent chooser = Intent.createChooser(mapIntent, title);
//
//// Verify the intent will resolve to at least one activity
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(chooser);
//        }
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                EditText setNumber = (EditText) findViewById(R.id.phone_number);
                setNumber.setText(number);
                // Do something with the phone number...
            }
        }
    }
}
