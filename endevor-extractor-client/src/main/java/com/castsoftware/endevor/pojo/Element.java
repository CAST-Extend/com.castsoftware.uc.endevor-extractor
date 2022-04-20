package com.castsoftware.endevor.pojo;

import java.util.Date;

public class Element {
	private String rcdType;
	private String sideId;
	private String envName;
	private String sysName;
	private String sbsName;
	private String elmName;
	private String fullElmName;
	private String typeName;
	private String stgName;
	private String stgId;
	private String stgNum;
	private String stgSeqNum;
	private String procGrpName;
	private Date updtDate; //"2017-06-07T12:14:33.99+0000",
	private String signoutId;
	private String elmVVLL;
	private String cmpntVVLL;
	private String relId;
	private String lastActModElm;
	private String lastAct;
	private String endevorRC;
	private Date lastActDate; //"2017-06-07T12:14:00.00+0000",
	private String lastActUserid;
	private String lastActCcid;
	private String lastActComment;
	private String elmBaseMbr;
	private Date elmBaseDate; //"2017-04-04T14:05:07.09+0000",
	private String elmBaseStmtsNum;
	private String baseVVLL;
	private String baseCompressed;
	private String baseUserid;
	private String baseComment;
	private String elmDeltaMbrName;
	private Date elmLastLLDate; //"2017-04-04T14:10:57.43+0000",
	private String elmLastLLStmtsNum;
	private String elmLastLLUsrid;
	private String elmLastLLCcid;
	private String elmLastLLComment;
	private String elmLastLLInsertCnt;
	private String elmLastLLDeleteCnt;
	private String elmDeltaFormat;
	private String cmpntMbr;
	private String cmpntDate; //"2017-04-05T14:25:00.00+0000"
	private String cmpntBaseStmtsCnt;
	private String cmpntBaseVVLL;
	private String cmpntDeltaMbrName;
	private String cmpntLastLLDate; //"2017-04-05T14:25:00.00+0000"
	private String cmpntLastLLStmtsCnt;
	private String cmpntLastLLInsertCnt;
	private String cmpntLastLLDeleteCnt;
	private String cmpntDeltaFormat;
	private String cmpntListMonitor;
	private String cmpntListCopied;
	private String cmpntListLoc;
	private String moveDate; //"2017-05-03T09:25:00.00+0000"
	private String moveUsrid;
	private String procFlg;
	private String procFailed;
	private Date lastProcDate; //"2017-06-07T12:14:00.00+0000",
	private Date generateDate; //"2017-06-07T12:14:00.00+0000",
	private String generateUsrid;
	private String generateCcid;
	private String generateComment;
	private String failedLit;
	private String procName;
	private String procRC;
	private String retrDate; //"2018-03-28T13:26:00.00+0000"
	private String retrUsrid;
	private String retrCcid;
	private String retrComment;
	//public String pkgIdS" : null,
	//public String pkgExecDateS" : null,
	//public String backedOutO" : null,
	//public String pkgIdO" : null,
	//public String pkgExecDateO" : null,
	//public String pkgId" : null,
	//public String lockedDate" : null,
	private String siteIdF;
	private String envNameF;
	private String sysNameF;
	private String sbsNameF;
	private String elmF;
	private String typeNameF;
	private String stgNumF;
	private String actThatUpdtF;
	private String vvllF;
	private String addUpdtDsnPathF;
	private String addUpdtMbrFileF;
	private String retrDsnPathT;
	//public String retrMbrFileT" : null,
	private String userData;
	private String nosource;
	//public String altFirstDate" : null,
	//public String altLastDate" : null,
	//public String altLastUserid" : null,
	//public String altCumulativeCnt" : null,
	//public String altCcg" : null,
	//public String altCcl" : null,
	//public String altCcr" : null,
	//public String altDes" : null,
	//public String altPrg" : null,
	//public String altSgn" : null,
	//public String altUserData" : null,
	private String signoutDate;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elmName == null) ? 0 : elmName.hashCode());
		result = prime * result + ((envName == null) ? 0 : envName.hashCode());
		result = prime * result + ((sysName == null) ? 0 : sysName.hashCode());
		result = prime * result + ((sbsName == null) ? 0 : sbsName.hashCode());
		result = prime * result + ((stgId == null) ? 0 : stgId.hashCode());
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
		
		Element other = (Element) obj;
		return elmName.equals(other.elmName) && sbsName.equals(other.sbsName) && typeName.equals(other.typeName) 
				&& envName.equals(other.envName) && stgId.equals(other.stgId) && sysName.equals(other.sysName);
	}
	
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s/%s/%s", 
				envName, stgId, sysName, sbsName, typeName, elmName);
	}
	public Element(String rcdType, String sideId, String envName, String sysName, String sbsName, String elmName,
			String fullElmName, String typeName, String stgName, String stgId, String stgNum, String stgSeqNum,
			String procGrpName, Date updtDate, String signoutId, String elmVVLL, String cmpntVVLL, String relId,
			String lastActModElm, String lastAct, String endevorRC, Date lastActDate, String lastActUserid,
			String lastActCcid, String lastActComment, String elmBaseMbr, Date elmBaseDate, String elmBaseStmtsNum,
			String baseVVLL, String baseCompressed, String baseUserid, String baseComment, String elmDeltaMbrName,
			Date elmLastLLDate, String elmLastLLStmtsNum, String elmLastLLUsrid, String elmLastLLCcid,
			String elmLastLLComment, String elmLastLLInsertCnt, String elmLastLLDeleteCnt, String elmDeltaFormat,
			String cmpntMbr, String cmpntDate, String cmpntBaseStmtsCnt, String cmpntBaseVVLL, String cmpntDeltaMbrName,
			String cmpntLastLLDate, String cmpntLastLLStmtsCnt, String cmpntLastLLInsertCnt,
			String cmpntLastLLDeleteCnt, String cmpntDeltaFormat, String cmpntListMonitor, String cmpntListCopied,
			String cmpntListLoc, String moveDate, String moveUsrid, String procFlg, String procFailed,
			Date lastProcDate, Date generateDate, String generateUsrid, String generateCcid, String generateComment,
			String failedLit, String procName, String procRC, String retrDate, String retrUsrid, String retrCcid,
			String retrComment, String siteIdF, String envNameF, String sysNameF, String sbsNameF, String elmF,
			String typeNameF, String stgNumF, String actThatUpdtF, String vvllF, String addUpdtDsnPathF,
			String addUpdtMbrFileF, String retrDsnPathT, String userData, String nosource, String signoutDate) {
		super();
		this.rcdType = rcdType;
		this.sideId = sideId;
		this.envName = envName;
		this.sysName = sysName;
		this.sbsName = sbsName;
		this.elmName = elmName;
		this.fullElmName = fullElmName;
		this.typeName = typeName;
		this.stgName = stgName;
		this.stgId = stgId;
		this.stgNum = stgNum;
		this.stgSeqNum = stgSeqNum;
		this.procGrpName = procGrpName;
		this.updtDate = updtDate;
		this.signoutId = signoutId;
		this.elmVVLL = elmVVLL;
		this.cmpntVVLL = cmpntVVLL;
		this.relId = relId;
		this.lastActModElm = lastActModElm;
		this.lastAct = lastAct;
		this.endevorRC = endevorRC;
		this.lastActDate = lastActDate;
		this.lastActUserid = lastActUserid;
		this.lastActCcid = lastActCcid;
		this.lastActComment = lastActComment;
		this.elmBaseMbr = elmBaseMbr;
		this.elmBaseDate = elmBaseDate;
		this.elmBaseStmtsNum = elmBaseStmtsNum;
		this.baseVVLL = baseVVLL;
		this.baseCompressed = baseCompressed;
		this.baseUserid = baseUserid;
		this.baseComment = baseComment;
		this.elmDeltaMbrName = elmDeltaMbrName;
		this.elmLastLLDate = elmLastLLDate;
		this.elmLastLLStmtsNum = elmLastLLStmtsNum;
		this.elmLastLLUsrid = elmLastLLUsrid;
		this.elmLastLLCcid = elmLastLLCcid;
		this.elmLastLLComment = elmLastLLComment;
		this.elmLastLLInsertCnt = elmLastLLInsertCnt;
		this.elmLastLLDeleteCnt = elmLastLLDeleteCnt;
		this.elmDeltaFormat = elmDeltaFormat;
		this.cmpntMbr = cmpntMbr;
		this.cmpntDate = cmpntDate;
		this.cmpntBaseStmtsCnt = cmpntBaseStmtsCnt;
		this.cmpntBaseVVLL = cmpntBaseVVLL;
		this.cmpntDeltaMbrName = cmpntDeltaMbrName;
		this.cmpntLastLLDate = cmpntLastLLDate;
		this.cmpntLastLLStmtsCnt = cmpntLastLLStmtsCnt;
		this.cmpntLastLLInsertCnt = cmpntLastLLInsertCnt;
		this.cmpntLastLLDeleteCnt = cmpntLastLLDeleteCnt;
		this.cmpntDeltaFormat = cmpntDeltaFormat;
		this.cmpntListMonitor = cmpntListMonitor;
		this.cmpntListCopied = cmpntListCopied;
		this.cmpntListLoc = cmpntListLoc;
		this.moveDate = moveDate;
		this.moveUsrid = moveUsrid;
		this.procFlg = procFlg;
		this.procFailed = procFailed;
		this.lastProcDate = lastProcDate;
		this.generateDate = generateDate;
		this.generateUsrid = generateUsrid;
		this.generateCcid = generateCcid;
		this.generateComment = generateComment;
		this.failedLit = failedLit;
		this.procName = procName;
		this.procRC = procRC;
		this.retrDate = retrDate;
		this.retrUsrid = retrUsrid;
		this.retrCcid = retrCcid;
		this.retrComment = retrComment;
		this.siteIdF = siteIdF;
		this.envNameF = envNameF;
		this.sysNameF = sysNameF;
		this.sbsNameF = sbsNameF;
		this.elmF = elmF;
		this.typeNameF = typeNameF;
		this.stgNumF = stgNumF;
		this.actThatUpdtF = actThatUpdtF;
		this.vvllF = vvllF;
		this.addUpdtDsnPathF = addUpdtDsnPathF;
		this.addUpdtMbrFileF = addUpdtMbrFileF;
		this.retrDsnPathT = retrDsnPathT;
		this.userData = userData;
		this.nosource = nosource;
		this.signoutDate = signoutDate;
	}
	public String getRcdType() {
		return rcdType;
	}
	public void setRcdType(String rcdType) {
		this.rcdType = rcdType;
	}
	public String getSideId() {
		return sideId;
	}
	public void setSideId(String sideId) {
		this.sideId = sideId;
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
	public String getElmName() {
		return elmName;
	}
	public void setElmName(String elmName) {
		this.elmName = elmName;
	}
	public String getFullElmName() {
		return fullElmName;
	}
	public void setFullElmName(String fullElmName) {
		this.fullElmName = fullElmName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public String getStgSeqNum() {
		return stgSeqNum;
	}
	public void setStgSeqNum(String stgSeqNum) {
		this.stgSeqNum = stgSeqNum;
	}
	public String getProcGrpName() {
		return procGrpName;
	}
	public void setProcGrpName(String procGrpName) {
		this.procGrpName = procGrpName;
	}
	public Date getUpdtDate() {
		return updtDate;
	}
	public void setUpdtDate(Date updtDate) {
		this.updtDate = updtDate;
	}
	public String getSignoutId() {
		return signoutId;
	}
	public void setSignoutId(String signoutId) {
		this.signoutId = signoutId;
	}
	public String getElmVVLL() {
		return elmVVLL;
	}
	public void setElmVVLL(String elmVVLL) {
		this.elmVVLL = elmVVLL;
	}
	public String getCmpntVVLL() {
		return cmpntVVLL;
	}
	public void setCmpntVVLL(String cmpntVVLL) {
		this.cmpntVVLL = cmpntVVLL;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	public String getLastActModElm() {
		return lastActModElm;
	}
	public void setLastActModElm(String lastActModElm) {
		this.lastActModElm = lastActModElm;
	}
	public String getLastAct() {
		return lastAct;
	}
	public void setLastAct(String lastAct) {
		this.lastAct = lastAct;
	}
	public String getEndevorRC() {
		return endevorRC;
	}
	public void setEndevorRC(String endevorRC) {
		this.endevorRC = endevorRC;
	}
	public Date getLastActDate() {
		return lastActDate;
	}
	public void setLastActDate(Date lastActDate) {
		this.lastActDate = lastActDate;
	}
	public String getLastActUserid() {
		return lastActUserid;
	}
	public void setLastActUserid(String lastActUserid) {
		this.lastActUserid = lastActUserid;
	}
	public String getLastActCcid() {
		return lastActCcid;
	}
	public void setLastActCcid(String lastActCcid) {
		this.lastActCcid = lastActCcid;
	}
	public String getLastActComment() {
		return lastActComment;
	}
	public void setLastActComment(String lastActComment) {
		this.lastActComment = lastActComment;
	}
	public String getElmBaseMbr() {
		return elmBaseMbr;
	}
	public void setElmBaseMbr(String elmBaseMbr) {
		this.elmBaseMbr = elmBaseMbr;
	}
	public Date getElmBaseDate() {
		return elmBaseDate;
	}
	public void setElmBaseDate(Date elmBaseDate) {
		this.elmBaseDate = elmBaseDate;
	}
	public String getElmBaseStmtsNum() {
		return elmBaseStmtsNum;
	}
	public void setElmBaseStmtsNum(String elmBaseStmtsNum) {
		this.elmBaseStmtsNum = elmBaseStmtsNum;
	}
	public String getBaseVVLL() {
		return baseVVLL;
	}
	public void setBaseVVLL(String baseVVLL) {
		this.baseVVLL = baseVVLL;
	}
	public String getBaseCompressed() {
		return baseCompressed;
	}
	public void setBaseCompressed(String baseCompressed) {
		this.baseCompressed = baseCompressed;
	}
	public String getBaseUserid() {
		return baseUserid;
	}
	public void setBaseUserid(String baseUserid) {
		this.baseUserid = baseUserid;
	}
	public String getBaseComment() {
		return baseComment;
	}
	public void setBaseComment(String baseComment) {
		this.baseComment = baseComment;
	}
	public String getElmDeltaMbrName() {
		return elmDeltaMbrName;
	}
	public void setElmDeltaMbrName(String elmDeltaMbrName) {
		this.elmDeltaMbrName = elmDeltaMbrName;
	}
	public Date getElmLastLLDate() {
		return elmLastLLDate;
	}
	public void setElmLastLLDate(Date elmLastLLDate) {
		this.elmLastLLDate = elmLastLLDate;
	}
	public String getElmLastLLStmtsNum() {
		return elmLastLLStmtsNum;
	}
	public void setElmLastLLStmtsNum(String elmLastLLStmtsNum) {
		this.elmLastLLStmtsNum = elmLastLLStmtsNum;
	}
	public String getElmLastLLUsrid() {
		return elmLastLLUsrid;
	}
	public void setElmLastLLUsrid(String elmLastLLUsrid) {
		this.elmLastLLUsrid = elmLastLLUsrid;
	}
	public String getElmLastLLCcid() {
		return elmLastLLCcid;
	}
	public void setElmLastLLCcid(String elmLastLLCcid) {
		this.elmLastLLCcid = elmLastLLCcid;
	}
	public String getElmLastLLComment() {
		return elmLastLLComment;
	}
	public void setElmLastLLComment(String elmLastLLComment) {
		this.elmLastLLComment = elmLastLLComment;
	}
	public String getElmLastLLInsertCnt() {
		return elmLastLLInsertCnt;
	}
	public void setElmLastLLInsertCnt(String elmLastLLInsertCnt) {
		this.elmLastLLInsertCnt = elmLastLLInsertCnt;
	}
	public String getElmLastLLDeleteCnt() {
		return elmLastLLDeleteCnt;
	}
	public void setElmLastLLDeleteCnt(String elmLastLLDeleteCnt) {
		this.elmLastLLDeleteCnt = elmLastLLDeleteCnt;
	}
	public String getElmDeltaFormat() {
		return elmDeltaFormat;
	}
	public void setElmDeltaFormat(String elmDeltaFormat) {
		this.elmDeltaFormat = elmDeltaFormat;
	}
	public String getCmpntMbr() {
		return cmpntMbr;
	}
	public void setCmpntMbr(String cmpntMbr) {
		this.cmpntMbr = cmpntMbr;
	}
	public String getCmpntDate() {
		return cmpntDate;
	}
	public void setCmpntDate(String cmpntDate) {
		this.cmpntDate = cmpntDate;
	}
	public String getCmpntBaseStmtsCnt() {
		return cmpntBaseStmtsCnt;
	}
	public void setCmpntBaseStmtsCnt(String cmpntBaseStmtsCnt) {
		this.cmpntBaseStmtsCnt = cmpntBaseStmtsCnt;
	}
	public String getCmpntBaseVVLL() {
		return cmpntBaseVVLL;
	}
	public void setCmpntBaseVVLL(String cmpntBaseVVLL) {
		this.cmpntBaseVVLL = cmpntBaseVVLL;
	}
	public String getCmpntDeltaMbrName() {
		return cmpntDeltaMbrName;
	}
	public void setCmpntDeltaMbrName(String cmpntDeltaMbrName) {
		this.cmpntDeltaMbrName = cmpntDeltaMbrName;
	}
	public String getCmpntLastLLDate() {
		return cmpntLastLLDate;
	}
	public void setCmpntLastLLDate(String cmpntLastLLDate) {
		this.cmpntLastLLDate = cmpntLastLLDate;
	}
	public String getCmpntLastLLStmtsCnt() {
		return cmpntLastLLStmtsCnt;
	}
	public void setCmpntLastLLStmtsCnt(String cmpntLastLLStmtsCnt) {
		this.cmpntLastLLStmtsCnt = cmpntLastLLStmtsCnt;
	}
	public String getCmpntLastLLInsertCnt() {
		return cmpntLastLLInsertCnt;
	}
	public void setCmpntLastLLInsertCnt(String cmpntLastLLInsertCnt) {
		this.cmpntLastLLInsertCnt = cmpntLastLLInsertCnt;
	}
	public String getCmpntLastLLDeleteCnt() {
		return cmpntLastLLDeleteCnt;
	}
	public void setCmpntLastLLDeleteCnt(String cmpntLastLLDeleteCnt) {
		this.cmpntLastLLDeleteCnt = cmpntLastLLDeleteCnt;
	}
	public String getCmpntDeltaFormat() {
		return cmpntDeltaFormat;
	}
	public void setCmpntDeltaFormat(String cmpntDeltaFormat) {
		this.cmpntDeltaFormat = cmpntDeltaFormat;
	}
	public String getCmpntListMonitor() {
		return cmpntListMonitor;
	}
	public void setCmpntListMonitor(String cmpntListMonitor) {
		this.cmpntListMonitor = cmpntListMonitor;
	}
	public String getCmpntListCopied() {
		return cmpntListCopied;
	}
	public void setCmpntListCopied(String cmpntListCopied) {
		this.cmpntListCopied = cmpntListCopied;
	}
	public String getCmpntListLoc() {
		return cmpntListLoc;
	}
	public void setCmpntListLoc(String cmpntListLoc) {
		this.cmpntListLoc = cmpntListLoc;
	}
	public String getMoveDate() {
		return moveDate;
	}
	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}
	public String getMoveUsrid() {
		return moveUsrid;
	}
	public void setMoveUsrid(String moveUsrid) {
		this.moveUsrid = moveUsrid;
	}
	public String getProcFlg() {
		return procFlg;
	}
	public void setProcFlg(String procFlg) {
		this.procFlg = procFlg;
	}
	public String getProcFailed() {
		return procFailed;
	}
	public void setProcFailed(String procFailed) {
		this.procFailed = procFailed;
	}
	public Date getLastProcDate() {
		return lastProcDate;
	}
	public void setLastProcDate(Date lastProcDate) {
		this.lastProcDate = lastProcDate;
	}
	public Date getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}
	public String getGenerateUsrid() {
		return generateUsrid;
	}
	public void setGenerateUsrid(String generateUsrid) {
		this.generateUsrid = generateUsrid;
	}
	public String getGenerateCcid() {
		return generateCcid;
	}
	public void setGenerateCcid(String generateCcid) {
		this.generateCcid = generateCcid;
	}
	public String getGenerateComment() {
		return generateComment;
	}
	public void setGenerateComment(String generateComment) {
		this.generateComment = generateComment;
	}
	public String getFailedLit() {
		return failedLit;
	}
	public void setFailedLit(String failedLit) {
		this.failedLit = failedLit;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcRC() {
		return procRC;
	}
	public void setProcRC(String procRC) {
		this.procRC = procRC;
	}
	public String getRetrDate() {
		return retrDate;
	}
	public void setRetrDate(String retrDate) {
		this.retrDate = retrDate;
	}
	public String getRetrUsrid() {
		return retrUsrid;
	}
	public void setRetrUsrid(String retrUsrid) {
		this.retrUsrid = retrUsrid;
	}
	public String getRetrCcid() {
		return retrCcid;
	}
	public void setRetrCcid(String retrCcid) {
		this.retrCcid = retrCcid;
	}
	public String getRetrComment() {
		return retrComment;
	}
	public void setRetrComment(String retrComment) {
		this.retrComment = retrComment;
	}
	public String getSiteIdF() {
		return siteIdF;
	}
	public void setSiteIdF(String siteIdF) {
		this.siteIdF = siteIdF;
	}
	public String getEnvNameF() {
		return envNameF;
	}
	public void setEnvNameF(String envNameF) {
		this.envNameF = envNameF;
	}
	public String getSysNameF() {
		return sysNameF;
	}
	public void setSysNameF(String sysNameF) {
		this.sysNameF = sysNameF;
	}
	public String getSbsNameF() {
		return sbsNameF;
	}
	public void setSbsNameF(String sbsNameF) {
		this.sbsNameF = sbsNameF;
	}
	public String getElmF() {
		return elmF;
	}
	public void setElmF(String elmF) {
		this.elmF = elmF;
	}
	public String getTypeNameF() {
		return typeNameF;
	}
	public void setTypeNameF(String typeNameF) {
		this.typeNameF = typeNameF;
	}
	public String getStgNumF() {
		return stgNumF;
	}
	public void setStgNumF(String stgNumF) {
		this.stgNumF = stgNumF;
	}
	public String getActThatUpdtF() {
		return actThatUpdtF;
	}
	public void setActThatUpdtF(String actThatUpdtF) {
		this.actThatUpdtF = actThatUpdtF;
	}
	public String getVvllF() {
		return vvllF;
	}
	public void setVvllF(String vvllF) {
		this.vvllF = vvllF;
	}
	public String getAddUpdtDsnPathF() {
		return addUpdtDsnPathF;
	}
	public void setAddUpdtDsnPathF(String addUpdtDsnPathF) {
		this.addUpdtDsnPathF = addUpdtDsnPathF;
	}
	public String getAddUpdtMbrFileF() {
		return addUpdtMbrFileF;
	}
	public void setAddUpdtMbrFileF(String addUpdtMbrFileF) {
		this.addUpdtMbrFileF = addUpdtMbrFileF;
	}
	public String getRetrDsnPathT() {
		return retrDsnPathT;
	}
	public void setRetrDsnPathT(String retrDsnPathT) {
		this.retrDsnPathT = retrDsnPathT;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	public String getNosource() {
		return nosource;
	}
	public void setNosource(String nosource) {
		this.nosource = nosource;
	}
	public String getSignoutDate() {
		return signoutDate;
	}
	public void setSignoutDate(String signoutDate) {
		this.signoutDate = signoutDate;
	}
}
