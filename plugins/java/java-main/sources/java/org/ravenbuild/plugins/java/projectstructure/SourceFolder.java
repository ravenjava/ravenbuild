package org.ravenbuild.plugins.java.projectstructure;

import net.davidtanzer.jdefensive.Args;

public class SourceFolder {
	private final String relativePath;
	private final Scope scope;
	
	public static enum Scope {
		PRODUCTION, DEVELOPMENT, PROVIDED
	}

	public SourceFolder(final String relativePath, final Scope scope) {
		Args.notEmpty(relativePath, "relativePath");
		Args.notNull(scope, "scope");
		
		this.relativePath = relativePath;
		this.scope = scope;
	}
	
	public String relativePath() {
		return relativePath;
	}
	
	public Scope scope() {
		return scope;
	}
}
