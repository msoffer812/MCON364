package application;

/**
 * Just a static class that holds the values of each operation for comparison
 */
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class OperandValueIndices {
	private static Map<Operand, Integer> mapOfValues;
	public OperandValueIndices() {
		this.mapOfValues = new HashMap();
		initializeMap();
	}
	private void initializeMap() {
		mapOfValues.put(Operand.ADD, 3);
		mapOfValues.put(Operand.SUBTRACT, 3);
		mapOfValues.put(Operand.MULTIPLY, 2);
		mapOfValues.put(Operand.DIVIDE, 2);
		mapOfValues.put(Operand.POWER, 1);
	}
	
	public int getOperandValue(Operand o) {
		if(mapOfValues.containsKey(o)) {
			return mapOfValues.get(o);
		}else{
			throw new InputMismatchException("No such operand exists");
		}
	}
}
