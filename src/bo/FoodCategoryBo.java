package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import bean.FoodCategory;
import dao.FoodCategoryDao;

public class FoodCategoryBo {
	private FoodCategoryDao fcDao;
	private ArrayList<FoodCategory> FoodCategoryList;
	
	public FoodCategoryBo() throws SQLException {
		fcDao = new FoodCategoryDao();
		FoodCategoryList = fcDao.getListFoodCategory();
	}
	
	public void refeshListFoodCategory() {
		FoodCategoryList = fcDao.getListFoodCategory();
	}
	
	public FoodCategory getFCategory(int id) {
		for(FoodCategory fc: FoodCategoryList)
			if(fc.getId() == id)
				return fc;
		return null;
	}
	
	public FoodCategory getFCategory(String name) {
		for(FoodCategory fc: FoodCategoryList)
			if(fc.getName().equals(name))
				return fc;
		return null;
	}
	
	public ArrayList<FoodCategory> getListFoodCategory() {
		return FoodCategoryList;
	}
	
	public boolean AddFoodCategor(String name) {
		if(fcDao.insertFoodCategory(name)){
			refeshListFoodCategory();
			return true;
		}
		return false;
	}
	
	public DefaultComboBoxModel<String> getListName(){
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		
		ArrayList<FoodCategory> list = fcDao.getListFoodCategory();
		for(FoodCategory fc: list)
			if(fc.getStatus())
				model.addElement(fc.getName());
		return model;
	}
}
