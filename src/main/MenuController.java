package main;
 
import models.Course;
import models.Room;
import models.Teacher;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuController {
	public static final String GRAPH_LOCATION = "resources/university/university.ttl";
	public static final String TRIPPLE_STORE_DIR = "resources/university/";
	
	
	public void findCoursesTaughtByTeacher(){
		String teacherName = askParameter("<Professor's last and first name>");
		Teacher teacher = new Teacher(teacherName);
		printCourses(teacher.getCoursesTaught());
		waitForUser();
	}
	
	public void findTeacherForCourse(){
		String courseTitle = askParameter("<Course title>");
		Course course = new Course(courseTitle);
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		teachers.add(course.getTeacher());
		printTeachers(teachers);
		waitForUser();
	}
	
	public void findAllCoursesWithType() throws Exception{
		String courseType = askParameter("<Course type>");
		printCourses(Course.findCoursesWithType(courseType));
		waitForUser();
	}
	
	public void findCoursesWithECTSMoreThan() throws Exception{
		String amount = askParameter("<amount>");
		printCourses(Course.findCoursesWithECTSMoreThan(Integer.parseInt(amount)));
		waitForUser();
	}
	
	public void findCoursesWithECTSLessThan() throws Exception{
		String amount = askParameter("<amount>");
		printCourses(Course.findCoursesWithECTSLessThan(Integer.parseInt(amount)));
		waitForUser();
	}
	
	public void findRoomsWithCapacityMoreThan() throws Exception{
		String amount = askParameter("<amount>");
		printRooms(Room.findRoomsWithCapacityMoreThan(Integer.parseInt(amount)));
		waitForUser();
	}
	
	public void findRoomsWithCapacityLessThan() throws Exception{
		String amount = askParameter("<amount>");
		printRooms(Room.findRoomsWithCapacityLessThan(Integer.parseInt(amount)));
		waitForUser();
	}
    
    /**
	 * Waits until user hits enter.
	 */
	private static void waitForUser() {
		System.out.print("Press <Enter> to return to the main menu.");
		new Scanner(System.in).nextLine();
	}
	
	/**
	 * Reads value for specified parameter from standard input.
	 * @param paramName name of the parameter
	 * @return String
	 */
	private String askParameter(String paramName) {
        System.out.println("Input: " + paramName);
        return new Scanner(System.in).nextLine();
    }
	
	public static void printCourses(ArrayList<Course> courses){
		int resultCount = courses.size();
		for(Course course : courses){
			System.out.println("---------------------");
			System.out.println("Course title: " + course.getCourseTitle());
			System.out.println("Course type: " + course.getCourseType());
			System.out.println("Professor: " + course.getTeacher().getName());
			System.out.println("Weekly hours: " + course.getWeeklyHours());
			System.out.println("ECTS: " + course.getEcts());
			System.out.println("---------------------");
		}
		System.out.println("Total results: " + resultCount);
	}
	
	public static void printTeachers(ArrayList<Teacher> teachers){
		int resultCount = teachers.size();
		if(resultCount > 0){
			for(Teacher teacher : teachers){
				if(teacher != null){
					System.out.println("---------------------");
					System.out.println("Name: " + teacher.getName());
					System.out.println("Homepage: " + teacher.getHomepage());
					System.out.println("---------------------");
				}else{
					System.out.println("Couldn't find the professor for the course you entered.\n Please try again (make sure to capitalize the course title)");
				}
			}
			System.out.println("Total results: " + resultCount);
		}
	}
	
	public static void printRooms(ArrayList<Room> rooms){
		int resultCount = rooms.size();
		for(Room room : rooms){
			System.out.println("---------------------");
			System.out.println("Name: " + room.getName());
			System.out.println("Address: " + room.getAddress());
			System.out.println("Number: " + room.getNumber());
			System.out.println("Capacity: " + room.getCapacity());
			System.out.println("---------------------");
		}
		System.out.println("Total results: " + resultCount);
	}
}
