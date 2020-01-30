import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 * This class simplifies the process in which the compiler takes in and processes the user input. 
 * As for different commands call for different types of acceptable inputs, this class helps check
 * if what the user inputed is acceptable or not (depending on what the compiler is asking for.)
 * 
 * 
 * @author Marcus
 *
 */
public class UserScannerStep2 {
	
	/**
	 * This method checks the validity of a variety of inputs, delineated by whichInput. 
	 * 
	 * If the user inputed a invalid input, 
	 * this method throws an  exception (if needed) and reprompts the user to try again. Giving an invalid 
	 * input will make the method return false, causing the loop in which this method is called in to repeat 
	 * itself to reprompt the user for another input. 
	 * 
	 * See table below to see what each which number whichInput corresponds to. 
	 * 
	 * @param input the String that is inputed from the user that will be processed to see whether it is valid
	 * or not.
	 * @param whichInput is an int that designates the type of input the user is giving. For example, if program is 
	 * only looking for "g", "p", "r", "s", or "q", whichInput == 1.  
	 * @return returns true if the input is acceptable and false if it is not. Returning a false
	 * 
	 * whichInput index:
	 * 0: accepts "g" (to create a new TextFile) and "g" + file with .txt extension (to open an existing file to be edited)
	 * 1: accepts the commands "g", "p", "r", "s", or "q"
	 * 2: accepts only ints.
	 * 3: accepts Strings only if they don't have an extension. 
	 * 4: accepts only either 1 or 2 to create a .cmp or .txt file
	 * 5:
	 */
	public boolean process(String input, int whichInput) {
		
		
		//checking if the file name is valid or not.
		
		
		switch (whichInput) {
		
		case 0: 
			
			if (input.equals("g")) {
				//do nothing because this is a valid input
				return true;
			}
			
			// if the first letter of the input is not g, prompt the user to try again
			else if (!input.substring(0,1).equals("g")) {
				System.out.println("Invalid input. Please try again: ");
				
				return false;
			}
			break;
		
		
		
		
		//checking if the inputed command is valid or not. If it doesn't equal g, p, r, s, or q, prompt the user to try again.
		case 1:
			
			if ((input.equals("g") || input.equals("p") || input.equals("r") || input.equals("w") || input.equals("s") || input.equals("q") )){
				return true;
			}
			System.out.println("Invalid input. Please input either g, p, r, w, s, or q: ");
			return false;
			
			
		//checking if the inputed line number is within the bounds of the file.
		case 2:
			//test if the String is a valid int. 
			try {
				Integer.parseInt(input);
			}
			//if the input is not a parseInt-able, throw this exception.
			catch (Exception e) {
				
				
				System.out.println("Invalid input. Please input an integer within the bounds.");
				return false;
			}
			return true;

			
		//excluding extensions. 
		case 3: 
			//if there is no extension, proceed
			if (input.indexOf('.') == -1) {
				return true;
			}
			
			//If there is a ".txt" ending, prompt the user to try again.
			System.out.println("Invalid input. Please exclude the extension and try again: ");
			return true;
		

		//checks the input as either 1 (to create .cmp file) or 2 (to create .txt file)
		case 4:
			if (input.equals("1") || input.equals("2")) {
				
				return true;
			}
			
			System.out.println("Invalid input. Please input either 1 or 2 to create a file.");
			return false;

		
		case 5:
			input = input.trim();
			
			//return true only if there are two words in the input (each word separated by only one space)
			if (input.lastIndexOf(' ') == input.indexOf(' ') ) {
				return true;
			}
			//return true if there is only one word. 
			else if (input.indexOf(' ') == -1) {
				return true;
			}
			
			System.out.println("Invalid input. Please input 2 words separated by a space to replace or"
							+ "\none word to delete.");
			return false;
		}
		
		return true;
		
	}
	
	
	
	/**
	 * 
	 * This method creates a new Scanner object for each time the user is prompted to input a 
	 * command into the console. It returns that inputed String only if what he or she inputed 
	 * is valid. However, if it is not valid, it will continue to prompt the user to input 
	 * a valid String until what they input is acceptable.  
	 * 
	 * @param whichInput helps denote what type of input is being processed. 
	 * @return returns the input if it is a valid input after processed by process()
	 */
	public String getScannerInput(int whichInput) {
		
		myScanner = new Scanner(System.in);
		
		boolean validInput = false;
		
		String input = "";
		while (!validInput) {
			try {
				input = myScanner.nextLine();
				validInput = process(input, whichInput);
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}	
		return input;
		
	}
	
	Scanner myScanner;
}
