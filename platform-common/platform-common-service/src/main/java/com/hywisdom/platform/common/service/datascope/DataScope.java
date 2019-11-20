package com.hywisdom.platform.common.service.datascope;

import java.util.List;

/**
 * 〈功能简述〉<br>
 * 〈数据范围〉
 * 1：所有数据；2：所在机构及以下数据；3：所在机构数据；
 * 4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置
 * @author wangxz
 * @create 2018/11/6
 * @since 1.0.0
 */
public class DataScope {
    /**
     * 限制范围的字段名称,部门名称
     */
    private String scopeName = "deptid";

    /**
     * 多对多关联限制：关联表
     */
    private String relationTable = "";

    /**
     * 多对多关联限制：关联字段
     */
    private String relationField = "";

    /**
     * 排序字段
     */
    private String orderField = "";

    /**
     * 排序方式，默认ASC
     */
    private boolean isASC = true;

    /**
     * 具体的数据范围
     */
    private List<Integer> deptIds;

    public DataScope() {
    }

    public DataScope(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public DataScope(String scopeName, List<Integer> deptIds) {
        this.scopeName = scopeName;
        this.deptIds = deptIds;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public String getRelationTable() {
        return relationTable;
    }

    public void setRelationTable(String relationTable) {
        this.relationTable = relationTable;
    }

    public String getRelationField() {
        return relationField;
    }

    public void setRelationField(String relationField) {
        this.relationField = relationField;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public boolean getASC() {
        return isASC;
    }

    public void setASC(boolean isASC) {
        this.isASC = isASC;
    }

}
