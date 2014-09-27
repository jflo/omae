package org.chummer.omae.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChummerController {

	@Autowired
	ChummerRepo repo;
	
	@RequestMapping(value = "/chummer", method = RequestMethod.POST)
	public @ResponseBody Chummer newChummer(@RequestBody Chummer chum) {
		repo.newChummer(chum);
		Chummer retval = repo.getChummer("cn="+chum.getUserName()+",ou=Runners");
		return retval;
	}
	
	@RequestMapping(value="/chummer/{ldapDN}", method = RequestMethod.GET)
	public @ResponseBody Chummer getChummer(@PathVariable String ldapDN ) {
		return repo.getChummer(ldapDN);
	}
	
}
