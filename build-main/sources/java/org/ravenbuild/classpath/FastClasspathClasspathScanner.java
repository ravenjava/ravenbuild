package org.ravenbuild.classpath;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.util.ArrayList;
import java.util.List;

public class FastClasspathClasspathScanner implements org.ravenbuild.plugins.ClasspathScanner {
	
	public FastClasspathClasspathScanner() {
	}
	
	@Override
	public <T> List<Class<? extends T>> findAllClassesImplementing(final Class<T> interfaceType) {
		final FastClasspathScanner scanner = new FastClasspathScanner();
		
		final List<Class<? extends T>> scanResult = new ArrayList<>();
		scanner.matchClassesImplementing(interfaceType, (implementingClass) -> scanResult.add(implementingClass));

		scanner.scan();

		return scanResult;
	}
}
