package com.EMS.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leave")
public class LeaveModel {

	
	@Id
	@Column(name = "leaveId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long leaveId;
	
	@ManyToOne
	private UserModel user;
	
	private Double CL;
	private Double SL;
	private Double compensatoryOff;
	private Date leaveFrom;
	private Date leaveTo;
	
	
	
	public long getLeaveId() {
		return leaveId;
	}
	
	public void setLeaveId(long leaveId) {
		this.leaveId = leaveId;
	}
	
	public UserModel getUser() {
		return user;
	}
	
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public Double getCL() {
		return CL;
	}
	
	public void setCL(Double cL) {
		this.CL = cL;
	}
	
	public Double getSL() {
		return SL;
	}
	
	public void setSL(Double sL) {
		this.SL = sL;
	}
	
	public Double getCompensatoryOff() {
		return compensatoryOff;
	}
	
	public void setCompensatoryOff(Double compensatoryOff) {
		this.compensatoryOff = compensatoryOff;
	}
	
	public Date getLeaveFrom() {
		return leaveFrom;
	}
	
	public void setLeaveFrom(Date leaveFrom) {
		this.leaveFrom = leaveFrom;
	}
	
	public Date getLeaveTo() {
		return leaveTo;
	}
	
	public void setLeaveTo(Date leaveTo) {
		this.leaveTo = leaveTo;
	}
	
	
	
	
}
