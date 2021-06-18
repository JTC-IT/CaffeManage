package bean;

public class FoodCategory {
	private int Id;
	private String Name;
	private boolean Status;
	
	public FoodCategory(int id, String name, boolean status) {
		super();
		Id = id;
		Name = name;
		Status = status;
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

	public boolean getStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}
}
