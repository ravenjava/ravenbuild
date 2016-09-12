package org.ravenbuild.plugins.dependencies;

public class DependenciesTaskOptions {
	private boolean initialize = true;
	private boolean showUpdates;
	private boolean update;
	private boolean tree;
	
	public boolean isInitialize() {
		return initialize;
	}
	
	public boolean isShowUpdates() {
		return showUpdates;
	}
	
	public boolean isUpdate() {
		return update;
	}
	
	public boolean isTree() {
		return tree;
	}
}
