package com.EMS.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.EMS.repository.ProjectReportsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ProjectReportsRepository projectReportsRepository;

	@Autowired
	ObjectMapper objectMapper;
	
	public ArrayNode getProjectReportDetails(long projectId,Date fromDate, Date toDate) {
		ArrayNode array = objectMapper.createArrayNode();
		try {

			array = objectMapper.convertValue(projectReportsRepository.GenerateProjectReports(projectId,fromDate,toDate), ArrayNode.class);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return array;
	}
	
	public ArrayNode getBenchProjectReportDetails(long uId,Date fromDate, Date toDate) {
		ArrayNode array = objectMapper.createArrayNode();
		try {
			array = objectMapper.convertValue(projectReportsRepository.GenerateBenchProjectReports(uId,fromDate,toDate), ArrayNode.class);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return array;
	}
	public ArrayNode getBenchProjectReportDetails(Date fromDate, Date toDate) {
		ArrayNode array = objectMapper.createArrayNode();
		try {
			array = objectMapper.convertValue(projectReportsRepository.GenerateBenchProjectReports(fromDate,toDate), ArrayNode.class);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return array;
	}
	public List getProjectAllocationDetails() {
		List list = new ArrayList();
		try {
			list = projectReportsRepository.GenerateProjectAllocationReportForExporting();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return list;
	}
	public List getProjectTaskReportDetails(Date fromDate,Date toDate,Long projectId) {
		List list = new ArrayList();
		try {
			list = projectReportsRepository.GenerateProjectTaskReportForExporting(fromDate,toDate,projectId);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return list;
	}
}
