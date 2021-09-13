package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import bean.Bill;
import bean.Food;
import bean.FormatMoney;
import bean.TableFood;
import bean.TimeFormat;
import bo.BillBo;
import bo.FoodBo;

public class PanelBill extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPopupMenu popupMenu;
	private static JLabel lblTable, lblStatus, lblTime, lblSum, lblThanhTien
	, lblPayer1, lblPayer2, lblRecost1, lblRecost2;
	private static JButton btnDiscount, btnDel, btnPay, btnSave, btnIn;
	private static JPanel paneHead, paneListFood, panePay;
	private static DefaultTableModel modelTb;
	private static JTable table;
	private InBill inbill;
	private static BillBo billBo;
	
	private JMenuItem itemDel, itemAdd, itemSub;
	
	public PanelBill(FoodBo fb) throws SQLException {
		setOpaque(false);
		setLayout(new BorderLayout());
		setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		setPreferredSize(new Dimension(450,0));
		
		billBo = new BillBo(fb);
		inbill = new InBill();
		setModelTable();
		new_PanePay();
		new_PaneListFood();
		setPopupMenu();
		new_PaneHead();
	}

	public void new_PaneHead() {
		paneHead = new JPanel();
		paneHead.setOpaque(false);
		paneHead.setBorder(new EmptyBorder(5,10,5,10));
		paneHead.setLayout(new GridLayout(3, 1));
		paneHead.setPreferredSize(new Dimension(0,100));
		add(paneHead, BorderLayout.NORTH);
		
		lblTable = new JLabel();
		lblTable.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblTable.setForeground(Color.orange);
		paneHead.add(lblTable);
		
		lblStatus = new JLabel();
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblStatus.setForeground(Color.white);
		paneHead.add(lblStatus);
		
		lblTime = new JLabel();
		lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblTime.setForeground(Color.white);
		paneHead.add(lblTime);
	}
	
	public void new_PaneListFood() {
		paneListFood = new JPanel();
		paneListFood.setOpaque(false);
		paneListFood.setLayout(new BorderLayout());
		add(paneListFood, BorderLayout.CENTER);
		
		table = new JTable(){
			private static final long serialVersionUID = 1L;
	        @Override
			public boolean isCellEditable(int row, int column) {                
	                return false;
	        };
		};
		table.setForeground(Color.white);
		table.setRowHeight(25);
		table.setBackground(new Color(122,111,143));
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(122,111,143));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBorder(new MatteBorder(1, 0, 1, 0, Color.white));
		table.getTableHeader().setPreferredSize(new Dimension(0, 25));
		table.setModel(modelTb);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				if(billBo.getBill() == null) {
					setItemPopup(false);
					return;
				}
				setItemPopup(true);
		        Point point = event.getPoint();
		        int currentRow = table.rowAtPoint(point);
		        table.setRowSelectionInterval(currentRow, currentRow);
		    }
		});
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(JLabel.CENTER);
		
		TableColumnModel column = table.getColumnModel();
		column.getColumn(0).setPreferredWidth(25);
		column.getColumn(0).setCellRenderer(dtcr);
		column.getColumn(1).setPreferredWidth(170);
		column.getColumn(2).setPreferredWidth(50);
		column.getColumn(2).setCellRenderer(dtcr);
		column.getColumn(3).setPreferredWidth(30);
		column.getColumn(3).setCellRenderer(dtcr);
		column.getColumn(4).setPreferredWidth(60);
		column.getColumn(4).setCellRenderer(dtcr);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(new Color(122,111,143));
		scrollPane.getVerticalScrollBar().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBorder(null);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,0));
		paneListFood.add(scrollPane);
	}
	
	public void new_PanePay() {
		SpringLayout layout = new SpringLayout();
		
		panePay = new JPanel();
		panePay.setOpaque(false);
		panePay.setBorder(new EmptyBorder(5, 10, 0, 10));
		panePay.setLayout(layout);
		panePay.setPreferredSize(new Dimension(0,250));
		add(panePay, BorderLayout.SOUTH);
		
		JLabel lbl1 = new JLabel("Tổng tiền:");
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl1.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl1.setForeground(Color.white);
		panePay.add(lbl1);
		layout.putConstraint(SpringLayout.WEST, lbl1, 0, SpringLayout.WEST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lbl1, 5, SpringLayout.NORTH, panePay);
		
		JLabel lbl2 = new JLabel("Giảm giá:");
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl2.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl2.setForeground(Color.white);
		panePay.add(lbl2);
		layout.putConstraint(SpringLayout.WEST, lbl2, 0, SpringLayout.WEST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lbl2, 35, SpringLayout.NORTH, panePay);
		
		JLabel lbl3 = new JLabel("Thành tiền:");
		lbl3.setFont(new Font("Tahoma", Font.BOLD, 22));
		lbl3.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl3.setForeground(Color.white);
		panePay.add(lbl3);
		layout.putConstraint(SpringLayout.WEST, lbl3, 0, SpringLayout.WEST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lbl3, 67, SpringLayout.NORTH, panePay);
		
		lblSum = new JLabel();
		lblSum.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSum.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSum.setForeground(Color.white);
		panePay.add(lblSum);
		layout.putConstraint(SpringLayout.EAST, lblSum, 0, SpringLayout.EAST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lblSum, 5, SpringLayout.NORTH, panePay);
		
		btnDiscount = new JButton(new ImageIcon(PanelBill.class.getResource("/imgs/edit-16.png")));
		btnDiscount.setPressedIcon(new ImageIcon(PanelBill.class.getResource("/imgs/edit2-16.png")));
		btnDiscount.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDiscount.setVerticalAlignment(SwingConstants.BOTTOM);
		btnDiscount.setForeground(Color.white);
		btnDiscount.setContentAreaFilled(false);
		btnDiscount.setBorder(null);
		btnDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(billBo.getBill() != null) {
					try {
						String discount = JOptionPane.showInputDialog(
							null
							, "Chọn giảm giá:"
							, "Giảm giá"
							, JOptionPane.PLAIN_MESSAGE
							, null
							, new Object[] {"0%","5%","10%","15%","20%","25%","30%","35%","40%","45%","50%","80%"}
							, ((int)(billBo.getBill().getDiscount()*100))+"%").toString().replace("%", "");
					
						billBo.updateBill(Main.getAccount().getId(), (Float.parseFloat(discount)/100), false);
						setlblPay();
					} catch (Exception e2) {
					}
				}
			}
		});
		panePay.add(btnDiscount);
		layout.putConstraint(SpringLayout.EAST, btnDiscount, 0, SpringLayout.EAST, panePay);
		layout.putConstraint(SpringLayout.NORTH, btnDiscount, 35, SpringLayout.NORTH, panePay);
		
		lblThanhTien = new JLabel();
		lblThanhTien.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblThanhTien.setVerticalAlignment(SwingConstants.BOTTOM);
		lblThanhTien.setForeground(Color.white);
		panePay.add(lblThanhTien);
		layout.putConstraint(SpringLayout.EAST, lblThanhTien, 0, SpringLayout.EAST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lblThanhTien, 67, SpringLayout.NORTH, panePay);
		
		lblPayer1 = new JLabel("Tiền khách đưa:");
		lblPayer1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPayer1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPayer1.setForeground(Color.white);
		panePay.add(lblPayer1);
		layout.putConstraint(SpringLayout.WEST, lblPayer1, 0, SpringLayout.WEST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lblPayer1, 105, SpringLayout.NORTH, panePay);
		
		lblPayer2 = new JLabel();
		lblPayer2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPayer2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPayer2.setForeground(Color.white);
		panePay.add(lblPayer2);
		layout.putConstraint(SpringLayout.EAST, lblPayer2, 0, SpringLayout.EAST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lblPayer2, 105, SpringLayout.NORTH, panePay);
		
		lblRecost1 = new JLabel("Tiền trả khách:");
		lblRecost1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRecost1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblRecost1.setForeground(Color.white);
		panePay.add(lblRecost1);
		layout.putConstraint(SpringLayout.WEST, lblRecost1, 0, SpringLayout.WEST, panePay);
		layout.putConstraint(SpringLayout.EAST, lblRecost1, 0, SpringLayout.EAST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lblRecost1, 132, SpringLayout.NORTH, panePay);
		
		lblRecost2 = new JLabel("");
		lblRecost2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRecost2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblRecost2.setForeground(Color.white);
		panePay.add(lblRecost2);
		layout.putConstraint(SpringLayout.EAST, lblRecost2, 0, SpringLayout.EAST, panePay);
		layout.putConstraint(SpringLayout.NORTH, lblRecost2, 132, SpringLayout.NORTH, panePay);
		
		Font fontbtn = new Font("Segoe UI", Font.BOLD, 15);
		btnSave = new JButton("Sao lưu", new ImageIcon(PanelBill.class.getResource("/imgs/save-24.png")));
		btnSave.setFont(fontbtn);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				billBo.getBillInforBo().setEdit(false);
				saveBill(false);
			}
		});
		btnSave.setForeground(Color.decode("#0D61A9"));
		panePay.add(btnSave);
		btnSave.setPreferredSize(new Dimension(140,30));
		layout.putConstraint(SpringLayout.EAST, btnSave, -5, SpringLayout.HORIZONTAL_CENTER, panePay);
		layout.putConstraint(SpringLayout.SOUTH, btnSave, -50, SpringLayout.SOUTH, panePay);
		
		btnIn = new JButton("In bill", new ImageIcon(PanelBill.class.getResource("/imgs/printer-24.png")));
		btnIn.setFont(fontbtn);
		btnIn.setPreferredSize(new Dimension(140,30));
		btnIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inbill.PrintBill(billBo.getBill().getId());
			}
		});
		panePay.add(btnIn);
		layout.putConstraint(SpringLayout.WEST, btnIn, 5, SpringLayout.HORIZONTAL_CENTER, panePay);
		layout.putConstraint(SpringLayout.SOUTH, btnIn, -50, SpringLayout.SOUTH, panePay);
		
		btnDel = new JButton("Hủy bill", new ImageIcon(PanelBill.class.getResource("/imgs/del-24.png")));
		btnDel.setFont(fontbtn);
		btnDel.setForeground(Color.RED);
		btnDel.setPreferredSize(new Dimension(170,30));
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBill(billBo.getTableId());
			}
		});
		panePay.add(btnDel);
		layout.putConstraint(SpringLayout.EAST, btnDel, -5, SpringLayout.HORIZONTAL_CENTER, panePay);
		layout.putConstraint(SpringLayout.SOUTH, btnDel, -10, SpringLayout.SOUTH, panePay);
		
		btnPay = new JButton("Thanh toán", new ImageIcon(PanelBill.class.getResource("/imgs/pay-24.png")));
		btnPay.setFont(fontbtn);
		btnPay.setForeground(Color.decode("#0D61A9"));
		btnPay.setPreferredSize(new Dimension(170,30));
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelRight(1);
				PanelPay.setInput(billBo.ThanhTien());
			}
		});
		panePay.add(btnPay);
		layout.putConstraint(SpringLayout.WEST, btnPay, 5, SpringLayout.HORIZONTAL_CENTER, panePay);
		layout.putConstraint(SpringLayout.SOUTH, btnPay, -10, SpringLayout.SOUTH, panePay);
	}
	
	public static void setPaneHead(TableFood tbFood) {
		setVisibleLblPay(false);
		billBo.setTableId(tbFood.getId());
		Bill bill = billBo.getBill();
		lblTable.setText("Bàn "+tbFood.getName());
		if(tbFood.getStatus() == 1) {
			lblStatus.setText("<html><b><u>Trạng thái</u>:</b> Đang phục vụ !</html>");
			lblTime.setText("<html><b><u>Giờ đến</u>:</b> "+TimeFormat.format(bill.getTimeIn())+"</html>");
			refeshModelTable();
		}
		else{
			lblStatus.setText("<html><b><u>Trạng thái</u>:</b> Trống !</html>");
			modelTb.setRowCount(0);
			lblTime.setText("<html><b><u>Giờ đến</u>:</b></html>");
			setVisibleLbl(false);
		}
		refreshBtn();
	}
	
	
	public void setModelTable() {
		modelTb = new DefaultTableModel();
		modelTb.addColumn("STT");
		modelTb.addColumn("Tên món");
		modelTb.addColumn("Đ.Giá");
		modelTb.addColumn("SL");
		modelTb.addColumn("T.Tiền");
	}
	
	public static void refeshModelTable() {
		ArrayList<Object[]> list = billBo.getBillInfor();
		modelTb.setRowCount(0);
		for(Object[] row: list) {
			modelTb.addRow(row);
		}
		setlblPay();
	}
	
	public static void addFood(Food food) throws SQLException {
		if(billBo.getBill() == null) {
			if(Main.checkConfirm("Add Bill", "Tạo hóa đơn mới?")) {
				setVisibleLblPay(false);
				billBo.addBill(Main.getAccount().getId());
				PanelTable.setStatusTable(billBo.getTableId(), 1);
			} else return;
		}
		billBo.getBillInforBo().setEdit(true);
		if(billBo.getBillInforBo().addBillInfor(food.getId())) {
			int n = modelTb.getRowCount();
			modelTb.addRow(new Object[] {n+1
					, food.getName()
					, FormatMoney.formatVnd(food.getPrice())
					, 1
					, FormatMoney.formatVnd(food.getPrice())});
			setlblPay();
		}else refeshModelTable();
		refreshBtn();
	}
	
	public static void saveBill(boolean b) {
		billBo.getBillInforBo().saveBillInfor();
		billBo.updateBill(Main.getAccount().getId(), billBo.getBill().getDiscount(), b);
		refreshBtn();
	}
	
	public static void moveTable(int tableFrom, int tableTo){
		billBo.setTableId(tableFrom);
		billBo.moveTable(tableTo);
	}
	
	public static void megreTable(int tableFrom, int tableTo) {
		billBo.setTableId(tableTo);
		billBo.megreBill(tableFrom);
	}
	
	public static void setlblPay() {
		setVisibleLbl(true);
		lblSum.setText(FormatMoney.formatVnd(billBo.getSumCost()));
		btnDiscount.setText((int)(billBo.getBill().getDiscount()*100)+"%");
		lblThanhTien.setText(FormatMoney.formatVnd(billBo.ThanhTien()));
	}
	
	public static void setVisibleLbl(boolean b) {
		lblSum.setVisible(b);
		btnDiscount.setVisible(b);
		lblThanhTien.setVisible(b);
	}
	
	public void setPopupMenu() {
		Font font = new Font("Segoe UI", Font.BOLD, 14);
		popupMenu = new JPopupMenu();
		popupMenu.setBackground(Main.myColor);
		
		itemAdd = new JMenuItem("+");
		itemAdd.setBackground(Main.myColor);
		itemAdd.setForeground(Color.white);
		itemAdd.setFont(font);
		itemAdd.setHorizontalAlignment(SwingConstants.CENTER);
		itemAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				billBo.getBillInforBo().addAmount(row, 1);
				refeshModelTable();
				refreshBtn();
			}
		});
		popupMenu.add(itemAdd);
		
		itemSub = new JMenuItem("-");
		itemSub.setBackground(Main.myColor);
		itemSub.setForeground(Color.white);
		itemSub.setFont(font);
		itemSub.setHorizontalAlignment(SwingConstants.CENTER);
		itemSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				billBo.getBillInforBo().addAmount(row, -1);
				refeshModelTable();
				refreshBtn();
			}
		});
		popupMenu.add(itemSub);
		
		itemDel = new JMenuItem("X");
		itemDel.setBackground(Main.myColor);
		itemDel.setForeground(Color.red);
		itemDel.setFont(font);
		itemDel.setHorizontalAlignment(SwingConstants.CENTER);
		itemDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				billBo.getBillInforBo().deleteBillInfor(row);
				refeshModelTable();
				refreshBtn();
			}
		});
		popupMenu.add(itemDel);
		
		table.setComponentPopupMenu(popupMenu);
	}
	
	public void setItemPopup(boolean b) {
		itemAdd.setEnabled(b);
		itemSub.setEnabled(b);
		itemDel.setEnabled(b);
	}
	
	public static void refreshBtn() {
		if(billBo.getBill() != null) {
			if(billBo.getBillInforBo().isEmpty()) {
				btnSave.setEnabled(false);
				btnIn.setEnabled(false);
				btnPay.setEnabled(false);
			}else {
				boolean b = billBo.getBillInforBo().isEdit();
				btnSave.setEnabled(b);
				btnIn.setEnabled(!b);
				btnPay.setEnabled(true);
			}
			btnDel.setEnabled(true);
		}else {
			btnSave.setEnabled(false);
			btnIn.setEnabled(false);
			btnDel.setEnabled(false);
			btnPay.setEnabled(false);
		}
	}
	
	public static void finishPay(int k) {
		int tt = billBo.ThanhTien();
		if(k < tt) {
			Main.showMessError("Thanh toán không thành công", "Số tiền không đủ để thanh toán hóa đơn!");
		}else {
			setVisibleLblPay(true);
			lblPayer2.setText(FormatMoney.formatVnd(k));
			lblRecost2.setText(FormatMoney.formatVnd(k-tt));
			lblStatus.setText("<html><b><u>Trạng thái</u>:</b> Đã thanh toán !</html>");
			PanelTable.setStatusTable(billBo.getTableId(), 0);
			saveBill(true);
			refreshBtn();
		}
	}
	
	public static void deleteBill(int tableId) {
		billBo.setTableId(tableId);
		if(Main.checkConfirm("Delete Bill", "Hủy hóa đơn?")) {
			billBo.deleteBill();
			PanelTable.setStatusTable(billBo.getTableId(), 0);
			CafeHome.switchTabs(1);
		}
	}
	
	public static void setVisibleLblPay(boolean b) {
		lblPayer1.setVisible(b);
		lblPayer2.setVisible(b);
		lblRecost1.setVisible(b);
		lblRecost2.setVisible(b);
	}
}