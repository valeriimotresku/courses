package unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;



import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "course")
public class Course implements Unit {
	//fields
	private String name;
	private Calendar dateStart;
	private Calendar dateEnd;
	@XmlElementWrapper(name = "modules-set")
	@XmlElement(name = "module")
	private Set<Module> modules = new HashSet<>();
	
	
	
	//constructors
	
	//no-args constructor for JAXB
	public Course() {
		this("New Instance", new GregorianCalendar(), new GregorianCalendar());
	}
	
	public Course(String name, Calendar dateStart, Calendar dateEnd) {
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}
	
	//methods
	
	// Natalia Romanenko note:
	// TASK: print all public tests for current course
	public List<Test> getPublicTests() {
		List<Test> tests = new ArrayList<>();
		for (Module module : this.modules) {
			Test test = module.getTest();
			if (test == null) {
				continue;
			}
			if (!test.getPrivateAccess()) {
				tests.add(module.getTest());
			}
		}
		return tests;
	}
	
	
	@Override
	public String toString() {
		return "Course [name=" + name + ", dateStart=" + dateStart.getTime()
				+ ", dateEnd=" + dateEnd.getTime() + ", \nModulesSet=" + modules + "]";
	}
	
	//base methods
	
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
	
	//modules
	private boolean compareNames(String a, String b) {
		return (a.trim().compareToIgnoreCase(b.trim()) == 0);
	}
	public boolean addModule(Module module) {
		return this.modules.add(module);
	}
	public Module getModule(String name) {
		for (Module module : this.modules) {
			if (this.compareNames(name, module.getName())) {
				return module;
			}
		}
		return null;
	}
	public boolean setModule(String name, Module module) {
		Iterator<Module> iterator = this.modules.iterator();
		while (iterator.hasNext()) {
			Module currentModule = iterator.next(); 
			if (this.compareNames(name, currentModule.getName())) {
				iterator.remove();
				this.modules.add(module);
				return true;
			}
		}
		return false;
	}
	public Module removeModule(String name) {
		Iterator<Module> iterator = this.modules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next(); 
			if (this.compareNames(name, module.getName())) {
				iterator.remove();
				return module;
			}
		}
		return null;
	}

	public Set<Module> getModulesSet() {
		return this.modules;
	}

	public Set<Module> setModulesSet(Set<Module> modulesSet) {
		Set<Module> oldSet = this.modules;
		this.modules = modulesSet;
		return oldSet;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
