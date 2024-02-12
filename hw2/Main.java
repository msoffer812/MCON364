package hw2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int dataType = getIntInput(1, 3, in, "What would you like the tree to be composed of?\n1.Strings\n2.Doubles\n3.Integers");
		int entryType = getIntInput(1, 2, in, "1. Enter your own data into the tree\n2.Randomly generate data");
		int size = getIntInput(1, Integer.MAX_VALUE, in, "Enter the size of the tree:"); 
		if(dataType == 1) {
			makeTreeOfStrings(entryType, size, in);
		}else if(dataType == 2) {
			makeTreeOfDoubles(entryType, size, in);
		}else {
			makeTreeOfInts(entryType, size, in);
		}
		System.out.println("Goodbye!");
		in.close();
		System.exit(0);
	}
	
	public static void traverseTree(Scanner in, BinarySearchTree tree) {
		int choice = getIntInput(1, 4, in, "1.Preorder dfs\n2.Postorder dfs\n3.Inorder dfs\n4.Breadth-First Search");
		Iterator iter = null; 
		switch(choice) {
		case 1: 
			iter = tree.getIterator(Traversal.DFSPreorder);
			break;
		case 2: 
			iter = tree.getIterator(Traversal.DFSPostorder);
			break;	
		case 3: 
			iter = tree.getIterator(Traversal.DFSInorder);
			break;
		case 4: 
			iter = tree.getIterator(Traversal.BFS);
			break;
		}
		while(iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.println("");
	}
	/**
	 * method calls methods to make tree of strings, then to play with tree
	 * @param entryType
	 * @param size
	 * @param in
	 */
	public static void makeTreeOfStrings(int entryType, int size, Scanner in){
		BinarySearchTree<String> tree;
		if(entryType == 1) {
			tree = createTreeOfStringsBasedOnUserInput(size, in);
		}else {
			tree = generateRandomStringsAndPutIntoTree(size);
		}
		playWithStringTree(tree, in);
	}
	public static void makeTreeOfDoubles(int entryType, int size, Scanner in){
		BinarySearchTree<Double> tree;
		if(entryType == 1) {
			tree = createTreeOfDoublesBasedOnUserInput(size, in);
		}else {

			tree = generateRandomDoubleAndPutIntoTree(size);
		}	
		playWithDoubleTree(tree, in);
	}
	public static void makeTreeOfInts(int entryType, int size, Scanner in){
		BinarySearchTree<Integer> tree;
		if(entryType == 1) {
			tree = createTreeOfIntsBasedOnUserInput(size, in);
		}else {
			tree = generateRandomIntsAndPutIntoTree(size);
		}
		playWithIntTree(tree, in);
	}	
	
	/**
	 * Play around with tree until user wants to leave
	 * @param tree
	 * @param in
	 */
	public static void playWithStringTree(BinarySearchTree<String> tree, Scanner in) {
		System.out.println(tree.toString());
		int choice = getIntInput(1, 7, in, "1.Remove an element\n2.Add an element\n3.Get min value \n"
				+ "4.Get max value\n5.Check if tree contains a value\n6.Traverse tree\n7.Exit");
		while(choice != 7) {
			switch(choice) {
			case 1:
				removeStringFromTree(in, tree);
				break;
			case 2:
				addStringToTree(in, tree);
				break;
			case 3:
				System.out.println("Min value:" + tree.min());
				break;
			case 4:
				System.out.println("Max value:" + tree.max());
				break;
			case 5:
				lookUpStringInTree(in, tree);
				break;
			case 6:
				traverseTree(in, tree);
				break;
			}
			choice = getIntInput(1, 7, in, "1.Remove an element\n2.Add an element\n3.Get min value \n"
					+ "4.Get max value\n5.Check if tree contains a value\n6.Traverse tree\n7.Exit");
		}
	}
	
	/**
	 * Play around with tree until user wants to leave
	 * @param tree
	 * @param in
	 */
	public static void playWithIntTree(BinarySearchTree<Integer> tree, Scanner in) {
		System.out.println(tree.toString());
		int choice = getIntInput(1, 7, in, "1.Remove an element\n2.Add an element\n3.Get min value \n"
				+ "4.Get max value\n5.Check if tree contains a value\n6.Traverse tree\n7.Exit");
		while(choice != 7) {
			switch(choice) {
			case 1:
				removeIntFromTree(in, tree);
				break;
			case 2:
				addIntToTree(in, tree);
				break;
			case 3:
				System.out.println("Min value:" + tree.min());
				break;
			case 4:
				System.out.println("Max value:" + tree.max());
				break;
			case 5:
				lookUpIntInTree(in, tree);
				break;
			case 6:
				traverseTree(in, tree);
				break;
			}
			choice = getIntInput(1, 7, in, "1.Remove an element\n2.Add an element\n3.Get min value \n"
					+ "4.Get max value\n5.Check if tree contains a value\n6.Traverse tree\n7.Exit");
		}
	}
	
	/**
	 * Play around with tree until user wants to leave
	 * @param tree
	 * @param in
	 */
	public static void playWithDoubleTree(BinarySearchTree<Double> tree, Scanner in) {
		System.out.println(tree.toString());
		int choice = getIntInput(1, 7, in, "1.Remove an element\n2.Add an element\n3.Get min value \n"
				+ "4.Get max value\n5.Check if tree contains a value\n6.Traverse tree\n7.Exit");
		while(choice != 7) {
			switch(choice) {
			case 1:
				removeDoubleFromTree(in, tree);
				break;
			case 2:
				addDoubleToTree(in, tree);
				break;
			case 3:
				System.out.println("Min value:" + tree.min());
				break;
			case 4:
				System.out.println("Max value:" + tree.max());
				break;
			case 5:
				lookUpDoubleInTree(in, tree);
				break;
			case 6:
				traverseTree(in, tree);
				break;
			}
			choice = getIntInput(1, 7, in, "1.Remove an element\n2.Add an element\n3.Get min value \n"
					+ "4.Get max value\n5.Check if tree contains a value\n6.Traverse tree\n7.Exit");
		}
	}
	/**
	 * Get user input as to word to remove from tree, then remove it.
	 * @param in
	 * @param tree
	 */
	public static void removeStringFromTree(Scanner in, BinarySearchTree<String> tree) {
		String wordToRemove = getStringInput(in, "Enter word you'd like to remove");
		tree.remove(wordToRemove);
		System.out.println(tree.toString());
	}
	
	/**
	 * Get user input as to word to add to the tree, then add it.
	 * @param in
	 * @param tree
	 */
	public static void addStringToTree(Scanner in, BinarySearchTree<String> tree) {
		String word = getStringInput(in, "Enter word you'd like to add");
		tree.add(word);
		System.out.println(tree.toString());
	}
	
	/**
	 * Get user input as to word to look up, then print if found
	 * @param in
	 * @param tree
	 */
	public static void lookUpStringInTree(Scanner in, BinarySearchTree<String> tree) {
		String word = getStringInput(in, "Enter word you'd like to find");
		if(tree.contains(word)) {
			System.out.println("The tree does contain this word.");
		}else {
			System.out.println("The tree does not contain this word.");
		}
	}
	
	/**
	 * Get user input as to double to remove from tree, then remove it.
	 * @param in
	 * @param tree
	 */
	public static void removeDoubleFromTree(Scanner in, BinarySearchTree<Double> tree) {
		double numToRemove = getDoubleInput(Double.MIN_VALUE, Double.MAX_VALUE, in, "Enter number you'd like to remove");
		tree.remove(numToRemove);
		System.out.println(tree.toString());
	}
	
	/**
	 * Get user input as to Double to add to the tree, then add it.
	 * @param in
	 * @param tree
	 */
	public static void addDoubleToTree(Scanner in, BinarySearchTree<Double> tree) {
		double num = getDoubleInput(Double.MIN_VALUE, Double.MAX_VALUE, in, "Enter number you'd like to add");
		tree.add(num);
		System.out.println(tree.toString());
	}
	
	/**
	 * Get user input as to double to look up, then print if found
	 * @param in
	 * @param tree
	 */
	public static void lookUpDoubleInTree(Scanner in, BinarySearchTree<Double> tree) {
		double num = getDoubleInput(Double.MIN_VALUE, Double.MAX_VALUE, in, "Enter number you'd like to find");
		if(tree.contains(num)) {
			System.out.println("The tree does contain this number.");
		}else {
			System.out.println("The tree does not contain this number.");
		}
	}

	/**
	 * Get user input as to int to remove from tree, then remove it.
	 * @param in
	 * @param tree
	 */
	public static void removeIntFromTree(Scanner in, BinarySearchTree<Integer> tree) {
		int numToRemove = getIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE, in, "Enter number you'd like to remove");
		tree.remove(numToRemove);
		System.out.println(tree.toString());
	}
	
	/**
	 * Get user input as to int to add to the tree, then add it.
	 * @param in
	 * @param tree
	 */
	public static void addIntToTree(Scanner in, BinarySearchTree<Integer> tree) {
		int num = getIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE, in, "Enter number you'd like to add");
		tree.add(num);
		System.out.println(tree.toString());
	}
	
	/**
	 * Get user input as to double to look up, then print if found
	 * @param in
	 * @param tree
	 */
	public static void lookUpIntInTree(Scanner in, BinarySearchTree<Integer> tree) {
		int num = getIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE, in, "Enter number you'd like to find");
		if(tree.contains(num)) {
			System.out.println("The tree does contain this number.");
		}else {
			System.out.println("The tree does not contain this number.");
		}
	}
	
	/**
	 * Creates and generates data for a binary search true of strings
	 * @param size
	 * @param in
	 * @return
	 */
	public static BinarySearchTree<String> generateRandomStringsAndPutIntoTree(int size){
		BinarySearchTree<String> tree = new BinarySearchTree();
		/*
		 * Use RandomFruitGenerator to spit out random strings
		 */
		RandomFruitGenerator gen = new RandomFruitGenerator();
		for(int i=0;i<size;i++) {
			String fruit = gen.getRandomFruit();
			System.out.println("Adding " + fruit);
			tree.add(fruit);
		}
		return tree;
	}
	/**
	 * Creates and generates data for a binary search true of doubles
	 * @param size
	 * @param in
	 * @return
	 */
	public static BinarySearchTree<Double> generateRandomDoubleAndPutIntoTree(int size){
		BinarySearchTree<Double> tree = new BinarySearchTree();
		Random rand = new Random();
		for(int i=0;i<size;i++) {
			double num = 999999999*rand.nextDouble();
			System.out.println("Adding " + num);
			tree.add(num);
		}
		return tree;
	}
	
	/**
	 * Creates and generates data for a binary search true of ints
	 * @param size
	 * @param in
	 * @return
	 */
	public static BinarySearchTree<Integer> generateRandomIntsAndPutIntoTree(int size){
		BinarySearchTree<Integer> tree = new BinarySearchTree();
		Random rand = new Random();
		for(int i=0;i<size;i++) {
			int num = rand.nextInt(999999999);
			System.out.println("Adding " + num);
			tree.add(num);
		}
		return tree;
	}
	
	/**
	 * create a tree on strings based on user input
	 * @param size
	 * @param in
	 * @return said tree
	 */
	public static BinarySearchTree<String> createTreeOfStringsBasedOnUserInput(int size, Scanner in){
		BinarySearchTree<String> tree = new BinarySearchTree();
		for(int i=0;i<size;i++) {
			String word = getStringInput(in, "Enter the next word");
			tree.add(word);
		}
		return tree;
	}
	/**
	 * create a tree on doubles based on user input
	 * @param size
	 * @param in
	 * @return said tree
	 */
	public static BinarySearchTree<Double> createTreeOfDoublesBasedOnUserInput(int size, Scanner in){
		BinarySearchTree<Double> tree = new BinarySearchTree();
		for(int i=0;i<size;i++) {
			tree.add(getDoubleInput(Double.MIN_VALUE, Double.MAX_VALUE, in, "Enter a double:"));
		}
		return tree;
	}
	/**
	 * create a tree on ints based on user input
	 * @param size
	 * @param in
	 * @return said tree
	 */
	public static BinarySearchTree<Integer> createTreeOfIntsBasedOnUserInput(int size, Scanner in){
		BinarySearchTree<Integer> tree = new BinarySearchTree();
		for(int i=0;i<size;i++) {
			tree.add(getIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE, in, "Enter an int:"));
		}
		return tree;
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
	/**
	 * parse and validate user input that's an int
	 * @param min
	 * @param max
	 * @param in
	 * @param message
	 * @return
	 */
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
	 * parse and validate user input that's a double
	 * @param min
	 * @param max
	 * @param in
	 * @param message
	 * @return
	 */
	public static double getDoubleInput(double min, double max, Scanner in, String message) {
		boolean loopOver=false;
		double ans = -1;
		do {
			loopOver=false;
			System.out.println(message);
			try {
				ans = Double.parseDouble(in.nextLine());
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

}
