package com.EMS.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EMS.model.AllocationModel;
import com.EMS.model.ExportProjectAllocationReportModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
@Service
public class ProjectAllocationImportServiceImpl implements projectAllocationImportService {

	@Autowired
	ProjectAllocationService projectAllocationService;
	
	@Override
	public void importReport(String filepath) {
		
		try {

            FileInputStream excelFile = new FileInputStream(new File(filepath));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            long id=0;
            Double perc;
			String billable;//,startDate,endDate;
            AllocationModel allocation= new AllocationModel();
            Date startDate,endDate;
            Map startDateData = new HashMap();
            Map endDateData = new HashMap();
            Map billableData = new HashMap();
            Map percentageData = new HashMap();
            ArrayList ids= new ArrayList();
            
            while (iterator.hasNext()) {

            	Row currentRow = iterator.next();
            	Iterator<Cell> cellIterator = currentRow.iterator();

            	while (cellIterator.hasNext()) {

            		Cell currentCell = cellIterator.next();
            		if(currentCell.getRowIndex()<3)
            			continue;

            		if (currentCell.getCellTypeEnum() == CellType.STRING) {
            			// System.out.print(currentCell.getStringCellValue() + "--String");
            			if(currentCell.getColumnIndex()==7) {
            				try {
            					startDate=new SimpleDateFormat("yyyy/MM/dd").parse(currentCell.getStringCellValue());
            					startDateData.put(id, startDate);
            				} catch (ParseException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}

            			}
            			if(currentCell.getColumnIndex()==8) {
            				try {
            					endDate=new SimpleDateFormat("yyyy/MM/dd").parse(currentCell.getStringCellValue());
            					endDateData.put(id, endDate);
            				} catch (ParseException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
            			}
            			if(currentCell.getColumnIndex()==9) {
            				billable=currentCell.getStringCellValue();
            				if(billable.equalsIgnoreCase("Yes")) {
            					billableData.put(id, true);
            				}
            				else
            				{
            					billableData.put(id, false);
            				}
            			}
            		} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
            			
            			
            			if(currentCell.getColumnIndex()==0) {
            				id=(long) currentCell.getNumericCellValue();
            			
            				allocation= projectAllocationService.findById(id) ;
            				ids.add(id);
            			}

            			if(currentCell.getColumnIndex()==6)
            			{
            				perc= currentCell.getNumericCellValue();
            				percentageData.put(id, perc) ;
            				
            			}

            			//System.out.print(currentCell.getNumericCellValue() + "--Numeric");



            		}

            	}
            }
            System.out.println("ids=="+ids.toString());
            System.out.println("percentageData=="+percentageData);
            System.out.println("billableData=="+billableData);
            System.out.println("startDateData=="+startDateData);
            System.out.println("endDateData=="+endDateData);
           
            //Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1); 
           /* while (iterator.hasNext()) {

            	Row currentRow = iterator.next();
            	Iterator<Cell> cellIterator = currentRow.iterator();

            	while (cellIterator.hasNext()) {

            		Cell currentCell = cellIterator.next();
            		if(currentCell.getRowIndex()<3)
            			continue;

            		if (currentCell.getCellTypeEnum() == CellType.STRING) {
            			// System.out.print(currentCell.getStringCellValue() + "--String");
            			if(currentCell.getColumnIndex()==7) {
            				try {
            					startDate=new SimpleDateFormat("yyyy/MM/dd").parse(currentCell.getStringCellValue());
            					System.out.println("startDate=="+startDate);
            					allocation.setStartDate(startDate);
            				} catch (ParseException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}

            			}
            			if(currentCell.getColumnIndex()==8) {
            				try {
            					endDate=new SimpleDateFormat("yyyy/MM/dd").parse(currentCell.getStringCellValue());
            					allocation.setEndDate(endDate);
            					System.out.println("endDate=="+endDate);
            				} catch (ParseException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
            			}
            			if(currentCell.getColumnIndex()==9) {
            				billable=currentCell.getStringCellValue();
            				if(billable.equalsIgnoreCase("Yes")) {
            					allocation.setIsBillable(true);
            				}
            				else
            				{
            					allocation.setIsBillable(false);
            				}
            			}
            		} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
            			
            			
            			if(currentCell.getColumnIndex()==0) {
            				id=(long) currentCell.getNumericCellValue();
            				System.out.print("id=="+id);
            				allocation= projectAllocationService.findById(id) ;
            			}

            			if(currentCell.getColumnIndex()==6)
            			{
            				perc= currentCell.getNumericCellValue();
            				allocation.setAllocatedPerce(perc);  
            				
            			}

            			//System.out.print(currentCell.getNumericCellValue() + "--Numeric");



            		}

            	}
            	System.out.println();

            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
