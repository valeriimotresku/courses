package unit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import tools.StringOperator;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "school")
public class School {
	
	//fields
	@XmlElementWrapper(name = "courses-set")
	@XmlElement(name = "course")
	private Set<Course> courses = new HashSet<>();
	@XmlElementWrapper(name = "users-set")
	@XmlElement(name = "user")
	private Set<User> users = new HashSet<>();
	
	//constructors
	
	public School() {
		super();
	}
	
	public School(Set<Course> coursesSet, Set<User> usersSet) {
		courses = coursesSet;
		users = usersSet;
	}

	//methods
	// Natalia Romanenko note:
	// TASK: print all courses starting no later than a week from today
	public List<Course> getCurrentWeekCourses() {
		List<Course> result = new ArrayList<>();
		//initialize current date object
		Calendar today = Calendar.getInstance();
		//initialize next week from today date object
		Calendar nextWeek = Calendar.getInstance();
		nextWeek.roll(Calendar.DAY_OF_MONTH, 7);
		for (Course course : this.courses) {
			if (course.getDateStart().after(today) 
					&& course.getDateStart().before(nextWeek)) {
			    result.add(course);
			}
		}
		return result;
	}
	
	
	//courses
	public boolean addCourse(Course course) {
		return this.courses.add(course);
	}
	public Course getCourse(String name) {
		for (Course course : this.courses) {
			if (StringOperator.compareTrimedCaseInsesitive(name, course.getName())) {
				return course;
			}
		}
		return null;
	}
	public boolean setCourse(String name, Course course) {
		Iterator<Course> iterator = this.courses.iterator();
		while (iterator.hasNext()) {
			Course currentCourse = iterator.next(); 
			if (StringOperator.compareTrimedCaseInsesitive(name, currentCourse.getName())) {
				iterator.remove();
				this.courses.add(course);
				return true;
			}
		}
		return false;
	}
	public Course removeCourse(String name) {
		Iterator<Course> iterator = this.courses.iterator();
		while (iterator.hasNext()) {
			Course course = iterator.next(); 
			if (StringOperator.compareTrimedCaseInsesitive(name, course.getName())) {
				iterator.remove();
				return course;
			}
		}
		return null;
	}
	
	//users
	public boolean addUser(User user) {
		return this.users.add(user);
	}
	public User getUser(String name) {
		for (User user : this.users) {
			if (StringOperator.compareTrimedCaseInsesitive(name, user.getLogin())) {
				return user;
			}
		}
		return null;
	}
	public boolean setUser(String name, User user) {
		Iterator<User> iterator = this.users.iterator();
		while (iterator.hasNext()) {
			User currentUser = iterator.next(); 
			if (StringOperator.compareTrimedCaseInsesitive(name, currentUser.getLogin())) {
				iterator.remove();
				this.users.add(user);
				return true;
			}
		}
		return false;
	}
	public User removeUser(String name) {
		Iterator<User> iterator = this.users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next(); 
			if (StringOperator.compareTrimedCaseInsesitive(name, user.getLogin())) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}

	
	//setters getters
	public Set<Course> getCoursesSet() {
		return courses;
	}

	public void setCoursesSet(Set<Course> coursesSet) {
		courses = coursesSet;
	}

	public Set<User> getUsersSet() {
		return users;
	}

	public void setUsersSet(Set<User> usersSet) {
		users = usersSet;
	}

	@Override
	public String toString() {
		return "School [courses=\n" + courses + ", \nUsersSet=\n" + users
				+ "]";
	}

	//hashcode, equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courses == null) ? 0 : courses.hashCode());
		result = prime * result
				+ ((users == null) ? 0 : users.hashCode());
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
		School other = (School) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	
	
}
