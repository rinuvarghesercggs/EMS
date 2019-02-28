package com.EMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contract_type")
public class ContractModel {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String contrct_type;
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContrct_type() {
		return contrct_type;
	}
	public void setContrct_type(String contrct_type) {
		this.contrct_type = contrct_type;
	}
	public ContractModel(long id, String contrct_type) {
		super();
		this.id = id;
		this.contrct_type = contrct_type;
	}
	
	public ContractModel() {
		
	}
}
