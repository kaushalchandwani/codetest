/**
 * @author Kaushal Chandwani
 *
 */
package com.register.utility;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtility 
{

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell cell;
    private static XSSFRow row;

    //This method is to set the File path and to open the Excel file
    //Pass Excel Path and SheetName as Arguments to this method
    public static void setExcelFile(String Path,String SheetName) throws Exception 
    {
    	try
		{
            FileInputStream excelFile = new FileInputStream(Path);
            ExcelWBook = new XSSFWorkbook(excelFile);
           ExcelWSheet = ExcelWBook.getSheet(SheetName);
		}
    	catch (Exception e)
    	{
    		 
			throw (e);

		}
    }


	public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow)    throws Exception

	{   

	   String[][] tabArray = null;

	   try{

		   FileInputStream ExcelFile = new FileInputStream(FilePath);

		   // Access the required test data sheet

		   ExcelWBook = new XSSFWorkbook(ExcelFile);

		   ExcelWSheet = ExcelWBook.getSheet(SheetName);

		   int startCol = 1;

		   int ci=0,cj=0;

		   int totalRows = 1;

		   int totalCols = 3;//from 2 to 3

		   tabArray=new String[totalRows][totalCols];

			   for (int j=startCol;j<=totalCols;j++, cj++)

			   {

				   tabArray[ci][cj]=getCellData(iTestCaseRow,j);

				   System.out.println(tabArray[ci][cj]);

			   }

		}

		catch (FileNotFoundException e)

		{

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e)

		{

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return(tabArray);

	}
	
	public static Object[][] getTableDataArray(String FilePath, String SheetName, int iTotalRows)    throws Exception

	{   

	   String[][] tabDataArray = null;

	   try{

		   FileInputStream ExcelFile = new FileInputStream(FilePath);

		   // Access the required test data sheet

		   ExcelWBook = new XSSFWorkbook(ExcelFile);

		   ExcelWSheet = ExcelWBook.getSheet(SheetName);

		   int startCol = 1;
		   int startRow = 1;

		   int ci=0,cj=0;

		   int totalRows = iTotalRows-1;

		   int totalCols = 3;//from 2 to 3

		   tabDataArray=new String[totalRows][totalCols];
		   
		   for(int i = startRow;i<= totalRows; i++,ci++)
		   {
			   cj=0;
			   for (int j=startCol;j<=totalCols;j++, cj++)

			   {

				   tabDataArray[ci][cj]=getCellData(i,j);

				   //System.out.println(tabDataArray[ci][cj]);

			   }
		   }
		}

		catch (FileNotFoundException e)

		{

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e)

		{

			System.out.println("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return(tabDataArray);

	}
    //This method is to read the test data from the Excel cell
    //In this we are passing parameters/arguments as Row Num and Col Num
    public static String getCellData(int RowNum, int ColNum) throws Exception
    {
    		//ExcelWSheet = ExcelWBook.getSheet(SheetName);
    		try
    			{
        		  cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                  String CellData = cell.getStringCellValue();
                  return CellData; 
    			}
    		catch (Exception e)
        	  	{
        		  return "";
        	  	}
    }
    
    //This method is to get the row count used of the excel sheet
	public static int getColumnCount(String SheetName)
	{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getRow(0).getLastCellNum();
			return number;
	}
	
	  //This method is to get the row count used of the excel sheet
	public static int getRowCount()
	{
			//ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number=ExcelWSheet.getLastRowNum()+1;
			return number;
	}
	
	//This method is to get the Row number of the test case
	//This methods takes three arguments(Test Case name , Column Number & Sheet name)
	public static int getRowContains(String sTestCaseName, int colNum) throws Exception
	{
			int i;	
			try
			{
				//ExcelWSheet = ExcelWBook.getSheet(SheetName);
				int rowCount = ExcelUtility.getRowCount();
				for (i=0 ; i<rowCount; i++)
				{
					if  (getCellData(i,colNum).equalsIgnoreCase(sTestCaseName))
					{
						break;
					}
				}
				return i;
			}
			catch (Exception e)
			{
				 
				throw(e);
 
			}
			
	}
	
	public static String getTestCaseName(String sTestCase)throws Exception{
		 
		String value = sTestCase;

		try{

			int posi = value.indexOf("@");

			value = value.substring(0, posi);

			posi = value.lastIndexOf(".");	

			value = value.substring(posi + 1);

			return value;

				}catch (Exception e){

			throw (e);

					}

		}
	
	//This method is use to write value in the excel sheet
	//This method accepts four arguments (Result, Row Number, Column Number & Sheet Name)
	public static void setCellData(String Result,  int rowNumber, int columnNumber, String sheetPath,String sheetName) throws Exception    
	{
		try
		{		
			    //setExcelFile(SheetPath);
				ExcelWSheet = ExcelWBook.getSheet(sheetName);
				row  = ExcelWSheet.getRow(rowNumber);
				if(row == null) {
					row = ExcelWSheet.createRow(rowNumber);
				}
				cell = row.getCell(columnNumber, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (cell == null) 
				{
					cell = row.createCell(columnNumber);
					cell.setCellValue(Result);
				} 
				else 
				{
					cell.setCellValue(Result);
				}
				// Constant variables Test Data path and Test Data file name
				FileOutputStream fileOut = new FileOutputStream(sheetPath);
				ExcelWBook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				ExcelWBook = new XSSFWorkbook(new FileInputStream(sheetPath));
		}
		catch(Exception e)
		{
			e.printStackTrace();
				boolean bResult = false;
		}
	}
	
	/*
	public static void closeFile() 
	{
		try 
		{
			if(ExcelWBook != null) 
			{
				ExcelWBook.close();
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
