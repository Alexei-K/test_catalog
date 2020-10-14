package com.kolis.test_catalog_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT;

public class RegisterActivity extends AppCompatActivity {
    public static String IS_LOGGED_PREF = "is_logged_in_app";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkRegistration();

        setContentView(R.layout.activity_register);
        ViewPager pager = findViewById(R.id.register_viewPager);
        TabLayout tabs = findViewById(R.id.register_tabs);
        RegisterTabsAdapter adapter = new RegisterTabsAdapter(getSupportFragmentManager(), BEHAVIOR_SET_USER_VISIBLE_HINT);
        adapter.addFragment(0, new RegistrationInfoFragment(R.drawable.registration_1,
                getString(R.string.welcome_to_fluxstore), getString(R.string.register_screen_text)));
        adapter.addFragment(1, new RegistrationInfoFragment(R.drawable.registration_2,
                getString(R.string.second_register_screen_title), getString(R.string.register_screen_text)));
        adapter.addFragment(2, new RegistrationInfoFragment(R.drawable.registration_3,
                "", getString(R.string.register_screen_text)));
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
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

    private void checkRegistration() {
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        if (pref.getBoolean(IS_LOGGED_PREF, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
}
