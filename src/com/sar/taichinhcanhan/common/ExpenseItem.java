package com.sar.taichinhcanhan.common;

import com.sar.taichinhcanhan.component.DatabaseManager;

public class ExpenseItem{
	public static final String KEY_DATETIME = "Ngay";
	public static final String KEY_CATALOGUE = "MaDanhMuc";
	public static final String KEY_PRODUCT = "TenSanPham";
	public static final String KEY_MONEY = "SoTien";
	public static final String KEY_CODEID = "Ma";
	public static final String KEY_DESCRIPTION = "MoTa";
	public static final String KEY_ACCOUNT = "ChiTu";
	private String datetime,cataloge,product,des,from,id;
	private DatabaseManager dataMan;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private Double money;
	public ExpenseItem(){
		dataMan=new DatabaseManager();
	}
	public ExpenseItem(String id,String datetime, String cataloge, String product, Double money,
			String des, String from) {
		dataMan=new DatabaseManager();
		this.id=id;
		this.datetime = datetime;
		this.cataloge = cataloge; //idCatalogue
		this.product = product;
		this.des = des;
		this.from = from;
		this.money = money;
	}
	
	
	public ExpenseItem(String datetime, String cataloge, String product,
			String des, String from, Double money) {
		super();
		this.datetime = datetime;
		this.cataloge = cataloge;//idCatalogue
		this.product = product;
		this.des = des;
		this.from = from;
		this.money = money;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getCataloge() {
		return cataloge;
	}
	public void setCataloge(String cataloge) {
		this.cataloge = cataloge;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String string=datetime
				+" | "+dataMan.getNameByID(CommonVL.CATALOGUE_TABLE_NAME, cataloge)
				+" | "+product
				+" | "+money
				+" | "+des
				+" | "+dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME, from);
		return string;
	}
}