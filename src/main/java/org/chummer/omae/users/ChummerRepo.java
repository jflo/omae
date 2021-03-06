package org.chummer.omae.users;

import java.io.IOException;
import java.util.List;

import javax.naming.ldap.LdapName;

public interface ChummerRepo {

	public List<String> getAllChummNames();
	public Chummer getChummer(String ldapDN);
	public void newChummer(Chummer chum) throws IOException;
	public LdapName buildDn(Chummer chum);
	public void updateChummer(Chummer chum);
	public LdapName buildDn(String name);

}
