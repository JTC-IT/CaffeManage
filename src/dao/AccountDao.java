package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import bean.Account;

public class AccountDao {
	String sql;
	
	public AccountDao() throws SQLException {
		super();
	}

	public ArrayList<Account> getListAccount(){
		ArrayList<Account> list = new ArrayList<Account>();
		sql = "select * from Account where Type > -1";
		try {
			Statement st = ConnecDataBase.getConnec().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				list.add(new Account(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int insertAccount(String username, String fullname, String password, int type) {
		sql = "{Call proc_AccountInsert(?,?,?,?,?)}";
		int id = 0;
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, fullname);
			cs.setString(4, password);
			cs.setInt(5, type);
			cs.execute();
			id = cs.getInt(1);
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean updateAccount(Account ac) {
		sql = "update Account set Username = ?, Fullname = ?, Password = ?, Type = ? where IdStaff = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, ac.getUsername());
			ps.setString(2, ac.getFullname());
			ps.setString(3, ac.getPassword());
			ps.setInt(4, ac.getType());
			ps.setInt(5, ac.getId());
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteAccount(int id) {
		sql = "update Account set Fullname = ?, Type = ? where IdStaff = ?";
		try {
			PreparedStatement ps = ConnecDataBase.getConnec().prepareStatement(sql);
			ps.setString(1, "Đã xóa!");
			ps.setInt(2, -1);
			ps.setInt(3, id);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int checkAccount(String username, String pass) {
		sql = "{? = Call func_CheckAccount(?,?)}";
		int id = 0;
		try {
			CallableStatement cs = ConnecDataBase.getConnec().prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, pass);
			cs.execute();
			id = cs.getInt(1);
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}
