package com.tal;

public class PrintBalancedParenthesis {

	public static void printParen(int pos, int availLeft, int availRight, char res[]) {
		if (availLeft < 0 || availRight < 0) {
			return;

		} else if (availLeft == 0 && availRight == 0) {
			System.out.println(res);
			;

		} else {

			if (availLeft > 0) {
				res[pos] = '(';
				printParen(pos + 1, availLeft - 1, availRight, res);
			}

			if (availRight > availLeft) {
				res[pos] = ')';
				printParen(pos + 1, availLeft, availRight - 1, res);
			}

		}
	}

	public static void main(String[] args) {
		int count = 10;
		char[] res = new char[count * 2];
		printParen(0, count, count, res);
	}

}
