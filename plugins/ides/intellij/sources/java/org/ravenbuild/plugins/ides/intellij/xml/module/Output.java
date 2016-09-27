package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import net.davidtanzer.jdefensive.Args;

public class Output extends Element implements ModuleContent {
	public Output(final Url url) {
		super(new TagName("output"));
		
		Args.notNull(url, "url");
		setAttribute(AttributeName.of("url"), url);
	}
	
	public static class Url extends AttributeValue {
		public Url(final String value) {
			super(value);
		}
	}
}
