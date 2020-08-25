import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SampleGraphics extends Canvas{
	
	public void paint(Graphics g) {
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = t.getImage("Images/sample1.jpg");
		g.drawImage(i, 120, 100,this);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SampleGraphics m = new SampleGraphics();
		final JFrame f = new JFrame();
		final JPanel p = new JPanel();
		final int dim = 10;
		
		p.setLayout(new GridLayout(dim, dim));
		
		for(int r = 0; r < dim; r++) {
			for(int c = 0; c < dim; c++) {
				p.add(new SampleButton(r, c));
			}
		}
		f.add(p);
		f.pack();
		f.setVisible(true);
		
	}

}

class SampleButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int gRow;
	private int gCol;
	private int gVal;
	private ImageIcon[] gImgs; 
	
	public SampleButton(int x, int y) throws IOException {
		gRow = x;
		gCol = y;
		gVal = 0;
		gImgs = new ImageIcon[] {new ImageIcon(ImageIO.read(new File("Images/10.png"))),
						        new ImageIcon(ImageIO.read(new File("Images/flag.png"))),
						        new ImageIcon(ImageIO.read(new File("Images/1.png")))};
		
		setIcon(gImgs[0]);
		
		addMouseListener(new MouseAdapter(){
			boolean lPressed;
			
			public void mousePressed(MouseEvent e) {
				getModel().setPressed(true);
				lPressed = true;
			}
			
			public void mouseReleased(MouseEvent e) {
				getModel().setPressed(false);
				
				if(lPressed) {
					if(SwingUtilities.isRightMouseButton(e)) {
						setIcon(gImgs[1]);
					}
					else {
						setIcon(gImgs[2]);
					}
				}
				lPressed = false;
			}
			
			
		});
		
		
	}
}
