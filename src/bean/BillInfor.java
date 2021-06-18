package bean;

public class BillInfor {
	private int Bill;
	private int Food;
	private int Amount;
	
	public BillInfor(int bill, int food, int amount) {
		super();
		Bill = bill;
		Food = food;
		Amount = amount;
	}
	public int getBill() {
		return Bill;
	}
	public void setBill(int bill) {
		Bill = bill;
	}
	public int getFood() {
		return Food;
	}
	public void setFood(int food) {
		Food = food;
	}
	public int getAmount() {
		return Amount;
	}
	public void setAmount(int amount) {
		Amount = amount;
	}
	public void addAmount(int k) {
		Amount += k;
	}
}
