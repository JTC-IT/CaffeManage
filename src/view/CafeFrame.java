package view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.ConnecDataBase;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.CardLayout;
import java.awt.Color;

public class CafeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public CafeFrame() {
		UIManager.put("OptionPane.background", Main.myColor);
		UIManager.put("OptionPane.messageForeground", Color.ORANGE);
		UIManager.put("Panel.background", Main.myColor);
		UIManager.put("ScrollBarUI", "view.MyScrollBarUI");
		
		new ConnecDataBase();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(Main.getAccount() == null || Main.checkConfirm("Close System!", "Bạn có muốn đóng chương trình không?")) {
					ConnecDataBase.close();
					System.exit(0);
				}
			}
		});
		setTitle("CafeManager");
		setIconImage(new ImageIcon(CafeLogin.class.getResource("/imgs/logo.png")).getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(1000,700));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout());
	}
	public void addPanel(JPanel panel) {
		contentPane.add(panel);
	}
	
	public static void setBtnColor(JButton button, Color color) {
		button.setForeground(color);
		button.setBorder(new LineBorder(color, 1));
	}
}