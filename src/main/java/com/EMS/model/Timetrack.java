package com.EMS.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Timetrack")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Timetrack {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="track_id")
	private long id;
	
    @Column(name="description")
	private String description;
    
    @Column(name="date")
   	private Date date;
    
    @Column(name="hours")
   	private Integer hours;
    @OneToOne
    private TaskModel task;
    @OneToOne
    private ProjectModel projectModel;
     
     
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Timetrack(long id, String description, Date date, Integer hours) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.hours = hours;
	}

	public Timetrack() {
		super();
	}
	
	

	


}
