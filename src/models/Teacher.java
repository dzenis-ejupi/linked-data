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
import org.apache.jena.sparql.vocabulary.FOAF;

import main.TripleStore;
import vocabularies.TEACH;
import vocabularies.TUVIENNA;

public class Teacher {
	private String name;
	private String homepage;
	
	public Teacher(){}
	
	public Teacher(String name){
		try {
			queryTeacherByName(name);
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	public ArrayList<Course> getCoursesTaught(){
		ArrayList<Course> courses = new ArrayList<Course>();
		Dataset dataset = TripleStore.getDataset();
		
		String queryString = "PREFIX teach: <http://linkedscience.org/teach/ns#>" +
			    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "PREFIX tuvienna: <http://ifs.tuwien.ac.at/tulid/group03/>" +
			    "SELECT ?course WHERE { ?course teach:teacher ?teacher . ?teacher foaf:name \"" + name + "\" } ";
		
		dataset.begin(ReadWrite.READ);
		try {
			  Query query = QueryFactory.create(queryString);
			  QueryExecution qexec = QueryExecutionFactory.create(query,dataset);
			  try {
			    	ResultSet results = qexec.execSelect();
			    	while(results.hasNext())
					{
						QuerySolution solution = results.nextSolution();
						Resource courseResource = solution.getResource("course");
						Course course = new Course();
						course.setCourseType(courseResource.getProperty(TUVIENNA.courseType).getString());
						course.setCourseTitle(courseResource.getProperty(TEACH.courseTitle).getString());
						course.setEcts(courseResource.getProperty(TEACH.ects).getString());
						course.setWeeklyHours(courseResource.getProperty(TEACH.weeklyHours).getString());
						course.setTeacher(this);
						courses.add(course);
					}
			  }
			  finally {
			      qexec.close();
			  }
		}
		finally {
			dataset.end();
		}
		return courses;
	}
	
	private void queryTeacherByName(String name) throws Exception{
		Dataset dataset = TripleStore.getDataset();
		
		String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "SELECT ?teacher WHERE { ?teacher foaf:name \"" + name + "\" } ";
		
		dataset.begin(ReadWrite.READ);
		try {
			  Query query = QueryFactory.create(queryString);
			  QueryExecution qexec = QueryExecutionFactory.create(query,dataset);
			  try {
			    	ResultSet results = qexec.execSelect();
			    	while(results.hasNext())
					{
						QuerySolution solution = results.nextSolution();
						Resource teacherResource = solution.getResource("teacher");
						this.setName(teacherResource.getProperty(FOAF.name).getString());
						this.setHomepage(teacherResource.getProperty(FOAF.homepage).getString());
					}
			  }
			  finally {
			      qexec.close();
			  }
		}
		finally {
			dataset.end();
		}
		
		if (this.homepage == null){
			throw new Exception("The teacher with the given name does not exist in this dataset.");
		}
	}
	
	
}
