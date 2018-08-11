package com.airom.jigsaw_puzzle.Setting;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.airom.jigsaw_puzzle.BaseActivity;
import com.airom.jigsaw_puzzle.R;
import com.airom.jigsaw_puzzle.Utils.HttpUtil;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SettingActivity extends BaseActivity {

    private boolean isOnline = true;
    private ImageView bingPicImg;


    public void setting(View v){

    }
    private List<Setting> settingList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initSetting();
loadBingPic();
    }

    private void initSetting() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        bingPicImg = findViewById(R.id.bing_pic_img);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        SettingAdapter adapter = new SettingAdapter(settingList);
        recyclerView.setAdapter(adapter);

//        String bingPic = SharedPreferences.getString("bing_pic",null);
        String bingPic = null;
        if (bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else {
            loadBingPic();
        }


        if(isOnline){
            Setting user = new Setting ("个人中心",R.mipmap.enter);
            settingList.add(user);
        }else{
            Setting register = new Setting ("没有账号？前往注册",R.mipmap.enter);
            settingList.add(register);
            Setting login = new Setting ("已有账号？前往登录",R.mipmap.enter);
            settingList.add(login);
        }

        Setting language = new Setting("切换语言",R.mipmap.enter);
        settingList.add(language);
        Setting suggestions = new Setting("投诉/建议",R.mipmap.enter);
        settingList.add(suggestions);
        Setting checkUpdate = new Setting("检测更新",R.mipmap.enter);
        settingList.add(checkUpdate);
        Setting aboutUs = new Setting("关于我们",R.mipmap.enter);
        settingList.add(aboutUs);


    }

    public void loadBingPic(){//加载bing每日一图
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(SettingActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

}
