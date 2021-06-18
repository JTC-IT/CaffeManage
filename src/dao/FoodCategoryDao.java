package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.FoodCategory;

public class FoodCategoryDao {
	String sql;
	
	public FoodCategoryDao() throws SQLException {
		super();
	}

	public ArrayList<FoodCategory> getListFoodCategory(){
		sql = "select* from FoodCategory where Status = 1 order by FoodCategoryName";
		ArrayList<FoodCategory> list = new ArrayList<FoodCategory>();
		try {
			Statement st = ConnecDataBase.getConnec().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(new FoodCategory(rs.getInt(1), rs.getString(2), rs.getBoolean(3)));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean insertFoodCategory(String name) {
		sql = "insert into FoodCategory(FoodCategoryName) values(?)";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, name);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateFoodCategory(FoodCategory fc) {
		sql = "UPDATE FoodCategory SET FoodCategoryName = ?, Status = ? WHERE FoodCategory = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, fc.getName());
			ps.setInt(2, fc.getId());
			ps.setBoolean(3, fc.getStatus());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteFoodCategory(int id) {
		sql = "UPDATE FoodCategory SET Status = ? WHERE FoodCategory = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setBoolean(1, false);
			ps.setInt(2, id);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
