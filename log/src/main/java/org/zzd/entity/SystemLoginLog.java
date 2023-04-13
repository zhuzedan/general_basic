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
 * 系统访问记录(SystemLoginLog)表实体类
 *
 * @author zzd
 * @since 2023-03-13 10:03:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_login_log")
public class SystemLoginLog implements Serializable {
    //访问ID
    @TableId
    private Long id;
    //用户账号
    private String username;
    //登录IP地址
    private String ipaddr;
    //登录状态（0成功 1失败）
    private Integer status;
    //提示信息
    private String msg;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

