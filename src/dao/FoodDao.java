package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import bean.Food;

public class FoodDao {
	String sql;
	
	public FoodDao() throws SQLException {
		super();
	}

	public ArrayList<Food> getListFood(){
		sql = "select * from Food where Status = 1 order by FoodName";
		ArrayList<Food> list = new ArrayList<Food>();
		try {
			Statement st = ConnecDataBase.getConnec().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(new Food(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getBoolean(5)));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int InsertFood(String foodName, int foodCategoryId, int price) {
		sql = "{Call proc_FoodInsert(?,?,?,?)}";
		int id = 0;
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, foodName);
			cs.setInt(3, foodCategoryId);
			cs.setInt(4, price);
			cs.execute();
			id = cs.getInt(1);
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean UpdateFood(Food food) {
		sql = "UPDATE Food SET FoodName = ?, FoodCategoryId = ?, Price = ?, Status = ?  WHERE FoodId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, food.getName());
			ps.setInt(2, food.getCategory());
			ps.setInt(3, food.getPrice());
			ps.setBoolean(4, food.getStatus());
			ps.setInt(5, food.getId());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean DeleteFood(int id) {
		sql = "UPDATE Food SET Status = ?  WHERE FoodId = ?";
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
