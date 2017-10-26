package com.messoft.gzmy.nineninebrothers.view.cityselectpop;

import java.util.List;

public class CityModel {
	private String name;
	private String cityID;

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	private List<DistrictModel> districtList;
	
	public CityModel() {
		super();
	}

	public CityModel(String name, List<DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ",cityID="+cityID+", districtList=" + districtList
				+ "]";
	}
	
}
