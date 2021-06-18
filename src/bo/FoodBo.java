package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.Food;
import dao.FoodDao;

public class FoodBo {
	private FoodDao foodDao;
	private ArrayList<Food> FoodList;
	
	public FoodBo() throws SQLException{
		foodDao = new FoodDao();
		FoodList = foodDao.getListFood();
	}
	
	public ArrayList<Food> getListFood(){
		return FoodList;
	}
	
	public Food getFood(int id) {
		for(Food food: FoodList)
			if(food.getId() == id)
				return food;
		return null;
	}
	
	public ArrayList<Food> getListFood(int foodCategoryId){
		ArrayList<Food> list = new ArrayList<Food>();
		for(Food food: FoodList)
			if(food.getCategory() == foodCategoryId)
				list.add(food);
		return list;
	}
	
	public ArrayList<Food> searchFood(String name){
		ArrayList<Food> list = new ArrayList<Food>();
		for(Food food: FoodList)
			if(food.getName().toLowerCase().indexOf(name) >= 0)
				list.add(food);
		return list;
	}
	
	public Food addFood(String foodName, int foodCategoryId, int price) {
		for(Food food: FoodList)
			if(food.getName().equals(foodName))
				return null;
		int id = foodDao.InsertFood(foodName, foodCategoryId, price);
		if(id == 0)
			return null;
		Food food = new Food(id, foodName, foodCategoryId, price, true);
		FoodList.add(food);
		return food;
	}
	
	public boolean updateFood(Food food) {
		if(foodDao.UpdateFood(food)) {
			for(Food f: FoodList)
				if(f.getId() == food.getId()) {
					f.setName(food.getName());
					f.setCategory(food.getCategory());
					f.setPrice(food.getPrice());
					f.setStatus(food.getStatus());
					return true;
				}
		}
		return false;
	}
	public boolean deleteFood(int id) {
		if(foodDao.DeleteFood(id)) {
			for(Food f: FoodList)
				if(f.getId() == id) {
					FoodList.remove(f);
					return true;
				}
		}
		return false;
	}
	
	public int getSize() {
		return FoodList.size();
	}
}
