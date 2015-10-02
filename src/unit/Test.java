/**
 * 
 */
package unit;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import tools.TestTimer;


/**
 * @author amberu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Test implements Unit {

	//fields
	private String name;
	private Calendar dateStart;
	private Calendar dateEnd;
	
	private boolean privateAccess = false;
	
	
	//constructors
	
	//no-args constructor for JAXB
	public Test() {
		this("New Instance", new GregorianCalendar(), new GregorianCalendar());
	}
	
	
	public Test(String name, GregorianCalendar dateStart, GregorianCalendar dateEnd) {
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	

	
	//methods
	@Override
	public String toString() {
		return "\nTest [name=" + name + ", dateStart=" + dateStart.getTime() + ", dateEnd="
				+ dateEnd.getTime() + ", privateAccess=" + privateAccess + "]";
	}
	
	//Base methods
	
	//name
	public String getName() {
		return this.name;
	}
	public String editName(String newName) {
		String oldName = this.name;
		this.name = newName;
		return oldName;
	}
	
	//date
	public Calendar getDateStart() {
		return this.dateStart;
	}
	public Calendar getDateEnd() {
		return this.dateEnd;
	}
	public Calendar setDateStart(Calendar newStart) {
		Calendar oldStart = this.dateStart;
		this.dateStart = newStart;
		return oldStart;
	}
	public Calendar setDateEnd(Calendar newEnd) {
		Calendar oldEnd = this.dateEnd;
		this.dateEnd = newEnd;
		return oldEnd;
	}


	//access
	public boolean getPrivateAccess() {
		return this.privateAccess;
	}
	public boolean setPrivateAccess(boolean privateAccess) {
		boolean oldValue = this.privateAccess;
		this.privateAccess = privateAccess;
		return oldValue;
	}
	
	//timer
	public Timer startTestTimer(int seconds, long delay, long period) {
		return TestTimer.startTimer(seconds, delay, period);
	}


	//hashcode, equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result
				+ ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (privateAccess ? 1231 : 1237);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (privateAccess != other.privateAccess)
			return false;
		return true;
	}
	
	//
}
