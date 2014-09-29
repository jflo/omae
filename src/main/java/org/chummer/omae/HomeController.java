package org.chummer.omae;

import java.io.IOException;

import org.chummer.omae.runners.RunnerRepo;
import org.chummer.omae.users.Chummer;
import org.chummer.omae.users.ChummerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ChummerRepo chummers;
	
	@Autowired
	private RunnerRepo runners;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException if we can't load a list of runners for the logged in user.
	 */
	@Secured("ROLE_PLAYERS")
	@RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
	public String home(Model model) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
		Chummer chum = chummers.getChummer(chummers.buildDn(name).toString());
		model.addAttribute("user", chum);
		model.addAttribute("runners", runners.getRunners(name));
		return "home";
	}
	
	public ChummerRepo getChummers() {
		return chummers;
	}
	public void setChummers(ChummerRepo chummers) {
		this.chummers = chummers;
	}
	
}
