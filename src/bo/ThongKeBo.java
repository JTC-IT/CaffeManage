package bo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import bean.FormatMoney;
import dao.ConnecDataBase;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Date;
public class ThongKeBo {
	NumberFormat en = NumberFormat.getInstance(new Locale("en", "EN"));
	String sql;
	
	public ThongKeBo() throws SQLException {
		super();
	}
	
	public DefaultTableModel Tk_ALL_by_Date(Date datef, Date dateto){
		DefaultTableModel tbmodel = new DefaultTableModel();
		Vector<String> cot = new Vector<String>();
		Vector<Vector<String>> rows = new Vector<Vector<String>>();		
		cot.add("ID");
		cot.add("Phục vụ");
		cot.add("Bàn");
		cot.add("Giờ vào");
		cot.add("Giờ ra");
		cot.add("Giảm giá");
		cot.add("T.Tiền");
		String qr = "{CALL Tk_by_date(?,?)}";
		ResultSet rs = null;
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setTimestamp(1, new Timestamp(datef.getTime()));
			stmt.setTimestamp(2, new Timestamp(dateto.getTime()));
			rs = stmt.executeQuery();
			while(rs.next())
			{	
				Vector<String> row = new Vector<String>();		
				row.add(rs.getString("BillId"));
				row.add(rs.getString("Fullname"));
				row.add(rs.getString("TableFoodName"));
				row.add(rs.getString("TimeIn"));
				row.add(rs.getString("TimeOut"));
				int gg = (int)(rs.getFloat("Discount")*100);
				String discount = String.valueOf(gg);
				row.add(discount + '%');				
				row.add(en.format(rs.getInt("T.Tiền")).toString());	
				
				rows.add(row);
			}
			tbmodel = new DefaultTableModel(rows,cot);
		} catch (Exception e) {
			e.printStackTrace();
		}
       return tbmodel;
	}
	
	public DefaultTableModel ListFoodByBillId(int billid)
	{
		DefaultTableModel tbmodel = new DefaultTableModel();
		Vector<String> cot = new Vector<String>();
		Vector<Vector<Object>> rows = new Vector<Vector<Object>>();		
		
		cot.add("Món");
		cot.add("Giá tiền");
		cot.add("Số lượng");
		cot.add("Thành tiền");
		cot.add("Bill");
		String qr = "{CALL InBill()}";
		
		ResultSet rs = null;
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setInt(1, billid);			
			rs = stmt.executeQuery();
			while(rs.next()){	
				Vector<Object> row = new Vector<Object>();		
				row.add(rs.getString("Tên hàng"));
				row.add(rs.getInt("Đ.giá"));
				row.add(rs.getInt("SL"));
				row.add(rs.getInt("T.Tiền"));			
				
				rows.add(row);
			}
			tbmodel = new DefaultTableModel(rows,cot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbmodel;
	}
	public DefaultTableModel Rank_Food(Date datef, Date dateto)
	{
		DefaultTableModel tbmodel = new DefaultTableModel();
		Vector<String> cot = new Vector<String>();
		Vector<Vector<Object>> rows = new Vector<Vector<Object>>();	
		cot.add("STT");
		cot.add("Tên món");
		cot.add("SL");
		String qr = "{CALL Tk_by_rankfood(?,?)}";
		ResultSet rs = null;
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setTimestamp(1, new Timestamp(datef.getTime()));
			stmt.setTimestamp(2, new Timestamp(dateto.getTime()));
			rs = stmt.executeQuery();
			int i = 1;
			while(rs.next())
			{	
				
				Vector<Object> row = new Vector<Object>();		
				row.add(i);				
				row.add(rs.getString("Name"));
				row.add(rs.getInt("SL"));			
				i++;
				rows.add(row);				
			}
			tbmodel = new DefaultTableModel(rows,cot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbmodel;
	}
	public ArrayList<Integer> Tk_Money(Date datef, Date dateto) //0:TGG  1:T.T ----2:TONGTIEN
	{
		ArrayList<Integer> ds = new ArrayList<Integer>();
		String qr = "{CALL Tk_Money(?,?)}";
		ResultSet rs = null;
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setTimestamp(1, new Timestamp(datef.getTime()));
			stmt.setTimestamp(2, new Timestamp(dateto.getTime()));
			rs = stmt.executeQuery();
			
			while(rs.next()){	
				ds.add(rs.getInt("TGG"));
				ds.add(rs.getInt("T.T"));
				ds.add(rs.getInt("TONG"));
				ds.add(rs.getInt("SLBill"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return ds;
	}	
	
	public ArrayList<Double> BieuDo1(int year){
		ArrayList<Double> ds = new ArrayList<Double>();
		String qr = "{Call BieuDo1(?)}";
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setInt(1, year);
			ResultSet rs = stmt.executeQuery();			
			while(rs.next()){	
				ds.add(Double.valueOf(rs.getInt("t1")));
				ds.add(Double.valueOf(rs.getInt("t2")));
				ds.add(Double.valueOf(rs.getInt("t3")));
				ds.add(Double.valueOf(rs.getInt("t4")));
				ds.add(Double.valueOf(rs.getInt("t5")));
				ds.add(Double.valueOf(rs.getInt("t6")));
				ds.add(Double.valueOf(rs.getInt("t7")));
				ds.add(Double.valueOf(rs.getInt("t8")));
				ds.add(Double.valueOf(rs.getInt("t9")));
				ds.add(Double.valueOf(rs.getInt("t10")));
				ds.add(Double.valueOf(rs.getInt("t11")));
				ds.add(Double.valueOf(rs.getInt("t12")));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return ds;
	}
	
	public ArrayList<Double> BieuDo2(int year){
		ArrayList<Double> ds = new ArrayList<Double>();
		String qr = "{Call BieuDo2(?)}";
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setInt(1, year);
			ResultSet rs = stmt.executeQuery();		
			while(rs.next()){
				ds.add(Double.valueOf(rs.getInt("t1")));
				ds.add(Double.valueOf(rs.getInt("t2")));
				ds.add(Double.valueOf(rs.getInt("t3")));
				ds.add(Double.valueOf(rs.getInt("t4")));
				ds.add(Double.valueOf(rs.getInt("t5")));
				ds.add(Double.valueOf(rs.getInt("t6")));
				ds.add(Double.valueOf(rs.getInt("t7")));
				ds.add(Double.valueOf(rs.getInt("t8")));
				ds.add(Double.valueOf(rs.getInt("t9")));
				ds.add(Double.valueOf(rs.getInt("t10")));
				ds.add(Double.valueOf(rs.getInt("t11")));
				ds.add(Double.valueOf(rs.getInt("t12")));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return ds;
	}
	
	public ArrayList<Vector<String>> XemTTBill(int billid){
		ArrayList<Vector<String>> ds = new ArrayList<Vector<String>>();
		String qr = "{CALL InBill(?)}";
		ResultSet rs = null;
		try {
			CallableStatement stmt = ConnecDataBase.getConnec().prepareCall(qr);
			stmt.setInt(1, billid);
			rs = stmt.executeQuery();
			
			while(rs.next()){	
				Vector<String> row = new Vector<>();
				row.add(rs.getString("Tên Hàng"));				
				row.add(FormatMoney.format(rs.getInt("Đ.giá")));
				row.add(rs.getString("SL"));				
				row.add(FormatMoney.format(rs.getInt("TT")));
				ds.add(row);
			}
			Vector<String> rowtam = new Vector<>();
			rowtam.add("");rowtam.add("");rowtam.add("");rowtam.add("");
			ds.add(rowtam);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ds;
	}
	
	public double[][] ConvertListToArray(ArrayList<Double> a, ArrayList<Double> b){
		double[][] arr = new double[2][12];
		for(int i = 0; i < 12; i++) {
			arr[0][i] = a.get(i);
			arr[1][i] = b.get(i);
		}
		return arr;
	}
}
