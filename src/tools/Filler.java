package tools;

import java.util.GregorianCalendar;

import unit.Course;
import unit.Module;
import unit.School;
import unit.Test;

public final class Filler {
	
	//constructors
	private Filler() { }
	
	//methods
	//filling school with single course and users
	public static School fillSchool(School school) {
		Course course = new Course();
		course = Filler.fillCourse(course);
		school.addCourse(course);
		return school;
	}
	
	//filling users
	
	//filling course with some random modules(including test for certain)
	public static Course fillCourse(Course course) {
		/*	GregorianCalendar
		 * 	month's index goes from 0 to 11 
		 * 
		 */
		//initialize course
		course = new Course("Java", new GregorianCalendar(2015, 9, 7), 
				 new GregorianCalendar(2016, 10, 1));
		//add modules
		Module tempModule;
		Test tempTest;
		
		course.addModule(new Module("Base",  new GregorianCalendar(2015, 10, 1), 
				 new GregorianCalendar(2016, 0, 1)));
		course.addModule(new Module("Data Types",  new GregorianCalendar(2016, 0, 1), 
				 new GregorianCalendar(2016, 3, 1)));
		
		course.addModule(new Module("Collections",  new GregorianCalendar(2016, 3, 1), 
				 new GregorianCalendar(2016, 5, 1)));
		tempModule = course.getModule("Collections");
		tempTest = new Test("Collections Test", 
				new GregorianCalendar(2016, 4, 30, 10, 0), 
				new GregorianCalendar(2016, 4, 30, 10, 30));
		tempTest.setPrivateAccess(false);
		tempModule.setTest(tempTest);
		if (!course.setModule("Collections", tempModule)) {
			System.out.println("setMoule was unsuccessful");
		}
		
		course.addModule(new Module("Concurrency",  new GregorianCalendar(2016, 5, 1), 
				 new GregorianCalendar(2016, 7, 1)));
		tempModule = course.getModule("Concurrency");
		tempTest = new Test("Collections Test", 
				new GregorianCalendar(2016, 4, 30, 10, 0), 
				new GregorianCalendar(2016, 4, 30, 10, 30));
		tempTest.setPrivateAccess(false);
		tempModule.setTest(tempTest);
		if (!course.setModule("Concurrency", tempModule)) {
			System.out.println("setMoule was unsuccessful");
		}
		course.addModule(new Module("Exceptions",  new GregorianCalendar(2017, 7, 1), 
				 new GregorianCalendar(2016, 9, 1)));
		course.addModule(new Module("IO",  new GregorianCalendar(2016, 9, 1), 
				 new GregorianCalendar(2016, 11, 1)));
		return course;
	}
}
