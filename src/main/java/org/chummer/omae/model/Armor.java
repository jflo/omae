package org.chummer.omae.model;

import java.util.List;

import org.springframework.expression.Expression;

public class Armor {

	public String name;
	public String guid;
	//this is an expression because it can be +2 for things like Helmets, which stack
	public Expression armorValue;
	public int availability;
	public int cost;
	public Source source;
	public boolean equipped;
	public int damage;
	public int capacity;
	public List<Gear> addonGear;
	public List<ArmorMod> mods;
	
	public int getCurrentArmorValue() {
		return armorValue.getValue(this, Integer.class);
	}

	public float getCurrentCapacity() {
		float gearTotal = 0.0f;
		float modsTotal = 0.0f;
		for(Gear g : addonGear) {
			gearTotal = gearTotal + Math.abs(g.getCurrentCapacity());
		}
		for(ArmorMod m : mods) {
			modsTotal = modsTotal + Math.abs(m.getCurrentCapacity());
		}
		return capacity - (gearTotal + modsTotal);
	}
}
