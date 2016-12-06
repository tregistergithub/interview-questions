package com.tal;

import java.util.Stack;

public class TopLeftToBottomRightRoutes {
	private static final char BACKGROUND = '-';
	private static final char TRAIL = 'o';
	private static final int ROW_COUNT = 5;
	private static final int COL_COUNT = 2;

	public static class Coordinates {
		public Coordinates(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + col;
			result = prime * result + row;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinates other = (Coordinates) obj;
			if (col != other.col)
				return false;
			if (row != other.row)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return row + "," + col;
		}

		private final int row;
		private final int col;
	}

	private static int bigO = 0;

	private static int calcRoute(Stack<Coordinates> route, int row, int col) {
		bigO++;
		route.push(new Coordinates(row, col));

		if (isReachedDestination(row, col)) {
			printRoute(route);
			return 1;
		} else {
			int res = 0;
			res = calcRoute(route, row, col + 1, res);
			res = calcRoute(route, row + 1, col, res);
			res = calcRoute(route, row + 1, col + 1, res);
			return res;
		}
	}

	private static int calcRoute(Stack<Coordinates> route, int row, int col, int res) {
		if (!isOutOfBoundaries(row, col)) {
			res += calcRoute(route, row, col);
			route.pop();
		}
		return res;
	}

	private static void printRoute(Stack<Coordinates> route) {
		for (int row = 0; row < ROW_COUNT; row++) {
			for (int col = 0; col < COL_COUNT; col++) {
				Coordinates position = new Coordinates(row, col);
				if (route.contains(position)) {
					System.out.print(TRAIL);
				} else {
					System.out.print(BACKGROUND);
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private static boolean isOutOfBoundaries(int row, int col) {
		return row >= ROW_COUNT || col >= COL_COUNT;
	}

	private static boolean isReachedDestination(int row, int col) {
		return row == ROW_COUNT - 1 && col == COL_COUNT - 1;
	}

	public static void main(String[] args) {
		Stack<Coordinates> route = new Stack<>();
		System.out.println("The number of routes from top-left to bottom-right is: " + calcRoute(route, 0, 0));
		System.out.println("The number of iterations took to find it out was : " + bigO);
	}
}
