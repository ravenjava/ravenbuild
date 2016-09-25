package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import com.ravensuite.value.StringValue;

public class Component extends Element {
	protected Component(final ComponentName componentName) {
		super(new TagName("component"));
		
		setAttribute(AttributeName.of("name"), componentName);
	}
	
	public static class ComponentName extends AttributeValue {
		public ComponentName(final String value) {
			super(value);
		}
	}
}
