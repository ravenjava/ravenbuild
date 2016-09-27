package org.ravenbuild.plugins.ides.intellij.xml.project;

import com.ravensuite.ravenxml.*;
import org.ravenbuild.plugins.ides.intellij.xml.project.CompilerConfigurationSection;

public class WildcardResourcePatterns extends Element implements CompilerConfigurationSection {
	public WildcardResourcePatterns() {
		super(new TagName("wildcardResourcePatterns"));
	}
	
	public void addPattern(final String wildcardPattern) {
		add(new Entry(wildcardPattern));
	}
	
	private class Entry extends Element {
		public Entry(final String wildcardPattern) {
			super(new TagName("entry"));
			
			setAttribute(AttributeName.of("name"), new AttributeValue("!?"+wildcardPattern));
		}
	}
}
