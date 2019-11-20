package com.hywisdom.easy.excel.demo;

import java.sql.Timestamp;

/**
 * Elplan entity. @author MyEclipse Persistence Tools
 */

public class Elplan<T> implements java.io.Serializable,Comparable<T> {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private Timestamp starttime;
	private Timestamp endtime;
	private Timestamp preStarttime;
	private Timestamp preEndtime;
	private String workhours;
	private String milestone;
	private String owner;
	private Timestamp relStarttime;
	private Timestamp relEndtime;
	private String percent;
	private String parent;
	private String relplan;
	private String status;

	// Constructors

	/** default constructor */
	public Elplan() {
	}

	/** minimal constructor */
	public Elplan(String id) {
		this.id = id;
	}

	/** full constructor */
	public Elplan(String id, String name, Timestamp starttime, Timestamp endtime, Timestamp preStarttime,
			Timestamp preEndtime, String workhours, String milestone, String owner, Timestamp relStarttime,
			Timestamp relEndtime, String percent, String parent, String relplan) {
		this.id = id;
		this.name = name;
		this.starttime = starttime;
		this.endtime = endtime;
		this.preStarttime = preStarttime;
		this.preEndtime = preEndtime;
		this.workhours = workhours;
		this.milestone = milestone;
		this.owner = owner;
		this.relStarttime = relStarttime;
		this.relEndtime = relEndtime;
		this.percent = percent;
		this.parent = parent;
		this.relplan = relplan;
	}

	// Property accessors
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public Timestamp getPreStarttime() {
		return this.preStarttime;
	}

	public void setPreStarttime(Timestamp preStarttime) {
		this.preStarttime = preStarttime;
	}

	public Timestamp getPreEndtime() {
		return this.preEndtime;
	}

	public void setPreEndtime(Timestamp preEndtime) {
		this.preEndtime = preEndtime;
	}

	public String getWorkhours() {
		return this.workhours;
	}

	public void setWorkhours(String workhours) {
		this.workhours = workhours;
	}

	public String getMilestone() {
		return this.milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Timestamp getRelStarttime() {
		return this.relStarttime;
	}

	public void setRelStarttime(Timestamp relStarttime) {
		this.relStarttime = relStarttime;
	}

	public Timestamp getRelEndtime() {
		return this.relEndtime;
	}

	public void setRelEndtime(Timestamp relEndtime) {
		this.relEndtime = relEndtime;
	}

	public String getPercent() {
		return this.percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((preEndtime == null) ? 0 : preEndtime.hashCode());
		result = prime * result + ((preStarttime == null) ? 0 : preStarttime.hashCode());
		result = prime * result + ((starttime == null) ? 0 : starttime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elplan other = (Elplan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (preEndtime == null) {
			if (other.preEndtime != null)
				return false;
		} else if (!preEndtime.equals(other.preEndtime))
			return false;
		if (preStarttime == null) {
			if (other.preStarttime != null)
				return false;
		} else if (!preStarttime.equals(other.preStarttime))
			return false;
		if (starttime == null) {
			if (other.starttime != null)
				return false;
		} else if (!starttime.equals(other.starttime))
			return false;
		return true;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getRelplan() {
		return this.relplan;
	}

	public void setRelplan(String relplan) {
		this.relplan = relplan;
	}

	@Override
	public int compareTo(T o) {
		// TODO 自动生成的方法存根
		return 0;
	}


}