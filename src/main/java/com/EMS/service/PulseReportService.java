package com.EMS.service;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PulseReportService {

	@Autowired
	ProjectAllocationService projectAllocationservice;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	ProjectService projectservice;
	
	public void generateReport(Workbook userbook, String[] pReportHeading, Sheet pulsedata) {
		
		Sheet sheetstyle=setsheetStyle(pulsedata,pReportHeading);
		Font normalFont=setnormalfont(userbook);
		CellStyle normalstyle=setnormalstyle(userbook);
		normalstyle.setFont(normalFont);
		
		Font headerfont=setheaderfont(userbook);
		CellStyle headerstyle=userbook.createCellStyle();
		
		Font hFont=setdataheadfont(userbook);
		CellStyle headstyle=setnormalstyle(userbook);
		headstyle.setFont(hFont);
		
		CellStyle datestyle=setnormaldatestyle(userbook);
		
		
		headerstyle.cloneStyleFrom(normalstyle);
		headerstyle.setFont(headerfont);
		
		Row pulserow0=pulsedata.createRow(0);
		Cell pulsecell0=createcell(pulserow0, 0, normalstyle);
		pulsecell0.setCellValue("Ti Technology (India)-  Pulse as of 03/31/2019");
		pulsecell0.setCellStyle(headerstyle);
		
		Row pulserow1=pulsedata.createRow(3);
		for(int i=0;i<pReportHeading.length;i++) {
			Cell pulseheadcell=createcell(pulserow1, i, headstyle);
			pulseheadcell.setCellValue(pReportHeading[i]);
		}
	/*	
	
	 	List<AllocationModel> allocationlist=projectAllocationservice.getList();
	 
		System.out.println("alloc :"+allocationlist.size());
		int rownum=4;
		for(AllocationModel alloc: allocationlist) {
			
			ProjectModel project=projectservice.findById(alloc.getproject().getProjectId());
			System.out.println("project :"+project.getProjectName());
			
			UserModel user=(UserModel) userservice.getUser(alloc.getuser().getUserId());
			
			
			Row datarow=pulsedata.createRow(rownum++);
			Cell datacell0=createcell(datarow, 0, normalstyle);
			datacell0.setCellValue(user.getFirstName()+" "+user.getLastName());
			Cell datacell1=createcell(datarow, 1, normalstyle);
			datacell1.setCellValue(user.getJoiningDate());
			Cell datacell2=createcell(datarow, 2, normalstyle);
			datacell2.setCellValue("S3");
			Cell datacell3=createcell(datarow, 3, normalstyle);
			datacell3.setCellValue("");
			Cell datacell4=createcell(datarow, 4, normalstyle);
			datacell4.setCellValue(project.getProjectName());
			Cell datacell5=createcell(datarow, 5, normalstyle);
			datacell5.setCellValue("");
			Cell datacell6=createcell(datarow, 6, normalstyle);
			datacell6.setCellValue("UK/ India");
			Cell datacell7=createcell(datarow, 7, normalstyle);
			datacell7.setCellValue("");
			Cell datacell8=createcell(datarow, 8, datestyle);
			datacell8.setCellValue(project.getStartDate());
			Cell datacell9=createcell(datarow, 9, datestyle);
			datacell9.setCellValue(project.getEndDate());
			Cell datacell10=createcell(datarow, 10, normalstyle);
			datacell10.setCellValue("T & M");
			Cell datacell11=createcell(datarow, 11, normalstyle);
			datacell11.setCellValue("13336");
			Cell datacell12=createcell(datarow, 12, normalstyle);
			datacell12.setCellValue("13336");
			
			*/
//			pulsedata.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1,3,6));
//			pulsedata.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 0,2));
//			pulsedata.addMergedRegion(new CellRangeAddress(rownum-1, rownum-1, 7, 13));
//			pulsedata.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,14,17));
//		}
		
		
	}
	
	public Cell createcell(Row row, int index, CellStyle style) {
		Cell cell0=row.createCell(index);
		cell0.setCellStyle(style);
		return cell0;
	}
	
	public Font setnormalfont(Workbook userbook) {
		Font normalFont = userbook.createFont();
		normalFont.setBold(false);
		normalFont.setFontHeightInPoints((short)10);
		normalFont.setColor(IndexedColors.BLACK.getIndex());
		return normalFont;
	}
	
	public Font setheaderfont(Workbook userbook) {
		Font headerFont = userbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)18);
		headerFont.setColor(IndexedColors.DARK_BLUE.getIndex());
		
		return headerFont;
		
	}



	public Font setdataheadfont(Workbook userbook) {
		Font headerFont = userbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		
		return headerFont;
	}

	private Sheet setsheetStyle(Sheet pulsedata, String[] pReportHeading) {
		
		pulsedata.addMergedRegion(new CellRangeAddress(0, 2, 0, pReportHeading.length-1));
//		pulsedata.addMergedRegion(new CellRangeAddress(3,3,0,pReportHeading.length-1));
		pulsedata.createFreezePane(3, 4);
		for(int i=0;i<pReportHeading.length;i++)
			pulsedata.setColumnWidth(i, 23*256);
		pulsedata.setDefaultRowHeightInPoints((float) 16.0);
		pulsedata.setDisplayGridlines(false);
		return pulsedata;
	}

	public CellStyle setnormalstyle(Workbook userbook) {
		CellStyle normalStyle=userbook.createCellStyle();
		normalStyle.setWrapText(true);
		normalStyle.setAlignment(HorizontalAlignment.CENTER);
		
		normalStyle.setBorderLeft(BorderStyle.THIN);  
		normalStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); 
		normalStyle.setBorderRight(BorderStyle.THIN);  
		normalStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());  
		normalStyle.setBorderTop(BorderStyle.THIN);  
		normalStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
		normalStyle.setBorderBottom(BorderStyle.THIN);  
		normalStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		return normalStyle;
	}
	public CellStyle setnormaldatestyle(Workbook userbook) {
		CellStyle normalStyle=userbook.createCellStyle();
		normalStyle.setWrapText(true);
		normalStyle.setAlignment(HorizontalAlignment.CENTER);
		
		normalStyle.setBorderLeft(BorderStyle.THIN);  
		normalStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); 
		normalStyle.setBorderRight(BorderStyle.THIN);  
		normalStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());  
		normalStyle.setBorderTop(BorderStyle.THIN);  
		normalStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
		normalStyle.setBorderBottom(BorderStyle.THIN);  
		normalStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		CreationHelper createHelper = userbook.getCreationHelper();
		normalStyle.setDataFormat(
			    createHelper.createDataFormat().getFormat("m/d/yy"));
		return normalStyle;
	}
	
	
}
