package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.*;
import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.ides.intellij.xml.ModuleContent;

public class Content extends Element implements ModuleContent {
	public Content(final Url url) {
		super(new TagName("content"));
		
		Args.notNull(url, "url");
		setAttribute(AttributeName.of("url"), url);
	}
	
	public void addSourceFolder(final String relativePath, final boolean isTestSource) {
		add(new ModuleSourceFolder(relativePath, isTestSource));
	}
	
	public static class Url extends AttributeValue {
		public Url(final String value) {
			super(value);
		}
	}
	
	private class ModuleSourceFolder extends Element {
		public ModuleSourceFolder(final String relativePath, final boolean isTestSource) {
			super(new TagName("sourceFolder"));
			
			setAttribute(AttributeName.of("url"), new AttributeValue("file://$MODULE_DIR$/"+relativePath));
			setAttribute(AttributeName.of("isTestSource"), new AttributeValue(String.valueOf(isTestSource)));
		}
	}
}
