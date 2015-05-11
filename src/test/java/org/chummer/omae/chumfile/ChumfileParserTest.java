package org.chummer.omae.chumfile;

import static org.junit.Assert.*;

import java.io.File;

import org.chummer.omae.model.AttributeType;
import org.chummer.omae.model.AwakenedType;
import org.chummer.omae.model.Description;
import org.chummer.omae.model.Metatype;
import org.chummer.omae.model.Movement;
import org.chummer.omae.model.Shadowrunner;
import org.junit.Test;

public class ChumfileParserTest {

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
		
	}

}
