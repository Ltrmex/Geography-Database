package com.geog.Controlller;

import java.sql.SQLException;
import java.util.ArrayList;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.geog.DAO.CityDAO;
import com.geog.Model.City;

@ManagedBean
@SessionScoped
public class FindAllCitiesController {
	private ArrayList<City> foundCities;
	private CityDAO dao;
	private City c;
	
	public FindAllCitiesController(){
		foundCities = new ArrayList<City>();
		c = new City();
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

	public ArrayList<City> getFoundCities() {
		return foundCities;
	}

	public void loadCities() throws ClassNotFoundException, SQLException {
		foundCities = dao.searchCities(c.getCityCode(), c.getPopulation(),c.getCountryCode(), c.getByTheSea());
	}
	
	public String setCities(String findPopulation,int findNoPopulation, String findCode, String findCoastal){
		if(findPopulation.equals("Greater Than"))
			findPopulation = ">";
		else if(findPopulation.equals("Less Than"))
			findPopulation = "<";
		else if(findPopulation.equals("Equal To"))
			findPopulation = "=";
		
		c.setCityCode(findPopulation);
		c.setPopulation(findNoPopulation);
		c.setByTheSea(findCoastal);
		c.setCountryCode(findCode);
		
		return "foundCities.xhtml";
		
	}
	
}

