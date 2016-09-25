package org.ravenbuild.plugins.ides.intellij.xml;

public class ProjectModuleManager extends Component {
	public ProjectModuleManager() {
		super(new Component.ComponentName("ProjectModuleManager"));
	}
	
	public void addModules(final Modules modules) {
		this.add(modules);
	}
}
