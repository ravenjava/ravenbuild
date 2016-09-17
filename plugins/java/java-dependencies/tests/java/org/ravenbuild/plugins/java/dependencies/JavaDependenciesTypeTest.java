package org.ravenbuild.plugins.java.dependencies;

import org.junit.Test;
import org.ravenbuild.plugins.dependencies.Dependency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JavaDependenciesTypeTest {
	@Test
	public void reportsDependenciesToItsDependenciesConsumer() {
		JavaDependenciesType dependenciesType = new JavaDependenciesType();
		JavaDependenciesConsumer dependenciesConsumer = mock(JavaDependenciesConsumer.class);
		dependenciesType.reportDependenciesTo(dependenciesConsumer);
		
		Dependency dependency = mock(Dependency.class);
		dependenciesType.dependencyResolved(dependency);
		
		verify(dependenciesConsumer).addDependency(dependency);
	}
}