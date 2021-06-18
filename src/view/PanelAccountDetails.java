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
import bean.Account;

public class PanelAccountDetails extends JPanel {
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
		
		public MyComboBox(String title) {
			setOpaque(false);
			setLayout(new BorderLayout());
			setPreferredSize(new Dimension(300,65));
			
			TitledBorder titleBorder = new TitledBorder(title);
			titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
			titleBorder.setTitleColor(Color.white);
			setBorder(new CompoundBorder(titleBorder, new EmptyBorder(5, 5, 5, 5)));
			
			comboBox = new JComboBox<String>(new String[]{"Nhân viên","Quản lý"});
			comboBox.setBackground(Color.decode("#7A6F8F"));
			comboBox.setBorder(new LineBorder(Color.black, 1));
			comboBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
			comboBox.setForeground(Color.YELLOW);
			comboBox.setPreferredSize(new Dimension(200, 35));
			add(comboBox);
		}
		
		public void setSelected(int k) {
			comboBox.setSelectedIndex(k);
		}
		
		public int getSelected() {
			return comboBox.getSelectedIndex();
		}
		
		public void setEnabled(boolean b) {
			comboBox.setEnabled(b);
		}
	}
	
	private TitledBorder border;
	private static MyText txtName, txtusername,txtpasswork;
	private static MyComboBox cmbCategory;	
	private MyButton btnSave, btnCancel;
	private int idAccount;
	
	public PanelAccountDetails() {
		setOpaque(false);
		setPreferredSize(new Dimension(200,0));
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		border = new TitledBorder(" Thêm Tài Khoản ");
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 22));
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleColor(Color.WHITE);
		setBorder(new CompoundBorder(border, new EmptyBorder(20, 20, 20, 20)));
		
		txtName = new MyText("Họ tên");
		add(txtName);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtName, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, txtName, -115, SpringLayout.VERTICAL_CENTER, this);
		
		txtusername = new MyText("Tên tài khoản");
		add(txtusername);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtusername, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, txtusername, -40, SpringLayout.VERTICAL_CENTER, this);
		
		txtpasswork = new MyText("Mật khẩu");		
		add(txtpasswork);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtpasswork, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtpasswork, 0, SpringLayout.VERTICAL_CENTER, this);
		
		cmbCategory = new MyComboBox("Loại tài khoản");
		add(cmbCategory);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cmbCategory, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, cmbCategory, 40, SpringLayout.VERTICAL_CENTER, this);
		
		btnSave = new MyButton();
		btnSave.setText("Lưu");
		btnSave.setIcon("/imgs/save-24.png");
		btnSave.setFont(15);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText().trim();
				if(name.isEmpty() || name == "") {
					Main.showMessError("Thông tin không hợp lệ", "Họ tên không được để trống!");
					return;
				}
				String username = txtusername.getText().trim();
				if(username.isEmpty() || username == "") {
					Main.showMessError("Thông tin không hợp lệ", "Tên tài khoản không được để trống!");
					return;
				}
				String pass = txtpasswork.getText().trim();
				if(pass.isEmpty() || pass == "") {
					Main.showMessError("Thông tin không hợp lệ", "Tên tài khoản không được để trống!");
					return;
				}
				int type = cmbCategory.getSelected();
				
				if(idAccount == 0) {
					try {
						PanelAccount.addAccount(username, name, pass, type);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					try {
						PanelAccount.editAccount(new Account(idAccount, username, name, pass, type));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				CafeHome.showPanelRight(-1);
				PanelAccount.setVisibleBtn(true);
			}
		});
		add(btnSave);
		layout.putConstraint(SpringLayout.WEST, btnSave, 10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, btnSave, 130, SpringLayout.VERTICAL_CENTER, this);
		
		btnCancel = new MyButton();
		btnCancel.setText("Hủy");
		btnCancel.setIcon("/imgs/delete-24.png");
		btnCancel.setFont(15);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelRight(-1);
				PanelAccount.setVisibleBtn(true);
			}
		});
		add(btnCancel);
		layout.putConstraint(SpringLayout.EAST, btnCancel, -10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, btnCancel, 130, SpringLayout.VERTICAL_CENTER, this);
	}
	
	public void setPanel(Account account) {		
		if(account == null) {
			idAccount = 0;
			border.setTitle(" Thêm Tài Khoản ");
			txtName.setText("");
			cmbCategory.setSelected(0);
			txtusername.setText("");
			txtpasswork.setText("");
			cmbCategory.setEnabled(true);
		}else {
			idAccount = account.getId();
			border.setTitle(" Sửa Tài Khoản ");
			txtName.setText(account.getFullname());
			cmbCategory.setSelected(account.getType());
			txtusername.setText(account.getUsername());
			txtpasswork.setText(account.getPassword());
			cmbCategory.setEnabled(account.getId() != Main.getAccount().getId());
		}
		repaint();
	}
}