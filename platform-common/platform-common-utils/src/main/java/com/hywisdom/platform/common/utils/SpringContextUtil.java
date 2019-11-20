package com.hywisdom.platform.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;


public class SpringContextUtil {

//    private static ApplicationContext applicationContext;
//
//    //获取上下文
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    //设置上下文
//    public static void setApplicationContext(ApplicationContext application) {
//        SpringContextUtil.applicationContext = application;
//    }
//
//
//    //通过名字获取上下文中的bean
//    public static Object getBean(String name){
//        return applicationContext.getBean(name);
//    }
//
//    //通过类型获取上下文中的bean
//    public static Object getBean(Class<?> requiredType){
//        return applicationContext.getBean(requiredType);
//    }
//

	private static ApplicationContext context;

	public static <T> T getBean(Class<T> clazz) {
		try {
			return getApplicationContext().getBean(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T getBean(String name) {
		try {
			return (T) getApplicationContext().getBean(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		Validate.validState(context != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
		return context;
	}

    /**
     * 实例是否存在Spring 容器中
     * @param interfaceId
     * @param methodName
     * @return true 存在， false 不存在
     */
    public static boolean existsInstance(String interfaceId, String methodName){
    	boolean flag = false;
    	if(StringUtils.isEmpty(interfaceId)){
    		return false;
    	}
    	Object instanceObject = SpringContextUtil.getBean(interfaceId);
    	if(instanceObject == null){
    		return false;
    	}
    	if(StringUtils.isNotEmpty(methodName)){
    		Method[] methods = instanceObject.getClass().getMethods();
        	for(Method m : methods){
    			if(m.getName().equals(methodName)){
    				flag = true;
    				break;
    			}
    		}
    	}else{
    		flag = true;
    	}
    	return flag;
    }
}  
