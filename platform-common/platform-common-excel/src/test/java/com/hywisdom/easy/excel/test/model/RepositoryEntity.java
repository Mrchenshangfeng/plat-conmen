package com.hywisdom.easy.excel.test.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * 〈功能简述〉<br>
 * 〈仓库信息〉
 *
 * @author wangxz
 * @create 2019/4/18
 * @since 1.0.0
 */

@ApiModel(value = "仓库信息",reference = "")
@TableName("store_repository")
public class RepositoryEntity  {

    private String code;
    private String name;
    private String repType; //仓库类型
    private Long parentId;
    private String address;
    //描述
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    //    private String adminId; //仓库管理员id
//    private String adminName.
    private String state; //仓库状态，是否禁用
    private String isDefault;//是否默认
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    /**
     * 删除标记（0:未删除，1:删除）
     */
    @TableLogic
    private String delFlag = "0";
}
