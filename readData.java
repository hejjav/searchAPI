package abc;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.poi.hssf.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.apache.poi.ss.usermodel.Sheet;


public class readData {
	
	//Class name is the name of the data sheet for the test
	@DataProvider(name="data-provider")
	public static Object[][] getExcelData(Method m) {
		String[][] arrayExcelData = null;
		try {
			
			FileInputStream file = new FileInputStream(new File("C:\\iTunesAPITest.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sh = workbook.getSheet(m.getName());

			int totalNoOfCols = 5;
			
			int totalNoOfRows = sh.getLastRowNum()-sh.getFirstRowNum();
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i < totalNoOfRows; i++) {
				 Row row = sh.getRow(i);
				for (int j=0; j < totalNoOfCols; j++) {
					arrayExcelData[i-1][j] = row.getCell(j).getStringCellValue();
					
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();		
		
		}
		return arrayExcelData;
	}
	
	
}
