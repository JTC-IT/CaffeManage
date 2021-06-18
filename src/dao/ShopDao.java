package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.Shop;

public class ShopDao {
	String sql;
	public ShopDao() throws SQLException
	{
		super();
	}
	public Shop getShop(){
		sql = "SELECT * FROM TTQuan";
		try {
			Statement st = ConnecDataBase.getConnec().createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				Shop shop = new Shop(rs.getString(1), rs.getString(2), rs.getString(3));
				rs.close();
				st.close();
				return shop;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertQuan(String name,String dc,String sdt){
		if(getShop() != null)
			return false;
		sql = "INSERT INTO TTQuan(TenQuan,DiaChi,Sđt) VALUES(?,?,?)";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, dc);
			ps.setString(3, sdt);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateQuan(String name,String dc,String sdt){
		if(getShop() == null)
			return false;
		sql = "UPDATE TTQuan SET TenQuan = ?, DiaChi = ?, Sđt = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, dc);
			ps.setString(3, sdt);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}	
}
