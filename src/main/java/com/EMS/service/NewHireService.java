package com.EMS.service;

import java.time.LocalDate;
import java.util.Date;
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

import com.EMS.model.Technology;


@Service
public class NewHireService {

	@Autowired
	UserService userservice;

	public Sheet setSheetStyle(Sheet newhire, String[] heading) {
		newhire.addMergedRegion(new CellRangeAddress(0, 3, 0, heading.length-1));
		newhire.createFreezePane(0, 5);
		for(int i=0;i<heading.length;i++)
			newhire.setColumnWidth(i, 22*256);
		newhire.setDefaultRowHeightInPoints((float) 16.0);
		newhire.setDisplayGridlines(false);
		return newhire;
	}



	public Font setnormalfont(Workbook userbook) {
		Font normalFont = userbook.createFont();
		normalFont.setBold(false);
		normalFont.setFontHeightInPoints((short)10);
		normalFont.setColor(IndexedColors.BLACK.getIndex());
		return normalFont;
	}

	public CellStyle setnormalStyle(Workbook userbook, Font normalfont) {
		CellStyle normalStyle=userbook.createCellStyle();
		normalStyle.setFont(normalfont);
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




	public CellStyle setheaderstyle(Workbook userbook, Font dataheadfont) {
		CellStyle normalStyle=userbook.createCellStyle();
		normalStyle.setFont(dataheadfont);
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


	public Cell createcell(Row row, int index, CellStyle style) {
		Cell cell0=row.createCell(index);
		cell0.setCellStyle(style);
		return cell0;
	}


	public Font setdataheadfont(Workbook userbook) {
		Font headerFont = userbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		
		return headerFont;
	}


	public void generatenewhireReport(Workbook userbook, String[] newHireheading, Sheet newhire,String startdate1,String enddate1) {
		Sheet sheetstyle = setSheetStyle(newhire, newHireheading);

		Font normalfont = setnormalfont(userbook);
		CellStyle normalstyle = setnormalStyle(userbook, normalfont);
	
		Font dataheadfont=setdataheadfont(userbook);
		CellStyle headerstyle=userbook.createCellStyle();
		headerstyle.cloneStyleFrom(normalstyle);
		headerstyle.setFont(dataheadfont);
		
	
		CellStyle headstyle=setheaderstyle(userbook,dataheadfont);
		

		Row firstrow = newhire.createRow(0);
		Cell firstcol = createcell(firstrow, 0, normalstyle);
		firstcol.setCellValue("Ti Technology (India) \n NewHire Summary\n");
		firstcol.setCellStyle(headerstyle);

		
		Row headrow = newhire.createRow(4);
		for (int i = 0; i < newHireheading.length; i++) {
			Cell headcell = createcell(headrow, i, headstyle);
			headcell.setCellValue(newHireheading[i]);
			
		}
		LocalDate checkdate1=LocalDate.parse(startdate1);
		LocalDate checkdate2=LocalDate.parse(enddate1);
		int startmonth=checkdate1.getMonthValue();
		int endmonth=checkdate2.getMonthValue();
		int year=checkdate1.getYear();

		List<Object[]> userlist = userservice.getnewHire(startmonth,endmonth,year);
		int rownum = 5;

		for (Object[] user : userlist) {
			long userId=Long.parseLong(String.valueOf(user[8]));
			List<Technology> techList=userservice.getprimarySkills(userId);
			String skills=null;
			for(Technology obj:techList) {
				if(skills==null) {
					skills=obj.getTechnologyName();
				}else {
					skills=skills+","+obj.getTechnologyName();
				}
			}
			Row datarow = newhire.createRow(rownum++);
			Cell cell0 = createcell(datarow, 0, normalstyle);
			Date dateval = (Date) user[0];
			cell0.setCellValue(dateval);
			Cell cell1 = createcell(datarow, 1, normalstyle);
			String fname = String.valueOf(user[1]);
			cell1.setCellValue(fname);
			Cell cell2 = createcell(datarow, 2, normalstyle);
			String lname = String.valueOf(user[2]);
			cell2.setCellValue(lname);
			Cell cell3 = createcell(datarow, 3, normalstyle);
			String cat = String.valueOf(user[4]);
			cell3.setCellValue(cat);
			Cell cell4 = createcell(datarow, 4, normalstyle);
			String cpp = String.valueOf(user[3]);
			cell4.setCellValue(cpp);
			
			Cell cell5 = createcell(datarow, 5, normalstyle);
			
			cell5.setCellValue(skills);
			Cell cell6 = createcell(datarow, 6, normalstyle);
			String recruiter = String.valueOf(user[5]);
			cell6.setCellValue(recruiter);
			Cell cell7 = createcell(datarow, 7, normalstyle);
			String reference = String.valueOf(user[6]);
			cell7.setCellValue(reference);
			Cell cell8 = createcell(datarow, 8, normalstyle);
			boolean status = (boolean) user[7];
			cell8.setCellValue(status);
		}

		Row footrow=newhire.createRow(rownum++);
		Cell footcell=createcell(footrow, 0, headstyle);
		footcell.setCellValue("Record Count");
		Cell footcell1=createcell(footrow, 1, headstyle);
		footcell1.setCellFormula("COUNTA(B6:B"+(rownum-1)+")");
		
//		return newhire;
	}

	

}
