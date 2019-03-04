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
	private String contractType;
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getcontractType() {
		return contractType;
	}
	public void setcontractType(String contractType) {
		this.contractType = contractType;
	}
	public ContractModel(long id, String contractType) {
		super();
		this.id = id;
		this.contractType = contractType;
	}
	
	public ContractModel() {
		
	}
}
