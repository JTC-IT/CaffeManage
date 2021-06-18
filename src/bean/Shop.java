package bean;

public class Shop {
	private String TenQuan;
	private String DiaChi;
	private String Sđt;
	public Shop(String tenQuan, String diaChi, String sđt) {
		super();
		TenQuan = tenQuan;
		DiaChi = diaChi;
		Sđt = sđt;
	}
	public String getTenQuan() {
		return TenQuan;
	}
	public void setTenQuan(String tenQuan) {
		TenQuan = tenQuan;
	}
	public String getDiaChi() {
		return DiaChi;
	}
	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}
	public String getSđt() {
		return Sđt;
	}
	public void setSđt(String sđt) {
		Sđt = sđt;
	}

}
