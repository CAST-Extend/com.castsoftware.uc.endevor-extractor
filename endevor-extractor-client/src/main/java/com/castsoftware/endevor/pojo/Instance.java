package com.castsoftware.endevor.pojo;

public class Instance 
{
	private String description;
	private String message;
	private String name;
	private String status;
	private String jobName;
	private String programName;
	private String hostName;
	private String comments;
	private String userId;
	private String password;
	private Integer poolInitSize;
	private Integer poolIncrSize;
	private Integer poolMaxSize;
	private Integer poolReapTime;
	private Integer unusedTimeout;
	private Integer agedTimeout;
	private String lang;
	private String timeZone;
	private String characterSet;
	private String codepage;
	private String contentType;
	private String traced;
	
	@Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Instance)) {
            return false ;
        }
        Instance other = (Instance) obj ;
        return name.equals(other.name);
    }
	
	@Override
	public String toString() {
		return name;
	}
	
	public static Instance getStarInstance() {
		return new Instance(null, null, "*", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				 null, null, null, null, null);
	}

	public Instance(String description, String message, String name, String status, String jobName, String programName,
			String hostName, String comments, String userId, String password, Integer poolInitSize,
			Integer poolIncrSize, Integer poolMaxSize, Integer poolReapTime, Integer unusedTimeout, Integer agedTimeout,
			String lang, String timeZone, String characterSet, String codepage, String contentType, String traced) {
		super();
		this.description = description;
		this.message = message;
		this.name = name;
		this.status = status;
		this.jobName = jobName;
		this.programName = programName;
		this.hostName = hostName;
		this.comments = comments;
		this.userId = userId;
		this.password = password;
		this.poolInitSize = poolInitSize;
		this.poolIncrSize = poolIncrSize;
		this.poolMaxSize = poolMaxSize;
		this.poolReapTime = poolReapTime;
		this.unusedTimeout = unusedTimeout;
		this.agedTimeout = agedTimeout;
		this.lang = lang;
		this.timeZone = timeZone;
		this.characterSet = characterSet;
		this.codepage = codepage;
		this.contentType = contentType;
		this.traced = traced;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPoolInitSize() {
		return poolInitSize;
	}

	public void setPoolInitSize(Integer poolInitSize) {
		this.poolInitSize = poolInitSize;
	}

	public Integer getPoolIncrSize() {
		return poolIncrSize;
	}

	public void setPoolIncrSize(Integer poolIncrSize) {
		this.poolIncrSize = poolIncrSize;
	}

	public Integer getPoolMaxSize() {
		return poolMaxSize;
	}

	public void setPoolMaxSize(Integer poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}

	public Integer getPoolReapTime() {
		return poolReapTime;
	}

	public void setPoolReapTime(Integer poolReapTime) {
		this.poolReapTime = poolReapTime;
	}

	public Integer getUnusedTimeout() {
		return unusedTimeout;
	}

	public void setUnusedTimeout(Integer unusedTimeout) {
		this.unusedTimeout = unusedTimeout;
	}

	public Integer getAgedTimeout() {
		return agedTimeout;
	}

	public void setAgedTimeout(Integer agedTimeout) {
		this.agedTimeout = agedTimeout;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getCharacterSet() {
		return characterSet;
	}

	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}

	public String getCodepage() {
		return codepage;
	}

	public void setCodepage(String codepage) {
		this.codepage = codepage;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTraced() {
		return traced;
	}

	public void setTraced(String traced) {
		this.traced = traced;
	}
}
