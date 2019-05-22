package com.EMS.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.EMS.model.ExportProjectHourReportModel;
import com.EMS.model.ExportProjectTaskReportModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class ProjectExportServiceImpl implements ProjectExportService {

	@Override
	public void exportProjectTaskReport(List <ExportProjectTaskReportModel> data,HttpServletResponse response) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		String[] headers = { "Project Name", "Task Date","Resource Name","Task Category","Task Description", "Hours","Billable"};
		
		//public static void main(String[] args) throws IOException, InvalidFormatException {
			List<ExportProjectTaskReportModel> Listdata = new ArrayList<ExportProjectTaskReportModel>();
			
			  for(ExportProjectTaskReportModel obj : data) {
				Listdata.add(new ExportProjectTaskReportModel
						(obj.getProjectName(),obj.getResourceName(),obj.getTaskDate()
						,obj.getHours(),obj.getTaskCategory(),obj.getTaskDescription(),obj.getBillable()));
						  
			  }

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Project Task Report");
			//Removing grids
			sheet.setDisplayGridlines(false);
			//Freezing columns and rows from scrooling
			sheet.createFreezePane(0,3);
			
			//Bordered Cell Style
			CellStyle borderedCellStyle = workbook.createCellStyle();
			borderedCellStyle.setBorderLeft(BorderStyle.THIN);  
			borderedCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); 
			borderedCellStyle.setBorderRight(BorderStyle.THIN);  
			borderedCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());  
			borderedCellStyle.setBorderTop(BorderStyle.THIN);  
			borderedCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
			borderedCellStyle.setBorderBottom(BorderStyle.THIN);  
			borderedCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

			//Title Cell Style
			CellStyle titleCellStyle = workbook.createCellStyle();
			//titleCellStyle.setFont((org.apache.poi.ss.usermodel.Font) headerFont);
			
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("PROJECT TASK REPORT");
			titleCell.setCellStyle(titleCellStyle);

			titleRow = sheet.createRow(1);
			titleCell = titleRow.createCell(1);
			titleCell.setCellValue("");

			// Header Cell Style
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.cloneStyleFrom(borderedCellStyle);
			headerCellStyle.setBorderTop(BorderStyle.THICK);
			//headerCellStyle.setFont((org.apache.poi.ss.usermodel.Font) headerFont);
			
			Row headerRow = sheet.createRow(2);
			int widthInChars = 50;
		    sheet.setColumnWidth(4, widthInChars);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Create Other rows and cells with contacts data
			int rowNum = 3;
			ExportProjectTaskReportModel totalSummary = new ExportProjectTaskReportModel();
			for (ExportProjectTaskReportModel summary : Listdata) {
				Row row = sheet.createRow(rowNum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(summary.getProjectName());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(summary.getTaskDate());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(summary.getResourceName());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(3);
				cell.setCellValue(summary.getTaskCategory());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(summary.getTaskDescription());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(summary.getHours());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(summary.getBillable());
				cell.setCellStyle(borderedCellStyle);

			}

			
			// Resize all columns to fit the content size
			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}

			//Adding filter menu in column headers
			sheet.setAutoFilter(new CellRangeAddress(2, 13, 1, 6));


			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader( "Content-Disposition", "filename=\"" + "ProjectTaskReport.xlsx" + "\"" );

			try {
				workbook.write(response.getOutputStream());
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void exportProjectHourReport(List <ExportProjectHourReportModel> data,Workbook workbook,Sheet sheet) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		String[] headers = { "Project Name", "First Name","Last Name","Actual Hours","Approved Hours"};
		
		//public static void main(String[] args) throws IOException, InvalidFormatException {
			List<ExportProjectHourReportModel> Listdata = new ArrayList<ExportProjectHourReportModel>();
			
			  for(ExportProjectHourReportModel obj : data) {
				Listdata.add(new ExportProjectHourReportModel
						(obj.getProjectName(),obj.getFirstName(),obj.getLastName()
						,obj.getApproved(),obj.getLogged()));
						  
			  }

			//Removing grids
			sheet.setDisplayGridlines(false);
			//Freezing columns and rows from scrooling
			sheet.createFreezePane(0,3);
			
			//Bordered Cell Style
			CellStyle borderedCellStyle = workbook.createCellStyle();
			borderedCellStyle.setBorderLeft(BorderStyle.THIN);  
			borderedCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); 
			borderedCellStyle.setBorderRight(BorderStyle.THIN);  
			borderedCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());  
			borderedCellStyle.setBorderTop(BorderStyle.THIN);  
			borderedCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
			borderedCellStyle.setBorderBottom(BorderStyle.THIN);  
			borderedCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

			//Title Cell Style
			CellStyle titleCellStyle = workbook.createCellStyle();
			//titleCellStyle.setFont((org.apache.poi.ss.usermodel.Font) headerFont);
			
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("PROJECT HOUR REPORT");
			titleCell.setCellStyle(titleCellStyle);

			titleRow = sheet.createRow(1);
			titleCell = titleRow.createCell(1);
			titleCell.setCellValue("");

			// Header Cell Style
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.cloneStyleFrom(borderedCellStyle);
			headerCellStyle.setBorderTop(BorderStyle.THICK);
			
			Row headerRow = sheet.createRow(2);
			int widthInChars = 50;
		    sheet.setColumnWidth(4, widthInChars);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Create Other rows and cells with contacts data
			int rowNum = 3;
			ExportProjectHourReportModel totalSummary = new ExportProjectHourReportModel();
			for (ExportProjectHourReportModel summary : Listdata) {
				Row row = sheet.createRow(rowNum++);
				Cell cell = row.createCell(0);
				cell.setCellValue(summary.getProjectName());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(summary.getFirstName());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(summary.getLastName());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(3);
				cell.setCellValue(summary.getLogged());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(summary.getApproved());
				cell.setCellStyle(borderedCellStyle);
				
			}
			
			// Resize all columns to fit the content size
			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}

			//Adding filter menu in column headers
			sheet.setAutoFilter(new CellRangeAddress(2, 13, 1, 4));

	}
}
