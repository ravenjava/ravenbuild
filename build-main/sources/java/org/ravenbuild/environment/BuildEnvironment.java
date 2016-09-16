package org.ravenbuild.environment;

import net.davidtanzer.jdefensive.Args;

import java.io.Reader;

public class BuildEnvironment {
	private final RunMode runMode;
	
	public BuildEnvironment(final RunMode runMode) {
		Args.notNull(runMode, "runMode");
		this.runMode = runMode;
	}
	
	public Reader readFile(final String path) {
		return null;
	}
}
