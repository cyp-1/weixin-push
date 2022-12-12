package usi.weixinpush.util;

import java.io.IOException;
import java.io.InputStream;
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
		System.out.println(getValue("APP_SECRET"));
	}
}
