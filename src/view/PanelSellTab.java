package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import bean.FoodCategory;
import bo.FoodCategoryBo;

public class PanelSellTab extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private FoodCategoryBo fcBo;
	private static MyButton itemTable;
	private static MyButton[] listItemFCategory;
	
	private static int item;
	private static int fcsize;
	
	public PanelSellTab() throws SQLException {
		fcBo = new FoodCategoryBo();
		
		setOpaque(false);
		
		itemTable = new MyButton();
		itemTable.setText("Bàn");
		itemTable.setHorizontalAlignment(SwingConstants.LEFT);
		itemTable.setFont(22);
		itemTable.setIcon("/imgs/table-48.png");
		itemTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTableFood();
			}
		});
		itemTable.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if(item != -1) {
					itemTable.setBackground(Color.decode("#5d546d"));
				}
			}
			public void mouseExited(MouseEvent e) {
				if(item != -1) {
					itemTable.setBackground(new Color(122,111,143));
				}
			}
		});
		refeshTab();
	}
	
	public void refeshTab() {
		item = -2;
		fcsize = fcBo.getListFoodCategory().size();
		
		removeAll();
		setLayout(new GridLayout(1+fcsize, 1, 0, 0));
		
		itemTable.setForeground(Color.white);
		itemTable.setBackground(Color.decode("#383342"));
		add(itemTable);
		
		listItemFCategory = new MyButton[fcsize];
		int i = 0;
		for(FoodCategory fc: fcBo.getListFoodCategory()) {
			listItemFCategory[i] = new MyButton();
			listItemFCategory[i].setText(fc.getName());
			listItemFCategory[i].setId(fc.getId());
			listItemFCategory[i].setForeground(Color.green);
			listItemFCategory[i].setFont(18);
			listItemFCategory[i].setBackground(new Color(122,111,143));
			listItemFCategory[i].addActionListener(this);
			listItemFCategory[i].addMouseListener(mouseAdapter);
			
			add(listItemFCategory[i]);
			
			i++;
		}
		showTableFood();
	}
	
	public static void showTableFood() {
		if(item != -1) {
			if(item > -1) {
				listItemFCategory[item].setBackground(new Color(122,111,143));
				listItemFCategory[item].setForeground(Color.green);
			}
			itemTable.setForeground(Color.white);
			itemTable.setBackground(Color.decode("#383342"));
			
			setVisibleItemFcategory(false);
			CafeHome.showPanelCenter(0);
			item = -1;
		}
	}
	
	public static void setVisibleItemFcategory(boolean b) {
		for(int i = 0; i < fcsize; i++) {
			listItemFCategory[i].setVisible(b);
		}
		if(b) {
			CafeHome.showPanelRight(0);
			CafeHome.showPanelCenter(1);
			showFoodCategory(0);
		}else {
			CafeHome.showPanelRight(-1);
		}
	}
	
	public static void showFoodCategory(int i) {
		if(i != item) {
			if(item == -1) {
				itemTable.setForeground(Color.yellow);
				itemTable.setBackground(new Color(122,111,143));
			}else {
				listItemFCategory[item].setBackground(new Color(122,111,143));
				listItemFCategory[item].setForeground(Color.green);
			}
			listItemFCategory[i].setBackground(Color.decode("#383342"));
			listItemFCategory[i].setForeground(Color.white);
			
			PanelFood.showFoodOfCategory(listItemFCategory[i].getId());
			
			item = i;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < fcsize; i++) {
			if(e.getSource() == listItemFCategory[i]) {
				showFoodCategory(i);
			}
		}
	}
	
	MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			for(int i = 0; i < fcsize; i++) {
				if(e.getSource() == listItemFCategory[i]) {
					if(i != item)
						listItemFCategory[i].setBackground(Color.decode("#5d546d"));
				}
			}
		}
		public void mouseExited(MouseEvent e) {
			for(int i = 0; i < fcsize; i++) {
				if(e.getSource() == listItemFCategory[i]) {
					if(i != item)
						listItemFCategory[i].setBackground(new Color(122,111,143));
				}
			}
		}
	};
}
