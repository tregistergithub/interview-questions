package com.tbla.tbz.calc.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TokenTypeTest {

	@Test
	public void testTokenTypes() {
		
		// DIV_OPERATOR("/", 8, true, true), 
		assertEquals(TokenType.DIV_OPERATOR.asString(), "/");
		assertEquals(TokenType.DIV_OPERATOR.getPrecendence(), 8);
		assertEquals(TokenType.DIV_OPERATOR.isOperator(), true);
		assertEquals(TokenType.DIV_OPERATOR.isOperand(), false);
		assertEquals(TokenType.DIV_OPERATOR.isBinaryOperator(), true);

		// MUL_OPERATOR("*", 8, true, true), 
		assertEquals(TokenType.MUL_OPERATOR.asString(), "*");
		assertEquals(TokenType.MUL_OPERATOR.getPrecendence(), 8);
		assertEquals(TokenType.MUL_OPERATOR.isOperator(), true);
		assertEquals(TokenType.MUL_OPERATOR.isOperand(), false);
		assertEquals(TokenType.MUL_OPERATOR.isBinaryOperator(), true);

		// PLUS_OPERATOR("+", 6, true, true),
		assertEquals(TokenType.PLUS_OPERATOR.asString(), "+");
		assertEquals(TokenType.PLUS_OPERATOR.getPrecendence(), 6);
		assertEquals(TokenType.PLUS_OPERATOR.isOperator(), true);
		assertEquals(TokenType.PLUS_OPERATOR.isOperand(), false);
		assertEquals(TokenType.PLUS_OPERATOR.isBinaryOperator(), true);

		// MINUS_OPERATOR("-", 6, true, true),
		assertEquals(TokenType.MINUS_OPERATOR.asString(), "-");
		assertEquals(TokenType.MINUS_OPERATOR.getPrecendence(), 6);
		assertEquals(TokenType.MINUS_OPERATOR.isOperator(), true);
		assertEquals(TokenType.MINUS_OPERATOR.isOperand(), false);
		assertEquals(TokenType.MINUS_OPERATOR.isBinaryOperator(), true);
		
		// LEFT_PAREN("(", 2, true, false),
		assertEquals(TokenType.LEFT_PAREN.asString(), "(");
		assertEquals(TokenType.LEFT_PAREN.getPrecendence(), 2);
		assertEquals(TokenType.LEFT_PAREN.isOperator(), true);
		assertEquals(TokenType.LEFT_PAREN.isOperand(), false);
		assertEquals(TokenType.LEFT_PAREN.isBinaryOperator(), false);
		
		// RIGHT_PAREN(")", 2, true, false),
		assertEquals(TokenType.RIGHT_PAREN.asString(), ")");
		assertEquals(TokenType.RIGHT_PAREN.getPrecendence(), 2);
		assertEquals(TokenType.RIGHT_PAREN.isOperator(), true);
		assertEquals(TokenType.RIGHT_PAREN.isOperand(), false);
		assertEquals(TokenType.RIGHT_PAREN.isBinaryOperator(), false);
		
		// DECREMENT_OPERATOR("--", 1, true, false),
		assertEquals(TokenType.DECREMENT_OPERATOR.asString(), "--");
		assertEquals(TokenType.DECREMENT_OPERATOR.getPrecendence(), 1);
		assertEquals(TokenType.DECREMENT_OPERATOR.isOperator(), true);
		assertEquals(TokenType.DECREMENT_OPERATOR.isOperand(), false);
		assertEquals(TokenType.DECREMENT_OPERATOR.isBinaryOperator(), false);
		
		// INCREMENT_OPERATOR("++", 1, true, false),
		assertEquals(TokenType.INCREMENT_OPERATOR.asString(), "++");
		assertEquals(TokenType.INCREMENT_OPERATOR.getPrecendence(), 1);
		assertEquals(TokenType.INCREMENT_OPERATOR.isOperator(), true);
		assertEquals(TokenType.INCREMENT_OPERATOR.isOperand(), false);
		assertEquals(TokenType.INCREMENT_OPERATOR.isBinaryOperator(), false);
		
		// INTEGER("", 0, false, false),
		assertTrue(TokenType.INTEGER.asString().isEmpty());
		assertEquals(TokenType.INTEGER.getPrecendence(), 0);
		assertEquals(TokenType.INTEGER.isOperator(), false);
		assertEquals(TokenType.INTEGER.isOperand(), true);
		assertEquals(TokenType.INTEGER.isBinaryOperator(), false);
		
		// VARIABLE("", 0, false, false),
		assertTrue(TokenType.VARIABLE.asString().isEmpty());
		assertEquals(TokenType.VARIABLE.getPrecendence(), 0);
		assertEquals(TokenType.VARIABLE.isOperator(), false);
		assertEquals(TokenType.VARIABLE.isOperand(), true);
		assertEquals(TokenType.VARIABLE.isBinaryOperator(), false);
		
		// VARIABLE_WITH_EVAL_ACTION("", 0, false, false);
		assertTrue(TokenType.VARIABLE_WITH_EVAL_ACTION.asString().isEmpty());
		assertEquals(TokenType.VARIABLE_WITH_EVAL_ACTION.getPrecendence(), 0);
		assertEquals(TokenType.VARIABLE_WITH_EVAL_ACTION.isOperator(), false);
		assertEquals(TokenType.VARIABLE_WITH_EVAL_ACTION.isOperand(), true);
		assertEquals(TokenType.VARIABLE_WITH_EVAL_ACTION.isBinaryOperator(), false);
		
		assertEquals(TokenType.values().length, 11);
	}

}
