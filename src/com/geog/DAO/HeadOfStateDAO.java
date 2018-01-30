package com.geog.DAO;

import com.geog.Model.HeadOfState;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;


public class HeadOfStateDAO {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> headsOfStateData;
	ArrayList<HeadOfState> headsOfStateList;

	public HeadOfStateDAO() throws Exception {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("headsOfStateDB");
		headsOfStateData = database.getCollection("headsOfState");
	}
	
	public ArrayList<HeadOfState> getHeadOfStateData() throws Exception {	
		headsOfStateList = new ArrayList<>();
		Gson gson = new Gson();
		FindIterable<Document> objects = headsOfStateData.find();
		
		for (Document d: objects) {
			HeadOfState headOfState = gson.fromJson(d.toJson(), HeadOfState.class);
			headsOfStateList.add(headOfState);
		}
		
		return headsOfStateList;	
	}

	public void addNewHeadOfState(String countryCode, String headOfState) throws Exception  {
		Document document = new Document();
		
		document.append("_id", countryCode)
				.append("headOfState", headOfState);
		
		headsOfStateData.insertOne(document);
	}
	
	
	public void deleteHeadOfState(String countryCode) throws Exception  {
		Bson delete = new Document("_id", countryCode);
		
		headsOfStateData.deleteOne(delete);
	}
}