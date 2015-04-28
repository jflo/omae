package org.chummer.omae.model;

import java.util.Map;
import java.util.Set;

public class Shadowrunner {

	public Metatype type;
	public Movement move;
	public String name;
	public Description description;
	public String playername;
	public int karma;
	public SocialStanding rep;
	public int nuyen;
	public AwakenedType awakened;
	Map<AttributeType, Attribute> attributes;
	public float totalEssence;
	int physicalDamage;
	int stunDamage;
	Map<SkillGroupType, SkillGroup> groupsTaken;
	Set<Skill> skills;
	Set<Contact> contacts;
	Set<Armor> armor;
	Set<Weapon> weapons;
	Set<Cyberware> chrome;
	Set<Quality> qualities;
	Lifestyle lifestyle;
	Set<Gear> inventory;
}
