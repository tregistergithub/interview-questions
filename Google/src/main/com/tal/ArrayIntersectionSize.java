package com.tal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayIntersectionSize {
	public static void main(String[] args) {
		int a1[] = { 3 };
		int a2[] = { 1, 2, 3, 4, 11, 12 };

		System.out.println("a1=" + Arrays.toString(a1));
		System.out.println("a2=" + Arrays.toString(a2));
		Integer[] intersection = intersection(a1, a2);
		System.out.println("a1 ç a2=" + Arrays.toString(intersection) + ", size=" + intersection.length);
	}

	private static Integer[] intersection(int[] srcArr, int[] destArr) {

		Set<Integer> res = new HashSet<>();
		if (srcArr == null || srcArr.length == 0 || destArr == null || destArr.length == 0) {
			return new Integer[0];
		}
		int destPos = 0;
		int dest = destArr[destPos];
		for (int src : srcArr) {
			while (dest < src) {
				destPos++;
				if (isArrayFullyProcessed(destArr, destPos)) {
					break;
				}
				dest = destArr[destPos];
			}
			if (src == dest) {
				res.add(src);
			}
		}

		return res.toArray(new Integer[res.size()]);
	}

	private static boolean isArrayFullyProcessed(int[] a, int pos) {
		return pos >= a.length;
	}
}
