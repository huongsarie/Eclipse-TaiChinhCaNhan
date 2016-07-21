package com.sar.taichinhcanhan.common;

public class CommonVL{

	
	public static final String START_ACTIVITY = "start_activity";
	public static final String START_ACTION = "start_action";
	public static final String START_FRAGMENT = "start_fragment";
	public static final String END_ACTION = "end_activity";
	
	public static final int VL_END= 0;
	public static final int START_COLLECT = 1001;
	public static final int START_EXPENSE = 1002;
	public static final int START_LOAN = 1003;
	public static final int START_DEBT = 1004;
	public static final int START_STATISTIC = 1005;
	public static final int START_PLANNING = 1006;
	public static final int START_INTRODUCTION = 1007;
	public static final int START_CATALOGUE = 1008;
	public static final int START_ACCOUNT = 1009;
	public static final int START_EDIT = 10021;
	public static final int START_DETAIL_EXPENSE = 10022;
	public static final int START_REPORT = 1010;
	public static final int START_REPORT1 = 10101;
	public static final int START_REPORT2 = 10102;
	public static final int START_REPORT3 = 10103;
	
	
	public static final int VL_NUM_COLLECT_FRAG =1;
	public static final int VL_NUM_EXPENSE_FRAG = 2;
	public static final int VL_NUM_DEPT_FRAG = 3;
	public static final int VL_NUM_LOAN_FRAG =4;
	public static final int VL_NUM_STATISTIC_FRAG =5;
	public static final int VL_NUM_PLANNING_FRAG =6;
	public static final int VL_NUM_INTRODUCE_FRAG =7;
	public static final int VL_NUM_CATALOGUE_FRAG =8;
	public static final int VL_NUM_ACCOUNT_FRAG =9;
	public static final int VL_NUM_DETAIL_FRAG =10;
	public static final int VL_NUM_REPORT_FRAG1 = 11; //thống kê chi tiêu theo danh mục
	public static final int VL_NUM_REPORT_FRAG2 = 12; //thống kê chi tiêu so với thu nhập
	public static final int VL_NUM_REPORT_FRAG3 = 13; //thống kê thu nhập đối với các tài khoản
	
	
	public static final String DETAIL_FRAG = "start_show_detail";
	
	public static final String CATALOGUE_TABLE_NAME = "DanhMucChiTieu";
	public static final String ACCOUNT_TABLE_NAME = "TaiKhoanTien";
	public static final String EXPENSE_TABLE_NAME = "ChiTieu";
	public static final String COLLECTING_TABLE_NAME = "ThuNhap";
	public static final String LOAN_TABLE_NAME = "ChoVay";
	public static final String DEBT_TABLE_NAME = "VayNo";
	public static final String SETTING_REQUEST = "setting_request";
	public static final String ID_TO_EDIT = "id_to_edit";
	
	public static String getNameTableByNumBer(int num){
		String name=null;
		switch (num) {
		case VL_NUM_COLLECT_FRAG:
			name= COLLECTING_TABLE_NAME;
			break;
		case VL_NUM_EXPENSE_FRAG:
			name= EXPENSE_TABLE_NAME; break;
		case VL_NUM_CATALOGUE_FRAG:
			name= CATALOGUE_TABLE_NAME; break;
		case VL_NUM_ACCOUNT_FRAG:
			name= ACCOUNT_TABLE_NAME;break;
		case VL_NUM_DEPT_FRAG:
			name= DEBT_TABLE_NAME;break;
		case VL_NUM_LOAN_FRAG:
			name= LOAN_TABLE_NAME;break;
		}
		return name;
	}
}