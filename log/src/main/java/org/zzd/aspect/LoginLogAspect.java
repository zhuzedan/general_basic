package org.zzd.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.zzd.entity.SystemLoginLog;
import org.zzd.mapper.SystemLoginLogMapper;
import org.zzd.utils.ApiUtils;
import org.zzd.utils.AuthUtils;
import org.zzd.utils.ThrowableUtil;

import javax.annotation.Resource;

/**
 * @author :zzd
 * @apiNote :登录日志
 * @date : 2023-03-13 10:11
 */
@Aspect
@Component
@Slf4j
public class LoginLogAspect {
    @Resource
    private SystemLoginLogMapper systemLoginLogMapper;

    @Pointcut("@annotation(org.zzd.annotation.LoginLog)")
    public void loginPointcut() {

    }

    @AfterThrowing(value = "loginPointcut()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        log.info("方法异常时执行");
        SystemLoginLog systemLoginLog = new SystemLoginLog();
        //ip地址
        systemLoginLog.setIpaddr(ApiUtils.getHostIp());
        //用户名
        systemLoginLog.setUsername(AuthUtils.getCurrentUsername());
        //异常exception
        if (e != null) {
            systemLoginLog.setStatus(0);
            systemLoginLog.setMsg(e.getMessage());
            byte[] bytes = ThrowableUtil.getStackTrace(e).getBytes();
        }
        systemLoginLogMapper.insert(systemLoginLog);
    }

    @AfterReturning(value = "loginPointcut()", returning = "jsonResult")
    public void doAfterReturning(Object jsonResult) {
        SystemLoginLog systemLoginLog = new SystemLoginLog();
        //ip地址
        systemLoginLog.setIpaddr(ApiUtils.getHostIp());
        //用户名
        systemLoginLog.setUsername(AuthUtils.getCurrentUsername());
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(jsonResult));
        String code = jsonObject.getString("code");
        if ("200".equals(code)) {
            systemLoginLog.setStatus(1);
            systemLoginLog.setMsg(jsonObject.getString("message"));
        }
        systemLoginLogMapper.insert(systemLoginLog);
    }
}
