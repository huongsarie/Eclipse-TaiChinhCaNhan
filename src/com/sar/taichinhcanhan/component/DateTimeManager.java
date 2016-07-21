package com.sar.taichinhcanhan.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class DateTimeManager {
	// THEM CODE CLASS MOI 16/8/15
	private String dateTimeChoosed, dateTimeNow, timeChoosed, dateChoosed;
	private Calendar currentDateTime;
	private Calendar dateAndTime, dateAndTimeSQL;
	private EditText etTime, etDate;
	private SimpleDateFormat simpleFormat = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm");
	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat format3 = new SimpleDateFormat("HH:mm dd-MM-yyyy");
	private String dateTimeFromSql;
	private Context context;
	private TimePickerDialog.OnTimeSetListener onTimePickerDialog = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			setTimeChoosed(simpleFormat.format(dateAndTime.getTime()));
			Log.e("setTimeChoosed", simpleFormat.format(dateAndTime.getTime())
					+ "\n" + getTimeChoosed());
			setTextTime(getTimeChoosed());
		}
	};
	private DatePickerDialog.OnDateSetListener onDatePickerDialog = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			setDateChoosed(simpleFormat.format(dateAndTime.getTime()));
			Log.e("setDateChoosed", simpleFormat.format(dateAndTime.getTime())
					+ "\n" + getDateChoosed());
			setTextDate(getDateChoosed());
		}
	};
	private TimePickerDialog.OnTimeSetListener onTimePickerDialog2 = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			dateAndTimeSQL.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTimeSQL.set(Calendar.MINUTE, minute);
			setTimeChoosed(simpleFormat.format(dateAndTimeSQL.getTime()));
			setTextTime(getTimeChoosed());
		}
	};
	private DatePickerDialog.OnDateSetListener onDatePickerDialog2 = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			dateAndTimeSQL.set(Calendar.YEAR, year);
			dateAndTimeSQL.set(Calendar.MONTH, monthOfYear);
			dateAndTimeSQL.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			setDateChoosed(simpleFormat.format(dateAndTimeSQL.getTime()));
			Log.e("setDateChoosedSQL",
					simpleFormat.format(dateAndTimeSQL.getTime()) + "\n"
							+ getDateChoosed());
			setTextDate(getDateChoosed());
		}
	};

	public DateTimeManager(Context context, EditText etTime, EditText etDate) {
		this.context = context;
		this.etTime = etTime;
		this.etDate = etDate;
		init();
	}

	public DateTimeManager(Context context, EditText etTime, EditText etDate,
			String dateTimeFromSql) {
		this.context = context;
		this.etTime = etTime;
		this.etDate = etDate;
		this.dateTimeFromSql = dateTimeFromSql;
		initWithPreValue();
	}
	
	public DateTimeManager(String dateTimeFromSql){
		this.dateTimeFromSql = dateTimeFromSql;
		initWithPreValue();
	}

	private void init() {
		currentDateTime = Calendar.getInstance();
		dateAndTime = Calendar.getInstance();
		dateTimeNow = simpleFormat.format(currentDateTime.getTime()).toString();
		timeChoosed = dateTimeNow.substring(dateTimeNow.lastIndexOf(" ") + 1);
		dateChoosed = dateTimeNow.substring(0, dateTimeNow.lastIndexOf(" "));
		//dateTimeChoosed = dateChoosed + " " + timeChoosed;
	}
	
	private void initWithPreValue(){
		dateAndTimeSQL = Calendar.getInstance();
		setDateTimeFromSQLToSQLFormat(dateTimeFromSql);
	}

	public void showTimePickerDialog() {
		new TimePickerDialog(context, onTimePickerDialog,
				dateAndTime.get(Calendar.HOUR_OF_DAY),
				dateAndTime.get(Calendar.MINUTE), true).show();
	}

	public void showDatePickerDialog() {
		new DatePickerDialog(context, onDatePickerDialog,
				dateAndTime.get(Calendar.YEAR),
				dateAndTime.get(Calendar.MONTH),
				dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
	}

	public void showTimePickerDialogWithValue() {
		new TimePickerDialog(context, onTimePickerDialog2,
				dateAndTimeSQL.get(Calendar.HOUR_OF_DAY),
				dateAndTimeSQL.get(Calendar.MINUTE), true).show();
	}

	public void showDatePickerDialogWithValue() {
		new DatePickerDialog(context, onDatePickerDialog2,
				dateAndTimeSQL.get(Calendar.YEAR),
				dateAndTimeSQL.get(Calendar.MONTH),
				dateAndTimeSQL.get(Calendar.DAY_OF_MONTH)).show();
	}

	String getCurrentDateTime() {

		return dateTimeNow;
	}

	public String getTimeNow() {
		return dateTimeNow.substring(dateTimeNow.lastIndexOf(" ") + 1);
	}

	public String getDateNow() {
		return dateTimeNow.substring(0, dateTimeNow.lastIndexOf(" "));
	}

	public String getTimeChoosed() {
		return timeChoosed;
	}

	String getDateChoosed() {
		return dateChoosed;
	}

	public String getDateTimeChoosed() {
		return dateTimeChoosed;
	}

	void setTimeChoosed(String dateTimeChoosed) {
		this.timeChoosed = dateTimeChoosed.substring(dateTimeChoosed
				.lastIndexOf(" ") + 1);
	}

	void setDateChoosed(String dateTimeChoosed) {
		this.dateChoosed = dateTimeChoosed.substring(0,
				dateTimeChoosed.lastIndexOf(" "));
	}

	public void setDateTimeChoosed(String dateTimeChoosed) {
		this.dateTimeChoosed = dateTimeChoosed;
	}

	void setTextTime(String timeChoosed) {
		etTime.setText(timeChoosed);
	}

	void setTextDate(String dateChoosed) {
		etDate.setText(dateChoosed);
	}

	public String getDateTimeFormatSQL() {
		Log.i("test format of Calendar: ",
				format2.format(dateAndTime.getTime()));
		return format2.format(dateAndTime.getTime());
	}
	
	public String getDateTimeFormatSQLFromSQL() {
		return format2.format(dateAndTimeSQL.getTime());
	}
	
	public String getDateTimeFromSQLToShow() {
		return format3.format(dateAndTimeSQL.getTime());
	}

	void setDateTimeFromSQLToSQLFormat(String stringDT) {
		dateAndTimeSQL = Calendar.getInstance();
		try {
			dateAndTimeSQL.setTime(format2.parse(stringDT));
		} catch (ParseException e) {
			Log.e("error - getDateTimeFromSQL ", e.getMessage());
		}
	}

	public String getTimeSQLString() {
		String string = simpleFormat.format(dateAndTimeSQL.getTime());
		return string.substring(string.lastIndexOf(" ") + 1);
	}

	public String getDateSQLString() {
		String string = simpleFormat.format(dateAndTimeSQL.getTime());
		return string.substring(0, string.lastIndexOf(" "));
	}
}
