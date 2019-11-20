package com.hywisdom.platform.common.model.constants;

/**
 * 〈功能简述〉<br>
 * 〈日志模块枚举〉
 *
 * @author wangxz
 * @create 2019/3/8
 * @since 1.0.0
 */
public enum LogEnum {
    BUSINESSCODE("编码规则"),
    BASICMODEL("基础建模"),
    PRODMGT("生产管理"),
    MATERMGT("物料管理"),
    QUALITY("质量管理"),
    EQPMGT("设备管理"),
    STORE("仓储管理"),
    SYSTEM("系统管理"),
    PERSON("人员管理"),
    MESSAGE("通知"),
    LAMP("矿灯管理"),
    LED("Led显示管理"),
    REPORTFORMS("报表看板"),
    OPS("运维管理");

    String value;

    LogEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//        public static void main(String[] args) {
//            Module type = Module.USER;
//            System.out.println(type.value);
//            System.out.println(type.toString());
//        }
}
