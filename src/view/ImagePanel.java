package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image img;
	
	public ImagePanel() {
		super();
	}
	public ImagePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}
	public ImagePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
	public ImagePanel(LayoutManager layout) {
		super(layout);
	}
	public void setImage(String path) {
		img = new ImageIcon(ImagePanel.class.getResource(path)).getImage();
	}
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}