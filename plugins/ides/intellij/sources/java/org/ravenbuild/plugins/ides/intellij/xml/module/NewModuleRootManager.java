package org.ravenbuild.plugins.ides.intellij.xml.module;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import com.ravensuite.value.StringValue;
import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.ides.intellij.xml.Component;

public class NewModuleRootManager extends Component {
	public NewModuleRootManager() {
		super(new ComponentName("NewModuleRootManager"));
		
		setAttribute(AttributeName.of("inherit-compiler-output"), new AttributeValue("false"));
	}
	
	public void setLanguageLevel(final LanguageLevel languageLevel) {
		Args.notNull(languageLevel, "languageLevel");
		
		setAttribute(AttributeName.of("LANGUAGE_LEVEL"), languageLevel);
	}
	
	public void add(final ModuleContent moduleContent) {
		Args.notNull(moduleContent, "moduleContent");
		super.add(moduleContent);
	}
	
	public static class LanguageLevel extends AttributeValue {
		public LanguageLevel(final String value) {
			super(value);
		}
	}
}
