package net.eduware.myapplication1.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.eduware.myapplication1.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DisplayMessageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.savetofile:
                saveToFile();
                break;
            case R.id.getdata:
                ((EditText) findViewById(R.id.datafromfile)).setText(getData());
                break;
            default:
                break;
        }
    }

    public void saveToFile() {
        String filename = "myfile";
        FileOutputStream outputStream;

        EditText editText = (EditText) findViewById(R.id.stofile);
        String string = editText.getText().toString();
        try {
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData() {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            FileInputStream fis = openFileInput("myfile");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}