package com.fina.musta.movietracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.fina.musta.movietracker.sql.DbHelper;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Login.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutUser;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditUser;
    private TextInputEditText textInputEditPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DbHelper databaseHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListener();
        initObjects();

    }
    private void initViews(){
        nestedScrollView = findViewById(R.id.nestedScrollView_login_id);
        textInputLayoutUser = findViewById(R.id.text_input_layout_user_login_id);
        textInputLayoutPassword = findViewById(R.id.text_input_layout_password_login_id);
        textInputEditUser = findViewById(R.id.user_input_login_id);
        textInputEditPassword = findViewById(R.id.password_input_login_id);
        appCompatButtonLogin = findViewById(R.id.login_button_id);
        textViewLinkRegister = findViewById(R.id.to_register_id);

    }
    private void initListener(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }
    private void initObjects(){
        databaseHelper = new DbHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_button_id:

                verifyFromSQLite();
                break;
            case R.id.to_register_id:
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
                break;
        }

    }

    private void verifyFromSQLite() {
        if(!inputValidation.IsInputEditTextFilled(textInputEditUser,textInputLayoutUser,"Please Enter UserName"))
        return;
        if(!inputValidation.IsInputEditTextFilled(textInputEditPassword,textInputLayoutPassword,"Please Enter Password"))
            return;
        if(databaseHelper.checkUser(textInputEditUser.getText().toString().trim(),textInputEditPassword.getText().toString().trim())){
            final  Intent i = new Intent(activity,MainActivity.class);
            i.putExtra("user",textInputEditUser.getText().toString().trim());
            Snackbar.make(nestedScrollView,"Login Successful",Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(i);
                    finish();
                }
            }, 2000);

        }
        else
        {
            Snackbar.make(nestedScrollView,"Error Wrong Login Details",Snackbar.LENGTH_SHORT).show();
        }
    }
}
