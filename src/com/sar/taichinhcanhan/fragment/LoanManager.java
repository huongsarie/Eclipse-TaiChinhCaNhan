package com.sar.taichinhcanhan.fragment;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.common.LoanItem;
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

public class LoanManager extends Fragment implements OnClickListener,FormFragment {

	private String id;
	private Context context;
	private Button btnSave,btnCancel;
	private View view,view1,view3;
	private Spinner spinner;
	private DatabaseManager dataMan;
	private DateTimeManager dtMan1,dtMan2,dtMan3,dtMan4;
	private EditText edtTimeLoan1,edtDateLoan1,edtTimeLoan2,edtDateLoan2;
	private EditText edtMoney,edtDescription,edtName,edtPurpose;
	private ContentValues value;
	private String code,account,dateTimeBegin,dateTimeEnd,purpose,name,description;
	private String money;
	private int state;
	private MySpinner  mySpinnerAccount, mySpinnerAccount1;
	
	public LoanManager(Context context) {
		super();
		this.context = context;
	}

	public LoanManager() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		bundle=this.getArguments();
		switch (bundle.getInt(CommonVL.START_FRAGMENT)) {
		case CommonVL.START_LOAN:
			view1 = inflater.inflate(R.layout.chovay_them_layout, container,
					false);
			init(view1);
			view=view1;
			break;

		case CommonVL.START_EDIT:
			view3 = inflater.inflate(R.layout.chovay_them_layout, container, false);
			this.id=bundle.getString(CommonVL.ID_TO_EDIT, null);
			init(view3, id);
			view = view3;
			break;
		}
		return view;
	}
	
	private void init(View view){
		dataMan= new DatabaseManager(context);		
		spinner=(Spinner)view.findViewById(R.id.spinnerTKChoVay);
		mySpinnerAccount = new MySpinner(context, spinner,
				CommonVL.VL_NUM_ACCOUNT_FRAG);
		edtDateLoan1=(EditText)view.findViewById(R.id.txtNgayChoVay);
		edtTimeLoan1=(EditText)view.findViewById(R.id.txtGioChoVay);
		dtMan1= new DateTimeManager(getActivity(),edtTimeLoan1,edtDateLoan1);
		edtDateLoan1.setText(dtMan1.getDateNow());
		edtTimeLoan1.setText(dtMan1.getTimeNow());
		edtTimeLoan1.setOnClickListener(this);
		edtDateLoan1.setOnClickListener(this);
		
		edtDateLoan2=(EditText)view.findViewById(R.id.txtNgayHenChoVay);
		edtTimeLoan2=(EditText)view.findViewById(R.id.txtGioHenChoVay);
		dtMan2= new DateTimeManager(getActivity(),edtTimeLoan2,edtDateLoan2);
		edtDateLoan2.setText(dtMan2.getDateNow());
		edtTimeLoan2.setText(dtMan2.getTimeNow());
		edtTimeLoan2.setOnClickListener(this);
		edtDateLoan2.setOnClickListener(this);
		
		edtMoney= (EditText)view.findViewById(R.id.txtTienChoVay);
		edtDescription= (EditText)view.findViewById(R.id.txtMoTaChoVay);
		edtPurpose=(EditText)view.findViewById(R.id.txtMucDichChoVay);
		edtName=(EditText)view.findViewById(R.id.txtNguoiVay);
		btnSave=(Button)view1.findViewById(R.id.btLuuChoVay);
		btnCancel=(Button)view1.findViewById(R.id.btHuyChoVay);
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
		LoanItem item = dataMan.getLoanItemById(id);
		spinner=(Spinner)view1.findViewById(R.id.spinnerTKChoVay);
		mySpinnerAccount1 = new MySpinner(context, spinner,
				CommonVL.VL_NUM_ACCOUNT_FRAG,dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,dataMan.getNameByID(CommonVL.CATALOGUE_TABLE_NAME,item.getFrom())));
		edtDateLoan1=(EditText)view1.findViewById(R.id.txtNgayChoVay);
		edtTimeLoan1=(EditText)view1.findViewById(R.id.txtGioChoVay);
		dtMan3= new DateTimeManager(getActivity(),edtTimeLoan1,edtDateLoan1,item.getTimeBegin());
		edtDateLoan1.setText(dtMan3.getDateSQLString());
		edtTimeLoan1.setText(dtMan3.getTimeSQLString());
		edtTimeLoan1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan3.showTimePickerDialogWithValue();
			}
		});
		edtDateLoan1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan3.showDatePickerDialogWithValue();
			}
		});
		
		edtDateLoan2=(EditText)view1.findViewById(R.id.txtNgayHenChoVay);
		edtTimeLoan2=(EditText)view1.findViewById(R.id.txtGioHenChoVay);
		dtMan4= new DateTimeManager(getActivity(),edtTimeLoan2,edtDateLoan2,item.getTimeEnd());
		edtDateLoan2.setText(dtMan4.getDateNow());
		edtTimeLoan2.setText(dtMan4.getTimeNow());
		edtTimeLoan2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan4.showTimePickerDialogWithValue();
			}
		});
		edtDateLoan2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan4.showDatePickerDialogWithValue();
			}
		});
		
		edtMoney= (EditText)view1.findViewById(R.id.txtTienChoVay);
		edtMoney.setText(item.getMoney().toString());
		edtDescription= (EditText)view1.findViewById(R.id.txtMoTaChoVay);
		edtDescription.setText(item.getDes());
		edtPurpose=(EditText)view1.findViewById(R.id.txtMucDichChoVay);
		edtPurpose.setText(item.getPurpose());
		edtName=(EditText)view1.findViewById(R.id.txtNguoiVay);
		edtName.setText(item.getName());
		btnSave=(Button)view1.findViewById(R.id.btLuuChoVay);
		btnSave.setText("Cập nhật cho vay");
		btnCancel=(Button)view1.findViewById(R.id.btHuyChoVay);
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
		
		code="CV"+(long)(dataMan.getNumberOfLastId(CommonVL.LOAN_TABLE_NAME)+1);
		state=0;
		value= new ContentValues();
		value.put(LoanItem.KEY_DATETIME_BEGIN, dateTimeBegin);
		value.put(LoanItem.KEY_DATETIME_END, dateTimeEnd);
		value.put(LoanItem.KEY_CODE, code);
		value.put(LoanItem.KEY_NAME, name);
		value.put(LoanItem.KEY_FROM_ACCOUNT, account);
		value.put(LoanItem.KEY_MONEY, Double.parseDouble(money));
		value.put(LoanItem.KEY_PURPOSE, purpose);
		value.put(LoanItem.KEY_DESCRIPTION, description);
		value.put(LoanItem.KEY_STATE, state);
		
		if(dataMan.insertData(CommonVL.LOAN_TABLE_NAME, value)){
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
		case R.id.txtNgayChoVay:
			dtMan1.showDatePickerDialog();
			break;
			
		case R.id.txtGioChoVay:
			dtMan1.showTimePickerDialog();
			break;
			
		case R.id.txtNgayHenChoVay:
			dtMan2.showDatePickerDialog();
			break;
			
		case R.id.txtGioHenChoVay:
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
		
		if(name.equals("") || money.equals("") || money.equals("0")){
			Toast.makeText(getActivity(), "Yêu cầu nhập đủ", Toast.LENGTH_SHORT).show();
			return;
		}
		
		code="CV"+(long)(dataMan.getNumberOfLastId(CommonVL.LOAN_TABLE_NAME)+1);
		state=0;
		value= new ContentValues();
		value.put(LoanItem.KEY_DATETIME_BEGIN, dateTimeBegin);
		value.put(LoanItem.KEY_DATETIME_END, dateTimeEnd);
		value.put(LoanItem.KEY_CODE, code);
		value.put(LoanItem.KEY_NAME, name);
		value.put(LoanItem.KEY_FROM_ACCOUNT, account);
		value.put(LoanItem.KEY_MONEY, Double.parseDouble(money));
		value.put(LoanItem.KEY_PURPOSE, purpose);
		value.put(LoanItem.KEY_DESCRIPTION, description);
		if(dataMan.updateData(CommonVL.LOAN_TABLE_NAME, value,LoanItem.KEY_CODE+"=?",new String[]{id})){
			backToDetail();
		}
		
	}

	@Override
	public void backToDetail() {
		// TODO Auto-generated method stub
		DetailFragment detailMan= new DetailFragment(context);
		Bundle b= new Bundle();
		b.putInt(CommonVL.DETAIL_FRAG, CommonVL.VL_NUM_LOAN_FRAG);
		detailMan.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.fragment_content, detailMan);
	    fragmentTransaction.addToBackStack(null).commit();
	}

}

