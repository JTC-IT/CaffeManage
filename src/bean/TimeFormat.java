package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormat {
	private static Calendar calendar = Calendar.getInstance();
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
	
	public static Date parse(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date date) {
		return sdf.format(date);
	}
	
	public static Date getDate() {
		return new Date();
	}
	
	public static int getCurMonth() {
		return calendar.get(Calendar.MONTH)+1;
	}
	
	public static int getCurYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	public static Date getFirstDayOfMonth() {
		Date date = new Date();
		date = parse("01/"+getCurMonth()+"/"+getCurYear()+" 00:01 AM");
		return date;
	}
}
