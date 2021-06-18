package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bo.ShopBo;

public class PanelShop extends JPanel {	
		private static final long serialVersionUID = 1L;

		class MyText extends JTextField{
			private static final long serialVersionUID = 1L;
			
			public MyText(String title) {
				setOpaque(false);
				setHorizontalAlignment(SwingConstants.CENTER);
				setDisabledTextColor(Color.yellow);
				setForeground(Color.yellow);
				setCaretColor(Color.yellow);
				setFont(new Font("Segoe UI", Font.PLAIN, 20));
				setPreferredSize(new Dimension(600,70));
				
				TitledBorder titleBorder = new TitledBorder(title);
				titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
				titleBorder.setTitleColor(Color.white);
				setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 8, 8, 8)));
			}
		}
	
	private TitledBorder border;
	private static MyText txtName, txtDiachi,txtSdt;		
	private MyButton btnSave, btnCancel;
	private ShopBo shopBo;
	
	private int status;
	
	public PanelShop() {
		setOpaque(false);
		setPreferredSize(new Dimension(200,0));
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		border = new TitledBorder(" THÔNG TIN QUÁN ");
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 25));
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleColor(Color.WHITE);
		
		txtName = new MyText("Tên Quán");
		add(txtName);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtName, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, txtName, -115, SpringLayout.VERTICAL_CENTER, this);
				
		
		txtDiachi = new MyText("Địa Chỉ");
		add(txtDiachi);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtDiachi, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtDiachi, -40, SpringLayout.VERTICAL_CENTER, this);
		
		txtSdt = new MyText("Số điện thoại");		
		add(txtSdt);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtSdt, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, txtSdt, 40, SpringLayout.VERTICAL_CENTER, this);
		
		btnSave = new MyButton();
		btnSave.setFont(15);
		btnSave.setPreferredSize(new Dimension(140,35));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(status == 1) {
					status = -1;
					refresh();
				}else save();
			}
		});
		add(btnSave);
		layout.putConstraint(SpringLayout.EAST, btnSave, 300, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, btnSave, 150, SpringLayout.VERTICAL_CENTER, this);
		
		btnCancel = new MyButton();
		btnCancel.setText("Hủy bỏ");
		btnCancel.setIcon("/imgs/delete-24.png");
		btnCancel.setFont(15);
		btnCancel.setPreferredSize(new Dimension(130,35));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPanel();
			}
		});
		add(btnCancel);
		layout.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, btnCancel, 150, SpringLayout.VERTICAL_CENTER, this);
		
		setPanel();
	}
	
	public void setPanel(){
		try {
			shopBo = new ShopBo();
			if(shopBo.getShop() == null){
				status = 0;
				txtName.setText("");			
				txtDiachi.setText("");
				txtSdt.setText("");
			}
			else{
				status = 1;
				txtName.setText(shopBo.getShop().getTenQuan());			
				txtDiachi.setText(shopBo.getShop().getDiaChi());
				txtSdt.setText(shopBo.getShop().getSđt());
			}
			refresh();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	public void refreshBorder(String title) {
		border.setTitle(title);
		setBorder(new CompoundBorder(border, new EmptyBorder(10, 10, 10, 10)));
	}
	
	private void refresh(){
		switch(status) {
		case 0:
			refreshBorder("Cập nhật thông tin quán");
			setEnabledText(true);
			txtName.requestFocus();
			btnCancel.setVisible(false);
			btnSave.setText("Cập nhật");
			btnSave.setIcon("/imgs/save-24.png");
			break;
		case -1:
			refreshBorder("Cập nhật thông tin quán");
			setEnabledText(true);
			txtName.requestFocus();
			btnCancel.setVisible(true);
			btnSave.setText("Cập nhật");
			btnSave.setIcon("/imgs/save-24.png");
			break;
		case 1:
			refreshBorder("Thông tin quán");
			setEnabledText(false);
			btnCancel.setVisible(false);
			btnSave.setText("Chỉnh sửa");
			btnSave.setIcon("/imgs/edit-24.png");
			break;
		}
		repaint();
	}
	
	private void setEnabledText(boolean b) {
		txtName.setEnabled(b);
		txtDiachi.setEnabled(b);
		txtSdt.setEnabled(b);
	}
	
	private void save() {
		String name = txtName.getText();
		if(name.isEmpty() || name == "") {
			Main.showMessError("Thông tin không hợp lệ", "Vui lòng nhập tên của quán!");
			txtName.requestFocus();
			return;
		}
		String txtdc = txtDiachi.getText();
		if(txtdc.isEmpty() || txtdc == "") {
			Main.showMessError("Thông tin không hợp lệ", "Vui lòng nhập địa chỉ quán!");
			txtDiachi.requestFocus();
			return;
		}
		String sdt = txtSdt.getText();
		if(sdt.isEmpty() || sdt == "") {
			Main.showMessError("Thông tin không hợp lệ", "Vui lòng nhập số điện thoại của quán!");
			txtSdt.requestFocus();
			return;
		}
		if(status == 0) {
			if(shopBo.addShop(txtName.getText(), txtDiachi.getText(), txtSdt.getText()))
				Main.showMessCorrect("Thành công", "Thông tin quán đã được cập nhật!");
			else {
				Main.showMessError("Cập nhật thất bại!", "Thông tin quán chưa được cập nhật!");
				return;
			}
		}else {
			if(shopBo.updateShop(txtName.getText(), txtDiachi.getText(), txtSdt.getText()))
				Main.showMessCorrect("Thành công", "Thông tin quán đã được cập nhật!");
			else {
				Main.showMessError("Cập nhật thất bại!", "Thông tin quán chưa được cập nhật!");
				return;
			}
		}
		status = 1;
		refresh();
	}
}