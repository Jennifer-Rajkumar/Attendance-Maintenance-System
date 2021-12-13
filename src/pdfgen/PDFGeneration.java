package pdfgen;

import java.io.Serializable;
import java.util.ArrayList;

public interface PDFGeneration extends Serializable {
	public void generateReport(ArrayList<ArrayList<String>> absent);

}