package org.chummer.omae;

import java.util.List;
import java.util.Locale;

import org.chummer.omae.users.ChummerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Secured("ROLE_PLAYERS")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		List<String> chumNames = chummers.getAllChummNames();
		model.addAttribute("userCount", chumNames.size());
		return "home";
	}
	
	public ChummerRepo getChummers() {
		return chummers;
	}
	public void setChummers(ChummerRepo chummers) {
		this.chummers = chummers;
	}
	
}
