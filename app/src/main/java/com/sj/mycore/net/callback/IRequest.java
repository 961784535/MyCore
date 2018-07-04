package com.sj.mycore.net.callback;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description:请求状态返回
 */
public interface IRequest {
    void onRequestStart();
    void onRequestEnd();
}
