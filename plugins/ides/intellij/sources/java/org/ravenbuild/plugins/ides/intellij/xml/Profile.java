package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import net.davidtanzer.jdefensive.Args;

public class Profile extends Element {
	public Profile() {
		super(new TagName("profile"));
		
		setAttribute(AttributeName.of("default"), new AttributeValue("true"));
		setAttribute(AttributeName.of("name"), new AttributeValue("Default"));
		setAttribute(AttributeName.of("enabled"), new AttributeValue("false"));
	}
	
	public void add(final ProcessorPath processorPath) {
		Args.notNull(processorPath, "processorPath");
		
		super.add(processorPath);
	}
}
