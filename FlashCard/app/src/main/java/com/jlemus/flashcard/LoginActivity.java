package com.jlemus.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;
    private Button submitButton;
    private String userName = "Dude";
    private String passWord = "password";
    private String ok = "ok";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword = (EditText) findViewById(R.id.etPassWord);
        etUserName = (EditText) findViewById(R.id.etUserName);
        submitButton = (Button) findViewById(R.id.btnSubmitUserNamePass);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String submittedUserName = etUserName.getText().toString();
                String submittedPassWrod = etPassword.getText().toString();

                if(submittedPassWrod.equals(passWord) && submittedUserName.equals(userName)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username", userName);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong username or password. Please " +
                            "enter them again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
