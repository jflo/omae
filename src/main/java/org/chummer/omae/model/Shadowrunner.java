package org.chummer.omae.model;

import java.util.Map;
import java.util.Set;

public class Shadowrunner {

	Metatype type;
	Movement move;
	String name;
	Description description;
	String playername;
	int karma;
	SocialStanding rep;
	int nuyen;
	AwakenedType awakened;
	Map<AttributeType, Attribute> attributes;
	float totalEssence;
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
