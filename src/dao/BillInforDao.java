package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.BillInfor;

public class BillInforDao {
	String sql;
	
	public BillInforDao() throws SQLException {
		super();
	}
	
	public ArrayList<BillInfor> getBillInfor(int billId)
	{
		sql = "SELECT * FROM BillInfo WHERE BillId = ?";
		ArrayList<BillInfor> list = new ArrayList<BillInfor>();
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(1, billId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new BillInfor(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return list;
	}
	
	public boolean insertBillInfor(int billid, int foodid, int amount) {
		sql = "{Call proc_BillInfoInsert(?,?,?)}";
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.setInt(1, billid);
			cs.setInt(2, foodid);
			cs.setInt(3, amount);
			return cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setBillId(int billFirst, int billLast) {
		sql = "UPDATE BillInfo SET BillId = ? WHERE BillId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(1, billLast);
			ps.setInt(2, billFirst);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBillInfor(int billid, int foodid) {
		sql = "DELETE FROM BillInfo WHERE BillId = ? AND FoodId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(1, billid);
			ps.setInt(2, foodid);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBillInforByBill(int billId) {
		sql = "DELETE FROM BillInfo WHERE BillId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(1, billId);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}