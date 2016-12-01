package com.tal;

import java.util.Stack;

public class HanoiTowers {

	public static Stack<Integer> left = new Stack<>();
	public static Stack<Integer> middle = new Stack<>();
	public static Stack<Integer> right = new Stack<>();

	public static final int DISK_NUM = 4;

	static {
		for (int i = 0; i < DISK_NUM; i++) {
			left.push(DISK_NUM - i);
		}
	}

	public static Stack<Integer> getPole(int pole) {
		Stack<Integer> res = null;
		switch (pole) {
		case 1:
			res = left;
			break;
		case 2:
			res = middle;
			break;
		case 3:
			res = right;
			break;
		default:
			throw new RuntimeException("unsupported pole number " + pole);
		}

		return res;
	}

	public static void hanoi(int diskNum, int srcPole, int tempPole, int destPole) {
		Stack<Integer> src = getPole(srcPole);
		Stack<Integer> dest = getPole(destPole);
		if (diskNum == 1) {
			dest.push(src.pop());
			printPoles();
		} else {
			hanoi(diskNum - 1, srcPole, destPole, tempPole);
			hanoi(1, srcPole, tempPole, destPole);
			hanoi(diskNum - 1, tempPole, srcPole, destPole);
		}
	}

	private static void printPoles() {
		for (int i = 0; i < DISK_NUM; i++) {
			System.out.print(getAt(left, i) + " ");
			System.out.print(getAt(middle, i) + " ");
			System.out.print(getAt(right, i) + " ");
			System.out.println();
		}
		System.out.println();
	}

	private static String getAt(Stack<Integer> pole, int i) {
		if (pole.size() < DISK_NUM - i) {
			return "|";
		} else if (pole.size() >= DISK_NUM - i) {
			return String.valueOf(pole.get(DISK_NUM - i - 1));
		} else {
			return "|";
		}
	}

	public static void main(String[] args) {
		printPoles();
		hanoi(DISK_NUM, 1, 2, 3);
	}
}
