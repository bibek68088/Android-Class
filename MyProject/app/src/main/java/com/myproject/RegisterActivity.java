package com.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;

public class RegisterActivity extends AppCompatActivity {
    EditText firstname, lastname, username, email, password, confirmpassword;
    SharedPreferences sharedPreferences;

    DatabaseHelper databaseHelper;
    int id;
    Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        id = getIntent().getIntExtra("id",0);
        sharedPreferences = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(this);
        firstname = findViewById(R.id.first_name);
        lastname = findViewById(R.id.last_name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);
        register = findViewById(R.id.register);
        if(id!=0){
            Userinfo info = databaseHelper.getUserInfo(id+"");
            firstname.setText(info.firstname);
            lastname.setText(info.lastname);
            username.setText(info.username);
            email.setText(info.email);
            password.setText(info.password);
            confirmpassword.setText(info.password);
            register.setText("Update");

        }
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emptyFieldValidation(username) &&
                        emptyFieldValidation(firstname) &&
                        emptyFieldValidation(lastname) &&
                        emptyFieldValidation(password) &&
                        emailValidation(email)) {
                    String firstnameValue = firstname.getText().toString();
                    String lastnameValue = lastname.getText().toString();
                    String usernameValue = username.getText().toString();
                    String passwordValue = password.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("firstname", firstnameValue);
                    editor.putString("lastname", lastnameValue);
                    editor.putString("username", usernameValue);
                    editor.putString("password", passwordValue);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("username", usernameValue);
                    contentValues.put("password", passwordValue);
                    contentValues.put("firstname", firstnameValue);
                    contentValues.put("lastname", lastnameValue);
                    contentValues.put("email", email.getText().toString());
                    if(id==0) {
                        databaseHelper.insertUser(contentValues);

                    }else{
                        databaseHelper.updateUser(contentValues,String.valueOf(id));
                        finish();
                    }
//                editor.commit();
                    editor.apply();
                    Toast.makeText(RegisterActivity.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean emailValidation(EditText view) {
        String value = view.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return true;
        } else {
            view.setError("Invalid Email address");
            return false;
        }
    }

    public boolean emptyFieldValidation(EditText view) {
        String value = view.getText().toString();
        if (value.length() > 4) {
            return true;
        } else {
            view.setError("Enter value atleast ...");
            return false;
        }
    }


}
