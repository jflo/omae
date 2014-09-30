package org.chummer.omae.runners;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Controller
public class RunnerController {
	
	private Logger log = Logger.getLogger(RunnerController.class);
	
	@Autowired
	private RunnerRepo runners;
	
	@Autowired
	private ServletContext servletContext;

	@Secured("ROLE_PLAYERS")
    @RequestMapping(value="/runner/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("name") String runnerName,
            @RequestParam("file") MultipartFile file, Model model){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String userName = auth.getName();
	    
	    try {
	    	runners.addRunner(userName, runnerName, file);
	    } catch(IOException e) {
	    	log.error("couldn't create new runner for user "+userName, e);
			model.addAttribute("error", e.getMessage());
        	return "addRunner";
	    } finally {
	    	return "redirect:/home";
	    }
	    
    }
	
	@Secured("ROLE_PLAYERS")
	@RequestMapping(value="/runner/uploadForm", method=RequestMethod.GET)
	public String uploadView() {
		return "addRunner";
	}
	
	@Secured("ROLE_PLAYERS")
	@RequestMapping(value="/runner/download/{runnerName}.chum5", method=RequestMethod.GET)
	public void downloadChummer(@PathVariable String runnerName, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		InputStream is = runners.downloadChumFile(userName, runnerName);
		response.setContentType("application/xml");		
		IOUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
	}
	
	@Secured("ROLE_PLAYERS")
	@RequestMapping(value="/runner/play/{runnerName}", method=RequestMethod.GET)
	public void characterSheet(@PathVariable String runnerName, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException, TransformerException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		InputStream is = runners.downloadChumFile(userName, runnerName);
		//Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
				
		TransformerFactory factory = TransformerFactory.newInstance();
		File xsl = new File(servletContext.getRealPath("/WEB-INF/xsl/Shadowrun5.xsl"));
		log.debug("found xsl transform at "+xsl.getAbsolutePath());
		Source xslt = new StreamSource(xsl);
        Transformer transformer = factory.newTransformer(xslt);
        Source sheet = new StreamSource(is);
        
        response.setContentType("text/html");
        transformer.transform(sheet, new StreamResult(response.getOutputStream()));
		//IOUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
		
	}

}