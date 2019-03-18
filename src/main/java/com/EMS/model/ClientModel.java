package com.EMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class ClientModel {
	
	@Id
	@Column(name="clientId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long clientId;
	
	private String clientName;
	private String location,country,timeZone,consultantName,consultantLocation;
	private int clientType;
	
	public ClientModel() {
		
	}	
	
	public ClientModel(long clientId, String clientName, String location, String country, String timeZone,
			String consultantName, String consultantLocation, int clientType) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.location = location;
		this.country = country;
		this.timeZone = timeZone;
		this.consultantName = consultantName;
		this.consultantLocation = consultantLocation;
		this.clientType = clientType;
	}
	public long getClientId() {
		return clientId;
	}
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getConsultantName() {
		return consultantName;
	}
	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}
	public String getConsultantLocation() {
		return consultantLocation;
	}
	public void setConsultantLocation(String consultantLocation) {
		this.consultantLocation = consultantLocation;
	}
	public int getClientType() {
		return clientType;
	}
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	
	
	
}
