package com.amtechsolutions.becpbas.newdataModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SuperData{

	@SerializedName("data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}
}