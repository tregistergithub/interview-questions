package com.tbla.tbz.calc.parser;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionTokenizer {

	private static List<TokenParsingInfo> tokenParsingInfoList = new ArrayList<>();
	private static final String REGEXP_VARIABLE = "[a-zA-Z][a-zA-Z0-9_]*";

	static {
		addTokenInfo("\\(", TokenType.LEFT_PAREN);
		addTokenInfo("\\)", TokenType.RIGHT_PAREN);
		addTokenInfo("\\+\\+", TokenType.INCREMENT_OPERATOR);
		addTokenInfo("--", TokenType.DECREMENT_OPERATOR);
		addTokenInfo("\\+", TokenType.PLUS_OPERATOR);
		addTokenInfo("-", TokenType.MINUS_OPERATOR);
		addTokenInfo("\\*", TokenType.MUL_OPERATOR);
		addTokenInfo("\\*", TokenType.MUL_OPERATOR);
		addTokenInfo("/", TokenType.DIV_OPERATOR);
		addTokenInfo("[0-9]+", TokenType.INTEGER); // TODO - consider supporting decimal point ?:\d+\.?\d*|\d*\.\d+
		addTokenInfo(REGEXP_VARIABLE, TokenType.VARIABLE);
	}

	private static void addTokenInfo(String innerRegExp, TokenType tokenType) {
		tokenParsingInfoList.add(new TokenParsingInfo(compilePattern(innerRegExp), tokenType));
	}

	public static boolean isVariableName(String s) {
		return compilePattern(REGEXP_VARIABLE).matcher(s).find();
	}

	private static Pattern compilePattern(String regex) {
		return Pattern.compile("^(\\s*" + regex + "\\s*)");
	}

	private final List<Token> tokenList = new ArrayList<>();
	private final String mathExpression;

	public ExpressionTokenizer(String mathExpression) {
		this.mathExpression = mathExpression;
	}

	public Queue<Token> getPostfixTokenQueue() throws StatementException {
		tokenize(mathExpression);
		return infixToPostfix();
	}

	private void tokenize(String str) throws StatementException {
		String s = str.trim();
		tokenList.clear();
		while (!s.equals("")) {
			boolean match = false;
			for (TokenParsingInfo parsingInfo : tokenParsingInfoList) {
				Matcher m = parsingInfo.regex.matcher(s);
				if (m.find()) {
					match = true;
					String strToken = m.group().trim();
					s = m.replaceFirst("").trim();
					Token newToken = new Token(strToken, parsingInfo.tokenType);
					tokenList.add(newToken);
					break;
				}
			}
			if (!match) {
				throw new StatementException("Unexpected character in mathematical expression " + s);
			}
		}
	}

	private Queue<Token> infixToPostfix() throws StatementException {
		Deque<Token> outputQueue = new LinkedList<>();
		Stack<Token> operatorStack = new Stack<>();

		for (Token currentToken : tokenList) {

			if (currentToken.getTokenType().isOperand()) {

				if (currentToken.getTokenType() == TokenType.VARIABLE) {
					if (!operatorStack.isEmpty() && TokenUtil.isIncDecOperator(operatorStack.peek())) {
						Token incDecOp = operatorStack.pop();
						currentToken = new Token(currentToken.getTokenStr(), TokenType.VARIABLE_WITH_EVAL_ACTION, TokenUtil.getPreEvalAction(incDecOp.getTokenType()));
					}
				}

				outputQueue.add(currentToken);

			} else if (TokenUtil.isIncDecOperator(currentToken)) {
				if (!outputQueue.isEmpty() && TokenUtil.isVariable(outputQueue.peekLast())) {
					Token previousToken = outputQueue.pollLast();
					Token newToken = new Token(previousToken.getTokenStr(), TokenType.VARIABLE_WITH_EVAL_ACTION, TokenUtil.getPostEvalAction(currentToken.getTokenType()) );
					outputQueue.add(newToken);
				} else {
					operatorStack.add(currentToken);
				}

			} else if (currentToken.getTokenType().isBinaryOperator()) {

				while (!operatorStack.isEmpty() && !TokenUtil.isParenthesis(operatorStack.peek()) 
						&& currentToken.getTokenType().getPrecendence() <= operatorStack.peek().getTokenType().getPrecendence()) {
					outputQueue.add(operatorStack.pop());
				}

				operatorStack.push(currentToken);

			} else if (currentToken.getTokenType() == TokenType.LEFT_PAREN) {
				operatorStack.push(currentToken);

			} else if (TokenUtil.isRightParen(currentToken)) {
				while (!TokenUtil.isLeftParen(operatorStack.peek())) {
					if (operatorStack.isEmpty()) {
						throw new StatementException("Parenthesis balancing error 1");
					}

					outputQueue.add(operatorStack.pop());
				}
				operatorStack.pop();
			}
		}

		while (!operatorStack.isEmpty()) {
			if (!operatorStack.isEmpty() && !TokenUtil.isParenthesis(operatorStack.peek())) {
				outputQueue.add(operatorStack.pop());
			} else {
				throw new StatementException("Parenthesis balancing error 2");
			}
		}

		return outputQueue;
	}

	private static class TokenParsingInfo {

		public final Pattern regex;
		public final TokenType tokenType;

		public TokenParsingInfo(Pattern regex, TokenType tokenType) {
			this.regex = regex;
			this.tokenType = tokenType;
		}

		@Override
		public String toString() {
			return "TokenInfo [regex=" + regex + ", tokenType=" + tokenType + "]";
		}
	}
}
