package org.chummer.omae.model;

import java.util.Set;

public class Weapon {

	String name;
	String guid;
	WeaponType type;
	WeaponCategory category;
	Damage damage;
	int armorPen;
	int concealability;
	Availability availability;
	int cost;
	Skill uses;
	int range;
	Set<FiringModes> modes;
	int recoilComp;
	AmmoCapacity maxAmmo;
	int rangeMultiplier;
	int fullBurst;
	int suppressive;
	Source source;
	String petName;
	int accuracy;
	Set<WeaponAccessory> accessories;
	Weapon underBarrel;
}
