package hw8;

import java.time.Year;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		SchulMember[] shul = {
	            new SchulMember("Levine", "Yosef", 1985, "Rochel", "Levine", new String[]{"Shmuel", "Esther"}, 5),
	            new SchulMember("Friedman", "Rivka", 1978, "Shlomo", "Friedman", new String[]{"Yitzchak", "Chana"}, 10),
	            new SchulMember("Weiss", "Moshe", 1990, "Rivka", "Weiss", new String[]{"David", "Sarah"}, 7),
	            new SchulMember("Gross", "Malka", 1982, "Yehuda", "Gross", new String[]{"Ruchel", "Avraham"}, 8),
	            new SchulMember("Goldstein", "Avraham", 1975, "Miryam", "Goldstein", new String[]{"Leah", "Yakov"}, 15),
	            new SchulMember("Spielman", "Ruchel", 1987, "Shmuel", "Spielman", new String[]{"Chaya", "Yisroel"}, 6),
	            new SchulMember("Stein", "Yaakov", 1980, "Dina", "Stein", new String[]{"Yosef", "Rachel"}, 12),
	            new SchulMember("Schwartz", "Shaindel", 1972, "Aaron", "Schwartz", new String[]{"Esther", "Ephraim", "Malka", "Yosef", "Binyamin"}, 20),
	            new SchulMember("Katz", "Chaim", 1992, "Bracha", "Katz", new String[]{"Yitzchak", "Malka"}, 3),
	            new SchulMember("Eisenberg", "Rochel", 1983, "Ephraim", "Eisenberg", new String[]{"Shaindel", "Yisrael"}, 9),
	            new SchulMember("Landau", "Avraham", 1976, "Bracha", "Landau", new String[]{"Shmuel", "Esther"}, 14),
	            new SchulMember("Fischer", "Esther", 1989, "Yosef", "Fischer", new String[]{"Rivka", "Moshe"}, 5),
	            new SchulMember("Schapiro", "Yehuda", 1984, "Miryam", "Schapiro", new String[]{"Chaim", "Ruchel"}, 11),
	            new SchulMember("Gordon", "Miryam", 1979, "Avraham", "Gordon", new String[]{"Leah", "Yaakov"}, 13),
	            new SchulMember("Rosen", "Yitzchak", 1995, "Esther", "Rosen", new String[]{"Shlomo", "Miryam"}, 2),
	            new SchulMember("Klein", "Esther", 1981, "Yehuda", "Klein", new String[]{"Miryam", "Avraham"}, 7),
	            new SchulMember("Weinstein", "Yakov", 1973, "Rochel", "Weinstein", new String[]{"Yosef", "Malka", "Chavie", "Shprintzie"}, 18),
	            new SchulMember("Stern", "Chana", 1991, "Yehuda", "Stern", new String[]{"Yitzchak", "Rivka"}, 4),
	            new SchulMember("Segal", "Yisroel", 1986, "Chana", "Segal", new String[]{"Moshe", "Ruchel"}, 8),
	            new SchulMember("Glick", "Bracha", 1977, "Yakov", "Glick", new String[]{"Yosef", "Miryam"}, 16)
	        };
		 
		 /**
		  * Consumer that accepts the stream, prints out its size
		  */
		 Consumer<SchulMember[]> printSize = arr -> System.out.println("Shul Size: " + 
				 															Arrays.stream(arr)
		 																		.count()
		 															  );
		 
		 
		 /**
		  * Take families, sort by length of time spent in shul, print out
		  */
		 Consumer<SchulMember[]> printLengthFamWasInShul = arr -> {
			/*
			 * Sort then print out
			 */
			 Arrays.stream(arr)
				.sorted((SchulMember mem1, SchulMember mem2) -> mem1.getYearsOfMembership() - mem2.getYearsOfMembership())
				.forEach(member ->{
					System.out.println(member.getLastNameOfMember() + ": " + 
						member.getYearsOfMembership() + " years");
				});
		 };
		 
		 /**
		  * Print out from oldest to youngest the ages of the members
		  */
		 Consumer<SchulMember[]> printOutAges = arr ->{
			int currYear = Year.now().getValue();
			/*
			 * Sort - default from built in compareTo, then print out
			 */
			Arrays.stream(arr)
				.sorted((mem1, mem2) -> mem2.compareTo(mem1))
				.forEach(member ->{
					System.out.println(member.getFirstNameOfMember() + " " + member.getLastNameOfMember() + ": " + 
						(currYear - member.getBirthDateOfMember()) + " years old");
				});
		 };
		 
		 /**
		  *  Sort the names of the spouses of all members 
		  */
		 Function<SchulMember[], String[]> returnSortedSpouseNames = arr -> {
			 String[] ans = Arrays.stream(arr)
							 	.map(member->(member.getSpouseFirstName() + " " + member.getspouseLastName()))
							 	.sorted()
							 	.toArray(String[]::new);
			 return ans;
		 };
		 
		 /**
		  * print out all families who have more than 3 children 
		  */
		 Consumer<SchulMember[]> printOutFamiliesWithMoreThan3Children = arr -> {
			 /**
			  * This predicate checks family size and returns boolean if matches
			  */
			 Predicate<SchulMember> checkFamilySize = 
					 member -> {
						 return member.getChildrenNames().length > 3;
			 			};
			 Arrays.stream(arr)
			 	.forEach(member -> {
			 				if(checkFamilySize.test(member)) {
			 					System.out.println(member.getLastNameOfMember() + " family");
			 				}
			 			});
		 };
		 
		 Consumer<SchulMember[]> printOutChildrensNames = arr -> {
			 /*
			  * Predicate to test if the given name is greater or smaller than
			  * c, comparing first letter to c
			  */
			Predicate<String> testName = name -> {
				char c = Character.toLowerCase(name.charAt(0));
				return c > 'c';
			};
			/**
			 * Now apply the predicate to a stream, printing them out if they pass
			 */
			Arrays.stream(arr)
				.forEach( member ->{
					Arrays.stream(member.getChildrenNames())
						.forEach(child ->{
							if(testName.test(child)) {
								System.out.println(child + ", from the " + member.getLastNameOfMember() + " family");
							}
						});
				});
		 };
		 
		 printSize.accept(shul);
		 printLengthFamWasInShul.accept(shul);
		 printOutAges.accept(shul);
		 String[] spouses = returnSortedSpouseNames.apply(shul);
		 printOutFamiliesWithMoreThan3Children.accept(shul);
		 printOutChildrensNames.accept(shul);
	}

}
