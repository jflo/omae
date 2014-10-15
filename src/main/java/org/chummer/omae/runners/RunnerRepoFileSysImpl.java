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
			MultipartFile chumFile, MultipartFile renderFile) throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName);
		if (Files.exists(userHomeDir)
				&& Files.isDirectory(userHomeDir, LinkOption.NOFOLLOW_LINKS)) {
			log.debug("home dir for " + userName + " found");
		} else {
			log.debug("home dir for " + userName + " NOT found, will create");
			Files.createDirectory(userHomeDir);

		}
		if (!chumFile.isEmpty() && !renderFile.isEmpty()) {
			Path toChumFile = Paths.get(userHomeDir.toString(), runnerName
					+ ".chum5");
			
			BufferedOutputStream stream = new BufferedOutputStream(
					Files.newOutputStream(toChumFile, StandardOpenOption.CREATE_NEW));
			stream.write(chumFile.getBytes());
			stream.close();
			
			Path toRenderFile = Paths.get(userHomeDir.toString(), runnerName
					+ ".render.xml");
			
			stream = new BufferedOutputStream(
					Files.newOutputStream(toRenderFile, StandardOpenOption.CREATE_NEW));
			stream.write(renderFile.getBytes());
			stream.close();

		} else {
			IOException ioe = new IOException("one of the files received is empty");
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
	
	@Override
	public InputStream downloadRenderFile(String userName, String runnerName)
			throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName);
		Path runnerFile = Paths.get(userHomeDir.toString(), runnerName
				+ ".render.xml");
		return new BufferedInputStream(Files.newInputStream(runnerFile, StandardOpenOption.READ));
	}

	@Override
	public void deleteFiles(String userName, String runnerName)
			throws IOException {
		Path userHomeDir = Paths.get("/home/omaes/" + userName);
		Path renderFile = Paths.get(userHomeDir.toString(), runnerName
				+ ".render.xml");
		Path chumFile = Paths.get(userHomeDir.toString(), runnerName
				+ ".chum5");
		Files.delete(renderFile);
		Files.delete(chumFile);
		
	}

	@Override
	public void stun(String userName, String runnerName, int amount)
			throws IOException {
		//open renderfile
		//increment <stuncmfilled>0</stuncmfilled>
		//if overflowed, increment physical?
		
	}

	@Override
	public void physicalDamage(String userName, String runnerName, int amount)
			throws IOException {
		//increment <physicalcmfilled>0</physicalcmfilled>
		
	}

}
