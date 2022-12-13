package usi.weixinpush.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public class ConfigUtil {
	//初始化配置文件
	private static Properties pro = new Properties();

	static{
		ClassLoader loader = ConfigUtil.class.getClassLoader();
		InputStream ips= loader.getResourceAsStream("config.properties");
		try {
			pro.load(ips);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ips!=null){
					ips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	//获取配置文件的中配置的值
	public static String getValue(String key){
		return pro.getProperty(key);
	}

	//根据类名获取类对象
	public static Object getInstance(String className){
		Object obj = null;
		try {
			obj = Class.forName(getValue(className)).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void main(String[] args) {
//		System.out.println(getValue("APP_SECRET"));
		System.out.println(getBirthDay("2000-12-17"));
	}

	public static int getBirthDay(String addtime) {
		int days = 0;
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String clidate = addtime;
			Calendar cToday = Calendar.getInstance(); // 存今天
			Calendar cBirth = Calendar.getInstance(); // 存生日
			cBirth.setTime(myFormatter.parse(clidate)); // 设置生日
			cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR)); // 修改为本年
			if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
				// 生日已经过了，要算明年的了
				days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
				days += cBirth.get(Calendar.DAY_OF_YEAR);
			} else {
				// 生日还没过
				days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

}
