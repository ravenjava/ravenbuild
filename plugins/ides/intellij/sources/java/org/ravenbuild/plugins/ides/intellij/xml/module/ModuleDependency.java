package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import net.davidtanzer.jdefensive.Args;

public class ModuleDependency extends OrderEntry {
	public ModuleDependency(final ModuleName moduleName) {
		super(new Type("module"));
		
		Args.notNull(moduleName, "moduleName");
		setAttribute(AttributeName.of("module-name"), moduleName);
	}
	
	public static class ModuleName extends AttributeValue {
		public ModuleName(final String value) {
			super(value);
		}
	}
}
