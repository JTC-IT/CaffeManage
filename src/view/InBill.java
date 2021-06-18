package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import bo.ShopBo;
import dao.ConnecDataBase;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class InBill {
	
	private ShopBo shopBo;
	
	public  InBill(){
		super();
	}
	
	public void PrintBill(int billid){
		try {
			shopBo = new ShopBo();
			
			String filein = new File("").getAbsolutePath()+"/library/bill.jrxml";		
			InputStream in = new FileInputStream(new File(filein));
			JasperDesign jd = JRXmlLoader.load(in);
			String sql ="SELECT f.FoodName AS \"Tên Hàng\",f.Price AS \"Giá\", bf.Amount AS \"SL\",(f.Price * bf.Amount) AS \"TT\", \r\n"
					+ "(SELECT ac.Fullname \r\n"
					+ "FROM Account AS ac \r\n"
					+ "WHERE ac.IdStaff =  b.IdStaff) AS \"Thu ngân\",\r\n"
					+ "(SELECT tb.TableFoodName FROM TableFood AS tb WHERE tb.TableFoodId = b.TableFoodId) AS \"Bàn\",\r\n"
					+ "(SELECT b.TimeIn FROM Bill AS b WHERE b.BillId = $P{BillID} )AS \"TimeIn\",\r\n"
					+ "(SELECT b.TimeOut FROM Bill AS b WHERE b.BillId =  $P{BillID} )AS \"TimeOut\",\r\n"
					+ "b.Discount \r\n"
					+ "\r\n"
					+ "FROM Bill AS b\r\n"
					+ "JOIN BillInfo AS bf ON b.BillId = bf.BillId\r\n"
					+ "JOIN Food AS f ON bf.FoodId = f.FoodId\r\n"
					+ "WHERE b.BillId = $P{BillID}";
			JRDesignQuery nqr = new JRDesignQuery();
			nqr.setText(sql);
			jd.setQuery(nqr);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			;
			HashMap<String, Object> para = new HashMap<String, Object>();
			para.put("BillID", billid);
			para.put("sdt", shopBo.getShop().getSđt());
			para.put("diachiquan", shopBo.getShop().getDiaChi());
			para.put("namequan", shopBo.getShop().getTenQuan());
			JasperPrint jpri = JasperFillManager.fillReport(jr, para,ConnecDataBase.getConnec());				
			JasperViewer.viewReport(jpri,false);
		} catch (FileNotFoundException | JRException | SQLException e1) {					
			e1.printStackTrace();
		}		
	}
}
