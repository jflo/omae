package org.chummer.omae.chumfile;

public class ChummerToSpel {

	public static String translate(String evaluate) {
		String propertyCase =  evaluate.toLowerCase();
		//regex means "everything between [ ] brackets
		String insideBracketsRegex = "\\[(.*)\\]";
		String retval = propertyCase.replaceAll(insideBracketsRegex, "$1");
		return retval;
	}

}
