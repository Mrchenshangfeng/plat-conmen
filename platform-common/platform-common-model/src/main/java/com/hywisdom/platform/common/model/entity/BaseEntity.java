package com.hywisdom.platform.common.model.entity;

import com.hywisdom.platform.common.model.convert.Convert;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 〈功能简述〉<br>
 * 〈自增主键entity基类〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseEntity extends Convert implements Cloneable{

//    public static final String ID = "id";
//
    private Long id;

    @Override
    public Object clone() {
        BaseEntity stu = null;
        try{
            stu = (BaseEntity)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
