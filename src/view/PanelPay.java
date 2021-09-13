package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import bean.FormatMoney;

public class PanelPay extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static JLabel lblInput, lblAmount;
	private JPanel paneLeft, paneCenter;
	private ArrayList<MyButton> listBtn;
	
	class MyButton extends JButton{
	
			private static final long serialVersionUID = 1L;
			private Color colorEnter = new Color(93,100,142);
			private Color colorExit = new Color(133,144,204);
			public MyButton(String txt) {
				setText(txt);
				setBackground(colorExit);
				setBorder(new LineBorder(Color.BLACK, 2, true));
				setFont(new Font("Segoe UI", Font.BOLD, 20));
				setForeground(Color.yellow);
				setHorizontalAlignment(SwingConstants.CENTER);
				setFocusPainted(false);
				
				addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						setBackground(colorEnter);
					}
					public void mouseExited(MouseEvent e) {
						setBackground(colorExit);
					}
				});
			}
		}
	
	public PanelPay() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		TitledBorder border = new TitledBorder(" Nhập tiền khách đưa ");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 24));
		border.setTitleColor(Color.white);
		setBorder(new CompoundBorder(border, new EmptyBorder(20, 10, 10, 10)));
		
		JPanel panelHead = new JPanel(new BorderLayout());
		panelHead.setOpaque(false);
		panelHead.setBorder(new LineBorder(Color.white, 2, true));
		panelHead.setPreferredSize(new Dimension(0,50));
		
		lblAmount = new JLabel();
		lblAmount.setForeground(Color.yellow);
		lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmount.setVerticalAlignment(SwingConstants.TOP);
		lblAmount.setPreferredSize(new Dimension(85, 0));
		panelHead.add(lblAmount, BorderLayout.EAST);
		
		lblInput = new JLabel();
		lblInput.setForeground(Color.GREEN);
		lblInput.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		panelHead.add(lblInput, BorderLayout.CENTER);
		
		add(panelHead, BorderLayout.NORTH);
		
		setLeft();
		setCenter();
	}
	
	public void setLeft(){
		paneLeft = new JPanel();
		paneLeft.setOpaque(false);
		paneLeft.setBorder(new EmptyBorder(30, 0, 10, 5));
		paneLeft.setLayout(new GridLayout(9, 1, 0, 10));
		add(paneLeft, BorderLayout.WEST);
		paneLeft.setPreferredSize(new Dimension(135,0));
		
		MyButton btnDel = new MyButton("X");
		btnDel.setBackground(new Color(168,20,18));
		btnDel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnDel.setBackground(new Color(124,0,24));
			}
			public void mouseExited(MouseEvent e) {
				btnDel.setBackground(new Color(168,20,18));
			}
		});
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText("");
			}
		});
		paneLeft.add(btnDel);
		
		MyButton btn10 = new MyButton("10,000");
		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn10.getText());
			}
		});
		paneLeft.add(btn10);
		
		MyButton btn20 = new MyButton("20,000");
		btn20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn20.getText());
			}
		});
		paneLeft.add(btn20);
		
		MyButton btn50 = new MyButton("50,000");
		btn50.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn50.getText());
			}
		});
		paneLeft.add(btn50);
		
		MyButton btn100 = new MyButton("100,000");
		btn100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn100.getText());
			}
		});
		paneLeft.add(btn100);
		
		MyButton btn200 = new MyButton("200,000");
		btn200.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn200.getText());
			}
		});
		paneLeft.add(btn200);
		
		MyButton btn500 = new MyButton("500,000");
		btn500.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn500.getText());
			}
		});
		paneLeft.add(btn500);
		
		MyButton btn1tr = new MyButton("1,000,000");
		btn1tr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput.setText(btn1tr.getText());
			}
		});
		paneLeft.add(btn1tr);
		
		MyButton btnCancel = new MyButton("Hủy");
		btnCancel.setBackground(new Color(150,20,46));
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnCancel.setBackground(new Color(124,0,24));
			}
			public void mouseExited(MouseEvent e) {
				btnCancel.setBackground(new Color(150,20,46));
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CafeHome.showPanelRight(0);
			}
		});
		paneLeft.add(btnCancel);
	}
	
	public void setCenter(){
		paneCenter = new JPanel();
		paneCenter.setOpaque(false);
		paneCenter.setBorder(new EmptyBorder(35, 5, 15, 0));
		paneCenter.setLayout(new GridLayout(4, 3, 10, 10));
		add(paneCenter, BorderLayout.CENTER);
		
		listBtn = new ArrayList<MyButton>();
		for(int i = 1; i < 10; i++) {
			MyButton btn = new MyButton(""+i);
			btn.addActionListener(actionListener);
			listBtn.add(btn);
			paneCenter.add(btn);
		}
		
		MyButton btn0 = new MyButton("0");
		btn0.addActionListener(actionListener);
		listBtn.add(btn0);
		paneCenter.add(btn0);
		
		MyButton btn000 = new MyButton("000");
		btn000.addActionListener(actionListener);
		listBtn.add(btn000);
		paneCenter.add(btn000);
		
		MyButton btnPay = new MyButton("<html>Thanh<br>Toán</html>");
		btnPay.setBackground(new Color(32,186,158));
		btnPay.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnPay.setBackground(new Color(29,132,113));
			}
			public void mouseExited(MouseEvent e) {
				btnPay.setBackground(new Color(32,186,158));
			}
		});
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Main.checkConfirm("Thanh toán", "Hoàn tất thanh toán?")) {
					String st = lblInput.getText().replaceAll(",", "");
					CafeHome.showPanelRight(0);
					if(st.isEmpty() || st  == "") {
						Main.showMessError("Thanh toán không thành công", "Vui lòng nhập số tiền khách đưa!");
						return;
					}
					int k = Integer.parseInt(st);
					PanelBill.finishPay(k);
				}
			}
		});
		paneCenter.add(btnPay);
	}
	
	public static void setInput(int pay){
		lblAmount.setText(FormatMoney.formatVnd(pay));
		lblInput.setText(FormatMoney.format(pay));
	}
	
	public  void updateInput(String a){
		String st = lblInput.getText().replaceAll(",", "")+a;
		if(st.length() < 8)
			lblInput.setText(FormatMoney.format(Integer.parseInt(st)));
	}
	
	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for(MyButton btn: listBtn) {
				if(e.getSource() == btn) {
					updateInput(btn.getText());
				}
			}
		}
	};
}
