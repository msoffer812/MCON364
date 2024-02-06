package hw1;
import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size = getIntInput(1, 1000, in, "How large should the list be? Give an number between 1 and 1000"); 
		Comparable[] students = {};
		int listCreationType = getIntInput(1, 2, in, "1. Generate random grades \n2. Write grades manually"); 
		if(listCreationType == 1) {
			students = generateRandomList(size);
		}else {
			students = generateListFromInput(in, size);
		}
		sortGrades(new QuickSorter(), students);
		sortGrades(new MergeSorter(), students);
	}
	
	/**
	 * display grades once they're sorted
	 * @param sortedGrades
	 */
	public static void displaySortedGrades(Comparable[] sortedGrades) {
		for(Comparable s:sortedGrades) {
			System.out.println(s.toString());
		}
	}
	/**
	 * Sort the grades and display sorted grades
	 * @param students
	 */
	public static void sortGrades(Sorter sorter, Comparable[] students) {
		sorter = new QuickSorter();
		Comparable[] sortedGrades = (Comparable[]) sorter.sort(students);
		System.out.println("Sorted!");
		displaySortedGrades(sortedGrades);
	}
	
	/**
	 * 
	 * @param in
	 * @param size
	 * @return list of students generated from user input
	 */
	public static Comparable[] generateListFromInput(Scanner in, int size) {
		Comparable[] students = new Comparable[size];
		for(int i=0;i<size;i++) {
			System.out.println("Enter Student " + i + "'s " + "name:");
			String name = in.nextLine();
			int grade = getIntInput(0, 105, in, "Enter Student " + i + "'s " + "grade:");
			Student student = new Student(name, grade);
			students[i] = student;
		}
		return students;
	}
	
	/**
	 * Create and return randomly generated list of grades
	 * @param in
	 * @param size
	 * @return randomly generated list of students and their grades
	 */
	public static Comparable[] generateRandomList(int size) {
		Comparable[] students = new Comparable[size];
		Random rand = new Random();
		for(int i=0;i<size;i++) {
			int grade = rand.nextInt(101);
			Student student = new Student(("Student " + i) , grade);
			students[i] = student;
		}
		return students;
	}
	
	/**
	 * Get and validate user's integer input, then return
	 * @param min
	 * @param max
	 * @param in
	 * @param message
	 * @return user's int input
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
}
