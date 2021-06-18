package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import bean.TableFood;

public class TableFoodDao {
	String sql;
	
	public TableFoodDao() throws SQLException {
		super();
	}

	public ArrayList<TableFood> getListTableFood(){
		sql = "select* from TableFood";
		ArrayList<TableFood> list = new ArrayList<TableFood>();
		try {
			Statement st = ConnecDataBase.getConnec().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(new TableFood(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int insertTableFood(String name, int status) {
		sql = "{Call proc_TableInsert(?,?,?)}";
		int id = 0;
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, status);
			cs.execute();
			id = cs.getInt(1);
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean updateTableFood(TableFood tb) {
		sql = "update TableFood set TableFoodName = ?, Status = ? where TableFoodId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(3, tb.getId());
			ps.setString(1, tb.getName());
			ps.setInt(2, tb.getStatus());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateStatus(int id, int status) {
		sql = "update TableFood set Status = ? where TableFoodId = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, id);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}