package editexcel;

import java.io.Serializable;
import java.util.ArrayList;

public interface EditExcelGeneration extends Serializable {
	public ArrayList<ArrayList<String>> Read();
	public void Write();
}
