package org.zzd.result;

/**
 * @author :zzd
 * @date : 2022/12/10
 */
public enum ResultCodeEnum implements CustomizeResultCode{
    SUCCESS(200,"操作成功"),
    FAIL(500, "操作失败"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),

    UNAUTHORIZED(401, "登录认证失败，请重新登录"),
    FORBIDDEN(403, "权限不足"),

    NO_USERNAME(213, "用户名未填写"),
    ACCOUNT_ERROR(214, "用户名不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    LOGIN_ERROR( 216, "用户名或密码不正确"),
    ACCOUNT_STOP( 217, "账号已停用"),

    NODE_ERROR( 218, "该节点下有子节点，不可以删除"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    ;

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
