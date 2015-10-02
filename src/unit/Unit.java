/**
 * 
 */
package unit;

import java.util.Calendar;

/**
 * @author amberu
 *
 */
//Base interface for all units that have name and time period
public interface Unit {
	String getName();
	String editName(String newName);
	
	Calendar getDateStart();
	Calendar getDateEnd();
	
	Calendar setDateStart(Calendar newStart);
	Calendar setDateEnd(Calendar newEnd);
}
