package com.airom.jigsaw_puzzle.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BingPic {
    @SerializedName("images")
    public List<BaseBingPic> images;

    public class BaseBingPic {
        @SerializedName("url")
        public String bingBasePicUrl;//这里的链接还需要加上微软的http://cn.bing.com在前面

        @SerializedName("enddate")
        public String endDate;//最后更新的时间

        @SerializedName("startdate")
        public String startDate;

    }

}
