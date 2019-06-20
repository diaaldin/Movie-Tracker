package com.fina.musta.movietracker.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.fina.musta.movietracker.R;
import com.fina.musta.movietracker.helper.InputValidation;
import com.fina.musta.movietracker.model.User;
import com.fina.musta.movietracker.sql.DbHelper;

/**
 * Created by musta on 1/3/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUser;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextUser;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextView;

    private InputValidation inputValidation;
    private DbHelper databaseHelper;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        initViews();
        initListeners();
        initObjects();
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DbHelper(activity);
        user = new User();
    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextView.setOnClickListener(this);
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView_register_id);
        textInputLayoutUser = findViewById(R.id.text_input_layout_user_register_id);
        textInputLayoutPassword = findViewById(R.id.text_input_layout_password_register_id);
        textInputLayoutConfirmPassword = findViewById(R.id.text_input_layout_confirm_password_register_id);
        textInputEditTextUser = findViewById(R.id.user_input_register_id);
        textInputEditTextPassword= findViewById(R.id.password_input_register_id);
        textInputEditTextConfirmPassword = findViewById(R.id.confirm_password_input_register_id);
        appCompatButtonRegister = findViewById(R.id.register_button_id);
        appCompatTextView = findViewById(R.id.to_login_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.register_button_id:
                postDataToSQLite();
                break;
            case R.id.to_login_id:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if(!inputValidation.IsInputEditTextFilled(textInputEditTextUser,textInputLayoutUser,"Please fill user"))
        {
            return;
        }
        if(!inputValidation.IsInputEditTextFilled(textInputEditTextPassword,textInputLayoutConfirmPassword,"please fill password"))
        {
            return;
        }
        if(!inputValidation.IsInputEditTextFilled(textInputEditTextConfirmPassword,textInputLayoutConfirmPassword,"please fill confirm password"))
        {
            return;
        }
        if(!inputValidation.isInputEditTextMatches(textInputEditTextPassword,textInputEditTextConfirmPassword,textInputLayoutConfirmPassword,"passwords Dont match"))
        {
            return;
        }
        if(!databaseHelper.checkUser(textInputEditTextUser.getText().toString().trim()))
        {
            user.setName(textInputEditTextUser.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);
            Snackbar.make(nestedScrollView,"Registration successful",Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        }
        else
        {
            Snackbar.make(nestedScrollView,"User already registered",Snackbar.LENGTH_SHORT).show();

        }
    }
}
