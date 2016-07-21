package com.sar.taichinhcanhan.common;

import com.sar.taichinhcanhan.component.DatabaseManager;

public 
class LoanItem{
	public static final String KEY_DATETIME_BEGIN = "Ngay";
public static final String KEY_DATETIME_END = "NgayHenTra";
public static final String KEY_NAME = "Nguoi";
public static final String KEY_MONEY = "SoTien";
public static final String KEY_FROM_ACCOUNT = "TuTaiKhoan";
public static final String KEY_PURPOSE = "MucDich";
public static final String KEY_DESCRIPTION = "MoTa";
public static final String KEY_CODE = "Ma";
public static final String KEY_STATE = "TrangThai";
public static final String KEY_PAYED = "NgayTra";
	private String id,timeBegin,timeEnd,name,purpose,des,from,payed;
	private Double money;
	private Integer state;
	private DatabaseManager dataMan;
	
	public String getPayed() {
		return payed;
	}
	public void setPayed(String payed) {
		this.payed = payed;
	}
	public LoanItem() {
		dataMan=new DatabaseManager();
	}
	public LoanItem(String id,String name,String timeBegin,String timeEnd,String purpose,String from,Double money,String des,Integer state,String payed){
		this.id = id;
		this.from=from;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.name = name;
		this.purpose = purpose;
		this.des = des;
		this.money = money;
		this.state=state;
		this.payed=payed;
		dataMan=new DatabaseManager();
	}
	public String getFrom() {
		return from;
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	
	public String getStateString(){
		return state==0?"Chưa trả":"Đã trả";
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return timeBegin
				+" | "+name
				+" | "+timeEnd
				+" | "+purpose
				+" | "+dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME, from)
				+" | "+money
				+" | "+des
				+" | "+getStateString()
				+" | "+payed;
		
	}
	
}
