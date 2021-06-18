package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import bean.Bill;

public class BillDao {
	String sql;
	
	public BillDao() throws SQLException {
		super();
	}

	public ArrayList<Bill> getlistbill(){
		sql = "SELECT * FROM Bill WHERE Status = 0";
		ArrayList<Bill> list = new ArrayList<Bill>();
		try {
			Statement st = ConnecDataBase.getConnec().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(new Bill(rs.getInt(1),rs.getTimestamp(2),rs.getTimestamp(3),rs.getInt(4),rs.getInt(5),rs.getFloat(6),rs.getBoolean(7)));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int InsertBill(int staffId, int tableId, float dicount)
	{
		int ID = 0;
		sql = "{Call proc_BillInsert(?,?,?,?)}";
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, staffId);
			cs.setInt(3, tableId);
			cs.setFloat(4, dicount);
			cs.execute();
			ID = cs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ID;
	}
	public boolean UpdateBill(int billId, int staffId, int tableId, float discount, boolean status)
	{
		sql = "{Call proc_BillUpdate(?,?,?,?,?)}";
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.setInt(1, billId);
			cs.setInt(2, staffId);
			cs.setInt(3, tableId);
			cs.setFloat(4, discount);
			cs.setBoolean(5, status);
			cs.execute();
			cs.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean DeleteBill(int billid)
	{
		sql = "DELETE FROM Bill WHERE BillId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(1, billid);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
}
