package com.sj.mycore.net;

import com.sj.mycore.app.ConfigKeys;
import com.sj.mycore.app.ProjectInit;
import com.sj.mycore.net.rx.RxRestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description:  连接retrofit
 */
public final class RestCreator {

    /**
     * 产生一个全局的retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = ProjectInit.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT=60;
        private static final OkHttpClient OK_HTTP_CLIENT=new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    //提供接口 让调用者的到retrofit对象
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE=RetrofitHolder.RETROFIT_CLIENT
                .create(RestService.class);
    }

    /**
     * 获取对象
     * @return
     */
    public  static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }




    //提供接口 让调用者的到retrofit对象
    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE=RetrofitHolder.RETROFIT_CLIENT
                .create(RxRestService.class);
    }

    /**
     * 获取 rx对象
     * @return
     */
    public  static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }


}
