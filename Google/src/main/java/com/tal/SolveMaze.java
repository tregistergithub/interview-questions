package com.tal;

import java.util.Stack;

public class SolveMaze {
	private static final String TRAIL = "@";
	private static final Character WATER = '.';
	private static final Character LAND = '+';

	private static final Character _0 = WATER;
	private static final Character _1 = LAND;
	
	private static final int START_ROW = 0;
	private static final int START_COL = 0;
	
	private static final int END_ROW;
	private static final int END_COL;
	
	public static class Pair {
		private final int row;
		private final int col;
		
		public Pair(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public String toString() {
			return row + "," + col;
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
			Pair other = (Pair) obj;
			if (col != other.col)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
	}

	public static final Stack<Pair> path = new Stack<>();
	
	
	private static final Character[][] maze = 
			  { { _0, _0, _0, _0, _0, _0, _0 },
				{ _1, _0, _1, _1, _1, _1, _0 }, 
				{ _1, _0, _1, _0, _0, _1, _0 }, 
				{ _0, _0, _0, _0, _0, _1, _0 }, 
				{ _0, _1, _1, _1, _0, _1, _0 },	
				{ _0, _1, _0, _1, _0, _1, _0 }, 
				{ _0, _1, _0, _0, _1, _1, _0 }, 
				{ _0, _0, _0, _0, _1, _0, _0 },	
				{ _1, _0, _1, _0, _1, _0, _1 }, 
				{ _0, _0, _1, _0, _1, _0, _0 }, 
				{ _0, _1, _1, _0, _1, _1, _0 },	
				{ _0, _1, _1, _0, _0, _1, _0 }, 
				{ _0, _1, _1, _0, _0, _1, _0 }, 
				{ _0, _0, _0, _0, _1, _1, _1 }, 
				{ _1, _1, _0, _1, _0, _0, _0 },	
				{ _0, _0, _0, _1, _0, _1, _0 }, 
				{ _0, _1, _0, _1, _0, _0, _0 }, 
				{ _0, _1, _0, _1, _1, _1, _1 },	
				{ _0, _1, _0, _0, _0, _0, _0 }, 
				{ _0, _1, _1, _1, _1, _1, _1 },	
				{ _0, _0, _0, _0, _0, _0, _0 } };
	
	static {
		END_ROW = maze.length - 1;
		END_COL = maze[0].length -1;
	}

	private static void printMaze(Character[][]  mat) {
		System.out.println();
		for (int row = 0; row < mat.length; row++) {
			for (int col = 0; col < mat[row].length; col++) {
				Character cell = mat[row][col];
				System.out.print(String.valueOf(cell));
			}
			System.out.println();
		}
	}

	private static boolean solve(Character[][] m, int row, int col) {


		if (!isValidPosition(m, row, col)) {
			return false;
		}

		if (path.contains(new Pair(row, col))) {
			return false;
		}

		path.push(new Pair(row,col));
		printMaze(m, path);		
		
		if (row == END_ROW && col == END_COL) {
			return true;
			
		} else {
			if (solve(m, row + 1, col) ||
				   solve(m, row - 1, col) || 
				   solve(m, row, col + 1) || 
				   solve(m, row, col - 1) ) {
				return true;
			} else {
				path.pop();
				return false;
			}
		}
	}

	private static void printMaze(Character[][] mat, Stack<Pair> path2) {
		System.out.println();
		for (int row = 0; row < mat.length; row++) {
			for (int col = 0; col < mat[row].length; col++) {
				Character cell = mat[row][col];
				if (path2.contains(new Pair(row,col))) {
					System.out.print(TRAIL);
				} else {
					System.out.print(String.valueOf(cell));
				}
				
			}
			System.out.println();
		}
	}

	private static boolean isValidPosition(Character[][] m, int row, int col) {
		if (row < 0 || col < 0 || row >= m.length || col >= m[0].length) {
			return false;
		} else if (m[row][col].equals(LAND)) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		printMaze(maze);
		if (solve(maze, START_ROW, START_COL)) {
			System.out.println("Bingo");
		}
	}
}
