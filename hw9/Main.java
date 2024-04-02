package hw9;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

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
		while(mapIterator.hasNext()){
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
	public static BiFunction<String, MapEntry[], Integer> getHasherType(int answer) {
		if(answer == 1) {
			return returnSophisticatedHasher();
		}
		return returnNaiveHasher();
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
	
	public static BiFunction<String, MapEntry[], Integer> returnNaiveHasher(){
		BiFunction<String, MapEntry[], Integer> hasher = (key, table)-> {
			int totalAsc = 0;
			/*
			 * Count up the total ASCII numerical value
			 */
			for(int i=0;i<key.length();i++) {
				totalAsc += (int)key.charAt(i);
			}
			/*
			 * return the remainder when divided by the 
			 * length of the array so it spits out a valid index
			 */
			return totalAsc % table.length;
		};
		return hasher;
	}
	
	public static BiFunction<String, MapEntry[], Integer> returnSophisticatedHasher(){
		BiFunction<String, MapEntry[], Integer> hasher = (word, table)-> {
			int PRIME=1;
			/**
		     * Calculate and return total ASCII value of a word
		     * @param word
		     * @return
		     */
			Function<String, Integer> calcASCII = key -> {
			    int totalAsc = 0;
			    for (int i = 0; i < key.length(); i++) {
			        totalAsc += (int) key.charAt(i);
			    }
			    return totalAsc;
			};
			Function<Integer, Integer> getBiggestPrimeNumberWithCoolAlgorithm = MAX_SIZE -> {
				/*
		    	 * Initialize the array set to falses
		    	 */
		    	boolean[] isntPrime = new boolean[MAX_SIZE];
		    	isntPrime[0] = true;
		    	isntPrime[1] = true;
		    	
		    	for(int i=2;i<Math.sqrt(MAX_SIZE);i++) {
		    		if(!isntPrime[i]) {
		    			/*
		    			 * Now loop through all multiples of 2 until MAXSize -
		    			 * set each one in the array to true since it can be
		    			 * divided by i
		    			 */
		    			 for (int j=i*i; j<MAX_SIZE; j+=i) {
		    				 isntPrime[j] = true;
		    			 }
		    		}
		    	}
		    	/*
		    	 * Now find the largest prime number starting from closest to maxSize
		    	 */
		    	for(int i=isntPrime.length-1; i>=2;i--) {
		    		if(!isntPrime[i]) {
		    			return i;
		    		}
		    	}
		    	return 1;
			};
			Function<Integer, Integer>  hash2 = asciiVal -> {
				return PRIME - (asciiVal % PRIME);
			};
			BiFunction<Integer, Integer, Integer>  hash1 = (asciiVal, arraySize) -> {
				return asciiVal % arraySize;
			};
			int asciiVal = calcASCII.apply(word), origin = hash1.apply(asciiVal, table.length), i = 0, index = origin, maxProbes = table.length; // Maximum number of probes before we give up and chain
	        while (i < maxProbes && table[index] != null) {
	            if (table[index] == null || table[index].contains(word)) {
	                // Word found at the current index
	                return index;
	            }
	            // Collision resolution using double hashing
	            index = (origin + i * hash2.apply(asciiVal)) % table.length;
	            i++;
	        }
	        // Word not found or no more available slots
	        // Return the current index (where it should be inserted)
	        return index; 	

		};
		return hasher;
	}
}
