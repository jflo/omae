package org.chummer.omae.users;

import java.io.Serializable;

import org.springframework.ldap.odm.annotations.Entry;

@Entry(objectClasses = { "person" })
public class Chummer implements Serializable{

	private static final long serialVersionUID = 1L;
	
		private String id;
	    private String userName;
	    private String email;
	    private String password;
	    		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		
}
