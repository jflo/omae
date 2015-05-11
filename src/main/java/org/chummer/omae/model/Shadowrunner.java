package org.chummer.omae.model;

import java.util.Map;
import java.util.Set;

public class Shadowrunner {

	//these are all public because i totes don't care
	public Metatype type;
	public Movement move;
	public String name;
	public Description description;
	public String playername;
	public int karma;
	public SocialStanding rep;
	public int nuyen;
	public AwakenedType awakened;
	public Map<AttributeType, Attribute> attributes;
	public float totalEssence;
	public int physicalDamage;
	public int stunDamage;
	public Map<SkillGroupType, SkillGroup> groupsTaken;
	public Set<Skill> skills;
	public Set<Contact> contacts;
	public Set<Armor> armor;
	public Set<Weapon> weapons;
	public Set<Cyberware> chrome;
	public Set<Quality> qualities;
	public Lifestyle lifestyle;
	public Set<Gear> inventory;
}
