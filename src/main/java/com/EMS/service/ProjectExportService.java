package com.EMS.service;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.EMS.model.ExportProjectAllocationReportModel;
import com.EMS.model.ExportProjectTaskReportModel;
import com.EMS.model.HolidayModel;
import com.EMS.model.LeaveModel;

public interface ProjectExportService {
	
	public void exportProjectAllocationReport(List <ExportProjectAllocationReportModel> data,HttpServletResponse response) throws FileNotFoundException;

	public void exportProjectTaskReport(List <ExportProjectTaskReportModel> data,HttpServletResponse response) throws FileNotFoundException;


}
