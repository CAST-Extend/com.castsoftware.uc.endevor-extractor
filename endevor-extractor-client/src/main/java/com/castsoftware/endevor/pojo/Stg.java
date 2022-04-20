package com.castsoftware.endevor.pojo;

public class Stg {
	private String stgId;
	//private String stgName;
	//private Integer stgSeqNum;
	
	@Override
    public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Stg other = (Stg) obj ;
		return stgId.equals(other.stgId);
    }
	
	@Override
	public String toString() {
		return stgId;
	}
	
	public static Stg getStarStg() {
		return new Stg("*");
	}

	public Stg(String stgId) {
		super();
		this.stgId = stgId;
	}

	public String getStgId() {
		return stgId;
	}

	public void setStgId(String stgId) {
		this.stgId = stgId;
	}
}
