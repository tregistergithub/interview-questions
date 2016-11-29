package com.tal;

import java.util.Stack;

public class CheckParenthesis {

	public static void main(String[] args) {
		// printIsValid("shul()");
		// printIsValid("");
		// printIsValid(null);
		// printIsValid("shul(");
		// printIsValid("(");
		// printIsValid("]dd[");
		// printIsValid("]");
		// printIsValid("}");
		printIsValid("{[]}");
		// printIsValid("{[]}");
//		printIsValid("|||");
//		printIsValid("<|<||>|>");
//		printIsValid("<|<||>|||>");
		printIsValid("<|<||>|>|| <|<||>|>");
		printIsValid("|");
		printIsValid("|{||}|||");
		printIsValid("||");
	}

	private static void printIsValid(String s) {
		if (isValidParentesis(s)) {
			System.out.println("String " + s + " has " + (isValidParentesis(s) ? "valid" : "invalid ") + " parenthesis");
			
		} else {
			System.err.println("String " + s + " has " + (isValidParentesis(s) ? "valid" : "invalid ") + " parenthesis");
		}
		
		try {
			Thread.sleep(1l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean isValidParentesis(String s) {

		if (s == null) {
			return false;
		}

		Stack<Character> pStack = new Stack<>();

		for (int i = 0; i < s.length(); i++) {


			if ((charAt(s, i).equals('|'))) {

				if (!pStack.isEmpty()) {
					Character peek = pStack.peek();
					if (peek.equals('|')) { // this is close
						if (!pStack.isEmpty()) {
							pStack.pop();
						}
					} else { // This is open
						pStack.push(charAt(s, i)); // This is open
					}
				} else { // This is open
					pStack.push(charAt(s, i));
				}

			} else {
				if (isOpen(s, i)) {
					pStack.push(charAt(s, i));

				} else if (isClose(s, i)) {

					if (!pStack.isEmpty()) {
						Character popped = pStack.pop();
						if (!isPair(popped, s, i)) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		}

		if (!pStack.isEmpty()) {
			return false;
		}

		return true;
	}

	private static Character charAt(String s, int i) {
		return s.charAt(i);
	}

	private static boolean isPair(Character popped, String s, int i) {
		char c = s.charAt(i);
		if ((popped.equals('(') && c == ')') || (popped.equals('{') && c == '}') || (popped.equals('[') && c == ']')
				|| (popped.equals('<') && c == '>') || (popped.equals('|') && c == '|')) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isOpen(String s, int i) {
		return isContains("({[<|", s, i);
	}

	private static boolean isContains(String allOptions, String s, int i) {
		String c = String.valueOf(s.charAt(i));

		if (allOptions.contains(c)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isClose(String s, int i) {
		return isContains(")}]>|", s, i);
	}
}
