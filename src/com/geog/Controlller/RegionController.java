package com.geog.Controlller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.geog.DAO.RegionDAO;
import com.geog.Model.Region;

@ManagedBean
@SessionScoped
public class RegionController {
	private ArrayList<Region> regions;
	private RegionDAO dao;
	
	public RegionController(){
		regions = new ArrayList<Region>();
		try {
			this.dao = new RegionDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Region> getRegions() {
		return regions;
	}

	public void loadRegions() throws ClassNotFoundException, SQLException {
		regions = dao.getRegionsData();
	}
	
	public String processNewRegion(String countryCode, String regionCode, String regionName, String regionDescription) {
		dao.addNewRegion(countryCode, regionCode, regionName, regionDescription);
		
		return dao.getRedirect();
	}
}
