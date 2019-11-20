package com.hywisdom.platform.common.model.entity;

import com.hywisdom.platform.common.model.http.ResponseMessage;
import com.hywisdom.platform.common.model.http.Result;
import com.hywisdom.platform.common.utils.TreeUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.hywisdom.platform.common.utils.CollectionUtils.cloneEntityList;

/**
 * @program: platform-common
 * @description:    实体类处理常用方法类
 * @author: xfl
 * @CreateTime: 2018年11月09日 10:35
 **/
public class EntityUtil {

    /**
     * @Method treeOrList
     * @Description:  返回树状结构实体或者列表结构实体
     * @param pList list
     * @param pT tree
     * @param type
     * @param permissionKey
     * @param permissionPKey
     * @param subListName
     * @return：com.hywisdom.platform.common.model.http.ResponseMessage
     * @exception
     * @Author: xfl
     * @Date: 2018/11/9
    **/
    public static <T> ResponseMessage treeOrList(List<T> pList,T pT, String type, String permissionKey, String permissionPKey, String subListName){
        //将列表结构格式化成树状
        if ("tree".equals(type)) {
            return Result.success(returnTreeEntity(pList,pT,type,permissionKey,permissionPKey,subListName));
        } else {
            return Result.success(pList);
        }
    }

    /**
     * @Method returnTreeEntity
     * @Description:  返回树状结构数据
     * @param pList
     * @param pT
     * @param type
     * @param permissionKey
     * @param permissionPKey
     * @param subListName
     * @return：T
     * @exception
     * @Author: xfl
     * @Date: 2018/11/16
    **/
    public static <T> T returnTreeEntity(List<T> pList,T pT, String type, String permissionKey, String permissionPKey, String subListName) {
        if(pList==null||pList.size()==0){//值为空
            return null;
        }
        try {
            if(pT == null){
                pT = (T) BeanUtils.cloneBean(pList.get(0));//取第一个，存在风险
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<T> persClone=cloneEntityList(pList);
        TreeUtil.createTree(persClone, pT, permissionKey, permissionPKey, subListName);
        return pT;
    }
}
