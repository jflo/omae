package org.chummer.omae.model;

public class Skill {
	public String name;
	public SkillGroupType group;
	public boolean grouped;
	public boolean defaulted;
	public int rating;
	public int base;
	public int karma;
	public int freeLevels;
	public int ratingMax;
	public boolean exotic;
	public String specialization;
	public boolean isKnowledge;
	public Attribute uses;
	public Source src;
	public String category;
	
	public int pool() {
		return rating + uses.total();
	}
}
