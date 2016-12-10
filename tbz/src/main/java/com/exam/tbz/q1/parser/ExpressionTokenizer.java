package com.exam.tbz.q1.parser;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a math expression and returns it in a post-fix notation
 * 
 * See also:  Shunting-yard algorithm ( Edsger Dijkstra ) in Wikipedia
 *
 * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 *
 */
class ExpressionTokenizer {

	private static List<TokenParsingInfo> tokenParsingInfoList = new ArrayList<>();
	private static final String REGEXP_VARIABLE = "[a-zA-Z][a-zA-Z0-9_]{0,29}";

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

	private static Pattern compilePattern(String regex) {
		return Pattern.compile("^(\\s*" + regex + "\\s*)");
	} 
	
	private static void addTokenInfo(String innerRegExp, TokenType tokenType) {
		tokenParsingInfoList.add(new TokenParsingInfo(compilePattern(innerRegExp), tokenType));
	}

	public static boolean isVariableName(String s) {
		return compilePattern(REGEXP_VARIABLE+"$").matcher(s).find();
	}

	private final String mathExpression;

	public ExpressionTokenizer(String mathExpression) {
		if (mathExpression == null) throw new RuntimeException("mathExpression must be non-null");
		this.mathExpression = mathExpression;
	}

	public Queue<Token> getPostfixTokenQueue()  {
		return infixToPostfix(tokenize(mathExpression));
	}

	private List<Token> tokenize(String str)  {
		List<Token> tokenList = new ArrayList<>();
		String s = str.trim();
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
				throw new StatementException(9, "Unexpected character in mathematical expression " + s);
			}
		}
		return tokenList;
	}

	/**
	 * Converts infix to post-fix with Shunting-yard algorithm
	 * 
	 * In addition, handles variables with pre/post increment/decrement opeations
	 */
	private Queue<Token> infixToPostfix(List<Token> tokenList)  {
		Deque<Token> outputQueue = new LinkedList<>();
		Stack<Token> operatorStack = new Stack<>();

		for (Token currentToken : tokenList) { 

			// If its an operand (variable / literal) - put in output
			if (currentToken.getTokenType().isOperand()) {

				if (currentToken.getTokenType() == TokenType.VARIABLE) {

					// Handle variables with pre/post increment/decrement operations
					if (!operatorStack.isEmpty() && operatorStack.peek().isIncDecOperator()) {
						Token incDecOp = operatorStack.pop();
						currentToken = new Token(currentToken.getTokenStr(), TokenType.VARIABLE_WITH_EVAL_ACTION, Token.getPreEvalAction(incDecOp.getTokenType()));
					}
				}

				outputQueue.add(currentToken);

			} else if (currentToken.isIncDecOperator()) {

				// Handle variables with pre/post increment/decrement operations
				if (!outputQueue.isEmpty() && outputQueue.peekLast().isVariable()) {
					Token previousToken = outputQueue.pollLast();
					Token newToken = new Token(previousToken.getTokenStr(), TokenType.VARIABLE_WITH_EVAL_ACTION, Token.getPostEvalAction(currentToken.getTokenType()) );
					outputQueue.add(newToken);
					
				} else { // Insert the operator to it can be picked by the algorithm to assign it to the closest variable  
					operatorStack.add(currentToken);
				}

			// Handle binary operators (+, -, *, / , etc.)
			} else if (currentToken.getTokenType().isBinaryOperator()) {

				while (!operatorStack.isEmpty() && !operatorStack.peek().isParenthesis() 
						&& currentToken.getTokenType().getPrecendence() <= operatorStack.peek().getTokenType().getPrecendence()) {
					// put in output all of the higher/same precendence operator
					// Need to add in the future : association handling and unary operator handling
					outputQueue.add(operatorStack.pop()); 
				}

				operatorStack.push(currentToken); 

			// left parenthesis - treat it like it is the begging of the statement
			} else if (currentToken.isLeftParen()) {
				operatorStack.push(currentToken);

			// right parenthesis - pop all until the left parenthesis (treat it like it is the end of the statement) 
			} else if (currentToken.isRightParen()) {
				while (!operatorStack.isEmpty() && !operatorStack.peek().isLeftParen()) {
					outputQueue.add(operatorStack.pop());
				}
				if (!operatorStack.isEmpty()) {
					operatorStack.pop(); // Discard left parenthesis
				} else  {
					throw new StatementException(13, "Parenthesis balancing error 1"); // Missing left parenthesis
				}
			}
		}

		while (!operatorStack.isEmpty()) {
			if (!operatorStack.isEmpty() && !operatorStack.peek().isParenthesis()) {
				outputQueue.add(operatorStack.pop()); // pop all of the remaining operators 
			} else {
				throw new StatementException(5, "Parenthesis balancing error 2"); // Missing right parenthesis at end
			}
		}

		return outputQueue;
	}

	/**
	 * Helper class to contain the token parsing info
	 *
	 */
	private static class TokenParsingInfo {

		public final Pattern regex;
		public final TokenType tokenType;

		public TokenParsingInfo(Pattern regex, TokenType tokenType) {
			this.regex = regex;
			this.tokenType = tokenType;
		}

		@Override
		public String toString() {
			return "regex=" + regex + ", tokenType=" + tokenType + "]";
		}
	}
}