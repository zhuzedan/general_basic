package org.zzd.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * (SystemOperationLog)表实体类
 *
 * @author zzd
 * @since 2023-03-02 15:21:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_operation_log")
public class SystemOperationLog implements Serializable {
    //日志主键
    @TableId
    private Long id;
    //模块标题
    private String title;
    //业务类型（0其它 1新增 2修改 3删除）
    private String businessType;
    //方法名称
    private String method;
    //请求方式
    private String requestMethod;
    //操作类别（0其它 1后台用户 2手机端用户）
    private String operatorType;
    //操作人员
    private String operationName;
    //请求URL
    private String operationUrl;
    //主机地址
    private String operationIp;
    //请求参数
    private String operationParam;
    //返回参数
    private String jsonResult;
    //操作状态（0正常 1异常）
    private Integer status;
    //错误消息
    private String errorMsg;
    //操作时间
    private String operationTime;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

