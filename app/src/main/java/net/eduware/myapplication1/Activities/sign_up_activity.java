package net.eduware.myapplication1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import net.eduware.myapplication1.Database.FeedReaderDbHelper;
import net.eduware.myapplication1.Functions.validation;
import net.eduware.myapplication1.R;

public class sign_up_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameText;
    private EditText lastNameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        registerViews();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.submit_user:
                if (checkValidation()) {
                    FeedReaderDbHelper c = new FeedReaderDbHelper(this);
                    c.insertUser(this, ((EditText) findViewById(R.id.txt_name)).getText().toString(),
                            ((EditText) findViewById(R.id.txt_lastname)).getText().toString(),
                            ((EditText) findViewById(R.id.txt_password)).getText().toString());
                    ((EditText) findViewById(R.id.txt_name)).setText("");
                    ((EditText) findViewById(R.id.txt_lastname)).setText("");
                    ((EditText) findViewById(R.id.txt_password)).setText("");
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            default:
                break;
        }
    }

    public void registerViews() {
        nameText = (EditText) findViewById(R.id.txt_name);
        // TextWatcher would let us check validation error on the fly
        nameText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                validation.hasText(nameText);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        lastNameText = (EditText) findViewById(R.id.txt_lastname);
        lastNameText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                validation.hasText(lastNameText);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        passwordText = (EditText) findViewById(R.id.txt_password);
        passwordText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                validation.hasText(passwordText);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private boolean checkValidation() {
        boolean valid = true;

        if (!validation.hasText(nameText)) valid = false;
        if (!validation.hasText(lastNameText)) valid = false;
        if (!validation.hasText(passwordText)) valid = false;

        return valid;
    }


}
