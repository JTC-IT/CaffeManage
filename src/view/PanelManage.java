package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PanelManage extends JPanel {
	private static final long serialVersionUID = 1L;

	private MyButton btnAccount, btnTable, btnFood, btnShop;
	static final ImageIcon iconTable = new ImageIcon(PanelManage.class.getResource("/imgs/table-100.png"));
	static final ImageIcon iconFood = new ImageIcon(PanelManage.class.getResource("/imgs/typefood-100.png"));
	static final ImageIcon iconAccount = new ImageIcon(PanelManage.class.getResource("/imgs/account-100.png"));
	static final ImageIcon iconInfo = new ImageIcon(PanelManage.class.getResource("/imgs/infor-100.png"));
	
	public PanelManage() {
		setBorder(new EmptyBorder(40, 40, 40, 40));
		setLayout(new GridLayout(2, 2, 30, 30));
		setOpaque(false);
		
		btnTable = new MyButton();
		btnTable.setText("Bàn");
		btnTable.setIcon(iconTable);
		btnTable.setHorizontalAlignment(SwingConstants.CENTER);
		btnTable.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTable.setVerticalAlignment(SwingConstants.CENTER);
		btnTable.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelLeft(1);
				PanelManageLeft.setPanel(1);
				CafeHome.showPanelCenter(4);
				try {
					PanelTable2.refreshTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnTable.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnTable.setBackground(Color.decode("#50495f"));
			}
			public void mouseExited(MouseEvent e) {
				btnTable.setBackground(new Color(93,84,109));
			}
		});
		add(btnTable);
		
		btnFood = new MyButton();
		btnFood.setText("Món & Loại Món");
		btnFood.setIcon(iconFood);
		btnFood.setHorizontalAlignment(SwingConstants.CENTER);
		btnFood.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFood.setVerticalAlignment(SwingConstants.CENTER);
		btnFood.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelLeft(1);
				PanelManageLeft.setPanel(2);
				CafeHome.showPanelCenter(5);
				PanelFood2.refreshPanel();
			}
		});
		btnFood.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnFood.setBackground(Color.decode("#50495f"));
			}
			public void mouseExited(MouseEvent e) {
				btnFood.setBackground(new Color(93,84,109));
			}
		});
		add(btnFood);
		
		btnAccount = new MyButton();
		btnAccount.setText("Tài Khoản");
		btnAccount.setIcon(iconAccount);
		btnAccount.setHorizontalAlignment(SwingConstants.CENTER);
		btnAccount.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAccount.setVerticalAlignment(SwingConstants.CENTER);
		btnAccount.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelLeft(1);
				PanelManageLeft.setPanel(3);
				CafeHome.showPanelCenter(6);
				PanelAccount.setVisibleBtn(true);
				try {
					PanelAccount.refreshTable();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnAccount.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnAccount.setBackground(Color.decode("#50495f"));
			}
			public void mouseExited(MouseEvent e) {
				btnAccount.setBackground(new Color(93,84,109));
			}
		});
		add(btnAccount);
		
		btnShop = new MyButton();
		btnShop.setText("Thông Tin Quán");
		btnShop.setIcon(iconInfo);
		btnShop.setHorizontalAlignment(SwingConstants.CENTER);
		btnShop.setHorizontalTextPosition(SwingConstants.CENTER);
		btnShop.setVerticalAlignment(SwingConstants.CENTER);
		btnShop.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelLeft(1);
				PanelManageLeft.setPanel(4);
				CafeHome.showPanelCenter(7);
				CafeHome.setPanelShop();
			}
		});
		btnShop.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnShop.setBackground(Color.decode("#50495f"));
			}
			public void mouseExited(MouseEvent e) {
				btnShop.setBackground(new Color(93,84,109));
			}
		});
		add(btnShop);
	}
}