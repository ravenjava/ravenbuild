package org.ravenbuild.plugins.ides.intellij.xml.project;

import com.ravensuite.ravenxml.*;
import net.davidtanzer.jdefensive.Args;

public class Modules extends Element {
	public Modules() {
		super(new TagName("modules"));
	}
	
	public void addModule(final String relativeModulePath) {
		Args.notNull(relativeModulePath, "relativeModulePath");
		add(new Module(relativeModulePath));
	}
	
	private class Module extends Element {
		private Module(final String relativeModulePath) {
			super(new TagName("module"));
			assert relativeModulePath != null : "Parent class never passes in null here";
			
			setAttribute(AttributeName.of("fileurl"), new AttributeValue("file://$PROJECT_DIR$/"+relativeModulePath));
			setAttribute(AttributeName.of("filepath"), new AttributeValue("$PROJECT_DIR$/"+relativeModulePath));
		}
	}
}
