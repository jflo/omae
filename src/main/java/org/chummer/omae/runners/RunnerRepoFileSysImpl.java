package org.chummer.omae.runners;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class RunnerRepoFileSysImpl implements RunnerRepo {

	private Logger log = Logger.getLogger(RunnerRepoFileSysImpl.class);

	@Override
	public void addRunner(String userName, String runnerName,
			MultipartFile chumFile) throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName);
		if (Files.exists(userHomeDir)
				&& Files.isDirectory(userHomeDir, LinkOption.NOFOLLOW_LINKS)) {
			log.debug("home dir for " + userName + " found");
		} else {
			log.debug("home dir for " + userName + " NOT found, will create");
			Files.createDirectory(userHomeDir);

		}
		if (!chumFile.isEmpty()) {
			Path runnerFile = Paths.get(userHomeDir.toString(), runnerName
					+ ".chum5");
			byte[] bytes = chumFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					Files.newOutputStream(runnerFile, StandardOpenOption.CREATE_NEW));
			stream.write(bytes);
			stream.close();

		} else {
			IOException ioe = new IOException("File received is empty");
			ioe.fillInStackTrace();
			throw ioe;
		}

	}

	@Override
	public List<Runner> getRunners(String userName) throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName+"/");
		DirectoryStream<Path> stream = Files.newDirectoryStream(userHomeDir,
				"*.chum5");
		ArrayList<Runner> retval = new ArrayList<Runner>();
		for (Path entry : stream) {
			Runner r = new Runner();
			r.setChumFile(entry);
			String filename = entry.getFileName().toString();
			String runnerName = filename.substring(0, filename.lastIndexOf(".chum5"));
			r.setName(runnerName);
			retval.add(r);
		}
		return retval;
	}

	@Override
	public void updateRunner(String userName, String runnerName,
			MultipartFile chumFile) throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName);
		if (Files.exists(userHomeDir)
				&& Files.isDirectory(userHomeDir, LinkOption.NOFOLLOW_LINKS)) {
			log.debug("home dir for " + userName + " found");
		} else {
			IOException ioe = new IOException("File Not Found");
			ioe.fillInStackTrace();
			throw ioe;
		}
		if (!chumFile.isEmpty()) {
			Path runnerFile = Paths.get(userHomeDir.toString(), runnerName
					+ ".chum5");
			byte[] bytes = chumFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					Files.newOutputStream(runnerFile, StandardOpenOption.WRITE));
			stream.write(bytes);
			stream.close();

		} else {
			IOException ioe = new IOException("File received is empty, no overwrite");
			ioe.fillInStackTrace();
			throw ioe;
		}
		
	}

	@Override
	public InputStream downloadChumFile(String userName, String runnerName)
			throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName);
		Path runnerFile = Paths.get(userHomeDir.toString(), runnerName
				+ ".chum5");
		return new BufferedInputStream(Files.newInputStream(runnerFile, StandardOpenOption.READ));
	}

}
