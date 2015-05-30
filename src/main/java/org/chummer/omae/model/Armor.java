package org.chummer.omae.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.Expression;

public class Armor {

	public String name;
	public String guid;
	//this is an expression because it can be +2 for things like Helmets, which stack
	public Expression armorValue;
	public Availability availability;
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
			gearTotal = gearTotal + g.getCurrentCapacity();
		}
		for(ArmorMod m : mods) {
			modsTotal = modsTotal + m.getCurrentCapacity();
		}
		return capacity - (gearTotal + modsTotal);
	}
	
	public int getCurrentCost() {
		int gearTotal = 0;
		int modsTotal = 0;
		for(Gear g : addonGear) {
			gearTotal = gearTotal + g.getCost();
		}
		for(ArmorMod m : mods) {
			modsTotal = modsTotal + m.getCost();
		}
		return cost + (gearTotal + modsTotal);
	}
	
	public Availability getCurrentAvailability() {
		//total of all availability values across mods/gears
		//plus the most illegal legality
		//List<Integer> vals = new ArrayList<Integer>();
		//vals.add(this.availability.value);
		int val = this.availability.value;
		List<Legality> legals = new ArrayList<Legality>();
		legals.add(this.availability.legal);
		for(Gear g: addonGear) {
			Availability a = g.getCurrentAvailability();
			//vals.add(a.value);
			val += a.value;
			legals.add(a.legal);
		}
		for(ArmorMod m : mods) {
			Availability a = m.availability;
			//vals.add(a.value);
			val += a.value;
			legals.add(a.legal);
		}
		//vals.sort(null);
		legals.sort(null);
		Availability retval = new Availability();
		retval.legal = legals.get(legals.size()-1);
		//retval.value = vals.get(vals.size()-1);
		retval.value = val;
		return retval;
	}
}
