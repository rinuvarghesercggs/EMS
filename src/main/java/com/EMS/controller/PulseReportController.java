package com.EMS.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMS.service.NewHireService;
import com.EMS.service.PulseReportService;
import com.EMS.service.TermService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class PulseReportController {

	@Autowired
	NewHireService newHireservice;

	@Autowired
	TermService termservice;

	@Autowired
	PulseReportService pulsereportservice;

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

		Workbook userbook = new HSSFWorkbook();
		OutputStream output = new FileOutputStream("PulseReport.xls");
		Sheet summary = userbook.createSheet("Summary");
		Sheet pulsedata = userbook.createSheet("PulseReport");
		Sheet term = userbook.createSheet("Term");
		Sheet staff = userbook.createSheet("NonBillableAdminStaff");
		Sheet newhire = userbook.createSheet("NewHire");

		termservice.generateReport(userbook, termheading, term, startdate, enddate);
		newHireservice.generatenewhireReport(userbook, newHireheading, newhire, startdate, enddate);
		pulsereportservice.generateReport(userbook, pReportHeading, pulsedata,startdate, enddate);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "filename=\"" + "PulseReport.xlsx" + "\"");
//		userbook.write(output);
		userbook.write(response.getOutputStream());
		userbook.close();

	}
}
