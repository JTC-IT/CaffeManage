package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bean.TableFood;

public class PanelTableDetails extends JPanel {

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
	
	class MyToggle extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private ImageIcon onIcon = new ImageIcon(PanelTableDetails.class.getResource("/imgs/toggle-on-24.png"));
		private ImageIcon offIcon = new ImageIcon(PanelTableDetails.class.getResource("/imgs/toggle-off-24.png"));
		
		private JLabel status;
		private JCheckBox toggle;
		
		public MyToggle(String title) {
			setOpaque(false);
			setLayout(new BorderLayout());
			
			status = new JLabel("Tạm khóa");
			status.setHorizontalAlignment(SwingConstants.LEFT);
			status.setVerticalAlignment(SwingConstants.CENTER);
			status.setForeground(Color.yellow);
			status.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			add(status, BorderLayout.CENTER);
			
			TitledBorder titleBorder = new TitledBorder(title);
			titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
			titleBorder.setTitleColor(Color.white);
			setBorder(new CompoundBorder(titleBorder, new EmptyBorder(8, 8, 8, 8)));
			
			toggle = new JCheckBox(offIcon);
			toggle.setOpaque(false);
			toggle.setVerticalAlignment(SwingConstants.CENTER);
			toggle.setSelectedIcon(onIcon);
			toggle.setDisabledIcon(offIcon);
			toggle.addItemListener((ItemListener) new ItemListener() {
	            public void itemStateChanged(ItemEvent e) {
	                if (e.getStateChange() == 1) {
	                	status.setText("Hoạt động");
	                } else {
	                	status.setText("Tạm khóa");
	                }
	            }
	        });
			add(toggle, BorderLayout.EAST);
		}
		
		public void setSelected(boolean b) {
			toggle.setSelected(b);
			if(b)
	        	status.setText("Hoạt động");
	        else 
	        	status.setText("Tạm khóa");
		}
		
		public boolean isSelected() {
			return toggle.isSelected();
		}
		
		public void setEnabled(boolean b) {
			status.setEnabled(b);
			toggle.setEnabled(b);
		}
	}
	
	private TitledBorder border;
	private static MyText txtName;
	private static MyToggle Status;
	private MyButton btnSave, btnCancel;
	private TableFood table;

	public PanelTableDetails() {
		setOpaque(false);
		setPreferredSize(new Dimension(200,0));
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		border = new TitledBorder(" Thêm Bàn ");
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 22));
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleColor(Color.WHITE);
		setBorder(new CompoundBorder(border, new EmptyBorder(20, 20, 20, 20)));
		
		txtName = new MyText("Số bàn");
		add(txtName);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtName, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, txtName, -70, SpringLayout.VERTICAL_CENTER, this);
		
		Status = new MyToggle("Trạng thái");
		Status.setPreferredSize(new Dimension(300,65));
		add(Status);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Status, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, Status, 0, SpringLayout.VERTICAL_CENTER, this);
		
		btnSave = new MyButton();
		btnSave.setText("Lưu");
		btnSave.setIcon("/imgs/save-24.png");
		btnSave.setFont(15);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				if(name.isEmpty() || name == "") {
					Main.showMessError("Tên bàn đang trống", "Vui lòng nhập tên bàn!");
					return;
				}
				if(table == null) {
					try {
						PanelTable2.addTable(name, Status.isSelected() ? 0 :-1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					int status = table.getStatus();
					if(!Status.isSelected())
						status = -1;
					else if(status == -1) 
						status = 0;
						
					try {
						PanelTable2.editTable(new TableFood(table.getId(), name, status));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				CafeHome.showPanelRight(-1);
				PanelTable2.setVisibleBtn(true);
			}
		});
		add(btnSave);
		layout.putConstraint(SpringLayout.WEST, btnSave, 10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, btnSave, 70, SpringLayout.VERTICAL_CENTER, this);
		
		btnCancel = new MyButton();
		btnCancel.setText("Hủy");
		btnCancel.setIcon("/imgs/delete-24.png");
		btnCancel.setFont(15);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelRight(-1);
				PanelTable2.setVisibleBtn(true);
			}
		});
		add(btnCancel);
		layout.putConstraint(SpringLayout.EAST, btnCancel, -10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, btnCancel, 70, SpringLayout.VERTICAL_CENTER, this);
	}
	
	public void setPanel(TableFood tb) {
		table = tb;
		if(table == null) {
			border.setTitle(" Thêm Bàn ");
			txtName.setText("");
			Status.setSelected(true);
		}else {
			border.setTitle(" Sửa Bàn ");
			txtName.setText(table.getName());
			Status.setSelected(table.getStatus() != -1);
			Status.setEnabled(table.getStatus() != 1);
		}
		repaint();
	}
}
