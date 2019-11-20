package com.hywisdom.easy.excel.demo;

import java.sql.Timestamp;

/**
 * Elproject entity. @author MyEclipse Persistence Tools
 */

public class Elproject implements java.io.Serializable {

	// Fields

	private String id;
	private String projectProgramId;
	private String projectId;
	private String name;
	private String description;
	private String projectClass;
	private String pmname;
	private String pmid;
	private Short isFile;
	private String state;
	private String activeState;
	private String prodId;
	private String deptId;
	private Timestamp startTime;
	private Timestamp closeTime;
	private Short phase;
	private Float budgetDollar;
	private Float budgetRmb;
	private Short workload;
	private Short visibleRange;
	private String delFlag;
	private String createBy;
	private Timestamp createtime;
	private String updateBy;
	private Timestamp updatetime;

	// Constructors

	/** default constructor */
	public Elproject() {
	}

	/** minimal constructor */
	public Elproject(String id) {
		this.id = id;
	}

	/** full constructor */
	public Elproject(String id, String projectProgramId, String projectId, String name, String description,
			String projectClass, String pmname, String pmid, Short isFile, String state, String activeState,
			String prodId, String deptId, Timestamp startTime, Timestamp closeTime, Short phase, Float budgetDollar,
			Float budgetRmb, Short workload, Short visibleRange, String delFlag, String createBy, Timestamp createtime,
			String updateBy, Timestamp updatetime) {
		this.id = id;
		this.projectProgramId = projectProgramId;
		this.projectId = projectId;
		this.name = name;
		this.description = description;
		this.projectClass = projectClass;
		this.pmname = pmname;
		this.pmid = pmid;
		this.isFile = isFile;
		this.state = state;
		this.activeState = activeState;
		this.prodId = prodId;
		this.deptId = deptId;
		this.startTime = startTime;
		this.closeTime = closeTime;
		this.phase = phase;
		this.budgetDollar = budgetDollar;
		this.budgetRmb = budgetRmb;
		this.workload = workload;
		this.visibleRange = visibleRange;
		this.delFlag = delFlag;
		this.createBy = createBy;
		this.createtime = createtime;
		this.updateBy = updateBy;
		this.updatetime = updatetime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectProgramId() {
		return this.projectProgramId;
	}

	public void setProjectProgramId(String projectProgramId) {
		this.projectProgramId = projectProgramId;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectClass() {
		return this.projectClass;
	}

	public void setProjectClass(String projectClass) {
		this.projectClass = projectClass;
	}

	public String getPmname() {
		return this.pmname;
	}

	public void setPmname(String pmname) {
		this.pmname = pmname;
	}

	public String getPmid() {
		return this.pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public Short getIsFile() {
		return this.isFile;
	}

	public void setIsFile(Short isFile) {
		this.isFile = isFile;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getActiveState() {
		return this.activeState;
	}

	public void setActiveState(String activeState) {
		this.activeState = activeState;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	public Short getPhase() {
		return this.phase;
	}

	public void setPhase(Short phase) {
		this.phase = phase;
	}

	public Float getBudgetDollar() {
		return this.budgetDollar;
	}

	public void setBudgetDollar(Float budgetDollar) {
		this.budgetDollar = budgetDollar;
	}

	public Float getBudgetRmb() {
		return this.budgetRmb;
	}

	public void setBudgetRmb(Float budgetRmb) {
		this.budgetRmb = budgetRmb;
	}

	public Short getWorkload() {
		return this.workload;
	}

	public void setWorkload(Short workload) {
		this.workload = workload;
	}

	public Short getVisibleRange() {
		return this.visibleRange;
	}

	public void setVisibleRange(Short visibleRange) {
		this.visibleRange = visibleRange;
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}