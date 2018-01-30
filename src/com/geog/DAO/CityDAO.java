package com.geog.DAO;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;

import com.geog.Model.City;
import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CityDAO {
	private DataSource mysqlDS;
	private String redirect;

	public CityDAO() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/geography";
		mysqlDS = (DataSource) context.lookup(jndiName);
		this.redirect = "";
	}
	
	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
	public ArrayList<City> getCitiesData(){
		ArrayList<City> cities = new ArrayList<City>();
		
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			
			String query = "select * from city;";
			
			ResultSet rs = myStmt.executeQuery(query);
			
			while( rs.next() ) {
	
				String cityCode = rs.getString("cty_code");
				String countryCode = rs.getString("co_code");
				String regionCode = rs.getString("reg_code");
				String cityName = rs.getString("cty_name");
				int population = rs.getInt("population");
				String byTheSea = rs.getString("isCoastal");
				double area = rs.getDouble("areaKM");
				
				City c = new City();
				c.setCityCode(cityCode);
				c.setCountryCode(countryCode);
				c.setRegionCode(regionCode);
				c.setCityName(cityName);
				c.setPopulation(population);
				c.setByTheSea(byTheSea);
				c.setArea(area);
				
				cities.add(c);
			}
			
			conn.close();
			myStmt.close();
			rs.close();
		
		}catch( SQLException se ) {
            System.out.println(se.getMessage());
		}
		
		return cities;
	}
	
	public void addNewCity(String cityCode, String countryCode, String regionCode, String cityName, int population, String byTheSea, double area){
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			setRedirect("");
			String query = "insert into city values(" +
				"'" + cityCode + "', " +
				"'" + countryCode + "', " +
				"'" + regionCode + "', " +
				"'" + cityName + "', " +
				"'" + population + "', " +
				"'" + byTheSea + "', " +
				"'" + area + "');";
			
			myStmt.executeUpdate(query);
			
			conn.close();
			myStmt.close();
			
			setRedirect("list_cities.xhtml");
		
		}catch( SQLIntegrityConstraintViolationException  rp) {
			FacesMessage message = new FacesMessage("Error: Attempting to add City: " + cityCode + ", Region: " + regionCode + " and City: " + cityCode);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(MySQLIntegrityConstraintViolationException rs){
			FacesMessage message = new FacesMessage("Error: Attempting to add City: " + cityCode + ", Region: " + regionCode + " and City: " + cityCode);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( CommunicationsException ce){
			FacesMessage message = new FacesMessage("The Database is offline/or the communication link down");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( SQLException se) {
	        System.out.println(se.getMessage());
		}

	}
	
	public String dataFromAnotherTable(String cityCode, String name, String code, String table) {
		String output = "";
		
		try {
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			
			String query = "select " + name + " from " + table + " where '" + code + "' = "
					+ "(select '" + code + "' from city where cty_code = '" + cityCode +"');";
			
			ResultSet rs = myStmt.executeQuery(query);
			
			while( rs.next() ) {
				output = rs.getString(1);
			}
			
			conn.close();
			myStmt.close();
			rs.close();
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return output;
	}
	
	public ArrayList<City> searchCities(String findPopulation,int findNoPopulation, String findCode, String findCoastal ) {
		ArrayList<City> foundCities = new ArrayList<City>();
		
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			
			String query = "select * from city;";
			
			if(findCode.length() > 2){
				query = "select * from city WHERE population " + findPopulation + " " +findNoPopulation + 
				" AND co_code = '" + findCode + "' AND isCoastal = '" + findCoastal + "';";
			}
			else{
				query = "select * from city WHERE population " + findPopulation + " " + findNoPopulation +  
				" AND isCoastal = '" + findCoastal + "';";
			}
			
			ResultSet rs = myStmt.executeQuery(query);
			
			while( rs.next() ) {
	
				String cityCode = rs.getString("cty_code");
				String countryCode = rs.getString("co_code");
				String regionCode = rs.getString("reg_code");
				String cityName = rs.getString("cty_name");
				int population = rs.getInt("population");
				String byTheSea = rs.getString("isCoastal");
				double area = rs.getDouble("areaKM");
				
				City c = new City();
				c.setCityCode(cityCode);
				c.setCountryCode(countryCode);
				c.setRegionCode(regionCode);
				c.setCityName(cityName);
				c.setPopulation(population);
				c.setByTheSea(byTheSea);
				c.setArea(area);
				
				foundCities.add(c);
			}
			
			conn.close();
			myStmt.close();
			rs.close();
		
		}catch( SQLException se ) {
            System.out.println(se.getMessage());
		}
		
		return foundCities;
	}

}


