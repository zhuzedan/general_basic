package org.zzd.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zzd.constant.SecurityConstants;
import org.zzd.exception.ResponseException;
import org.zzd.utils.JwtTokenUtil;
import org.zzd.utils.RedisCache;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :zzd
 * @apiNote :OncePerRequestFilter保证过滤只执行一次，在请求前
 * @date : 2023-03-02 11:26
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token  header的token
        String token = null;
        String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (!StringUtils.isBlank(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token =  bearerToken.replace(SecurityConstants.TOKEN_PREFIX+" ","");
        }
        if (!StringUtils.isBlank(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            //解析token
            String username;
            try {
                username = jwtTokenUtil.getUserNameFromToken(token);
            }catch (Exception e) {
                throw new ResponseException(400, "token不合法");
            }
            String frontToken = redisCache.getCacheObject("token_");
            if (!frontToken.equals(token)) {
                throw new ResponseException(500,"token验证失败");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                //封装Authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //存入SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //如果token没有获取到就放行，让后面的过滤器执行，后面过滤器会拦截到
        filterChain.doFilter(request, response);
    }
}