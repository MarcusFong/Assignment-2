import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * 
 * This class represents the "Editor" that will edit TextFileStep2 object, which will
 * later be converted into either .cmp or .txt files in the future. Some examples of this class's 
 * functionality include its ability to create TextFileStep2 objects, edit its contents,
 * print its contents, and convert its contents into a .cmp file, which will be saved in
 * the directory of this program.
 * 
 * This class's main job is to work the "main menu" of the program, namely to take in commands
 * 'g', 'p', 'r', 's', or 'q'.
 * 
 * 
 * g: To create or load a new file different than the one that is currently loaded.
 * p: To print the file's contents
 * r: To edit line at a specified line number
 * s: To create the file (.cmp or .txt)
 * q: To quit the program
 * 
 * 
 * As shown in the UML diagram, 
 * 
 * @author Marcus
 *
 */
public class EditorStep2 {
	
	/**
	 * This constructor instantiates the editor. It asks the user whether or not they would like to load 
	 * a new file or create a new one. It then calls checkUserDecision() to process what they 
	 * inputed. 
	 */
	public EditorStep2() {
		
		myScanner = new UserScannerStep2();
		
		System.out.println("Hello! Welcome to my text editor/reader! Please input g and then the file name"
				+ "\nto get started. E.G: g fileName"
				+ "\nIf you would like to create a new file, input only 'g'.");
		
		String fileName = myScanner.getScannerInput(0);
		createTextFile(fileName);
		
		
		checkUserDecision("");
		
	}
	
	
	/**
	 * This method checks the decision of the user from the main menu. Each character represents
	 * a different command for the editor. See below for what each character represents.
	 * @param userDecision represents the user's input from the consolo.
	 * 
	 * g: To create or load a new file different than the one that is currently loaded.
	 * p: To print the file's contents
	 * r: To edit line at a specified line number
	 * s: To create the file (.cmp or .txt)
	 * q: To quit the program
	 * 
	 */
	public void checkUserDecision(String userDecision) {
		
		while (!userDecision.equals("q")) {
			promptUser();
			
			//1 is to check if the input is a valid command (either g, p, r, s or q) 
			userDecision = myScanner.getScannerInput(1);			

			switch (userDecision) {
			
			case "g":
				System.out.println("Please input g then the name of the file (without the extension) to access  a file "
								+ "\nor nothing if you would prefer to create a new one. E.G: g fileName");
				
				//0 is to check if the input is a valid input for a file name
				String newFileName = myScanner.getScannerInput(0);
				
				createTextFile(newFileName);
				
				printFile();
				break;
			
			case "p":
				printFile();
				
				break;

			case "r":
				System.out.println("Please input the line number of the text you would like to replace.");
				
				//2 is to check if the input is a a valid int input.
				int lineNum = Integer.parseInt(myScanner.getScannerInput(2));
				
				System.out.println("\nPlease input the text you would like to replace.");
				
				//The user just has to input any String of text so anything will do. 
				String replaceText = myScanner.getScannerInput(-1);
				
				reviseFile(lineNum, replaceText);
				
				printFile();
				break;
				
			case "w": //replace a word
				
				System.out.println("Please input the word that you would like to replace and the word that you like"
								+ "\nto replace it with. If you would like to delete a word, input just that word. "
								+ "\nA word is defined as a connected string of characters that end with a space."
								+ "\nThis is case sensitive!"
								+ "\n\nE.G: 'this that' (to replace all instances of 'this' with 'that'."
								+ "\nE.G: 'this' to delete instances of 'this'.");
				String inReplace = myScanner.getScannerInput(5).trim();
				
				String whatToReplace;
				String replaceWith;
				
				//if the wants to delete a word 
				if (inReplace.indexOf(' ') == -1) {
					
					//an extra space is added so when whatToReplace is replaced, the redundant space that came after it is replaced as well 
					whatToReplace = inReplace + " "; 
					replaceWith = "";
				}
				else {
					whatToReplace = inReplace.substring(0, inReplace.indexOf(' '));
					replaceWith = inReplace.substring(inReplace.indexOf(' ') + 1);
					
					myCompressor.getDictionary().replaceDictEntry(whatToReplace, replaceWith);
					
					whatToReplace += " ";
					replaceWith += " ";
				}

				//replace whatToReplace's dictionary entry with replaceWith.
				
				replaceWord(whatToReplace, replaceWith);
				
				
				printFile();
				break;
				
				
			case "s":
				System.out.println("What would you like to name your file? Please exclude the extension");
				//3 is to check if the input is a valid name of the file to be written. 
				myTextFile.setFileName(myScanner.getScannerInput(3));
				
				System.out.println("Which type of file would you like to create? Please input: "
								+ "\n1) .cmp"
								+ "\n2) .txt");
				
				writeFile(Integer.parseInt(myScanner.getScannerInput(4)));
				
				break;	
				
				
			}
		}
	}
	
	
	/**
	 * 
	 * This method creates a TextFileStep2 object that represents a .cmp file. It sets
	 * myTextFile equal to the TextFile (or potentially the .cmp file) the Editor 
	 * wants to work with. Depending on what fileName is equal to (what the user inputed), 
	 * this method will either make a blank TextFile object or represent a currently 
	 * existing .cmp file through TextFile. 
	 * 
	 * @param fileName holds the user input. If it is equal to g, the program
	 * automatically makes a blank text file. If it doesn't, it accesses the 
	 * .cmp file and specified location.
	 */
	public void createTextFile(String fileName) {
		
		//create new file
		if (fileName.equals("g")) { 
			myTextFile = new TextFileStep2();
			myLinesOfText = myTextFile.getLinesOfText();
			myCompressor = myTextFile.getCompressor();
			
		}
		
		//read/access an existing file file
		else { 			
			myTextFile = new TextFileStep2(fileName.substring(2, fileName.length()));
			myLinesOfText = myTextFile.getLinesOfText();
			myCompressor = myTextFile.getCompressor();
			
		}
		
		printFile();
		
		
		
	}
	
	
	public void printFile() {
		ArrayList<String> linesOfText = myTextFile.getLinesOfText();
		int count = 0;
		System.out.println("\n\nYour text file:");//extra clarity when printing to the console
		for (String n :linesOfText) {
			System.out.println(count + ") " + n); 
			count++;
		}
	}
	
	/**
	 * This method edits the a line of text at the specified line number and also adds 
	 * any new words to the dictionary in the Compressor class. Each time a revision is
	 * made, the program checks if each word in that revision is added to the dictionary
	 * to ensure that nothing is missed. 
	 * 
	 * @param inLineNum
	 * @param inReplaceText
	 */
	public void reviseFile(int inLineNum, String inReplaceText) {
		
		ArrayList<String> linesOfText = myTextFile.getLinesOfText();
		
		linesOfText.set(inLineNum, inReplaceText);
		
		//adds words to dictionary that haven't been added yet.
		myTextFile.addToDictionary(inReplaceText);

		if (inLineNum == linesOfText.size()-1) { //if the line that is revised is the last line of the file. 
			linesOfText.add("");
		}
		
		else if (inLineNum == 0) { //if the line that is revised is the first line of the file.
			linesOfText.add(0, ""); 
		}		
	}
	
	/**
	 * 
	 * This method replaces every instance of whatToReplace in the text file with replaceWith.
	 * 
	 * In the process of replacing of each instance of whatToReplace, its replaceWith also 
	 * takes whatToReplace's dictionary index. 
	 * 
	 * @param whatToReplace the String to be replaced
	 * @param replaceWith what to replace whatToReplace with
	 */
	public void replaceWord(String whatToReplace, String replaceWith) {
		
		ArrayList<String> linesOfText = myTextFile.getLinesOfText();
		String line;
		String newLine;
		
		for (int i = 1; i < linesOfText.size(); i++) {
			//I add a space at the end to allow the compiler to count the last word.  
			line = linesOfText.get(i) + " ";
			newLine = line.replaceAll(whatToReplace, replaceWith);
			
			linesOfText.set(i, newLine.trim());
		}
	}
	
	
	/**
	 * https://www.youtube.com/watch?v=fgjIk7qQong
	 * This program writes either a .cmp or .txt file that will be saved in this program's directory.
	 * A .cmp file will be encoded with words (using the dictionary) and a .txt file will be in its 
	 * original word form.  
	 */
	public void writeFile(int typeOfFile) {
		File file = null;
		if(typeOfFile == 1) {
			file = new File(myTextFile.getFileName() + ".cmp");
		}
		else if (typeOfFile == 2) {
			file = new File(myTextFile.getFileName() + ".txt");	
		}
		
		if (!file.exists()) {
			try {
				
				file.createNewFile();	
				PrintWriter pw = new PrintWriter(file);
				
				if (typeOfFile == 1) {//if the user wants to write a .cmp file, write the file using the dictionary
					String header = "";
					
					//this creates the header (that is the dictionary) for the .cmp file
					for (Word word : myCompressor.getDictionary().getDictionaryOfWords()) {
						header += word.getWord() + " ";
					}
					pw.println(header);
					
					
					/*
					 * The index starts at 1 and ends at size-1 because it ignores the "footer" and "header" lines of the 
					 * linesOfText.
					 */
					for (int i = 1; i < myLinesOfText.size()-1; i++) {
						pw.println(myCompressor.compressLine( myLinesOfText.get(i) ));
						
					}	
				}
				
				else if (typeOfFile == 2) {//if the user wants to write a .txt file, write the file using its original words 
					for (int i = 1; i < myLinesOfText.size()-1; i++) {
						pw.println(myTextFile.getLinesOfText().get(i));
					}
				}
				
				//adds the header containing the dictionary of each word to the top of the file
				
				pw.close();
				
				System.out.println("Created file!");
			}		
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void promptUser() {
		System.out.println("\n\nPlease input:"
				+ "\ng) To get a file from the directory."
				+ "\np) To print the entire file to console from lines 0 to n+1."
				+ "\nr) To replace the text at specified line with text."
				+ "\nw) To replace all instances of a word in the text."
				+ "\ns) To set the file contents to the directory."
				+ "\nq) To quit the program.\n");
		
	}
	
	
	
	
	
	
	private TextFileStep2 myTextFile;
	private UserScannerStep2 myScanner;
	private Compressor myCompressor;
	private ArrayList<String> myLinesOfText;
	
}
