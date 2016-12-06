package com.tal;

public class Fibonacci {

	public static int n = 47;
	public static Long[] tempRes = new Long[n + 1];

	public static void main(String[] args) {
		System.out.println(n + ": (n) " + fib_O_n(n));
		System.out.println(n + ": (2^n) " + fib_O_2_power_n(n));
	}

	private static long fib_O_n(int n) {
		if (n < 0) {
			return 0;

		} else {

			if (tempRes[n] == null) {
				if (n == 0) {
					tempRes[n] = 0L;
				} else if (n == 1) {
					tempRes[n] = 1L;
				} else {
					tempRes[n] = fib_O_n(n - 1) + fib_O_n(n - 2);
				}
			}

			return tempRes[n];
		}

	}

	private static long fib_O_2_power_n(int n) {
		if (n < 0) {
			return 0L;

		} else if (n == 0) {
			return 0L;
		} else if (n == 1) {
			return 1L;
		} else {
			return fib_O_2_power_n(n - 1) + fib_O_2_power_n(n - 2);
		}
	}

}
