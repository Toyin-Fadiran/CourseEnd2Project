package com.example.ZumbaRegistry.model;

public class Participant {


	private int pid;
	private String name;
	private String phone;
	private String email;
	private int bid;
	private String batchName;
	
	
	
	public Participant() {
		super();
	}
	

	public Participant(int pid, String name, String phone, String email, int bid, String batchName) {
		super();
		this.pid = pid;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.bid = bid;
		this.batchName = batchName;
	}
	
	
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}


	public String getBatchName() {
		return batchName;
	}


	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}





	

}

