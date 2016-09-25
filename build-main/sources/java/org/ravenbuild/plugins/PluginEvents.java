package org.ravenbuild.plugins;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.events.DependenciesResolvedEventListener;

import java.util.ArrayList;
import java.util.List;

public class PluginEvents {
	private final List<DependenciesResolvedEventListener> dependenciesResolvedEventListeners = new ArrayList<>();
	public void onDependenciesResolved(final DependenciesResolvedEventListener listener) {
		Args.notNull(listener, "listener");
		dependenciesResolvedEventListeners.add(listener);
	}
	
	void dependenciesResolved() {
		for(DependenciesResolvedEventListener listener : dependenciesResolvedEventListeners) {
			listener.dependenciesResolved();
		}
	}
}
