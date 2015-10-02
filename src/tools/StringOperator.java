package tools;

import java.util.Arrays;

public abstract class StringOperator {
	// 
	public static String[] splitBlanksIncluded(String input, String regex, int limit) {
		String[] result = input.split(regex, limit);
		result = Arrays.copyOf(result, limit);
		return result;
	}
	/*
	 * trims all spaces and one lip symbol at the end and start of String
	 */
	public static String trimLips(String input) {
		input = input.trim();
		input = input.substring(1, input.length() - 1);
		return input;
	}
	
	
	public static boolean compareTrimedCaseInsesitive(String a, String b) {
		return (a.trim().compareToIgnoreCase(b.trim()) == 0);
	}
}