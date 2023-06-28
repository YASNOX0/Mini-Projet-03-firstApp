package com.example.mini_projet_03;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    ImageView iv_startAct;
    InternetReceiver broadcastReceiver;
    Button btn_startActGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn_startActGo = findViewById(R.id.btn_startActGo);

        iv_startAct = findViewById(R.id.iv_startAct);
        broadcastReceiver = InternetReceiver.newInstance(iv_startAct);

        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        btn_startActGo.setOnClickListener(view->{
            startActivity(new Intent(this , QuotesListActivity.class));
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


}