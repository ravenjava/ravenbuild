package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import net.davidtanzer.jdefensive.Args;

public class TestOutput extends Element implements ModuleContent {
	public TestOutput(final Output.Url url) {
		super(new TagName("output-test"));
		
		Args.notNull(url, "url");
		setAttribute(AttributeName.of("url"), url);
	}
}
