/**
 * **THIS CLASS IS FOR STEP TWO AND THREE ONLY**
 * 
 * This class represents an individual word that helps the dictionary (stored as a LinkedList
 * in Compressor) remember the numerical of each unique word.
 * 
 * @author Marcus
 *
 */
public class Word {

	public Word(String word, int number) {
		theWord = word;
		numberValue = number;
	}
	
	public String getWord() {
		return theWord;
	}
	
	public int getNumberValue() {
		return numberValue;
	}
	
	public void setNumberValue(int newNum) {
		numberValue = newNum;
	}
	
	public void setWord(String newWord) {
		theWord = newWord;
	}
	
	
	
	
	
	private String theWord;
	private int numberValue;
	
	
}
