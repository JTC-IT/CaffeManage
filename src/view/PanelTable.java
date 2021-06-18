package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bean.TableFood;
import bo.TableFoodBo;

public class PanelTable extends JPanel implements ActionListener{
	
	private class TableButton extends JButton{
		private static final long serialVersionUID = 1L;
		private int Id;
		private boolean isUsing;
		private String name;
		
		private ImageIcon imgPress, imgGreen, imgGray;
		private Image img;

		public TableButton(TableFood table) {
			
			imgPress = new ImageIcon(PanelTable.class.getResource("/imgs/presstb.png"));
			imgGreen = new ImageIcon(PanelTable.class.getResource("/imgs/greentb.png"));
			imgGray = new ImageIcon(PanelTable.class.getResource("/imgs/graytb.png"));
			
			Id = table.getId();
			setName(table.getName());
			setUsing(table.getStatus() == 1);
			
			setFont(new Font("Segoe UI", Font.BOLD, 18));
			setBorder(null);
			setForeground(Color.YELLOW);
			setContentAreaFilled(false);
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					setImage(imgPress);
				}
				public void mouseExited(MouseEvent e) {
					if(isUsing) {
						setImage(imgGreen);
					}else {
						setImage(imgGray);
					}
				}
				public void mouseReleased(MouseEvent e) {
					if(isUsing) {
						setImage(imgGreen);
					}else {
						setImage(imgGray);
					}
				}
			});
		}
		
		public int getId() {
			return Id;
		}
		
		public void setName(String s) {
			name = "Bàn "+s;
			repaint();
		}
		
		public void setUsing(boolean using) {
			isUsing = using;
			if(using) {
				setImage(imgGreen);
			}else {
				setImage(imgGray);
			}
		}
		
		public void setImage(ImageIcon m) {
			img = m.getImage();
			repaint();
		}
		
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		    g.drawString(name, (int)(getWidth()/2)-25, getHeight()/2+5);
		}
	}
	
	private static final long serialVersionUID = 1L;
	private static JPopupMenu popupMenu;
	private static JMenuItem itemOpen;
	private static JMenuItem itemMove;
	private static JMenuItem itemDel;
	private static TableFoodBo tbfoodBo;
	private static ArrayList<TableButton> listTable;
	private static int selectTb;
	private static boolean isMoveTable;
	private static int total, count;
	TitledBorder border;
	
	private static MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			if(isMoveTable) {
				setItemPopup(false);
				return;
			}
			setItemPopup(true);
			for(TableButton btn: listTable) {
				if(e.getSource() == btn) {
					selectTb = btn.getId();
				}
			}
		}
	};
	
	public void refreshBorder(String title) {
		border.setTitle(title);
		setBorder(new CompoundBorder(border, new EmptyBorder(10, 10, 10, 10)));
	}
	
	public PanelTable(TableFoodBo tbbo) throws SQLException {
		
		selectTb = 0;
		
		border = new TitledBorder(" Bàn ");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 25));
		border.setTitleColor(Color.white);
		refreshBorder(" Bàn ");
		
		setOpaque(false);
		
		tbfoodBo = tbbo;
		setPopupMenu();
		refeshPaneTable();
	}
	
	public void refeshPaneTable() {
		
		total = tbfoodBo.getSize();
		int n = (int) Math.sqrt(total);
		removeAll();
		setLayout(new GridLayout(n, n*2-1, 15, 15));
		listTable = new ArrayList<>();
		count = 0;
		for(TableFood table:  tbfoodBo.getListTableFood()) {
			if(table.getStatus() == -1)
				continue;
			TableButton btn = new TableButton(table);
			btn.addActionListener(this);
			if(table.getStatus() == 1) {
				btn.setComponentPopupMenu(popupMenu);
				btn.addMouseListener(mouseAdapter);
				count++;
			}
			listTable.add(btn);
			add(btn);
		}
		CafeHome.setlblBanUse(count,total);
	}
	
	public void addTable(TableFood table) {
		if(table.getStatus() == -1)
			return;
		TableButton btn = new TableButton(table);
		btn.addActionListener(this);
		listTable.add(btn);
		add(btn);
		total++;
		CafeHome.setlblBanUse(count,total);
	}
	
	public void upTable(TableFood table) {
		for(TableButton btn: listTable)
			if(btn.getId() == table.getId()) {
				if(table.getStatus() == -1) {
					remove(btn);
					listTable.remove(btn);
					total--;
				}else {
					btn.setName(table.getName());
				}
				CafeHome.setlblBanUse(count,total);
				return;
			}
		addTable(table);
	}
	
	public void actionPerformed(ActionEvent e) {
		for(TableButton btn: listTable) {
			if(e.getSource() == btn) {
				int id = btn.getId();
				if(isMoveTable) {
					isMoveTable = false;
					if(id != selectTb) {
						if(tbfoodBo.getTableFood(id).getStatus() == 0) {
							if(Main.checkConfirm("Chuyển bàn", "Bàn "+tbfoodBo.getTableFood(selectTb).getName()+" chuyển tới Bàn "+tbfoodBo.getTableFood(id).getName()+"?")) {
								PanelBill.moveTable(selectTb, id);
								tbfoodBo.setStatus(selectTb, 0);
								setStatusTable(id, 1);
								setStatusTable(selectTb, 0);
							}
						}else if(Main.checkConfirm("Gộp bàn", "Bàn "+tbfoodBo.getTableFood(selectTb).getName()+" gộp vào Bàn"+tbfoodBo.getTableFood(id).getName()+"?")) {
							PanelBill.megreTable(selectTb, id);
							setStatusTable(selectTb, 0);
						}
					}
					refreshBorder(" Bàn ");
				}else {
					PanelSellTab.setVisibleItemFcategory(true);
					PanelBill.setPaneHead(tbfoodBo.getTableFood(id));
				}
			}
		}
	}
	
	public void setPopupMenu() {
		Font font = new Font("Segoe UI", Font.BOLD, 14);
		popupMenu = new JPopupMenu();
		popupMenu.setBackground(new Color(238,234,174));
		
		itemOpen = new JMenuItem("Vào bàn");
		itemOpen.setBackground(new Color(238,234,174));
		itemOpen.setForeground(Color.black);
		itemOpen.setFont(font);
		itemOpen.setHorizontalAlignment(SwingConstants.CENTER);
		itemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelSellTab.setVisibleItemFcategory(true);
				PanelBill.setPaneHead(tbfoodBo.getTableFood(selectTb));
			}
		});
		popupMenu.add(itemOpen);
		
		itemMove = new JMenuItem("Chuyển bàn");
		itemMove.setBackground(new Color(238,234,174));
		itemMove.setForeground(Color.black);
		itemMove.setFont(font);
		itemMove.setHorizontalAlignment(SwingConstants.CENTER);
		itemMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isMoveTable = true;
				refreshBorder(" Chọn bàn chuyển tới: ");
			}
		});
		popupMenu.add(itemMove);
		
		itemDel = new JMenuItem("Hủy bàn");
		itemDel.setBackground(new Color(238,234,174));
		itemDel.setForeground(Color.red);
		itemDel.setFont(font);
		itemDel.setHorizontalAlignment(SwingConstants.CENTER);
		itemDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PanelBill.deleteBill(selectTb);
			}
		});
		popupMenu.add(itemDel);
	}
	
	public static void setStatusTable(int id, int status) {
		tbfoodBo.setStatus(id, status);
		for(TableButton btn: listTable)
			if(btn.getId() == id) {
				if(status == 1) {
					PanelBill.setPaneHead(tbfoodBo.getTableFood(id));
					btn.setUsing(true);
					btn.setComponentPopupMenu(popupMenu);
					btn.addMouseListener(mouseAdapter);
					count++;
				}else {
					btn.setUsing(false);
					btn.setComponentPopupMenu(null);
					btn.removeMouseListener(mouseAdapter);
					count--;
				}
				CafeHome.setlblBanUse(count,total);
				return;
			}
	}
	
	public static void setItemPopup(boolean b) {
		itemOpen.setEnabled(b);
		itemMove.setEnabled(b);
		itemDel.setEnabled(b);
	}
}