package addexcel;

import java.io.Serializable;
import java.util.ArrayList;

import server.Student;

public interface AddExcelGeneration extends Serializable {
	public ArrayList<ArrayList<String>> ReadFile1();
	public void WriteFile1(Student student);
}
