package bean;

public class TableFood {
	private int Id;
	private String Name;
	private int Status;
	public TableFood(int id, String name, int status) {
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
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
}
