package org.zzd.enums;

/**
 * 业务操作类型
 * @author :zzd
 * @date : 2023-02-15 13:25
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,
    /**
     * 查询
     */
    SELECT,
    /**
     * 详情
     */
    READ,
    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    ASSGIN,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 更新状态
     */
    STATUS,

    /**
     * 清空数据
     */
    CLEAN,
}
