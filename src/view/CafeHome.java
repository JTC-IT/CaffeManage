package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import bean.Account;
import bean.Food;
import bean.TableFood;
import bo.FoodBo;
import bo.TableFoodBo;

public class CafeHome extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static JPanel panelLeft, panelCenter, PanelRight, PanelBottom;
	private static JPanel[] listPanelLeft, listPanelCenter, listPanelRight;
	private static JLabel lblBanUse;
	private static PanelFood paneFood;
	private static PanelTable paneTable;
	private static PanelTableDetails tableDetails;
	private static PanelFoodDetails foodDetails;
	private static PanelAccountDetails accountDetails;
	private static PanelShop panelShop;
	private static PanelStatistics panelStatistics;
	private static PanelBill panelBill;
	
	private FoodBo fb;
	private TableFoodBo tbBo;
	
	public CafeHome() throws SQLException {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		fb = new FoodBo();
		tbBo = new TableFoodBo();
		
		setPanelBottom();
		setPanelCenter();
		setPanelRight();
		setPanelLeft();
	}
	
	public void setPanelLeft() throws SQLException {
		panelLeft = new JPanel();
		panelLeft.setBackground(new Color(122,111,143));
		panelLeft.setLayout(new CardLayout());
		add(panelLeft, BorderLayout.WEST);
		panelLeft.setPreferredSize(new Dimension(196,0));
		
		listPanelLeft = new JPanel[]{
				new PanelSellTab()
				, new PanelManageLeft()
		};
		for(int i = 0; i < listPanelLeft.length; i++)
			panelLeft.add(listPanelLeft[i]);
	}
	
	public void setPanelCenter() throws SQLException {
		panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		panelCenter.setLayout(new CardLayout());
		add(panelCenter, BorderLayout.CENTER);
		
		paneFood = new PanelFood(fb);
		paneTable = new PanelTable(tbBo);
		panelStatistics = new PanelStatistics();
		panelShop = new PanelShop();
		
		listPanelCenter = new JPanel[] {
				paneTable
				, paneFood
				, panelStatistics
				, new PanelManage()
				, new PanelTable2(tbBo)
				, new PanelFood2(fb)
				, new PanelAccount()
				, panelShop
		};
		for(int i = 0; i < listPanelCenter.length; i++)
			panelCenter.add(listPanelCenter[i]);
	}
	
	public void setPanelBottom() {
		PanelBottom = new JPanel();
		PanelBottom.setLayout(new BorderLayout());
		PanelBottom.setBackground(Color.decode("#5D546D"));
		PanelBottom.setPreferredSize(new Dimension(0,25));
		add(PanelBottom, BorderLayout.SOUTH);
		
		Clock clock = new Clock();
		Thread t = new Thread(clock);
		t.start();
		PanelBottom.add(clock, BorderLayout.EAST);
		clock.setPreferredSize(new Dimension(180,0));
		
		lblBanUse = new JLabel();
		lblBanUse.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblBanUse.setForeground(Color.white);
		lblBanUse.setHorizontalAlignment(SwingConstants.CENTER);
		PanelBottom.add(lblBanUse, BorderLayout.CENTER);
	}
	
	public void setPanelRight() throws SQLException {
		PanelRight = new JPanel();
		PanelRight.setLayout(new CardLayout());
		PanelRight.setBackground(new Color(122,111,143));
		add(PanelRight, BorderLayout.EAST);
		
		panelBill = new PanelBill(fb);
		tableDetails = new PanelTableDetails();
		foodDetails = new PanelFoodDetails();
		accountDetails = new PanelAccountDetails();
		
		
		listPanelRight = new JPanel[] {
				panelBill
				, new PanelPay()
				, tableDetails
				, foodDetails
				, accountDetails
		};
		for(int i = 0; i < listPanelRight.length; i++)
			PanelRight.add(listPanelRight[i]);
	}
	
	public static void switchTabs(int k) {
		switch(k) {
		case 1:
			showPanelLeft(0);
			showPanelCenter(0);
			PanelSellTab.showTableFood();
			break;
		case 2: 
			showPanelLeft(-1);
			showPanelCenter(2);
			showPanelRight(-1);
			break;
		case 3: 
			showPanelLeft(-1);
			showPanelCenter(3);
			showPanelRight(-1);
			break;
	}
	}
	
	public static void showPanelLeft(int k) {
		if(k < 0) {
			panelLeft.setVisible(false);
		}else {
			panelLeft.setVisible(true);
			for(int i = 0; i < listPanelLeft.length; i++)
				if(k == i)
					listPanelLeft[i].setVisible(true);
				else listPanelLeft[i].setVisible(false);
		}
	}
	
	public static void showPanelCenter(int k) {
		for(int i = 0; i < listPanelCenter.length; i++)
			if(k == i) {
				listPanelCenter[i].setVisible(true);
			}else
				listPanelCenter[i].setVisible(false);
		if(k == 2)
			panelStatistics.LoadForm();
	}
	
	public static void showPanelRight(int k) {
		if(k < 0) {
			PanelRight.setVisible(false);
		}else {
			PanelRight.setVisible(true);
			int len = listPanelRight.length;
			for(int i = 0; i < len; i++)
				if(i != k)
					listPanelRight[i].setVisible(false);
			listPanelRight[k].setVisible(true);
		}
	}
	
	public static void setChangeFoodData(String type, Food food) {
		switch(type) {
		case "add":
			paneFood.addFood(food);
			break;
		case "up":
			paneFood.upFood(food);
			break;
		case "del":
			paneFood.delFood(food.getId());
			break;
		}
	}
	
	public static void setChangeTableData(String type, TableFood table) {
		switch(type) {
		case "add":
			paneTable.addTable(table);
			break;
		case "up":
			paneTable.upTable(table);
			break;
		}
	}
	
	public static void setlblBanUse(int k,int n) {
		lblBanUse.setText("Đang phục vụ: "+k+"/"+n);
	}
	
	public static void setPaneTableDetails(TableFood table) {
		tableDetails.setPanel(table);
	}
	
	public static void setPaneFoodDetails(Food food) {
		foodDetails.setPanel(food);
	}
	
	public static void setPanelAccountDetails(Account account) {
		accountDetails.setPanel(account);
	}
	
	public static void setPanelShop() {
		panelShop.setPanel();
	}
}
