package org.zzd.constant;

/**
 * @author :zzd
 * @apiNote :springSecurity常量
 * @date : 2023-03-03 8:50
 */
public class SecurityConstants {
    public static final String SECRET_KEY = "zzd";
    public static final long EXPIRATION_TIME = 24 * 60 * 60 *1000L; //有效期24h
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "created";
}
