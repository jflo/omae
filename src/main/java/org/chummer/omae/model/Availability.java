package org.chummer.omae.model;

public class Availability {

	Legality legal;
	int value;
	
	public Legality getLegal() {
		return legal;
	}
	public void setLegal(Legality legal) {
		this.legal = legal;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public Availability() {
		value = 0;
		legal = Legality.LEGAL;
	}
	
	public Availability(int value, Legality l) {
		this.value = value;
		this.legal = l;
	}
	
	public static Availability parse(String avail) {
		Availability retval = new Availability();
		if(avail.endsWith("F")) {
			retval.legal = Legality.FORBIDDEN;
			retval.value = Integer.parseInt(avail.substring(0, avail.length()-2));
		} else if(avail.endsWith("R")) {
			retval.legal = Legality.RESTRICTED;
			retval.value = Integer.parseInt(avail.substring(0, avail.length()-2));
		} else {
			retval.legal = Legality.LEGAL;
			retval.value = Integer.parseInt(avail);
		}
		return retval;
	}
	
	
}
