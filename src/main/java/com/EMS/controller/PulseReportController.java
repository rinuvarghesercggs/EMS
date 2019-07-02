package com.EMS.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.model.ExportApprovalReportModel;
import com.EMS.model.ExportProjectHourReportModel;
import com.EMS.model.TaskTrackApproval;
import com.EMS.repository.TimeTrackApprovalRepository;
import com.EMS.service.NewHireService;
import com.EMS.service.ProjectExportService;
import com.EMS.service.PulseReportService;
import com.EMS.service.ReportService;
import com.EMS.service.SummaryService;
import com.EMS.service.TermService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class PulseReportController {

	@Autowired
	NewHireService newHireservice;

	@Autowired
	TermService termservice;

	@Autowired
	PulseReportService pulsereportservice;
	
	
	@Autowired
	SummaryService summaryservice;

	@Autowired
	ProjectExportService projectExportService;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	TimeTrackApprovalRepository timeTrackApprovalRepository;
	
	private static String[] pReportHeading = { "Consultant", "Hire Date", "Emp. Type", "Client", "Project Name", "PM",
			"Revenue/ Location", "CPP Level", "Start Date", "End Date", "Billing Type","Daily Bill Rate-INR","Loaded Daily Pay Rate-INR","Daily GM $","Daily GM %","Primary Skill Set","Hourly Bill Rate","Hourly Bill Rate in US$"};

	private static String[] newHireheading = { "Hire Date", "First Name", "Last Name", "Employee Category", "CPP Level",
			"Primary Skills", "Recruiter", "Referred By", "Emp Status" };

	private static String[] termheading = { "Termination Date", "Consultant", "Employee Type", "CPP Level",
			"Start Date", "Term Type", "Termination Reason", "No. Of Days", "Yrs of Service" };

	private static String[] summaryHeading = { "Billing", "Cost", "Gross Margin", "GM%", "Population" };
	
	static final String exchangeRateName = "Exchange Rate";
	static final int exchangeRate = 70;

	@PostMapping("/report")
	public void pulseReport(@RequestBody JsonNode requestData, HttpServletResponse response) throws Exception {
		String startdate = requestData.get("startDate").asText();
		String enddate = requestData.get("endDate").asText();
		String reportName = requestData.get("reportName").asText();

		if(reportName.equalsIgnoreCase("pulseReport")) {//Added By Jinu On 22/05/19 for adding new reports under Other Reports.
		Workbook userbook = new XSSFWorkbook();
		OutputStream output = new FileOutputStream("PulseReport.xls");
		Sheet summary = userbook.createSheet("Summary");
		Sheet pulsedata = userbook.createSheet("PulseReport");
		Sheet term = userbook.createSheet("Term");
		Sheet staff = userbook.createSheet("NonBillableAdminStaff");
		Sheet newhire = userbook.createSheet("NewHire");
		
		
		termservice.generateReport(userbook, termheading, term, startdate, enddate);
		newHireservice.generatenewhireReport(userbook, newHireheading, newhire, startdate, enddate);
		int endrow=pulsereportservice.generateReport(userbook, pReportHeading, pulsedata,startdate, enddate);
		summaryservice.generateReport(userbook,summaryHeading,summary,startdate,enddate,exchangeRateName,exchangeRate,endrow);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "filename=\"" + "PulseReport.xlsx" + "\"");
		//userbook.write(output);
		userbook.write(response.getOutputStream());
		userbook.close();
		}
		else if(reportName.equalsIgnoreCase("hourReport")) {
			
			Date startDate = null, endDate = null;
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (!startdate.isEmpty()) {
				startDate = outputFormat.parse(startdate);
			}
			if (!enddate.isEmpty()) {
				endDate = outputFormat.parse(enddate);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			
			String[] monthName = {"January", "February","March", "April", "May", "June",
					"July","August", "September", "October", "November", "December"};
			String month = monthName[cal.get(Calendar.MONTH)];
			
			int monthIndex = (cal.get(Calendar.MONTH) + 1);
			int yearIndex = cal.get(Calendar.YEAR);
			String sheetName = month+" "+yearIndex;
			
			Workbook workrbook = new XSSFWorkbook();
			Sheet sheet = workrbook.createSheet(sheetName);
			
			List <ExportProjectHourReportModel>exportData = reportService.getProjectHourReportDetails(startDate,endDate,monthIndex,yearIndex);
			projectExportService.exportProjectHourReport(exportData,workrbook,sheet);
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Content-Disposition", "filename=\"" + "HourReport.xlsx" + "\"");
			workrbook.write(response.getOutputStream());
			workrbook.close();
		}
		else if(reportName.equalsIgnoreCase("approvalReport")) {
			
			Date startDate = null, endDate = null;
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (!startdate.isEmpty()) {
				startDate = outputFormat.parse(startdate);
			}
			if (!enddate.isEmpty()) {
				endDate = outputFormat.parse(enddate);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			
			String[] monthName = {"January", "February","March", "April", "May", "June",
					"July","August", "September", "October", "November", "December"};
			String month = monthName[cal.get(Calendar.MONTH)];
			
			int monthIndex = (cal.get(Calendar.MONTH) + 1);
			int yearIndex = cal.get(Calendar.YEAR);
			
			int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	     			
	        String sheetName = month+" "+yearIndex;
			
	        ArrayList<String> colNames = new ArrayList<String>();
	        
	        for(int i=1;i<=maxDay;i++) {
	        	colNames.add(yearIndex+"-"+month+"-"+(i<10? "0"+i : i));
	        }
	        
			Workbook workrbook = new XSSFWorkbook();
			Sheet sheet = workrbook.createSheet(sheetName);
			
			List <ExportApprovalReportModel>exportData = timeTrackApprovalRepository.getApprovalReportData(monthIndex,yearIndex);
			projectExportService.exportApprovalReport(exportData,workrbook,sheet,colNames);
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Content-Disposition", "filename=\"" + "ApprovalReport.xlsx" + "\"");
			workrbook.write(response.getOutputStream());
			workrbook.close();
		}
		else if(reportName.equalsIgnoreCase("nonapprovalReport")) {

			Date startDate = null, endDate = null;
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (!startdate.isEmpty()) {
				startDate = outputFormat.parse(startdate);
			}
			if (!enddate.isEmpty()) {
				endDate = outputFormat.parse(enddate);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);

			String[] monthName = {"January", "February","March", "April", "May", "June",
					"July","August", "September", "October", "November", "December"};
			String month = monthName[cal.get(Calendar.MONTH)];

			int monthIndex = (cal.get(Calendar.MONTH) + 1);
			int yearIndex = cal.get(Calendar.YEAR);

			int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			String sheetName = month+" "+yearIndex;

			ArrayList<String> colNames = new ArrayList<String>();

			for(int i=1;i<=maxDay;i++) {
				colNames.add(yearIndex+"-"+month+"-"+(i<10? "0"+i : i));
			}

			Workbook workrbook = new XSSFWorkbook();
			Sheet sheet = workrbook.createSheet(sheetName);

			List <ExportApprovalReportModel>exportData = timeTrackApprovalRepository.getNonApprovalReportData(monthIndex,yearIndex);
			projectExportService.exportApprovalReport(exportData,workrbook,sheet,colNames);

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Content-Disposition", "filename=\"" + "NonApprovalReport.xlsx" + "\"");
			workrbook.write(response.getOutputStream());
			workrbook.close();
		}
	}
}
