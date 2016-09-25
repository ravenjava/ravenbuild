package org.ravenbuild.plugins.events;

@FunctionalInterface
public interface DependenciesResolvedEventListener {
	void dependenciesResolved();
}
