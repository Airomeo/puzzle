package com.airom.jigsaw_puzzle.GameActivity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airom.jigsaw_puzzle.BaseActivity;
import com.airom.jigsaw_puzzle.ModeActivity.ModeActivity;
import com.airom.jigsaw_puzzle.R;

public class GameActivity extends BaseActivity{

    private static MainView mainView;
    private static int screenWidth;
    private static int screenHeight;
    private static boolean gameStatus = true;
    private static ImageButton imageButton;
    private static long time;
    private static Chronometer timer;
    private static int steps = 0;
    private static TextView stepsView;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
//      设置窗体全屏，不会隐藏导航栏
//        getWindow().setFlags(1024,1024);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getScreenHeight();
        getScreenWidth();
        setContentView(R.layout.activity_game);

        mainView = (MainView) findViewById(R.id.puzzleLayout);
//        setContentView(new MainView(this));
        initView();

    }

    private void initView(){
        ImageView thumbnail = findViewById(R.id.thumbnail);
        timer = findViewById(R.id.timer);
        imageButton = (ImageButton) findViewById(R.id.pause);
        stepsView= findViewById(R.id.steps);
        thumbnail.setImageBitmap(ModeActivity.getImg());
        timer.start();
        resetSteps();
    }

    public static int getScreenWidth() {
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        return screenWidth;
    }
    //此方法不包含导航栏高度
    public static int getScreenHeight() {
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        return screenHeight;
    }

    public static void switchGameStatus(View view){//更改游戏状态，暂停或继续，通过点击图片按钮触发，并计时
        if(gameStatus){
            timer.stop();//暂停计时
            time = SystemClock.elapsedRealtime();//当时暂停的时间

            gameStatus = !gameStatus;//更改游戏状态，
            imageButton.setImageResource(R.mipmap.go_on);//同时更改相反状态的图标，
        }else {
            if (time != 0) {
                timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - time));//一共计时的时间   减去   当时暂停的时间
            } else {
                timer.setBase(SystemClock.elapsedRealtime());//时间重置0
            }
            timer.start();//开始计时
            gameStatus = !gameStatus;//更改游戏状态，
            imageButton.setImageResource(R.mipmap.pause);//同时更改相反状态的图标，
        }

    }

    public static void countSteps(){//统计步数，每次+1，放在点击有效移动的碎图片后
        steps++;
        stepsView.setText("步数："+steps);

    }

    public static void resetSteps(){//重置步数，重新一局的时候用到
        steps=0;
        stepsView.setText("步数："+steps);
    }

    public static void resetTime(){//重置时间，重新一局时用到
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

    public static long getTime(){//获取当前的时间，并暂停计时
        time=(SystemClock.elapsedRealtime()-timer.getBase());
        timer.stop();
        return time;
    }

    public static int getSteps(){//获取当前的步数
        return steps;
    }

}
