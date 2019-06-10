package com.EMS.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.EMS.model.ExportApprovalReportModel;
import com.EMS.model.ExportProjectHourReportModel;
import com.EMS.model.ExportProjectTaskReportModel;


public interface ProjectExportService {
	
	public void exportProjectTaskReport(List <ExportProjectTaskReportModel> data,HttpServletResponse response) throws FileNotFoundException;
	
	public void exportProjectHourReport(List <ExportProjectHourReportModel> data,Workbook workbook,Sheet sheet) throws FileNotFoundException;

	public void exportApprovalReport(List <ExportApprovalReportModel> data,Workbook workbook,Sheet sheet, ArrayList<String> colNames) throws FileNotFoundException;
}
