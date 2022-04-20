package com.castsoftware.endevor.pojo;

import java.util.Date;

public class Type {
	private String siteId;
	private String envName;
	private String sysName;
	private String typeName;
	private String typeId;
	private String stgName;
	private String stgId;
	private String stgNum;
	private Integer stgSeqNum;
	private Integer rcdUpdtCnt;
	private Date updtDate; //"2017-03-23T10:56:00.00+0000",
	private String updtUsrid;
	private String relId;
	private String nextType;
	private String description;
	private String dfltProcGrp;
	private String dataFm;
	private String fileExt;
	private String lang;
	private String pvLbLang;
	private String regrPct;
	private String regrSev;
	private String srcLgt;
	private String compF;
	private String compT;
	private Boolean autoConsol;
	private String consolLl;
	private String autoConsolLl;
	private Boolean cmpntAutoConsol;
	private String cmpntConsolLl;
	private String cmpmtAutoConsolLl;
	private Boolean expandIncl;
	private String elmDelta;
	private String cmpntDelta;
	private Boolean compressBase;
	private Boolean elmNameNotNcr;
	private String srcOpDsType;
	private String srcOpDsn;
	private String inclDsType;
	private String inclDsn;
	private String baseDsType;
	private String baseimgDsn;
	private String deltaDsType;
	private String deltaDsn;
	private String eleRecfm;
	private String ussDlim;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((envName == null) ? 0 : envName.hashCode());
		result = prime * result + ((stgName == null) ? 0 : stgName.hashCode());
		result = prime * result + ((sysName == null) ? 0 : sysName.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
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
		
		Type other = (Type) obj;
		if (typeName.equals("*"))
        	return typeName.equals(other.typeName);
        else
        	return typeName.equals(other.typeName) && envName.equals(other.envName) 
        			&& stgId.equals(other.stgId) && sysName.equals(other.sysName);
	}
	
	@Override
	public String toString() {
		if (typeName.equals("*"))
			return typeName;
		else
			return String.format("[%s] [Stg:%s - %s] [Sys: %s] %s", envName, stgId, stgName, sysName, typeName);
	}
	
	public static Type getStarType() {
		return new Type(null, null, null, "*", null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
				null, null);
	}
	public Type(String siteId, String envName, String sysName, String typeName, String typeId, String stgName,
			String stgId, String stgNum, Integer stgSeqNum, Integer rcdUpdtCnt, Date updtDate, String updtUsrid,
			String relId, String nextType, String description, String dfltProcGrp, String dataFm, String fileExt,
			String lang, String pvLbLang, String regrPct, String regrSev, String srcLgt, String compF, String compT,
			Boolean autoConsol, String consolLl, String autoConsolLl, Boolean cmpntAutoConsol, String cmpntConsolLl,
			String cmpmtAutoConsolLl, Boolean expandIncl, String elmDelta, String cmpntDelta, Boolean compressBase,
			Boolean elmNameNotNcr, String srcOpDsType, String srcOpDsn, String inclDsType, String inclDsn,
			String baseDsType, String baseimgDsn, String deltaDsType, String deltaDsn, String eleRecfm,
			String ussDlim) {
		super();
		this.siteId = siteId;
		this.envName = envName;
		this.sysName = sysName;
		this.typeName = typeName;
		this.typeId = typeId;
		this.stgName = stgName;
		this.stgId = stgId;
		this.stgNum = stgNum;
		this.stgSeqNum = stgSeqNum;
		this.rcdUpdtCnt = rcdUpdtCnt;
		this.updtDate = updtDate;
		this.updtUsrid = updtUsrid;
		this.relId = relId;
		this.nextType = nextType;
		this.description = description;
		this.dfltProcGrp = dfltProcGrp;
		this.dataFm = dataFm;
		this.fileExt = fileExt;
		this.lang = lang;
		this.pvLbLang = pvLbLang;
		this.regrPct = regrPct;
		this.regrSev = regrSev;
		this.srcLgt = srcLgt;
		this.compF = compF;
		this.compT = compT;
		this.autoConsol = autoConsol;
		this.consolLl = consolLl;
		this.autoConsolLl = autoConsolLl;
		this.cmpntAutoConsol = cmpntAutoConsol;
		this.cmpntConsolLl = cmpntConsolLl;
		this.cmpmtAutoConsolLl = cmpmtAutoConsolLl;
		this.expandIncl = expandIncl;
		this.elmDelta = elmDelta;
		this.cmpntDelta = cmpntDelta;
		this.compressBase = compressBase;
		this.elmNameNotNcr = elmNameNotNcr;
		this.srcOpDsType = srcOpDsType;
		this.srcOpDsn = srcOpDsn;
		this.inclDsType = inclDsType;
		this.inclDsn = inclDsn;
		this.baseDsType = baseDsType;
		this.baseimgDsn = baseimgDsn;
		this.deltaDsType = deltaDsType;
		this.deltaDsn = deltaDsn;
		this.eleRecfm = eleRecfm;
		this.ussDlim = ussDlim;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
	public String getStgNum() {
		return stgNum;
	}
	public void setStgNum(String stgNum) {
		this.stgNum = stgNum;
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
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public String getNextType() {
		return nextType;
	}
	public void setNextType(String nextType) {
		this.nextType = nextType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDfltProcGrp() {
		return dfltProcGrp;
	}
	public void setDfltProcGrp(String dfltProcGrp) {
		this.dfltProcGrp = dfltProcGrp;
	}
	public String getDataFm() {
		return dataFm;
	}
	public void setDataFm(String dataFm) {
		this.dataFm = dataFm;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getPvLbLang() {
		return pvLbLang;
	}
	public void setPvLbLang(String pvLbLang) {
		this.pvLbLang = pvLbLang;
	}
	public String getRegrPct() {
		return regrPct;
	}
	public void setRegrPct(String regrPct) {
		this.regrPct = regrPct;
	}
	public String getRegrSev() {
		return regrSev;
	}
	public void setRegrSev(String regrSev) {
		this.regrSev = regrSev;
	}
	public String getSrcLgt() {
		return srcLgt;
	}
	public void setSrcLgt(String srcLgt) {
		this.srcLgt = srcLgt;
	}
	public String getCompF() {
		return compF;
	}
	public void setCompF(String compF) {
		this.compF = compF;
	}
	public String getCompT() {
		return compT;
	}
	public void setCompT(String compT) {
		this.compT = compT;
	}
	public Boolean getAutoConsol() {
		return autoConsol;
	}
	public void setAutoConsol(Boolean autoConsol) {
		this.autoConsol = autoConsol;
	}
	public String getConsolLl() {
		return consolLl;
	}
	public void setConsolLl(String consolLl) {
		this.consolLl = consolLl;
	}
	public String getAutoConsolLl() {
		return autoConsolLl;
	}
	public void setAutoConsolLl(String autoConsolLl) {
		this.autoConsolLl = autoConsolLl;
	}
	public Boolean getCmpntAutoConsol() {
		return cmpntAutoConsol;
	}
	public void setCmpntAutoConsol(Boolean cmpntAutoConsol) {
		this.cmpntAutoConsol = cmpntAutoConsol;
	}
	public String getCmpntConsolLl() {
		return cmpntConsolLl;
	}
	public void setCmpntConsolLl(String cmpntConsolLl) {
		this.cmpntConsolLl = cmpntConsolLl;
	}
	public String getCmpmtAutoConsolLl() {
		return cmpmtAutoConsolLl;
	}
	public void setCmpmtAutoConsolLl(String cmpmtAutoConsolLl) {
		this.cmpmtAutoConsolLl = cmpmtAutoConsolLl;
	}
	public Boolean getExpandIncl() {
		return expandIncl;
	}
	public void setExpandIncl(Boolean expandIncl) {
		this.expandIncl = expandIncl;
	}
	public String getElmDelta() {
		return elmDelta;
	}
	public void setElmDelta(String elmDelta) {
		this.elmDelta = elmDelta;
	}
	public String getCmpntDelta() {
		return cmpntDelta;
	}
	public void setCmpntDelta(String cmpntDelta) {
		this.cmpntDelta = cmpntDelta;
	}
	public Boolean getCompressBase() {
		return compressBase;
	}
	public void setCompressBase(Boolean compressBase) {
		this.compressBase = compressBase;
	}
	public Boolean getElmNameNotNcr() {
		return elmNameNotNcr;
	}
	public void setElmNameNotNcr(Boolean elmNameNotNcr) {
		this.elmNameNotNcr = elmNameNotNcr;
	}
	public String getSrcOpDsType() {
		return srcOpDsType;
	}
	public void setSrcOpDsType(String srcOpDsType) {
		this.srcOpDsType = srcOpDsType;
	}
	public String getSrcOpDsn() {
		return srcOpDsn;
	}
	public void setSrcOpDsn(String srcOpDsn) {
		this.srcOpDsn = srcOpDsn;
	}
	public String getInclDsType() {
		return inclDsType;
	}
	public void setInclDsType(String inclDsType) {
		this.inclDsType = inclDsType;
	}
	public String getInclDsn() {
		return inclDsn;
	}
	public void setInclDsn(String inclDsn) {
		this.inclDsn = inclDsn;
	}
	public String getBaseDsType() {
		return baseDsType;
	}
	public void setBaseDsType(String baseDsType) {
		this.baseDsType = baseDsType;
	}
	public String getBaseimgDsn() {
		return baseimgDsn;
	}
	public void setBaseimgDsn(String baseimgDsn) {
		this.baseimgDsn = baseimgDsn;
	}
	public String getDeltaDsType() {
		return deltaDsType;
	}
	public void setDeltaDsType(String deltaDsType) {
		this.deltaDsType = deltaDsType;
	}
	public String getDeltaDsn() {
		return deltaDsn;
	}
	public void setDeltaDsn(String deltaDsn) {
		this.deltaDsn = deltaDsn;
	}
	public String getEleRecfm() {
		return eleRecfm;
	}
	public void setEleRecfm(String eleRecfm) {
		this.eleRecfm = eleRecfm;
	}
	public String getUssDlim() {
		return ussDlim;
	}
	public void setUssDlim(String ussDlim) {
		this.ussDlim = ussDlim;
	}
}
