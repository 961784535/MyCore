package com.sj.mycore;

import android.app.Application;

import com.sj.mycore.app.ProjectInit;

import java.util.ArrayList;

import okhttp3.Interceptor;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description: application 中配置  Projectinit
 */
public class CoreApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProjectInit.init(this)
                .withApiHost("http://120.76.205.241:8000/")
              //  .withApiHost("http://dengpaoedu.com:8080")
                .withInterreceptors(new ArrayList<Interceptor>())
                .configure();
    }
}
