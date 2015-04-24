package org.chummer.omae.chumfile;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.chummer.omae.model.Metatype;
import org.chummer.omae.model.Movement;
import org.chummer.omae.model.Shadowrunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ChumfileParser {

	private static final Logger log = LoggerFactory.getLogger(ChumfileParser.class);
	
	public Shadowrunner parseFile(File chumfile) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Shadowrunner retval = new Shadowrunner();
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(chumfile);
		XPath xPath =  XPathFactory.newInstance().newXPath();
		
		String metatypePath = "/character/metatype";
		retval.type = Metatype.valueOf(xPath.compile(metatypePath).evaluate(doc).toUpperCase());
		
		String movePath = "/character/movement";
		String movement = xPath.compile(movePath).evaluate(doc);
		Movement move = new Movement();
		move.walk = Integer.parseInt(movement.split("/")[0]);
		move.run = Integer.parseInt(movement.split("/")[1]);
		retval.move = move;
		return retval;
	}
}
