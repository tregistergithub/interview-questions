package com.tal;

public class FindDupNumberInArray {

	public static void main(String[] args) {
		int arr[] = { 1, 2, 3, 4, 5, 1 };

		int actualSum = 0;
		for (int j = 0; j < arr.length; j++) {
			int num = arr[j];
			actualSum += num;
		}

		final int n = arr.length - 1;

		int expectedSum = (1 + n) * n / 2;

		int dupNum = actualSum - expectedSum;

		System.out.println("The duplicate number is: " + dupNum);
	}

}
