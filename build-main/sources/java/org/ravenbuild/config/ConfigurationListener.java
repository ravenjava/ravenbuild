package org.ravenbuild.config;

@FunctionalInterface
public interface ConfigurationListener<T> {
	void configurationLoaded(final T configuration);
}
