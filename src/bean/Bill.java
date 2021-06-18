package bean;

import java.sql.Timestamp;

public class Bill {
	private int Id;
	private Timestamp TimeIn;
	private Timestamp TimeOut;
	private int Staff;
	private int TableFood;
	private float Discount;
	private boolean Status;
	
	public Bill(int id, Timestamp timeIn, Timestamp timeOut, int idstaff, int tableFood, float discount, boolean status) {
		super();
		Id = id;
		TimeIn = timeIn;
		TimeOut = timeOut;
		Staff = idstaff;
		TableFood = tableFood;
		Discount = discount;
		Status = status;
	}
	public Bill() {super();}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Timestamp getTimeIn() {
		return TimeIn;
	}
	public void setTimeIn(Timestamp timeIn) {
		TimeIn = timeIn;
	}
	public Timestamp getTimeOut() {
		return TimeOut;
	}
	public void setTimeOut(Timestamp timeOut) {
		TimeOut = timeOut;
	}
	public int getStaff() {
		return Staff;
	}
	public void setStaff(int staff) {
		Staff = staff;
	}
	public int getTableFood() {
		return TableFood;
	}
	public void setTableFood(int tableFood) {
		TableFood = tableFood;
	}
	public float getDiscount() {
		return Discount;
	}
	public void setDiscount(float discount) {
		Discount = discount;
	}
	public boolean getStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
}