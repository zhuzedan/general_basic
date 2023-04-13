package org.zzd.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author :zzd
 * @date : 2023-02-15 14:25
 */
@Component
public class AuthUtils {
    /**
     * @apiNote 获取当前登录用户名
     * @date 2023/3/3 21:57
     * @return java.lang.String
     */
    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public static Long getUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
