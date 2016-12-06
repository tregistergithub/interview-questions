package com.tal;

public class SumDigitArrays {

	private static int sumDigits(Integer[] digitArr1, Integer[] digitArr2, int i, int reminder) {
		if (digitArr1.length - 1 - i < 0 && digitArr2.length - 1 - i < 0) {
			return 0;
		}

		int d1 = digitArr1.length - 1 - i < 0 ? 0 : digitArr1[digitArr1.length - 1 - i];
		int d2 = digitArr2.length - 1 - i < 0 ? 0 : digitArr2[digitArr2.length - 1 - i];

		int digitSum = (d1 + d2 + reminder);
		int newReminder = digitSum % 10;
		int tens = digitSum / 10;
		int res = newReminder * (int) Math.pow(10, i);
		res += sumDigits(digitArr1, digitArr2, i + 1, tens);
		return res;
	}

	public static void main(String[] args) {
		Integer digitArr1[] = {    1, 9, 8, 1, 9 };
		Integer digitArr2[] = { 1, 6, 8, 9, 1, 3 };

		int sum = sumDigits(digitArr1, digitArr2, 0, 0);
		System.out.println(convertToNum(digitArr1) + " + " + convertToNum(digitArr2) + " = " + sum);
	}

	private static int convertToNum(Integer[] digitArr) {
		int sum = 0;
		for (int i = 0; i < digitArr.length; i++) {
			int digit = digitArr[digitArr.length - 1 - i];
			sum += digit * (int) Math.pow(10, i);
		}
		return sum;
	}

}
