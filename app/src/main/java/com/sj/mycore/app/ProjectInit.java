package com.sj.mycore.app;

import android.content.Context;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description:  项目初始化
 */
public class ProjectInit {

    public static Configurator init(Context context) {
        Configurator.getInstace()
                .getConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstace();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstace();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT.name());
    }


}
