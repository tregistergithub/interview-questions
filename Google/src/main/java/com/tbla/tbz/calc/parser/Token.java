package com.tbla.tbz.calc.parser;

/**
 * Represents a token in the math expression
 * 
 * Supports variable unary operations (such as ++i, i++, --i, i--, etc.)
 *
 */
public class Token {

	public enum EvalAction {
		PRE_INC, PRE_DEC, POST_DEC, POST_INC;
	}

	private final String tokenStr;
	private final TokenType tokenType;
	private final EvalAction evaluationAction;

	public Token(String tokenStr, TokenType tokenType) {
		this(tokenStr, tokenType, null);
	}

	@Override
	public String toString() {
		return tokenStr + (evaluationAction != null ? "(" + evaluationAction + ")" : "");
	}

	public Token(String tokenStr, TokenType tokenType, EvalAction evaluationAction) {
		if (tokenStr == null || tokenStr.trim().isEmpty()) {
			throw new IllegalArgumentException("The token string must be non-null and non-empty");
		}

		if (tokenType == null) {
			throw new IllegalArgumentException("The token string must be non-null and non-empty");
		}

		if (tokenType == TokenType.VARIABLE_WITH_EVAL_ACTION && evaluationAction == null) {
			throw new IllegalArgumentException(
					"The evaluationAction be non-null since it is a variable with evaluation action");
		}

		this.tokenStr = tokenStr;
		this.tokenType = tokenType;
		this.evaluationAction = evaluationAction;
	}

	public String getTokenStr() {
		return tokenStr;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public EvalAction getEvaluationAction() {
		return evaluationAction;
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

	public static EvalAction getPreEvalAction(TokenType tokenType) {
		return Token.getEvaluationAction(tokenType, EvalAction.PRE_INC, EvalAction.PRE_DEC);
	}

	public static EvalAction getPostEvalAction(TokenType tokenType) {
		return Token.getEvaluationAction(tokenType, EvalAction.POST_INC, EvalAction.POST_DEC);
	}

	public boolean isLeftParen() {
		return getTokenType() == TokenType.LEFT_PAREN;
	}

	public boolean isRightParen() {
		return getTokenType() == TokenType.RIGHT_PAREN;
	}

	public boolean isParenthesis() {
		return isLeftParen() || isRightParen();
	}

	public boolean isVariable() {
		return getTokenType() == TokenType.VARIABLE;
	}

	public boolean isIncDecOperator() {
		return getTokenType() == TokenType.DECREMENT_OPERATOR || getTokenType() == TokenType.INCREMENT_OPERATOR;
	}
}