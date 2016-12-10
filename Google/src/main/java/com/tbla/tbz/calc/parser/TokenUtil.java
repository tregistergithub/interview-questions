package com.tbla.tbz.calc.parser;

import com.tbla.tbz.calc.parser.Token.EvalAction;

public class TokenUtil {

	public static boolean isIncDecOperator(Token currentToken) {
		return currentToken.getTokenType() == TokenType.DECREMENT_OPERATOR
				|| currentToken.getTokenType() == TokenType.INCREMENT_OPERATOR;
	}

	public static boolean isVariable(Token token) {
		return token.getTokenType() == TokenType.VARIABLE;
	}

	public static boolean isParenthesis(Token token) {
		return isLeftParen(token) || isRightParen(token);
	}

	public static boolean isLeftParen(Token token) {
		return token.getTokenType() == TokenType.LEFT_PAREN;
	}

	public static boolean isRightParen(Token token) {
		return token.getTokenType() == TokenType.RIGHT_PAREN;
	}

	public static EvalAction getPostEvalAction(TokenType tokenType) {
		return getEvaluationAction(tokenType, EvalAction.POST_INCREMENT, EvalAction.POST_DECREMENT);
	}

	public static EvalAction getPreEvalAction(TokenType tokenType) {
		return getEvaluationAction(tokenType, EvalAction.PRE_INCREMENT, EvalAction.PRE_DECREMENT);
	}

	private static EvalAction getEvaluationAction(TokenType tokenType, EvalAction incAction, EvalAction decAction) {
		switch (tokenType) {
		case INCREMENT_OPERATOR:
			return incAction;

		case DECREMENT_OPERATOR:
			return decAction;

		default:
			throw new IllegalArgumentException("Unsupported token type " + tokenType);
		}
	}
}
