package com.sar.taichinhcanhan.main;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.component.DatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btChiTieu, btThu, btChoVay, btVayNo, btThongKe,btGioiThieu;
	private TextView txtTongDuMain;
	private Intent mIntent;
	private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btChi:
				clickButton(CommonVL.START_EXPENSE);
				break;
			case R.id.btThu:
				clickButton(CommonVL.START_COLLECT);
				break;
			case R.id.btChoVay:
				clickButton(CommonVL.START_LOAN);
				break;
			case R.id.btVayNo:
				clickButton(CommonVL.START_DEBT);
				break;
			case R.id.btThongKe:
				clickButton(CommonVL.START_REPORT);
				break;
		
			}

		}
	};

	private void clickButton(int startWhat) {
		mIntent = new Intent();
		mIntent.setClass(MainActivity.this, MyFragment.class);
		mIntent.setAction(CommonVL.START_ACTIVITY);
		mIntent.putExtra(CommonVL.START_ACTION, startWhat);
		startActivity(mIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Start App", "STARTING_APPLICATION");
		setContentView(R.layout.main);
		initComponents();
	}

	private void initComponents() {
		// dataMan= new DatabaseManager(MainActivity.this);
		btChiTieu = (Button) findViewById(R.id.btChi);
		btChiTieu.setOnClickListener(onClick);
		btThu = (Button) findViewById(R.id.btThu);
		btThu.setOnClickListener(onClick);
		btChoVay = (Button) findViewById(R.id.btChoVay);
		btChoVay.setOnClickListener(onClick);
		btVayNo = (Button) findViewById(R.id.btVayNo);
		btVayNo.setOnClickListener(onClick);
		btThongKe = (Button) findViewById(R.id.btThongKe);
		btThongKe.setOnClickListener(onClick);
		txtTongDuMain=(TextView)findViewById(R.id.txtTongDuMain);
	}
	
	private Double tongTienDu(){
		DatabaseManager dataMan= new DatabaseManager(MainActivity.this);
		return //(dataMan.totalMoneyColect()-dataMan.totalMoneyExpense()
				(dataMan.sumMoneyByMonthYear(CommonVL.COLLECTING_TABLE_NAME, null, null)
						-dataMan.sumMoneyByMonthYear(CommonVL.EXPENSE_TABLE_NAME, null, null)
				+(dataMan.totalMoneyDebtNotPay()-dataMan.totalMoneyDebtPayed())
						+(dataMan.totalMoneyLoanPayed()-dataMan.totalMoneyLoanNotPay()));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		final AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
				.create();
		alert.setTitle("Thoát?");

		alert.setCanceledOnTouchOutside(false);
		alert.setCancelable(false);

		alert.setMessage("Bạn muốn thoát?");
		alert.setButton(AlertDialog.BUTTON_POSITIVE, "Có",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//MainActivity.this.finish();
						System.exit(0);
					}
				});
		alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Không",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						alert.cancel();
					}
				});
		alert.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		txtTongDuMain.setText(formatter.format(tongTienDu()).replace(".", ",")+" VNĐ");
	}
}
