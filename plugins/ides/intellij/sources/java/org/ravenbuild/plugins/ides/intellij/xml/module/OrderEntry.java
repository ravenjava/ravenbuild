package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import net.davidtanzer.jdefensive.Args;

public abstract class OrderEntry extends Element implements ModuleContent {
	protected OrderEntry(final Type type) {
		super(new TagName("orderEntry"));
		
		Args.notNull(type, "type");
		setAttribute(AttributeName.of("type"), type);
	}
	
	public static class Type extends AttributeValue {
		public Type(final String value) {
			super(value);
		}
	}
}
