
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * This class represents the "Editor" that will edit TextFile object, which will
 * later be converted in .txt files in the future. Some of examples of this class's 
 * functionality include its ability to create TextFile objects, edit its contents,
 * print its contents, and convert its contents into a .txt file, which will be saved in
 * the directory of this program.
 * 
 * 
 * STEP 1 and STEP 2 FILE FILE COMPRESSION RECORDS/REFLECTION:
 * Below are the data and reflections I collected after running and comparing the efficiency
 * of my compression algorithm compared to the regular editor. In the directory, there is a folder
 * that contains the text files I used to compare eachother. Each text file's .cmp or .txt equivalent
 * contains the same "words".  
 * 
 * (Each number is in bytes. The first number represents the .cmp size and the second .txt size)
 * 
 * TextFiles:
 * Darth Plagueis: 984, 762
 * Marcus Intro: 193, 133
 * Hi My Name Is: 183, 194
 * Name Trial: 98, 102
 * Yoda Trial: 125, 159
 * 
 * From this data, it is pretty clear that this compressor is not 100% effective and efficient; 
 * the "Darth Plagueis" and "Marcus Intro" trials ended up having "compressed" files that are 
 * bigger than their .txt counterparts! This is likely due to because both of these have an increased
 * amount of unique words – therefore the use of dictionary isn't as efficient. In addition, in the 
 * Darth Plagueis trial, I included punctuation, where a word like "hello" and "hello!" are considered 
 * different for the compiler creating more space in the .cmp file.
 * 
 * However, fortunately for the majority of the trials, the compressor was able to shrink the size of the 
 * file (albeit by a marginal amount.) This is likely because these files used an increased amount of 
 * repeated words, allowing the dictionary to be of greater efficiency. Indeed, their small size
 * restricts the dictionary from being more useful as the less amount of text there is, the more room for
 * unique words to be used more often than common, ZIPF words. 

 * 
 * 
 * @author Marcus
 *
 */

public class Editor {
	
	public Editor() {//if the user wants to create a new file.
		
		myScanner = new UserScanner();
		
		System.out.println("Hello! Welcome to my text editor/reader! Please input g and then the file name"
						+ "\nto get started. Make sure to add .txt at the end of the name! E.G: g fileName.txt"
						+ "\nIf you would like to create a new file, input only 'g'.");
		
		
		String fileName = myScanner.getScannerInput(0);
		createTextFile(fileName);
		
		checkUserDecision("");

		
		
	}
	
	
	
	public void checkUserDecision(String userDecision) {
		
		while (!userDecision.equals("q")) {
			promptUser();
			
			//1 is to check if the input is a valid command (either g, p, r, s or q) 
			userDecision = myScanner.getScannerInput(1);			

			switch (userDecision) {
			
			case "g":
				System.out.println("Please input g then the name of the file you would like to access or nothing if you would prefer to create"
								+ "\na new one. Please remember to add the .txt extension! E.G: g fileName.txt ");
				
				//0 is to check if the input is a valid input for a file name
				String newFileName = myScanner.getScannerInput(0);
				createTextFile(newFileName);
				
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
				
				//if the user wants to replace a word
				else {
					whatToReplace = inReplace.substring(0, inReplace.indexOf(' ')) + " ";
					replaceWith = inReplace.substring(inReplace.indexOf(' ') + 1) + " ";

				}

				//replace whatToReplace's dictionary entry with replaceWith.
				
				replaceWord(whatToReplace, replaceWith);
				
				
				printFile();
				break;
				
			case "s":
				System.out.println("What would you like to name your file? Please include the .txt extension.");
				//3 is to check if the input is a valid name of the file to be written. 
				myTextFile.setFileName(myScanner.getScannerInput(3));
				
				writeTxtFile();
				break;		
			}
		}
		
		System.out.println("Bye!");

		
		
	}
	
	
	
	
	/**
	 * 
	 * This method creates a TextFile object that represents a .txt file. It sets
	 * myTextFile equal to the TextFile (or potentially the .txt file) the Editor 
	 * wants to work with. Depending on what fileName is equal to (what the user inputed), 
	 * this method will either make a blank TextFile object or represent a currently 
	 * existing .txt file through TextFile. 
	 * 
	 * @param fileName holds the user input. If it is equal to g, the program
	 * automatically makes a blank text file. If it doesn't, it accesses the 
	 * .txt file and specified location.
	 */
	public void createTextFile(String fileName) {
		
		//create new file
		if (fileName.equals("g")) { 
			myTextFile = new TextFile();
		}
		
		//read/access a current file
		else { 			
			myTextFile = new TextFile(fileName.substring(2, fileName.length()));
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
	 * This method edits the a line of text at the specified line number. 
	 * 
	 * @param inLineNum
	 * @param inReplaceText
	 */
	public void reviseFile(int inLineNum, String inReplaceText) {
		
		ArrayList<String> linesOfText = myTextFile.getLinesOfText();
		
		linesOfText.set(inLineNum, inReplaceText);

		if (inLineNum == linesOfText.size()-1) { //if the line that is revised is the last line of the file. 
			linesOfText.add("");
		}
		
		else if (inLineNum == 0) { //if the line that is revised is the first line of the file.
			linesOfText.add(0, ""); 
		}
		
		printFile(); //to add clarity for the user whenever the file is revised
		
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
	 * This program writes the file into a .txt format that will be saved in this program's directory.
	 */
	public void writeTxtFile() {
		File file = new File(myTextFile.getFileName());
		
		if (!file.exists()) {
			try {
				
				file.createNewFile();	
				PrintWriter pw = new PrintWriter(file);
				/*
				 * The index starts at 1 and ends at size-1 because it ignores the "footer" and "header" lines of the 
				 * linesOfText.
				 */
				for (int i = 1; i < myTextFile.getNumOfLines()-1; i++) {
					pw.println(myTextFile.getLinesOfText().get(i));//print each line of text
					
				}
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
	
	
	private UserScanner myScanner;
	private TextFile myTextFile;
	
}
