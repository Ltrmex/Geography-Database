package com.geog.Controlller;

import java.sql.SQLException;
import java.util.ArrayList;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.geog.DAO.DAO;
import com.geog.Model.Country;

@ManagedBean
@SessionScoped
public class CountryController {
	private ArrayList<Country> countries;
	private DAO dao;
	private Country c;
	
	public CountryController(){
		c = new Country();
		countries = new ArrayList<Country>();
		try {
			this.dao = new DAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Country getC() {
		return c;
	}


	public void setC(Country c) {
		this.c = c;
	}


	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void loadCountries() throws ClassNotFoundException, SQLException {
		countries = dao.getCountriesData();
	}
	  
	public String setUpdate(Country c){
		setC(c);
		return "updateCountry.xhtml";
	}
	
	public String processUpdate(String countryCode, String countryName, String countryDetails){
		dao.updateCountry(countryCode, countryName, countryDetails);
		
		return dao.getRedirect();
	}
	
	public void processDelete(String countryCode){
		dao.deleteCountry(countryCode);
	}
	
	public String processNewCountry(String countryCode, String countryName, String countryDetails) {
			dao.addNewCountry(countryCode, countryName, countryDetails);
			
			return dao.getRedirect();
	}
}

