package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;

public class ProcessorPath extends Element {
	public ProcessorPath() {
		super(new TagName("processorPath"));
		
		setAttribute(AttributeName.of("useClasspath"), new AttributeValue("true"));
	}
}
