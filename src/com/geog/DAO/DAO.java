package com.geog.DAO;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;

import com.geog.Model.Country;
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

public class DAO {
	private DataSource mysqlDS;
	private String redirect;
	
	public DAO() throws Exception {
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

	public ArrayList<Country> getCountriesData(){
		ArrayList<Country> countries = new ArrayList<Country>();
		
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();

			String query = "select * from country;";
			
			ResultSet rs = myStmt.executeQuery(query);
			
			while( rs.next() ) {
	
				String countryCode = rs.getString("co_code");
				String countryName = rs.getString("co_name");
				String countryDetails = rs.getString("co_details");
				
				Country c = new Country();
				c.setCountryCode(countryCode);
				c.setCountryName(countryName);
				c.setCountryDetails(countryDetails);
				
				countries.add(c);
			}
			
			conn.close();
			myStmt.close();
			rs.close();
		
		}catch( SQLException se ) {
            System.out.println(se.getMessage());
		}
		
		return countries;
	}
	
	public void deleteCountry(String countryCode){
		try{
			Connection conn = mysqlDS.getConnection();
			
		 	//DELETE
			String sql = "Delete from country " +
					  "where co_code = '" + countryCode +"';";
			
			java.sql.PreparedStatement myStmt = conn.prepareStatement(sql);
			
			myStmt.executeUpdate(sql);
			conn.close();
			myStmt.close();
		
		}catch( SQLIntegrityConstraintViolationException  rp) {
			FacesMessage message = new FacesMessage("Error: Cannot Delete Country Code: " + countryCode + " due to foreign key constraint, i.e. reference in other table.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(MySQLIntegrityConstraintViolationException rs){
			FacesMessage message = new FacesMessage("Error: Cannot Delete Country Code: " + countryCode + " due to foreign key constraint, i.e. reference in other table.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( CommunicationsException ce){
			FacesMessage message = new FacesMessage("The Database is offline/or the communication link down");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( SQLException se) {
	        System.out.println(se.getMessage());
		}
	}
	
	public void updateCountry(String countryCode, String countryName, String countryDetails){
		try{
			Connection conn = mysqlDS.getConnection();
			setRedirect("");
			String sql = "UPDATE country SET co_name = '" + countryName + "' , co_details = '" +countryDetails + 
					"' WHERE co_code = '" + countryCode +"';";
			
			java.sql.PreparedStatement myStmt = conn.prepareStatement(sql);
			
			myStmt.executeUpdate(sql);
			conn.close();
			myStmt.close();
			
			setRedirect("list_countries");
		}catch( SQLException se ) {
            System.out.println(se.getMessage());
		}
	}
	
	public void addNewCountry(String countryCode, String countryName, String countryDetails){
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			setRedirect("");
			String query = "insert into country values(" +
				"'" + countryCode + "', " +
				"'" + countryName + "', " +
				"'" + countryDetails + "');";
			
			myStmt.executeUpdate(query);
			
			conn.close();
			myStmt.close();
			
			setRedirect("list_countries");
			
		}catch( SQLIntegrityConstraintViolationException  rp) {
			FacesMessage message = new FacesMessage("Error: Country Code: " + countryCode + " already exists");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(MySQLIntegrityConstraintViolationException rs){
			FacesMessage message = new FacesMessage("Error: Country Code: " + countryCode + " already exists");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( CommunicationsException ce){
			FacesMessage message = new FacesMessage("The Database is offline/or the communication link down");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( SQLException se) {
	        System.out.println(se.getMessage());
		}

	}

}

