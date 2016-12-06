package com.tal;

import java.util.Arrays;

public class BalancedArraySum {
	public static final int DID_NOT_FIND_BALANCED_INDEX = -1;

	/**
	 * Searches the balanced element in an an array of integers.
	 * 
	 * Balanced element is an element which the sum of the the elements to its right 
	 * is equal to the sum of the elements to its left. 
	 * 
	 * @param arr array of integers in which may contain a balanced element  
	 * @return the index of the balanced element, or DID_NOT_FIND_BALANCED_INDEX in case such index was not found
	 */
	public int getBalancedIndex(int[] arr) {
		
		if (arr == null) {
			throw new IllegalArgumentException( "arr must be non-null" );
		}
		int leftSum[] = new int[arr.length];
		int rightSum[] = new int[arr.length];

		int sumFromLeft = 0;
		int sumFromRight = 0;

		int len = rightSum.length;
		for (int i = 0; i < len; i++) {
			leftSum[i] = sumFromLeft;
			rightSum[len - i - 1] = sumFromRight;
			int leftElement = arr[i];
			int rightElement = arr[len - i - 1];
			sumFromLeft += leftElement;
			sumFromRight += rightElement;
		}

		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(leftSum));
		System.out.println(Arrays.toString(rightSum));

		for (int i = 0; i < rightSum.length; i++) {
			if (rightSum[i] == leftSum[i]) {
				System.out.println("index = " + i + ", val=" + arr[i] + ", sum=" + leftSum[i]);
				return i;
			}
		}

		return DID_NOT_FIND_BALANCED_INDEX;
	}
}
