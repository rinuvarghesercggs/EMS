package com.EMS.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.EMS.model.ExportProjectAllocationReportModel;
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
	public void exportProjectAllocationReport(List <ExportProjectAllocationReportModel> data,HttpServletResponse response) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String[] headers = { "AllocId", "ProjectId", "ProjectName","UserId","UserName","FullName","percentage","StartDate","EndDate","Billable"};

		//public static void main(String[] args) throws IOException, InvalidFormatException {
		List<ExportProjectAllocationReportModel> Listdata = new ArrayList<ExportProjectAllocationReportModel>();

		for(ExportProjectAllocationReportModel obj : data) {

			Listdata.add(new ExportProjectAllocationReportModel(obj.getAllocId(),obj.getProjectId(),obj.getProjectName(),obj.getUserId(),obj.getUserName()
					,obj.getFullName(),obj.getPercentage(),obj.getStartDate(),obj.getEndDate(), obj.getBillable()));

		}

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Project Allocation Report");
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
		titleCell.setCellValue("PROJECT ALLOCATION REPORT");
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
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Other rows and cells with contacts data
		int rowNum = 3;
		ExportProjectAllocationReportModel totalSummary = new ExportProjectAllocationReportModel();
		for (ExportProjectAllocationReportModel summary : Listdata) {
			Row row = sheet.createRow(rowNum++);

			Cell cell = row.createCell(0);
			cell.setCellValue(summary.getAllocId());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(1);
			cell.setCellValue(summary.getProjectId());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(2);
			cell.setCellValue(summary.getProjectName());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(3);
			cell.setCellValue(summary.getUserId());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(4);
			cell.setCellValue(summary.getUserName());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(5);
			cell.setCellValue(summary.getFullName());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(6);
			cell.setCellValue(summary.getPercentage());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(7);
			cell.setCellValue(summary.getStartDate());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(8);
			cell.setCellValue(summary.getEndDate());
			cell.setCellStyle(borderedCellStyle);

			cell = row.createCell(9);
			cell.setCellValue(summary.getBillable());
			cell.setCellStyle(borderedCellStyle);

		}


		// Resize all columns to fit the content size
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		//Adding filter menu in column headers
		sheet.setAutoFilter(new CellRangeAddress(2, 13, 1, 9));

		// Write the output to a file

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
		 response.setHeader("Content-Disposition", "attachment; filename=ProjectAllocationReport.xlsx");

		try {
			workbook.write(response.getOutputStream());
			workbook.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	@Override
	public void exportProjectTaskReport(List <ExportProjectTaskReportModel> data,HttpServletResponse response) throws FileNotFoundException {
		
		// TODO Auto-generated method stub
		String[] headers = { "Id", "TaskDate","ResourceName","TaskDescription", "hours","ProjectId","ProjectName"};
		
		//public static void main(String[] args) throws IOException, InvalidFormatException {
			List<ExportProjectTaskReportModel> Listdata = new ArrayList<ExportProjectTaskReportModel>();
			
			  for(ExportProjectTaskReportModel obj : data) {
				Listdata.add(new ExportProjectTaskReportModel
						(obj.getId(),obj.getProjectId(),obj.getProjectName(),obj.getResourceName(),obj.getTaskDate()
						,obj.getHours(),obj.getTaskDescription(),obj.getBillable()));
						  
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
				cell.setCellValue(summary.getId());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(1);
				cell.setCellValue(summary.getTaskDate());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(summary.getResourceName());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(3);
				cell.setCellValue(summary.getTaskDescription());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(summary.getHours());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(summary.getProjectId());
				cell.setCellStyle(borderedCellStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(summary.getProjectName());
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
}
