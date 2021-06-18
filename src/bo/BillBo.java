package bo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import bean.Bill;
import bean.BillInfor;
import bean.Food;
import bean.FormatMoney;
import dao.BillDao;

public class BillBo {
	private BillDao billDao;
	private FoodBo foodBo;
	private ArrayList<Bill> ListBill;
	private HashMap<Integer, BillInforBo> hashMap;
	
	private int tableId;
	
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int table) {
		this.tableId = table;
	}

	public BillBo(FoodBo fb) throws SQLException {
		super();
		foodBo = fb;
		billDao = new BillDao();
		hashMap = new HashMap<Integer, BillInforBo>();
		refreshListBill();
	}
	
	public BillInforBo getBillInforBo() {
		return hashMap.get(getBill().getId());
	}

	public ArrayList<Bill> getListBill() {
		return ListBill;
	}

	public void refreshListBill() throws SQLException {
		ListBill = billDao.getlistbill();
		for(Bill bill: ListBill) {
			hashMap.put(bill.getId(), new BillInforBo(bill.getId()));
		}
	}
	
	public Bill getBill() {
		return getBill(tableId);
	}
	
	public Bill getBill(int table) {
		for(Bill bill: ListBill)
			if(bill.getTableFood() == table)
				return bill;
		return null;
	}
	
	public int addBill(int staffId) throws SQLException {
		int id = billDao.InsertBill(staffId, tableId, 0);
		if(id > 0) {
			ListBill.add(new Bill(id, new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), staffId, tableId, 0, false));
			hashMap.put(id, new BillInforBo(id));
		}
		return id;
	}
	
	public boolean updateBill(int staffId, float discount, boolean status){
		Bill bill = getBill();
		if(billDao.UpdateBill(bill.getId(), staffId, tableId, discount, status)) {
			if(status) {
				ListBill.remove(bill);
				hashMap.remove(bill.getId());
			}else {
				bill.setStaff(staffId);
				bill.setDiscount(discount);
				bill.setStatus(status);
			}
			return true;
		}
		return false;
	}
	
	public void moveTable(int tableNew) {
		Bill bill = getBill();
		billDao.UpdateBill(bill.getId(), bill.getStaff(), tableNew, bill.getDiscount(), false);
		bill.setTableFood(tableNew);
	}
	
	public void megreBill(int tableFrom) {
		Bill billFrom = getBill(tableFrom);
		getBillInforBo().setBill(billFrom.getId(), hashMap.get(billFrom.getId()).getList());
		int id = billFrom.getId();
		hashMap.remove(id);
		ListBill.remove(billFrom);
		billDao.DeleteBill(id);
	}
	
	public void deleteBill(){
		Bill bill = getBill();
		int id = bill.getId();
		hashMap.get(id).deleteAll();
		hashMap.remove(id);
		ListBill.remove(bill);
		billDao.DeleteBill(id);
	}
	
	public int getSumCost() {
		int sum = 0;
		for(BillInfor bf: getBillInforBo().getList())
			sum += foodBo.getFood(bf.getFood()).getPrice()*bf.getAmount();
		return sum;
	}
	
	public ArrayList<Object[]> getBillInfor() {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		int i = 1;
		for(BillInfor bf: getBillInforBo().getList()) {
			Food f = foodBo.getFood(bf.getFood());
			list.add(new Object[] {i
					,f.getName()
					,FormatMoney.formatVnd(f.getPrice())
					,bf.getAmount()
					,FormatMoney.formatVnd(f.getPrice()*bf.getAmount())
					});
			i++;
		}
		return list;
	}
	
	public int ThanhTien() {
		Bill bill = getBill();
		int cost = getSumCost();
		return (int) (cost - (cost*bill.getDiscount()));
	}
}