package main;

import java.util.Scanner;

public class Main {
	
	private static final String menu = "\n\nWelcome to Super Data Linkernatorix 5000, demonstrating a simple application with TU linked data\n" + "-----------\n" 
			+ "1 Which courses are being taught by <Professor surname and name>? \n"
			+ "2 Who is the professor of <Course title>?\n"
			+ "3 Which courses have more than <amount> of ECTS?\n"
			+ "4 Which courses have less than <amount> of ECTS?\n"
			+ "5 Which rooms have more than <amount> of seat capacity?\n"
			+ "6 Which rooms have less than <amount> of seat capacity?\n"
			+ "7 Find all courses of type <course type>?\n"
			+ "==========HELPERS==========\n"
			+ "Q Quit\n"
			+ "Your input <number or q>:"; 
	
	
	public static void main(String[] args) throws Exception{
		boolean exit = false;
		String input;
        do {
        	MenuController controller = new MenuController();
            input = printMenu();
            try {
				if (input.equals("1")) {
					controller.findCoursesTaughtByTeacher();
				}else if (input.equals("2")) {
				    controller.findTeacherForCourse();
				} else if (input.equals("3")) {
					controller.findCoursesWithECTSMoreThan();
				} else if (input.equals("4")) {
					controller.findCoursesWithECTSLessThan();
				}else if (input.equals("5")) {
					controller.findRoomsWithCapacityMoreThan();
				} else if (input.equals("6")) {
					controller.findRoomsWithCapacityLessThan();
				}else if (input.equals("7")) {
					controller.findAllCoursesWithType();
				}
				else if (input.equalsIgnoreCase("q")) {
					break;
				} else {
					System.out.println("Wrong input. Please try again.");
				}
			} catch (Exception e) {
				System.out.println("There was an error while trying to execute your request: " + e.getMessage());
				e.printStackTrace();
			}
        } while (!exit);
	}
	
	public static String printMenu() {
        System.out.println(menu);
        return new Scanner(System.in).nextLine();
    }
}
