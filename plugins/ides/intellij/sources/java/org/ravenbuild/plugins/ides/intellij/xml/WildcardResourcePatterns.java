package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.*;

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
