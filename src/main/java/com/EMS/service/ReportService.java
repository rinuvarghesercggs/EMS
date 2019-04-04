package com.EMS.service;

import java.util.Date;
import com.fasterxml.jackson.databind.node.ArrayNode;


public interface ReportService {
	
	public ArrayNode getProjectReportDetails(long projectId,Date fromDate, Date toDate);
	public ArrayNode getBenchProjectReportDetails(long uId,Date fromDate, Date toDate);
	public ArrayNode getBenchProjectReportDetails(Date fromDate, Date toDate);
		
}

