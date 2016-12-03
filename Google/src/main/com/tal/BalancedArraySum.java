package com.tal;

import java.util.Arrays;

public class BalancedArraySum {

	public static void main(String[] args) {
		int a[] = { 1, 1, 1, 3, 6, 1, 2, 3 };

		int leftSum[] = new int[a.length];
		int rightSum[] = new int[a.length];

		int sumFromLeft = 0;
		int sumFromRight = 0;

		int len = rightSum.length;
		for (int i = 0; i < len; i++) {
			leftSum[i] = sumFromLeft;
			rightSum[len - i - 1] = sumFromRight;
			int leftElement = a[i];
			int rightElement = a[len - i - 1];
			sumFromLeft += leftElement;
			sumFromRight += rightElement;
		}

		for (int i = 0; i < rightSum.length; i++) {
			if (rightSum[i] == leftSum[i]) {
				System.out.println("index = " + i + ", val=" + a[i]);
				break;
			}
		}

		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(leftSum));
		System.out.println(Arrays.toString(rightSum));
	}
}
