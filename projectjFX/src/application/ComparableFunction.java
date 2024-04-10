package application;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.*;
/**
 * Can compare two functions to see which one is a higher - order
 * @author mbrso
 *
 */
public class ComparableFunction implements Comparable<ComparableFunction>{
	private Operand operand;
	private BiFunction<Integer, Integer, Double> function;
	private int index1;
	private int index2;
	private int orderAdded;
	private static OperandValueIndices mapOfValues = new OperandValueIndices();
	
	public ComparableFunction(Operand o, BiFunction<Integer, Integer, Double> s, int index1, int index2, int added) {
		this.operand = o;
		this.function = s;
		this.index1 = index1;
		this.index2 = index2;
		this.orderAdded = added;
	}
	
	@Override
	public int compareTo(ComparableFunction c) {
		int myValue = mapOfValues.getOperandValue(operand);
		int otherValue = mapOfValues.getOperandValue(c.operand());
		if(myValue == otherValue) {
			return orderAdded - c.orderAdded();
		}
		return myValue - otherValue;
	}
	
	public double result() {
		return this.function.apply(index1, index2);
	}
	public int orderAdded() {
		return this.orderAdded;
	}
	public Operand operand() {
		return this.operand;
	}
}
