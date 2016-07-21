package com.sar.taichinhcanhan.common;

import com.sar.taichinhcanhan.component.DatabaseManager;

public class DebtItem{
	public static final String KEY_DATETIME_BEGIN = "Ngay";
public static final String KEY_DATETIME_END = "NgayHenTra";
public static final String KEY_NAME = "Nguoi";
public static final String KEY_MONEY = "SoTien";
public static final String KEY_TO_ACCOUNT = "VaoTaiKhoan";
public static final String KEY_PURPOSE = "MucDich";
public static final String KEY_DESCRIPTION = "MoTa";
public static final String KEY_CODE = "Ma";
public static final String KEY_STATE = "TrangThai";
public static final String KEY_PAYED = "NgayTra"; //kieu thoi gian tra
	private String id,name,timeBegin,timeEnd,purpose,to,des,payed;
	private Double money;
	private Integer state;
	
	public String getPayed() {
		return payed;
	}
	public void setPayed(String payed) {
		this.payed = payed;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimeBegin() {
		return timeBegin;
	}
	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	private DatabaseManager dataMan;
	public DebtItem(String id, String name, String timeBegin, String timeEnd,
			String purpose, String to, Double money, String des,Integer state,String payed) {
		super();
		this.id = id;
		this.name = name;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.purpose = purpose;
		this.to = to;
		this.des = des;
		this.money = money;
		this.state=state;
		this.payed=payed;
		dataMan=new DatabaseManager();
	}
	public String getStateString(){
		return state==0?"Chưa trả":"Đã trả";
	}
	public DebtItem() {
		dataMan=new DatabaseManager();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (timeBegin
				+" | "+name
				+" | "+timeEnd
				+" | "+purpose
				+" | "+dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME, to)
				+" | "+money
				+" | "+des
				+" | "+getStateString())
				+" | "+payed;
	}
	
	
	
}