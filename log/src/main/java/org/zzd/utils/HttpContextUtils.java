package org.zzd.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :zzd
 * @date : 2022/12/12
 */
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {
        // RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        // return servletRequestAttributes.getRequest();
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
