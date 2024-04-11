package application;
	/**
	 * Sample Skeleton for 'Main.fxml' Controller Class
	 */

	import java.net.URL;
	import java.util.ResourceBundle;
import java.util.function.BiFunction;

import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.TextField;

public class CalculatorController {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="button0"
    private Button button0; // Value injected by FXMLLoader

    @FXML // fx:id="button1"
    private Button button1; // Value injected by FXMLLoader

    @FXML // fx:id="button2"
    private Button button2; // Value injected by FXMLLoader

    @FXML // fx:id="button3"
    private Button button3; // Value injected by FXMLLoader

    @FXML // fx:id="button4"
    private Button button4; // Value injected by FXMLLoader

    @FXML // fx:id="button5"
    private Button button5; // Value injected by FXMLLoader

    @FXML // fx:id="button6"
    private Button button6; // Value injected by FXMLLoader

    @FXML // fx:id="button7"
    private Button button7; // Value injected by FXMLLoader

    @FXML // fx:id="button8"
    private Button button8; // Value injected by FXMLLoader

    @FXML // fx:id="button9"
    private Button button9; // Value injected by FXMLLoader

    @FXML // fx:id="buttonC"
    private Button buttonC; // Value injected by FXMLLoader

    @FXML // fx:id="buttonDivide"
    private Button buttonDivide; // Value injected by FXMLLoader

    @FXML // fx:id="buttonEquals"
    private Button buttonEquals; // Value injected by FXMLLoader

    @FXML // fx:id="buttonMinus"
    private Button buttonMinus; // Value injected by FXMLLoader

    @FXML // fx:id="buttonMultiply"
    private Button buttonMultiply; // Value injected by FXMLLoader

    @FXML // fx:id="buttonPlus"
    private Button buttonPlus; // Value injected by FXMLLoader

    @FXML // fx:id="calculatorDisplay"
    private TextField calculatorDisplay; // Value injected by FXMLLoader
    protected boolean canAddOperand;
    protected boolean shouldClearScreen;
    protected boolean shouldIncludePrevFunction;
    protected OperationGroup operations;
    protected int indexOfPrev;
    protected String prevOperand;
    protected NumberWrapper prevNumber; //Holds the reference to the previous number
    protected StringBuilder newNum;
    
	
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert button0 != null : "fx:id=\"button0\" was not injected: check your FXML file 'Main.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'Main.fxml'.";
        assert button2 != null : "fx:id=\"button2\" was not injected: check your FXML file 'Main.fxml'.";
        assert button3 != null : "fx:id=\"button3\" was not injected: check your FXML file 'Main.fxml'.";
        assert button4 != null : "fx:id=\"button4\" was not injected: check your FXML file 'Main.fxml'.";
        assert button5 != null : "fx:id=\"button5\" was not injected: check your FXML file 'Main.fxml'.";
        assert button6 != null : "fx:id=\"button6\" was not injected: check your FXML file 'Main.fxml'.";
        assert button7 != null : "fx:id=\"button7\" was not injected: check your FXML file 'Main.fxml'.";
        assert button8 != null : "fx:id=\"button8\" was not injected: check your FXML file 'Main.fxml'.";
        assert button9 != null : "fx:id=\"button9\" was not injected: check your FXML file 'Main.fxml'.";
        assert buttonC != null : "fx:id=\"buttonC\" was not injected: check your FXML file 'Main.fxml'.";
        assert buttonDivide != null : "fx:id=\"buttonDivide\" was not injected: check your FXML file 'Main.fxml'.";
        assert buttonEquals != null : "fx:id=\"buttonEquals\" was not injected: check your FXML file 'Main.fxml'.";
        assert buttonMinus != null : "fx:id=\"buttonMinus\" was not injected: check your FXML file 'Main.fxml'.";
        assert buttonMultiply != null : "fx:id=\"buttonMultiply\" was not injected: check your FXML file 'Main.fxml'.";
        assert buttonPlus != null : "fx:id=\"buttonPlus\" was not injected: check your FXML file 'Main.fxml'.";
        assert calculatorDisplay != null : "fx:id=\"calculatorDisplay\" was not injected: check your FXML file 'Main.fxml'.";
		reset();
		shouldClearScreen = false;
    } 
    
    @FXML
    protected void handleButtonClick(ActionEvent event) {
		/*
		 * Specifically for when you want to clear the screen after a result
		 * has been posted. and you want to start a new equation - clear the screen then
		 * set the variable to false
		 */
		
		/*
		 * Get the button type it was
		 */
		String button = ((Button) event.getSource()).getText();
		if(shouldClearScreen) {
			reset();
			shouldClearScreen = false;
		}
		switch(button){
		case "=":
			handleEqualsSignEntering();
			break;
		case "C":
			reset();
			break;
		case "+":
		case "-":
		case "x":
		case "/":
			/*
			 * We don't want to add two operands together - will make errors. So only add an
			 * operand if we're allowed to
			 */
			if(canAddOperand) {
				handleNewOperand(button);
				concatenateText(button);
			}
			break;
		default:
			canAddOperand = true;
			newNum.append(button);
			concatenateText(button);
			break;
		}
	}
	/**
	 * Go through the list of operations and calculate a final answer
	 * @return final calculation
	 */
    protected double calculateAnswer() {
		return operations.calculate();
	}
	
	/**
	 * Reset everything to normal
	 */
	protected void reset() {
		operations = new OperationGroup();
		indexOfPrev = 0;
		shouldIncludePrevFunction = false;
		canAddOperand = false;
		prevOperand = "+";
		prevNumber = new NumberWrapper(0);
		newNum = new StringBuilder();
		calculatorDisplay.setText("");
	}
	
	protected void handleEqualsSignEntering() {
		String finalAns = "ERROR";
		if(canAddOperand) {
			/*
			 * first we encapsulate the most recent operation
			 */
			handleNewOperand("=");
			/*
			 * then we calculate the answer
			 */
			double ans = calculateAnswer();
			finalAns = String.valueOf(ans);
		}
		/*
		 * We set shouldClearScreen to true so then next entered button will wipe the result off
		 */
		shouldClearScreen = true; 
		/*
		 * We display the final answer
		 */
		this.calculatorDisplay.setText(finalAns);
	}
	/**
	 * Handles what happens if someone enters a new operand - so +, - etc
	 * Basically parses out the previous number set, then creates a new operation based on the 2 previous
	 * numbers and operands and adds to the priorityqueue of operations to do at the end
	 * @param newOperand
	 */
	protected void handleNewOperand(String newOperand) {
		canAddOperand = false;
		double newNum = getValueOfNewNumAndReset();
		this.operations.addNumber(newNum);
		BiFunction<Double, Double, Double> operation = null;
		Operand operand = null;
		if(shouldIncludePrevFunction) {
			switch(prevOperand) {
			case "+":
				operation = (num1, num2) -> {
					return num1 + num2;
				};
				operand = Operand.ADD;
				break;
			case "-":
				operation = (num1, num2) -> {
					return num1 - num2;
				};
				operand = Operand.SUBTRACT;
				break;
			case "x":
				operation = (num1, num2) -> {
					return num1 * num2;
				};
				operand = Operand.MULTIPLY;
				break;
			case "/":
				operation = (num1, num2) -> {
					return num1 / num2;
				};
				operand = Operand.DIVIDE;
				break;
			}
			operations.add(operation, indexOfPrev, indexOfPrev + 1, operand);
			indexOfPrev++;
		}else {
			shouldIncludePrevFunction = true;
		}
		prevOperand = newOperand;
	}
	/**
	 * Gets the value of the new number - was a String - then resets it to an empty string and returns
	 * @return
	 */
	protected double getValueOfNewNumAndReset() {
		double num = 0;

		System.out.println(newNum);
		try {
			num = Double.parseDouble(newNum.toString());
		}catch(NumberFormatException e) {
			System.out.println("Not a number");
			reset();
		}
		System.out.println(num);
		newNum = new StringBuilder();
		return num;
	}
	/**
	 * Concats text to the end of the display so user knows where they are
	 * @param text
	 */
	protected void concatenateText(String text) {
		String newDisplay = calculatorDisplay.getText() + text;
		this.calculatorDisplay.setText(newDisplay);
	}

}


