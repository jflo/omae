package org.chummer.omae.model;

public class Movement {

	public int walk;
	public int run;
	public int sprint;
	public int swim;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + run;
		result = prime * result + sprint;
		result = prime * result + swim;
		result = prime * result + walk;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		if (run != other.run)
			return false;
		if (sprint != other.sprint)
			return false;
		if (swim != other.swim)
			return false;
		if (walk != other.walk)
			return false;
		return true;
	}
	
	
}
