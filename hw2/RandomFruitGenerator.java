package hw2;
import java.util.*;
/**
 * 
 * @author mbrso
 * Generates random fruits from the array/gives an array of the fruits given a size.
 * To be used in a BST of words
 */
public class RandomFruitGenerator {
	private static String[] words = {
		    "apple", "banana", "orange", "grape", "kiwi", "strawberry", "blueberry", "peach", "watermelon", "melon",
		    "pineapple", "pear", "cherry", "plum", "apricot", "raspberry", "blackberry", "pomegranate", "mango",
		    "fig", "lemon", "lime", "coconut", "guava", "papaya", "nectarine", "cranberry", "tangerine", "avocado",
		    "lychee", "persimmon", "dragonfruit", "passionfruit", "grapefruit", "mandarin", "durian", "jackfruit",
		    "rhubarb", "quince", "date", "kumquat", "boysenberry", "elderberry", "starfruit", "mulberry", "loganberry",
		    "cantaloupe", "honeydew", "citron", "plantain", "raspberry", "blackcurrant", "gooseberry", "huckleberry",
		    "elderflower", "carambola", "pomelo", "soursop", "kiwifruit", "ackee", "feijoa", "breadfruit", "ugli fruit",
		    "tamarillo", "sapote", "longan", "sapodilla", "rambutan", "pepino", "abiu", "jabuticaba", "mangosteen",
		    "miracle fruit", "horned melon", "uvaia", "salak", "santol", "carissa", "banana", "orange", "grape", "kiwi",
		    "strawberry", "blueberry", "peach", "watermelon", "melon", "pineapple", "pear", "cherry", "plum", "apricot",
		    "raspberry", "blackberry", "pomegranate"
		};
	
	/**
	 * 
	 * @return a randomly selected fruit
	 */
	public String getRandomFruit() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(words.length);
		return words[randomIndex];
	}
	/**
	 * 
	 * @param size
	 * @return an array of randomly selected fruits
	 */
	public String[] getArrayOfFruits(int size) {
		String[] fruits = new String[size];
		for(int i=0;i<size;i++) {
			fruits[i] = getRandomFruit();
		}
		return fruits;
	}
}
