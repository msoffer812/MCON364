package hw4;
import java.util.*;
/**
 * Implement the graph in a program
 * @author mbrso
 *
 */
public class Main {
	public static void main(String[] args) {
		Graph<Person> graph = new Graph(new PersonHasher());
		/*
		 * Create a list to hold the SocialMediaUser instances
		 */
        List<SocialMediaUser> users = new ArrayList<>();
        initializePeople(users);
        initializeGraph(graph, users);
        Scanner in = new Scanner(System.in);
        getGraphOptions(graph, in);
        System.out.println("Goodbye!");
	}
	
	/**
	 * Let user play around with graph capabilities
	 * @param graph
	 * @param in
	 */
	public static void getGraphOptions(Graph<Person> graph, Scanner in) {
		int choice = getIntInput(1, 3, in,"1. Get adjacency list\n2. Add a connection\n3. Find path");
		switch(choice) {
		case 1:
			System.out.println(graph.getAdjacencyTable());
			break;
		case 2:
			addAConnection(graph, in);
			break;
		case 3:
			findPath(graph, in);
			break;
		}
	}
	/**
	 * add a connection between two people
	 * @param graph
	 * @param in
	 */
	public static void addAConnection(Graph<Person> graph, Scanner in) {
		System.out.println(graph.getAdjacencyTable());
		int[] vertices = getTwoPeopleFromUserInput(graph, in);
		graph.addEdge(vertices[0], vertices[1]);
		System.out.println(graph.getAdjacencyTable());
	}
	
	/**
	 * finds and prints out path between 2 vertices
	 * @param graph
	 * @param in
	 */
	public static void findPath(Graph<Person> graph, Scanner in) {
		System.out.println(graph.getAdjacencyTable());
		int[] vertices = getTwoPeopleFromUserInput(graph, in);
		String path = graph.getPath(vertices[0], vertices[1]);
		System.out.println(path);
	}
	
	/**
	 * 
	 * @param in
	 * @return array of 2 people gotten for user input
	 */
	public static int[] getTwoPeopleFromUserInput(Graph<Person> graph, Scanner in) {
		int user1 = getIntInput(1, graph.size(), in, "Select Person 1's number: ");
		int user2 = getIntInput(1, graph.size(), in, "Select Person 2's number: ");
		return new int[] {user1, user2};
	}
	/**
	 * parse and validate string input
	 * @param in
	 * @param message
	 * @return string user input
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
	 * basically validates int input and returns
	 * @param min
	 * @param max
	 * @param in
	 * @param message
	 * @return user int input
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
	 * Initialize a graph with a few edges from given list
	 * @param graph
	 * @param users
	 */
	public static void initializeGraph(Graph<Person> graph, List<SocialMediaUser> users) {
		   
        for(Person person:users) {
        	graph.addVertex(person);
        }
        for(int i=2;i<users.size()-1;i++) {
        	graph.addEdge(i, i-1);
        	graph.addEdge(i, i+1);
        }
	}
	/**
	 * Initialize a bunch of socialmediausers
	 */
	public static void initializePeople(List<SocialMediaUser> users) {
		String[] emails = {
	            "user1@example.com",
	            "user2@example.com",
	            "user3@example.com",
	            "user4@example.com",
	            "user5@example.com",
	            "user6@example.com",
	            "user7@example.com",
	            "user8@example.com",
	            "user9@example.com",
	            "user10@example.com"
	        };
	        String[] firstNames = {"John", "Alice", "Michael", "Emily", "David", "Sarah", "Daniel", "Olivia", "James", "Sophia"};
	        String[] lastNames = {"Smith", "Johnson", "Brown", "Taylor", "Anderson", "Thomas", "Walker", "Moore", "Parker", "White"};
	        int[] ages = {25, 30, 22, 35, 28, 31, 29, 27, 26, 33};
	        List<List<String>> interests = new ArrayList<>();
	        interests.add(Arrays.asList("Travel", "Photography", "Music"));
	        interests.add(Arrays.asList("Reading", "Writing", "Cooking"));
	        interests.add(Arrays.asList("Sports", "Gaming", "Movies"));
	        interests.add(Arrays.asList("Art", "Fashion", "Dancing"));
	        interests.add(Arrays.asList("Technology", "Coding", "Fitness"));
	        interests.add(Arrays.asList("Food", "Nature", "Pets"));
	        interests.add(Arrays.asList("History", "Politics", "Science"));
	        interests.add(Arrays.asList("Languages", "Education", "Health"));
	        interests.add(Arrays.asList("Fashion", "Shopping", "Socializing"));
	        interests.add(Arrays.asList("Cars", "Adventure", "Hiking"));

	        // Create 10 SocialMediaUser instances and add them to the list
	        for (int i = 0; i < 10; i++) {
	            users.add(new SocialMediaUser(emails[i], firstNames[i], lastNames[i], ages[i], interests.get(i)));
	        }
	}
}
