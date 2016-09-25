package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;

public class Project extends Element {
	public Project() {
		super(new TagName("project"));
		
		setAttribute(AttributeName.of("version"), new AttributeValue("4"));
	}
	
	public void addComponent(final Component component) {
		add(component);
	}
}
