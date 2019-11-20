package com.hywisdom.platform.common.service.mybatisplus;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 〈功能简述〉<br>
 * 〈自动填充配置〉
 *
 * @author wangxz
 * @create 2018/10/30
 * @since 1.0.0
 */
@Component
public class MetaObjectHandlerConfig  implements MetaObjectHandler {

    /**
     * 创建时间
     */
    private final String createTime = "createTime";
    /**
     * 修改时间
     */
    private final String updateTime = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        //新增填充
        System.out.println("*************************");
        System.out.println("insert fill");
        System.out.println("*************************");
        fillOfInsertOrUpdate(metaObject, FieldFill.INSERT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新填充
        System.out.println("*************************");
        System.out.println("update fill");
        System.out.println("*************************");
        fillOfInsertOrUpdate(metaObject, FieldFill.INSERT_UPDATE);
    }

    /**
     * 填充方法
     *
     * @param metaObject
     * @param fieldFill
     */
    private void fillOfInsertOrUpdate(MetaObject metaObject, FieldFill fieldFill) {
        switch (fieldFill) {
            case INSERT:
                setFieldValByName(createTime, LocalDateTime.now(), metaObject);
                setFieldValByName(updateTime, LocalDateTime.now(), metaObject);
                break;
            case UPDATE:
                setFieldValByName(updateTime, LocalDateTime.now(), metaObject);
                break;
            default:
        }
    }
}
