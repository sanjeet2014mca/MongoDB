package com.mkyong.core;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * Java + MongoDB Hello world Example
 * 
 */
public class App {
	public static void main(String[] args) throws ParseException {
		try {
			SimpleDateFormat sdf9 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

			String dateInString9 = "22-01-2015 10:20:56";
			String dateToVal= "29-05-2017 10:20:56";
			Date date9 = sdf9.parse(dateInString9);
			Date dateTo=sdf9.parse(dateToVal);
			System.out.println(":"+date9+"\n:"+dateTo);
			System.out.println("(/**** Connect to MongoDB ****/");
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);
			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("testdb");
			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("user");
			System.out.println("*** Insert ****");
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			document.put("name", "mkyong");
			document.put("age", 30);
			document.put("createdDate", new Date());
			table.insert(document);
			System.out.println("**** Find and display ****");
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "mkyong");
			DBCursor cursor = table.find(searchQuery);
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			System.out.println("**** Update ****");
			// search document where name="mkyong" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "mkyong");
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "mkyong-updated");
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
			table.update(query, updateObj);

			/**** Find and display ****/
			/*BasicDBObject searchQuery2 
			= new BasicDBObject().append("name", "mkyong-updated");
			 */
			DBCursor cursor2 = table.find(query);
			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}
			System.out.println("Done");
			System.out.println("**********************************************Several Operation with mongodb*****************************************");		
			System.out.println("/*Mongo Database*/");
			/*Display all databases.*/
			List<String> dbs = mongo.getDatabaseNames();
			for(String dbDetails : dbs){
				System.out.println(dbDetails);
			}
			/*Get collection / table.*/
			DBCollection tables = db.getCollection("user");
			System.out.println(tables);
			/*Display all collections from selected database.*/
			Set<String> tableDetails = db.getCollectionNames();
			for(String coll : tableDetails){
				System.out.println(coll);
			}
			System.out.println("Save example");
			System.out.println(";Save a document (data) into a collection (table) named “user”.");
			DBCollection tableSecond = db.getCollection("userTest");
			//	DBCollection table = db.getCollection("user");
			BasicDBObject document1 = new BasicDBObject();
			document1.put("name", "Sohan");
			document1.put("age", 30);
			document1.put("createdDate", date9);
			tableSecond.insert(document1);
			System.out.println("7. Update example");
			BasicDBObject queryTEst = new BasicDBObject();
			queryTEst.put("name", "Sohan");
			BasicDBObject newDocument7 = new BasicDBObject();
			newDocument7.put("name", "Test  Updated");
			BasicDBObject updateObj7 = new BasicDBObject();
			updateObj7.put("$set", newDocument7);
			tableSecond.update(queryTEst, updateObj7);
			System.out.println("Find example");
			//System.out.println("Find document where “name=mkyong”, and display it with DBCursor");
			//DBCollection table = db.getCollection("user");
			BasicDBObject searchQuery8 = new BasicDBObject();
			searchQuery8.put("name", "Test  Updated");
			DBCursor cursor8 = tableSecond.find(searchQuery8);
			while (cursor8.hasNext()) {
				System.out.println("Json"+cursor8.next());
			}
			System.out.println("Delete example:");
			BasicDBObject searchQuery9 = new BasicDBObject();
			searchQuery9.put("name", "Test  Updated");
			tableSecond.remove(searchQuery9);

			BasicDBObject queryDateComp = new BasicDBObject();
			queryDateComp.put("createdDate", BasicDBObjectBuilder.start("$gte", date9).add("$lte", dateTo).get());
			DBCursor dateBwtCur=tableSecond.find(query).sort(new BasicDBObject("createdDate", -1));
			while(dateBwtCur.hasNext()){
				System.out.println("dateBwtCur Json:-"+dateBwtCur.next());
			}



		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}
