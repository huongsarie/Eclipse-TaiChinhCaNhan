package com.sar.taichinhcanhan.fragment;


import java.text.NumberFormat;
import java.util.Locale;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CollectingMoneyItem;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.component.DatabaseManager;
import com.sar.taichinhcanhan.component.DateTimeManager;
import com.sar.taichinhcanhan.component.MySpinner;
import com.sar.taichinhcanhan.main.MainActivity;
import com.sar.taichinhcanhan.main.MyFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CollectingMoneyManager extends Fragment implements
		OnClickListener, FormFragment {
	// Ä‘á»‘i tÆ°á»£ng quáº£n lÃ½ THU TIá»€N
	private String id;
	private Context context;
	private View view,view1,view3;
	private Spinner spinner;
	private DatabaseManager dataMan;
	private DateTimeManager dtMan;
	private String account, code, from, des, date;
	private String money;
	private EditText edtTimeCol, edtDateCol;
	private EditText edtFrom, edtMoney, edtDescription;
	private ContentValues value;
	private MySpinner mySpinner,mySpinner1;
	private Button btnSave,btnCancel;
	private TextView txtTitle;
	private NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(Locale.US);
	public CollectingMoneyManager(Context context) {
		super();
		this.context = context;
	}

	public CollectingMoneyManager() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		bundle=this.getArguments();
		switch (bundle.getInt(CommonVL.START_FRAGMENT)) {
		case CommonVL.START_COLLECT:
			view1 = inflater.inflate(R.layout.thunhap_them_layout, container,
					false);
			init(view1);
			view=view1;
			break;

		case CommonVL.START_EDIT:
			view3 = inflater.inflate(R.layout.thunhap_them_layout, container, false);
			this.id=bundle.getString(CommonVL.ID_TO_EDIT, null);
			init(view3, id);
			view = view3;
			break;
		}
		return view;
	}

	private void init(View view) {
		dataMan = new DatabaseManager(context);
		spinner = (Spinner) view.findViewById(R.id.spinnerTKThu);
		mySpinner = new MySpinner(context, spinner, CommonVL.VL_NUM_ACCOUNT_FRAG);

		edtDateCol = (EditText) view.findViewById(R.id.txtNgayThu);
		edtTimeCol = (EditText) view.findViewById(R.id.txtGioThu);
		dtMan = new DateTimeManager(context, edtTimeCol, edtDateCol);
		edtDateCol.setText(dtMan.getDateNow());
		edtTimeCol.setText(dtMan.getTimeNow());

		edtTimeCol.setOnClickListener(this);
		edtDateCol.setOnClickListener(this);

		edtFrom = (EditText) view.findViewById(R.id.txtNguonThu);
		edtMoney = (EditText) view.findViewById(R.id.txtTienThu);
		edtDescription = (EditText) view.findViewById(R.id.txtMoTaThu);
		btnSave=(Button)view1.findViewById(R.id.btLuuThu);
		btnCancel=(Button)view1.findViewById(R.id.btHuyThu);
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
//				getActivity().getFragmentManager().beginTransaction().remove(CollectingMoneyManager.this).commit();
				Intent i= new Intent();
				i.setClass(context, MyFragment.class);
				i.setAction(CommonVL.END_ACTION);
				startActivity(i);
				getActivity().finish();
			}
		});
	}
	private void init(View view, final String id) {
		dataMan = new DatabaseManager(context);
		txtTitle=(TextView)view.findViewById(R.id.txtTienThu);
		txtTitle.setText("SỬA THU NHẬP");
		CollectingMoneyItem item = dataMan.getCollectingItemById(id);
		spinner = (Spinner) view.findViewById(R.id.spinnerTKThu);
		mySpinner1 = new MySpinner(context, spinner, CommonVL.VL_NUM_ACCOUNT_FRAG,dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,item.getToAcc()));

		edtDateCol = (EditText) view.findViewById(R.id.txtNgayThu);
		edtTimeCol = (EditText) view.findViewById(R.id.txtGioThu);
		dtMan = new DateTimeManager(context, edtTimeCol, edtDateCol,item.getDatetime());
		edtDateCol.setText(dtMan.getDateSQLString());
		edtTimeCol.setText(dtMan.getTimeSQLString());

		edtTimeCol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan.showTimePickerDialogWithValue();
			}
		});
		edtDateCol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan.showDatePickerDialogWithValue();
			}
		});
		edtFrom = (EditText) view.findViewById(R.id.txtNguonThu);
		edtFrom.setText(item.getFrom());
		edtMoney = (EditText) view.findViewById(R.id.txtTienThu);
		edtMoney.setText(item.getMoney().toString());
		edtDescription = (EditText) view.findViewById(R.id.txtMoTaThu);
		edtDescription.setText(item.getDescription());
		btnSave=(Button)view.findViewById(R.id.btLuuThu);
		btnSave.setText("Cập nhật");
		btnCancel=(Button)view.findViewById(R.id.btHuyThu);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtNgayThu: // khi click vao edittext ngay thang
			dtMan.showDatePickerDialog();

			break;
		case R.id.txtGioThu: // click vafi edittext gio
			dtMan.showTimePickerDialog();
			Log.i("Testing TIME choosed",
					dtMan.getTimeChoosed() + "\n" + dtMan.getDateTimeChoosed());
			break;

		default:
			break;
		}

	}

	@Override
	public void lastValueResult() {
		date = dtMan.getDateTimeFormatSQL();
		des = edtDescription.getText().toString();
		from = edtFrom.getText().toString();
		money = edtMoney.getText().toString();
		account=mySpinner.getIdSelected();
	}

	@Override
	public void saveDatabase() {
		//kiểm tra các edittext quan tr�?ng chưa nhập
		lastValueResult();
		if (money.equals("")|| money.equals("0")) {
			Toast.makeText(context, "Yêu cầu nhập đủ", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		
		code = "T" + (long)(dataMan.getNumberOfLastId(CommonVL.COLLECTING_TABLE_NAME)+1);
		value = new ContentValues();
		value.put(CollectingMoneyItem.KEY_DATETIME, date);
		value.put(CollectingMoneyItem.KEY_MONEY, Double.parseDouble(money));
		value.put(CollectingMoneyItem.KEY_CODEID, code);
		value.put(CollectingMoneyItem.KEY_DESCRIPTION, des);
		value.put(CollectingMoneyItem.KEY_FROM, from);
		value.put(CollectingMoneyItem.KEY_ACCOUNT, account);

		if(dataMan.insertData(CommonVL.COLLECTING_TABLE_NAME, value)){
		resetAllOffValue();}
	}

	@Override
	public void resetAllOffValue() {
		init(view1);
		edtDescription.setText("");
		edtMoney.setText("");
		edtFrom.setText("");

	}

	@Override
	public void updateDatabase(String id) {
		// TODO Auto-generated method stub
		date = dtMan.getDateTimeFormatSQLFromSQL();
		des = edtDescription.getText().toString();
		from = edtFrom.getText().toString();
		money = edtMoney.getText().toString();
		account=mySpinner1.getIdSelected();
		if (money.equals("")|| money.equals("0")) {
			Toast.makeText(context, "Yêu cầu nhập đủ", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		code = "T" + (long)(dataMan.getNumberOfLastId(CommonVL.COLLECTING_TABLE_NAME)+1);
		value = new ContentValues();
		value.put(CollectingMoneyItem.KEY_DATETIME, date);
		value.put(CollectingMoneyItem.KEY_MONEY, Double.parseDouble(money));
		value.put(CollectingMoneyItem.KEY_DESCRIPTION, des);
		value.put(CollectingMoneyItem.KEY_FROM, from);
		value.put(CollectingMoneyItem.KEY_ACCOUNT, account);
		if(dataMan.updateData(CommonVL.COLLECTING_TABLE_NAME, value,CollectingMoneyItem.KEY_CODEID+"=?",new String[]{id}))
			backToDetail();
	}

	@Override
	public void backToDetail() {
		// TODO Auto-generated method stub
		DetailFragment detailMan= new DetailFragment(context);
		Bundle b= new Bundle();
		b.putInt(CommonVL.DETAIL_FRAG, CommonVL.VL_NUM_COLLECT_FRAG);
		detailMan.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.fragment_content, detailMan);
	    fragmentTransaction.addToBackStack(null).commit();
	}

}
