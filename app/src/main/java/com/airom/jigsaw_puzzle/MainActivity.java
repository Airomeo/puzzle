package com.airom.jigsaw_puzzle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airom.jigsaw_puzzle.GameActivity.MainView;
import com.airom.jigsaw_puzzle.ModeActivity.ModeActivity;
import com.airom.jigsaw_puzzle.Setting.SettingActivity;
import com.airom.jigsaw_puzzle.Utils.HttpUtil;

public class MainActivity extends BaseActivity {


    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HttpUtil.getDailyBingPic();
    }



    public void selectPattern(View v){
        Intent intent = new Intent(MainActivity.this,ModeActivity.class);
        startActivity(intent);
    }


    public void exit(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("退出");
        dialog.setMessage("再陪我玩一会好么？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
            }
        });
        dialog.show();
    }
    private int BGMImg=1;
    public void switchBGMImg(View v){
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        if (BGMImg==1) {
            imageButton.setImageResource(R.mipmap.bgmoff);
            BGMImg=0;
        }else{
            imageButton.setImageResource(R.mipmap.bgmon);
            BGMImg=1;
        }
    }
    private int SoundEffectImg=1;
    public void switchSoundEffectImg(View v){
        imageButton = (ImageButton) findViewById(R.id.imageButton1);
        if (SoundEffectImg==1) {
            imageButton.setImageResource(R.mipmap.soundeffectoff);
            SoundEffectImg=0;
        }else{
            imageButton.setImageResource(R.mipmap.soundeffecton);
            SoundEffectImg=1;
        }
    }

    public void setting(View v){
        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
    }

}
