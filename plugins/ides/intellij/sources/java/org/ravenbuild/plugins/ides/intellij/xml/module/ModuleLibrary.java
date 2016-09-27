package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.*;

public class ModuleLibrary extends OrderEntry {
	public ModuleLibrary(final JarUrl jarUrl) {
		super(new Type("module-library"));
		
		Library library = new Library();
		add(library);
		library.add(new LibraryEntry("CLASSES", jarUrl));
	}
	
	public static class JarUrl extends AttributeValue {
		public JarUrl(final String value) {
			super(value);
		}
	}
	
	private class Library extends Element {
		public Library() {
			super(new TagName("library"));
		}
		
		public void add(final LibraryEntry libraryEntry) {
			super.add(libraryEntry);
		}
	}
	
	private class LibraryEntry extends Element {
		public LibraryEntry(final String name, final JarUrl jarUrl) {
			super(new TagName(name));
			
			add(new Root(jarUrl));
		}
	}
	
	private class Root extends Element {
		public Root(final JarUrl jarUrl) {
			super(new TagName("root"));
			
			setAttribute(AttributeName.of("url"), jarUrl);
		}
	}
}
