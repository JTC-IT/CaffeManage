package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import bean.FormatMoney;
import bean.TimeFormat;
import bo.ThongKeBo;

public class PanelStatistics extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPopupMenu popupMenu;
	private JPanel paneLeft, paneUp, paneCenter;
	private JTable tableLeft, tableCenter;
	private JDateChooser dateFrom, dateTo;
	private MyButton btnRefresh, btnChart;
	private JLabel lblDoanhThu, lblSumBill, lblSumDiscount;
	private ThongKeBo thongKeBo;
	private JMenuItem itemIn, itemXem;
	private InBill inbill;
	
	public PanelStatistics() throws SQLException {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		setPaneUp();
		setPaneLeft();
		setPaneCenter();
		setPopupMenu();
		thongKeBo = new ThongKeBo();
		LoadForm();
		inbill = new InBill();
	}

	public void setPaneUp() {
		paneUp = new JPanel();
		paneUp.setOpaque(false);
		paneUp.setPreferredSize(new Dimension(0,120));
		add(paneUp,BorderLayout.NORTH);
		
		SpringLayout layout = new SpringLayout();
		
		paneUp.setLayout(layout);
		
		dateFrom = new JDateChooser(TimeFormat.getFirstDayOfMonth());
		dateFrom.setDateFormatString("dd/MM/yyyy");
		dateFrom.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dateFrom.setPreferredSize(new Dimension(130, 28));
		paneUp.add(dateFrom);
		layout.putConstraint(SpringLayout.WEST, dateFrom, 20, SpringLayout.WEST, paneUp);
		layout.putConstraint(SpringLayout.NORTH, dateFrom, 30, SpringLayout.NORTH, paneUp);
		
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFrom.getDateEditor();
		editor.setBackground(new Color(238,234,174));
		editor.setHorizontalAlignment(SwingConstants.CENTER);
		editor.setFont(new Font("Segoe UI", Font.BOLD, 16));
		editor.setForeground(Color.BLACK);
		editor.setRequestFocusEnabled(false);
		
		JLabel lblTo = new JLabel("- Đến -");
		lblTo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblTo.setForeground(Color.white);
		paneUp.add(lblTo);
		layout.putConstraint(SpringLayout.WEST, lblTo, 170, SpringLayout.WEST, paneUp);
		layout.putConstraint(SpringLayout.NORTH, lblTo, 33, SpringLayout.NORTH, paneUp);
		
		dateTo = new JDateChooser(new Date());
		dateTo.setDateFormatString("dd/MM/yyyy");
		dateTo.setFont(new Font("Segoe UI", Font.BOLD, 12));
		dateTo.setPreferredSize(new Dimension(130, 28));
		paneUp.add(dateTo);
		layout.putConstraint(SpringLayout.WEST, dateTo, 240, SpringLayout.WEST, paneUp);
		layout.putConstraint(SpringLayout.NORTH, dateTo, 30, SpringLayout.NORTH, paneUp);
		
		editor = (JTextFieldDateEditor) dateTo.getDateEditor();
		editor.setBackground(new Color(238,234,174));
		editor.setHorizontalAlignment(SwingConstants.CENTER);
		editor.setFont(new Font("Segoe UI", Font.BOLD, 16));
		editor.setForeground(Color.BLACK);
		editor.setRequestFocusEnabled(false);
		
		btnRefresh = new MyButton();
		btnRefresh.setIcon("/imgs/refresh-26.png");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadForm();
			}
		});
		paneUp.add(btnRefresh);
		layout.putConstraint(SpringLayout.WEST, btnRefresh, 380, SpringLayout.WEST, paneUp);
		layout.putConstraint(SpringLayout.NORTH, btnRefresh, 27, SpringLayout.NORTH, paneUp);
		
		lblDoanhThu = new JLabel();
		lblDoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblDoanhThu.setForeground(Color.GREEN);
		lblDoanhThu.setVerticalAlignment(SwingConstants.BOTTOM);
		paneUp.add(lblDoanhThu);
		layout.putConstraint(SpringLayout.WEST, lblDoanhThu, 0, SpringLayout.HORIZONTAL_CENTER, paneUp);
		layout.putConstraint(SpringLayout.NORTH, lblDoanhThu, 20, SpringLayout.NORTH, paneUp);
		
		lblSumDiscount = new JLabel();
		lblSumDiscount.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSumDiscount.setForeground(Color.GREEN);
		lblSumDiscount.setVerticalAlignment(SwingConstants.BOTTOM);
		paneUp.add(lblSumDiscount);
		layout.putConstraint(SpringLayout.WEST, lblSumDiscount, 0, SpringLayout.HORIZONTAL_CENTER, paneUp);
		layout.putConstraint(SpringLayout.NORTH, lblSumDiscount, 45, SpringLayout.NORTH, paneUp);
		
		lblSumBill = new JLabel();
		lblSumBill.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblSumBill.setForeground(Color.GREEN);
		lblSumBill.setVerticalAlignment(SwingConstants.BOTTOM);
		paneUp.add(lblSumBill);
		layout.putConstraint(SpringLayout.WEST, lblSumBill, 0, SpringLayout.HORIZONTAL_CENTER, paneUp);
		layout.putConstraint(SpringLayout.NORTH, lblSumBill, 70, SpringLayout.NORTH, paneUp);
		
		btnChart = new MyButton();
		btnChart.setIcon("/imgs/chart-32.png");
		btnChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateTo.getDate());
					BieuDo bieuDo = new BieuDo(calendar.get(Calendar.YEAR));
					bieuDo.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		paneUp.add(btnChart);
		layout.putConstraint(SpringLayout.EAST, btnChart, -20, SpringLayout.EAST, paneUp);
		layout.putConstraint(SpringLayout.NORTH, btnChart, 30, SpringLayout.NORTH, paneUp);
	}
	
	public void setPaneLeft() {
		TitledBorder border = new TitledBorder(" Món bán chạy nhất ");
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
		border.setTitleColor(Color.white);
		
		paneLeft = new JPanel();
		paneLeft.setOpaque(false);
		paneLeft.setPreferredSize(new Dimension(430,0));
		paneLeft.setBorder(new CompoundBorder(border, new EmptyBorder(10, 10, 10, 10)));
		paneLeft.setLayout(new BorderLayout());
		add(paneLeft,BorderLayout.WEST);
		
		tableLeft = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;
	        };
		};
		tableLeft.setForeground(Color.white);
		tableLeft.setRowHeight(25);
		tableLeft.setBackground(Main.myColor);
		tableLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tableLeft.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableLeft.getTableHeader().setBackground(new Color(122,111,143));
		tableLeft.getTableHeader().setForeground(Color.white);
		tableLeft.getTableHeader().setBorder(new MatteBorder(1, 0, 1, 0, Color.white));
		tableLeft.getTableHeader().setPreferredSize(new Dimension(0, 25));
		
		JScrollPane scrollPane = new JScrollPane(tableLeft);
		scrollPane.getViewport().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBorder(null);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,0));
		paneLeft.add(scrollPane);
	}
	
	public void setPaneCenter() {
		TitledBorder border = new TitledBorder(" Lịch sử bán hàng ");
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
		border.setTitleColor(Color.white);
		
		paneCenter = new JPanel();
		paneCenter.setOpaque(false);
		paneCenter.setBorder(new CompoundBorder(border, new EmptyBorder(10, 10, 10, 10)));
		paneCenter.setLayout(new BorderLayout());
		add(paneCenter,BorderLayout.CENTER);
		
		tableCenter = new JTable(){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
	                return false;
	        };
		};
		tableCenter.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
		        Point point = event.getPoint();
		        int currentRow = tableCenter.rowAtPoint(point);
		        tableCenter.setRowSelectionInterval(currentRow, currentRow);
		    }
		});
		tableCenter.setForeground(Color.white);
		tableCenter.setRowHeight(25);
		tableCenter.setBackground(Main.myColor);
		tableCenter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tableCenter.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableCenter.getTableHeader().setBackground(new Color(122,111,143));
		tableCenter.getTableHeader().setForeground(Color.white);
		tableCenter.getTableHeader().setBorder(new MatteBorder(1, 0, 1, 0, Color.white));
		tableCenter.getTableHeader().setPreferredSize(new Dimension(0, 25));
		
		JScrollPane scrollPane = new JScrollPane(tableCenter);
		scrollPane.getViewport().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBorder(null);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,0));
		paneCenter.add(scrollPane);
	}
	
	public void LoadTable(DefaultTableModel modelLeft, DefaultTableModel modelCenter) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		tableLeft.setModel(modelLeft);
		
		tableLeft.getColumnModel().getColumn(0).setPreferredWidth(5);
		tableLeft.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		tableLeft.getColumnModel().getColumn(1).setPreferredWidth(80);
		tableLeft.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		tableLeft.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableLeft.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		tableLeft.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		tableCenter.setModel(modelCenter);
		
		tableCenter.getColumnModel().getColumn(0).setPreferredWidth(5);
		tableCenter.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		tableCenter.getColumnModel().getColumn(1).setPreferredWidth(80);
		tableCenter.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		tableCenter.getColumnModel().getColumn(2).setPreferredWidth(5);
		tableCenter.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		tableCenter.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableCenter.getColumnModel().getColumn(4).setPreferredWidth(150);
		tableCenter.getColumnModel().getColumn(5).setPreferredWidth(5);
		tableCenter.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
		tableCenter.getColumnModel().getColumn(6).setPreferredWidth(150);
		tableCenter.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
		tableCenter.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}
	
	public void LoadLabel(int doanhThu, int giamGia, int bill) {
		lblDoanhThu.setText("Tổng Doanh thu:          "+FormatMoney.formatVnd(doanhThu));
		lblSumDiscount.setText("Tổng Giảm giá:            "+FormatMoney.formatVnd(giamGia));
		lblSumBill.setText("Tổng Đơn hàng đã bán:     "+bill);
	}
	
	public void LoadForm(){
		LoadTable(thongKeBo.Rank_Food(dateFrom.getDate(),dateTo.getDate())
				, thongKeBo.Tk_ALL_by_Date(dateFrom.getDate(),dateTo.getDate()));
		
		ArrayList<Integer> data = thongKeBo.Tk_Money(dateFrom.getDate(),dateTo.getDate());
		LoadLabel(data.get(1), data.get(0), data.get(3));
	}
	
	public void setPopupMenu() {
		Font font = new Font("Segoe UI", Font.BOLD, 14);
		popupMenu = new JPopupMenu();
		popupMenu.setBackground(Main.myColor);
		
		itemXem = new JMenuItem("Chi tiết");
		itemXem.setBackground(new Color(238,234,174));
		itemXem.setForeground(Color.black);
		itemXem.setFont(font);
		itemXem.setHorizontalAlignment(SwingConstants.CENTER);
		itemXem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				DefaultTableModel tbmodel = new DefaultTableModel();
				tbmodel.addColumn("Tên hàng");
				tbmodel.addColumn("Đ.giá");
				tbmodel.addColumn("SL");
				tbmodel.addColumn("T.Tiền");
				int row = tableCenter.getSelectedRow();
				if(row>=0){
					ArrayList<Vector<String>> data = thongKeBo.XemTTBill(Integer.parseInt(tableCenter.getValueAt(row, 0).toString()));
					int len = data.size();					
					for(int i = 0;i<len;i++)
						tbmodel.addRow(data.get(i));
					
					JTable table = new JTable(tbmodel){
						private static final long serialVersionUID = 1L;
						public boolean isCellEditable(int row, int column) {                
				                return false;
				        };
					};
					
					double tt = FormatTT(tableCenter.getValueAt(row, 6).toString());
					double gg =  FormatGG(tableCenter.getValueAt(row, 5).toString());
					double tong = tt/(1-(gg/100));
					Vector<String> rowt = new Vector<>();
					rowt.add("");rowt.add("");rowt.add("TỔNG TIỀN:");rowt.add(FormatMoney.format((int)tong));
					tbmodel.addRow(rowt);
					Vector<String> rowt1 = new Vector<>();
					rowt1.add("");rowt1.add("");rowt1.add("GIẢM GIÁ:");rowt1.add(tableCenter.getValueAt(row, 5).toString());
					tbmodel.addRow(rowt1);
					Vector<String> rowt2 = new Vector<>();
					int tem = FormatTT(tableCenter.getValueAt(row, 6).toString());
					rowt2.add("");rowt2.add("");rowt2.add("THÀNH TIỀN:");rowt2.add(FormatMoney.format(tem));
					tbmodel.addRow(rowt2);		

					table.setForeground(Color.white);
					table.setRowHeight(25);
					table.setBackground(Main.myColor);
					table.setFont(new Font("Tahoma", Font.PLAIN, 14));
					
					table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
					table.getTableHeader().setBackground(new Color(122,111,143));
					table.getTableHeader().setForeground(Color.white);
					table.getTableHeader().setBorder(new MatteBorder(1, 0, 1, 0, Color.white));
					table.getTableHeader().setPreferredSize(new Dimension(0, 25));
					
					JScrollPane scrollPane = new JScrollPane(table);
					scrollPane.getViewport().setBackground(Main.myColor);
					scrollPane.getVerticalScrollBar().setBackground(Main.myColor);
					scrollPane.getVerticalScrollBar().setBorder(null);
					scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8,0));
					
					table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
					table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
					table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
					table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
					
					JOptionPane.showMessageDialog(null,scrollPane);
				}
			}
		});
		popupMenu.add(itemXem);
		
		itemIn = new JMenuItem("In Bill");
		itemIn.setBackground(new Color(238,234,174));
		itemIn.setForeground(Color.black);
		itemIn.setFont(font);
		itemIn.setHorizontalAlignment(SwingConstants.CENTER);
		itemIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableCenter.getSelectedRow();
				if(row>=0)				
					inbill.PrintBill(Integer.parseInt(tableCenter.getValueAt(row, 0).toString()));				
			}
		});
		popupMenu.add(itemIn);		
		tableCenter.setComponentPopupMenu(popupMenu);
	}
	private int FormatGG(String gg){
		gg = gg.replaceAll("%", "");
		int g = Integer.parseInt(gg);
		return g;
	}
	private int FormatTT(String tt){
		tt =  tt.replaceAll(",", "");
		return Integer.parseInt(tt);
	}	
}
