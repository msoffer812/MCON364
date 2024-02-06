package hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.InputMismatchException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

class QuickSortTest {

	@Test
	void emptyListReturnsEmptyList() {
		MergeSorter sorter = new MergeSorter();
		Student[] originalArray = {};
		Student[] sortedArray = {};
		assertArrayEquals(sorter.sort(originalArray), sortedArray);
	}
	
	@Test
	void unsortedListSortsCorrectlyByGrade() {
		Student a = new Student("a", 97);
		Student b = new Student("b", 82);
		Student c = new Student("c", 94);
		Student d = new Student("d", 50);
		Student e = new Student("e", 100);
		Student f = new Student("f", 99);
		Comparable[] originalArray = {f, d, c, b, e, a};
		Comparable[] sortedArray = {d, b, c, a, f, e};
		QuickSorter sorter = new QuickSorter();
		assertArrayEquals(sorter.sort(originalArray), sortedArray);
		
	}

}
