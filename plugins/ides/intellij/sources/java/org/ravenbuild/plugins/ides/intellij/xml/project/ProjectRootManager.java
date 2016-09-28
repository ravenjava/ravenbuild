package org.ravenbuild.plugins.ides.intellij.xml.project;

import com.ravensuite.ravenxml.AttributeName;
import com.ravensuite.ravenxml.AttributeValue;
import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.plugins.ides.intellij.xml.Component;
import org.ravenbuild.plugins.ides.intellij.xml.Output;

public class ProjectRootManager extends Component {
	public ProjectRootManager(final String languageLevel) {
		super(new ComponentName("ProjectRootManager"));
		
		Args.notNull(languageLevel, "languageLevel");
		
		setAttribute(AttributeName.of("version"), new AttributeValue("2"));
		setAttribute(AttributeName.of("languageLevel"), new AttributeValue(languageLevel));
		setAttribute(AttributeName.of("default"), new AttributeValue("false"));
		setAttribute(AttributeName.of("assert-keyword"), new AttributeValue("true"));
		setAttribute(AttributeName.of("jdk-15"), new AttributeValue("true"));
		
		//FIXME how do we set the project JDK?
		setAttribute(AttributeName.of("project-jdk-name"), new AttributeValue("1.8"));
		setAttribute(AttributeName.of("project-jdk-type"), new AttributeValue("JavaSDK"));
	}
	
	public void add(final Output output) {
		Args.notNull(output, "output");
		
		super.add(output);
	}
}
