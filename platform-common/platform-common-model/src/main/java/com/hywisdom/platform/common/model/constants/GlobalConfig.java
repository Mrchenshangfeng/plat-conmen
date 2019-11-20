package com.hywisdom.platform.common.model.constants;

public class GlobalConfig {

    // Api 前缀
    private static String restApiPath;

    // 项目基础路径
    private static String hyUrl;

    /**
     * token失效时间
     */
    private static Long tokenExpireTime = 60 * 30L;

    /**
     * 注册验证码失效时间
     */
    private static Long registryCodeExpireTime = 60 * 30L;

    /**
     * 登录失败次数限制
     */
    private static Integer maxLoginErrorCount = 3;

    private static String companyName;

    public static String DEFAULT_CHART_SET = "UTF-8";

    public static String getRestApiPath() {
        return restApiPath;
    }

    public static void setRestApiPath(String restApiPath) {
        GlobalConfig.restApiPath = restApiPath;
    }

    public static String getHyUrl() {
        return hyUrl;
    }
    public static void setHyUrl(String hyUrl) {
        GlobalConfig.hyUrl = hyUrl;
    }
    public static Long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public static void setTokenExpireTime(Long tokenExpireTime) {
        GlobalConfig.tokenExpireTime = tokenExpireTime;
    }

    public static Long getRegistryCodeExpireTime() {
        return registryCodeExpireTime;
    }

    public static void setRegistryCodeExpireTime(Long registryCodeExpireTime) {
        GlobalConfig.registryCodeExpireTime = registryCodeExpireTime;
    }

    public static Integer getMaxLoginErrorCount() {
        return maxLoginErrorCount;
    }

    public static void setMaxLoginErrorCount(Integer maxLoginErrorCount) {
        GlobalConfig.maxLoginErrorCount = maxLoginErrorCount;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String companyName) {
        GlobalConfig.companyName = companyName;
    }
}
