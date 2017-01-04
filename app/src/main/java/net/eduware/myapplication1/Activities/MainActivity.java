package net.eduware.myapplication1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import net.eduware.myapplication1.Database.FeedReaderDbHelper;
import net.eduware.myapplication1.Functions.validation;
import net.eduware.myapplication1.R;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
//        startActivity(mapIntent);

//        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(2012, 0, 19, 7, 30);
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2012, 0, 19, 10, 30);
//        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
//        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
//        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
//        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
//        startActivity(calendarIntent);

        String title = "new app intent";
// Create intent to show chooser
        Intent chooser = Intent.createChooser(mapIntent, title);

// Verify the intent will resolve to at least one activity
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}
