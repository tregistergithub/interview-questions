package com.tal;

public class FourOceans {
	private static final boolean ALREADY_VISITED = true;
	
	private static final boolean OCEAN_IS_NOT_REACHABLE = false;
	private static final boolean OCEAN_IS_REACHABLE = true;
	
	private static final Character LAKE = '.';
	private static final Character WATER = '0';
	private static final Character LAND = '+';
	private static final Character OCEAN_WATER = '!';
	private static final Character VISITED_LAND = '?';

	private static final Character _0 = WATER;
	private static final Character _1 = LAND;
	
	private static boolean isOceanReachable(Character[][] geo, int row, int col, boolean[][] isAlreadyVisited) {

		if (isInOcean(geo, row, col)) {
			return OCEAN_IS_REACHABLE;
			
		} else {
			
			final Character cell = geo[row][col];
			
			if (isLand(cell)) {
				return OCEAN_IS_NOT_REACHABLE;

			} else { // RIVER
				
				if (isAlreadyVisited[row][col]) {
					return OCEAN_IS_NOT_REACHABLE;
					
				} else {
					
					isAlreadyVisited[row][col] = ALREADY_VISITED;
					
					return isOceanReachable(geo, row - 1, col,     isAlreadyVisited) || 
						   isOceanReachable(geo, row,     col + 1, isAlreadyVisited) || 
						   isOceanReachable(geo, row + 1, col,     isAlreadyVisited) || 
						   isOceanReachable(geo, row,     col - 1, isAlreadyVisited);
				}
				
			}
		}
	}

	private static boolean isLand(final Character cell) {
		return cell.equals(LAND);
	}

	private static boolean isWater(final Character cell) {
		return cell.equals(WATER);
	}

	private static boolean isInOcean(Character[][]  geo, int row, int col) {
		return (row < 0) || (col < 0) || (row > geo.length - 1) || (col > geo[0].length - 1);
	}

	private static Character[][] markIslands(Character[][]  geo) {
		
		Character result[][] = new Character[geo.length][geo[0].length];
		
		for (int row = 0; row < geo.length; row++) {
			for (int col = 0; col < geo[row].length; col++) {
				
				processCell(geo, row, col, result);
			}
		}
		
		return result;
	}

	private static void printMatrix(Character[][]  mat) {
		System.out.println();
		for (int row = 0; row < mat.length; row++) {
			for (int col = 0; col < mat[row].length; col++) {
				Character cell = mat[row][col];
				System.out.print(String.valueOf(cell));
			}
			System.out.println();
		}
	}

	private static void markIslands(Character[][] geo, int row, int col, Character[][] result) {
		if (isInOcean(geo, row, col)) {
			return;
		}
		processCell(geo, row, col, result);
		markIslandsRow(geo, row, col + 1, result);
		markIslandsCol(geo, row + 1, col, result);
		markIslands(geo, row + 1, col + 1, result);
	}

	private static void processCell(Character[][] geo, int row, int col, Character[][] result) {
		Character cell = geo[row][col];
		
		if (isWater(cell)) {

			boolean isAlreadyVisited[][] = new boolean[geo.length][geo[0].length];
			
			if (isOceanReachable(geo, row, col, isAlreadyVisited)) {
				result[row][col] = OCEAN_WATER;
			} else {
				result[row][col] = LAKE;
			}
			
		} else {
			result[row][col] = VISITED_LAND;
		}
	}

	private static void markIslandsRow(Character[][] geo, int row, int col, Character[][] result) {
		if (isInOcean(geo, row, col)) {
			return;
		}
		processCell(geo, row, col, result);
		markIslandsRow(geo, row, col + 1, result);
	}

	private static void markIslandsCol(Character[][] geo, int row, int col, Character[][] result) {
		if (isInOcean(geo, row, col)) {
			return;
		}
		processCell(geo, row, col, result);
		markIslandsCol(geo, row + 1, col, result);
	}

	public static void main(String[] args) {
		Character[][]  geo = { { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },
  				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },	
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _1, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },	
				               { _0, _0, _0, _0, _0, _0, _1, _1, _0, _1, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _1, _1, _0, _0, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _1, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },	
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _1, _1, _1, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _1, _0, _0, _0, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },	
				               { _0, _0, _0, _1, _0, _1, _0, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _1, _0, _0, _0, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _1, _1, _1, _1, _1, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },	
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 }, 
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 },	
				               { _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0, _0 } };

		Character result[][] = new Character[geo.length][geo[0].length];
		
		printMatrix(geo);
		markIslands(geo, 0, 0, result);
//		printMatrix(result);
		printMatrix(markIslands(geo));
	}

}
