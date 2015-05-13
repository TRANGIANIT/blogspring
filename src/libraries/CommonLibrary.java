package libraries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonLibrary {
	public static String getSlugByURL(String url) {
		String slug = null;
		String s[] = url.split("/");
		if (s.length > 0) {
			slug = s[s.length - 1];
		}
		return slug;
	}

	public static String getPageByURL(String url) {
		String page = null;
		String s[] = url.split("/");
		if (s.length > 2) {
			page = s[2];
		}
		return page;
	}

	public static String timePrinter(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//Lay ngay thang nam theo dinh dang
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'Tháng' M',' yyyy");
		String timer = dateFormat.format(date).toString();
		//Lay gio va phut
		timer+=" lúc "+cal.get(Calendar.HOUR_OF_DAY);
		timer+=":"+cal.get(Calendar.MINUTE);
		return timer;
	}
	
	public static String timePrinterForFilter(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//Lay ngay thang nam theo dinh dang
		SimpleDateFormat dateFormat = new SimpleDateFormat("'Tháng' M',' yyyy");
		String timer = dateFormat.format(date).toString();
		return timer;
	}
	
	public static String printMonthAndYearOnly(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMyyyy");
		return dateFormat.format(date).toString();
	}
	
	public static void main(String[] args) {
	}
	
	
}
