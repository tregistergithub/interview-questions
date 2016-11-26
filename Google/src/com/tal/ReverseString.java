package com.tal;

public class ReverseString {
	public static void main(String[] args) {
		String s = "123";
		System.out.println(s + " - " + reverseStr(s));
		System.out.println(s + " - " + reverseStr2(s));
	}

	private static String reverseStr2(String s) {
		int len = s.length();
		char res[] = new char[len];

		for (int leftIndex = 0; leftIndex < len / 2; leftIndex++) {
			char leftChar = s.charAt(leftIndex);
			int rightIndex = len - 1 - leftIndex;
			char rightChar = s.charAt(rightIndex);
			res[leftIndex] = rightChar;
			res[rightIndex] = leftChar;
		}
		
		if (len % 2 == 1) {
			res[len / 2] = s.charAt(len / 2);
		}
		
		return String.valueOf(res);
	}

	private static String reverseStr(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			sb.append(s.charAt(len - i - 1));
		}
		return sb.toString();
	}
}
