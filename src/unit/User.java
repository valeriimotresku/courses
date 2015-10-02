/**
 * 
 */
package unit;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author amberu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	//fields
	private final String login;
	private String password;
	
	//constructors
	
	//no-args constructor for JAXB
	public User() {
		this("NoName", UUID.randomUUID().toString());
	}
	
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	//methods
	@Override
	public String toString() {
		return "\nUser [login=" + login + "]";
	}

	//getters setters
	public String getLogin() {
		return this.login;
	}
	
	//after some validation, obvious
	public String getPassword() {
		return this.password;
	}
	
	//after some validation, obvious
	public String setPassword(String newPassword) {
		this.password = newPassword;
		return newPassword;
	}

	
	//hashcode, equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
