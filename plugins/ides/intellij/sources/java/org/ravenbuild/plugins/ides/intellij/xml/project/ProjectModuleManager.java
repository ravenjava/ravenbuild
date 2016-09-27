package org.ravenbuild.plugins.ides.intellij.xml.project;

import org.ravenbuild.plugins.ides.intellij.xml.Component;

public class ProjectModuleManager extends Component {
	public ProjectModuleManager() {
		super(new Component.ComponentName("ProjectModuleManager"));
	}
	
	public void addModules(final Modules modules) {
		this.add(modules);
	}
}
