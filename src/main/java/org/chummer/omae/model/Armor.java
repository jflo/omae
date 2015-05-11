package org.chummer.omae.model;

import java.util.Set;

public class Armor {

	public String name;
	public String guid;
	public int armorValue;
	public int availability;
	public int cost;
	public Source source;
	public boolean equipped;
	public int damage;
	public Set<Gear> addonGear;
	public Set<ArmorMod> mods;
}
