package studio.papercube.criticaltime.application;

public enum SettingItem {
	
	version("Demo");
	
	public String defaultValue;
	public String name;
	
	private SettingItem(){
		this.defaultValue = "";
		name = name();
	}
	
	private <T> SettingItem(T defaultval){
		this.defaultValue = String.valueOf(defaultval);
		name = name();
	}
	
	
}
