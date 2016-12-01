package com.tal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FindAnagramInString {
	// text = "kdBACeiABCei" text.length() == N
	// match = "ACB" match.length() == K

	// find_match(text, match) => [2, 7]

	// text2 = "kdBACBeiABCei" => [2, 3, 8]

	// TODO test for: the empty string, null

	static List<Integer> find_match(String text, String match) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < text.length() - match.length(); i++) { // TODO: off
																	// by one
																	// check...
			if (isMatch(text, i, match)) {
				result.add(i);
			}
		}
		return result;
	}

	static boolean isMatch(String text, int i, String match) {
		// boolean isFound = true;

		// boolean [] charWasMatched = new boolean [match.length]; NKK
		Map<Character, Integer> charWasMatched = new HashMap<>();

		for (int j = 0; j < match.length(); j++) { // TODO optimization -
													// caching a inilized map //
													// AABC
			Integer count = charWasMatched.get(match.charAt(j));
			if (count != null) {
				charWasMatched.put(match.charAt(j), count + 1);
			} else {
				charWasMatched.put(match.charAt(j), 1);
			}
		} // (A->2, B->1, C->1)

		for (int j = 0; j < match.length(); j++) {
			char ch = text.charAt(i + j); // TODO fix name

			// boolean matchFound = false; // NKK

			Integer count = charWasMatched.get(ch); // text(3)=BAC in match=ABC

			if (count != null) {
				if (count.equals(Integer.valueOf(1))) {
					charWasMatched.remove(ch); // TODO: is this is the syntax
												// for removing a key
				} else { // the count is bigger than 1
					charWasMatched.put(ch, count - 1);
				}
			} else {
				return false;
			}

			// for (int m = 0; m < match.length; m++) {
			// if (!charWasMatched[m]) {
			// if (ch == match[m]) {
			// charWasMatched[m] = true;
			// matchFound = true;
			// break;
			// }
			// }
			// }
			// if (!matchFound) {
			// isFound = false;
			// break;
			// }
		}

		return true;
	}

	public static List<Integer> find_match2(String text, String match) {
		List<Integer> res = new LinkedList<Integer>();
		int[] matchCharCount = new int[256];
		int[] textCharCount = new int[256];
		for (int i = 0; i < match.length(); i++) {
			matchCharCount[match.charAt(i)]++;
		}
		int i = 0;
		for (i = 0; i < match.length() && i < text.length(); i++) {
			textCharCount[text.charAt(i)]++;
		}
		if (isSame(matchCharCount, textCharCount)) {
			res.add(i - match.length());
		}
		while (i < text.length()) {
			textCharCount[text.charAt(i)]++;
			textCharCount[text.charAt(i - match.length())]--;
			i++;
			if (isSame(matchCharCount, textCharCount)) {
				res.add(i - match.length());
			}
		}
		return res;
	}

	public static boolean isSame(int[] arr1, int[] arr2) {
		if (arr1.length != arr2.length)
			return false;
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// going over all permulations in alsmost all indices O(N*(K!))
	// O(N*(K^2))
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String t = "kdBACBeiABCei";
		String match = "ABC";
		System.out.println(find_match(t, match));
		System.out.println(find_match2(t, match));
	}

}
