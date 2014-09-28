package org.chummer.omae.users;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
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

	private Attributes buildAttributes(Chummer chum) {
		Attributes retval = new BasicAttributes();
		
		BasicAttribute objClassAttribs = new BasicAttribute("objectclass");
		objClassAttribs.add("inetOrgPerson");
		retval.put(objClassAttribs);
		retval.put("cn", chum.getUserName());
		retval.put("sn", "Amigo");
		retval.put("userPassword", chum.getPassword());
		retval.put("mail", chum.getEmail());
		return retval;
	}

	@Override
	public LdapName buildDn(Chummer c) {
		return LdapNameBuilder.newInstance("ou=Users")			
				.add("cn", c.getUserName()).build();
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
