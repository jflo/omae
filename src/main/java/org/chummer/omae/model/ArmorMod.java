package org.chummer.omae.model;

import org.springframework.expression.Expression;

public class ArmorMod implements Costs, Carries {
	public String guid;
	public String name;
	public String category;
	public Expression armor;
	public Expression capacity;
	public int maxRating;
	public int rating;
	public Expression availability;
	public Expression cost;
	public Source source;
	public boolean equipped;
	public Armor installedIn;
	
	public float getCurrentCapacity() {
		if(capacity != null) {
			return capacity.getValue(installedIn, Float.class);
		} else {
			return 0.0f;
		}
	}
	
	public int getCost() {
		if(cost != null) {
			//depends on the Expression actually, if it contained "Armor" then you want to pass in what this is installed in.
			
			//otherwise, you likely want to use "this" since the expression is likely a function of device rating, availability or capacity
			return cost.getValue(installedIn, Integer.class);
		} else {
			return 0;
		}
	}
}
