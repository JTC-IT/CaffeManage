package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import bean.TimeFormat;

public class Clock extends JLabel implements Runnable{
	private static final long serialVersionUID = 1L;
	Thread t;

	public Clock(){
		setFont(new Font("Segoe UI", Font.BOLD, 14));
		setForeground(Color.white);
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(new MatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
	}
	@Override
	public void run() {
		while(true) {
			try {
				setText(TimeFormat.format(new Date()));
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
