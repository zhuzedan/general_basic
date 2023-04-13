package org.zzd.annotation;

import java.lang.annotation.*;

/**
 * @author :zzd
 * @apiNote :登录日志注解
 * @date : 2023-03-13 10:08
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
}
