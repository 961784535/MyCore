package com.sj.mycore.net.callback;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description: 返回错误结果
 */
public interface IError {
    void onError(int code,String msg);
}
