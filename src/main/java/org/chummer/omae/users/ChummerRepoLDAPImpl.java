package org.chummer.omae.users;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Repository
public class ChummerRepoLDAPImpl implements ChummerRepo {

	private static final String BASE_DN = "dc=omae,dc=org";
	@Autowired
	private LdapTemplate ldapTemplate;

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	private class ChummerAttributesMapper implements AttributesMapper<Chummer> {
		public Chummer mapFromAttributes(Attributes attrs)
				throws NamingException {
			Chummer person = new Chummer();
			person.setUserName((String) attrs.get("cn").get());
			person.setEmail((String) attrs.get("mail").get());
			return person;
		}
	}

	private Attributes buildAttributes(Chummer chum) {
		Attributes retval = new BasicAttributes();
		BasicAttribute objClassAttribs = new BasicAttribute("objectclass");
		//objClassAttribs.add("top");
		objClassAttribs.add("inetOrgPerson");
		retval.put(objClassAttribs);
		retval.put("givenName", "Shadow");
		retval.put("sn", "Runner");		
		retval.put("cn", chum.getUserName());
		retval.put("mail", chum.getEmail());
		retval.put("userPassword", "shadowrun");
		return retval;
	}

	protected LdapName buildDn(Chummer c) {
		return LdapNameBuilder.newInstance("ou=Runners")				
				.add("cn", c.getUserName()).build();
	}

	@Override
	public void newChummer(Chummer chum) {
		ldapTemplate.bind(buildDn(chum), null, buildAttributes(chum));
	}

	public Chummer getChummer(String ldapDN) {
		return (Chummer) ldapTemplate.lookup(ldapDN,
				new ChummerAttributesMapper());
	}

	public List<Chummer> getAllChums() {
		return ldapTemplate.search(
				query().where("objectclass").is("inetOrgPerson"),
				new ChummerAttributesMapper());
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
