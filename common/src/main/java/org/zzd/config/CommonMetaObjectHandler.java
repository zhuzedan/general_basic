package org.zzd.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author :zzd
 * @apiNote :mp自动填充时间配置类
 * @date : 2023-03-30 16:48
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * @apiNote 新增
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("自动创建时间");
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
        this.strictInsertFill(metaObject,"updateTime",Date.class,new Date());
    }

    /**
     * @apiNote 修改
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("自动更新时间");
        this.strictInsertFill(metaObject,"updateTime",Date.class,new Date());
    }
}
