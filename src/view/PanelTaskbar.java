package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class PanelTaskbar extends JPanel {

	private static final long serialVersionUID = 1L;
	private MyButton ItemSell, ItemStatistics, ItemManage;
	private JPanel paneInfor;
	private JLabel lblUser;
	private JButton btnLogOut;
	
	private int itemShow = 0;
	
	public PanelTaskbar() {
		setBackground(new Color(93,84,109));
		setLayout(new BorderLayout());
		
		ImagePanel plogo = new ImagePanel();
		plogo.setImage("/imgs/head.jpg");
		plogo.setPreferredSize(new Dimension(196,0));
		add(plogo,BorderLayout.WEST);

		JPanel panel0 = new JPanel();
		panel0.setOpaque(false);
		panel0.setLayout(new GridLayout(1,4));
		add(panel0, BorderLayout.CENTER);
		
		ItemSell = new MyButton();
		ItemSell.setText("Bán hàng");
		ItemSell.setIcon("/imgs/shopping-48.png");
		ItemSell.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if(itemShow != 1) {
					ItemSell.setBackground(Color.decode("#50495f"));
				}
			}
			public void mouseExited(MouseEvent e) {
				if(itemShow != 1) {
					ItemSell.setBackground(new Color(93,84,109));
				}
			}
		});
		ItemSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickItem(1);
			}
		});
		panel0.add(ItemSell);
		
		ItemStatistics = new MyButton();
		ItemStatistics.setText("Thống kê");
		ItemStatistics.setIcon("/imgs/statistics-48.png");
		ItemStatistics.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if(itemShow != 2) {
					ItemStatistics.setBackground(Color.decode("#50495f"));
				}
			}
			public void mouseExited(MouseEvent e) {
				if(itemShow != 2) {
					ItemStatistics.setBackground(new Color(93,84,109));
				}
			}
		});
		ItemStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickItem(2);
			}
		});
		panel0.add(ItemStatistics);
		
		ItemManage = new MyButton();
		ItemManage.setText("Quản lý");
		ItemManage.setIcon("/imgs/manager-48.png");
		ItemManage.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if(itemShow != 3) {
					ItemManage.setBackground(Color.decode("#50495f"));
				}
			}
			public void mouseExited(MouseEvent e) {
				if(itemShow != 3) {
					ItemManage.setBackground(new Color(93,84,109));
				}
			}
		});
		ItemManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickItem(3);
			}
		});
		panel0.add(ItemManage);
		
		SpringLayout layout = new SpringLayout();
		paneInfor = new JPanel();
		paneInfor.setOpaque(false);
		paneInfor.setLayout(layout);
		paneInfor.setPreferredSize(new Dimension(0,100));
		panel0.add(paneInfor);
		
		lblUser = new JLabel();
		lblUser.setIcon(new ImageIcon(PanelTaskbar.class.getResource("/imgs/user-30.png")));
		lblUser.setForeground(Color.orange);
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblUser.setHorizontalAlignment(SwingConstants.LEFT);
		paneInfor.add(lblUser);
		layout.putConstraint(SpringLayout.EAST, lblUser, -15, SpringLayout.EAST, paneInfor);
        layout.putConstraint(SpringLayout.NORTH, lblUser, 10, SpringLayout.NORTH, paneInfor);
        
        btnLogOut = new JButton("Đăng xuất");
        btnLogOut.setIcon(new ImageIcon(PanelTaskbar.class.getResource("/imgs/exit-28.png")));
        btnLogOut.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogOut.setForeground(Color.white);
        btnLogOut.setContentAreaFilled(false);
        btnLogOut.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btnLogOut.setForeground(Color.red);
			}
			public void mouseExited(MouseEvent e) {
				btnLogOut.setForeground(Color.white);
			}
		});
		
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Main.checkConfirm("Đăng xuất!", "Bạn có muốn đăng xuất không?")) {
					Main.setAccount(null);
					Main.setFrame(false);
				}
			}
		});
		paneInfor.add(btnLogOut);
		layout.putConstraint(SpringLayout.EAST, btnLogOut, -15, SpringLayout.EAST, paneInfor);
        layout.putConstraint(SpringLayout.SOUTH, btnLogOut, -10, SpringLayout.SOUTH, paneInfor);
	}
	
	public void exitItem(int k) {
		switch(k) {
			case 1: ItemSell.setBackground(new Color(93,84,109));
					ItemSell.setForeground(Color.orange);
			break;
			case 2: ItemStatistics.setBackground(new Color(93,84,109));
					ItemStatistics.setForeground(Color.orange);
			break;
			case 3: ItemManage.setBackground(new Color(93,84,109));
					ItemManage.setForeground(Color.orange);
			break;
		}
	}
	public void clickItem(int k) {
		if(k != itemShow) {
			switch(k) {
				case 1: ItemSell.setBackground(Color.decode("#383342"));
						ItemSell.setForeground(Color.white);
				break;
				case 2: ItemStatistics.setBackground(Color.decode("#383342"));
						ItemStatistics.setForeground(Color.white);
				break;
				case 3: ItemManage.setBackground(Color.decode("#383342"));
						ItemManage.setForeground(Color.white);
				break;
			}
			exitItem(itemShow);
			itemShow = k;
			CafeHome.switchTabs(k);
		}
	}
	
	public void setUser(String username) {
		lblUser.setText(username);
	}
	
	public void setTaskbar(boolean admin, String fullname) {
		if(admin) {
			setUser("QTV: "+fullname);
			ItemManage.setVisible(true);
		}else {
			setUser("Nhân viên: "+fullname);
			ItemManage.setVisible(false);
		}
	}
}
