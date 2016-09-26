package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;

public class ResourceExtensions extends Element implements CompilerConfigurationSection {
	public ResourceExtensions() {
		super(new TagName("resourceExtensions"));
	}
}
