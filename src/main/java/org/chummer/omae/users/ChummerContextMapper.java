package org.chummer.omae.users;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

public class ChummerContextMapper implements ContextMapper<Chummer> {
	
	public Chummer mapFromContext(Object ctx) {
		DirContextAdapter context = (DirContextAdapter) ctx;
		Chummer c = new Chummer();
		c.setUserName(context.getStringAttribute("cn"));
		c.setEmail(context.getStringAttribute("mail"));
		c.setId(context.getDn().toString());
		return c;
	}
	
	
}