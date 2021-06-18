package bo;

import java.sql.SQLException;

import bean.Shop;
import dao.ShopDao;

public class ShopBo {
	private ShopDao shopDao;
	private Shop shop;
	
	public ShopBo() throws SQLException{
		super();
		shopDao = new ShopDao();
		shop = shopDao.getShop();
	}
	
	public Shop getShop(){
		return shop;
	}
	
	public boolean addShop(String name,String dc,String sdt){
		if(shop == null) {
			if(shopDao.insertQuan(name, dc, sdt)){
				shop = new Shop(name, dc, sdt);
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean updateShop(String name,String dc,String sdt){
		if(shop != null){
			if(shopDao.updateQuan(name, dc, sdt)){
				shop.setTenQuan(name);
				shop.setDiaChi(dc);
				shop.setSđt(sdt);
				return true;
			}
			return false;
		}
		return false;
	}	
}
