package org.chummer.omae.chumfile;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.chummer.omae.model.AwakenedType;
import org.chummer.omae.model.Description;
import org.chummer.omae.model.Metatype;
import org.chummer.omae.model.Movement;
import org.chummer.omae.model.Shadowrunner;
import org.chummer.omae.model.SocialStanding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ChumfileParser {

	private static final Logger log = LoggerFactory.getLogger(ChumfileParser.class);
	XPath xPath =  XPathFactory.newInstance().newXPath();
	
	public Shadowrunner parseFile(File chumfile) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Shadowrunner retval = new Shadowrunner();
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(chumfile);
		
		
		String metatypePath = "/character/metatype";
		retval.type = Metatype.valueOf(xPath.compile(metatypePath).evaluate(doc).toUpperCase());
		
		String movePath = "/character/movement";
		String movement = xPath.compile(movePath).evaluate(doc);
		Movement move = new Movement();
		move.walk = Integer.parseInt(movement.split("/")[0]);
		move.run = Integer.parseInt(movement.split("/")[1]);
		retval.move = move;
		
		retval.name = xPath.compile("/character/name").evaluate(doc);
		retval.description = this.parseDescription(doc);
		retval.playername = xPath.compile("/character/playername").evaluate(doc);
		retval.karma = Integer.parseInt(xPath.compile("/character/karma").evaluate(doc));
		retval.rep = this.parseRep(doc);
		retval.nuyen = Integer.parseInt(xPath.compile("/character/nuyen").evaluate(doc));
		retval.awakened = this.parseAwake(doc);
		retval.totalEssence = Float.parseFloat(xPath.compile("/character/totaless").evaluate(doc));
		return retval;
	}

	private AwakenedType parseAwake(Document doc) throws XPathExpressionException {
		AwakenedType retval = AwakenedType.MUNDANE;
		if(Boolean.parseBoolean(xPath.compile("/character/adept").evaluate(doc))) {
			retval = AwakenedType.ADEPT;
		}
		if(Boolean.parseBoolean(xPath.compile("/character/magician").evaluate(doc))) {
			retval = AwakenedType.MAGICIAN;
		}
		if(Boolean.parseBoolean(xPath.compile("/character/technomancer").evaluate(doc))) {
			retval = AwakenedType.TECHNOMANCER;
		}
		return retval;
	}

	private SocialStanding parseRep(Document doc) throws NumberFormatException, XPathExpressionException {
		SocialStanding rep = new SocialStanding();
		rep.burntstreetcred = Integer.parseInt(xPath.compile("/character/burntstreetcred").evaluate(doc));
		rep.notoriety = Integer.parseInt(xPath.compile("/character/notoriety").evaluate(doc));
		rep.streetcred = Integer.parseInt(xPath.compile("/character/streetcred").evaluate(doc));
		rep.publicawareness = Integer.parseInt(xPath.compile("/character/publicawareness").evaluate(doc));
		return rep;
	}

	private Description parseDescription(Document doc) throws NumberFormatException, XPathExpressionException {
		Description d = new Description();
		d.age = Integer.parseInt(xPath.compile("/character/age").evaluate(doc));
		d.alias = xPath.compile("/character/alias").evaluate(doc);
		d.background = xPath.compile("/character/background").evaluate(doc);
		d.concept = xPath.compile("/character/concept").evaluate(doc);
		d.description = xPath.compile("/character/description").evaluate(doc);
		d.eyes = xPath.compile("/character/eyes").evaluate(doc);
		d.hair = xPath.compile("/character/hair").evaluate(doc);
		d.height = xPath.compile("/character/height").evaluate(doc);
		d.notes = xPath.compile("/character/notes").evaluate(doc);
		d.sex = xPath.compile("/character/sex").evaluate(doc);
		d.skin = xPath.compile("/character/skin").evaluate(doc);
		d.weight = xPath.compile("/character/weight").evaluate(doc);
		return d;
	}
}
