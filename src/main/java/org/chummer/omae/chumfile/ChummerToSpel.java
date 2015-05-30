package org.chummer.omae.chumfile;

import org.chummer.omae.model.Legality;
import org.springframework.util.StringUtils;

public class ChummerToSpel {

	public static String translate(String evaluate) {
		String propertyCase = evaluate.toLowerCase();
		// regex means "everything between [ ] brackets
		String insideBracketsRegex = "\\[(.*)\\]";
		String retval = propertyCase.replaceAll(insideBracketsRegex, "$1");
		return retval;
	}

	public static String translateAvailability(String avail) {
		// Spel expression that parses out the Legality from the string
		// must return an Availability object on parse.
		if (StringUtils.isEmpty(avail)) {
			return "new org.chummer.omae.model.Availability(0,T(org.chummer.omae.model.Legality).LEGAL)";
		} else {
			String legal = "T(org.chummer.omae.model.Legality).LEGAL";
			String valueExp = avail.substring(0, avail.length())
					.toLowerCase();
			if (avail.endsWith("F")) {
				legal = "T(org.chummer.omae.model.Legality).FORBIDDEN";
				valueExp = avail.substring(0, avail.length() - 1).toLowerCase();
			} else if (avail.endsWith("R")) {
				legal = "T(org.chummer.omae.model.Legality).RESTRICTED";
				valueExp = avail.substring(0, avail.length() - 1).toLowerCase();
			} else {

			}
			return "new org.chummer.omae.model.Availability(" + valueExp + ","
					+ legal + ")";
		}
	}

}
