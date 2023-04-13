package org.zzd.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author :zzd
 * @apiNote :异常工具类
 * @date : 2023-03-07 10:13
 */
public class ThrowableUtil {

    /**
     * @apiNote 获取堆栈信息
     * @date 2023/3/7 10:13
     * @param throwable: 异常
     * @return java.lang.String
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
