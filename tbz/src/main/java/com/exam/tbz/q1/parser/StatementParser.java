package com.exam.tbz.q1.parser;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles the statement parsing, separates the variable and the math expression
 * 
 * In addition handles assignment math operations (such as +=, -=, *=, /= etc.)
 *
 * Uses ExpressionTokenizer to tokenize the math expression
 */
public class StatementParser {

	enum AssignmentOperators {
		PLUS_EQUALS("+=", TokenType.PLUS_OPERATOR),
		MINUS_EQUALS("-=", TokenType.MINUS_OPERATOR),
		MUL_EQUALS("*=", TokenType.MUL_OPERATOR),
		DIV_EQUALS("/=", TokenType.DIV_OPERATOR),
		EQUALS("=", null);
		
		private final TokenType mathOperator;
		private final String strValue;
		
		AssignmentOperators(String strValue, TokenType mathOperator) {
			this.mathOperator = mathOperator;
			this.strValue = strValue;
		}

		public String getStrValue() {
			return strValue;
		}

		public TokenType getMathOperator() {
			return mathOperator;
		}
	}
	
	private static final Logger log = LogManager.getLogger();
	private static final int ASSIGNMENT_OPERATOR_NOT_FOUND = -1;
	
	private String mathExpression;
	private String variableName;
	private Token assignmentOperationToken;
	
	public StatementParser(String stmtStr)  {
		parse(stmtStr);
	}

	@Override
	public String toString() {
		return variableName + " " + (assignmentOperationToken != null ? assignmentOperationToken : "") + "= " + mathExpression;
	}

	private void parse(String stmtStr)  {
		for (AssignmentOperators assignment : AssignmentOperators.values()) {
			int indexOfAssignmentOperator = stmtStr.indexOf(assignment.getStrValue());
			
			if (indexOfAssignmentOperator != ASSIGNMENT_OPERATOR_NOT_FOUND) {
				
				if (assignment.getMathOperator() != null) {
					assignmentOperationToken = new Token(assignment.getMathOperator().asString(), assignment.getMathOperator());
				}

				log.debug("Assignment operator is {}", assignment.getStrValue());
				
				variableName = stmtStr.substring(0, indexOfAssignmentOperator).trim();

				if (!ExpressionTokenizer.isVariableName(variableName)) {
					throw new StatementException(7, "Invalid variable name " + variableName);
				}
				
				if (indexOfAssignmentOperator + assignment.getStrValue().length() < stmtStr.length()) {
					mathExpression = stmtStr.substring(indexOfAssignmentOperator + assignment.getStrValue().length()).trim();
				} else {
					throw new StatementException(10, "Missing mathematical expression in the input line " + stmtStr);
				}
				log.debug("Math expression is {}", mathExpression);
				
				break;
			}
		}

		if (mathExpression == null || mathExpression.isEmpty()) {
			throw new StatementException(8, "Missing mathematical expression for variable ");
		}
		
		log.debug("Variable name is {}", variableName);
	}

	public String getVariableName() {
		return variableName;
	}

	public Queue<Token> generatePostfixTokenQueue()  {
		return new ExpressionTokenizer(mathExpression).getPostfixTokenQueue();
	}

	public Token getAssignmentTokenType() {
		return assignmentOperationToken;
	}
}
