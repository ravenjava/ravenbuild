package org.ravenbuild.subprojects;

public class SubProjectBuilderFactory {
	public SubProjectBuilder getSubProjectBuilder(final SubProject subProject) {
		return new SubProjectBuilder();
	}
}
