package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.maplehomeapp.R;
import com.assignment.maplehomeapp.remote.Communicator;
import com.assignment.maplehomeapp.utils.CommonDataArea;
import com.squareup.otto.Bus;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    EditText userName, password;
    Button signBtn;
    private Communicator communicator;
    public static Bus bus;
    private boolean patientid;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        communicator = new Communicator(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        userName = (EditText) findViewById(R.id.username_edt);
        password = (EditText) findViewById(R.id.passwd_edt);
        signBtn = (Button) findViewById(R.id.signin_button);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userName.getText().toString().equalsIgnoreCase("") && !password.getText().toString().equalsIgnoreCase("")) {
                    CommonDataArea.passwdEdt=password;
                    CommonDataArea.userName=userName;
                  communicator.loginGet(userName.getText().toString(), password.getText().toString()).equalsIgnoreCase("");



                } else {
                    Toast.makeText(LoginActivity.this, "Invalid user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (isShow) {
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            isShow = false;
                        } else {
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            isShow = true;
                        }
                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
