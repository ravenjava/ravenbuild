package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;

public class SourceFolder extends OrderEntry {
	public SourceFolder() {
		super(new Type("sourceFolder"));
		
		setAttribute(AttributeName.of("forTests"), new AttributeValue("false"));
	}
}
