package org.chummer.omae.users;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

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
	public List<String> getAllPersonNames() {
	      return ldapTemplate.search(
	         query().where("objectclass").is("inetOrgPerson"),
	         new AttributesMapper<String>() {
	            public String mapFromAttributes(Attributes attrs)
	               throws NamingException {
	               return (String) attrs.get("cn").get();
	            }
	         });
	   }
}
