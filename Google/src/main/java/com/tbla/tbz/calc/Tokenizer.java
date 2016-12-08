package com.tbla.tbz.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tbla.tbz.calc.Tokenizer.Token;

public class Tokenizer {

	private static final String REGEXP_VARIABLE = "[a-zA-Z][a-zA-Z0-9_]*";
	private static final int VARIABLE = 1;
	private static final int NUMBER = 2;
	private static final int BINARY_OPERATOR = 3;
	private static final int LEFT_PAREN = 5;
	private static final int RIGHT_PAREN = 6;
	private static final int INCREMENT_OPERATOR = 10;
	private static final int DECREMENT_OPERATOR = 20;
	private List<TokenInfo> tokenInfos = new ArrayList<TokenInfo>();

	private void addTokenInfo(String regex, int token) {
		tokenInfos.add(new TokenInfo(compilePattern(regex), token));
	}

	private static Pattern compilePattern(String regex) {
		return Pattern.compile("^(\\s*" + regex + "\\s*)");
	}

	public static class Token {

		
		public final int tokenType;
		public final String strToken;

		public Token(int tokenType, String strToken) {
			this.tokenType = tokenType;
			this.strToken = strToken;
		}

		@Override
		public String toString() {
			return strToken;
		}
		
		public boolean isParenthesis() {
			// TODO Auto-generated method stub
			return isRightParen() || isLeftParen();
		}

		public boolean isRightParen() {
			return tokenType == RIGHT_PAREN;
		}

		public boolean isLeftParen() {
			return tokenType == LEFT_PAREN;
		}

		public String getAssociativity() {
			return "left";
		}

		public boolean isOperator() {
			return tokenType == BINARY_OPERATOR || isIncrementOrDecrementOperator();
		}

		public boolean isNumber() {
			return tokenType == NUMBER;
		}

		public boolean isVariable() {
			return tokenType == VARIABLE;
		}
		
		public static boolean isVariableName(String s) {
			return compilePattern(REGEXP_VARIABLE).matcher(s).find();
		}
		
		public boolean isOperand() {
			return isNumber() || isVariable();
		}    
		
		
		public int getPrecedence()		{
		    switch (strToken)		    {
		        case "*":          
		        case "/":
		            return 8;
		        case "+":
		        case "-":
		            return 6;
		        case "(":
		        case ")":
		            return 1;
		        default: 
		            throw new ParserException("Unsupported operator " + strToken );
		    }
		}

		public static Token buildNumberToken(int value) {
			return new Token(NUMBER, String.valueOf(value));			
		}

		public static Token buildVariableToken(String variableName) {
			return new Token(VARIABLE, variableName);
		}

		public static Token buildBinaryOperatorToken(char operator) {
			return new Token(BINARY_OPERATOR, String.valueOf(operator));
		}

		public boolean isIncrementOrDecrementOperator() {
			return isIncrementOperator() || isDecrementOperator();
		}
		
		public boolean isIncrementOperator() {
			return tokenType == INCREMENT_OPERATOR;
		}
		
		public boolean isDecrementOperator() {
			return tokenType == DECREMENT_OPERATOR;
		}
	}

	private final List<Token> tokens = new ArrayList<Token>();

	public Tokenizer(String s) {
		addTokenInfo("\\(", LEFT_PAREN);
		addTokenInfo("\\)", RIGHT_PAREN);
		addTokenInfo("\\+\\+", INCREMENT_OPERATOR);
		addTokenInfo("--", DECREMENT_OPERATOR);
		addTokenInfo("\\+|-", BINARY_OPERATOR);
		addTokenInfo("\\*|/", BINARY_OPERATOR);
		addTokenInfo("[0-9]+", NUMBER); // ?:\d+\.?\d*|\d*\.\d+
		addTokenInfo(REGEXP_VARIABLE, VARIABLE);
		tokenize(s);
	}

	public void tokenize(String str) {
		String s = str.trim();
		Token previousToken = null;
		while (!s.equals("")) {
			boolean match = false;
			for (TokenInfo tokenInfo : tokenInfos) {
				Matcher m = tokenInfo.regex.matcher(s);
				if (m.find()) {
					match = true;
					String strToken = m.group().trim();
					s = m.replaceFirst("").trim();
					
					if (isPostIncrement(previousToken, tokenInfo)) {
						Token newPreviousToken = new Token(previousToken.tokenType, previousToken.strToken, POST_INCREMENT);
					}
					
					Token newToken = new Token(tokenInfo.tokenType, strToken);
					tokens.add(newToken);
					
					previousToken = newToken;
					break;
				}
			}
			if (!match) {
				throw new ParserException("Unexpected character in input: " + s);
			}
		}
	}

	private boolean isPostIncrement(Token previousToken, TokenInfo tokenInfo) {
		return previousToken!=null && previousToken.isVariable() && tokenInfo.tokenType == INCREMENT_OPERATOR;
	}

	public List<Token> getTokens() {
		return tokens;
	}
	
	private class TokenInfo {

		public final Pattern regex;
		public final int tokenType;

		public TokenInfo(Pattern regex, int tokenType) {
			this.regex = regex;
			this.tokenType = tokenType;
		}

		@Override
		public String toString() {
			return "TokenInfo [regex=" + regex + ", tokenType=" + tokenType + "]";
		}		
	}

}
