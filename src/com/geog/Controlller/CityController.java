package com.geog.Controlller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.geog.DAO.CityDAO;
import com.geog.Model.City;

@ManagedBean
@SessionScoped
public class CityController {
	private ArrayList<City> cities;
	private CityDAO dao;
	private City c;
	
	public CityController(){
		c = new City();
		cities = new ArrayList<City>();
		try {
			this.dao = new CityDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public City getC() {
		return c;
	}


	public void setC(City c) {
		this.c = c;
	}
	
	public String getCountryName(String cityCode) {
		return dao.dataFromAnotherTable(cityCode, "co_name", "co_code", "country");
	}
	
	public String getRegionName(String cityCode) {
		return dao.dataFromAnotherTable(cityCode, "reg_name", "reg_code", "region");
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}

	public void loadCities() throws ClassNotFoundException, SQLException {
		cities = dao.getCitiesData();
	}
	
	public String processNewCity(String cityCode, String countryCode, String regionCode, String cityName, int population, String byTheSea, double area) {
		dao.addNewCity(cityCode, countryCode, regionCode, cityName, population, byTheSea, area);
		
		return dao.getRedirect();
	}
	
	public String allCityDetails(City c){
		setC(c);
		return "fullCityDetail.xhtml";
	}
}
