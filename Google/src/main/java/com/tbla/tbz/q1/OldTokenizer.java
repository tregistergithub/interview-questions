package com.tbla.tbz.q1;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldTokenizer {

	public static final int PRE_INCREMEMNT = 1;
	public static final int POST_INCREMENT = 2;
	public static final int PRE_DECREMEMNT = 3;
	public static final int POST_DECREMENT = 4;
	public static final int NO_SPECIAL_INSTRUCTIONS = 0;
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
		public final int specialInstructions;

		public Token(int tokenType, String strToken, int specialInstructions) {
			this.tokenType = tokenType;
			this.strToken = strToken;
			this.specialInstructions = specialInstructions; 
		}
		
		public Token(int tokenType, String strToken) {
			this(tokenType, strToken, NO_SPECIAL_INSTRUCTIONS);
		}

		@Override
		public String toString() {
			return strToken + (specialInstructions != NO_SPECIAL_INSTRUCTIONS ? "*" : "");
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
			return tokenType == BINARY_OPERATOR;
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
		            throw new OldParserException2("Unsupported operator " + strToken );
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
		
		public static Token buildSpecialPostVariableToken(Token currentToken, Token incrementDecrementOperator) {
			if (incrementDecrementOperator.isDecrementOperator()) {
				currentToken = Token.buildPostDecrementVariableToken(currentToken);	
			} else if (incrementDecrementOperator.isIncrementOperator()) {
				currentToken = Token.buildPostIncrementVariableToken(currentToken);	
			} else {
				throw new RuntimeException("Failed to build increment / decrement variable " + currentToken);
			}
			return currentToken;
		}
		
		public static Token buildSpecialPreVariableToken(Token currentToken, Token incrementDecrementOperator) {
			if (incrementDecrementOperator.isDecrementOperator()) {
				currentToken = Token.buildPreDecrementVariableToken(currentToken);	
			} else if (incrementDecrementOperator.isIncrementOperator()) {
				currentToken = Token.buildPreIncrementVariableToken(currentToken);	
			} else {
				throw new RuntimeException("Failed to build increment / decrement variable " + currentToken);
			}
			return currentToken;
		}

		public boolean isIncrementOperator() {
			return tokenType == INCREMENT_OPERATOR;
		}
		
		public boolean isDecrementOperator() {
			return tokenType == DECREMENT_OPERATOR;
		}

		static Queue<Token> infixToPostfix(List<Token> tokenList) {
			Deque<Token> outputQueue = new LinkedList<>();
			Stack<Token> operatorStack = new Stack<>();
		
			for (int i = 0; i < tokenList.size(); i++) {
				Token currentToken = tokenList.get(i);
				
				if (currentToken.isOperand()) {					
					if (currentToken.isVariable()) {
						if (!operatorStack.isEmpty() && operatorStack.peek().isIncrementOrDecrementOperator()) {
							Token incrementDecrementOperator = operatorStack.pop();
							currentToken = Token.buildSpecialPreVariableToken(currentToken, incrementDecrementOperator);						
						}
					}
					
					outputQueue.add(currentToken);
					
				} else if (currentToken.isIncrementOrDecrementOperator()) {
					if (!outputQueue.isEmpty() && outputQueue.peekLast().isVariableNotSpecial()) {
						Token previousToken = outputQueue.pollLast();
						Token specialVariableToken = Token.buildSpecialPostVariableToken(previousToken, currentToken);
						outputQueue.add(specialVariableToken);
					} else { 
						operatorStack.add(currentToken);
					}
					
				}  else if (currentToken.isOperator()) {
					
					while (!operatorStack.isEmpty() && !operatorStack.peek().isParenthesis()
							&& currentToken.getPrecedence() <= operatorStack.peek().getPrecedence()) {
						outputQueue.add(operatorStack.pop());
					}

					operatorStack.push(currentToken);
		
				} else if (currentToken.isLeftParen()) {
					operatorStack.push(currentToken);
		
				} else if (currentToken.isRightParen()) {
					while (!operatorStack.peek().isLeftParen()) {
						if (operatorStack.isEmpty()) {
							throw new OldParserException2("Parenthesis balancing error");
						}
		
						outputQueue.add(operatorStack.pop());
					}
					operatorStack.pop();
				}
			}
		
			while (!operatorStack.isEmpty()) {
				if (!operatorStack.isEmpty() && !operatorStack.peek().isParenthesis())
					outputQueue.add(operatorStack.pop());
				else
					throw new OldParserException2("Parenthesis balancing error");
			}
		
			return outputQueue;
		}

		private boolean isVariableNotSpecial() {
			// TODO Auto-generated method stub
			return tokenType==VARIABLE && specialInstructions == NO_SPECIAL_INSTRUCTIONS;
		}

		public static Token buildPostIncrementVariableToken(Token originalVaribleToken) {
			return new Token(originalVaribleToken.tokenType, originalVaribleToken.strToken, POST_INCREMENT);
		}
		public static Token buildPreIncrementVariableToken(Token originalVaribleToken) {
			return new Token(originalVaribleToken.tokenType, originalVaribleToken.strToken, PRE_INCREMEMNT);
		}
		public static Token buildPostDecrementVariableToken(Token originalVaribleToken) {
			return new Token(originalVaribleToken.tokenType, originalVaribleToken.strToken, POST_DECREMENT);
		}
		public static Token buildPreDecrementVariableToken(Token originalVaribleToken) {
			return new Token(originalVaribleToken.tokenType, originalVaribleToken.strToken, PRE_DECREMEMNT);
		}
	}

	private final List<Token> tokens = new ArrayList<Token>();

	public OldTokenizer(String s) {
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
		while (!s.equals("")) {
			boolean match = false;
			for (TokenInfo tokenInfo : tokenInfos) {
				Matcher m = tokenInfo.regex.matcher(s);
				if (m.find()) {
					match = true;
					String strToken = m.group().trim();
					s = m.replaceFirst("").trim();
					Token newToken = new Token(tokenInfo.tokenType, strToken);
					tokens.add(newToken);
					break;
				}
			}
			if (!match) {
				throw new OldParserException2("Unexpected character in mathematical expression " + s);
			}
		}
	}

	public boolean isPostIncrementOrDecrement(Token previousToken, TokenInfo tokenInfo) {
		return previousToken!=null && previousToken.isVariable() && isIncrementOrDecrementOperator(tokenInfo);
	}

	public boolean isIncrementOrDecrementOperator(TokenInfo tokenInfo) {
		return tokenInfo.tokenType == INCREMENT_OPERATOR || tokenInfo.tokenType == DECREMENT_OPERATOR;
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
