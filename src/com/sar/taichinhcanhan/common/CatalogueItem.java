package com.sar.taichinhcanhan.common;

public class CatalogueItem{
	public static final String KEY_CODEID = "Ma";
	public static final String KEY_NAME = "Ten";
	public static final String KEY_DESCRIPTION = "MoTa";
	private String id, name, description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CatalogueItem(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public CatalogueItem(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public CatalogueItem() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name+" | "+description;
	}
}
