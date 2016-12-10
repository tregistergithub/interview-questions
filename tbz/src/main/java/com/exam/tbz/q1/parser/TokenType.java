package com.exam.tbz.q1.parser;

/**
 * All the token type thes  
 *
 */
public enum TokenType {
	
	DIV_OPERATOR("/", 8, true, true), 
	MUL_OPERATOR("*", 8, true, true), 
	PLUS_OPERATOR("+", 6, true, true), 
	MINUS_OPERATOR("-", 6, true, true), 
	LEFT_PAREN("(", 2, true, false), 
	RIGHT_PAREN(")", 2, true, false), 
	DECREMENT_OPERATOR("--", 1, true, false), 
	INCREMENT_OPERATOR("++", 1, true, false), 
	INTEGER("", 0, false, false), 
	VARIABLE("", 0, false, false),
	VARIABLE_WITH_EVAL_ACTION("", 0, false, false);
	
	private final String operatorString;
	private final int precendence;
	private final boolean isOperator;
	private final boolean isBinaryOperator;

	TokenType(String operatorString, int precendence, boolean isOperator, boolean isBinaryOperator) {
        this.operatorString = operatorString;
        this.precendence = precendence;
        this.isOperator = isOperator;
		this.isBinaryOperator = isBinaryOperator; 
    }

	public final String asString() {
		return operatorString;
	}

	public int getPrecendence() {
		return precendence;
	}

	public boolean isOperator() {
		return isOperator;
	}
	
	public boolean isOperand() {
		return isOperator == false;
	}
	
    public boolean isBinaryOperator() {
		return isBinaryOperator;
	}
}