package ReadExcelData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {
	public static String[] data;
	
	public static String[] getData(int dataSize, String fileName, String sheetName) throws IOException {
		
			data=new String[dataSize];
		
			//getting absolute path of excel file
			String Name=new File(fileName).getAbsolutePath();
			
			//Locate excel file
			FileInputStream file=new FileInputStream(fileName); 
			XSSFWorkbook workbook=new XSSFWorkbook(file);
			
			//Select excel sheet
			XSSFSheet sheet=workbook.getSheet(sheetName);
			
			//read data
			for(int i=0;i<data.length;i++)
			{ 
				 data[i]=String.valueOf(sheet.getRow(0).getCell(i)); 
			}
			workbook.close();
			
			return data;
			
	}

}
