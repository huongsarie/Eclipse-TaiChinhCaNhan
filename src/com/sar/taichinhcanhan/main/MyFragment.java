package com.sar.taichinhcanhan.main;

import com.sar.taichinhcanhan.R;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.component.DatabaseManager;
import com.sar.taichinhcanhan.fragment.AccountManager;
import com.sar.taichinhcanhan.fragment.CatalogueManager;
import com.sar.taichinhcanhan.fragment.CollectingMoneyManager;
import com.sar.taichinhcanhan.fragment.DebtManager;
import com.sar.taichinhcanhan.fragment.DetailFragment;
import com.sar.taichinhcanhan.fragment.ExpenseManager;
import com.sar.taichinhcanhan.fragment.IntroductionManager;
import com.sar.taichinhcanhan.fragment.LoanManager;
import com.sar.taichinhcanhan.fragment.StatisticsManager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyFragment extends FragmentActivity implements OnClickListener,OnItemClickListener,DrawerListener{
	// quản lý các fragment
	private Context context;
	private FragmentManager manager;
	private FragmentTransaction transaction;
	private ExpenseManager expenseMan;
	private CollectingMoneyManager collectingMan;
	private LoanManager loanMan;
	private CatalogueManager catalogueMan;
	private AccountManager accountMan;
	private StatisticsManager statisticMan;
	private DebtManager debtMan;
	private TextView txtTitleTopBar;
	private int currentFrag; //danh dau frag nao dang dc display
	private Fragment fragmentDisplaying;
	//var cua nevigation
	private ListView listView;
	private LevelListDrawable levelList;
	private DrawerLayout drawableLayout;
	private ArrayAdapter<String> adapter;
	
	private DetailFragment detailFrag;
	
	private ImageView imgMenu;
	private OnItemClickListener onExpenseItemClick,onCollectionItemClick,onLoanItemClick, onDebtItemClick,onReportItemClick;
	private final String[] arrExpenseSelection=
		{"Thêm chi tiêu","Xem danh sách đã chi","Thêm danh mục chi tiêu","Xem danh sách danh mục"},
		arrCollectionSelection={"Thêm thu nhập","Xem danh sách thu nhập","Thêm tài khoản tiền","Xem danh sách tài khoản tiền"},
		arrLoanSelection={"Thêm cho vay","Xem danh sách cho vay"},
				arrDebtSelection={"Thêm vay nợ","Xem danh sách vay nợ"},
				arrReportSelection={"Thống kê chi tiêu theo danh mục","Thống kê chi tiêu đối với thu nhập","Thống kê thu nhập từ tài khoản"};
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		context=MyFragment.this;
		setContentView(R.layout.fragment_content_layout);
		currentFrag=0;
		init();
		getActionToShow(getIntent());
	}
	
	private void init() {
		initClick();
		fragmentDisplaying=null;
		txtTitleTopBar=(TextView)findViewById(R.id.topbarName);
		
		drawableLayout = (DrawerLayout) findViewById(R.id.drawLayout);
		drawableLayout.setDrawerListener(this);
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		imgMenu=(ImageView)findViewById(R.id.imgMenu);
		levelList = (LevelListDrawable) imgMenu.getDrawable();
		drawableLayout.setDrawerLockMode(drawableLayout.LOCK_MODE_UNLOCKED);
		imgMenu.setOnClickListener(this);
		manager = getFragmentManager();
		transaction = manager.beginTransaction();
	}
	
	private void initClick(){
		onExpenseItemClick= new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
					switch (pos) {
					case 0:if(currentFrag==CommonVL.VL_NUM_EXPENSE_FRAG)
						break;
						showExpenseLayout();
						break;
					case 1:
						txtTitleTopBar.setText("Danh sách chi tiêu");
						onClickDetail(CommonVL.VL_NUM_EXPENSE_FRAG);
						break;
					case 2:
						if(currentFrag==CommonVL.VL_NUM_CATALOGUE_FRAG)
							break;
						showCatalogueLayout();
						break;
					case 3:
						txtTitleTopBar.setText("Danh sách danh mục");
						onClickDetail(CommonVL.VL_NUM_CATALOGUE_FRAG);
						break;
					
					}
				drawableLayout.closeDrawers();
			}
			
		};
		
		onCollectionItemClick= new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
					switch (pos) {
					case 0:
						if(currentFrag==CommonVL.VL_NUM_COLLECT_FRAG)
							break;
						showCollectingLayout();
						break;
					case 1:
						txtTitleTopBar.setText("Danh sách thu nhập");
						onClickDetail(CommonVL.VL_NUM_COLLECT_FRAG);
						break;
					case 2:
						if(currentFrag==CommonVL.VL_NUM_ACCOUNT_FRAG)
							break;
						showAccountLayout();
						break;
					case 3:
						txtTitleTopBar.setText("Danh sách tài khoản ti�?n");
						onClickDetail(CommonVL.VL_NUM_ACCOUNT_FRAG);
						break;
					
					}
				drawableLayout.closeDrawers();
			}
			
		};
		
		onLoanItemClick= new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
					switch (pos) {
					case 0:
						if(currentFrag==CommonVL.VL_NUM_LOAN_FRAG)
							break;
						showLoanLayout();
						break;
					case 1:
						txtTitleTopBar.setText("Danh sách cho vay");
						onClickDetail(CommonVL.VL_NUM_LOAN_FRAG);
						break;
					
					}
				drawableLayout.closeDrawers();
			}
			
		};
		
		onDebtItemClick= new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
					switch (pos) {
					case 0:
						if(currentFrag==CommonVL.VL_NUM_DEPT_FRAG)
							break;
						showDebtLayout();
						break;
					case 1:
						txtTitleTopBar.setText("Danh sách vay nợ");
						onClickDetail(CommonVL.VL_NUM_DEPT_FRAG);
						break;
					
					}
				drawableLayout.closeDrawers();
			}
			
		};
		onReportItemClick=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
					switch (pos) {
					case 0:
						if(currentFrag==CommonVL.VL_NUM_REPORT_FRAG1)
							break;
						showReport1Layout();
						break;
					case 1:if(currentFrag==CommonVL.VL_NUM_REPORT_FRAG2)
						break;
					showReport2Layout();
						break;
					case 2:if(currentFrag==CommonVL.VL_NUM_REPORT_FRAG3)
						break;
					showReport3Layout();
						break;
					
					}
				drawableLayout.closeDrawers();
			}
		};
	}
	private void showNevigation(final int cur){
		//show navigation theo fragment hiện tại đang hiện
		switch (cur) {
		case CommonVL.VL_NUM_COLLECT_FRAG:
			showNavigation(arrCollectionSelection, onCollectionItemClick);
			break;
			
		case CommonVL.VL_NUM_EXPENSE_FRAG:
			showNavigation(arrExpenseSelection,onExpenseItemClick);
			break;
		case CommonVL.VL_NUM_LOAN_FRAG:
			showNavigation(arrLoanSelection,onLoanItemClick);
			break;

		case CommonVL.VL_NUM_DEPT_FRAG:
			showNavigation(arrDebtSelection,onDebtItemClick);
			break;
		case CommonVL.VL_NUM_REPORT_FRAG1:
			showNavigation(arrReportSelection,onReportItemClick);
			break;
		case CommonVL.VL_NUM_REPORT_FRAG2:
			showNavigation(arrReportSelection,onReportItemClick);
			break;
		case CommonVL.VL_NUM_REPORT_FRAG3:
			showNavigation(arrReportSelection,onReportItemClick);
			break;
		default:
			break;
		}
	}

	private void getActionToShow(Intent intent) {
		
		intent = getIntent();
		if (!intent.getAction().equals(CommonVL.START_ACTIVITY))
			finish();
		else{
		switch (intent.getIntExtra(CommonVL.START_ACTION,
				CommonVL.START_EXPENSE)) {

		case CommonVL.START_EXPENSE:
			currentFrag=CommonVL.VL_NUM_EXPENSE_FRAG;
			showExpenseLayout();
			break;

		case CommonVL.START_COLLECT:
			currentFrag=CommonVL.VL_NUM_COLLECT_FRAG;
			showCollectingLayout();
			break;
			
		case CommonVL.START_LOAN:
			currentFrag=CommonVL.VL_NUM_LOAN_FRAG;
			showLoanLayout();
			break;
		case CommonVL.START_DEBT:
			currentFrag=CommonVL.VL_NUM_DEPT_FRAG;
			showDebtLayout();
			break;
		case CommonVL.START_REPORT:
			currentFrag=CommonVL.VL_NUM_REPORT_FRAG1;
			showReport1Layout();
			break;
		case CommonVL.START_INTRODUCTION:
			imgMenu.setVisibility(View.INVISIBLE);
			showIntroductionLayout();
			break;
		default:
			break;
		}

		showNevigation(currentFrag);
		}
	}

	
	private void showCollectingLayout() {
		collectingMan= new CollectingMoneyManager(context);
		transaction.remove(fragmentDisplaying);
		txtTitleTopBar.setText("THU NHẬP");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_COLLECT);
		collectingMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, collectingMan).commit();
		fragmentDisplaying=collectingMan;
	}
	
	private void showExpenseLayout() {
		expenseMan= new ExpenseManager(context);
		transaction.remove(fragmentDisplaying);
		txtTitleTopBar.setText("CHI TIÊU");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_EXPENSE);
		expenseMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, expenseMan).commit();
		fragmentDisplaying=expenseMan;
	}

	private void showLoanLayout() {
		loanMan=new LoanManager(context);
		transaction.remove(fragmentDisplaying);
		txtTitleTopBar.setText("CHO VAY");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_LOAN);
		loanMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, loanMan).commit();
		fragmentDisplaying=loanMan;
	}
	
	private void showDebtLayout() {
		debtMan= new DebtManager(context);
		transaction.remove(fragmentDisplaying);
		txtTitleTopBar.setText("VAY NỢ");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_DEBT);
		debtMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, debtMan).commit();
		fragmentDisplaying=debtMan;
	}
	
	private void showCatalogueLayout() {
		catalogueMan=new CatalogueManager(context);
		transaction.remove(fragmentDisplaying);
		currentFrag=CommonVL.VL_NUM_CATALOGUE_FRAG;
		txtTitleTopBar.setText("DANH MỤC");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_CATALOGUE);
		catalogueMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, catalogueMan).commit();
		fragmentDisplaying=catalogueMan;
	}
	
	private void showAccountLayout() {
		accountMan=new AccountManager(context);
		transaction.remove(fragmentDisplaying);
		currentFrag=CommonVL.VL_NUM_ACCOUNT_FRAG;
		txtTitleTopBar.setText("TÀI KHOẢN TIỀN");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_ACCOUNT);
		accountMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, accountMan).commit();
		fragmentDisplaying=accountMan;
	}
	private void showReport1Layout() {
		statisticMan= new StatisticsManager(context);
		transaction.remove(fragmentDisplaying);
		currentFrag=CommonVL.VL_NUM_REPORT_FRAG1;
		txtTitleTopBar.setText("THỐNG KÊ");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_REPORT1);
		statisticMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, statisticMan).commit();
		fragmentDisplaying=statisticMan;
	}
	
	private void showReport2Layout() {
		statisticMan= new StatisticsManager(context);
		transaction.remove(fragmentDisplaying);
		currentFrag=CommonVL.VL_NUM_REPORT_FRAG2;
		txtTitleTopBar.setText("THỐNG KÊ");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_REPORT2);
		statisticMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, statisticMan).commit();
		fragmentDisplaying=statisticMan;
	}
	private void showReport3Layout() {
		statisticMan= new StatisticsManager(context);
		transaction.remove(fragmentDisplaying);
		currentFrag=CommonVL.VL_NUM_REPORT_FRAG3;
		txtTitleTopBar.setText("THỐNG KÊ");
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.START_FRAGMENT, CommonVL.START_REPORT3);
		statisticMan.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, statisticMan).commit();
		fragmentDisplaying=statisticMan;
	}

	private void showIntroductionLayout(){
		IntroductionManager intro= new IntroductionManager();
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, intro).commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imgMenu:
			if (levelList.getLevel() == 0){// click vao menu moi cho mo menu
				showNevigation(currentFrag);
				drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);//cho phep mo menu
			}
			else{
				drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//cho phep dong menu

				drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			}
			break;
		}
	}
	

	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub
		levelList.setLevel(0);
	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub
		levelList.setLevel(1);
	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	void showNavigation(String[] arrSelection,OnItemClickListener onItemClick){
		adapter=new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, arrSelection);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClick);
		levelList.setLevel(0);
	}

	void onClickDetail(int value){
		detailFrag=new DetailFragment(context);
		transaction.remove(fragmentDisplaying);
		currentFrag=CommonVL.VL_NUM_DETAIL_FRAG;
		Bundle bundle = new Bundle();
		bundle.putInt(CommonVL.DETAIL_FRAG, value);
		detailFrag.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.fragment_content, detailFrag).commit();
		fragmentDisplaying=detailFrag;
	}


	//hide-keyboard-when-touched-outside
	 @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	            if (getCurrentFocus() != null) {
	                  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                  imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	                }
	        return super.dispatchTouchEvent(ev);
	    }
	
}

