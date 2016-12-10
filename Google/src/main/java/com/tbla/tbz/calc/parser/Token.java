package com.tbla.tbz.calc.parser;

public class Token {
	
	public enum EvalAction {
		PRE_INCREMENT, PRE_DECREMENT, POST_DECREMENT, POST_INCREMENT;
	}

	private final String tokenStr;
	private final TokenType tokenType;
	private final EvalAction evaluationAction;

	public Token(String tokenStr, TokenType tokenType) {
		this(tokenStr, tokenType, null); 
	}

	@Override
	public String toString() {
		return tokenStr + (evaluationAction != null ? "(" + evaluationAction + ")": "");
	}

	public Token(String tokenStr, TokenType tokenType, EvalAction evaluationAction) {
		if (tokenStr == null || tokenStr.trim().isEmpty()) {
			throw new IllegalArgumentException( "The token string must be non-null and non-empty" );
		}
		
		if (tokenType == null) {
			throw new IllegalArgumentException( "The token string must be non-null and non-empty" );
		}
		
		if (tokenType == TokenType.VARIABLE_WITH_EVAL_ACTION && evaluationAction == null) {
			throw new IllegalArgumentException( "The evaluationAction be non-null since it is a variable with evaluation action" );
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
}
