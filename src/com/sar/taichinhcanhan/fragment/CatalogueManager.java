package com.sar.taichinhcanhan.fragment;

import java.util.ArrayList;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.AccountItem;
import com.sar.taichinhcanhan.common.CatalogueItem;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.component.DatabaseManager;
import com.sar.taichinhcanhan.main.MainActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CatalogueManager extends Fragment implements FormFragment{

	private View view,view1,view3;
	private Button btnSave,btnCancel;
	private String id;
	private Context context;
	private EditText etName,etDescription;
	private TextView txtTitle;
	private String code,name,des;
	private DatabaseManager dataMan;
	private ContentValues value;
	private ArrayList<CatalogueItem> arrCataloge;
	
	public CatalogueManager(Context context) {
		super();
		this.context = context;
	}

	public CatalogueManager() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		bundle=this.getArguments();
		switch (bundle.getInt(CommonVL.START_FRAGMENT)) {
		case CommonVL.START_CATALOGUE:
		view1=inflater.inflate(R.layout.danhmuc_them_layout, container,false);
		init(view1);
		view=view1;
		break;
		case CommonVL.START_EDIT:
			view3 = inflater.inflate(R.layout.danhmuc_them_layout, container, false);
			this.id=bundle.getString(CommonVL.ID_TO_EDIT, null);
			init(view3, id);
			view = view3;
			break;
		}
		return view;
	}
	
	void init(View view){
		etName=(EditText)view.findViewById(R.id.txtTenDM);
		etDescription=(EditText)view.findViewById(R.id.txtMoTaDM);
		dataMan = new DatabaseManager(getActivity());
		arrCataloge=dataMan.getAllOfCatalogueItem();
		btnSave=(Button)view.findViewById(R.id.btLuuDM);
		btnCancel=(Button)view.findViewById(R.id.btHuyDM);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveDatabase();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i= new Intent();
				i.setClass(context, MainActivity.class);
				startActivity(i);
				getActivity().finish();
			}
		});
	}
	

	private void init(View view1, final String id) {
		dataMan = new DatabaseManager(context);
		arrCataloge=dataMan.getAllOfCatalogueItem();
		CatalogueItem item= dataMan.getCatalogueItemById(id);
		txtTitle=(TextView)view1.findViewById(R.id.txtTitleDMThem);
		txtTitle.setText("SỬA DANH MỤC");
		etName=(EditText)view1.findViewById(R.id.txtTenDM);
		etDescription=(EditText)view1.findViewById(R.id.txtMoTaDM);
		Log.i("etDescription", etDescription.getId()+"");
		etName.setText(item.getName());
		etDescription.setText(item.getDescription());
		btnSave=(Button)view1.findViewById(R.id.btLuuDM);
		btnSave.setText("Cập nhật");
		btnCancel=(Button)view1.findViewById(R.id.btHuyDM);
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				updateDatabase(id);
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				backToDetail();
			}
		});
	}
	@Override
	public void lastValueResult() {
		// TODO Auto-generated method stub
		name=etName.getText().toString();
		des=etDescription.getText().toString();
	}

	@Override
	public void saveDatabase() {
		// TODO Auto-generated method stub
		lastValueResult();
		if( name.equals("")){
			Toast.makeText(getActivity(), "Yêu cầu nhập đủ", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
		for(CatalogueItem catalogue : arrCataloge){
			if(catalogue.getName().equals(name)){
				Toast.makeText(getActivity(), "Trùng tên\nYêu cầu nhập lại tên tài khoản!", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		code="DM"+(long)(dataMan.getNumberOfLastId(CommonVL.CATALOGUE_TABLE_NAME)+1);
		
		value=new ContentValues();
		value.put(CatalogueItem.KEY_CODEID, code);
		value.put(CatalogueItem.KEY_DESCRIPTION, des);
		value.put(CatalogueItem.KEY_NAME, name);
		
		if(dataMan.insertData(CommonVL.CATALOGUE_TABLE_NAME, value)){
		resetAllOffValue();
		}
		}
	}

	@Override
	public void resetAllOffValue() {
		// TODO Auto-generated method stub

		etName.setText("");
		etDescription.setText("");
	}


	@Override
	public void updateDatabase(String id) {
		// TODO Auto-generated method stub
		lastValueResult();
		if( name.equals("")){
			Toast.makeText(context, "Yêu cầu nhập đủ", Toast.LENGTH_SHORT).show();
			return;
		}else{
			int i=0;
		for(CatalogueItem cata : arrCataloge){
			if(cata.getName().equals(name))
				i++;
			if(i>1){
				Toast.makeText(context, "Trùng tên\nYêu cầu nhập lại tên danh mục!", Toast.LENGTH_SHORT).show();
				return;}
		}
		ContentValues value = new ContentValues();
		value.put(CatalogueItem.KEY_NAME, name);
		value.put(CatalogueItem.KEY_DESCRIPTION, des);

		if(dataMan.updateData(CommonVL.CATALOGUE_TABLE_NAME, value,CatalogueItem.KEY_CODEID+"=?",new String[]{id})){
			backToDetail();
		}}
	}

	@Override
	public void backToDetail() {
		// TODO Auto-generated method stub
		DetailFragment detailMan= new DetailFragment(context);
		Bundle b= new Bundle();
		b.putInt(CommonVL.DETAIL_FRAG, CommonVL.VL_NUM_CATALOGUE_FRAG);
		detailMan.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.fragment_content, detailMan);
	    fragmentTransaction.addToBackStack(null).commit();
	}

}


