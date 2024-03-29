package com.EMS.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
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

	public void exportAllReport(List <ExportApprovalReportModel> data,Workbook workbook,Sheet sheet, ArrayList<String> colNames, String reportName) throws FileNotFoundException;

	public void exportBenchReport(Workbook workbook, Sheet sheet, ArrayList<String> colNames, String reportName, Integer monthIndex, Integer yearIndex, String reportType, Date startDate, Date endDate) throws FileNotFoundException;

	public void exportSummaryReport(Workbook workbook, Sheet sheet, ArrayList<String> colNames, String reportName, Integer monthIndex, Integer yearIndex, String reportType, Date startDate, Date endDate) throws Exception;

    public void exportFinanceDataByProject(Workbook workbook,Sheet sheet,String reportName,Integer month,Integer year,Long projectId,String projectName) throws Exception;

    public void exportFinanceDataByUser(Workbook workbook,Sheet sheet,String reportName,Integer month,Integer year,Long userId,String userName) throws Exception;

    public void exportFinanceDataByUserAndProject(Workbook workbook,Sheet sheet,String reportName,Integer month,Integer year,Long userId,Long projectId) throws Exception;

}
