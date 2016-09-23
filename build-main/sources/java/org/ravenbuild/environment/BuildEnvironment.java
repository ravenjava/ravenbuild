package org.ravenbuild.environment;

import net.davidtanzer.jdefensive.Args;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class BuildEnvironment {
	private final RunMode runMode;
	private final File buildBaseDirectory;
	private final File currentDirectory;
	
	public BuildEnvironment(final RunMode runMode, File buildBaseDirectory, File currentDirectory) {
		Args.notNull(runMode, "runMode");
		Args.notNull(buildBaseDirectory, "buildBaseDirectory");
		Args.notNull(currentDirectory, "currentDirectory");
		
		this.runMode = runMode;
		this.buildBaseDirectory = buildBaseDirectory;
		this.currentDirectory = currentDirectory;
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
		
	}
}
