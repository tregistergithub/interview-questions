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

public class Calculator {

	private static final int REQUIRED_ARGUMENT_COUNT = 2;
	private static final Logger log = LogManager.getLogger();
	private static final int SINGLE_FINAL_RESULT = 1;
	private final Memory memory;

	public Calculator(Memory memory) {
		this.memory = memory;
	}

	public void evalStmtStr(String strStmt) throws StatementException {
		StatementParser parsedStmt = new StatementParser(strStmt);
		Queue<Token> postfixTokenQueue = parsedStmt.generatePostfixTokenQueue();
		Token resultToken = evaluatePostfixExpression(postfixTokenQueue);
		resultToken = handleMathAssignmentOperator(parsedStmt.getVariableName(), parsedStmt.getAssignmentTokenType(),
				resultToken);
		log.debug("===================> {} = {}", parsedStmt.getVariableName(), resultToken);
		memory.set(parsedStmt.getVariableName(), getValueAndEval(resultToken));
	}

	private Token evaluatePostfixExpression(Queue<Token> postfixTokenQueue) throws StatementException {
		Stack<Token> evaluationStack = new Stack<>();

		while (!postfixTokenQueue.isEmpty()) {
			Token currentToken = postfixTokenQueue.remove();

			if (currentToken.getTokenType().isOperand()) {
				evaluationStack.add(currentToken);

			} else if (currentToken.getTokenType().isOperator()) {

				if (evaluationStack.size() < REQUIRED_ARGUMENT_COUNT) {
					throw new StatementException("Missin operand(s) for operator " + currentToken);
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
			throw new StatementException("Missing operator in mathematical expression");
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
			throw new StatementException("Unsupported operator " + tokenType);
		}
	}

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
				throw new StatementException("Undifined variable " + variableName);
			}

		} else {
			throw new StatementException("Unsupported operand token " + token);
		}
	}

	private Integer evalUnary(Token token, String variableName, Integer value) {
		switch (token.getEvaluationAction()) {
		case PRE_INCREMENT:
			++value;
			memory.set(variableName, value);
			break;

		case PRE_DECREMENT:
			--value;
			memory.set(variableName, value);
			break;

		case POST_INCREMENT:
			memory.set(variableName, value + 1);
			break;

		case POST_DECREMENT:
			memory.set(variableName, value - 1);
			break;

		default:
			break;
		}
		return value;
	}

	private Token handleMathAssignmentOperator(String variableName, Token assignmentOperation, Token expressionResult)
			throws StatementException {
		Queue<Token> postfixTokenQueue = new LinkedList<>();

		if (assignmentOperation != null) {
			postfixTokenQueue.add(new Token(variableName, TokenType.VARIABLE));
			postfixTokenQueue.add(expressionResult);
			postfixTokenQueue.add(assignmentOperation);
			expressionResult = evaluatePostfixExpression(postfixTokenQueue);
		}

		return expressionResult;
	}

}