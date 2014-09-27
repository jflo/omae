package org.chummer.omae.users;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public interface ChummerRepo {

	public abstract List<String> getAllPersonNames();

}
