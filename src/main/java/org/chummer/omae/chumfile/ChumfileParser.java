package org.chummer.omae.chumfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.chummer.omae.model.Armor;
import org.chummer.omae.model.ArmorMod;
import org.chummer.omae.model.Attribute;
import org.chummer.omae.model.AttributeType;
import org.chummer.omae.model.Availability;
import org.chummer.omae.model.AwakenedType;
import org.chummer.omae.model.Book;
import org.chummer.omae.model.Contact;
import org.chummer.omae.model.Description;
import org.chummer.omae.model.Gear;
import org.chummer.omae.model.GearCategory;
import org.chummer.omae.model.Metatype;
import org.chummer.omae.model.Movement;
import org.chummer.omae.model.Shadowrunner;
import org.chummer.omae.model.Skill;
import org.chummer.omae.model.SkillGroupType;
import org.chummer.omae.model.SocialStanding;
import org.chummer.omae.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ChumfileParser {

	private static final Logger log = LoggerFactory.getLogger(ChumfileParser.class);
	XPath xPath =  XPathFactory.newInstance().newXPath();
	ExpressionParser parser = new SpelExpressionParser();
	
	public Shadowrunner parseFile(File chumfile) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		Shadowrunner retval = new Shadowrunner();
		DocumentBuilderFactory builderFactory =
		        DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = null;
		
		builder = builderFactory.newDocumentBuilder();
		
		
		InputStream inputStream= new FileInputStream(chumfile);
        Reader reader = new InputStreamReader(inputStream,"UTF-16");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-16");
        Document doc = builder.parse(is);
		
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
		retval.attributes = this.parseAttributes(doc);
		retval.skills = this.parseSkills(doc, retval);
		retval.contacts = this.parseContacts(doc);
		retval.armor = this.parseArmor(doc);
		return retval;
		
	}
	
	private Set<Armor> parseArmor(Document doc) throws XPathExpressionException {
		Set<Armor> armors = new HashSet<Armor>();
		NodeList armorsNL = (NodeList) xPath.compile("/character/armors/armor").evaluate(doc, XPathConstants.NODESET);
		for(int i=0; i< armorsNL.getLength(); i++) {
			Node node = armorsNL.item(i);
			Armor a = new Armor();
			NodeList armorDetails = node.getChildNodes();
			for(int j=0; j<armorDetails.getLength(); j++) {
				Node deet = armorDetails.item(j);
				//TODO; just use a new expression from this node
				a.guid = (String)xPath.compile("guid").evaluate(node, XPathConstants.STRING);
				a.name = (String)xPath.compile("name").evaluate(node, XPathConstants.STRING);
				a.cost = Integer.parseInt((String)xPath.compile("cost").evaluate(node, XPathConstants.STRING));
				String spel = ChummerToSpel.translate((String)xPath.compile("armor").evaluate(node, XPathConstants.STRING));
				Expression exp = parser.parseExpression(spel);
				a.armorValue = exp;
				a.capacity = Integer.parseInt((String)xPath.compile("armorcapacity").evaluate(node, XPathConstants.STRING));
				a.availability = Availability.parse((String)xPath.compile("avail").evaluate(node, XPathConstants.STRING));
				NodeList gears = ((Node)xPath.compile("gears").evaluate(node, XPathConstants.NODE)).getChildNodes();
				a.addonGear = parseGears(gears);
				a.source = parseSource(node);
				NodeList mods = ((Node)xPath.compile("armormods").evaluate(node,  XPathConstants.NODE)).getChildNodes();
				a.mods = parseArmorMods(mods, a);
			}
			armors.add(a);
		}
		return armors;
	}
	
	private ArmorMod parseArmorMod(Node node) throws XPathExpressionException {
		ArmorMod retval = new ArmorMod();
		retval.guid = (String)xPath.compile("guid").evaluate(node, XPathConstants.STRING);
		retval.name = (String)xPath.compile("name").evaluate(node, XPathConstants.STRING);
		retval.category = (String)xPath.compile("category").evaluate(node, XPathConstants.STRING);
		String spel = ChummerToSpel.translate((String)xPath.compile("armor").evaluate(node, XPathConstants.STRING));
		retval.armor = parser.parseExpression(spel);
		spel = ChummerToSpel.translate((String)xPath.compile("armorcapacity").evaluate(node, XPathConstants.STRING));
		retval.capacity = parser.parseExpression(spel);
		retval.maxRating = Integer.parseInt((String)xPath.compile("maxrating").evaluate(node, XPathConstants.STRING));
		retval.rating = Integer.parseInt((String)xPath.compile("rating").evaluate(node, XPathConstants.STRING));
		
		retval.availability = Availability.parse(((String)xPath.compile("avail").evaluate(node, XPathConstants.STRING)));
		retval.costExpression = (String)xPath.compile("cost").evaluate(node, XPathConstants.STRING);
		retval.source = parseSource(node);
		return retval;
	}
	
	private List<ArmorMod> parseArmorMods(NodeList nodes, Armor a) throws XPathExpressionException {
		List<ArmorMod> retval = new ArrayList<ArmorMod>();
		for(int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if("armormod".equals(node.getNodeName())) {
				ArmorMod mod = parseArmorMod(node);
				mod.installedIn = a;
				retval.add(mod);
			}
		}
		return retval;
	}
	
	private List<Gear> parseGears(NodeList gears) throws XPathExpressionException {
		List<Gear> retval = new ArrayList<Gear>();
		for(int i=0; i<gears.getLength(); i++) {
			Node gearNode = gears.item(i);
			if("gear".equals(gearNode.getNodeName())) {
				retval.add(parseGear(gearNode));
			}
		}
		return retval;
	}
	
	private Gear parseGear(Node node) throws XPathExpressionException {
		Gear retval = new Gear();
		retval.guid = (String) xPath.compile("guid").evaluate(node, XPathConstants.STRING);
		retval.name = (String) xPath.compile("name").evaluate(node, XPathConstants.STRING);
		String gearCategoryName = (String) xPath.compile("category").evaluate(node, XPathConstants.STRING);
		retval.category = GearCategory.valueOf(gearCategoryName.toUpperCase().replaceAll(" ", "_"));
		retval.rating = Integer.parseInt((String)xPath.compile("rating").evaluate(node, XPathConstants.STRING));
		//time to figure out expression parsing
		String spel = ChummerToSpel.translate((String)xPath.compile("capacity").evaluate(node, XPathConstants.STRING));
		Expression exp = parser.parseExpression(spel);
		
		retval.capacity = exp;
		//retval.armorCapacity = Integer.parseInt((String)xPath.compile("armorcapacity").evaluate(node, XPathConstants.STRING));
		retval.minRating = Integer.parseInt((String)xPath.compile("minrating").evaluate(node, XPathConstants.STRING));
		retval.maxRating = Integer.parseInt((String)xPath.compile("maxrating").evaluate(node, XPathConstants.STRING));
		try {
			retval.quantity = Integer.parseInt((String)xPath.compile("quantity").evaluate(node, XPathConstants.STRING));
		} catch(NumberFormatException nfe) {
			//log.info("error getting quantity, it IS optional after all");
		}
		spel = ChummerToSpel.translate((String)xPath.compile("cost").evaluate(node, XPathConstants.STRING));
		retval.cost = parser.parseExpression(spel);
		spel = ChummerToSpel.translateAvailability((String)xPath.compile("avail").evaluate(node, XPathConstants.STRING));
		retval.availability = parser.parseExpression(spel);
		return retval;
	}

	private Source parseSource(Node node) throws XPathExpressionException {
		Source retval = new Source();
		String book = (String) xPath.compile("source").evaluate(node, XPathConstants.STRING);
		String page = (String) xPath.compile("page").evaluate(node, XPathConstants.STRING);
		if(!StringUtils.isEmpty(book)) {
			retval.book = Book.valueOf(book);
		}
		if(!StringUtils.isEmpty(page)) {
			retval.page = Integer.parseInt(page);
		}
		return retval;
	}
	
	private Map<String, Contact> parseContacts(Document doc) throws XPathExpressionException {
		Map<String, Contact> contacts = new HashMap<String, Contact>();
		NodeList contactsNL = (NodeList) xPath.compile("/character/contacts/contact").evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i< contactsNL.getLength(); i++) {
			Node node = contactsNL.item(i);
			Contact c = new Contact();
			NodeList contactDetails = node.getChildNodes();
			for(int j=0; j<contactDetails.getLength(); j++) {
				Node deet = contactDetails.item(j);
				//TODO; just use a new expression from this node
				if("name".equals(deet.getNodeName())) {
					c.name = deet.getTextContent();
				} else if ("role".equals(deet.getNodeName())) {
					c.role = deet.getTextContent();
				} else if ("location".equals(deet.getNodeName())) {
					c.location = deet.getTextContent();
				} else if ("connection".equals(deet.getNodeName())) {
					c.connection = Integer.parseInt(deet.getTextContent());
				} else if ("loyalty".equals(deet.getNodeName())) {
					c.loyalty = Integer.parseInt(deet.getTextContent());
				} 
			}
			contacts.put(c.name, c);
		}
		return contacts;
	}

	private Map<String, Skill> parseSkills(Document doc, Shadowrunner sr) throws XPathExpressionException {
		Map<String, Skill> skills = new HashMap<String, Skill>();
		NodeList skillsNL = (NodeList) xPath.compile("/character/skills/skill").evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i< skillsNL.getLength(); i++) {
			Node node = skillsNL.item(i);
			Skill s = new Skill();
			Source src = new Source();
			NodeList skillDetails = node.getChildNodes();
			for(int j=0; j<skillDetails.getLength(); j++) {
				Node deet = skillDetails.item(j);
				//TODO; just use a new expression from this node
				if("name".equals(deet.getNodeName())) {
					s.name = deet.getTextContent();
				} else if("skillgroup".equals(deet.getNodeName())){
					String rawGroupName = deet.getTextContent();
					rawGroupName = rawGroupName.toUpperCase();
					rawGroupName = rawGroupName.replaceAll(" ", "");
					if(!StringUtils.isEmpty(rawGroupName)) {
						s.group = SkillGroupType.valueOf(rawGroupName);
					}
				} else if("skillcategory".equals(deet.getNodeName())) {
					s.category = deet.getTextContent();					
				} else if("grouped".equals(deet.getNodeName())) {
					s.grouped = Boolean.parseBoolean(deet.getTextContent());
				} else if("default".equals(deet.getNodeName())) {
					s.defaulted = Boolean.parseBoolean(deet.getTextContent());
				} else if("rating".equals(deet.getNodeName())) {
					s.rating = Integer.parseInt(deet.getTextContent());
				} else if("base".equals(deet.getNodeName())) {
					s.base = Integer.parseInt(deet.getTextContent());
				} else if("ratingmax".equals(deet.getNodeName())) {
					s.ratingMax = Integer.parseInt(deet.getTextContent());
				} else if("knowledge".equals(deet.getNodeName())) {
					s.isKnowledge = Boolean.parseBoolean(deet.getTextContent());
				} else if("exotic".equals(deet.getNodeName())) {
					s.exotic = Boolean.parseBoolean(deet.getTextContent());
				} else if("spec".equals(deet.getNodeName())) {
					s.specialization = deet.getTextContent();
				} else if("attribute".equals(deet.getNodeName())) {
					s.uses = sr.attributes.get(AttributeType.valueOf(deet.getTextContent()));
				} else if("source".equals(deet.getNodeName())) {
					if(!StringUtils.isEmpty(deet.getTextContent())) {
						src.book = Book.valueOf(deet.getTextContent());
					}
				} else if("page".equals(deet.getNodeName())) {
					if(!StringUtils.isEmpty(deet.getTextContent())) {
						src.page = Integer.parseInt(deet.getTextContent());
					}
				}
			}
			s.src = src;
			skills.put(s.name, s);
		}
		return skills;
	}

	private Map<AttributeType, Attribute> parseAttributes(Document doc) throws XPathExpressionException {
		Map<AttributeType, Attribute> attribs = new HashMap<AttributeType, Attribute>();
		NodeList attribNL = (NodeList) xPath.compile("/character/attributes/attribute").evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i< attribNL.getLength(); i++) {
			Node node = attribNL.item(i);
			Attribute a = new Attribute();
			NodeList attribDetails = node.getChildNodes();
			for(int j=0; j<attribDetails.getLength(); j++) {
				Node deet = attribDetails.item(j);
				//TODO just use a new xPath expression from the new node.
				if("name".equals(deet.getNodeName())) {
					a.type = AttributeType.valueOf(AttributeType.class, deet.getTextContent());
				} else if ("metatypemin".equals(deet.getNodeName())) {
					a.metatypeMin = Integer.parseInt(deet.getTextContent());
				} else if ("metatypemax".equals(deet.getNodeName())) {
					a.metatypeMax = Integer.parseInt(deet.getTextContent());
				} else if ("metatypeaugmax".equals(deet.getNodeName())) {
					a.metatypeAugMax = Integer.parseInt(deet.getTextContent());
				} else if ("value".equals(deet.getNodeName())) {
					a.value = Integer.parseInt(deet.getTextContent());
				} else if ("base".equals(deet.getNodeName())) {
					a.base = Integer.parseInt(deet.getTextContent());
				} else if ("augmodifier".equals(deet.getNodeName())) {
					a.augMod = Integer.parseInt(deet.getTextContent());
				} 
			}
			attribs.put(a.type, a);
		}
		return attribs;
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
