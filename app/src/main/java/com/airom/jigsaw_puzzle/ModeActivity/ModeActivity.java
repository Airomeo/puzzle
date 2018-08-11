package com.airom.jigsaw_puzzle.ModeActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.airom.jigsaw_puzzle.BaseActivity;
import com.airom.jigsaw_puzzle.GameActivity.GameActivity;
import com.airom.jigsaw_puzzle.R;
import com.airom.jigsaw_puzzle.Utils.SystemPhotoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Random;

public class ModeActivity extends BaseActivity {

    private Spinner imgSpinner;
    private Spinner modeSpinner;
    private static ImageView imageView;
    private String[] imgArray;
    private String[] modeArray;
    private ArrayAdapter<String> imgAdapter;
    private ModeAdapter modeAdapter;
    private static int COL;
    private static int ROW;
    private static Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        initView();
        initListener();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onDestroy() {
        img=null;
        COL = 0;
        ROW = 0;

        super.onDestroy();
    }

    private void initView() {
        imgSpinner = (Spinner)findViewById(R.id.imgSpinner);
        modeSpinner = (Spinner)findViewById(R.id.modeSpinner);
        imageView = findViewById(R.id.imageView);

        //准备需要加载的数据源
        imgArray = getResources().getStringArray(R.array.selectImg);
        modeArray = getResources().getStringArray(R.array.mode_type);
        //将数据源的数据加载到适配器中(context，上下文对象，resource列表item的布局资源id，objetcs，加载的数据源)
//        imgAdapter = new ArrayAdapter<String>(com.airom.jigsaw_puzzle.ModeActivity.this,android.R.layout.simple_spinner_item,img);
//        modeAdapter = new ArrayAdapter<String>(com.airom.jigsaw_puzzle.ModeActivity.this,android.R.layout.simple_spinner_item,mode);
        imgAdapter = new ModeAdapter(com.airom.jigsaw_puzzle.ModeActivity.ModeActivity.this,android.R.layout.simple_spinner_item,imgArray);
        modeAdapter = new ModeAdapter(com.airom.jigsaw_puzzle.ModeActivity.ModeActivity.this,android.R.layout.simple_spinner_item,modeArray);

        //将适配器中的数据加载到控件中
        imgSpinner.setAdapter(imgAdapter);
        modeSpinner.setAdapter(modeAdapter);
        //将最后一个隐藏的选项显示出来
        imgSpinner.setSelection(imgAdapter.getCount(), true);
        modeSpinner.setSelection(modeAdapter.getCount(), true);

    }




    private void initListener() {

        imgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
            表示当spinner控件中item被选中时回调的方法
            AdapterView<?> parent, 表示当前出发时间的适配器控件对象spinner
            View view, 表示当前被选中的item的对象
            int position, 表示当前被选中的item的下标位置
            long id, 表示当前被选中的item的id
             */

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    //以下三行代码是解决问题所在
                    Field field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
                    field.setAccessible(true);  //设置mOldSelectedPosition可访问
                    field.setInt(imgSpinner, AdapterView.INVALID_POSITION); //设置mOldSelectedPosition的值
                } catch(Exception e){
                    e.printStackTrace();
                }

                switch (position){
                    case 0:
                        pickPhoto();
//                        imageView.setImageBitmap(getImg());
                        Toast.makeText(ModeActivity.this,"pickPhoto",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //拍摄照片
                        takePhoto();
//                        imageView.setImageBitmap(getImg());

                        break;
                    case 2:
                        localPhoto(ModeActivity.this);
                        imageView.setImageBitmap(getImg());
                        break;
                    case 3:
//                        imageView.setImageBitmap(getImg());

                        break;

                    default:

                        break;

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    //以下三行代码是解决问题所在
                    Field field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
                    field.setAccessible(true);  //设置mOldSelectedPosition可访问
                    field.setInt(modeSpinner, AdapterView.INVALID_POSITION); //设置mOldSelectedPosition的值
                } catch(Exception e){
                    e.printStackTrace();
                }

                switch (position){
                    case 0:
                        COL = 3;
                        ROW = 3;
                        break;
                    case 1:
                        COL = 4;
                        ROW = 4;
                        break;
                    case 2:
                        COL = 5;
                        ROW = 5;
                        break;
                    case 3:
                        COL = 6;
                        ROW = 6;
                        break;
                    case 4:
                        COL = 7;
                        ROW = 7;
                        break;
                    case 5:
                        COL = 8;
                        ROW = 8;
                        break;
                    default:

                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    public void startGame(View view){
        if (img == null){
            Toast.makeText(this,"你还没有选择图片哦(。・＿・。)ﾉ",Toast.LENGTH_SHORT).show();

        }else if (COL==0 || ROW == 0){
            Toast.makeText(this,"你还没有选择难度哦(＞人＜)",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(com.airom.jigsaw_puzzle.ModeActivity.ModeActivity.this,GameActivity.class);
            startActivity(intent);
        }


    }


    public static int getCOL(){
        return COL;
    }

    public static int getROW(){
        return ROW;
    }

    public static Bitmap getImg(){
        img=Bitmap.createScaledBitmap(img,GameActivity.getScreenWidth(),GameActivity.getScreenWidth(),true);
        return img;
    }



        //创建一个对象
        SystemPhotoUtil  photoUtil = new SystemPhotoUtil();


    public void takePhoto(){
        //设置你要截取的图片的大小,默认是250*250的
        photoUtil.setCropPhotoSize(GameActivity.getScreenWidth(),GameActivity.getScreenWidth());
        //拍摄照片
        photoUtil.takePhoto(ModeActivity.this);
    }

    public void pickPhoto(){
        //设置你要截取的图片的大小,默认是250*250的
        photoUtil.setCropPhotoSize(GameActivity.getScreenWidth(),GameActivity.getScreenWidth());
        //选择一张照片
        photoUtil.pickPhoto(ModeActivity.this);
    }

//    public void bingPhoto(){
//
//
//    }

    public void localPhoto(Context context){
        String fileName = null;
        Random rd=new Random();
        int num=rd.nextInt(6);//产生0~5的随机数
        switch (num){
            case 0:
                fileName = "0.jpg";
                break;

            case 1:
                fileName = "1.jpg";
                break;

            case 2:
                fileName = "2.jpg";
                break;

            case 3:
                fileName = "3.jpg";
                break;

            case 4:
                fileName = "4.jpg";
                break;

            case 5:
                fileName = "5.jpg";
                break;

        }

        //                载入图像
        AssetManager assetManager= context.getAssets();
        try {
            InputStream assetInputStream=assetManager.open(fileName);
            img= BitmapFactory.decodeStream(assetInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //你可以在 onActivityResult方法中获取到截取的bitmap对象,并使用它
    private static final String TAG = "ModeActivity";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = photoUtil.onResult(this, requestCode, resultCode, data);
        if (bitmap != null) {//这里必须判空
            img = bitmap;
            imageView.setImageBitmap(img);
            Toast.makeText(this,"getbitmap ok",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"好像没有选择图片哦",Toast.LENGTH_SHORT).show();

        }
    }
}
