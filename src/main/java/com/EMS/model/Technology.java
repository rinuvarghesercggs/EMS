package com.EMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="technology")
public class Technology {

	@Id
	@Column(name="technology")
	private long technologyId;
	private String technologyName;
}
