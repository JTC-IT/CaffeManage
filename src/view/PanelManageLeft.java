package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelManageLeft extends JPanel {
	private static final long serialVersionUID = 1L;

	private static JLabel lblType;
	
	public PanelManageLeft() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		lblType = new JLabel();
		lblType.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblType.setForeground(Color.yellow);
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		lblType.setHorizontalTextPosition(SwingConstants.CENTER);
		lblType.setVerticalAlignment(SwingConstants.CENTER);
		lblType.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(lblType, BorderLayout.CENTER);
		
		MyButton btnBack = new MyButton();
		btnBack.setIcon("/imgs/left-48.png");
		btnBack.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnBack.setBackground(Color.decode("#50495f"));
			}
			public void mouseExited(MouseEvent e) {
				btnBack.setBackground(new Color(93,84,109));
			}
		});
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.switchTabs(3);
			}
		});
		add(btnBack, BorderLayout.SOUTH);
		btnBack.setPreferredSize(new Dimension(0,70));
	}

	public static void setPanel(int k) {
		switch(k) {
		case 1:
			lblType.setText("Bàn");
			lblType.setIcon(PanelManage.iconTable);
			break;
		case 2:
			lblType.setText("Món & Loại món");
			lblType.setIcon(PanelManage.iconFood);
			break;
		case 3:
			lblType.setText("Tài khoản");
			lblType.setIcon(PanelManage.iconAccount);
			break;
		case 4:
			lblType.setText("Thông tin quán");
			lblType.setIcon(PanelManage.iconInfo);
			break;
		}
	}
}