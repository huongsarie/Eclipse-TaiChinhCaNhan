package com.sar.taichinhcanhan.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.AccountItem;
import com.sar.taichinhcanhan.common.CatalogueItem;
import com.sar.taichinhcanhan.common.CollectingMoneyItem;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.common.DebtItem;
import com.sar.taichinhcanhan.common.ExpenseItem;
import com.sar.taichinhcanhan.common.LoanItem;
import com.sar.taichinhcanhan.component.DatabaseManager;
import com.sar.taichinhcanhan.component.DateTimeManager;
import com.sar.taichinhcanhan.main.MainActivity;
import com.sar.taichinhcanhan.main.MyFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DetailFragment extends Fragment {

	public static final String KEY_ID_TO_EDIT = "key_id_to_edit";
	private View view;
	private TableLayout tableLayout;
	private int detailOf;
	private Bundle bundle;
	private Button btnCancel;
	private Context context;
	private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

	private DatabaseManager dataMan;
	private String[] arrCataNameColumn = { "Tên", "Mô tả" },
			arrAccountNameColumn = { "Tên", "Mô tả" },
			arrExpenseNameColumn = { "Ngày", "Danh mục", "Vật mua", "Số tiền", "Từ TK",
					"Mô tả" }, 
					arrCollectNameColumn = { "Ngày",
					"Nguồn thu", "Vào TK", "Số tiền", "Mô tả" };

	public DetailFragment(Context context, int detailOf) {
		this.detailOf = detailOf;
		this.context = context;
	}

	public DetailFragment(Context context) {
		this.context = context;
	}

	public DetailFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		bundle = this.getArguments();
		detailOf = bundle.getInt(CommonVL.DETAIL_FRAG);
		view = inflater.inflate(R.layout.chitiet_layout, container, false);
		tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
		tableLayout.setStretchAllColumns(true);
		init(view);
		showDetail(detailOf);
		return view;
	}

	void init(View view) {
		dataMan = new DatabaseManager(context);
		btnCancel = (Button) view.findViewById(R.id.btThoat);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(context, MyFragment.class);
				i.setAction(CommonVL.END_ACTION);
				startActivity(i);
				getActivity().finish();
			}
		});
	}

	public void showDetail(int detailOf) {
		// TODO Auto-generated method stub
		switch (detailOf) {
		case CommonVL.VL_NUM_EXPENSE_FRAG:
			tableLayout.removeAllViews();
			showDetailAllOfExpense();
			break;
		case CommonVL.VL_NUM_CATALOGUE_FRAG:
			tableLayout.removeAllViews();
			showDetailAllOfCatalogue();
			break;
		case CommonVL.VL_NUM_ACCOUNT_FRAG:
			tableLayout.removeAllViews();
			showDetailAllOfAccount();
			break;
		case CommonVL.VL_NUM_COLLECT_FRAG:
			tableLayout.removeAllViews();
			showDetailAllOfCollection();
			break;
		case CommonVL.VL_NUM_LOAN_FRAG:
			tableLayout.removeAllViews();
			showDetailAllOfLoan();
			break;
		case CommonVL.VL_NUM_DEPT_FRAG:
			tableLayout.removeAllViews();
			showDetailAllOfDebt();
			break;
		}
	}

	void showDetailAllOfExpense() {
		ArrayList<ExpenseItem> arrItem = dataMan.getAllOfExpenseTable();
		String[] arrNameCol = arrExpenseNameColumn;
		if (arrItem == null) {
			return;
		}
		tableLayout.setHorizontalScrollBarEnabled(true);

		TableRow tableRow;
		TextView textView;
		tableRow = new TableRow(context);
		for (String str : arrNameCol) {
			tableRow.setBackgroundColor(Color.parseColor("#c6e99d"));
			textView = new TextView(context);
			textView.setText(str);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
		}
		int i=0;
		tableLayout.addView(tableRow);
		for (final ExpenseItem item : arrItem) {
			tableRow = new TableRow(context);
			int color=(i==1? Color.parseColor("#effbe1"):Color.WHITE);
			tableRow.setBackgroundColor(color);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			DateTimeManager dtM= new DateTimeManager(item.getDatetime());
			textView.setText(dtM.getDateTimeFromSQLToShow());
			Log.i("getDateTimeFormatSQLFromSQL", dtM.getDateTimeFromSQLToShow());
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(200);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(dataMan.getNameByID(CommonVL.CATALOGUE_TABLE_NAME,
					item.getCataloge()));
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(200);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getProduct());
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(200);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(formatter.format(item.getMoney()).replace(".", ","));
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(150);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,
					item.getFrom()));
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getDes());
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
			final String id = item.getId();
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					createSettingDialog(item, id, CommonVL.VL_NUM_EXPENSE_FRAG)
							.show();
				}
			});
			i=(i==0?1:0);
		}

	}

	void showDetailAllOfCollection() {
		ArrayList<CollectingMoneyItem> arrItem = dataMan
				.getAllOfCollectingTable();
		String[] arrNameCol = arrCollectNameColumn;
		if (arrItem == null) {
			return;
		}
		int i=0;
		tableLayout.setHorizontalScrollBarEnabled(true);

		TableRow tableRow;
		TextView textView;
		tableRow = new TableRow(context);
		for (String str : arrNameCol) {
			tableRow.setBackgroundColor(Color.parseColor("#c6e99d"));
			textView = new TextView(context);
			textView.setText(str);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
		}
		tableLayout.addView(tableRow);
		for (final CollectingMoneyItem item : arrItem) {
			tableRow = new TableRow(context);
			int color=(i==1? Color.parseColor("#effbe1"):Color.WHITE);
			tableRow.setBackgroundColor(color);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			DateTimeManager dtM= new DateTimeManager(item.getDatetime());
			textView.setText(dtM.getDateTimeFromSQLToShow());
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(200);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getFrom());
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(150);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,
					item.getToAcc()));
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(200);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextColor(Color.parseColor("#669900"));
			textView.setPadding(10, 10, 10, 10);
			textView.setText(formatter.format(item.getMoney()).replace(".", ","));
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getDescription());
			tableRow.addView(textView);

			tableLayout.addView(tableRow);
			final String id = item.getId();
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					createSettingDialog(item, id, CommonVL.VL_NUM_COLLECT_FRAG)
							.show();
				}
			});
			i=(i==0?1:0);
		}

	}

	void showDetailAllOfCatalogue() {
		ArrayList<CatalogueItem> arrItem = dataMan.getAllOfCatalogueItem();
		String[] arrNameCol = arrCataNameColumn;
		if (arrItem == null) {
			return;
		}
		tableLayout.setHorizontalScrollBarEnabled(true);

		TableRow tableRow;
		TextView textView;
		tableRow = new TableRow(context);
		for (String str : arrNameCol) {
			tableRow.setBackgroundColor(Color.parseColor("#c6e99d"));
			textView = new TextView(context);
			textView.setText(str);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
		}
		int i=0;
		tableLayout.addView(tableRow);
		for (final CatalogueItem item : arrItem) {
			tableRow = new TableRow(context);
			int color=(i==1? Color.parseColor("#effbe1"):Color.WHITE);
			tableRow.setBackgroundColor(color);
			textView = new TextView(context);
			textView.setText(item.getName());
			textView.setTextSize(18);
			textView.setWidth(150);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(500);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getDescription());
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
			final String id = item.getId();
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					createSettingDialog(item, id,
							CommonVL.VL_NUM_CATALOGUE_FRAG).show();
				}
			});
			i=(i==0?1:0);
		}

	}

	void showDetailAllOfAccount() {

		ArrayList<AccountItem> arrItem = dataMan.getAllOfAccountItem();
		String[] arrNameCol = arrAccountNameColumn;
		if (arrItem == null) {
			return;
		}
		tableLayout.setHorizontalScrollBarEnabled(true);

		TableRow tableRow;
		TextView textView;
		tableRow = new TableRow(context);
		for (String str : arrNameCol) {
			tableRow.setBackgroundColor(Color.parseColor("#c6e99d"));
			textView = new TextView(context);
			textView.setText(str);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
		}
		int i=0;
		tableLayout.addView(tableRow);
		for (final AccountItem item : arrItem) {
			tableRow = new TableRow(context);
			int color=(i==1? Color.parseColor("#effbe1"):Color.WHITE);
			tableRow.setBackgroundColor(color);
			textView = new TextView(context);
			textView.setText(item.getName());
			textView.setWidth(200);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(500);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getDescription());
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
			final String id = item.getId();
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					createSettingDialog(item, id, CommonVL.VL_NUM_ACCOUNT_FRAG)
							.show();
				}
			});
			i=(i==0?1:0);
		}

	}

	AlertDialog createSettingDialog(final Object item, final String id,
			final int valueDetail) {

		final AlertDialog alertSetting;
		alertSetting = new AlertDialog.Builder(context).create();
		alertSetting.setTitle("Tùy chọn");

		alertSetting.setCanceledOnTouchOutside(false);
		alertSetting.setCancelable(false);

		alertSetting.setMessage("\nChọn thao tác");
		alertSetting.setButton(AlertDialog.BUTTON_POSITIVE, "Sửa",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (valueDetail) {
						case CommonVL.VL_NUM_COLLECT_FRAG:
							editCollect(id);
							break;
						case CommonVL.VL_NUM_EXPENSE_FRAG:
							editExpense(id);
							break;
						case CommonVL.VL_NUM_DEPT_FRAG:
							editDebt(id);
							break;
						case CommonVL.VL_NUM_LOAN_FRAG:
							editLoan(id);
							break;
						case CommonVL.VL_NUM_ACCOUNT_FRAG:
							editAccount(id);
							break;
						case CommonVL.VL_NUM_CATALOGUE_FRAG:
							editCatalogue(id);
							break;
						}
					}
				});
		if (valueDetail == CommonVL.VL_NUM_LOAN_FRAG
				|| valueDetail == CommonVL.VL_NUM_DEPT_FRAG) {
			String nameOfButton = null;
			switch (valueDetail) {
			case CommonVL.VL_NUM_LOAN_FRAG:
				LoanItem loan = dataMan.getLoanItemById(id);
				nameOfButton = (loan.getState() == 0 ? "Trả tiền" : "Chưa trả");
				break;
			case CommonVL.VL_NUM_DEPT_FRAG:
				DebtItem debt = dataMan.getDebtItemById(id);
				nameOfButton = (debt.getState() == 0 ? "Trả tiền" : "Chưa trả");
				break;
			default:
				break;
			}
			alertSetting.setButton(AlertDialog.BUTTON_POSITIVE, nameOfButton,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							createConfirmDialog(item, id).show();
						}
					});
		}
		alertSetting.setButton(AlertDialog.BUTTON_NEGATIVE, "Xóa",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (valueDetail) {
						case CommonVL.VL_NUM_ACCOUNT_FRAG:
							if(id.equals("TK1") || dataMan.isExistOfValue(CommonVL.COLLECTING_TABLE_NAME, CollectingMoneyItem.KEY_CODEID, id)
									|| dataMan.isExistOfValue(CommonVL.DEBT_TABLE_NAME, DebtItem.KEY_TO_ACCOUNT, id)
									|| dataMan.isExistOfValue(CommonVL.LOAN_TABLE_NAME, LoanItem.KEY_FROM_ACCOUNT, id)
									|| dataMan.isExistOfValue(CommonVL.EXPENSE_TABLE_NAME, ExpenseItem.KEY_ACCOUNT, id)){
								Toast.makeText(context, "Thay đổi làm hỏng dữ liệu có liên quan\nKHÔNG XÓA ĐƯỢC", Toast.LENGTH_LONG).show();
							}else 
								createDeleteDialog(item, id).show();
							break;
						case CommonVL.VL_NUM_CATALOGUE_FRAG:
							if(id.equals("DM1") || dataMan.isExistOfValue(CommonVL.EXPENSE_TABLE_NAME, ExpenseItem.KEY_CATALOGUE, id)){
								Toast.makeText(context, "Thay đổi làm hỏng dữ liệu có liên quan\nKHÔNG XÓA ĐƯỢC", Toast.LENGTH_LONG).show();
							}else 
								createDeleteDialog(item, id).show();
							break;
						default:
							createDeleteDialog(item, id).show();
							break;
						}
					}
				});
		alertSetting.setButton(AlertDialog.BUTTON_NEUTRAL, "Hủy",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						alertSetting.dismiss();
					}
				});
		return alertSetting;
	}

	AlertDialog createDeleteDialog(final Object item, final String id) {
		final AlertDialog alertDelete = new AlertDialog.Builder(context)
				.create();
		alertDelete.setTitle("Tùy chọn");

		alertDelete.setCanceledOnTouchOutside(false);
		alertDelete.setCancelable(false);

		alertDelete.setMessage("Bạn muốn xóa ?");
		alertDelete.setButton(AlertDialog.BUTTON_POSITIVE, "Có",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (dataMan.deleteData(
								CommonVL.getNameTableByNumBer(detailOf), id)) {
							Toast.makeText(context, "Xóa thành công!",
									Toast.LENGTH_SHORT).show();
							showDetail(detailOf);
						} else
							Toast.makeText(context, "Xóa KHÔNG thành công!",
									Toast.LENGTH_SHORT).show();

					}
				});
		alertDelete.setButton(AlertDialog.BUTTON_NEGATIVE, "Không",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Toast.makeText(context, "KHÔNG xóa", Toast.LENGTH_SHORT)
								.show();
					}
				});
		return alertDelete;
	}

	void showDetailAllOfDebt() {
		ArrayList<DebtItem> arrItem = dataMan.getAllOfDebtItem();
		String[] arrNameCol = { DebtItem.KEY_DATETIME_BEGIN, DebtItem.KEY_NAME,
				DebtItem.KEY_DATETIME_END, DebtItem.KEY_PURPOSE,
				DebtItem.KEY_TO_ACCOUNT, DebtItem.KEY_MONEY,
				DebtItem.KEY_DESCRIPTION, DebtItem.KEY_STATE, DebtItem.KEY_PAYED };
		if (arrItem == null) {
			return;
		}
		tableLayout.setHorizontalScrollBarEnabled(true);

		TableRow tableRow;
		TextView textView;
		tableRow = new TableRow(context);
		for (String str : arrNameCol) {
			tableRow.setBackgroundColor(Color.parseColor("#c6e99d"));
			textView = new TextView(context);
			textView.setText(str);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
		}
		int i=0;
		tableLayout.addView(tableRow);
		for (final DebtItem item : arrItem) {
			tableRow = new TableRow(context);
			int color=(i==1? Color.parseColor("#effbe1"):Color.WHITE);
			tableRow.setBackgroundColor(color);
			textView = new TextView(getActivity());
			DateTimeManager dtM= new DateTimeManager(item.getTimeBegin());
			textView.setText(dtM.getDateTimeFromSQLToShow());
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(item.getName());
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			DateTimeManager dtM2= new DateTimeManager(item.getTimeEnd());
			textView.setText(dtM2.getDateTimeFromSQLToShow());
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(item.getPurpose());
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,
					item.getTo()));
			textView.setTextSize(18);
			textView.setWidth(150);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(formatter.format(item.getMoney()).replace(".", ","));
			textView.setTextSize(18);
			textView.setTypeface(null, Typeface.BOLD);
			if(item.getState()==0)
				textView.setTextColor(Color.parseColor("#669900"));
			textView.setWidth(200);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getDes());
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(item.getStateString());
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			if(item.getPayed()==null|| item.getPayed().equals("null")) textView.setText("");
			else{
				DateTimeManager dtM1= new DateTimeManager(item.getPayed());
				textView.setText(dtM1.getDateTimeFromSQLToShow());
			}
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
			final String id = item.getId();
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					createSettingDialog(item, id, CommonVL.VL_NUM_DEPT_FRAG)
							.show();
				}
			});
			i=(i==0?1:0);
		}
	}

	void showDetailAllOfLoan() {
		ArrayList<LoanItem> arrItem = dataMan.getAllOfLoanItem();
		String[] arrNameCol = { LoanItem.KEY_DATETIME_BEGIN, LoanItem.KEY_NAME,
				LoanItem.KEY_DATETIME_END, LoanItem.KEY_PURPOSE,
				LoanItem.KEY_FROM_ACCOUNT, LoanItem.KEY_MONEY,
				LoanItem.KEY_DESCRIPTION, LoanItem.KEY_STATE ,LoanItem.KEY_PAYED};
		if (arrItem == null) {
			return;
		}
		tableLayout.setHorizontalScrollBarEnabled(true);

		TableRow tableRow;
		TextView textView;
		tableRow = new TableRow(context);
		for (String str : arrNameCol) {
			tableRow.setBackgroundColor(Color.parseColor("#c6e99d"));
			textView = new TextView(context);
			textView.setText(str);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
		}
		int i=0;
		tableLayout.addView(tableRow);
		for (final LoanItem item : arrItem) {
			tableRow = new TableRow(context);
			int color=(i==1? Color.parseColor("#effbe1"):Color.WHITE);
			tableRow.setBackgroundColor(color);
			textView = new TextView(getActivity());
			DateTimeManager dtM= new DateTimeManager(item.getTimeBegin());
			textView.setText(dtM.getDateTimeFromSQLToShow());
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(item.getName());
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			DateTimeManager dtM1= new DateTimeManager(item.getTimeEnd());
			textView.setText(dtM1.getDateTimeFromSQLToShow());
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(item.getPurpose());
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(dataMan.getNameByID(CommonVL.ACCOUNT_TABLE_NAME,
					item.getFrom()));
			textView.setTextSize(18);
			textView.setWidth(150);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(formatter.format(item.getMoney()).replace(".", ","));
			textView.setTextSize(18);
			textView.setTypeface(null, Typeface.BOLD);
			if(item.getState()==1)
				textView.setTextColor(Color.parseColor("#669900"));
			textView.setWidth(200);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setWidth(250);
			textView.setMaxHeight(100);
			textView.setPadding(10, 10, 10, 10);
			textView.setText(item.getDes());
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setText(item.getStateString());
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			tableRow.addView(textView);
			textView = new TextView(context);
			textView.setTextSize(18);
			textView.setPadding(10, 10, 10, 10);
			if(item.getPayed()==null || item.getPayed().equals("null")) textView.setText("");
			else{
			DateTimeManager dtM2= new DateTimeManager(item.getPayed());
			textView.setText(dtM2.getDateTimeFromSQLToShow());}
			tableRow.addView(textView);
			tableLayout.addView(tableRow);
			final String id = item.getId();
			tableRow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					createSettingDialog(item, id, CommonVL.VL_NUM_LOAN_FRAG)
							.show();
				}
			});
			i=(i==0?1:0);
		}

	}

	AlertDialog createConfirmDialog(final Object item, final String id) {
		final String tableName = (detailOf == CommonVL.VL_NUM_DEPT_FRAG ? CommonVL.DEBT_TABLE_NAME
				: CommonVL.LOAN_TABLE_NAME);
		final String whereClause = "Ma = '" + id + "'";
		String text="";
		String isPay = null;
		int statePay=0;
		switch (detailOf) {
		case CommonVL.VL_NUM_DEPT_FRAG:
			DebtItem debt = dataMan.getDebtItemById(id);
			statePay = debt.getState();
			debt.setState(statePay==1?0:1);
			isPay = debt.getStateString();
			text=debt.getName();
			break;
		case CommonVL.VL_NUM_LOAN_FRAG:
			LoanItem loan = dataMan.getLoanItemById(id);
			statePay = loan.getState();
			loan.setState(statePay==1?0:1);
			isPay = loan.getStateString();
			text=loan.getName();
			break;
		}
		final AlertDialog alertConfirm = new AlertDialog.Builder(context)
				.create();
		alertConfirm.setCanceledOnTouchOutside(false);
		alertConfirm.setCancelable(false);
		if (statePay == 0) {
			alertConfirm.setTitle("Ngày trả:");
			LayoutInflater factory = LayoutInflater.from(context);
			final View view = factory.inflate(R.layout.item_layout, null);
			alertConfirm.setView(view);
			final EditText etTime = (EditText) view
					.findViewById(R.id.txtGioTra);
			final EditText etDate = (EditText) view
					.findViewById(R.id.txtNgayTra);
			final DateTimeManager dtMan = new DateTimeManager(context, etTime,
					etDate);
			etTime.setText(dtMan.getTimeNow());
			etDate.setText(dtMan.getDateNow());
			etTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dtMan.showTimePickerDialog();
				}
			});
			etDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dtMan.showDatePickerDialog();
				}
			});
			alertConfirm.setMessage("Xác nhận " + isPay + " cho:\n"
					+ text + " ?");
			alertConfirm.setButton(AlertDialog.BUTTON_POSITIVE, "Xác nhận",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String datetime = dtMan.getDateTimeFormatSQL();
							ContentValues value = new ContentValues();
							value.put("NgayTra", datetime);
							value.put("TrangThai", 1);
							dataMan.updateData(tableName, value, whereClause,
									null);
							showDetail(detailOf);
						}
					});
		} else if(statePay==1) {
			alertConfirm.setTitle("Xác nhận:");
			alertConfirm.setMessage("Xác nhận " + isPay + " cho:\n"
					+ text + " ?");
			alertConfirm.setButton(AlertDialog.BUTTON_POSITIVE, "Xác nhận",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							ContentValues value = new ContentValues();
							value.put("NgayTra", "null");
							value.put("TrangThai", 0);
							if(dataMan.updateData(tableName, value, whereClause,
									null))
							showDetail(detailOf);
						}
					});
		}
		alertConfirm.setButton(AlertDialog.BUTTON_NEGATIVE, "Không",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Toast.makeText(context, "KHÔNG xác nhận",
								Toast.LENGTH_SHORT).show();
					}
				});
		return alertConfirm;
	}

	private void editExpense(final String id) {
		ExpenseManager exManager = new ExpenseManager(context);
		Bundle b = new Bundle();
		b.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EDIT);
		b.putString(CommonVL.ID_TO_EDIT, id);
		exManager.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, exManager);
		fragmentTransaction.commit();
	}

	private void editCollect(final String id) {
		CollectingMoneyManager colManager = new CollectingMoneyManager(context);
		Bundle b = new Bundle();
		b.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EDIT);
		b.putString(CommonVL.ID_TO_EDIT, id);
		colManager.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, colManager);
		fragmentTransaction.commit();
	}

	private void editDebt(final String id) {
		DebtManager manager = new DebtManager(context);
		Bundle b = new Bundle();
		b.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EDIT);
		b.putString(CommonVL.ID_TO_EDIT, id);
		manager.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, manager);
		fragmentTransaction.commit();
	}

	private void editLoan(final String id) {
		LoanManager manager = new LoanManager(context);
		Bundle b = new Bundle();
		b.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EDIT);
		b.putString(CommonVL.ID_TO_EDIT, id);
		manager.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, manager);
		fragmentTransaction.commit();
	}

	private void editAccount(final String id) {
		AccountManager manager = new AccountManager(context);
		Bundle b = new Bundle();
		b.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EDIT);
		b.putString(CommonVL.ID_TO_EDIT, id);
		manager.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, manager);
		fragmentTransaction.commit();
	}

	private void editCatalogue(final String id) {
		CatalogueManager manager = new CatalogueManager(context);
		Bundle b = new Bundle();
		b.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EDIT);
		b.putString(CommonVL.ID_TO_EDIT, id);
		manager.setArguments(b);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_content, manager);
		fragmentTransaction.commit();
	}
}
