import java.io.IOException;

public class MineSweeperOutput {

	public static void main(String[] args) throws IOException {
		MineSweeper game = new MineSweeper();
		game.initializeBoard();
		
		game.run();
	}

}
