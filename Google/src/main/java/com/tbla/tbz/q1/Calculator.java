package com.tbla.tbz.q1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Stream;

import com.tbla.tbz.q1.Tokenizer.Token;

/**
 * <b>Question 1</b>
 * <p/>
 * Objective : Implement a text based calculator application. Usage of Rhino,
 * Nashorn and other similar solutions is not allowed.
 * <p/>
 * Input : The input is a series of assignment expressions. The syntax is a
 * subset of Java numeric expressions and operators.
 * <p/>
 * Output : At the end of evaluating the series, the value of each variable is
 * printed out.
 * 
 * @author Tal Bar-Zvi
 *
 */

// TODO handle unary minus
// TODO handle design
public class Calculator {
	private static final int NUMBER_OF_REQUIRED_OPERANDS_FOR_BINARY_OPERATION = 2;
	private static final int ASSIGNMENT_OPERATOR_NOT_FOUND = -1;
	private static final int SINGLE_FINAL_RESULT = 1;
	private static Memory memory;
	private static boolean isVerbose = false;

	public static void main(String[] args) {

		// System.out.println("Please enter assinment statments (enter empty
		// line to finish): ");

		memory = new HashMapMemory();
		// calculateLine(memory, "i=2/3+3*3*(3/2)+k_1d*(2/j)+3");
		// calculateLine(memory, "i= i+++1 ");
		// calculateLine(memory, "i= 2 / + ---i++++ -3 + 3 * 3* ( 3 / 2) + k_1d*
		// (2 /j) +3 ");
		// Stream.of("i = 2 + 3").forEach(p->System.out.println(p));

		int i = 3;

		int j = i+++i+++i+++i++;
		j += i+ +i;
		int m = 3;
		int c = 3+ + + + + +3;
		int k = ++m + ++m + ++m + ++m;
		
		
		i =9;
		j = 3+ i-- - 3 +3;
		i +=9;
		j += i++;
		j += j++;
		Stream.of(// "i = 2 + 3", "j=30 - 33", "k=2+4/2+2/2", "n=i+j",
					// "foo=(2+4)/2+2/2", "puki=1*2+3",
					// "tiki=2*((2*puki*(foo-1))-2)-3", "sami=puki",
				"i =9",
				"j = 3+ i-- - 3 +3",
				 "i +=9",
				 "j += i++",
				 "j += j++"
//				 "x = i+++ 5",
//				 "y = 5 + 3 * 10",
//				 "i += y",
//				// "i-=3",
//				// "i*=3",
//				"i = 3", 
//				"r = i+++i+++i+++i++"
				//"m = 3",
				//"k = ++m + ++m + ++m + ++m"
				// "x = i++ + 5", "y = 5 + 3", "i += y"
				).forEach(p -> calculateLine(p));
		// calculateLine("i = 2 + 3");
		System.out.println(memory);
	}

	private static void doIt() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String inputLine = null;
			Memory memory = new HashMapMemory();
			while (true) {
				inputLine = br.readLine();
				if (!inputLine.isEmpty()) {
					calculateLine(inputLine);
				} else {
					break;
				}
			}
			;

			System.out.println(memory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void calculateLine(String line) {
		System.out.println(line);
		ParsedStatment parsedStmt = parse(line);

		String variableName = parsedStmt.getVariableName();

		parsedStmt.getCalcExpression();
		parsedStmt.evaluateUnaryOperators();
		parsedStmt.dereferenceVariables();
		Integer expressionResult = parsedStmt.evaluate();
		parsedStmt.updatePostfixOperatorVariables();
		// memory.put(variableName, expressionResult);
	}

	private static ParsedStatment parse(String inputLine) {

		String[] ASSINGMENT_OPERATORS = { "+=", "-=", "*=", "/=", "=" };

		String selectedAssignmentOperator = null;
		String variableName = null;
		String mathExpression = null;

		for (String assignmentOperator : ASSINGMENT_OPERATORS) {
			int indexOfAssignmentOperator = inputLine.indexOf(assignmentOperator);
			if (indexOfAssignmentOperator != ASSIGNMENT_OPERATOR_NOT_FOUND) {
				selectedAssignmentOperator = assignmentOperator;
				if (isVerbose) {
					System.out.println(selectedAssignmentOperator);
				}
				if (indexOfAssignmentOperator != ASSIGNMENT_OPERATOR_NOT_FOUND) {
					variableName = inputLine.substring(0, indexOfAssignmentOperator).trim();

				} else {
					throw new ParserException("Missing assignment operator in the input line " + inputLine);
				}
				if (!Token.isVariableName(variableName)) {
					throw new ParserException("Invalid variable name " + variableName);
				}
				if (isVerbose)
					System.out.println(variableName);

				if (indexOfAssignmentOperator + assignmentOperator.length() < inputLine.length()) {
					mathExpression = inputLine.substring(indexOfAssignmentOperator + assignmentOperator.length()).trim();
				} else {
					throw new ParserException("Missing mathematical expression in the input line " + inputLine);
				}
				;
				break;

			}
		}

		if (mathExpression.isEmpty()) {
			throw new ParserException("Missing mathematical expression for variable " + variableName);
		}
		
		if (isVerbose)
			System.out.println(mathExpression);

		ParsedStatment parsedStatement = new ParsedStatment(memory);
		// List<String> tokenList = tokenize(mathExpression);

		List<Token> infixTokenList = new Tokenizer(mathExpression).getTokens();
		if (isVerbose)
			System.out.println(infixTokenList);
		Queue<Token> postfixTokenQueue = Token.infixToPostfix(infixTokenList);

		if (isVerbose)
			System.out.println(postfixTokenQueue);
		Token result = evaluateExpression(postfixTokenQueue);
		
		result = handleMathAssignmentOperator(selectedAssignmentOperator, variableName, postfixTokenQueue, result);

		if (isVerbose)
			System.out.println(result);
		parsedStatement.setVariableName(variableName);
		memory.put(variableName, integerValue(result));
		System.out.println("===================> " + variableName + " = " + result);
		// System.out.println(memory);
		return parsedStatement;
	}

	private static Token handleMathAssignmentOperator(String selectedAssignmentOperator, String variableName,
			Queue<Token> postfixTokenQueue, Token result) {
		if (isMathAssignmentOperator(selectedAssignmentOperator)) {
			postfixTokenQueue.add(Token.buildVariableToken(variableName));
			postfixTokenQueue.add(result);
			postfixTokenQueue.add(Token.buildBinaryOperatorToken(selectedAssignmentOperator.charAt(0)));
			result = evaluateExpression(postfixTokenQueue);
		}
		return result;
	}

	private static String extractVariableName(String inputLine) {
		final int indexOfAssignmentOperator = inputLine.indexOf("=");
		if (indexOfAssignmentOperator != ASSIGNMENT_OPERATOR_NOT_FOUND) {
			return inputLine.substring(0, indexOfAssignmentOperator + 1).trim();

		} else {
			throw new ParserException("Missing assignment operator in the input line " + inputLine);
		}
	}

	private static void handleAssignmentOperators(String inputLine) {

	}

	private static boolean isMathAssignmentOperator(String assignmentOperator) {
		return assignmentOperator.length() == 2;
	}

	// private static String[] divideIntoVariableNameAndMathExpression(String
	// inputLine) {
	// return inputLine.indexOf("=");
	// }

	public static Token evaluateExpression(Queue<Token> postfixTokenQueue) {
		Stack<Token> evalStack = new Stack<>();
		while (!postfixTokenQueue.isEmpty()) {
			Token currentToken = postfixTokenQueue.remove();

			if (currentToken.isOperand()) {
				evalStack.add(currentToken);

			} else if (currentToken.isOperator()) {
				
				if (evalStack.size() < NUMBER_OF_REQUIRED_OPERANDS_FOR_BINARY_OPERATION) {
					throw new RuntimeException("Missin operand(s) for operator " + currentToken.strToken);
				}
				Token operand1 = evalStack.pop();
				Token operand2 = evalStack.pop();

				int intVal2 = integerValue(operand2);
				int intVal1 = integerValue(operand1);
				Token result = performOperation(intVal1, intVal2, currentToken);
				evalStack.push(result);
			}
		}
		if (evalStack.size() != SINGLE_FINAL_RESULT) {
			throw new ParserException("Missing operator in mathematical expression" );
		}
		return evalStack.pop();
	}

	private static int integerValue(Token token) {

		if (token.isNumber()) {
			return Integer.valueOf(token.strToken);
			
		} else if (token.isVariable()) {
			String variableName = token.strToken;
			Integer value = memory.get(variableName);
			if (value == null) {
				throw new ParserException("Undifined variable " + variableName);
			} else {
				if (token.specialInstructions == Tokenizer.PRE_INCREMEMNT) {
					if (isVerbose) System.out.println("Pre INC rementing variable <" + variableName + ">");
					
					value++;
					memory.put(variableName, value);
				}

				if (token.specialInstructions == Tokenizer.PRE_DECREMEMNT) {
					if (isVerbose) System.out.println("Pre DEC rementing variable <" + variableName + ">");
					value++;
					memory.put(variableName, value);
				}
				
				if (token.specialInstructions == Tokenizer.POST_INCREMENT) {
					if (isVerbose) System.out.println("Post INC rementing variable <" + variableName + ">");
					memory.put(variableName, value + 1);
				}

				if (token.specialInstructions == Tokenizer.POST_DECREMENT) {
					if (isVerbose) System.out.println("Post DEC rementing variable <" + variableName + ">");
					memory.put(variableName, value - 1);
				}
				return value;
			}

		} else {
			throw new ParserException("Unsupported operand token " + token);
		}
	}

	private static Token performOperation(Integer operand1, Integer operand2, Token operator) {
		switch (operator.strToken) {
		case "+":
			return buildNumberToken(operand2 + operand1);
		case "-":
			return buildNumberToken(operand2 - operand1);
		case "*":
			return buildNumberToken(operand2 * operand1);
		case "/":
			return buildNumberToken(operand2 / operand1);
		default:
			throw new ParserException("Unsupported operator " + operator);
		}

	}

	private static Token buildNumberToken(int value) {
		return Token.buildNumberToken(value);
	}
}
