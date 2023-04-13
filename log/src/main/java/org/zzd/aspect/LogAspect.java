package org.zzd.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.zzd.annotation.Log;
import org.zzd.entity.SystemOperationLog;
import org.zzd.mapper.SystemOperationLogMapper;
import org.zzd.utils.AuthUtils;
import org.zzd.utils.HttpContextUtils;
import org.zzd.utils.IpUtil;
import org.zzd.utils.ThrowableUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * @author :zzd
 * @apiNote :切面日志
 * @date : 2023-03-02 15:16
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    //请求处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Resource
    private SystemOperationLogMapper systemOperationLogMapper;

    @Pointcut("@annotation(org.zzd.annotation.Log)")
    public void logPointcut() {

    }

    @Before("logPointcut()")
    public void doBefore() {
        // 记录请求开始时间
        startTime.set(System.currentTimeMillis());
    }

    /**
     * @param joinPoint:     切点
     * @param controllerLog: 日志注解
     * @param jsonResult:    返回的json结果
     * @apiNote 处理完请求后执行
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * @param joinPoint:     切点
     * @param controllerLog: 日志注解
     * @param e:             异常
     * @apiNote 拦截异常操作
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            SystemOperationLog systemOperationLog = new SystemOperationLog();
            systemOperationLog.setStatus(1);

            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            systemOperationLog.setOperationIp(IpUtil.getIpAddress(request));   // 请求ip地址
            systemOperationLog.setOperationUrl(request.getRequestURI());   // 请求url

            //异常exception
            if (e != null) {
                systemOperationLog.setStatus(0);
                systemOperationLog.setErrorMsg(e.getMessage());
                byte[] bytes = ThrowableUtil.getStackTrace(e).getBytes();
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            systemOperationLog.setMethod(className + "." + methodName + "()");
            // 设置请求方法类型
            systemOperationLog.setRequestMethod(request.getMethod());
            //操作人
            systemOperationLog.setOperationName(AuthUtils.getCurrentUsername());
            //操作时间
            systemOperationLog.setOperationTime(System.currentTimeMillis() - startTime.get() + "ms");
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, systemOperationLog, jsonResult);
            // 保存数据库
            systemOperationLogMapper.insert(systemOperationLog);

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * @param joinPoint:    切点
     * @param log:          日志
     * @param operationLog: 操作日志
     * @param jsonResult:   json结果
     * @return void
     * @apiNote 获取注解中对方法的描述信息 用于Controller层注解
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SystemOperationLog operationLog, Object jsonResult) {
        // 设置action动作
        operationLog.setBusinessType(log.businessType().name());
        // 设置标题
        operationLog.setTitle(log.title());
        // 设置操作人类别
        operationLog.setOperatorType(log.operatorType().name());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operationLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && !StringUtils.isEmpty(jsonResult)) {
            operationLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }

    private void setRequestValue(JoinPoint joinPoint, SystemOperationLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperationParam(params);
        }
    }

    /**
     * @apiNote 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params.append(jsonObj.toString()).append(" ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * @param o: 对象信息
     * @return boolean如果是需要过滤的对象，则返回true；否则返回false。
     * @apiNote 判断是否需要过滤的对象
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}