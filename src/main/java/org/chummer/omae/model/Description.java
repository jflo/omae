package org.chummer.omae.model;

public class Description {
	public String sex;
	public int age;
	public String eyes;
	public String hair;
	public String skin;
	public String height;
	public String weight;
	public String description;
	public String background;
	public String concept;
	public String notes;
	public String alias;

	public Description() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result
				+ ((background == null) ? 0 : background.hashCode());
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((eyes == null) ? 0 : eyes.hashCode());
		result = prime * result + ((hair == null) ? 0 : hair.hashCode());
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((skin == null) ? 0 : skin.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Description other = (Description) obj;
		if (age != other.age)
			return false;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (background == null) {
			if (other.background != null)
				return false;
		} else if (!background.equals(other.background))
			return false;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (eyes == null) {
			if (other.eyes != null)
				return false;
		} else if (!eyes.equals(other.eyes))
			return false;
		if (hair == null) {
			if (other.hair != null)
				return false;
		} else if (!hair.equals(other.hair))
			return false;
		if (height == null) {
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (skin == null) {
			if (other.skin != null)
				return false;
		} else if (!skin.equals(other.skin))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
	
}