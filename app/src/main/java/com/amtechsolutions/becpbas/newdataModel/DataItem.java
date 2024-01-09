package com.amtechsolutions.becpbas.newdataModel;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("taDeviceOperatingSystem")
	private String taDeviceOperatingSystem;

	@SerializedName("taEmpCode")
	private String taEmpCode;

	@SerializedName("taTxnCompKey")
	private int taTxnCompKey;

	@SerializedName("taStatus")
	private String taStatus;

	@SerializedName("taTime")
	private String taTime;

	@SerializedName("taDeviceName")
	private String taDeviceName;

	@SerializedName("taErrorDesc")
	private String taErrorDesc;

	@SerializedName("taEmployeeImageTemp")
	private String taEmployeeImageTemp;

	@SerializedName("taSyncStatus")
	private String taSyncStatus;

	@SerializedName("taSource")
	private String taSource;

	@SerializedName("taGeoLangtitude")
	private String taGeoLangtitude;

	@SerializedName("taGeoLocationName")
	private String taGeoLocationName;

	@SerializedName("taAttendIndicator")
	private String taAttendIndicator;

	@SerializedName("taTxnDate")
	private String taTxnDate;

	@SerializedName("taGeoLatitude")
	private String taGeoLatitude;

	@SerializedName("taAutoManual")
	private String taAutoManual;

	@SerializedName("createdUser")
	private int createdUser;

	public void setTaDeviceOperatingSystem(String taDeviceOperatingSystem){
		this.taDeviceOperatingSystem = taDeviceOperatingSystem;
	}

	public String getTaDeviceOperatingSystem(){
		return taDeviceOperatingSystem;
	}

	public void setTaEmpCode(String taEmpCode){
		this.taEmpCode = taEmpCode;
	}

	public String getTaEmpCode(){
		return taEmpCode;
	}

	public void setTaTxnCompKey(int taTxnCompKey){
		this.taTxnCompKey = taTxnCompKey;
	}

	public int getTaTxnCompKey(){
		return taTxnCompKey;
	}

	public void setTaStatus(String taStatus){
		this.taStatus = taStatus;
	}

	public String getTaStatus(){
		return taStatus;
	}

	public void setTaTime(String taTime){
		this.taTime = taTime;
	}

	public String getTaTime(){
		return taTime;
	}

	public void setTaDeviceName(String taDeviceName){
		this.taDeviceName = taDeviceName;
	}

	public String getTaDeviceName(){
		return taDeviceName;
	}

	public void setTaErrorDesc(String taErrorDesc){
		this.taErrorDesc = taErrorDesc;
	}

	public String getTaErrorDesc(){
		return taErrorDesc;
	}

	public void setTaEmployeeImageTemp(String taEmployeeImageTemp){
		this.taEmployeeImageTemp = taEmployeeImageTemp;
	}

	public String getTaEmployeeImageTemp(){
		return taEmployeeImageTemp;
	}

	public void setTaSyncStatus(String taSyncStatus){
		this.taSyncStatus = taSyncStatus;
	}

	public String getTaSyncStatus(){
		return taSyncStatus;
	}

	public void setTaSource(String taSource){
		this.taSource = taSource;
	}

	public String getTaSource(){
		return taSource;
	}

	public void setTaGeoLangtitude(String taGeoLangtitude){
		this.taGeoLangtitude = taGeoLangtitude;
	}

	public String getTaGeoLangtitude(){
		return taGeoLangtitude;
	}

	public void setTaGeoLocationName(String taGeoLocationName){
		this.taGeoLocationName = taGeoLocationName;
	}

	public String getTaGeoLocationName(){
		return taGeoLocationName;
	}

	public void setTaAttendIndicator(String taAttendIndicator){
		this.taAttendIndicator = taAttendIndicator;
	}

	public String getTaAttendIndicator(){
		return taAttendIndicator;
	}

	public void setTaTxnDate(String taTxnDate){
		this.taTxnDate = taTxnDate;
	}

	public String getTaTxnDate(){
		return taTxnDate;
	}

	public void setTaGeoLatitude(String taGeoLatitude){
		this.taGeoLatitude = taGeoLatitude;
	}

	public String getTaGeoLatitude(){
		return taGeoLatitude;
	}

	public void setTaAutoManual(String taAutoManual){
		this.taAutoManual = taAutoManual;
	}

	public String getTaAutoManual(){
		return taAutoManual;
	}

	public void setCreatedUser(int createdUser){
		this.createdUser = createdUser;
	}

	public int getCreatedUser(){
		return createdUser;
	}
}