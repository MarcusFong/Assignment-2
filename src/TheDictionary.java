import java.util.LinkedList;

/**
 * ** THIS CLASS IS ONLY FOR STEP TWO AND THREE**
 * 
 * This class represents the dictionary that is created and specific to each individual file
 * (or TextFileStep2) that is loaded. 
 * 
 * The main job of this class is to manipulated the contents of the dictionary. In addition,
 * this class simplifies the process of searching and retrieving individuals elements from the
 * dictionary.
 * 
 * Methods in this class include creating the a dictionary line 
 * by line, checking if a word is in the dictionary, and converting between a word and its
 * dictionary value. 
 * 
 * @author Marcus
 *
 */
public class TheDictionary {
	
	/**
	 * Creates a new and empty dictionary.
	 */
	public TheDictionary() {
		dictionaryOfWords = new LinkedList<Word>();
		dictSize = 0;
	}
	
	
	/**
	 * 
	 * This method adds each unique word in a line of text to the dictionary (if it hasn't 
	 * been added already). I define a "word" as a String that starts from a letter and ends
	 * at a space. 
	 * 
	 * @param lineOfText reprents the String line of text that the user just 
	 * inputed.
	 */
	public void addToDictionary(String lineOfText) {
		/*
		I define a "word" as a string of characters ending with a space. To count the last word in 
		the line, I add a space so the program won't skip over it.
		*/ 
		lineOfText = lineOfText.trim() + " ";   

		
		int startOfWord = 0;
		String word = "";
		char currentChar;
		
		for (int i = 0; i < lineOfText.length(); i++) {
			currentChar = lineOfText.charAt(i);
			
			//true if found the ending of a word.
			if (currentChar == ' ') { 
				word = lineOfText.substring(startOfWord, i);
				
				if (!isInDictionary(word)) { //only adds the word to the dictionary if it wasn't in it before
					
					dictionaryOfWords.add(new Word(word, dictSize));
					dictSize++;
					
				}
				
				startOfWord = i + 1;
			}
		}

		
		
		
	}
	/**
	 * 
	 * @param word
	 * @return
	 */
	public boolean isInDictionary(String word) {
		for (Word dictWord : dictionaryOfWords) {
			
			//true if the word is in the dictionary already
			if (dictWord.getWord().equals(word)) { 
				return true;
			}
		}
		
		return false; //if the word isn't in the dictionary yet
		
	}
	
	/**
	 * This method takes in a word and searches through the dictionary
	 * to find its corresponding numerical value. 
	 * 
	 * @param word a String word that will be searched in the dictionary.
	 * @return an int that corresponds to number value of word. 
	 */
	public int getWordNumValue(String word) {
		for (Word dictWord : dictionaryOfWords) {
			
			if (dictWord.getWord().equals(word)) {
				return dictWord.getNumberValue();
			}
			
		}
		return -1;
	}
	/**
	 * This method takes in an int wordValue and returns its corresponding respective word based 
	 * of its place in dictionary. 
	 * 
	 * @param wordValue the int value of a number that corresponds to a word in the dictionary.
	 * @return The word that corresponds to wordValue
	 */
	public String convertNumValToWord(int wordValue) {
		for (Word dictWord : dictionaryOfWords) {
			
			if (dictWord.getNumberValue() == wordValue) {
				return dictWord.getWord();
			}
		}
		return "NOT_FOUND";
	}
	
	
	/**
	 * This method scans through each element of the index and swaps the places
	 * of originalWord with newWord. For example, if all instances of the word
	 * "this" were replaced with the word "that" in the text file, the dictionary spot reserved
	 * for "this" will now be replaced by "that". 
	 * 
	 * @param originalWord
	 * @param newWord
	 */
	public void replaceDictEntry(String originalWord, String newWord) {
		for (Word word : dictionaryOfWords) {
			if (word.getWord().equals(originalWord)){
				word.setWord(newWord);
				
			}
			
		}
	}
	
	
	
	
	public LinkedList<Word> getDictionaryOfWords(){
		return dictionaryOfWords;
	}

	
	
	private int dictSize;
	/**
	 * I decided to make the dictionary a LL because the size is mutable and for search efficiency –
	 * namely when checking if a Word is in the dictionary or obtaining the numerical value of a Word
	 * in the dictionary. Choosing a data different structure to represent the dictionary (either an AL 
	 * or an array) would take up more space and wouldn't be any more efficient when searching through 
	 * the data structure, element by element.  
	 */
	private LinkedList<Word> dictionaryOfWords;
	
}
