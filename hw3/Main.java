package hw3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	private static final String BuffererReader  = null;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int hasherType = getIntInput(1, 2, in, "1.Sophisticated Hasher \n2.Naive Hasher");
		int mapCapacity = getIntInput(1, Integer.MAX_VALUE, in, "Enter map capacity");
		HashMap map = new HashMap(mapCapacity, getHasherType(hasherType));
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("book.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parseInputAndAddToTheMap(map, new BufferedReader(fileReader));
		displayMenuAndRunMap(map, in);
		in.close();
	}
	public static void displayMenuAndRunMap(HashMap map, Scanner in) {
		String message = "1.View a word count\n"
				+ "2.View words in descending order according to word count\n"
				+ "3.View a report on the internal structure of the hash table\n"
				+ "4.Exit";
		int ans = getIntInput(1, 4, in, message);
		while(ans != 4) {
			switch(ans) {
			case 1:
				handleDisplayingWordCount(in, map);
				break;
			case 2:
				displayWords(map);
				break;
			case 3:
				System.out.println(map.report());
				break;
			}
			ans = getIntInput(1, 4, in, message);
		}
	}
	public static void displayWords(HashMap map) {
		Iterator<MapEntry> mapIterator = map.iterator();
		while(mapIterator.hasNext())){
			System.out.println(mapIterator.next().toString());
		}
	}
	/**
	 * handles displaying word counts based on user input
	 * @param in
	 * @param map
	 */
	public static void handleDisplayingWordCount(Scanner in, HashMap map) {
		String word = getStringInput(in, "Enter the word you'd like to search for:");
		int count = map.get(word);
		System.out.println(word + " was in the book " + count + " times.");
		System.out.println("The linked list where it would have been found on is " 
		+ map.getSlotLength(word) + " entries long.");
	}
	/**
	 * parse and validate string input
	 * @param in
	 * @param message
	 * @return
	 */
	public static String getStringInput(Scanner in, String message) {
		String word ="";
		do {
			System.out.println(message);
			word = in.nextLine();
			message = "Please enter an actual word.";
		}while(word.length()==0);
		return word;
	}
	public static Hasher getHasherType(int answer) {
		if(answer == 1) {
			return new SophisticatedHasher();
		}
		return new NaiveHasher();
	}
	
	public static int getIntInput(int min, int max, Scanner in, String message) {
		boolean loopOver=false;
		int ans = -1;
		do {
			loopOver=false;
			System.out.println(message);
			try {
				ans = Integer.parseInt(in.nextLine());
			}catch(NumberFormatException e) {
				message = "Please enter a number";
				loopOver = true;
			}
			if(ans < min || ans>max) {
				message = "Please enter a number between " + min + " and " + max + ":";
				loopOver = true;
			}
		}while(loopOver);
		return ans;
	}
	/**
	 * inputs a bufferedReader and the map,
	 * processes the book from a txt file and put it into the map.
	 * @param map
	 * @param reader
	 */
	public static void parseInputAndAddToTheMap(HashMap map, BufferedReader reader) {
		String line;
		try { while ((line = reader.readLine()) != null) {
            /*
             * Split the line into an array of words,
             * based on whitespace/punctuation marks
             */
			String[] words = line.split("\\s+");

            /*
             * Process each word into map
             */
            for (String word : words) {
            	/*
            	 * Replace any non-alphabetical character with whitespace
            	 * so its not counted(:
            	 */
            	word = word.replaceAll("[^a-zA-Z]", "").toLowerCase(); 
                map.put(word);
            }
        }

        /*
         * Close the BufferedReader
         */
        reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
