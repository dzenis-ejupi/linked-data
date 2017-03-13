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

public class Course {
	private String courseType;
	private String courseTitle;
	private String weeklyHours;
	private String ects;
	private Teacher teacher;
	
	private Dataset dataset = TripleStore.getDataset();
	
	private static final String MORE_THAN = "more than";
	private static final String LESS_THAN = "less than";
	
	public Course(){}
	
	public Course(String courseTitle){
		findCourseByTitle(courseTitle);
	}
	
	public Course(String courseType, String courseTitle, String weeklyHours, String ects, Teacher teacher){
		this.courseType = courseType;
		this.courseTitle = courseTitle;
		this.weeklyHours = weeklyHours;
		this.ects = ects;
		this.teacher = teacher;
	}
	
	public void findCourseByTitle(String courseTitle){
		String queryString = "PREFIX teach: <http://linkedscience.org/teach/ns#>" +
			    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "PREFIX tuvienna: <http://ifs.tuwien.ac.at/tulid/group03/>" +
			    "SELECT ?course WHERE { ?course teach:courseTitle \"" + courseTitle + "\" . } ";
		
		dataset.begin(ReadWrite.READ);
		try {
			  Query query = QueryFactory.create(queryString);
			  QueryExecution qexec = QueryExecutionFactory.create(query,dataset);
			  try {
			    	ResultSet results = qexec.execSelect();
			    	if(results.hasNext())
					{
						QuerySolution solution = results.nextSolution();
						Resource courseResource = solution.getResource("course");
						this.setCourseType(courseResource.getProperty(TUVIENNA.courseType).getString());
						this.setCourseTitle(courseResource.getProperty(TEACH.courseTitle).getString());
						this.setEcts(courseResource.getProperty(TEACH.ects).getString());
						this.setWeeklyHours(courseResource.getProperty(TEACH.weeklyHours).getString());
						teacher = new Teacher();
						teacher.setName(courseResource.getProperty(TEACH.teacher).getProperty(FOAF.name).getString());
						teacher.setHomepage(courseResource.getProperty(TEACH.teacher).getProperty(FOAF.homepage).getString());
					}
			  } catch (Exception e) {
				e.printStackTrace();
			}
			finally {
			    qexec.close();
			}
		}
		finally {
			dataset.end();
		}
	}
	
	public static ArrayList<Course> findCoursesWithECTSMoreThan(int amount) throws Exception{
		return findCoursesWithEcts(MORE_THAN, amount);
	}
	
	public static ArrayList<Course> findCoursesWithECTSLessThan(int amount) throws Exception{
		return findCoursesWithEcts(LESS_THAN, amount);
	}
	
	private static ArrayList<Course> findCoursesWithEcts(String moreOrLess, int amount) throws Exception{
		String valueSign = moreOrLess.equals(MORE_THAN) ? ">" : "<";
		
		Dataset dataset = TripleStore.getDataset();
		ArrayList<Course> courses = new ArrayList<Course>();
		
		String queryString = "PREFIX teach: <http://linkedscience.org/teach/ns#>" +
			    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "PREFIX tuvienna: <http://ifs.tuwien.ac.at/tulid/group03/>" +
			    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + 
			    "SELECT ?course ?ects WHERE { ?course teach:ects ?ects . FILTER ( xsd:integer(?ects) " + valueSign + amount + " ) . } ORDER BY DESC(xsd:integer(?ects))";
		
		String currentCourseTitle = null;
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
						
						Teacher teacher = new Teacher();
						teacher.setName(courseResource.getProperty(TEACH.teacher).getProperty(FOAF.name).getString());
						teacher.setHomepage(courseResource.getProperty(TEACH.teacher).getProperty(FOAF.homepage).getString());
						
						course.setTeacher(teacher);
						course.setCourseType(courseResource.getProperty(TUVIENNA.courseType).getString());
						course.setCourseTitle(courseResource.getProperty(TEACH.courseTitle).getString());
						course.setEcts(courseResource.getProperty(TEACH.ects).getString());
						course.setWeeklyHours(courseResource.getProperty(TEACH.weeklyHours).getString());
						currentCourseTitle = course.getCourseTitle();
						
						courses.add(course);
					}
			  } catch(PropertyNotFoundException e){
				// If resource is missing some properties.
				// Not good, but other resources might be OK, so continue but log this one.
				  System.out.println("!!! ---- Could not load Teacher name and homepage properties for !!! ---- " + currentCourseTitle);
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
	
	public static ArrayList<Course> findCoursesWithType(String type) throws Exception{
		Dataset dataset = TripleStore.getDataset();
		ArrayList<Course> courses = new ArrayList<Course>();
		
		String queryString = "PREFIX teach: <http://linkedscience.org/teach/ns#>" +
			    "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
			    "PREFIX tuvienna: <http://ifs.tuwien.ac.at/tulid/group03/>" +
			    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + 
			    "SELECT ?course WHERE { ?course tuvienna:courseType ?courseType . FILTER ( ?courseType = \"" + type + "\" ) . }";
		
		String currentCourseTitle = null;
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
						
						Teacher teacher = new Teacher();
						teacher.setName(courseResource.getProperty(TEACH.teacher).getProperty(FOAF.name).getString());
						teacher.setHomepage(courseResource.getProperty(TEACH.teacher).getProperty(FOAF.homepage).getString());
						
						course.setTeacher(teacher);
						course.setCourseType(courseResource.getProperty(TUVIENNA.courseType).getString());
						course.setCourseTitle(courseResource.getProperty(TEACH.courseTitle).getString());
						course.setEcts(courseResource.getProperty(TEACH.ects).getString());
						course.setWeeklyHours(courseResource.getProperty(TEACH.weeklyHours).getString());
						currentCourseTitle = course.getCourseTitle();
						
						courses.add(course);
					}
			  } catch(PropertyNotFoundException e){
				// If resource is missing some properties.
				// Not good, but other resources might be OK, so continue but log this one.
				  System.out.println("!!! ---- Could not load Teacher name and homepage properties for !!! ---- " + currentCourseTitle);
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
	
	public Teacher getTeacher(){
		return teacher;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getWeeklyHours() {
		return weeklyHours;
	}

	public void setWeeklyHours(String weeklyHours) {
		this.weeklyHours = weeklyHours;
	}

	public String getEcts() {
		return ects;
	}

	public void setEcts(String ects) {
		this.ects = ects;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
