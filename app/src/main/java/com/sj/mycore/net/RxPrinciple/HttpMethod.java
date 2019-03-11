package com.sj.mycore.net.RxPrinciple;

/**
 * Date: 2018/6/7
 * Author: sj
 * Description: 网络请求类型
 */
public enum HttpMethod {
    GET,
    POST,
    POST_RAW,
    PUT,
    PUT_RAW,  //原始数据 （比如一组音频数据）
    DELETE,
    UPLOAD,   //文件上传
    DOWNLOAD   //下载
}
