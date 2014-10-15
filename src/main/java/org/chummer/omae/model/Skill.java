package org.chummer.omae.model;

public class Skill {
	String name;
	SkillGroupType group;
	boolean grouped;
	boolean defaulted;
	int rating;
	int base;
	int karma;
	int freeLevels;
	int ratingMax;
	boolean exotic;
	String specialization;
	boolean isKnowledge;
	Attribute uses;
	Source src;
	
	public int total() {
		return rating + uses.total();
	}
}
