package application.testing;

import application.OperationGroup;
import application.Operand;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationGroupTest extends OperationGroup{

    private OperationGroup operationGroup;

    @BeforeEach
    public void setUp() {
        operationGroup = new OperationGroup();
    }
    
    void putStuffIntoGroup() {
    	operationGroup.addNumber(5.0);
        operationGroup.addNumber(10.0);

        // Define an addition operation
        BiFunction<Double, Double, Double> addition = (num1, num2) -> num1 + num2;

        // Add the addition operation to the group
        operationGroup.add(addition, 0, 1, Operand.ADD);
    }
    
    @Test
    void testAddAndCalculate() {
    	putStuffIntoGroup();

        // Calculate the result
        double result = operationGroup.calculate();

        assertEquals(15.0, result);
    }
    
    @Test
    void testIsEmpty() {
    	assertTrue(operationGroup.isEmpty());
    	putStuffIntoGroup();

        assertFalse(operationGroup.isEmpty());
    }
    
    @Test
    void testClear() {
    	putStuffIntoGroup();
        assertFalse(operationGroup.isEmpty());
        operationGroup.clear();
        assertTrue(operationGroup.isEmpty());
    }

    
}

