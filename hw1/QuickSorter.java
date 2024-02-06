package hw1;

import java.util.List;

public class QuickSorter implements Sorter<Comparable>{
	
	@Override
	public Comparable[] sort(Comparable[] array) {
		Comparable[] newArr = new Comparable[array.length];
		for(int i=0;i<array.length;i++) {
			newArr[i] = array[i];
		}
		System.out.println("Calling Quicksort");
		quicksort(newArr, 0, array.length-1);
		return newArr;
	}
	
	/**
	 * Prints out the current state of given subarray
	 * to indicate its current state
	 * @param left
	 * @param right
	 * @param arr
	 */
	private void printOutSubarray(int left, int right, Comparable[] arr) {
		for(int i=left;i<right;i++) {
			System.out.println("{" + arr[i].toString() + "}");
		}
		System.out.println("");
	}
	/**
	 * Main function that sorts the array, calling partition
	 * over and over again on smaller chunks of the array until it's all sorted
	 * @param array
	 * @param left
	 * @param right
	 */
	private void quicksort(Comparable[] array, int left, int right) {
		/*
		 * Base case - we keep dividing up the list and quicksorting until
		 * it's all sorted and the left and right indices have crossed over
		 */
		if(left<right) {
			/*
			 * First call partition on the array so we have one element in roughly the middle 
			 * and all the values on the left are less than it and all the values on the right 
			 * are larger. Then we assign that partitioning index to partitionIndex;
			 */
			System.out.println("Calling partition to partion the array into left"
					+ " and right sides: Left index = " + left + ", Right index = " + right);
			int partitionIndex = partition(array, left, right);
			System.out.println("Current state of array: ");
			printOutSubarray(0, array.length-1, array);
			
			/*
			 * Finally, we recursively call quicksort to partition the left and right
			 * sides of the array on their own, treating them like their own arrays. 
			 */
			System.out.println("Now going to recursively call quicksort on the left side, " +
					left + " -> " + (partitionIndex-1));
			quicksort(array, left, partitionIndex-1);
			System.out.println("Now going to recursively call quicksort on the right side, " +
					(partitionIndex+1) + " -> " + right);
			quicksort(array, partitionIndex+1, right);
		}else {
			System.out.println("Base case has been reached, we've sorted down to basic units of the array and "
					+ "the pointers have crossed over.");
		}
	}
	/**
	 * takes the last element in the array as a pivot,
	 * rearranges so the pivot is where all the elements to the left are smaller 
	 * and all the elements to the right are greater than it
	 * @return the index of the partition element, so the program can then
	 * sort before and after it
	 */
	private int partition(Comparable[] array, int left, int right) {
		Comparable pivotValue = array[right];
		
		/*
		 * i is the smaller index that points to where the
		 * pivot should be so far
		 */
		int i = left - 1;
		
		/*
		 * loop through the array. Compare each element to the pivotValue.
		 * If at any point the value in the array is smaller than the pivotValue,
		 * then it needs to be moved to the left side of the array, where the smaller values go.
		 * So you increment i to make more space for it and swap out the value at i for this value
		 * at j, since that number will be greater than the pivotValue.
		 */
		System.out.println("Pivot element: " + pivotValue.toString());
		for(int j=left; j < right;j++) {
			System.out.println("Pivot index: " + i + ", Current index: " + j);
			if(array[j].compareTo(pivotValue) < 0) {
				System.out.println("The element at " + j + ", {" + array[j].toString() + "}" +
						"is less than the pivot value. Swapping out " + j + " and " + i + ". Now incrementing i");
				i++;
				swap(i, j, array);
			}
			printOutSubarray(left, right, array);
		}
		
		/*
		 * Increment i one more time, then swap the pivot value into it. Now you have the correct placement for 
		 * the pivot element, where all the values smaller than it are to its left and the rest are to its right
		 */
		i++;
		swap(i, right, array);
		/*
		 * Finally, return i, which is the placement of the pivot element.
		 * If the array hasn't been sorted down to its unit parts,
		 * we're going to go back and sort to the right and left of the pivot.
		 */
		return i;
	}
	
	/**
	 * Swaps the elements in the given indices 
	 * @param a
	 * @param b
	 * @param array
	 */
	private void swap(int a, int b, Comparable[] array) {
		Comparable temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
}
