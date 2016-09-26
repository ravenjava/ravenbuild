package org.ravenbuild.plugins.ides.intellij.xml;

import com.ravensuite.ravenxml.Element;
import com.ravensuite.ravenxml.TagName;
import net.davidtanzer.jdefensive.Args;

public class AnnotationProcessing extends Element implements CompilerConfigurationSection {
	public AnnotationProcessing() {
		super(new TagName("annotationProcessing"));
	}
	
	public void add(final Profile profile) {
		Args.notNull(profile, "profile");
		
		super.add(profile);
	}
}
