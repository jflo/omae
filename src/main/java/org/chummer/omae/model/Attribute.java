package org.chummer.omae.model;

public class Attribute {
	AttributeType type;
	int metatypeMin;
	int metatypeMax;
	int metatypeAugMax;
	int value;
	int base;
	int karma;
	int augMod;
	
	public int total() {
		return value+augMod;
	}
}
