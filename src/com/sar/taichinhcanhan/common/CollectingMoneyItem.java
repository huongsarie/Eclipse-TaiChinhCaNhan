package com.sar.taichinhcanhan.common;

public class CollectingMoneyItem{
	public static final String KEY_DATETIME = "Ngay";
	public static final String KEY_MONEY = "SoTien";
	public static final String KEY_CODEID = "Ma";
	public static final String KEY_DESCRIPTION = "MoTa";
	public static final String KEY_FROM = "NguonThu";
	public static final String KEY_ACCOUNT = "MaTaiKhoan";
	private String id,datetime,from,toAcc,description;
	private double money;
	
	public CollectingMoneyItem() {
		super();
	}
	
	public CollectingMoneyItem(String id, String datetime, String from,
			String toAcc, String description, double money) {
		super();
		this.id = id;
		this.datetime = datetime;
		this.from = from;
		this.toAcc = toAcc;//idAccount
		this.description = description;
		this.money = money;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getToAcc() {
		return toAcc;
	}
	public void setToAcc(String toAcc) {
		this.toAcc = toAcc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	
}

