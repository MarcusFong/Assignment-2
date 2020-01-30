import java.util.ArrayList;
import java.util.LinkedList;

	/**
 * ** THIS CLASS IS ONLY FOR STEP TWO AND THREE**
 * 
 * This class is connected to and uses TheDictionary class to help with conversions from
 * and to .cmp and .txt files. This classes main purpose is to help with only converting/
 * compressing lines of text. I decided to separate this class from TheDictionary class
 * because it organizes the methods in groups where the methods that convert text are 
 * separate from the methods that manipulate the dictionary (like adding and searching
 * for elements in the dictionary.)  
 * 
 * @author Marcus
 *
 */

 
public class Compressor {
	/**
	 * Creates a Compressor object that instantiates a dictionary for it. For every
	 * TextFileStep2 that exists, a Compressor (which contains a dictionary) is instantiated
	 * along with it.
	 */
	public Compressor(TheDictionary inDictionary) {
		theDictionary = inDictionary;
	}
	
	
	/**
	 * This method takes in a String and compresses it into a smaller String
	 * by replacing each word with its corresponding value saved from the 
	 * dictionary created from before. 
	 * 
	 * @param original this paramenter holds the String line of text to be 
	 * converted into a compressed form of just numbers.
	 * 
	 * @return the compressed String of numbers corresponding to its respective
	 * words. 
	 */
	public String compressLine(String original) {
		/*
		I define a "word" as a string of characters ending with a space. To count the last word in 
		the line, I add a space so the program won't skip over it.
		*/ 
		original = original.trim() + " ";
		
		String convertedLine = "";
		String word = "";
		int startOfWord = 0;
		char currentChar;
		
		for (int i = 0; i < original.length(); i++) {
			currentChar = original.charAt(i);
			
			if (currentChar == ' ') {
				word = original.substring(startOfWord, i);
				convertedLine += theDictionary.getWordNumValue(word) + " ";
				startOfWord = i+1;
			}
		}
		
		return convertedLine;		
}
	
	/**
	 * Assuming the dictionary has already been created, this program loops through each line 
	 * in the linesOfText AL and converts each number to its word equivalent. For this case,
	 * linesOfText contains only numerical values as it represents the .cmp file. 
	 * 
	 * This method is called when the user loads a .cmp file. A .cmp file contains only numbers that represent
	 * its word equivalent from the dictionary, so this method helps with converting each number back to its 
	 * respective word. We convert the dictionary-afied text into actual words to hide the fact that the file
	 * that was opened is compressed from the user. 
	 * 
	 * This method doesn't return a new ArrayList because the parameter, linesOfText, is passed by reference,
	 * so when the AL is edited, there is no need to return a new AL. 
	 * 
	 * 
	 * @param linesOfText represents the linesOfText AL in TextFileStep2. linesOfText contains each line of 
	 * text that will be soon converted from its current numerical form into its word form. 
	 */
	public void convertCmpVersionToText(ArrayList<String> linesOfText) {
		String originalLine; //contains just the numerical values of each word
		String convertedLine; //will be used to convert the numerical values to its respective word 
		int numVal = 0;
		int startOfWord = 0;
		
		//the loop starts at one because the first line of lineOfText is always empty 
		//(to represent the header). 
		for (int i = 1; i < linesOfText.size(); i++) {
			originalLine = linesOfText.get(i);
			
			/*
			I define a "word" as a string of characters ending with a space. To count the last word in 
			the line, I add a space so the program won't skip over it. Although the String of characters
			in originalLine are in their numerical form, my definition of a word still prevails.
			*/ 
			originalLine = originalLine.trim() + " ";
			convertedLine = "";
			
			/*
			this loops through each character of the line, stopping at the end of each word (albeit
			they're in their numerical form)
			*/
			for (int j = 0; j < originalLine.length(); j++) {
				
				if (originalLine.charAt(j) == ' ') {//if found the end of a word.
					numVal = Integer.parseInt(originalLine.substring(startOfWord, j));
					convertedLine += theDictionary.convertNumValToWord(numVal) + " ";
					
					startOfWord = j+1;
				}
			}
			linesOfText.set(i, convertedLine); //changes the original line into its converted form
			
			//resets variables for the next line in the text
			convertedLine = "";
			startOfWord = 0;
		}
	}
	
	
	public TheDictionary getDictionary() {
		return theDictionary;
	}
	
	
	
	/**
	 * I decided to make the dictionary a LL because the size is mutable and for search efficiency –
	 * namely when checking if a Word is in the dictionary or obtaining the numerical value of a Word
	 * in the dictionary. Choosing a data different structure to represent the dictionary (either an AL 
	 * or an array) would take up more space and wouldn't be any more efficient when searching through 
	 * the data structure, element by element.  
	 */
	private TheDictionary theDictionary;
	
}	
