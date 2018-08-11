package com.airom.jigsaw_puzzle.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.airom.jigsaw_puzzle.BaseActivity;
import com.airom.jigsaw_puzzle.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class SystemPhotoUtil extends BaseActivity {

    public static final int PICK_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int TAKE_PHOTO = 3;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private static final String PHOTO_CUT_FILE_NAME = "temp_cut_photo.jpg";
    private File tempFile;
    private File tempCutFile;
    private Uri uri;//原图路径
    private Uri imageUri;//裁剪的图片路径
    private int width = 250;
    private int height = 250;

    private static final String TAG = "SystemPhotoUtil";
//    private void crop(Activity context, Uri uri) {//获取清晰图片
//        Intent intent = new Intent("com.android.camera.action.CROP");
//
////        Log.d(TAG, "crop: "+uri.getPath());
////        createFile(context,intent);
//
////判断存储卡是否可以用，可用进行存储
//        if (hasSdcard()) {
////            tempCutFile = new File(context.getExternalCacheDir(), PHOTO_CUT_FILE_NAME);
//            tempCutFile = new File(Environment.getExternalStorageDirectory(), PHOTO_CUT_FILE_NAME);
//            try {
//                if (tempCutFile.exists()){
//                    tempCutFile.delete();
//                }
//                tempCutFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//
//
//
//                imageUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", tempCutFile);
//                FileProviderUtils.grantUriPermission(context,intent,imageUri);
////                重要的一步，使用grantUriPermission来给对应的包提升读写指定uri的临时权限。否则即使调用成功，也会保存裁剪照片失败。
////                List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
////                for (ResolveInfo resolveInfo : resInfoList) {
////                    String packageName = resolveInfo.activityInfo.packageName;
////                    grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
////                }
//
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            } else {
//                // 从文件中创建uri
//                imageUri = Uri.fromFile(tempCutFile);
//            }
////            if (tempFile.exists()){
////                tempFile.delete();
////            }
//        }else {
//            Toast.makeText(context,"储存卡不可用，请检查权限",Toast.LENGTH_SHORT).show();
//        }
//
//        // 裁剪图片意图
//
//        intent.setDataAndType(uri, "image/*");//文件类型，*指图片的所有类型
//        intent.putExtra("crop", "true");
//        // 裁剪框的比例，1：1
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // 裁剪后输出图片的尺寸大小
//        intent.putExtra("outputX", width);
//        intent.putExtra("outputY", height);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());  // 图片格式
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", false);// 开始return-data设置了true的话直接返回bitmap，可能会很占内存
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//设置输出的地址
//
//
//
//        // 开启一个带有返回值的Activity，请求码为CROP_PHOTO
//        context.startActivityForResult(intent, CROP_PHOTO);
//    }


    private void crop(Activity context, Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");//文件类型，*指图片的所有类型
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// 开始return-data设置了true的话直接返回bitmap，可能会很占内存

        // 开启一个带有返回值的Activity，请求码为CROP_PHOTO
        context.startActivityForResult(intent, CROP_PHOTO);
    }


    public void setCropPhotoSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void pickPhoto(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        context.startActivityForResult(intent, PICK_PHOTO);
    }


    //相机
    public void takePhoto(Activity context) {

//        Uri uri = null;
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        //判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(context.getExternalCacheDir(), PHOTO_FILE_NAME);
//            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
//            try {
//                if (tempFile.exists()){
//                    tempFile.delete();
//                }
//                tempFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", tempFile);
//                grantUriPermission(context,intent,uri);

            } else {
                // 从文件中创建uri
                uri = Uri.fromFile(tempFile);
            }
        }else{
            Toast.makeText(context,"储存卡不可用，请检查权限",Toast.LENGTH_SHORT).show();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        context.startActivityForResult(intent, TAKE_PHOTO);
    }


    /*
     * 判断sdcard是否被挂载
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }



    private Bitmap decodeUriBitmap(Uri uri) {//获取清晰图片
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public Bitmap onResult(Activity context, int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return null;
        if (requestCode == PICK_PHOTO) {
            // 从相册返回的数据
            if (data != null) {
                uri = data.getData();
                crop(context, uri);
            }
        } else if (requestCode == TAKE_PHOTO) {

            if (hasSdcard()) {
                crop(context, uri);
            } else {
                Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CROP_PHOTO) {
            if (data != null) {
                //以下是获取清晰图片
//                imageUri = data.getData();
//                Bitmap bitmap = decodeUriBitmap(imageUri);
                Bitmap bitmap = data.getParcelableExtra("data");

                return bitmap;
            }else{

            }
        }
        return null;
    }

}
