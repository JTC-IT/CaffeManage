package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import bo.ThongKeBo;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import org.jfree.data.general.DatasetUtilities;

public class BieuDo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private  JTabbedPane tabbedPane;
	private ThongKeBo tkb;
	private int year;
	
	public BieuDo(int year) throws SQLException {
		super("Biểu Đồ Thống Kê");
		
		this.year = year;
		UIManager.put("TabbedPane.selected", Color.decode("#8A2FF6"));	
		
		setSize(700, 480);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(new Color(255, 255, 255));
		tabbedPane.setBackground( Color.decode("#53544F"));
		tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		tabbedPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);		
		setContentPane(chartPanel);
	}
	
	private CategoryDataset createDataset() throws SQLException {
		  tkb = new ThongKeBo(); 	  
		  String[] type = new String[] {"Doanh thu", "Giảm giá"};
		  String[] loai = new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		  return DatasetUtilities.createCategoryDataset(type, loai, tkb.ConvertListToArray(tkb.BieuDo1(year), tkb.BieuDo2(year)));
	}
	private JFreeChart createChart(final CategoryDataset dataset) {

		  final JFreeChart chart = 
		   ChartFactory.createStackedBarChart3D(
		  "THỐNG KÊ DOANH THU THEO THÁNG TRONG NĂM "+ year, "THÁNG", "VNĐ",
		  dataset, PlotOrientation.VERTICAL, true, true, false);

		  chart.setBackgroundPaint(new Color(249, 231, 236));

		  CategoryPlot plot = chart.getCategoryPlot();
		  plot.getRenderer().setSeriesPaint(0, new Color(30, 100, 175));		  
		  plot.getRenderer().setSeriesPaint(2, new Color(225, 45, 45));
		  return chart;
	}	
}