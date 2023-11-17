package com.example.ZumbaRegistry.model;

public class Batch {

	private int bid;
	private String batchName;
	private String batchInstructor;
	private int capacity;
	private int batchDuration;

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

	public String getBatchInstructor() {
		return batchInstructor;
	}

	public void setBatchInstructor(String batchInstructor) {
		this.batchInstructor = batchInstructor;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getBatchDuration() {
		return batchDuration;
	}

	public void setBatchDuration(int batchDuration) {
		this.batchDuration = batchDuration;
	}

	public Batch() {
		super();
	}

	public Batch(int bid, String batchName, String batchInstructor, int capacity, int batchDuration) {
		super();
		this.bid = bid;
		this.batchName = batchName;
		this.batchInstructor = batchInstructor;
		this.capacity = capacity;
		this.batchDuration = batchDuration;
	}

	@Override
	public String toString() {
		return "Batch [bid=" + bid + ", batchName=" + batchName + ", batchInstructor=" + batchInstructor + ", capacity="
				+ capacity + ", batchDuration=" + batchDuration + "]";
	}

}
