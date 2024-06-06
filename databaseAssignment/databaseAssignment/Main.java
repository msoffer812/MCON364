package databaseAssignment;

import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		PREMIERCOInteract dbInter = new PREMIERCOInteract();
		//Task 1 - just print out all database info
		System.out.println(dbInter.dbToString());
		//Task 2
		task2(dbInter);
		//Task 3
		task3(dbInter);
	}
	
	/**
	 * Complete task 3 of changing the employee's password
	 * with changes
	 * @param dbInter
	 */
	public static void task3(PREMIERCOInteract dbInter) {
		System.out.println("Let's change the password from nothing to 'hi': ");
		System.out.println(dbInter.alterEmployeePassword(10, null, "hi"));
		System.out.println("");
		System.out.println("Let's change the password from 'hi' to 'sdfasduhaewl': ");
		System.out.println(dbInter.alterEmployeePassword(10, "hi", "sdfasduhaewl"));
		System.out.println("");
		System.out.println("Now we will enter the wrong password for old password, see what happens: ");
		System.out.println(dbInter.alterEmployeePassword(10, "fgdg", "hgjyrthr"));
		System.out.println("");
		System.out.println("Changing password back to null so this query can work again");
		System.out.println(dbInter.alterEmployeePassword(10, "sdfasduhaewl", null));
		System.out.println("");
	}
	
	/**
	 * Complete task2 of looking up user's information with
	 * dynamic parameterization
	 * @param dbInter
	 */
	public static void task2(PREMIERCOInteract dbInter) {
		try {
			System.out.println("Customer 148");
			System.out.println(dbInter.lookUpUser(148));
			System.out.println("");
			System.out.println("Customer 282");
			System.out.println(dbInter.lookUpUser(282));
			System.out.println("");
			System.out.println("Customer 408");
			System.out.println(dbInter.lookUpUser(408));
			System.out.println("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
