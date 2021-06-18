package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.TableFood;
import dao.TableFoodDao;

public class TableFoodBo {
	private TableFoodDao tableDao;
	private ArrayList<TableFood> TableFoodList;
	
	public TableFoodBo() throws SQLException {
		tableDao = new TableFoodDao();
		TableFoodList = tableDao.getListTableFood();
	}
	
	public ArrayList<TableFood> getListTableFood(){
		return TableFoodList;
	}
	
	public TableFood getTableFood(int id) {
		for(TableFood tf: TableFoodList) 
			if(tf.getId() == id) {
				return tf;
			}
		return null;
	}
	
	public ArrayList<TableFood> getListTableFood(int k){
		ArrayList<TableFood> list = new ArrayList<>();
		for(TableFood tf: TableFoodList) 
			if(tf.getStatus() == k)
				list.add(tf);
		return list;
	}
	
	public ArrayList<TableFood> getListTableFood(String name){
		ArrayList<TableFood> list = new ArrayList<>();
		for(TableFood tf: TableFoodList) 
			if(tf.getName().indexOf(name) >= 0)
				list.add(tf);
		return list;
	}
	
	public TableFood AddTableFood(String name, int status) {
		int id = tableDao.insertTableFood(name, status);
		if(id == 0)
			return null;
		TableFood table = new TableFood(id, name, status);
		TableFoodList.add(table);
		return table;
	}
	
	public void setStatus(int id, int status) {
		TableFood tf = getTableFood(id);
		if(tf != null)
			tf.setStatus(status);
		tableDao.updateStatus(id, status);
	}
	
	public boolean updateTableFood(TableFood tbFood) {
		if(tableDao.updateTableFood(tbFood))
			for(TableFood tf: TableFoodList) 
				if(tf.getId() == tbFood.getId()) {
					tf.setName(tbFood.getName());
					tf.setStatus(tbFood.getStatus());
					return true;
				}
		return false;
	}
	
	public int getSize() {
		int d = 0;
		for(TableFood tf: TableFoodList) 
			if(tf.getStatus() != -1)
				d++;
		return d;
	}
}