package org.chummer.omae.users;

import javax.naming.Name;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepo implements BaseLdapNameAware {
		@Autowired
	    private LdapTemplate ldapTemplate;
		
		@Autowired
		private ChummerRepo chums;

	    public void setLdapTemplate(LdapTemplate ldapTemplate) {
	        this.ldapTemplate = ldapTemplate;
	    }

	    public void setBaseLdapPath(LdapName baseLdapPath) {
	        this.setBaseLdapPath(baseLdapPath);
	    }

	    public void addMemberToGroup(String groupName, Chummer c) {
	        Name groupDn = buildGroupDn(groupName);
	        Name userDn = LdapNameBuilder.newInstance("dc=omae,dc=org").add(chums.buildDn(c)).build();

	        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
	        ctx.addAttributeValue("member", userDn);

	        ldapTemplate.modifyAttributes(ctx);
	    }

	    public void removeMemberFromGroup(String groupName, Chummer c) {
	        Name groupDn = buildGroupDn(groupName);
	        Name userDn = chums.buildDn(c);

	        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
	        ctx.removeAttributeValue("member", userDn);

	        ldapTemplate.modifyAttributes(ctx);
	    }

	    private Name buildGroupDn(String groupName) {
	        return LdapNameBuilder.newInstance("ou=Groups")
	            .add("cn", groupName).build();
	    }

	    
	}