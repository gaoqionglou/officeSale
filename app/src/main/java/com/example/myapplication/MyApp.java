package com.example.myapplication;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 创建bmob数据库的唯一标识码，在bmob数据库的设置里面可以看到
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("38127bee7eab45f1032c3479842ff2a9")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }
}
