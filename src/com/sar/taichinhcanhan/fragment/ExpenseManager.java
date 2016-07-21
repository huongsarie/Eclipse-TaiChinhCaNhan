package com.sar.taichinhcanhan.fragment;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.common.ExpenseItem;
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

public class ExpenseManager extends Fragment implements FormFragment{
	// đối tượng quản lý CHI TIÊU

	private String id;
	private Context context;
	private Button btnSave,btnCancel;
	private View view,view1,view3;
	private Spinner spinnerCatalogue, spinnerAcc;
	private DatabaseManager dataMan;
	private DateTimeManager dtMan;
	private EditText edtTimeEx, edtDateEx;
	private TextView txtTitle;
	private EditText edtProduct, edtMoney, edtDescript;
	private String catalogue, account;// danhmục ch�?n, chi từ
										// tài khoản ch�?n;
	private String productName, money, description, date;
	private String codeExpense;
	private ContentValues value;
	private MySpinner mySpinnerCatalogue, mySpinnerAccount,mySpinnerCatalogue1, mySpinnerAccount1;


	public ExpenseManager() {
		super();
	}

	public ExpenseManager(Context context) {
		super();
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		bundle=this.getArguments();
		switch (bundle.getInt(CommonVL.START_FRAGMENT)) {
		case CommonVL.START_EXPENSE:
			view1 = inflater.inflate(R.layout.chitieu_them_layout, container,
					false);
			init(view1);
			view=view1;
			break;

		case CommonVL.START_EDIT:
			view3 = inflater.inflate(R.layout.chitieu_them_layout, container, false);
			this.id=bundle.getString(CommonVL.ID_TO_EDIT, null);
			init(view3, id);
			view = view3;
			break;
		}
		return view;
	}


	private void init(View view1) {
		dataMan = new DatabaseManager(context);
		edtProduct = (EditText) view1.findViewById(R.id.txtSPChi);
		edtMoney = (EditText) view1.findViewById(R.id.txtTienChi);
		edtDescript = (EditText) view1.findViewById(R.id.txtMoTaChi);
		spinnerCatalogue = (Spinner) view1.findViewById(R.id.spinnerDMChi);
		spinnerAcc = (Spinner) view1.findViewById(R.id.spinnerChiTu);
		edtDateEx = (EditText) view1.findViewById(R.id.txtNgayChi);
		edtTimeEx = (EditText) view1.findViewById(R.id.txtGioChi);
		dtMan = new DateTimeManager(context, edtTimeEx, edtDateEx);
		mySpinnerCatalogue = new MySpinner(context, spinnerCatalogue,
				CommonVL.VL_NUM_CATALOGUE_FRAG);
		mySpinnerAccount = new MySpinner(context, spinnerAcc,
				CommonVL.VL_NUM_ACCOUNT_FRAG);
		edtDateEx.setText(dtMan.getDateNow());
		edtTimeEx.setText(dtMan.getTimeNow());
		edtTimeEx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dtMan.showTimePickerDialog();
			}
		});
		edtDateEx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dtMan.showDatePickerDialog();
			}
		});
		btnSave=(Button)view1.findViewById(R.id.btLuuChi);
		btnCancel=(Button)view1.findViewById(R.id.btHuyChi);
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

	private void init(View view1, final String id) {
		txtTitle=(TextView)view1.findViewById(R.id.txtTitleChi);
		txtTitle.setText("SỬA CHI TIÊU");
		dataMan = new DatabaseManager(context);
		edtProduct = (EditText) view1.findViewById(R.id.txtSPChi);
		edtMoney = (EditText) view1.findViewById(R.id.txtTienChi);
		edtDescript = (EditText) view1.findViewById(R.id.txtMoTaChi);
		spinnerCatalogue = (Spinner) view1.findViewById(R.id.spinnerDMChi);
		spinnerAcc = (Spinner) view1.findViewById(R.id.spinnerChiTu);
		edtDateEx = (EditText) view1.findViewById(R.id.txtNgayChi);
		edtTimeEx = (EditText) view1.findViewById(R.id.txtGioChi);
		dtMan = new DateTimeManager(context, edtTimeEx, edtDateEx);
		ExpenseItem item = dataMan.getExpenseItemById(id);
		mySpinnerCatalogue1 = new MySpinner(context, spinnerCatalogue,
				CommonVL.VL_NUM_CATALOGUE_FRAG,dataMan.getNameByID(CommonVL.CATALOGUE_TABLE_NAME,item.getCataloge()));
		mySpinnerAccount1 = new MySpinner(context, spinnerAcc,
				CommonVL.VL_NUM_ACCOUNT_FRAG,dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,item.getFrom()));
		edtDateEx.setText(dtMan.getDateNow());
		edtTimeEx.setText(dtMan.getTimeNow());
		edtTimeEx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan.showTimePickerDialogWithValue();
			}
		});
		edtDateEx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan.showDatePickerDialogWithValue();
			}
		});
		dtMan = new DateTimeManager(context, edtTimeEx,
				edtDateEx, item.getDatetime());
		edtProduct.setText(item.getProduct());
		edtMoney.setText(item.getMoney().toString());
		edtDescript.setText(item.getDes());
		edtDateEx.setText(dtMan.getDateSQLString());
		edtTimeEx.setText(dtMan.getTimeSQLString());
		edtTimeEx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan.showTimePickerDialogWithValue();
			}
		});
		edtDateEx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dtMan.showDatePickerDialogWithValue();
			}
		});
		btnSave=(Button)view1.findViewById(R.id.btLuuChi);
		btnSave.setText("Cập nhật");
		btnCancel=(Button)view1.findViewById(R.id.btHuyChi);
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
public void backToDetail(){
		DetailFragment detailMan= new DetailFragment(context);
		Bundle b= new Bundle();
		b.putInt(CommonVL.DETAIL_FRAG, CommonVL.VL_NUM_EXPENSE_FRAG);
		detailMan.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.fragment_content, detailMan);
	    fragmentTransaction.addToBackStack(null).commit();
	}

	@Override
	public void lastValueResult() {
		// gan cac gia tri cuoi cung sau khi nhap vao string
		date = dtMan.getDateTimeFormatSQL();
		Log.v("format to save into sqlite", date);
		productName = edtProduct.getText().toString();
		money = edtMoney.getText().toString();
		description = edtDescript.getText().toString();
		catalogue = mySpinnerCatalogue.getIdSelected();
		account = mySpinnerAccount.getIdSelected();
	}

	@Override
	public void saveDatabase() {
		lastValueResult();
		if (money.equals("")|| money.equals("0")) {
			Toast.makeText(context, "Yêu cầu nhập đủ", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		codeExpense = "C"
				+ (long) (dataMan
						.getNumberOfLastId(CommonVL.EXPENSE_TABLE_NAME) + 1);

		value = new ContentValues();
		value.put(ExpenseItem.KEY_DATETIME, date);
		value.put(ExpenseItem.KEY_CATALOGUE, catalogue);
		value.put(ExpenseItem.KEY_PRODUCT, productName);
		value.put(ExpenseItem.KEY_MONEY, Double.parseDouble(money));
		value.put(ExpenseItem.KEY_CODEID, codeExpense);
		value.put(ExpenseItem.KEY_DESCRIPTION, description);
		value.put(ExpenseItem.KEY_ACCOUNT, account);

		if(dataMan.insertData(CommonVL.EXPENSE_TABLE_NAME, value)){
		resetAllOffValue();
		}
	}

	@Override
	public void resetAllOffValue() {
		init(view1);
		edtDescript.setText("");
		edtMoney.setText("");
		edtProduct.setText("");
	}

	public void updateDatabase(String id) {
		String date = dtMan.getDateTimeFormatSQLFromSQL();
		String productName = edtProduct.getText().toString();
		String money = edtMoney.getText().toString();
		String description = edtDescript.getText().toString();
		String catalogue = mySpinnerCatalogue1.getIdSelected();
		String account = mySpinnerAccount1.getIdSelected();
		if (money.equals("")||money.equals("0")) {
			Toast.makeText(context, "Yêu cầu nhập đủ", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		ContentValues value = new ContentValues();
		value.put(ExpenseItem.KEY_DATETIME, date);
		value.put(ExpenseItem.KEY_CATALOGUE, catalogue);
		value.put(ExpenseItem.KEY_PRODUCT, productName);
		value.put(ExpenseItem.KEY_MONEY, Double.parseDouble(money));
		value.put(ExpenseItem.KEY_DESCRIPTION, description);
		value.put(ExpenseItem.KEY_ACCOUNT, account);

		if(dataMan.updateData(CommonVL.EXPENSE_TABLE_NAME, value,ExpenseItem.KEY_CODEID+"=?",new String[]{id})){
			backToDetail();
		};
	}



}
