
package courses;

import marshal.json.MarshallerJson;
import marshal.schoolformat.Validator;
import marshal.schoolformat.exception.InvaildFieldFormatException;
import tools.Filler;
import tools.storage.Storekeeper;



import unit.School;
import unit.Test;



public final class SchoolConsole {

	//fields
	private static final String PATH_XML = "school-jaxb.xml";
	private static School school;
	
	
	//constructors
	private SchoolConsole() {
		super();
	}
	
	//methods
	
	public static void main(String[] args) throws InvaildFieldFormatException {
		
		//json xml TESTING:
		
		
		//fill school etc.; remove bellow after all classes will be done 
		SchoolConsole.school = new School();
		//xml
		SchoolConsole.school = Filler.fillSchool(school);
		Storekeeper storekeeper = new Storekeeper();
		storekeeper.setMarshallerXmlPath(SchoolConsole.PATH_XML);
		//write
		storekeeper.saveStateToXML(School.class, SchoolConsole.school);
		//read
		SchoolConsole.school = storekeeper.loadStateFromXML(School.class);
		
		//json
		storekeeper.setMarshallerJsonPath("school-json-gson.txt");
		//write
		storekeeper.saveStateToJson(SchoolConsole.school);
		//read
		SchoolConsole.school = storekeeper.loadStateFromJson(School.class);
		//TODO Hug your mama
		
		
		
		// Natalia Romanenko note:
		// TASK: print all public tests for current course
		System.out.println("\n\n\nTASK: print all public tests for current course");
		System.out.println(SchoolConsole.school.getCourse("Java").getPublicTests());	
		// Natalia Romanenko note:
		// TASK: print all courses starting no later than a week from today
		System.out.println("\n\nTASK: print all courses starting no later than a week from today");
		System.out.println(SchoolConsole.school.getCurrentWeekCourses());
		
		
		// VALIDATOR TESTING:
		
		String name = "Casd";
		String bool = "False";
		String date = "";
		String test = "Test1::2016.11.31:";
		String module = "Java:2015.0&.28:2015.12.11:[Test1:2015.11.11&10.15.30:2015.02.25&10.45.59:FALSE]";
		String course = 
			"Java Course:2015.10.11:2015.11.11:"
			+ "["
			+ "Data Types:2015.10.11:2015.11.11:[Test1:2015.11.11&10.15.30:2015.11.11&10.45.59:true]"
			+ ";"
			+ "Collections:2015.10.11:2015.11.11:[Test45:2015.11.11&10.15.30:2016.11.31&10.45.59:false]"
			+ "]";
		//incomplete input
		date = "2015.11.12&12.11.1";
		//at index
		date = "2015.1&.12&12.11.11";
		//invalid symbols
		date = "2015.11.12-12.11.11";
		//valid
		date = "2016.02.07&10.45.59";
		//leap year; 2016 is the leap year;
		date = "2016.02.30&10.45.59";
		//hour
		date = "2016.02.07&24.45.59";
		//minutes
		date = "2016.02.07&23.60.59";
		//seconds
		date = "2016.2&.07&23.45.16";
		
		Validator validator = new Validator();
		//validator.validateName(name);
		//validator.validateBool(bool);
		//validator.validateDate(date);
		//validator.validateTest(test);
		//validator.validateModule(module);
		validator.validateCourse(course);
		
		
	}

}
