import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * @author cjjin
 *
 */
public class MineSquare extends JButton {
	
	private final int pRow;
	private final int pCol;
	private final int pNumMinesNear;
	
	private boolean pPressed = false;
	private MineView pMineView = MineView.HIDDEN;
	private ImageIcon pImageView;
	
	/**
	 * @ param aNimMinesNear is set to pNumMinesNear
	 * @throws HeadlessException
	 */
	public MineSquare(int aRow, int aCol, int aNumMinesNear) {
		pRow = aRow;
		pCol = aCol;
		pNumMinesNear = aNumMinesNear;
		setIcon(new ImageIcon("Images/10.png"));
		
		//sets the size of the button to be the image
		setMargin(new Insets(0,0,0,0));
		setBorder(null);
		
		//adds a mouse listener to the square
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				getModel().setPressed(true);
				pPressed = true;
			}
			
			public void mouseReleased(MouseEvent e) {
				getModel().setPressed(false);
				
				if(pPressed) {
					if(SwingUtilities.isRightMouseButton(e)) { //deals with flagging and unflagging
						if(pMineView == MineView.FLAG) {
							pMineView = MineView.HIDDEN;
							setIcon(new ImageIcon("Images/10.png"));
						}
						else if(pMineView == MineView.HIDDEN) {
							pMineView = MineView.FLAG;
							setIcon(new ImageIcon("Images/flag.png"));
						}
					}
					else {
						if(pMineView == MineView.HIDDEN) { //deals with only revealing if hidden
							pMineView = MineView.OPEN;
							setIcon(new ImageIcon("Images/bomb.png"));
						}
					}
				}
				pPressed = false;
			}
		});
	}
	
	/**
	 * @ param new_icon is set to default
	 */
	public void setIcon(ImageIcon new_icon) {
		setIcon(new_icon);
	}
	
	public void setView(MineView aView) {
		pMineView = aView;
	}
	
	/*
	 * getter methods
	 */
	public int getRow() {
		return pRow;
	}
	
	public int getCol() {
		return pCol;
	}
	
	public int getNumMines() {
		return pNumMinesNear;
	}
}
