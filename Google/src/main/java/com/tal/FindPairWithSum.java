package com.tal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindPairWithSum {

	private static boolean pairWithSumExist(List<Integer> numList, Integer sum) {

		Set<Integer> complementNums = new HashSet<>();

		for (Integer num : numList) {
			int complement = sum - num;
			if (complementNums.contains(complement)) {
				return true;
			} else {
				complementNums.add(num);
			}
		}

		return false;
	}

	public static void main(String[] args) {
		List<Integer> numList = new ArrayList<>();
		numList.add(2);
		numList.add(3);
		numList.add(4);
		numList.add(3);
		numList.add(6);

		final int SUM = 8;
		System.out.println("In " + numList + " there is" + (pairWithSumExist(numList, SUM) ? "" : " not")
				+ " a pair that sums to " + SUM);
	}
}
