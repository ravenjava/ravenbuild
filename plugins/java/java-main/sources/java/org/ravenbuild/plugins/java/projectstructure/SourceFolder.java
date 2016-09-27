package org.ravenbuild.plugins.java.projectstructure;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.ides.intellij.java.JavaSourceFolder;

public class SourceFolder implements JavaSourceFolder {
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
	
	@Override
	public String relativePath() {
		return relativePath;
	}
	
	@Override
	public boolean isTestSource() {
		return scope == Scope.DEVELOPMENT;
	}
	
	public Scope scope() {
		return scope;
	}
}
