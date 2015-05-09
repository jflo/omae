package org.chummer.omae.chumfile;

import static org.junit.Assert.*;

import java.io.File;

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
			sr = parser.parseFile(new File("src/test/resources/Castle Career.chum5"));
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(sr != null);
		assertTrue(Metatype.HUMAN.equals(sr.type));
		Movement m = new Movement();
		m.walk = 12;
		m.run = 24;
		assertTrue(sr.move.equals(m));
		assertTrue("Frank Castle".equals(sr.name));
		
		assertTrue(sr.description.age == 48);
		assertTrue("240".equals(sr.description.weight));
		assertTrue("Justin".equals(sr.playername));
		assertTrue(sr.karma == 16);
		assertTrue(sr.rep.streetcred == 0);
		assertTrue(sr.nuyen == 53994);
		assertTrue(sr.awakened == AwakenedType.MUNDANE);
		
	}

}
