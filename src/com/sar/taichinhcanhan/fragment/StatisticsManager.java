package com.sar.taichinhcanhan.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.component.DatabaseManager;
import com.sar.taichinhcanhan.component.MySpinner;
import com.sar.taichinhcanhan.main.MainActivity;
import com.sar.taichinhcanhan.main.MyFragment;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StatisticsManager extends Fragment {
	// đối tươgnj xử lý các TH�?NG KÊ
	private Context context;
	private View view;
	private ArrayList<String> arrCataloge;
	private ArrayList<String> arrExpensePerCatalogue;
	private ArrayList<Double> listValue;
	private ArrayList<Float> listPercent;
	private DatabaseManager dataMan;
	private LinearLayout contentLayout;
	private PieChart pieChart;
	private Spinner spnMonth, spnYear;
	private ArrayList<String> arrMonth, arrYear;
	private MySpinner mySpinnerMonth, mySpinnerYear;
	private Button btnShow, btnCancel;
	private TextView txtTongChi, txtTitle;
	private DecimalFormat formatter = new DecimalFormat("###,###,###,###");

	public StatisticsManager(Context context) {
		super();
		this.context = context;
	}

	public StatisticsManager() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.thongke_layout, container, false);
		bundle = this.getArguments();
		init(bundle.getInt(CommonVL.START_FRAGMENT));
		return view;
	}

	private void init(final int numberBundle) {
		txtTitle = (TextView) view.findViewById(R.id.textTitleThongKe);
		switch (numberBundle) {
		case CommonVL.START_REPORT1:

			txtTitle.setText("Thống kê chi tiêu theo danh mục");
			break;
		case CommonVL.START_REPORT2:
			txtTitle.setText("Thống kê chi tiêu đối với thu nhập");
			break;
		case CommonVL.START_REPORT3:
			txtTitle.setText("Thống kê thu nhập từ tài khoản");
			break;

		default:
			break;
		}
		contentLayout = (LinearLayout) view.findViewById(R.id.contentThongKe);
		dataMan = new DatabaseManager(context);
		arrMonth = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			arrMonth.add("Tháng " + i);
		}
		arrMonth.add("Tất cả");
		arrYear = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		int thisYear = calendar.get(Calendar.YEAR);
		int fromYear = 2010;// gia su nam nho nhat lay tu csdl la 2010
		for (int i = fromYear; i <= thisYear; i++) {
			arrYear.add("Năm " + i);
		}

		spnMonth = (Spinner) view.findViewById(R.id.spinnerMonth);
		mySpinnerMonth = new MySpinner(context, spnMonth, arrMonth);
		spnYear = (Spinner) view.findViewById(R.id.spinnerYear);
		mySpinnerYear = new MySpinner(context, spnYear, arrYear);
		txtTongChi = (TextView) view.findViewById(R.id.tongTienThongKe);
		btnCancel = (Button) view.findViewById(R.id.btThoatThongKe);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				i.setClass(context, MyFragment.class);
				i.setAction(CommonVL.END_ACTION);
				startActivity(i);
				getActivity().finish();
			}
		});

		btnShow = (Button) view.findViewById(R.id.btXemThongKe);
		btnShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (numberBundle) {
				case CommonVL.START_REPORT1:
					contentLayout.removeAllViews();
					init1();
					break;
				case CommonVL.START_REPORT2:
					contentLayout.removeAllViews();
					init2();
					break;
				case CommonVL.START_REPORT3:
					contentLayout.removeAllViews();
					init3();
					break;
				default:
					break;
				}
			}
		});

	}

	private void init1() {
		dataMan = new DatabaseManager(context);
		arrExpensePerCatalogue = dataMan.getArrayNameCatalogue();
		arrExpensePerCatalogue.add("Khoản vay nợ đã trả");
		arrExpensePerCatalogue.add("Khoản cho vay chưa trả");
		String monthN = mySpinnerMonth.getItemSelected().equals("Tất cả") ? null
				: mySpinnerMonth.getItemSelected();
		listValue = dataMan.listExpenseWithCatalogue(monthN,
				mySpinnerYear.getItemSelected());
		listPercent = new ArrayList<>();
		double sum = 0;
		for (double i : listValue) {
			sum += i;
		}
		txtTongChi.setText("Tổng chi: \n"
				+ formatter.format(sum).replace(".", ",") + " VNĐ");
		for (int i = 0; i < listValue.size(); i++) {
			listPercent.add((float) (listValue.get(i) / sum * 100));
		}
		pieChart = new PieChart(context);
		contentLayout.addView(pieChart);
		pieChart.setUsePercentValues(true);
		pieChart.setDescription("Thống kê chi tiêu theo danh mục");
		pieChart.setDrawHoleEnabled(true);
		pieChart.setHoleRadius(10);
		pieChart.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		pieChart.setHoleColorTransparent(false);
		pieChart.setTransparentCircleRadius(100);
		pieChart.setRotationAngle(0);
		pieChart.setRotationEnabled(true);
		pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				// TODO Auto-generated method stub
				if (e == null)
					return;
			}

			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub

			}
		});
		addData();
		Legend le = pieChart.getLegend();
		le.setPosition(LegendPosition.RIGHT_OF_CHART);
		le.setXEntrySpace(3);
		le.setYEntrySpace(2);

	}

	private void addData() {
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		for (int i = 0; i < listPercent.size(); i++)
			yVals.add(new Entry(listPercent.get(i), i));
		PieDataSet dataSet = new PieDataSet(yVals,
				"Thống kê chi tiêu theo danh mục");
		dataSet.setValueTextSize(20);
		dataSet.setSliceSpace(1);
		dataSet.setSelectionShift(5);

		ArrayList<Integer> colors = new ArrayList<Integer>();
		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);
		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);
		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);
		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);
		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);
		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);
		colors.add(ColorTemplate.getHoloBlue());
		dataSet.setColors(colors);
		PieData data = new PieData(arrExpensePerCatalogue, dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(16);
		data.setValueTextColor(Color.GRAY);

		pieChart.setData(data);

		// undo all highlights
		pieChart.highlightValues(null);

		// update pie chart
		// pieChart.invalidate();

	}

	private void init2() {
		// TODO Auto-generated method stub
	}

	private void init3() {
		// TODO Auto-generated method stub
		txtTitle.setText("Thống kê thu nhập từ tài khoản");

	}

}
