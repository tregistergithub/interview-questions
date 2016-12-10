package com.tbla.tbz.calc.parser;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tbla.tbz.q1.OldParserException;

public class StatementParser {

	enum AssignmentOperators {
		PLUS_EQUALS("+=", TokenType.PLUS_OPERATOR),
		MINUS_EQUALS("-=", TokenType.MINUS_OPERATOR),
		MUL_EQUALS("*=", TokenType.MUL_OPERATOR),
		DIV_EQUALS("/=", TokenType.DIV_OPERATOR),
		EQUALS("=", null);
		
		private final TokenType tokenType;
		private final String strValue;
		
		AssignmentOperators(String strValue, TokenType tokenType) {
			this.tokenType = tokenType;
			this.strValue = strValue;
		}

		public String getStrValue() {
			return strValue;
		}

		public TokenType getTokenType() {
			return tokenType;
		}
	}
	
	private static final Logger log = LogManager.getLogger();
	private static final int ASSIGNMENT_OPERATOR_NOT_FOUND = -1;
	
	private String mathExpression;
	private String variableName;
	private Token assignmentOperationToken;
	
	public StatementParser(String stmtStr) throws StatementException {
		parse(stmtStr);
	}

	@Override
	public String toString() {
		return variableName + " " + (assignmentOperationToken != null ? assignmentOperationToken : "") + "= " + mathExpression;
	}

	private void parse(String stmtStr) throws StatementException {
		for (AssignmentOperators assignment : AssignmentOperators.values()) {
			int indexOfAssignmentOperator = stmtStr.indexOf(assignment.getStrValue());
			
			if (indexOfAssignmentOperator != ASSIGNMENT_OPERATOR_NOT_FOUND) {
				
				if (assignment.getTokenType() != null) {
					assignmentOperationToken = new Token(assignment.getTokenType().getOperatorString(), assignment.getTokenType());
				}

				log.debug("Assignment operator is {}", assignment.getStrValue());
				
				if (indexOfAssignmentOperator != ASSIGNMENT_OPERATOR_NOT_FOUND) {
					variableName = stmtStr.substring(0, indexOfAssignmentOperator).trim();

				} else {
					throw new StatementException("Missing assignment operator in the input line " + stmtStr);
				}
				
				if (!ExpressionTokenizer.isVariableName(variableName)) {
					throw new StatementException("Invalid variable name " + variableName);
				}
				
				log.debug("Variable name is {}", variableName);

				if (indexOfAssignmentOperator + assignment.getStrValue().length() < stmtStr.length()) {
					mathExpression = stmtStr.substring(indexOfAssignmentOperator + assignment.getStrValue().length()).trim();
				} else {
					throw new OldParserException("Missing mathematical expression in the input line " + stmtStr);
				}
				log.debug("Math expression is {}", mathExpression);
				
				break;
			}
		}

		if (mathExpression.isEmpty()) {
			throw new StatementException("Missing mathematical expression for variable " + variableName);
		}
		
		log.debug(mathExpression);
	}

	public String getVariableName() {
		return variableName;
	}

	public Queue<Token> generatePostfixTokenQueue() throws StatementException {
		return new ExpressionTokenizer(mathExpression).getPostfixTokenQueue();
	}

	public Token getAssignmentTokenType() {
		return assignmentOperationToken;
	}
}
