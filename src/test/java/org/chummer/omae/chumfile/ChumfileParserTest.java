package org.chummer.omae.chumfile;

import static org.junit.Assert.*;

import java.io.File;

import org.chummer.omae.model.Armor;
import org.chummer.omae.model.ArmorMod;
import org.chummer.omae.model.AttributeType;
import org.chummer.omae.model.AwakenedType;
import org.chummer.omae.model.Book;
import org.chummer.omae.model.Description;
import org.chummer.omae.model.Gear;
import org.chummer.omae.model.GearCategory;
import org.chummer.omae.model.Metatype;
import org.chummer.omae.model.Movement;
import org.chummer.omae.model.Shadowrunner;
import org.junit.Test;

public class ChumfileParserTest {

	//TODO: refactorme. break up into test cases for each subsection. especially stuff that stacks like gear and armor and cyber
	@Test
	public void test() {
		ChumfileParser parser = new ChumfileParser();
		Shadowrunner sr = null;
		try {
			sr = parser.parseFile(new File("src/test/resources/TinkerCareer.chum5"));
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(sr != null);
		assertTrue(Metatype.ELF.equals(sr.type));
		Movement m = new Movement();
		m.walk = 4;
		m.run = 8;
		assertTrue(sr.move.equals(m));
		assertTrue("Tinker".equals(sr.name));
		
		assertTrue(sr.description.age == 22);
		assertTrue("190".equals(sr.description.weight));
		assertTrue("Jflo".equals(sr.playername));
		assertTrue(sr.karma == 0);
		assertTrue(sr.rep.streetcred == 0);
		assertTrue(sr.nuyen == 5536);
		assertTrue(sr.awakened == AwakenedType.MUNDANE);
		assertNotNull(sr.attributes);
		assertTrue(sr.attributes.size() > 0);
		assertTrue(sr.attributes.get(AttributeType.BOD).base == 3);
		
		assertNotNull(sr.skills);
		assertTrue(sr.skills.size() > 0);
		assertTrue(sr.skills.get("Aeronautics Mechanic").pool() == 6);
		
		assertNotNull(sr.contacts);
		assertTrue(sr.contacts.size() == 2);
		assertTrue(sr.contacts.get("Frank Sobotka").loyalty == 2);
		
		assertNotNull(sr.armor);
		assertTrue(sr.armor.size() == 2);
		for(Armor a : sr.armor) {
			if("87897ed2-57d7-4bde-9445-05785bc16cac".equals(a.guid)) {
				assertEquals(2, a.getCurrentArmorValue());
				assertEquals(Book.SR5, a.source.book);
				assertNotNull(a.addonGear);
				assertEquals(1, a.addonGear.size());
				for(Gear g : a.addonGear) {
					assertTrue("Audio Enhancement".equals(g.name));
					assertTrue(GearCategory.AUDIO_ENHANCEMENTS.equals(g.category));
					assertEquals(2f, g.getCurrentCapacity(), 0.001f);
					assertEquals(1000, g.getCost());
				}
				assertEquals(1, a.mods.size());
				for(ArmorMod mod : a.mods) {
					assertEquals(200, mod.getCost());
					assertEquals(3f, mod.getCurrentCapacity(), 0.001f);
				}
				assertEquals(1, a.getCurrentCapacity(), 0.001f);
				
				//test availability stacking
				//test armor stacking
				//test cost stacking
			}
		}
		
	}

}
