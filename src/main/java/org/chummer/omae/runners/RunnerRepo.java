package org.chummer.omae.runners;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface RunnerRepo {
	
	public void addRunner(String userName, String runnerName, MultipartFile chumFile) throws IOException;
	
	public List<Runner> getRunners(String userName) throws IOException ;
	
	public void updateRunner(String userName, String runnerName, MultipartFile chumFile) throws IOException;
	
	public InputStream downloadChumFile(String userName, String runnerName) throws IOException;
}
