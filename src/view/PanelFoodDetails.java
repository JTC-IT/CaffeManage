package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import bean.Food;
import bo.FoodCategoryBo;

public class PanelFoodDetails extends JPanel {
	private static final long serialVersionUID = 1L;

	class MyText extends JTextField{
		private static final long serialVersionUID = 1L;
		
		public MyText(String title) {
			setOpaque(false);
			setHorizontalAlignment(SwingConstants.LEFT);
			setForeground(Color.yellow);
			setCaretColor(Color.yellow);
			setFont(new Font("Segoe UI", Font.PLAIN, 15));
			setPreferredSize(new Dimension(300,65));
			
			TitledBorder titleBorder = new TitledBorder(title);
			titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
			titleBorder.setTitleColor(Color.white);
			setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 8, 8, 8)));
		}
	}
	
	class MyComboBox extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JComboBox<String> comboBox;
		private FoodCategoryBo categoryBo;
		
		public MyComboBox(String title) {
			setOpaque(false);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(300,65));
			
			TitledBorder titleBorder = new TitledBorder(title);
			titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
			titleBorder.setTitleColor(Color.white);
			setBorder(new CompoundBorder(titleBorder, new EmptyBorder(5, 5, 5, 5)));
			
			comboBox = new JComboBox<String>();
			comboBox.setBackground(Color.decode("#7A6F8F"));
			comboBox.setBorder(new LineBorder(Color.black, 1));
			comboBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
			comboBox.setForeground(Color.YELLOW);
			comboBox.setPreferredSize(new Dimension(200, 35));
			add(comboBox);
			
			refreshComboBox();
		}
		
		public void setSelected(String name) {
			comboBox.setSelectedItem(name);
		}
		
		public void setSelected(int id) {
			comboBox.setSelectedItem(categoryBo.getFCategory(id).getName());
		}
		
		public int getSelected() {
			return categoryBo.getFCategory(comboBox.getSelectedItem().toString()).getId();
		}
		
		public void refreshComboBox() {
			try {
				categoryBo = new FoodCategoryBo();
				comboBox.setModel(categoryBo.getListName());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private TitledBorder border;
	private static MyText txtName, txtPrice;
	private static MyComboBox cmbCategory;
	private MyButton btnSave, btnCancel;
	private int idFood;
	
	public PanelFoodDetails() {
		setOpaque(false);
		setPreferredSize(new Dimension(200,0));
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		border = new TitledBorder(" Thêm Món ");
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 22));
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleColor(Color.WHITE);
		setBorder(new CompoundBorder(border, new EmptyBorder(20, 20, 20, 20)));
		
		txtName = new MyText("Tên món");
		add(txtName);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtName, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, txtName, -115, SpringLayout.VERTICAL_CENTER, this);
		
		txtPrice = new MyText("Giá (vnđ)");
		add(txtPrice);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtPrice, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, txtPrice, -40, SpringLayout.VERTICAL_CENTER, this);
		
		cmbCategory = new MyComboBox("Loại");
		add(cmbCategory);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cmbCategory, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, cmbCategory, 0, SpringLayout.VERTICAL_CENTER, this);
		
		btnSave = new MyButton();
		btnSave.setText("Lưu");
		btnSave.setIcon("/imgs/save-24.png");
		btnSave.setFont(15);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				if(name.isEmpty() || name == "") {
					Main.showMessError("Thông tin không hợp lệ", "Vui lòng nhập tên món!");
					return;
				}
				String txtprice = txtPrice.getText();
				if(txtprice.isEmpty() || txtprice == "") {
					Main.showMessError("Thông tin không hợp lệ", "Vui lòng nhập tên món!");
					return;
				}
				int price;
				try {
					price = Integer.parseInt(txtprice);
				} catch (Exception e2) {
					Main.showMessError("Thông tin không hợp lệ", "Giá phải là một số!");
					return;
				}
				if(idFood == 0) {
					PanelFood2.addFood(name, cmbCategory.getSelected(), price);
				}else {
					PanelFood2.editFood(new Food(idFood, name, cmbCategory.getSelected(), price, true));
				}
				CafeHome.showPanelRight(-1);
				PanelFood2.setVisibleBtn(true);
			}
		});
		add(btnSave);
		layout.putConstraint(SpringLayout.WEST, btnSave, 10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, btnSave, 50, SpringLayout.VERTICAL_CENTER, this);
		
		btnCancel = new MyButton();
		btnCancel.setText("Hủy");
		btnCancel.setIcon("/imgs/delete-24.png");
		btnCancel.setFont(15);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelRight(-1);
				PanelFood2.setVisibleBtn(true);
			}
		});
		add(btnCancel);
		layout.putConstraint(SpringLayout.EAST, btnCancel, -10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, btnCancel, 50, SpringLayout.VERTICAL_CENTER, this);
	}
	
	public void setPanel(Food food) {
		cmbCategory.refreshComboBox();
		if(food == null) {
			idFood = 0;
			border.setTitle(" Thêm Món ");
			txtName.setText("món mới");
			txtPrice.setText("000");
		}else {
			idFood = food.getId();
			border.setTitle(" Sửa Món ");
			txtName.setText(food.getName());
			cmbCategory.setSelected(food.getCategory());
			txtPrice.setText(food.getPrice()+"");
		}
		repaint();
	}
}