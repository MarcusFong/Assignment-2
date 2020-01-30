import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * 
 * This class represents a text file that enables users to create and swap between .txt and .cmp files 
 * depending on what they ask for. This class often works with the Compressor class to create dictionaries
 * of incoming files. This class also uses the Compressor class to write .cmp/.txt files when needed to 
 * convert from word to dictionary number value. 
 * 
 * As shown in the UML diagram, this class interacts mostly with EditorStep2 and the Compressor. This class
 * uses the Compressor class to help with conversion from and to .cmp and .txt files. 
 * 
 * Writing and creating: https://www.youtube.com/watch?v=fgjIk7qQong
 * 
 * Reading: https://www.youtube.com/watch?v=7ICZGNFD28g
 * 
 * @author Marcus
 *
 */
public class TextFileStep2 {
	
	/**
	 * This constructor converts either a a .cmp file that is already created to be read and edited. 
	 * Assuming the loaded file has words in it already, the constructor also prepares a dictionary 
	 * for the words that exist inside it already. 
	 *  
	 * @param inFileName file name for the .cmp file path.
	 */
	public TextFileStep2(String inFileName) {
		fileName = inFileName;
		theDictionary = new TheDictionary();
		compressor = new Compressor(theDictionary);

		linesOfText = new ArrayList<String>();
		linesOfText.add("");

		readFromFile(searchForFile(inFileName));
		
		
		//if the loaded file is a .cmp file, this code converts the numerical values of the text into its
		//String equivalents for the array linesOfText. 
		if (fileName.substring(fileName.length()-4, fileName.length()).equals(".cmp")) {
			compressor.convertCmpVersionToText(linesOfText);
		}
				
		linesOfText.add("");

	}
	
	/**
	 * This constructor sets up a new file 
	 */
	public TextFileStep2() {
		theDictionary = new TheDictionary();
		compressor = new Compressor(theDictionary);
		
		linesOfText = new ArrayList<String>();
		linesOfText.add("");
		linesOfText.add("");
	}
	
	
	/**
	 * This method loads a text file first by first checking if a .cmp file exists. If a .cmp
	 * file exists, it loads the .cmp file. If a .txt file exists, it loads the .txt file. If 
	 * neither exist, this method is skipped. 
	 * 
	 * In case the user wants to convert back and forth between .txt and .cmp, after he or she edits
	 * this file, while the method is reading the file, it also uses the data to create a dictionary 
	 * for the file for later use. The .cmp file is able to create a dictionary from the start by 
	 * reading the first line of the text file while the .txt file has to go through the entire
	 * text, word by word, to create a dictionary before it can be edited. 
	 * 
	 * @param fileType represents the type of file being loaded. 
	 * 0: .cmp
	 * 1: .txt
	 * anything else: it doesn't exist and the method is skipped
	 * https://www.youtube.com/watch?v=7ICZGNFD28g
	 */
	public void readFromFile(int fileType) {
			
		BufferedReader br = null;
		if (fileType != -1) {
			try {
				
				if (fileType == 0) {
					fileName += ".cmp";
				}
				
				else if (fileType == 1) {
					fileName += ".txt";
				}
	 
				br = new BufferedReader(new FileReader(fileName)); 
				String line = br.readLine(); //read the first line
				
				/*
				if the program is reading a .cmp file and is currently reading the first line of the file, 
				use this line of this file to create to create a dictionary for the text. However, the 
				first line of a .cmp file is not added to linesOfText because it represents the dictionary.
				*/
				if (fileName.substring(fileName.length()-4, fileName.length()).equals(".cmp")
						&& line != null){
					theDictionary.addToDictionary(line);
				}
				
				else  {//Add the first line of the .txt file because it is needed, unlike in a .cmp file. 
					linesOfText.add(line);
				}
				
				//if each line has something in it, add it to linesOfText. 
				while ((line = br.readLine()) != null) {					
					linesOfText.add(line);
					
					//if it is reading from a .txt file, also use this line to create a dictionary
					if (fileName.substring(fileName.length()-4, fileName.length()).equals(".txt")){
						theDictionary.addToDictionary(line);
					}
					
				}
				
			}
		
			catch (IOException e){
				e.printStackTrace();
			}
			
			finally {
				if (!(br == null)) {
					try {
						br.close();
					} 
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else {
					System.out.println("File not found... Creating new file.");
				}
				
	
			}
		}
		
	}
	
	/**
	 * 
	 * This method checks the directory if the file the user is looking for is either a .cmp or .txt file.
	 * (If there are two files with the same name, the .cmp file takes priority.) If the file doesn't exist,
	 * the program returns -1. 
	 * 
	 * @return an int denoting whether a .cmp, .txt, or no file of the specified file name has 
	 * been found. 
	 * 0: found .cmp file
	 * 1: found .txt file
	 * -1: found no file
	 */
	public int searchForFile(String fileName) {
		BufferedReader br = null;
		int whichFileExists = 0;

		
		try {//check to see if a .cmp exists
			br = new BufferedReader(new FileReader(fileName + ".cmp")); 
			
			
			whichFileExists = 0;// a .cmp exists
		}
		
		catch (IOException e){ //runs if a .cmp file has not been found
			
			try {//check to see if a .txt file exists
				
				br = new BufferedReader(new FileReader(fileName + ".txt"));
				whichFileExists = 1;//a .txt file exists
				
			}
			catch (IOException e2) {//if no .txt (and .cmp) file was found  
				whichFileExists = -1; //no file exists
				System.out.println("No file with the name: " + fileName + "  exists. Creating new file...");

			}

		}
		
		
		finally {	
			if (br != null) {
				try {
					br.close();
				} 
			
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return whichFileExists;
	}

	
	
	/**
	 * 
	 * @param line is the line of text that will be analyzed in the compressor object and will 
	 * add any new words to the dictionary that are not in it already.  
	 */
	public void addToDictionary(String line) {
		theDictionary.addToDictionary(line);
	}
	
	
	public ArrayList<String> getLinesOfText(){
		return linesOfText;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String name) {
		fileName = name;
	}
	
	
	public Compressor getCompressor() {
		return compressor;
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
	private Compressor compressor; 
	private TheDictionary theDictionary;
	
	
	
	
	
}
