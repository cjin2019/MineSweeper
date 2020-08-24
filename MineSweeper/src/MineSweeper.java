import java.util.*;
import java.io.*;

public class MineSweeper {
	/*
	 * @ gBoard contains the game values
	 * 9 represents the mine
	 * 0-8 represent the number of mines surrounding
	 * the square
	 */
	private int[][] gBoard;
	/*
	 * @ gPlayerBoard contains the player's view of the board
	 * 0 represents the hidden 
	 * 1 represents dug
	 * 2 represents flagged
	 */
	private int[][] gPlayerBoard;
	private final int gNumMines = 10;
	private final int gRows = 10;
	private final int gCols = 10;
	private Random gRGen;
	
	/*
	 * Initializes a MineSweeper game 
	 * @ gBoard contains the mines 
	 * @ gPlayerBoard contains the player's view of the game
	 * @ gRGen helps generate random locations for the mines
	 * @ gReader helps read the input
	 */
	public MineSweeper() {
		gBoard = new int[gRows][gCols];
		gPlayerBoard = new int[gRows][gCols];
		gRGen = new Random();
	}
	
	/*
	 * @ returns the gBoard state
	 */
	public int[][] getBoard(){
		return gBoard;
	}
	
	/*
	 * Sets the gBoard with mines and the values of neighboring square
	 */
	public void initializeBoard() {
		for(int fCount = 0; fCount<gNumMines; fCount++) {
			int fRow = gRGen.nextInt(gRows);
			int fCol = gRGen.nextInt(gCols);
			
			while(gBoard[fRow][fCol]==9) {
				fRow = gRGen.nextInt(gRows);
				fCol = gRGen.nextInt(gCols);
			}
			
			//Initializing neighboring squares
			final int fRow1 = Math.max(0, fRow-1);
			final int fRow2 = Math.min(gRows-1, fRow+1);
			final int fCol1 = Math.max(0, fCol-1);
			final int fCol2 = Math.min(gCols-1, fCol+1);
			
			for(int ffRow = fRow1; ffRow <= fRow2; ffRow++) {
				for(int ffCol = fCol1; ffCol <= fCol2; ffCol++) {
					if(gBoard[ffRow][ffCol]!=9) {
						gBoard[ffRow][ffCol]++;
					}
				}
			}
			gBoard[fRow][fCol]=9;
		}
	}
	
	/*
	 * Prints the game state considering the player view
	 * '-' represents hidden squares
	 * '*' represents flagged squares
	 * integer represents the value of the revealed square
	 */
	public void printGame() {
		for(int lr = 0; lr < gBoard.length; lr++) {
			for(int lc = 0; lc < gBoard[0].length; lc++) {
				String outChar = "";
				if(gPlayerBoard[lr][lc]==0) {
					outChar = "-";
				}
				else if(gPlayerBoard[lr][lc]==1) {
					outChar = "" + gBoard[lr][lc];
				}
				else {
					outChar = "*";
				}
				System.out.print(outChar);
			}
			System.out.println();
		}
	}
	
	/*
	 * A helper method for checkDig()
	 * Assumes the square does not contain a mine
	 * @ params r, c are the row and column of the square to be
	 * revealed
	 * @ returns the number of squares revealed
	 */
	public int revealSquare(int r, int c) {
		if(r < 0 || r>=gRows || c < 0 || c >=gCols || gPlayerBoard[r][c]==1) {
			return 0;
		}
		else if(gBoard[r][c]>0){
			gPlayerBoard[r][c] = 1;
			return 1;
		}
		else {
			gPlayerBoard[r][c] = 1;
			return 1 + revealSquare(r+1, c) + revealSquare(r-1, c) + revealSquare(r, c+1) + revealSquare(r, c-1);
		}
	}
	
//	/*
//	 * @ returns a valid action value
//	 */
//	public String readAction() throws IOException {
//		System.out.print("Please type D (dig), F (flag), U(unflag): ");
//		String lInputLine = gReader.readLine().toUpperCase();
//		Set<String> validAction = Set.of("F", "U", "D");
//		
//		while(!validAction.contains(lInputLine)) {
//			System.out.print("Invalid Input. Please Please type D (dig), F (flag), U(unflag): ");
//			lInputLine = gReader.readLine().toUpperCase();
//		}
//		return lInputLine;
//	}
//	
//	/*
//	 * @ returns a valid int array containing the row and column
//	 */
//	public int[] readRowCol() throws IOException {
//		int[] lOutput = null;
//		
//		while(lOutput==null) {
//			System.out.println("Type row (0-"+ (gBoard.length-1) +") and column "
//					+ "(0-"+ (gBoard[0].length-1)+ ") in the form of 'r c': ");
//			String lInputLine = gReader.readLine();
//			try {
//				String[] lStrings = lInputLine.split(" ");
//				int r = Integer.parseInt(lStrings[0]);
//				int c = Integer.parseInt(lStrings[1]);
//				if(r<0 || r>= gBoard.length || c<0 || c>= gBoard[0].length) {
//					System.out.println("Out of bounds. Please type the input again.");
//				}
//				else {
//					lOutput = new int[]{r, c};
//				}
//			}
//			catch (Exception e){
//				System.out.println("You got the following error "+e);
//				System.out.println("Please type the input again");
//			}
//		}
//		return lOutput;
//	}
	
	
	/*
	 * @ params r, c are the row and column of the square to be revealed
	 * @ returns the number of squares revealed
	 * If the square is a mine, returns -1
	 */
	public int checkDig(int r, int c) {
		if(gBoard[r][c]==9) {
			gPlayerBoard[r][c] = 1;
			return -1;
		}
		else {
			return revealSquare(r, c);
		}
	}
	
	/*
	 * Flags the square
	 * Sets the gPlayerBoard[r][c] to 2
	 */
	public void flag(int r, int c) {
		gPlayerBoard[r][c] = 2;
	}
	
	/*
	 * Unflags the square
	 */
	public void unflag(int r, int c) {
		gPlayerBoard[r][c] = 0;
	}
	
	/*
	 * Runs the game
	 */
	public void run() throws IOException {
		int numSquaresLeft = gRows * gCols - gNumMines;
		while(numSquaresLeft > 0) {
			System.out.println("Current Board State");
			printGame();
			
			//guarantees the action and row and columns are valid
			String lAction = readAction();
			int[] lRC = readRowCol();
			int lNumSquaresRevealed = 0;
			
			switch(lAction) {
				case "F":
					flag(lRC[0], lRC[1]);
					break;
				case "U":
					unflag(lRC[0], lRC[1]);
					break;
				case "D":
					lNumSquaresRevealed = checkDig(lRC[0], lRC[1]);
					break;
				default:
					System.out.println("Invalid Action.");
			}
			
			//hit mine
			if(lNumSquaresRevealed == -1) {
				System.out.println("Oops. Hit mine!");
				printGame();
			}
			else {
				numSquaresLeft -= lNumSquaresRevealed;
			}
		}
		System.out.println("You won!");
	}
	
}
