/*
File: Practice Problem Unit 4
Author: Stephen Li
Date Created: April 20, 2026
Date Last Modified: April 27, 2026
*/

import java.util.Random;
import java.util.Scanner;

public class ProblemSet {

	public static void main(String args[]) {
	    Scanner input = new Scanner(System.in);
	    Random random = new Random();
	    
	    //Welcome message
	    System.out.println("Welcome to the High Low Guessing Game.\n");
	    System.out.print("Input a number of rounds to play: ");
	    int roundNumber = 0;
	    
	    do { //Round number checker
	        while (!(input.hasNextInt())) { //Integer check
	            input.nextLine();
                System.out.print("\nInvalid Input!\nInput a number of rounds to play: ");
	        }
	        roundNumber = input.nextInt();
	        if (roundNumber <= 0) { //Greater than 0 check
                System.out.print("\nInvalid Input!\nInput a number of rounds to play: ");
	        }
            input.nextLine();
	    } while (roundNumber <= 0); //Repeat until valid
	    
	    String range = ""; //Range checker
	    String rangeLeft = "";
	    String rangeRight = "";
	    int rangeHasDash;
        System.out.println("\nWhat Range would you like to play between (#-#)?");
        do {
            rangeHasDash = -1;
	        while (rangeHasDash < 0) { //While there is no dash
	            range = input.nextLine();
	            rangeHasDash = rangeHasDash(range);
	            if (rangeHasDash < 0) {
	                System.out.print("\nInvalid Input!\nWhat Range would you like to play between (#-#)?\n");
	            }
	        }
	        rangeLeft = range.substring(0, rangeHasDash); //Split range into two scales
	        rangeRight = range.substring(rangeHasDash + 1);
	        if (!rangeIsNumber(rangeLeft) || !rangeIsNumber(rangeRight)) { //While inputted numbers in range are not integers
	            System.out.print("\nInvalid Input!\nWhat Range would you like to play between (#-#)?\n");
	            rangeHasDash = -1;
	        }
	        else { //Checks if range is more than 3 numbers
                int num1 = Integer.parseInt(rangeLeft);
                int num2 = Integer.parseInt(rangeRight);
                if (Math.abs(num1 - num2) < 2) {
                    System.out.print("\nInvalid Input!\nWhat Range would you like to play between (#-#)?\n");
                    rangeHasDash = -1;
                }
            }
        }   while (rangeHasDash < 0);
        
        //Split up range into max, min, and middle
        int rangeMin = Math.min(Integer.parseInt(rangeLeft), Integer.parseInt(rangeRight));
        int rangeMax = Math.max(Integer.parseInt(rangeLeft), Integer.parseInt(rangeRight));
        double rangeMiddle = (rangeMin + rangeMax) / 2.0;
        int rangeBottomMiddle = (int) Math.floor(rangeMiddle);
        int rangeTopMiddle = (int) Math.ceil(rangeMiddle);
        int chosenOption = 0;
        int score = 0;
        boolean scoreChecker;
        
        for (int i = 1; i <= roundNumber; i++) { //start of game output
            System.out.println("\nRound " + i + ":\n");
            System.out.println(optionMenu(i, rangeTopMiddle, rangeBottomMiddle, rangeMin, rangeMax)); //Uses method to get option menu
            int randomNumber = random.nextInt(rangeMax - rangeMin) + rangeMin; //chooses random number
            do { //Chosen option checker
                while (!(input.hasNextInt())) { //Makes sure it's an integer
                    input.nextLine();
                    System.out.println("\nInvalid Input!");
                    System.out.println(optionMenu(i, rangeTopMiddle, rangeBottomMiddle, rangeMin, rangeMax));
                }
                chosenOption = input.nextInt();
                input.nextLine();
                if (chosenOption > 3 || chosenOption < 1) { //Makes sure it's 1, 2, or 3
                    System.out.println("\nInvalid Input!");
                    System.out.println(optionMenu(i, rangeTopMiddle, rangeBottomMiddle, rangeMin, rangeMax));
                }
            } while (chosenOption > 3 || chosenOption < 1);
            
            scoreChecker = check(chosenOption, randomNumber, rangeTopMiddle, rangeBottomMiddle, rangeMin, rangeMax); //Uses method to check if random number matches option chosen range
            System.out.print("\nThe number was " + randomNumber + ", You were ");
            if (scoreChecker) {
                System.out.println("correct.");
                score++;
            }
            else {
                System.out.println("incorrect.");
            }
            System.out.println("Current Score: " + score);
            chosenOption = 0;
        }
        
        System.out.println("\nTotal Score: " + score); //Game end message
        if (roundNumber % 2 > score) {
            System.out.println("You got " + score + " out of " + roundNumber + " correct. Better Luck next time.");
        }
        else {
            System.out.println("You got " + score + " out of " + roundNumber + " rounds right!");
        }
	}
	
	public static int rangeHasDash(String range) { //Checks if range can be split then returns dash index
	    if (!range.contains("-")) { //No dash in range check
	        return -1;
	    }
	    if (range.startsWith("-")) { //Range starts with dash
	        return range.indexOf("-", 1);
	    }
	    return range.indexOf("-"); //Range does not start with dash
	}
	
	public static boolean rangeIsNumber(String num) { //Checks if input are numbers
	    if (num.length() == 0) {  //Checks if string length is not 0
	        return false;
	    }
	    int start = 0;
	    if (num.charAt(0) == '-') { //Checks if first character is "-" to not count it because not number
	        if (num.length() == 1) { //Checks if only "-" was input
	            return false;
	        }
	        start = 1;
	    }
	    for (int i = start; i < num.length(); i++) { //Checks if all numbers in range are numbers
	        if (!Character.isDigit(num.charAt(i))) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static String optionMenu(int i, int rangeTopMiddle, int rangeBottomMiddle, int rangeMin, int rangeMax) { //print menu for options
        String menu = ("Please select High, Low or Even:\n");
        if (rangeTopMiddle + 1 != rangeMax) { //High single or multiple numbers
            menu += "1. High (" + (rangeTopMiddle + 1) + " to " + rangeMax + ")\n";
        }
        else {
            menu += "1. High (" + rangeMax + ")\n";
        }
        
        if (rangeMin != rangeBottomMiddle - 1) { //Low single or multiple numbers
            menu += "2. Low (" + rangeMin + " to " + (rangeBottomMiddle - 1) + ")\n";
        }
        else {
            menu += "2. Low (" + rangeMin + ")\n";
        }
        
        if (rangeBottomMiddle != rangeTopMiddle) { //Even single or multiple numbers
            menu += "3. Even (" + rangeBottomMiddle + " to " + rangeTopMiddle + ")\n";
        }
        else {
            menu += "3. Even (" + rangeBottomMiddle + ")\n";
        }
        return menu;
	}
	
	public static boolean check(int chosenOption, int randomNumber, int rangeTopMiddle, int rangeBottomMiddle, int rangeMin, int rangeMax) { //Checks if your selected option matches random number
	    if (chosenOption == 1) { //Checks for High
	        if (randomNumber > rangeTopMiddle && randomNumber <= rangeMax) {
	            return true;
	        }
	        return false;
	    }
	    if (chosenOption == 2) { //Checks for Low
	        if (randomNumber >= rangeMin && randomNumber < rangeBottomMiddle) {
	            return true;
	        }
	        return false;
	    }
	    else { //Checks for Even
	        if (randomNumber == rangeBottomMiddle || randomNumber == rangeTopMiddle) {
	            return true;
	        }
	        return false;
	    }
	}
}
