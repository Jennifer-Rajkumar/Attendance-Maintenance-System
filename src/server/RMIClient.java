package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import addexcel.Add;
import addexcel.AddExcelGeneration;
import editexcel.EditExcelGeneration;
import mailwithatt.SendMailToHM;
import mailwithoutatt.EmailV1;
import mailwithoutatt.SendMailToAbsentees;
import pdfgen.PDFGeneration;

public class RMIClient {
	public static void main(String[] args)throws Exception {
		
		EAttendance obj = (EAttendance)Naming.lookup("rmi://localhost:2000/service");
		Scanner sc = new Scanner(System.in);
		
		AddExcelGeneration excel1=(AddExcelGeneration) obj.getObject();
		EditExcelGeneration excel2=(EditExcelGeneration) obj.getObject();
		PDFGeneration pdf = (PDFGeneration) obj.getObject();
		SendMailToAbsentees mail1 = (SendMailToAbsentees) obj.getObject();
		SendMailToHM mail2 = (SendMailToHM) obj.getObject();
		
		
		while(true) {
			
			System.out.println("1. Add new student\n2. Take attendance\n3. Download Report\n4. Send Mail to Absentees\n5. Send report to HM\n6. End process");			
			int choice = Integer.parseInt(sc.nextLine());
			
			switch(choice) {
			case 1:
				Student student = new Student();
				System.out.print("Enter the registration number: ");
				student.setRegno(sc.nextLine());
				System.out.print("Enter the name: ");
		    	student.setName(sc.nextLine());
		    	System.out.print("Enter the mail id: ");
		    	student.setMail(sc.nextLine());
		    	
				excel1.WriteFile1(student);
				break;
			case 2:
				excel2.Write();
				break;
			case 3:
				ArrayList<ArrayList<String>> res1 = excel1.ReadFile1();
				ArrayList<ArrayList<String>> absent1 = new ArrayList<ArrayList<String>>();
				
				for(int i=0;i<res1.size();i++) {
					if(res1.get(i).get(3).equals("A")) {
						absent1.add(new ArrayList<String>());
						absent1.get(absent1.size()-1).add(res1.get(i).get(0));
						absent1.get(absent1.size()-1).add(res1.get(i).get(1));
						absent1.get(absent1.size()-1).add(res1.get(i).get(2));
					}
				}
				
				pdf.generateReport(absent1);
				break;
			case 4:
				ArrayList<ArrayList<String>> res2 = excel1.ReadFile1();
				ArrayList<ArrayList<String>> absent2 = new ArrayList<ArrayList<String>>();
				
				for(int i=0;i<res2.size();i++) {
					if(res2.get(i).get(3).equals("A")) {
						absent2.add(new ArrayList<String>());
						absent2.get(absent2.size()-1).add(res2.get(i).get(0));
						absent2.get(absent2.size()-1).add(res2.get(i).get(1));
						absent2.get(absent2.size()-1).add(res2.get(i).get(2));
					}
				}
				
				if(absent2.size()==0) break; 
				
				ArrayList<String> toAddress1 = new ArrayList<String>();
				
				for(int i=0;i<absent2.size();i++) {
					toAddress1.add(absent2.get(i).get(2));
				}
				System.out.println(toAddress1);
				mail1.Notification1(toAddress1);
				break;
			case 5:
				String toAddress2 = "tomailid";
				mail2.Notification2(toAddress2);
				break;
			default: System.exit(0);
			}
		}
		
	}
}
