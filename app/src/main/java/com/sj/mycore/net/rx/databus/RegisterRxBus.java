package com.sj.mycore.net.rx.databus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by arJun on 2018/6/8.
 */
@Target(ElementType.METHOD)//标识在方法上
@Retention(RetentionPolicy.RUNTIME)//运行时执行
@Documented
public @interface RegisterRxBus {
}
