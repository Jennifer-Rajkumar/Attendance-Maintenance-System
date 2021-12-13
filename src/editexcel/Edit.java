package editexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Edit implements EditExcelGeneration,Serializable {
	
	public Edit() throws RemoteException {
		
	}
	
	@Override
	public ArrayList<ArrayList<String>> Read() {  
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        try {  
            File file = new File("attendance.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wbi = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wbi.getSheetAt(0);     //creating a Sheet object to retrieve object  
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
            while (itr.hasNext()) {  
                Row row = itr.next();
                res.add(new ArrayList<String>());
                Iterator<Cell> cell = row.cellIterator();   //iterating over each column  
                while (cell.hasNext()) {  
                    Cell pcell = cell.next();
                    res.get(res.size()-1).add(pcell.getStringCellValue());
                } 
            }  
            fis.close();
        }  
        catch(Exception e) {  
            System.out.println(e);  
        }  
        return res;
    }
	
	@Override
	public void Write() {
		try {
			
            Scanner sc = new Scanner(System.in);
			
            Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("Today");
			
			ArrayList<ArrayList<String>> res = Read();
			
			XSSFRow rowhead = (XSSFRow) sheet.createRow((short)0);   
			rowhead.createCell(0).setCellValue("Registration Number");  
			rowhead.createCell(1).setCellValue("Student Name");  
			rowhead.createCell(2).setCellValue("Mail");  
			rowhead.createCell(3).setCellValue("Attendance");    
			
			for(int i=1;i<res.size();i++) {
				System.out.print(res.get(i).get(0)+" "+ res.get(i).get(1)+" P/A: ");
				String att = sc.nextLine();
			    XSSFRow row = (XSSFRow) sheet.createRow((short)i);  
                row.createCell(0).setCellValue(res.get(i).get(0));  
			    row.createCell(1).setCellValue(res.get(i).get(1));  
			    row.createCell(2).setCellValue(res.get(i).get(2));  
			    row.createCell(3).setCellValue(att); 
			}
		    
		    //File file = new File("attendance.xlsx");
			FileOutputStream fos = new FileOutputStream("attendance.xlsx");
			wb.write(fos);
			fos.close();
			
			System.out.println("Excel file has been generated successfully.");  
		}   
		catch (Exception e) {  
			e.printStackTrace();  
		}  
	}
}
