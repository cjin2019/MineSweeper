
import java.awt.GraphicsConfiguration;
import java.util.Random;

/**
 * 
 */

/**
 * @author cjjin
 *
 */
public class MineSweeperBoard {

	/**
	 * 
	 */
	private final int pRows = 10;
	private final int pCols = 10;
	private final int pNumMines = 15;
	
	private MineSquare[][] pBoard;
	public MineSweeperBoard() {
		pBoard = new MineSquare[pRows][pCols];
		
		int[][] lBoard = randomBoard();
		for(int r = 0; r < pRows; r++) {
			for(int c = 0; c < pCols; c++) {
				pBoard[r][c] = new MineSquare(r, c, lBoard[r][c]);
			}
		}
	}
	
	private int[][] randomBoard() {
		Random lGen = new Random();
		int[][] lBoard = new int[pRows][pCols];
		for(int fCount = 0; fCount<pNumMines; fCount++) {
			int fRow = lGen.nextInt(pRows);
			int fCol = lGen.nextInt(pCols);
			
			while(lBoard[fRow][fCol]==9) {
				fRow = lGen.nextInt(pRows);
				fCol = lGen.nextInt(pCols);
			}
			
			//Initializing neighboring squares
			final int fRow1 = Math.max(0, fRow-1);
			final int fRow2 = Math.min(pRows-1, fRow+1);
			final int fCol1 = Math.max(0, fCol-1);
			final int fCol2 = Math.min(pCols-1, fCol+1);
			
			for(int ffRow = fRow1; ffRow <= fRow2; ffRow++) {
				for(int ffCol = fCol1; ffCol <= fCol2; ffCol++) {
					if(lBoard[ffRow][ffCol]!=9) {
						lBoard[ffRow][ffCol]++;
					}
				}
			}
			lBoard[fRow][fCol]=9;
		}
		
		return lBoard;
	}
	/**
	 * @param config
	 */
	public MineSweeperBoard(GraphicsConfiguration config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

}
