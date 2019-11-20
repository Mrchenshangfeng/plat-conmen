package com.hywisdom.platform.common.service.datascope;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
//import com.baomidou.mybatisplus.plugins.SqlParserHandler;
import com.google.common.base.Strings;
import com.hywisdom.platform.common.utils.CollectionUtils;
import com.hywisdom.platform.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * 〈功能简述〉<br>
 * 〈数据范围拦截器〉
 *
 * @author wangxz
 * @create 2018/11/7
 * @since 1.0.0
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor implements Interceptor {
// extends SqlParserHandler

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//        this.sqlParser(metaObject);

        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();

        //查找参数中包含DataScope类型的参数
        DataScope dataScope = findDataScopeObject(parameterObject);

        if (dataScope == null) {
            originalSql = "select * from (" + originalSql + ") temp_data_scope ";
            metaObject.setValue("delegate.boundSql.sql", originalSql);
            return invocation.proceed();
        } else {
//            String scopeName = dataScope.getScopeName();
//            List<Integer> deptIds = dataScope.getDeptIds();
//            if (StringUtil.isNotBlank(scopeName) && CollectionUtils.isNotEmpty(deptIds)) {
//                String join = CollectionUtils.join(deptIds, ",");
//                originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
//                metaObject.setValue("delegate.boundSql.sql", originalSql);
//            }
            String scopeName = dataScope.getScopeName();
            String relationTable = dataScope.getRelationTable();
            String relationField = dataScope.getRelationField();
            String orderField = dataScope.getOrderField();
            Boolean isASC = dataScope.getASC();
            List<Integer> deptIds = dataScope.getDeptIds();
            String join = CollectionUtils.join(deptIds, ",");
            if (isRelation(relationTable, relationField)) {
                if (CollectionUtils.isEmpty(deptIds)) {
                    originalSql = "select * from (" + originalSql + ") temp_data_scope " +
                            "where temp_data_scope.id in (SELECT " + relationField + " FROM " + relationTable + " temp_relation_scope WHERE temp_relation_scope." + scopeName + " in (-1))";
                } else {
                    originalSql = "select * from (" + originalSql + ") temp_data_scope " +
                            "where temp_data_scope.id in (SELECT " + relationField + " FROM " + relationTable + " temp_relation_scope WHERE temp_relation_scope." + scopeName + " in (" + join + "))";
                }
            } else {
                originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
            }
//            try {
//                //添加当前用户能够访问
//                if (originalSql.contains("p.UserId as User_Id")) {
//                    originalSql += " or temp_data_scope.User_Id = " + SpringSecurityUtils.getUser().getId();
//                }
//            } catch (Exception e) {
//                log.error("DataScopeInterceptor拦截器未获取到用户id", e);
//            }
            //如果有排序
            if (StringUtils.hasText(orderField)) {
                String orderSql = escapeOrder(orderField, isASC);
                originalSql += orderSql;
            }
            metaObject.setValue("delegate.boundSql.sql", originalSql);
            return invocation.proceed();
        }
    }

    private boolean isRelation(String relationTable,String relationField) {
        Boolean isRelation = false;
        if (StringUtils.hasText(relationTable) && StringUtils.hasText(relationField)) {
            isRelation = true;
        }
        return isRelation;
    }

    private String escapeOrder(String orderField,Boolean isASC) {
        String reOrderSql = "";
        if (StringUtils.hasText(orderField)) {
            if (isASC) {
                reOrderSql = " ORDER BY temp_data_scope." + orderField + " ASC";
            } else
                reOrderSql = " ORDER BY temp_data_scope." + orderField + " DESC";
        }
        return reOrderSql;
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }
    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    private DataScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                }
            }
        }
        return null;
    }

}
