package com.airom.jigsaw_puzzle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;

import java.util.Random;

public class BaseActivity extends AppCompatActivity{
    private static SoundPool soundPool;//声明一个SoundPool
    private static int soundID0;//创建某个声音对应的音频ID
    private static int soundID1;//创建某个声音对应的音频ID
    private static int soundID2;//创建某个声音对应的音频ID
    private static int soundID3;//创建某个声音对应的音频ID
    private static int soundID4;//创建某个声音对应的音频ID
    private static int soundID5;//创建某个声音对应的音频ID
    private static int soundID6;//创建某个声音对应的音频ID
    private static int soundID7;//创建某个声音对应的音频ID
    private static int soundID8;//创建某个声音对应的音频ID
    private static int soundID9;//创建某个声音对应的音频ID
    private static int soundID10;//创建某个声音对应的音频ID
    private static int soundID11;//创建某个声音对应的音频ID


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSound();
        ActivityCollector.addActivity(this);
        verifyStoragePermissions(this);

        //严苛模式
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ActivityCollector.removeActivity(this);

    }


    protected void hideBottomUIMenu() {//隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);

        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.

            Window window = getWindow();

            View decorView = window.getDecorView();
            int uiOptions =View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);

        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {//请求文件存储权限
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }


    private void initSound() {
        if (Build.VERSION.SDK_INT>=21){
            soundPool = new SoundPool.Builder().build();

        }else {
            soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 0);
        }


        soundID0 = soundPool.load(this, R.raw.phone_key_pressed_piano_0, 1);
        soundID1 = soundPool.load(this, R.raw.phone_key_pressed_piano_1, 1);
        soundID2 = soundPool.load(this, R.raw.phone_key_pressed_piano_2, 1);
        soundID3 = soundPool.load(this, R.raw.phone_key_pressed_piano_3, 1);
        soundID4 = soundPool.load(this, R.raw.phone_key_pressed_piano_4, 1);
        soundID5 = soundPool.load(this, R.raw.phone_key_pressed_piano_5, 1);
        soundID6 = soundPool.load(this, R.raw.phone_key_pressed_piano_6, 1);
        soundID7 = soundPool.load(this, R.raw.phone_key_pressed_piano_7, 1);
        soundID8 = soundPool.load(this, R.raw.phone_key_pressed_piano_8, 1);
        soundID9 = soundPool.load(this, R.raw.phone_key_pressed_piano_9, 1);
        soundID10 = soundPool.load(this, R.raw.phone_key_pressed_piano_10, 1);
        soundID11 = soundPool.load(this, R.raw.phone_key_pressed_piano_11, 1);

    }

    public static void playSound() {
        Random rd=new Random();
        int num=rd.nextInt(12);//产生0~11的随机数
        int soundID ;

        switch (num){
            case 0:
                soundID = soundID0;
                break;
            case 1:
                soundID = soundID1;
                break;
            case 2:
                soundID = soundID2;
                break;
            case 3:
                soundID = soundID3;
                break;
            case 4:
                soundID = soundID4;
                break;
            case 5:
                soundID = soundID5;
                break;
            case 6:
                soundID = soundID6;
                break;
            case 7:
                soundID = soundID7;
                break;
            case 8:
                soundID = soundID8;
                break;
            case 9:
                soundID = soundID9;
                break;
            case 10:
                soundID = soundID10;
                break;
            case 11:
                soundID = soundID11;
                break;
            default:
                soundID = soundID0;
                break;
        }

        soundPool.play(
                soundID,
                1.0f,   //左耳道音量【0~1】
                1.0f,   //右耳道音量【0~1】
                1,     //播放优先级【0表示最低优先级】
                0,     //循环模式【0表示播放一次，-1表示一直循环，n循环n+1】
                1     //播放速度【1是正常，范围从0~2】
        );
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        hideBottomUIMenu();
    }

}
