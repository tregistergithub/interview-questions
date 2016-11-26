package com.tal;

public class IsPalindrome {
	public static void main(String[] args) {
		isPalindrome("shula");
		isPalindrome2("", 0);

	}

	private static void isPalindrome2(String s, int i) {
		if (s == null) {
			System.err.println(s + " is null");
			return;
		}

		if (s.isEmpty()) {
			System.out.println(s + " is Palindrome");
			return;
		}

		if (i == s.length() / 2 + 1) {
			System.out.println(s + " is Palindrome");
		} else {
			if (s.charAt(i) == s.charAt(s.length() - i - 1)) {
				isPalindrome2(s, i + 1);
			} else {
				System.err.println(s + " is not Palindrome");
			}
		}
	}

	private static void isPalindrome(String s) {
		if (s == null)
			return;

		int halfLen = s.length() / 2;

		boolean isPalindrome = true;
		for (int leftIndex = 0; leftIndex < halfLen; leftIndex++) {
			int rightIndex = s.length() - leftIndex - 1;
			char leftChar = s.charAt(leftIndex);
			char rightChar = s.charAt(rightIndex);

			if (leftChar != rightChar) {
				isPalindrome = false;
				break;
			}
		}

		System.out.println(s + (isPalindrome ? " is" : " is not") + " Palindrome");
	}
}
