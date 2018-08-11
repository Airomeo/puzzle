package com.airom.jigsaw_puzzle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstInActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstin);

    }


    public void login(View v){
        Intent intent = new Intent(FirstInActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void menu(View v){
        Intent intent = new Intent(FirstInActivity.this,MainActivity.class);
        startActivity(intent);
    }


}
