package com.exam.tbz.q1.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.exam.tbz.q1.parser.Token.EvalAction;

public class TokenTest {

	@Test
	public void testNegative_Construction() {
		try {
			new Token(null, null);
			fail("Should not reach here");
		} catch (Exception e) { // Do nothing
		}
		try {
			new Token("a", null);
			fail("Should not reach here");
		} catch (Exception e) { // Do nothing
		}

		try {
			new Token(null, TokenType.DECREMENT_OPERATOR);
			fail("Should not reach here");
		} catch (Exception e) { // Do nothing
		}

		try {
			new Token(null, TokenType.DECREMENT_OPERATOR, EvalAction.POST_DEC);
			fail("Should not reach here");
		} catch (Exception e) { // Do nothing
		}

		try {
			new Token(TokenType.PLUS_OPERATOR.asString(), TokenType.VARIABLE_WITH_EVAL_ACTION, null);
			fail("Should not reach here");
		} catch (Exception e) { // Do nothing
		}
	}

	@Test
	public void testConstruction() {
		String tokenStr = TokenType.PLUS_OPERATOR.asString();
		TokenType type = TokenType.VARIABLE_WITH_EVAL_ACTION;
		EvalAction evalAction = EvalAction.POST_DEC;
		Token token = new Token(tokenStr, type, evalAction);
		assertEquals("Token string", tokenStr, token.getTokenStr());
		assertEquals("Token type", type, token.getTokenType());
		assertEquals("Token eval action", evalAction, token.getEvaluationAction());
	}

}
