package org.chummer.omae.users;

import java.util.List;

import javax.naming.ldap.LdapName;

public interface ChummerRepo {

	public List<String> getAllChummNames();
	public Chummer getChummer(String ldapDN);
	public void newChummer(Chummer chum);
	public LdapName buildDn(Chummer c);

}
