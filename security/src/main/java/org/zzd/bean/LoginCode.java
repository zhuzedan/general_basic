package org.zzd.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zzd
 * @apiNote 验证码配置信息
 * @date 2023/3/31 14:25
 */
@Data
public class LoginCode {

    @ApiModelProperty(value = "验证码配置")
    private LoginCodeEnum codeType;

    @ApiModelProperty(value = " 验证码v有效期 分钟")
    private Long expiration = 2L;

    @ApiModelProperty(value = "验证码内容长度")
    private int length = 2;

    @ApiModelProperty(value = "验证码宽度")
    private int width = 111;

    @ApiModelProperty(value = "验证码高度")
    private int height = 36;

    @ApiModelProperty(value = "验证码字体")
    private String fontName;

    @ApiModelProperty("字体大小")
    private int fontSize = 25;

    @ApiModelProperty("验证码前缀")
    private String codeKey;

    public LoginCodeEnum getCodeType() {
        return codeType;
    }
}
