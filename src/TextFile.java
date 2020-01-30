import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 * This file represents the text file that will hold lines of text 
 * for N+1 many times. 
 * 
 * Writing and creating: https://www.youtube.com/watch?v=fgjIk7qQong
 * 
 * Reading: https://www.youtube.com/watch?v=7ICZGNFD28g
 * 
 * @author Marcus
 *
 */
public class TextFile {
	/**
	 * This constructor loads a .txt file that was already created to be read and edited.  
	 *  
	 * @param inFileName file name for the .txt file path.
	 */
	public TextFile(String inFileName) {
		fileName = inFileName;

		linesOfText = new ArrayList<String>();
		linesOfText.add("");

		readFromFile();
		
		linesOfText.add("");

	}
	/**
	 * This constructor sets up a new file 
	 */
	public TextFile() {
		linesOfText = new ArrayList<String>();
		linesOfText.add("");
		linesOfText.add("");
	}
	
	
	/**
	 * This method loads a .txt file onto the program and adds each line of it to the
	 * String array, linesOfText. 
	 * 
	 * https://www.youtube.com/watch?v=7ICZGNFD28g
	 */
	public void readFromFile() {
		
		BufferedReader br = null;
		try {
			
			br = new BufferedReader(new FileReader(fileName));
			String line;
			

			//if each line has something in it, add it to linesOfText. 
			while ((line = br.readLine()) != null) {
				linesOfText.add(line);
			}
			
		}
	
		catch (IOException e){
			e.printStackTrace();
		}
		
		finally {
			
			try {
				br.close();
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public ArrayList<String> getLinesOfText(){
		return linesOfText;
	}
	
	
	public int getNumOfLines() {
		return linesOfText.size();
	}
	
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String inFileName) {
		fileName= inFileName;
	}
	
	
	private String fileName;
	/**
	 * linesOfText will always contain the actual words the user will when the EditorStep2 prints 
	 * them.  
	 * 
	 * I decided to represent linesOfText as a String AL because this method is set to change
	 * all of the time. From getting, to adding, and setting different lines (or elements in the
	 * array) to what the user wants, an AL accomplishes this in the most efficient way possible. 
	 */
	private ArrayList<String> linesOfText;
	
}
