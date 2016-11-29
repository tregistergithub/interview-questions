package com.tal.leetcode;

public class ArrangingCoins441 {

	public int arrangeCoins(int n) {
		long i = 0;
		int step = 1;
		while (i <= n) {
			i += step;
			if (i <= n) {
				step++;
			}
		}
		return step - 1;
	}

	public static void main(String[] args) {
		System.out.println(new ArrangingCoins441().arrangeCoins(2147483642));
	}
}
