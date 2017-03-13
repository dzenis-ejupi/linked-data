package models;

import java.util.ArrayList;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.PropertyNotFoundException;
import org.apache.jena.sparql.vocabulary.FOAF;

import main.TripleStore;
import vocabularies.TEACH;
import vocabularies.TUVIENNA;

public class Room {
	private String name;
	private String capacity;
	private String address;
	private String number;
	
	private static final String MORE_THAN = "more than";
	private static final String LESS_THAN = "less than";
	
	public Room(){}
	
	public static ArrayList<Room> findRoomsWithCapacityMoreThan(int amount) throws Exception{
		return findRoomsWithCapacity(MORE_THAN, amount);
	}
	
	public static ArrayList<Room> findRoomsWithCapacityLessThan(int amount) throws Exception{
		return findRoomsWithCapacity(LESS_THAN, amount);
	}
	
	private static ArrayList<Room> findRoomsWithCapacity(String moreOrLess, int amount) throws Exception{
		String valueSign = moreOrLess.equals(MORE_THAN) ? ">" : "<";
		
		Dataset dataset = TripleStore.getDataset();
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		String queryString = "PREFIX teach: <http://linkedscience.org/teach/ns#>" +
			    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "PREFIX tuvienna: <http://ifs.tuwien.ac.at/tulid/group03/>" +
			    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + 
			    "SELECT ?room WHERE { ?room tuvienna:roomCapacity ?capacity . FILTER ( xsd:integer(?capacity) " + valueSign + amount + " ) . } ORDER BY DESC(xsd:integer(?capacity))";
		
		dataset.begin(ReadWrite.READ);
		try {
			  Query query = QueryFactory.create(queryString);
			  QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
			  try {
			    	ResultSet results = qexec.execSelect();
			    	while(results.hasNext())
					{
						QuerySolution solution = results.nextSolution();
						Resource roomResource = solution.getResource("room");
						
						Room room = new Room();
						
						room.setName(roomResource.getProperty(TUVIENNA.roomName).getString());
						room.setAddress(roomResource.getProperty(TUVIENNA.roomAddress).getString());
						room.setCapacity(roomResource.getProperty(TUVIENNA.roomCapacity).getString());
						room.setNumber(roomResource.getProperty(TUVIENNA.roomNumber).getString());
						
						rooms.add(room);
					}
			  }
			  finally {
			      qexec.close();
			  }
		}
		finally {
			dataset.end();
		}
		return rooms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
