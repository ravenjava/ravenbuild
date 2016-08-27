package org.ravenbuild.plugins;

import java.util.List;

public interface ClasspathScanner {
	<T> List<Class<? extends T>> findAllClassesImplementing(Class<T> interfaceType);
}
