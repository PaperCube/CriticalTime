package studio.papercube.criticaltime.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class Settings {
	private static SettingsProvider sp = new SettingsProvider();
	
	public static String get(SettingItem item){
		return sp.getProperty(item.name, item.defaultValue);
	}
	
	public static boolean getBoolean(SettingItem item){
		return Boolean.parseBoolean(get(item));
	}
	
	public static int getInt(SettingItem item){
		return Integer.parseInt(get(item));
		
	}
	
	public static double getDouble(SettingItem item){
		return Double.parseDouble(get(item));
	}
	
	public static <T> void set(SettingItem item,T value){
		sp.setProperty(item.name, String.valueOf(value));
		sp.save();
	}
	
	
	
	
	@SuppressWarnings("serial")
	private static class SettingsProvider extends Properties{
		File f = new File("/Config.prop");
		public SettingsProvider() {
			super();
			try {
				
				if(!f.exists()) f.createNewFile();
				FileInputStream fis = new FileInputStream(f);
				load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public void save(){
			FileWriter fos;
			try {
				fos = new FileWriter(f);
				store(fos, "last edited at "+ LocalDate.now());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
