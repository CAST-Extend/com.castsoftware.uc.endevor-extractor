package com.castsoftware.endevor.pojo;

public class Env {
	private String envName;
	
	@Override
    public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
        Env other = (Env) obj ;
        return envName.equals(other.envName);
    }
	
	@Override
	public String toString() {
		return envName;
	}
	
	public static Env getStarEnv() {
		return new Env("*");
	}

	public Env(String envName) {
		super();
		this.envName = envName;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}
}
