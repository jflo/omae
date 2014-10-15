package org.chummer.omae.dashboard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.chummer.omae.runners.RunnerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GamePlayController {

	private Logger log = LoggerFactory.getLogger(GamePlayController.class);

	@Autowired
	private RunnerRepo runners;
	
	@Autowired
	private ServletContext servletContext;
	
	@Secured("ROLE_PLAYERS")
	@RequestMapping(value="/dashboard/play/{runnerName}")
	public void play(Model model, @PathVariable String runnerName, HttpServletResponse response) throws IOException, TransformerException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		InputStream is = runners.downloadRenderFile(userName, runnerName);
		//Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
				
		TransformerFactory factory = TransformerFactory.newInstance();
		File xsl = new File(servletContext.getRealPath("/WEB-INF/xsl/dashboard.xsl"));
		log.debug("found xsl transform at "+xsl.getAbsolutePath());
		Source xslt = new StreamSource(xsl);
        Transformer transformer = factory.newTransformer(xslt);
        Source sheet = new StreamSource(is);
        
        response.setContentType("text/html");
        transformer.transform(sheet, new StreamResult(response.getOutputStream()));
	    response.flushBuffer();		
	}
	
	//@Secured("ROLE_PLAYERS")
	//@RequestMapping(value="/dashboard/update/{runnerName}")
	//public void update()
}
