package com.tbla.tbz.calc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tbla.tbz.calc.memory.Memory;
import com.tbla.tbz.calc.parser.StatementException;
import com.tbla.tbz.calc.parser.StatementParser;
import com.tbla.tbz.calc.parser.Token;
import com.tbla.tbz.calc.parser.TokenType;

/**
 * <p/>
 * ******************************************************************
 * <p/>
 *  IMPORTANT: <b>NOT THREAD SAFE</b>
 * <p/>
 * ******************************************************************
 * <p/>
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
 * 
 * ******************************************************************
 * <p/>
 *  IMPORTANT: <b>NOT THREAD SAFE</b>
 * <p/>
 * ******************************************************************
 * <p/>
 *
 * Note 1: Supports integers only (double not supported)
 * <p/>
 * Note 2: Does not support unary minus operator 
 * <p/>
 * Note 3: Does not support excessive plus operators like 1 + + 1 
 * <p/>
 *
 **/
public class Calculator {

	private static final int REQUIRED_ARGUMENT_COUNT = 2;
	private static final Logger log = LogManager.getLogger();
	private static final int SINGLE_FINAL_RESULT = 1;
	private final Memory memory;

	/**
	 * Creates a calculator for evaluation of java-like math expressions and variables
	 * 
	 * Note 1: Supports integers only (double not supported)
	 * <p/>
	 * Note 2: Does not support unary minus operator 
	 * <p/>
	 * Note 3: Does not support excessive plus operators like 1 + + 1 
	 * <p/>
	 * 
	 * @param memory <b>MANDATORY</b> - the memory unit for the calculator
	 */
	public Calculator(Memory memory) {
		if (memory == null) throw new RuntimeException("Memory must be not null");
		this.memory = memory;
	}

	/**
	 * Evaluates a string statement, updates the memory with the results
	 * 
	 * This is the main algorithm flow 
	 * 
	 * @param strStmt <b>MANDATORY</b> the statement to evaluate into memory
	 */
	public void evaluate(String strStmt)  {
		if (strStmt==null) throw new IllegalArgumentException("strStmt must be not null");
		
		try {
			// First divide the variable from the math statement
			StatementParser parsedStmt = new StatementParser(strStmt);

			// Get the math expression in post-fix notation
			Queue<Token> postfixTokenQueue = parsedStmt.generatePostfixTokenQueue();

			// Save the variable value for later in case of math assignment operator (such as +=, -=, *=, /=, etc) 
			Integer savedValue = memory.get(parsedStmt.getVariableName());

			Token resultToken = evaluatePostfixExpression(postfixTokenQueue);

			// In case of math assignment operator (such as +=, -=, *=, /=, etc) - perform the operation and update memory
			resultToken = handleMathAssignmentOperator(parsedStmt.getVariableName(), savedValue,
					parsedStmt.getAssignmentTokenType(), resultToken);
			
			log.debug("===================> {} = {}", parsedStmt.getVariableName(), resultToken);
			
			// Set the result to memory
			memory.set(parsedStmt.getVariableName(), getValueAndEval(resultToken));
			
		} catch (Exception e) {
			log.debug("Exception during evaluation of statement" + strStmt);
			throw e;
		}
	}

	/**
	 * Evaluates the post-fix expression
	 * 
	 * Uses Postfix algorithm
	 * 
	 * See also: Reverse Polish Notation
	 * 
	 * https://en.wikipedia.org/wiki/Reverse_Polish_notation
	 *  
	 */
	private Token evaluatePostfixExpression(Queue<Token> postfixTokenQueue)  {
		Stack<Token> evaluationStack = new Stack<>();

		while (!postfixTokenQueue.isEmpty()) {
			Token currentToken = postfixTokenQueue.remove();

			if (currentToken.getTokenType().isOperand()) {
				evaluationStack.add(currentToken);

			} else if (currentToken.getTokenType().isOperator()) {

				if (evaluationStack.size() < REQUIRED_ARGUMENT_COUNT) {
					throw new StatementException(2,"Missin operand(s) for operator " + currentToken);
				}

				Token operand1 = evaluationStack.pop();
				Token operand2 = evaluationStack.pop();

				int intVal2 = getValueAndEval(operand2);
				int intVal1 = getValueAndEval(operand1);

				Token resultToken = evalBinaryExpression(intVal1, intVal2, currentToken.getTokenType());

				evaluationStack.push(resultToken);
			}
		} 
		if (evaluationStack.size() != SINGLE_FINAL_RESULT) {
			throw new StatementException(12, "Missing operator in mathematical expression");
		}
		return evaluationStack.pop();
	}

	private Token evalBinaryExpression(int int1, int int2, TokenType tokenType) {
		switch (tokenType) {
		case PLUS_OPERATOR:
			return new Token(String.valueOf(int2 + int1), TokenType.INTEGER);

		case MINUS_OPERATOR:
			return new Token(String.valueOf(int2 - int1), TokenType.INTEGER);

		case MUL_OPERATOR:
			return new Token(String.valueOf(int2 * int1), TokenType.INTEGER);

		case DIV_OPERATOR:
			return new Token(String.valueOf(int2 / int1), TokenType.INTEGER);

		default:
			throw new StatementException(1, "Unsupported operator " + tokenType);
		}
	}

	/**
	 * Evaluates an operand to its integer value 
	 * 
	 * For a number - returns its value
	 * For a variable - gets its value from memory
	 * In case a variable has a unary operator assigned (such as ++, --)
	 * Performs the operation and updates memory 
	 *  
	 */
	private int getValueAndEval(Token token) {
		if (token.getTokenType() == TokenType.INTEGER) {
			return Integer.valueOf(token.getTokenStr());

		} else if (token.getTokenType() == TokenType.VARIABLE
				|| token.getTokenType() == TokenType.VARIABLE_WITH_EVAL_ACTION) {

			String variableName = token.getTokenStr();
			Integer value = memory.get(variableName);

			if (value != null) {
				if (token.getTokenType() == TokenType.VARIABLE_WITH_EVAL_ACTION) {
					value = evalUnary(token, variableName, value);
				}

				return value;
			} else {
				throw new StatementException(3, "Undifined variable " + variableName);
			}

		} else {
			throw new StatementException(4, "Unsupported operand token " + token);
		}
	}

	/**
	 * Handles the variable unary operators (such as ++i, i++, --i, i--)
	 * 
	 * Returns the value and updates the variable in memory accordingly
	 * 
	 */
	private Integer evalUnary(Token token, String variableName, Integer value) {
		switch (token.getEvaluationAction()) {
		case PRE_INC:
			memory.set(variableName, ++value);
			break;

		case PRE_DEC:
			memory.set(variableName, --value);
			break;

		case POST_INC:
			memory.set(variableName, value + 1);
			break;

		case POST_DEC:
			memory.set(variableName, value - 1);
			break;

		default:
			throw new IllegalArgumentException("Unsupported evaluation action " + token.getEvaluationAction());
		}
		return value;
	}

	/**
	 * Performs the assignment operator using a post-fix expression  
	 * 
	 */
	private Token handleMathAssignmentOperator(String variableName, Integer savedValue, Token assignmentOperation, Token expressionResult) {
		Queue<Token> postfixTokenQueue = new LinkedList<>();

		if (assignmentOperation != null) {
			if (savedValue == null) {
				throw new StatementException(11, "Undefined variable" + variableName);
			}

			postfixTokenQueue.add(new Token(String.valueOf(savedValue), TokenType.INTEGER));
			postfixTokenQueue.add(expressionResult);
			postfixTokenQueue.add(assignmentOperation);
			expressionResult = evaluatePostfixExpression(postfixTokenQueue);
		}

		return expressionResult;
	}
}