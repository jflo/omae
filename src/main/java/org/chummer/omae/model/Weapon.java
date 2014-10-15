package org.chummer.omae.model;

import java.util.Set;

public class Weapon {

	String name;
	String guid;
	WeaponType type;
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
	
}
