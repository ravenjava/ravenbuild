package org.ravenbuild.plugins.java.dependencies;

import net.davidtanzer.jdefensive.Args;
import net.davidtanzer.jdefensive.Assert;
import org.ravenbuild.plugins.dependencies.DependenciesType;
import org.ravenbuild.plugins.dependencies.Dependency;

public class JavaDependenciesType implements DependenciesType {
	private JavaDependenciesConsumer dependenciesConsumer;
	
	@Override
	public void dependencyResolved(final Dependency dependency) {
		Args.notNull(dependency, "dependency");
		Assert.is(dependenciesConsumer, Assert.notNull("dependenciesConsumer"));
		
		dependenciesConsumer.addDependency(dependency);
	}
	
	public void reportDependenciesTo(final JavaDependenciesConsumer dependenciesConsumer) {
		Args.notNull(dependenciesConsumer, "dependenciesConsumer");
		this.dependenciesConsumer = dependenciesConsumer;
	}
}
