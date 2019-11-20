package com.hywisdom.platform.common.model.http;

/**
 * 〈功能简述〉<br>
 * 〈rest响应结果code〉
 *
 * @author wangxz
 * @create 2018/10/18
 * @since 1.0.0
 */
public enum ResponseMessageCodeEnum {

    SUCCESS("00"),
    ERROR("01"),
    NO_DATA("07"),//不存在数据
    VALID_ERROR("1000"),
    APPLICATION_ERROR("9000"),//应用级错误
    VALIDATE_ERROR("9001"),// 参数验证错误
    SERVICE_ERROR("9002"),// 业务逻辑验证错误
    CACHE_ERROR("9003"),// 缓存访问错误
    DAO_ERROR("9004"),// 数据访问错误
    REPEAT_ERROR("9005"),//数据重复错误
    RE_LOGIN("999");

    private String code;

    ResponseMessageCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
