package com.fina.musta.movietracker.helper;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by musta on 1/3/2018.
 */

public class InputValidation {
    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }
    public boolean IsInputEditTextFilled(TextInputEditText editText, TextInputLayout inputLayout,String message){
        String value = editText.getText().toString();
        if(value.isEmpty())
        {
            inputLayout.setError(message);
            hideKeyboardFrom(editText);
            return false;
        }
        else{
            inputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean isInputEditTextMatches(TextInputEditText editText,TextInputEditText editText2,TextInputLayout inputLayout,String message){
        String value1 = editText.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if(!value1.contentEquals(value2)){
            inputLayout.setError(message);
            hideKeyboardFrom(editText2);
            return false;
        }
        else{
            inputLayout.setErrorEnabled(false);
        }
        return true;

    }

    private void hideKeyboardFrom(View editText) {
        InputMethodManager iam = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        iam.hideSoftInputFromWindow(editText.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
