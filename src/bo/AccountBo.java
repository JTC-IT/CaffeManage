package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Account;
import dao.AccountDao;

public class AccountBo {
	private AccountDao Acdao;
	private ArrayList<Account> AccountList;
	
	public AccountBo() throws SQLException {
		super();
		Acdao = new AccountDao();
	}
	
	public void refresh() {
		AccountList = Acdao.getListAccount();
	}
	
	public int checkLogin(String username, String password) {
		int result = Acdao.checkAccount(username, password);
		if(result > 0)
			refresh();
		return result;
	}

	public ArrayList<Account> getAccountList(){
		return AccountList;
	}
	
	public Account getAccount(int id){
		for(Account ac:AccountList)
			if(ac.getId()==id)
				return ac;
		return null;
	}
	
	public ArrayList<Account> getlistAccount(int type){
		ArrayList<Account> ds = new ArrayList<Account>();
		for(Account ac:AccountList)
			if(ac.getType()==type)
				ds.add(ac);
		return ds;
	}
	
	public ArrayList<Account> getlistAccount(String name)
	{
		ArrayList<Account> ds = new ArrayList<Account>();
		for(Account ac:AccountList)
			if(ac.getUsername().toLowerCase().indexOf(name.toLowerCase()) >= 0||ac.getFullname().toLowerCase().indexOf(name.toLowerCase()) >= 0)
				ds.add(ac);
		return ds;
	}
	
	public boolean addAccount(String username, String fullname, String password, int type) {
		if(!checkUsername(username, 0))
			return false;
		int id = Acdao.insertAccount(username, fullname, password, type);
		if(id <= 0)
			return false;
		AccountList.add(new Account(id, username, fullname, password, type));
		return true;
	}
	
	public boolean updateAccount(Account ac) {
		if(!checkUsername(ac.getUsername(), ac.getId()))
			return false;
		if(Acdao.updateAccount(ac)) {
			for(Account a: AccountList) {
				if(a.getId() == ac.getId()) {
					a.setUsername(ac.getUsername());
					a.setFullname(ac.getFullname());
					a.setPassword(ac.getPassword());
					a.setType(ac.getType());
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean deleteAccount(int id) {
		if(Acdao.deleteAccount(id)) {
			for(Account a: AccountList) {
				if(a.getId() == id) {
					AccountList.remove(a);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkUsername(String username, int id) {
		for(Account ac: AccountList) {
			if(ac.getId() == id)
				continue;
			if(ac.getUsername().equals(username))
				return false;
		}
		return true;
	}
}
