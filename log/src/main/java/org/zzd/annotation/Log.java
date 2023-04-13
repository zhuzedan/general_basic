package org.zzd.annotation;

import org.zzd.enums.BusinessType;
import org.zzd.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author :zzd
 * @apiNote :自定义操作日志记录注解
 * @date : 2023-03-02 14:54
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
