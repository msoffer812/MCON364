package hw1;
import java.util.*;
public class MergeSorter implements Sorter<Comparable>{
	private Comparable[] sortedArray;
	
	@Override
	public Comparable[] sort(Comparable[] array) {
		sortedArray = makeTempArray(array.length, 0, array);
		System.out.println("Calling mergeSort to sort the list by breaking it down into unit parts, then merging it back together");
		mergeSort(sortedArray, 0, array.length-1);
		return sortedArray;
	}
	
	/**
	 * Recursively splits array into smaller and smaller bits until it's down to unit parts, then merges
	 * them back together
	 * @param array
	 * @param left
	 * @param right
	 */
	private void mergeSort(Comparable[] array, int left, int right) {
		/*
		 * Recursively break up array in smaller parts until base case has been reached, where the 
		 * indices have crossed over because there's nowhere left to go
		 */
		if(left< right) {
			int mid = left + (right-left) / 2;
			System.out.println("Left = " + left + ", Right = " + right + ", Middle = " + mid);
			/*
			 * Call mergeSort recursively to go sort down to the basic unit of the array
			 */
			System.out.println("Recursively calling mergesort from " + left + " to " + mid);
			mergeSort(array, left, mid);
			System.out.println("Recursively calling mergesort from " + (mid+1) + " to " + right);
			mergeSort(array, mid+1, right);
			
			/*
			 * Then we call the method that merges the two sorted halves
			 */
			System.out.println("Now we call merge to merge the two sorted subarrays " + "(" + left + "->" + mid + " and " + (mid+1) + 
					"->" + right + ") together into one sorted array");
			merge(array, left, mid, right);
		}else {
			System.out.println("Base case reached, the array is all broken up.");
		}
	}
	/**
	 * 
	 * @return new temp array based on given indices
	 */
	private Comparable[] makeTempArray(int size, int startIndex, Comparable[] originalArray) {
		System.out.println("Making a temporary array starting from " + startIndex + " of size " + size + ":");
		Comparable[] temp = new Comparable[size];
		 for (int i = 0; i < size; i++) {
	            temp[i] = originalArray[startIndex + i];
	        }
		 printOutSubarray(startIndex, (startIndex + size), originalArray);
		 return temp;
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
	
	private void merge(Comparable[] originalArray, int left, int middle, int right) {
        // Sizes of the two subarrays to be merged
        int temp1Size = middle - left + 1;
        int temp2Size = right - middle;
        
        //Make copy the stuff in specified indices into new temp arrays
        Comparable[] temp1Array = makeTempArray(temp1Size, left, originalArray);
        Comparable[] temp2Array = makeTempArray(temp2Size, middle + 1, originalArray);

        // Merge the temporary arrays
        mergeTempArrays(originalArray, temp1Array, temp2Array, left);
    }
	private void mergeTempArrays(Comparable[] originalArray, Comparable[] temp1Array, 
			Comparable[] temp2Array, int left) {
		System.out.println("Merging two sorted subarrays into one sorted subarray:");
		 // Initial indices of the subarrays
        int i = 0, j = 0, temp1Size = temp1Array.length, temp2Size = temp2Array.length, startIndex = left;

        while (i < temp1Size && j < temp2Size) {
            if (temp1Array[i].compareTo(temp2Array[j]) <= 0) {
            	System.out.println("Temp1's element, {" + temp1Array[i].toString() + 
            			"} <= temp2's " + temp2Array[j].toString() + "}");
            	sortedArray[left] = temp1Array[i];
                i++;
            } else {
            	System.out.println("Temp1's element, {" + temp1Array[i].toString() + 
            			"} > temp2's " + temp2Array[j].toString() + "}");
            	sortedArray[left] = temp2Array[j];
                j++;
            }
            left++;
            printOutSubarray(startIndex, left, sortedArray);
        }

        // Copy rest of elements from temp1Array into the big array if there are any
        while (i < temp1Size) {
        	System.out.println("Copying rest of temp1 array's elements into the merged list");
        	sortedArray[left] = temp1Array[i];
            i++;
            left++;
            printOutSubarray(startIndex, left, sortedArray);
        }

     // Copy rest of elements from temp1Array into the big array if there are any
        while (j < temp2Size) {
        	System.out.println("Copying rest of temp2 array's elements into the merged list");
        	sortedArray[left] = temp2Array[j];
            j++;
            left++;
            printOutSubarray(startIndex, left, sortedArray);
        }
	}
}
