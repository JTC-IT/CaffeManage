package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MyButton extends JButton{
	private static final long serialVersionUID = 1L;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MyButton() {
		setBackground(new Color(93,84,109));
		setFont(new Font("Segoe UI", Font.BOLD, 20));
		setForeground(Color.orange);
		setHorizontalAlignment(SwingConstants.CENTER);
		setFocusPainted(false);
		setBorderPainted(false);
	}
	public void setIcon(String url) {
		super.setIcon(new ImageIcon(MyButton.class.getResource(url)));
	}
	public void setFont(int size) {
		setFont(new Font("Segoe UI", Font.BOLD, size));
	}
}