package com.example.mini_projet_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mini_projet_03.Adapters.VPAdapter;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    ViewPager2 vp2_IntroActMessages;
    ArrayList<String> messages;
    Button btn_IntroActSkip;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        vp2_IntroActMessages = findViewById(R.id.vp2_IntroActMessages);
        btn_IntroActSkip = findViewById(R.id.btn_IntroActSkip);

        //region Handle First time opened
        sharedPreferences = getSharedPreferences("checkFirstTimeOpened", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean isFirstTimeOpened = sharedPreferences.getBoolean("firstTime", false);

        if (isFirstTimeOpened) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        } else {
            btn_IntroActSkip.setOnClickListener(view -> {
                editor.putBoolean("firstTime", true);
                editor.commit();
                startActivity(new Intent(this, StartActivity.class));
            });
        }
        //endregion

        //region Handle ViewPager2 : vp2_IntroActMessages
        messages = new ArrayList<>();
        messages.add("Hi");
        messages.add("This app. let you manage your quotes");
        messages.add("Enjoy...");

        VPAdapter vpAdapter = new VPAdapter(messages);

        vp2_IntroActMessages.setAdapter(vpAdapter);
        vp2_IntroActMessages.setClipChildren(false);
        vp2_IntroActMessages.setClipToPadding(false);
        vp2_IntroActMessages.setOffscreenPageLimit(2);
        vp2_IntroActMessages.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        //endregion

    }
}