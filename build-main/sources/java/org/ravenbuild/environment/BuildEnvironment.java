package org.ravenbuild.environment;

import net.davidtanzer.jdefensive.Args;

import java.io.*;

public class BuildEnvironment {
	private final RunMode runMode;
	private final File buildBaseDirectory;
	private final File currentDirectory;
	private final File writingBaseDirectory;
	private final File writingCurrentDirectory;
	
	public BuildEnvironment(final RunMode runMode, File buildBaseDirectory, File currentDirectory) {
		Args.notNull(runMode, "runMode");
		Args.notNull(buildBaseDirectory, "buildBaseDirectory");
		Args.notNull(currentDirectory, "currentDirectory");
		
		this.runMode = runMode;
		this.buildBaseDirectory = buildBaseDirectory;
		this.currentDirectory = currentDirectory;
		
		if(runMode == RunMode.ALLOW_MODIFICATIONS) {
			writingBaseDirectory = buildBaseDirectory;
			writingCurrentDirectory = currentDirectory;
		} else {
			writingBaseDirectory = new File(buildBaseDirectory, "dryrun");
			writingBaseDirectory.delete();
			String relativePathToCurrent = buildBaseDirectory.toURI().relativize(currentDirectory.toURI()).getPath();
			writingCurrentDirectory = new File(writingBaseDirectory, relativePathToCurrent);
		}
	}
	
	public Reader readFile(final String path) {
		try {
			return new FileReader(getFile(path));
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("FIXME: Add better error handling!");
		}
	}
	
	public File getFile(final String path) {
		if (path.startsWith("/")) {
			return new File(buildBaseDirectory, path.substring(1));
		} else {
			return new File(currentDirectory, path);
		}
	}
	
	public void writeFile(final String path, final FileWriterHandler fileWriterHandler) {
		File file = getFileForWriting(path);
		file.getParentFile().mkdirs();
		
		try(FileWriter fileWriter = new FileWriter(file)) {
			fileWriterHandler.write(fileWriter);
		} catch (IOException e) {
			throw new IllegalStateException("FIXME: Better error. Could not write file.", e);
		}
	}
	
	private File getFileForWriting(final String path) {
		if(runMode == RunMode.ALLOW_MODIFICATIONS) {
			return getFile(path);
		}
		
		if (path.startsWith("/")) {
			return new File(writingBaseDirectory, path.substring(1));
		} else {
			return new File(writingCurrentDirectory, path);
		}
	}
	
	public File buildBaseDirectory() {
		return buildBaseDirectory;
	}
}
