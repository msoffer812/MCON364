package application.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import application.ComparableFunction;
import application.Operand;

class ComparableFunctionTesting {
	
	@Test
	void testAdditionComparedToSubtractionWithAdditionComingFirst() {
		ComparableFunction functionAdd = new ComparableFunction(Operand.ADD, ((x, y) -> (double)x+y), 5, 6, 1);
		ComparableFunction functionSub = new ComparableFunction(Operand.SUBTRACT, ((x, y) -> (double)x-y), 5, 6, 5);
		assertTrue(functionAdd.compareTo(functionSub) < 0);
	}
	
	@Test
	void testAdditionComparedToSubtractionWithSubtractionComingFirst() {
		ComparableFunction functionAdd = new ComparableFunction(Operand.ADD, ((x, y) -> (double)x+y), 5, 6, 5);
		ComparableFunction functionSub = new ComparableFunction(Operand.SUBTRACT, ((x, y) -> (double)x-y), 5, 6, 1);
		assertTrue(functionAdd.compareTo(functionSub) > 0);
	}
	
	@Test
	void testMultiplicationComparedToDivisionWithMultiplicationComingFirst() {
		ComparableFunction functionMult = new ComparableFunction(Operand.MULTIPLY, ((x, y) -> (double)x*y), 5, 6, 1);
		ComparableFunction functionDiv = new ComparableFunction(Operand.DIVIDE, ((x, y) -> (double)x/y), 5, 6, 5);
		assertTrue(functionMult.compareTo(functionDiv) < 0);
	}
	
	@Test
	void testMultiplicationComparedToDivisionWithDivisionComingFirst() {
		ComparableFunction functionMult = new ComparableFunction(Operand.MULTIPLY, ((x, y) -> (double)x*y), 5, 6, 5);
		ComparableFunction functionDiv = new ComparableFunction(Operand.DIVIDE, ((x, y) -> (double)x/y), 5, 6, 1);
		assertTrue(functionMult.compareTo(functionDiv) > 0);
	}
	
	@Test
	void testMultiplicationComparedToAdditionWithAdditionComingFirst() {
		ComparableFunction functionMult = new ComparableFunction(Operand.MULTIPLY, ((x, y) -> (double)x*y), 5, 6, 5);
		ComparableFunction functionAdd = new ComparableFunction(Operand.ADD, ((x, y) -> (double)x+y), 5, 6, 1);
		assertTrue(functionMult.compareTo(functionAdd) < 0);
	}
	
	@Test
	void testMultiplicationComparedToAdditionWithMultiplicationComingFirst() {
		ComparableFunction functionMult = new ComparableFunction(Operand.MULTIPLY, ((x, y) -> (double)x*y), 5, 6, 1);
		ComparableFunction functionAdd = new ComparableFunction(Operand.ADD, ((x, y) -> (double)x+y), 5, 6, 5);
		assertTrue(functionMult.compareTo(functionAdd) < 0);
	}
	
	@Test
	void testDivisionComparedToAdditionWithAdditionComingFirst() {
		ComparableFunction functionDiv = new ComparableFunction(Operand.DIVIDE, ((x, y) -> (double)x*y), 5, 6, 5);
		ComparableFunction functionAdd = new ComparableFunction(Operand.ADD, ((x, y) -> (double)x+y), 5, 6, 1);
		assertTrue(functionDiv.compareTo(functionAdd) < 0);
	}
	
	@Test
	void testDivisionComparedToAdditionWithDivisionComingFirst() {
		ComparableFunction functionDiv = new ComparableFunction(Operand.DIVIDE, ((x, y) -> (double)x*y), 5, 6, 1);
		ComparableFunction functionAdd = new ComparableFunction(Operand.ADD, ((x, y) -> (double)x+y), 5, 6, 5);
		assertTrue(functionDiv.compareTo(functionAdd) < 0);
	}
	
	@Test
	void testMultiplicationComparedToSubtractionWithSubtractionComingFirst() {
		ComparableFunction functionMult = new ComparableFunction(Operand.MULTIPLY, ((x, y) -> (double)x*y), 5, 6, 5);
		ComparableFunction functionSub = new ComparableFunction(Operand.SUBTRACT, ((x, y) -> (double)x-y), 5, 6, 1);
		assertTrue(functionMult.compareTo(functionSub) < 0);
	}
	
	@Test
	void testMultiplicationComparedToSubtractionWithMultiplicationComingFirst() {
		ComparableFunction functionMult = new ComparableFunction(Operand.MULTIPLY, ((x, y) -> (double)x*y), 5, 6, 1);
		ComparableFunction functionSub = new ComparableFunction(Operand.SUBTRACT, ((x, y) -> (double)x-y), 5, 6, 5);
		assertTrue(functionMult.compareTo(functionSub) < 0);
	}
	
	@Test
	void testDivisionComparedToSubtractionWithSubtractionComingFirst() {
		ComparableFunction functionDiv = new ComparableFunction(Operand.DIVIDE, ((x, y) -> (double)x*y), 5, 6, 5);
		ComparableFunction functionSub = new ComparableFunction(Operand.SUBTRACT, ((x, y) -> (double)x-y), 5, 6, 1);
		assertTrue(functionDiv.compareTo(functionSub) < 0);
	}
	
	@Test
	void testDivisionComparedToSubtractionWithDivisionComingFirst() {
		ComparableFunction functionDiv = new ComparableFunction(Operand.DIVIDE, ((x, y) -> (double)x*y), 5, 6, 1);
		ComparableFunction functionSub = new ComparableFunction(Operand.SUBTRACT, ((x, y) -> (double)x-y), 5, 6, 5);
		assertTrue(functionDiv.compareTo(functionSub) < 0);
	}

}
