package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import bean.Account;
import bo.AccountBo;

public class CafeLogin extends ImagePanel {
	private static final long serialVersionUID = 1L;

	private JPanel paneCenter;
	
	private ImagePanel login_panel;
	private JLabel title;
	private JLabel lblUser;
	private JLabel lblPass;
	private JTextField Username;
	private JPasswordField Password;
	private JLabel lblError;
	private JButton btnLogin;
	
	private AccountBo AcBo;

	public CafeLogin() throws SQLException {
		AcBo = new AccountBo();
		
		setImage("/imgs/coffeeshop.jpg");
		setLayout(new BorderLayout());
		
		JLabel lblLicense = new JLabel("Bản quyền thuộc nhóm sinh viên IT_PT-TKHT_K42-HUSC"
				, new ImageIcon(CafeLogin.class.getResource("/imgs/c-18.png"))
				, SwingConstants.CENTER);
		lblLicense.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLicense.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLicense.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblLicense.setForeground(Color.white);
		add(lblLicense, BorderLayout.CENTER);
		
		login_panel = new ImagePanel();
		login_panel.setImage("/imgs/bglogin.png");
		login_panel.setOpaque(false);
		login_panel.setLayout(new BorderLayout());
		add(login_panel,BorderLayout.EAST);
		login_panel.setPreferredSize(new Dimension(600, 0));
		
		title = new JLabel("Đăng nhập Hệ thống");
		title.setFont(new Font("Segoe UI", Font.BOLD, 27));
		title.setForeground(Color.orange);
		title.setIcon(new ImageIcon(CafeLogin.class.getResource("/imgs/security-48.png")));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setHorizontalTextPosition(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		title.setVerticalTextPosition(SwingConstants.TOP);
		login_panel.add(title,BorderLayout.NORTH);
		title.setPreferredSize(new Dimension(0,230));
		
		SpringLayout layout = new SpringLayout();
		
		paneCenter = new JPanel();
		paneCenter.setOpaque(false);
		paneCenter.setLayout(layout);
		login_panel.add(paneCenter, BorderLayout.CENTER);
		
		lblUser = new JLabel("Tên tài khoản:");
        lblUser.setIcon(new ImageIcon(CafeLogin.class.getResource("/imgs/user-30.png")));
		lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblUser.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblUser.setForeground(Color.LIGHT_GRAY);
		paneCenter.add(lblUser);
		layout.putConstraint(SpringLayout.WEST, lblUser, -185, SpringLayout.HORIZONTAL_CENTER, paneCenter);
        layout.putConstraint(SpringLayout.NORTH, lblUser, 36, SpringLayout.NORTH, paneCenter);
		
        lblPass = new JLabel("Mật khẩu:");
        lblPass.setIcon(new ImageIcon(CafeLogin.class.getResource("/imgs/password-30.png")));
		lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblPass.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblPass.setForeground(Color.LIGHT_GRAY);
		paneCenter.add(lblPass);
		layout.putConstraint(SpringLayout.WEST, lblPass, -185, SpringLayout.HORIZONTAL_CENTER, paneCenter);
        layout.putConstraint(SpringLayout.NORTH, lblPass, 120, SpringLayout.NORTH, paneCenter);
        
        Username = new JTextField("",26);
        Username.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if(Password.getPassword().length == 0)
        				Password.requestFocus();
        			else login();
        		}
        		super.keyPressed(e);
        	}
		});
        Username.setFont(new Font("Segoe UI", Font.BOLD, 15));
        Username.setForeground(Color.yellow);
        Username.setOpaque(false);
        Username.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
        Username.setCaretColor(Color.yellow);
        Username.setHorizontalAlignment(SwingConstants.CENTER);
        paneCenter.add(Username);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Username, 0, SpringLayout.HORIZONTAL_CENTER, paneCenter);
        layout.putConstraint(SpringLayout.NORTH, Username, 70, SpringLayout.NORTH, paneCenter);
        
        Password = new JPasswordField("",26);
        Password.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        			login();
        		}
        		super.keyPressed(e);
        	}
		});
        Password.setFont(new Font("Segoe UI", Font.BOLD, 15));
        Password.setForeground(Color.yellow);
        Password.setOpaque(false);
        Password.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
        Password.setCaretColor(Color.yellow);
        Password.setHorizontalAlignment(SwingConstants.CENTER);
        paneCenter.add(Password);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Password, 0, SpringLayout.HORIZONTAL_CENTER, paneCenter);
        layout.putConstraint(SpringLayout.NORTH, Password, 155, SpringLayout.NORTH, paneCenter);
        
        lblError = new JLabel("");
        lblError.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblError.setForeground(Color.orange);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        paneCenter.add(lblError);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblError, 0, SpringLayout.HORIZONTAL_CENTER, paneCenter);
        layout.putConstraint(SpringLayout.NORTH, lblError, 220, SpringLayout.NORTH, paneCenter);
        lblError.setVisible(false);
        
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.white);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorder(new LineBorder(Color.WHITE, 1));
        btnLogin.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
				CafeFrame.setBtnColor(btnLogin, Color.green);
			}
			public void mouseExited(MouseEvent e) {
				CafeFrame.setBtnColor(btnLogin, Color.white);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
        paneCenter.add(btnLogin);
        btnLogin.setPreferredSize(new Dimension(115, 30));
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnLogin, 0, SpringLayout.HORIZONTAL_CENTER, paneCenter);
        layout.putConstraint(SpringLayout.NORTH, btnLogin, 250, SpringLayout.NORTH, paneCenter);
	}
	
	public void login() {
		String name = Username.getText().trim();
		if(name.isEmpty()){
			refeshLogin();
			lblError.setText("Tên tài khoản không được để trống!");
			lblError.setVisible(true);
			return;
		}
		String pass = String.valueOf(Password.getPassword()).trim();
		if(pass.isEmpty()){
			refeshLogin();
			lblError.setText("Mật khẩu không hợp lệ!");
			lblError.setVisible(true);
			return;
		}
		int result = AcBo.checkLogin(name, pass);
		if(result > 0) {
			Account ac = AcBo.getAccount(result);
			Main.setAccount(ac);
			Main.setFrame(true);
			lblError.setVisible(false);
		}else {
			lblError.setText("Tên đăng nhập hoặc mật khẩu không đúng!");
			lblError.setVisible(true);
		}
		refeshLogin();
	}
	
	public void refeshLogin() {
		Username.setText("");
		Password.setText("");
		Username.requestFocus();
	}
}
