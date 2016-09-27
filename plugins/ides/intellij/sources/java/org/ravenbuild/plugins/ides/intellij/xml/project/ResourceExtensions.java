package org.ravenbuild.plugins.ides.intellij.xml.project;

import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import org.ravenbuild.plugins.ides.intellij.xml.project.CompilerConfigurationSection;

public class ResourceExtensions extends Element implements CompilerConfigurationSection {
	public ResourceExtensions() {
		super(new TagName("resourceExtensions"));
	}
}
