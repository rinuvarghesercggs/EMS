package com.EMS.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "holiday")
public class HolidayModel {

	@Id
	@Column(name = "holidayId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long holidayId;
	
	private Date date;
	private String day;
	private String holidayName;
	private String holidayType;
	
	
	public long getHolidayId() {
		return holidayId;
	}
	
	public void setHolidayId(long holidayId) {
		this.holidayId = holidayId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getHolidayName() {
		return holidayName;
	}
	
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	public String getHolidayType() {
		return holidayType;
	}
	
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	
	
}
