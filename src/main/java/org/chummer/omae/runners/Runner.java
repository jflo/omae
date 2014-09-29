package org.chummer.omae.runners;

import java.nio.file.Path;

public class Runner {
	private String name;
	private Path chumFile;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Path getChumFile() {
		return chumFile;
	}
	public void setChumFile(Path chumFile) {
		this.chumFile = chumFile;
	}
	
	
}
