package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import net.davidtanzer.jdefensive.Args;

public class Module extends Element {
	public Module() {
		super(new TagName("module"));
		
		//FIXME JAVA_MODULE is probably not the correct type when we generalize this ;)
		setAttribute(AttributeName.of("type"), new AttributeValue("JAVA_MODULE"));
		setAttribute(AttributeName.of("version"), new AttributeValue("4"));
	}
	
	public void add(final NewModuleRootManager newModuleRootManager) {
		Args.notNull(newModuleRootManager, "newModuleRootManager");
		
		super.add(newModuleRootManager);
	}
}
