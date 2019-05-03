package com.EMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leaveBalance")
public class LeaveBalanceModel {
	
	@Id
	@Column(name = "leaveBalanceId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long leaveBalanceId;
	
	private long totalLeave;
	private double clBalance,elBalance,slBalance;
	private int quater;
	private long year;
	
	@OneToOne
	private UserModel user;
	
	
	
	
	public double getClBalance() {
		return clBalance;
	}

	public void setClBalance(double clBalance) {
		this.clBalance = clBalance;
	}

	public double getElBalance() {
		return elBalance;
	}

	public void setElBalance(double elBalance) {
		this.elBalance = elBalance;
	}

	public double getSlBalance() {
		return slBalance;
	}

	public void setSlBalance(double slBalance) {
		this.slBalance = slBalance;
	}

	public int getQuater() {
		return quater;
	}

	public void setQuater(int quater) {
		this.quater = quater;
	}

	public long getLeavebalanceId() {
		return leaveBalanceId;
	}
	
	public void setLeavebalanceId(long leavebalanceId) {
		this.leaveBalanceId = leavebalanceId;
	}

	public long getLeaveBalanceId() {
		return leaveBalanceId;
	}

	public void setLeaveBalanceId(long leaveBalanceId) {
		this.leaveBalanceId = leaveBalanceId;
	}

	public long getTotalLeave() {
		return totalLeave;
	}

	public void setTotalLeave(long totalLeave) {
		this.totalLeave = totalLeave;
	}


	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}
	
	

}
