package com.geog.DAO;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;

import com.geog.Model.Region;
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

public class RegionDAO {
	private DataSource mysqlDS;
	private String redirect;

	public RegionDAO() throws Exception {
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
	
	public ArrayList<Region> getRegionsData(){
		ArrayList<Region> regions = new ArrayList<Region>();
		
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			
			String query = "select * from region;";
			
			ResultSet rs = myStmt.executeQuery(query);
			
			while( rs.next() ) {
	
				String countryCode = rs.getString("co_code");
				String regionCode = rs.getString("reg_code");
				String regionName = rs.getString("reg_name");
				String regionDescription = rs.getString("reg_desc");
				
				Region r = new Region();
				r.setCountryCode(countryCode);
				r.setRegionCode(regionCode);
				r.setRegionName(regionName);
				r.setRegionDescription(regionDescription);
				
				regions.add(r);
			}
			
			conn.close();
			myStmt.close();
			rs.close();
		
		}catch( SQLException se ) {
            System.out.println(se.getMessage());
		}
		
		return regions;
	}
	
	public void addNewRegion(String countryCode, String regionCode, String regionName, String regionDescription){
		try{
			Connection conn = mysqlDS.getConnection();
			
			Statement myStmt = conn.createStatement();
			setRedirect("");
			
			String query = "insert into region values(" +
				"'" + countryCode + "', " +
				"'" + regionCode + "', " +
				"'" + regionName + "', " +
				"'" + regionDescription + "');";
			
			myStmt.executeUpdate(query);
			
			conn.close();
			myStmt.close();
		
		}catch( SQLIntegrityConstraintViolationException  rp) {
			FacesMessage message = new FacesMessage("Error: Region Code: " + regionCode + " already exists or Country Code: " + countryCode +" doesn't exist");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch(MySQLIntegrityConstraintViolationException rs){
			FacesMessage message = new FacesMessage("Error: Region Code: " + regionCode + " already exists or Country Code: " + countryCode +" doesn't exist");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( CommunicationsException ce){
			FacesMessage message = new FacesMessage("The Database is offline/or the communication link down");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}catch( SQLException se) {
	        System.out.println(se.getMessage());
		}

	}

}


