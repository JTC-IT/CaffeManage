package bean;

public class Account {
	private int Id;
	private String Username;
	private String Fullname;
	private String Password;
	private int Type;
	public Account(int id, String username, String fullname, String password, int type) {
		super();
		Id = id;
		Username = username;
		Fullname = fullname;
		Password = password;
		Type = type;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getFullname() {
		return Fullname;
	}
	public void setFullname(String fullname) {
		Fullname = fullname;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
}
