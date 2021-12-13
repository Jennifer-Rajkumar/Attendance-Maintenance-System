package pdfgen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Report implements PDFGeneration,Serializable {
	
	public Report() throws RemoteException {
		
	}
	public void generateReport(ArrayList<ArrayList<String>> absent)  {  
        Document doc = new Document();  
        try  {  
	        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("report.pdf"));  
	        System.out.println("PDF created.");  
	        doc.open();  
	        
	        doc.add(new Paragraph("TODAYS ABSENTEES"));
	        
	        for(int i=0;i<absent.size();i++) {
	            doc.add(new Paragraph(absent.get(i).get(0)+" "+absent.get(i).get(1))); 
	        }
	        
	        doc.close();  
	        writer.close();  
        }   
	    catch (DocumentException e)  {  
	        e.printStackTrace();  
	    }   
	    catch (FileNotFoundException e)  {  
	        e.printStackTrace();  
	    }  
    }  
}
