import static org.junit.Assert.*;

import org.junit.Test;

public class MineSweeperTest {

	@Test
	public void testBoard() {
		MineSweeper game = new MineSweeper();
		assertEquals(10, game.getBoard().length);
		assertEquals(0, game.getBoard()[5][5]);
	}
	
	public void testMine() {
		MineSweeper game = new MineSweeper();
		game.initializeBoard();
		int countMines = 0;
		for(int[] row: game.getBoard()) {
			for(int square: row) {
				if(square == 9) {
					countMines++;
				}
			}
		}
		System.out.print(game.getBoard());
		assertEquals(10, countMines);
	}

}
