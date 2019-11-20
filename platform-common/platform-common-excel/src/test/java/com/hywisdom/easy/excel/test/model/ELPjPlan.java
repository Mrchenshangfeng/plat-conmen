package com.hywisdom.easy.excel.test.model;

import com.hywisdom.easy.excel.demo.Elplan;

public class ELPjPlan {
	private int index = 0;
	private int level = 0;
	private String wbs = "";
	private String relIndex = "";
	private Elplan plan = null;
	
	public ELPjPlan() {
	}
	
	public ELPjPlan(Elplan plan) {
		this.plan = plan;
	}
	
	public ELPjPlan(int index, String wbs, Elplan plan) {
		super();
		this.index = index;
		this.wbs = wbs;
		this.plan = plan;
	}

	public ELPjPlan(String wbs, Elplan plan) {
		super();
		this.wbs = wbs;
		this.plan = plan;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getRelIndex() {
		return relIndex;
	}
	public void setRelIndex(String relIndex) {
		this.relIndex = relIndex;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getWbs() {
		return wbs;
	}
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
	public Elplan getPlan() {
		return plan;
	}
	public void setPlan(Elplan plan) {
		this.plan = plan;
	}
}
