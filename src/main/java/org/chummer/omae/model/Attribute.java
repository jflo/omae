package org.chummer.omae.model;

public class Attribute {
	//i know properties should be private. don't care.
	public AttributeType type;
	public int metatypeMin;
	public int metatypeMax;
	public int metatypeAugMax;
	public int value;
	public int base;
	public int karma;
	public int augMod;
	
	public int total() {
		return value+augMod;
	}
	
	//TODO: add links to contributing augments if augmod > 0
}
