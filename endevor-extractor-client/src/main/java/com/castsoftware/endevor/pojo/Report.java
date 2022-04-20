package com.castsoftware.endevor.pojo;

public class Report {
	public String APIMSGS;
	public String C1MSGS1;
	public String C1MSGSA;	
	
	@Override
	public String toString() {
		return String.format("APIMSGS: %s\nC1MSGS1: %s\nC1MSGSA: %s", APIMSGS, C1MSGS1, C1MSGSA);
	}
}
