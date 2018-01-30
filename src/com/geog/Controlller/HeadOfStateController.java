package com.geog.Controlller;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.geog.DAO.HeadOfStateDAO;
import com.geog.Model.HeadOfState;

@ManagedBean
@SessionScoped
public class HeadOfStateController {
	private ArrayList<HeadOfState> headsOfState;
	private HeadOfStateDAO dao;
	
	public HeadOfStateController(){
		headsOfState = new ArrayList<HeadOfState>();
		try {
			this.dao = new HeadOfStateDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<HeadOfState> getHeadsOfState() {
		return headsOfState;
	}

	public void loadData() {
		try {
			headsOfState = dao.getHeadOfStateData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void processDelete(String countryCode) throws Exception{
		dao.deleteHeadOfState(countryCode);
	}
	
	public String processNewHeadOfState(String countryCode, String headOfState) throws Exception {
			dao.addNewHeadOfState(countryCode, headOfState);
			
			return "list_heads_of_state";
	}
}

