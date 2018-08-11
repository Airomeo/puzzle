//package com.airom.jigsaw_puzzle.Utils;
//
//import com.google.gson.Gson;
//
//import java.io.IOException;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class BingPic {
//
//    private void getDailyBingPic1() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder().url(url).build();
//                    Response response = client.newCall(request).execute();
//                    String content=response.body().string() ;
//                    //response.body().string() 不容许同时出现两次
//                    //   System.out.println(response.body().string() + "-------------" + "okhtpp");
//
//                    BingPic bingPic = new Gson().fromJson(content, BingPic.class);
//                    System.out.println(bingPic + "-------------");
////                    System.out.println(bingPic.images.get(0).bingBasePicUrl + "-------------");
////                    System.out.println(bingPic.images.get(0).endDate + "-------------");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//    }
//
//
//    public void getDailyBingPic() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
//                String content = getHtmlStr1(url);
//                BingPic bingPic = new Gson().fromJson(content, BingPic.class);
//                System.out.println(bingPic + "-------------");
//                System.out.println(bingPic.images.get(0).bingBasePicUrl + "-------------");
//                System.out.println(bingPic.images.get(0).endDate + "-------------");
//
//            }
//        }).start();
//
//
//    }
//}
