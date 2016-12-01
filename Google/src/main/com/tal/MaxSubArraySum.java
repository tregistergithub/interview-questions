package com.tal;

import java.util.Arrays;

public class MaxSubArraySum {

	public static void main(String[] args) {
		int[] arr = { -2, 3, 3, -1, 1, 0 };
		findMaxSumOfSubArray(arr);
	}

	private static void findMaxSumOfSubArray(int[] arr) {
		if (arr != null) {
			System.out.println(Arrays.toString(arr));
			if (arr.length > 0) {
				System.out.println(maxSubArraySum(arr, 0, null));
			} else {
				System.out.println("No elements in the arrays, therefore, no sum of max sub array");
			}
		}
	}

	private static Integer maxSubArraySum(int[] arr, int startIndex, Integer maxSoFar) {
		if (startIndex == arr.length) {
			return null;
		}

		for (int endIndex = startIndex; endIndex < arr.length; endIndex++) {
			Integer subArraySum = calcSubArraySum(arr, startIndex, endIndex);
			maxSoFar = maxWithNullHandling(maxSoFar, subArraySum);
		}

		Integer maxSubArraySum = maxSubArraySum(arr, startIndex + 1, maxSoFar);

		return maxWithNullHandling(maxSoFar, maxSubArraySum);
	}

	private static Integer maxWithNullHandling(Integer a, Integer b) {
		Integer res = null;
		if (a == null) {
			res = b;
		} else {
			res = a;
			if (b != null) {
				res = Math.max(a, b);
			}
		}
		return res;
	}

	private static Integer calcSubArraySum(int[] arr, int startIndex, int endIndex) {
		Integer sum = null;
		for (int i = startIndex; i < endIndex + 1; i++) {
			int j = arr[i];
			if (sum == null) {
				sum = new Integer(0);
			}
			sum += j;
		}
		return sum;
	}
}
