package org.chummer.omae.model;

import org.chummer.omae.chumfile.ChummerToSpel;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

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
	//stupid YHT softweave
	public String costExpression;
	private ExpressionParser parser = new SpelExpressionParser();
	
	public float getCurrentCapacity() {
		if(capacity != null) {
			return capacity.getValue(installedIn, Float.class);
		} else {
			return 0.0f;
		}
	}
	
	public int getCost() {
		if(costExpression != null) {
			//depends on the Expression actually, if it contained "Armor" then you want to pass in what this is installed in.
			//damn you YHT softweave!
			if(costExpression.contains("Armor ")) {
				String spel = ChummerToSpel.translate(costExpression.replaceAll("Armor ", ""));
				cost = parser.parseExpression(spel);
				return cost.getValue(installedIn, Integer.class);
			} else {
			//otherwise, you likely want to use "this" since the expression is likely a function of device rating, availability or capacity
				String spel = ChummerToSpel.translate(costExpression);
				cost = parser.parseExpression(spel);
				return cost.getValue(this, Integer.class);
			}
		} else {
			return 0;
		}
	}
}
