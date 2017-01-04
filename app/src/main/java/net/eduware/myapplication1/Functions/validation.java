package net.eduware.myapplication1.Functions;

import android.widget.EditText;


public class validation {

    // Error Messages
    private static final String REQUIRED_MSG = "required";

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        return !(required && !hasText(editText));

    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.isEmpty()) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        return true;
    }

}
