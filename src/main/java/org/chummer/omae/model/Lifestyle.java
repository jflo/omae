package org.chummer.omae.model;

import java.util.Set;

public class Lifestyle {
	String guid;
	String name;
	LifestyleType grade;
	int perMonthCost;
	int monthsPaid;
	int percentPaid;
	Source source;
	String notes;
	Set<LifestyleQuality> extras;
}
