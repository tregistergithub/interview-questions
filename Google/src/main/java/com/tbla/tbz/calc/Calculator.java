package com.tbla.tbz.calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Stream;

import com.tbla.tbz.calc.Tokenizer.Token;

import sun.security.util.Length;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Question 1
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
	private static final int ASSIGNMENT_OPERATOR_NOT_FOUND = -1;
	private static final int VAR_POSITION = 0;
	private static final int EXPRESSION_POSITION = 1;
	private static Memory memory;
	private static boolean isVerbose = true;

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

		int j = i++ + i++ + i++ + i++;
		int m = 3;
		int k = ++m + ++m + ++m + ++m;
		Stream.of(// "i = 2 + 3", "j=30 - 33", "k=2+4/2+2/2", "n=i+j",
					// "foo=(2+4)/2+2/2", "puki=1*2+3",
					// "tiki=2*((2*puki*(foo-1))-2)-3", "sami=puki",
				// "i=0",
				// "i-=3",
				// "i*=3",
				"i = 0", "j = ++i", "x = i++ + 5", "y = 5 + 3", "i += y").forEach(p -> calculateLine(p));
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
					mathExpression = inputLine.substring(indexOfAssignmentOperator + assignmentOperator.length());
				} else {
					throw new ParserException("Missing mathematical expression in the input line " + inputLine);
				}
				;
				break;

			}
		}

		if (isVerbose)
			System.out.println(mathExpression);

		ParsedStatment parsedStatement = new ParsedStatment(memory);
		// List<String> tokenList = tokenize(mathExpression);

		List<Token> infixTokenList = new Tokenizer(mathExpression).getTokens();
		if (isVerbose)
			System.out.println(infixTokenList);
		Queue<Token> postfixTokenList = infixToPostfix(infixTokenList);
		if (isVerbose)
			System.out.println(postfixTokenList);
		Token result = evaluateExpression(postfixTokenList);
		if (isMathAssignmentOperator(selectedAssignmentOperator)) {
			postfixTokenList.add(Token.buildVariableToken(variableName));
			postfixTokenList.add(result);
			postfixTokenList.add(Token.buildBinaryOperatorToken(selectedAssignmentOperator.charAt(0)));
			result = evaluateExpression(postfixTokenList);
		}

		if (isVerbose)
			System.out.println(result);
		parsedStatement.setVariableName(variableName);
		memory.put(variableName, intVal(result));
		System.out.println("===================> " + variableName + " = " + result);
		// System.out.println(memory);
		return parsedStatement;
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

	private static Queue<Token> infixToPostfix(List<Token> tokenList) {
		Queue<Token> outputQueue = new LinkedList<>();
		Stack<Token> operatorStack = new Stack<>();

		for (int i = 0; i < tokenList.size(); i++) {
			Token currentToken = tokenList.get(i);
			if (currentToken.isOperand()) {
				outputQueue.add(currentToken);
				
			} else if (currentToken.isOperator()) {
				while (!operatorStack.isEmpty() && !operatorStack.peek().isParenthesis()
						&& currentToken.getPrecedence() <= operatorStack.peek().getPrecedence()) {
					outputQueue.add(operatorStack.pop());
				}

				operatorStack.push(currentToken);

			} else if (currentToken.isLeftParen()) {
				operatorStack.push(currentToken);

			} else if (currentToken.isRightParen()) {
				while (!operatorStack.peek().isLeftParen()) {
					if (operatorStack.isEmpty()) {
						throw new ParserException("Parenthesis balancing error");
					}

					outputQueue.add(operatorStack.pop());
				}
				operatorStack.pop();
			}
		}

		while (!operatorStack.isEmpty()) {
			if (!operatorStack.isEmpty() && !operatorStack.peek().isParenthesis())
				outputQueue.add(operatorStack.pop());
			else
				throw new ParserException("Parenthesis balancing error");
		}

		return outputQueue;
	}

	public static Token evaluateExpression(Queue<Token> postfixTokenQueue) {
		Stack<Token> evalStack = new Stack<>();
		while (!postfixTokenQueue.isEmpty()) {
			Token currentToken = postfixTokenQueue.remove();

			if (currentToken.isOperand()) {
				evalStack.add(currentToken);

			} else if (currentToken.isOperator()) {
				Token operand1 = evalStack.pop();
				Token operand2 = evalStack.pop();

				Token result = performOperation(intVal(operand1), intVal(operand2), currentToken);
				evalStack.push(result);
			}
		}
		return evalStack.pop();
	}

	private static int intVal(Token token) {

		if (token.isNumber()) {
			return Integer.valueOf(token.strToken);
		} else if (token.isVariable()) {
			String variableName = token.strToken;
			Integer value = memory.get(variableName);
			if (value == null) {
				throw new ParserException("Undifined variable " + variableName);
			} else {
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
