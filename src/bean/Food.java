package bean;

public class Food {
	private int Id;
	private String Name;
	private int Price;
	private int Category;
	private boolean Status;
	
	public Food(int id, String name, int category, int price, boolean status) {
		super();
		Id = id;
		Name = name;
		Price = price;
		Category = category;
		Status = status;
	}
	public Food(Food food) {
		super();
		Id = food.getId();
		Name = food.getName();
		Price = food.getPrice();
		Category = food.getCategory();
		Status = food.getStatus();
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getCategory() {
		return Category;
	}
	public void setCategory(int category) {
		Category = category;
	}
	public boolean getStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
}