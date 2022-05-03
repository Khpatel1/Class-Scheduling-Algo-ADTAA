package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBconnection {
	public static MongoClient client = MongoClients.create(
			URISecret.URI);
	public static MongoDatabase db = client.getDatabase("test");

	// get tables
	public static MongoCollection<Document> db_instructors = db.getCollection("instructors");
	public static MongoCollection<Document> db_courses = db.getCollection("courses");
	public static MongoCollection<Document> db_schedule = db.getCollection("schedule");
	public static MongoCollection<Document> db_timeslots = db.getCollection("Timeslots");

}
