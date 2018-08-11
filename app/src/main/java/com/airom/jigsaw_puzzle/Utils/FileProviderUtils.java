package com.airom.jigsaw_puzzle.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

public class FileProviderUtils {
    /**
     * 为Intent中文件路径的Uri，授予读写的权限
     * @param context
     * @param intent
     * @param uri
     */
    public static  void grantUriPermission(Context context , Intent intent, Uri uri){
        //        重要的一步，使用grantUriPermission来给对应的包提升读写指定uri的临时权限。否则即使调用成功，也会保存裁剪照片失败。

        List<ResolveInfo> resolveInfoList=context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo:resolveInfoList){
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
}
