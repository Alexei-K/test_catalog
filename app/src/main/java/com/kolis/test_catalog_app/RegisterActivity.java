package com.kolis.test_catalog_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    public static String IS_LOGGED_PREF = "is_logged_in_app";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);
//        Button registerButton = findViewById(R.id.registerButton);
//        Button unregisterButton = findViewById(R.id.unregisterButton);
//        registerButton.setOnClickListener(v -> {
//            SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
//            if (!pref.getBoolean(IS_LOGGED_PREF, false)) {
//                pref.edit().putBoolean(IS_LOGGED_PREF, true).apply();
//            } else {
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
//            }
//        });
//
//        unregisterButton.setOnClickListener(v -> {
//            SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
//                pref.edit().putBoolean(IS_LOGGED_PREF, false).apply();
//        });
    }

}
