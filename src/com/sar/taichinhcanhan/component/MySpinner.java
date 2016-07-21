package com.sar.taichinhcanhan.component;

import java.util.ArrayList;

import com.sar.taichinhcanhan.common.*;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MySpinner{

	private Context context;
	private String itemSelected,idSelected;
	public String getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(String idSelected) {
		this.idSelected = idSelected;
	}

	private DatabaseManager dataMan;
	
	public MySpinner(Context context, Spinner spinner,int numberOfObject){
		this.context=context;
		dataMan= new DatabaseManager();
		initSpinner(spinner,numberOfObject);
	}
	
	public MySpinner(Context context,Spinner spinner,ArrayList<String> arr){
		this.context=context;
		initSpinner(spinner,arr);
	}

	private void initSpinner(Spinner spinner, final ArrayList<String> arr) {
		// TODO Auto-generated method stub
		ArrayAdapter<String> adapterCat = new ArrayAdapter<>(context,
				android.R.layout.simple_spinner_item, arr);
		adapterCat
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Log.i("spinner", spinner.getId()+"");
		spinner.setAdapter(adapterCat);
		spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						// TODO Auto-generated method stub
						setItemSelected(arr.get(pos).toString());

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						setItemSelected(arr.get(0).toString());
					}

				});
	}

	public MySpinner(Context context, Spinner spinner, int numberOfObject, String preValue) {
		super();
		this.context = context;
		dataMan= new DatabaseManager();
		initSpinnerPreValue(numberOfObject,preValue,spinner);
	}

	public String getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(String itemSelected) {
		this.itemSelected = itemSelected;
	}

	//với spinner thông thư�?ng với danh sách item theo bảng SQL
	private void initSpinner(Spinner spinner,int number){
		switch (number) {
		case CommonVL.VL_NUM_CATALOGUE_FRAG:
			spinnerCatalogue(spinner);
			break;
		case CommonVL.VL_NUM_ACCOUNT_FRAG:
			spinnerAccount(spinner);
			break;
		default:
			break;
		}
	}
	
	private void spinnerCatalogue(Spinner spinner){
		final ArrayList<CatalogueItem> arrItem=dataMan.getAllOfCatalogueItem();
		ArrayList<String> arrName=new ArrayList<String>();
		for (CatalogueItem cataloge : arrItem) {
			arrName.add(cataloge.getName());
		}
		ArrayAdapter<String> adapterCat = new ArrayAdapter<>(context,
				android.R.layout.simple_spinner_item, arrName);
		adapterCat
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Log.i("spinner", spinner.getId()+"");
		spinner.setAdapter(adapterCat);
		spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						// TODO Auto-generated method stub
						setIdSelected( arrItem.get(pos).getId());
						setItemSelected(arrItem.get(pos).getName());

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						setIdSelected(arrItem.get(0).getId());
						setItemSelected(arrItem.get(0).getName());
						Log.v("catalogue name", itemSelected);
					}

				});
	}
	
	private void spinnerAccount(Spinner spinner){
		final ArrayList<AccountItem> arrItem=dataMan.getAllOfAccountItem();
		ArrayList<String> arrName=new ArrayList<String>();
		for (AccountItem account : arrItem) {
			arrName.add(account.getName());
		}
		ArrayAdapter<String> adapterCat = new ArrayAdapter<>(context,
				android.R.layout.simple_spinner_item, arrName);
		adapterCat
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapterCat);
		spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						// TODO Auto-generated method stub
						setIdSelected( arrItem.get(pos).getId());
						setItemSelected(arrItem.get(pos).getName());

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						setIdSelected(arrItem.get(0).getId());
						setItemSelected(arrItem.get(0).getName());
					}

				});
	}
	
	//spinner với giá trị cho trước hiển thị, dùng trong Edit
	
	private void initSpinnerPreValue(int number,String preValue,Spinner spinner){
		switch (number) {
		case CommonVL.VL_NUM_CATALOGUE_FRAG:
			spinnerCataloguePreValue(preValue,spinner);
			break;
		case CommonVL.VL_NUM_ACCOUNT_FRAG:
			spinnerAccountPreValue(preValue,spinner);
			break;
		default:
			break;
		}
	}
	
	private void spinnerCataloguePreValue(String preValue,Spinner spinner){
		final ArrayList<CatalogueItem> arrItem1= dataMan.getAllOfCatalogueItem();
		ArrayList<String> arrName=new ArrayList<String>();
		for (CatalogueItem cataloge : arrItem1) {
			arrName.add(cataloge.getName());
		}
		ArrayAdapter<String> adapterCat1 = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, arrName);
		adapterCat1
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			Log.i("spinner", spinner.getId()+"");
		spinner.setAdapter(adapterCat1);
		final int positionOfPreValue=adapterCat1.getPosition(preValue);
		spinner.setSelection(positionOfPreValue);
		spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						// TODO Auto-generated method stub
						setIdSelected( arrItem1.get(pos).getId());
						setItemSelected(arrItem1.get(pos).getName());

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						setIdSelected(arrItem1.get(positionOfPreValue).getId());
						setItemSelected(arrItem1.get(positionOfPreValue).getName());
						Log.v("catalogue name", itemSelected);
					}
				});
	}
	
	private void spinnerAccountPreValue(String preValue,Spinner spinner){
		final ArrayList<AccountItem> arrItem2=dataMan.getAllOfAccountItem();
		ArrayList<String> arrName2=new ArrayList<String>();
		for (AccountItem account : arrItem2) {
			arrName2.add(account.getName());
		}
		ArrayAdapter<String> adapterAcc = new ArrayAdapter<>(context,
				android.R.layout.simple_spinner_item, arrName2);
		adapterAcc
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapterAcc);
		final int positionOfPreValue=adapterAcc.getPosition(preValue);
		spinner.setSelection(positionOfPreValue);
		
		spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						// TODO Auto-generated method stub
						setIdSelected( arrItem2.get(pos).getId());
						setItemSelected(arrItem2.get(pos).getName());
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						setIdSelected(arrItem2.get(positionOfPreValue).getId());
						setItemSelected(arrItem2.get(positionOfPreValue).getName());
						Log.v("catalogue name", itemSelected);
					}

				});
	}
	
}
