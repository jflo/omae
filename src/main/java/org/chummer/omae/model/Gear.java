package org.chummer.omae.model;

import org.springframework.expression.Expression;

public class Gear implements Costs, Carries {

	public String guid;
	public String name;
	public GearCategory category;
	public Expression capacity;
	public int armorCapacity;
	public int minRating;
	public int maxRating;
	public int rating;
	public int quantity;
	public Availability availability;
	public Expression cost;
	public String extra;
	public boolean bonded;
	public boolean equipped;
	public Source source;
	
	public float getCurrentCapacity() {
		//todo, subtract what is used by child gears?
		if(capacity != null) {
			return capacity.getValue(this, Float.class);
		} else {
			return 0.0f;
		}
	}
	
	public int getCost() {
		if(cost != null) {
			return cost.getValue(this, Integer.class);
		} else {
			return 0;
		}
	}
	
}
