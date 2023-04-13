package org.zzd.utils;

import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author :zzd
 * @apiNote :api通用工具类
 * @date : 2023-03-01 18:48
 */
public class ApiUtils {

    /**
     * @apiNote: 获取设备ip
     * @return: String
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * @apiNote: 将对象转为字符串数据
     * @param: [obj：带转换对象]
     * @return: java.lang.String
     */
    public static String getStr(Object obj) {
        String str = Objects.nonNull(obj) ? String.valueOf(obj).trim().replaceAll("\\s*|\r|\n|\t", "") : "";
        return "null".equalsIgnoreCase(str) ? "" : str;
    }

    /**
     * @apiNote: 将对象转为字符串数据, obj为空时返回defaultVal值
     * @param: [obj, defaultVal]
     * @return: java.lang.String
     */
    public static String getStr(Object obj, String defaultVal) {
        final String str = getStr(obj);
        return StringUtils.isBlank(str) ? defaultVal : str;
    }

    /**
     * @apiNote: 当对象obj为空时返回defaultVal值
     * @param: [obj, defaultVal]
     * @return: java.lang.Object
     */
    public static <T> T getObj(Object obj, Object defaultVal) {
        final String str = getStr(obj);
        if (StringUtils.isBlank(str) && ObjectUtil.isNull(defaultVal)) {
            return null;
        }
        return (T) (StringUtils.isBlank(str) ? defaultVal : obj);
    }

    /**
     * @apiNote: 校验数据是否为空
     * @param: [msg, val]
     * @return: void
     */
    public static void hasText(String msg, Object... val) {
        if (ObjectUtil.hasNull(val) || !ObjectUtil.isAllNotEmpty(val) || val.length == 0 || StringUtils.isBlank(getStr(val))) {
            Assert.hasText(null, msg);
        }
    }

    /**
     * @apiNote: 向前台输出数据
     * @param: [obj, response]
     * @return: void
     */
    public static void printJsonMsg(Object obj, HttpServletResponse response) {
        if (ObjectUtil.isAllNotEmpty(obj, response)) {
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            try (final PrintWriter writer = response.getWriter()) {
                writer.print(obj);
                writer.flush();
            } catch (IOException ignored) {}
        }
    }

    /**
     * @apiNote: 校验数据是否未空，为空则抛出异常
     * @param: tipMsg：异常提示信息
     * @param: params：需要校验的参数值
     */
    public static void checkParamsIsEmpty(String tipMsg, Object... params) {
        if (ObjectUtil.isNull(params) || !ObjectUtil.isAllNotEmpty(params)) {
            throw new RuntimeException(getStr(tipMsg, "校验失败：参数值为空！"));
        }
    }
}
