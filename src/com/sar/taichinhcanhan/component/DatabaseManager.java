package com.sar.taichinhcanhan.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import com.sar.taichinhcanhan.common.AccountItem;
import com.sar.taichinhcanhan.common.CatalogueItem;
import com.sar.taichinhcanhan.common.CollectingMoneyItem;
import com.sar.taichinhcanhan.common.CommonVL;
import com.sar.taichinhcanhan.common.DebtItem;
import com.sar.taichinhcanhan.common.ExpenseItem;
import com.sar.taichinhcanhan.common.LoanItem;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DatabaseManager {
	private static final String DATA_PATH = Environment.getDataDirectory()
			+ "/data/com.sar.taichinhcanhan/databases/";
	private static final String DATA_FILE = "TaiChinhCaNhan.sqlite";
	// quản lý CSDL, m�?i thao tác đối với CSDL
	private Context context;
	private SQLiteDatabase sqlData;
	private String sql;
	private ArrayList<String> arrNameAccount;
	private DebtItem debtItem;

	public DatabaseManager(Context context) {
		super();
		this.context = context;
		copyFileOn();
	}

	public DatabaseManager() {
		super();
		copyFileOn();
	}

	private void copyFileOn() {
		new File(DATA_PATH).mkdir();
		File fileOn = new File(DATA_PATH + DATA_FILE);
		if (fileOn.exists()) {
			return;
		}
		try {
			fileOn.createNewFile();
			AssetManager asset = this.context.getAssets();
			InputStream fileIn = asset.open(DATA_FILE);
			FileOutputStream fileOut = new FileOutputStream(fileOn);
			byte b[] = new byte[1024];
			int len = -1;
			while ((len = fileIn.read(b)) > 0) {
				fileOut.write(b);
			}
			fileIn.close();
			fileOut.close();
		} catch (Exception ex) {
			Log.e("Database_Error copyFileOn: ", ex.toString());
		}

	}

	void openDatabase() {
		if (sqlData == null || sqlData.isOpen() == false) {
			sqlData = SQLiteDatabase.openDatabase(DATA_PATH + DATA_FILE, null,
					SQLiteDatabase.OPEN_READWRITE);
		}
	}

	public void closeDatabase() {
		if (sqlData != null || sqlData.isOpen() == true) {
			sqlData.close();
		}
	}

	public boolean insertData(String tableName, ContentValues values) {
		// thêm vào bảng tableName bộ giá trị values
		openDatabase();
		boolean kt;
		long t = sqlData.insert(tableName, null, values);
		if (t != -1) {
			Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT)
					.show();
			Log.v("Them", "ThanhCong!");
			kt = true;
		} else {
			Toast.makeText(context, "Thêm KHÔNG thành công!",
					Toast.LENGTH_SHORT).show();
			Log.v("Them", "KHONG ThanhCong!");
			kt = false;
		}
		return kt;
	}

	public boolean updateData(String tableName, ContentValues values,
			String whereClause, String[] whereArg) {
		openDatabase();
		boolean kt;
		Log.i("updateData", "Cập nhật dữ liệu!");
		long index = sqlData.update(tableName, values, whereClause, whereArg);
		if (index != 0) {
			Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT)
					.show();
			kt = true;
		} else {
			Toast.makeText(context, "Cập nhật KHÔNG thành công!",
					Toast.LENGTH_SHORT).show();
			kt = false;
		}
		return kt;
	}

	public boolean deleteData(String tableName, String id) {
		return sqlData.delete(tableName, "Ma = '" + id + "'", null) > 0;

	}

	ArrayList<String> getArrayID(String tableName) {
		ArrayList<String> arr = new ArrayList<String>();
		openDatabase();
		Cursor cursor = sqlData.query(tableName, null, null, null, null, null,
				null);
		if (cursor == null)
			return null;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			arr.add(cursor.getString(cursor.getColumnIndex("Ma")));
			cursor.moveToNext();
		}
		cursor.close();
		return arr;
	}

	private void loadAllOfAccount() {
		openDatabase();
		Cursor cursor = sqlData.query(CommonVL.ACCOUNT_TABLE_NAME, null, null,
				null, null, null, null);
		if (cursor == null)
			return;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			arrNameAccount.add(cursor.getString(cursor.getColumnIndex("Ten")));
			cursor.moveToNext();
		}
		cursor.close();
	}

	public ArrayList<String> getArrayNameAccount() {

		arrNameAccount = new ArrayList<>();
		loadAllOfAccount();
		return arrNameAccount;
	}

	public ArrayList<String> getArrayNameCatalogue() {
		ArrayList<String> arrNameCatalogue = new ArrayList<>();
		openDatabase();
		Cursor cursor = sqlData.query(CommonVL.CATALOGUE_TABLE_NAME, null,
				null, null, null, null, null);
		if (cursor == null || cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			arrNameCatalogue.add(cursor.getString(cursor
					.getColumnIndex(CatalogueItem.KEY_NAME)));
			cursor.moveToNext();
		}
		cursor.close();
		return arrNameCatalogue;
	}

	public double countAllOfRow(String tableName) {
		sql = "SELECT COUNT(*) FROM " + tableName;
		openDatabase();
		double d;
		Cursor cursor = sqlData.rawQuery(sql, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
			d = cursor.getInt(0);
		} else {
			d = 0;
		}
		cursor.close();
		return d;
	}

	public String getIdByName(String tableName, String name) {
		// yeu cau dat ten khong giong nhau
		sql = "SELECT Ma FROM " + tableName + "WHERE Ten='" + name + "'";
		Cursor cursor = sqlData.rawQuery(sql, null);
		cursor.moveToFirst();
		String id = cursor.getString(0);
		Log.v("get ID", id + "");
		cursor.close();
		return id;
	}

	public Cursor executeSQLCommand(String sqlCommand) {
		return sqlData.rawQuery(sqlCommand, null);
	}

	//

	public String getNameByID(String tableName, String id) {
		openDatabase();
		sql = "SELECT Ten FROM " + tableName + " WHERE Ma = '" + id + "'";
		Cursor cursor = sqlData.rawQuery(sql, null);
		if (cursor == null || cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		String ten = cursor.getString(cursor.getColumnIndex("Ten"));
		cursor.close();
		return ten;
	}

	public ArrayList<ExpenseItem> getAllOfExpenseTable() {
		openDatabase();
		ArrayList<ExpenseItem> arr = new ArrayList<ExpenseItem>();
		String sql = "SELECT * FROM " + CommonVL.EXPENSE_TABLE_NAME
				+ " ORDER BY datetime(Ngay)";// DESC";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT)
					.show();
			return null;
		} else {
			c.moveToFirst();

			while (!c.isAfterLast()) {
				String ma = c.getString(c.getColumnIndex("Ma"));
				String datetime = c.getString(c.getColumnIndex("Ngay"));
				String cataloge = c.getString(c.getColumnIndex("MaDanhMuc"));
				String product = c.getString(c.getColumnIndex("TenSanPham"));
				Double money = c.getDouble(c.getColumnIndex("SoTien"));
				String description = c.getString(c.getColumnIndex("MoTa"));
				String from = c.getString(c.getColumnIndex("ChiTu"));

				ExpenseItem ex = new ExpenseItem(ma, datetime, cataloge,
						product, money, description, from);
				arr.add(ex);
				c.moveToNext();
			}
			c.close();
			return arr;
		}
	}

	public ArrayList<CollectingMoneyItem> getAllOfCollectingTable() {
		openDatabase();
		ArrayList<CollectingMoneyItem> arr = new ArrayList<CollectingMoneyItem>();
		String sql = "SELECT * FROM ThuNhap" + " ORDER BY datetime(Ngay)";// DESC";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT)
					.show();
			return null;
		} else {
			c.moveToFirst();

			while (!c.isAfterLast()) {
				String ma = c.getString(c.getColumnIndex("Ma"));
				String datetime = c.getString(c.getColumnIndex("Ngay"));
				String account = c.getString(c.getColumnIndex("MaTaiKhoan"));
				String from = c.getString(c.getColumnIndex("NguonThu"));
				Double money = c.getDouble(c.getColumnIndex("SoTien"));
				String description = c.getString(c.getColumnIndex("MoTa"));

				CollectingMoneyItem como = new CollectingMoneyItem(ma,
						datetime, from, account, description, money);
				arr.add(como);
				c.moveToNext();
			}
			c.close();
			Log.d("size of arr", arr.size() + "");
			return arr;
		}
	}

	public ArrayList<CatalogueItem> getAllOfCatalogueItem() {
		ArrayList<CatalogueItem> arr = new ArrayList<>();
		openDatabase();
		Cursor cursor = sqlData.query(CommonVL.CATALOGUE_TABLE_NAME, null,
				null, null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		try {
			while (!cursor.isAfterLast()) {
				CatalogueItem catalogue = new CatalogueItem(
						cursor.getString(cursor.getColumnIndex("Ma")),
						cursor.getString(cursor.getColumnIndex("Ten")),
						cursor.getString(cursor.getColumnIndex("MoTa")));
				arr.add(catalogue);
				cursor.moveToNext();
			}
		} catch (Exception ex) {
			Log.e("getAllOfCatalogueItem", ex.getMessage().toString());
		}
		cursor.close();
		return arr;
	}

	public ArrayList<AccountItem> getAllOfAccountItem() {
		ArrayList<AccountItem> arr = new ArrayList<>();
		openDatabase();
		Cursor cursor = sqlData.query(CommonVL.ACCOUNT_TABLE_NAME, null, null,
				null, null, null, null);
		if (cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AccountItem account = new AccountItem(cursor.getString(cursor
					.getColumnIndex("Ma")), cursor.getString(cursor
					.getColumnIndex("Ten")), cursor.getString(cursor
					.getColumnIndex("MoTa")));
			arr.add(account);
			cursor.moveToNext();
		}
		cursor.close();
		return arr;
	}

	public String[] getAllOfColumnName(String tableName) {
		String[] arrName = null;
		openDatabase();
		Cursor c = sqlData.query(tableName, null, null, null, null, null, null);
		arrName = c.getColumnNames();
		c.close();
		return arrName;
	}

	public int getCountOfColumn(String tableName) {
		int d = 0;
		openDatabase();
		Cursor c = sqlData.query(tableName, null, null, null, null, null, null);
		d = c.getColumnCount();
		c.close();
		return d;
	}

	public long getNumberOfLastId(String tableName) {
		long num = 0;
		String number = "";
		String lastId = null;
		openDatabase();
		Cursor c = sqlData.query(tableName, null, null, null, null, null, null);
		if (c.getCount() == 0)
			return 0;
		else {
			c.moveToLast();
			lastId = c.getString(c.getColumnIndex("Ma"));
			char[] charArr = lastId.toCharArray();

			for (char ch : charArr) {
				if (Character.isDigit(ch))
					number = number + ch;
			}
			num = Long.parseLong(number);
		}
		return num;
	}

	public ExpenseItem getExpenseItemById(String id) {
		openDatabase();
		ExpenseItem item = null;
		String sql = "SELECT *FROM " + CommonVL.EXPENSE_TABLE_NAME
				+ " WHERE Ma = '" + id + "'";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			return null;
		}
		c.moveToFirst();
		item = new ExpenseItem(id, c.getString(c
				.getColumnIndex(ExpenseItem.KEY_DATETIME)), c.getString(c
				.getColumnIndex(ExpenseItem.KEY_CATALOGUE)), c.getString(c
				.getColumnIndex(ExpenseItem.KEY_PRODUCT)), c.getDouble(c
				.getColumnIndex(ExpenseItem.KEY_MONEY)), c.getString(c
				.getColumnIndex(ExpenseItem.KEY_DESCRIPTION)), c.getString(c
				.getColumnIndex(ExpenseItem.KEY_ACCOUNT)));
		c.close();
		return item;
	}

	public CollectingMoneyItem getCollectingItemById(String id) {
		openDatabase();
		CollectingMoneyItem item = null;
		String sql = "SELECT *FROM " + CommonVL.COLLECTING_TABLE_NAME
				+ " WHERE Ma = '" + id + "'";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			return null;
		}
		c.moveToFirst();
		item = new CollectingMoneyItem(id, c.getString(c
				.getColumnIndex(CollectingMoneyItem.KEY_DATETIME)),
				c.getString(c.getColumnIndex(CollectingMoneyItem.KEY_FROM)),
				c.getString(c.getColumnIndex(CollectingMoneyItem.KEY_ACCOUNT)),
				c.getString(c
						.getColumnIndex(CollectingMoneyItem.KEY_DESCRIPTION)),
				c.getDouble(c.getColumnIndex(CollectingMoneyItem.KEY_MONEY)));
		c.close();
		return item;
	}

	public AccountItem getAccountItemById(String id) {
		openDatabase();
		AccountItem item = null;
		String sql = "SELECT *FROM " + CommonVL.ACCOUNT_TABLE_NAME
				+ " WHERE Ma = '" + id + "'";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			return null;
		}
		c.moveToFirst();
		item = new AccountItem(id, c.getString(c
				.getColumnIndex(AccountItem.KEY_NAME)), c.getString(c
				.getColumnIndex(AccountItem.KEY_DESCRIPTION)));
		c.close();
		return item;
	}

	public CatalogueItem getCatalogueItemById(String id) {
		openDatabase();
		CatalogueItem item = null;
		String sql = "SELECT *FROM " + CommonVL.CATALOGUE_TABLE_NAME
				+ " WHERE Ma = '" + id + "'";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			return null;
		}
		c.moveToFirst();
		item = new CatalogueItem(id, c.getString(c
				.getColumnIndex(AccountItem.KEY_NAME)), c.getString(c
				.getColumnIndex(AccountItem.KEY_DESCRIPTION)));
		c.close();
		return item;
	}

	public ArrayList<LoanItem> getAllOfLoanItem() {
		ArrayList<LoanItem> arr = new ArrayList<>();
		openDatabase();
		String sql = "SELECT * FROM " + CommonVL.LOAN_TABLE_NAME
				+ " ORDER BY datetime(Ngay)";// DESC";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			return null;
		} else {
			c.moveToFirst();

			while (!c.isAfterLast()) {
				String ma = c.getString(c.getColumnIndex("Ma"));
				String timeBegin = c.getString(c.getColumnIndex("Ngay"));
				String name = c.getString(c.getColumnIndex("Nguoi"));
				String timeEnd = c.getString(c.getColumnIndex("NgayHenTra"));
				Double money = c.getDouble(c.getColumnIndex("SoTien"));
				String description = c.getString(c.getColumnIndex("MoTa"));
				String from = c.getString(c.getColumnIndex("TuTaiKhoan"));
				String purpose = c.getString(c.getColumnIndex("MucDich"));
				Integer state = c.getInt(c.getColumnIndex("TrangThai"));
				String payed = c
						.getString(c.getColumnIndex(LoanItem.KEY_PAYED));
				LoanItem loan = new LoanItem(ma, name, timeBegin, timeEnd,
						purpose, from, money, description, state, payed);
				arr.add(loan);
				c.moveToNext();
			}
			c.close();
			return arr;
		}
	}

	public ArrayList<DebtItem> getAllOfDebtItem() {
		ArrayList<DebtItem> arr = new ArrayList<>();
		openDatabase();
		String sql = "SELECT * FROM " + CommonVL.DEBT_TABLE_NAME
				+ " ORDER BY datetime(Ngay)";// DESC";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT)
					.show();
			return null;
		} else {
			c.moveToFirst();

			while (!c.isAfterLast()) {
				String ma = c.getString(c.getColumnIndex(DebtItem.KEY_CODE));
				String timeBegin = c.getString(c
						.getColumnIndex(DebtItem.KEY_DATETIME_BEGIN));
				String name = c.getString(c.getColumnIndex(DebtItem.KEY_NAME));
				String timeEnd = c.getString(c
						.getColumnIndex(DebtItem.KEY_DATETIME_END));
				Double money = c
						.getDouble(c.getColumnIndex(DebtItem.KEY_MONEY));
				String description = c.getString(c
						.getColumnIndex(DebtItem.KEY_DESCRIPTION));
				String from = c.getString(c
						.getColumnIndex(DebtItem.KEY_TO_ACCOUNT));
				String purpose = c.getString(c
						.getColumnIndex(DebtItem.KEY_PURPOSE));
				Integer state = c.getInt(c.getColumnIndex(DebtItem.KEY_STATE));
				String payed = c
						.getString(c.getColumnIndex(DebtItem.KEY_PAYED));

				debtItem = new DebtItem(ma, name, timeBegin, timeEnd, purpose,
						from, money, description, state, payed);
				arr.add(debtItem);
				c.moveToNext();
			}
			c.close();
			return arr;
		}
	}

	public LoanItem getLoanItemById(String id) {
		openDatabase();
		LoanItem item = null;
		String sql = "SELECT *FROM " + CommonVL.LOAN_TABLE_NAME
				+ " WHERE Ma = '" + id + "'";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			Toast.makeText(context, "Không có dữ liệu", Toast.LENGTH_SHORT)
					.show();
			return null;
		}
		c.moveToFirst();
		item = new LoanItem(id,
				c.getString(c.getColumnIndex(LoanItem.KEY_NAME)), c.getString(c
						.getColumnIndex(LoanItem.KEY_DATETIME_BEGIN)),
				c.getString(c.getColumnIndex(LoanItem.KEY_DATETIME_END)),
				c.getString(c.getColumnIndex(LoanItem.KEY_PURPOSE)),
				c.getString(c.getColumnIndex(LoanItem.KEY_FROM_ACCOUNT)),
				c.getDouble(c.getColumnIndex(LoanItem.KEY_MONEY)),
				c.getString(c.getColumnIndex(LoanItem.KEY_DESCRIPTION)),
				c.getInt(c.getColumnIndex(LoanItem.KEY_STATE)), c.getString(c
						.getColumnIndex(LoanItem.KEY_DESCRIPTION)));
		c.close();
		return item;
	}

	public DebtItem getDebtItemById(String id) {
		openDatabase();
		String sql = "SELECT *FROM " + CommonVL.DEBT_TABLE_NAME
				+ " WHERE Ma = '" + id + "'";
		Cursor c = sqlData.rawQuery(sql, null);
		if (c.getCount() == 0) {
			return null;
		}
		c.moveToFirst();
		debtItem = new DebtItem(id, c.getString(c
				.getColumnIndex(DebtItem.KEY_NAME)), c.getString(c
				.getColumnIndex(DebtItem.KEY_DATETIME_BEGIN)), c.getString(c
				.getColumnIndex(DebtItem.KEY_DATETIME_END)), c.getString(c
				.getColumnIndex(DebtItem.KEY_PURPOSE)), c.getString(c
				.getColumnIndex(DebtItem.KEY_TO_ACCOUNT)), c.getDouble(c
				.getColumnIndex(DebtItem.KEY_MONEY)), c.getString(c
				.getColumnIndex(DebtItem.KEY_DESCRIPTION)), c.getInt(c
				.getColumnIndex(DebtItem.KEY_STATE)), c.getString(c
				.getColumnIndex(DebtItem.KEY_PAYED)));
		c.close();
		return debtItem;
	}

//	public Double totalMoneyColect() {
//		// TO-DO tinh tong theo ngay thang của bang ThuNhap
//		openDatabase();
//		double total;
//			sql = "SELECT SUM(" + CollectingMoneyItem.KEY_MONEY + ") FROM "
//					+ CommonVL.COLLECTING_TABLE_NAME;
//			Cursor c = sqlData.rawQuery(sql, null);
//			c.moveToFirst();
//			total=c.getDouble(0);
//			c.close();
//			return total;
//	}
//	public Double totalMoneyExpense(){
//		openDatabase();
//		double total;
//			sql = "SELECT SUM(" + ExpenseItem.KEY_MONEY + ") FROM "
//					+ CommonVL.EXPENSE_TABLE_NAME;
//			Cursor c = sqlData.rawQuery(sql, null);
//			c.moveToFirst();
//			total=c.getDouble(0);
//			c.close();
//			return total;
//	}
	
	public Double totalMoneyLoanNotPay(){
		openDatabase();
		double total;
			sql = "SELECT SUM(" + LoanItem.KEY_MONEY + ") FROM "
					+ CommonVL.LOAN_TABLE_NAME
					+ " WHERE "+LoanItem.KEY_STATE+" = '0'";
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
			total=c.getDouble(0);
			c.close();
			return total;
	}
	public Double totalMoneyLoanPayed(){
		openDatabase();
		double total;
			sql = "SELECT SUM(" + LoanItem.KEY_MONEY + ") FROM "
					+ CommonVL.LOAN_TABLE_NAME
					+ " WHERE "+LoanItem.KEY_STATE+" = '1'";
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
			total=c.getDouble(0);
			c.close();
			return total;
	}
	public Double totalMoneyDebtNotPay(){
		openDatabase();
		double total;
			sql = "SELECT SUM(" + DebtItem.KEY_MONEY + ") FROM "
					+ CommonVL.DEBT_TABLE_NAME
					+ " WHERE "+DebtItem.KEY_STATE+" = '0'";
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
			total=c.getDouble(0);
			c.close();
			return total;
	}
	public Double totalMoneyDebtPayed(){
		openDatabase();
		double total;
			sql = "SELECT SUM(" + DebtItem.KEY_MONEY + ") FROM "
					+ CommonVL.DEBT_TABLE_NAME
					+ " WHERE "+DebtItem.KEY_STATE+" = '1'";
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
			total=c.getDouble(0);
			c.close();
			return total;
	}

	public ArrayList<Double> listExpenseWithCatalogue(String nameMonth, String nameYear) {
		 ArrayList<Double> list = new ArrayList<Double>();
		ArrayList<CatalogueItem> arrCata = getAllOfCatalogueItem();
		openDatabase();
		String month;
		if(nameMonth==null || nameMonth.equals("null")){
			int monthNum=Integer.parseInt(nameMonth.substring(nameMonth.indexOf(" ") + 1));
			month = monthNum>=10?monthNum+"":"0"+monthNum;
			String year = nameYear.substring(nameYear.indexOf(" ") + 1);
			for (int i = 0; i < arrCata.size(); i++) {
				sql = "SELECT SUM(" + ExpenseItem.KEY_MONEY + ") FROM "
						+ CommonVL.EXPENSE_TABLE_NAME + " WHERE "
						+ ExpenseItem.KEY_CATALOGUE + " = '"
						+ arrCata.get(i).getId() + "'" + " AND strftime('%Y'," + ExpenseItem.KEY_DATETIME + ")= '"
						+ year + "'";

				Log.v("sqlString",sql );
				Cursor c = sqlData.rawQuery(sql, null);
				c.moveToFirst();
//				Log.v("SUM",c.getDouble(0)+"" );
				list.add(c.getDouble(0));
				c.close();
			}
			// cacs khoan vay nợ đã trả
			String sql1 = "SELECT SUM(" + DebtItem.KEY_MONEY + ") FROM "
					+ CommonVL.DEBT_TABLE_NAME + " WHERE " + DebtItem.KEY_STATE
					+ " = '1'" + " AND strftime('%Y'," + DebtItem.KEY_PAYED + ")= '"
					+ year + "'";
			Cursor c = sqlData.rawQuery(sql1, null);
			c.moveToFirst();
			list.add( c.getDouble(0));
			c.close();
			// cac khoan cho vay chưa trả
			String sql2 = "SELECT SUM(" + LoanItem.KEY_MONEY + ") FROM "
					+ CommonVL.LOAN_TABLE_NAME + " WHERE " + LoanItem.KEY_STATE
					+ " = '0'" + " AND strftime('%Y'," + LoanItem.KEY_DATETIME_BEGIN + ")= '"
					+ year + "'";
			Cursor c1 = sqlData.rawQuery(sql2, null);
			c1.moveToFirst();
			list.add(c1.getDouble(0));
			c1.close();
//			for (Double i : list) {
//				Log.i("totalMoneyColect", i.toString());
//			}
		}
		else{
		int monthNum=Integer.parseInt(nameMonth.substring(nameMonth.indexOf(" ") + 1));
		month = monthNum>=10?monthNum+"":"0"+monthNum;
		String year = nameYear.substring(nameYear.indexOf(" ") + 1);
		for (int i = 0; i < arrCata.size(); i++) {
			sql = "SELECT SUM(" + ExpenseItem.KEY_MONEY + ") FROM "
					+ CommonVL.EXPENSE_TABLE_NAME + " WHERE "
					+ ExpenseItem.KEY_CATALOGUE + " = '"
					+ arrCata.get(i).getId() + "'" + " AND strftime('%m',"
					+ ExpenseItem.KEY_DATETIME + ")= '" + month + "'"
					+ " AND strftime('%Y'," + ExpenseItem.KEY_DATETIME + ")= '"
					+ year + "'";

//			Log.v("sqlString",sql );
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
			Log.v("SUM",c.getDouble(0)+"" );
			list.add(c.getDouble(0));
			c.close();
		}
		// cacs khoan vay nợ đã trả
		String sql1 = "SELECT SUM(" + DebtItem.KEY_MONEY + ") FROM "
				+ CommonVL.DEBT_TABLE_NAME + " WHERE " + DebtItem.KEY_STATE
				+ " = '1'" + " AND strftime('%m',"
				+ DebtItem.KEY_PAYED + ")= '" + month + "'"
				+ " AND strftime('%Y'," + DebtItem.KEY_PAYED + ")= '"
				+ year + "'";
		Cursor c = sqlData.rawQuery(sql1, null);
		c.moveToFirst();
		list.add( c.getDouble(0));
		c.close();
		// cac khoan cho vay chưa trả
		String sql2 = "SELECT SUM(" + LoanItem.KEY_MONEY + ") FROM "
				+ CommonVL.LOAN_TABLE_NAME + " WHERE " + LoanItem.KEY_STATE
				+ " = '0'" + " AND strftime('%m',"
				+ LoanItem.KEY_DATETIME_BEGIN + ")= '" + month + "'"
				+ " AND strftime('%Y'," + LoanItem.KEY_DATETIME_BEGIN + ")= '"
				+ year + "'";
		Cursor c1 = sqlData.rawQuery(sql2, null);
		c1.moveToFirst();
		list.add(c1.getDouble(0));
		c1.close();
//		for (Double i : list) {
//			Log.i("totalMoneyColect", i.toString());
//		}
		}
		return list;
	}
	//dung cho expense và collect, debt và loan k dùng đc
	public Double sumMoneyByMonthYear(String nameTable,String nameMonth,String nameYear){
		double sum=0;
		openDatabase();
		if(nameMonth==null && nameYear!=null){
			String year = nameYear.substring(nameYear.indexOf(" ") + 1);
			sql = "SELECT SUM(" + "SoTien" + ") FROM "
					+ nameTable + " WHERE "
					+ " strftime('%Y'," + "Ngay" + ")= '"
					+ year + "'";

			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
//			Log.v("SUM",c.getDouble(0)+"" );
			sum=c.getDouble(0);
			c.close();
		}
		if(nameMonth!=null && nameYear==null){
			int monthNum=Integer.parseInt(nameMonth.substring(nameMonth.indexOf(" ") + 1));
			String month = monthNum>=10?monthNum+"":"0"+monthNum;
			sql = "SELECT SUM( SoTien ) FROM "
					+ nameTable + " WHERE "
					+ " strftime('%m',Ngay)= '" + month + "'";
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
//			Log.v("SUM",c.getDouble(0)+"" );
			sum=c.getDouble(0);
			c.close();
		}
		if(nameYear==null && nameMonth==null ){
			sql = "SELECT SUM(SoTien) FROM "
					+ nameTable ;
			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
//			Log.v("SUM",c.getDouble(0)+"" );
			sum=c.getDouble(0);
			c.close();
		}
		else{
		int monthNum=Integer.parseInt(nameMonth.substring(nameMonth.indexOf(" ") + 1));
		String month = monthNum>=10?monthNum+"":"0"+monthNum;
		String year = nameYear.substring(nameYear.indexOf(" ") + 1);
			sql = "SELECT SUM(" + CollectingMoneyItem.KEY_MONEY + ") FROM "
					+ CommonVL.COLLECTING_TABLE_NAME + " WHERE "
					+ " strftime('%m',"
					+ CollectingMoneyItem.KEY_DATETIME + ")= '" + month + "'"
					+ " AND strftime('%Y'," + CollectingMoneyItem.KEY_DATETIME + ")= '"
					+ year + "'";

			Cursor c = sqlData.rawQuery(sql, null);
			c.moveToFirst();
//			Log.v("SUM",c.getDouble(0)+"" );
			sum=c.getDouble(0);
			c.close();
		}
		return sum;
	}
	
	public boolean isExistOfValue(String tableName,String columnName,String value){
		openDatabase();
		sql=("SELECT *FROM "+tableName+" WHERE "+columnName+" = '"+value+"'");
		Cursor c= sqlData.rawQuery(sql, null);
		if(c.getCount()==0){
			c.close();
			return false;
		}
		else {
			c.close();
			return true;
		}
	}
	
}
