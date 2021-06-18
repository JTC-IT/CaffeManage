package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import bean.Account;

public class Main {
	private static CafeFrame frame;
	private static CafeLogin FLogin;
	private static CafeHome FHome;
	private static PanelTaskbar taskBar;
	private static Account account;
	
	final static Color myColor = Color.decode("#383342");

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		frame = new CafeFrame();
		
		try {
			FLogin = new CafeLogin();
			FHome = new CafeHome();
			frame.add(FLogin);
			frame.add(FHome);
			frame.setVisible(true);
			
			taskBar = new PanelTaskbar();
			FHome.add(taskBar, BorderLayout.NORTH);
			taskBar.setPreferredSize(new Dimension(0, 100));
			
			setFrame(false);
		} catch (SQLException e) {
			showMessError("Lỗi kết nối Database!", "<html>Không có Dữ liệu!<br>Vui lòng xem lại kết nối Database và khởi động lại</html>");
			System.exit(0);
			e.printStackTrace();
		}
	}
	
	public static void refeshHome(boolean isLogin) {
		taskBar.setTaskbar(Main.getAccount().getType() == 1, Main.getAccount().getFullname());
		if(isLogin)
			taskBar.clickItem(1);
	}
	
	public static void setFrame(boolean isLogin) {
		if(isLogin) {
			FLogin.setVisible(false);
			FHome.setVisible(true);
			refeshHome(true);
		}else {
			FHome.setVisible(false);
			FLogin.setVisible(true);
		}
	}
	
	public static Account getAccount() {
		return account;
	}
	
	public static void setAccount(Account ac) {
		if(ac == null)
			account = null;
		else account = new Account(ac.getId(), ac.getUsername(), ac.getFullname(), ac.getPassword(), ac.getType());
	}
	
	public static boolean checkConfirm(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION
				, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}
	
	public static void showMessError(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showMessCorrect(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int getInputMess(String message, int k) {
		int n = -1;
		try {
			String kq = JOptionPane.showInputDialog(null, message,k);
			if(kq != null && !kq.isEmpty() && Integer.parseInt(kq) >= 0)
				n = Integer.parseInt(kq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}
}