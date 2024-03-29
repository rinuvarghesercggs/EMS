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
	@Column(name="contractTypeId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contractTypeId;
	private String contractTypeName;
	
	
	public long getContractTypeId() {
		return contractTypeId;
	}
	public void setContractTypeId(long contractTypeId) {
		this.contractTypeId = contractTypeId;
	}
	public String getContractTypeName() {
		return contractTypeName;
	}

	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}
	public ContractModel(long contractTypeId, String contractTypeName) {
		super();
		this.contractTypeId = contractTypeId;
		this.contractTypeName = contractTypeName;
	}
	public ContractModel() {
		
	}
}
