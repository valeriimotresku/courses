package marshal.schoolformat;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tools.StringOperator;
import marshal.schoolformat.exception.InvaildFieldFormatException;

public class Validator {
	//fields
	private static final String VALID_DATE_FORMAT = 
			"((\\d{4})\\.(\\d{2})\\.(\\d{2}))(&(\\d{2})\\.(\\d{2})\\.(\\d{2})){0,1}";
	private static final String INVALID_DATE_SYMBOL = "[^\\d|\\.|&]";
	private static final String VALID_NAME_FORMAT = "[A-Z][^<>{}\\[\\];,:]*";
	private static final String INVALID_NAME_SYMBOL = "[<>{}\\[\\];,:]";
	private static final String VALID_BOOL_FORMAT = "(true|false)";
	private static final String VALID_TEST_FORMAT = "[^:]+:[^:]*:[^:]*:[^:]*";
	private static final String VALID_MODULE_FORMAT = "[^:]+:[^:]+:[^:]+:.*";
	private static final String VALID_COLLECTION_FORMAT = "([^;]*\\s*;{0,1}\\s*)*";
	private static final String VALID_COURSE_FORMAT = "[^:]+:[^:]+:[^:]+:(\\s|.)*";
	private static final String UNIT_DELIMETER = ":";
	private static final String COLLECTION_DELIMETER = ";";
	private static final int TEST_INPUTS_COUNT = 4;
	private static final int MODULE_INPUTS_COUNT = 4;
	private static final int COURSE_INPUTS_COUNT = 4;
	
	
	
	//methods
	private int indexOfMismatch(String input, Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        for (int i = input.length(); i > 0; --i) {
            Matcher region = matcher.region(0, i);
            if (region.matches() || region.hitEnd()) {
                return i;
            }
        }

        return 0;
    }
	private String getInvalidSymbolsString(String input, Pattern pattern) {
		Matcher m = pattern.matcher(input);
		String result = "";
		boolean commaFlag = false;
		while (m.find()) {
			if (commaFlag) {
				result += ", '" + input.charAt(m.start()) + "'"; 
			} else {
				result += "'" + input.charAt(m.start()) + "'";
				commaFlag = true;
			}
		}
		return result;
	}
	private String generateInvalidIndexMsg(String input, String inputName, String regex) {
		int invalidIndex = 
			this.indexOfMismatch(input, Pattern.compile(regex));
		String msg = 
				"Invalid \"" + inputName + "\" format in input:\"" + input + "\"";
		msg += (invalidIndex >= input.length()) ? 
			". Incomplete input"
			: " at index=" + invalidIndex + ":'" + input.charAt(invalidIndex) + "'";
		return msg;
	}
	private String generateInvalidSymbolsMsg(String input, String inputName, 
			String invalidSymbols) {
		
		String msg = 
				"Invalid symbols in \"" + inputName + "\" input:\"" + input 
				+ "\". Invalid symbols: " + invalidSymbols;
		return msg;
	}
	
	public boolean validateName(String name) throws InvaildFieldFormatException {
		name = name.trim();
		if (name.isEmpty()) {
			throw new InvaildFieldFormatException(
					"Field \"name\" can not be blank"
				  );
		}
		// validate input format
		Pattern p = Pattern.compile(VALID_NAME_FORMAT);
		Matcher m = p.matcher(name);
		if (!m.matches()) {
			String invalidFormatMessage;
			String invalidSymbols = 
					this.getInvalidSymbolsString(name, Pattern.compile(INVALID_NAME_SYMBOL));
			// if all symbols are valid, then print in exception the index where pattern fails
			// here it always would be first symbol, that must be Latin capital letter
			if (invalidSymbols.isEmpty()) {
				invalidFormatMessage = 
					"Invalid \"name\" format. Input:\"" + name 
						+ "\". Field \"name\" must start with Latin capital letter";
			} else {
			// otherwise print invalid symbols
				invalidFormatMessage = 
						this.generateInvalidSymbolsMsg(name, "name", invalidSymbols);
			}
			throw new InvaildFieldFormatException(invalidFormatMessage);
		}
		return true;
	}
	/*
	 * handles:
	 * incomplete input, fails at index, invalid symbols, valid date, leap year; 2016 is the leap year
	 * valid hour, minutes, seconds
	 */
	public boolean validateDate(String date) throws InvaildFieldFormatException {
		date = date.trim();
		if (date.isEmpty()) { 
			return true;
		}
		// validate input format
		Pattern p = Pattern.compile(VALID_DATE_FORMAT);
		Matcher m = p.matcher(date);
		if (!m.matches()) {
			String invalidFormatMessage;
			String invalidSymbols = 
					this.getInvalidSymbolsString(date, Pattern.compile(INVALID_DATE_SYMBOL));
			// if all symbols are valid, then print in exception the index where pattern fails
			if (invalidSymbols.isEmpty()) {
				invalidFormatMessage = 
						this.generateInvalidIndexMsg(date, "date", VALID_DATE_FORMAT);
			} else {
			// otherwise print invalid symbols
				invalidFormatMessage = 
						this.generateInvalidSymbolsMsg(date, "date", invalidSymbols);
			}
			throw new InvaildFieldFormatException(invalidFormatMessage);
		}
		
		// validate date values
		// capturing groups: 
		// Date: 1-YYY.MM.DD, 2-YYYY, 3-MM, 4-DD; Time: 5-&hh.mm.ss, 6-hh, 7-mm, 8-ss
		
		Calendar calendar = Calendar.getInstance();
		// year could be less or greater than current, so Validator doesn't check it at all
		int year = Integer.parseInt(m.group(2));	
		
		// month in calendar instance start from 0, but in school format from 01
		int month = Integer.parseInt(m.group(3));
		// Also getMaximum returns maximum for field in general
		// getActualMaximum returns maximum for current calendar date field
		// so getActualMaximum for instance with February month and leap year returns 29
		// pay attention to getActualMaximum(Calendar.MONTH) + 1 in comparing bellow
		if ((month > (calendar.getActualMaximum(Calendar.MONTH) + 1)) || (month < 1)) {
			throw new 
				InvaildFieldFormatException("Month value must be in range between '01'-'12'; date:" + m.group(1));
		}
		
		// set calendar to parsed year and month
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		// set calendar DAY_OF_MONTH to 1.
		// To be able to check maximum for current month!; and to prevent the calendar rolling
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int day = Integer.parseInt(m.group(4));
		if ((day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) || (day < 1)) {
			throw new InvaildFieldFormatException(
					"The day value is out of range for current month and year in date:"
					+ m.group(1) + ". The day value must be in range between '01'-'" 
					+ calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "'"
					+ " in this particular case"
				);
		}
		
		// validate time values
		if (m.group(5) == null) {
			return true;
		}
		
		int hours = Integer.parseInt(m.group(6));
		if ((hours > calendar.getActualMaximum(Calendar.HOUR_OF_DAY)) 
				|| (hours < calendar.getActualMinimum(Calendar.HOUR_OF_DAY))) {
			throw new InvaildFieldFormatException("The hours value must be in range between '00'-'23'; time:" + m.group(5));
		}
		
		int minutes = Integer.parseInt(m.group(7));
		if ((minutes > calendar.getActualMaximum(Calendar.MINUTE)) 
				|| (minutes < calendar.getActualMinimum(Calendar.MINUTE))) {
			throw new InvaildFieldFormatException("The minutes value must be in range between '00'-'59'; time:" + m.group(5));
		}
		
		int seconds = Integer.parseInt(m.group(8));
		if ((seconds > calendar.getActualMaximum(Calendar.SECOND)) 
				|| (seconds < calendar.getActualMinimum(Calendar.SECOND))) {
			throw new InvaildFieldFormatException("The seconds value must be in range between '00'-'59'; time:" + m.group(5));
		}
		
		
		return true;
	}
	public boolean validateBool(String bool) throws InvaildFieldFormatException {
		bool = bool.trim();
		if (bool.isEmpty()) { 
			return true;
		}
		// validate input boolean format
		Pattern p = Pattern.compile(VALID_BOOL_FORMAT, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(bool);
		if (!m.matches()) {
			String invalidFormatMessage = 
				"Invalid \"boolean\" format in input:\"" + bool + "\". Valid boolean values are: 'true', 'false' (case insesitive)";
			throw new InvaildFieldFormatException(invalidFormatMessage);
		}
		return true;
	}
	
	public boolean validateUnitFormat(
			String input, String inputName, final String validUnitFormat) 
					throws InvaildFieldFormatException {
		
		// validate input Unit format
		Pattern p = Pattern.compile(validUnitFormat);
		Matcher m = p.matcher(input);
		if (!m.matches()) {
			String invalidFormatMessage = 
					this.generateInvalidIndexMsg(input, inputName, validUnitFormat);
			throw new InvaildFieldFormatException(invalidFormatMessage);
		}
		return true;
	}
	
	public boolean validateUnitFields(String[] unitFields) throws InvaildFieldFormatException {
		// indexes for all Unit implementors:
		// 0-name, 1-dateStart, 2-dateEnd
		if (unitFields.length < 3) {
			throw new IndexOutOfBoundsException("unitFields.lengh  "); 
		}
		// TODO replace all magic numbers in current class with static constants or something else appropriate 
		this.validateName(unitFields[0]);
		this.validateDate(unitFields[1]);
		this.validateDate(unitFields[2]);		
		// TODO validate dateStart before dateEnd relationship
		// Calendar ...
		return true;
	}
	
	public boolean validateTest(String test) throws InvaildFieldFormatException {
		// format:
		// Test1:2015.11.11&10.15.30:2015.11.11&10.45.59:true
		test = test.trim();
		// empty means null. See docs. That will exist in future :)
		// Test could has null value
		if (test.isEmpty()) { 
			return true;
		}
		// validate input Test format
		this.validateUnitFormat(test, "Test", VALID_TEST_FORMAT);
		
		// validate fields
		//always returns array with length == TEST_INPUTS_COUNT
		String[] testFields = 
				StringOperator.splitBlanksIncluded(test, UNIT_DELIMETER, TEST_INPUTS_COUNT);
		// indexes:
		// 0-name, 1-dateStart, 2-dateEnd, 3-privateAcces
		this.validateUnitFields(testFields);
		this.validateBool(testFields[3]);
		return true;
	}
	
	public boolean validateModule(String module) throws InvaildFieldFormatException {
		// format:
		// Java:2015.10.11:2015.11.11:[Test1:2015.11.11&10.15.30:2015.11.11&10.45.59:true]
		
		module = module.trim();
		// empty means null. See docs. That will exist in future :)
		// Module could has null value
		if (module.isEmpty()) { 
			return true;
		}
		// validate input Module format
		this.validateUnitFormat(module, "Module", VALID_MODULE_FORMAT);
		
		// validate fields
		//always returns array with length == MODULE_INPUTS_COUNT
		String[] moduleFields = 
				StringOperator.splitBlanksIncluded(module, UNIT_DELIMETER, MODULE_INPUTS_COUNT);
		// indexes:
		// 0-name, 1-dateStart, 2-dateEnd, 3-test
		this.validateUnitFields(moduleFields);
		//pass substring of test input without []-inner structure format symbols
		this.validateTest(StringOperator.trimLips(moduleFields[3]));
		return true;
	}
	
	public boolean validateModules(String modulesInput) throws InvaildFieldFormatException {
		// format:
		/*
		  "Data Types:2015.10.11:2015.11.11:[Test1:2015.11.11&10.15.30:2015.11.11&10.45.59:true]"
		+ ";"
		+ "Collections:2015.10.11:2015.11.11:[Test45:2015.11.11&10.15.30:2015.11.11&10.45.59:false]"
		*/
		modulesInput = modulesInput.trim();
		// empty means null. See docs. That will exist in future :)
		// could has null value
		if (modulesInput.isEmpty()) { 
			return true;
		}
		// validate input collection format
		this.validateUnitFormat(modulesInput, "Collection", VALID_COLLECTION_FORMAT);
		
		// validate each module in collection
		String[] modules = modulesInput.split(COLLECTION_DELIMETER);
		for (String module : modules) {
			this.validateModule(module);
		}
		return true;
	}
	
	public boolean validateCourse(String course) throws InvaildFieldFormatException {
		/*
	 	"Java Course:2015.10.11:2015.11.11:"
		+ "["
		+ "Data Types:2015.10.11:2015.11.11:[Test1:2015.11.11&10.15.30:2015.11.11&10.45.59:true]"
		+ ";"
		+ "Collections:2015.10.11:2015.11.11:[Test45:2015.11.11&10.15.30:2015.11.11&10.45.59:false]"
		+ "]";
		 */
		course = course.trim();
		// empty means null. See docs. That will exist in future :)
		// Course could has null value
		if (course.isEmpty()) { 
			return true;
		}
		// validate input Course format
		this.validateUnitFormat(course, "Course", VALID_COURSE_FORMAT);
		
		// validate fields
		//always returns array with length == COURSE_INPUTS_COUNT
		String[] courseFields = 
				StringOperator.splitBlanksIncluded(course, UNIT_DELIMETER, COURSE_INPUTS_COUNT);
		// indexes:
		// 0-name, 1-dateStart, 2-dateEnd, 3-modules(Collection)
		this.validateUnitFields(courseFields);
		//validate modules from modules collection input
		//cut []-inner structure format symbols
		this.validateModules(StringOperator.trimLips(courseFields[3]));
		return true;
	}
}
