package com.castsoftware.endevor.pojo;

import java.util.Date;

public class Sys {
	private String siteId;
	private String envName;
	private String sysName;
	private String stgName;
	private String stgId;
	private Integer stgSeqNum;
	private Integer rcdUpdtCnt;
	private Date updtDate; //"2018-03-22T15:42:00.00+0000"
	private String updtUsrId;
	private String title;
	private String nextSys;
	private String procLoadLib;
	private String procListLib;
	private Boolean commentReq;
	private Boolean ccidReq;
	private Boolean signoutReq;
	private Boolean validateRetrDsn;
	private Boolean jumpOptReq;
	private Date backupDate; //"0002-11-30T00:00:00.00+0000"
	private String relId;
	private Boolean dupElmReg;
	private String dupElmRegSevLl;
	private Boolean procOpReg;
	private String procOpRegSevLl;
	private Boolean elmAutoRetent;
	private Integer retainElmlvlForMths;
	private Boolean cmpAutoRetent;
	private Integer retainCmplvlForMths;
	private Boolean procOpRegAcrossSbs;
	
	@Override
    public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
        Sys other = (Sys) obj ;
        if (sysName.equals("*"))
        	return sysName.equals(other.sysName);
        else
        	return sysName.equals(other.sysName) && envName.equals(other.envName) && stgId.equals(other.stgId);
    }
	
	@Override
	public String toString() {
		if (sysName.equals("*"))
			return sysName;
		else
			return String.format("[%s] [Stg:%s - %s] %s", envName, stgId, stgName, sysName);
	}
	
	public static Sys getStarSys() {
		return new Sys(null, null, "*", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null);
	}

	public Sys(String siteId, String envName, String sysName, String stgName, String stgId, Integer stgSeqNum,
			Integer rcdUpdtCnt, Date updtDate, String updtUsrId, String title, String nextSys, String procLoadLib,
			String procListLib, Boolean commentReq, Boolean ccidReq, Boolean signoutReq, Boolean validateRetrDsn,
			Boolean jumpOptReq, Date backupDate, String relId, Boolean dupElmReg, String dupElmRegSevLl,
			Boolean procOpReg, String procOpRegSevLl, Boolean elmAutoRetent, Integer retainElmlvlForMths,
			Boolean cmpAutoRetent, Integer retainCmplvlForMths, Boolean procOpRegAcrossSbs) {
		super();
		this.siteId = siteId;
		this.envName = envName;
		this.sysName = sysName;
		this.stgName = stgName;
		this.stgId = stgId;
		this.stgSeqNum = stgSeqNum;
		this.rcdUpdtCnt = rcdUpdtCnt;
		this.updtDate = updtDate;
		this.updtUsrId = updtUsrId;
		this.title = title;
		this.nextSys = nextSys;
		this.procLoadLib = procLoadLib;
		this.procListLib = procListLib;
		this.commentReq = commentReq;
		this.ccidReq = ccidReq;
		this.signoutReq = signoutReq;
		this.validateRetrDsn = validateRetrDsn;
		this.jumpOptReq = jumpOptReq;
		this.backupDate = backupDate;
		this.relId = relId;
		this.dupElmReg = dupElmReg;
		this.dupElmRegSevLl = dupElmRegSevLl;
		this.procOpReg = procOpReg;
		this.procOpRegSevLl = procOpRegSevLl;
		this.elmAutoRetent = elmAutoRetent;
		this.retainElmlvlForMths = retainElmlvlForMths;
		this.cmpAutoRetent = cmpAutoRetent;
		this.retainCmplvlForMths = retainCmplvlForMths;
		this.procOpRegAcrossSbs = procOpRegAcrossSbs;
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

	public String getUpdtUsrId() {
		return updtUsrId;
	}

	public void setUpdtUsrId(String updtUsrId) {
		this.updtUsrId = updtUsrId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNextSys() {
		return nextSys;
	}

	public void setNextSys(String nextSys) {
		this.nextSys = nextSys;
	}

	public String getProcLoadLib() {
		return procLoadLib;
	}

	public void setProcLoadLib(String procLoadLib) {
		this.procLoadLib = procLoadLib;
	}

	public String getProcListLib() {
		return procListLib;
	}

	public void setProcListLib(String procListLib) {
		this.procListLib = procListLib;
	}

	public Boolean getCommentReq() {
		return commentReq;
	}

	public void setCommentReq(Boolean commentReq) {
		this.commentReq = commentReq;
	}

	public Boolean getCcidReq() {
		return ccidReq;
	}

	public void setCcidReq(Boolean ccidReq) {
		this.ccidReq = ccidReq;
	}

	public Boolean getSignoutReq() {
		return signoutReq;
	}

	public void setSignoutReq(Boolean signoutReq) {
		this.signoutReq = signoutReq;
	}

	public Boolean getValidateRetrDsn() {
		return validateRetrDsn;
	}

	public void setValidateRetrDsn(Boolean validateRetrDsn) {
		this.validateRetrDsn = validateRetrDsn;
	}

	public Boolean getJumpOptReq() {
		return jumpOptReq;
	}

	public void setJumpOptReq(Boolean jumpOptReq) {
		this.jumpOptReq = jumpOptReq;
	}

	public Date getBackupDate() {
		return backupDate;
	}

	public void setBackupDate(Date backupDate) {
		this.backupDate = backupDate;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public Boolean getDupElmReg() {
		return dupElmReg;
	}

	public void setDupElmReg(Boolean dupElmReg) {
		this.dupElmReg = dupElmReg;
	}

	public String getDupElmRegSevLl() {
		return dupElmRegSevLl;
	}

	public void setDupElmRegSevLl(String dupElmRegSevLl) {
		this.dupElmRegSevLl = dupElmRegSevLl;
	}

	public Boolean getProcOpReg() {
		return procOpReg;
	}

	public void setProcOpReg(Boolean procOpReg) {
		this.procOpReg = procOpReg;
	}

	public String getProcOpRegSevLl() {
		return procOpRegSevLl;
	}

	public void setProcOpRegSevLl(String procOpRegSevLl) {
		this.procOpRegSevLl = procOpRegSevLl;
	}

	public Boolean getElmAutoRetent() {
		return elmAutoRetent;
	}

	public void setElmAutoRetent(Boolean elmAutoRetent) {
		this.elmAutoRetent = elmAutoRetent;
	}

	public Integer getRetainElmlvlForMths() {
		return retainElmlvlForMths;
	}

	public void setRetainElmlvlForMths(Integer retainElmlvlForMths) {
		this.retainElmlvlForMths = retainElmlvlForMths;
	}

	public Boolean getCmpAutoRetent() {
		return cmpAutoRetent;
	}

	public void setCmpAutoRetent(Boolean cmpAutoRetent) {
		this.cmpAutoRetent = cmpAutoRetent;
	}

	public Integer getRetainCmplvlForMths() {
		return retainCmplvlForMths;
	}

	public void setRetainCmplvlForMths(Integer retainCmplvlForMths) {
		this.retainCmplvlForMths = retainCmplvlForMths;
	}

	public Boolean getProcOpRegAcrossSbs() {
		return procOpRegAcrossSbs;
	}

	public void setProcOpRegAcrossSbs(Boolean procOpRegAcrossSbs) {
		this.procOpRegAcrossSbs = procOpRegAcrossSbs;
	}
}
