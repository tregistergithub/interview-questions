package com.tal;

import java.util.stream.Stream;

public class ReverseString {
	public static void main(String[] args) {
		Stream.of("123").forEach(s -> {
			System.out.println(s + " - " + reverseStr(s));
			System.out.println(s + " - " + reverseStr2(s));
		});

		Stream.of("program creek program     man what's   up", "creek java", "abc xyz", "", null, "  ", " ",
				"web program bomba", "Buenos Aires", "Cordoba", "La Plata").forEach(s -> {
					doIt(s);
					doIt2(s);
				});
	}

	private static void doIt(String s1) {
		char[] charArray = s1 != null ? s1.toCharArray() : null;
		reverseStrInPlace(charArray);
		if (s1 != null) {
			System.out.println("(" + s1 + ") <--> (" + String.valueOf(charArray) + ")");
		} else {
			System.err.println(s1);
		}
	}

	private static void doIt2(String s1) {
		char[] charArray = s1 != null ? s1.toCharArray() : null;
		reverseWordsInPlace(charArray);
		if (s1 != null) {
			System.out.println("(" + s1 + ") <--> (" + String.valueOf(charArray) + ")");
		} else {
			System.err.println(s1);
		}
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

	private static void reverseStrInPlace(char[] s) {
		if (s == null) {
			return;
		}

		int len = s.length;
		for (int i = 0; i < (len / 2); i++) {
			swap(s, i, len - i - 1);
		}
	}

	public static void reverseStrInPlaceWithRange(char[] s, int startPos, int endPos) {
		if (s == null) {
			return;
		}

		int len = endPos - startPos + 1;

		for (int i = startPos; i < startPos + (len / 2); i++) {
			swap(s, i, startPos + endPos - i);
		}
	}

	private static void swap(char[] s, int i, int j) {
		char temp = s[i];
		s[i] = s[j];
		s[j] = temp;
	}

	private static void reverseWordsInPlace(char[] s) {
		if (s == null) {
			return;
		}

		reverseStrInPlace(s);

		boolean seekingStart = false;
		int wordStartPos = 0;

		for (int i = 0; i < s.length; i++) {
			if (!seekingStart && isSpaceOrEnd(s, i)) {
				int wordEndPos = (s.length - 1 == i) ? i : i - 1;
				reverseStrInPlaceWithRange(s, wordStartPos, wordEndPos);
				seekingStart = true;

			} else if (seekingStart && !isSpaceOrEnd(s, i)) {
				seekingStart = false;
				wordStartPos = i;
			}
		}
	}

	private static boolean isSpaceOrEnd(char[] s, int i) {
		return (s.length - 1 == i) || (s[i] == ' ');
	}
}
