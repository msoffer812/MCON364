package application;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

/**
 * Holds the PriorityQueue of operations, with numbers corresponding, and calculates result
 * @author mbrso
 *
 */
public class OperationGroup {
	private List<NumberWrapper> nums;						/* The numbers involved in the calculation*/
	private PriorityQueue<ComparableFunction> operations; 	/* The actual operations */
	
	private void printNums() {
		for(NumberWrapper num: nums) {
			System.out.print(num.value + " ");
		}
		System.out.println();
	}
	public OperationGroup() {
		this.nums = new ArrayList<>();
		this.operations = new PriorityQueue<>();
	}
	
	/**
	 * Clears the operations from the list
	 */
	public void clear() {
		this.nums = new ArrayList<>();
		this.operations = new PriorityQueue<>();
	}
	
	/**
	 * Add a new operation
	 */
	public void add(BiFunction<Double, Double, Double> function, int ind1, int ind2, Operand operand) {
		/*
		 * The function that actually calculates the value, then assigns each spot that number
		 */
		BiFunction<Integer, Integer, Double> newAction = (index1, index2) -> {
			NumberWrapper num1 = this.nums.get(index1);
			NumberWrapper num2 = this.nums.get(index2);
			num1.value = function.apply(num1.value, num2.value);
			num2.value = num1.value;
			this.nums.set(index2, num1);
			return num1.value;	
		};
		ComparableFunction newOperation = new ComparableFunction(operand, newAction, ind1, ind2, operations.size());
		this.operations.add(newOperation);
	}
	
	public void addNumber(double num) {
		this.nums.add(new NumberWrapper(num));
	}
	/**
	 * 
	 * @return if there are no operations stored
	 */
	public boolean isEmpty() {
		return operations.isEmpty();
	}
	/**
	 * Calculate all the operations
	 * @return calculated value
	 */
	public double calculate() {
		ComparableFunction currOperation;
		double currAns = 0;
		while(!operations.isEmpty()) {
			printNums();
			currOperation = operations.poll(); 
			currAns = currOperation.result();
			printNums();
		}
		return currAns;
	}
}
