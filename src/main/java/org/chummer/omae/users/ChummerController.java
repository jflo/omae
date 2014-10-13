package org.chummer.omae.users;

import java.io.IOException;

import javax.ws.rs.FormParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChummerController {

	@Autowired
	ChummerRepo chumms;
	
	@Autowired
	GroupRepo groups;
	
	@RequestMapping(value = "/chummer", method = RequestMethod.POST)
	public @ResponseBody Chummer newChummer(@RequestBody Chummer chum) throws IOException {
		chumms.newChummer(chum);
		groups.addMemberToGroup("Players", chum);
		Chummer retval = chumms.getChummer("cn="+chum.getUserName()+",ou=Users" );
		return retval;
	}
	
	@RequestMapping(value="/chummer/{ldapDN}", method = RequestMethod.GET)
	public @ResponseBody Chummer getChummer(@PathVariable String ldapDN ) {
		return chumms.getChummer(ldapDN);
	}
	
	@RequestMapping(value = "/chummer", method = RequestMethod.PUT)
	public @ResponseBody Chummer updateChummer(@RequestBody Chummer chum) {
		chumms.updateChummer(chum);		
		return chumms.getChummer(chum.getId());
	}
	
	
	@RequestMapping(value="/chummer/register", method = RequestMethod.POST)
	public String newChummer(@FormParam(value = "username") String username, @FormParam(value = "email") String email, @FormParam(value = "password") String password, Model model) throws IOException {
		Chummer player = new Chummer();
		player.setEmail(email);
		player.setUserName(username);
		player.setPassword(password);
		chumms.newChummer(player);
		groups.addMemberToGroup("Players", player);
		player = chumms.getChummer("cn="+player.getUserName()+",ou=Users" );
		return "redirect:/home?welcome=yes";
	}
	
	@RequestMapping(value="/reg", method=RequestMethod.GET)
	public String registrationView() {
		return "reg";	
	}
	
}
