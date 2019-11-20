package com.hywisdom.platform.common.utils.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author wangxz
 * @create 2018/11/5
 * @since 1.0.0
 */
public class SecurityAuthenUtil {
    /**
     * 直接获取当前用户的登录账号
     * @return
     */
    public static String getLoginName() {
        return getAuthenticationUser().getUsername();
    }

    /**
     * 直接获取当前用户的认证用户信息
     * @return
     */
    public static User getAuthenticationUser() {
        Authentication authenObj = SecurityContextHolder.getContext().getAuthentication();
        User authenUser = (User)authenObj.getPrincipal();
        return authenUser;
    }

    /**
     * @Method enCoderPswcd
     * @Description:  加密密码,无法解密
     * @param pswd
     * @return：java.lang.String
     * @exception
     * @Author: xfl
     * @Date: 2018/7/18
     **/
    public static String enCoderPswcd(String pswd) {
        return "{bcrypt}"+new BCryptPasswordEncoder().encode(pswd);
    }

    /**
     * @Method checkPswcd
     * @Description:  校验密码
     * @param pswd	明文
     * @param encodePwd	加密
     * @return：boolean
     * @exception
     * @Author: xfl
     * @Date: 2018/9/6
     **/
    public static boolean checkPswcd(String pswd,String encodePwd) {
        return new BCryptPasswordEncoder().matches(pswd,encodePwd.replace("{bcrypt}",""));
    }
}
