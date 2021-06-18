package bo;

import java.sql.SQLException;
import java.util.ArrayList;
import bean.BillInfor;
import dao.BillInforDao;

public class BillInforBo {
	private BillInforDao billInforDao;
	private int BillId;
	private ArrayList<BillInfor> List;
	private boolean Edit;
	
	public BillInforBo(int billId) throws SQLException {
		super();
		billInforDao = new BillInforDao();
		Edit = false;
		BillId = billId;
		List = billInforDao.getBillInfor(billId);
	}

	public boolean isEdit() {
		return Edit;
	}

	public void setEdit(boolean edit) {
		this.Edit = edit;
	}

	public ArrayList<BillInfor> getList() {
		return List;
	}

	public void setList(ArrayList<BillInfor> list) {
		List = list;
	}
	
	public boolean isEmpty() {
		return List.size() == 0;
	}
	
	public BillInfor getBillInfo(int foodid) {
		for(BillInfor bf: List)
			if(bf.getFood() == foodid)
				return bf;
		return null;
	}
	
	public void addAmount(int index, int k) {
		if(k == -1 && List.get(index).getAmount() == 1)
			deleteBillInfor(index);
		else{
			List.get(index).addAmount(k);
			Edit = true;
		}
	}
	
	public boolean addBillInfor(int foodid) {
		if(getBillInfo(foodid) == null) {
			List.add(new BillInfor(BillId, foodid, 1));
			return true;
		}
		else {
			getBillInfo(foodid).addAmount(1);
			return false;
		}
	}
	
	public void saveBillInfor() {
		for(BillInfor bf: List)
			billInforDao.insertBillInfor(BillId, bf.getFood(), bf.getAmount());
	}
	
	public void setBill(int billOld, ArrayList<BillInfor> list) {
		billInforDao.setBillId(billOld, BillId);
		for(BillInfor b: list)
			b.setBill(BillId);
		List.addAll(list);
	}
	
	public void deleteBillInfor(int index) {
		billInforDao.deleteBillInfor(BillId,List.get(index).getFood());
		List.remove(index);
	}
	
	public void deleteAll() {
		billInforDao.deleteBillInforByBill(BillId);
		List.clear();
	}
}
