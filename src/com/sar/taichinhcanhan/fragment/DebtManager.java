package com.sar.taichinhcanhan.fragment;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.common.DebtItem;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DebtManager extends Fragment implements OnClickListener,FormFragment {

	private String id;
	private Context context;
	private Button btnSave,btnCancel;
	private View view,view1,view3;
	private Spinner spinner;
	private DatabaseManager dataMan;
	private DateTimeManager dtMan1,dtMan2,dtMan3,dtMan4;
	private EditText edtTimeDebt1,edtDateDebt1,edtTimeDebt2,edtDateDebt2;
	private EditText edtMoney,edtDescription,edtName,edtPurpose;
	private ContentValues value;
	private String code,account,dateTimeBegin,dateTimeEnd,purpose,name,description;
	private String money;
	private int state;
	private MySpinner  mySpinnerAccount, mySpinnerAccount1;
	public DebtManager(Context context) {
		super();
		this.context = context;
	}

	public DebtManager() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		bundle=this.getArguments();
		switch (bundle.getInt(CommonVL.START_FRAGMENT)) {
		case CommonVL.START_DEBT:
			view1 = inflater.inflate(R.layout.vayno_them_layout, container,
					false);
			init(view1);
			view=view1;
			break;

		case CommonVL.START_EDIT:
			view3 = inflater.inflate(R.layout.vayno_them_layout, container, false);
			this.id=bundle.getString(CommonVL.ID_TO_EDIT, null);
			init(view3, id);
			view = view3;
			break;
		}
		return view;
	}
	
	private void init(View view){
		dataMan= new DatabaseManager(context);		
		spinner=(Spinner)view.findViewById(R.id.spinnerTKVayNo);
		mySpinnerAccount = new MySpinner(context, spinner,
				CommonVL.VL_NUM_ACCOUNT_FRAG);
		edtDateDebt1=(EditText)view.findViewById(R.id.txtNgayVayNo);
		edtTimeDebt1=(EditText)view.findViewById(R.id.txtGioVayNo);
		dtMan1= new DateTimeManager(context,edtTimeDebt1,edtDateDebt1);
		edtDateDebt1.setText(dtMan1.getDateNow());
		edtTimeDebt1.setText(dtMan1.getTimeNow());
		edtTimeDebt1.setOnClickListener(this);
		edtDateDebt1.setOnClickListener(this);
		
		edtDateDebt2=(EditText)view.findViewById(R.id.txtNgayHenVayNo);
		edtTimeDebt2=(EditText)view.findViewById(R.id.txtGioHenVayNo);
		dtMan2= new DateTimeManager(context,edtTimeDebt2,edtDateDebt2);
		edtDateDebt2.setText(dtMan2.getDateNow());
		edtTimeDebt2.setText(dtMan2.getTimeNow());
		edtTimeDebt2.setOnClickListener(this);
		edtDateDebt2.setOnClickListener(this);
		
		edtMoney= (EditText)view.findViewById(R.id.txtTienVayNo);
		edtDescription= (EditText)view.findViewById(R.id.txtMoTaVayNo);
		edtPurpose=(EditText)view.findViewById(R.id.txtMucDichVayNo);
		edtName=(EditText)view.findViewById(R.id.txtNguoiChoVay);
		btnSave=(Button)view1.findViewById(R.id.btLuuVayNo);
		btnCancel=(Button)view1.findViewById(R.id.btHuyVayNo);
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
				i.setClass(context, MyFragment.class);
				i.setAction(CommonVL.END_ACTION);
				startActivity(i);
				getActivity().finish();
			}
		});
		
	}
	
	private void init(View view1,final String id){
		dataMan= new DatabaseManager(context);		
		DebtItem item = dataMan.getDebtItemById(id);
		spinner=(Spinner)view1.findViewById(R.id.spinnerTKVayNo);
		mySpinnerAccount1 = new MySpinner(context, spinner,
				CommonVL.VL_NUM_ACCOUNT_FRAG,dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,dataMan.getNameByID(CommonVL.CATALOGUE_TABLE_NAME,item.getTo())));
		edtDateDebt1=(EditText)view1.findViewById(R.id.txtNgayVayNo);
		edtTimeDebt1=(EditText)view1.findViewById(R.id.txtGioVayNo);
		dtMan3= new DateTimeManager(getActivity(),edtTimeDebt1,edtDateDebt1,item.getTimeBegin());
		edtDateDebt1.setText(dtMan3.getDateSQLString());
		edtTimeDebt1.setText(dtMan3.getTimeSQLString());
		edtTimeDebt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan3.showTimePickerDialogWithValue();
			}
		});
		edtDateDebt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan3.showDatePickerDialogWithValue();
			}
		});
		edtDateDebt2=(EditText)view1.findViewById(R.id.txtNgayHenVayNo);
		edtTimeDebt2=(EditText)view1.findViewById(R.id.txtGioHenVayNo);
		dtMan4= new DateTimeManager(getActivity(),edtTimeDebt2,edtDateDebt2,item.getTimeEnd());
		edtDateDebt2.setText(dtMan4.getDateNow());
		edtTimeDebt2.setText(dtMan4.getTimeNow());
		edtTimeDebt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan4.showTimePickerDialogWithValue();
			}
		});
		edtDateDebt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan4.showDatePickerDialogWithValue();
			}
		});
		
		edtMoney= (EditText)view1.findViewById(R.id.txtTienVayNo);
		edtMoney.setText(item.getMoney().toString());
		edtDescription= (EditText)view1.findViewById(R.id.txtMoTaVayNo);
		edtDescription.setText(item.getDes());
		edtPurpose=(EditText)view1.findViewById(R.id.txtMucDichVayNo);
		edtPurpose.setText(item.getPurpose());
		edtName=(EditText)view1.findViewById(R.id.txtNguoiChoVay);
		edtName.setText(item.getName());
		btnSave=(Button)view1.findViewById(R.id.btLuuVayNo);
		btnSave.setText("Cập nhật vay nợ");
		btnCancel=(Button)view1.findViewById(R.id.btHuyVayNo);
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
		dateTimeBegin=dtMan1.getDateTimeFormatSQL();
		dateTimeEnd=dtMan2.getDateTimeFormatSQL();
		money=edtMoney.getText().toString();
		name=edtName.getText().toString();
		purpose=edtPurpose.getText().toString();
		description=edtDescription.getText().toString();
		account=mySpinnerAccount.getIdSelected();
	}

	@Override
	public void saveDatabase() {
		// TODO Auto-generated method stub
		lastValueResult();
		if(name.equals("") || money.equals("")||money.equals("0")){
			Toast.makeText(getActivity(), "Yêu cầu nhập đủ", Toast.LENGTH_SHORT).show();
			return;
		}
		
		code="VN"+(long)(dataMan.getNumberOfLastId(CommonVL.DEBT_TABLE_NAME)+1);
		state=0;
		value= new ContentValues();
		value.put(DebtItem.KEY_DATETIME_BEGIN, dateTimeBegin);
		value.put(DebtItem.KEY_DATETIME_END, dateTimeEnd);
		value.put(DebtItem.KEY_CODE, code);
		value.put(DebtItem.KEY_NAME, name);
		value.put(DebtItem.KEY_TO_ACCOUNT, account);
		value.put(DebtItem.KEY_MONEY, Double.parseDouble(money));
		value.put(DebtItem.KEY_PURPOSE, purpose);
		value.put(DebtItem.KEY_DESCRIPTION, description);
		value.put(DebtItem.KEY_STATE, state);
		value.put(DebtItem.KEY_PAYED, "null");
		
		
		if(dataMan.insertData(CommonVL.DEBT_TABLE_NAME, value)){
			resetAllOffValue();	
		}
		
		
	}

	@Override
	public void resetAllOffValue() {
		// TODO Auto-generated method stub
		init(view1);
		edtMoney.setText("");
		edtName.setText("");
		edtPurpose.setText("");
		edtDescription.setText("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtNgayVayNo:
			dtMan1.showDatePickerDialog();
			break;
			
		case R.id.txtGioVayNo:
			dtMan1.showTimePickerDialog();
			break;
			
		case R.id.txtNgayHenVayNo:
			dtMan2.showDatePickerDialog();
			break;
			
		case R.id.txtGioHenVayNo:
			dtMan2.showTimePickerDialog();
			break;

		default:
			break;
		}
		
	}
//đối tươgnj quản lý CHO VAY

	@Override
	public void updateDatabase(String id) {
		// TODO Auto-generated method stub
		dateTimeBegin=dtMan3.getDateTimeFormatSQLFromSQL();
		dateTimeEnd=dtMan4.getDateTimeFormatSQLFromSQL();
		money=edtMoney.getText().toString();
		name=edtName.getText().toString();
		purpose=edtPurpose.getText().toString();
		description=edtDescription.getText().toString();
		account=mySpinnerAccount1.getIdSelected();
		if(name.equals("") || money.equals("")||money.equals("0")){
			Toast.makeText(getActivity(), "Yêu cầu nhập đủ", Toast.LENGTH_SHORT).show();
			return;
		}
		
		value= new ContentValues();
		value.put(DebtItem.KEY_DATETIME_BEGIN, dateTimeBegin);
		value.put(DebtItem.KEY_DATETIME_END, dateTimeEnd);
		value.put(DebtItem.KEY_NAME, name);
		value.put(DebtItem.KEY_TO_ACCOUNT, account);
		value.put(DebtItem.KEY_MONEY, Double.parseDouble(money));
		value.put(DebtItem.KEY_PURPOSE, purpose);
		value.put(DebtItem.KEY_DESCRIPTION, description);
		if(dataMan.updateData(CommonVL.DEBT_TABLE_NAME, value,DebtItem.KEY_CODE+"=?",new String[]{id})){
			backToDetail();
		};
	}

	@Override
	public void backToDetail() {
		// TODO Auto-generated method stub
		DetailFragment detailMan= new DetailFragment(context);
		Bundle b= new Bundle();
		b.putInt(CommonVL.DETAIL_FRAG, CommonVL.VL_NUM_DEPT_FRAG);
		detailMan.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.fragment_content, detailMan);
	    fragmentTransaction.addToBackStack(null).commit();
	}

}


