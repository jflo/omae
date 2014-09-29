package org.chummer.omae.users;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.BasicAttribute;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class ChummerRepoLDAPImpl implements ChummerRepo {
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}	

	@Override
	public LdapName buildDn(Chummer c) {
		return LdapNameBuilder.newInstance("ou=Users")			
				.add("cn", c.getUserName()).build();
	}
	
	/**
	 * Because sometimes the security sys only give you the username, not a full DN.
	 */
	@Override
	public LdapName buildDn(String name) {
		return LdapNameBuilder.newInstance("ou=Users")			
				.add("cn", name).build();
	}

	@Override
	public void newChummer(Chummer chum) {
		LdapName newChumDn = buildDn(chum);
		BasicAttribute objClassAttribs = new BasicAttribute("objectclass");
		objClassAttribs.add("inetOrgPerson");
		
		DirContextAdapter ctx = new DirContextAdapter(newChumDn);
		ctx.setAttribute(objClassAttribs);
		ctx.setAttributeValue("cn", chum.getUserName());
		ctx.setAttributeValue("sn", "Amigo");
		ctx.setAttributeValue("userPassword", chum.getPassword());
		ctx.setAttributeValue("mail", chum.getEmail());
		
		ldapTemplate.bind(ctx);		
	}
	
	@Override
	public void updateChummer(Chummer chum) {
		LdapName dn = buildDn(chum);
		DirContextOperations ctx = ldapTemplate.lookupContext(dn);
		ctx.setAttributeValue("cn", chum.getUserName());
		ctx.setAttributeValue("sn", "Amigo");
		ctx.setAttributeValue("userPassword", chum.getPassword());
		ctx.setAttributeValue("mail", chum.getEmail());
		ldapTemplate.modifyAttributes(ctx);
		
	}

	public Chummer getChummer(String ldapDN) {
		return (Chummer) ldapTemplate.lookup(ldapDN,
				new ChummerContextMapper());
	}

	public List<Chummer> getAllChums() {
		return ldapTemplate.search(
				query().where("objectclass").is("inetOrgPerson"),
				new ChummerContextMapper());
	}

	@Override
	public List<String> getAllChummNames() {
		List<Chummer> chums = this.getAllChums();
		List<String> chumNames = new ArrayList<String>();
		for (Chummer c : chums) {
			chumNames.add(c.getUserName());
		}
		return chumNames;
	}

	

}
