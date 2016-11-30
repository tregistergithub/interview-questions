package com.tal;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {

	public static void main(String[] args) {
		int arr[] = new int[32];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < arr.length; i++) {
			arr[i] = random.nextInt(100) + 1;
		}

		p(arr);
		quicksort(arr, 0, arr.length - 1);
		p(arr);
	}

	private static void quicksort(int[] a, int leftIndex, int rightIndex) {
		if (rightIndex - leftIndex < 2) {
			return;
		}
		int pivotIndex = rightIndex;
		int wallIndex = leftIndex;
		int pivot = a[pivotIndex];
		System.out.println(leftIndex + ".." + rightIndex);

		for (int j = wallIndex; j < pivotIndex; j++) {
			int curr = a[j];
			if (curr < pivot) {
				swap(a, wallIndex, j);
				wallIndex++;
				// p(a);
			}
		}

		swap(a, pivotIndex, wallIndex);
		// p(a);
		quicksort(a, leftIndex, wallIndex - 1);
		quicksort(a, wallIndex + 1, rightIndex);
	}

	private static void p(int[] a) {
		System.out.println(Arrays.toString(a));
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
