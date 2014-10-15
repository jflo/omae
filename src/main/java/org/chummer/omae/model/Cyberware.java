package org.chummer.omae.model;

import java.util.Set;

public class Cyberware {

	String guid;
	String name;
	CyberCategory category;
	float essence;
	int cost;
	int rating;
	Source source;
	int minRating;
	int maxRating;
	CyberGrade grade;
	int capacity;
	Set<Cyberware> improvements;
	//StatBonus statBonus; armor?
	AttributeBonus attribBonus;
}
