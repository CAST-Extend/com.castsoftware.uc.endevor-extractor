package com.castsoftware.endevor.pojo;

import java.util.Date;

public class SubSys {
	private String siteId;
	private String envName;
	private String sysName;
	private String sbsName;
	private String stgName;
	private String stgId;
	private Integer stgSeqNum;
	private Integer rcdUpdtCnt;
	private Date updtDate; //"2017-03-23T10:56:00.00+0000"
	private String updtUsrid;
	private String title;
	private String nextSbs;
	private String relId;
	private Boolean excludeFromProc;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((envName == null) ? 0 : envName.hashCode());
		result = prime * result + ((sbsName == null) ? 0 : sbsName.hashCode());
		result = prime * result + ((stgName == null) ? 0 : stgName.hashCode());
		result = prime * result + ((sysName == null) ? 0 : sysName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		SubSys other = (SubSys) obj;
		if (sbsName.equals("*"))
        	return sbsName.equals(other.sbsName);
        else
        	return sbsName.equals(other.sbsName) && envName.equals(other.envName) 
        			&& stgId.equals(other.stgId) && sysName.equals(other.sysName);
	}
	
	@Override
	public String toString() {
		if (sbsName.equals("*"))
			return sbsName;
		else
			return String.format("[%s] [Stg:%s - %s] [Sys: %s] %s", envName, stgId, stgName, sysName, sbsName);
	}
	
	public static SubSys getStarSubSys() {
		return new SubSys(null, null, null, "*", null, null, null, null, null, null, null, null, null, null);
	}

	public SubSys(String siteId, String envName, String sysName, String sbsName, String stgName, String stgId,
			Integer stgSeqNum, Integer rcdUpdtCnt, Date updtDate, String updtUsrid, String title, String nextSbs,
			String relId, Boolean excludeFromProc) {
		super();
		this.siteId = siteId;
		this.envName = envName;
		this.sysName = sysName;
		this.sbsName = sbsName;
		this.stgName = stgName;
		this.stgId = stgId;
		this.stgSeqNum = stgSeqNum;
		this.rcdUpdtCnt = rcdUpdtCnt;
		this.updtDate = updtDate;
		this.updtUsrid = updtUsrid;
		this.title = title;
		this.nextSbs = nextSbs;
		this.relId = relId;
		this.excludeFromProc = excludeFromProc;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSbsName() {
		return sbsName;
	}

	public void setSbsName(String sbsName) {
		this.sbsName = sbsName;
	}

	public String getStgName() {
		return stgName;
	}

	public void setStgName(String stgName) {
		this.stgName = stgName;
	}

	public String getStgId() {
		return stgId;
	}

	public void setStgId(String stgId) {
		this.stgId = stgId;
	}

	public Integer getStgSeqNum() {
		return stgSeqNum;
	}

	public void setStgSeqNum(Integer stgSeqNum) {
		this.stgSeqNum = stgSeqNum;
	}

	public Integer getRcdUpdtCnt() {
		return rcdUpdtCnt;
	}

	public void setRcdUpdtCnt(Integer rcdUpdtCnt) {
		this.rcdUpdtCnt = rcdUpdtCnt;
	}

	public Date getUpdtDate() {
		return updtDate;
	}

	public void setUpdtDate(Date updtDate) {
		this.updtDate = updtDate;
	}

	public String getUpdtUsrid() {
		return updtUsrid;
	}

	public void setUpdtUsrid(String updtUsrid) {
		this.updtUsrid = updtUsrid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNextSbs() {
		return nextSbs;
	}

	public void setNextSbs(String nextSbs) {
		this.nextSbs = nextSbs;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public Boolean getExcludeFromProc() {
		return excludeFromProc;
	}

	public void setExcludeFromProc(Boolean excludeFromProc) {
		this.excludeFromProc = excludeFromProc;
	}
}
