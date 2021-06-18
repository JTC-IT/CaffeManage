package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import bean.Food;
import bean.FormatMoney;
import bo.FoodBo;
import bo.FoodCategoryBo;

public class PanelFood2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private static JPanel paneRight, paneUp;
	private static MyButton btnAdd, btnEdit, btnDel;
	private static DefaultTableModel model;
	private static JComboBox<String> comboBox;
	private static JTextField txtSearch;
	private static MyButton btnSearch;
	private static JLabel lblAmount;
	
	private static FoodBo foodBo;
	private static FoodCategoryBo categoryBo;
	
	public PanelFood2(FoodBo fb) throws SQLException {
		setOpaque(false);
		setBorder(new EmptyBorder(20,10,10,10));
		setLayout(new BorderLayout());
		
		categoryBo = new FoodCategoryBo();
		foodBo = fb;
		
		//ModelTable
		model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Tên Món");
		model.addColumn("Đơn Giá");
		
		//Table
		table = new JTable(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {                
	                return false;
	        };
		};
		table.setForeground(Color.white);
		table.setRowHeight(30);
		table.setBackground(Main.myColor);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
		table.getTableHeader().setBackground(new Color(122,111,143));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBorder(new MatteBorder(1, 0, 1, 0, Color.white));
		table.getTableHeader().setPreferredSize(new Dimension(0, 30));
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
		        Point point = event.getPoint();
		        int currentRow = table.rowAtPoint(point);
		        table.setRowSelectionInterval(currentRow, currentRow);
		    }
			@Override
			public void mouseClicked(MouseEvent e) {
				CafeHome.showPanelRight(-1);
				setVisibleBtn(true);
				btnEdit.setEnabled(true);
				btnDel.setEnabled(true);
			}
		});
		table.setModel(model);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(JLabel.CENTER);
		
		TableColumnModel column = table.getColumnModel();
		column.getColumn(0).setPreferredWidth(40);
		column.getColumn(0).setCellRenderer(dtcr);
		column.getColumn(1).setPreferredWidth(170);
		column.getColumn(1).setCellRenderer(dtcr);
		column.getColumn(2).setPreferredWidth(50);
		column.getColumn(2).setCellRenderer(dtcr);
		
		//ScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBackground(Main.myColor);
		scrollPane.getVerticalScrollBar().setBorder(null);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));
		add(scrollPane, BorderLayout.CENTER);
		
		setPaneRight();
		setPaneUp();
		refreshPanel();
	}
	
	public void setPaneUp() {
		paneUp = new JPanel();
		paneUp.setOpaque(false);
		paneUp.setBorder(new EmptyBorder(10,20,20,10));
		paneUp.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		add(paneUp, BorderLayout.NORTH);
		
		comboBox = new JComboBox<String>();
		comboBox.setBackground(Color.decode("#7A6F8F"));
		comboBox.setBorder(new LineBorder(Color.black, 1));
		comboBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
		comboBox.setForeground(Color.YELLOW);
		comboBox.setPreferredSize(new Dimension(150, 35));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(comboBox.getSelectedIndex() == 0)
					loadTable(foodBo.getListFood());
				else {
					String st = comboBox.getSelectedItem().toString();
					loadTable(foodBo.getListFood(categoryBo.getFCategory(st).getId()));
				}
			}
		});
		paneUp.add(comboBox);
		
		txtSearch = new JTextField();
		txtSearch.setOpaque(false);
		txtSearch.setBorder(new LineBorder(Color.GREEN, 1, true));
		txtSearch.setHorizontalAlignment(SwingConstants.CENTER);
		txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtSearch.setCaretColor(Color.white);
		txtSearch.setForeground(Color.WHITE);
		txtSearch.setPreferredSize(new Dimension(350, 35));
		txtSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				search();
			}
		});
		paneUp.add(txtSearch);
		
		btnSearch = new MyButton();
		btnSearch.setIcon("/imgs/search-24.png");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		paneUp.add(btnSearch);
	}
	
	public void setPaneRight() {
		paneRight = new JPanel();
		paneRight.setOpaque(false);
		paneRight.setBorder(new EmptyBorder(10,20,20,10));
		
		SpringLayout layout = new SpringLayout();
		paneRight.setLayout(layout);
		add(paneRight, BorderLayout.EAST);
		paneRight.setPreferredSize(new Dimension(200,0));
		
		lblAmount = new JLabel();
		lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblAmount.setForeground(Color.GREEN);
		paneRight.add(lblAmount);
		layout.putConstraint(SpringLayout.NORTH, lblAmount, 20, SpringLayout.NORTH, paneRight);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblAmount, 0, SpringLayout.HORIZONTAL_CENTER, paneRight);
		
		btnAdd = new MyButton();
		btnAdd.setText("Thêm");
		btnAdd.setIcon("/imgs/add-32.png");
		btnAdd.setFont(16);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleBtn(false);
				CafeHome.showPanelRight(3);
				CafeHome.setPaneFoodDetails(null);
			}
		});
		btnAdd.setPreferredSize(new Dimension(130,35));
		paneRight.add(btnAdd);
		layout.putConstraint(SpringLayout.SOUTH, btnAdd, -10, SpringLayout.VERTICAL_CENTER, paneRight);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnAdd, 0, SpringLayout.HORIZONTAL_CENTER, paneRight);
		
		btnEdit = new MyButton();
		btnEdit.setText("Sửa");
		btnEdit.setIcon("/imgs/edit-24.png");
		btnEdit.setFont(16);
		btnEdit.setPreferredSize(new Dimension(130,35));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index >= 0) {
					setVisibleBtn(false);
					CafeHome.showPanelRight(3);
					CafeHome.setPaneFoodDetails(foodBo.getFood(Integer.parseInt(table.getValueAt(index, 0).toString())));
				}				
			}
		});
		paneRight.add(btnEdit);
		layout.putConstraint(SpringLayout.NORTH, btnEdit, 10, SpringLayout.VERTICAL_CENTER, paneRight);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnEdit, 0, SpringLayout.HORIZONTAL_CENTER, paneRight);
		
		btnDel = new MyButton();
		btnDel.setText("Xóa");
		btnDel.setIcon("/imgs/del-24.png");
		btnDel.setFont(16);
		btnDel.setPreferredSize(new Dimension(130,35));
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if(index >= 0) {
					int id = Integer.parseInt(table.getValueAt(index, 0).toString());
					Food food = foodBo.getFood(id);
					if(Main.checkConfirm("Xóa món", "Xóa "+" '"+food.getName()+"'  khỏi menu?")) {
						model.removeRow(index);
						btnEdit.setEnabled(false);
						btnDel.setEnabled(false);
						CafeHome.setChangeFoodData("del", food);
						foodBo.deleteFood(id);
					}
				}				
			}
		});
		paneRight.add(btnDel);
		layout.putConstraint(SpringLayout.NORTH, btnDel, 65, SpringLayout.VERTICAL_CENTER, paneRight);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnDel, 0, SpringLayout.HORIZONTAL_CENTER, paneRight);
	}

	public static void refreshComboBox() {
		comboBox.setModel(categoryBo.getListName());
		comboBox.insertItemAt("Tất cả", 0);
		comboBox.setSelectedIndex(0);
	}
	
	public static void loadTable(ArrayList<Food> list) {
		model.setRowCount(0);
		for(Food food: list)
			model.addRow(new Object[] {food.getId()
					, food.getName()
					, FormatMoney.formatVnd(food.getPrice())
						});
		lblAmount.setText("SL: "+list.size()+"/"+foodBo.getListFood().size());
		btnEdit.setEnabled(false);
		btnDel.setEnabled(false);
	}
	
	public static void refreshPanel() {
		refreshComboBox();
		txtSearch.setText("");
		loadTable(foodBo.getListFood());
		setVisibleBtn(true);
	}
	
	public void search() {
		String st = txtSearch.getText().trim().toLowerCase();
		loadTable(foodBo.searchFood(st));
	}
	
	public static void setVisibleBtn(boolean b) {
		paneRight.setVisible(b);
	}
	
	public static void addFood(String name, int category, int price) {
		Food food = foodBo.addFood(name, category, price);
		if(food != null){
			comboBox.setSelectedIndex(0);
			loadTable(foodBo.getListFood());
			Main.showMessCorrect("Hoàn tất", name+" đã được thêm vào Menu!");
			CafeHome.setChangeFoodData("add", food);
		}else Main.showMessError("Thêm món không thành công", name+" đã tồn tại!");
	}
	
	public static void editFood(Food food) {
		foodBo.updateFood(food);
		comboBox.setSelectedIndex(0);
		loadTable(foodBo.getListFood());
		Main.showMessCorrect("Hoàn tất", "Cập nhật menu thành công!");
		CafeHome.setChangeFoodData("up", food);
	}
}