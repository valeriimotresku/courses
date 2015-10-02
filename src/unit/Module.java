/**
 * 
 */
package unit;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author amberu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Module implements Unit {
	//fields
	private String name;
	private Calendar dateStart;
	private Calendar dateEnd;
	//@XmlElement(nillable = true) 
	private Test test = null;
	
	
	//constructors
	
	//no-args constructor for JAXB
	public Module() {
		this("New Instance", new GregorianCalendar(), new GregorianCalendar());
	}
	
	public Module(String name, Calendar dateStart, Calendar dateEnd) {
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	

	//methods
	
	
	@Override
	public String toString() {
		return "\nModule [name=" + name + ", dateStart=" + dateStart.getTime()
				+ ", dateEnd=" + dateEnd.getTime() + ", \ntest="
				+ test + "]";
	}
	
	//base unit methods
	
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
	
	//Test
	public void setTest(Test test) {
		this.test = test;
	}

	public Test getTest() {
		return test;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Module other = (Module) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
