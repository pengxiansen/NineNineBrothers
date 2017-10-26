package com.messoft.gzmy.nineninebrothers.view.cityselectpop;

import java.util.List;

public class ProvinceModel {
	private String name;
	private List<CityModel> cityList;
	private String provinceID;

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String name, List<CityModel> cityList) {
		super();
		this.name = name;
		this.cityList = cityList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {

		return "ProvinceModel [name=" + name + ", provinceID="+provinceID+", cityList=" + cityList + "]";
	}
	
}
